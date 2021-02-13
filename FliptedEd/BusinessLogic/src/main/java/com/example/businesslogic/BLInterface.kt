package com.example.businesslogic

interface BLInterface {
    suspend fun SaveString(str : String)
    suspend fun GetStrings() : List<String>
}