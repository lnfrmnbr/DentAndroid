package com.example.dentapp.presentation.appointment.utils

import android.content.Context
import android.graphics.*
import android.graphics.pdf.PdfDocument
import androidx.core.content.res.ResourcesCompat
import com.caverock.androidsvg.SVG
import com.example.dentapp.R
import com.example.dentapp.domain.model.appointment.Appointment
import com.example.dentapp.domain.model.clinic.Clinic
import com.example.dentapp.presentation.periochart.utils.Tooth
import com.example.dentapp.utils.DateUtils
import java.io.File
import androidx.core.graphics.withTranslation

class PdfReportGenerator(
    private val context: Context
) {

    private val pageWidth = 1240
    private val pageHeight = 1754

    private lateinit var canvas: Canvas

    private val blackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }

    val montserratNorm = ResourcesCompat.getFont(
        context,
        R.font.montserrat_regular
    )

    val montserratBold = ResourcesCompat.getFont(
        context,
        R.font.montserrat_bold
    )

    private val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 34f
        typeface = montserratBold
    }

    private val boldPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 20f
        typeface = montserratBold
    }

    private val normalPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 20f
        typeface = montserratNorm
    }

    private val sectionPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#F2F2F2")
    }

    fun generate(
        appointment: Appointment,
        clinic: Clinic,
        doctorFullName: String,
        outputFile: File
    ) {

        val document = PdfDocument()

        val pageInfo = PdfDocument.PageInfo.Builder(
            pageWidth,
            pageHeight,
            1
        ).create()

        val page = document.startPage(pageInfo)

        canvas = page.canvas

        drawPage(
            appointment = appointment,
            clinic = clinic,
            doctorFullName = doctorFullName
        )

        document.finishPage(page)

        outputFile.outputStream().use {
            document.writeTo(it)
        }

        document.close()
    }

    private fun drawPage(
        appointment: Appointment,
        clinic: Clinic,
        doctorFullName: String
    ) {

        canvas.drawColor(Color.WHITE)

        var y = 80f

        y = drawHeader(clinic, y)

        y += 10f

        drawDivider(y = y)

        y += 50f

        y = drawPatientInfo(
            appointment = appointment,
            doctorFullName = doctorFullName,
            startY = y
        )

        y += 50f

        y = drawSectionTitle(
            title = "Диагноз",
            y = y
        )

        y += 40f

        y = drawTextRow(
            title = "Жалобы:",
            value = appointment.complaints ?: "",
            y = y
        )

        y += 40f

        y = drawTextRow(
            title = "Диагноз:",
            value = appointment.diagnosis?.joinToString(", ") ?: "",
            y = y
        )

        y += 40f

        y = drawPerioChart(
            teeth = appointment.chart ?: emptyList(),
            y = y
        )

        y += 120f

        canvas.drawText(
            "Подпись",
            50f,
            y,
            boldPaint
        )

        drawDivider(180f, y, 350f)

        canvas.drawText(
            "Расшифровка",
            400f,
            y,
            boldPaint
        )

        drawDivider(590f, y, 900f)

        canvas.drawText(
            "Стр. 1 из 2",
            1100f,
            y,
            normalPaint
        )
    }

    private fun drawHeader(
        clinic: Clinic,
        startY: Float
    ): Float {

        var y = startY

        drawMultilineText(
            text = clinic.orgName,
            x = 50f,
            y = y,
            paint = titlePaint,
            maxWidth = 700f
        )

        drawLogo(
            base64 = clinic.logo,
            x = 900f,
            y = 50f
        )

        y += 80f

        canvas.drawText(
            "Контакты:",
            50f,
            y,
            boldPaint
        )

        drawMultilineText(
            text = clinic.contacts,
            x = 50f,
            y = y + 40f,
            paint = normalPaint,
            maxWidth = 300f
        )

        canvas.drawText(
            "Адрес:",
            350f,
            y,
            boldPaint
        )

        drawMultilineText(
            text = clinic.address,
            x = 350f,
            y = y + 40f,
            paint = normalPaint,
            maxWidth = 300f
        )

        return y + 120f
    }

    private fun drawPatientInfo(
        appointment: Appointment,
        doctorFullName: String,
        startY: Float
    ): Float {

        var y = startY

        val leftX = 50f
        val valueLX = 240f

        val rightX = 750f
        val valueRX = 850f

        drawLabelValue(
            label = "Дата приема:",
            value = DateUtils.toDDMMYYYY(appointment.date),
            x1 = leftX,
            x2 = valueLX,
            y = y
        )

        drawLabelValue(
            label = "Время:",
            value = DateUtils.toHHMM(appointment.date),
            x1 = rightX,
            x2 = valueRX,
            y = y
        )

        y += 40f

        drawLabelValue(
            label = "ФИО пациента:",
            value = appointment.patient?.fullName ?: "",
            x1 = leftX,
            x2 = valueLX,
            y = y
        )

        drawLabelValue(
            label = "Пол:",
            value = appointment.patient?.sex.toString(),
            x1 = rightX,
            x2 = valueRX,
            y = y
        )

        y += 40f

        drawLabelValue(
            label = "Дата рождения:",
            value = appointment.patient?.birthDate?.let {
                DateUtils.toDDMMYYYY(it)
            } ?: "",
            x1 = leftX,
            x2 = valueLX,
            y = y
        )

        drawLabelValue(
            label = "Возраст:",
            value = appointment.patient?.birthDate?.let {
                DateUtils.countAge(it).toString()
            } ?: "",
            x1 = rightX,
            x2 = valueRX,
            y = y
        )

        y += 40f

        drawLabelValue(
            label = "Лечащий врач:",
            value = doctorFullName,
            x1 = leftX,
            x2 = valueLX,
            y = y
        )

        drawLabelValue(
            label = "ИДС:",
            value = "Получено",
            x1 = rightX,
            x2 = valueRX,
            y = y
        )

        return y
    }

    private fun drawSectionTitle(
        title: String,
        y: Float
    ): Float {

        canvas.drawRoundRect(
            RectF(
                40f,
                y,
                pageWidth - 40f,
                y + 60f
            ),
            16f,
            16f,
            sectionPaint
        )

        canvas.drawText(
            title,
            60f,
            y + 40f,
            boldPaint
        )

        return y + 60f
    }

    private fun drawTextRow(
        title: String,
        value: String,
        y: Float
    ): Float {

        canvas.drawText(
            title,
            50f,
            y,
            boldPaint
        )

        drawMultilineText(
            text = value,
            x = 150f,
            y = y,
            paint = normalPaint,
            maxWidth = 850f
        )

        return y
    }

    private fun drawPerioChart(
        teeth: List<Tooth>,
        x: Float = 152f,
        y: Float,
        width: Float = 492f*1.9f,
        height: Float = 472f*1.9f
    ): Float {

        val svg = SVG.getFromResource(
            context,
            R.raw.periochart_pdf
        )

        canvas.withTranslation(x, y) {
            svg.setDocumentWidth(width)
            svg.setDocumentHeight(height)

            svg.renderToCanvas(this)
        }

        return y+height
    }

    private fun drawDivider(x: Float = 40f, y: Float, stopX: Float? = null) {

        canvas.drawLine(
            x,
            y,
            stopX ?: (pageWidth - 40f),
            y,
            blackPaint
        )
    }

    private fun drawLabelValue(
        label: String,
        value: String,
        x1: Float,
        x2: Float,
        y: Float
    ) {

        canvas.drawText(
            label,
            x1,
            y,
            boldPaint
        )

        canvas.drawText(
            value,
            x2,
            y,
            normalPaint
        )
    }

    private fun drawMultilineText(
        text: String,
        x: Float,
        y: Float,
        paint: Paint,
        maxWidth: Float
    ) {

        val words = text.split("\n")

        var line = ""
        var currentY = y

        words.forEach { word ->

            val testLine = "$line$word"

            val width = paint.measureText(testLine)

            if (width > maxWidth) {

                canvas.drawText(
                    line,
                    x,
                    currentY,
                    paint
                )

                line = word
                currentY += paint.textSize + 10f

            } else {

                line = testLine
            }
        }

        canvas.drawText(
            line,
            x,
            currentY,
            paint
        )
    }

    private fun drawLogo(
        base64: String,
        x: Float,
        y: Float
    ) {

        val bitmap = base64ToBitmap(base64)

        canvas.drawBitmap(
            bitmap,
            x,
            y,
            null
        )
    }

    private fun base64ToBitmap(base64: String): Bitmap {

        val decodedBytes = android.util.Base64.decode(
            base64,
            android.util.Base64.DEFAULT
        )

        return BitmapFactory.decodeByteArray(
            decodedBytes,
            0,
            decodedBytes.size
        )
    }
}