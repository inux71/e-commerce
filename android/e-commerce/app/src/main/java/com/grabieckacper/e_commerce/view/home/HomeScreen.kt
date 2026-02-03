package com.grabieckacper.e_commerce.view.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.grabieckacper.e_commerce.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onCartButtonClick: () -> Unit,
    onSettingsButtonClick: () -> Unit,
    onSignOutButtonClick: () -> Unit
) {
    HomeContent(
        onCartButtonClick = onCartButtonClick,
        onSettingsButtonClick = onSettingsButtonClick,
        onSignOutButtonClick = {
            viewModel.signOut()
            onSignOutButtonClick()
        },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeContent(
    onCartButtonClick: () -> Unit,
    onSettingsButtonClick: () -> Unit,
    onSignOutButtonClick: () -> Unit
) {
    val startHomeTab: HomeTab = HomeTab.PRODUCTS
    var selectedHomeTab by rememberSaveable { mutableStateOf(startHomeTab) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = selectedHomeTab.title)
                },
                actions = {
                    IconButton(onClick = onSettingsButtonClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings_24),
                            contentDescription = null
                        )
                    }

                    IconButton(onClick = onSignOutButtonClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.logout_24),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                HomeTab.entries.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedHomeTab.ordinal == index,
                        onClick = {
                            selectedHomeTab = tab
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = tab.id),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = tab.title)
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCartButtonClick) {
                Icon(
                    painter = painterResource(id = R.drawable.shopping_cart_24),
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        when (selectedHomeTab) {
            HomeTab.PRODUCTS -> {
                Text(text = "Products", modifier = Modifier.padding(paddingValues = paddingValues))
            }
            HomeTab.ACCOUNT -> {
                Text(text = "Account", modifier = Modifier.padding(paddingValues = paddingValues))
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeContent(
        onCartButtonClick = {},
        onSettingsButtonClick = {},
        onSignOutButtonClick = {}
    )
}
