package com.example.myflickr.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myflickr.Item
import com.example.myflickr.recycler.ItemHolder

class ItemAdapter(private var list: MutableList<Item>) : RecyclerView.Adapter<ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item: Item = list[position]
        holder.bind(item)
    }

    fun replaceList(l: MutableList<Item>) {
        list = l
        notifyDataSetChanged()
    }

    fun addItem(i:Item){
        list.add(i)
        notifyItemChanged(itemCount-1)
    }

    fun getList(): MutableList<Item> {
        return list
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = list.size

}