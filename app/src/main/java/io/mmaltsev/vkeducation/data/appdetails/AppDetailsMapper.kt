package io.mmaltsev.vkeducation.data.appdetails

import io.mmaltsev.vkeducation.domain.appdetails.AppDetails
import javax.inject.Inject

class AppDetailsMapper @Inject constructor() {
    fun toDomain(dto: AppDetailsDto): AppDetails = AppDetails(
        id = dto.id,
        name = dto.name,
        developer = dto.developer,
        category = dto.category,
        ageRating = dto.ageRating,
        size = dto.size.toFloat(),
        iconUrl = dto.iconUrl,
        screenshotUrlList = dto.screenshotUrlList,
        description = dto.description,
    )
}
