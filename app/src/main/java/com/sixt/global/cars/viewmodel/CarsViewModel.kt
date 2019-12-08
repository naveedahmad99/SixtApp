package com.sixt.global.cars.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sixt.global.cars.viewstate.CarsViewState

interface CarsViewModel {

    fun getStateStream(): MutableLiveData<CarsViewState>
    fun perform(action: Action)

    sealed class Action {
        object RequestCars : Action()
    }
}