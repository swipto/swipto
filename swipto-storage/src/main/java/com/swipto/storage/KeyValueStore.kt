package com.swipto.storage

import kotlinx.coroutines.flow.StateFlow

/** Typed asynchronous key-value storage contract; implementations may use DataStore or encrypted storage. */
interface KeyValueStore {
    fun string(key: PreferenceKey<String>): StateFlow<String?>
    suspend fun put(key: PreferenceKey<String>, value: String?)
    fun boolean(key: PreferenceKey<Boolean>): StateFlow<Boolean?>
    suspend fun put(key: PreferenceKey<Boolean>, value: Boolean?)
}

data class PreferenceKey<T>(val name: String)

fun stringPreference(name: String) = PreferenceKey<String>(name)
fun booleanPreference(name: String) = PreferenceKey<Boolean>(name)
