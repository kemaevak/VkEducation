package io.mmaltsev.vkeducation.data.applist

import kotlinx.serialization.Serializable

@Serializable
data class AppSummaryDto(
    val id: String,
    val name: String,
    val developer: String,
    val category: String,
    val rating: Double,
    val iconUrl: String,
)
