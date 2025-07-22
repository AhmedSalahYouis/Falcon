package com.salah.falcon.presentation.launches.launchdetails.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.salah.falcon.R

@Composable
internal fun SiteDetailsSection(
    modifier: Modifier = Modifier,
    site: String,
) {
    Column(
        modifier = modifier,
    ) {
        DetailsTitle(
            text = stringResource(id = R.string.site_title),
        )
        DetailsProperty(text = site)
    }
}

