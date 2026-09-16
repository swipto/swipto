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
import com.swipto.style.layoutStyleOrNull
import com.swipto.style.style

@Composable
fun AppColumn(
    modifier: Modifier = Modifier,
    style: Style? = null,
    content: @Composable () -> Unit,
) {
    val layout = style?.layoutStyleOrNull()
    Column(
        modifier = modifier.style(style),
        verticalArrangement = Arrangement.spacedBy(layout?.spacing ?: 8.dp),
        horizontalAlignment = (layout?.alignment as? androidx.compose.ui.Alignment.Horizontal)
            ?: androidx.compose.ui.Alignment.Start,
        content = content,
    )
}

@Composable
fun AppText(text: String, modifier: Modifier = Modifier, style: Style? = null) = Text(
    text = text,
    modifier = modifier.style(style),
    style = style?.textStyleOrNull() ?: androidx.compose.ui.text.TextStyle.Default,
)

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: Style? = null,
) = Button(onClick = onClick, modifier = modifier.style(style), enabled = enabled) { Text(text) }

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
