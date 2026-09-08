package com.furkanfidanoglu.artbooktesting.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.furkanfidanoglu.artbooktesting.model.Art
import com.furkanfidanoglu.artbooktesting.viewmodel.ArtViewModel
import androidx.compose.runtime.setValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState

@Composable
fun HomeScreen(modifier: Modifier = Modifier, goToDetailScreen: (Int) -> Unit ,goToAddScreen: () -> Unit , viewModel: ArtViewModel) {
    val artList by viewModel.artList.collectAsStateWithLifecycle()
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "My Collection",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )


            Spacer(modifier = Modifier.size(24.dp))

            ItemList(
                artList = artList,
                goToDetailScreen = goToDetailScreen,
                onDelete = { id ->
                    viewModel.deleteArtById(id)
                }
            )
        }

        FloatingActionButton(
            onClick = {
                if (artList.isEmpty()) {
                    Toast.makeText(context, "No artwork to delete", Toast.LENGTH_SHORT).show()
                } else {
                    showDeleteDialog = true
                }
            },
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete All"
            )
        }

        FloatingActionButton(
            onClick = { goToAddScreen() },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Art"
            )
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text("Delete All Artworks?")
            },
            text = {
                Text("This action cannot be undone.")
            },
            confirmButton = {
                TextButton (
                    onClick = {
                        viewModel.deleteAllArts()
                        showDeleteDialog = false
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
fun ItemList(artList: List<Art>, goToDetailScreen: (Int) -> Unit, onDelete: (Int) -> Unit) {

    if (artList.isEmpty()) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "No artwork yet",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Add your first artwork to the collection.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

    } else {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(
                items = artList,
                key = { art -> art.id }
            ) { art ->

                //En altta Composable olarak tanımlı fonksiyonu kullanıyoruz
                SwipeToDeleteItem(
                    art = art,
                    goToDetailScreen = goToDetailScreen,
                    onDelete = onDelete
                )
            }
        }
    }
}

@Composable
fun ItemRow(art : Art, goToDetailScreen: (Int) -> Unit) {

    Card(
        onClick = { goToDetailScreen(art.id) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {

                AsyncImage(
                    model = art.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = art.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = art.artistName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteItem(art: Art, goToDetailScreen: (Int) -> Unit, onDelete: (Int) -> Unit) {

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->

            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete(art.id)
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Artwork",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    ) {

        ItemRow(
            art = art,
            goToDetailScreen = goToDetailScreen
        )
    }
}