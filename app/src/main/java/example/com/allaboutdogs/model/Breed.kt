package example.com.allaboutdogs.model


data class Breed(val breedName: String) {
    var subBreeds: List<SubBreed> = emptyList()
}
