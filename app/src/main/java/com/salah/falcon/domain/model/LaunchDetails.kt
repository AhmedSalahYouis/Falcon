package com.salah.falcon.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchDetails(
    val id: String,
    val rocket: Rocket,
    val mission: Mission,
    val site: String,
) : Parcelable
