package io.mmaltsev.vkeducation.data.applist

import io.mmaltsev.vkeducation.domain.appdetails.Category
import io.mmaltsev.vkeducation.domain.applist.AppSummary
import kotlinx.serialization.json.Json

class AppListMapper {

    private val json = Json { ignoreUnknownKeys = true }

    fun toDomain(dto: AppSummaryDto): AppSummary = AppSummary(
        id = dto.id,
        name = dto.name,
        developer = dto.developer,
        category = json.decodeFromString<Category>("\"${dto.category}\""),
        rating = dto.rating.toFloat(),
        iconUrl = dto.iconUrl,
    )
}
