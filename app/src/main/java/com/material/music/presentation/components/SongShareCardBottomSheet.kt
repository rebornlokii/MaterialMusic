package com.material.music.presentation.components

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material.icons.rounded.IosShare
import androidx.compose.material.icons.rounded.CropSquare
import androidx.compose.material.icons.rounded.Smartphone
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.material.music.R
import com.material.music.data.model.Song
import com.material.music.ui.theme.GoogleSansFlex
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.CaptureController
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import java.io.File
import java.text.Normalizer
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private enum class SongShareCardFormat {
    SQUARE,
    STORY
}

/** A precomposed square/story pair with a springy crossfade so switching formats feels like a morph. */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SongShareCardBottomSheet(
    song: Song,
    onDismiss: () -> Unit,
    dimBackground: Boolean = true,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val appName = stringResource(R.string.app_name)
    val shareChooserTitle = stringResource(R.string.share_card_chooser_title)
    val shareText = stringResource(
        R.string.share_card_share_text,
        song.title,
        song.displayArtist,
        appName
    )
    val savedMessage = stringResource(R.string.share_card_saved)
    val saveFailedMessage = stringResource(R.string.share_card_save_failed)
    val shareFailedMessage = stringResource(R.string.share_card_share_failed)
    val sheetState = rememberModalSheetState(skipPartiallyExpanded = true)
    val squareCaptureController = rememberCaptureController()
    val storyCaptureController = rememberCaptureController()
    var selectedFormat by rememberSaveable(song.id) { mutableStateOf(SongShareCardFormat.SQUARE) }
    var isWorking by remember { mutableStateOf(false) }

    fun selectedController(): CaptureController = when (selectedFormat) {
        SongShareCardFormat.SQUARE -> squareCaptureController
        SongShareCardFormat.STORY -> storyCaptureController
    }

    fun captureAndShare() {
        if (isWorking) return
        isWorking = true
        scope.launch {
            try {
                val bitmap = checkNotNull(selectedController().captureAsync().await()) {
                    "Card capture returned no bitmap"
                }.asAndroidBitmap()
                shareSongCard(context, bitmap, song, selectedFormat, shareChooserTitle, shareText)
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (exception: Exception) {
                Timber.e(exception, "Failed to share song card for %s", song.id)
                Toast.makeText(context, shareFailedMessage, Toast.LENGTH_LONG).show()
            } finally {
                isWorking = false
            }
        }
    }

    fun captureAndSave() {
        if (isWorking) return
        isWorking = true
        scope.launch {
            try {
                val bitmap = checkNotNull(selectedController().captureAsync().await()) {
                    "Card capture returned no bitmap"
                }.asAndroidBitmap()
                saveSongCard(context, bitmap, song, selectedFormat)
                Toast.makeText(context, savedMessage, Toast.LENGTH_SHORT).show()
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (exception: Exception) {
                Timber.e(exception, "Failed to save song card for %s", song.id)
                Toast.makeText(context, saveFailedMessage, Toast.LENGTH_LONG).show()
            } finally {
                isWorking = false
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        scrimColor = if (dimBackground) MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f) else Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.share_card_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.height(14.dp))

            SongShareCardPreview(
                song = song,
                selectedFormat = selectedFormat,
                squareCaptureController = squareCaptureController,
                storyCaptureController = storyCaptureController,
            )

            Spacer(Modifier.height(16.dp))
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(
                    selected = selectedFormat == SongShareCardFormat.SQUARE,
                    onClick = { selectedFormat = SongShareCardFormat.SQUARE },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                    enabled = !isWorking,
                    icon = {
                        Icon(Icons.Rounded.CropSquare, contentDescription = null, modifier = Modifier.size(18.dp))
                    },
                ) {
                    Text(stringResource(R.string.share_card_format_square))
                }
                SegmentedButton(
                    selected = selectedFormat == SongShareCardFormat.STORY,
                    onClick = { selectedFormat = SongShareCardFormat.STORY },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                    enabled = !isWorking,
                    icon = {
                        Icon(Icons.Rounded.Smartphone, contentDescription = null, modifier = Modifier.size(18.dp))
                    },
                ) {
                    Text(stringResource(R.string.share_card_format_story))
                }
            }

            Spacer(Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Button(
                    onClick = ::captureAndShare,
                    enabled = !isWorking,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(horizontal = 12.dp),
                ) {
                    if (isWorking) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    } else {
                        Icon(Icons.Rounded.IosShare, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.share_card_share))
                    }
                }
                FilledTonalButton(
                    onClick = ::captureAndSave,
                    enabled = !isWorking,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(horizontal = 12.dp),
                ) {
                    Icon(Icons.Rounded.Download, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.share_card_save))
                }
            }
        }
    }
}

