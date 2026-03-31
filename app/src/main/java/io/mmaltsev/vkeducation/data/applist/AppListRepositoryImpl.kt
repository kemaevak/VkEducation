package io.mmaltsev.vkeducation.data.applist

import io.mmaltsev.vkeducation.domain.applist.AppListRepository
import io.mmaltsev.vkeducation.domain.applist.AppSummary

class AppListRepositoryImpl(
    private val mapper: AppListMapper,
) : AppListRepository {

    override suspend fun getAppList(): List<AppSummary> {
        return hardcodedApps.map { mapper.toDomain(it) }
    }
}
