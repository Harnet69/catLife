package com.example.catlife

import com.example.catlife.model.CatFact
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CatFactService {
    @GET("facts")
    fun getFacts(): Call<Response<CatFact>>
}