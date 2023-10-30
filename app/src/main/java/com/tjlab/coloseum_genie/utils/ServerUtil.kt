package com.tjlab.coloseum_genie.utils

import android.content.Context
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    companion object {

        interface JsonResponseHandler {
            fun onResponse(jsonObj : JSONObject)
        }

        //val BASE_URL = "http://54.100.52.26"
        val BASE_URL = "http://3.37.226.131"

        fun postRequestLogin(email : String, pw : String, handler : JsonResponseHandler?) {
            val urlString = "${BASE_URL}/user"

            //Log.d("CHECK > email", email)
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("응답본문", "postRequestLogin onFailure")
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("응답본문", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }
            })
        }

        fun putRequestSignUp(email : String, pw : String, nick : String, handler : JsonResponseHandler?) {
            val urlString = "${BASE_URL}/user"

            //Log.d("CHECK > email", email)
            //Log.d("CHECK > password", pw)
            //Log.d("CHECK > nick_name", nick)
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nick)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .put(formData)
                .build()


            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("응답본문", "putRequestSignUp onFailure")
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("응답본문", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }
            })
        }

        fun getRequestDuplCheck(type : String, value : String, handler : JsonResponseHandler?) {
            val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedQueryParameter("type", type)
            urlBuilder.addEncodedQueryParameter("value", value)
            val urlString = urlBuilder.build().toString()
            Log.d("완성된 URL", urlString)
            val request = Request.Builder()
                .url(urlString)
                .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답본문", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })
        }

        fun getRequestMainInfo(context : Context, handler : JsonResponseHandler?) {
            val urlBuilder = "${BASE_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()
//            urlBuilder.addEncodedQueryParameter("type", type)
//            urlBuilder.addEncodedQueryParameter("value", value)
            val urlString = urlBuilder.build().toString()
            Log.d("완성된 URL", urlString)
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답본문", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })
        }

        fun getRequestTopicDetail(context : Context, topicId : Int, handler : JsonResponseHandler?) {
            val urlBuilder = "${BASE_URL}/topic".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedPathSegment(topicId.toString())
            urlBuilder.addEncodedQueryParameter("order_type", "NEW")
//            urlBuilder.addEncodedQueryParameter("value", value)
            val urlString = urlBuilder.build() .toString()
            Log.d("완성된 URL", urlString)
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답본문", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })
        }

        fun postRequestVote(context : Context, sideId : Int, handler : JsonResponseHandler?) {
            val urlString = "${BASE_URL}/topic_vote"

            //Log.d("CHECK > email", email)
            val formData = FormBody.Builder()
                .add("side_id", sideId.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http_Toke", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("응답본문", "postRequestLogin onFailure")
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("응답본문", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }
            })
        }

        fun postRequestReply(context : Context, topicId : Int, content : String, handler : JsonResponseHandler?) {
            val urlString = "${BASE_URL}/topic_reply"

            //Log.d("CHECK > email", email)
            val formData = FormBody.Builder()
                .add("topic_id", topicId.toString())
                .add("content", content.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http_Toke", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("응답본문", "postRequestLogin onFailure")
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("응답본문", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }
            })
        }

        fun postRequestLikeOrDislike(context : Context, replyId : Int, isLike : Boolean, handler : JsonResponseHandler?) {
            val urlString = "${BASE_URL}/topic_reply_like"

            //Log.d("CHECK > email", email)
            val formData = FormBody.Builder()
                .add("reply_id", replyId.toString())
                .add("is_like", isLike.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http_Toke", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("응답본문", "postRequestLogin onFailure")
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("응답본문", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }
            })
        }


    }

}