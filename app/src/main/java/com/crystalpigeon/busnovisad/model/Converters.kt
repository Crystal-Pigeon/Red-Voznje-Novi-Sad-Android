package com.crystalpigeon.busnovisad.model

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromHashMap(hashMap: HashMap<String, ArrayList<String>>): String {
        val moshi: Moshi = Moshi.Builder().build()
        val type: Type = Types.newParameterizedType(Map::class.java, String::class.java)
        val adapter = moshi.adapter<HashMap<String, ArrayList<String>>>(type)
        return adapter.toJson(hashMap)
    }

    @TypeConverter
    fun fromString(value: String): HashMap<String, ArrayList<String>>? {
        val moshi: Moshi = Moshi.Builder().build()
        val type: Type = Types.newParameterizedType(String::class.java, Map::class.java)
        val adapter = moshi.adapter<HashMap<String,ArrayList<String>>>(type)
        return adapter.fromJson(value)
    }
}