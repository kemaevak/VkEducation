package io.mmaltsev.vkeducation.presentation.applist

import androidx.compose.runtime.Immutable

@Immutable
sealed interface AppListState {
    data class Content(val apps: List<App>) : AppListState
}
