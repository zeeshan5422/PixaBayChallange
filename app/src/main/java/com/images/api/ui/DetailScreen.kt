package com.images.api.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.images.api.domain.model.ImageModel

/**
 * Created by ZEESHAN on 5/14/2023.
 */

@Composable
fun DetailScreen(item: ImageModel) {
    detailContent(item)
}

@Composable
private fun detailContent(item: ImageModel) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        headerImage(item.webformatURL)

        imageTitle(item.user)

        imageTags(item.tags)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            rowItem("Likes", "${item.likes}")
            rowItem("Downloads", "${item.downloads}")
            rowItem("Comments", "${item.comments}")
        }
    }
}


@Composable
private fun headerImage(url: String?) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                transformations(RoundedCornersTransformation())
            }
        ),
        contentDescription = null,
        modifier = Modifier
            .fillMaxHeight(0.35f)
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
    )
}

@Preview
@Composable
private fun headerImage() {
    headerImage("https://pixabay.com/photos/berries-fruits-food-blackberries-2277/")
}

@Composable
private fun imageTitle(user: String?) {
    Text(
        text = user ?: "",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        style = MaterialTheme.typography.h4,
    )
}

@Composable
private fun imageTags(tags: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        tags.forEach { chipText ->
            Chip(
                text = chipText,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
}

@Composable
private fun rowItem(title: String, value: String) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = MaterialTheme.shapes.large,
                clip = true
            ),
        shape = RoundedCornerShape(2.dp),
    ) {

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title)
            Spacer(modifier = Modifier.weight(1f))
            Text(value)
        }
    }
}


@Preview
@Composable
private fun detailContent() {
    val item = ImageModel(
        user = "ZEESHAN",
        tags = listOf("tag1", "tag2"),
        likes = 23,
        downloads = 5,
        comments = 99
    )
    detailContent(item)
}