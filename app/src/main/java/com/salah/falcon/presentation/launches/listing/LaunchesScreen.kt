package com.salah.falcon.presentation.launches.listing

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.salah.falcon.R
import com.salah.falcon.core.designsystem.compose.EmptyView
import com.salah.falcon.core.designsystem.compose.ErrorView
import com.salah.falcon.core.designsystem.compose.ProgressView
import com.salah.falcon.core.designsystem.theme.FalconTheme
import com.salah.falcon.core.error.DataErrorException
import com.salah.falcon.presentation.ConsumeSideEffect
import com.salah.falcon.presentation.launches.listing.compose.LaunchList
import com.salah.falcon.presentation.launches.models.LaunchSummaryUiModel
import com.salah.falcon.presentation.util.extensions.toUiText
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun LaunchesScreen(
    viewModel: LaunchesViewModel = koinViewModel(),
) {
    val state by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    ConsumeSideEffect(viewModel = viewModel) { effect ->

    }

    Content(
        state = state,
        onAction = viewModel::handleAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    state: LaunchesUiState,
    onAction: (LaunchesViewModel.Action) -> Unit
) {
    val lazyLaunchesList = state.launches.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = {
                MediumTopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    title = { Text(stringResource(id = R.string.launches_screen_title)) },
                    scrollBehavior = scrollBehavior,
                )
            },
        ) { scaffoldPaddings ->

            when {
                lazyLaunchesList.loadState.refresh is LoadState.Loading -> {
                    ProgressView(modifier = Modifier.padding(scaffoldPaddings))
                }

                lazyLaunchesList.loadState.refresh is LoadState.Error -> {
                    val error = lazyLaunchesList.loadState.refresh as LoadState.Error
                    val errorMessage = (error.error as? DataErrorException)?.error?.toUiText()?.asString()
                        ?: stringResource(R.string.unexpected_error)

                    ErrorView(
                        modifier = Modifier.padding(scaffoldPaddings),
                        text = errorMessage,
                        onRetry = { lazyLaunchesList.retry() }
                    )
                }

                lazyLaunchesList.itemCount == 0 -> {
                    EmptyView(
                        modifier = Modifier.padding(scaffoldPaddings),
                        text = stringResource(id = R.string.empty_launches_message),
                    )
                }

                else -> {
                    LaunchList(
                        list = lazyLaunchesList,
                        scaffoldPaddings = scaffoldPaddings,
                        scrollBehavior = scrollBehavior,
                        onItemClick = {
                            onAction(LaunchesViewModel.Action.OnLaunchClick(it.id))
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ContentPreviewLight() {
    val launches = listOf(
        LaunchSummaryUiModel(
            id = "1",
            rocketName = "Falcon 9",
            missionName = "Starlink-15",
            missionPatchImageURL = "",
        ),
        LaunchSummaryUiModel(
            id = "2",
            rocketName = "Falcon 1",
            missionName = "Starlink-15",
            missionPatchImageURL = "",
        )
    )

    FalconTheme(
        darkTheme = false,/*true to preview dark theme*/
    ) {
        Content(
            state = LaunchesUiState(
                launches = flowOf(PagingData.from(launches)),
            ),
            onAction = {}
        )
    }
}

