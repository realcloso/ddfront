package up.ddm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import up.ddm.GameCharacter
import strategies.HumanRace
import up.ddm.AtributosScreen

@Composable
fun HomeScreen(
    onCreateCharacter: (GameCharacter) -> Unit
) {
    var showAtributosScreen by remember { mutableStateOf(false) }

    if (showAtributosScreen) {
        // Exibir a AtributosScreen quando o botão for clicado
        val newCharacter = GameCharacter(
            name = "Novo Personagem",
            strength = 8,
            dexterity = 8,
            constitution = 8,
            intelligence = 8,
            wisdom = 8,
            charisma = 8,
            race = HumanRace() // Define uma raça inicial, pode ser alterada conforme necessário
        )
        AtributosScreen(character = newCharacter)
    } else {
        // Layout principal da HomeScreen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bem-vindo ao Criador de Personagens")

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Exibir a AtributosScreen quando o botão for clicado
                    showAtributosScreen = true
                }
            ) {
                Text("Criar Novo Personagem")
            }
        }
    }
}
