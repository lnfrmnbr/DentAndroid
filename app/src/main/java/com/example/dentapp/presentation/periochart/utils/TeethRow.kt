package com.example.dentapp.presentation.periochart.utils

import android.annotation.SuppressLint
import android.graphics.drawable.PictureDrawable
import android.view.View
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.caverock.androidsvg.SVG
import com.example.dentapp.presentation.periochart.viewmodel.PerioChartViewModel
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.SkyBlue

@Composable
fun SlidingTeethBox(
    upperJaw: List<ToothUiModel>,
    lowerJaw: List<ToothUiModel>? = null,
    onSlide: (PerioChartViewModel.JawType) -> Unit,
    onToothClick: (Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 2 })

    LaunchedEffect(pagerState.currentPage) {
        val jawType = when (pagerState.currentPage) {
            0 -> PerioChartViewModel.JawType.UPPER
            1 -> PerioChartViewModel.JawType.LOWER
            else -> PerioChartViewModel.JawType.UPPER
        }
        onSlide(jawType)
    }

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
                            onToothClick(tooth.id)
                        }
                    )

                    1 -> TeethRow(
                        teeth = lowerJaw ?: emptyList(),
                        onToothClick = { tooth ->
                            onToothClick(tooth.id)
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
        teeth.forEach { tooth ->
            ToothSvgItem(
                tooth = tooth,
                modifier = Modifier
                    .height(250.dp),
                onClick = { onToothClick(tooth) }
            )

            if (tooth.id == 11) {
                Spacer(modifier = Modifier.width(24.dp))
            }
        }
    }
}


@Composable
fun TeethDetailedRow(
    teeth: List<ToothUiModel>,
    selectedTeeth: List<Int>,
    maxWidth: Dp
) {
    val density = LocalDensity.current

    val selectedIndices = remember(teeth, selectedTeeth) {
        teeth.mapIndexedNotNull { index, tooth ->
            if (selectedTeeth.contains(tooth.id)) index else null
        }
    }

    val visibleTeeth = remember(teeth, selectedIndices) {
        if (selectedIndices.isEmpty()) {
            teeth.take(7)
        } else {
            val centerIndex = selectedIndices[selectedIndices.size / 2]
            val startIndex = maxOf(0, centerIndex - 3)
            val endIndex = minOf(teeth.size, centerIndex + 4)
            teeth.subList(startIndex, endIndex)
        }
    }

    val positions = remember { mutableStateMapOf<Int, Pair<Float, Float>>() }

    var parentWidth by remember { mutableFloatStateOf(0f) }
    var parentX by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .width(maxWidth)
            .clip(RoundedCornerShape(24.dp))
            .onGloballyPositioned { coords ->
                parentWidth = coords.size.width.toFloat()
                parentX = coords.positionInRoot().x
            }
    ) {

        Row(
            modifier = Modifier.wrapContentWidth(unbounded = true),
            horizontalArrangement = Arrangement.spacedBy((-2).dp),
            verticalAlignment = Alignment.Top
        ) {
            visibleTeeth.forEach { tooth ->

                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .onGloballyPositioned { coords ->
                            val x = coords.positionInRoot().x - parentX
                            val w = coords.size.width.toFloat()
                            positions[tooth.id] = x to (x + w)
                        }
                ) {

                    ToothSvgItem(
                        tooth = tooth,
                        modifier = Modifier
                            .height(300.dp),
                        isClickable = false
                    )
                }

                if (tooth.id == 11) {
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        }

        val firstSelectedTooth = visibleTeeth.firstOrNull { it.id in selectedTeeth }
        val lastSelectedTooth = visibleTeeth.lastOrNull { it.id in selectedTeeth }

        val firstBounds = firstSelectedTooth?.let { positions[it.id] }
        val lastBounds = lastSelectedTooth?.let { positions[it.id] }

        if (firstBounds != null) {
            val leftWidth = firstBounds.first.coerceAtLeast(0f)

            if (leftWidth > 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(with(density) { leftWidth.toDp() })
                        .background(LightBlue.copy(alpha = 0.5f))
                        .zIndex(1f)
                )
            }
        }

        if (lastBounds != null) {
            val rightWidth = (parentWidth - lastBounds.second).coerceAtLeast(0f)

            if (rightWidth > 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(with(density) { rightWidth.toDp() })
                        .align(Alignment.CenterEnd)
                        .background(LightBlue.copy(alpha = 0.5f))
                        .zIndex(1f)
                )
            }
        }
    }
}

@SuppressLint("LocalContextResourcesRead")
@Composable
fun ToothSvgItem(
    tooth: ToothUiModel,
    modifier: Modifier = Modifier,
    isClickable: Boolean = true,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    val svgPicture = remember(tooth.hashCode()) {
        val inputStream = context.resources.openRawResource(tooth.svgRes)
        var svgText = inputStream.bufferedReader().use { it.readText() }

        if (tooth.removed) {
            val id = tooth.id
            svgText = svgText
                .hideSvgGroup("O$id")
                .hideSvgGroup("V$id")
        }

        if (tooth.isImplant) {
            svgText = svgText.showSvgGroup("implant")
        }

        if (tooth.hasSeal) {
            svgText = svgText.showSvgGroup("plomba")
        }

        if (tooth.pulpPerio == 1) {
            svgText = svgText.showSvgGroup("pulpit")
        }

        if (tooth.pulpPerio == 2) {
            svgText = svgText.showSvgGroup("periodontit")
        }

        val svg = SVG.getFromString(svgText)
        svg.renderToPicture()
    }
    key(tooth.hashCode()) {

        AndroidView(
            modifier = modifier.then(
                if (isClickable) Modifier.clickable { onClick() } else Modifier
            ),
            factory = {
                ImageView(context).apply {
                    setLayerType(View.LAYER_TYPE_SOFTWARE, null)
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    adjustViewBounds = true
                }
            },
            update = { imageView ->
                imageView.setImageDrawable(PictureDrawable(svgPicture))
            }
        )
    }
}

private fun String.hideSvgGroup(groupId: String): String {
    val regex = Regex(
        "<g([^>]*id=\"$groupId\"[^>]*)>",
        RegexOption.IGNORE_CASE
    )

    return this.replace(regex) { match ->
        val tag = match.value

        if (tag.contains("display=")) {
            tag.replace(Regex("display=\"[^\"]*\""), "display=\"none\"")
        } else {
            tag.replace("<g", "<g display=\"none\"")
        }
    }
}

private fun String.showSvgGroup(groupId: String): String {
    val regex = Regex(
        "<g([^>]*id=\"$groupId\"[^>]*)>",
        RegexOption.IGNORE_CASE
    )

    return this.replace(regex) { match ->
        val tag = match.value

        when {
            tag.contains("display=\"none\"") -> {
                tag.replace("display=\"none\"", "display=\"inline\"")
            }
            tag.contains(Regex("display=\"[^\"]*\"")) -> {
                tag.replace(Regex("display=\"[^\"]*\""), "display=\"inline\"")
            }
            else -> {
                tag.replace("<g", "<g display=\"inline\"")
            }
        }
    }
}