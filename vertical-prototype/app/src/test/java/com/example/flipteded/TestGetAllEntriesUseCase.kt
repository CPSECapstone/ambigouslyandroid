package com.example.flipteded

import com.example.flipteded.businesslogic.old.DataEntry
import com.example.flipteded.businesslogic.old.DataRepo
import com.example.flipteded.businesslogic.old.GetAllEntriesUseCase
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.Assert.*

class TestGetAllEntriesUseCase {

    class MockDataRepo : DataRepo {
        val entries = mutableListOf<DataEntry>()
        override suspend fun getAllEntries(): List<DataEntry> {
            return entries
        }

        override suspend fun saveEntry(entry: DataEntry) {
            entries.add(entry)
        }

    }

    @Test
    fun getAllEntriesUseCaseTest() = runBlockingTest {
        val repo = MockDataRepo()
        val useCase = GetAllEntriesUseCase(repo)
        assertEquals(listOf<DataEntry>(), repo.getAllEntries())
        repo.entries.add(DataEntry("Test"))
        assertEquals(listOf(DataEntry("Test")), useCase.invoke())
        repo.entries.add(DataEntry("Test2"))
        assertEquals(listOf(DataEntry("Test"), DataEntry("Test2")), useCase.invoke())
        repo.entries.remove(DataEntry("Test"))
        assertEquals(listOf(DataEntry("Test2")), useCase.invoke())
    }
}