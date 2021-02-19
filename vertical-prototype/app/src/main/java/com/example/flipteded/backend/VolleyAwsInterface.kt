package com.example.flipteded.backend

import android.app.Application
import com.example.flipteded.businesslogic.DataEntry
import com.example.flipteded.businesslogic.DataRepo
import java.util.function.Supplier

class VolleyAwsInterface(private val contextSupplier : Supplier<Application>) : DataRepo {
    override suspend fun getAllEntries(): List<DataEntry> {
        return VerticalSliceNetworkCalls.getStringsFromAWS(contextSupplier.get()).map { DataEntry(it) }
    }

    override suspend fun saveEntry(entry: DataEntry) {
        VerticalSliceNetworkCalls.sendStringToAWS(contextSupplier.get(), entry.item)
    }
}