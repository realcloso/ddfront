package up.ddm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider

class CharacterDetailActivity : ComponentActivity() {

    private lateinit var viewModel: GameCharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up the DAO, repository, and ViewModel as in CharacterListActivity
        val database = AppDatabase.getDatabase(this)
        val gameCharacterDao = database.gameCharacterDao()
        val repository = GameCharacterRepository(gameCharacterDao)
        val factory = GameCharacterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(GameCharacterViewModel::class.java)

        // Retrieve the character ID passed from the intent
        val characterId = intent.getIntExtra("CHARACTER_ID", -1)

        setContent {
            if (characterId != -1) {
                CharacterDetailScreen(viewModel, characterId)
            } else {
                // Display an error if no valid character ID was provided
                Text("Personagem não encontrado.", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun CharacterDetailScreen(viewModel: GameCharacterViewModel, characterId: Int) {
    var character by remember { mutableStateOf<GameCharacterEntity?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch character details once the composable is displayed
    LaunchedEffect(characterId) {
        viewModel.getCharacterById(characterId) { result ->
            if (result != null) {
                character = result
                errorMessage = null
            } else {
                errorMessage = "Personagem não encontrado."
            }
        }
    }

    if (errorMessage != null) {
        // Display an error message if character retrieval failed
        Text(errorMessage!!, modifier = Modifier.padding(16.dp))
    } else {
        // Display character details if the character is successfully loaded
        character?.let {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Nome: ${it.name}", style = MaterialTheme.typography.titleLarge)
                Text("Raça: ${it.race}")
                Text("Nível: ${it.level}")
                Text("Força: ${it.strength}")
                Text("Destreza: ${it.dexterity}")
                Text("Constituição: ${it.constitution}")
                Text("Inteligência: ${it.intelligence}")
                Text("Sabedoria: ${it.wisdom}")
                Text("Carisma: ${it.charisma}")

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    // Action for editing the character or returning to the list
                }) {
                    Text("Editar Personagem")
                }
            }
        }
    }
}
