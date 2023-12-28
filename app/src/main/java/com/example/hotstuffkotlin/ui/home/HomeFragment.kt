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
        val derivedTotal = DatabaseHelper(context).calculateTotal()

        totalValue.text = "$currencyIcon $derivedTotal" // TODO: Placeholders w/ string resource
        totalItems.text = DatabaseHelper(context).tallyItems()

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
//        distroChart.description.text="Item Distribution by Category"
        distroChart.axisRight.setDrawGridLines(false)
        distroChart.axisLeft.setDrawGridLines(false)
        distroChart.xAxis.setDrawGridLines(false)

        val valueChart = view.findViewById<HorizontalBarChart>(R.id.bottomBarChart)
        val valueList : ArrayList<BarEntry> = ArrayList()
        valueList.add(BarEntry(1f, 106f))
        valueList.add(BarEntry(2f, 101f))
        valueList.add(BarEntry(3f, 104f))
        valueList.add(BarEntry(4f, 103f))
        valueList.add(BarEntry(5f, 105f))
        valueList.add(BarEntry(6f, 102f))
        valueList.add(BarEntry(7f, 106f))
        valueList.add(BarEntry(8f, 107f))
        valueList.add(BarEntry(9f, 102f))
        valueList.add(BarEntry(10f, 104f))
        valueList.add(BarEntry(11f, 103f))

        val valueAxisLabels : ArrayList<String> = ArrayList()
        valueAxisLabels.add("")
        valueChart.xAxis.valueFormatter.

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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}