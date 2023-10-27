package com.tjlab.coloseum_genie

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.tjlab.coloseum_genie.databinding.ActivityLoginBinding
import com.tjlab.coloseum_genie.utils.ContextUtil
import com.tjlab.coloseum_genie.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.loginBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            val inputPw = binding.passwordEdt.text.toString()
            //Log.d("CHECK > inputEmail", inputEmail)

            ServerUtil.postRequestLogin(inputEmail, inputPw, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {
                    val code = jsonObj.getInt("code")
                    if (code == 200) {
                        val dataObj = jsonObj.getJSONObject("data")
                        val token = dataObj.getString("token")
                        ContextUtil.setToken(mContext, token)

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
                        finish()
                        
                    } else {
                        val message = jsonObj.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

        binding.signUpBtn.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }



    }

    override fun setValues() {

    }


}