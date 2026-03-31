package io.mmaltsev.vkeducation.domain.applist

import io.mmaltsev.vkeducation.domain.appdetails.Category

data class AppSummary(
    val id: String,
    val name: String,
    val developer: String,
    val category: Category,
    val rating: Float,
    val iconUrl: String,
)
