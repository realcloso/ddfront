package up.ddm

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import strategies.iRace
import java.io.Serializable

@Entity(
    tableName = "character",
    foreignKeys = [ForeignKey(
        entity = Atributos::class,
        parentColumns = ["id"],
        childColumns = ["atributosId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class GameCharacter(
    @PrimaryKey(autoGenerate = true) var id: Int = 0, // Primary key with auto-increment
    var nome: String = "",
    var atributosId: Int = 0,
    var race: iRace // Race interface
) : Character(
    name = nome,
    race = race
), Serializable { // Implement Serializable

    // Initialize and apply race bonuses if needed
    init {
        validateAttributes() // Validate attributes on initialization
    }

    /**
     * Ensures that attributes are within the correct range.
     */
    private fun validateAttributes() {
        // Assuming the Character class has the following attributes
        require(strength in 8..15) { "Strength must be between 8 and 15" }
        require(dexterity in 8..15) { "Dexterity must be between 8 and 15" }
        require(constitution in 8..15) { "Constitution must be between 8 and 15" }
        require(intelligence in 8..15) { "Intelligence must be between 8 and 15" }
        require(wisdom in 8..15) { "Wisdom must be between 8 and 15" }
        require(charisma in 8..15) { "Charisma must be between 8 and 15" }
    }
}
