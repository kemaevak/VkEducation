package io.mmaltsev.vkeducation.presentation.applist

import androidx.compose.runtime.Immutable
import io.mmaltsev.vkeducation.domain.applist.AppSummary

@Immutable
sealed interface AppListState {
    data class Content(val apps: List<AppSummary>) : AppListState
}
