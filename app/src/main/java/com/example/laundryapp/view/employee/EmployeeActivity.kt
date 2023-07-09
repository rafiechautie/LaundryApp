package com.example.laundryapp.view.employee

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundryapp.R
import com.example.laundryapp.database.Employee
import com.example.laundryapp.databinding.ActivityEmployeeBinding
import com.example.laundryapp.view.employee.detail.EmployeeDetailActivity
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeBinding

    private lateinit var adapter: EmployeeAdapter
    private lateinit var db: FirebaseDatabase

    private lateinit var dataList: MutableList<Employee>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val actionBarTitle: String

        actionBarTitle = "Daftar Employee"
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        db = Firebase.database

        val employeeRef = db.reference.child(EmployeeDetailActivity.EMPLOYEE_CHILD)

        // Inisialisasi data
        dataList = mutableListOf()
        // Ambil data dari Realtime Database dan tambahkan ke dataList

        val manager = LinearLayoutManager(this)

        binding.rvNotes.layoutManager = manager

        val options = FirebaseRecyclerOptions.Builder<Employee>()
            .setQuery(employeeRef, Employee::class.java)
            .build()
        adapter = EmployeeAdapter(options)
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.adapter = adapter


        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@EmployeeActivity, EmployeeDetailActivity::class.java)
            startActivity(intent)
        }

//        imageDeleteUser.setOnClickListener {
//            showToast("delete")
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Tambahkan kode untuk mengarahkan ke halaman home di sini
                onBackPressed() // Jika Anda ingin menavigasi ke halaman sebelumnya, jika ada
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    public override fun onResume() {
        super.onResume()
        adapter.startListening()
    }
    public override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }
}