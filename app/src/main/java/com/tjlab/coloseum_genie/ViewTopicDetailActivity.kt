package com.tjlab.coloseum_genie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.tjlab.coloseum_genie.adapers.ReplyAdapter
import com.tjlab.coloseum_genie.databinding.ActivityViewTopicDetailBinding
import com.tjlab.coloseum_genie.datas.Reply
import com.tjlab.coloseum_genie.datas.Side
import com.tjlab.coloseum_genie.datas.Topic
import com.tjlab.coloseum_genie.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    private lateinit var binding : ActivityViewTopicDetailBinding
    lateinit var mTopic : Topic

    val mReplyList = ArrayList<Reply>()
    lateinit var mReplyAdapter : ReplyAdapter

    var mySelectedSide : Side? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewTopicDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        getTopicDetailFromServer()
    }

    override fun setupEvents() {
        binding.voteToFirstSideBtn.setOnClickListener {
            ServerUtil.postRequestVote(mContext, mTopic.sides[0].id, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {
                    getTopicDetailFromServer()
                }
            })
        }

        binding.voteToSecondSideBtn.setOnClickListener {
            ServerUtil.postRequestVote(mContext, mTopic.sides[1].id, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {
                    getTopicDetailFromServer()
                }
            })
        }

        binding.writeReply.setOnClickListener {
            if (mySelectedSide == null) {
                Toast.makeText(mContext, "투표를 한 이후에만 의견을 등록할수 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val myIntent = Intent(mContext, EditReplyActivity::class.java)
                myIntent.putExtra("mySide", mySelectedSide)
                startActivity(myIntent)
            }

        }

    }

    override fun setValues() {
        mTopic = intent.getSerializableExtra("topic") as Topic
        binding.topicTitleTxt.text = mTopic.title
        Glide.with(mContext).load(mTopic.imageURL).into(binding.topicImg)

        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        binding.replyListView.adapter = mReplyAdapter

        getTopicDetailFromServer()

    }

    fun getTopicDetailFromServer() {
        ServerUtil.getRequestTopicDetail(mContext, mTopic.id, object : ServerUtil.Companion.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {
                val dataObj = jsonObj.getJSONObject("data")
                val topicObj = dataObj.getJSONObject("topic")
                val topic = Topic.getTopicDataFromJson(topicObj)
                mTopic = topic

                val replyArr = topicObj.getJSONArray("replies")

                mReplyList.clear()
                for (i in 0 until replyArr.length()) {
                    val replyObj = replyArr.getJSONObject(i)
                    val reply = Reply.getReplyFromJson(replyObj)
                    mReplyList.add(reply)
                }

                mySelectedSide = null
                if (topicObj.isNull("my_side")) {
                    val mySideObj = topicObj.getJSONObject("my_side")
                    val mySide = Side.getSideFromJson(mySideObj)
                    mySelectedSide = mySide
                }

                runOnUiThread {
                    binding.firstSideTxt.text = mTopic.sides[0].title
                    binding.firstSideVoteCountTxt.text = "${mTopic.sides[0].voteCount}표"
                    binding.secondSideTxt.text = mTopic.sides[1].title
                    binding.secondSideVoteCountTxt.text = "${mTopic.sides[1].voteCount}표"

                    mReplyAdapter.notifyDataSetChanged()
                }
            }
        })

    }
}