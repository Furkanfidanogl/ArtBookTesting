package com.furkanfidanoglu.artbooktesting.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.furkanfidanoglu.artbooktesting.model.ImageSrc
import com.furkanfidanoglu.artbooktesting.viewmodel.ArtViewModel


@Composable
fun SearchScreen(modifier: Modifier = Modifier, viewModel: ArtViewModel, goBack: () -> Unit) {
    val searchInput by viewModel.searchInput.collectAsStateWithLifecycle()
    val imageList by viewModel.imageList.collectAsStateWithLifecycle()

    val loading by viewModel.loading.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Search Artwork",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = searchInput,
            onValueChange = {
                viewModel.updateSearchInput(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Search images...")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (searchInput.isNotBlank()) {
                    viewModel.searchImages(searchInput)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (loading) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator()

                    Text(
                        text = "Searching artworks...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

        } else {

            ImageGrid(
                imageList = imageList,
                onImageClick = { image ->
                    viewModel.updateSelectedImage(image.original)
                    goBack()
                },

            )
        }
    }
}

@Composable
fun ImageGrid(imageList: List<ImageSrc>, onImageClick: (ImageSrc) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(imageList) { image ->
            ImageGridItem(
                image = image,
                onImageClick = onImageClick
            )
        }
    }
}

@Composable
fun ImageGridItem(image: ImageSrc, onImageClick: (ImageSrc) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
        onClick = {
            onImageClick(image)
        },
        shape = RoundedCornerShape(18.dp)
    ) {
        AsyncImage(
            model = image.medium,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}