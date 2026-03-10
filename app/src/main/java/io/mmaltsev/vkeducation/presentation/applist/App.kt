package io.mmaltsev.vkeducation.presentation.applist

import io.mmaltsev.vkeducation.domain.appdetails.Category

data class App(
    val id: String,
    val name: String,
    val developer: String,
    val category: Category,
    val rating: Float,
    val iconUrl: String,
)