package com.theoec.kmpfirebasics

import cocoapods.FirebaseAuth.FIRAuth
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
actual class FirebaseAuth() {
    val auth: FIRAuth
        get() = FIRAuth.auth()

    @OptIn(ExperimentalForeignApi::class)
    actual val currentUser: FirebaseUser?
        get() = if (auth.currentUser() != null)
            FirebaseUser(auth.currentUser()!!)
        else
            null

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Result<FirebaseUser> = suspendCoroutine { cont ->
        FIRAuth.auth().createUserWithEmail(
            email = email,
            password = password,
            completion = { user, error ->
                if (error != null)
                    cont.resume(Result.failure(Exception(error.localizedDescription)))
                else if (user != null)
                    cont.resume(Result.success(user.user().bind()))
                else
                    cont.resume(Result.failure(Exception("Unknown error")))
            }
        )
    }
}
