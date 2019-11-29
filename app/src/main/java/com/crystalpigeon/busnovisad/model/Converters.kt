package com.crystalpigeon.busnovisad.model

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.json.JSONObject
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromLinkedHashMap(hashMap: LinkedHashMap<String, ArrayList<String>>?): String {
        val moshi: Moshi = Moshi.Builder().build()
        val type: Type = Types.newParameterizedType(Map::class.java, String::class.java, List::class.java)
        val adapter = moshi.adapter<LinkedHashMap<String, ArrayList<String>>>(type)
        return adapter.toJson(hashMap).toString()
    }

    @TypeConverter
    fun fromString(value: String?): LinkedHashMap<String, ArrayList<String>>? {
        val map = LinkedHashMap<String, ArrayList<String>>()
        val jsonObj = JSONObject(value)
        val keys = jsonObj.keys()
        while(keys.hasNext()){
            val key = keys.next()
            val jsonArray = jsonObj.getJSONArray(key)
            val minutes = ArrayList<String>()
            for( i in 0 until jsonArray.length()){
                minutes.add(jsonArray.getString(i))
            }
            map[key] = minutes
        }
        return map
    }
}