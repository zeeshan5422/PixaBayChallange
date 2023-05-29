package com.images.api.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.images.api.R
import com.images.api.util.*
import kotlin.random.Random

/**
 * Created by ZEESHAN on 5/14/2023.
 */


@Composable
fun ProgressIndicatorView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(48.dp),
            color = MaterialTheme.colors.primary
        )
    }
}


@Composable
fun PixabayAlertDialog(text: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.body2
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "NO")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "YES")
            }
        }
    )
}

@Preview
@Composable
fun PixabayAlertDialog() {
    PixabayAlertDialog("Do you want to open details \\uD83D\\uDE48", {}, {})
}


@Composable
fun Chip(text: String, style: TextStyle = MaterialTheme.typography.body2) {
    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = RandomDarkColor(),
        contentColor = Color.White,
        modifier = Modifier.padding(all = 4.dp)
    ) {
        Text(
            text = text,
            style = style,
            modifier = Modifier.padding(all = 4.dp),
            color = Color.White
        )
    }
}

@Composable
fun RandomDarkColor(): Color {
    val random = Random.Default

    // Generate random values for red, green, and blue components
    val red = random.nextInt(0, 128)
    val green = random.nextInt(0, 128)
    val blue = random.nextInt(0, 128)

    return Color(red, green, blue)
}

@Composable
fun ErrorView(
    error: ResourceError,
    onRetryClick: () -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(getErrorImageByCode(error.errorCode)),
                contentDescription = null, // Provide a description if needed
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = ErrorTextByCode(context, error),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .wrapContentSize()
            )

            if (error.errorCode !in (400..403) || error.errorCode != 440) {
                Button(
                    onClick = onRetryClick,
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

@Preview
@Composable
fun ErrorView() {
    ErrorView(error = emptyException()) {}
}

private fun getErrorImageByCode(errorCode: Int): Int {
    return when (errorCode) {
        in (400..403), 440 -> {
            R.drawable.ic_not_allowed
        }
        ERROR_EMPTY_DATA -> {
            R.drawable.ic_error_info
        }
        UNAUTHORIZED,
        NOT_FOUND,
        BAD_RESPONSE,
        NO_NETWORK -> {
            R.drawable.ic_error_info
        }
        else -> {
            R.drawable.ic_cell_wifi
        }
    }
}

private fun ErrorTextByCode(context: Context, error: ResourceError): String {
    return when (error.errorCode) {
        in (400..403), 440 -> {
            context.resources.getString(R.string.un_authorize_error)
        }
        ERROR_EMPTY_DATA -> {
            context.resources.getString(R.string.empty_data)
        }
        UNAUTHORIZED,
        NOT_FOUND,
        BAD_RESPONSE,
        NO_NETWORK -> {
            context.resources.getString(R.string.network_error)
        }
        else -> {
            error.message.ifBlank {
                context.resources.getString(R.string.unhandled_error_occurred)
            }
        }
    }
}
