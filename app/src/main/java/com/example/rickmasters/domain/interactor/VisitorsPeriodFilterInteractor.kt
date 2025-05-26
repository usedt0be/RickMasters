package com.example.rickmasters.domain.interactor

import com.example.rickmasters.domain.models.Statistic

interface VisitorsPeriodFilterInteractor {
    fun filterVisitorsByDays(statistics: List<Statistic>): Map<String, Int>?


    fun filterVisitorsByWeeks(statistics: List<Statistic>): Map<String, Int>?


    fun filterVisitorsByMonths(statistics: List<Statistic>): Map<String, Int>?
}