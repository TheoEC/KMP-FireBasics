package com.theoec.kmpfirebasics

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class FirebaseAuth(
    private val original: FirebaseAuth = Firebase.auth
) {
    actual val currentUser
        get() = if (original.currentUser != null)
            FirebaseUser(Firebase.auth.currentUser!!)
        else
            null

    actual suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Result<FirebaseUser> = suspendCoroutine { cont ->
        original.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    if (user != null)
                        cont.resume(Result.success(user.bind()))
                    else
                        cont.resume(Result.failure(Exception("User is null")))
                } else {
                    cont.resume(Result.failure(task.exception ?: Exception("Unknown error")))
                }
            }
    }
}