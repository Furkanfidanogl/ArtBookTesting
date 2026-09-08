package com.furkanfidanoglu.artbooktesting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.furkanfidanoglu.artbooktesting.viewmodel.ArtViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.furkanfidanoglu.artbooktesting.model.Art

@Composable
fun AddScreen(modifier: Modifier = Modifier, goToSearchScreen: () -> Unit, viewModel: ArtViewModel , goBack: () -> Unit) {
    var artName by remember { mutableStateOf("") }
    var artistName by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    val selectedImage by viewModel.selectedImage.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Add Artwork",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        ArtImagePlaceholder(goToSearchScreen, selectedImage)

        OutlinedTextField(
            value = artName,
            onValueChange = { artName = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Artwork Name")
            }
        )

        OutlinedTextField(
            value = artistName,
            onValueChange = { artistName = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Artist Name")
            }
        )

        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Year")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val art = Art(
                    name = artName,
                    artistName = artistName,
                    year = year.toIntOrNull() ?: 0,
                    image = selectedImage
                )
                if (art.name.isNotBlank() && art.artistName.isNotBlank() && art.year != 0 && art.image.isNotBlank()){
                    viewModel.insertArt(art)
                    viewModel.clearAddScreenState()
                    goBack()
                }
              },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Save Artwork")
        }
    }
}

@Composable
fun ArtImagePlaceholder(goToSearchScreen: () -> Unit, selectedImage: String) {

    Card(
        onClick = { goToSearchScreen() },
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        shape = RoundedCornerShape(20.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {

            if (selectedImage.isNotBlank()) {
                SubcomposeAsyncImage(
                    model = selectedImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    error = {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}