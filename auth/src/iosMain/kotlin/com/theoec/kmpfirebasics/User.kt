package com.theoec.kmpfirebasics

import cocoapods.FirebaseAuth.FIRUser
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual class FirebaseUser(
    private val user: FIRUser
) {
    actual val uid
        get() = user.uid()
    actual val email
        get() = user.email()
}

@OptIn(ExperimentalForeignApi::class)
fun FIRUser.bind() = FirebaseUser(this)