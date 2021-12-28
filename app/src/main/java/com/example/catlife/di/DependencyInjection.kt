package com.example.catlife.di

import com.example.catlife.catfact.CatFactAction
import com.example.catlife.catfact.CatFactState
import com.example.catlife.catfact.CatFactUseCaseImpl
import com.example.catlife.repository.CatFactRepository
import com.example.catlife.retrofit.CatFactService
import com.example.catlife.viewModel.CatFactViewModel
import com.squareup.moshi.Moshi
import com.ww.roxie.BaseViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

// used for testing
interface DependencyInjection{
    // in big projects is using ViewModelFactory
    val catFactViewModel: BaseViewModel<CatFactAction, CatFactState>
}

class DependencyInjectionImpl(apiUrl: String): DependencyInjection{
    override lateinit var catFactViewModel: BaseViewModel<CatFactAction, CatFactState>

    init {
        // OkHttp interceptor which logs HTTP request and response data
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // parse JSON into Java and Kotlin classes
        val moshi = Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(apiUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val catFactService = retrofit.create<CatFactService>()

        val catFactRepository = CatFactRepository(catFactService)
        val catFactUseCase = CatFactUseCaseImpl(catFactRepository)
        catFactViewModel =  CatFactViewModel(catFactUseCase)
    }
}