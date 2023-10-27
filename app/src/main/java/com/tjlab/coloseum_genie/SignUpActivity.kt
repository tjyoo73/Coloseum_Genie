package com.tjlab.coloseum_genie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tjlab.coloseum_genie.databinding.ActivitySignUpBinding
import com.tjlab.coloseum_genie.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    private lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.signUpBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            val inputPw = binding.passwordEdt.text.toString()
            val inputNickname = binding.nicknameEdt.text.toString()
            //Log.d("CHECK > inputEmail", inputEmail)
            //Log.d("CHECK > inputEmail", inputPw)
            //Log.d("CHECK > inputEmail", inputNickname)답

            ServerUtil.putRequestSignUp(inputEmail, inputPw, inputNickname, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {
                    val code = jsonObj.getInt("code")
                    if (code == 200) {
                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val nickName = userObj.getString("nick_name")
                        runOnUiThread {
                            Toast.makeText(mContext, "${nickName}님, 환영합니다 !!", Toast.LENGTH_SHORT).show()
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

        binding.emailCheckBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            ServerUtil.getRequestDuplCheck("EMAIL", inputEmail, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {
                    val code = jsonObj.getInt("code")
                    if (code == 200) {
                        Toast.makeText(mContext, "사용해도 좋습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        val message = jsonObj.getString("message")
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }

                }
            })
        }
    }

    override fun setValues() {

    }

}