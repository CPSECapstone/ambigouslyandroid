package com.example.flipteded.businesslogic.old

interface DataRepo {
    suspend fun getAllEntries() : List<DataEntry>
    suspend fun saveEntry(entry : DataEntry)
}