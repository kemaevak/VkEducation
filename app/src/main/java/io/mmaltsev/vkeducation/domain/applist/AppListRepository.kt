package io.mmaltsev.vkeducation.domain.applist

interface AppListRepository {
    suspend fun getAppList(): List<AppSummary>
}
