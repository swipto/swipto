package com.swipto.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/** State for a single form field. Validation remains ordinary Kotlin. */
class FormFieldState(initialValue: String, private val validator: (String) -> String? = { null }) {
    var value by mutableStateOf(initialValue)
        private set
    var touched by mutableStateOf(false)
        private set

    val error: String? get() = if (touched) validator(value) else null
    val isValid: Boolean get() = validator(value) == null

    fun update(value: String) {
        this.value = value
        touched = true
    }
}

@Composable
fun rememberFormField(
    initialValue: String = "",
    validator: (String) -> String? = { null },
): FormFieldState = remember { FormFieldState(initialValue, validator) }

@Composable
fun FormTextField(field: FormFieldState, label: String) {
    AppTextField(value = field.value, onValueChange = field::update, label = label, error = field.error)
}
