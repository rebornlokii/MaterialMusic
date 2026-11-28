package com.material.music.presentation.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.material.music.data.extension.ExtensionTrack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtensionSearchScreen(
    onBack: () -> Unit,
    onOpenAlbum: (provider: String, albumId: String, title: String) -> Unit,
    viewModel: ExtensionSearchViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }

    LaunchedEffect(query) { viewModel.search(query) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search online") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                placeholder = { Text("Song, album or artist") },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
            )

            when (val current = state) {
                ExtensionSearchState.Idle -> Unit
                ExtensionSearchState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }

                is ExtensionSearchState.Empty -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("No results for \"${current.query}\"")
                }

                is ExtensionSearchState.Failed -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(current.message, color = MaterialTheme.colorScheme.error)
                }

                is ExtensionSearchState.Results -> {
                    val albums = current.tracks.filter { it.itemType == "album" }
                    val artists = current.tracks.filter { it.itemType == "artist" }
                    val playlists = current.tracks.filter { it.itemType == "playlist" }
                    val songs = current.tracks.filterNot { it.isCollection }

                    LazyColumn(
                        Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        if (albums.isNotEmpty()) {
                            item { SectionHeader("Albums") }
                            items(albums, key = { "album:${it.id}" }) { album ->
                                AlbumRow(album) {
                                    onOpenAlbum(album.providerId, album.id, album.name)
                                }
                            }
                        }
                        if (artists.isNotEmpty()) {
                            item { SectionHeader("Artists") }
                            items(artists, key = { "artist:${it.id}" }) { artist ->
                                ResultRow(
                                    title = artist.name,
                                    subtitle = "Artist",
                                    cover = null,
                                )
                            }
                        }
                        if (playlists.isNotEmpty()) {
                            item { SectionHeader("Playlists") }
                            items(playlists, key = { "playlist:${it.id}" }) { playlist ->
                                ResultRow(title = playlist.name, subtitle = "Playlist", cover = null)
                            }
                        }
                        if (songs.isNotEmpty()) {
                            item { SectionHeader("Songs") }
                            items(songs, key = { "song:${it.id}" }) { song ->
                                ResultRow(
                                    title = song.name,
                                    subtitle = song.artists,
                                    cover = song.coverUrl,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    )
}

@Composable
private fun AlbumRow(album: ExtensionTrack, onClick: () -> Unit) {
    ResultRow(
        title = album.name,
        subtitle = album.artists,
        cover = album.coverUrl,
        onClick = onClick,
    )
}

@Composable
private fun ResultRow(
    title: String,
    subtitle: String,
    cover: String?,
    onClick: (() -> Unit)? = null,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = cover,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(8.dp)),
        )
        Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
            Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodyLarge)
            Text(
                subtitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

