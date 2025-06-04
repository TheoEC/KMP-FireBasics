package com.theoec.kmpfirebasics

expect class FirebaseAuth {
    val currentUser: FirebaseUser?

    suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<FirebaseUser>
}