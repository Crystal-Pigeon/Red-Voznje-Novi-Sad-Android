package com.crystalpigeon.busnovisad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.helper.Event
import com.crystalpigeon.busnovisad.model.Result
import com.crystalpigeon.busnovisad.model.entity.Schedule
import com.crystalpigeon.busnovisad.model.repository.LanesRepository
import com.crystalpigeon.busnovisad.model.repository.ScheduleRepository
import com.crystalpigeon.busnovisad.model.repository.SeasonRepository
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject


class MainViewModel : ViewModel() {
    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    @Inject
    lateinit var seasonRepository: SeasonRepository

    @Inject
    lateinit var lanesRepository: LanesRepository

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    private val _importantError = MutableLiveData<Event<Message>>()
    private val _nonImportantError = MutableLiveData<Event<Message>>()
    private val _info = MutableLiveData<Event<Message>>()

    val info: LiveData<Event<Message>>
        get() = _info

    val nonImportantError: LiveData<Event<Message>>
        get() = _nonImportantError

    val importantError: LiveData<Event<Message>>
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
                _info.postValue(Event(Message.UP_TO_DATE))
                return
            }
        } else if (shouldUpdate is Result.Error) {
            showError(
                Message.ERROR_CHECKING_FOR_UPDATE,
                shouldUpdate.exception
            )
            return
        }

        val cacheLanes = lanesRepository.cacheAllLanes()
        if (cacheLanes is Result.Error) {
            showError(Message.ERROR_FETCHING_DATA, cacheLanes.exception)
            return
        }

        val favorites = lanesRepository.getFavorites()
        val cacheFavLanes = scheduleRepository.cacheSchedule(favorites)
        if (cacheFavLanes is Result.Error) {
            showError(
                Message.ERROR_FETCHING_DATA,
                cacheFavLanes.exception
            )
            return
        }

        val nonFavorites = lanesRepository.getNonFavorites()
        val cacheNonFavLanes = scheduleRepository.cacheSchedule(nonFavorites)
        if (cacheNonFavLanes is Result.Success) {
            hideLoader()
            seasonRepository.seasonUpdated()
            _info.postValue(Event(Message.UP_TO_DATE))
        } else if (cacheNonFavLanes is Result.Error) {
            showError(
                Message.ERROR_FETCHING_DATA,
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

    private fun showError(_message: Message, exception: Exception) {
        var message = _message
        if (exception is UnknownHostException) {
            message = Message.NO_INTERNET
        }

        if (seasonRepository.isEverUpdated()) {
            _nonImportantError.postValue(Event(message))
        } else {
            _importantError.postValue(Event(message))
        }
        hideLoader()
    }

    enum class Message {
        UP_TO_DATE, ERROR_FETCHING_DATA, NO_INTERNET, ERROR_CHECKING_FOR_UPDATE
    }
}