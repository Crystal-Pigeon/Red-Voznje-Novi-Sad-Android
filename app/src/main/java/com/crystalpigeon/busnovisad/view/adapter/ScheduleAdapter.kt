package com.crystalpigeon.busnovisad.view.adapter

import CustomTypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.model.Schedule
import kotlinx.android.synthetic.main.schedule_item.view.*
import java.util.*
import kotlin.collections.ArrayList
import android.widget.TextView
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.TypedValue

class ScheduleAdapter(schedules: ArrayList<Schedule>?, context: Context) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    private var schedules: ArrayList<Schedule>? = null
    private var currentHours: Int? = null
    private var context: Context? = null
    private var typeface: Typeface? = null
    private var typefaceLight: Typeface? = null

    init {
        this.schedules = schedules
        currentHours = Calendar.getInstance().time.hours
        this.context = context
        typeface = Typeface.createFromAsset(context.assets, "muli_semibold.ttf")
        typefaceLight = Typeface.createFromAsset(context.assets, "muli_light.ttf")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        )
    }

    override fun getItemCount(): Int = schedules?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(schedules?.elementAt(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(schedule: Schedule?) {
            view.circle_id.text = schedule?.number
            view.lineName.text = schedule?.name

            if (schedule?.lane != null) {
                view.layoutRasp.orientation = LinearLayout.VERTICAL
                view.dir.visibility = View.VISIBLE
                view.dir1.visibility = View.GONE
                view.dir2.visibility = View.GONE
                view.lineForDirectionB.visibility = View.GONE
                view.layoutRasp.visibility = View.VISIBLE
                view.layoutRaspA.visibility = View.GONE
                view.layoutRaspB.visibility = View.GONE
                view.dir.text = schedule.lane
                val keys = schedule.schedule?.keys
                val ifKeyExists = schedule.schedule?.containsKey(currentHours.toString())

                keys?.forEach { key ->
                    val textView = TextView(context)
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.00f)
                    textView.setTextColor(Color.parseColor("#454F73"))
                    textView.typeface = typefaceLight
                    var text = "$key : "

                    schedule.schedule[key]?.forEach { value ->
                        text += "$value "
                    }
                    val spannable = SpannableString(text)

                    if (ifKeyExists!!) {
                        if (key == currentHours.toString()) {

                            spannable.setSpan(
                                ForegroundColorSpan(Color.parseColor("#FD983B")),
                                0,
                                key.length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )

                        }
                    }
                    spannable.setSpan(
                        CustomTypefaceSpan(typeface!!),
                        0,
                        key.length,
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    textView.setText(spannable, TextView.BufferType.SPANNABLE)
                    view.layoutRasp.addView(textView)
                }

            } else {
                view.layoutRaspA.orientation = LinearLayout.VERTICAL
                view.layoutRaspB.orientation = LinearLayout.VERTICAL
                val ifKeyExistsInScheduleA =
                    schedule?.scheduleA?.containsKey(currentHours.toString())
                val ifKeyExistsInScheduleB =
                    schedule?.scheduleB?.containsKey(currentHours.toString())
                view.dir.visibility = View.GONE
                view.dir1.visibility = View.VISIBLE
                view.dir2.visibility = View.VISIBLE
                view.lineForDirectionB.visibility = View.VISIBLE
                view.layoutRasp.visibility = View.GONE
                view.layoutRaspA.visibility = View.VISIBLE
                view.layoutRaspB.visibility = View.VISIBLE
                view.dir1.text = schedule?.directionA
                view.dir2.text = schedule?.directionB
                val keysA = schedule?.scheduleA?.keys
                keysA?.forEach { key ->
                    val textView = TextView(context)
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.00f)
                    textView.setTextColor(Color.parseColor("#454F73"))
                    textView.typeface = typefaceLight
                    var text = "$key : "

                    schedule.scheduleA[key]?.forEach { value ->
                        text += "$value "
                    }

                    val spannable = SpannableString(text)

                    if (ifKeyExistsInScheduleA!!) {
                        if (key == currentHours.toString()) {
                            spannable.setSpan(
                                ForegroundColorSpan(Color.parseColor("#FD983B")),
                                0,
                                key.length,
                                Spannable.SPAN_COMPOSING
                            )
                        }
                    }
                    spannable.setSpan(
                        CustomTypefaceSpan(typeface!!),
                        0,
                        key.length,
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    textView.setText(spannable, TextView.BufferType.SPANNABLE)
                    view.layoutRaspA.addView(textView)
                }
                val keysB = schedule?.scheduleB?.keys
                keysB?.forEach { key ->
                    val textView = TextView(context)
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.00f)
                    textView.setTextColor(Color.parseColor("#454F73"))
                    textView.typeface = typefaceLight
                    var text = "$key : "

                    schedule.scheduleB[key]?.forEach { value ->
                        text += "$value "
                    }
                    val spannable = SpannableString(text)

                    if (ifKeyExistsInScheduleB!!) {
                        if (key == currentHours.toString()) {

                            spannable.setSpan(
                                ForegroundColorSpan(Color.parseColor("#FD983B")),
                                0,
                                key.length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                    }
                    spannable.setSpan(
                        CustomTypefaceSpan(typeface!!),
                        0,
                        key.length,
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    textView.setText(spannable, TextView.BufferType.SPANNABLE)
                    view.layoutRaspB.addView(textView)
                }
            }


        }
    }

}