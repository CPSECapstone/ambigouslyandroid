package com.example.flipteded

import com.example.flipteded.businesslogic.DataEntry
import com.example.flipteded.businesslogic.DataRepo
import com.example.flipteded.businesslogic.GetAllEntriesUseCase
import com.example.flipteded.businesslogic.SaveNewEntryUseCase
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class TestSaveNewEntryUseCase {

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
    fun saveNewEntryUseCaseTest() = runBlockingTest {
        val repo = MockDataRepo()
        val useCase = SaveNewEntryUseCase(repo)
        Assert.assertEquals(listOf<DataEntry>(), repo.entries)
        useCase.invoke(DataEntry("Test"))
        Assert.assertEquals(listOf(DataEntry("Test")), repo.entries)
        useCase.invoke(DataEntry("Test2"))
        Assert.assertEquals(listOf(DataEntry("Test"), DataEntry("Test2")), repo.getAllEntries())
    }
}