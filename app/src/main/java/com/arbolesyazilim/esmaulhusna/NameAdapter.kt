package com.arbolesyazilim.esmaulhusna


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NameAdapter(private val nameList: List<Name>, private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<NameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = nameList[position]
        holder.bind(name)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(name)
        }
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    interface ItemClickListener {
        fun onItemClick(name: Name)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        fun bind(name: Name) {
            nameTextView.text = name.name
        }
    }
}
