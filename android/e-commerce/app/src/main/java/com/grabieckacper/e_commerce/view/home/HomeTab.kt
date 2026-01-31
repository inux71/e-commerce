package com.grabieckacper.e_commerce.view.home

import androidx.annotation.DrawableRes
import com.grabieckacper.e_commerce.R

enum class HomeTab(
    val title: String,
    @field:DrawableRes val id: Int
) {
    PRODUCTS(
        title = "Products",
        id = R.drawable.list_24
    ),
    ACCOUNT(
        title = "Account",
        id = R.drawable.account_circle_24
    )
}
