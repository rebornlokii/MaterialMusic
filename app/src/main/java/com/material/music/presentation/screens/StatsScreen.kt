@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.material.music.presentation.screens

<<<<<<< HEAD
import android.text.format.DateFormat as AndroidDateFormat
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
=======
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
>>>>>>> origin/main
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
<<<<<<< HEAD
=======
import androidx.compose.foundation.layout.defaultMinSize
>>>>>>> origin/main
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
<<<<<<< HEAD
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
=======
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
>>>>>>> origin/main
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
<<<<<<< HEAD
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.GraphicEq
import androidx.compose.material.icons.outlined.Hearing
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.rounded.Headphones
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ButtonGroupDefaults
=======
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Hearing
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.rounded.Refresh
>>>>>>> origin/main
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
<<<<<<< HEAD
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialShapes
=======
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
>>>>>>> origin/main
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
<<<<<<< HEAD
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.ripple
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
=======
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
>>>>>>> origin/main
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
<<<<<<< HEAD
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
=======
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
>>>>>>> origin/main
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
<<<<<<< HEAD
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.material.music.R
import com.material.music.data.stats.PlaybackStatsRepository
import com.material.music.data.stats.PlaybackStatsRepository.PlaybackStatsSummary
import com.material.music.data.stats.StatsTimeRange
import com.material.music.presentation.components.CollapsibleCommonTopBar
import com.material.music.presentation.components.MiniPlayerHeight
import com.material.music.presentation.components.SmartImage
import com.material.music.presentation.components.SmartImageCompactListTargetSize
import com.material.music.presentation.navigation.Screen
import com.material.music.presentation.navigation.navigateSafely
import com.material.music.presentation.stats.displayNameRes
import com.material.music.presentation.viewmodel.PlayerViewModel
import com.material.music.presentation.viewmodel.StatsViewModel
import com.material.music.utils.formatListeningDurationCompact
import com.material.music.utils.shapes.RoundedStarShape
import java.net.URLEncoder
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import racra.compose.smooth_corner_rect_library.AbsoluteSmoothCornerShape

private const val PULL_TO_REFRESH_MIN_DURATION_MS = 3500L
private const val HEAVY_ROTATION_LIMIT = 5

private val SectionSpacing = 20.dp
private val ScreenGutter = 20.dp
=======
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.material.music.R
import com.material.music.data.stats.PlaybackStatsRepository
import com.material.music.data.stats.StatsTimeRange
import com.material.music.presentation.components.CollapsibleCommonTopBar
import com.material.music.presentation.components.ExpressiveTopBarContent
import com.material.music.presentation.components.MiniPlayerHeight
import com.material.music.presentation.components.SmartImage
import com.material.music.presentation.screens.TabAnimation
import com.material.music.presentation.viewmodel.StatsViewModel
import com.material.music.utils.formatListeningDurationCompact
import com.material.music.utils.formatListeningDurationLong
import androidx.compose.ui.platform.LocalContext
import java.util.Locale
import android.text.format.DateFormat as AndroidDateFormat
import kotlin.math.roundToInt
import kotlin.math.PI
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import racra.compose.smooth_corner_rect_library.AbsoluteSmoothCornerShape
import androidx.compose.ui.unit.sp
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.material.music.presentation.stats.displayNameRes
import androidx.compose.material.icons.outlined.Album
import com.material.music.utils.shapes.RoundedStarShape
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.PlayCircleOutline
import com.material.music.ui.theme.ExpTitleTypography
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

private const val PULL_TO_REFRESH_MIN_DURATION_MS = 3500L
>>>>>>> origin/main

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class, ExperimentalFoundationApi::class)
@Composable
fun StatsScreen(
    navController: NavController,
<<<<<<< HEAD
    playerViewModel: PlayerViewModel,
=======
>>>>>>> origin/main
    statsViewModel: StatsViewModel = hiltViewModel()
) {
    val uiState by statsViewModel.uiState.collectAsStateWithLifecycle()
    val summary = uiState.summary
    val lazyListState = rememberLazyListState()
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val minTopBarHeight = 62.dp + statusBarHeight
    val maxTopBarHeight = 176.dp

    val minTopBarHeightPx = with(density) { minTopBarHeight.toPx() }
    val maxTopBarHeightPx = with(density) { maxTopBarHeight.toPx() }

    val topBarHeight = remember { Animatable(maxTopBarHeightPx) }
    var collapseFraction by remember { mutableStateOf(0f) }

    LaunchedEffect(topBarHeight.value) {
        collapseFraction = 1f - ((topBarHeight.value - minTopBarHeightPx) / (maxTopBarHeightPx - minTopBarHeightPx)).coerceIn(0f, 1f)
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val scrollingDown = delta < 0

                if (!scrollingDown && (lazyListState.firstVisibleItemIndex > 0 || lazyListState.firstVisibleItemScrollOffset > 0)) {
                    return Offset.Zero
                }

                val previousHeight = topBarHeight.value
                val newHeight = (previousHeight + delta).coerceIn(minTopBarHeightPx, maxTopBarHeightPx)
                val consumed = newHeight - previousHeight

                if (consumed.roundToInt() != 0) {
                    coroutineScope.launch {
                        topBarHeight.snapTo(newHeight)
                    }
                }

                val canConsume = !(scrollingDown && newHeight == minTopBarHeightPx)
                return if (canConsume) Offset(0f, consumed) else Offset.Zero
            }
        }
    }

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (!lazyListState.isScrollInProgress) {
            val shouldExpand = topBarHeight.value > (minTopBarHeightPx + maxTopBarHeightPx) / 2
            val canExpand = lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0
            val target = if (shouldExpand && canExpand) maxTopBarHeightPx else minTopBarHeightPx

            if (topBarHeight.value != target) {
                coroutineScope.launch {
                    topBarHeight.animateTo(target, spring(stiffness = Spring.StiffnessMedium))
                }
            }
        }
    }

    val currentTopBarHeightDp = with(density) { topBarHeight.value.toDp() }
    val tabsHeight = 62.dp
    val showRangeTabIndicator = false
    val tabIndicatorExtraSpacing = if (showRangeTabIndicator) 8.dp else 0.dp
    val tabContentSpacing = 20.dp
    var selectedTimelineMetric by rememberSaveable { mutableStateOf(TimelineMetric.ListeningTime) }
<<<<<<< HEAD
    var selectedRotation by rememberSaveable { mutableStateOf(RotationDimension.Tracks) }
=======
    var selectedCategoryDimension by rememberSaveable { mutableStateOf(CategoryDimension.Song) }
>>>>>>> origin/main
    val pullToRefreshState = rememberPullToRefreshState()
    var isPullRefreshAnimating by remember { mutableStateOf(false) }
    var isPullRefreshMinDelayActive by remember { mutableStateOf(false) }
    var hasPendingPullRefresh by remember { mutableStateOf(false) }
    val isRefreshingFromViewModel by rememberUpdatedState(uiState.isRefreshing)

    val onPullRefresh: () -> Unit = {
        if (!hasPendingPullRefresh && !uiState.isLoading) {
            hasPendingPullRefresh = true
            isPullRefreshAnimating = true
            isPullRefreshMinDelayActive = true
            statsViewModel.requestStatsRefresh()
            coroutineScope.launch {
                delay(PULL_TO_REFRESH_MIN_DURATION_MS)
                isPullRefreshMinDelayActive = false
                if (!isRefreshingFromViewModel) {
                    isPullRefreshAnimating = false
                    hasPendingPullRefresh = false
                }
            }
        }
    }

    LaunchedEffect(uiState.isRefreshing, hasPendingPullRefresh, isPullRefreshMinDelayActive) {
        if (!hasPendingPullRefresh) return@LaunchedEffect
        if (uiState.isRefreshing) {
            isPullRefreshAnimating = true
        } else if (!isPullRefreshMinDelayActive) {
            isPullRefreshAnimating = false
            hasPendingPullRefresh = false
        }
    }

<<<<<<< HEAD
    // Artist pictures live in the artists table (custom image, folder portrait, else Deezer when
    // the user enabled it), not on the played songs, so they are looked up once there is
    // something to show.
    val topArtistNames = remember(summary) { summary?.topArtists.orEmpty().map { it.artist } }
    val artistImages by produceState(emptyMap<String, String>(), topArtistNames) {
        value = if (topArtistNames.isEmpty()) emptyMap() else statsViewModel.resolveArtistImages(topArtistNames)
    }

    val heavyRotationQueueName = stringResource(R.string.presentation_batch_g_stats_section_heavy_rotation)
    // Stats only carry display names for artists, albums and genres, so each tap resolves the
    // library entity first; entries that no longer resolve (deleted songs, the synthetic
    // "unknown" buckets) are silently ignored rather than opening a broken detail screen.
    val onRotationEntryClick: (RotationEntry) -> Unit = { entry ->
        val playedSongIds = summary?.songs.orEmpty().map { it.songId }
        coroutineScope.launch {
            when (entry.dimension) {
                RotationDimension.Tracks -> {
                    val queue = statsViewModel.resolveSongs(playedSongIds.ifEmpty { listOf(entry.key) })
                    val song = queue.firstOrNull { it.id == entry.key } ?: return@launch
                    playerViewModel.showAndPlaySong(song, queue, queueName = heavyRotationQueueName)
                }

                RotationDimension.Artists -> statsViewModel.resolveArtistId(entry.title)?.let { artistId ->
                    navController.navigateSafely(Screen.ArtistDetail.createRoute(artistId))
                }

                RotationDimension.Albums -> statsViewModel.resolveAlbumId(entry.title, playedSongIds)?.let { albumId ->
                    navController.navigateSafely(Screen.AlbumDetail.createRoute(albumId))
                }

                RotationDimension.Genres -> if (statsViewModel.isLibraryGenre(entry.title)) {
                    navController.navigateSafely(
                        Screen.GenreDetail.createRoute(URLEncoder.encode(entry.title, "UTF-8"))
                    )
                }
            }
        }
    }

