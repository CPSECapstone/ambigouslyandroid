package com.example.flipteded.businesslogic.old


class SaveNewEntryUseCase (private val repo : DataRepo) {
    suspend fun invoke(entry : DataEntry) {
        repo.saveEntry(entry)
    }
}