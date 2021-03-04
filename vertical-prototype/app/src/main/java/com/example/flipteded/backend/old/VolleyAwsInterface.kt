package com.example.flipteded.backend.old

import android.app.Application
import com.example.flipteded.businesslogic.old.DataEntry
import com.example.flipteded.businesslogic.old.DataRepo
import java.util.function.Supplier

class VolleyAwsInterface(private val contextSupplier : Supplier<Application>) : DataRepo {
    override suspend fun getAllEntries(): List<DataEntry> {
        return VerticalSliceNetworkCalls.getStringsFromAWS(contextSupplier.get()).map { DataEntry(it) }
    }

    override suspend fun saveEntry(entry: DataEntry) {
        VerticalSliceNetworkCalls.sendStringToAWS(contextSupplier.get(), entry.item)
    }
}