package com.salah.falcon.presentation.launches.models

import android.os.Parcelable
import com.salah.falcon.domain.model.LaunchDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchDetailsUiModel(
    val id: String,
    val rocket: RocketUiModel,
    val mission: MissionUiModel,
    val site: String,
): Parcelable

fun LaunchDetails.toUiModel() = LaunchDetailsUiModel(
    id = this.id,
    rocket = this.rocket.toUiModel(),
    mission = this.mission.toUiModel(),
    site = this.site
)
