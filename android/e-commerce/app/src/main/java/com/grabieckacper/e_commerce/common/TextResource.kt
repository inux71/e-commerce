package com.grabieckacper.e_commerce.common

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class TextResource {
    data object Empty : TextResource()

    class StringResource(
        @field:StringRes val id: Int,
        vararg val args: Any
    ) : TextResource()

    data class Text(val text: String) : TextResource()

    @Composable
    fun asString(): String {
        return when(this) {
            is Empty -> ""
            is StringResource -> stringResource(id = id, *args)
            is Text -> text
        }
    }
}
