package edu.calpoly.flipted.backend

interface AuthProvider {
    suspend fun getToken(): String
}