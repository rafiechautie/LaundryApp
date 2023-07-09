package com.example.laundryapp.view.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.laundryapp.database.Preferences

import com.example.laundryapp.databinding.FragmentSettingsBinding
import com.example.laundryapp.view.SignInActivity
import com.example.laundryapp.view.employee.EmployeeActivity

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = Preferences(requireActivity().applicationContext)

        binding.namaPegawai.text = preferences.getValues("nama")

        binding.usernamePegawai.text = preferences.getValues("username")

        binding.btnLogout.setOnClickListener {
            //simpan data user di preferences
            preferences.setValues("nama", "")
            preferences.setValues("username", "")
            preferences.setValues("password", "")
            preferences.setValues("userType", "")
            //jika status bukan 1 maka user belom login
            //mengatur agar status login menjadi 1
            preferences.setValues("status_login", "")

            startActivity(Intent(requireActivity(), SignInActivity::class.java))
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}