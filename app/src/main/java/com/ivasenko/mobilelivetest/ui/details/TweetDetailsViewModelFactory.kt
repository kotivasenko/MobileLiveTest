package com.ivasenko.mobilelivetest.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ivasenko.mobilelivetest.data.repository.TweeterRepository

class TweetDetailsViewModelFactory (
    private val repository: TweeterRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TweetDetailsViewModel(repository) as T
        }
}