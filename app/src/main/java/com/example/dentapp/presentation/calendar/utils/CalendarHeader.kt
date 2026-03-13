package com.example.dentapp.presentation.calendar.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dentapp.R
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.NewBlue
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CalendarHeader(
    yearMonth: YearMonth,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {

    val formatter = DateTimeFormatter.ofPattern("LLLL", Locale("ru"))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onPrev) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                tint = NewBlue
            )
        }

        Text(
            text = yearMonth.format(formatter).uppercase(),
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .background(NewBlue, RoundedCornerShape(10.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp)
        )

        IconButton(onClick = onNext) {
            Icon(
                painter = painterResource(id = R.drawable.next),
                contentDescription = null,
                tint = NewBlue
            )
        }
    }
}