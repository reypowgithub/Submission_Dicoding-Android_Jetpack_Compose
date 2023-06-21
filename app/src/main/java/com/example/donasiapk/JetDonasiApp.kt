package com.example.donasiapk

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.donasiapk.data.DonasiRepository
import com.example.donasiapk.model.donasi
import com.example.donasiapk.model.donasiData
import com.example.donasiapk.ui.theme.DonasiApkTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JetDonasiApp (
    modifier : Modifier = Modifier,
    viewModel : JetDonasiViewModel = viewModel(factory = ViewModelFactory(DonasiRepository())),
    navigateToDetail : (String) -> Unit
) {
    val groupedDonasi by viewModel.groupedDonasi.collectAsState()
    val query by viewModel.query


    Box(modifier = modifier){
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton : Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item{
                SearchBar(
                    query = query ,
                    onQueryChange = viewModel::search,
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary))
            }
            groupedDonasi.forEach { (initial, donation) ->
                items(donation, key = {it.id}){ donasi ->
                    DonasiListItem(
                        donasi = donasi,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(tween(durationMillis = 100)),
                        navigateToDetail = navigateToDetail
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}

@Composable
fun DonasiListItem(
    donasi : donasi,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
){
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                navigateToDetail(donasi.id)
            }
    ){
        Row {
            AsyncImage(
                model = donasi.photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .width(170.dp)
                    .clip(shape = RoundedCornerShape(8.dp)),
            )
            Column (
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = donasi.nama,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.target),
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = donasi.harga,
                    fontWeight = FontWeight.Light,
                )
                Text(
                    text = stringResource(id = R.string.waktu),
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = donasi.sisa_hari,
                    fontWeight = FontWeight.Light,
                )
            }

        }
    }
}

@Composable
fun ScrollToTopButton(
    onClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(elevation = 10.dp, shape = CircleShape)
            .clip(shape = CircleShape)
            .size(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ){
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query : String,
    onQueryChange : (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        value = query ,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search ,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(stringResource(R.string.search_donasi))
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(16.dp))

    )
}
@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    DonasiApkTheme {
        val donasi = donasiData.donation.first() // Get the first donasi item as a sample
        DonasiListItem(donasi = donasi, navigateToDetail = {})
    }
}
