package up.ddm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider

class CharacterListActivity : ComponentActivity() {
    private lateinit var viewModel: GameCharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the DAO from the AppDatabase singleton
        val database = AppDatabase.getDatabase(this)
        val gameCharacterDao = database.gameCharacterDao() // Make sure your database class exposes this DAO

        // Create the repository with the DAO
        val repository = GameCharacterRepository(gameCharacterDao)

        // Use the ViewModelFactory to initialize the ViewModel
        val factory = GameCharacterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(GameCharacterViewModel::class.java)

        setContent {
            CharacterListScreen(viewModel) { character ->
                // Navigation to character detail screen
                val intent = Intent(this, CharacterDetailActivity::class.java)
                intent.putExtra("CHARACTER_ID", character.id) // Pass the character ID
                startActivity(intent)
            }
        }

        // Fetch characters on activity creation
        viewModel.getAllCharacters()
    }
}
