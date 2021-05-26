package com.example.earthquakeapi.adapters

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.earthquakeapi.R
import com.example.earthquakeapi.model.Earthquake
import com.example.earthquakeapi.model.Feature
import com.example.earthquakeapi.model.Properties
import kotlinx.android.synthetic.main.earthquake_list.view.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

class EarthQuakeAdapter : RecyclerView.Adapter<EarthQuakeAdapter.MyViewHolder>(){
    private var earthquake = mutableListOf<Properties>()

    private val LOCATION_SEPARATOR = " of ";
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.earthquake_list,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEarthquake: Properties = earthquake[position]
        val magnitudeTextView: TextView = holder.itemView.mag_textView
        val magnitudeCircle: GradientDrawable = magnitudeTextView.background as GradientDrawable
        val magnitudeColor: Int = getMagnitudeColor(currentEarthquake.mag,holder.itemView)
        magnitudeCircle.setColor(magnitudeColor)

        var  formattedMagnitude:String = formatMagnitude(currentEarthquake.mag);
        magnitudeTextView.text = formattedMagnitude

        // Get the original location string from the Earthquake object,
        // which can be in the format of "5km N of Cairo, Egypt" or "Pacific-Antarctic Ridge".
        var originalLocation = currentEarthquake.place;

        // If the original location string (i.e. "5km N of Cairo, Egypt") contains
        // a primary location (Cairo, Egypt) and a location offset (5km N of that city)
        // then store the primary location separately from the location offset in 2 Strings,
        // so they can be displayed in 2 TextViews.
        var  primaryLocation:String
        var locationOffset:String
        // Check whether the originalLocation string contains the " of " text
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the " of " text. We expect an array of 2 Strings, where
            // the first String will be "5km N" and the second String will be "Cairo, Egypt".
            var parts = originalLocation.split(LOCATION_SEPARATOR);
            // Location offset should be "5km N " + " of " --> "5km N of"
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            // Primary location should be "Cairo, Egypt"
            primaryLocation = parts[1];
        } else {
            // Otherwise, there is no " of " text in the originalLocation string.
            // Hence, set the default location offset to say "Near the".
            locationOffset = "Near the"
//                getContext().getString(R.string.near_the);
            // The primary location will be the full location string "Pacific-Antarctic Ridge".
            primaryLocation = originalLocation;
        }

        var primaryLocationTextView = holder.itemView.primary_location
        primaryLocationTextView.text = primaryLocation

        val locationOffSetTextView = holder.itemView.location_offset
        locationOffSetTextView.text = locationOffset

        //Create a new date object from milliseconds
        val  dateObject =  Date(currentEarthquake.time);
        val  dateTextView = holder.itemView.date
//        //format the date string
        var formattedDate = formatDate(dateObject);
        dateTextView.text = formattedDate
//
        var timeTextView = holder.itemView.time
        var formatTime = formatTime(dateObject);
        timeTextView.text = formatTime
        Log.d("EarthQuakes recycler", earthquake[0].mag.toString())
        Log.d("EarthQuakes", earthquake[0].place.toString())
        Log.d("EarthQuakes", earthquake[0].time.toString())
        Log.d("EarthQuakes", earthquake[0].url.toString())
    }

    override fun getItemCount(): Int {
        return earthquake.size
    }

    fun setData(newList: Earthquake){
        val index = newList.features.size-1
        for (i in 0..index) {
            earthquake.add(newList.features[i].properties)
        }
        notifyDataSetChanged()
    }
    /**
    //     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
    //     */
    private fun formatTime(dateObject: Date): String {
        var  timeFormat =  SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private fun formatMagnitude(magnitude: Double): String {
        val magnitudeFormat =  DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private fun formatDate(dateObject: Date):String {
        val  dateFormat =  SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }


    private fun getMagnitudeColor(magnitude: Double,view: View): Int {
        var magnitudeColorResourceId: Int
        var magnitudeFloor = floor(magnitude).toInt()

        when (magnitudeFloor) {
            0 -> magnitudeColorResourceId = R.color.magnitude1

            1 -> magnitudeColorResourceId = R.color.magnitude1

            2 -> magnitudeColorResourceId = R.color.magnitude2;

            3 -> magnitudeColorResourceId = R.color.magnitude3;

            4 -> magnitudeColorResourceId = R.color.magnitude4;

            5 -> magnitudeColorResourceId = R.color.magnitude5;

            6 -> magnitudeColorResourceId = R.color.magnitude6;

            7 -> magnitudeColorResourceId = R.color.magnitude7;

            8 -> magnitudeColorResourceId = R.color.magnitude8;

            9 -> magnitudeColorResourceId = R.color.magnitude9;

            else ->
                magnitudeColorResourceId = R.color.magnitude10plus;

        }
        return ContextCompat.getColor(view.context,magnitudeColorResourceId)
    }


}