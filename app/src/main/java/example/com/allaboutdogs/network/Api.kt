package example.com.allaboutdogs.network

import retrofit2.http.GET
import retrofit2.http.Path


interface Api {

    @GET("breeds/list/all")
    suspend fun fetchAllBreeds() : Map<String, Any>

    @GET("breed/{breedName}/images/random")
    suspend fun fetchBreedImage(@Path("breedName") breedName: String) : Map<String, Any>

    @GET("breed/{breedName}/images")
    suspend fun fetchImagesForBreed(@Path("breedName") breedName: String) : Map<String, Any>

}