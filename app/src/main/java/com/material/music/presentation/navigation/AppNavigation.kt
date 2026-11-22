package com.material.music.presentation.navigation

import DelimiterConfigScreen
import com.material.music.presentation.screens.WordDelimiterConfigScreen
import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.material.music.R
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.material.music.data.preferences.LaunchTab
import com.material.music.data.preferences.UserPreferencesRepository
import com.material.music.presentation.screens.AlbumDetailScreen
import com.material.music.presentation.screens.AudioBookmarkFolderScreen
import com.material.music.presentation.screens.AudioBookmarksScreen
import com.material.music.presentation.screens.CloudDownloadsScreen
import com.material.music.presentation.screens.UpdatesScreen
import com.material.music.presentation.model.SettingsCategory
import com.material.music.presentation.screens.AccountsScreen
import com.material.music.presentation.screens.ArtistDetailScreen
import com.material.music.presentation.screens.ArtistSettingsScreen
import com.material.music.presentation.screens.DailyMixScreen
import com.material.music.presentation.screens.EditTransitionScreen
import com.material.music.presentation.screens.EasterEggScreen
import com.material.music.presentation.screens.ExperimentalSettingsScreen
import com.material.music.presentation.screens.LyricsSettingsScreen
import com.material.music.presentation.screens.GenreDetailScreen
import com.material.music.presentation.screens.HomeScreen
import com.material.music.presentation.screens.LibraryScreen
import com.material.music.presentation.screens.MashupScreen
import com.material.music.presentation.screens.NavBarCornerRadiusScreen
import com.material.music.presentation.screens.PaletteStyleSettingsScreen
import com.material.music.presentation.screens.PlaylistDetailScreen
import com.material.music.presentation.screens.RecentlyPlayedScreen

import com.material.music.presentation.screens.AboutScreen
import com.material.music.presentation.screens.LicensesScreen
import com.material.music.presentation.screens.SearchScreen
import com.material.music.presentation.screens.StatsScreen
import com.material.music.presentation.screens.DuplicateSongsScreen
import com.material.music.presentation.screens.SettingsScreen
import com.material.music.presentation.screens.SettingsCategoryScreen
import com.material.music.presentation.screens.EqualizerScreen
import com.material.music.presentation.viewmodel.PlayerViewModel
import com.material.music.presentation.viewmodel.PlaylistViewModel
import com.material.music.presentation.viewmodel.RecapViewModel
import com.material.music.presentation.recap.RecapScreen
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.material.music.presentation.screens.ExcludedDirectoriesScreen
import com.material.music.presentation.components.ScreenWrapper
import com.material.music.presentation.settings.search.ProvideSettingsHighlight

