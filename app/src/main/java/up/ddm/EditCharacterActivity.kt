package up.ddm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider

class EditCharacterActivity : ComponentActivity() {

    private lateinit var viewModel: GameCharacterViewModel
    private lateinit var character: GameCharacterEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up the DAO, repository, and ViewModel as in CharacterListActivity
        val database = AppDatabase.getDatabase(this)
        val gameCharacterDao = database.gameCharacterDao()
        val repository = GameCharacterRepository(gameCharacterDao)
        val factory = GameCharacterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(GameCharacterViewModel::class.java)

        // Retrieve the character data passed from the intent
        character = intent.getParcelableExtra("CHARACTER_DATA") ?: throw IllegalArgumentException("Character data missing.")

        setContent {
            EditCharacterScreen(character, viewModel) {
                // Handle successful character edit, perhaps navigate back or display a success message
                finish() // Close the activity after editing
            }
        }
    }
}

@Composable
fun EditCharacterScreen(character: GameCharacterEntity, viewModel: GameCharacterViewModel, onEditComplete: () -> Unit) {
    var name by remember { mutableStateOf(character.name) }
    var race by remember { mutableStateOf(character.race) }
    var level by remember { mutableStateOf(character.level) }
    var strength by remember { mutableStateOf(character.strength) }
    var dexterity by remember { mutableStateOf(character.dexterity) }
    var constitution by remember { mutableStateOf(character.constitution) }
    var intelligence by remember { mutableStateOf(character.intelligence) }
    var wisdom by remember { mutableStateOf(character.wisdom) }
    var charisma by remember { mutableStateOf(character.charisma) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Edit Character", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        OutlinedTextField(
            value = race,
            onValueChange = { race = it },
            label = { Text("Race") }
        )

        OutlinedTextField(
            value = level.toString(),
            onValueChange = { level = it.toIntOrNull() ?: level },
            label = { Text("Level") }
        )

        OutlinedTextField(
            value = strength.toString(),
            onValueChange = { strength = it.toIntOrNull() ?: strength },
            label = { Text("Strength") }
        )

        OutlinedTextField(
            value = dexterity.toString(),
            onValueChange = { dexterity = it.toIntOrNull() ?: dexterity },
            label = { Text("Dexterity") }
        )

        OutlinedTextField(
            value = constitution.toString(),
            onValueChange = { constitution = it.toIntOrNull() ?: constitution },
            label = { Text("Constitution") }
        )

        OutlinedTextField(
            value = intelligence.toString(),
            onValueChange = { intelligence = it.toIntOrNull() ?: intelligence },
            label = { Text("Intelligence") }
        )

        OutlinedTextField(
            value = wisdom.toString(),
            onValueChange = { wisdom = it.toIntOrNull() ?: wisdom },
            label = { Text("Wisdom") }
        )

        OutlinedTextField(
            value = charisma.toString(),
            onValueChange = { charisma = it.toIntOrNull() ?: charisma },
            label = { Text("Charisma") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Create a new GameCharacterEntity with updated values
            val updatedCharacter = character.copy(
                name = name,
                race = race,
                level = level,
                strength = strength,
                dexterity = dexterity,
                constitution = constitution,
                intelligence = intelligence,
                wisdom = wisdom,
                charisma = charisma
            )

            // Save the updated character using the ViewModel
            viewModel.updateCharacter(updatedCharacter) { success ->
                if (success) {
                    onEditComplete() // Notify that editing is complete
                } else {
                    errorMessage = "Failed to update character."
                }
            }
        }) {
            Text("Save Changes")
        }

        if (errorMessage != null) {
            Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
        }
    }
}
