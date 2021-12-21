package com.example.catlife.retrofit

import com.example.catlife.model.CatFact
import io.reactivex.Single
import retrofit2.http.GET

interface CatFactService {
    @GET("facts")
    fun getFacts(): Single<List<CatFact>>
}