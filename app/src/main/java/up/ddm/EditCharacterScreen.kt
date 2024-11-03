package up.ddm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditCharacterScreen(
    character: GameCharacterEntity,
    onSave: (GameCharacterEntity) -> Unit
) {
    // Estados mutáveis para cada atributo que pode ser editado
    var name by remember { mutableStateOf(character.name) }
    var strength by remember { mutableStateOf(character.strength.toString()) }
    var dexterity by remember { mutableStateOf(character.dexterity.toString()) }
    var constitution by remember { mutableStateOf(character.constitution.toString()) }
    var intelligence by remember { mutableStateOf(character.intelligence.toString()) }
    var wisdom by remember { mutableStateOf(character.wisdom.toString()) }
    var charisma by remember { mutableStateOf(character.charisma.toString()) }
    var experiencePoints by remember { mutableStateOf(character.experiencePoints.toString()) }
    var level by remember { mutableStateOf(character.level.toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Editar Personagem", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)

        // Campos de texto para cada atributo
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") }
        )
        TextField(
            value = strength,
            onValueChange = { strength = it },
            label = { Text("Força") }
        )
        TextField(
            value = dexterity,
            onValueChange = { dexterity = it },
            label = { Text("Destreza") }
        )
        TextField(
            value = constitution,
            onValueChange = { constitution = it },
            label = { Text("Constituição") }
        )
        TextField(
            value = intelligence,
            onValueChange = { intelligence = it },
            label = { Text("Inteligência") }
        )
        TextField(
            value = wisdom,
            onValueChange = { wisdom = it },
            label = { Text("Sabedoria") }
        )
        TextField(
            value = charisma,
            onValueChange = { charisma = it },
            label = { Text("Carisma") }
        )
        TextField(
            value = experiencePoints,
            onValueChange = { experiencePoints = it },
            label = { Text("Experiência") }
        )
        TextField(
            value = level,
            onValueChange = { level = it },
            label = { Text("Nível") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Valida e converte os valores de entrada
                val updatedCharacter = character.copy(
                    name = name,
                    strength = strength.toIntOrNull() ?: character.strength,
                    dexterity = dexterity.toIntOrNull() ?: character.dexterity,
                    constitution = constitution.toIntOrNull() ?: character.constitution,
                    intelligence = intelligence.toIntOrNull() ?: character.intelligence,
                    wisdom = wisdom.toIntOrNull() ?: character.wisdom,
                    charisma = charisma.toIntOrNull() ?: character.charisma,
                    experiencePoints = experiencePoints.toIntOrNull() ?: character.experiencePoints,
                    level = level.toIntOrNull() ?: character.level
                )
                onSave(updatedCharacter)
            }
        ) {
            Text("Salvar Alterações")
        }
    }
}
