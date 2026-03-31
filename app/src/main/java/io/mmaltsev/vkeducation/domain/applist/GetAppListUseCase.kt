package io.mmaltsev.vkeducation.domain.applist

class GetAppListUseCase(
    private val repository: AppListRepository,
) {
    suspend operator fun invoke(): List<AppSummary> = repository.getAppList()
}
