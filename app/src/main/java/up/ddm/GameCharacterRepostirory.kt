package up.ddm

import kotlinx.coroutines.flow.Flow


class GameCharacterRepository(private val gameCharacterDao: GameCharacterDao) {
    suspend fun insert(character: GameCharacterEntity) {
        gameCharacterDao.insertCharacter(character)
    }

    suspend fun update(character: GameCharacterEntity) {
        gameCharacterDao.updateCharacter(character)
    }

    suspend fun delete(character: GameCharacterEntity) {
        gameCharacterDao.deleteCharacter(character)
    }

    suspend fun getCharacterById(id: Int): GameCharacterEntity? {
        return gameCharacterDao.fetchCharacterById(id)
    }

    fun getAllCharacters(): Flow<List<GameCharacterEntity>> {
        return gameCharacterDao.fetchAllCharacters()
    }
}
