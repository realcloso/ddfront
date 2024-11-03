package up.ddm

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [GameCharacterEntity::class], version = 1, exportSchema = false)
abstract class GameCharacterDatabase : RoomDatabase() {
    abstract fun gameCharacterDao(): GameCharacterDao

    companion object {
        @Volatile
        private var INSTANCE: GameCharacterDatabase? = null

        fun getDatabase(context: Context): GameCharacterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameCharacterDatabase::class.java,
                    "game_character_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
