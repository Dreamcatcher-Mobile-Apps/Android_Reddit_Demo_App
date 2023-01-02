/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package redditandroidapp.features.home

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.lang.reflect.Modifier

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun Home(
    viewModel: HomeViewModel = viewModel()
) {
//    val viewState by viewModel.state.collectAsStateWithLifecycle()
//    Surface(Modifier.fillMaxSize()) {
//        HomeContent(
//            featuredPodcasts = viewState.featuredPodcasts,
//            isRefreshing = viewState.refreshing,
//            modifier = Modifier.fillMaxSize()
//        )
//    }
}

//@Composable
//fun HomeAppBar(
//    backgroundColor: Color,
//    modifier: Modifier = Modifier
//) {
//    TopAppBar(
//        title = {
//            Row {
//                Image(
//                    painter = painterResource(R.drawable.ic_logo),
//                    contentDescription = null
//                )
//                Icon(
//                    painter = painterResource(R.drawable.ic_text_logo),
//                    contentDescription = stringResource(R.string.app_name),
//                    modifier = Modifier
//                        .padding(start = 4.dp)
//                        .heightIn(max = 24.dp)
//                )
//            }
//        },
//        backgroundColor = backgroundColor,
//        actions = {
//            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//                IconButton(
//                    onClick = { /* TODO: Open search */ }
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.Search,
//                        contentDescription = stringResource(R.string.cd_search)
//                    )
//                }
//                IconButton(
//                    onClick = { /* TODO: Open account? */ }
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.AccountCircle,
//                        contentDescription = stringResource(R.string.cd_account)
//                    )
//                }
//            }
//        },
//        modifier = modifier
//    )
//}

