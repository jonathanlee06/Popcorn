package com.jonathanlee.popcorn.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonathanlee.popcorn.data.model.network.SearchModel
import com.jonathanlee.popcorn.data.repository.Repository
import com.jonathanlee.popcorn.data.repository.SearchRepository
import com.jonathanlee.popcorn.data.source.ApiResultState
import com.jonathanlee.popcorn.util.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepo: SearchRepository = Repository.provideSearchRepository(),
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ""
        )

    private val _searchResult = MutableLiveData<List<SearchModel>>()
    val searchResult: LiveData<List<SearchModel>> = _searchResult

    fun search(query: String, page: Int) {
        viewModelScope.launch {
            searchRepo.search(query, page).collect { result ->
                when (result) {
                    is ApiResultState.Loading -> {
                        // do loading
                    }

                    is ApiResultState.Success -> {
                        _searchResult.value = result.data?.results
                        Log.d(TAG, "search: ${result.data?.results}")

                    }

                    is ApiResultState.Failure -> {
                        Log.d(TAG, "response error=${result.exception}")
                    }
                }
            }
        }
    }

    fun setQuery(text: String) {
        _searchQuery.value = text
    }
}