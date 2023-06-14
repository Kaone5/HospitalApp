package com.mycompany.hospitalapp2.ui.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.hospitalapp2.data.model.Doctor
import com.mycompany.hospitalapp2.databinding.ItemDoctorNameLayoutBinding

class DoctorNameAdapter(
    val onItemClicked: (Int, Doctor) -> Unit
): RecyclerView.Adapter<DoctorNameAdapter.MyViewHolder>(){
    private var list: MutableList<Doctor> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorNameAdapter.MyViewHolder {
        val itemView = ItemDoctorNameLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: DoctorNameAdapter.MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<Doctor>){
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
    inner class MyViewHolder(val binding: ItemDoctorNameLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Doctor){
            binding.docName.setText(item.name)
            binding.docCabinet.setText((item.cabinet))
            binding.itemDoctorNameLayout.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }
}

