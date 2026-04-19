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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dentapp.R
import com.example.dentapp.presentation.periochart.utils.Caries
import com.example.dentapp.presentation.periochart.utils.IntPoints
import com.example.dentapp.presentation.periochart.utils.TeethDetailedRow
import com.example.dentapp.presentation.periochart.viewmodel.PerioChartViewModel
import com.example.dentapp.ui.theme.Brown
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.Grey
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.LightDarkBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.Pink
import com.example.dentapp.ui.theme.Red
import com.example.dentapp.ui.theme.SkyBlue
import com.example.dentapp.ui.theme.VeryLightBlue
import com.example.dentapp.ui.theme.White
import com.example.dentapp.ui.theme.Yellow


@Composable
fun DetailToothScreen(
    navController: NavHostController,
    viewModel: PerioChartViewModel,
    teethIds: List<Int>
) {
    val upTeeth by viewModel.upTeeth.collectAsState()
    val lowTeeth by viewModel.lowTeeth.collectAsState()

    val currentJawType by viewModel.currentJawType.collectAsState()

    val currentTeeth = when (currentJawType) {
        PerioChartViewModel.JawType.UPPER -> upTeeth
        PerioChartViewModel.JawType.LOWER -> lowTeeth
    }

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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {

                IconButton(
                    onClick = {
                        navController.navigateUp()
                    },
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
                    .padding(top = 8.dp),
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
                    TeethDetailedRow(
                        currentTeeth,
                        teethIds,
                        180.dp
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
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

                        Spacer(modifier = Modifier.width(10.dp))

                        BooleanToothParam(false, "Нет зуба", Grey, White, 24.dp) { }

                        Spacer(modifier = Modifier.width(30.dp))


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

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 2.dp,
                                color = SkyBlue,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .background(LightBlue, shape = RoundedCornerShape(24.dp))
                            .clip(RoundedCornerShape(24.dp))
                            .padding(10.dp)
                    ) {

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                BooleanToothParam(
                                    value = currentTeeth.first { it.id == teethIds.first() }.isImplant,
                                    label = stringResource(R.string.implant)
                                ) {
                                    viewModel.changeIsImplant(
                                        teethIds,
                                        !(currentTeeth.first { it.id == teethIds.first() }.isImplant)
                                    )
                                }

                                ThreeColorsToothParam(label = "Кровоточивость", color = Red)

                                BooleanToothParam(
                                    value = currentTeeth.first { it.id == teethIds.first() }.hasSeal,
                                    label = stringResource(R.string.seal)
                                ) {
                                    viewModel.changeHasSeal(
                                        teethIds,
                                        !(currentTeeth.first { it.id == teethIds.first() }.hasSeal)
                                    )
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    ThreeColorsToothParam(label = "Камни", color = Grey)

                                    ThreeColorsToothParam(label = "Налёт", color = Yellow)
                                }


                                MultiVariantsToothParam(
                                    1,
                                    label = stringResource(R.string.pulp_perio),
                                    states = listOf(
                                        CheckBoxState(null, null),
                                        CheckBoxState(null, Red),
                                        CheckBoxState(null, Grey)
                                    ),
                                    height = 82.dp
                                ) {}

                                CariesToothParam(Caries(), "Кариес") {}

                            }

                            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                                ThreeColorsToothParam(label = "Гноетечение", color = Brown)

                                MovabilityToothParam(3) {}

                                MultiVariantsToothParam(
                                    3,
                                    label = stringResource(R.string.furcation),
                                    states = listOf(
                                        CheckBoxState(null, null),
                                        CheckBoxState(R.drawable.furk1, null),
                                        CheckBoxState(R.drawable.furk2, null),
                                        CheckBoxState(R.drawable.furk3, null)
                                    )
                                ) {}
                            }

                            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                                GraphicsToothParam(IntPoints(), "Десневой край") { a, b -> print(a.toString()) }
                                GraphicsToothParam(IntPoints(), "Глубина зондирования") { a, b -> print(a.toString()) }
                            }
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun GraphicsToothParam(value: IntPoints, label: String, onValueChange: (Int, Int) -> Unit) {
    val options = (-30..30).toList()

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = VeryLightBlue,
        modifier = Modifier
            .height(36.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Text(
                label,
                color = DarkBlue,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                ChooseIntValueBox(value.p1, options) { newValue -> onValueChange(newValue, 1) }
                ChooseIntValueBox(value.p2, options) { newValue -> onValueChange(newValue, 2) }
                ChooseIntValueBox(value.p3, options) { newValue -> onValueChange(newValue, 3) }
            }

        }
    }
}

