package com.salah.falcon.presentation.launches.launchdetails.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.salah.falcon.R
import com.salah.falcon.presentation.launches.models.MissionUiModel

@Composable
internal fun MissionDetailsSection(
    modifier: Modifier = Modifier,
    mission: MissionUiModel,
) {
    Column(
        modifier = modifier,
    ) {
        DetailsTitle(
            text = stringResource(id = R.string.mission_title),
        )
        DetailsProperty(
            text = stringResource(
                id = R.string.mission_name_label,
                mission.name,
            ),
        )
    }
}