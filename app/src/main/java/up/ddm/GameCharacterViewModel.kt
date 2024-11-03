package up.ddm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameCharacterViewModel(private val repository: GameCharacterRepository) : ViewModel() {
    private val _characters = MutableLiveData<List<GameCharacterEntity>>()
    val characters: LiveData<List<GameCharacterEntity>> get() = _characters

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun getAllCharacters() {
        viewModelScope.launch {
            repository.getAllCharacters().collect { characterList ->
                _characters.value = characterList
                if (characterList.isEmpty()) {
                    _errorMessage.value = "Nenhum personagem encontrado."
                } else {
                    _errorMessage.value = null // Clear the error message if there are characters
                }
            }
        }
    }

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
}
