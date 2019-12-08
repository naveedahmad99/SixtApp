package com.sixt.global.cars.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.espresso.idling.CountingIdlingResource
import com.sixt.global.cars.repository.CarsRepository
import com.sixt.global.cars.viewmodel.CarsViewModel.Action
import com.sixt.global.cars.viewstate.CarsViewState
import com.sixt.global.cars.viewstate.CarsViewStateReducer.ShowData
import com.sixt.global.cars.viewstate.CarsViewStateReducer.ShowError
import com.sixt.global.common.dispatcher.CoroutineDispatchers
import com.sixt.global.common.extension.launchSafely
import com.sixt.global.common.extension.update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CarsViewModelImpl(
    private val repository: CarsRepository,
    private val dispatchers: CoroutineDispatchers
) : ViewModel(), CarsViewModel, CoroutineScope {

    private val state: MutableLiveData<CarsViewState> = MutableLiveData(CarsViewState())

    @VisibleForTesting
    val idlingResource by lazy { CountingIdlingResource(REQUEST_LIST_IDLING_RESOURCE) }

    override fun getStateStream(): MutableLiveData<CarsViewState> = state

    override fun perform(action: Action) {
        when(action) {
            is Action.RequestCars -> { requestCars() }
        }
    }

    private fun requestCars() {
        launchSafely(idlingResource, ::showError) {
            state.update(ShowData(withContext(dispatchers.io()) { repository.requestCars() }))
        }
    }

    private fun showError(exception: Exception) {
        state.update(ShowError(exception))
    }

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext + dispatchers.main()

    companion object {
        const val REQUEST_LIST_IDLING_RESOURCE = "REQUEST_LIST_IDLING_RESOURCE"
    }
}