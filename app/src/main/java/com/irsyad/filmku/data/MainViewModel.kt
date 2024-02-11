package com.irsyad.filmku.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irsyad.filmku.data.response.ResultsItem
import com.irsyad.filmku.data.network.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _listPopuler = MutableLiveData<List<ResultsItem>>()
    val listPopuler: LiveData<List<ResultsItem>>
        get() = _listPopuler

    init {
        // Set API key before using MainViewModel
        ApiConfig.setApiKey("5553594234f065a59e3fdc260e3d95ed")
    }

    fun getAllPopuler() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiConfig.getApiService().getAllPopuler(ApiConfig.getApiKey())
                if (response.isSuccessful) {
                    val results: List<ResultsItem> = response.body()?.results?.filterNotNull() ?: emptyList()
                    _listPopuler.postValue(results)

                    // Tampilkan data respons ke Logcat
                    Log.d("MainViewModel", "List Populer: $results")
                } else {
                    // Handle error response
                    Log.e("MainViewModel", "Error fetching data: ${response.message()}")
                }
            } catch (e: Exception) {
                // Handle exceptionz
                handleError(e)
            }
        }
    }

    private fun handleError(e: Throwable) {
        when (e) {
            is HttpException -> {
                // Handle HTTP exceptions (like 404, 500, etc.)
            }
            else -> {
                // Handle other exceptions
            }
        }
    }
}
