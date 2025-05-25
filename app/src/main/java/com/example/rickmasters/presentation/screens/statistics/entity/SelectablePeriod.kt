package com.example.rickmasters.presentation.screens.statistics.entity

interface SelectablePeriod {
    val label: String
}

enum class VisitorsPeriod(override val label: String): SelectablePeriod {
    DAYS(label = "По дням"),
    WEEKS(label = "По неделям"),
    MONTHS(label = "По месяцам")
}


enum class GenderAgeDiagramPeriod(override val label: String): SelectablePeriod {
    TODAY("Сегодня"),
    WEEK("Неделя"),
    MONTH("Месяц"),
    ALL_TIME("Все время")
}