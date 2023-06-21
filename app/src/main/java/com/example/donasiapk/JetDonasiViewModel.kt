package com.example.donasiapk

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.donasiapk.data.DonasiRepository
import com.example.donasiapk.model.donasi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetDonasiViewModel(private val repository :DonasiRepository) : ViewModel() {
    private val _groupedDonasi = MutableStateFlow(
        repository.getDonasi()
            .sortedBy { it.nama }
            .groupBy { it.nama[0] }
    )
    val groupedDonasi : StateFlow<Map<Char, List<donasi>>> get() = _groupedDonasi

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery : String){
        _query.value = newQuery
        _groupedDonasi.value = repository.searchDonasi(_query.value)
            .sortedBy { it.nama }
            .groupBy { it.nama[0] }
    }

}



class ViewModelFactory(private val repository: DonasiRepository):
        ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JetDonasiViewModel::class.java)){
            return JetDonasiViewModel(repository) as T
        }
        throw IllegalAccessException("Unknown ViewModel class : " + modelClass.name)
    }

        }























