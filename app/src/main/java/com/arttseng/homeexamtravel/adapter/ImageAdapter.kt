package com.arttseng.homeexamtravel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.arttseng.homeexamtravel.R
import com.arttseng.homeexamtravel.datamodel.Image
import com.arttseng.homeexamtravel.tools.Utils
import com.arttseng.homeexamtravel.tools.load

class ImageAdapter(data: List<Image>) : RecyclerView.Adapter<ImageAdapter.mViewHolder>() {

    var unAssignList = data

    inner class mViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //把layout檔的元件們拉進來，指派給當地變數
        val iv_images: ImageView = itemView.findViewById(R.id.iv_images)

        fun bind(item: Image){
            Utils.log("image load:" + item.src)
            if(item.src.isNotEmpty()) {
                iv_images.load(item.src)
            } else {
                iv_images.setImageResource(R.drawable.ic_landscape)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):mViewHolder {
        //載入項目模板
        val inflater = LayoutInflater.from(parent.context)
        val example = inflater.inflate(R.layout.item_image_slide, parent, false)
        return mViewHolder(example)

    }

    override fun getItemCount() = unAssignList.size

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        //呼叫上面的bind方法來綁定資料
        holder.bind(unAssignList[position])

        holder.itemView.setTag(position)
    }

    //更新資料用
    fun updateList(list:ArrayList<Image>){
        unAssignList = list
        notifyDataSetChanged()
    }
}