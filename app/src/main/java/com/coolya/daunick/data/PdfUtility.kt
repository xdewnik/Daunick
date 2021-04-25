package com.coolya.daunick.data

import android.content.Context
import android.os.Environment
import com.coolya.daunick.data.entities.DiaryEvent
import com.coolya.daunick.ext.toStringDate
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream


class PdfUtility {

    fun createPDF(context: Context, list: List<DiaryEvent>): String {

        //Create document file
        val document = Document();
        val path = "${context.getExternalFilesDir(Environment.DIRECTORY_DCIM)?.path}/Dnewnick.pdf"
        val outputStream = FileOutputStream(
            File(path).apply {
                delete()
                createNewFile()
            }
        );

        PdfWriter.getInstance(document, outputStream)

        //Open the document
        document.open();

        document.pageSize = PageSize.A4_LANDSCAPE;
        document.addAuthor("Ya");
        val defaultFont = FontFactory.getFont(
            "assets/fonts/montserrat_regular.ttf",
            BaseFont.IDENTITY_H,
            true
        )
        val font = FontFactory.getFont(
            "assets/fonts/montserrat_regular.ttf",
            BaseFont.IDENTITY_H,
            true
        ).apply { style = Font.BOLD }
        val fontSmall = FontFactory.getFont(
            "assets/fonts/montserrat_regular.ttf",
            BaseFont.IDENTITY_H,
            true
        ).apply { size = 10f }
        val table = PdfPTable(floatArrayOf(15f, 28f, 15f, 42f))
        table.widthPercentage = 100f
        table.addCell(Phrase("Время", font))
        table.addCell(Phrase("Ситуация", font))
        table.addCell(Phrase("Эмоция", font))
        table.addCell(Phrase("Мысли", font))
        list.forEach {
            table.addCell(Phrase(it.time.toStringDate(), fontSmall))
            table.addCell(Phrase(it.event, defaultFont))
            table.addCell(Phrase("${it.emo} ${it.scale}", defaultFont))
            table.addCell(Phrase(it.thoughts, defaultFont))
        }

        document.add(table)
        document.close()
        return path
    }

}