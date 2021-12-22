package com.example.catlife.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.catlife.R
import com.example.catlife.di.di
import com.example.catlife.repository.CatFactRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class CatFactActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catFactRepository = di.catFactRepository

        cat_get_btn.setOnClickListener {
            fetchCatFacts(catFactRepository)
        }
    }

    // fetch API using rxJava
    private fun fetchCatFacts(catFactRepository: CatFactRepository) {
        disposable = catFactRepository.getFact()
            // onError handler is necessary
            .toObservable()
            .onErrorResumeNext(Observable.empty())

            // specify threading doing call not in the Main thread
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            // loading
            .doOnSubscribe {
                onLoading()
            }
            // error
            .doOnError {
                onError(it.localizedMessage)
            }
            // success
            .subscribe { catFactsText ->
                onSuccess(catFactsText)
            }
    }

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

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}