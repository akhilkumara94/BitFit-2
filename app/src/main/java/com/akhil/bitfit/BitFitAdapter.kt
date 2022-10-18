package com.akhil.bitfit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BitFitAdapter(private val context: Context, private val bitfits:List<BitFit>) : RecyclerView.Adapter<BitFitAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bitfit, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bitfit = bitfits[position]
        holder.bind(bitfit)
    }

    override fun getItemCount(): Int {
        return bitfits.size
    }

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val foodTV : TextView = itemView.findViewById(R.id.foodNameTV)
        private val calorieCount : TextView = itemView.findViewById(R.id.calorieCountTV)
        init
        {
            itemView.setOnClickListener(this)
        }

        fun bind(bitfit: BitFit)
        {
            foodTV.text = bitfit.foodName
            calorieCount.text = bitfit.calorieCount
        }

        override fun onClick(p0: View?) {
            // No operation
        }
    }
}