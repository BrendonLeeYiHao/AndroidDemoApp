package com.example.profileapplication.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.NavController
import com.example.profileapplication.R
import com.example.profileapplication.app.main.MainHostActivity
import com.example.profileapplication.app.main.account.view.AccountActivity
import com.example.profileapplication.app.main.login.view.LoginActivity

object Navigator {

    fun performNavigation(context: Context?, navController: NavController?, destination: NavigateToKey.App) {

        when (destination) {
            is NavigateToKey.App.ProfileFragment -> {
                navController?.navigate(R.id.profile_fragment)
            }
            // Add more destinations as needed
            is NavigateToKey.App.ContactFragment -> {

            }
            is NavigateToKey.App.EmailFragment -> {
                navController?.navigate(R.id.emailFragment)
            }
            is NavigateToKey.App.FeedbackFragment -> {
                navController?.navigate(R.id.feedback_fragment)
            }
            is NavigateToKey.App.MainHostActivity -> {
                val intent = Intent(context, MainHostActivity::class.java)
                context?.startActivity(intent)
            }
            is NavigateToKey.App.AccountActivity -> {
                val intent = Intent(context, AccountActivity::class.java)
                context?.startActivity(intent)
            }
            is NavigateToKey.App.LoginActivity -> {
//                navController?.let {
//                    it.popBackStack(it.graph.startDestinationId, true)
//                }
                val intent = Intent(context, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                val options = ActivityOptionsCompat.makeCustomAnimation(context!!, androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
                context.startActivity(intent, options.toBundle())
//                context?.startActivity(intent)
                (context as? Activity)?.finish()
            }
        }
    }
}