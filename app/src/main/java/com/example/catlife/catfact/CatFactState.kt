package com.example.catlife.catfact

import com.ww.roxie.BaseState

/*
    states of the application
 */
data class CatFactState(
    val loading: Boolean = false,
    val catFact: String = "",
    val showErr: Boolean = false
): BaseState