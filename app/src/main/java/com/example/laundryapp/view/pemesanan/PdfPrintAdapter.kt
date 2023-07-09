package com.example.laundryapp.view.pemesanan

import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintJob
import android.print.PrintManager
import java.io.File
import java.io.FileOutputStream

class PdfPrintAdapter(private val file: File) : PrintDocumentAdapter() {

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?
    ) {
        if (cancellationSignal?.isCanceled == true) {
            callback?.onLayoutCancelled()
            return
        }

        val printDocumentInfo = PrintDocumentInfo.Builder("invoice.pdf")
            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
            .build()

        callback?.onLayoutFinished(printDocumentInfo, true)
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?
    ) {
        val input = file.inputStream()
        val output = FileOutputStream(destination?.fileDescriptor)

        input.copyTo(output)

        callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))

        input.close()
        output.close()
    }

}