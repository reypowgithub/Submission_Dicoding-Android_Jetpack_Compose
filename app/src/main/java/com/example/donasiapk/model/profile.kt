package com.example.donasiapk.model

import com.example.donasiapk.R

data class profile (
    val photoUrl : String,
    val name : String,
    val email : String
    )

val sampleProfile = profile(
    photoUrl = "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/small/avatar/dos:d00bcb480039c9a495f3dabe2487e00320230308224934.png",
    name = "Reynhard Powiwi",
    email = "a360dkx4118@bangkit.academy"
)
