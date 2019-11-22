package com.crystalpigeon.busnovisad.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.model.Lane
import kotlinx.android.synthetic.main.line.view.*

class LineAdapter(var lines: ArrayList<Lane>) :
    RecyclerView.Adapter<LineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.line,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = lines.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lines.elementAt(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(line: Lane?) {
            view.line_number.text = line?.number
            view.line_name.text = line?.laneName
        }
    }
}