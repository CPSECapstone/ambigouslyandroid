package com.example.flipteded.businesslogic

interface DataRepo {
    suspend fun getAllEntries() : List<DataEntry>
    suspend fun saveEntry(entry : DataEntry)
}