=======
>>>>>>> origin/main
    PullToRefreshBox(
        isRefreshing = isPullRefreshAnimating,
        onRefresh = onPullRefresh,
        state = pullToRefreshState,
        modifier = Modifier.fillMaxSize(),
        indicator = {
            PullToRefreshDefaults.LoadingIndicator(
                state = pullToRefreshState,
                isRefreshing = isPullRefreshAnimating,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = currentTopBarHeightDp + tabsHeight + tabIndicatorExtraSpacing + 4.dp)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .nestedScroll(nestedScrollConnection)
        ) {
            if (uiState.isLoading && summary == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    ContainedLoadingIndicator()
                }
            } else {
<<<<<<< HEAD
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = currentTopBarHeightDp + tabsHeight + tabIndicatorExtraSpacing + tabContentSpacing,
                        bottom = MiniPlayerHeight + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(SectionSpacing)
                ) {
                    if (summary == null || !summary.hasListeningActivity()) {
                        item(key = "empty", contentType = "empty") {
                            StatsEmptyState(
                                icon = Icons.Outlined.GraphicEq,
                                title = stringResource(R.string.presentation_batch_g_stats_empty_no_timeline_title),
                                subtitle = stringResource(R.string.presentation_batch_g_stats_empty_no_timeline_subtitle),
                                modifier = Modifier.padding(horizontal = ScreenGutter)
                            )
                        }
                    } else {
                        item(key = "hero", contentType = "hero") {
                            StatsHeroSection(
                                summary = summary,
                                modifier = Modifier.padding(horizontal = ScreenGutter)
                            )
                        }
                        item(key = "timeline", contentType = "timeline") {
                            ListeningTimelineSection(
                                summary = summary,
                                selectedMetric = selectedTimelineMetric,
                                onMetricSelected = { selectedTimelineMetric = it },
                                modifier = Modifier.padding(horizontal = ScreenGutter)
                            )
                        }
                        item(key = "heavy_rotation", contentType = "heavy_rotation") {
                            HeavyRotationSection(
                                summary = summary,
                                artistImages = artistImages,
                                selected = selectedRotation,
                                onSelected = { selectedRotation = it },
                                onEntryClick = onRotationEntryClick,
                                modifier = Modifier.padding(horizontal = ScreenGutter)
                            )
                        }
                        item(key = "habits", contentType = "habits") {
                            ListeningHabitsGrid(
                                summary = summary,
                                modifier = Modifier.padding(horizontal = ScreenGutter)
                            )
                        }
                        item(key = "variety", contentType = "variety") {
                            ListeningVarietyCard(
                                summary = summary,
                                modifier = Modifier.padding(horizontal = ScreenGutter)
                            )
                        }
=======
                val showDailyRhythm = summary?.range == StatsTimeRange.DAY || summary?.range == StatsTimeRange.WEEK

                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = currentTopBarHeightDp + tabsHeight + tabIndicatorExtraSpacing + tabContentSpacing + 0.dp,
                        bottom = MiniPlayerHeight + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    item(key = "hero_section") {
                        StatsHeroSection(
                            summary = summary,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                    item {
                        ListeningTimelineSection(
                            summary = summary,
                            selectedMetric = selectedTimelineMetric,
                            onMetricSelected = { selectedTimelineMetric = it },
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                    item {
                        CategoryMetricsSection(
                            summary = summary,
                            selectedDimension = selectedCategoryDimension,
                            onDimensionSelected = { selectedCategoryDimension = it },
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }

                    item {
                        ListeningHabitsCard(
                            summary = summary,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                    item {
                        TopArtistsCard(
                            summary = summary,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                    item {
                        TopAlbumsCard(
                            summary = summary,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                    item {
                        TrackConcentrationCard(
                            summary = summary,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                    item {
                        SongStatsCard(
                            summary = summary,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
>>>>>>> origin/main
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .zIndex(5f)
            ) {
                val solidAlpha = (collapseFraction * 2f).coerceIn(0f, 1f)
                val backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = solidAlpha)

                Column(
                    modifier = Modifier
                        .background(backgroundColor)
                        .padding(bottom = 8.dp)
                ) {
                    CollapsibleCommonTopBar(
<<<<<<< HEAD
                        title = stringResource(R.string.presentation_batch_g_stats_title),
=======
                        title = stringResource(R.string.stats_title),
>>>>>>> origin/main
                        collapseFraction = collapseFraction,
                        headerHeight = currentTopBarHeightDp,
                        onBackClick = { navController.popBackStack() },
                        containerColor = Color.Transparent,
                        actions = {
                            FilledIconButton(
                                modifier = Modifier
                                    .padding(end = 12.dp),
                                onClick = statsViewModel::requestStatsRefresh,
                                enabled = !uiState.isLoading && !uiState.isRefreshing && !isPullRefreshAnimating,
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Refresh,
<<<<<<< HEAD
                                    contentDescription = stringResource(R.string.presentation_batch_g_stats_cd_refresh)
=======
                                    contentDescription = stringResource(R.string.stats_cd_refresh)
>>>>>>> origin/main
                                )
                            }
                        }
                    )

                    RangeTabsHeader(
                        ranges = uiState.availableRanges,
                        selected = uiState.selectedRange,
                        onRangeSelected = statsViewModel::onRangeSelected,
                        indicatorSpacing = tabIndicatorExtraSpacing,
                        showIndicator = showRangeTabIndicator,
                    )
                }
            }
        }
    }
}

<<<<<<< HEAD
// region Shared building blocks

@OptIn(ExperimentalTextApi::class)
@Composable
private fun rememberStatsFlexFont(weight: Int, width: Float, rond: Float = 100f): FontFamily =
    remember(weight, width, rond) {
        FontFamily(
            Font(
                resId = R.font.gflex_variable,
                weight = FontWeight(weight),
                variationSettings = FontVariation.Settings(
                    FontVariation.weight(weight),
                    FontVariation.width(width.coerceIn(25f, 151f)),
                    FontVariation.Setting("ROND", rond)
                )
            )
        )
    }

@Composable
private fun StatsSectionHeader(
    title: String,
    subtitle: String?,
=======
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StatsHeroSection(
    summary: PlaybackStatsRepository.PlaybackStatsSummary?,
    modifier: Modifier = Modifier
) {
    val hasData = (summary?.totalDurationMs ?: 0L) > 0 || (summary?.totalPlayCount ?: 0) > 0
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HeroCard(
            title = stringResource(R.string.stats_hero_listening),
            value = if (hasData) formatListeningDurationCompact(summary?.totalDurationMs ?: 0L) else "--",
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )

        HeroCard(
            title = stringResource(R.string.stats_hero_plays),
            value = if (hasData) "${summary?.totalPlayCount ?: 0}" else "--",
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
    }
}

@Composable
private fun HeroCard(
    title: String,
    value: String,
    containerColor: Color,
    contentColor: Color,
>>>>>>> origin/main
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
<<<<<<< HEAD
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontFamily = rememberStatsFlexFont(weight = 700, width = 118f),
            color = MaterialTheme.colorScheme.onSurface
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** Spring-backed press scale, read inside graphicsLayer so presses never recompose the caller. */
@Composable
private fun animatedPressScale(
    interactionSource: InteractionSource,
    pressedScale: Float = 0.96f
): State<Float> {
    val pressed by interactionSource.collectIsPressedAsState()
    return animateFloatAsState(
        targetValue = if (pressed) pressedScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "pressScale"
    )
}

/** 0 → [target] on first composition, then springs between later targets. */
@Composable
private fun animatedFraction(target: Float): State<Float> {
    val animatable = remember { Animatable(0f) }
    LaunchedEffect(target) {
        animatable.animateTo(
            targetValue = target,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
    }
    return animatable.asState()
}

/**
 * Corners for one item of a connected list: the group's first item keeps round top corners,
 * its last keeps round bottom corners, and every edge shared with a neighbour tightens to
 * [inner], so the group reads as one segmented block.
 */
private fun segmentedItemShape(
    index: Int,
    count: Int,
    outer: Dp = 22.dp,
    inner: Dp = 8.dp,
): RoundedCornerShape {
    val top = if (index == 0) outer else inner
    val bottom = if (index == count - 1) outer else inner
    return RoundedCornerShape(topStart = top, topEnd = top, bottomStart = bottom, bottomEnd = bottom)
}

// endregion

// region Hero vitals

@Composable
private fun StatsHeroSection(
    summary: PlaybackStatsSummary,
    modifier: Modifier = Modifier
) {
    // A per-day average of a single day is just the total again; sessions say more there.
    val listeningFooter = if (summary.range == StatsTimeRange.DAY) {
        pluralStringResource(R.plurals.stats_sessions_count, summary.totalSessions, summary.totalSessions)
    } else {
        stringResource(
            R.string.presentation_batch_g_stats_hero_per_day,
            formatListeningDurationCompact(summary.averageDailyDurationMs)
        )
    }
    val playsValue = remember(summary.totalPlayCount) {
        NumberFormat.getIntegerInstance().format(summary.totalPlayCount)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HeroVitalCard(
            label = stringResource(R.string.presentation_batch_g_stats_hero_listening),
            value = formatListeningDurationCompact(summary.totalDurationMs),
            footer = listeningFooter,
            icon = Icons.Rounded.Headphones,
            iconShape = RoundedStarShape(sides = 8, curve = 0.1, rotation = 0f),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            accentColor = MaterialTheme.colorScheme.primary,
            onAccentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.weight(1f)
        )
        HeroVitalCard(
            label = stringResource(R.string.presentation_batch_g_stats_hero_plays),
            value = playsValue,
            footer = pluralStringResource(R.plurals.stats_tracks_count, summary.uniqueSongs, summary.uniqueSongs),
            icon = Icons.Rounded.MusicNote,
            iconShape = MaterialShapes.Cookie9Sided.toShape(),
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            accentColor = MaterialTheme.colorScheme.tertiary,
            onAccentColor = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun HeroVitalCard(
    label: String,
    value: String,
    footer: String,
    icon: ImageVector,
    iconShape: Shape,
    containerColor: Color,
    contentColor: Color,
    accentColor: Color,
    onAccentColor: Color,
    modifier: Modifier = Modifier
) {
    val valueFont = rememberStatsFlexFont(weight = 760, width = 118f)
    Column(
        modifier = modifier
            .clip(AbsoluteSmoothCornerShape(26.dp, 80))
            .background(containerColor)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(iconShape)
                    .background(accentColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = onAccentColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontFamily = rememberStatsFlexFont(weight = 620, width = 108f),
                color = contentColor.copy(alpha = 0.86f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.height(18.dp))
        // Fixed-height slot so both cards line up however far auto-size has to shrink a value.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            BasicText(
                text = value,
                style = TextStyle(
                    fontFamily = valueFont,
                    fontSize = 40.sp,
                    lineHeight = 44.sp,
                    letterSpacing = (-0.5).sp,
                    color = contentColor
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 22.sp,
                    maxFontSize = 40.sp,
                    stepSize = 1.sp
                )
            )
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = footer,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .padding(horizontal = 10.dp, vertical = 5.dp),
            style = MaterialTheme.typography.labelMedium,
            fontFamily = rememberStatsFlexFont(weight = 600, width = 104f),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
=======
            .clip(RoundedCornerShape(24.dp))
            .background(containerColor)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            color = contentColor.copy(alpha = 0.85f)
        )
        Text(
            text = value,
            style = ExpTitleTypography.displayMedium.copy(fontSize = 32.sp),
            fontWeight = FontWeight.Bold,
            color = contentColor
>>>>>>> origin/main
        )
    }
}

<<<<<<< HEAD
// endregion

=======
>>>>>>> origin/main
@Composable
private fun StatsEmptyState(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedStarShape(sides = 8, curve = 0.1, rotation = 0f))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

<<<<<<< HEAD
=======

@Composable
private fun SummaryPill(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.85f))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun SummaryHeroTile(
    title: String,
    value: String,
    supporting: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.22f)
                    )
                )
            )
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .widthIn(min = 160.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = supporting,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SummaryProgressRow(
    title: String,
    label: String?,
    supporting: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val displayLabel = label ?: stringResource(R.string.stats_em_dash)
    val progressValue = progress.coerceIn(0f, 1f)
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.9f))
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = displayLabel,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        LinearWavyProgressIndicator(
            progress = { progressValue },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(50)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
        Text(
            text = supporting,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

>>>>>>> origin/main
@Composable
private fun RangeTabsHeader(
    ranges: ImmutableList<StatsTimeRange>,
    selected: StatsTimeRange,
    onRangeSelected: (StatsTimeRange) -> Unit,
    indicatorSpacing: Dp,
    showIndicator: Boolean = false,
    modifier: Modifier = Modifier
) {
    val selectedIndex = remember(ranges, selected) { ranges.indexOf(selected).coerceAtLeast(0) }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f),
        color = Color.Transparent,
    ) {
        PrimaryScrollableTabRow(
            modifier = if (indicatorSpacing > 0.dp) Modifier.padding(bottom = indicatorSpacing) else Modifier,
            selectedTabIndex = selectedIndex,
            edgePadding = 12.dp,
            divider = {},
            containerColor = Color.Transparent,
            indicator = {
                if (showIndicator) {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(
                            selectedTabIndex = selectedIndex,
                            matchContentSize = true
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        height = 3.dp
                    )
                }
            }
        ) {
            ranges.forEachIndexed { index, range ->
                TabAnimation(
                    index = index,
                    selectedIndex = selectedIndex,
                    onClick = { onRangeSelected(range) },
                    selectedColor = MaterialTheme.colorScheme.primary,
                    onSelectedColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    onUnselectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    title = stringResource(range.displayNameRes())
                ) {
                    Text(
                        text = stringResource(range.displayNameRes()),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = if (index == selectedIndex) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}

<<<<<<< HEAD
// region Listening habits

private fun bentoShape(index: Int): Shape {
    val outer = 26.dp
    val inner = 8.dp
    return RoundedCornerShape(
        topStart = if (index == 0) outer else inner,
        topEnd = if (index == 1) outer else inner,
        bottomStart = if (index == 2) outer else inner,
        bottomEnd = if (index == 3) outer else inner
    )
}

@Composable
private fun ListeningHabitsGrid(
    summary: PlaybackStatsSummary,
    modifier: Modifier = Modifier
) {
    val emDash = stringResource(R.string.presentation_batch_g_stats_em_dash)
    // In the day and week views the timeline above already names the peak slot, so the
    // second tile offers the streak instead of repeating it.
    val timelineShowsPeakDay = summary.range == StatsTimeRange.DAY || summary.range == StatsTimeRange.WEEK

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatsSectionHeader(
            title = stringResource(R.string.presentation_batch_g_stats_section_listening_habits),
            subtitle = null
        )
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                HabitTile(
                    icon = Icons.Outlined.Hearing,
                    iconShape = CircleShape,
                    iconContainer = MaterialTheme.colorScheme.primaryContainer,
                    onIconContainer = MaterialTheme.colorScheme.onPrimaryContainer,
                    value = formatListeningDurationCompact(summary.averageSessionDurationMs),
                    label = stringResource(R.string.presentation_batch_g_stats_habit_avg_session),
                    supporting = pluralStringResource(R.plurals.stats_sessions_count, summary.totalSessions, summary.totalSessions),
                    shape = bentoShape(0),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                if (timelineShowsPeakDay) {
                    HabitTile(
                        icon = Icons.Outlined.CalendarMonth,
                        iconShape = MaterialShapes.Cookie9Sided.toShape(),
                        iconContainer = MaterialTheme.colorScheme.secondaryContainer,
                        onIconContainer = MaterialTheme.colorScheme.onSecondaryContainer,
                        value = pluralStringResource(R.plurals.stats_days_count, summary.longestStreakDays, summary.longestStreakDays),
                        label = stringResource(R.string.presentation_batch_g_stats_habit_longest_streak),
                        supporting = null,
                        shape = bentoShape(1),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                } else {
                    HabitTile(
                        icon = Icons.Outlined.CalendarMonth,
                        iconShape = MaterialShapes.Cookie9Sided.toShape(),
                        iconContainer = MaterialTheme.colorScheme.secondaryContainer,
                        onIconContainer = MaterialTheme.colorScheme.onSecondaryContainer,
                        value = summary.peakDayLabel?.replaceFirstChar { it.titlecase(Locale.getDefault()) } ?: emDash,
                        label = stringResource(R.string.presentation_batch_g_stats_habit_most_active_day),
                        supporting = if (summary.peakDayDurationMs > 0L) {
                            formatListeningDurationCompact(summary.peakDayDurationMs)
                        } else {
                            stringResource(R.string.presentation_batch_g_stats_habit_no_playback_yet)
                        },
                        shape = bentoShape(1),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }
            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                HabitTile(
                    icon = Icons.Outlined.Bolt,
                    iconShape = RoundedStarShape(sides = 8, curve = 0.1, rotation = 0f),
                    iconContainer = MaterialTheme.colorScheme.tertiaryContainer,
                    onIconContainer = MaterialTheme.colorScheme.onTertiaryContainer,
                    value = if (summary.longestSessionDurationMs > 0L) {
                        formatListeningDurationCompact(summary.longestSessionDurationMs)
                    } else {
                        emDash
                    },
                    label = stringResource(R.string.presentation_batch_g_stats_habit_longest_session),
                    supporting = null,
                    shape = bentoShape(2),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                HabitTile(
                    icon = Icons.Outlined.AutoGraph,
                    iconShape = RoundedCornerShape(12.dp),
                    iconContainer = MaterialTheme.colorScheme.primaryContainer,
                    onIconContainer = MaterialTheme.colorScheme.onPrimaryContainer,
                    value = String.format(LocalConfiguration.current.locales[0], "%.1f", summary.averageSessionsPerDay),
                    label = stringResource(R.string.presentation_batch_g_stats_habit_sessions_per_day),
                    supporting = pluralStringResource(R.plurals.stats_active_days_count, summary.activeDays, summary.activeDays),
                    shape = bentoShape(3),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
=======
@Composable
private fun ListeningHabitsCard(
    summary: PlaybackStatsRepository.PlaybackStatsSummary?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.stats_section_listening_habits),
                style = MaterialTheme.typography.titleLargeEmphasized,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (summary == null) {
                StatsEmptyState(
                    icon = Icons.Outlined.History,
                    title = stringResource(R.string.stats_empty_no_habits_title),
                    subtitle = stringResource(R.string.stats_empty_no_habits_subtitle)
                )
            } else {
                val emDash = stringResource(R.string.stats_em_dash)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    HabitMetric(
                        icon = Icons.Outlined.History,
                        label = stringResource(R.string.stats_habit_total_sessions),
                        value = summary.totalSessions.toString()
                    )
                    HabitMetric(
                        icon = Icons.Outlined.Hearing,
                        label = stringResource(R.string.stats_habit_avg_session),
                        value = formatListeningDurationCompact(summary.averageSessionDurationMs)
                    )
                    HabitMetric(
                        icon = Icons.Outlined.Bolt,
                        label = stringResource(R.string.stats_habit_longest_session),
                        value = if (summary.longestSessionDurationMs > 0L) {
                            formatListeningDurationCompact(summary.longestSessionDurationMs)
                        } else {
                            emDash
                        }
                    )
                    HabitMetric(
                        icon = Icons.Outlined.AutoGraph,
                        label = stringResource(R.string.stats_habit_sessions_per_day),
                        value = String.format(Locale.US, "%.1f", summary.averageSessionsPerDay)
                    )
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                HighlightRow(
                    title = stringResource(R.string.stats_habit_most_active_day),
                    value = summary.peakDayLabel ?: emDash,
                    supporting = if (summary.peakDayDurationMs > 0L) {
                        formatListeningDurationCompact(summary.peakDayDurationMs)
                    } else {
                        stringResource(R.string.stats_habit_no_playback_yet)
                    },
                    icon = Icons.Outlined.CalendarMonth
                )
                summary.peakTimeline?.let { peak ->
                    val use24Hour = AndroidDateFormat.is24HourFormat(LocalContext.current)
                    HighlightRow(
                        title = stringResource(R.string.stats_habit_peak_timeline_slot),
                        value = formatTimelineLabelForRange(peak.label, summary.range, emDash, use24Hour),
                        supporting = formatListeningDurationCompact(peak.totalDurationMs),
                        icon = Icons.Outlined.AutoGraph
                    )
                }
            }
>>>>>>> origin/main
        }
    }
}

@Composable
<<<<<<< HEAD
private fun HabitTile(
    icon: ImageVector,
    iconShape: Shape,
    iconContainer: Color,
    onIconContainer: Color,
    value: String,
    label: String,
    supporting: String?,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(iconShape)
                .background(iconContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = onIconContainer,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(
            Modifier
                .weight(1f)
                .heightIn(min = 18.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = rememberStatsFlexFont(weight = 750, width = 110f),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontFamily = rememberStatsFlexFont(weight = 560, width = 102f),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (supporting != null) {
            Text(
                text = supporting,
                modifier = Modifier.padding(top = 2.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
=======
private fun HabitMetric(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
>>>>>>> origin/main
            )
        }
    }
}

<<<<<<< HEAD
// endregion
=======
private fun formatMinutesWindowLabel(startMinute: Int, endMinute: Int): String {
    val safeStart = startMinute.coerceIn(0, 24 * 60)
    val safeEnd = endMinute.coerceIn(0, 24 * 60)
    return "${formatHourLabel(safeStart)} – ${formatHourLabel(safeEnd)}"
}

private fun formatHourLabel(minute: Int): String {
    val normalized = minute.coerceIn(0, 24 * 60)
    val hours = normalized / 60
    val mins = normalized % 60
    return String.format(Locale.getDefault(), "%02d:%02d", hours % 24, mins)
}
>>>>>>> origin/main

@Composable
private fun HighlightRow(
    title: String,
    value: String,
    supporting: String,
    icon: ImageVector
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = supporting,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private enum class TimelineMetric(
    @StringRes val displayNameRes: Int,
    @StringRes val descriptionRes: Int,
    val extractValue: (PlaybackStatsRepository.TimelineEntry) -> Double,
) {
    ListeningTime(
<<<<<<< HEAD
        displayNameRes = R.string.presentation_batch_g_stats_timeline_metric_listening_time,
        descriptionRes = R.string.presentation_batch_g_stats_timeline_metric_listening_time_desc,
        extractValue = { it.totalDurationMs.toDouble() },
    ),
    PlayCount(
        displayNameRes = R.string.presentation_batch_g_stats_timeline_metric_play_count,
        descriptionRes = R.string.presentation_batch_g_stats_timeline_metric_play_count_desc,
        extractValue = { it.playCount.toDouble() },
    ),
    AverageSession(
        displayNameRes = R.string.presentation_batch_g_stats_timeline_metric_avg_session,
        descriptionRes = R.string.presentation_batch_g_stats_timeline_metric_avg_session_desc,
=======
        displayNameRes = R.string.stats_timeline_metric_listening_time,
        descriptionRes = R.string.stats_timeline_metric_listening_time_desc,
        extractValue = { it.totalDurationMs.toDouble() },
    ),
    PlayCount(
        displayNameRes = R.string.stats_timeline_metric_play_count,
        descriptionRes = R.string.stats_timeline_metric_play_count_desc,
        extractValue = { it.playCount.toDouble() },
    ),
    AverageSession(
        displayNameRes = R.string.stats_timeline_metric_avg_session,
        descriptionRes = R.string.stats_timeline_metric_avg_session_desc,
>>>>>>> origin/main
        extractValue = { entry ->
            if (entry.playCount > 0) entry.totalDurationMs.toDouble() / entry.playCount.toDouble() else 0.0
        },
    )
}

@Composable
private fun TimelineMetric.formatEntryValue(entry: PlaybackStatsRepository.TimelineEntry): String = when (this) {
    TimelineMetric.ListeningTime -> formatListeningDurationCompact(entry.totalDurationMs)
<<<<<<< HEAD
    TimelineMetric.PlayCount -> stringResource(R.string.presentation_batch_g_stats_n_plays, entry.playCount)
=======
    TimelineMetric.PlayCount -> stringResource(R.string.stats_n_plays, entry.playCount)
>>>>>>> origin/main
    TimelineMetric.AverageSession -> {
        val average = if (entry.playCount > 0) entry.totalDurationMs / entry.playCount else 0L
        formatListeningDurationCompact(average)
    }
}

<<<<<<< HEAD
=======
private enum class CategoryDimension(
    @StringRes val displayNameRes: Int,
    @StringRes val cardTitleRes: Int,
) {
    Genre(
        displayNameRes = R.string.stats_dim_genre,
        cardTitleRes = R.string.stats_card_listening_by_genre,
    ),
    Artist(
        displayNameRes = R.string.stats_dim_artist,
        cardTitleRes = R.string.stats_card_listening_by_artist,
    ),
    Album(
        displayNameRes = R.string.stats_dim_album,
        cardTitleRes = R.string.stats_card_listening_by_album,
    ),
    Song(
        displayNameRes = R.string.stats_dim_song,
        cardTitleRes = R.string.stats_card_listening_by_song,
    )
}

private data class CategoryMetricEntry(
    val label: String,
    val durationMs: Long,
    val supporting: String
)

>>>>>>> origin/main
private enum class TimelineChartLayout {
    Vertical,
    Horizontal
}

private data class TimelineChartSpec(
    val layout: TimelineChartLayout,
    val minItemWidth: Dp,
    val maxItemWidth: Dp,
    val maxVisibleItems: Int,
    val chartHeight: Dp,
    val labelMaxLines: Int,
    val horizontalContentPadding: Dp
)

<<<<<<< HEAD
=======
private data class CategoryChartPalette(
    val containerColor: Color,
    val contentColor: Color,
    val accentColor: Color,
    val accentOnColor: Color
)

private data class TrackShareSlice(
    val label: String,
    val durationMs: Long,
    val color: Color
)

>>>>>>> origin/main
@OptIn(ExperimentalTextApi::class)
@Composable
private fun rememberStatsSectionTitleStyle(): TextStyle {
    return remember {
        androidx.compose.ui.text.TextStyle(
            fontFamily = FontFamily(
                Font(
                    resId = R.font.gflex_variable,
                    variationSettings = FontVariation.Settings(
                        FontVariation.weight(570)
                    )
                )
            ),
            fontWeight = FontWeight(570),
            fontSize = 24.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.2).sp
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun rememberStatsAxisLabelStyle(range: StatsTimeRange): TextStyle {
    val fontSize = when (range) {
        StatsTimeRange.DAY, StatsTimeRange.WEEK -> 10.sp
        StatsTimeRange.MONTH, StatsTimeRange.ALL -> 11.sp
        StatsTimeRange.YEAR -> 10.sp
    }
    return remember(range, fontSize) {
        TextStyle(
            fontFamily = FontFamily(
                Font(
                    resId = R.font.gflex_variable,
                    variationSettings = FontVariation.Settings(
                        FontVariation.weight(520)
                    )
                )
            ),
            fontWeight = FontWeight(520),
            fontSize = fontSize,
            lineHeight = if (range == StatsTimeRange.YEAR) 12.sp else 11.sp,
            letterSpacing = 0.sp
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun rememberStatsMetricValueStyle(compact: Boolean): TextStyle {
    return remember(compact) {
        TextStyle(
            fontFamily = FontFamily(
                Font(
                    resId = R.font.genre_variable,
                    variationSettings = FontVariation.Settings(
                        FontVariation.weight(650),
                        FontVariation.width(95f),
                        FontVariation.grade(30)
                    )
                )
            ),
            fontWeight = FontWeight(650),
            fontSize = if (compact) 10.sp else 12.sp,
            lineHeight = if (compact) 12.sp else 14.sp,
            letterSpacing = 0.sp
        )
    }
}

@Composable
private fun ListeningTimelineSection(
    summary: PlaybackStatsRepository.PlaybackStatsSummary?,
    selectedMetric: TimelineMetric,
    onMetricSelected: (TimelineMetric) -> Unit,
    modifier: Modifier = Modifier
) {
    val range = summary?.range ?: StatsTimeRange.WEEK
    val timeline = remember(summary) { summary?.timeline.orEmpty().toImmutableList() }
    val hasTimeline = timeline.isNotEmpty() && timeline.any { it.totalDurationMs > 0L || it.playCount > 0 }
    val sectionTitleStyle = rememberStatsSectionTitleStyle()
<<<<<<< HEAD
    val emDash = stringResource(R.string.presentation_batch_g_stats_em_dash)
=======
    val emDash = stringResource(R.string.stats_em_dash)
>>>>>>> origin/main
    val use24Hour = AndroidDateFormat.is24HourFormat(LocalContext.current)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
<<<<<<< HEAD
                text = stringResource(R.string.presentation_batch_g_stats_section_listening_timeline),
=======
                text = stringResource(R.string.stats_section_listening_timeline),
>>>>>>> origin/main
                style = sectionTitleStyle,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = timelineSupportingCopy(selectedMetric = selectedMetric, range = range),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            TimelineMetric.entries.forEach { metric ->
                val isSelected = metric == selectedMetric
                ToggleButton(
                    checked = isSelected,
                    onCheckedChange = { onMetricSelected(metric) },
<<<<<<< HEAD
                    colors = ToggleButtonDefaults.toggleButtonColors(
=======
                    colors = ToggleButtonDefaults.colors(
>>>>>>> origin/main
                        checkedContainerColor = MaterialTheme.colorScheme.primary,
                        checkedContentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(
                        text = stringResource(metric.displayNameRes),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        if (!hasTimeline) {
            StatsEmptyState(
                icon = Icons.Outlined.PlayCircleOutline,
<<<<<<< HEAD
                title = stringResource(R.string.presentation_batch_g_stats_empty_no_timeline_title),
                subtitle = stringResource(R.string.presentation_batch_g_stats_empty_no_timeline_subtitle)
=======
                title = stringResource(R.string.stats_empty_no_timeline_title),
                subtitle = stringResource(R.string.stats_empty_no_timeline_subtitle)
>>>>>>> origin/main
            )
        } else {
            val cardColor = when (range) {
                StatsTimeRange.DAY, StatsTimeRange.WEEK -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                StatsTimeRange.MONTH, StatsTimeRange.YEAR -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.42f)
                StatsTimeRange.ALL -> MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.40f)
            }
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = when (range) {
<<<<<<< HEAD
                                    StatsTimeRange.DAY -> stringResource(R.string.presentation_batch_g_stats_rhythm_daily)
                                    StatsTimeRange.WEEK -> stringResource(R.string.presentation_batch_g_stats_rhythm_weekly)
                                    StatsTimeRange.MONTH -> stringResource(R.string.presentation_batch_g_stats_rhythm_monthly)
                                    StatsTimeRange.YEAR -> stringResource(R.string.presentation_batch_g_stats_rhythm_year)
                                    StatsTimeRange.ALL -> stringResource(R.string.presentation_batch_g_stats_rhythm_all_time)
=======
                                    StatsTimeRange.DAY -> stringResource(R.string.stats_rhythm_daily)
                                    StatsTimeRange.WEEK -> stringResource(R.string.stats_rhythm_weekly)
                                    StatsTimeRange.MONTH -> stringResource(R.string.stats_rhythm_monthly)
                                    StatsTimeRange.YEAR -> stringResource(R.string.stats_rhythm_year)
                                    StatsTimeRange.ALL -> stringResource(R.string.stats_rhythm_all_time)
>>>>>>> origin/main
                                },
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = when (range) {
<<<<<<< HEAD
                                    StatsTimeRange.DAY -> stringResource(R.string.presentation_batch_g_stats_grouped_4h)
                                    StatsTimeRange.WEEK -> stringResource(R.string.presentation_batch_g_stats_grouped_dow)
                                    StatsTimeRange.MONTH -> stringResource(R.string.presentation_batch_g_stats_grouped_week_of_month)
                                    StatsTimeRange.YEAR -> stringResource(R.string.presentation_batch_g_stats_grouped_by_month)
                                    StatsTimeRange.ALL -> stringResource(R.string.presentation_batch_g_stats_grouped_by_year)
=======
                                    StatsTimeRange.DAY -> stringResource(R.string.stats_grouped_4h)
                                    StatsTimeRange.WEEK -> stringResource(R.string.stats_grouped_dow)
                                    StatsTimeRange.MONTH -> stringResource(R.string.stats_grouped_week_of_month)
                                    StatsTimeRange.YEAR -> stringResource(R.string.stats_grouped_by_month)
                                    StatsTimeRange.ALL -> stringResource(R.string.stats_grouped_by_year)
>>>>>>> origin/main
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        TimelineMetricBadge(metric = selectedMetric)
                    }

                    TimelineBarChart(
                        entries = timeline,
                        metric = selectedMetric,
                        range = range,
                        blankLabel = emDash,
                        use24Hour = use24Hour
                    )
                }
            }

            summary?.peakTimeline?.let { peak ->
                HighlightRow(
<<<<<<< HEAD
                    title = stringResource(R.string.presentation_batch_g_stats_peak_segment),
                    value = formatTimelineLabelForRange(peak.label, range, emDash, use24Hour),
                    supporting = when (selectedMetric) {
                        TimelineMetric.ListeningTime -> formatListeningDurationCompact(peak.totalDurationMs)
                        TimelineMetric.PlayCount -> stringResource(R.string.presentation_batch_g_stats_n_plays, peak.playCount)
=======
                    title = stringResource(R.string.stats_peak_segment),
                    value = formatTimelineLabelForRange(peak.label, range, emDash, use24Hour),
                    supporting = when (selectedMetric) {
                        TimelineMetric.ListeningTime -> formatListeningDurationCompact(peak.totalDurationMs)
                        TimelineMetric.PlayCount -> stringResource(R.string.stats_n_plays, peak.playCount)
>>>>>>> origin/main
                        TimelineMetric.AverageSession -> {
                            val average = if (peak.playCount > 0) peak.totalDurationMs / peak.playCount else 0L
                            formatListeningDurationCompact(average)
                        }
                    },
                    icon = Icons.Outlined.AutoGraph
                )
            }
        }
    }
}

<<<<<<< HEAD
=======
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoryMetricsSection(
    summary: PlaybackStatsRepository.PlaybackStatsSummary?,
    selectedDimension: CategoryDimension,
    onDimensionSelected: (CategoryDimension) -> Unit,
    modifier: Modifier = Modifier
) {
    val palette = categoryPaletteFor(selectedDimension)
    val sectionTitleStyle = rememberStatsSectionTitleStyle()
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.stats_section_top_categories),
                style = sectionTitleStyle,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(R.string.stats_top_categories_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            CategoryDimension.entries.reversed().forEach { dimension ->
                val isSelected = dimension == selectedDimension
                val chipPalette = categoryPaletteFor(dimension)
                ToggleButton(
                    checked = isSelected,
                    onCheckedChange = { onDimensionSelected(dimension) },
                    colors = ToggleButtonDefaults.colors(
                        checkedContainerColor = chipPalette.accentColor,
                        checkedContentColor = chipPalette.accentOnColor,
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(
                        text = stringResource(dimension.displayNameRes),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        val entries = when (selectedDimension) {
            CategoryDimension.Genre -> summary?.topGenres.orEmpty().map {
                CategoryMetricEntry(
                    label = it.genre,
                    durationMs = it.totalDurationMs,
                    supporting = stringResource(
                        R.string.stats_plays_artists,
                        it.playCount,
                        it.uniqueArtists
                    )
                )
            }

            CategoryDimension.Artist -> summary?.topArtists.orEmpty().map {
                CategoryMetricEntry(
                    label = it.artist,
                    durationMs = it.totalDurationMs,
                    supporting = stringResource(
                        R.string.stats_plays_tracks,
                        it.playCount,
                        it.uniqueSongs
                    )
                )
            }

            CategoryDimension.Album -> summary?.topAlbums.orEmpty().map {
                CategoryMetricEntry(
                    label = it.album,
                    durationMs = it.totalDurationMs,
                    supporting = stringResource(
                        R.string.stats_plays_tracks,
                        it.playCount,
                        it.uniqueSongs
                    )
                )
            }

            CategoryDimension.Song -> summary?.topSongs.orEmpty().map {
                val supportingParts = buildList {
                    add(stringResource(R.string.stats_n_plays, it.playCount))
                    if (it.artist.isNotBlank()) {
                        add(it.artist)
                    }
                }
                CategoryMetricEntry(
                    label = it.title,
                    durationMs = it.totalDurationMs,
                    supporting = supportingParts.joinToString(separator = " • ")
                )
            }
        }.filter { it.durationMs > 0L }.toImmutableList()

        if (entries.isEmpty()) {
            StatsEmptyState(
                icon = Icons.Outlined.MusicNote,
                title = stringResource(R.string.stats_empty_no_category_title),
                subtitle = stringResource(R.string.stats_empty_no_category_subtitle)
            )
        } else {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = palette.containerColor)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = stringResource(selectedDimension.cardTitleRes),
                        style = MaterialTheme.typography.titleLargeEmphasized,
                        color = palette.contentColor
                    )
                    CategoryHorizontalBarChart(entries = entries, palette = palette)
                }
            }
        }
    }
}

@Composable
private fun CategoryHorizontalBarChart(
    entries: ImmutableList<CategoryMetricEntry>,
    palette: CategoryChartPalette,
    modifier: Modifier = Modifier
) {
    if (entries.isEmpty()) return
    val maxDuration = entries.maxOf { it.durationMs }.coerceAtLeast(1L)
    val metricStyle = rememberStatsMetricValueStyle(compact = false)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        entries.forEachIndexed { index, entry ->
            val progress = (entry.durationMs.toFloat() / maxDuration.toFloat()).coerceIn(0f, 1f)
            val isTop = index == 0
            val rowColor = if (isTop) {
                palette.accentColor.copy(alpha = 0.16f)
            } else {
                palette.contentColor.copy(alpha = 0.06f)
            }
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = rowColor
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CategoryRankBadge(
                            rank = index + 1,
                            accentColor = palette.accentColor,
                            accentOnColor = palette.accentOnColor,
                            highlighted = isTop
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = entry.label,
                                style = MaterialTheme.typography.titleSmall,
                                color = palette.contentColor,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (entry.supporting.isNotBlank()) {
                                Text(
                                    text = entry.supporting,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = palette.contentColor.copy(alpha = 0.76f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Text(
                            text = formatListeningDurationCompact(entry.durationMs),
                            style = metricStyle,
                            fontWeight = if (isTop) FontWeight.SemiBold else FontWeight.Medium,
                            color = palette.contentColor
                        )
                    }

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = if (isTop) palette.accentColor else palette.accentColor.copy(alpha = 0.74f),
                        trackColor = palette.contentColor.copy(alpha = 0.18f)
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryRankBadge(
    rank: Int,
    accentColor: Color,
    accentOnColor: Color,
    highlighted: Boolean
) {
    val metricStyle = rememberStatsMetricValueStyle(compact = false)
    val containerColor = if (highlighted) {
        accentColor
    } else {
        accentColor.copy(alpha = 0.24f)
    }
    val contentColor = if (highlighted) {
        accentOnColor
    } else {
        accentColor
    }
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = containerColor,
        contentColor = contentColor
    ) {
        Box(
            modifier = Modifier
                .defaultMinSize(minWidth = 34.dp, minHeight = 34.dp)
                .padding(horizontal = 10.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rank.toString(),
                style = metricStyle,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

>>>>>>> origin/main
@Composable
private fun TimelineMetricBadge(
    metric: TimelineMetric,
    modifier: Modifier = Modifier
) {
    val (containerColor, contentColor) = when (metric) {
        TimelineMetric.ListeningTime -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        TimelineMetric.PlayCount -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
        TimelineMetric.AverageSession -> MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer
    }
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = containerColor,
        contentColor = contentColor
    ) {
        Text(
            text = stringResource(metric.displayNameRes),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun TimelineBarChart(
    entries: ImmutableList<PlaybackStatsRepository.TimelineEntry>,
    metric: TimelineMetric,
    range: StatsTimeRange,
    blankLabel: String,
    use24Hour: Boolean = false,
    modifier: Modifier = Modifier
) {
    if (entries.isEmpty()) return
    val spec = timelineChartSpecFor(range = range, entryCount = entries.size)

    when (spec.layout) {
        TimelineChartLayout.Vertical -> VerticalTimelineBarChart(
            entries = entries,
            metric = metric,
            range = range,
            spec = spec,
            blankLabel = blankLabel,
            use24Hour = use24Hour,
            modifier = modifier
        )

        TimelineChartLayout.Horizontal -> HorizontalTimelineBarChart(
            entries = entries,
            metric = metric,
            range = range,
            spec = spec,
            blankLabel = blankLabel,
            use24Hour = use24Hour,
            modifier = modifier
        )
    }
}

@Composable
private fun VerticalTimelineBarChart(
    entries: ImmutableList<PlaybackStatsRepository.TimelineEntry>,
    metric: TimelineMetric,
    range: StatsTimeRange,
    spec: TimelineChartSpec,
    blankLabel: String,
    use24Hour: Boolean = false,
    modifier: Modifier = Modifier
) {
    val maxMetricValue = entries.maxOfOrNull { metric.extractValue(it) }?.coerceAtLeast(0.0) ?: 0.0
    val hasNonZeroValues = maxMetricValue > 0.0
    val axisLabelStyle = rememberStatsAxisLabelStyle(range)
    val metricValueStyle = rememberStatsMetricValueStyle(compact = true)

    val highlightColor = when (range) {
        StatsTimeRange.DAY, StatsTimeRange.WEEK -> MaterialTheme.colorScheme.primary
        StatsTimeRange.MONTH, StatsTimeRange.YEAR -> MaterialTheme.colorScheme.secondary
        StatsTimeRange.ALL -> MaterialTheme.colorScheme.tertiary
    }
    val regularColor = highlightColor.copy(alpha = 0.72f)
    val trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.10f)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val spacing = 10.dp
            val itemCount = entries.size.coerceAtLeast(1)
            val innerWidth = (maxWidth - (spec.horizontalContentPadding * 2)).coerceAtLeast(0.dp)
            val spacingTotal = spacing * (itemCount - 1).coerceAtLeast(0)
            val minimumChartWidth = (spec.minItemWidth * itemCount) + spacingTotal
            val needsHorizontalScroll = minimumChartWidth > innerWidth || entries.size > spec.maxVisibleItems
            val fittedItemWidth = ((innerWidth - spacingTotal) / itemCount).coerceIn(spec.minItemWidth, spec.maxItemWidth)
            val itemWidth = if (needsHorizontalScroll) spec.minItemWidth else fittedItemWidth

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(spec.chartHeight),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
                    verticalAlignment = Alignment.Bottom,
                    userScrollEnabled = needsHorizontalScroll,
                    contentPadding = PaddingValues(horizontal = spec.horizontalContentPadding)
                ) {
                    itemsIndexed(
                        items = entries,
                        key = { index, entry -> "${entry.label}-$index" }
                    ) { _, entry ->
                        val value = metric.extractValue(entry).coerceAtLeast(0.0)
                        val progress = if (maxMetricValue > 0.0) (value / maxMetricValue).toFloat().coerceIn(0f, 1f) else 0f
                        val isPeak = hasNonZeroValues && (maxMetricValue - value) <= maxMetricValue * 0.01
                        val label = formatTimelineLabelForRange(entry.label, range, blankLabel, use24Hour)

                        Column(
                            modifier = Modifier.width(itemWidth),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = metric.formatEntryValue(entry),
                                style = metricValueStyle,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .clip(CircleShape)
                                    .background(trackColor),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(if (progress == 0f) 0f else progress)
                                        .clip(CircleShape)
                                        .background(if (isPeak) highlightColor else regularColor)
                                )
                            }
                            Text(
                                text = label,
                                style = axisLabelStyle,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = spec.labelMaxLines,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HorizontalTimelineBarChart(
    entries: ImmutableList<PlaybackStatsRepository.TimelineEntry>,
    metric: TimelineMetric,
    range: StatsTimeRange,
    spec: TimelineChartSpec,
    blankLabel: String,
    use24Hour: Boolean = false,
    modifier: Modifier = Modifier
) {
    val maxMetricValue = entries.maxOfOrNull { metric.extractValue(it) }?.coerceAtLeast(0.0) ?: 0.0
    val hasNonZeroValues = maxMetricValue > 0.0
    val axisLabelStyle = rememberStatsAxisLabelStyle(range)
    val metricValueStyle = rememberStatsMetricValueStyle(compact = true)
    val highlightColor = when (range) {
        StatsTimeRange.DAY, StatsTimeRange.WEEK -> MaterialTheme.colorScheme.primary
        StatsTimeRange.MONTH, StatsTimeRange.YEAR -> MaterialTheme.colorScheme.secondary
        StatsTimeRange.ALL -> MaterialTheme.colorScheme.tertiary
    }
    val regularColor = highlightColor.copy(alpha = 0.72f)
    val trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.10f)
    val edgeContentPadding = spec.horizontalContentPadding

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        entries.forEach { entry ->
            val value = metric.extractValue(entry).coerceAtLeast(0.0)
            val progress = if (maxMetricValue > 0.0) (value / maxMetricValue).toFloat().coerceIn(0f, 1f) else 0f
            val isPeak = hasNonZeroValues && (maxMetricValue - value) <= maxMetricValue * 0.01
            val label = formatTimelineLabelForRange(entry.label, range, blankLabel, use24Hour)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(edgeContentPadding))
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = label,
                        modifier = Modifier.widthIn(min = 72.dp, max = 96.dp),
                        style = axisLabelStyle,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(26.dp)
                            .clip(CircleShape)
                            .background(trackColor),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(if (progress == 0f) 0f else progress)
                                .clip(CircleShape)
                                .background(if (isPeak) highlightColor else regularColor)
                        )
                    }
                    Text(
                        text = metric.formatEntryValue(entry),
                        modifier = Modifier.widthIn(min = 58.dp),
                        style = metricValueStyle,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.End,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.width(edgeContentPadding))
            }
        }
    }
}

@Composable
private fun timelineSupportingCopy(
    selectedMetric: TimelineMetric,
    range: StatsTimeRange
): String {
    val rangeCopy = stringResource(
        when (range) {
<<<<<<< HEAD
            StatsTimeRange.DAY -> R.string.presentation_batch_g_stats_timeline_support_day
            StatsTimeRange.WEEK -> R.string.presentation_batch_g_stats_timeline_support_week
            StatsTimeRange.MONTH -> R.string.presentation_batch_g_stats_timeline_support_month
            StatsTimeRange.YEAR -> R.string.presentation_batch_g_stats_timeline_support_year
            StatsTimeRange.ALL -> R.string.presentation_batch_g_stats_timeline_support_all
=======
            StatsTimeRange.DAY -> R.string.stats_timeline_support_day
            StatsTimeRange.WEEK -> R.string.stats_timeline_support_week
            StatsTimeRange.MONTH -> R.string.stats_timeline_support_month
            StatsTimeRange.YEAR -> R.string.stats_timeline_support_year
            StatsTimeRange.ALL -> R.string.stats_timeline_support_all
>>>>>>> origin/main
        }
    )
    return stringResource(selectedMetric.descriptionRes) + " " + rangeCopy
}

private fun formatTimelineLabelForRange(
    rawLabel: String,
    range: StatsTimeRange,
    blankLabel: String,
    use24Hour: Boolean = false
): String {
    val label = rawLabel.trim()
    if (label.isBlank()) return blankLabel
    return when (range) {
        StatsTimeRange.DAY -> convertHourLabel(label, use24Hour)
<<<<<<< HEAD
        StatsTimeRange.YEAR -> shortMonthLabel(label, blankLabel)
=======
        StatsTimeRange.YEAR -> monthThreeLetters(label, blankLabel)
>>>>>>> origin/main
        else -> label
    }
}

/**
 * Converts any recognisable hour label to the correct display format.
 *
 * Handles:
 *   "7 AM", "7AM", "7 am", "7am"
 *   "7 PM", "7PM", "7 pm", "7pm"
 *   "7:00 AM", "7:00AM", "7:00 am", "7:00am"  (and PM variants)
 *   "07:00", "19:00"  (already 24h — returned as-is when use24Hour=true,
 *                      converted to 12h when use24Hour=false)
 *
 * If nothing matches the original trimmed label is returned unchanged.
 */
private fun convertHourLabel(label: String, use24Hour: Boolean): String {
    // --- attempt 1: "H am/pm" or "H:MM am/pm" ---
    val amPmMatch = Regex("(?i)^(\\d{1,2})(?::(\\d{2}))?\\s*(am|pm)$").matchEntire(label)
    if (amPmMatch != null) {
        val hour12 = amPmMatch.groupValues[1].toIntOrNull() ?: return label
        val isPm = amPmMatch.groupValues[3].equals("pm", ignoreCase = true)
        val hour24 = when {
            isPm && hour12 != 12 -> hour12 + 12
            !isPm && hour12 == 12 -> 0
            else -> hour12
        }
        return if (use24Hour) {
            String.format(Locale.getDefault(), "%02d:00", hour24)
        } else {
            val time = java.time.LocalTime.of(hour24, 0)
            val formatter = java.time.format.DateTimeFormatter.ofPattern("h a", Locale.getDefault())
            time.format(formatter)
        }
    }

    // --- attempt 2: already "HH:MM" 24h format ---
    val h24Match = Regex("^(\\d{1,2}):(\\d{2})$").matchEntire(label)
    if (h24Match != null) {
        val hour24 = h24Match.groupValues[1].toIntOrNull() ?: return label
        return if (use24Hour) {
            String.format(Locale.getDefault(), "%02d:00", hour24)
        } else {
            val time = java.time.LocalTime.of(hour24, 0)
            val formatter = java.time.format.DateTimeFormatter.ofPattern("h a", Locale.getDefault())
            time.format(formatter)
        }
    }

    // --- attempt 3: bare integer hour, e.g. "7" or "19" ---
    val bareHour = label.toIntOrNull()
    if (bareHour != null && bareHour in 0..23) {
        return if (use24Hour) {
            String.format(Locale.getDefault(), "%02d:00", bareHour)
        } else {
            val time = java.time.LocalTime.of(bareHour, 0)
            val formatter = java.time.format.DateTimeFormatter.ofPattern("h a", Locale.getDefault())
            time.format(formatter)
        }
    }

    return label
}

<<<<<<< HEAD
/**
 * Month labels come localized from the repository, so they are not cut to three letters:
 * that turns French "juin" and "juil." into the same "jui" and splits Arabic words. Only the
 * abbreviation dot is dropped; the YEAR chart wraps long names onto a second line.
 */
private fun shortMonthLabel(label: String, blankLabel: String): String {
    val cleaned = label.trim().trimEnd('.')
    if (cleaned.isEmpty()) return blankLabel
    return cleaned
=======
private fun monthThreeLetters(label: String, blankLabel: String): String {
    val cleaned = label.trim()
    if (cleaned.isEmpty()) return blankLabel
    return cleaned.take(3)
>>>>>>> origin/main
}

private fun timelineChartSpecFor(
    range: StatsTimeRange,
    entryCount: Int
): TimelineChartSpec {
    return when (range) {
        StatsTimeRange.DAY -> TimelineChartSpec(
            layout = TimelineChartLayout.Vertical,
            minItemWidth = 52.dp,
            maxItemWidth = 72.dp,
            maxVisibleItems = 5,
            chartHeight = 224.dp,
            labelMaxLines = 1,
            horizontalContentPadding = 20.dp
        )

        StatsTimeRange.WEEK -> TimelineChartSpec(
            layout = TimelineChartLayout.Vertical,
            minItemWidth = 50.dp,
            maxItemWidth = 68.dp,
            maxVisibleItems = 6,
            chartHeight = 224.dp,
            labelMaxLines = 1,
            horizontalContentPadding = 20.dp
        )

        StatsTimeRange.MONTH -> TimelineChartSpec(
            layout = TimelineChartLayout.Vertical,
            minItemWidth = 62.dp,
            maxItemWidth = 82.dp,
            maxVisibleItems = 4,
            chartHeight = 232.dp,
<<<<<<< HEAD
            // Localized week labels such as "Settimana 1" can be wider than a bar.
            labelMaxLines = 2,
=======
            labelMaxLines = 1,
>>>>>>> origin/main
            horizontalContentPadding = 16.dp
        )

        StatsTimeRange.YEAR -> TimelineChartSpec(
            layout = TimelineChartLayout.Vertical,
            minItemWidth = 56.dp,
            maxItemWidth = 68.dp,
            maxVisibleItems = 5,
            chartHeight = 236.dp,
            labelMaxLines = 2,
            horizontalContentPadding = 16.dp
        )

        StatsTimeRange.ALL -> {
            val isSparse = entryCount <= 4
            TimelineChartSpec(
                layout = TimelineChartLayout.Vertical,
                minItemWidth = if (isSparse) 62.dp else 56.dp,
                maxItemWidth = if (isSparse) 78.dp else 66.dp,
                maxVisibleItems = if (isSparse) 4 else 6,
                chartHeight = 228.dp,
                labelMaxLines = 1,
                horizontalContentPadding = 16.dp
            )
        }
    }
}

<<<<<<< HEAD
// region Heavy rotation

private enum class RotationDimension(
    @StringRes val labelRes: Int,
    @StringRes val emptyTitleRes: Int,
    @StringRes val emptySubtitleRes: Int,
) {
    Tracks(
        R.string.presentation_batch_g_stats_tab_tracks,
        R.string.presentation_batch_g_stats_empty_no_tracks_title,
        R.string.presentation_batch_g_stats_empty_no_tracks_subtitle
    ),
    Artists(
        R.string.presentation_batch_g_stats_tab_artists,
        R.string.presentation_batch_g_stats_empty_no_artists_title,
        R.string.presentation_batch_g_stats_empty_no_artists_subtitle
    ),
    Albums(
        R.string.presentation_batch_g_stats_tab_albums,
        R.string.presentation_batch_g_stats_empty_no_albums_title,
        R.string.presentation_batch_g_stats_empty_no_albums_subtitle
    ),
    Genres(
        R.string.presentation_batch_g_stats_tab_genres,
        R.string.presentation_batch_g_stats_empty_no_category_title,
        R.string.presentation_batch_g_stats_empty_no_category_subtitle
    );

    val emptyIcon: ImageVector
        get() = when (this) {
            Tracks -> Icons.Outlined.MusicNote
            Artists -> Icons.Outlined.Person
            Albums -> Icons.Outlined.Album
            Genres -> Icons.Outlined.Category
        }
}

/**
 * One ranked row of the heavy-rotation hub, flattened from whichever summary list backs the
 * selected tab. [key] is the song id for tracks and the display name for everything else.
 */
@Immutable
private data class RotationEntry(
    val key: String,
    val dimension: RotationDimension,
    val title: String,
    val artist: String?,
    val artUri: String?,
    val durationMs: Long,
    val playCount: Int,
    /** Distinct tracks for artists and albums, distinct artists for genres. */
    val childCount: Int,
)

private fun buildRotationEntries(
    summary: PlaybackStatsSummary,
    dimension: RotationDimension,
    artistImages: Map<String, String>
): List<RotationEntry> {
    val entries = when (dimension) {
        RotationDimension.Tracks -> summary.topSongs.ifEmpty { summary.songs }.map {
            RotationEntry(
                key = it.songId,
                dimension = dimension,
                title = it.title,
                artist = it.artist,
                artUri = it.albumArtUri,
                durationMs = it.totalDurationMs,
                playCount = it.playCount,
                childCount = 1
            )
        }

        RotationDimension.Artists -> {
            // The artist's own picture wins; artists without one borrow the cover of their
            // most-played track before falling back to initials.
            val coverByArtist = summary.songs
                .asSequence()
                .filter { !it.albumArtUri.isNullOrBlank() }
                .distinctBy { it.artist }
                .associate { it.artist to it.albumArtUri }
            summary.topArtists.map {
                RotationEntry(
                    key = it.artist,
                    dimension = dimension,
                    title = it.artist,
                    artist = null,
                    artUri = artistImages[it.artist] ?: coverByArtist[it.artist],
                    durationMs = it.totalDurationMs,
                    playCount = it.playCount,
                    childCount = it.uniqueSongs
                )
            }
        }

        RotationDimension.Albums -> summary.topAlbums.map {
            RotationEntry(
                key = it.album,
                dimension = dimension,
                title = it.album,
                artist = null,
                artUri = it.albumArtUri,
                durationMs = it.totalDurationMs,
                playCount = it.playCount,
                childCount = it.uniqueSongs
            )
        }

        RotationDimension.Genres -> summary.topGenres.map {
            RotationEntry(
                key = it.genre,
                dimension = dimension,
                title = it.genre,
                artist = null,
                artUri = null,
                durationMs = it.totalDurationMs,
                playCount = it.playCount,
                childCount = it.uniqueArtists
            )
        }
    }
    return entries.filter { it.durationMs > 0L }.take(HEAVY_ROTATION_LIMIT)
}

@Composable
private fun RotationEntry.supportingText(): String? = when (dimension) {
    RotationDimension.Tracks -> artist?.takeIf { it.isNotBlank() }
    RotationDimension.Artists,
    RotationDimension.Albums -> pluralStringResource(R.plurals.stats_tracks_count, childCount, childCount)
    RotationDimension.Genres -> pluralStringResource(R.plurals.stats_artists_count, childCount, childCount)
}

@Composable
private fun HeavyRotationSection(
    summary: PlaybackStatsSummary,
    artistImages: Map<String, String>,
    selected: RotationDimension,
    onSelected: (RotationDimension) -> Unit,
    onEntryClick: (RotationEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptics = LocalHapticFeedback.current
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        StatsSectionHeader(
            title = stringResource(R.string.presentation_batch_g_stats_section_heavy_rotation),
            subtitle = stringResource(R.string.presentation_batch_g_stats_heavy_rotation_subtitle)
        )

        RotationTabSelector(
            selectedTabIndex = selected.ordinal,
            onTabSelected = { index ->
                val dimension = RotationDimension.entries[index]
                if (dimension != selected) {
                    haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
                    onSelected(dimension)
                }
            },
            tabs = RotationDimension.entries.map { stringResource(it.labelRes) }
        )

        AnimatedContent(
            targetState = selected,
            transitionSpec = {
                (fadeIn(tween(durationMillis = 220, delayMillis = 60)) +
                    scaleIn(initialScale = 0.96f, animationSpec = spring(stiffness = Spring.StiffnessMediumLow)))
                    .togetherWith(fadeOut(tween(durationMillis = 90)))
                    .using(SizeTransform(clip = false))
            },
            label = "heavyRotationContent"
        ) { dimension ->
            val entries = remember(summary, dimension, artistImages) {
                buildRotationEntries(summary, dimension, artistImages)
            }
            if (entries.isEmpty()) {
                StatsEmptyState(
                    icon = dimension.emptyIcon,
                    title = stringResource(dimension.emptyTitleRes),
                    subtitle = stringResource(dimension.emptySubtitleRes)
                )
            } else {
                val leader = entries.first()
                val chasers = entries.drop(1)
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    RotationSpotlightCard(entry = leader, onClick = onEntryClick)
                    if (chasers.isNotEmpty()) {
                        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                            chasers.forEachIndexed { index, entry ->
                                RotationRankRow(
                                    entry = entry,
                                    rank = index + 2,
                                    fractionOfLeader = (entry.durationMs.toFloat() / leader.durationMs.coerceAtLeast(1L))
                                        .coerceIn(0f, 1f),
                                    shape = segmentedItemShape(index, chasers.size),
                                    onClick = onEntryClick
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Connected toggle-button tabs that never truncate a label: when every tab fits they fill the
 * width with equal-sized segments, otherwise each segment takes the width its text needs and
 * the whole group scrolls horizontally. The selected tab is scrolled into view.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RotationTabSelector(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    tabs: List<String>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val requesters = remember(tabs.size) { List(tabs.size) { BringIntoViewRequester() } }
    LaunchedEffect(selectedTabIndex, tabs.size) {
        requesters.getOrNull(selectedTabIndex)?.bringIntoView()
    }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val viewportWidth = maxWidth
        val spacingPx = with(LocalDensity.current) { ButtonGroupDefaults.ConnectedSpaceBetween.roundToPx() }
        val tabHeightPx = with(LocalDensity.current) { 52.dp.roundToPx() }

        Layout(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .widthIn(min = viewportWidth),
            content = {
                tabs.forEachIndexed { index, label ->
                    val selected = selectedTabIndex == index
                    ToggleButton(
                        checked = selected,
                        onCheckedChange = { onTabSelected(index) },
                        modifier = Modifier
                            .bringIntoViewRequester(requesters[index])
                            .height(52.dp)
                            .semantics { role = Role.Tab },
                        shapes = when (index) {
                            0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                            tabs.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                            else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                        },
                        colors = ToggleButtonDefaults.toggleButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            checkedContainerColor = MaterialTheme.colorScheme.primary,
                            checkedContentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        elevation = null,
                        contentPadding = PaddingValues(horizontal = 10.dp)
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelLarge,
                            fontFamily = rememberStatsFlexFont(
                                weight = if (selected) 720 else 560,
                                width = if (selected) 122f else 104f,
                                rond = if (selected) 92f else 100f
                            ),
                            maxLines = 1,
                            softWrap = false
                        )
                    }
                }
            }
        ) { measurables, constraints ->
            val count = measurables.size
            if (count == 0) return@Layout layout(constraints.minWidth, 0) {}

            val gaps = spacingPx * (count - 1)
            val natural = measurables.map { it.maxIntrinsicWidth(tabHeightPx) }
            val naturalTotal = natural.sum() + gaps
            val totalWidth = maxOf(naturalTotal, constraints.minWidth)

            // Equal segments (selected one slightly wider) whenever every label fits its share;
            // otherwise natural widths plus any spare room.
            val weights = List(count) { if (it == selectedTabIndex) 1.15f else 1f }
            val weightSum = weights.sum()
            val equal = weights.map { ((totalWidth - gaps) * it / weightSum).toInt() }
            val widths = if (equal.indices.all { equal[it] >= natural[it] }) {
                equal
            } else {
                val spare = totalWidth - naturalTotal
                natural.map { it + spare / count }
            }

            val placeables = measurables.mapIndexed { index, measurable ->
                measurable.measure(Constraints.fixed(widths[index], tabHeightPx))
            }
            layout(totalWidth, tabHeightPx) {
                var x = 0
                placeables.forEach { placeable ->
                    placeable.placeRelative(x, 0)
                    x += placeable.width + spacingPx
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RotationSpotlightCard(
    entry: RotationEntry,
    onClick: (RotationEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptics = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val pressScale = animatedPressScale(interactionSource)
    val container = MaterialTheme.colorScheme.secondaryContainer
    val content = MaterialTheme.colorScheme.onSecondaryContainer
    val badgeShape = MaterialShapes.Cookie9Sided.toShape()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                val scale = pressScale.value
                scaleX = scale
                scaleY = scale
            }
            .clip(AbsoluteSmoothCornerShape(28.dp, 60))
            .background(container)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = content)
            ) {
                haptics.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick(entry)
            }
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(106.dp)) {
            RotationArtwork(
                entry = entry,
                cornerRadius = 22.dp,
                large = true,
                modifier = Modifier
                    .size(98.dp)
                    .align(Alignment.BottomEnd)
            )
            // The ring is the card colour, so the badge reads as cut out of the artwork corner.
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopStart)
                    .clip(badgeShape)
                    .background(container)
                    .padding(3.dp)
                    .clip(badgeShape)
                    .background(MaterialTheme.colorScheme.tertiary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = NumberFormat.getIntegerInstance().format(1),
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = rememberStatsFlexFont(weight = 800, width = 110f),
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(R.string.presentation_batch_g_stats_top_pick),
                style = MaterialTheme.typography.labelMedium,
                fontFamily = rememberStatsFlexFont(weight = 650, width = 110f),
                color = content.copy(alpha = 0.76f)
            )
            Text(
                text = entry.title,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = rememberStatsFlexFont(weight = 720, width = 110f),
                color = content,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            entry.supportingText()?.let { supporting ->
                Text(
                    text = supporting,
                    style = MaterialTheme.typography.bodyMedium,
                    color = content.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(6.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                SpotlightStatChip(text = formatListeningDurationCompact(entry.durationMs), contentColor = content)
                SpotlightStatChip(
                    text = pluralStringResource(R.plurals.stats_plays_count, entry.playCount, entry.playCount),
                    contentColor = content
                )
            }
        }
    }
}

@Composable
private fun SpotlightStatChip(text: String, contentColor: Color) {
    Text(
        text = text,
        modifier = Modifier
            .clip(CircleShape)
            .background(contentColor.copy(alpha = 0.10f))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        style = MaterialTheme.typography.labelMedium,
        fontFamily = rememberStatsFlexFont(weight = 650, width = 104f),
        color = contentColor,
        maxLines = 1
    )
}

@Composable
private fun RotationRankRow(
    entry: RotationEntry,
    rank: Int,
    fractionOfLeader: Float,
    shape: Shape,
    onClick: (RotationEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptics = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val pressScale = animatedPressScale(interactionSource, pressedScale = 0.97f)
    val meter = animatedFraction(fractionOfLeader)
    val meterColor = MaterialTheme.colorScheme.primary
    val meterTrack = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                val scale = pressScale.value
                scaleX = scale
                scaleY = scale
            }
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(interactionSource = interactionSource, indication = ripple()) {
                haptics.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick(entry)
            }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = NumberFormat.getIntegerInstance().format(rank),
            modifier = Modifier.width(20.dp),
            style = MaterialTheme.typography.titleMedium,
            fontFamily = rememberStatsFlexFont(weight = 740, width = 108f),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        RotationArtwork(
            entry = entry,
            cornerRadius = 12.dp,
            large = false,
            modifier = Modifier.size(48.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = entry.title,
                style = MaterialTheme.typography.titleSmall,
                fontFamily = rememberStatsFlexFont(weight = 640, width = 104f),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            entry.supportingText()?.let { supporting ->
                Text(
                    text = supporting,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth()
                    .height(5.dp)
                    .drawBehind {
                        val radius = CornerRadius(size.height / 2f)
                        drawRoundRect(color = meterTrack, cornerRadius = radius)
                        val width = size.width * meter.value.coerceIn(0f, 1f)
                        if (width > 0f) {
                            drawRoundRect(
                                color = meterColor,
                                size = Size(width.coerceAtLeast(size.height), size.height),
                                cornerRadius = radius
                            )
                        }
                    }
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = formatListeningDurationCompact(entry.durationMs),
                style = MaterialTheme.typography.labelLarge,
                fontFamily = rememberStatsFlexFont(weight = 700, width = 106f),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            Text(
                text = pluralStringResource(R.plurals.stats_plays_count, entry.playCount, entry.playCount),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
=======
@Composable
private fun categoryPaletteFor(dimension: CategoryDimension): CategoryChartPalette {
    return when (dimension) {
        CategoryDimension.Genre -> CategoryChartPalette(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            accentColor = MaterialTheme.colorScheme.tertiary,
            accentOnColor = MaterialTheme.colorScheme.onTertiary
        )

        CategoryDimension.Artist -> CategoryChartPalette(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            accentColor = MaterialTheme.colorScheme.primary,
            accentOnColor = MaterialTheme.colorScheme.onPrimary
        )

        CategoryDimension.Album -> CategoryChartPalette(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            accentColor = MaterialTheme.colorScheme.secondary,
            accentOnColor = MaterialTheme.colorScheme.onSecondary
        )

        CategoryDimension.Song -> CategoryChartPalette(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.onSurface,
            accentColor = MaterialTheme.colorScheme.primary,
            accentOnColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun TopArtistsCard(
    summary: PlaybackStatsRepository.PlaybackStatsSummary?,
    modifier: Modifier = Modifier
) {
    val containerColor = MaterialTheme.colorScheme.secondaryContainer
    val contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    val supportingColor = contentColor.copy(alpha = 0.76f)
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.stats_section_top_artists),
                style = MaterialTheme.typography.titleLargeEmphasized,
                color = contentColor
            )
            val artists = summary?.topArtists.orEmpty()
            if (artists.isEmpty()) {
                StatsEmptyState(
                    icon = Icons.Outlined.MusicNote,
                    title = stringResource(R.string.stats_empty_no_artists_title),
                    subtitle = stringResource(R.string.stats_empty_no_artists_subtitle)
                )
            } else {
                val maxDuration = artists.maxOf { it.totalDurationMs }.coerceAtLeast(1L)
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    artists.forEachIndexed { index, artistSummary ->
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                ArtistAvatar(
                                    name = artistSummary.artist,
                                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.18f),
                                    contentColor = MaterialTheme.colorScheme.secondary
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = stringResource(
                                            R.string.stats_ranked_artist,
                                            index + 1,
                                            artistSummary.artist
                                        ),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = contentColor,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = stringResource(
                                            R.string.stats_plays_tracks,
                                            artistSummary.playCount,
                                            artistSummary.uniqueSongs
                                        ),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = supportingColor,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Text(
                                    text = formatListeningDurationCompact(artistSummary.totalDurationMs),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = supportingColor
                                )
                            }
                            LinearProgressIndicator(
                                progress = { (artistSummary.totalDurationMs.toFloat() / maxDuration.toFloat()).coerceIn(0f, 1f) },
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = contentColor.copy(alpha = 0.18f)
                            )
                        }
                    }
                }
            }
>>>>>>> origin/main
        }
    }
}

@Composable
<<<<<<< HEAD
private fun RotationArtwork(
    entry: RotationEntry,
    cornerRadius: Dp,
    large: Boolean,
    modifier: Modifier = Modifier
) {
    val targetSize = if (large) coil.size.Size(300, 300) else SmartImageCompactListTargetSize
    when {
        entry.dimension == RotationDimension.Genres -> MonogramArtwork(
            text = entry.title,
            shape = RoundedStarShape(sides = 8, curve = 0.1, rotation = 0f),
            large = large,
            modifier = modifier
        )

        entry.dimension == RotationDimension.Artists && entry.artUri.isNullOrBlank() -> MonogramArtwork(
            text = entry.title,
            shape = CircleShape,
            large = large,
            modifier = modifier
        )

        else -> SmartImage(
            model = entry.artUri,
            contentDescription = null,
            shape = if (entry.dimension == RotationDimension.Artists) CircleShape else RoundedCornerShape(cornerRadius),
            targetSize = targetSize,
            modifier = modifier
        )
    }
}

@Composable
private fun MonogramArtwork(
    text: String,
    shape: Shape,
    large: Boolean,
    modifier: Modifier = Modifier
) {
    val placeholder = stringResource(R.string.presentation_batch_g_stats_avatar_placeholder)
    val initials = remember(text, placeholder) {
        text.split(" ")
=======
private fun ArtistAvatar(
    name: String,
    containerColor: Color,
    contentColor: Color
) {
    val placeholder = stringResource(R.string.stats_avatar_placeholder)
    val initials = remember(name, placeholder) {
        name.split(" ")
>>>>>>> origin/main
            .filter { it.isNotBlank() }
            .take(2)
            .joinToString(separator = "") { it.first().uppercaseChar().toString() }
            .ifBlank { placeholder }
    }
    Box(
<<<<<<< HEAD
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.tertiaryContainer),
=======
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(containerColor),
>>>>>>> origin/main
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
<<<<<<< HEAD
            style = if (large) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.titleMedium,
            fontFamily = rememberStatsFlexFont(weight = 760, width = 112f),
            color = MaterialTheme.colorScheme.onTertiaryContainer
=======
            style = MaterialTheme.typography.titleMedium,
            color = contentColor,
            fontWeight = FontWeight.Bold
>>>>>>> origin/main
        )
    }
}

<<<<<<< HEAD
// endregion

// region Listening variety

@Immutable
private data class VarietySlice(
    @StringRes val labelRes: Int,
    val durationMs: Long,
    val share: Float,
)

private fun varietySlices(summary: PlaybackStatsSummary): List<VarietySlice> {
    val durations = summary.songs.map { it.totalDurationMs }.sortedDescending()
    val total = durations.sum().coerceAtLeast(1L)
    val topOne = durations.firstOrNull() ?: 0L
    val topTwoThree = durations.drop(1).take(2).sum()
    val others = (total - topOne - topTwoThree).coerceAtLeast(0L)
    return listOf(
        VarietySlice(R.string.presentation_batch_g_stats_slice_top_1, topOne, topOne.toFloat() / total),
        VarietySlice(R.string.presentation_batch_g_stats_slice_top_2_3, topTwoThree, topTwoThree.toFloat() / total),
        VarietySlice(R.string.presentation_batch_g_stats_slice_others, others, others.toFloat() / total),
    ).filter { it.durationMs > 0L }
}

@Composable
private fun ListeningVarietyCard(
    summary: PlaybackStatsSummary,
    modifier: Modifier = Modifier
) {
    val slices = remember(summary) { varietySlices(summary) }
    val sliceColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.secondary
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatsSectionHeader(
            title = stringResource(R.string.presentation_batch_g_stats_section_listening_variety),
            subtitle = stringResource(R.string.presentation_batch_g_stats_track_concentration_subtitle)
        )

        if (slices.isEmpty()) {
            StatsEmptyState(
                icon = Icons.Outlined.AutoGraph,
                title = stringResource(R.string.presentation_batch_g_stats_empty_no_concentration_title),
                subtitle = stringResource(R.string.presentation_batch_g_stats_empty_no_concentration_subtitle)
            )
            return@Column
        }

        val topThreeShare = slices
            .filter { it.labelRes != R.string.presentation_batch_g_stats_slice_others }
            .sumOf { it.share.toDouble() }
            .toFloat()
        val playCount = if (summary.totalPlayCount > 0) summary.totalPlayCount else summary.songs.sumOf { it.playCount }
        val averagePlaysPerTrack = playCount.toFloat() / summary.songs.size.coerceAtLeast(1)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(AbsoluteSmoothCornerShape(28.dp, 60))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            VarietyCapsuleBar(slices = slices, colors = sliceColors)

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                slices.forEachIndexed { index, slice ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(sliceColors[index])
                        )
                        Text(
                            text = stringResource(slice.labelRes),
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = formatListeningDurationCompact(slice.durationMs),
                            style = MaterialTheme.typography.labelLarge,
                            fontFamily = rememberStatsFlexFont(weight = 620, width = 104f),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
=======
@Composable
private fun TopAlbumsCard(
    summary: PlaybackStatsRepository.PlaybackStatsSummary?,
    modifier: Modifier = Modifier
) {
    val containerColor = MaterialTheme.colorScheme.tertiaryContainer
    val contentColor = MaterialTheme.colorScheme.onTertiaryContainer
    val supportingColor = contentColor.copy(alpha = 0.76f)
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.stats_section_top_albums),
                style = MaterialTheme.typography.titleLargeEmphasized,
                color = contentColor
            )
            val albums = summary?.topAlbums.orEmpty()
            if (albums.isEmpty()) {
                StatsEmptyState(
                    icon = Icons.Outlined.Album,
                    title = stringResource(R.string.stats_empty_no_albums_title),
                    subtitle = stringResource(R.string.stats_empty_no_albums_subtitle)
                )
            } else {
                val maxDuration = albums.maxOf { it.totalDurationMs }.coerceAtLeast(1L)
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    albums.forEachIndexed { index, albumSummary ->
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                SmartImage(
                                    model = albumSummary.albumArtUri,
                                    contentDescription = albumSummary.album,
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = stringResource(
                                            R.string.stats_ranked_album,
                                            index + 1,
                                            albumSummary.album
                                        ),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = contentColor,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = stringResource(
                                            R.string.stats_plays_tracks,
                                            albumSummary.playCount,
                                            albumSummary.uniqueSongs
                                        ),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = supportingColor,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Text(
                                    text = formatListeningDurationCompact(albumSummary.totalDurationMs),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = supportingColor
                                )
                            }
                            LinearProgressIndicator(
                                progress = { (albumSummary.totalDurationMs.toFloat() / maxDuration.toFloat()).coerceIn(0f, 1f) },
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.tertiary,
                                trackColor = contentColor.copy(alpha = 0.18f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SongStatsCard(
    summary: PlaybackStatsRepository.PlaybackStatsSummary?,
    modifier: Modifier = Modifier
) {
    val containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
    val contentColor = MaterialTheme.colorScheme.onSurface
    val supportingColor = MaterialTheme.colorScheme.onSurfaceVariant
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val songs = summary?.songs.orEmpty()
            var showAll by rememberSaveable(songs) { mutableStateOf(songs.size <= 8) }
            val displayedSongs = remember(songs, showAll) {
                if (showAll || songs.size <= 8) songs else songs.take(8)
            }
            val maxDuration = songs.maxOfOrNull { it.totalDurationMs }?.coerceAtLeast(1L) ?: 1L
            val positions = remember(songs) { songs.mapIndexed { index, song -> song.songId to index }.toMap() }

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = stringResource(R.string.stats_tracks_in_range_title),
                    style = MaterialTheme.typography.titleLargeEmphasized,
                    fontWeight = FontWeight.SemiBold,
                    color = contentColor
                )
                Text(
                    text = stringResource(R.string.stats_tracks_in_range_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = supportingColor
                )
            }

            if (songs.isEmpty()) {
                StatsEmptyState(
                    icon = Icons.Outlined.MusicNote,
                    title = stringResource(R.string.stats_empty_no_tracks_title),
                    subtitle = stringResource(R.string.stats_empty_no_tracks_subtitle)
                )
            } else {
                Column(
                    modifier = Modifier.animateContentSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    displayedSongs.forEach { songSummary ->
                        val position = positions[songSummary.songId] ?: songs.indexOf(songSummary)
                        val accentColor = when {
                            position == 0 -> MaterialTheme.colorScheme.primary
                            position < 3 -> MaterialTheme.colorScheme.secondary
                            else -> MaterialTheme.colorScheme.tertiary
                        }
                        val accentOnColor = when {
                            position == 0 -> MaterialTheme.colorScheme.onPrimary
                            position < 3 -> MaterialTheme.colorScheme.onSecondary
                            else -> MaterialTheme.colorScheme.onTertiary
                        }
                        val rowContainerColor = when {
                            position == 0 -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                            position < 3 -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.36f)
                            else -> MaterialTheme.colorScheme.surfaceContainerLow
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = rowContainerColor
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    CategoryRankBadge(
                                        rank = position + 1,
                                        accentColor = accentColor,
                                        accentOnColor = accentOnColor,
                                        highlighted = position == 0
                                    )
                                    SmartImage(
                                        model = songSummary.albumArtUri,
                                        contentDescription = songSummary.title,
                                        modifier = Modifier
                                            .size(52.dp)
                                            .clip(RoundedCornerShape(14.dp)),
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = songSummary.title,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = contentColor
                                        )
                                        Text(
                                            text = songSummary.artist,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = supportingColor,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = stringResource(
                                                R.string.stats_n_plays,
                                                songSummary.playCount
                                            ),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = supportingColor
                                        )
                                    }
                                    Text(
                                        text = formatListeningDurationCompact(songSummary.totalDurationMs),
                                        style = rememberStatsMetricValueStyle(compact = true),
                                        color = contentColor
                                    )
                                }

                                LinearProgressIndicator(
                                    progress = { (songSummary.totalDurationMs.toFloat() / maxDuration.toFloat()).coerceIn(0f, 1f) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(7.dp)
                                        .clip(CircleShape),
                                    color = accentColor,
                                    trackColor = supportingColor.copy(alpha = 0.20f)
                                )
                            }
                        }
                    }
                }

                if (songs.size > 8) {
                    TextButton(
                        onClick = { showAll = !showAll },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                shape = RoundedCornerShape(16.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerLow
                            )
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        Text(
                            text = if (showAll) {
                                stringResource(R.string.stats_collapse_tracks)
                            } else {
                                stringResource(R.string.stats_show_all_tracks)
                            },
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
>>>>>>> origin/main
                        )
                    }
                }
            }
<<<<<<< HEAD

            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                VarietyMetricPill(
                    value = stringResource(R.string.presentation_batch_g_stats_percent, (topThreeShare * 100f).roundToInt()),
                    label = stringResource(R.string.presentation_batch_g_stats_top_three_share_label),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                VarietyMetricPill(
                    value = String.format(LocalConfiguration.current.locales[0], "%.1f", averagePlaysPerTrack),
                    label = stringResource(R.string.presentation_batch_g_stats_avg_plays_per_track),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
=======
        }
    }
}

@Composable
private fun TrackConcentrationCard(
    summary: PlaybackStatsRepository.PlaybackStatsSummary?,
    modifier: Modifier = Modifier
) {
    val songs = summary?.songs.orEmpty()
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = stringResource(R.string.stats_track_concentration_title),
                    style = MaterialTheme.typography.titleLargeEmphasized,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = stringResource(R.string.stats_track_concentration_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.78f)
                )
            }

            if (songs.isEmpty()) {
                StatsEmptyState(
                    icon = Icons.Outlined.AutoGraph,
                    title = stringResource(R.string.stats_empty_no_concentration_title),
                    subtitle = stringResource(R.string.stats_empty_no_concentration_subtitle)
                )
            } else {
                val totalDuration = songs.sumOf { it.totalDurationMs }.coerceAtLeast(1L)
                val topOneDuration = songs.firstOrNull()?.totalDurationMs ?: 0L
                val topThreeDuration = songs.take(3).sumOf { it.totalDurationMs }
                val topThreeShare = (topThreeDuration.toFloat() / totalDuration.toFloat()).coerceIn(0f, 1f)
                val averagePlaysPerTrack = (
                    (summary?.totalPlayCount ?: songs.sumOf { it.playCount }).toFloat() /
                        songs.size.coerceAtLeast(1).toFloat()
                    )
                val topOneColor = MaterialTheme.colorScheme.primary
                val topTwoThreeColor = MaterialTheme.colorScheme.secondary
                val othersColor = MaterialTheme.colorScheme.tertiary
                val slices = buildList {
                    if (topOneDuration > 0L) {
                        add(
                            TrackShareSlice(
                                label = stringResource(R.string.stats_slice_top_1),
                                durationMs = topOneDuration,
                                color = topOneColor
                            )
                        )
                    }
                    val topTwoToThree = (topThreeDuration - topOneDuration).coerceAtLeast(0L)
                    if (topTwoToThree > 0L) {
                        add(
                            TrackShareSlice(
                                label = stringResource(R.string.stats_slice_top_2_3),
                                durationMs = topTwoToThree,
                                color = topTwoThreeColor
                            )
                        )
                    }
                    val remaining = (totalDuration - topThreeDuration).coerceAtLeast(0L)
                    if (remaining > 0L) {
                        add(
                            TrackShareSlice(
                                label = stringResource(R.string.stats_slice_others),
                                durationMs = remaining,
                                color = othersColor
                            )
                        )
                    }
                }.toImmutableList()

                TrackDistributionOverview(
                    slices = slices,
                    totalDurationMs = totalDuration,
                    topThreeShare = topThreeShare,
                    averagePlaysPerTrack = averagePlaysPerTrack,
                    uniqueTracks = songs.size
>>>>>>> origin/main
                )
            }
        }
    }
}

@Composable
<<<<<<< HEAD
private fun VarietyCapsuleBar(
    slices: List<VarietySlice>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    // Segments open out from equal thirds into their real proportions.
    val reveal = animatedFraction(1f)
    val evenShare = 1f / slices.size
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(22.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        slices.forEachIndexed { index, slice ->
            val first = index == 0
            val last = index == slices.lastIndex
            val outer = 11.dp
            val inner = 4.dp
            // Keep slivers visible: a 1% slice still gets a readable nub.
            val weight = (evenShare + (slice.share.coerceAtLeast(0.03f) - evenShare) * reveal.value.coerceIn(0f, 1f))
                .coerceAtLeast(0.01f)
            Box(
                modifier = Modifier
                    .weight(weight)
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            topStart = if (first) outer else inner,
                            bottomStart = if (first) outer else inner,
                            topEnd = if (last) outer else inner,
                            bottomEnd = if (last) outer else inner
                        )
                    )
                    .background(colors[index])
            )
=======
private fun TrackDistributionOverview(
    slices: ImmutableList<TrackShareSlice>,
    totalDurationMs: Long,
    topThreeShare: Float,
    averagePlaysPerTrack: Float,
    uniqueTracks: Int,
    modifier: Modifier = Modifier
) {
    val metricStyle = rememberStatsMetricValueStyle(compact = false)
    val onColor = MaterialTheme.colorScheme.onSurface
    val supporting = MaterialTheme.colorScheme.onSurfaceVariant
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = MaterialTheme.colorScheme.surfaceContainerLowest
        ) {
            BoxWithConstraints(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp)
            ) {
                val compact = maxWidth < 420.dp
                if (compact) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        TrackDistributionDonut(
                            slices = slices,
                            totalDurationMs = totalDurationMs,
                            topThreeShare = topThreeShare,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        TrackDistributionStats(
                            metricStyle = metricStyle,
                            topThreeShare = topThreeShare,
                            averagePlaysPerTrack = averagePlaysPerTrack,
                            uniqueTracks = uniqueTracks
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TrackDistributionDonut(
                            slices = slices,
                            totalDurationMs = totalDurationMs,
                            topThreeShare = topThreeShare
                        )
                        TrackDistributionStats(
                            modifier = Modifier.weight(1f),
                            metricStyle = metricStyle,
                            topThreeShare = topThreeShare,
                            averagePlaysPerTrack = averagePlaysPerTrack,
                            uniqueTracks = uniqueTracks
                        )
                    }
                }
            }
        }

        if (slices.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                slices.forEach { slice ->
                    val share = (slice.durationMs.toFloat() / totalDurationMs.coerceAtLeast(1L).toFloat()).coerceIn(0f, 1f)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerLowest
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 9.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(slice.color)
                            )
                            Text(
                                text = slice.label,
                                style = MaterialTheme.typography.bodySmall,
                                color = supporting,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = stringResource(
                                    R.string.stats_percent,
                                    (share * 100f).roundToInt()
                                ),
                                style = MaterialTheme.typography.labelMedium,
                                color = onColor
                            )
                            Text(
                                text = formatListeningDurationCompact(slice.durationMs),
                                style = MaterialTheme.typography.labelSmall,
                                color = supporting
                            )
                        }
                    }
                }
            }
>>>>>>> origin/main
        }
    }
}

@Composable
<<<<<<< HEAD
private fun VarietyMetricPill(
    value: String,
    label: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(containerColor)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = rememberStatsFlexFont(weight = 760, width = 112f),
            color = contentColor,
            maxLines = 1
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = contentColor.copy(alpha = 0.8f),
            maxLines = 2
        )
    }
}

// endregion
=======
private fun TrackDistributionStats(
    metricStyle: TextStyle,
    topThreeShare: Float,
    averagePlaysPerTrack: Float,
    uniqueTracks: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.stats_listening_concentration_title),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(
                R.string.stats_top_three_share_line,
                (topThreeShare * 100f).roundToInt()
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 9.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = String.format(Locale.US, "%.1f", averagePlaysPerTrack),
                        style = metricStyle,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = stringResource(R.string.stats_avg_plays_per_track),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.76f)
                    )
                }
            }
            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.52f)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 9.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = uniqueTracks.toString(),
                        style = metricStyle,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = stringResource(R.string.stats_unique_tracks),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.76f)
                    )
                }
            }
        }
    }
}

@Composable
private fun TrackDistributionDonut(
    slices: ImmutableList<TrackShareSlice>,
    totalDurationMs: Long,
    topThreeShare: Float,
    modifier: Modifier = Modifier
) {
    val strokePx = with(LocalDensity.current) { 18.dp.toPx() }
    val visualGapPx = with(LocalDensity.current) { 4.dp.toPx() }
    val trackColor = Color.Transparent
    Box(
        modifier = modifier.size(158.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val safeTotal = totalDurationMs.coerceAtLeast(1L).toFloat()
            val arcInset = (strokePx / 2f) + 1f
            val diameter = (size.minDimension - (arcInset * 2f)).coerceAtLeast(0f)
            val radius = (diameter - strokePx).coerceAtLeast(0f) / 2f
            val gapAngle = if (slices.size > 1 && radius > 0f) {
                val centerlineGapPx = visualGapPx + strokePx
                ((centerlineGapPx / radius) * (180f / PI.toFloat())).coerceIn(1f, 24f)
            } else {
                0f
            }
            val arcTopLeft = Offset(
                x = (size.width - diameter) / 2f,
                y = (size.height - diameter) / 2f
            )
            val arcSize = Size(diameter, diameter)
            var startAngle = -90f

            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = strokePx,
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            )

            slices.forEach { slice ->
                val rawSweep = (slice.durationMs.toFloat() / safeTotal) * 360f
                val sweep = (rawSweep - gapAngle).coerceAtLeast(0f)
                if (sweep > 0.2f) {
                    drawArc(
                        color = slice.color,
                        startAngle = startAngle + (gapAngle / 2f),
                        sweepAngle = sweep,
                        useCenter = false,
                        topLeft = arcTopLeft,
                        size = arcSize,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(
                            width = strokePx,
                            cap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                    )
                }
                startAngle += rawSweep
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.stats_percent,
                    (topThreeShare * 100f).roundToInt()
                ),
                style = rememberStatsMetricValueStyle(compact = false).copy(
                    fontSize = 24.sp,
                    lineHeight = 26.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(R.string.stats_top_three_share_label),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
>>>>>>> origin/main
