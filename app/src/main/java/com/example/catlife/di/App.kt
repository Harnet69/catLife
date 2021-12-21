package com.example.catlife.di

import android.app.Application
import android.content.Context

class App : Application() {

    // will be created only when app asks for it
    val di: DependencyInjection by lazy {
        DependencyInjectionImpl()
    }
}

//make it available from anywhere
val Context.di: DependencyInjection
    get() = (this.applicationContext as App).di