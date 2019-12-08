package com.sixt.global.cars.viewstate

import com.sixt.global.cars.service.model.Car
import com.sixt.global.common.viewstate.ViewStateReducer

sealed class CarsViewStateReducer : ViewStateReducer<CarsViewState> {
    data class ShowError(val exception: Exception) : CarsViewStateReducer() {
        override val updateView: CarsViewState.() -> Unit = {
            showError = true
            errorMessage = exception.message ?: "Unknown error"
            moveCamera = false
        }
    }
    data class ShowData(val list: List<Car>) : CarsViewStateReducer() {
        override val updateView: CarsViewState.() -> Unit = {
            showError = false
            carsList = list
            moveCamera = true
        }
    }
}