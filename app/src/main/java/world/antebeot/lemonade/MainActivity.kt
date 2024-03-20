package world.antebeot.lemonade

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import world.antebeot.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LemonadeImageAntText()
                    }
                }
            }
        }
    }
}

enum class lemonState(val value: Int)
{
    LEMON_TREE(1), LEMON_SQUEEZE(2),  LEMON_DRINK(3), LEMON_RESTART(4);
    val max_value = 4
    val min_value = 1
    val limon_squeze = 2

    fun getNextState(): lemonState
    {
        if (this.value + 1 > max_value) return return lemonState.LEMON_TREE
        if (this.value == limon_squeze) // TODO: that not ok
        {
            val rand = (1..10).random()
            Log.d("LEMON_STATE", rand.toString())
            if (rand < 8) return this
        }
        return when(this.value)
        {
            1 -> lemonState.LEMON_SQUEEZE
            2 -> lemonState.LEMON_DRINK
            3 -> lemonState.LEMON_RESTART
            else -> lemonState.LEMON_TREE
        }
    }
    @Composable
    fun CurrentLemonImage(modifier: Modifier = Modifier)  {

        val imgPainter = when(this.value)
        {
            1 -> painterResource(id = R.drawable.lemon_tree)
            2 -> painterResource(id = R.drawable.lemon_squeeze)
            3 -> painterResource(id = R.drawable.lemon_drink)
            else -> painterResource(id = R.drawable.lemon_restart)
        }
        Image(imgPainter, "lemon_state " + this.value.toString(), modifier=modifier )
    }
    @Composable
    fun CurrentLemonText(modifier: Modifier = Modifier)
    {
        val text = when(this.value)
        {
            1 -> "Нажми на лемон"
            2 -> "Тыкай на лемон пока не вытечет"
            3 -> "Нажми на лимонад выпить"
            else -> "Стакан пуст"
        }
        Text(text, modifier=modifier)
    }
    fun isAllowStateOfLemon(state: Int) = state in min_value..max_value
}
@Composable
fun LemonadeImageAntText(modifier: Modifier = Modifier) {
    var lemonadeState by remember { mutableStateOf (lemonState.LEMON_TREE) }

    Column {
        lemonadeState.CurrentLemonImage()
        lemonadeState.CurrentLemonText()
        Button(modifier = Modifier,
            onClick = {
                val lemonStateNext = lemonadeState.getNextState()
                lemonadeState = lemonStateNext
            }) {
            Text("Нажать на лимон", modifier = Modifier)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LemonadeImageAntTextPreview() {
    LemonadeTheme {
        LemonadeImageAntText()
    }
}