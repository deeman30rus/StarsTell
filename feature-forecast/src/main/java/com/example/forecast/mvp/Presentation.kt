package com.example.forecast.mvp

import com.delizarov.core.mvp.IView
import com.delizarov.core.mvp.Presenter
import com.delizarov.zodiacview.ZodiacSign
import com.example.forecast.domain.Forecast
import com.example.forecast.domain.ForecastInteractor

internal interface ForecastView : IView {

    val sign: ZodiacSign

    fun showForecast(forecast: Forecast) {

    }
}

internal class ForecastPresenter : Presenter<ForecastView>() {

    private val interactors = ForecastInteractor()

    fun onSignSelected(sign: ZodiacSign) {
        view?.showForecast(interactors.getForecast(sign))
    }

}