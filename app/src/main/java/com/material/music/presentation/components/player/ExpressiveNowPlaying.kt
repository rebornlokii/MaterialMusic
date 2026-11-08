package com.material.music.presentation.components.player

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Bluetooth
import androidx.compose.material.icons.rounded.Speaker
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.material.music.R
import com.material.music.data.model.Artist
import com.material.music.data.model.Song
import com.material.music.data.preferences.AlbumArtQuality
import com.material.music.presentation.components.LocalMaterialTheme
import com.material.music.presentation.components.MaxSafeAlbumArtDimensionPx
import com.material.music.presentation.components.player.BottomToggleRow
import com.material.music.presentation.components.PlaylistBottomSheet
import com.material.music.presentation.components.ToggleSegmentButton
import com.material.music.presentation.components.rememberArtworkModelWithCacheVersion
import com.material.music.presentation.viewmodel.PlayerViewModel
import com.material.music.presentation.viewmodel.PlaylistViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

/** Fraction of the artwork's height that stays fully opaque before it starts dissolving. */
private const val ARTWORK_FADE_START = 0.7f
private const val ARTWORK_FADE_STEPS = 16

/**
 * Alpha mask for the artwork: opaque at the top, then a smoothstep falloff to fully
 * transparent, so the image melts into the palette background without a visible edge.
 */
private val ArtworkFadeMask: Brush = Brush.verticalGradient(
    colorStops = Array(ARTWORK_FADE_STEPS + 2) { index ->
        if (index == 0) {
            0f to Color.Black
        } else {
            val progress = (index - 1).toFloat() / ARTWORK_FADE_STEPS
            val stop = ARTWORK_FADE_START + (1f - ARTWORK_FADE_START) * progress
            val eased = progress * progress * (3f - 2f * progress)
            stop to Color.Black.copy(alpha = 1f - eased)
        }
    }
)

private class ExpressiveLayoutCoordinates {
    var container: LayoutCoordinates? = null
    var trackInfo: LayoutCoordinates? = null
}

