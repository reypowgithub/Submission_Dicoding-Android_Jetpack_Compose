package com.example.donasiapk.data

import com.example.donasiapk.model.donasi
import com.example.donasiapk.model.donasiData

class DonasiRepository {
    fun getDonasi() :List<donasi>{
        return donasiData.donation
    }
    fun searchDonasi(query : String): List<donasi>{
        return donasiData.donation.filter {
            it.nama.contains(query, ignoreCase = true)
        }
    }
}