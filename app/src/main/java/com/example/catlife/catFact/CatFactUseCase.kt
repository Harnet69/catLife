package com.example.catlife.catFact

import com.example.catlife.repository.CatFactRepository
import io.reactivex.Single

interface CatFactUseCase {
    fun getFact(): Single<String>
}

class CatFactUseCaseImpl(private val catFactRepository: CatFactRepository): CatFactUseCase {
    override fun getFact(): Single<String> = catFactRepository.getFact()
}