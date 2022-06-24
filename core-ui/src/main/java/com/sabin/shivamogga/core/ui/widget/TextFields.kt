package com.sabin.shivamogga.core.ui.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sabin.shivamogga.core.ui.state.InputTextState
import com.sabin.shivamogga.core.ui.state.TextFieldState


@Composable
fun AppOutLinedEditText(
    modifier: Modifier = Modifier,
    fieldState: TextFieldState = remember { InputTextState() },
    label: String,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    focused: Boolean = false,
    helperText: String? = null,
    forceError: Boolean = false,
    isReadOnly: Boolean = false,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    onImeAction: () -> Unit = {},
    onNextImeAction: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {},
) {
    val requester = FocusRequester()
    fieldState.setFieldLabel(label)
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = fieldState.text,
        onValueChange = {
            var valid = true
            if (fieldState.maxLength > 0) {
                if (fieldState.maxLength <= fieldState.text.length) valid = false
            }

            if (fieldState.text.length > it.length) valid = true
            if (valid) fieldState.text = it
            onValueChange(fieldState.text)
        },
        label = { Text(text = label) },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(requester)
            .onFocusChanged { focusState ->
                val isFocused = focusState.isFocused
                fieldState.onFocusChange(isFocused)
                onFocusChanged(isFocused)
                if (!isFocused) {
                    fieldState.enableShowErrors()
                }
            },
        readOnly = isReadOnly,
        maxLines = 1,
        isError = fieldState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = keyboardType,
            capitalization = capitalization
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onImeAction()
            },
            onNext = {
                focusManager.moveFocus(focusDirection = FocusDirection.Down)
                fieldState.onFocusChange(false)
                onNextImeAction()
            }
        )
    )
    // Request focus as a SideEffect (after the composition)
    SideEffect {
        if (focused) {
            requester.requestFocus()
        }
    }
    fieldState.getError()
        ?.let { error ->
            TextFieldError(textError = error)
        }
    helperText?.let {
        Text(
            text = it,
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.95f),
        )
    }
    if (forceError) {
        fieldState.isFocusedDirty = true
        fieldState.enableShowErrors()
        fieldState.showErrors()
    }
}

/**
 * To be removed when [OutlinedTextField]s support error
 */
@Composable
fun TextFieldError(
    textError: String,
    padding: PaddingValues = PaddingValues(0.dp)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.error)
        )

    }
}
