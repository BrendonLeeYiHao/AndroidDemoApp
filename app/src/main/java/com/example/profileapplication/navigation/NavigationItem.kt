package com.example.profileapplication.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.profileapplication.R

enum class NavigationItem(
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME(R.string.home, Icons.Filled.Home, Icons.Outlined.Home),
    LANGUAGE(R.string.language, Icons.Filled.Build, Icons.Outlined.Build),
    SETTINGS(R.string.settings, Icons.Filled.Settings, Icons.Outlined.Settings),
}