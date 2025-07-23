package com.salah.falcon.presentation.launches.launchdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.salah.falcon.R
import com.salah.falcon.core.designsystem.compose.ErrorView
import com.salah.falcon.core.designsystem.compose.ProgressView
import com.salah.falcon.core.designsystem.theme.FalconTheme
import com.salah.falcon.presentation.ConsumeSideEffect
import com.salah.falcon.presentation.launches.launchdetails.compose.MissionDetailsSection
import com.salah.falcon.presentation.launches.launchdetails.compose.RocketDetailsSection
import com.salah.falcon.presentation.launches.launchdetails.compose.SiteDetailsSection
import com.salah.falcon.presentation.launches.models.LaunchDetailsUiModel
import com.salah.falcon.presentation.launches.models.MissionUiModel
import com.salah.falcon.presentation.launches.models.RocketUiModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.platform.testTag

@Composable
fun LaunchDetailsScreen(
    viewModel: LaunchDetailsViewModel = koinViewModel(),
    launchId: String,
    navigateBack: () -> Unit,
) {
    ConsumeSideEffect(viewModel = viewModel) { sideEffect ->
        when (sideEffect) {
            is LaunchDetailsViewModel.SideEffect.NavigateBack -> {
                navigateBack()
            }
        }
    }

    LaunchedEffect(launchId) {
        viewModel.onArgsReceived(
            launchId = launchId,
        )
    }

    val state by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    Content(
        modifier = Modifier.testTag("LaunchDetailsScreen"),
        state = state,
        onAction = viewModel::handleAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
    state: LaunchDetailsUiState,
    onAction: (LaunchDetailsViewModel.Action) -> Unit,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                    title = {
                        Text(
                            stringResource(
                                id = R.string.title,
                                state.launchId,
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onAction(LaunchDetailsViewModel.Action.OnBackClick)
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Navigate back")
                        }
                    },
                )
            },
        ) { scaffoldPaddings ->
            when {
                state.isLoading -> ProgressView(
                    modifier = Modifier.padding(scaffoldPaddings),
                )

                state.error != null -> ErrorView(
                    modifier = Modifier.padding(scaffoldPaddings),
                    text = state.error.asString(),
                    onRetry = { onAction(LaunchDetailsViewModel.Action.OnRetryClick) },
                )

                state.details != null -> LaunchDetails(
                    modifier = Modifier.padding(scaffoldPaddings),
                    launchDetails = state.details
                )
            }
        }
    }
}

@Composable
private fun LaunchDetails(
    modifier: Modifier = Modifier,
    launchDetails: LaunchDetailsUiModel,
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .height(154.dp)
                .aspectRatio(1f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(launchDetails.mission.imageUrl)
                .crossfade(true)
                .error(R.drawable.ic_launcher_foreground)
                .build(),
            contentDescription = stringResource(
                id = R.string.mission_image_content_description,
                launchDetails.id,
            ),
        )

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth(),
        ) {
            Spacer(Modifier.height(16.dp))

            RocketDetailsSection(rocket = launchDetails.rocket)
            Spacer(Modifier.height(16.dp))

            MissionDetailsSection(mission = launchDetails.mission)
            Spacer(Modifier.height(16.dp))

            SiteDetailsSection(site = launchDetails.site)
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
private fun ContentPreviewLight() {
    FalconTheme(
        darkTheme = false,
    ) {
        val launchId = "123"
        val launchDetails = LaunchDetailsUiModel(
            id = launchId,
            rocket = RocketUiModel(
                id = "RockerId",
                name = "Falcon Heavy",
                type = "SFK-2",
            ),
            mission = MissionUiModel(
                name = "StarLink",
                imageUrl = "https://images2.imgbox.com/b5/1d/46Eo0yuu_o.png",
            ),
            site = "CCAFS SLC 40",
        )
        val state = LaunchDetailsUiState(
            launchId = launchId,
            isLoading = false,
            error = null,
            details = launchDetails,
        )
        Content(
            state = state,
            onAction = {}
        )
    }
}
