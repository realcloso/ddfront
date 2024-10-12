package up.ddm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "atributos")
class Atributos {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var pontos = 27

    fun setAtributo(atributo: Int) {
        if(atributo.toInt() < 8 || atributo.toInt() > 15)
            throw IllegalArgumentException("Valor deve estar entre 8 e 15")
        else if (atributo > pontos && atributo !=8)
            throw  IllegalArgumentException("Pontos insuficientes")
        else if(atributo != 8)
            // Não utilizei a contagem correta de custo de pontos, lembre que a partir de 13 o custo é 2
            // esta lógica não está correta, é só um exemplo, adapte com o teu código
            pontos -= atributo-8
    }

    fun getPontosDisponiveis(): Int {
        return pontos
    }
}
