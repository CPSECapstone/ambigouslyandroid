package com.example.flipteded.businesslogic.old

class GetAllEntriesUseCase(private val repo : DataRepo) {
    suspend fun invoke() : List<DataEntry> {
        return repo.getAllEntries()
    }
}