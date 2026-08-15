package com.example.ui.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = EditorialPrimaryDark,
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = EditorialPrimaryContainer,
    secondary = EditorialSecondaryDark,
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFFEFB8C8),
    onTertiary = Color(0xFF492532),
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Color(0xFFFFD8E4),
    background = EditorialCanvasDark,
    onBackground = EditorialTextPrimaryDark,
    surface = EditorialSurfaceDark,
    onSurface = EditorialTextPrimaryDark,
    surfaceVariant = EditorialSurfaceVariantDark,
    onSurfaceVariant = EditorialTextSecondaryDark,
    outline = EditorialBorderDark,
    outlineVariant = Color(0xFF49454F),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410)
)

private val LightColorScheme = lightColorScheme(
    primary = EditorialPrimary,
    onPrimary = Color.White,
    primaryContainer = EditorialPrimaryContainer,
    onPrimaryContainer = EditorialOnPrimaryContainer,
    secondary = EditorialSecondary,
    onSecondary = Color.White,
    secondaryContainer = EditorialSecondaryContainer,
    onSecondaryContainer = EditorialOnSecondaryContainer,
    tertiary = EditorialTertiary,
    onTertiary = Color.White,
    tertiaryContainer = EditorialTertiaryContainer,
    onTertiaryContainer = EditorialOnTertiaryContainer,
    background = EditorialCanvasLight,
    onBackground = EditorialTextPrimaryLight,
    surface = EditorialSurfaceLight,
    onSurface = EditorialTextPrimaryLight,
    surfaceVariant = EditorialSurfaceVariantLight,
    onSurfaceVariant = EditorialTextSecondaryLight,
    outline = EditorialBorderLight,
    outlineVariant = Color(0xFFE7E0EC),
    error = RoseDanger,
    onError = Color.White
)

@Composable
fun CareerAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent Editorial Aesthetic
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
