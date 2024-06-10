package com.example.sunnyweather.ui.home.place

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunnyweather.MainActivity
import com.example.sunnyweather.databinding.FragmentHomeBinding
import com.example.sunnyweather.ui.home.weather.WeatherActivity

class PlaceFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
     val viewModel by lazy { ViewModelProvider(this)[PlaceViewModel::class.java] }
      //懒加载实例化
    private lateinit var adapter: PlaceAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //这个写法真的抽象。
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()
        if(activity is MainActivity && viewModel.isPlaceSave()){
            val place = viewModel.getSavePlace()
            val intent = Intent(context,WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerVeiw.layoutManager = layoutManager
        adapter = PlaceAdapter(this,viewModel.placeList)
        binding.recyclerVeiw.adapter = adapter
        binding.searchPlaceEdit.addTextChangedListener { editable ->
            //添加内容改变监听器，还有这种监听器...
            val context = editable.toString()
            if(context.isNotEmpty()){
                viewModel.searchPlaces(context)
                //改变searchLiveData，被监听，实现搜索，改变对应的值
            }else{
                binding.recyclerVeiw.visibility = View.GONE
                binding.bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeListData.observe(this) { result ->
            val places = result.getOrNull()
            if (places != null) {
                binding.recyclerVeiw.visibility = View.VISIBLE
                binding.bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places) //初始化表的数据
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未查询到任何地点", Toast.LENGTH_SHORT).show()
                //"未查询到任何地点".showToast(activity) 这个还是有局限啊，可以考虑把这个方法设置为碎片...
                //把对应的类型转化给到文件里面。

                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}