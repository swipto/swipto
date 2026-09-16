package com.swipto.sample.ui

import com.swipto.core.Outcome
import com.swipto.sample.data.SampleItem
import com.swipto.sample.data.SampleRepository
import com.swipto.ui.SwiptoUiState
import com.swipto.ui.SwiptoViewModel

data class HomeState(
    val items: List<SampleItem> = emptyList(),
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
) : SwiptoUiState

class HomeViewModel(
    private val repository: SampleRepository,
) : SwiptoViewModel<HomeState>(HomeState(isLoading = true)) {

    init {
        refresh()
    }

    fun refresh() = launch {
        update { it.copy(isLoading = true, errorMessage = null) }
        when (val result = repository.items()) {
            is Outcome.Success -> update {
                it.copy(items = result.value, isLoading = false, errorMessage = null)
            }
            is Outcome.Failure -> update {
                it.copy(
                    isLoading = false,
                    errorMessage = result.error.message ?: "Failed to load items",
                )
            }
        }
    }
}
