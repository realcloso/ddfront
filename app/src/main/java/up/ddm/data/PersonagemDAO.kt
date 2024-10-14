package up.ddm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import up.ddm.GameCharacter

@Dao
interface PersonagemDAO {

    @Insert
    suspend fun insert(gameCharacter: GameCharacter)

    @Query("SELECT * FROM character")
    suspend fun getAllPersonagens(): List<GameCharacter>

    @Delete
    suspend fun delete(gameCharacter: GameCharacter)

    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun getCharacterById(id: Int): GameCharacter?
}
