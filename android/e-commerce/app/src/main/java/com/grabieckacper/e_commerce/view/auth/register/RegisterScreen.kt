package com.grabieckacper.e_commerce.view.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.grabieckacper.e_commerce.R
import com.grabieckacper.e_commerce.common.TextResource

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onSuccessfulSignUp: () -> Unit,
    onSignInButtonClick: () -> Unit
) {
    val state: RegisterViewModel.RegisterState = viewModel.state.value

    LaunchedEffect(key1 = state.signedUp) {
        if (state.signedUp) {
            onSuccessfulSignUp()
        }
    }

    RegisterContent(
        isLoading = state.isLoading,
        error = state.error,
        firstname = state.firstname,
        onFirstnameChange = viewModel::updateFirstname,
        clearFirstname = viewModel::clearFirstname,
        firstnameSupportingText = state.firstnameSupportingText,
        firstnameError = state.firstnameError,
        lastname = state.lastname,
        onLastnameChange = viewModel::updateLastname,
        clearLastname = viewModel::clearLastname,
        lastnameSupportingText = state.lastnameSupportingText,
        lastnameError = state.lastnameError,
        email = state.email,
        onEmailChange = viewModel::updateEmail,
        clearEmail = viewModel::clearEmail,
        emailSupportingText = state.emailSupportingText,
        emailError = state.emailError,
        password = state.password,
        onPasswordChange = viewModel::updatePassword,
        passwordVisible = state.passwordVisible,
        changePasswordVisibility = viewModel::updatePasswordVisibility,
        passwordSupportingText = state.passwordSupportingText,
        passwordError = state.passwordError,
        onSignUpButtonClick = viewModel::signUp,
        onSignInButtonClick = onSignInButtonClick
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun RegisterContent(
    isLoading: Boolean,
    error: TextResource,
    firstname: String,
    onFirstnameChange: (String) -> Unit,
    clearFirstname: () -> Unit,
    firstnameSupportingText: TextResource,
    firstnameError: Boolean,
    lastname: String,
    onLastnameChange: (String) -> Unit,
    clearLastname: () -> Unit,
    lastnameSupportingText: TextResource,
    lastnameError: Boolean,
    email: String,
    onEmailChange: (String) -> Unit,
    clearEmail: () -> Unit,
    emailSupportingText: TextResource,
    emailError: Boolean,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    changePasswordVisibility: () -> Unit,
    passwordSupportingText: TextResource,
    passwordError: Boolean,
    onSignUpButtonClick: () -> Unit,
    onSignInButtonClick: () -> Unit
) {
    val focusManager: FocusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = error) {
        when (error) {
            is TextResource.Text -> {
                snackbarHostState.showSnackbar(
                    message = error.text,
                    duration = SnackbarDuration.Short
                )
            }
            else -> Unit
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.register_title))
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.register_header),
                    style = MaterialTheme.typography.titleLarge
                )

                OutlinedTextField(
                    value = firstname,
                    onValueChange = onFirstnameChange,
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                    label = {
                        Text(text = stringResource(id = R.string.firstname_label))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.firstname_placeholder))
                    },
                    trailingIcon = {
                        if (firstname.isNotEmpty()) {
                            IconButton(
                                onClick = clearFirstname
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.close_24),
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    supportingText = {
                        Text(text = firstnameSupportingText.asString())
                    },
                    isError = firstnameError,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrectEnabled = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        showKeyboardOnFocus = true
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(focusDirection = FocusDirection.Next)
                    }),
                    singleLine = true
                )

                OutlinedTextField(
                    value = lastname,
                    onValueChange = onLastnameChange,
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                    label = {
                        Text(text = stringResource(id = R.string.lastname_label))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.lastname_placeholder))
                    },
                    trailingIcon = {
                        if (lastname.isNotEmpty()) {
                            IconButton(
                                onClick = clearLastname
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.close_24),
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    supportingText = {
                        Text(text = lastnameSupportingText.asString())
                    },
                    isError = lastnameError,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrectEnabled = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        showKeyboardOnFocus = true
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(focusDirection = FocusDirection.Next)
                    }),
                    singleLine = true
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                    label = {
                        Text(text = stringResource(id = R.string.email_label))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.email_placeholder))
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.mail_24),
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (email.isNotEmpty()) {
                            IconButton(
                                onClick = clearEmail
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.close_24),
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    supportingText = {
                        Text(text = emailSupportingText.asString())
                    },
                    isError = emailError,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrectEnabled = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        showKeyboardOnFocus = true
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(focusDirection = FocusDirection.Next)
                    }),
                    singleLine = true
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                    label = {
                        Text(text = stringResource(id = R.string.password_label))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.password_placeholder))
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.password_24),
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (passwordVisible) {
                            IconButton(onClick = changePasswordVisibility) {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibility_24),
                                    contentDescription = null
                                )
                            }
                        } else {
                            IconButton(onClick = changePasswordVisibility) {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibility_off_24),
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    supportingText = {
                        Text(text = passwordSupportingText.asString())
                    },
                    isError = passwordError,
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrectEnabled = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        showKeyboardOnFocus = true
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    singleLine = true
                )

                Button(
                    onClick = onSignUpButtonClick,
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                    enabled = !(firstnameError || lastnameError || emailError || passwordError)
                ) {
                    Text(text = stringResource(id = R.string.register_button))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.account_question))

                    TextButton(onClick = onSignInButtonClick) {
                        Text(text = stringResource(id = R.string.login_button))
                    }
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
@Preview
fun RegisterScreenPreview() {
    RegisterContent(
        isLoading = false,
        error = TextResource.Empty,
        firstname = "",
        onFirstnameChange = {},
        clearFirstname = {},
        firstnameSupportingText = TextResource.Empty,
        firstnameError = false,
        lastname = "",
        onLastnameChange = {},
        clearLastname = {},
        lastnameSupportingText = TextResource.Empty,
        lastnameError = false,
        email = "",
        onEmailChange = {},
        clearEmail = {},
        emailSupportingText = TextResource.Empty,
        emailError = false,
        password = "",
        onPasswordChange = {},
        passwordVisible = false,
        changePasswordVisibility = {},
        passwordSupportingText = TextResource.Empty,
        passwordError = false,
        onSignUpButtonClick = {},
        onSignInButtonClick = {}
    )
}
