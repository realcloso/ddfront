package up.ddm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameCharacterViewModel(private val repository: GameCharacterRepository) : ViewModel() {

    fun insert(character: GameCharacterEntity) {
        viewModelScope.launch {
            repository.insert(character)
        }
    }

    fun update(character: GameCharacterEntity) {
        viewModelScope.launch {
            repository.update(character)
        }
    }

    fun delete(character: GameCharacterEntity) {
        viewModelScope.launch {
            repository.delete(character)
        }
    }

    fun getCharacterById(id: Int, onResult: (GameCharacterEntity?) -> Unit) {
        viewModelScope.launch {
            onResult(repository.getCharacterById(id))
        }
    }

    fun getAllCharacters(onResult: (List<GameCharacterEntity>) -> Unit) {
        viewModelScope.launch {
            onResult(repository.getAllCharacters())
        }
    }
}
