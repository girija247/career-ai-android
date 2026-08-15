package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Editorial Aesthetic Core Palette
val EditorialPrimary = Color(0xFF6750A4)             // Signature plum / iris violet
val EditorialPrimaryDark = Color(0xFFD0BCFF)         // Light lilac for dark theme
val EditorialPrimaryContainer = Color(0xFFEADDFF)    // Soft lavender lilac pill highlight
val EditorialOnPrimaryContainer = Color(0xFF21005D)  // Deep midnight violet text

val EditorialSecondary = Color(0xFF21005D)           // Deep imperial violet
val EditorialSecondaryDark = Color(0xFFCCC2DC)
val EditorialSecondaryContainer = Color(0xFFFFD8E4) // Soft rose blush accent
val EditorialOnSecondaryContainer = Color(0xFF31111D)

val EditorialTertiary = Color(0xFF7D5260)            // Warm editorial berry
val EditorialTertiaryContainer = Color(0xFFFFD8E4)   // Soft blush
val EditorialOnTertiaryContainer = Color(0xFF31111D)

// Editorial Surface and Canvas Tones
val EditorialCanvasLight = Color(0xFFFCF8F9)         // Warm ivory / rose-tinted editorial canvas
val EditorialSurfaceLight = Color(0xFFFFFFFF)        // Crisp paper white
val EditorialSurfaceVariantLight = Color(0xFFF3EDF7) // Muted editorial lavender-gray container
val EditorialCardTonalLight = Color(0xFFE7E0EC)      // Subtle tonal card container
val EditorialTextPrimaryLight = Color(0xFF1D1B20)    // Deep editorial ink charcoal
val EditorialTextSecondaryLight = Color(0xFF49454F)  // Muted editorial charcoal
val EditorialBorderLight = Color(0xFFCAC4D0)         // Subtle editorial outline

// Dark Theme Surfaces
val EditorialCanvasDark = Color(0xFF1D1B20)
val EditorialSurfaceDark = Color(0xFF2B2830)
val EditorialSurfaceVariantDark = Color(0xFF36323B)
val EditorialCardTonalDark = Color(0xFF49454F)
val EditorialTextPrimaryDark = Color(0xFFE6E1E5)
val EditorialTextSecondaryDark = Color(0xFFCAC4D0)
val EditorialBorderDark = Color(0xFF49454F)

// Editorial Status & Accent Colors
val EmeraldSuccess = Color(0xFF2E6C4D)               // Editorial sage / forest green
val AmberWarning = Color(0xFF8D4F00)                 // Editorial warm amber
val RoseDanger = Color(0xFFB3261E)                   // Editorial crimson
val CyanAccent = Color(0xFF006877)                   // Editorial deep teal accent
val EditorialBlush = Color(0xFFFFD8E4)               // Soft rose blush

// Backward compatibility alias bindings to prevent breaking references
val RoyalBluePrimary = EditorialPrimary
val RoyalBlueDark = EditorialSecondary
val IndigoSecondary = EditorialPrimary
val PurpleHighlight = EditorialSecondary
val LightBg = EditorialCanvasLight
val LightSurface = EditorialSurfaceLight
val LightSurfaceVariant = EditorialSurfaceVariantLight
val LightBorder = EditorialBorderLight
val LightTextPrimary = EditorialTextPrimaryLight
val LightTextSecondary = EditorialTextSecondaryLight
val DarkBg = EditorialCanvasDark
val DarkSurface = EditorialSurfaceDark
val DarkSurfaceVariant = EditorialSurfaceVariantDark
val DarkBorder = EditorialBorderDark
val DarkTextPrimary = EditorialTextPrimaryDark
val DarkTextSecondary = EditorialTextSecondaryDark