//@OptIn(ExperimentalPagerApi::class) // HorizontalPager is experimental
//@Composable
//fun HomeContent(
//    featuredPodcasts: List<PodcastWithExtraInfo>,
//    isRefreshing: Boolean,
//    selectedHomeCategory: HomeCategory,
//    homeCategories: List<HomeCategory>,
//    modifier: Modifier = Modifier,
//    onPodcastUnfollowed: (String) -> Unit,
//    onCategorySelected: (HomeCategory) -> Unit,
//    navigateToPlayer: (String) -> Unit
//) {
//    Column(
//        modifier = modifier.windowInsetsPadding(
//            WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
//        )
//    ) {
//        // We dynamically theme this sub-section of the layout to match the selected
//        // 'top podcast'
//
//        val surfaceColor = MaterialTheme.colors.surface
//        val dominantColorState = rememberDominantColorState { color ->
//            // We want a color which has sufficient contrast against the surface color
//            color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
//        }
//
//        DynamicThemePrimaryColorsFromImage(dominantColorState) {
//            val pagerState = rememberPagerState()
//
//            val selectedImageUrl = featuredPodcasts.getOrNull(pagerState.currentPage)
//                ?.podcast?.imageUrl
//
//            // When the selected image url changes, call updateColorsFromImageUrl() or reset()
//            LaunchedEffect(selectedImageUrl) {
//                if (selectedImageUrl != null) {
//                    dominantColorState.updateColorsFromImageUrl(selectedImageUrl)
//                } else {
//                    dominantColorState.reset()
//                }
//            }
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .verticalGradientScrim(
//                        color = MaterialTheme.colors.primary.copy(alpha = 0.38f),
//                        startYPercentage = 1f,
//                        endYPercentage = 0f
//                    )
//            ) {
//                val appBarColor = MaterialTheme.colors.surface.copy(alpha = 0.87f)
//
//                // Draw a scrim over the status bar which matches the app bar
//                Spacer(
//                    Modifier
//                        .background(appBarColor)
//                        .fillMaxWidth()
//                        .windowInsetsTopHeight(WindowInsets.statusBars)
//                )
//
//                HomeAppBar(
//                    backgroundColor = appBarColor,
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                if (featuredPodcasts.isNotEmpty()) {
//                    Spacer(Modifier.height(16.dp))
//
//                    FollowedPodcasts(
//                        items = featuredPodcasts,
//                        pagerState = pagerState,
//                        onPodcastUnfollowed = onPodcastUnfollowed,
//                        modifier = Modifier
//                            .padding(start = Keyline1, top = 16.dp, end = Keyline1)
//                            .fillMaxWidth()
//                            .height(200.dp)
//                    )
//
//                    Spacer(Modifier.height(16.dp))
//                }
//            }
//        }
//
//        if (isRefreshing) {
//            // TODO show a progress indicator or similar
//        }
//
//        if (homeCategories.isNotEmpty()) {
//            HomeCategoryTabs(
//                categories = homeCategories,
//                selectedCategory = selectedHomeCategory,
//                onCategorySelected = onCategorySelected
//            )
//        }
//
//        when (selectedHomeCategory) {
//            HomeCategory.Library -> {
//                // TODO
//            }
//            HomeCategory.Discover -> {
//                Discover(
//                    navigateToPlayer = navigateToPlayer,
//                    Modifier
//                        .fillMaxWidth()
//                        .weight(1f)
//                )
//            }
//        }
//    }
//}

//@Composable
//private fun HomeCategoryTabs(
//    categories: List<HomeCategory>,
//    selectedCategory: HomeCategory,
//    onCategorySelected: (HomeCategory) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
//    val indicator = @Composable { tabPositions: List<TabPosition> ->
//        HomeCategoryTabIndicator(
//            Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
//        )
//    }
//
//    TabRow(
//        selectedTabIndex = selectedIndex,
//        indicator = indicator,
//        modifier = modifier
//    ) {
//        categories.forEachIndexed { index, category ->
//            Tab(
//                selected = index == selectedIndex,
//                onClick = { onCategorySelected(category) },
//                text = {
//                    Text(
//                        text = when (category) {
//                            HomeCategory.Library -> stringResource(R.string.home_library)
//                            HomeCategory.Discover -> stringResource(R.string.home_discover)
//                        },
//                        style = MaterialTheme.typography.body2
//                    )
//                }
//            )
//        }
//    }
//}

//@Composable
//fun HomeCategoryTabIndicator(
//    modifier: Modifier = Modifier,
//    color: Color = MaterialTheme.colors.onSurface
//) {
//    Spacer(
//        modifier
//            .padding(horizontal = 24.dp)
//            .height(4.dp)
//            .background(color, RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
//    )
//}

//@ExperimentalPagerApi // HorizontalPager is experimental
//@Composable
//fun FollowedPodcasts(
//    items: List<PodcastWithExtraInfo>,
//    pagerState: PagerState,
//    modifier: Modifier = Modifier,
//    onPodcastUnfollowed: (String) -> Unit,
//) {
//    HorizontalPager(
//        count = items.size,
//        state = pagerState,
//        modifier = modifier
//    ) { page ->
//        val (podcast, lastEpisodeDate) = items[page]
//        FollowedPodcastCarouselItem(
//            podcastImageUrl = podcast.imageUrl,
//            podcastTitle = podcast.title,
//            lastEpisodeDate = lastEpisodeDate,
//            onUnfollowedClick = { onPodcastUnfollowed(podcast.uri) },
//            modifier = Modifier
//                .padding(4.dp)
//                .fillMaxHeight()
//        )
//    }
//}

//@Composable
//private fun FollowedPodcastCarouselItem(
//    modifier: Modifier = Modifier,
//    podcastImageUrl: String? = null,
//    podcastTitle: String? = null,
//    lastEpisodeDate: OffsetDateTime? = null,
//    onUnfollowedClick: () -> Unit,
//) {
//    Column(
//        modifier.padding(horizontal = 12.dp, vertical = 8.dp)
//    ) {
//        Box(
//            Modifier
//                .weight(1f)
//                .align(Alignment.CenterHorizontally)
//                .aspectRatio(1f)
//        ) {
//            if (podcastImageUrl != null) {
//                AsyncImage(
//                    model = podcastImageUrl,
//                    contentDescription = podcastTitle,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clip(MaterialTheme.shapes.medium),
//                )
//            }
//
//            ToggleFollowPodcastIconButton(
//                onClick = onUnfollowedClick,
//                isFollowed = true, /* All podcasts are followed in this feed */
//                modifier = Modifier.align(Alignment.BottomEnd)
//            )
//        }
//
//        if (lastEpisodeDate != null) {
//            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//                Text(
//                    text = lastUpdated(lastEpisodeDate),
//                    style = MaterialTheme.typography.caption,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier
//                        .padding(top = 8.dp)
//                        .align(Alignment.CenterHorizontally)
//                )
//            }
//        }
//    }
//}

//@Composable
//private fun lastUpdated(updated: OffsetDateTime): String {
//    val duration = Duration.between(updated.toLocalDateTime(), LocalDateTime.now())
//    val days = duration.toDays().toInt()
//
//    return when {
//        days > 28 -> stringResource(R.string.updated_longer)
//        days >= 7 -> {
//            val weeks = days / 7
//            quantityStringResource(R.plurals.updated_weeks_ago, weeks, weeks)
//        }
//        days > 0 -> quantityStringResource(R.plurals.updated_days_ago, days, days)
//        else -> stringResource(R.string.updated_today)
//    }
//}

/*
TODO: Fix preview error
@Composable
@Preview
fun PreviewHomeContent() {
    JetcasterTheme {
        HomeContent(
            featuredPodcasts = PreviewPodcastsWithExtraInfo,
            isRefreshing = false,
            homeCategories = HomeCategory.values().asList(),
            selectedHomeCategory = HomeCategory.Discover,
            onCategorySelected = {},
            onPodcastUnfollowed = {}
        )
    }
}
*/

//@Composable
//@Preview
//fun PreviewPodcastCard() {
//    JetcasterTheme {
//        FollowedPodcastCarouselItem(
//            modifier = Modifier.size(128.dp),
//            onUnfollowedClick = {}
//        )
//    }
//}
