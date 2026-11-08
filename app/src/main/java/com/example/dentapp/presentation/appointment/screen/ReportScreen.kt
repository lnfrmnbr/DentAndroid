package com.example.dentapp.presentation.appointment.screen

import android.content.Context
import android.content.Intent
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.core.graphics.createBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.dentapp.R
import com.example.dentapp.presentation.appointment.utils.PdfReportGenerator
import com.example.dentapp.presentation.appointment.viewmodel.AppointmentViewModel
import com.example.dentapp.presentation.navigation.Screen
import com.example.dentapp.ui.theme.Blue
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.LightDarkBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.NewBlue
import com.example.dentapp.ui.theme.White
import com.example.dentapp.utils.DateUtils
import java.io.File
import java.time.LocalDateTime


@Composable
fun ReportScreen(
    navController: NavHostController,
    viewModel: AppointmentViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val appointment by viewModel.appointment.collectAsState()
    val clinic by viewModel.clinic.collectAsState()
    val doctorFIO by viewModel.doctorFIO.collectAsState()
    val isLocal by viewModel.isOfflineMode.collectAsState()

    val context = LocalContext.current

    val fileName = "${appointment?.patient?.fullName ?: "ФИО"}_" +
            "${DateUtils.toDD_MM_YYYY_HH_MM(appointment?.date ?: LocalDateTime.now())}.pdf"

    val pdfFile = remember {
        File(context.cacheDir, fileName)
    }

    var pdfReady by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(appointment, clinic) {
        if (appointment != null && clinic != null) {

            PdfReportGenerator(context).generate(
                appointment = appointment!!,
                clinic = clinic!!,
                doctorFullName = doctorFIO,
                outputFile = pdfFile
            )

            pdfReady = pdfFile.exists()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 56.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.previous_icon),
                        contentDescription = null,
                        tint = LightDarkBlue
                    )
                }


                Row {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.share_icon),
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

            Spacer(modifier = Modifier.height(16.dp))

            if (pdfReady) {

                    PdfPreview(
                        file = pdfFile
                    )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.file_icon),
                        contentDescription = "file_icon",
                        modifier = Modifier.size(40.dp),
                        colorFilter = ColorFilter.tint(NewBlue)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(
                            text = fileName,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = DarkBlue
                        )

                        Text(
                            text = "pdf-file",
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = DarkBlue
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    openPdf(context, pdfFile)
                },
                enabled = appointment != null && clinic != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = Color.Blue,
                    disabledContainerColor = White.copy(alpha = 0.5f),
                    disabledContentColor = Color.Blue.copy(alpha = 0.7f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.Blue,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Скачать отчёт",
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Blue
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (!isLocal) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        navController.navigate(Screen.OfflineGraph.route) {
                            popUpTo(Screen.AuthGraph.route) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue,
                    contentColor = Color.White,
                    disabledContainerColor = Blue.copy(alpha = 0.5f),
                    disabledContentColor = Color.White.copy(alpha = 0.7f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Вернуться на главную",
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = White
                    )
                }
            }
        }
    }
}

@Composable
fun PdfPreview(
    file: File,
    modifier: Modifier = Modifier
) {
    val bitmap = remember(file) {
            val renderer = PdfRenderer(
                ParcelFileDescriptor.open(
                    file,
                    ParcelFileDescriptor.MODE_READ_ONLY
                )
            )

            val page = renderer.openPage(0)

            val bmp = createBitmap((page.width / 2.8).toInt(), (page.height / 2.8).toInt())

            page.render(
                bmp,
                null,
                null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
            )

            page.close()
            renderer.close()

            bmp
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "PDF preview page 1"
        )

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "PDF preview page 2"
        )
    }
}

fun openPdf(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }

    context.startActivity(intent)
}