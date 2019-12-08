package com.sixt.global.cars.di

import com.sixt.global.cars.repository.CarsRepository
import com.sixt.global.cars.repository.CarsRepositoryImpl
import com.sixt.global.cars.service.CarsService
import com.sixt.global.cars.viewmodel.CarsViewModelImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val carsModule = module {
    single { provideCarsService(get()) }
    factory<CarsRepository> { CarsRepositoryImpl(get()) }
    viewModel { CarsViewModelImpl(get(), get()) }
}

fun provideCarsService(retrofit: Retrofit): CarsService = retrofit.create(
    CarsService::class.java)