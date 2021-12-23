package com.example.catlife.catfact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.catlife.RxTestSchedulerRule
import com.example.catlife.viewModel.CatFactViewModel
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

class CatFactViewModelTest {

    // allows to execute each task synchronously
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testRule: RxTestSchedulerRule = RxTestSchedulerRule()


    private lateinit var testSubject: CatFactViewModel

    private val initialState = CatFactState()

    private val loadingState = CatFactState(loading = true)

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
        val successState = CatFactState(loading = false, catFact = factExample)

        // whenever factExample is invoked
        whenever(catFactUseCase.getFact()).thenReturn(Single.just(factExample))

        //WHEN:  when action is triggered GetFact is received
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

        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `Given fact failed, when action GetFact is received, than State contains error`(){
        //Given
        val errorState = CatFactState(showErr = true)
        whenever(catFactUseCase.getFact()).thenReturn(Single.error(RuntimeException()))

        //WHEN
        testSubject.dispatch(CatFactAction.GetFactBtnClicked)
        testRule.triggerActions()

        //THAN
        inOrder(observer) {
            verify(observer).onChanged(CatFactState()) // initial state at first
            verify(observer).onChanged(loadingState) // loading state
            verify(observer).onChanged(errorState) // error state
        }

        verifyNoMoreInteractions(observer)
    }

}