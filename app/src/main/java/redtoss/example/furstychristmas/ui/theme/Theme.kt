package redtoss.example.furstychristmas.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = FurstyBlack,
    onPrimary = FurstyWhite,
    secondary = FurstySecondaryDark,
    onSecondary = FurstyWhite,
)

private val LightColorPalette = lightColors(
    primary = FurstyWhite,
    onPrimary = FurstyBlack,
    secondary = FurstySecondary,
    onSecondary = FurstyBlack
    /*
transparent">#00ffffff</color>
<color name="colorText">#dbd8e3</color>
<color name="colorTextLight">#dbd8e3</color>
<color name="background">#2a2438</color>

<color name="colorCompleted">#42b49b</color>
<color name="colorNotCompleted">#f14e3f</color>
<color name="colorLocked">@color/transparent</color>
*/


/* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

@Composable
fun FurstyChrismasTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}