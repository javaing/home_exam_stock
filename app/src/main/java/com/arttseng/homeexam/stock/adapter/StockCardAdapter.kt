package com.arttseng.homeexam.stock.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arttseng.homeexam.stock.R
import com.arttseng.homeexam.stock.datamodel.Stock_Day_AVG_AllItem
import com.arttseng.homeexam.stock.datamodel.Stock_Day_AllItem
import com.arttseng.homeexam.stock.tools.format
import com.arttseng.homeexam.stock.tools.removeDecimalZero
import com.arttseng.homeexam.stock.tools.toWan

class StockCardAdapter(onClick: View.OnClickListener) : RecyclerView.Adapter<StockCardAdapter.mViewHolder>() {

    var unAssignList = arrayListOf<Stock_Day_AllItem>()
    var avgDataList = arrayListOf<Stock_Day_AVG_AllItem>()
    private val myClick = onClick
    var isReverse = false;

    inner class mViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val tv_stock_name: TextView = itemView.findViewById(R.id.tv_stock_name)
        val tv_stock_code: TextView = itemView.findViewById(R.id.tv_stock_code)
        val tv_stock_open: TextView = itemView.findViewById(R.id.tv_stock_open)
        val tv_stock_close: TextView = itemView.findViewById(R.id.tv_stock_close)
        val tv_stock_high: TextView = itemView.findViewById(R.id.tv_stock_high)
        val tv_stock_low: TextView = itemView.findViewById(R.id.tv_stock_low)
        val tv_stock_avg: TextView = itemView.findViewById(R.id.tv_stock_avg)
        val tv_stock_change: TextView = itemView.findViewById(R.id.tv_stock_change)
        val tv_transaction: TextView = itemView.findViewById(R.id.tv_transaction)
        val tv_tradeValue: TextView = itemView.findViewById(R.id.tv_tradeValue)
        val tv_tradeVolume: TextView = itemView.findViewById(R.id.tv_tradeVolume)

        fun bind(item: Stock_Day_AllItem){
            tv_stock_name.text = item.Name
            tv_stock_code.text = item.Code
            tv_stock_open.text = item.OpeningPrice
            tv_stock_close.text = item.ClosingPrice
            tv_stock_high.text = item.HighestPrice
            tv_stock_low.text = item.LowestPrice
            tv_stock_avg.text = getMonthAvg(item.Code, avgDataList)
            tv_stock_change.text = item.Change.removeDecimalZero()
            tv_transaction.text = item.Transaction.format()
            tv_tradeValue.text = item.TradeValue.toWan()
            tv_tradeVolume.text = item.TradeVolume.format()

            tv_stock_close.setTextColor(getAdjustCloseColor(item.ClosingPrice, tv_stock_avg.text.toString()))
            tv_stock_change.setTextColor(getAdjustChangeColor(tv_stock_change.text.toString()))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):mViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val example = inflater.inflate(R.layout.item_stock, parent, false)
        return mViewHolder(example)

    }

    override fun getItemCount() = unAssignList.size

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {

        holder.bind(unAssignList[position])
        holder.itemView.setTag(unAssignList[position].Code)
        holder.itemView.setOnClickListener(myClick)
    }

    fun updateList(list:ArrayList<Stock_Day_AllItem>){
        unAssignList = list
        notifyDataSetChanged()
    }

    fun updateMonthAvg(avgList: ArrayList<Stock_Day_AVG_AllItem>){
        avgDataList = avgList
        notifyDataSetChanged()
    }

    fun getMonthAvg(code: String, avgList: ArrayList<Stock_Day_AVG_AllItem>):String {
        if(avgList.isEmpty()) return ""
        val find = avgList.filter { it.Code == code }
        if(find.isEmpty()) return ""
        return find.first().MonthlyAveragePrice
    }

    fun getAdjustCloseColor(close: String, avg: String):Int {
        if(close.isEmpty() || avg.isEmpty() || close.equals(avg)) return Color.BLACK
        return if( close.toDouble()> avg.toDouble()) Color.RED else Color.GREEN
    }

    fun getAdjustChangeColor(change: String):Int {
        if(change.isEmpty()) return Color.BLACK
        if(change.equals("0.0")) return Color.BLACK
        return if( change.toDouble()> 0) Color.RED else Color.GREEN
    }

    fun reverseData(descend: Boolean) {
        if(descend)
            unAssignList.sortByDescending { list -> list.Code }
        else
            unAssignList.sortBy { list -> list.Code }
        notifyDataSetChanged()
    }
}