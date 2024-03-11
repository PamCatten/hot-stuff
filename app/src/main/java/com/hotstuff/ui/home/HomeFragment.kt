package com.hotstuff.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.color.MaterialColors
import com.hotstuff.R
import com.hotstuff.databinding.FragmentHomeBinding
import com.hotstuff.ui.onboard.OnboardActivity
import com.hotstuff.utils.ChartMarker
import com.hotstuff.utils.DatabaseHelper
import com.hotstuff.utils.PreferenceHelper
import java.math.RoundingMode
import java.text.DecimalFormat

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val context = requireContext()
        val preferenceHelper = PreferenceHelper(context)

        if (preferenceHelper.getStringPref(getString(R.string.key_buildingName)).isNullOrEmpty()) {
            startActivity(Intent(context, OnboardActivity::class.java))
        }
//        if (preferenceHelper.getBooleanPref(getString(R.string.key_onboard)) != false) {
//            startActivity(Intent(context, OnboardActivity::class.java))
//        }
//        DatabaseHelper(context).upgradeTables() // TEMP FUNCTION
        val totalItems = view.findViewById<TextView>(R.id.label_building_quantity)
        val totalValue = view.findViewById<TextView>(R.id.label_value_total)
        val buildingName = view.findViewById<TextView>(R.id.label_building_name)
        val colorOnPrimary = MaterialColors.getColor(context, com.google.android.material.R.attr.colorOnPrimary, Color.RED)
        val currency = preferenceHelper.getStringPref(context.getString(R.string.key_currency))
        val currencyIcon = preferenceHelper.getCurrencyIcon(currency)
        val derivedTotal = DatabaseHelper(context).getTotalValue()

        totalValue.text = getString(R.string.label_building_value_builder, currencyIcon, derivedTotal)
        totalItems.text = DatabaseHelper(context).getTotalQuantity()

        val derivedName = PreferenceHelper(context).getStringPref(getString(R.string.key_buildingName))
        buildingName.text = derivedName

        val catChart = view.findViewById<BarChart>(R.id.chart_home_one)
        val catDatabaseLabels = DatabaseHelper(context).getCategoryQuantity().first
        val catDatabaseTotals = DatabaseHelper(context).getCategoryQuantity().second

        val catEntries: ArrayList<BarEntry> = ArrayList()
        val catAxisLabels: ArrayList<String> = ArrayList()

        for (i in 0 until catDatabaseTotals.size) {
            catEntries.add(BarEntry(i.toFloat(), catDatabaseTotals[i]))
        }

        for (i in 0 until catDatabaseLabels.size) {
            catAxisLabels.add(catDatabaseLabels[i])
        }

        val catBarDataSet = BarDataSet(catEntries, getString(R.string.label_chart_category_title, currencyIcon))
        catBarDataSet.valueFormatter = ChartIntValueFormatter()
        catChart.data = BarData(catBarDataSet)
        catBarDataSet.setColors(com.hotstuff.utils.ThemeHelper.CHART_THEME, 255)
        catChart.setFitBars(true)
        catChart.description.text = ""
        catChart.axisRight.setDrawGridLines(false)
        catChart.axisLeft.setDrawGridLines(false)
        catChart.xAxis.setDrawGridLines(false)
        catChart.xAxis.labelCount = if (catAxisLabels.size > 3) 3 else catAxisLabels.size
        catChart.xAxis.valueFormatter = IndexAxisValueFormatter(catAxisLabels)
        catChart.legend.isEnabled = false
        catBarDataSet.valueTextColor = colorOnPrimary
        catChart.xAxis.textColor = colorOnPrimary
        catChart.setBorderColor(colorOnPrimary)
        catChart.axisLeft.textColor = colorOnPrimary
        catChart.axisRight.textColor = colorOnPrimary
        catChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        catChart.axisRight.isEnabled = false
        catChart.isScaleXEnabled = false
        catChart.marker = ChartMarker(context, R.layout.dialog_chart, catAxisLabels)

        val roomChart = view.findViewById<HorizontalBarChart>(R.id.chart_home_two)
        val roomDatabaseLabels = DatabaseHelper(context).getRoomValue().first
        val roomDatabaseTotals = DatabaseHelper(context).getRoomValue().second
        val roomEntries: ArrayList<BarEntry> = ArrayList()
        val roomAxisLabels: ArrayList<String> = ArrayList()
        for (i in 0 until roomDatabaseTotals.size) {
            roomEntries.add(BarEntry(i.toFloat(), roomDatabaseTotals[i]))
        }
        for (i in 0 until roomDatabaseLabels.size) {
            roomAxisLabels.add(roomDatabaseLabels[i])
        }

        val roomBarDataSet = BarDataSet(roomEntries, getString(R.string.label_chart_room_title, currencyIcon))
        roomBarDataSet.valueFormatter = ChartDecimalValueFormatter()
        roomChart.data = BarData(roomBarDataSet)
        roomBarDataSet.setColors(com.hotstuff.utils.ThemeHelper.CHART_THEME, 255)
        roomChart.setFitBars(true)
        roomChart.description.text = ""
        roomChart.axisRight.setDrawGridLines(false)
        roomChart.axisLeft.setDrawGridLines(false)
        roomChart.xAxis.setDrawGridLines(false)
        roomChart.xAxis.labelCount = if (roomAxisLabels.size > 25) 25 else roomAxisLabels.size
        roomChart.xAxis.valueFormatter = IndexAxisValueFormatter(roomAxisLabels)
        roomChart.legend.isEnabled = false
        roomBarDataSet.valueTextColor = colorOnPrimary
        roomChart.xAxis.textColor = colorOnPrimary
        roomChart.setBorderColor(colorOnPrimary)
        roomChart.axisLeft.textColor = colorOnPrimary // top axis
        roomChart.axisRight.textColor = colorOnPrimary // bottom axis
        roomChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        roomChart.axisLeft.isEnabled = false
        roomChart.isScaleYEnabled = false
        roomChart.marker = ChartMarker(context, R.layout.dialog_chart, roomAxisLabels)

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// TODO: ADD CURRENCY ICON WHEN TIME
class ChartDecimalValueFormatter: ValueFormatter() {
    private val df = DecimalFormat("#,###,###.##")
    override fun getFormattedValue(value: Float): String {
        df.roundingMode = RoundingMode.CEILING
        return df.format(value)
    }
}

class ChartIntValueFormatter: ValueFormatter() {
    private val df = DecimalFormat("#,###,###")
    override fun getFormattedValue(value: Float): String {
        df.roundingMode = RoundingMode.CEILING
        return df.format(value)
    }
}