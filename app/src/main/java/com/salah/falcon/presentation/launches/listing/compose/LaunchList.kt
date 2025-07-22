package com.salah.falcon.presentation.launches.listing.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.salah.falcon.R
import com.salah.falcon.core.designsystem.compose.ErrorView
import com.salah.falcon.core.designsystem.compose.PagingProgressView
import com.salah.falcon.core.error.DataErrorException
import com.salah.falcon.presentation.launches.models.LaunchSummaryUiModel
import com.salah.falcon.presentation.util.extensions.toUiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchList(
    list: LazyPagingItems<LaunchSummaryUiModel>,
    scaffoldPaddings: PaddingValues,
    scrollBehavior: TopAppBarScrollBehavior,
    onItemClick: (LaunchSummaryUiModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = scaffoldPaddings
    ) {
        items(list.itemCount) { index ->
            list[index]?.let { launch ->
                LaunchItem(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    launchDetails = launch,
                    onClick = { onItemClick(launch) }
                )
            }
        }

        if (list.loadState.append is LoadState.Loading) {
            item { PagingProgressView() }
        }

        if (list.loadState.append is LoadState.Error) {
            val errorState = list.loadState.append as LoadState.Error
            val uiText = (errorState.error as? DataErrorException)?.error?.toUiText()
            item {
                val errorMessage = uiText?.asString() ?: stringResource(R.string.unexpected_error)
                ErrorView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = errorMessage,
                    onRetry = { list.retry() }
                )
            }
        }
    }
}
