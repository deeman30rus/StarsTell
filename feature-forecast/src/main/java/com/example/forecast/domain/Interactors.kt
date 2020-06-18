package com.example.forecast.domain

import com.delizarov.zodiacview.ZodiacSign

internal class ForecastInteractor {

    fun getForecast(sign: ZodiacSign): Forecast {

        return Forecast("forecasts are fake")
    }
}
