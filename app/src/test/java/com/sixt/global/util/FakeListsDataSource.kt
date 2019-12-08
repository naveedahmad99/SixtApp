package com.sixt.global.util

import com.sixt.global.cars.service.model.Car

object FakeListsDataSource {

    fun getFakeCars() = listOf(
        Car(
            "WMWSW31030T222518",
            "mini",
            "MINI",
            "Vanessa",
            "BMW",
            "MINI",
            "midnight_black",
            "MINI",
            "D",
            0.7,
            "M",
            "M-VO0259",
            48.134557,
            11.576921,
            "REGULAR",
            "https://cdn.sixt.io/codingtask/images/mini.png"
        ),
        Car(
            "WMWSW31030T222517",
            "bmw_1er",
            "BMW 1er",
            "Sonja",
            "BMW",
            "BMW",
            "alpinweiss",
            "ler",
            "P",
            0.51,
            "M",
            "M-Z8478",
            48.106956,
            11.590711,
            "CLEAN",
            "https://cdn.sixt.io/codingtask/images/bmw_1er.png"
        )
    )

}