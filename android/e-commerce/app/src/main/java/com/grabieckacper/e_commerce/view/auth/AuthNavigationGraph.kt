package com.grabieckacper.e_commerce.view.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.grabieckacper.e_commerce.navigation.Route
import com.grabieckacper.e_commerce.view.auth.login.LoginScreen

@Composable
fun AuthNavigationGraph(
    modifier: Modifier = Modifier,
    onSuccessfulSignIn: () -> Unit
) {
    val backStack = rememberNavBackStack(Route.Auth.Login)

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.Auth.Login> {
                LoginScreen(
                    onSuccessfulSignIn = onSuccessfulSignIn,
                    onSignUpButtonClick = {
                        // TODO: navigate to register screen
                    }
                )
            }
        }
    )
}
