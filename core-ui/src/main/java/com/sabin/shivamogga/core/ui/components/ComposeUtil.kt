package com.sabin.shivamogga.core.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sabin.shivamogga.core.ui.R
import kotlinx.coroutines.flow.Flow

/**
 * Remembers in the Composition a flow that only emits data when `lifecycle` is
 * at least in `minActiveState`. That's achieved using the `Flow.flowWithLifecycle` operator.
 *
 * Explanation: If flows with operators in composable functions are not remembered, operators
 * will be _always_ called and applied on every recomposition.
 */
@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}

/**
 * Support wide screen by making the content width max 840dp, centered horizontally.
 */
fun Modifier.supportWideScreen() = this
    .fillMaxWidth()
    .wrapContentWidth(align = Alignment.CenterHorizontally)
    .widthIn(max = 840.dp)


@Composable
fun FullScreenTransparentLoader(
    isButtonLoader: Boolean = true,
    clickable: Boolean = false,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() } // This is mandatory
            ) {
                if (clickable)
                    onClick()
            }
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
        //.wrapContentSize(Alignment.Center)
    ) {
        if (!isButtonLoader)
            CircularProgressIndicator()
    }
}

@Composable
fun LoadingItem(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    CircularProgressIndicator(
        color = color,
        modifier = modifier
    )
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit,
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Red
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Try again")
        }
    }
}


/**
 * Display an initial empty state or swipe to refresh content.
 *
 * @param empty (state) when true, display [emptyContent]
 * @param emptyContent (slot) the content to display for the empty state
 * @param content (slot) the main content to show
 *
 */
@Composable
fun LoadingContent(
    error: Boolean = false,
    errorContent: @Composable () -> Unit = {},
    empty: Boolean = false,
    emptyContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    when {
        error -> {
            errorContent()
        }
        empty -> {
            emptyContent()
        }
        else -> {
            content()
        }
    }
}


@Composable
fun EmptyContentScreen(
    text: String = stringResource(R.string.text_no_data),
    buttonLabel: String = stringResource(id = R.string.text_retry),
    floatingActionButton: @Composable () -> Unit = {},
    isRetry: Boolean = true,
    onRetry: () -> Unit = {},
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = Icons.Default.ImageNotSupported,
                contentDescription = null,
            )
            VerticalSpacer()
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            if (isRetry) {
                VerticalSpacer()
                Button(onClick = { onRetry() }) {
                    Text(text = buttonLabel)
                }
            }
        }
        floatingActionButton()
    }
}

@Composable
fun EmptyContentScreen(
    text: String = stringResource(R.string.text_no_data),
    label: String = stringResource(id = R.string.text_retry),
    modifier: Modifier,
    isRetry: Boolean = true,
    onRetry: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = Icons.Default.ImageNotSupported,
            contentDescription = null,
        )
        VerticalSpacer()
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        if (isRetry) {
            VerticalSpacer()
            Button(onClick = { onRetry() }) {
                Text(text = label)
            }
        }
    }
}

@Composable
fun HorizontalSpacer(size: Dp = 8.dp) {
    Spacer(modifier = Modifier.width(size))
}

@Composable
fun VerticalSpacer(size: Dp = 8.dp) {
    Spacer(modifier = Modifier.height(size))
}

@Composable
fun ContentBody(content: @Composable () -> Unit) {
    val focusManager = LocalFocusManager.current
    Surface(
        Modifier
            .fillMaxSize()
            .supportWideScreen()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { focusManager.clearFocus() },
                    onDoubleTap = { },
                    onLongPress = { },
                    onTap = { }
                )
            }) {
        content()
    }
}


enum class LoaderType {
    FULLSCREEN_LOADER,
    LINEAR_PROGRESS,
    CIRCULAR_ON_TOP,
    NON_CLICKABLE_FULLSCREEN_LOADER,
    CLICKABLE_FULLSCREEN_LOADER,
    NON_CLICKABLE_FULLSCREEN
}
