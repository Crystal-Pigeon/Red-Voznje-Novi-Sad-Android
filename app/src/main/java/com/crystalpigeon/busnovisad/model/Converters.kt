package com.crystalpigeon.busnovisad.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromLinkedHashMap(hashMap: LinkedHashMap<String, ArrayList<String>>?): String {
        return Gson().toJson(hashMap)
    }

    @TypeConverter
    fun fromString(value: String?): LinkedHashMap<String, ArrayList<String>>? {
        return Gson().fromJson(value,
            object : TypeToken<java.util.LinkedHashMap<String, ArrayList<String>>>() {}.type
        )
    }
}