package com.example.laundryapp.view.pemesanan



import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import java.io.File
import java.io.FileOutputStream
import android.view.WindowInsets
import android.view.WindowManager
import android.print.*
import com.example.laundryapp.R
import com.example.laundryapp.database.Preferences
import com.example.laundryapp.databinding.ActivityCheckOutSuccessBinding
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.laundryapp.helper.FunctionHelper
import com.example.laundryapp.view.HomeActivity
import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter

class CheckOutSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckOutSuccessBinding

    private lateinit var preferences: Preferences

    private val hargaPakaian = 7000
    private val hargaSepatu = 5000
    private val hargaSprei = 55000
    private val hargaKarpet = 150000


    private val STORAGE_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()

        preferences = Preferences(this.applicationContext)

        val name = intent.getStringExtra(EXTRA_NAMA)
        val pakaian = intent.getIntExtra(EXTRA_PAKAIAN, 0)
        val sepatu = intent.getIntExtra(EXTRA_SEPATU, 0)
        val sprei = intent.getIntExtra(EXTRA_SPREI, 0)
        val karpet = intent.getIntExtra(EXTRA_KARPET, 0)
        val totalPrice = intent.getIntExtra(EXTRA_TOTAL_PRICE, 0)
        val alamat = intent.getStringExtra(EXTRA_ALAMAT)
        val noHp = intent.getStringExtra(EXTRA_NO_HP)
        val date = intent.getStringExtra(EXTRA_DATE)
        val status = intent.getStringExtra(EXTRA_STATUS)
        val tanggalPengambilan = intent.getStringExtra(EXTRA_TANGGAL_PENGAMBILAN)

        binding.tvTotal.text = FunctionHelper.rupiahFormat(totalPrice)
        binding.tvPenjual.text = preferences.getValues("nama")
        binding.tvPenbeli.text = name
        binding.totalPakaian.text = pakaian.toString()
        binding.totalSepatu.text =  sepatu.toString()

        binding.totalSprei.text = sprei.toString()

        binding.totalKarpet.text = karpet.toString()
        binding.tvTanggalAmbil.text = tanggalPengambilan

        // Request storage permission if not granted
        if (!isStoragePermissionGranted()) {
            requestStoragePermission()
        }

        binding.btnCetakNota.setOnClickListener {
            createPDF(
                name,
                alamat,
                noHp,
                date,
                tanggalPengambilan,
                pakaian,
                sepatu,
                sprei,
                karpet,
                status,
                totalPrice)
        }

        binding.btnBelumLunas.setOnClickListener {
            startActivity(Intent(this@CheckOutSuccessActivity, HomeActivity::class.java))
        }
    }

    fun createPDF(name: String?, alamat: String?, noHp: String?, date: String?, tanggal_ambil: String?, pakaian: Int?, sepatu: Int?, sprei: Int?, karpet: Int?, status: String?, totalPrice: Int?) {
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
        val customerNoHp = "Nomor Handphone Customer: $noHp"
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

        val centeredTextNoHp = Paragraph(customerNoHp)
        centeredTextNoHp.alignment = Element.ALIGN_CENTER
        paragraph.add(centeredTextNoHp)

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

    private fun setUpView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    companion object{
        private val TAG = "LAUNDRY ACTIVITY"
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_PAKAIAN = "extra_pakaian"
        const val EXTRA_SEPATU = "extra_sepatu"
        const val EXTRA_SPREI = "extra_sprei"
        const val EXTRA_KARPET = "extra_karpet"
        const val EXTRA_TOTAL_PRICE = "extra_total_price"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_ALAMAT = "extra_alamat"
        const val EXTRA_NO_HP = "extra_no_hp"
        const val EXTRA_STATUS = "extra_status"
        const val EXTRA_TANGGAL_PENGAMBILAN = "extra_tanggal_pengambilan"
    }
}