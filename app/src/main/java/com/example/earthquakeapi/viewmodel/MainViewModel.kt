package com.example.earthquakeapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.earthquakeapi.model.Earthquake
import com.example.earthquakeapi.data.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository):ViewModel() {


val myResponse:MutableLiveData<Earthquake> = MutableLiveData()

    fun getEarthquake(updateSetting: HashMap<String, String>) {
        viewModelScope.launch {
            val response = repository.getEarthquake(updateSetting)
            myResponse.value = response
        }
    }

}