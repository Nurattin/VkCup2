package com.smartdev.vkcup2.ui.screens.rating

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.common.TransitionButtons
import com.smartdev.vkcup2.ui.screens.rating.components.*
import com.smartdev.vkcup2.ui.theme.*
import com.smartdev.vkcup2.util.verticalSpace
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RatingScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    uiState: RatingUiState,
    onClickNext: () -> Unit,
    onClickPrev: () -> Unit,
    ratingArticle: (Int) -> Unit,
    addComment: (String) -> Unit,
) {

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    with(uiState) {
        ModalBottomSheetLayout(
            modifier = modifier.statusBarsPadding(),
            scrimColor = MainBackground.copy(0.5f),
            sheetContent = {
                SheetContent(
                    article = listArticle[page],
                    ratingArticle = ratingArticle,
                    addComment = { comment ->
                        addComment(comment)
                        hideSheet(coroutineScope, sheetState, 700)
                    },
                    onClickClose = {
                        hideSheet(coroutineScope, sheetState)
                    }
                )

            },
            sheetShape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp),
            sheetBackgroundColor = SheetBackground,
            sheetElevation = 8.dp,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainBackground)
            ) {
                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable(onClick = onBackClick),
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
                Crossfade(targetState = page) { index ->
                    MainContent(
                        article = listArticle[index],
                        onClickRating = {
                            coroutineScope.launch {
                                sheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        },
                        onClickNext = onClickNext,
                        onClickPrev = onClickPrev,
                    )
                }
            }
        }
    }
}

@Composable
private fun MainContent(
    article: Article,
    onClickRating: () -> Unit,
    onClickNext: () -> Unit,
    onClickPrev: () -> Unit,
) {
    val scrollState = rememberScrollState()

    with(article) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(scrollState)
                ) {
                    User(
                        userName = authorName,
                        extraParams = publicationDate
                    )
                    verticalSpace(height = 16.dp)
                    Text(
                        text = articleName,
                        style = MaterialTheme.typography.h5
                    )
                    verticalSpace(height = 16.dp)
                    Text(
                        modifier = Modifier,
                        text = text,
                        color = Color.White.copy(0.8f),
                        style = MaterialTheme.typography.body2
                    )
                    verticalSpace(height = 26.dp)
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = FillUnSelected
                    )
                    verticalSpace(height = 8.dp)
                    Text(
                        modifier = Modifier,
                        text = "${allComment.size} comments",
                        style = MaterialTheme.typography.subtitle1
                    )
                    verticalSpace(height = 16.dp)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        allComment.forEach { comment ->
                            User(
                                userName = comment.userName,
                                extraParams = comment.text
                            )
                        }
                    }
                    verticalSpace(height = 64.dp)
                }
                RatingFloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(
                            bottom = dimensionResource(id = R.dimen.chip_small),
                            end = dimensionResource(id = R.dimen.container_small)
                        ),
                    isFavorite = userRating != null,
                    numberAppraisers = numberAppraisers.toString(),
                    onClickRating = onClickRating
                )
            }
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.container_small)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.container_small)),
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.container_small))
                        .align(CenterHorizontally),
                    text = stringResource(id = R.string.rating_the_article_hint),
                    style = MaterialTheme.typography.caption
                )
                TransitionButtons(
                    onClickNext = onClickNext,
                    onClickPrev = onClickPrev,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun hideSheet(
    coroutineScope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    delay: Long = 0
) {
    coroutineScope.launch {
        delay(delay)
        sheetState.animateTo(ModalBottomSheetValue.Hidden)
    }
}

@Composable
private fun SheetContent(
    article: Article,
    ratingArticle: (Int) -> Unit,
    addComment: (String) -> Unit,
    onClickClose: () -> Unit
) {
    with(article) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Icon(
                modifier = Modifier
                    .clickable(
                        indication = rememberRipple(),
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onClickClose
                    )
                    .padding(dimensionResource(id = R.dimen.container_small))
                    .align(End),
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                tint = Color.White
            )
            verticalSpace(dimensionResource(id = R.dimen.container_small))
            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = stringResource(R.string.rating_the_article),
                style = MaterialTheme.typography.h6,
            )
            verticalSpace(dimensionResource(id = R.dimen.container_small))
            RatingBar(
                rating = userRating ?: 0,
                onClickRating = ratingArticle
            )
            verticalSpace(dimensionResource(id = R.dimen.container_small))
            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = "Оценило ${if (userRating != null) totalRating + 1 else totalRating} человек",
                style = MaterialTheme.typography.subtitle2,
                color = Color.White.copy(0.8f)
            )
            verticalSpace(height = 48.dp)
            AnimatedVisibility(visible = userRating != null) {
                CommentTextField(
                    onSentComment = addComment
                )
            }
            verticalSpace(dimensionResource(id = R.dimen.container_small))
            RatingTable(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.container_medium)
                ),
                ratingTable = ratingTable
            )
            verticalSpace(dimensionResource(id = R.dimen.container_medium))
        }
    }
}

