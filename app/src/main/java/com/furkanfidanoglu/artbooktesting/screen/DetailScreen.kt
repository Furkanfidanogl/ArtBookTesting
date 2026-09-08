package com.furkanfidanoglu.artbooktesting.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.furkanfidanoglu.artbooktesting.viewmodel.ArtViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage


@Composable
fun DetailScreen(modifier: Modifier = Modifier, id: Int, viewModel: ArtViewModel, goBack: () -> Unit) {
    LaunchedEffect(id) {
        viewModel.getArtByID(id)
    }
    val selectedArt by viewModel.selectedArt.collectAsStateWithLifecycle()

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Artwork Detail",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        DetailArtImage(selectedArt?.image.orEmpty())

        OutlinedTextField(
            value = selectedArt?.name.orEmpty(),
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Art Name")
            },
            readOnly = true
        )

        OutlinedTextField(
            value = selectedArt?.artistName.orEmpty(),
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Artist Name")
            },
            readOnly = true
        )

        OutlinedTextField(
            value = selectedArt?.year?.toString().orEmpty(),
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Year")
            },
            readOnly = true
        )

        Spacer(modifier = Modifier.weight(1f))

        FloatingActionButton(
            onClick = {
                showDeleteDialog = true
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Artwork"
            )
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text("Delete The Artwork?")
            },
            text = {
                Text("This action cannot be undone.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedArt?.id?.let { artId ->
                            viewModel.deleteArtById(artId)
                            Toast.makeText(context, "Artwork deleted", Toast.LENGTH_SHORT).show()
                            goBack()
                        }
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun DetailArtImage(imageString: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RoundedCornerShape(20.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {

            if (imageString.isNotBlank()) {
                SubcomposeAsyncImage(
                    model = imageString,
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