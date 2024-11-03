package up.ddm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import usecases.AttributeModifiers

class ShowCharacterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val characterName = intent.getStringExtra("CHARACTER_NAME") ?: "Desconhecido"
            val characterStrength = intent.getIntExtra("CHARACTER_STRENGTH", 8)
            val characterDexterity = intent.getIntExtra("CHARACTER_DEXTERITY", 8)
            val characterConstitution = intent.getIntExtra("CHARACTER_CONSTITUTION", 8)
            val characterIntelligence = intent.getIntExtra("CHARACTER_INTELLIGENCE", 8)
            val characterWisdom = intent.getIntExtra("CHARACTER_WISDOM", 8)
            val characterCharisma = intent.getIntExtra("CHARACTER_CHARISMA", 8)
            val characterExperiencePoints = intent.getIntExtra("CHARACTER_EXPERIENCE_POINTS", 0)
            val characterLevel = intent.getIntExtra("CHARACTER_LEVEL", 1)
            val characterRace = intent.getStringExtra("CHARACTER_RACE") ?: "Desconhecida"

            val characterLifePoints = calculateLifePoints(characterConstitution)

            ShowCharacterScreen(
                characterName,
                characterStrength,
                characterDexterity,
                characterConstitution,
                characterIntelligence,
                characterWisdom,
                characterCharisma,
                characterExperiencePoints,
                characterLevel,
                characterLifePoints,
                characterRace
            ) {
                // Navigate to HomeActivity instead of HomeScreen
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish() // Optionally close the current activity
            }
        }
    }

    private fun calculateLifePoints(constitution: Int): Int {
        val constitutionModifier = AttributeModifiers.modifiers[constitution] ?: 0
        return 10 + constitutionModifier
    }
}

@Composable
fun ShowCharacterScreen(
    name: String,
    strength: Int,
    dexterity: Int,
    constitution: Int,
    intelligence: Int,
    wisdom: Int,
    charisma: Int,
    experiencePoints: Int,
    level: Int,
    lifePoints: Int,
    race: String,
    onBackToHomeClick: () -> Unit // Add a callback parameter for the button
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Detalhes do Personagem", style = MaterialTheme.typography.titleLarge)
        Text("Nome: $name")
        Text("Raça: $race")
        Text("Força: $strength")
        Text("Destreza: $dexterity")
        Text("Constituição: $constitution")
        Text("Inteligência: $intelligence")
        Text("Sabedoria: $wisdom")
        Text("Carisma: $charisma")
        Text("Pontos de Vida: $lifePoints")
        Text("Nível: $level")
        Text("Experiência: $experiencePoints")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBackToHomeClick) {
            Text("Menu Principal")
        }
    }
}