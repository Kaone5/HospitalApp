package com.mycompany.hospitalapp2.ui.appointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.hospitalapp2.data.model.Appointment
import com.mycompany.hospitalapp2.databinding.ItemAppointmentLayoutBinding

class AppointmentHistoryAdapter(
    val onItemClicked: (Int, Appointment) -> Unit
): RecyclerView.Adapter<AppointmentHistoryAdapter.MyViewHolder>(){
    private var list: MutableList<Appointment> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHistoryAdapter.MyViewHolder {
        val itemView = ItemAppointmentLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: AppointmentHistoryAdapter.MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<Appointment>){
        this.list = list
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        list.removeAt(position)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemAppointmentLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Appointment){
            binding.appointmentPatName.setText(item.patient)
            binding.appointmentDoctorRole.setText(item.service)
            binding.appointmentDoctorName.setText(item.doctor)
            binding.appointmentCabinet.setText(item.cabinet)
            binding.appointmentDate.setText(item.date)
            binding.itemAppointmentLayout.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }
}