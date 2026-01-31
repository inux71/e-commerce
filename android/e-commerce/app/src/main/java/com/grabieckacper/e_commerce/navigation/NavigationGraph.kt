package com.grabieckacper.e_commerce.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.grabieckacper.e_commerce.view.home.HomeScreen
import com.grabieckacper.e_commerce.view.settings.SettingsScreen

@Composable
fun NavigationGraph(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Route.Home)

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.Home> {
                HomeScreen(
                    onCartButtonClick = {},
                    onSettingsButtonClick = {
                        backStack.add(Route.Settings)
                    },
                    onSignOutButtonClick = {}
                )
            }
            entry<Route.Settings> {
                SettingsScreen(
                    onGoBackButtonClick = {
                        backStack.remove(Route.Settings)
                    }
                )
            }
        }
    )
}
