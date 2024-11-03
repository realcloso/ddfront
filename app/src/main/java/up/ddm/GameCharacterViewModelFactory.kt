package up.ddm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameCharacterViewModelFactory(private val repository: GameCharacterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameCharacterViewModel::class.java)) {
            return GameCharacterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
