package example.com.allaboutdogs.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import example.com.allaboutdogs.network.Service
import kotlinx.coroutines.*


class BreedListViewModel(private val service: Service) : ViewModel() {

    val observer: MutableLiveData<BreedResponse> = MutableLiveData()

    private val viewModelJob = SupervisorJob()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun fetchBreeds() {
        observer.value = LoadingResp

        try {
            uiScope.launch {
                startBreedsFetch()
            }
        } catch (throwable: Throwable) {
            observer.value = ErrorResp().also {
                it.throwable = throwable
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun startBreedsFetch() = withContext(Dispatchers.IO) {

        val breedsList = service.fetchAllBreeds()["message"] as Map<String, List<String>>

        val success = SuccessResp().also { success ->
            val breedMap = mutableMapOf<String, String>()

            breedsList.keys.map {

                val fetchBreedImage = service.fetchBreedImage(it)

                val imageLink = fetchBreedImage["message"] as String

                breedMap.put(it, imageLink)
            }

            success.breeds = breedMap
        }

        observer.postValue(success)
    }
}

sealed class BreedResponse

class SuccessResp : BreedResponse() {
    lateinit var breeds: Map<String, String>
}

class ErrorResp : BreedResponse() {
    lateinit var throwable: Throwable
}

object LoadingResp : BreedResponse()