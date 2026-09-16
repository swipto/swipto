package com.swipto.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.swipto.style.Style
import com.swipto.style.style

@Composable
fun AppColumn(
    modifier: Modifier = Modifier,
    style: Style? = null,
    content: @Composable () -> Unit,
) = Column(
    modifier = modifier.style(style),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    content = { content() },
)

@Composable
fun AppText(text: String, modifier: Modifier = Modifier) = Text(text = text, modifier = modifier)

@Composable
fun AppButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) =
    Button(onClick = onClick, modifier = modifier, enabled = enabled) { Text(text) }

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    error: String? = null,
) {
    OutlinedTextField(value, onValueChange, modifier.fillMaxWidth(), label = { Text(label) }, isError = error != null)
    if (error != null) Text(error, color = MaterialTheme.colorScheme.error)
}

@Composable fun LoadingContent(modifier: Modifier = Modifier) = CircularProgressIndicator(modifier)
@Composable fun EmptyContent(message: String, modifier: Modifier = Modifier) = Text(message, modifier)
