package com.example.forecast.mvp

import com.delizarov.core.mvp.MVPFeature

internal class ForecastFeature(
    presenter: ForecastPresenter,
    view: ForecastView
) : MVPFeature<ForecastView, ForecastPresenter>(presenter, view) {

}