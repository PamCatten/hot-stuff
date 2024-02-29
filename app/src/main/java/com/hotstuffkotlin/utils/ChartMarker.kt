package com.hotstuffkotlin.utils

import android.content.Context
import android.widget.TextView
import com.hotstuffkotlin.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

/**
 * A class used to specify displayed markers for values in a chart.
 * @param context Used to push and receive information from the application environment.
 * @param layoutResource Resource to use for the underlying [MarkerView].
 * @param labels List of strings to appear as data labels in derived charts.
 * @see MarkerView
 * @author Cam Patten
 */
class ChartMarker(context: Context, layoutResource: Int,
    private val labels: ArrayList<String>): MarkerView(context, layoutResource) {
    private var txtViewData: TextView? = null
    init { txtViewData = findViewById(R.id.home_chart_dialog) }

    override fun refreshContent(entry: Entry, highlight: Highlight?) {
        val xAxis = entry.x.toInt()
        txtViewData?.text = context.getString(R.string.label_chart_marker, labels[xAxis], entry.y)

        super.refreshContent(entry, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2f), -height.toFloat())
    }
}