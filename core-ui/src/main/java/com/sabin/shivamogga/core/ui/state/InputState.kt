package com.sabin.shivamogga.core.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.regex.Pattern


open class TextFieldState(
    private var label: String? = null,
    val maxLength: Int = -1,
    val minLength: Int = -1,
    private val isOptional: Boolean? = false,
    private val validator: (String) -> Boolean = { true },
    private val errorFor: (String?) -> String = { "" },
) {

    var text: String by mutableStateOf("")

    // was the TextField ever focused

    var isFocusedDirty: Boolean by mutableStateOf(false)


    var isFocused: Boolean by mutableStateOf(false)


    private var displayErrors: Boolean by mutableStateOf(false)

    open val isValid: Boolean
        get() {
            if (maxLength > 0) {
                if (text.length > maxLength)
                    return false
            }
            if (minLength > 0) {
                if (text.length < minLength)
                    return false
            }
            if (isOptional == true && text.isEmpty()) {
                return true
            }
            return validator(text)
        }

    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    fun setFieldLabel(value: String) {
        this.label = value
    }

    fun enableShowErrors() {
        // only show errors if the text was at least once focused
        if (isFocusedDirty) displayErrors = true
    }

    fun clearErrors() {
        displayErrors = false
        getError()
    }

    fun showErrors() = !isValid && displayErrors

    open fun getError(): String? {
        return if (showErrors()) {
            errorFor(label)
        } else
            null
    }
}

open class RegexTextFieldState(
    label: String? = null,
    isOptional: Boolean? = false,
    private val validator: (String, String) -> Boolean = { _, _ -> true },
    errorFor: (String?) -> String = { "" },
    var regex: String? = null,
    maxLen: Int = -1,
    minLen: Int = -1,
    defaultValidator: (String) -> Boolean = ::fieldValid,
) : TextFieldState(
    label = label,
    isOptional = isOptional,
    validator = defaultValidator,
    errorFor = errorFor,
    maxLength = maxLen,
    minLength = minLen,
) {
    override val isValid: Boolean
        get() {
            val value = super.isValid
            return if (value) {
                regex?.let {
                    if (it.isNotEmpty()) validator(super.text, it) else true
                } ?: run { true }
            } else {
                false
            }
        }
}


class PhoneNumberState(
    label: String? = null, maxLength: Int = -1, minLength: Int = -1,
    var isOptional: Boolean = false,
) :
    TextFieldState(
        label = label,
        maxLength = maxLength,
        minLength = minLength,
        isOptional = isOptional,
        validator = ::isPhoneValid,
        errorFor = ::commonErrorMessage
    )

class InputTextState(
    label: String? = null,
    maxLength: Int = 0,
    minLength: Int = 0,
    optional: Boolean? = false,
) :
    TextFieldState(
        label = label,
        maxLength = maxLength,
        minLength = minLength,
        isOptional = optional,
        validator = ::fieldValid,
        errorFor = ::commonErrorMessage
    )


const val PHONE_VALIDATION_REGEX = "[6-9]{1}\\d{9}\$"

private fun isPhoneValid(phone: String): Boolean = Pattern.matches(PHONE_VALIDATION_REGEX, phone)


/**
 * Returns an error to be displayed or null if no error was found
 */

private fun commonErrorMessage(value: String?): String =
    "Please enter valid ${value?.lowercase() ?: "Input"}"

private fun fieldValid(value: String?): Boolean {
    return !value.isNullOrEmpty()
}
