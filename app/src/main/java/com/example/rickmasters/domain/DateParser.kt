package com.example.rickmasters.domain


fun parseTimestampToDate(input: String): String? {
    if (input.length < 6 || input.length > 8) return null
    val dayLength = input.length - 6
    val day = input.substring(0, dayLength).padStart(2, '0')
    val month = input.substring(dayLength, dayLength + 2).padStart(2, '0')
    return "$day.$month"
}