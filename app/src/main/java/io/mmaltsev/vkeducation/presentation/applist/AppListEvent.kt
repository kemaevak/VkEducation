package io.mmaltsev.vkeducation.presentation.applist

sealed interface AppListEvent {
    data class ShowSnackbar(val appName: String) : AppListEvent
}
