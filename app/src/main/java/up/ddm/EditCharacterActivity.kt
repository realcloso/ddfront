@file:Suppress("DEPRECATION")

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

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Create a new GameCharacterEntity with updated values
            val updatedCharacter = character.copy(
                name = name
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
            Text("Salvar mudanças")
        }

        if (errorMessage != null) {
            Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
        }
    }
}
