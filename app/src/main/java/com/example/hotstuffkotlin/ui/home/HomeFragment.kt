package com.example.hotstuffkotlin.ui.home

import android.graphics.Color
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
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val totalItems = view.findViewById<TextView>(R.id.label_building_quantity)
        val totalValue = view.findViewById<TextView>(R.id.label_value_total)
        val buildingName = view.findViewById<TextView>(R.id.label_building_name)

        val context = requireContext()

        val currencyIcon = SharedPreferenceHelper.getInstance(context).getCurrencyPref(context)
        val derivedTotal = DatabaseHelper(context).retrieveTotalValue()

        totalValue.text = "$currencyIcon $derivedTotal" // TODO: Placeholders w/ string resource
        totalItems.text = DatabaseHelper(context).retrieveTotalQuantity()

//
//        val tvActiveBuildingName = view?.findViewById<TextView>(R.id.tvActiveBuildingName)
//        val derivedName = "Sample House"
//        tvActiveBuildingName?.text = derivedName

        val distroChart = view.findViewById<BarChart>(R.id.topBarChart)
        val list : ArrayList<BarEntry> = ArrayList()
        list.add(BarEntry(1f, 100f))
        list.add(BarEntry(2f, 101f))
        list.add(BarEntry(3f, 103f))
        list.add(BarEntry(4f, 105f))
        list.add(BarEntry(5f, 104f))
        list.add(BarEntry(6f, 102f))
        val barDataSet = BarDataSet(list, "Category by Quantity")
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
        val barData = BarData(barDataSet)
        distroChart.setFitBars(true)
        distroChart.data = barData
        distroChart.description.text = ""
//        distroChart.description.text="Item Distribution by Category"
        distroChart.axisRight.setDrawGridLines(false)
        distroChart.axisLeft.setDrawGridLines(false)
        distroChart.xAxis.setDrawGridLines(false)



        val valueChart = view.findViewById<HorizontalBarChart>(R.id.bottomBarChart)
        val valueList : ArrayList<BarEntry> = ArrayList()
        valueList.add(BarEntry(0f, 16f))
//        valueList.add(BarEntry(1f, 11f))
//        valueList.add(BarEntry(2f, 14f))
//        valueList.add(BarEntry(3f, 13f))
//        valueList.add(BarEntry(4f, 15f))
//        valueList.add(BarEntry(5f, 12f))
//        valueList.add(BarEntry(6f, 16f))
        val roomLabels = DatabaseHelper(context).retrieveRoomValues().first
//        valueList.add(BarEntry(0f, roomLabels[0]))
//        valueList.add(BarEntry(1f, roomLabels[1]))
//        valueList.add(BarEntry(2f, roomLabels[2]))

//        for (i in 0..roomLabels.size) {
//            valueList.add(BarEntry(i.toFloat(), roomLabels[i]))
//        }

        val valueAxisLabels : ArrayList<String> = ArrayList()
        valueAxisLabels.add(roomLabels[0])
//        valueAxisLabels.add(roomLabels[1])

        val valueBarDataSet = BarDataSet(valueList, "Room by Total Item Value")
        valueBarDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
        valueBarDataSet.valueTextColor = Color.RED
        val valueBarData = BarData(valueBarDataSet)
        valueChart.setFitBars(true)
        valueChart.data = valueBarData
        valueChart.description.text = ""
//        valueChart.description.text="Value by Room"
        valueChart.axisRight.setDrawGridLines(false)
        valueChart.axisLeft.setDrawGridLines(false)
        valueChart.xAxis.setDrawGridLines(false)
        valueChart.xAxis.valueFormatter = IndexAxisValueFormatter(valueAxisLabels)

//        valueChart.xAxis.valueFormatter = LabelFormatter()
//        valueChart.axisLeft.valueFormatter = LabelFormatter()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}