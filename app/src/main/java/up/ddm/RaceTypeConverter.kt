package up.ddm.data

import androidx.room.TypeConverter
import strategies.iRace
import strategies.*

class RaceTypeConverter {

    @TypeConverter
    fun fromRace(race: iRace?): String? {
        return race?.javaClass?.simpleName // Armazena o nome da classe como string
    }

    @TypeConverter
    fun toRace(raceName: String?): iRace? {
        return when (raceName) {
            "Human" -> HumanRace()
            "Elf" -> ElfRace()
            "Orc" -> OrcRace()
            "Dwarf" -> DwarfRace()
            "Dragonborn" -> DragonbornRace()
            "Halfling" -> HalflingRace()
            "Tiefling" -> TieflingRace()
            "Gnome" -> GnomeRace()
            else -> null
        }
    }
}
