package com.theoec.kmpfirebasics

actual class FirebaseUser(
    private val original: com.google.firebase.auth.FirebaseUser
) {
    actual val uid
        get() = original.uid
    actual val email
        get() = original.email
}


fun com.google.firebase.auth.FirebaseUser.bind() = FirebaseUser(this)
