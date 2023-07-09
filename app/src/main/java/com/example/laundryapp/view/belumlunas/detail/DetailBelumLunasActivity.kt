package com.example.laundryapp.view.belumlunas.detail

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.laundryapp.R
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.databinding.ActivityDetailBelumLunasBinding
import com.example.laundryapp.helper.DateHelper
import com.example.laundryapp.helper.ViewModelFactory
import com.example.laundryapp.view.lunas.detail.DetailPemesananViewModel
import java.io.File
import java.io.FileOutputStream
import android.graphics.Color
import android.os.Build
import android.print.PrintAttributes
import android.print.PrintManager
import androidx.core.graphics.drawable.toBitmap

import com.example.laundryapp.helper.FunctionHelper
import com.example.laundryapp.view.belumlunas.BelumLunasActivity
import com.example.laundryapp.view.pemesanan.PdfPrintAdapter
import com.itextpdf.text.Chunk

import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter


class DetailBelumLunasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBelumLunasBinding

    private var pemesanan: Pemesanan? = null

    private lateinit var detailBelumLunasViewModel: DetailPemesananViewModel
    private val STORAGE_PERMISSION_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBelumLunasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailBelumLunasViewModel = obtainViewModel(this@DetailBelumLunasActivity)

        pemesanan = intent.getParcelableExtra(DetailBelumLunasActivity.EXTRA_PEMESANAN_BELUM_LUNAS)

        val actionBarTitle: String

        actionBarTitle = "Ubah"
        pemesanan?.let { pemesanan ->
            binding.etNamaPelanggan.setText(pemesanan.nama_pelanggan)
            binding.etAlamat.setText(pemesanan.alamat_pelanggan)
            binding.etPakaian.setText(pemesanan.jumlah_pakaian.toString())
            binding.etSepatu.setText(pemesanan.jumlah_sepatu.toString())
            binding.etSprei.setText(pemesanan.jumlah_bed_cover.toString())
            binding.etKarpet.setText(pemesanan.jumlah_karpet.toString())
            binding.etTotal.setText(pemesanan.total_pembayaran.toString())
            binding.etTanggalAmbil.setText(pemesanan.tanggal_pengambilan.toString())
            binding.SpinnerStatusPembayaran.setSelection(1)

        }

        val userTypes = resources.getStringArray(R.array.statusPembayaran_array)
        val adapter = ArrayAdapter(this, R.layout.spinner_item, userTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.SpinnerStatusPembayaran.adapter = adapter

        binding.SpinnerStatusPembayaran.setSelection(1)

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.SpinnerStatusPembayaran.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                val selectedItem = userTypes[position]
                pemesanan?.status_pembayaran = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }


        binding.btnUpdate.setOnClickListener {
            pemesanan.let {

                pemesanan?.date = DateHelper.getCurrentDate()
            }
            detailBelumLunasViewModel.update(pemesanan as Pemesanan)

            createPDF(
                pemesanan?.nama_pelanggan.toString(),
                pemesanan?.alamat_pelanggan.toString(),
                pemesanan?.date,
                pemesanan?.tanggal_pengambilan,
                pemesanan?.jumlah_pakaian,
                pemesanan?.jumlah_sepatu,
                pemesanan?.jumlah_bed_cover,
                pemesanan?.jumlah_karpet,
                pemesanan?.status_pembayaran,
                pemesanan?.total_pembayaran
                )
            showToast(getString(R.string.changed))
        }
    }

    fun createPDF(name: String?, alamat: String?, date: String?, tanggal_ambil: String?, pakaian: Int?, sepatu: Int?, sprei: Int?, karpet: Int?, status: String?, totalPrice: Int?) {
        // Create an instance of Document
        val document = Document()

        // Get the directory for storing the PDF file
        val directory = getExternalFilesDir(null)

        // Create a new file with the specified name
        val file = File(directory, "invoice.pdf")

        // Create a FileOutputStream to write the PDF file
        val fileOutputStream = FileOutputStream(file)

        // Create a PdfWriter instance to write the document to the file
        PdfWriter.getInstance(document, fileOutputStream)


        // Open the document
        document.open()

        val table = PdfPTable(2)

        val HargadiRupiah = totalPrice?.let { FunctionHelper.rupiahFormat(it) }

        // Add content to the document
        val date = "Tanggal Pemesanan: $date"
        val customertanggalAmbil = "Tanggal Ambil: $tanggal_ambil"
        val customerName = "Nama Customer: $name"
        val customerAlamat = "Alamat Customer: $alamat"
        val customerPakaian = "$pakaian Kg"
        val customerSepatu = "$sepatu Kg"
        val customerSprei = "$sprei Kg"
        val customerKarpet = "$karpet Kg"
        val customerStatus = "Status Pembayaran $status"
        val totalAmount = "$HargadiRupiah"



        // Create a Paragraph and add it to the document
        val paragraph = Paragraph()

        val boldFont = Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.BOLD)

        val centeredNotaLaundry= Paragraph("NOTA LAUNDRY", boldFont)
        centeredNotaLaundry.alignment = Element.ALIGN_CENTER
        paragraph.add(centeredNotaLaundry)

        val centeredNotaAlamatLaundry = Paragraph("JALAN MAYOR SALIM BATUBARA NO 5987 KOTA PALEMBANG", boldFont)
        centeredNotaAlamatLaundry.alignment = Element.ALIGN_CENTER
        paragraph.add(centeredNotaAlamatLaundry)

        val centeredNo= Paragraph("Nomor Telepon: 08117314334", boldFont)
        centeredNo.alignment = Element.ALIGN_CENTER
        paragraph.add(centeredNo)


        paragraph.add(Chunk.NEWLINE) // Menambahkan baris baru

        val centeredTextGaris = Paragraph("====================================")
        centeredTextGaris.alignment = Element.ALIGN_CENTER
        paragraph.add(centeredTextGaris)


        val centeredTextName = Paragraph(customerName)
        centeredTextName.alignment = Element.ALIGN_CENTER
        paragraph.add(centeredTextName)

        val centeredTextAlamat = Paragraph(customerAlamat)
        centeredTextAlamat.alignment = Element.ALIGN_CENTER
        paragraph.add(centeredTextAlamat)

        val centeredTextDate = Paragraph(date)
        centeredTextDate.alignment = Element.ALIGN_CENTER
        paragraph.add(centeredTextDate)

        val centeredTextTanggalAmbil = Paragraph(customertanggalAmbil)
        centeredTextTanggalAmbil.alignment = Element.ALIGN_CENTER
        paragraph.add(centeredTextTanggalAmbil)

        val centeredTextStatus = Paragraph(customerStatus)
        centeredTextStatus.alignment = Element.ALIGN_CENTER
        paragraph.add(centeredTextStatus)

        val centeredTextGaris2 = Paragraph("====================================")
        centeredTextGaris2.alignment = Element.ALIGN_CENTER
        paragraph.add(centeredTextGaris2)


        paragraph.add(Chunk.NEWLINE) // Menambahkan baris baru

        document.add(paragraph)

