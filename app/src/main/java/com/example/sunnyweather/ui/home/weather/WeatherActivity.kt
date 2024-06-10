package com.example.sunnyweather.ui.home.weather

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.sunnyweather.R
import com.example.sunnyweather.databinding.ActivityWeatherBinding
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.model.getSky
import com.example.sunnyweather.tool.hintDialog
import com.example.sunnyweather.tool.logD
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherActivity : AppCompatActivity() {
    lateinit var binding:ActivityWeatherBinding
     val viewModel by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT
        val frameLayout = findViewById<FrameLayout>(R.id.titleLayout)
        val insetsController =  WindowInsetsControllerCompat(window,frameLayout)
        //视图插入控制器，window,frameLayout(FrameLayout控件，没有的话用window.decorView获取设备视图)
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            //使内容在状态栏下面：
            insetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            //隐藏状态栏和导航栏，但保存对应的留白空间：
            insetsController.hide(WindowInsetsCompat.Type.statusBars() or
                    WindowInsetsCompat.Type.navigationBars())
        }else{
            //低版本方法：
            frameLayout.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
        }
        binding.now.navBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.drawerLayout.addDrawerListener(object :DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(newState: Int) {
                //改变
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                //滑动
            }

            override fun onDrawerOpened(drawerView: View) {
                //打开
            }

            override fun onDrawerClosed(drawerView: View) {
                //被关闭

                //隐藏输入法：
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS)
            }
        })
        //更新数据，好麻烦啊，这也就是dataBinding的存在的必要把
        if (viewModel.locationLng.isEmpty()){
            viewModel.locationLng = intent.getStringExtra("location_lng")?:""
            "lng".logD(viewModel.locationLng)
        }
        if (viewModel.locationLat.isEmpty()){
            viewModel.locationLat = intent.getStringExtra("location_lat")?:""
            "lat".logD(viewModel.locationLat)
        }
        if (viewModel.placeName.isEmpty()){
            viewModel.placeName = intent.getStringExtra("place_name")?:""
            "placeName".logD(viewModel.placeName)
        }

        //相当于监测这个weatherLiveData
        viewModel.weatherLiveData.observe(this) { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                "WeatherActivity".logD("加载天气布局")
                shouWeatherInfo(weather)
            } else {
                val string = "因网络原因，无法加载对应的天气信息,请刷新重试"
                hintDialog(this,string){
                    refreshWeather()
                }
                "WeatherActivity".logD("无法获取天气信息")
                result.exceptionOrNull()?.printStackTrace()
            }
            binding.swipeRefresh.isRefreshing = false
            //获取完信息后，就关闭刷新
        }
        binding.swipeRefresh.setColorSchemeResources(R.color.black)
        refreshWeather()//改变天气的数据，会被检测者检测
        binding.swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
    }



     fun refreshWeather() {
        //刷新对应的天气数据
        viewModel.refreshWeather(viewModel.locationLng,viewModel.locationLat)
        binding.swipeRefresh.isRefreshing = true
    }

    private fun shouWeatherInfo(weather: Weather) {
        //初始化数据:
        binding.now.placeName.text =viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        //填充now.xml下的东西：
        val currentTempText = "${realtime.temperature.toInt()}°c"
        binding.now.currentTemp.text = currentTempText
        binding.now.currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数${realtime.airQuality.aqi.chn.toInt()}°c"
        binding.now.currentAQI.text = currentPM25Text
        binding.now.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        //填充forecast下的东西：
        binding.forecast.forecastLayout.removeAllViews() //移除子视图的资源
        val days = daily.skycon.size  //得到对应的预测长度
        for (i in 0 until  days) {  //重新赋予对应的资源
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            //得到：forecast_item的视图，并绑进此context
            val view = LayoutInflater.from(this).inflate(
                R.layout.forecast_item,
                binding.forecast.forecastLayout, false
            )
            //from(当前context)，inflate(填充的布局，它的父布局，不覆盖)，
            // 由于碎片的和适配器的父布局很多，所以用的是ViewGroup(继承自View)

            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            //简单日期格式，“yyyy-MM-dd”按照”2000-12-12“格式，Locale.getDefault():区域默认设置
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()}~${temperature.max.toInt()}"
            temperatureInfo.text = tempText  //这样写的啊...
            binding.forecast.forecastLayout.addView(view)
            //以循环的方式，依次添加对应的视图，达到ViewGroup的效果
        }
            //填充life_index:
            //显示当天就行，所以就[0]
            val lifeIndex = daily.lifeIndex
            binding.lifeIndex.coldRiskText.text = lifeIndex.coldRisk[0].desc
            binding.lifeIndex.dressingText.text = lifeIndex.dressing[0].desc
            binding.lifeIndex.ultravioletText.text = lifeIndex.ultraviolet[0].desc
            binding.lifeIndex.carWashingImgText.text = lifeIndex.carWashing[0].desc
            binding.weatherLayout.visibility = View.VISIBLE

    }
}