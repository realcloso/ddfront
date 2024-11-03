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

    private val _operationMessage = MutableLiveData<String?>()
    val operationMessage: LiveData<String?> get() = _operationMessage

    // Fetch all characters from the repository
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

    // Insert a new character
    fun insert(character: GameCharacterEntity, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.insert(character)
                _operationMessage.value = "Personagem inserido com sucesso."
                onResult(true)
            } catch (e: Exception) {
                _operationMessage.value = "Erro ao inserir personagem: ${e.message}"
                onResult(false)
            }
        }
    }

    // Update an existing character
    fun updateCharacter(character: GameCharacterEntity, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.update(character)
                callback(true)
            } catch (e: Exception) {
                callback(false)
            }
        }
    }

    // Delete a character
    fun delete(character: GameCharacterEntity, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.delete(character)
                _operationMessage.value = "Personagem deletado com sucesso."
                onResult(true)
            } catch (e: Exception) {
                _operationMessage.value = "Erro ao deletar personagem: ${e.message}"
                onResult(false)
            }
        }
    }

    // Get a character by its ID
    fun getCharacterById(id: Int, onResult: (GameCharacterEntity?) -> Unit) {
        viewModelScope.launch {
            onResult(repository.getCharacterById(id))
        }
    }
}
