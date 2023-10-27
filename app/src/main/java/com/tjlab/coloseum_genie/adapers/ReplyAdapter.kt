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
import com.tjlab.coloseum_genie.datas.Reply
import com.tjlab.coloseum_genie.datas.Topic

class ReplyAdapter(val mContext : Context,
                   resId : Int,
                   val mList : List<Reply>) : ArrayAdapter<Reply>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //return super.getView(position, convertView, parent)
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = mInflater.inflate(R.layout.reply_list_item, null)
        }
        val row = tempRow!!
        val data = mList[position]

        val selectedSideTxt = row.findViewById<TextView>(R.id.selectedSideTxt)
        val userNicknameTxt = row.findViewById<TextView>(R.id.userNicknameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        contentTxt.text = data.content
        selectedSideTxt.text = "(${data.selectedSide.title})"
        userNicknameTxt.text = data.writerNickname

        return row
    }
}