package com.example.rickmasters.domain.models

import androidx.annotation.StringRes
import com.example.rickmasters.R

enum class CounterObservableValue(
    @StringRes val increaseRes: Int,
    @StringRes val decreaseRes: Int
) {
    VISITORS(
        increaseRes = R.string.visitors_increase_desc,
        decreaseRes = R.string.visitors_decrease_desc
    ),
    SUBSCRIPTIONS(
        increaseRes = R.string.subs_increase_desc,
        decreaseRes = R.string.subs_decrease_desc
    )
}