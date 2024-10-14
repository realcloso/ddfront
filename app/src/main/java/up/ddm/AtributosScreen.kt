package up.ddm.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import strategies.*
import up.ddm.Atributos
import up.ddm.GameCharacter
import up.ddm.data.PersonagemDAO
import usecases.AttributeCosts

@Composable
fun AtributosScreen(
    atributos: Atributos,
    gameCharacter: GameCharacter,
    onSaveAtributos: () -> Unit,
    personagemDAO: PersonagemDAO, // Injecting DAO
    onDeletePersonagem: (GameCharacter) -> Unit,
    onSalvarPersonagem: () -> Unit
) {
    var nome by remember { mutableStateOf(gameCharacter.nome) }
    var strength by remember { mutableStateOf(gameCharacter.strength) }
    var dexterity by remember { mutableStateOf(gameCharacter.dexterity) }
    var constitution by remember { mutableStateOf(gameCharacter.constitution) }
    var intelligence by remember { mutableStateOf(gameCharacter.intelligence) }
    var wisdom by remember { mutableStateOf(gameCharacter.wisdom) }
    var charisma by remember { mutableStateOf(gameCharacter.charisma) }
    var selectedRace by remember { mutableStateOf(gameCharacter.race) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    var availablePoints by remember { mutableStateOf(27) }
    val coroutineScope = rememberCoroutineScope()

    fun calculateCost(value: Int): Int {
        return AttributeCosts.costs[value] ?: 0
    }

    fun increaseAttribute(attribute: String) {
        val currentValue = when (attribute) {
            "strength" -> strength
            "dexterity" -> dexterity
            "constitution" -> constitution
            "intelligence" -> intelligence
            "wisdom" -> wisdom
            "charisma" -> charisma
            else -> 0
        }

        val cost = calculateCost(currentValue + 1)
        if (availablePoints >= cost) {
            availablePoints -= cost
            when (attribute) {
                "strength" -> {
                    strength += 1
                    gameCharacter.strength = strength
                }
                "dexterity" -> {
                    dexterity += 1
                    gameCharacter.dexterity = dexterity
                }
                "constitution" -> {
                    constitution += 1
                    gameCharacter.constitution = constitution
                }
                "intelligence" -> {
                    intelligence += 1
                    gameCharacter.intelligence = intelligence
                }
                "wisdom" -> {
                    wisdom += 1
                    gameCharacter.wisdom = wisdom
                }
                "charisma" -> {
                    charisma += 1
                    gameCharacter.charisma = charisma
                }
            }
        } else {
            snackbarMessage = "Pontos insuficientes!"
        }
    }

    fun decreaseAttribute(attribute: String) {
        val currentValue = when (attribute) {
            "strength" -> strength
            "dexterity" -> dexterity
            "constitution" -> constitution
            "intelligence" -> intelligence
            "wisdom" -> wisdom
            "charisma" -> charisma
            else -> 0
        }

        if (currentValue > 8) {
            val cost = calculateCost(currentValue)
            availablePoints += cost
            when (attribute) {
                "strength" -> {
                    strength -= 1
                    gameCharacter.strength = strength
                }
                "dexterity" -> {
                    dexterity -= 1
                    gameCharacter.dexterity = dexterity
                }
                "constitution" -> {
                    constitution -= 1
                    gameCharacter.constitution = constitution
                }
                "intelligence" -> {
                    intelligence -= 1
                    gameCharacter.intelligence = intelligence
                }
                "wisdom" -> {
                    wisdom -= 1
                    gameCharacter.wisdom = wisdom
                }
                "charisma" -> {
                    charisma -= 1
                    gameCharacter.charisma = charisma
                }
            }
        }
    }

    fun updateAttributesBasedOnRace() {
        // Reseta os atributos para o valor base de 8
        strength = 8
        dexterity = 8
        constitution = 8
        intelligence = 8
        wisdom = 8
        charisma = 8

        // Aplica os bônus da raça ao gameCharacter
        selectedRace.applyRaceBonus(gameCharacter)

        // Atualiza os valores de atributos na tela com base no gameCharacter
        strength = gameCharacter.strength
        dexterity = gameCharacter.dexterity
        constitution = gameCharacter.constitution
        intelligence = gameCharacter.intelligence
        wisdom = gameCharacter.wisdom
        charisma = gameCharacter.charisma
    }

    fun resetAttributes() {
        // Reseta todos os atributos para o valor base de 8
        strength = 8
        dexterity = 8
        constitution = 8
        intelligence = 8
        wisdom = 8
        charisma = 8

        // Atualiza o objeto gameCharacter com os valores resetados
        gameCharacter.strength = strength
        gameCharacter.dexterity = dexterity
        gameCharacter.constitution = constitution
        gameCharacter.intelligence = intelligence
        gameCharacter.wisdom = wisdom
        gameCharacter.charisma = charisma

        // Reseta os pontos disponíveis para 27
        availablePoints = 27
    }

    Scaffold(
        snackbarHost = {
            if (snackbarMessage.isNotEmpty()) {
                Snackbar {
                    Text(snackbarMessage)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Pontos disponíveis: $availablePoints", modifier = Modifier.padding(8.dp))

            TextField(
                value = nome,
                onValueChange = {
                    nome = it
                    gameCharacter.nome = it
                },
                label = { Text("Nome do Personagem") },
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isDropdownExpanded = !isDropdownExpanded }
            ) {
                Text("Raça: ${selectedRace.javaClass.simpleName}")
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = HumanRace()
                            gameCharacter.race = selectedRace
                            updateAttributesBasedOnRace()
                            isDropdownExpanded = false
                        },
                        text = { Text("Human") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = ElfRace()
                            gameCharacter.race = selectedRace
                            updateAttributesBasedOnRace()
                            isDropdownExpanded = false
                        },
                        text = { Text("Elf") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = OrcRace()
                            gameCharacter.race = selectedRace
                            updateAttributesBasedOnRace()
                            isDropdownExpanded = false
                        },
                        text = { Text("Orc") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = DwarfRace()
                            gameCharacter.race = selectedRace
                            updateAttributesBasedOnRace()
                            isDropdownExpanded = false
                        },
                        text = { Text("Dwarf") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = DragonbornRace()
                            gameCharacter.race = selectedRace
                            updateAttributesBasedOnRace()
                            isDropdownExpanded = false
                        },
                        text = { Text("Dragonborn") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = HalflingRace()
                            gameCharacter.race = selectedRace
                            updateAttributesBasedOnRace()
                            isDropdownExpanded = false
                        },
                        text = { Text("Halfling") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = TieflingRace()
                            gameCharacter.race = selectedRace
                            updateAttributesBasedOnRace()
                            isDropdownExpanded = false
                        },
                        text = { Text("Tiefling") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = GnomeRace()
                            gameCharacter.race = selectedRace
                            updateAttributesBasedOnRace()
                            isDropdownExpanded = false
                        },
                        text = { Text("Gnome") }
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // Alinhamento horizontal aplicado diretamente na Column
                modifier = Modifier.fillMaxWidth()
            ) {
                AtributoControl(
                    nomeAtributo = "Força",
                    valor = strength,
                    onIncrease = { increaseAttribute("strength") },
                    onDecrease = { decreaseAttribute("strength") }
                )
                AtributoControl(
                    nomeAtributo = "Destreza",
                    valor = dexterity,
                    onIncrease = { increaseAttribute("dexterity") },
                    onDecrease = { decreaseAttribute("dexterity") }
                )
                AtributoControl(
                    nomeAtributo = "Constituição",
                    valor = constitution,
                    onIncrease = { increaseAttribute("constitution") },
                    onDecrease = { decreaseAttribute("constitution") }
                )
                AtributoControl(
                    nomeAtributo = "Inteligência",
                    valor = intelligence,
                    onIncrease = { increaseAttribute("intelligence") },
                    onDecrease = { decreaseAttribute("intelligence") }
                )
                AtributoControl(
                    nomeAtributo = "Sabedoria",
                    valor = wisdom,
                    onIncrease = { increaseAttribute("wisdom") },
                    onDecrease = { decreaseAttribute("wisdom") }
                )
                AtributoControl(
                    nomeAtributo = "Carisma",
                    valor = charisma,
                    onIncrease = { increaseAttribute("charisma") },
                    onDecrease = { decreaseAttribute("charisma") }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = {
                    coroutineScope.launch {
                        personagemDAO.insert(gameCharacter)
                        onSalvarPersonagem()
                    }
                }) {
                    Text("Salvar Personagem")
                }
                Button(onClick = { onDeletePersonagem(gameCharacter) }) {
                    Text("Deletar Personagem")
                }
            }
        }
    }
}

@Composable
fun AtributoControl(
    nomeAtributo: String,
    valor: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$nomeAtributo: $valor",
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Button(onClick = onDecrease) {
            Text("-")
        }
        Button(onClick = onIncrease) {
            Text("+")
        }
    }
}
