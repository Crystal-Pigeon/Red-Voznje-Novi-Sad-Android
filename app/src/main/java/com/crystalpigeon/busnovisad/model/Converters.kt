package com.crystalpigeon.busnovisad.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {
    @TypeConverter
    fun fromLinkedHashMap(hashMap: SortedMap<String, ArrayList<String>>?): String {
        return Gson().toJson(hashMap)
    }

    @TypeConverter
    fun fromString(value: String?): SortedMap<String, ArrayList<String>>? {
        return Gson().fromJson(value,
            object : TypeToken<SortedMap<String, ArrayList<String>>>() {}.type
        )
    }
}