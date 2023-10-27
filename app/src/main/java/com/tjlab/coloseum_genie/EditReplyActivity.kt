package com.tjlab.coloseum_genie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tjlab.coloseum_genie.databinding.ActivityEditReplyBinding
import com.tjlab.coloseum_genie.datas.Side
import com.tjlab.coloseum_genie.utils.ServerUtil
import org.json.JSONObject

class EditReplyActivity : BaseActivity() {

    private lateinit var binding : ActivityEditReplyBinding
    lateinit var mSelectedSide : Side

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditReplyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.okBtn.setOnClickListener {
            val inputContent = binding.contentEdt.text.toString()
            ServerUtil.postRequestReply(mContext, mSelectedSide.topicId, inputContent, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {
                    val code = jsonObj.getInt("code")
                    if (code == 200) {
                        runOnUiThread {
                            Toast.makeText(mContext, "의견 등록에 성공했습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                    } else {
                        val message = jsonObj.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }

    override fun setValues() {
        mSelectedSide = intent.getSerializableExtra("myside") as Side
        binding.mySelectedSideTxt.text = mSelectedSide.title
    }
}