package up.ddm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameCharacterViewModelFactory(
    private val repository: GameCharacterRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the ViewModel class is GameCharacterViewModel
        if (modelClass.isAssignableFrom(GameCharacterViewModel::class.java)) {
            // Safely cast and return the ViewModel instance
            @Suppress("UNCHECKED_CAST")
            return GameCharacterViewModel(repository) as T
        }
        // Throw an exception for unknown ViewModel classes
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
