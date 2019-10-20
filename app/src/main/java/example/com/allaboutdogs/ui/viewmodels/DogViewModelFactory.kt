package example.com.allaboutdogs.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import example.com.allaboutdogs.network.Service


class BreedListViewModelFactory(private val service: Service) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BreedListViewModel(service) as T
    }
}

class SingleBreedViewModelFactory(private val service: Service) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SingleBreedViewModel(service) as T
    }
}