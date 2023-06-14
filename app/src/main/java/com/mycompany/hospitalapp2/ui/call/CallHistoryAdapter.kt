package com.mycompany.hospitalapp2.ui.call

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.hospitalapp2.data.model.Call
import com.mycompany.hospitalapp2.databinding.ItemCallLayoutBinding

class CallHistoryAdapter(
    val onItemClicked: (Int, Call) -> Unit
): RecyclerView.Adapter<CallHistoryAdapter.MyViewHolder>(){
    private var list: MutableList<Call> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallHistoryAdapter.MyViewHolder {
        val itemView = ItemCallLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: CallHistoryAdapter.MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<Call>){
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

    inner class MyViewHolder(val binding: ItemCallLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Call){
            binding.callPatName.setText(item.patient)
            binding.callServiceText.setText(item.service)
            binding.appointmentCabinet.setText(item.address)
            binding.appointmentDate.setText(item.date)
            binding.itemCallLayout.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }
}