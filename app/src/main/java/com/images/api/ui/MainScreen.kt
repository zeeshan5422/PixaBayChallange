package com.images.api.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.images.api.domain.model.ImageModel
import com.images.api.util.ResourceError
import com.images.api.util.UiState
import com.images.api.vm.MainViewModel

/**
 * Created by ZEESHAN on 5/14/2023.
 */

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {
    Column {
        SearchField(viewModel)
        mainContent(viewModel, navController)
    }
}


@Composable
private fun mainContent(viewModel: MainViewModel, navController: NavHostController) {

    val uiState by viewModel.data.observeAsState()

    when (uiState) {
        UiState.Initial -> {
            // NOTE: screen initial start, show information to user if required.
        }
        UiState.Loading -> {
            ProgressIndicatorView()
        }
        is UiState.Success -> {

            loadListing(
                viewModel,
                navController,
                (uiState as UiState.Success<List<ImageModel>>).data
            )
        }
        is UiState.Fail -> {
            ErrorView(error = (uiState as UiState.Fail).error as ResourceError) {
                viewModel.fetchImagesData()
            }
        }
        else -> {}
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun loadListing(
    viewModel: MainViewModel,
    navController: NavHostController,
    data: List<ImageModel>
) {

    val mItems = remember { data }

    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .imeNestedScroll(),
    ) {
        items(mItems) {
            listItem(viewModel = viewModel, item = it, navController)
        }
    }
}

@Composable
private fun listItem(
    viewModel: MainViewModel?,
    item: ImageModel,
    navController: NavHostController
) {
    var isAlertDialogShown = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = MaterialTheme.shapes.large,
                clip = true
            )
            .clickable {
                isAlertDialogShown.value = true
            },
        shape = RoundedCornerShape(8.dp),
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Image(
                painter = rememberImagePainter(
                    data = item.previewURL,
                    builder = {
                        transformations(CircleCropTransformation()) // Optional: Apply transformations
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .padding(start = 4.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.user ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    item.tags.forEach { chipText ->
                        Chip(text = chipText)
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }
    }


    if (isAlertDialogShown.value) {
        PixabayAlertDialog(text = "Do you want to open details \uD83D\uDE48",
            onConfirm = {
                isAlertDialogShown.value = false
                viewModel?.setImage(item)
                navController.navigate(ROUTE_DETAIL)
            }, onDismiss = {
                isAlertDialogShown.value = false
            })
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(viewModel: MainViewModel) {
    val queryState = viewModel.query.observeAsState()

    val keyboardController = LocalSoftwareKeyboardController.current


    OutlinedTextField(
        value = queryState.value ?: "",
        onValueChange = {
            viewModel.setQuery(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        label = { Text(text = "Search") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
            viewModel.fetchImagesData()
        })
    )

}


@Preview
@Composable
fun listItem() {
    val item = ImageModel(
        previewURL = "https://pixabay.com/photos/berries-fruits-food-blackberries-2277/",
        user = "PublicDomainPictures",
        tags = listOf("berries", "fruits", "food")
    )
    val navController = rememberNavController()
    listItem(null, item, navController)
}