@Composable
private fun SongShareCardPreview(
    song: Song,
    selectedFormat: SongShareCardFormat,
    squareCaptureController: CaptureController,
    storyCaptureController: CaptureController,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val maxCardHeight = (screenHeight * 0.47f).coerceIn(260.dp, 440.dp)
    val motionSpec = spring<Float>(dampingRatio = 0.82f, stiffness = 360f)
    val squareVisible by animateFloatAsState(
        targetValue = if (selectedFormat == SongShareCardFormat.SQUARE) 1f else 0f,
        animationSpec = motionSpec,
        label = "squareShareCardVisibility",
    )
    val storyVisible by animateFloatAsState(
        targetValue = if (selectedFormat == SongShareCardFormat.STORY) 1f else 0f,
        animationSpec = motionSpec,
        label = "storyShareCardVisibility",
    )
    val squareScale by animateFloatAsState(
        targetValue = if (selectedFormat == SongShareCardFormat.SQUARE) 1f else 0.94f,
        animationSpec = motionSpec,
        label = "squareShareCardScale",
    )
    val storyScale by animateFloatAsState(
        targetValue = if (selectedFormat == SongShareCardFormat.STORY) 1f else 0.94f,
        animationSpec = motionSpec,
        label = "storyShareCardScale",
    )

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth().height(maxCardHeight),
        contentAlignment = Alignment.Center,
    ) {
        val squareSide = minOf(maxWidth, maxCardHeight)
        val storyHeight = minOf(maxCardHeight, maxWidth * (16f / 9f))
        val storyWidth = storyHeight * (9f / 16f)

        // Both format trees stay composed and keep the same artwork warm in Coil's cache.
        // The selected card is already measured and painted before it fades into view.
        Box(
            modifier = Modifier
                .size(squareSide)
                .graphicsLayer {
                    alpha = squareVisible
                    scaleX = squareScale
                    scaleY = squareScale
                }
                .clip(RoundedCornerShape(28.dp))
                .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(28.dp)),
        ) {
            SongShareCard(
                song = song,
                format = SongShareCardFormat.SQUARE,
                modifier = Modifier.fillMaxSize().capturable(squareCaptureController),
            )
        }

        Box(
            modifier = Modifier
                .size(width = storyWidth, height = storyHeight)
                .graphicsLayer {
                    alpha = storyVisible
                    scaleX = storyScale
                    scaleY = storyScale
                }
                .clip(RoundedCornerShape(28.dp))
                .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(28.dp)),
        ) {
            SongShareCard(
                song = song,
                format = SongShareCardFormat.STORY,
                modifier = Modifier.fillMaxSize().capturable(storyCaptureController),
            )
        }
    }
}

