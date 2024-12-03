package com.arttseng.homeexam.airplane.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arttseng.homeexam.airplane.R
import com.arttseng.homeexam.airplane.datamodel.Airport
import com.arttseng.homeexam.airplane.datamodel.ArrivalItem
import com.arttseng.homeexam.airplane.tools.Utils
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ArrivalAdapter(onClick: View.OnClickListener) : RecyclerView.Adapter<ArrivalAdapter.mViewHolder>() {

    private var unAssignList = arrayListOf<ArrivalItem>()
    val myClick = onClick
    var airData = emptyList<Airport>()

    inner class mViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tv_departure_time: TextView = itemView.findViewById(R.id.tv_departure_time)
        val tv_departure_aircode: TextView = itemView.findViewById(R.id.tv_departure_aircode)
        val tv_departure_gate: TextView = itemView.findViewById(R.id.tv_departure_gate)
        val tv_departure_status: TextView = itemView.findViewById(R.id.tv_departure_status)
        val tv_departure_from: TextView = itemView.findViewById(R.id.tv_departure_from)
        val tv_departure_destination: TextView = itemView.findViewById(R.id.tv_departure_destination)

        fun bind(item: ArrivalItem){
            //tv_departure_time.text = "預定起飛：" + item.ScheduleDepartureTime
            val format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

            val date = LocalTime.parse(item.ActualArrivalTime, format)

            tv_departure_time.text =   "實際抵達：" + date
            tv_departure_aircode.text = "航班編號：" + item.AirlineID + item.FlightNumber
            tv_departure_gate.text =  "航廈：T" + item.Terminal + " / 登機門：" + item.Gate
            tv_departure_status.text = item.ArrivalRemark
            tv_departure_from.text = item.DepartureAirportID + "\n" + Utils.getAirportName( airData, item.DepartureAirportID)
            tv_departure_destination.text = item.ArrivalAirportID + "\n" + Utils.getAirportName( airData, item.ArrivalAirportID)
            tv_departure_from.setTextColor(itemView.resources.getColor(android.R.color.holo_blue_light, null))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):mViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val example = inflater.inflate(R.layout.item_departure, parent, false)
        return mViewHolder(example)

    }

    override fun getItemCount() = unAssignList.size

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.bind(unAssignList[position])

        holder.itemView.setTag(position)
        holder.itemView.setOnClickListener(myClick)
    }

    //更新資料用
    fun updateList(list:ArrayList<ArrivalItem>,  airportData: List<Airport>){
        airData = airportData
        unAssignList = list
        notifyDataSetChanged()
    }
}