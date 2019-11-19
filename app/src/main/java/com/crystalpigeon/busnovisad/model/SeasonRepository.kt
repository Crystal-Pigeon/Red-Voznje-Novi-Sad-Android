package com.crystalpigeon.busnovisad.model

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const
import kotlinx.coroutines.*
import javax.inject.Inject

class SeasonRepository {
    @Inject
    lateinit var api: Service
    @Inject
    lateinit var sharedPrefs: SharedPreferences
    @Inject
    lateinit var prefsEdit: SharedPreferences.Editor
    private lateinit var season:  List<SeasonResponse>
    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + parentJob)

    init {
        BusNsApp.app.component.inject(this)
    }

    fun shouldUpdate(): LiveData<Boolean>{
        val isSeason = MutableLiveData<Boolean>()
        val oldValue = sharedPrefs.getString(Const.DATE, "")

        coroutineScope.launch(Dispatchers.IO) {
            season = api.getSeason()
            if (oldValue == null || oldValue != season[0].date) {
                isSeason.value = true
                prefsEdit.putString(Const.DATE, season[0].date)
            } else isSeason.value = false
        }
        return isSeason
    }
}