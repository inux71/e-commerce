package com.grabieckacper.e_commerce.view.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit),
    content: @Composable (() -> Unit)
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        label()
        content()
    }
}

@Composable
@Preview
fun SettingsItemPreview() {
    SettingsItem(label = {
        Text(text = "Dark Theme")
    }) {
        Switch(
            checked = true,
            onCheckedChange = {}
        )
    }
}
