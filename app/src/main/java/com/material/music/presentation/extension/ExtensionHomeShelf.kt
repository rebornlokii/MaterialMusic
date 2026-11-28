package com.material.music.presentation.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.material.music.data.extension.ExtensionHomeItem

/**
 * Provider shelves at the top of Home. Reuses the app's existing section
 * rhythm (title + horizontal shelf) rather than introducing a new component.
 */
@Composable
fun ExtensionHomeShelf(
    onOpenAlbum: (provider: String, albumId: String, title: String) -> Unit,
    viewModel: ExtensionHomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val ready = state as? ExtensionHomeState.Ready ?: return

    Column(Modifier.fillMaxWidth()) {
        ready.feed.sections.filter { it.items.isNotEmpty() }.forEach { section ->
            Text(
                section.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(section.items, key = { "${section.uri}:${it.id}" }) { item ->
                    ShelfCard(item) {
                        val albumId = item.albumId ?: return@ShelfCard
                        onOpenAlbum(item.providerId, albumId, item.albumName ?: item.name)
                    }
                }
            }
        }
    }
}

@Composable
private fun ShelfCard(item: ExtensionHomeItem, onClick: () -> Unit) {
    Column(
        Modifier
            .width(140.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(4.dp),
    ) {
        AsyncImage(
            model = item.featuredCoverUrl ?: item.coverUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(140.dp).clip(RoundedCornerShape(12.dp)),
        )
        Text(
            item.name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp),
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                item.artists,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}