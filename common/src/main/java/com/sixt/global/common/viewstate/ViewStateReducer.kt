package com.sixt.global.common.viewstate

interface ViewStateReducer <T : ViewState> {
    val updateView: T.() -> Unit
}