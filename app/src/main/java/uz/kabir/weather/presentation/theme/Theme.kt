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
    primary = PrimaryBlue,
    secondary = IndicatorBlue,
    tertiary = DividerGray,
    background = LightBackground,
    surface = SurfaceLight,
    onPrimary = TextPrimary,     // text on buttons
    onSecondary = TextSecondary, // text on chips/indicators
    onTertiary = TextSecondary,
    onBackground = TextPrimary,  // general text color
    onSurface = TextPrimary      // card or box text
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,            // soft blue for accents
    secondary = IndicatorBlue,        // lighter blue (for icons/indicators)
    tertiary = DividerGray,           // subtle dividers if needed
    background = DarkBackground,      // general app background
    surface = DarkSurface,            // cards and content containers
    onPrimary = Color.White,          // text on buttons
    onSecondary = Color.LightGray,    // secondary text
    onTertiary = Color.LightGray,
    onBackground = Color.White,       // main text color
    onSurface = Color.White           // text inside cards
)



@Composable
fun WeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}