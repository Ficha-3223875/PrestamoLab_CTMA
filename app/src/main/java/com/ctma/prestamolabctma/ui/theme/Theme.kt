package com.ctma.prestamolabctma.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// =========================================================
// COLORES ADICIONALES
// =========================================================

private val ColorSuperficieVariant = Color(
    0xFFE8EEF5
)

private val ColorSuperficieOscuraVariant = Color(
    0xFF3E4A56
)

private val ColorFondoDark = Color(
    0xFF00315C
)

// =========================================================
// TEMA CLARO
// =========================================================

private val LightColorScheme = lightColorScheme(

    primary = AzulPrincipal,
    onPrimary = SuperficieClaro,

    primaryContainer = AzulClaro,
    onPrimaryContainer = AzulOscuro,

    secondary = VerdePrincipal,
    onSecondary = SuperficieClaro,

    secondaryContainer = VerdeClaro,
    onSecondaryContainer = TextoPrincipal,

    background = FondoClaro,
    onBackground = TextoPrincipal,

    surface = SuperficieClaro,
    onSurface = TextoPrincipal,

    surfaceVariant = ColorSuperficieVariant,
    onSurfaceVariant = TextoSecundario,

    error = RojoError,
    onError = SuperficieClaro
)

// =========================================================
// TEMA OSCURO
// =========================================================

private val DarkColorScheme = darkColorScheme(

    primary = AzulOscuroDark,
    onPrimary = ColorFondoDark,

    primaryContainer = AzulOscuro,
    onPrimaryContainer = TextoPrincipalDark,

    secondary = AzulSecundarioDark,
    onSecondary = ColorFondoDark,

    background = FondoOscuro,
    onBackground = TextoPrincipalDark,

    surface = SuperficieOscura,
    onSurface = TextoPrincipalDark,

    surfaceVariant = ColorSuperficieOscuraVariant,
    onSurfaceVariant = TextoSecundarioDark,

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

// =========================================================
// TEMA PRINCIPAL
// =========================================================

@Composable
fun PrestamoLabCTMATheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (darkTheme) {
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