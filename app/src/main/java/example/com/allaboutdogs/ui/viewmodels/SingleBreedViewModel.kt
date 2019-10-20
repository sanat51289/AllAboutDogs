package example.com.allaboutdogs.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import example.com.allaboutdogs.network.Service
import kotlinx.coroutines.*


class SingleBreedViewModel(val service: Service) : ViewModel(){

    val observer: MutableLiveData<BreedImageResponse> = MutableLiveData()

    private val viewModelJob = SupervisorJob()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun fetchBreedsImages(breedName: String) {
        observer.value = Loading

        try {
            uiScope.launch {
                startBreedsImageFetch(breedName)
            }
        } catch (throwable: Throwable) {
            observer.value = ImageFetchError().also {
                it.throwable = throwable
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun startBreedsImageFetch(breedName: String) = withContext(Dispatchers.IO) {

        val imageList = service.fetchImagesForBreed(breedName)["message"] as List<String>

        val success = ImageFetchSuccess().also { success ->

            success.images = imageList
        }

        observer.postValue(success)
    }
}

sealed class BreedImageResponse

class ImageFetchSuccess : BreedImageResponse() {
    lateinit var images: List<String>
}

class ImageFetchError : BreedImageResponse() {
    lateinit var throwable: Throwable
}

object Loading : BreedImageResponse()