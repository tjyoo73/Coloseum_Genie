package com.tjlab.coloseum_genie.adapers

import android.content.Context
import android.graphics.Color
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
import com.tjlab.coloseum_genie.utils.ServerUtil
import org.json.JSONObject

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
        val likeCountBtn = row.findViewById<TextView>(R.id.likeCountBtn)
        val dislikeCountBtn = row.findViewById<TextView>(R.id.dislikeCountBtn)

        contentTxt.text = data.content
        selectedSideTxt.text = "(${data.selectedSide.title})"
        userNicknameTxt.text = data.writerNickname
        likeCountBtn.text = "좋아요 ${data.likeCount}개"
        dislikeCountBtn.text = "싫어요 ${data.dislikeCount}개"

        if (data.myLike) {
            likeCountBtn.setBackgroundResource(R.drawable.red_border_box)
            likeCountBtn.setTextColor(Color.parseColor("#FF0000"))
            dislikeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeCountBtn.setTextColor(Color.parseColor("#A0A0A0"))
        } else if (data.myDislike) {
            likeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
            likeCountBtn.setTextColor(Color.parseColor("#A0A0A0"))
            dislikeCountBtn.setBackgroundResource(R.drawable.blue_border_box)
            dislikeCountBtn.setTextColor(Color.parseColor("#0000FF"))
        } else {
            likeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
            likeCountBtn.setTextColor(Color.parseColor("#A0A0A0"))
            dislikeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeCountBtn.setTextColor(Color.parseColor("#A0A0A0"))
        }

        likeCountBtn.setOnClickListener {
            ServerUtil.postRequestLikeOrDislike(mContext, data.id, true, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                }
            })
        }

        dislikeCountBtn.setOnClickListener {
            ServerUtil.postRequestLikeOrDislike(mContext, data.id, false, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                }
            })
        }

        return row
    }
}