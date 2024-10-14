package up.ddm

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import strategies.*
import usecases.AttributeCosts

@Composable
fun AtributosScreen(character: GameCharacter) {
    var nome by remember { mutableStateOf(character.name) }
    var strength by remember { mutableStateOf(character.strength) }
    var dexterity by remember { mutableStateOf(character.dexterity) }
    var constitution by remember { mutableStateOf(character.constitution) }
    var intelligence by remember { mutableStateOf(character.intelligence) }
    var wisdom by remember { mutableStateOf(character.wisdom) }
    var charisma by remember { mutableStateOf(character.charisma) }
    var selectedRace by remember { mutableStateOf(character.race) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    var availablePoints by remember { mutableStateOf(27) }
    var isCharacterSaved by remember { mutableStateOf(false) }
    val context = LocalContext.current

    fun resetAttributes() {
        strength = 8
        dexterity = 8
        constitution = 8
        intelligence = 8
        wisdom = 8
        charisma = 8

        character.strength = strength
        character.dexterity = dexterity
        character.constitution = constitution
        character.intelligence = intelligence
        character.wisdom = wisdom
        character.charisma = charisma

        availablePoints = 27
    }

    LaunchedEffect(Unit) {
        resetAttributes()
    }


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
                    character.strength = strength
                }
                "dexterity" -> {
                    dexterity += 1
                    character.dexterity = dexterity
                }
                "constitution" -> {
                    constitution += 1
                    character.constitution = constitution
                }
                "intelligence" -> {
                    intelligence += 1
                    character.intelligence = intelligence
                }
                "wisdom" -> {
                    wisdom += 1
                    character.wisdom = wisdom
                }
                "charisma" -> {
                    charisma += 1
                    character.charisma = charisma
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
                    character.strength = strength
                }
                "dexterity" -> {
                    dexterity -= 1
                    character.dexterity = dexterity
                }
                "constitution" -> {
                    constitution -= 1
                    character.constitution = constitution
                }
                "intelligence" -> {
                    intelligence -= 1
                    character.intelligence = intelligence
                }
                "wisdom" -> {
                    wisdom -= 1
                    character.wisdom = wisdom
                }
                "charisma" -> {
                    charisma -= 1
                    character.charisma = charisma
                }
            }
        }
    }
    fun updateAttributesBasedOnRace() {
        strength = character.strength
        dexterity = character.dexterity
        constitution = character.constitution
        intelligence = character.intelligence
        wisdom = character.wisdom
        charisma = character.charisma

        selectedRace.applyRaceBonus(character)

        strength = character.strength
        dexterity = character.dexterity
        constitution = character.constitution
        intelligence = character.intelligence
        wisdom = character.wisdom
        charisma = character.charisma
    }

    fun saveCharacter() {
        character.name = nome
        character.strength = strength
        character.dexterity = dexterity
        character.constitution = constitution
        character.intelligence = intelligence
        character.wisdom = wisdom
        character.charisma = charisma
        isCharacterSaved = true
        snackbarMessage = "Personagem salvo com sucesso!"
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
                    character.name = it
                },
                label = { Text("Nome do Personagem") },
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isDropdownExpanded = !isDropdownExpanded }
            ) {
                Text("Raça: ${selectedRace.javaClass.simpleName ?: "Escolha uma raça"}") // Exibe a raça ou a opção padrão

                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    // Opção padrão não selecionável
                    DropdownMenuItem(
                        enabled = false, // Desabilita a seleção desta opção
                        onClick = { /* Nenhuma ação ao clicar */ },
                        text = { Text("Escolha uma raça") }
                    )

                    // Opções de raça
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = HumanRace()
                            character.race = selectedRace
                            isDropdownExpanded = false
                        },
                        text = { Text("Human") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = ElfRace()
                            character.race = selectedRace
                            isDropdownExpanded = false
                        },
                        text = { Text("Elf") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = OrcRace()
                            character.race = selectedRace
                            isDropdownExpanded = false
                        },
                        text = { Text("Orc") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = DwarfRace()
                            character.race = selectedRace
                            isDropdownExpanded = false
                        },
                        text = { Text("Dwarf") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = DragonbornRace()
                            character.race = selectedRace
                            isDropdownExpanded = false
                        },
                        text = { Text("Dragonborn") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = HalflingRace()
                            character.race = selectedRace
                            isDropdownExpanded = false
                        },
                        text = { Text("Halfling") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = TieflingRace()
                            character.race = selectedRace
                            isDropdownExpanded = false
                        },
                        text = { Text("Tiefling") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            resetAttributes()
                            selectedRace = GnomeRace()
                            character.race = selectedRace
                            isDropdownExpanded = false
                        },
                        text = { Text("Gnome") }
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                AtributoControl(
                    nomeAtributo = "Força",
                    valor = strength,
                    cost = calculateCost(strength + 1), // Custo para aumentar a Força
                    onIncrease = { increaseAttribute("strength") },
                    onDecrease = { decreaseAttribute("strength") }
                )
                AtributoControl(
                    nomeAtributo = "Destreza",
                    valor = dexterity,
                    cost = calculateCost(dexterity + 1), // Custo para aumentar a Destreza
                    onIncrease = { increaseAttribute("dexterity") },
                    onDecrease = { decreaseAttribute("dexterity") }
                )
                AtributoControl(
                    nomeAtributo = "Constituição",
                    valor = constitution,
                    cost = calculateCost(constitution + 1), // Custo para aumentar a Constituição
                    onIncrease = { increaseAttribute("constitution") },
                    onDecrease = { decreaseAttribute("constitution") }
                )
                AtributoControl(
                    nomeAtributo = "Inteligência",
                    valor = intelligence,
                    cost = calculateCost(intelligence + 1), // Custo para aumentar a Inteligência
                    onIncrease = { increaseAttribute("intelligence") },
                    onDecrease = { decreaseAttribute("intelligence") }
                )
                AtributoControl(
                    nomeAtributo = "Sabedoria",
                    valor = wisdom,
                    cost = calculateCost(wisdom + 1), // Custo para aumentar a Sabedoria
                    onIncrease = { increaseAttribute("wisdom") },
                    onDecrease = { decreaseAttribute("wisdom") }
                )
                AtributoControl(
                    nomeAtributo = "Carisma",
                    valor = charisma,
                    cost = calculateCost(charisma + 1), // Custo para aumentar o Carisma
                    onIncrease = { increaseAttribute("charisma") },
                    onDecrease = { decreaseAttribute("charisma") }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { saveCharacter()
                                   updateAttributesBasedOnRace() }) {
                    Text("Salvar Personagem")
                }
                Button(onClick = {
                    // Iniciar nova activity para mostrar personagem
                    val intent = Intent(context, ShowCharacterActivity::class.java).apply {
                        putExtra("CHARACTER_NAME", character.name)
                        putExtra("CHARACTER_STRENGTH", character.strength)
                        putExtra("CHARACTER_DEXTERITY", character.dexterity)
                        putExtra("CHARACTER_CONSTITUTION", character.constitution)
                        putExtra("CHARACTER_INTELLIGENCE", character.intelligence)
                        putExtra("CHARACTER_WISDOM", character.wisdom)
                        putExtra("CHARACTER_CHARISMA", character.charisma)
                        putExtra("CHARACTER_RACE", selectedRace.javaClass.simpleName)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Mostrar Personagem")
                }
            }
        }
    }
}

@Composable
fun AtributoControl(nomeAtributo: String, valor: Int, cost: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(nomeAtributo, modifier = Modifier.weight(1f).padding(end = 16.dp))
        Text("Custo: $cost", modifier = Modifier.padding(end = 16.dp)) // Exibe o custo
        Button(onClick = onDecrease, modifier = Modifier.width(50.dp)) {
            Text("-")
        }
        Text(valor.toString(), modifier = Modifier.width(50.dp), textAlign = TextAlign.Center)
        Button(onClick = onIncrease, modifier = Modifier.width(50.dp)) {
            Text("+")
        }
    }
}
