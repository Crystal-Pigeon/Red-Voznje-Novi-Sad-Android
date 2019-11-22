package com.crystalpigeon.busnovisad.view.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.model.LaneResponse
import kotlinx.android.synthetic.main.line.view.*

class LineAdapter(lines: ArrayList<LaneResponse>) :
    RecyclerView.Adapter<LineAdapter.ViewHolder>() {

    private var lines: ArrayList<LaneResponse>? = null

    init {
        this.lines = lines
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

    override fun getItemCount(): Int = lines?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lines?.elementAt(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(line: LaneResponse?) {
            view.line_number.text = line?.number
            view.line_name.text = line?.laneName
        }

    }

}