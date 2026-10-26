package com.material.music.presentation.components.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.material.music.R
import com.material.music.presentation.components.rememberModalSheetState
import com.material.music.ui.theme.RoundedSans
import com.material.music.utils.AudioMetaUtils.mimeTypeToFormat
import java.util.Locale

internal enum class AudioQualityTier {
    DOLBY_ATMOS,
    DOLBY_AC4,
    DOLBY_TRUEHD,
    DOLBY_DIGITAL_PLUS,
    DOLBY_DIGITAL,
    DTS,
    HI_RES_LOSSLESS,
    LOSSLESS,
    LOSSY
}

internal data class AudioQualityInfo(
    val tier: AudioQualityTier,
    val formatName: String?,
    val mimeType: String?,
    val bitrate: Int?,
    val sampleRate: Int?,
    val bitDepth: Int?,
    val channelCount: Int?
)

private const val HI_RES_SAMPLE_RATE_HZ = 48_000
private const val HI_RES_BIT_DEPTH = 16

internal fun resolveAudioQualityInfo(
    mimeType: String?,
    bitrate: Int?,
    sampleRate: Int?,
    bitDepth: Int?,
    channelCount: Int?
): AudioQualityInfo? {
    val normalized = mimeType?.trim()?.lowercase(Locale.ROOT)?.substringBefore(';')
    val format = mimeTypeToFormat(mimeType).takeIf { it != "-" }
    if (normalized.isNullOrBlank() && format == null) return null

    val mime = normalized.orEmpty()
    val isLosslessCodec = mime.startsWith("audio/raw") ||
        mime.contains("true-hd") ||
        mime == "audio/vnd.dts.hd" ||
        format in LOSSLESS_FORMATS
    val isHiRes = (sampleRate ?: 0) > HI_RES_SAMPLE_RATE_HZ || (bitDepth ?: 0) > HI_RES_BIT_DEPTH

    val tier = when {
        mime == "audio/eac3-joc" -> AudioQualityTier.DOLBY_ATMOS
        mime == "audio/ac4" -> AudioQualityTier.DOLBY_AC4
        mime.contains("true-hd") -> AudioQualityTier.DOLBY_TRUEHD
        mime == "audio/eac3" -> AudioQualityTier.DOLBY_DIGITAL_PLUS
        mime == "audio/ac3" -> AudioQualityTier.DOLBY_DIGITAL
        mime.contains("dts") -> AudioQualityTier.DTS
        isLosslessCodec && isHiRes -> AudioQualityTier.HI_RES_LOSSLESS
        isLosslessCodec -> AudioQualityTier.LOSSLESS
        else -> AudioQualityTier.LOSSY
    }

    return AudioQualityInfo(
        tier = tier,
        formatName = (format ?: mime.substringAfter('/').takeIf { it.isNotBlank() })
            ?.uppercase(Locale.getDefault()),
        mimeType = mimeType,
        bitrate = bitrate?.takeIf { it > 0 },
        sampleRate = sampleRate?.takeIf { it > 0 },
        bitDepth = bitDepth?.takeIf { it > 0 },
        channelCount = channelCount?.takeIf { it > 0 }
    )
}

private val LOSSLESS_FORMATS = setOf("flac", "alac", "wav", "aiff")

@Composable
internal fun AudioQualityInfo.badgeLabel(): String = when (tier) {
    AudioQualityTier.DOLBY_ATMOS -> stringResource(R.string.audio_quality_dolby_atmos)
    AudioQualityTier.DOLBY_AC4 -> stringResource(R.string.audio_quality_dolby_ac4)
    AudioQualityTier.DOLBY_TRUEHD -> stringResource(R.string.audio_quality_dolby_truehd)
    AudioQualityTier.DOLBY_DIGITAL_PLUS -> stringResource(R.string.audio_quality_dolby_digital_plus)
    AudioQualityTier.DOLBY_DIGITAL -> stringResource(R.string.audio_quality_dolby_digital)
    AudioQualityTier.DTS -> stringResource(R.string.audio_quality_dts)
    AudioQualityTier.HI_RES_LOSSLESS -> stringResource(R.string.audio_quality_hi_res_lossless)
    AudioQualityTier.LOSSLESS -> stringResource(R.string.audio_quality_lossless)
    AudioQualityTier.LOSSY -> formatName ?: stringResource(R.string.audio_quality_unknown)
}

