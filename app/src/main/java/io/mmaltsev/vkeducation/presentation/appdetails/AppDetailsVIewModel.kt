package io.mmaltsev.vkeducation.presentation.appdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mmaltsev.vkeducation.domain.appdetails.AppDetailsRepository
import io.mmaltsev.vkeducation.domain.appdetails.GetAppDetailsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    private val getAppDetailsUseCase: GetAppDetailsUseCase,
    private val appDetailsRepository: AppDetailsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val appId: String = checkNotNull(savedStateHandle["appId"])

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state = _state.asStateFlow()

    private val _events = Channel<AppDetailsEvent>(BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        getAppDetails()
        observeAppDetails()
    }

    fun showUnderDevelopmentMessage() {
        viewModelScope.launch {
            _events.send(AppDetailsEvent.UnderDevelopment)
        }
    }

    fun collapseDescription() {
        _state.update { currentState ->
            if (currentState is AppDetailsState.Content) {
                currentState.copy(descriptionCollapsed = true)
            } else {
                currentState
            }
        }
    }

    fun toggleWishlist() {
        viewModelScope.launch {
            appDetailsRepository.toggleWishlist(appId)
        }
    }

    fun getAppDetails() {
        viewModelScope.launch {
            _state.value = AppDetailsState.Loading
            try {
                getAppDetailsUseCase(appId).catch {
                    _state.value = AppDetailsState.Error
                }.collect {}
            } catch (e: Exception) {
                _state.value = AppDetailsState.Error
            }
        }
    }

    private fun observeAppDetails() {
        viewModelScope.launch {
            appDetailsRepository.observeAppDetails(appId)
                .catch { _state.value = AppDetailsState.Error }
                .collect { appDetails ->
                    _state.value = AppDetailsState.Content(
                        appDetails = appDetails,
                        descriptionCollapsed = false,
                    )
                }
        }
    }
}
