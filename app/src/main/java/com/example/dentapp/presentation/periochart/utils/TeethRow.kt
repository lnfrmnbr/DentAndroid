package com.example.dentapp.presentation.periochart.utils

import android.graphics.drawable.PictureDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.caverock.androidsvg.SVG
import com.example.dentapp.presentation.periochart.screen.ToothUiModel
import com.example.dentapp.ui.theme.SkyBlue

@Composable
fun SlidingTeethBox(
    upperJaw: List<ToothUiModel>,
    lowerJaw: List<ToothUiModel>? = null
) {
    val pagerState = rememberPagerState(pageCount = { 2 })

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp))
            .background(Color.White)
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> TeethRow(
                        teeth = upperJaw,
                        onToothClick = { tooth ->
                            println("Clicked upper tooth ${tooth.id}")
                        }
                    )

                    1 -> TeethRow(
                        teeth = lowerJaw ?: emptyList(),
                        onToothClick = { tooth ->
                            println("Clicked lower tooth ${tooth.id}")
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.padding(top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(pagerState.pageCount) { index ->
                    val isActive = pagerState.currentPage == index

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .then(
                                if (isActive) {
                                    Modifier.background(SkyBlue)
                                } else {
                                    Modifier
                                        .background(Color.White)
                                        .border(
                                            width = 1.5.dp,
                                            color = SkyBlue,
                                            shape = CircleShape
                                        )
                                }
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun TeethRow(
    teeth: List<ToothUiModel>,
    onToothClick: (ToothUiModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(unbounded = true),
        horizontalArrangement = Arrangement.spacedBy((-2).dp),
        verticalAlignment = Alignment.Top
    ) {
        teeth.forEachIndexed { index, tooth ->
            if (index == 8) {
                Spacer(modifier = Modifier.width(24.dp))
            }
            ToothSvgItem(
                svgRes = tooth.svgRes,
                modifier = Modifier
                    .height(250.dp),
                onClick = { onToothClick(tooth) }
            )
        }
    }
}

@Composable
fun ToothSvgItem(
    @RawRes svgRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier.clickable { onClick() },
        factory = {
            ImageView(context).apply {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null)
                scaleType = ImageView.ScaleType.FIT_CENTER
                adjustViewBounds = true

                val svg = SVG.getFromResource(context, svgRes)
                setImageDrawable(PictureDrawable(svg.renderToPicture()))
            }
        }
    )
}