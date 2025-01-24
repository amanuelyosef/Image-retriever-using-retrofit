package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                ImageListBox()
            }
        }
    }
}


@Composable
fun ImageListBox(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){

        val imgUrl = remember{ mutableStateListOf<ImageInfo>() }

        LaunchedEffect(Unit) {

            imgUrl.addAll( RetrofitInstance.api
                .getRandomImage().body()?.toMutableList() ?: mutableListOf<ImageInfo>(
                ImageInfo(0,"null",0,0,"null","null")
            ))

        }

        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)) {
            items(imgUrl){
                ImageInfoContent(imageInfo = it)
            }
        }
    }
}

@Composable
fun ImageInfoContent(modifier: Modifier = Modifier,
                     imageInfo:ImageInfo
) {

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model= imageInfo.download_url
            ),
            contentDescription = "sample image",
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
            )

        Text(
            text ="Photographer: ${imageInfo.author}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp,10.dp)
            )
    }
}

@Preview(showBackground = true)
@Composable
fun ImageInfoContentPreview(modifier: Modifier = Modifier) {
    ImageInfoContent(imageInfo = ImageInfo(0,"null",0,0,"null","null"))
}






