package com.example.catlife.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.catlife.R
import com.example.catlife.model.CatFact
import com.example.catlife.retrofit.CatFactService
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        // OkHttp interceptor which logs HTTP request and response data
//        val logging = HttpLoggingInterceptor()
//        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
//        val client = OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .build()
//
//        // parse JSON into Java and Kotlin classes
//        val moshi = Moshi.Builder()
//            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .client(client)
//            .baseUrl("https://cat-fact.herokuapp.com/")
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//
//        val catFactService = retrofit.create<CatFactService>()



//        cat_get_btn.setOnClickListener { fetchCatFacts(catFactService) }
    }

//    private fun fetchCatFacts(catFactService: CatFactService){
//        onLoading()
//        val call = catFactService.getFacts()
//        call.clone().enqueue(object : Callback<List<CatFact>> {
//            override fun onResponse(
//                call: Call<List<CatFact>>,
//                response: retrofit2.Response<List<CatFact>>
//            ) {
//                onSuccess(response.body())
//            }
//
//            override fun onFailure(call: Call<List<CatFact>>, t: Throwable) {
//                onError(t.localizedMessage)
//            }
//
//        })
//    }

    private fun onSuccess(catFactsText: String?) {
        err_msg.visibility = View.GONE
        loading_progress_bar.visibility = View.GONE
        cat_info.apply {
            visibility = View.VISIBLE
            text = catFactsText
        }
        cat_get_btn.visibility = View.VISIBLE
    }

    private fun onLoading() {
        err_msg.visibility = View.GONE
        loading_progress_bar.visibility = View.VISIBLE
        cat_info.apply {
            visibility = View.GONE
        }
        cat_get_btn.visibility = View.GONE
    }

    private fun onError(errMsg: String?) {
        loading_progress_bar.visibility = View.GONE
        err_msg.apply {
            text = errMsg
            visibility = View.VISIBLE
        }
        cat_info.apply {
            visibility = View.GONE
        }

        cat_get_btn.visibility = View.VISIBLE
    }
}