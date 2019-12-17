package com.ivasenko.mobilelivetest.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ivasenko.mobilelivetest.data.repository.TweeterRepository

class SearchViewModelFactory (
    private val repository: TweeterRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchTweetsViewModel(repository) as T
        }
}