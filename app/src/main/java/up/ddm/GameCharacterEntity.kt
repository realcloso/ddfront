package up.ddm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_characters")
data class GameCharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val experiencePoints: Int,
    val level: Int,
    val lifePoints: Int,
    val race: String,
    val active: Boolean
)
