package up.ddm

import android.content.Context
import androidx.room.Room

object AppDatabase {
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
