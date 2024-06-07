package com.dacs3.shop.ui.screens.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState

    fun onSearchQueryChanged(query: String) {
        _searchUiState.value = _searchUiState.value.copy(query = query)
    }
}