@Composable
fun MovabilityToothParam(
    value: Int,
    onValueChange: (Int) -> Unit
) {
    val options = (0..30).toList()

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = VeryLightBlue,
        modifier = Modifier
            .height(36.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Text(
                stringResource(R.string.movability),
                color = DarkBlue,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            ChooseIntValueBox(value, options, onValueChange)
        }
    }
}

@Composable
fun ChooseIntValueBox(value: Int?, options: List<Int>, onValueChange: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(LightBlue)
            .padding(4.dp)

            .clickable { expanded = true },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = (value ?: 0).toString(),
            color = DarkBlue,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(VeryLightBlue, RoundedCornerShape(8.dp))
                .heightIn(max = 200.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option.toString(),
                            color = DarkBlue,
                            fontFamily = Montserrat,
                            fontSize = 14.sp
                        )
                    },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun BooleanToothParam(
    value: Boolean,
    label: String,
    bgColor: Color = VeryLightBlue,
    textColor: Color = DarkBlue,
    cornerRadius: Dp = 12.dp,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(cornerRadius),
        color = bgColor,
        modifier = Modifier.height(36.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp)
        ) {

            Text(
                label,
                color = textColor,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Checkbox(
                checked = value,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = textColor,
                    uncheckedColor = textColor,
                    checkmarkColor = bgColor
                ),
                modifier = Modifier.height(5.dp)
            )
        }
    }
}

@Composable
fun CariesToothParam(
    value: Caries,
    label: String,
    onValueChange: (Caries) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = VeryLightBlue,
        modifier = Modifier.height(82.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                label,
                color = DarkBlue,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Column(
                modifier = Modifier.padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(LightBlue)
                            .padding(4.dp)
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "2",
                            color = LightDarkBlue,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(LightBlue)
                            .padding(4.dp)
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "2",
                            color = LightDarkBlue,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(LightBlue)
                            .padding(4.dp)
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "1",
                            color = LightDarkBlue,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(LightBlue)
                            .padding(4.dp)
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "6",
                            color = LightDarkBlue,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(LightBlue)
                            .padding(4.dp)
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "6",
                            color = LightDarkBlue,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(LightBlue)
                            .padding(4.dp)
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "5",
                            color = LightDarkBlue,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun MultiVariantsToothParam(
    value: Int,
    label: String,
    states: List<CheckBoxState>,
    height: Dp = 36.dp,
    onValueChange: (Int) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = VeryLightBlue,
        modifier = Modifier.height(height)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {

            Text(
                label,
                color = DarkBlue,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            CustomMultiCheckbox(
                currentState = value,
                states = states,
                onStateChange = onValueChange
            )
        }
    }
}

data class CheckBoxState(
    val icon: Int?,
    val bgColor: Color?
)

@Composable
fun CustomMultiCheckbox(
    currentState: Int,
    states: List<CheckBoxState>,
    onStateChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clickable {
                val nextState = (currentState + 1) % states.size
                onStateChange(nextState)
            }
            .then(
                Modifier.border(
                    width = 2.dp,
                    color = DarkBlue,
                    shape = RoundedCornerShape(2.dp)
                )
            )
            .background(
                states[currentState].bgColor ?: VeryLightBlue,
                RoundedCornerShape(4.dp)
            )
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        states[currentState].icon?.let { iconRes ->
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ThreeColorsToothParam(
    value: String = "101",
    label: String,
    color: Color,
    onClick: (String) -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = VeryLightBlue,
        modifier = Modifier.height(36.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {

            Text(
                label,
                color = DarkBlue,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.width(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                ColoredBox(if (value[0] == '1') color else LightBlue) {}

                ColoredBox(if (value[1] == '1') color else LightBlue) {}

                ColoredBox(if (value[2] == '1') color else LightBlue) {}
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
                            fontSize = 14.sp,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }
    }
}