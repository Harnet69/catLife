package com.example.catlife.catfact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.catlife.RxTestSchedulerRule
import com.example.catlife.viewModel.CatFactViewModel
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatFactViewModelTest {

    // allows to execute each task synchronously
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testRule: RxTestSchedulerRule = RxTestSchedulerRule()


    private lateinit var testSubject: CatFactViewModel

    private val initialState = CatFactState()

    private val loadingState = CatFactState()


    private val catFactUseCase = mock<CatFactUseCase>()

    private val observer = mock<Observer<CatFactState>>()

    @Before
    fun setUp() {
        testSubject = CatFactViewModel(catFactUseCase)
        testSubject.observableState.observeForever(observer)
    }

    @Test
    fun `Given fact successfully loaded, when action GetFact is received, than State contains fact`() {
        //Given: define all properties used in the test
        val factExample = "Test cat fact example"
        val loadingState = CatFactState(loading = true)
        val successState = CatFactState(loading = false, catFact = factExample)
        //TODO think about implementing separate test for error case
//        val errorState = CatFactState(showErr = false)

        // whenever factExample is invoked
        whenever(catFactUseCase.getFact()).thenReturn(Single.just(factExample))

        //WHEN:  when action GetFact is received
        testSubject.dispatch(CatFactAction.GetFactBtnClicked)
        // trig Android scheduler switch to our test scheduler
        testRule.triggerActions()

        //THAN: observer gets its state in order of occurrence
        // !!! The right order is demanded!!!
        inOrder(observer){
            verify(observer).onChanged(CatFactState()) // initial state at first
            verify(observer).onChanged(loadingState) // loading state
            verify(observer).onChanged(successState) // success state
//            verify(observer).onChanged(errorState) // error state
        }
    }

}