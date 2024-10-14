package up.ddm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import up.ddm.data.AtributosDAO
import up.ddm.data.AtributosDB
import up.ddm.data.PersonagemDAO

class HomeActivity : ComponentActivity() {
    private lateinit var atributosDAO: AtributosDAO
    private lateinit var personagemDAO: PersonagemDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database and DAOs
        val db = AtributosDB.getDatabase(this)
        atributosDAO = db.atributosDAO()
        personagemDAO = db.personagemDAO()

        setContent {
            HomeScreen(
                atributosDAO = atributosDAO,
                personagemDAO = personagemDAO
            )
        }
    }
}

@Composable
fun HomeScreen(
    atributosDAO: AtributosDAO,
    personagemDAO: PersonagemDAO
) {
    val context = LocalContext.current // Capture the context

    // Column to arrange buttons vertically
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Button to create character
        Button(
            onClick = {
                // Redirect to MainActivity
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Criar Personagem")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Space between buttons

        // Button to list characters
        Button(
            onClick = {
                // Redirect to CharacterListActivity
                val intent = Intent(context, CharacterListActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Listar Personagens")
        }
    }
}
