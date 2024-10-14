package up.ddm

import androidx.room.Entity
import androidx.room.PrimaryKey
import usecases.AttributeCosts

@Entity(tableName = "atributos")
class Atributos {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var pontos = 27 // Keep this public so Room can set it

    /**
     * Set an attribute value, updating available points accordingly.
     *
     * @param atributo The value of the attribute being set.
     * @throws IllegalArgumentException if the attribute value is out of bounds or insufficient points are available.
     */
    fun setAtributo(atributo: Int) {
        require(atributo in 8..15) { "Valor deve estar entre 8 e 15" }

        val cost = AttributeCosts.costs[atributo] ?: throw IllegalArgumentException("Custo do atributo não encontrado")

        if (cost > pontos) {
            throw IllegalArgumentException("Pontos insuficientes")
        }

        // Deduct points only if the attribute is being increased
        if (atributo != 8) {
            pontos -= cost
        }
    }

    /**
     * Get the number of available points.
     *
     * @return The number of available points.
     */
    fun getPontosDisponiveis(): Int {
        return pontos
    }
}
