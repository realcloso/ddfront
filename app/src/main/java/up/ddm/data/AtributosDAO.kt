package up.ddm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import up.ddm.Atributos

@Dao
interface AtributosDAO {

    @Insert
    suspend fun insert(atributos: Atributos)

    @Query("SELECT * FROM atributos")
    suspend fun getAllAtributos(): List<Atributos>

    @Delete
    suspend fun delete(atributos: Atributos)

    @Query("SELECT * FROM atributos WHERE id = :id")
    suspend fun getAtributosById(id: Int): Atributos?
}
