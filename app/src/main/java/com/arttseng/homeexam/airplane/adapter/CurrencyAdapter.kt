package com.arttseng.homeexam.airplane.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arttseng.homeexam.airplane.R
import com.arttseng.homeexam.airplane.datamodel.CurrencyItem
import com.arttseng.homeexam.airplane.tools.round

class CurrencyAdapter(onClick: View.OnClickListener) : RecyclerView.Adapter<CurrencyAdapter.mViewHolder>() {

    var unAssignList = arrayListOf<CurrencyItem>()
    private val myClick = onClick

    inner class mViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        //把layout檔的元件們拉進來，指派給當地變數
        //val icon = itemView.img_news_detail
        val tv_currency_name: TextView = itemView.findViewById(R.id.tv_currency_name)
        val tv_currency_rate: TextView = itemView.findViewById(R.id.tv_currency_rate)

        fun bind(item: CurrencyItem){
            tv_currency_name.text = item.name
            tv_currency_rate.text = item.rate.round(3).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):mViewHolder {

        //載入項目模板
        val inflater = LayoutInflater.from(parent.context)
        val example = inflater.inflate(R.layout.item_currency, parent, false)
        return mViewHolder(example)

    }

    override fun getItemCount() = unAssignList.size

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {

        //呼叫上面的bind方法來綁定資料
        holder.bind(unAssignList[position])
        holder.itemView.setTag(position)
        holder.itemView.setOnClickListener(myClick)
    }

    //更新資料用
    fun updateList(list:ArrayList<CurrencyItem>){
        unAssignList = list
        notifyDataSetChanged()
    }
}