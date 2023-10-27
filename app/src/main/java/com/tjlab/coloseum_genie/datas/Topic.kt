package com.tjlab.coloseum_genie.datas

import org.json.JSONObject
import java.io.Serializable

class Topic : Serializable {

    var id = 0
    var title = ""
    var imageURL = ""

    val sides = ArrayList<Side>()

    companion object {

        fun getTopicDataFromJson(jsonObj : JSONObject) : Topic {
            val resultTopic = Topic()
            resultTopic.id =jsonObj.getInt("id")
            resultTopic.title = jsonObj.getString("title")
            resultTopic.imageURL = jsonObj.getString("img_url")
            val sidesArr = jsonObj.getJSONArray("sides")
            for (i in 0 until sidesArr.length()) {
                val sideObj = sidesArr.getJSONObject(i)
                val side = Side.getSideFromJson(sideObj)
                resultTopic.sides.add(side)
            }
            return resultTopic
        }
    }
}