package up.ddm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import up.ddm.Atributos
import up.ddm.GameCharacter

@Database(entities = [Atributos::class, GameCharacter::class], version = 2, exportSchema = false)
@TypeConverters(RaceTypeConverter::class)
abstract class AtributosDB : RoomDatabase() {

    abstract fun atributosDAO(): AtributosDAO
    abstract fun personagemDAO(): PersonagemDAO

    companion object {
        @Volatile
        private var INSTANCE: AtributosDB? = null

        fun getDatabase(context: Context): AtributosDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AtributosDB::class.java,
                    "atributosDB"
                )
                    .fallbackToDestructiveMigration() // Consider removing this in production
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
