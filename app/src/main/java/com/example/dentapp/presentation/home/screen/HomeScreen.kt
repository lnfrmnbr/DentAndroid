package com.example.dentapp.presentation.home.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.dentapp.R
import com.example.dentapp.presentation.home.viewmodel.HomeViewModel
import com.example.dentapp.presentation.home.viewmodel.HomeViewModel.HomeTabs
import com.example.dentapp.ui.theme.Blue
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.SkyBlue
import com.example.dentapp.ui.theme.VeryLightBlue
import com.example.dentapp.ui.theme.White

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val firstName by homeViewModel.firstName.collectAsState()
    val patronymic by homeViewModel.patronymic.collectAsState()
    val selectedTab by homeViewModel.selectedTab.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp, top = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LittleProfileInfo(firstName, patronymic)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TabCard(selectedTab, HomeTabs.UpcomingAppointments.title, R.drawable.upcoming_icon) { homeViewModel.selectTab(it) }

                TabCard( selectedTab, HomeTabs.ArchiveAppointments.title, R.drawable.archive_icon) { homeViewModel.selectTab(it) }

                TabCard(selectedTab,HomeTabs.ListOfPatients.title, R.drawable.patients_icon) { homeViewModel.selectTab(it) }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Заголовок",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )


                }
            }
        }
    }
}

@Composable
fun LittleProfileInfo(
    firstName: String,
    patronymic: String,
    avatarUrl: String? = null
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ProfileImagePicker(avatarUrl)

        Box(
            modifier = Modifier.align(Alignment.CenterVertically),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    "$firstName $patronymic",
                    color = Blue,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 16.sp
                )

                Text(
                    stringResource(R.string.go_to_profile),
                    color = Blue,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )
            }
        }
    }

}

@Composable
fun ProfileImagePicker(
    avatarUrl: String? = null
) {
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri ->
//        uri?.let { profileViewModel.uploadAvatar(it) }
//    }

    Box(
        modifier = Modifier
            .size(45.dp)
            .clip(CircleShape)
            .border(1.5.dp, Blue, CircleShape),
        //.clickable { launcher.launch("image/*") }
        contentAlignment = Alignment.Center
    ) {
        if (!avatarUrl.isNullOrEmpty()) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = "Аватар пользователя",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Загрузить фото",
                tint = Blue,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun TabCard(
    selectedTab:String,
    title: String,
    @DrawableRes iconRes: Int,
    onClick: (String) -> Unit
) {
    val isSelected = title == selectedTab
    Box(
        modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(20.dp))

            .border(
                width = if (isSelected) 2.dp else (-1).dp,
                color = SkyBlue,
                shape = RoundedCornerShape(20.dp)
            )
            .background(if (isSelected) White else VeryLightBlue)
            .clickable { onClick(title) },
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(SkyBlue)
            )

            Text(
                text = title,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Blue,
                lineHeight = 17.sp
            )
        }
    }
}