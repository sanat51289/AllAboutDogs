package example.com.allaboutdogs.network

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Service(context: Context) {

    private val cacheSize : Long = 10 * 1024 * 1024 // 10MB

    private val okHttpClient = OkHttpClient.Builder()
        .cache(Cache(context.cacheDir, cacheSize))
        .build()

    private val webservice : Api by lazy {
        Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build().create(Api::class.java)
    }

    suspend fun fetchAllBreeds() : Map<String, Any> {
        return webservice.fetchAllBreeds()
    }

    suspend fun fetchImagesForBreed(breedName: String) : Map<String, Any> {
        return webservice.fetchImagesForBreed(breedName)
    }

    suspend fun fetchBreedImage(breedName: String) : Map<String, Any> {
        return webservice.fetchBreedImage(breedName)
    }
}