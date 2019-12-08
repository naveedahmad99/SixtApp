package com.sixt.global.cars.repository

import com.sixt.global.cars.service.CarsService
import com.sixt.global.cars.service.model.Car

class CarsRepositoryImpl(
    private val carsService: CarsService
) : CarsRepository {

    override suspend fun requestCars(): List<Car> = carsService.getCars()
}