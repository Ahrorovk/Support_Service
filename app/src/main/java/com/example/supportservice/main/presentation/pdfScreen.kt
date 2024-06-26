package com.example.supportservice.main.presentation

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import com.example.supportservice.main.domain.main.models.application.Application
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun createAndSavePdf(context: Context, list: List<Application>) {
    val document = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = document.startPage(pageInfo)

    val canvas = page.canvas
    val paint = android.graphics.Paint()
    paint.textSize = 16f

    val items = list
    for ((index, item) in items.withIndex()) {
        canvas.drawText(
            "${item.title}\n${item.description}\n${item.email}\n${item.phone_number}\n${item.status}\n${item.comment}\n\n",
            10f,
            30f + index * 30,
            paint
        )
    }

    document.finishPage(page)

    val directoryPath =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
    val file = File(directoryPath, "backup.pdf")

    try {
        document.writeTo(FileOutputStream(file))
        Toast.makeText(context, "PDF сохранен в Downloads", Toast.LENGTH_SHORT).show()
    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, "Error saving PDF", Toast.LENGTH_SHORT).show()
    } finally {
        document.close()
    }
}
