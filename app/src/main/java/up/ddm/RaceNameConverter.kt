package up.ddm

import strategies.*

object RaceNameConverter {

    fun toRaceName(race: iRace): String {
        return when (race) {
            is HumanRace -> "Human"
            is ElfRace -> "Elf"
            is OrcRace -> "Orc"
            is DwarfRace -> "Dwarf"
            is DragonbornRace -> "Dragonborn"
            is HalflingRace -> "Halfling"
            is TieflingRace -> "Tiefling"
            is GnomeRace -> "Gnome"
            else -> "Unknown"
        }
    }
}