@Composable
private fun SongShareCard(
    song: Song,
    format: SongShareCardFormat,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
        val isStory = format == SongShareCardFormat.STORY
        val cardWidth = maxWidth.value
        val edgePadding = (cardWidth * if (isStory) 0.095f else 0.07f).dp
        val titleSize = (cardWidth * if (isStory) 0.105f else 0.085f).coerceIn(22f, 42f).sp
        val artistSize = (cardWidth * if (isStory) 0.055f else 0.048f).coerceIn(14f, 22f).sp
        val artworkFraction = if (isStory) 0.78f else 0.45f
        val artworkShape = RoundedCornerShape((cardWidth * 0.055f).coerceIn(16f, 26f).dp)
        val foreground = Color.White

        if (!song.albumArtUriString.isNullOrBlank()) {
            SmartImage(
                model = song.albumArtUriString,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                targetSize = coil.size.Size(1200, 1200),
                crossfadeDurationMillis = 0,
            )
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.tertiary,
                                MaterialTheme.colorScheme.secondary,
                            )
                        )
                    )
            )
        }

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to Color.Black.copy(alpha = 0.54f),
                            0.30f to Color.Black.copy(alpha = 0.08f),
                            0.56f to Color.Black.copy(alpha = 0.28f),
                            1f to Color.Black.copy(alpha = 0.86f),
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(edgePadding),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.16f))
                        .padding(horizontal = (cardWidth * 0.035f).dp, vertical = (cardWidth * 0.022f).dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy((cardWidth * 0.022f).dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.pixelplayer_base_monochrome),
                        contentDescription = null,
                        tint = foreground,
                        modifier = Modifier.size((cardWidth * 0.065f).coerceIn(22f, 30f).dp),
                    )
                    Text(
                        text = stringResource(R.string.app_name),
                        color = foreground,
                        fontFamily = GoogleSansFlex,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = (cardWidth * 0.043f).coerceIn(13f, 18f).sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.16f))
                        .padding(horizontal = (cardWidth * 0.035f).dp, vertical = (cardWidth * 0.022f).dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.GraphicEq,
                        contentDescription = null,
                        tint = foreground,
                        modifier = Modifier.size((cardWidth * 0.052f).coerceIn(17f, 22f).dp),
                    )
                    Text(
                        text = stringResource(R.string.share_card_now_playing),
                        color = foreground,
                        fontFamily = GoogleSansFlex,
                        fontWeight = FontWeight.Medium,
                        fontSize = (cardWidth * 0.035f).coerceIn(10f, 14f).sp,
                        maxLines = 1,
                    )
                }
            }

            SmartImage(
                model = song.albumArtUriString,
                contentDescription = stringResource(R.string.widget_album_art),
                modifier = Modifier
                    .fillMaxWidth(artworkFraction)
                    .aspectRatio(1f)
                    .align(Alignment.CenterHorizontally)
                    .clip(artworkShape)
                    .border(1.dp, Color.White.copy(alpha = 0.32f), artworkShape),
                contentScale = ContentScale.Crop,
                targetSize = coil.size.Size(1200, 1200),
                crossfadeDurationMillis = 0,
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy((cardWidth * 0.014f).dp),
            ) {
                Text(
                    text = song.title,
                    color = foreground,
                    fontFamily = GoogleSansFlex,
                    fontWeight = FontWeight.Bold,
                    fontSize = titleSize,
                    lineHeight = (titleSize.value * 1.08f).sp,
                    maxLines = if (isStory) 3 else 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = song.displayArtist,
                    color = foreground.copy(alpha = 0.82f),
                    fontFamily = GoogleSansFlex,
                    fontWeight = FontWeight.Medium,
                    fontSize = artistSize,
                    maxLines = if (isStory) 2 else 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

private suspend fun shareSongCard(
    context: Context,
    capturedBitmap: Bitmap,
    song: Song,
    format: SongShareCardFormat,
    chooserTitle: String,
    shareText: String,
) {
    val uri = withContext(Dispatchers.IO) {
        val bitmap = exportableBitmap(capturedBitmap)
        val directory = File(context.cacheDir, "song-cards").apply { mkdirs() }
        val file = File(
            directory,
            shareFileName(song, format).removeSuffix(".png") + "_${System.currentTimeMillis()}.png"
        )
        file.outputStream().use { output ->
            check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)) { "PNG encoding failed" }
        }
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        putExtra(Intent.EXTRA_TEXT, shareText)
        clipData = android.content.ClipData.newRawUri(null, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    val chooser = Intent.createChooser(sendIntent, chooserTitle).apply {
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (context !is Activity) addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(chooser)
}

private suspend fun saveSongCard(
    context: Context,
    capturedBitmap: Bitmap,
    song: Song,
    format: SongShareCardFormat,
): Unit = withContext(Dispatchers.IO) {
    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, shareFileName(song, format))
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(
            MediaStore.Images.Media.RELATIVE_PATH,
            "${Environment.DIRECTORY_PICTURES}/${context.getString(R.string.app_name)}"
        )
        put(MediaStore.Images.Media.IS_PENDING, 1)
    }
    val collection: Uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    val uri = checkNotNull(context.contentResolver.insert(collection, values)) { "Could not create image" }
    try {
        val bitmap = exportableBitmap(capturedBitmap)
        context.contentResolver.openOutputStream(uri)?.use { output ->
            check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)) { "PNG encoding failed" }
        } ?: error("Could not open image output")
        val published = ContentValues().apply { put(MediaStore.Images.Media.IS_PENDING, 0) }
        context.contentResolver.update(uri, published, null, null)
    } catch (exception: Exception) {
        context.contentResolver.delete(uri, null, null)
        throw exception
    }
}

private fun shareFileName(song: Song, format: SongShareCardFormat): String =
    "${safeFileName(song.title)}_${safeFileName(song.id).takeLast(12)}_${format.name.lowercase()}.png"

private fun exportableBitmap(bitmap: Bitmap): Bitmap =
    if (bitmap.config == Bitmap.Config.HARDWARE) {
        checkNotNull(bitmap.copy(Bitmap.Config.ARGB_8888, false)) { "Could not prepare the card bitmap" }
    } else {
        bitmap
    }

private fun safeFileName(value: String): String = Normalizer.normalize(value, Normalizer.Form.NFKC)
    .replace(Regex("[^\\p{L}\\p{N}._-]+"), "_")
    .trim('_', '.')
    .take(70)
    .ifBlank { "song" }
