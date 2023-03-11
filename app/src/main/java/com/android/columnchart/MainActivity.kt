package com.android.columnchart

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    lateinit var chart: AZColumnChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        chart.setData(
            arrayListOf(
                ChartEntry(700f, icon = R.drawable.bitcoin__btc),
                ChartEntry(600f, icon = R.drawable.binance_coin__bnb),
                ChartEntry(500f, icon = R.drawable.ethereum__eth),
                ChartEntry(400f, icon = R.drawable.binance_usd__busd),
                ChartEntry(300f, icon = R.drawable.cardano__ada),
                ChartEntry(200f, icon = R.drawable.tether__usdt),
                )
        )
        
        chart.setIconVisibility(View.VISIBLE)
        chart.setIconTint(Color.WHITE)
        chart.setMarginIcon(4f)
        chart.setInputColor(AZColumnChart.InputColor.DEFAULT_GRADIENT_COLOR)
        chart.setColorMode(AZColumnChart.ColorMode.GRADIENT_COLOR)
        chart.build()
    }


    private fun initViews() {
        chart = findViewById(R.id.chart)
    }

}