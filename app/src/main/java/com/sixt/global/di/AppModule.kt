package com.sixt.global.di

import com.sixt.global.common.dispatcher.CoroutineDispatchers
import com.sixt.global.common.dispatcher.CoroutineDispatchersImpl
import org.koin.dsl.module

val appModule = module {
    single<CoroutineDispatchers> { CoroutineDispatchersImpl() }
}