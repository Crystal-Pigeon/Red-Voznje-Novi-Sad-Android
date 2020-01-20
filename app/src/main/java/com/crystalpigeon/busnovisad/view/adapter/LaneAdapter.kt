package com.crystalpigeon.busnovisad.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.model.dao.FavoriteLanesDao
import com.crystalpigeon.busnovisad.model.entity.FavoriteLane
import com.crystalpigeon.busnovisad.model.entity.Lane
import kotlinx.android.synthetic.main.line.view.*
import kotlinx.coroutines.*
import javax.inject.Inject

class LaneAdapter(
    var lanes: ArrayList<Lane>,
    val context: Context
) :
    RecyclerView.Adapter<LaneAdapter.ViewHolder>() {

    @Inject
    lateinit var favLanesDao: FavoriteLanesDao
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    init {
        BusNsApp.app.component.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.line,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = lanes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lanes.elementAt(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(lane: Lane?) {
            view.lane_number.text = lane?.number
            view.lane_name.text = lane?.laneName

            coroutineScope.launch {
                withContext(Dispatchers.Main) {
                    lane?.let {
                        view.check.visibility = if (favLanesDao.getFavLane(it.id).isEmpty())
                            View.INVISIBLE else View.VISIBLE
                    }
                }
            }
            view.setOnClickListener {
                lane?.let {
                    coroutineScope.launch(Dispatchers.IO) {
                        if (favLanesDao.getFavLane(it.id).isEmpty()) {
                            val favLane = FavoriteLane(
                                it.id,
                                it.type,
                                favLanesDao.getBiggestOrder() ?: 1
                            )

                            favLanesDao.insertFavLane(favLane)
                        } else {
                            favLanesDao.deleteFavLane(it.id)
                        }

                        withContext(Dispatchers.Main) {
                            notifyItemChanged(adapterPosition)
                        }
                    }
                }
            }
        }
    }
}