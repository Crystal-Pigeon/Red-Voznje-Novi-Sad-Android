package com.crystalpigeon.busnovisad.helper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(val adapter: ItemTouchHelperAdapter) : ItemTouchHelper.Callback() {

    override fun isItemViewSwipeEnabled() = false

    override fun isLongPressDragEnabled() = false

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP.or(ItemTouchHelper.DOWN)
        val swipeFlags = ItemTouchHelper.START.or(ItemTouchHelper.END)
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //No swipe supported for now
    }
}