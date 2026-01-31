package com.grabieckacper.e_commerce.view.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.grabieckacper.e_commerce.R
import com.grabieckacper.e_commerce.view.settings.components.SettingsItem

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onGoBackButtonClick: () -> Unit
) {
    val state: SettingsViewModel.SettingsState = viewModel.state.value

    SettingsContent(
        onGoBackButtonClick = onGoBackButtonClick,
        darkTheme = state.darkTheme,
        dynamicColor = state.dynamicColor,
        onDarkThemeChange = viewModel::updateDarkTheme,
        onDynamicColorChange = viewModel::updateDynamicColor
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SettingsContent(
    onGoBackButtonClick: () -> Unit,
    darkTheme: Boolean,
    dynamicColor: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onDynamicColorChange: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.settings_title))
                },
                navigationIcon = {
                    IconButton(onClick = onGoBackButtonClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_24),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SettingsItem(label = {
                Text(text = stringResource(id = R.string.dark_theme_label))
            }) {
                Switch(
                    checked = darkTheme,
                    onCheckedChange = onDarkThemeChange,
                    thumbContent = {
                        if (darkTheme) {
                            Icon(
                                painter = painterResource(id = R.drawable.check_24),
                                contentDescription = null
                            )
                        }
                    }
                )
            }

            SettingsItem(label = {
                Text(text = stringResource(id = R.string.dynamic_color_label))
            }) {
                Switch(
                    checked = dynamicColor,
                    onCheckedChange = onDynamicColorChange,
                    thumbContent = {
                        if (dynamicColor) {
                            Icon(
                                painter = painterResource(id = R.drawable.check_24),
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun SettingsScreenPreview() {
    SettingsContent(
        onGoBackButtonClick = {},
        darkTheme = true,
        dynamicColor = false,
        onDarkThemeChange = {},
        onDynamicColorChange = {}
    )
}