@Composable
private fun AudioQualityInfo.description(): String = when (tier) {
    AudioQualityTier.DOLBY_ATMOS -> stringResource(R.string.audio_quality_desc_dolby_atmos)
    AudioQualityTier.DOLBY_AC4,
    AudioQualityTier.DOLBY_TRUEHD,
    AudioQualityTier.DOLBY_DIGITAL_PLUS,
    AudioQualityTier.DOLBY_DIGITAL -> stringResource(R.string.audio_quality_desc_dolby)
    AudioQualityTier.DTS -> stringResource(R.string.audio_quality_desc_dts)
    AudioQualityTier.HI_RES_LOSSLESS -> stringResource(R.string.audio_quality_desc_hi_res_lossless)
    AudioQualityTier.LOSSLESS -> stringResource(R.string.audio_quality_desc_lossless)
    AudioQualityTier.LOSSY -> stringResource(R.string.audio_quality_desc_lossy)
}

@Composable
internal fun AudioQualityInfo.badgeColors(): Pair<Color, Color> {
    val colors = MaterialTheme.colorScheme
    return when (tier) {
        AudioQualityTier.DOLBY_ATMOS,
        AudioQualityTier.DOLBY_AC4,
        AudioQualityTier.DOLBY_TRUEHD,
        AudioQualityTier.DOLBY_DIGITAL_PLUS,
        AudioQualityTier.DOLBY_DIGITAL -> colors.primaryContainer to colors.onPrimaryContainer
        AudioQualityTier.HI_RES_LOSSLESS,
        AudioQualityTier.LOSSLESS -> colors.secondaryContainer to colors.onSecondaryContainer
        AudioQualityTier.DTS -> colors.tertiaryContainer to colors.onTertiaryContainer
        AudioQualityTier.LOSSY -> colors.surfaceContainerHighest to colors.onSurfaceVariant
    }
}

@Composable
private fun AudioQualityInfo.channelLabel(): String? {
    val count = channelCount ?: return null
    return when (count) {
        1 -> stringResource(R.string.audio_quality_channels_mono)
        2 -> stringResource(R.string.audio_quality_channels_stereo)
        else -> stringResource(R.string.audio_quality_channels_count, count)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AudioQualityBottomSheet(
    info: AudioQualityInfo,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalSheetState(skipPartiallyExpanded = true)
    val colorScheme = MaterialTheme.colorScheme

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
            )
        },
        containerColor = colorScheme.surfaceContainerHigh,
        tonalElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = info.badgeLabel(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = RoundedSans,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = info.description(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant
                )
            }

            val rows = buildList {
                info.formatName?.let { add(stringResource(R.string.audio_quality_row_format) to it) }
                info.sampleRate?.let {
                    add(
                        stringResource(R.string.audio_quality_row_sample_rate) to
                            String.format(Locale.US, "%.1f kHz", it / 1000.0)
                    )
                }
                info.bitDepth?.let {
                    add(stringResource(R.string.audio_quality_row_bit_depth) to "$it-bit")
                }
                info.bitrate?.let {
                    add(stringResource(R.string.audio_quality_row_bitrate) to "${it / 1000} kbps")
                }
                info.channelLabel()?.let {
                    add(stringResource(R.string.audio_quality_row_channels) to it)
                }
            }

            if (rows.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(26.dp),
                    color = colorScheme.surfaceContainerLow
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        rows.forEach { (label, value) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = value,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
