package up.ddm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CharacterDetailActivity : ComponentActivity() {

    private lateinit var viewModel: GameCharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up the DAO, repository, and ViewModel
        val database = AppDatabase.getDatabase(this)
        val gameCharacterDao = database.gameCharacterDao()
        val repository = GameCharacterRepository(gameCharacterDao)
        val factory = GameCharacterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(GameCharacterViewModel::class.java)

        // Retrieve the character ID passed from the intent
        val characterId = intent.getIntExtra("CHARACTER_ID", -1)

        setContent {
            if (characterId != -1) {
                CharacterDetailScreen(viewModel, characterId) { character ->
                    // Navigate to EditCharacterActivity when character is clicked
                    val intent = Intent(this@CharacterDetailActivity, EditCharacterActivity::class.java).apply {
                        putExtra("CHARACTER_DATA", character) // Ensure GameCharacterEntity implements Parcelable
                    }
                    startActivity(intent)
                }
            } else {
                // Display an error if no valid character ID was provided
                Text("Personagem não encontrado.", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun CharacterDetailScreen(viewModel: GameCharacterViewModel, characterId: Int, onEditCharacter: (GameCharacterEntity) -> Unit) {
    var character by remember { mutableStateOf<GameCharacterEntity?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope() // Create a coroutine scope

    // Fetch character details every second while the composable is displayed
    LaunchedEffect(characterId) {
        while (true) {
            viewModel.getCharacterById(characterId) { result ->
                if (result != null) {
                    character = result
                    errorMessage = null
                } else {
                    errorMessage = "Personagem não encontrado."
                }
            }
            delay(1000) // Wait for 1 second before the next fetch
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
                    // Notify that the edit action is to be performed
                    onEditCharacter(it)
                }) {
                    Text("Editar Personagem")
                }
            }
        }
    }
}
