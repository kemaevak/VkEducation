package io.mmaltsev.vkeducation.data.applist

import io.mmaltsev.vkeducation.domain.applist.AppSummary
import javax.inject.Inject

class AppListMapper @Inject constructor() {

    fun toDomain(dto: AppSummaryDto): AppSummary = AppSummary(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        category = dto.category,
        iconUrl = dto.iconUrl,
    )
}
