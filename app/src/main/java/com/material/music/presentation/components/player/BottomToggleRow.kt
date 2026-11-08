package com.material.music.presentation.components.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.AbsoluteSmoothCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.material.music.R
import com.material.music.presentation.components.LocalMaterialTheme
import com.material.music.presentation.components.ToggleSegmentButton
import racra.compose.smooth_corner_rect_library.AbsoluteSmoothCornerShape as SmoothCornerShape

@Composable
fun BottomToggleRow(
    modifier: Modifier,
    isShuffleEnabled: Boolean,
    isShuffleTransitionInProgress: Boolean,
    repeatMode: Int,
    isFavoriteProvider: () -> Boolean,
    onShuffleToggle: () -> Unit,
    onRepeatToggle: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onLyricsClick: (() -> Unit)? = null,
    onAddToPlaylistClick: (() -> Unit)? = null,
    activeColorMain: Color = MaterialTheme.colorScheme.primary,
    activeColorSecondary: Color = MaterialTheme.colorScheme.secondary,
    activeColorTertiary: Color = MaterialTheme.colorScheme.tertiary,
    onActiveColorMain: Color = MaterialTheme.colorScheme.onPrimary,
    onActiveColorSecondary: Color = MaterialTheme.colorScheme.onSecondary,
    onActiveColorTertiary: Color = MaterialTheme.colorScheme.onTertiary,
    inactiveColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    inactiveContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer
) {
    val isFavorite = isFavoriteProvider()
    val rowCorners = 60.dp
    val lyricsDescription = stringResource(R.string.presentation_batch_g_player_cd_lyrics)
    val addToPlaylistDescription = stringResource(R.string.cd_add_to_playlist)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = containerColor,
                    shape = AbsoluteSmoothCornerShape(
                        cornerRadiusBL = rowCorners,
                        smoothnessAsPercentTR = 60,
                        cornerRadiusBR = rowCorners,
                        smoothnessAsPercentBL = 60,
                        cornerRadiusTL = rowCorners,
                        smoothnessAsPercentBR = 60,
                        cornerRadiusTR = rowCorners,
                        smoothnessAsPercentTL = 60
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(
                        AbsoluteSmoothCornerShape(
                            cornerRadiusBL = rowCorners,
                            smoothnessAsPercentTR = 60,
                            cornerRadiusBR = rowCorners,
                            smoothnessAsPercentBL = 60,
                            cornerRadiusTL = rowCorners,
                            smoothnessAsPercentBR = 60,
                            cornerRadiusTR = rowCorners,
                            smoothnessAsPercentTL = 60
                        )
                    )
                    .background(Color.Transparent),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val commonModifier = Modifier.weight(1f)

                ToggleSegmentButton(
                    modifier = commonModifier,
                    active = isShuffleEnabled,
                    activeColor = activeColorMain,
                    activeCornerRadius = rowCorners,
                    activeContentColor = onActiveColorMain,
                    inactiveColor = inactiveColor,
                    inactiveContentColor = inactiveContentColor,
                    onClick = onShuffleToggle,
                    iconId = R.drawable.rounded_shuffle_24,
                    contentDesc = stringResource(R.string.cd_shuffle)
                )
                val repeatActive = repeatMode != Player.REPEAT_MODE_OFF
                val repeatIcon = when (repeatMode) {
                    Player.REPEAT_MODE_ONE -> R.drawable.rounded_repeat_one_24
                    Player.REPEAT_MODE_ALL -> R.drawable.rounded_repeat_24
                    else -> R.drawable.rounded_repeat_24
                }
                ToggleSegmentButton(
                    modifier = commonModifier,
                    active = repeatActive,
                    activeColor = activeColorSecondary,
                    activeCornerRadius = rowCorners,
                    activeContentColor = onActiveColorSecondary,
                    inactiveColor = inactiveColor,
                    inactiveContentColor = inactiveContentColor,
                    onClick = onRepeatToggle,
                    iconId = repeatIcon,
                    contentDesc = stringResource(R.string.cd_repeat)
                )
                ToggleSegmentButton(
                    modifier = commonModifier,
                    active = isFavorite,
                    activeColor = activeColorTertiary,
                    activeCornerRadius = rowCorners,
                    activeContentColor = onActiveColorTertiary,
                    inactiveColor = inactiveColor,
                    inactiveContentColor = inactiveContentColor,
                    onClick = onFavoriteToggle,
                    iconId = if (isFavorite) R.drawable.round_favorite_24 else R.drawable.rounded_favorite_24,
                    contentDesc = stringResource(R.string.cd_favorite)
                )
                if (onLyricsClick != null) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(inactiveColor)
                            .clickable(onClick = onLyricsClick)
                            .semantics { contentDescription = lyricsDescription },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.rounded_lyrics_24),
                            contentDescription = null,
                            tint = inactiveContentColor
                        )
                    }
                }
            }
        }

        if (onAddToPlaylistClick != null) {
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
                    .background(containerColor)
                    .clickable(onClick = onAddToPlaylistClick)
                    .semantics { contentDescription = addToPlaylistDescription },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = inactiveContentColor
                )
            }
        }
    }
}
