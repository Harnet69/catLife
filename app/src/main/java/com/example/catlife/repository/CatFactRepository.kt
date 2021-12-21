package com.example.catlife.repository

import com.example.catlife.retrofit.CatFactService
import io.reactivex.Single

/*
    Transform a data before returning to a view
 */
class CatFactRepository(private val catFactService: CatFactService) {

    // single is observable which emits once on a subscription
    @Throws(Exception::class)
    fun getFact(): Single<String> {
        return catFactService.getFacts()
            .map { it.random().text }
    }
}