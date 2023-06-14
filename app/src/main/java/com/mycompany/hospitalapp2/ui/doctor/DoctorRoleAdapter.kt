package com.mycompany.hospitalapp2.ui.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.hospitalapp2.data.model.Doctor
import com.mycompany.hospitalapp2.data.model.Service
import com.mycompany.hospitalapp2.databinding.ItemDoctorNameLayoutBinding
import com.mycompany.hospitalapp2.databinding.ItemDoctorRoleLayoutBinding

class DoctorRoleAdapter(
    val onItemClicked: (Int, Service) -> Unit
): RecyclerView.Adapter<DoctorRoleAdapter.MyViewHolder>(){
    private var list: MutableList<Service> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorRoleAdapter.MyViewHolder {
        val itemView = ItemDoctorRoleLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: DoctorRoleAdapter.MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<Service>){
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

    inner class MyViewHolder(val binding: ItemDoctorRoleLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Service){
            binding.docRole.setText(item.name)
            binding.itemDoctorRoleLayout.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }
}