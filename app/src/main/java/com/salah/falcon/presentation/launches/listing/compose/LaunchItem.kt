package com.salah.falcon.presentation.launches.listing.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.salah.falcon.R
import com.salah.falcon.core.designsystem.theme.FalconTheme
import com.salah.falcon.presentation.launches.models.LaunchSummaryUiModel

@Composable
fun LaunchItem(
    modifier: Modifier = Modifier,
    launchDetails: LaunchSummaryUiModel,
    onClick: () -> Unit = {},
) {
    val shape = RoundedCornerShape(8.dp)
    Row(
        modifier = modifier
            .testTag("LaunchItem")
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = shape,
            )
            .clip(shape)
            .clickable { onClick.invoke() }
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            error = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .size(48.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(launchDetails.missionPatchImageURL)
                .crossfade(true)
                .error(R.drawable.ic_launcher_foreground)
                .build(),
            contentDescription = stringResource(
                id = R.string.listing_launch_item_image_content_description,
                launchDetails.missionName,
            ),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = launchDetails.rocketName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = launchDetails.missionName,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LaunchItemPreviewDark() {
    val launchDetails = LaunchSummaryUiModel(
            id = "1",
            rocketName = "Falcon 9",
            missionName = "Starlink-15",
            missionPatchImageURL = "",
    )

    FalconTheme(
        darkTheme = true,
    ) {
        LaunchItem(
            launchDetails = launchDetails,
        )
    }
}