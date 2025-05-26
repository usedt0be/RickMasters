package com.example.rickmasters.data.di

import com.example.rickmasters.data.network.StatisticApi
import com.example.rickmasters.data.repository.StatisticRepositoryImpl
import com.example.rickmasters.domain.interactor.GenderAgePeriodFilterInteractorImpl
import com.example.rickmasters.domain.interactor.VisitorsPeriodFilterInteractorImpl
import com.example.rickmasters.domain.usecase.GetFavoriteVisitorsUseCase
import com.example.rickmasters.domain.usecase.GetLatestMonthDateUseCase
import com.example.rickmasters.domain.usecase.GetPreviousSevenWeeksUseCase
import com.example.rickmasters.domain.usecase.GetStatisticUseCase
import com.example.rickmasters.domain.usecase.GetUniqueViewsCountUseCase
import com.example.rickmasters.domain.usecase.GetUsersUseCase
import com.example.rickmasters.domain.usecase.GetPreviousSevenDaysUseCase
import com.example.rickmasters.domain.usecase.GetPreviousSevenMonthsUseCase
import com.example.rickmasters.domain.usecase.GetPreviousThirtyDaysUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

private const val URL_HOST = "test.rikmasters.ru"

class AppContainer {

    val ktorHttp = HttpClient(CIO){
        defaultRequest {
            host = URL_HOST
            url {
                path("api")
                protocol = URLProtocol.HTTP
            }
        }

        install(ContentNegotiation) {
            json(
                Json{
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.tag("KTOR_LOGGER").d(message =message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Timber.tag("HTTP_STATUS").d(message = "$response")
            }
        }
    }

    val statisticApi by lazy {
        StatisticApi(
            httpClient = ktorHttp
        )
    }

    val statisticRepository by lazy {
        StatisticRepositoryImpl(
            statisticApi
        )
    }

    val statisticUseCase by lazy {
        GetStatisticUseCase(
            statisticRepository
        )
    }

    val getUserUseCase by lazy {
        GetUsersUseCase(
            statisticRepository
        )
    }

    val getFavoriteUsersUseCase by lazy {
        GetFavoriteVisitorsUseCase()
    }

    val getLatestMonthDateUseCase by lazy {
        GetLatestMonthDateUseCase()
    }
    val getFavoriteVisitorsUseCase by lazy {
        GetFavoriteVisitorsUseCase()
    }

    val getUniqueViewsCountUseCase by lazy {
        GetUniqueViewsCountUseCase()
    }

    val getWeekFromDateUseCase by lazy {
        GetPreviousSevenDaysUseCase()
    }

    val getPreviousSevenWeeksUseCase by lazy {
        GetPreviousSevenWeeksUseCase()
    }

    val getPreviousSevenMonthsUseCase by lazy {
        GetPreviousSevenMonthsUseCase()
    }

    val getPreviousSevenDaysUseCase by lazy{
        GetPreviousSevenDaysUseCase()
    }

    val getPreviousThirtyDaysUseCase by lazy {
        GetPreviousThirtyDaysUseCase()
    }

    val genderAgePeriodInteractor by lazy {
        GenderAgePeriodFilterInteractorImpl(
            getLatestMonthDateUseCase = getLatestMonthDateUseCase,
            getPreviousSevenDaysUseCase = getPreviousSevenDaysUseCase,
            getPreviousThirtyDaysUseCase = getPreviousThirtyDaysUseCase
        )
    }

    val visitorsPeriodFilterInteractor by lazy {
        VisitorsPeriodFilterInteractorImpl(
            getUniqueViewsCountUseCase = getUniqueViewsCountUseCase,
            getWeekFromDateUseCase = getWeekFromDateUseCase,
            getLatestMonthDateUseCase = getLatestMonthDateUseCase,
            getLastSevenWeeksUseCase = getPreviousSevenWeeksUseCase,
            getLastSevenMonthsUseCase = getPreviousSevenMonthsUseCase
        )
    }

}