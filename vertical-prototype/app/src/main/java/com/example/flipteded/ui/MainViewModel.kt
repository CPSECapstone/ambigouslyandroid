package com.example.flipteded.ui

import android.app.Application
import androidx.lifecycle.*
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.example.LaunchDetailsQuery
import com.example.flipteded.backend.VerticalSliceNetworkCalls
import com.example.flipteded.backend.VolleyAwsInterface
import com.example.flipteded.businesslogic.DataEntry
import com.example.flipteded.businesslogic.GetAllEntriesUseCase
import com.example.flipteded.businesslogic.SaveNewEntryUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.function.Supplier

class MainViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val REFRESH_PERIOD : Long = 1000
    }
    private val _data : MutableLiveData<List<String>> = MutableLiveData()
    val data : LiveData<List<String>>
        get() = _data

    private val repo = VolleyAwsInterface( Supplier<Application> { getApplication() })
    private val getAllEntriesUseCase = GetAllEntriesUseCase(repo)
    private val setEntryUseCase = SaveNewEntryUseCase(repo)

    val apolloClient = ApolloClient.builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .build()

    init {
        viewModelScope.launch {
            while(true) {
                _data.value = getAllEntriesUseCase.invoke().map{ it.item }
                delay(REFRESH_PERIOD)
            }
        }
    }

    fun save(str : String) {
        viewModelScope.launch {
            setEntryUseCase.invoke(DataEntry(str))
            _data.value = getAllEntriesUseCase.invoke().map{ it.item }
        }
    }

    fun apolloTest()
    {
        viewModelScope.launch {  val response = try {
            apolloClient.query(LaunchDetailsQuery(id = "83")).await()
        } catch (e: ApolloException) {
            // handle protocol errors
            return@launch
        }

            val launch = response.data?.launch
            if (launch == null || response.hasErrors()) {
                // handle application errors
                return@launch
            }

            // launch now contains a typesafe model of your data
            println("Launch site: ${launch!!.site}")
        }
    }
}