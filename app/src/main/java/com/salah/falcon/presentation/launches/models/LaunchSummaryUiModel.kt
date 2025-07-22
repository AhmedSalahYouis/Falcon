package com.salah.falcon.presentation.launches.models

import com.salah.falcon.domain.model.LaunchSummary

data class LaunchSummaryUiModel(
    val id: String,
    val rocketName: String,
    val missionName: String,
    val missionPatchImageURL: String?,
)

fun LaunchSummary.toUiModel() = LaunchSummaryUiModel(
    id = this.id,
    rocketName = this.rocketName,
    missionName = this.mission.name,
    missionPatchImageURL = this.mission.missionPatchImageURL
)
