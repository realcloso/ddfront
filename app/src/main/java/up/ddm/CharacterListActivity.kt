package up.ddm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

class CharacterListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CharacterListScreen()
        }
    }
}

@Composable
fun CharacterListScreen() {
    val characters = listOf("Personagem 1", "Personagem 2", "Personagem 3") // Lista de personagens

    // Layout de coluna para organizar os itens
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título da tela
        Text(
            text = "Lista de Personagens",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de personagens
        LazyColumn {
            items(characters) { character ->
                CharacterItem(character)
            }
        }
    }
}

@Composable
fun CharacterItem(character: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = character)

            // Obtenha o contexto local aqui
            val context = LocalContext.current

            Button(onClick = {
                // Ação para ver detalhes do personagem
                val intent = Intent(context, CharacterDetailActivity::class.java).apply {
                    putExtra("character_name", character)
                }
                context.startActivity(intent)
            }) {
                Text("Ver Detalhes")
            }
        }
    }
}

@Composable
fun CharacterDetailScreen(characterName: String) {
    // Detalhes do personagem
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Detalhes do $characterName",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Aqui você pode adicionar mais detalhes do personagem
        Text(text = "Informações adicionais sobre $characterName.")
    }
}

class CharacterDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val characterName = intent.getStringExtra("character_name") ?: "Desconhecido"
        setContent {
            CharacterDetailScreen(characterName)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCharacterListScreen() {
    CharacterListScreen()
}
