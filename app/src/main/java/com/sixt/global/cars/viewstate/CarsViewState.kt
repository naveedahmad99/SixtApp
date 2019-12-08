package com.sixt.global.cars.viewstate

import com.sixt.global.cars.service.model.Car
import com.sixt.global.common.viewstate.ViewState

data class CarsViewState(
    var showError: Boolean = false,
    var errorMessage: String = "",
    var carsList: List<Car> = listOf(),
    var moveCamera: Boolean = false
) : ViewState