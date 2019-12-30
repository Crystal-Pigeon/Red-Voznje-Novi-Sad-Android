package com.crystalpigeon.busnovisad.viewmodel

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.model.entity.Schedule
import com.crystalpigeon.busnovisad.model.repository.LanesRepository
import com.crystalpigeon.busnovisad.model.repository.ScheduleRepository
import com.crystalpigeon.busnovisad.model.repository.SeasonRepository
import kotlinx.coroutines.Deferred
import java.util.*
import javax.inject.Inject


class MainViewModel {
    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    @Inject
    lateinit var seasonRepository: SeasonRepository

    @Inject
    lateinit var lanesRepository: LanesRepository

    @Inject
    lateinit var context: Context

    val isLoading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Boolean>()

    init {
        BusNsApp.app.component.inject(this)
    }

    fun getFavorites(day: String): LiveData<List<Schedule>> {
        return scheduleRepository.getScheduleFavorites(day)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun getTabPositionByDate(): Int {
        return when (Calendar.getInstance() .get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> 2
            Calendar.SATURDAY -> 1
            else -> 0
        }
    }

    suspend fun fetchAllSchedule() {
        if (!seasonRepository.shouldUpdate()) return
        if (!isNetworkAvailable()) {
            networkError.postValue(true)
            return
        }

        var successful = true
        isLoading.postValue(true)
        lanesRepository.cacheAllLanes()
        val jobs: ArrayList<Deferred<Boolean>> = arrayListOf()

        val favorites = lanesRepository.getFavorites()
        jobs.addAll(scheduleRepository.cacheSchedule(favorites))

        val nonFavorites = lanesRepository.getNonFavorites()
        jobs.addAll(scheduleRepository.cacheSchedule(nonFavorites))

        for (result in jobs) {
            if (!result.await()) {
                successful = false
                break
            }
        }

        isLoading.postValue(false)
        if (successful) seasonRepository.seasonUpdated()
    }
}