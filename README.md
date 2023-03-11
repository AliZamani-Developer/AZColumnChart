
# AZColumnChart


## Screenshots

<img src="/images/image1.jpg" width=30% height=100%>




## Installation


Gradle:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
dependencies {
        implementation 'com.github.AliZamani-Developer:AZColumnChart:1.0.0'
}
```
Maven:
```
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
<dependency>
    <groupId>com.github.AliZamani-Developer</groupId>
    <artifactId>AZColumnChart</artifactId>
    <version>1.0.0</version>
</dependency>
```


## Usage
add `AZColumnChart` view to layout

```
 <com.android.columnchart.AZColumnChart
        android:id="@+id/chart"
        android:layout_width="match_parent"
        android:layout_margin="22dp"
        android:layout_height="250dp"/>
```

Set the display type and add values ​​to the chart

```
 chart = findViewById(R.id.chart)
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
```        

## Features

- setMarginIcon
- setColorMode
- setBottomColorOpacity
- setChartBackgroundColor
- setCornerColumn
- setIconTint
- setIconVisibility
- setInputColor
- setShadowColor
- setTextSize
- setTypeface
- setTextColor
- setTextPaint
- setTopColorOpacity
- setData
