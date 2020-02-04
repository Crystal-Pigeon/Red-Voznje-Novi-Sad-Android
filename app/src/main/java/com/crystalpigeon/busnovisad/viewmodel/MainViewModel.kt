package com.crystalpigeon.busnovisad.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.helper.Event
import com.crystalpigeon.busnovisad.model.Result
import com.crystalpigeon.busnovisad.model.entity.Schedule
import com.crystalpigeon.busnovisad.model.repository.LanesRepository
import com.crystalpigeon.busnovisad.model.repository.ScheduleRepository
import com.crystalpigeon.busnovisad.model.repository.SeasonRepository
import java.net.UnknownHostException
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

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    private val _importantError = MutableLiveData<Event<String>>()
    private val _nonImportantError = MutableLiveData<Event<String>>()
    private val _info = MutableLiveData<Event<String>>()

    val info: LiveData<Event<String>>
        get() = _info

    val nonImportantError: LiveData<Event<String>>
        get() = _nonImportantError

    val importantError: LiveData<Event<String>>
        get() = _importantError

    val isLoading: LiveData<Event<Boolean>>
        get() = _isLoading

    init {
        BusNsApp.app.component.inject(this)
    }

    fun getFavorites(day: String): LiveData<List<Schedule>> {
        return scheduleRepository.getScheduleFavorites(day)
    }

    suspend fun removeSchedule(schedule: Schedule) {
        scheduleRepository.deleteSchedule(schedule.id)
    }

    fun getTabPositionByDate(): Int {
        return when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> 2
            Calendar.SATURDAY -> 1
            else -> 0
        }
    }

    suspend fun fetchAllSchedule() {
        val shouldUpdate = seasonRepository.shouldUpdate()
        if (shouldUpdate is Result.Success) {
            if (shouldUpdate.data) {
                showLoader()
            } else {
                showSnackBar(context.getString(R.string.everything_is_up_to_date))
                return
            }
        } else if (shouldUpdate is Result.Error) {
            showError(
                context.getString(R.string.error_checking_for_update),
                shouldUpdate.exception
            )
            return
        }

        val cacheLanes = lanesRepository.cacheAllLanes()
        if (cacheLanes is Result.Error) {
            showError(context.getString(R.string.error_fetching_lanes), cacheLanes.exception)
            return
        }

        val favorites = lanesRepository.getFavorites()
        val cacheFavLanes = scheduleRepository.cacheSchedule(favorites)
        if (cacheFavLanes is Result.Error) {
            showError(
                context.getString(R.string.error_fetching_scedule),
                cacheFavLanes.exception
            )
            return
        }

        val nonFavorites = lanesRepository.getNonFavorites()
        val cacheNonFavLanes = scheduleRepository.cacheSchedule(nonFavorites)
        if (cacheNonFavLanes is Result.Success) {
            hideLoader()
            seasonRepository.seasonUpdated()
        } else if (cacheNonFavLanes is Result.Error) {
            showError(
                context.getString(R.string.error_fetching_scedule),
                cacheNonFavLanes.exception
            )
            return
        }
    }

    private fun showLoader() {
        _isLoading.postValue(Event(true))
    }

    private fun hideLoader() {
        _isLoading.postValue(Event(false))
    }

    private fun showError(_message: String, exception: Exception) {
        var message = _message
        if (exception is UnknownHostException) {
            message = context.getString(R.string.no_internet_connection)
        }

        if (seasonRepository.isEverUpdated()) {
            _nonImportantError.postValue(Event(message))
        } else {
            _importantError.postValue(Event(message))
        }
        hideLoader()
    }

    private fun showSnackBar(message: String) {
        _info.postValue(Event(message))
    }
}