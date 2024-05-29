package com.example.profileapplication.navigation

sealed class NavigateToKey {

    sealed class App: NavigateToKey() {

        data object ProfileFragment: App()
        data object EmailFragment: App()
        data object ContactFragment: App()
        data object FeedbackFragment: App()
        data object MainHostActivity: App()
        data object LoginActivity: App()
        data object AccountActivity: App()
    }
}