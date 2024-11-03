package up.ddm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CharacterListScreen(
    viewModel: GameCharacterViewModel,
    onNavigateToCharacterDetail: (GameCharacterEntity) -> Unit
) {
    // Observe the LiveData from the ViewModel for characters and error messages
    val charactersState by viewModel.characters.observeAsState(emptyList())
    val errorMessage by viewModel.errorMessage.observeAsState()

    // Load characters when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.getAllCharacters()
    }

    // Display the UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            errorMessage != null -> {
                Text("Erro ao carregar personagens: $errorMessage", color = MaterialTheme.colorScheme.error)
            }
            charactersState.isEmpty() -> {
                Text("Nenhum personagem encontrado.")
            }
            else -> {
                LazyColumn {
                    items(charactersState) { character ->
                        CharacterListItem(
                            character,
                            onClick = { onNavigateToCharacterDetail(character) },
                            onDeleteClick = { viewModel.delete(character) } // Delete character on icon click
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterListItem(
    character: GameCharacterEntity,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Nome: ${character.name}", fontSize = 20.sp, style = MaterialTheme.typography.titleLarge)
                Text("Raça: ${character.race}", style = MaterialTheme.typography.bodyMedium)
                Text("Nível: ${character.level}", style = MaterialTheme.typography.bodyMedium)
            }

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Character",
                    tint = Color.Red
                )
            }
        }
    }
}
