package com.tjlab.coloseum_genie.adapers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tjlab.coloseum_genie.R
import com.tjlab.coloseum_genie.datas.Topic

class TopicAdapter(val mContext : Context,
                   resId : Int,
                   val mList : List<Topic>) : ArrayAdapter<Topic>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //return super.getView(position, convertView, parent)
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = mInflater.inflate(R.layout.toipc_list_item, null)
        }
        val row = tempRow!!
        val data = mList[position]

        val topicImg = row.findViewById<ImageView>(R.id.topicImg)
        val topicTitleTxt = row.findViewById<TextView>(R.id.topicTitleTxt)

        Glide.with(mContext)
            .load(data.imageURL)
            .into(topicImg)
        topicTitleTxt.text = data.title

        return row
    }
}