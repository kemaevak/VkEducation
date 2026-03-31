package io.mmaltsev.vkeducation.data.applist

import io.mmaltsev.vkeducation.domain.applist.AppListRepository
import io.mmaltsev.vkeducation.domain.applist.AppSummary
import javax.inject.Inject

class AppListRepositoryImpl @Inject constructor(
    private val mapper: AppListMapper,
) : AppListRepository {

    override suspend fun getAppList(): List<AppSummary> {
        return hardcodedApps.map { mapper.toDomain(it) }
    }
}