@OptIn(UnstableApi::class)
@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun AppNavigation(
    playerViewModel: PlayerViewModel,
    navController: NavHostController,
    paddingValues: PaddingValues,
    userPreferencesRepository: UserPreferencesRepository,
    onSearchBarActiveChange: (Boolean) -> Unit,
    onOpenSidebar: () -> Unit
) {
    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        startDestination = userPreferencesRepository.launchTabFlow
            .first()
            .toRoute()
    }

    startDestination?.let { initialRoute ->
        NavHost(
            navController = navController,
            startDestination = initialRoute
        ) {
            composable(
                Screen.Home.route,
                enterTransition = {
                    mainRootEnterTransition(
                        fromRoute = initialState.destination.route,
                        toRoute = targetState.destination.route,
                        fallback = enterTransition()
                    )
                },
                exitTransition = {
                    mainRootExitTransition(
                        fromRoute = initialState.destination.route,
                        toRoute = targetState.destination.route,
                        fallback = exitTransition()
                    )
                },
                popEnterTransition = {
                    mainRootEnterTransition(
                        fromRoute = initialState.destination.route,
                        toRoute = targetState.destination.route,
                        fallback = popEnterTransition()
                    )
                },
                popExitTransition = {
                    mainRootExitTransition(
                        fromRoute = initialState.destination.route,
                        toRoute = targetState.destination.route,
                        fallback = popExitTransition()
                    )
                },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    HomeScreen(
                        navController = navController, 
                        paddingValuesParent = paddingValues, 
                        playerViewModel = playerViewModel,
                        onOpenSidebar = onOpenSidebar
                    )
                }
            }
            composable(
                Screen.Search.route,
                enterTransition = {
                    mainRootEnterTransition(
                        fromRoute = initialState.destination.route,
                        toRoute = targetState.destination.route,
                        fallback = enterTransition()
                    )
                },
                exitTransition = {
                    mainRootExitTransition(
                        fromRoute = initialState.destination.route,
                        toRoute = targetState.destination.route,
                        fallback = exitTransition()
                    )
                },
                popEnterTransition = {
                    mainRootEnterTransition(
                        fromRoute = initialState.destination.route,
                        toRoute = targetState.destination.route,
                        fallback = popEnterTransition()
                    )
                },
                popExitTransition = {
                    mainRootExitTransition(
                        fromRoute = initialState.destination.route,
                        toRoute = targetState.destination.route,
                        fallback = popExitTransition()
                    )
                },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    SearchScreen(
                        paddingValues = paddingValues,
                        playerViewModel = playerViewModel,
                        navController = navController,
                        onSearchBarActiveChange = onSearchBarActiveChange
                    )
                }
            }
            composable(
                Screen.Library.route,
                enterTransition = {
                    mainRootEnterTransition(
                        fromRoute = initialState.destination.route,
                        toRoute = targetState.destination.route,
                        fallback = enterTransition()
                    )
                },
                exitTransition = {
                    mainRootExitTransition(
                        fromRoute = initialState.destination.route,
                        toRoute = targetState.destination.route,
                        fallback = exitTransition()
                    )
                },
                popEnterTransition = {
                    mainRootEnterTransition(
                        fromRoute = initialState.destination.route,
                        toRoute = targetState.destination.route,
                        fallback = popEnterTransition()
                    )
                },
                popExitTransition = {
                    mainRootExitTransition(
                        fromRoute = initialState.destination.route,
                        toRoute = targetState.destination.route,
                        fallback = popExitTransition()
                    )
                },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    LibraryScreen(navController = navController, playerViewModel = playerViewModel)
                }
            }
            composable(
                Screen.Settings.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    SettingsScreen(
                        navController = navController,
                        playerViewModel = playerViewModel,
                        onNavigationIconClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
            composable(
                route = Screen.Accounts.route,
                arguments = listOf(navArgument("highlightKey") { type = NavType.StringType; nullable = true; defaultValue = null }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    AccountsScreen(
                        onBackClick = { navController.popBackStack() },
                        onOpenNavidromeDashboard = {
                            navController.navigateSafely(Screen.NavidromeDashboard.route)
                        },
                        onOpenJellyfinDashboard = {
                            navController.navigateSafely(Screen.JellyfinDashboard.route)
                        }
                    )
                }
            }
            composable(
                route = Screen.SettingsCategory.route,
                arguments = listOf(
                    navArgument("categoryId") { type = NavType.StringType },
                    navArgument("highlightKey") { type = NavType.StringType; nullable = true; defaultValue = null }
                ),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) { backStackEntry ->
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    val categoryId = backStackEntry.arguments?.getString("categoryId")
                    val highlightKey = backStackEntry.arguments?.getString("highlightKey")
                    if (categoryId != null) {
                        ProvideSettingsHighlight(highlightKey) {
                            SettingsCategoryScreen(
                                categoryId = categoryId,
                                highlightKey = highlightKey,
                                navController = navController,
                                playerViewModel = playerViewModel,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
            composable(
                route = Screen.PaletteStyle.route,
                arguments = listOf(navArgument("highlightKey") { type = NavType.StringType; nullable = true; defaultValue = null }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    PaletteStyleSettingsScreen(
                        playerViewModel = playerViewModel,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
            composable(
                route = Screen.Experimental.route,
                arguments = listOf(navArgument("highlightKey") { type = NavType.StringType; nullable = true; defaultValue = null }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    ExperimentalSettingsScreen(
                        navController = navController,
                        playerViewModel = playerViewModel,
                        onNavigationIconClick = { navController.popBackStack() }
                    )
                }
            }
            composable(
                route = Screen.LyricsSettings.route,
                arguments = listOf(navArgument("highlightKey") { type = NavType.StringType; nullable = true; defaultValue = null }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) { backStackEntry ->
                val highlightKey = backStackEntry.arguments?.getString("highlightKey")
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    ProvideSettingsHighlight(highlightKey) {
                        LyricsSettingsScreen(
                            navController = navController,
                            playerViewModel = playerViewModel,
                            highlightKey = highlightKey
                        )
                    }
                }
            }
            composable(
                Screen.DailyMixScreen.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    DailyMixScreen(
                        playerViewModel = playerViewModel,
                        navController = navController
                    )
                }
            }
            composable(
                Screen.RecentlyPlayed.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    RecentlyPlayedScreen(
                        playerViewModel = playerViewModel,
                        navController = navController
                    )
                }
            }
            composable(
                Screen.Stats.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    StatsScreen(
                        navController = navController,
                        playerViewModel = playerViewModel
                    )
                }
            }
            composable(
                route = Screen.Recap.route,
                arguments = listOf(navArgument(RecapViewModel.YEAR_ARG) { type = NavType.IntType }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    RecapScreen(
                        navController = navController,
                        playerViewModel = playerViewModel
                    )
                }
            }
            composable(
                route = Screen.Duplicates.route,
                arguments = listOf(navArgument("highlightKey") { type = NavType.StringType; nullable = true; defaultValue = null }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    DuplicateSongsScreen(
                        navController = navController,
                        playerViewModel = playerViewModel
                    )
                }
            }
            composable(
                route = Screen.PlaylistDetail.route,
                arguments = listOf(navArgument("playlistId") { type = NavType.StringType }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) { backStackEntry ->
                val playlistId = backStackEntry.arguments?.getString("playlistId")
                val playlistViewModel: PlaylistViewModel = hiltViewModel()
                if (playlistId != null) {
                    ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                        PlaylistDetailScreen(
                            playlistId = playlistId,
                            playerViewModel = playerViewModel,
                            playlistViewModel = playlistViewModel,
                            onBackClick = { navController.popBackStack() },
                            onDeletePlayListClick = { navController.popBackStack() },
                            navController = navController
                        )
                    }
                }
            }

            composable(
                Screen.DJSpace.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    MashupScreen()
                }
            }
            composable(
                route = Screen.GenreDetail.route,
                arguments = listOf(navArgument("genreId") { type = NavType.StringType }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) { backStackEntry ->
                val genreId = backStackEntry.arguments?.getString("genreId")
                if (genreId != null) {
                    ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                        GenreDetailScreen(
                            navController = navController,
                            genreId = genreId,
                            playerViewModel = playerViewModel
                        )
                    }
                } else {
                    Text(stringResource(R.string.nav_error_genre_id_missing), modifier = Modifier)
                }
            }
            composable(
                route = Screen.AlbumDetail.route,
                arguments = listOf(navArgument("albumId") { type = NavType.StringType }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) { backStackEntry ->
                val albumId = backStackEntry.arguments?.getString("albumId")
                if (albumId != null) {
                    ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                        AlbumDetailScreen(
                            albumId = albumId,
                            navController = navController,
                            playerViewModel = playerViewModel
                        )
                    }
                }
            }
            composable(
                route = Screen.ArtistDetail.route,
                arguments = listOf(navArgument("artistId") { type = NavType.StringType }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) { backStackEntry ->
                val artistId = backStackEntry.arguments?.getString("artistId")
                if (artistId != null) {
                    ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                        ArtistDetailScreen(
                            artistId = artistId,
                            navController = navController,
                            playerViewModel = playerViewModel
                        )
                    }
                }
            }
            composable(
                route = Screen.NavBarCrRad.route,
                arguments = listOf(navArgument("highlightKey") { type = NavType.StringType; nullable = true; defaultValue = null }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    NavBarCornerRadiusScreen(navController)
                }
            }
            composable(
                route = Screen.EditTransition.route,
                arguments = listOf(navArgument("playlistId") {
                    type = NavType.StringType
                    nullable = true
                }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    EditTransitionScreen(navController = navController)
                }
            }
            composable(
                Screen.About.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    AboutScreen(
                        navController = navController,
                        viewModel = playerViewModel,
                        onNavigationIconClick = { navController.popBackStack() }
                    )
                }
            }
            composable(
                Screen.Licenses.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    LicensesScreen(
                        onNavigationIconClick = { navController.popBackStack() }
                    )
                }
            }
            composable(
                Screen.EasterEgg.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    EasterEggScreen(
                        viewModel = playerViewModel,
                        onNavigationIconClick = { navController.popBackStack() },
                    )
                }
            }
            composable(
                route = Screen.ArtistSettings.route,
                arguments = listOf(navArgument("highlightKey") { type = NavType.StringType; nullable = true; defaultValue = null }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) { backStackEntry ->
                val highlightKey = backStackEntry.arguments?.getString("highlightKey")
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    ProvideSettingsHighlight(highlightKey) {
                        ArtistSettingsScreen(
                            navController = navController,
                            highlightKey = highlightKey
                        )
                    }
                }
            }
            composable(
                Screen.DelimiterConfig.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    DelimiterConfigScreen(navController = navController)
                }
            }
            composable(
                Screen.WordDelimiterConfig.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    WordDelimiterConfigScreen(navController = navController)
                }
            }
            composable(
                Screen.ExcludedDirectories.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                val initialMiniplayerVisible = remember(playerViewModel) {
                    playerViewModel.stablePlayerState.value.currentSong != null
                }
                val isMiniplayerVisible by remember(playerViewModel) {
                    playerViewModel.stablePlayerState
                        .map { state -> state.currentSong != null }
                        .distinctUntilChanged()
                }.collectAsStateWithLifecycle(initialValue = initialMiniplayerVisible)
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    ExcludedDirectoriesScreen(
                        onNavigateBack = {
                            navController.popBackStack(Screen.ExcludedDirectories.route, inclusive = true)
                        },
                        isMiniplayerVisible = isMiniplayerVisible
                    )
                }
            }
            composable(
                route = Screen.Equalizer.route,
                arguments = listOf(navArgument("highlightKey") { type = NavType.StringType; nullable = true; defaultValue = null }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    EqualizerScreen(
                        navController = navController,
                        playerViewModel = playerViewModel
                    )
                }
            }
            composable(
                route = Screen.DeviceCapabilities.route,
                arguments = listOf(navArgument("highlightKey") { type = NavType.StringType; nullable = true; defaultValue = null }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    com.material.music.presentation.screens.DeviceCapabilitiesScreen(
                        navController = navController,
                        playerViewModel = playerViewModel
                    )
                }
            }
            composable(
                Screen.NavidromeDashboard.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    com.material.music.presentation.navidrome.dashboard.NavidromeDashboardScreen(
                        onBack = { navController.popBackStack() }
                    )
                }
            }
            composable(
                Screen.JellyfinDashboard.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    com.material.music.presentation.jellyfin.dashboard.JellyfinDashboardScreen(
                        onBack = { navController.popBackStack() }
                    )
                }
            }
            composable(
                Screen.CloudDownloads.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    CloudDownloadsScreen(onBack = { navController.popBackStack() })
                }
            }
            composable(
                Screen.Updates.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                ScreenWrapper(navController = navController, playerViewModel = playerViewModel) {
                    UpdatesScreen(
                        onBack = { navController.popBackStack() },
                        onOpenBackup = {
                            navController.navigateSafely(
                                Screen.SettingsCategory.createRoute(
                                    SettingsCategory.BACKUP_RESTORE.id,
                                    highlightKey = "item_backup_export"
                                )
                            )
                        }
                    )
                }
            }
            composable(
                Screen.AudioBookmarks.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) {
                AudioBookmarksScreen(
                    navController = navController,
                    playerViewModel = playerViewModel,
                    onOpenSidebar = onOpenSidebar
                )
            }
            composable(
                Screen.AudioBookmarkFolder.route,
                arguments = listOf(navArgument("songId") { type = NavType.StringType }),
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() },
            ) { backStackEntry ->
                AudioBookmarkFolderScreen(
                    songId = backStackEntry.arguments?.getString("songId").orEmpty(),
                    navController = navController,
                    playerViewModel = playerViewModel
                )
            }
        }
    }
}

