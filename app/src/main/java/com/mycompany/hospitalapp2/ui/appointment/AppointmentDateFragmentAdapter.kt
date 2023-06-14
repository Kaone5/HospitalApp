package com.mycompany.hospitalapp2.ui.appointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.hospitalapp2.data.model.TimeAppointment
import com.mycompany.hospitalapp2.databinding.ItemDoctorTimeLayoutBinding


class AppointmentDateFragmentAdapter (
    val onItemClicked: (Int, TimeAppointment) -> Unit
): RecyclerView.Adapter<AppointmentDateFragmentAdapter.MyViewHolder>(){
    private var list: MutableList<TimeAppointment> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentDateFragmentAdapter.MyViewHolder {
        val itemView = ItemDoctorTimeLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: AppointmentDateFragmentAdapter.MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<TimeAppointment>){
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
    inner class MyViewHolder(val binding: ItemDoctorTimeLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TimeAppointment){
            binding.docTime.setText(item.one)
            binding.itemDoctorTimeLayout.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }

}