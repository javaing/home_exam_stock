package com.arttseng.homeexamtravel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arttseng.homeexamtravel.R
import com.arttseng.homeexamtravel.datamodel.Attraction
import com.arttseng.homeexamtravel.tools.roundImage

class AttractAdapter(onClick: View.OnClickListener) : RecyclerView.Adapter<AttractAdapter.mViewHolder>() {

    var unAssignList = arrayListOf<Attraction>()
    val myClick = onClick

    inner class mViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        //把layout檔的元件們拉進來，指派給當地變數
        //val icon = itemView.img_news_detail
        val tv_news_title: TextView = itemView.findViewById(R.id.tv_attract_title)
        val img_attract: ImageView = itemView.findViewById(R.id.img_attract)
        val tv_news_desc: TextView = itemView.findViewById(R.id.tv_attract_desc)

        fun bind(item: Attraction){
            tv_news_title.text = item.name
            if(item.images.isNotEmpty()) {
                img_attract.roundImage(item.images.first().src)
                img_attract.visibility = View.VISIBLE
            } else {
                img_attract.visibility = View.GONE
            }

            tv_news_desc.text = item.introduction
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):mViewHolder {

        //載入項目模板
        val inflater = LayoutInflater.from(parent.context)
        val example = inflater.inflate(R.layout.item_attract_detail, parent, false)
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
    fun updateList(list:ArrayList<Attraction>){
        unAssignList = list
        notifyDataSetChanged()
    }
}