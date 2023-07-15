package com.example.laundryapp.view.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.laundryapp.R
import com.example.laundryapp.database.Preferences
import com.example.laundryapp.databinding.FragmentHomeBinding
import com.example.laundryapp.view.belumlunas.BelumLunasActivity
import com.example.laundryapp.view.customer.CustomerActivity
import com.example.laundryapp.view.employee.EmployeeActivity
import com.example.laundryapp.view.history.HistoryActivity
import com.example.laundryapp.view.lunas.LunasActivity
import com.example.laundryapp.view.pemesanan.LaundryActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var btnEmployee: View
    private lateinit var btnLaundry: View
    private lateinit var btnLunas: View
    private lateinit var btnBelumLunas: View
    private lateinit var btnHistory: View
    private lateinit var btnCustomer: View

    private lateinit var tvUsername: TextView

    private lateinit var preferences: Preferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = Preferences(requireActivity().applicationContext)

        btnEmployee = view.findViewById(R.id.pegawai)
        btnLaundry = view.findViewById(R.id.laundry)
        btnLunas = view.findViewById(R.id.lunas)
        btnBelumLunas = view.findViewById(R.id.belumlunas)
        btnHistory = view.findViewById(R.id.historyTransaksi)
        btnCustomer = view.findViewById(R.id.customer)

        tvUsername = view.findViewById(R.id.username)

//        if (preferences.getValues("userType") === "pegawai"){
//            btnEmployee.
//        }

        tvUsername.text = preferences.getValues("nama")

        btnEmployee.setOnClickListener{
            startActivity(Intent(requireActivity(), EmployeeActivity::class.java))
        }

        btnLaundry.setOnClickListener {
            startActivity(Intent(requireActivity(), LaundryActivity::class.java))
        }

        btnLunas.setOnClickListener {
            startActivity(Intent(requireActivity(), LunasActivity::class.java))
        }

        btnBelumLunas.setOnClickListener {
            startActivity(Intent(requireActivity(), BelumLunasActivity::class.java))
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(requireActivity(), HistoryActivity::class.java))
        }

        btnCustomer.setOnClickListener {
            startActivity(Intent(requireActivity(), CustomerActivity::class.java))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}