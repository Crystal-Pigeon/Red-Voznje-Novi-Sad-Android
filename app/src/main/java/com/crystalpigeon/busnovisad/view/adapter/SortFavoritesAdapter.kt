package com.crystalpigeon.busnovisad.view.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.helper.ItemTouchHelperAdapter
import com.crystalpigeon.busnovisad.helper.OnStartDragListener
import com.crystalpigeon.busnovisad.model.entity.Lane
import kotlinx.android.synthetic.main.item_sort_fav.view.*
import java.util.*

class SortFavoritesAdapter(
    var favorites: List<Lane>,
    private val dragListener: OnStartDragListener,
    private val updateOrderListener: UpdateOrderListener
) : RecyclerView.Adapter<SortFavoritesAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sort_fav,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = favorites.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(lane: Lane) {
            view.lane_number.text = lane.number
            view.lane_name.text = lane.laneName
            view.sort.setOnTouchListener { view, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                return@setOnTouchListener false
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(favorites, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        updateOrderListener.updateOrder(favorites)
        return true
    }

    interface UpdateOrderListener{
        fun updateOrder(favorites: List<Lane>)
    }
}