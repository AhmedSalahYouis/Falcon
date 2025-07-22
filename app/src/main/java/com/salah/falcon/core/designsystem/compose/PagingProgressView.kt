package com.salah.falcon.core.designsystem.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PagingProgressView() {
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
