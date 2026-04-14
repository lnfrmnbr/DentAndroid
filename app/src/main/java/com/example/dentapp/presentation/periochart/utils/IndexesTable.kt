package com.example.dentapp.presentation.periochart.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dentapp.ui.theme.Grey
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.LightDarkBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.SkyBlue
import com.example.dentapp.ui.theme.White

@Composable
fun IndexesTable() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(
                width = 1.dp,
                color = Grey,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.weight(0.4f)
            ) {
                TableHeader("Индекс")
                TableRow("КПУ")
                TableRow("BOP")
                TableRow("API")
                CpitnRow("CPITN")
                TableRow("Russel")
                TableRow("OHI_S", false)
            }

            VerticalDivider()

            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                TableHeader("Результат")
                TableRow("0")
                TableRow("0%")
                TableRow("0%")
                CpitnValueRow()
                TableRow("0")
                TableRow("0", false)
            }

            VerticalDivider()

            Column(
                modifier = Modifier.weight(1.5f)
            ) {
                TableHeader("Интерпретация")
                TableRow("Низкая степень тяжести кариозного процесса")
                TableRow("Норма")
                TableRow("Оптимальный уровень гигиены")
                CpitnRow("Лечение не требуется")
                TableRow("Норма")
                TableRow("Хорошая гигиена", false)
            }
        }
    }
}

@Composable
fun TableHeader(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text,
            color = LightDarkBlue,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
    HorizontalDivider()
}

@Composable
fun TableRow(text: String, needDivider: Boolean = true) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 36.dp)
            .padding(top = 4.dp, bottom = 4.dp, start = 4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text,
            color = LightDarkBlue,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
    }

    if (needDivider) HorizontalDivider()
}

@Composable
fun CpitnValueRow() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 65.dp)
            .padding(top = 4.dp, bottom = 4.dp, start = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(LightBlue)
                    .padding(4.dp)
            ) {
                Text(
                    "0",
                    color = LightDarkBlue,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(LightBlue)
                    .padding(4.dp)
            ) {
                Text(
                    "0",
                    color = LightDarkBlue,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(LightBlue)
                    .padding(4.dp)
            ) {
                Text(
                    "0",
                    color = LightDarkBlue,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
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
            ) {
                Text(
                    "0",
                    color = LightDarkBlue,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(LightBlue)
                    .padding(4.dp)
            ) {
                Text(
                    "0",
                    color = LightDarkBlue,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(LightBlue)
                    .padding(4.dp)
            ) {
                Text(
                    "0",
                    color = LightDarkBlue,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }
    }

    HorizontalDivider()
}

@Composable
fun CpitnRow(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 65.dp)
            .padding(top = 4.dp, bottom = 4.dp, start = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text,
            color = LightDarkBlue,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
    }
    HorizontalDivider()
}

@Composable
fun VerticalDivider() {
    Divider(
        color = SkyBlue,
        modifier = Modifier
            .fillMaxHeight()
            .width(2.dp)
    )
}

@Composable
fun HorizontalDivider() {
    Divider(
        color = SkyBlue,
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
    )
}