package com.hotstuff.utils

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.hotstuff.R
import java.text.DecimalFormat

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
        val df = DecimalFormat("#,###,###.##")
        txtViewData?.text = context.getString(R.string.label_chart_marker, labels[xAxis], df.format(entry.y))

        super.refreshContent(entry, highlight)
    }
    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        val totalWidth = resources.displayMetrics.widthPixels
        val offset = MPPointF()

        if (posX > totalWidth - width) offset.x = (-width).toFloat()
        else offset.x = (-(width / 2)).toFloat()

        offset.y = -(height / 2).toFloat()

        return offset
    }
}