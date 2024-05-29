package com.example.profileapplication.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.profileapplication.R

enum class BottomNavigationItem (
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val navigateToKey: NavigateToKey.App
) {
    PROFILE(R.string.profile, Icons.Filled.Person, Icons.Outlined.Person, NavigateToKey.App.ProfileFragment),
    LIST(R.string.list, Icons.Filled.List, Icons.Outlined.List, NavigateToKey.App.EmailFragment),
    FEEDBACK(R.string.feedback, Icons.Filled.Face, Icons.Outlined.Face, NavigateToKey.App.FeedbackFragment)
}