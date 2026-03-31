package io.mmaltsev.vkeducation.presentation.applist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mmaltsev.vkeducation.domain.applist.GetAppListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val getAppListUseCase: GetAppListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<AppListState>(AppListState.Content(emptyList()))
    val state = _state.asStateFlow()

    private val _events = Channel<AppListEvent>(BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            val apps = getAppListUseCase()
            _state.value = AppListState.Content(apps)
        }
    }

    fun onAppIconClick(appName: String) {
        viewModelScope.launch {
            _events.send(AppListEvent.ShowSnackbar(appName))
        }
    }
}
