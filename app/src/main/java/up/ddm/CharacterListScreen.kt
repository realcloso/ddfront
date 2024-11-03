package up.ddm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
        viewModel.getAllCharacters() // Correção: removido o parâmetro
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
                        CharacterListItem(character) {
                            onNavigateToCharacterDetail(character) // Navegar para a tela de detalhes do personagem
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CharacterListItem(character: GameCharacterEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Nome: ${character.name}")
            Text("Raça: ${character.race}")
            Text("Nível: ${character.level}")
        }
    }
}
