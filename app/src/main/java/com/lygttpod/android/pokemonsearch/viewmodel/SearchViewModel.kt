package com.lygttpod.android.pokemonsearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.lygttpod.android.pokemonsearch.PokemonQuery
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * <pre>
 *      author  : Allen
 *      date    : 2023/10/26
 *      desc    :
 * </pre>
 */
class SearchViewModel : ViewModel() {

    companion object {
        var clickItemData: PokemonQuery.Pokemon_v2_pokemonspecy? = null
    }

    private val limit = 20
    private val serverUrl = "https://beta.pokeapi.co/graphql/v1beta"
    private var lastInput: String = ""

    private val apolloClient by lazy {
        ApolloClient.Builder()
            .serverUrl(serverUrl)
            .build()
    }

    private var searchJob: Job? = null
    val resultLiveData = MutableLiveData<PokemonQuery.Data?>()
    val errorLiveData = MutableLiveData<String>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun search(value: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            //debounce the search input
            if (lastInput == value) return@launch
            lastInput = value
            if (lastInput.isBlank()) {
                resultLiveData.value = null
                return@launch
            }
            delay(500)
            loadingLiveData.value = true
            val response =
                apolloClient.query(PokemonQuery(limit = limit, name = "%$value%")).execute()
            loadingLiveData.value = false
            if (response.hasErrors()) {
                errorLiveData.value =
                    response.errors?.firstOrNull { it.message.isNotBlank() }?.message
            } else {
                resultLiveData.value = response.data
            }
        }
    }
}