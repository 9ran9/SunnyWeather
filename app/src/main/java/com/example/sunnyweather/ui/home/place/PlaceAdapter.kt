package com.example.sunnyweather.ui.home.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.R
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.ui.home.weather.WeatherActivity

//我也蒙了，传了个碎片...感觉应该是为了能在碎片中调用吧。
class PlaceAdapter(private val fragment: PlaceFragment,private val placeList: List<Place>):
       RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val placeName :TextView = view.findViewById(R.id.placeName)
        val placeAddress : TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        val holder =  ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition  //这个方法得到对应的位置，就可以完成许多的操作了
            val place = placeList[position]

            val activity = fragment.activity
            if(activity is WeatherActivity){
                //猛啊，在碎片里对活动的东西动手：
               activity.binding.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            }else{
                val intent = Intent(parent.context,WeatherActivity::class.java).apply {
                    putExtra("location_lng",place.location.lng)
                    putExtra("location_lat",place.location.lat)
                    putExtra("place_name",place.name)
                }
                fragment.startActivity(intent)
                activity?.finish()//关闭此碎片所依附的活动
            }
            fragment.viewModel.savePlace(place)

        }
        return holder
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text =  place.address
    }

    override fun getItemCount() = placeList.size

}