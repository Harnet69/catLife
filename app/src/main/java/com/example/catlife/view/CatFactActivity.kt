package com.example.catlife.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.catlife.R
import com.example.catlife.catFact.CatFactAction
import com.example.catlife.catFact.CatFactState
import com.example.catlife.di.di
import com.example.catlife.viewModel.CatFactViewModel
import kotlinx.android.synthetic.main.activity_main.*

class CatFactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val catFactViewModel = di.catFactViewModel
        observe(catFactViewModel as CatFactViewModel)

        cat_get_btn.setOnClickListener {
            catFactViewModel.dispatch(CatFactAction.GetFactBtnClicked)
        }
    }

    // fetch API using rxJava
    private fun observe(catFactViewModel: CatFactViewModel) {
        catFactViewModel.observableState.observe(this, {state ->
            renderState(state)
        })

    }

    private fun renderState(state: CatFactState) {
        with(state){
            if(catFact.isNotEmpty()) cat_info.text = catFact
            loading_progress_bar.isVisible = loading
            err_msg.isVisible = showErr
        }
    }

}