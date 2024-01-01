package com.example.hotstuffkotlin.utils

import android.content.Context
import android.widget.TextView
import com.example.hotstuffkotlin.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class ChartMarker(context: Context?, layoutResource: Int,
    private val labels: ArrayList<String>): MarkerView(context, layoutResource) {

    private var txtViewData: TextView? = null
    init { txtViewData = findViewById(R.id.home_chart_dialog) }

    override fun refreshContent(entry: Entry, highlight: Highlight?) {
        val xAxis = entry.x.toInt()
        txtViewData?.text = "${labels[xAxis]}: ${entry.y}"

        super.refreshContent(entry, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2f), -height.toFloat())
    }
}