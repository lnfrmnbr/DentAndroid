package com.example.dentapp.presentation.periochart.screen

import android.graphics.drawable.PictureDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.RawRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.caverock.androidsvg.SVG
import com.example.dentapp.R

val upperJaw = listOf(
    ToothUiModel("11", R.raw.o18),
    ToothUiModel("12", R.raw.o17),
    ToothUiModel("13", R.raw.o16),
    ToothUiModel("14", R.raw.o15),
    ToothUiModel("15", R.raw.o14),
    ToothUiModel("16", R.raw.o13),
    ToothUiModel("17", R.raw.o12),
    ToothUiModel("18", R.raw.o11),
    ToothUiModel("11", R.raw.o21),
    ToothUiModel("12", R.raw.o22),
    ToothUiModel("13", R.raw.o23),
    ToothUiModel("14", R.raw.o24),
    ToothUiModel("15", R.raw.o25),
    ToothUiModel("16", R.raw.o26),
    ToothUiModel("17", R.raw.o27),
    ToothUiModel("18", R.raw.o28)
)

@Composable
fun PerioChartScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        TeethRow(
            teeth = upperJaw,
            onToothClick = { tooth ->
                println("Clicked tooth ${tooth.id}")
            }
        )
    }
}


@Composable
fun TeethRow(
    teeth: List<ToothUiModel>,
    onToothClick: (ToothUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        teeth.forEach { tooth ->
            ToothSvgItem(
                svgRes = tooth.svgRes,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
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

                val svg = SVG.getFromResource(context, svgRes)
                setImageDrawable(PictureDrawable(svg.renderToPicture()))

                setLayerType(View.LAYER_TYPE_SOFTWARE, null)

                adjustViewBounds = false
                scaleType = ImageView.ScaleType.FIT_CENTER
            }
        }
    )
}