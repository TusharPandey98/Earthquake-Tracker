package com.example.earthquakeapi

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earthquakeapi.adapters.EarthQuakeAdapter
import com.example.earthquakeapi.data.Repository
import com.example.earthquakeapi.utils.Constants
import com.example.earthquakeapi.viewmodel.MainViewModel
import com.example.earthquakeapi.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private var mAdapter: EarthQuakeAdapter = EarthQuakeAdapter()
    private val LOG_TAG = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: MainActivity OnCreate is Called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        //Check the Internet Connection if connection is active
        //then search and display the data
        if(hasInternetConnection()) {
            //Setting up RecyclerView
            setupRecyclerView()
            viewModel.getEarthquake(updateSetting())
            viewModel.myResponse.observe(this, { response ->
                //Showing progress bar while data is not loaded
                progress_circular.visibility = View.GONE
                response.let { mAdapter.setData(it) }
            })
        }else{
            empty_view.text ="No internet Connection"
            progress_circular.visibility = View.GONE
        }

    }

    private fun setupRecyclerView() {
        val earthquakeRecyclerView = recyclerView
        earthquakeRecyclerView.layoutManager = LinearLayoutManager(this)
        earthquakeRecyclerView.adapter = mAdapter
        earthquakeRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

    }


    private fun updateSetting(): HashMap<String, String> {
        val newQuery: HashMap<String, String> = HashMap()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val minMagnitude = sharedPreferences.getString(
            getString(R.string.settings_min_magnitude_key),
            getString(R.string.settings_min_magnitude_default)
        )
        val  limit = sharedPreferences . getString (getString(R.string.settings_min_list_key),
                        getString(R.string.settings_min_list_default))
        val  orderBy = sharedPreferences.getString(getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default))

        newQuery[Constants.QUERY_FORMAT] = "geojson"
        newQuery[Constants.MINIMUM_MAGNITUDE] = minMagnitude.toString()
        newQuery[Constants.LIMIT] = limit.toString()
        newQuery[Constants.ORDER_BY] = orderBy.toString()
        return  newQuery
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}


