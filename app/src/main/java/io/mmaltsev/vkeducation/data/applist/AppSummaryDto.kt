package io.mmaltsev.vkeducation.data.applist

import kotlinx.serialization.Serializable

@Serializable
data class AppSummaryDto(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String,
)
