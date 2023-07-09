package com.example.laundryapp.view.employee

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryapp.R
import com.example.laundryapp.database.Employee
import com.example.laundryapp.databinding.ItemEmployeeBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class EmployeeAdapter(
    options: FirebaseRecyclerOptions<Employee>,
    ) : FirebaseRecyclerAdapter<Employee, EmployeeAdapter.EmployeeViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_employee, parent, false)
        val binding = ItemEmployeeBinding.bind(view)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int, model: Employee) {
        holder.bind(model)
    }

    inner class EmployeeViewHolder(private val binding: ItemEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Employee) {
            binding.tvItemName.text = item.nama
            binding.tvItemUsername.text = item.username
            binding.btnDeleteUser.setOnClickListener {
                val employee = getItem(adapterPosition) // Dapatkan objek Employee yang akan dihapus
                deleteEmployee(employee)
            }
        }
        private fun deleteEmployee(employee: Employee) {
            val employeeRef = getRef(adapterPosition)
            employeeRef.removeValue()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(itemView.context, "Data employee berhasil dihapus", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(itemView.context, "Gagal menghapus data employee", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

}