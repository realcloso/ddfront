package up.ddm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Suppress("DEPRECATION")
class CharacterDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recuperar os dados do personagem passados através do Intent
        val character = intent.getParcelableExtra<GameCharacterEntity>("CHARACTER")

        setContent {
            character?.let {
                CharacterDetailScreen(character = it)
            } ?: run {
                // Tratar caso de personagem nulo
                Text("Personagem não encontrado.", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun CharacterDetailScreen(character: GameCharacterEntity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Nome: ${character.name}", style = MaterialTheme.typography.titleLarge)
        Text("Raça: ${character.race}")
        Text("Nível: ${character.level}")
        Text("Força: ${character.strength}")
        Text("Destreza: ${character.dexterity}")
        Text("Constituição: ${character.constitution}")
        Text("Inteligência: ${character.intelligence}")
        Text("Sabedoria: ${character.wisdom}")
        Text("Carisma: ${character.charisma}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Ação para editar o personagem ou retornar à lista
        }) {
            Text("Editar Personagem")
        }
    }
}
