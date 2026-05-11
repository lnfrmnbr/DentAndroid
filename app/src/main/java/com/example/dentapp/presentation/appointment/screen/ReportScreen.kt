package com.example.dentapp.presentation.appointment.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dentapp.domain.model.appointment.Appointment
import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.domain.model.clinic.Clinic
import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.presentation.appointment.utils.PdfPage1
import com.example.dentapp.presentation.appointment.utils.PdfReportGenerator
import com.example.dentapp.presentation.appointment.utils.teethUnit
import com.example.dentapp.presentation.appointment.viewmodel.AppointmentViewModel
import com.example.dentapp.presentation.navigation.BottomNavItem
import com.example.dentapp.ui.theme.Montserrat
import java.io.File
import java.time.LocalDateTime
import java.util.Date


@Composable
fun ReportScreen(
    navController: NavHostController,
    viewModel: AppointmentViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val context = LocalContext.current

    Button(
        onClick = {
            val appointment =
                        Appointment(
                            patient = Patient(
                                fullName = "Иванов Иван Иванович",
                                birthDate = Date(2000, 1, 1),
                                sex = "M",
                                email = "spamovik123@ya.ry",
                                phoneNumber = "+79111129019"
                            ),
                            date = LocalDateTime.now(),
                            tags = listOf("осмотр", "брекиты"),
                            indexes = Indexes(),
                            complaints = "болит всн",
                            comments = "комментариииииии",
                            plan = "чистка, брекиты, паста когейт",
                            isFirst = true,
                            diagnosis = listOf("Пульпит", "Кариес"),
                            chart = teethUnit
                        )
            val clinic =
                        Clinic(
                            logo = "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",
                            orgName = "ООО \"Арт Класс\" СК\"",
                            shortName = "Арт Класс",
                            contacts = "+7 (812) 492-90-00\n" +
                                    "+7 (812) 715-82-17\n" +
                                    "mail@ak-stom.ru",
                            address = "Санкт-Петербург, \n" +
                                    "Ланское шоссе, д. 65",
                            email = "spamovik123@ya.ru"
                        )
            val file = File(
                context.cacheDir,
                "report.pdf"
            )

            PdfReportGenerator(context).generate(
                appointment = appointment,
                clinic = clinic,
                doctorFullName = "Домбровская Юлия Андреевна",
                outputFile = file
            )

            openPdf(context, file)
        }
    ) {
        Text(
            text = "Скачать отчет",
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
        }


fun createBitmapFromCompose(
    activity: Activity,
    content: @Composable () -> Unit
): Bitmap {

    val composeView = ComposeView(activity).apply {
        setContent { content() }
    }

    val root = FrameLayout(activity)
    root.addView(composeView)

    // ВАЖНО: добавляем в decorView
    val decor = activity.window.decorView as ViewGroup
    decor.addView(root)

    composeView.measure(
        View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(1920, View.MeasureSpec.AT_MOST)
    )

    composeView.layout(0, 0, composeView.measuredWidth, composeView.measuredHeight)

    val bitmap = composeView.drawToBitmap()

    // УДАЛЯЕМ после рендера
    decor.removeView(root)

    return bitmap
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

fun createPdfPageView(
    context: Context,
    appointment: Appointment,
    clinic: Clinic,
    doctorFullName: String
): View {
    return ComposeView(context).apply {
        setContent {
            PdfPage1(
                appointment = appointment,
                clinic = clinic,
                doctorFullName = doctorFullName
            )
        }

        measure(
            View.MeasureSpec.makeMeasureSpec(1240, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(1754, View.MeasureSpec.EXACTLY)
        )
        layout(0, 0, measuredWidth, measuredHeight)
    }
}


fun viewToBitmap(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun createPdf(context: Context, bitmap: Bitmap, file: File) {
    val pdfDocument = PdfDocument()

    val pageInfo = PdfDocument.PageInfo.Builder(
        bitmap.width,
        bitmap.height,
        1
    ).create()

    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    canvas.drawBitmap(bitmap, 0f, 0f, null)

    pdfDocument.finishPage(page)

    file.outputStream().use {
        pdfDocument.writeTo(it)
    }

    pdfDocument.close()
}