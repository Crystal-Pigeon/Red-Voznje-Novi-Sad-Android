package com.crystalpigeon.busnovisad.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.model.entity.Lane
import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import kotlinx.android.synthetic.main.line.view.*
import kotlinx.coroutines.*

class LaneAdapter(
    var lanes: ArrayList<Lane>,
    val context: Context,
    val viewModel: LanesViewModel
) :
    RecyclerView.Adapter<LaneAdapter.ViewHolder>() {
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

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

        fun bind(lane: Lane) {
            view.lane_number.text = lane.number
            view.lane_name.text = lane.laneName

            coroutineScope.launch(Dispatchers.Main) {
                view.check.visibility =
                    if (viewModel.isFavorite(lane)) View.VISIBLE else View.INVISIBLE
            }

            view.setOnClickListener {
                coroutineScope.launch(Dispatchers.IO) {
                    viewModel.onLaneClicked(lane)
                    withContext(Dispatchers.Main) { notifyItemChanged(adapterPosition) }
                }
            }
        }
    }
}
