package com.swipto.sample.ui

import com.swipto.core.Outcome
import com.swipto.sample.data.SampleItem
import com.swipto.sample.data.SampleRepository
import com.swipto.ui.SwiptoUiState
import com.swipto.ui.SwiptoViewModel

data class DetailState(
    val item: SampleItem? = null,
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
) : SwiptoUiState

class DetailViewModel(
    private val repository: SampleRepository,
) : SwiptoViewModel<DetailState>(DetailState(isLoading = true)) {

    init {
        load("1")
    }

    fun load(id: String) = launch {
        update { it.copy(isLoading = true, errorMessage = null) }
        when (val result = repository.item(id)) {
            is Outcome.Success -> update {
                it.copy(item = result.value, isLoading = false, errorMessage = null)
            }
            is Outcome.Failure -> update {
                it.copy(
                    isLoading = false,
                    errorMessage = result.error.message ?: "Failed to load item",
                )
            }
        }
    }
}
