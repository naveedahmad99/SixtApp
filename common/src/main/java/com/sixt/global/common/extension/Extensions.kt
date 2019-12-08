package com.sixt.global.common.extension

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.espresso.idling.CountingIdlingResource
import com.sixt.global.common.viewstate.ViewState
import com.sixt.global.common.viewstate.ViewStateReducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import kotlin.math.abs

infix fun ViewGroup.inflate(layoutResId: Int): View =
    LayoutInflater.from(context).inflate(layoutResId, this, false)

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit): LiveData<T> =
    liveData.apply { observe(this@observe, Observer { observable -> observable?.let { action(it) } }) }

fun CoroutineScope.launchSafely(
    idlingResource: CountingIdlingResource? = null,
    error: (java.lang.Exception) -> Unit = {},
    block: suspend CoroutineScope.() -> Unit
) {
    launch {
        try {
            idlingResource?.increment()
            block.invoke(this)
        } catch (e: Exception) {
            error.invoke(e)
        } finally {
            idlingResource?.decrement()
        }
    }
}

fun <T : ViewState> MutableLiveData<T>.update(reducer: ViewStateReducer<T>) {
    value = value?.apply {
        reducer.updateView.invoke(this)
    }
}

@SuppressLint("DefaultLocale")
fun String.clean() =
    this.replace("_", " ")
        .toLowerCase()
        .capitalize()

fun Double?.toPercentFormat(
    isAbsoluteValue: Boolean = true,
    signal: String = "-",
    removeZeros: Boolean = false
): String {
    if (this == null) {
        return ""
    }

    val numberFormat = if (removeZeros) {
        DecimalFormat("#.##").apply {
            decimalFormatSymbols = DecimalFormatSymbols(Locale.getDefault())
            roundingMode = RoundingMode.HALF_UP
        }
    } else {
        NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
            roundingMode = RoundingMode.HALF_UP
        }
    }

    val value = numberFormat.format(abs(this)) + "%"

    return if (isAbsoluteValue || this >= 0) value else signal + value
}