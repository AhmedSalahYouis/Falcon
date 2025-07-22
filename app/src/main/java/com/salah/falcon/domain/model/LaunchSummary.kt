package com.salah.falcon.domain.model

data class LaunchSummary(
    val id: String,
    val rocketName: String,
    val mission: Mission,
)