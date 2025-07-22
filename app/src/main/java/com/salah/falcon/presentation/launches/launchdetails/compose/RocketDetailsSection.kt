package com.salah.falcon.presentation.launches.launchdetails.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.salah.falcon.R
import com.salah.falcon.presentation.launches.models.RocketUiModel

@Composable
internal fun RocketDetailsSection(
    modifier: Modifier = Modifier,
    rocket: RocketUiModel,
) {
    Column(
        modifier = modifier,
    ) {
        DetailsTitle(
            text = stringResource(id = R.string.rocket_title),
        )

        DetailsProperty(
            text = stringResource(
                id = R.string.rocket_name_label,
                rocket.name,
            ),
        )

        DetailsProperty(
            text = stringResource(
                id = R.string.rocket_type_label,
                rocket.type,
            ),
        )

        DetailsProperty(
            text = stringResource(
                id = R.string.rocket_id_label,
                rocket.id,
            ),
        )
    }
}

