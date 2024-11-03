package up.ddm

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
        return gameCharacterDao.getCharacterById(id)
    }

    suspend fun getAllCharacters(): List<GameCharacterEntity> {
        return gameCharacterDao.getAllCharacters()
    }
}
