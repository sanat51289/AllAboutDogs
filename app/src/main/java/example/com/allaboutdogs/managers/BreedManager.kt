package example.com.allaboutdogs.managers


class BreedManager {
    var breedsImageMap: Map<String, String> = emptyMap()
    var singleBreedImageMap: MutableMap<String, List<String>> = mutableMapOf()
}