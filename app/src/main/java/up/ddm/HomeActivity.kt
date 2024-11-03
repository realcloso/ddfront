package up.ddm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider

class HomeActivity : ComponentActivity() {

    private lateinit var viewModel: GameCharacterViewModel // Initialize your ViewModel here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the Room database and DAO
        val database = AppDatabase.getDatabase(this) // Get the database instance
        val gameCharacterDao = database.gameCharacterDao() // Get the DAO

        // Initialize the ViewModel with the repository
        val repository = GameCharacterRepository(gameCharacterDao)
        viewModel = ViewModelProvider(this, GameCharacterViewModelFactory(repository)).get(GameCharacterViewModel::class.java)

        setContent {
            HomeScreen(
                onCreateCharacter = { newCharacter ->
                    println("Character created: ${newCharacter.name}!")
                    viewModel.insert(newCharacter.toEntity()) // Convert to GameCharacterEntity
                },
                onNavigateToCharacterList = {
                    // Navigate to the character list screen
                    navigateToCharacterList()
                }
            )
        }
    }

    private fun navigateToCharacterList() {
        // Implement the navigation logic to the CharacterListScreen
        startActivity(Intent(this, CharacterListActivity::class.java))
    }
}
