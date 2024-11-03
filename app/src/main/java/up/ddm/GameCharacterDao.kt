package up.ddm

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: GameCharacterEntity): Long

    @Update
    suspend fun updateCharacter(character: GameCharacterEntity)

    @Delete
    suspend fun deleteCharacter(character: GameCharacterEntity)

    @Query("SELECT * FROM game_characters WHERE id = :id")
    suspend fun fetchCharacterById(id: Int): GameCharacterEntity?

    @Query("SELECT * FROM game_characters")
    fun fetchAllCharacters(): Flow<List<GameCharacterEntity>>

    @Query("SELECT COUNT(*) FROM game_characters")
    suspend fun countCharacters(): Int
}
