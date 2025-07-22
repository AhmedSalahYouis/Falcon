package com.salah.falcon.presentation.launches.models

import android.os.Parcelable
import com.salah.falcon.domain.model.Rocket
import kotlinx.parcelize.Parcelize

@Parcelize
data class RocketUiModel(
    val id: String,
    val name: String,
    val type: String,
) : Parcelable

fun Rocket.toUiModel() = RocketUiModel(
    id = this.name,
    name = this.name,
    type = this.type
)

