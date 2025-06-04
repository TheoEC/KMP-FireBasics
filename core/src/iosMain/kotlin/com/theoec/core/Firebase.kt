package com.theoec.core

import cocoapods.FirebaseCore.FIRApp
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
object Firebase {
    fun initialize() {
        FIRApp.initialize()
    }
}