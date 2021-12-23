package com.example.catlife.catfact

sealed class CatFactChange {
    // if not have any parameter - object, if has
    object Loading: CatFactChange()
    data class CatFactLoaded(val catFact: String): CatFactChange()
    object Error: CatFactChange()
}