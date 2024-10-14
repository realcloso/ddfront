package up.ddm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import up.ddm.ui.HomeScreen

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeScreen(
                onCreateCharacter = { newCharacter ->
                    println("Personagem criado: ${newCharacter.name}!")
                }
            )
        }
    }
}
