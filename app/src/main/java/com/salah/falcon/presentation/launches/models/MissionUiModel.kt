package com.salah.falcon.presentation.launches.models

import android.os.Parcelable
import com.salah.falcon.domain.model.Mission
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionUiModel(
    val name: String,
    val imageUrl: String?,
) : Parcelable
fun Mission.toUiModel() = MissionUiModel(
    name = this.name,
    imageUrl = this.missionPatchImageURL
)
