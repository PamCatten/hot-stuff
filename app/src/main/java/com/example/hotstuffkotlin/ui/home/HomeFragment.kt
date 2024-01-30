package com.example.hotstuffkotlin.ui.home

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentHomeBinding
import com.example.hotstuffkotlin.ui.OnboardActivity
import com.example.hotstuffkotlin.utils.ChartMarker
import com.example.hotstuffkotlin.utils.DatabaseHelper
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.color.MaterialColors

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // onboard screen testing
        val isOnboardCompleted = false
        if (isOnboardCompleted == false) {
//            findNavController().navigate(R.id.action_home_to_onboard)
            startActivity(Intent(context, OnboardActivity::class.java))
        }

        val totalItems = view.findViewById<TextView>(R.id.label_building_quantity)
        val totalValue = view.findViewById<TextView>(R.id.label_value_total)
//        val buildingName = view.findViewById<TextView>(R.id.label_building_name)

        val context = requireContext()
        val colorOnPrimary = MaterialColors.getColor(context, com.google.android.material.R.attr.colorOnPrimary, Color.RED)

        val currencyIcon = SharedPreferenceHelper.getInstance(context).getCurrencyPref(context)
        val derivedTotal = DatabaseHelper(context).getTotalValue()

        totalValue.text = "$currencyIcon $derivedTotal" // TODO: Placeholders w/ string resource
        totalItems.text = DatabaseHelper(context).getTotalQuantity()

//        val derivedName = "Sample House"
//        buildingName.text = derivedName

        val catChart = view.findViewById<BarChart>(R.id.topBarChart)
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

        val catBarDataSet = BarDataSet(catEntries, "Category by Total Item Quantity (in $currencyIcon)")
        catChart.data = BarData(catBarDataSet)
        catBarDataSet.setColors(com.example.hotstuffkotlin.utils.ColorTemplate.CHART_THEME, 255)
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

        val roomChart = view.findViewById<HorizontalBarChart>(R.id.bottomBarChart)
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

        val roomBarDataSet = BarDataSet(roomEntries, "Room by Total Item Value (in $currencyIcon)")
        roomChart.data = BarData(roomBarDataSet)

        roomBarDataSet.setColors(com.example.hotstuffkotlin.utils.ColorTemplate.CHART_THEME, 255)
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

        // TODO: Obliterates DRY, cannot customize menu items visibility w/ activity based menu providers, find another workaround
        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (!menu.hasVisibleItems()) menuInflater.inflate(R.menu.menu_toolbar_main, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.toolbar_main_search -> { return true }
                    R.id.toolbar_main_download -> {
                        DatabaseHelper(requireContext()).exportCSV()
                        return true
                    }
                    R.id.toolbar_main_report -> {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.toolbar_issue_link))))
                        return true
                    }
                    R.id.toolbar_main_rate -> { return true }
                    R.id.toolbar_main_feedback -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        // TODO: Find an alternative way to extract these
                        intent.data = Uri.parse(
                            "mailto:campatten.dev@outlook.com" +
                                    "?subject=FEEDBACK: (Your Suggestion)" +
                                    "&body=Hey! Thanks for helping me improve Hot Stuff. Just a quick heads up, please make sure 'feedback' is somewhere in the subject of your suggestion so it ends up where I can see it! \n\n Much love, \nCam"
                        )
                        startActivity(intent)
                        return true
                    }
                    else -> return true
                }
            }
            override fun onPrepareMenu(menu: Menu) {
                menu.findItem(R.id.toolbar_main_search).setVisible(false)
                menu.findItem(R.id.toolbar_main_download).setVisible(true)
                menu.findItem(R.id.toolbar_main_report).setVisible(true)
                menu.findItem(R.id.toolbar_main_rate).setVisible(true)
                menu.findItem(R.id.toolbar_main_feedback).setVisible(true)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}