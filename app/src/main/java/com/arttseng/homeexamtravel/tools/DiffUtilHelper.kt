package com.arttseng.homeexamtravel.tools

import androidx.recyclerview.widget.DiffUtil
import com.arttseng.homeexamtravel.datamodel.Attraction

class DiffUtilHelper(private val mOldData: List<Attraction>?, private val mNewData: List<Attraction>?) : DiffUtil.Callback() {
    //老数据集size
    override fun getOldListSize(): Int {
        return mOldData?.size ?: 0
    }

    //新数据集size
    override fun getNewListSize(): Int {
        return mNewData?.size ?: 0
    }

    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
        return mOldData!![oldPos].id == mNewData!![newPos].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, login) = mOldData!![oldItemPosition]
        val (_, login1) = mNewData!![newItemPosition]
        return login == login1
        //默认两个data内容是相同的
    }
}