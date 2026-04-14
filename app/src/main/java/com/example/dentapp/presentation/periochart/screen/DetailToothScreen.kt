package com.example.dentapp.presentation.periochart.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dentapp.R
import com.example.dentapp.presentation.periochart.utils.TeethDetailedRow
import com.example.dentapp.presentation.periochart.utils.ToothUiModel
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.LightDarkBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.Pink
import com.example.dentapp.ui.theme.SkyBlue
import com.example.dentapp.ui.theme.VeryLightBlue
import com.example.dentapp.ui.theme.White

val uppedrJaw = listOf(
    ToothUiModel(18, R.raw.t18),
    ToothUiModel(17, R.raw.t17),
    ToothUiModel(16, R.raw.t16),
    ToothUiModel(15, R.raw.t15),
    ToothUiModel(14, R.raw.t14),
    ToothUiModel(13, R.raw.t13),
    ToothUiModel(12, R.raw.t12),
    ToothUiModel(11, R.raw.t11),
    ToothUiModel(21, R.raw.t21),
    ToothUiModel(22, R.raw.t22),
    ToothUiModel(23, R.raw.t23),
    ToothUiModel(24, R.raw.t24),
    ToothUiModel(25, R.raw.t25),
    ToothUiModel(26, R.raw.t26),
    ToothUiModel(27, R.raw.t27),
    ToothUiModel(28, R.raw.t28)
)


@Composable
fun DetailToothScreen(
    navController: NavHostController,
    toothJaw: List<ToothUiModel> = uppedrJaw,
    tooth: List<Int> = listOf(28)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 16.dp)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {

                IconButton(
                    onClick = { },//navController.navigateUp() },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.previous_icon),
                        contentDescription = null,
                        tint = LightDarkBlue
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.refresh_icon),
                            contentDescription = null,
                            tint = LightDarkBlue
                        )
                    }

                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.more_icon),
                            contentDescription = null,
                            tint = LightDarkBlue
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = SkyBlue,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .background(Color.White, shape = RoundedCornerShape(24.dp))
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    TeethDetailedRow(toothJaw, tooth, 200.dp)
                }

                Spacer(modifier = Modifier.width(10.dp))


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row {
                        SegmentedControl(
                            modifier = Modifier.weight(1f),
                            items = listOf("Вестибулярно", "Орально"),
                            defaultSelectedItemIndex = 0,//if (selectedTab == Tab.SUBSCRIPTIONS) 0 else 1,
                            onItemSelection = { selectedIndex ->
                                when (selectedIndex) {
                                    0 -> {}//viewModel.selectTab(Tab.SUBSCRIPTIONS)
                                    1 -> {}//viewModel.selectTab(Tab.RECOMMENDATIONS)
                                }
                            }
                        )

                        Spacer(modifier = Modifier.width(40.dp))

                        IconButton(
                            onClick = { },//navController.navigateUp() },
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.prev_tooth_icon),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        IconButton(
                            onClick = { },//navController.navigateUp() },
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.next_tooth_icon),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TeethParams() {
    Box(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = SkyBlue,
                shape = RoundedCornerShape(24.dp)
            )
            .background(LightBlue, shape = RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
    ) {

    }
}

@Composable
fun BooleanToothParam(
    value: Boolean = false,
    label: String = "Имплант",
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = VeryLightBlue,
        modifier = Modifier
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {

            Text(
                label,
                color = DarkBlue,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Checkbox(
                checked = value,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = DarkBlue,
                    uncheckedColor = DarkBlue,
                    checkmarkColor = White
                )
            )
        }
    }
}

@Composable
fun ThreeColorsToothParam(
    value: String = "101",
    label: String = "Кровоточивость",
    onClick: (String) -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = VeryLightBlue,
        modifier = Modifier
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                label,
                color = DarkBlue,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.width(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                ColoredBox(if (value[0] == '1') Pink else LightBlue) {}

                ColoredBox(if (value[1] == '1') Pink else LightBlue) {}

                ColoredBox(if (value[2] == '1') Pink else LightBlue) {}
            }
        }
    }
}

@Composable
fun ColoredBox(color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color)
            .padding(4.dp)
            .width(8.dp)
            .clickable { onClick() }
    ) {
        Text(
            "",
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}

@Composable
fun SegmentedControl(
    modifier: Modifier = Modifier,
    items: List<String>,
    defaultSelectedItemIndex: Int,
    onItemSelection: (selectedItemIndex: Int) -> Unit
) {
    val selectedIndex = remember { mutableIntStateOf(defaultSelectedItemIndex) }

    Card(
        modifier = modifier
            .height(38.dp)
            .border(
                width = 2.dp,
                color = SkyBlue,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, item ->
                Card(
                    modifier = modifier
                        .weight(1f)
                        .clickable {
                            selectedIndex.intValue = index
                            onItemSelection(selectedIndex.intValue)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedIndex.intValue == index) SkyBlue else White,
                        contentColor = if (selectedIndex.intValue == index) White else SkyBlue
                    ),
                    shape = when (index) {
                        0 -> RoundedCornerShape(
                            topStartPercent = 24,
                            bottomStartPercent = 24
                        )

                        items.size - 1 -> RoundedCornerShape(
                            topEndPercent = 24,
                            bottomEndPercent = 24
                        )

                        else -> RoundedCornerShape(0.dp)
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            item,
                            color = if (selectedIndex.intValue == index) White else DarkBlue,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}