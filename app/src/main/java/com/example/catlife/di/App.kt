package com.example.catlife.di

import android.app.Application
import android.content.Context
import com.example.catlife.R

class App : Application() {

    // will be created only when app asks for it
    val di: DependencyInjection by lazy {
        DependencyInjectionImpl(getString(R.string.api_url))
    }
}

//make it available from anywhere
val Context.di: DependencyInjection
    get() = (this.applicationContext as App).di