/**
 * Portrait now-playing layout where the album art is the screen background. Colours come from
 * [LocalMaterialTheme], which the player sheet already derives from the artwork and animates
 * between tracks.
 */
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
internal fun ExpressiveNowPlayingContent(
    song: Song,
    currentSongArtists: ImmutableList<Artist>,
    albumArtQuality: AlbumArtQuality,
    paddingValues: PaddingValues,
    expansionFractionProvider: () -> Float,
    playerViewModel: PlayerViewModel,
    isPlayingProvider: () -> Boolean,
    isFavoriteProvider: () -> Boolean,
    isShuffleEnabled: Boolean,
    isShuffleTransitionInProgress: Boolean,
    repeatMode: Int,
    bluetoothOutputName: String?,
    progressSection: @Composable () -> Unit,
    onCollapse: () -> Unit,
    onAudioOutputClick: () -> Unit,
    onQueueClick: () -> Unit,
    onLyricsClick: () -> Unit,
    onArtistClick: () -> Unit,
    onAlbumClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onPrevious: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onShuffleToggle: () -> Unit,
    onRepeatToggle: () -> Unit
) {
    var showPlaylistSheet by rememberSaveable(song.id) { mutableStateOf(false) }
    val layoutCoordinates = remember { ExpressiveLayoutCoordinates() }
    var trackInfoTopPx by remember { mutableFloatStateOf(Float.NaN) }
    val artworkTailPx = with(LocalDensity.current) { 28.dp.toPx() }

    fun updateTrackInfoTop() {
        val container = layoutCoordinates.container
        val trackInfo = layoutCoordinates.trackInfo
        if (container?.isAttached == true && trackInfo?.isAttached == true) {
            trackInfoTopPx = container.localPositionOf(trackInfo, Offset.Zero).y
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                layoutCoordinates.container = it
                updateTrackInfoTop()
            }
    ) {
        ExpressiveArtworkBackdrop(
            song = song,
            albumArtQuality = albumArtQuality,
            artworkHeightPx = { containerHeightPx ->
                // The artwork finishes dissolving just below the top of the track title, so the
                // title always sits where the fade has already begun on every screen size.
                val trackInfoTop = trackInfoTopPx
                val target = if (trackInfoTop.isNaN()) {
                    containerHeightPx * 0.6f
                } else {
                    trackInfoTop + artworkTailPx
                }
                target.coerceIn(containerHeightPx * 0.42f, containerHeightPx * 0.7f).roundToInt()
            },
            expansionFractionProvider = expansionFractionProvider,
            onSwipeNext = onNext,
            onSwipePrevious = onPrevious,
            onClick = onAlbumClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .graphicsLayer {
                    alpha = ((expansionFractionProvider() - 0.25f) / 0.75f).coerceIn(0f, 1f)
                }
        ) {
            ExpressiveHeader(
                bluetoothOutputName = bluetoothOutputName,
                onCollapse = onCollapse,
                onAudioOutputClick = onAudioOutputClick,
                onQueueClick = onQueueClick
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        layoutCoordinates.trackInfo = it
                        updateTrackInfoTop()
                    },
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ExpressiveTrackInfo(
                    song = song,
                    currentSongArtists = currentSongArtists,
                    expansionFractionProvider = expansionFractionProvider,
                    playerViewModel = playerViewModel,
                    isPlayingProvider = isPlayingProvider,
                    isFavoriteProvider = isFavoriteProvider,
                    onArtistClick = onArtistClick,
                    onFavoriteToggle = onFavoriteToggle
                )
                progressSection()
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExpressiveTransportControls(
                isPlayingProvider = isPlayingProvider,
                onPrevious = onPrevious,
                onPlayPause = onPlayPause,
                onNext = onNext
            )

            Spacer(modifier = Modifier.height(14.dp))

            ExpressiveBottomActions(
                isShuffleEnabled = isShuffleEnabled,
                isShuffleTransitionInProgress = isShuffleTransitionInProgress,
                repeatMode = repeatMode,
                isFavoriteProvider = isFavoriteProvider,
                onShuffleToggle = onShuffleToggle,
                onRepeatToggle = onRepeatToggle,
                onFavoriteToggle = onFavoriteToggle,
                onLyricsClick = onLyricsClick,
                onAddToPlaylistClick = { showPlaylistSheet = true }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }

    if (showPlaylistSheet) {
        val playlistViewModel: PlaylistViewModel = hiltViewModel()
        val playlistUiState by playlistViewModel.uiState.collectAsStateWithLifecycle()
        MaterialTheme(
            colorScheme = LocalMaterialTheme.current,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes
        ) {
            PlaylistBottomSheet(
                playlistUiState = playlistUiState,
                songs = persistentListOf(song),
                onDismiss = { showPlaylistSheet = false },
                bottomBarHeight = 0.dp,
                playerViewModel = playerViewModel,
                playlistViewModel = playlistViewModel
            )
        }
    }
}

@Composable
private fun ExpressiveArtworkBackdrop(
    song: Song,
    albumArtQuality: AlbumArtQuality,
    artworkHeightPx: (containerHeightPx: Int) -> Int,
    expansionFractionProvider: () -> Float,
    onSwipeNext: () -> Unit,
    onSwipePrevious: () -> Unit,
    onClick: () -> Unit
) {
    val colors = LocalMaterialTheme.current
    val baseColor = colors.primaryContainer
    val deepColor = lerp(colors.primaryContainer, colors.surfaceContainerLowest, 0.22f)
    val context = LocalContext.current
    val haptics = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val dragOffset = remember { Animatable(0f) }
    val swipeThresholdPx = with(LocalDensity.current) { 88.dp.toPx() }
    val latestOnSwipeNext by rememberUpdatedState(onSwipeNext)
    val latestOnSwipePrevious by rememberUpdatedState(onSwipePrevious)

    val requestSizePx = when (albumArtQuality) {
        AlbumArtQuality.LOW -> 512
        AlbumArtQuality.ORIGINAL -> MaxSafeAlbumArtDimensionPx
        else -> 1024
    }
    val artworkModel = rememberArtworkModelWithCacheVersion(
        song.albumArtUriString?.takeIf { it.isNotBlank() }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = expansionFractionProvider().coerceIn(0f, 1f) }
                .background(
                    Brush.verticalGradient(
                        0f to baseColor,
                        0.55f to baseColor,
                        1f to deepColor
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .layout { measurable, constraints ->
                    val height = artworkHeightPx(constraints.maxHeight)
                        .coerceIn(0, constraints.maxHeight)
                    val placeable = measurable.measure(
                        constraints.copy(minHeight = height, maxHeight = height)
                    )
                    layout(placeable.width, height) { placeable.place(0, 0) }
                }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            val offset = dragOffset.value
                            if (abs(offset) >= swipeThresholdPx) {
                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                if (offset < 0f) latestOnSwipeNext() else latestOnSwipePrevious()
                            }
                            scope.launch { dragOffset.animateTo(0f, spring()) }
                        },
                        onDragCancel = {
                            scope.launch { dragOffset.animateTo(0f, spring()) }
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            scope.launch { dragOffset.snapTo(dragOffset.value + dragAmount * 0.5f) }
                        }
                    )
                }
                .clickable(
                    enabled = song.albumId != -1L,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                    alpha = expansionFractionProvider().coerceIn(0f, 1f)
                    translationX = dragOffset.value
                }
                .drawWithContent {
                    drawContent()
                    drawRect(brush = ArtworkFadeMask, blendMode = BlendMode.DstIn)
                }
        ) {
            Crossfade(
                targetState = artworkModel,
                animationSpec = tween(durationMillis = 450, easing = FastOutSlowInEasing),
                label = "ExpressiveArtworkCrossfade"
            ) { model ->
                if (model == null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(R.drawable.pixelplayer_base_monochrome),
                            contentDescription = null,
                            tint = colors.onPrimaryContainer.copy(alpha = 0.18f),
                            modifier = Modifier.size(120.dp)
                        )
                    }
                } else {
                    val request = remember(model, requestSizePx) {
                        val builder = if (model is ImageRequest) {
                            model.newBuilder(context)
                        } else {
                            ImageRequest.Builder(context).data(model)
                        }
                        builder.size(requestSizePx).crossfade(300).build()
                    }
                    AsyncImage(
                        model = request,
                        contentDescription = stringResource(R.string.cd_album_art_of, song.title),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpressiveHeader(
    bluetoothOutputName: String?,
    onCollapse: () -> Unit,
    onAudioOutputClick: () -> Unit,
    onQueueClick: () -> Unit
) {
    val colors = LocalMaterialTheme.current
    val containerColor = colors.onPrimary.copy(alpha = 0.72f)
    val contentColor = colors.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExpressiveHeaderButton(
            shape = CircleShape,
            containerColor = containerColor,
            actionDescription = stringResource(R.string.presentation_batch_g_player_cd_collapse),
            onClick = onCollapse
        ) {
            Icon(
                painter = painterResource(R.drawable.rounded_keyboard_arrow_down_24),
                contentDescription = null,
                tint = contentColor
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
            ExpressiveHeaderButton(
                shape = RoundedCornerShape(topStart = 22.dp, bottomStart = 22.dp, topEnd = 8.dp, bottomEnd = 8.dp),
                containerColor = containerColor,
                actionDescription = bluetoothOutputName
                    ?: stringResource(R.string.player_audio_output_title),
                onClick = onAudioOutputClick
            ) {
                Icon(
                    imageVector = if (bluetoothOutputName != null) {
                        Icons.Rounded.Bluetooth
                    } else {
                        Icons.Rounded.Speaker
                    },
                    contentDescription = null,
                    tint = contentColor
                )
            }
            ExpressiveHeaderButton(
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp, topEnd = 22.dp, bottomEnd = 22.dp),
                containerColor = containerColor,
                actionDescription = stringResource(R.string.presentation_batch_g_player_cd_queue),
                onClick = onQueueClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.rounded_queue_music_24),
                    contentDescription = null,
                    tint = contentColor
                )
            }
        }
    }
}

