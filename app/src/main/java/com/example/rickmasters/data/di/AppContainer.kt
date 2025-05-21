package com.example.rickmasters.data.di

import com.example.rickmasters.data.network.StatisticApi
import com.example.rickmasters.data.repository.StatisticRepositoryImpl
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
}