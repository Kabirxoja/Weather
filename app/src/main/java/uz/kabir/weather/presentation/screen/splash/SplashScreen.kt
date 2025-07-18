package uz.kabir.weather.presentation.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(onNavigateToMain: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "SPLASH SCREEN",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary
        )
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            //FETCH DATA
        }

        delay(2000)

        withContext(Dispatchers.Main) {
            onNavigateToMain()
        }
    }


}

