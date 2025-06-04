package com.theoec.core

import android.content.Context
import com.google.firebase.initialize

object Firebase {
    fun initialize(context: Context) = com.google.firebase.Firebase.initialize(context)
}