@Composable
private fun ExpressiveHeaderButton(
    shape: Shape,
    containerColor: Color,
    actionDescription: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(shape)
            .background(containerColor)
            .clickable(onClick = onClick)
            .semantics { contentDescription = actionDescription },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
private fun ExpressiveTrackInfo(
    song: Song,
    currentSongArtists: ImmutableList<Artist>,
    expansionFractionProvider: () -> Float,
    playerViewModel: PlayerViewModel,
    isPlayingProvider: () -> Boolean,
    isFavoriteProvider: () -> Boolean,
    onArtistClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    val colors = LocalMaterialTheme.current
    val isBuffering by remember(playerViewModel) {
        playerViewModel.stablePlayerState.map { it.isBuffering }.distinctUntilChanged()
    }.collectAsStateWithLifecycle(initialValue = false)
    val subtitle = remember(song.displayArtist, song.album) {
        listOf(song.displayArtist, song.album)
            .filter { it.isNotBlank() }
            .distinct()
            .joinToString(" • ")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 70.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PlayerSongInfo(
            title = song.title,
            artist = subtitle,
            artistId = song.artistId,
            artists = currentSongArtists,
            expansionFractionProvider = expansionFractionProvider,
            textColor = colors.onPrimaryContainer,
            artistTextColor = colors.onPrimaryContainer.copy(alpha = 0.72f),
            gradientEdgeColor = colors.primaryContainer,
            playerViewModel = playerViewModel,
            onClickArtist = onArtistClick,
            modifier = Modifier.weight(1f),
            isPlayingProvider = isPlayingProvider
        )

        if (isBuffering) {
            LoadingIndicator(
                modifier = Modifier.size(32.dp),
                color = colors.onPrimaryContainer
            )
        }

        val isFavorite = isFavoriteProvider()
        Surface(
            onClick = onFavoriteToggle,
            modifier = Modifier.size(52.dp),
            shape = RoundedCornerShape(16.dp),
            color = colors.secondaryFixedDim,
            contentColor = colors.onSecondaryFixed
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(
                        if (isFavorite) R.drawable.round_favorite_24 else R.drawable.rounded_favorite_24
                    ),
                    contentDescription = stringResource(R.string.cd_favorite)
                )
            }
        }
    }
}

