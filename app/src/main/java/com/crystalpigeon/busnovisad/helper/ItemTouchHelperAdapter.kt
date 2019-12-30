package com.crystalpigeon.busnovisad.helper

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition:Int):Boolean
}