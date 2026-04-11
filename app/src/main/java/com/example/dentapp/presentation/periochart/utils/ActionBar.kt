package com.example.dentapp.presentation.periochart.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dentapp.R
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.LightDarkBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.SkyBlue
import com.example.dentapp.ui.theme.White


@Composable
fun ActionButton(
    iconRes: Int,
    strRes: Int,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { onClick() },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .aspectRatio(2f)
                .background(if (isActive) White else LightBlue)
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = DarkBlue
            )
        }

        Text(
            stringResource(strRes),
            color = LightDarkBlue,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ActionBar() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(16.dp)
            .width(90.dp)
    ) {
        IconButton(
            onClick = { },
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .background(SkyBlue)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = null,
                tint = White,
                modifier = Modifier.padding(12.dp)
            )
        }

        ActionButton(R.drawable.pen_icon, R.string.select, true) {}
        ActionButton(R.drawable.back_icon, R.string.back, false) {}
        ActionButton(R.drawable.forward_icon, R.string.forward, false) {}

        Spacer(Modifier.height(8.dp))

        IconButton(
            onClick = { },
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .background(SkyBlue)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.check_mark_icon),
                contentDescription = null,
                tint = White,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}