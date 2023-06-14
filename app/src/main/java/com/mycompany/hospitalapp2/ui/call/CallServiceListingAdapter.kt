package com.mycompany.hospitalapp2.ui.call

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.hospitalapp2.data.model.CallService
import com.mycompany.hospitalapp2.databinding.ItemCallServiceLayoutBinding

class CallServiceListingAdapter(
    val onItemClicked: (Int, CallService) -> Unit
): RecyclerView.Adapter<CallServiceListingAdapter.MyViewHolder>() {
    private var list: MutableList<CallService> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallServiceListingAdapter.MyViewHolder {
        val itemView = ItemCallServiceLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: CallServiceListingAdapter.MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<CallService>){
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

    inner class MyViewHolder(val binding: ItemCallServiceLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CallService){
            binding.docRole.setText(item.name)
            binding.itemCallServiceLayout.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }
}