//        val boldFont = Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.BOLD)

        val cellBarang = PdfPCell(Phrase("Barang", boldFont))
        val cellJumlahKG = PdfPCell(Phrase("Jumlah KG", boldFont))

        table.widthPercentage = 100f

        cellBarang.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cellBarang)
        cellJumlahKG.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cellJumlahKG)


        val cellPakaian = PdfPCell(Phrase("Pakaian"))
        cellPakaian.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cellPakaian)

        val cellJumlahPakaian = PdfPCell(Phrase(customerPakaian))
        cellJumlahPakaian.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cellJumlahPakaian)


        val cellSepatu = PdfPCell(Phrase("Sepatu"))
        cellSepatu.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cellSepatu)

        val cellJumlahSepatu = PdfPCell(Phrase(customerSepatu))
        cellJumlahSepatu.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cellJumlahSepatu)

        val cellSprei = PdfPCell(Phrase("Sprei"))
        cellSprei.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cellSprei)

        val cellJumlahSprei = PdfPCell(Phrase(customerSprei))
        cellJumlahSprei.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cellJumlahSprei)

        val cellKarpet = PdfPCell(Phrase("Karpet"))
        cellKarpet.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cellKarpet)

        val cellJumlahKarpet = PdfPCell(Phrase(customerKarpet))
        cellJumlahKarpet.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cellJumlahKarpet)


        val cellTotalPembayaran = PdfPCell(Phrase("Total Pembayaran", boldFont))
        val cellTotalAmount = PdfPCell(Phrase(totalAmount, boldFont))

        cellTotalPembayaran.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cellTotalPembayaran)
        cellTotalAmount.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cellTotalAmount)



        // Add the paragraph to the document

        document.add(table)

        // Close the document
        document.close()

        // Print the PDF file
        printPDF(file)
    }


    private fun isStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE
        )
    }

    private fun printPDF(file: File) {
        val printManager = getSystemService(PRINT_SERVICE) as PrintManager
        val jobName = getString(R.string.app_name) + " Document"
        val printAdapter = PdfPrintAdapter(file)
        val printAttributes = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.ISO_A5)
            .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
            .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
            .build()

        printManager.print(jobName, printAdapter, printAttributes)
    }






    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailPemesananViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailPemesananViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_detail_pemesanan, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    detailBelumLunasViewModel.delete(pemesanan as Pemesanan)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }



    companion object {
        const val EXTRA_PEMESANAN_BELUM_LUNAS = "extra_pemesanan_belum_lunas"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
        private const val PERMISSION_REQUEST_CODE = 123
    }
}