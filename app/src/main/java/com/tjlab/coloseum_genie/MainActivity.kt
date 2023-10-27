package com.tjlab.coloseum_genie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tjlab.coloseum_genie.adapers.TopicAdapter
import com.tjlab.coloseum_genie.databinding.ActivityMainBinding
import com.tjlab.coloseum_genie.datas.Topic
import com.tjlab.coloseum_genie.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    private lateinit var binding : ActivityMainBinding
    val mTopicList = ArrayList<Topic>()
    lateinit var mTopicAdapter : TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.topicListView.setOnItemClickListener { parent, view, position, id ->
            val clickedTopic = mTopicList[position]
            val myIntent = Intent(mContext, ViewTopicDetailActivity::class.java)
            myIntent.putExtra("topic", clickedTopic)
            startActivity(myIntent)

        }

    }

    override fun setValues() {
        getTopicListFromServer()
        mTopicAdapter = TopicAdapter(mContext, R.layout.toipc_list_item, mTopicList)
        binding.topicListView.adapter = mTopicAdapter

    }

    fun getTopicListFromServer() {
        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.Companion.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {
                var dataObj = jsonObj.getJSONObject("data")
                var topicsArr = dataObj.getJSONArray("topics")

                for (index in 0 until topicsArr.length()) {
                    val topicObj = topicsArr.getJSONObject(index)
                    val topicData = Topic.getTopicDataFromJson(topicObj)
                    mTopicList.add(topicData)
                }

                runOnUiThread {
                    mTopicAdapter.notifyDataSetChanged()
                }
            }

        })

    }
}