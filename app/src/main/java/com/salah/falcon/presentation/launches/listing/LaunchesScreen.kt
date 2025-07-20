package com.salah.falcon.presentation.launches.listing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.salah.falcon.R
import com.salah.falcon.core.designsystem.theme.FalconTheme
import com.salah.falcon.domain.model.LaunchSummary
import com.salah.falcon.domain.model.Mission
import com.salah.falcon.presentation.ConsumeSideEffect
import com.salah.falcon.presentation.compose.ErrorView
import com.salah.falcon.presentation.compose.ProgressView
import com.salah.falcon.presentation.launches.listing.compose.LaunchItem
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun LaunchesScreen(
    viewModel: LaunchesViewModel = koinViewModel(),
) {
    val lazyLaunchesList =
        viewModel.uiStateFlow.collectAsStateWithLifecycle().value.launches.collectAsLazyPagingItems()

    // Retry logic is coupled with the lazyPagingItems instance, so we handle the side effect here.
    ConsumeSideEffect(viewModel = viewModel) { effect ->
        when (effect) {
            is LaunchesViewModel.SideEffect.Retry -> lazyLaunchesList.retry()
        }
    }

    val state by viewModel.uiStateFlow.collectAsStateWithLifecycle()
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        val scrollBehavior =
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        Scaffold(
            topBar = {
                MediumTopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    title = {
                        Text(stringResource(id = R.string.launches_screen_title))
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
        ) { scaffoldPaddings ->

            if (lazyLaunchesList.loadState.refresh is LoadState.Loading) {
                ProgressView(
                    modifier = Modifier.padding(scaffoldPaddings),
                )
            }
            LazyColumn(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = scaffoldPaddings
            ) {
                items(lazyLaunchesList.itemCount) { index ->
                    val launch = lazyLaunchesList[index]
                    launch?.let {
                        LaunchItem(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            launchDetails = launch,
                            onClick = {
                                val action = LaunchesViewModel.Action.OnLaunchClick(launch.id)
                                onAction(action)
                            }
                        )
                    }
                }
                if (lazyLaunchesList.loadState.append is LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .requiredHeight(80.dp)
                                .padding(16.dp)
                                .wrapContentHeight()
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            strokeWidth = 4.5.dp
                        )
                    }
                }
                if (lazyLaunchesList.loadState.append is LoadState.Error) {
                    val errorState = lazyLaunchesList.loadState.append as LoadState.Error
                    val errorMessage =
                        errorState.error.localizedMessage
                    item {
                        ErrorView(
                            modifier = Modifier.padding(scaffoldPaddings),
                            errorMessage ?: stringResource(R.string.unknow_error)
                        ) {
                            onAction(LaunchesViewModel.Action.OnRetryClick)
                        }
                    }
                }
            }

            if (lazyLaunchesList.loadState.refresh is LoadState.Error) {
                val errorState = lazyLaunchesList.loadState.refresh as LoadState.Error
                val errorMessage =
                    errorState.error.localizedMessage ?: stringResource(R.string.unknow_error)
                ErrorView(
                    modifier = Modifier.padding(scaffoldPaddings),
                    errorMessage
                ) {

                    onAction(LaunchesViewModel.Action.OnRetryClick)
                }
            }
        }
    }
}


@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ContentPreviewLight() {
    val launches = listOf(
        LaunchSummary(
            id = "1",
            rocketName = "Falcon 9",
            mission = Mission(
                name = "Starlink-15",
                imageUrl = ""
            )
        ),
        LaunchSummary(
            id = "2",
            rocketName = "Falcon 1",
            mission = Mission(
                name = "RazaKSat",
                imageUrl = ""
            )
        )
    )

    FalconTheme(
        darkTheme = false,
    ) {
        Content(
            state = LaunchesUiState(
                isLoading = false,
                error = null,
                launches = flowOf(PagingData.from(launches)),
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ContentPreviewDark() {
    val launches = listOf(
        LaunchSummary(
            id = "1",
            rocketName = "Falcon 9",
            mission = Mission(
                name = "Starlink-15",
                imageUrl = ""
            )
        ),
        LaunchSummary(
            id = "2",
            rocketName = "Falcon 1",
            mission = Mission(
                name = "RazaKSat",
                imageUrl = ""
            )
        )
    )
    FalconTheme(darkTheme = true) {
        Content(
            state = LaunchesUiState(
                isLoading = false, error = null, launches = flowOf(PagingData.from(launches))
            ),
            onAction = {}
        )
    }
}

