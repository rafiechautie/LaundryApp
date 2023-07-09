package com.example.laundryapp.view.employee.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.laundryapp.R
import com.example.laundryapp.database.Employee
import com.example.laundryapp.databinding.ActivityEmployeeDetailBinding
import com.example.laundryapp.view.employee.EmployeeActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EmployeeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeDetailBinding

    //inisialisasi database
    private lateinit var database: DatabaseReference
    private lateinit var db: FirebaseDatabase

    private var statusEmployee: String? = null

    private var employee: Employee? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.database


        val employeeRef = db.reference.child(EMPLOYEE_CHILD)

        val userTypes = resources.getStringArray(R.array.usertype_array)
        val adapter = ArrayAdapter(this, R.layout.spinner_item, userTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.SpinnerUserType.adapter = adapter

        binding.SpinnerUserType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                val selectedItem = userTypes[position]
                statusEmployee = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.btnSubmit.setOnClickListener {
            val employeeName = binding.etNamaPegawai.text.toString().trim()
            val employeeUsername = binding.etUsernamePegawai.text.toString().trim()
            val employeePassword = binding.etPassword.text.toString().trim()
            val userType = employee?.userType
            when{
                employeeName.isEmpty() -> {
                    binding.etNamaPegawai.error = getString(R.string.empty)
                }
                employeeUsername.isEmpty() -> {
                    binding.etUsernamePegawai.error = getString(R.string.empty)
                }
                employeePassword.isEmpty() -> {
                    binding.etPassword.error = getString(R.string.empty)
                }
                else-> {

                    val employee = Employee(
                        employeeName,
                        employeeUsername,
                        employeePassword,
                        statusEmployee
                    )
                    employeeRef.push().setValue(employee){error, _ ->
                        if (error != null) {
                            Toast.makeText(this, getString(R.string.send_error) + error.message, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, getString(R.string.send_success), Toast.LENGTH_SHORT).show()
                        }
                    }
                    val intent = Intent(this, EmployeeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }


    }

    companion object {
        const val EMPLOYEE_CHILD = "employee"
    }
}