package com.hstar.presentation.comon.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource


@Composable
fun ImageWithBackground(
    target: Painter,
    @DrawableRes backgroundDrawableResId: Int,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(backgroundDrawableResId),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
        )
        Image(
            painter = target,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
            modifier = Modifier
                .matchParentSize()
        )
    }
}

@Composable
fun ImageWholeBackground(
    @DrawableRes backgroundDrawableResId: Int,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(backgroundDrawableResId),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
        )
    }
}

//    @Composable
//    fun BottomStepBar(
//        showPrevious: Boolean,
//        onPreviousClick: () -> Unit,
//        enabledNext: Boolean,
//        onNextClick: () -> Unit,
//        showDone: Boolean,
//        onDoneClick: () -> Unit
//    ) {
//        Surface(
//            elevation = 7.dp,
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(18.dp)
//            ) {
//                if (showPrevious) {
//                    OutlinedButton(
//                        onClick = onPreviousClick,
//                        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
//                        shape = MaterialTheme.shapes.medium,
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text("이전", style = MaterialTheme.typography.subtitle1)
//                    }
//                    Spacer(modifier = Modifier.width(12.dp))
//                }
//                Button(
//                    onClick = if (showDone) onDoneClick else onNextClick,
//                    enabled = enabledNext,
//                    shape = MaterialTheme.shapes.medium,
//                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Text(
//                        if (showDone) "완료" else "다음",
//                        style = MaterialTheme.typography.subtitle1
//                    )
//                }
//            }
//        }
//    }



