package up.ddm

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_characters")
data class GameCharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val race: String,
    val level: Int,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val experiencePoints: Int,
    val lifePoints: Int,
    val active: Boolean = true
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        name = parcel.readString() ?: "",
        race = parcel.readString() ?: "",
        level = parcel.readInt(),
        strength = parcel.readInt(),
        dexterity = parcel.readInt(),
        constitution = parcel.readInt(),
        intelligence = parcel.readInt(),
        wisdom = parcel.readInt(),
        charisma = parcel.readInt(),
        experiencePoints = parcel.readInt(),
        lifePoints = parcel.readInt(),
        active = parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(race)
        parcel.writeInt(level)
        parcel.writeInt(strength)
        parcel.writeInt(dexterity)
        parcel.writeInt(constitution)
        parcel.writeInt(intelligence)
        parcel.writeInt(wisdom)
        parcel.writeInt(charisma)
        parcel.writeInt(experiencePoints)
        parcel.writeInt(lifePoints)
        parcel.writeByte(if (active) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameCharacterEntity> {
        override fun createFromParcel(parcel: Parcel): GameCharacterEntity {
            return GameCharacterEntity(parcel)
        }

        override fun newArray(size: Int): Array<GameCharacterEntity?> {
            return arrayOfNulls(size)
        }
    }
}