private fun String.toRoute(): String = when (this) {
    LaunchTab.SEARCH -> Screen.Search.route
    LaunchTab.LIBRARY -> Screen.Library.route
    else -> Screen.Home.route
}

private enum class MainRootDirection {
    FORWARD,
    BACKWARD
}

private const val BOTTOM_NAV_TRANSITION_DURATION = 380

private val BottomNavEasing = CubicBezierEasing(0.2f, 0f, 0f, 1f)

private val MAIN_ROOT_TRANSITION_SPEC =
    tween<IntOffset>(durationMillis = BOTTOM_NAV_TRANSITION_DURATION, easing = BottomNavEasing)

private val MAIN_ROOT_FADE_SPEC =
    tween<Float>(durationMillis = BOTTOM_NAV_TRANSITION_DURATION / 2, easing = BottomNavEasing)

private fun mainRootDirection(
    fromRoute: String?,
    toRoute: String?
): MainRootDirection? {
    val fromIndex = mainRootRouteIndex(fromRoute) ?: return null
    val toIndex = mainRootRouteIndex(toRoute) ?: return null
    if (fromIndex == toIndex) return null
    return if (toIndex > fromIndex) MainRootDirection.FORWARD else MainRootDirection.BACKWARD
}

private fun mainRootEnterTransition(
    fromRoute: String?,
    toRoute: String?,
    fallback: EnterTransition
): EnterTransition = when (mainRootDirection(fromRoute, toRoute)) {
    MainRootDirection.FORWARD -> {
        slideInHorizontally(
            animationSpec = MAIN_ROOT_TRANSITION_SPEC,
            initialOffsetX = { (it * 0.5f).toInt() }
        ) + fadeIn(animationSpec = MAIN_ROOT_FADE_SPEC)
    }
    MainRootDirection.BACKWARD -> {
        slideInHorizontally(
            animationSpec = MAIN_ROOT_TRANSITION_SPEC,
            initialOffsetX = { -(it * 0.5f).toInt() }
        ) + fadeIn(animationSpec = MAIN_ROOT_FADE_SPEC)
    }
    null -> fallback
}

private fun mainRootExitTransition(
    fromRoute: String?,
    toRoute: String?,
    fallback: ExitTransition
): ExitTransition = when (mainRootDirection(fromRoute, toRoute)) {
    MainRootDirection.FORWARD -> {
        slideOutHorizontally(
            animationSpec = MAIN_ROOT_TRANSITION_SPEC,
            targetOffsetX = { -(it * 0.5f).toInt() }
        ) + fadeOut(animationSpec = MAIN_ROOT_FADE_SPEC)
    }
    MainRootDirection.BACKWARD -> {
        slideOutHorizontally(
            animationSpec = MAIN_ROOT_TRANSITION_SPEC,
            targetOffsetX = { (it * 0.5f).toInt() }
        ) + fadeOut(animationSpec = MAIN_ROOT_FADE_SPEC)
    }
    null -> fallback
}
