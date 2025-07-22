package com.salah.falcon.core.designsystem.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.salah.falcon.R


@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    text: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = text,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = onRetry,
        ) {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.retry_button_label),
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ErrorPreview() {
    ErrorView(
        text = "preview error message, wave",
        onRetry = {},
    )
}
