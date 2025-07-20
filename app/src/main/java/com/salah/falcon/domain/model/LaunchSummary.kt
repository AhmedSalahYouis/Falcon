package com.salah.falcon.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchSummary(
    val id: String,
    val rocketName: String,
    val mission: Mission,
) : Parcelable{

}
