package com.example.laundryapp.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast

import com.example.laundryapp.R
import com.example.laundryapp.database.Employee
import com.example.laundryapp.database.Preferences
import com.example.laundryapp.databinding.ActivitySignInBinding
import com.google.firebase.database.*


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    //inisialisasi database
    private lateinit var database: FirebaseDatabase

    private lateinit var usersRef: DatabaseReference

    private lateinit var pref: Preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()

        showLoadingData(false)
        database = FirebaseDatabase.getInstance()
        usersRef = database.getReference("employee")

        pref = Preferences(this)

        if (pref.getValues("status_login").equals("1")){
            //jika status login 1
            //menghapus seluruh activity yang sebelumnya udah tampil (onboarding dan splashscreen)
            finishAffinity()
            //arahkan ke halaman home
            val intent = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {

//            startActivity(Intent(this, HomeActivity::class.java))
            val inputUsername = binding.usernameEditText.text.toString()
            val inputPassword = binding.passwordEditText.text.toString()

            when{
                inputUsername.isEmpty() -> {
                    binding.usernameEditText.error = getString(R.string.empty)
                    binding.usernameEditText.requestFocus()
                }
                inputPassword.isEmpty() -> {
                    binding.passwordEditText.error = getString(R.string.empty)
                    binding.usernameEditText.requestFocus()
                }
                else -> {
                    showLoadingData(true)
                    login(inputUsername, inputPassword)
                    showLoadingData(false)
                }
            }
        }
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

    private fun login(inputUsername: String, inputPassword: String) {
        val query = usersRef.orderByChild("username").equalTo(inputUsername)

        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(Employee::class.java)
                        if (user?.password == inputPassword) {
                            // Login successful, proceed to next screen
                            Toast.makeText(this@SignInActivity, "Login successful", Toast.LENGTH_SHORT).show()

                            //simpan data user di preferences
                            pref.setValues("nama", user.nama.toString())
                            pref.setValues("username", user.username.toString())
                            pref.setValues("password", user.password.toString())
                            pref.setValues("userType", user.userType.toString())
                            //jika status bukan 1 maka user belom login
                            //mengatur agar status login menjadi 1
                            pref.setValues("status_login", "1")

                            finishAffinity()

                            var intent = Intent(this@SignInActivity, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            // Invalid password
                            Toast.makeText(this@SignInActivity, "Invalid password", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    // User not found
                    Toast.makeText(this@SignInActivity, "User not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //tampilakn toast bahwa ada yang error dari db realtime firebase
                Toast.makeText(this@SignInActivity, error.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun showLoadingData(state: Boolean) {
        if (state) {
            binding.progressBarLogin.visibility = View.VISIBLE
        } else {
            binding.progressBarLogin.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val EMPLOYEE_CHILD = "employee"
        const val TAG = "signin Activity"
    }
}