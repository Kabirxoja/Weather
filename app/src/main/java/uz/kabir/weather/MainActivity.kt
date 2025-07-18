package uz.kabir.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import uz.kabir.weather.presentation.navigation.AppNavGraph
import uz.kabir.weather.presentation.theme.WeatherTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                AppNavGraph()
            }
        }
    }
}