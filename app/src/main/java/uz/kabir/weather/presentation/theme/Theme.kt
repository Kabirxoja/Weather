package uz.kabir.weather.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,               // buttons, icons
    secondary = IndicatorBlue,           // weather/air indicators
    tertiary = DividerGray,              // card/section dividers
    background = LightBackground,        // whole app background
    surface = SurfaceLight,              // white cards
    onPrimary = Color.White,             // text on blue buttons
    onSecondary = TextPrimary,           // icon labels, metric text
    onTertiary = TextSecondary,          // subtitles or tags
    onBackground = TextPrimary,          // main text
    onSurface = TextPrimary              // card text
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = IndicatorBlue,
    tertiary = DividerGray,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.White,
    onSecondary = Color.LightGray,
    onTertiary = Color.Gray,
    onBackground = Color.White,
    onSurface = Color.White
)



@Composable
fun WeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
