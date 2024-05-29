package com.example.profileapplication.app.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.profileapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainHostActivity : FragmentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}