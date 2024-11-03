package up.ddm

import androidx.room.*

@Dao
interface GameCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: GameCharacterEntity): Long

    @Update
    suspend fun updateCharacter(character: GameCharacterEntity)

    @Delete
    suspend fun deleteCharacter(character: GameCharacterEntity)

    @Query("SELECT * FROM game_characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): GameCharacterEntity?

    @Query("SELECT * FROM game_characters")
    suspend fun getAllCharacters(): List<GameCharacterEntity>
}