@Composable
private fun ExpressiveTransportControls(
    isPlayingProvider: () -> Boolean,
    onPrevious: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit
) {
    val colors = LocalMaterialTheme.current
    val playPauseWeight = 1.6f
    val buttonSpacing = 12.dp

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        // Sized so the previous/next buttons are circles at rest.
        val controlHeight: Dp = ((maxWidth - buttonSpacing) / (2f + playPauseWeight))
            .coerceIn(64.dp, 92.dp)

        AnimatedPlaybackControls(
            modifier = Modifier.fillMaxWidth(),
            isPlayingProvider = isPlayingProvider,
            onPrevious = onPrevious,
            onPlayPause = onPlayPause,
            onNext = onNext,
            height = controlHeight,
            baseWeight = 1f,
            playPauseBaseWeight = playPauseWeight,
            expansionWeight = 1.12f,
            compressionWeight = 0.85f,
            pressAnimationSpec = tween(durationMillis = 240, easing = FastOutSlowInEasing),
            playPauseCornerPaused = 24.dp,
            colorPlayPause = colors.tertiaryFixedDim,
            tintPlayPauseIcon = colors.onTertiaryFixed,
            colorOtherButtons = colors.secondaryFixedDim,
            tintOtherIcons = colors.onSecondaryFixed,
            playPauseIconSize = 34.dp,
            iconSize = 30.dp
        )
    }
}

@Composable
private fun ExpressiveBottomActions(
    isShuffleEnabled: Boolean,
    isShuffleTransitionInProgress: Boolean,
    repeatMode: Int,
    isFavoriteProvider: () -> Boolean,
    onShuffleToggle: () -> Unit,
    onRepeatToggle: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onLyricsClick: () -> Unit,
    onAddToPlaylistClick: () -> Unit
) {
    val colors = LocalMaterialTheme.current
    val groupColor = colors.surfaceContainerLowest.copy(alpha = 0.72f)

    BottomToggleRow(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 66.dp, max = 86.dp)
            .padding(horizontal = 5.dp)
            .background(groupColor)
            .padding(5.dp),
        isShuffleEnabled = isShuffleEnabled,
        isShuffleTransitionInProgress = isShuffleTransitionInProgress,
        repeatMode = repeatMode,
        isFavoriteProvider = isFavoriteProvider,
        onShuffleToggle = onShuffleToggle,
        onRepeatToggle = onRepeatToggle,
        onFavoriteToggle = onFavoriteToggle,
        onLyricsClick = onLyricsClick,
        onAddToPlaylistClick = onAddToPlaylistClick,
        showFavoriteToggle = false,
        activeColorMain = colors.primaryFixed,
        activeColorSecondary = colors.secondaryFixed,
        activeColorTertiary = colors.onSecondaryFixed,
        onActiveColorMain = colors.onPrimaryFixed,
        onActiveColorSecondary = colors.onSecondaryFixed,
        onActiveColorTertiary = colors.onPrimaryFixed,
        inactiveColor = colors.onSurface.copy(alpha = 0.07f),
        inactiveContentColor = colors.onSurface
    )
}
