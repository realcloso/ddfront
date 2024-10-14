package up.ddm

import up.ddm.ui.AtributosScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import up.ddm.data.AtributosDAO
import up.ddm.data.PersonagemDAO
import up.ddm.data.AtributosDB
import strategies.*

// MainActivity class
class MainActivity : ComponentActivity() {
    private lateinit var atributosDAO: AtributosDAO
    private lateinit var personagemDAO: PersonagemDAO
    private lateinit var gameCharacter: GameCharacter
    private lateinit var atributos: Atributos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Room database and DAOs
        val db = AtributosDB.getDatabase(this)
        atributosDAO = db.atributosDAO()
        personagemDAO = db.personagemDAO()

        // Initialize attributes and character
        atributos = Atributos()
        gameCharacter = GameCharacter(race = HumanRace()).apply {
            atributosId = 1
            nome = "Bruenor"
        }

        setContent {
            AtributosScreen(
                atributos = atributos,
                gameCharacter = gameCharacter,
                onSaveAtributos = { saveAtributos() },
                onDeletePersonagem = { deletePersonagem() },
                onSalvarPersonagem = { salvarPersonagem() },
                personagemDAO = personagemDAO
            )
        }
    }

    private fun saveAtributos() {
        lifecycleScope.launch {
            try {
                atributosDAO.insert(atributos)
                showSnackbar("Atributos salvos com sucesso!")
            } catch (e: Exception) {
                showSnackbar("Erro ao salvar atributos: ${e.message}")
            }
        }
    }

    private fun deletePersonagem() {
        lifecycleScope.launch {
            try {
                personagemDAO.delete(gameCharacter)
                showSnackbar("Personagem deletado!")
            } catch (e: Exception) {
                showSnackbar("Erro ao deletar personagem: ${e.message}")
            }
        }
    }

    private fun salvarPersonagem() {
        lifecycleScope.launch {
            try {
                personagemDAO.getAllPersonagens().find { it.id == gameCharacter.id }?.let {
                    personagemDAO.delete(it)
                }
                personagemDAO.insert(gameCharacter)
                showSnackbar("Personagem salvo!")
            } catch (e: Exception) {
                showSnackbar("Erro ao salvar personagem: ${e.message}")
            }
        }
    }

    private fun showSnackbar(message: String) {
        // Implement Snackbar display logic here
        // You can use a SnackbarHostState to show the snackbar in your UI
    }
}
