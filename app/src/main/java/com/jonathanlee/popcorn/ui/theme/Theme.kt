package com.jonathanlee.popcorn.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val darkColorPalette = darkColorScheme(
    primary = BrandPrimary,
    secondary = BrandSecondary,
    tertiary = BrandPrimary,
    background = BrandSurface,
    onBackground = BrandOnSurface,
    onPrimary = BrandOnPrimary,
    onSecondary = BrandOnSecondary,
    primaryContainer = BrandContainer,
    onPrimaryContainer = BrandOnContainer,
    surface = BrandSurface,
    onSurface = BrandOnSurface,
    surfaceVariant = BrandSurface,
    onSurfaceVariant = BrandOnSurface
)

@Composable
fun PopcornTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = darkColorPalette,
        shapes = Shapes,
        content = content
    )
}