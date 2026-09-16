package com.swipto.sample.data

import com.swipto.core.Outcome
import com.swipto.data.SwiptoRepository
import com.swipto.data.repositoryCall
import kotlinx.coroutines.delay

data class SampleItem(
    val id: String,
    val title: String,
    val description: String,
)

interface SampleRepository : SwiptoRepository {
    suspend fun items(): Outcome<List<SampleItem>>
    suspend fun item(id: String): Outcome<SampleItem>
}

class FakeSampleRepository : SampleRepository {
    private val catalog = listOf(
        SampleItem("1", "Welcome to Swipto", "Opinionated Kotlin Android kit"),
        SampleItem("2", "Compose screens", "Write against SwiptoScreen + ViewModel"),
        SampleItem("3", "Fast builds", "Convention plugins + thin modules"),
    )

    override suspend fun items(): Outcome<List<SampleItem>> = repositoryCall {
        delay(400)
        catalog
    }

    override suspend fun item(id: String): Outcome<SampleItem> = repositoryCall {
        delay(250)
        catalog.first { it.id == id }
    }
}
