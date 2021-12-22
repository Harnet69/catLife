package com.example.catlife.catFact

import com.ww.roxie.BaseAction

/*
    contain action user can preform on UI
 */
sealed class CatFactAction: BaseAction {
    object GetFactBtnClicked: CatFactAction()
}