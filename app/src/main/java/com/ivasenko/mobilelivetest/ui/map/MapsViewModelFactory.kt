package com.ivasenko.mobilelivetest.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ivasenko.mobilelivetest.data.repository.TweeterRepository

class MapsViewModelFactory(
    private val repository: TweeterRepository
    ) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapsViewModel(repository) as T
    }
}