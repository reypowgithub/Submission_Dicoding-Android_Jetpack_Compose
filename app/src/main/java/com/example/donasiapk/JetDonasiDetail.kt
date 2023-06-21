package com.example.donasiapk

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.donasiapk.model.donasi
import com.example.donasiapk.model.donasiData
import com.example.donasiapk.ui.theme.DonasiApkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    donasiId: String,
    navigateBack: () -> Unit
) {
    val donasi = donasiData.donation.find { it.id == donasiId }

    if (donasi != null){
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(donasi.company) },
                    navigationIcon = {
                        IconButton(onClick = navigateBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                if (donasi != null) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                    ) {
                        AsyncImage(
                            model = donasi.photoUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .background(Color.LightGray)
                                .clip(shape = RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = donasi.nama, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = donasi.dana_terkumpul)
                        Row{
                            Text(text = "dari total dana")
                            Text(text = donasi.harga)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Nama Perusahaaan", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = donasi.company)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Deskripsi Kegiatan", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = donasi.deskripsi)
                        // Add more content here if needed
                    }
                } else {
                    // Handle case when donasi is null
                }
            }
        }
    } else {

    }
}

@Preview(showBackground = true)
@Composable
fun DonasiDetailPreview() {
    DonasiApkTheme {
        val donasi = donasiData.donation.first() // Get the first donasi item as a sample
        DetailScreen(donasiId = donasi.id, navigateBack = {})
    }
}