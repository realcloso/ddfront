package up.ddm

import Character
import strategies.iRace

class GameCharacter(
    name: String = "",
    strength: Int = 8,
    dexterity: Int = 8,
    constitution: Int = 8,
    intelligence: Int = 8,
    wisdom: Int = 8,
    charisma: Int = 8,
    experiencePoints: Int = 0,
    level: Int = 1,
    lifePoints: Int = 10,
    race: iRace,
    active: Boolean = true
) : Character(
    name, strength, dexterity, constitution, intelligence, wisdom, charisma, experiencePoints, level, lifePoints, race, active
) {
    init {
        // Aplica o bônus da raça ao criar o personagem
        race.applyRaceBonus(this)
    }
}

// Função de extensão para converter GameCharacter em GameCharacterEntity
fun GameCharacter.toEntity(): GameCharacterEntity {
    return GameCharacterEntity(
        name = this.name,
        strength = this.strength,
        dexterity = this.dexterity,
        constitution = this.constitution,
        intelligence = this.intelligence,
        wisdom = this.wisdom,
        charisma = this.charisma,
        experiencePoints = this.experiencePoints,
        level = this.level,
        lifePoints = this.lifePoints,
        race = RaceNameConverter.toRaceName(this.race), // Convertendo a raça para string
        active = this.active
    )
}
