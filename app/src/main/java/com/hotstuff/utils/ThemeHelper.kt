package com.hotstuff.utils
import android.graphics.Color
import com.github.mikephil.charting.data.BarDataSet

/**
 * A helper object to hold an array of colors in RGB format.
 * @see [BarDataSet.setColors]
 * @author Cam Patten
 */
object ThemeHelper {
    val CHART_THEME = intArrayOf(
        Color.rgb(255, 187, 167), // #ffbba7
        Color.rgb(255, 173, 153), // #ffad99
        Color.rgb(255, 158, 139), // #ff9e8b
        Color.rgb(255, 142, 125), // #ff8e7d
        Color.rgb(255, 125, 110), // #ff7d6e
        Color.rgb(255, 107, 94), // #ff6b5e
        Color.rgb(250, 91, 81), // #fa5b51
        Color.rgb(239, 81, 72), // #ef5148
        Color.rgb(227, 71, 64), // #e34740
        Color.rgb(216, 60, 56), // #d83c38
        Color.rgb(205, 49, 48), // #cd3130
        Color.rgb(193, 36, 40), // #c12428
        Color.rgb(182, 20, 32), // #b61420
        Color.rgb(170, 6, 23), // #aa0617
        Color.rgb(155, 0, 13), // #9b000d
        Color.rgb(124, 0, 0), // #7c0000
        Color.rgb(109, 0, 0), // #6d0000
        Color.rgb(95, 0, 0), // #5f0000
        Color.rgb(81, 0, 0), // #510000
    )

    // Saving resources used here for the README writeup
    // Refresher article I used on choosing chart colors: https://chartio.com/learn/charts/how-to-choose-colors-data-visualization/
    // Colorblind-safe palette generator: https://www.vis4.net/palettes/#/10|s|fc5d52|ffffe0,ff005e,93003a|1|1
}