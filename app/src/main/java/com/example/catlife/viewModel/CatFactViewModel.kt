package com.example.catlife.viewModel

import com.example.catlife.catFact.CatFactAction
import com.example.catlife.catFact.CatFactChange
import com.example.catlife.catFact.CatFactState
import com.example.catlife.catFact.CatFactUseCase
import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class CatFactViewModel(private val catFactUseCase: CatFactUseCase) :
    BaseViewModel<CatFactAction, CatFactState>() {
    override val initialState: CatFactState = CatFactState()

    init {
        bindActions()
    }

    /*
     reducer function applies to function to previous state and returns a new state according
      changes we made
     */
    private val reducer: Reducer<CatFactState, CatFactChange> = {state, change ->
        when(change){
            is CatFactChange.Loading -> state.copy(loading = true, showErr = false)
            is CatFactChange.CatFactLoaded -> state.copy(loading = false, catFact = change.catFact)
            is CatFactChange.Error -> state.copy(loading = false, showErr = true)
        }
    }

    private fun bindActions() {
        val getCatFactChange = actions.ofType(CatFactAction.GetFactBtnClicked::class.java)
            .switchMap {
                // implementation when the button is clicked
                catFactUseCase.getFact()
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map<CatFactChange> { catFact ->
                        CatFactChange.CatFactLoaded(catFact)
                    }
                    .onErrorReturn { CatFactChange.Error }
                    .startWith(CatFactChange.Loading)
            }

        disposables += getCatFactChange.scan(initialState, reducer)
            .observeOn(AndroidSchedulers.mainThread())
            // result of the subscription is setting a view state
            .subscribe(state::setValue)
    }
}