package com.sixt.global.cars.service

import com.sixt.global.cars.service.model.Car
import retrofit2.http.GET

interface CarsService {
    @GET("cars")
    suspend fun getCars(): List<Car>
}