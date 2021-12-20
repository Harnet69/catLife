package com.example.catlife.retrofit

import com.example.catlife.model.CatFact
import com.example.catlife.model.Response
import retrofit2.Call
import retrofit2.http.GET

interface CatFactService {
    @GET("facts")
    fun getFacts(): Call<List<CatFact>>
}