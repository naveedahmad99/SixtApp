package com.sixt.global.cars.repository

import com.sixt.global.cars.service.model.Car

interface CarsRepository {
    suspend fun requestCars(): List<Car>
}