package com.example.hotstuffkotlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentHomeBinding
import com.example.hotstuffkotlin.utils.DatabaseHelper
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val totalItems = view.findViewById<TextView>(R.id.label_building_quantity)
        val totalValue = view.findViewById<TextView>(R.id.label_value_total)
//        val buildingName = view.findViewById<TextView>(R.id.label_building_name)

        val context = requireContext()

        val currencyIcon = SharedPreferenceHelper.getInstance(context).getCurrencyPref(context)
        val derivedTotal = DatabaseHelper(context).retrieveTotalValue()

        totalValue.text = "$currencyIcon $derivedTotal" // TODO: Placeholders w/ string resource
        totalItems.text = DatabaseHelper(context).retrieveTotalQuantity()

//        val derivedName = "Sample House"
//        buildingName.text = derivedName

        val catChart = view.findViewById<BarChart>(R.id.topBarChart)
        val catDatabaseLabels = DatabaseHelper(context).getCategoryQuantity().first
        val catDatabaseTotals = DatabaseHelper(context).getCategoryQuantity().second

        val catEntries: ArrayList<BarEntry> = ArrayList()
        val catAxisLabels: ArrayList<String> = ArrayList()
//        catEntries.add(BarEntry(1f, 100f))

        for (i in 0 until catDatabaseTotals.size) {
            catEntries.add(BarEntry(i.toFloat(), catDatabaseTotals[i]))
        }

        for (i in 0 until catDatabaseLabels.size) {
            catAxisLabels.add(catDatabaseLabels[i])
        }

        val catBarDataSet = BarDataSet(catEntries, "Category by Total Item Quantity (in $currencyIcon)")
        catChart.data = BarData(catBarDataSet)
//        catBarDataSet.setColors(ColorTemplate.LIBERTY_COLORS, 255)
        catBarDataSet.setColors(com.example.hotstuffkotlin.utils.ColorTemplate.CHART_THEME, 255)
        catChart.setFitBars(true)
        catChart.description.text = ""
        catChart.axisRight.setDrawGridLines(false)
        catChart.axisLeft.setDrawGridLines(false)
        catChart.xAxis.setDrawGridLines(false)
        catChart.xAxis.labelCount = if (catAxisLabels.size > 25) 25 else catAxisLabels.size
        catChart.xAxis.valueFormatter = IndexAxisValueFormatter(catAxisLabels)
        catChart.legend.form = Legend.LegendForm.CIRCLE

        val roomChart = view.findViewById<HorizontalBarChart>(R.id.bottomBarChart)
        val roomDatabaseLabels = DatabaseHelper(context).getRoomValue().first
        val roomDatabaseTotals = DatabaseHelper(context).getRoomValue().second

        val roomEntries: ArrayList<BarEntry> = ArrayList()
        val roomAxisLabels: ArrayList<String> = ArrayList()
//        roomEntries.add(BarEntry(0f, 16f))

        for (i in 0 until roomDatabaseTotals.size) {
            roomEntries.add(BarEntry(i.toFloat(), roomDatabaseTotals[i]))
        }

        for (i in 0 until roomDatabaseLabels.size) {
            roomAxisLabels.add(roomDatabaseLabels[i])
        }

        val roomBarDataSet = BarDataSet(roomEntries, "Room by Total Item Value (in $currencyIcon)")
        roomChart.data = BarData(roomBarDataSet)

//        roomBarDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
        roomBarDataSet.setColors(com.example.hotstuffkotlin.utils.ColorTemplate.CHART_THEME, 255)
        roomBarDataSet.valueTextColor = R.color.grey
        roomChart.setFitBars(true)
        roomChart.description.text = ""
        roomChart.axisRight.setDrawGridLines(false)
        roomChart.axisLeft.setDrawGridLines(false)
        roomChart.xAxis.setDrawGridLines(false)
        roomChart.xAxis.labelCount = if (roomAxisLabels.size > 25) 25 else roomAxisLabels.size
        roomChart.xAxis.valueFormatter = IndexAxisValueFormatter(roomAxisLabels)

//        roomChart.xAxis.valueFormatter = LabelFormatter()
//        roomChart.axisLeft.valueFormatter = LabelFormatter()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}