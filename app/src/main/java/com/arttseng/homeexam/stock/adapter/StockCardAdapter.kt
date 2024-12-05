package com.arttseng.homeexam.stock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arttseng.homeexam.stock.R
import com.arttseng.homeexam.stock.datamodel.Stock_Day_AVG_AllItem
import com.arttseng.homeexam.stock.datamodel.Stock_Day_AllItem

class StockCardAdapter(onClick: View.OnClickListener) : RecyclerView.Adapter<StockCardAdapter.mViewHolder>() {

    var unAssignList = arrayListOf<Stock_Day_AllItem>()
    var avgDataList = arrayListOf<Stock_Day_AVG_AllItem>()
    private val myClick = onClick

    inner class mViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val tv_currency_name: TextView = itemView.findViewById(R.id.tv_currency_name)
        val tv_currency_rate: TextView = itemView.findViewById(R.id.tv_currency_rate)

        fun bind(item: Stock_Day_AllItem){
            tv_currency_name.text = item.Name
            tv_currency_rate.text = item.Code
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):mViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val example = inflater.inflate(R.layout.item_currency, parent, false)
        return mViewHolder(example)

    }

    override fun getItemCount() = unAssignList.size

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {

        holder.bind(unAssignList[position])
        holder.itemView.setTag(position)
        holder.itemView.setOnClickListener(myClick)
    }

    fun updateList(list:ArrayList<Stock_Day_AllItem>, avgList: ArrayList<Stock_Day_AVG_AllItem>){
        unAssignList = list
        avgDataList = avgList
        notifyDataSetChanged()
    }
}