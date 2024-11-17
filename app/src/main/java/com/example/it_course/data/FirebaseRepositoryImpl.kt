package com.example.it_course.data

import com.example.it_course.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth

object FirebaseRepositoryImpl: FirebaseRepository {
    override fun authorization(auth: FirebaseAuth, mail: String, password: String): Boolean {
        var result: Boolean = false
        auth.signInWithEmailAndPassword(mail, password)
            .addOnSuccessListener {
                result = true
            }
            .addOnFailureListener {
                result = false
            }
        return result
    }

    override fun registration(auth: FirebaseAuth, mail: String, password: String): Boolean {
        var result: Boolean = false
        auth.createUserWithEmailAndPassword(mail, password)
            .addOnSuccessListener {
                result = true
            }
            .addOnFailureListener {
                result = false
            }
    return result
    }
}