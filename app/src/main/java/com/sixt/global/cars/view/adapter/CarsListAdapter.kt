package com.sixt.global.cars.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sixt.global.R
import com.sixt.global.cars.service.model.Car
import com.sixt.global.common.extension.clean
import com.sixt.global.common.extension.inflate
import com.sixt.global.common.extension.toPercentFormat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.car_list_item.view.*

class CarsListAdapter(
    private val cars: MutableList<Car> = mutableListOf(),
    private val onClick: (Car) -> Unit = {}
) : RecyclerView.Adapter<CarsListAdapter.CarsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        return CarsViewHolder(parent.inflate(R.layout.car_list_item))
    }

    override fun getItemCount() = cars.size

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        holder.bind(cars[position])
    }

    inner class CarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(car: Car) {
            with(itemView) {
                loadUrlIntoView(iv_car_icon, car.carImageUrl)
                tv_car_name.text = car.name
                tv_car_licence.text = car.licensePlate
                tv_car_model.text = car.modelName
                tv_car_color.text = car.color.clean()
                tv_car_transmission.text = when (car.transmission) {
                    "A" -> context.getString(R.string.auto_transmission)
                    "M" -> context.getString(R.string.manual_transmission)
                    else -> context.getString(R.string.unknown)
                }
                tv_car_cleanliness.text = car.innerCleanliness.clean()
                tv_car_fuel.text = when (car.fuelType) {
                    "P" -> context.getString(R.string.propane)
                    "E" -> context.getString(R.string.ethanol)
                    "D" -> context.getString(R.string.diesel)
                    else -> context.getString(R.string.unknown)
                }
                tv_car_fuel_level.text = (car.fuelLevel * 100).toPercentFormat(removeZeros = true)
                itemView.setOnClickListener { onClick.invoke(car) }
            }
        }

        private fun loadUrlIntoView(view: ImageView, url: String) {
            Glide.with(view.context)
                .load(url)
                .placeholder(R.drawable.mini_cabrio)
                .error(R.drawable.mini_cabrio)
                .into(view)
        }
    }
}