package com.example.muzicx.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.muzicx.CustomButton
import com.example.muzicx.R
import com.example.muzicx.model.MyTrack
import com.example.muzicx.ui.theme.MuzicxTheme

@Preview
@Composable
fun PlayScreen(

){

    MuzicxTheme {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)) {
            Column(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(MaterialTheme.colors.secondary)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 50.dp)
                        .height(5.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(MaterialTheme.colors.secondaryVariant)
                )
//                TrackInfoSection(
//                    modifier = Modifier.weight(1f) ,
//                    track = MyTrack() ,
//                    onMoreClicked = {
//
//                    }
//                )
                ControlSection(
                    onPlayingToggle = {

                    },
                    onPreviousClicked = {

                    },
                    onNextClick = {

                    }
                )
                CustomButton(
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .background(Color.Transparent)
                        .border(
                            2.dp,
                            MaterialTheme.colors.secondaryVariant,
                            MaterialTheme.shapes.large
                        )
                        .clickable {

                        }
                        .padding(horizontal = 45.dp, vertical = 20.dp),
                    text = "Add to cart",
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 2.sp
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "add",
                            tint = MaterialTheme.colors.onBackground,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                )

            }
        }
    }
}

@Composable
fun TrackInfoSection(
    modifier: Modifier = Modifier,
    track: MyTrack,
    onMoreClicked: ()-> Unit
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painter = rememberImagePainter(
                data = track.cover,
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(15.dp))
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = track.title,
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = track.artist.name,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.secondaryVariant
                    )
                }
                Row(
                    modifier = Modifier.background(Color.Transparent),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    var favorited by remember{
                        mutableStateOf(false)
                    }
                    Icon(
                        painter = painterResource(
                            if(favorited) R.drawable.ic_favorite
                            else R.drawable.ic_favorite_empty
                        ),
                        contentDescription = "favorite",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .padding(5.dp)
                            .clickable {
                                favorited = !favorited
                            },
                        tint = MaterialTheme.colors.secondaryVariant
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more),
                        contentDescription = "more",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .padding(5.dp)
                            .clickable {
                                onMoreClicked()
                            }
                            .border(2.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
                            .padding(3.dp),
                        tint = MaterialTheme.colors.secondaryVariant
                    )

                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(CircleShape),
                    backgroundColor = MaterialTheme.colors.secondaryVariant,
                    color = MaterialTheme.colors.onBackground,
                    progress = 0.33f
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = longToTimeConverter(60),
                        style = TextStyle(
                            color = MaterialTheme.colors.secondaryVariant,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium),
                    )
                    Text(
                        text = longToTimeConverter(track.duration),
                        style = TextStyle(
                            color = MaterialTheme.colors.secondaryVariant,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium),
                    )

                }
            }
        }
    }
}

fun longToTimeConverter(length: Int): String {
    var time = ""
    val hours = length / (60*60)
    var reminder = length % (60*60)
    val minutes = reminder / 60
    reminder %= 60
    val seconds = reminder
    if (hours > 0) time +="$hours:"
    time +=if(minutes > 0){
                if(minutes < 10) "0$minutes:"
                else "$minutes:"
            } else "00:"
    time +=if(seconds > 0){
                if(seconds < 10) "0$seconds:"
                else "$seconds"
            } else "00"

    return time
}

@Composable
fun ControlSection(
    modifier: Modifier = Modifier,
    onPlayingToggle: (isPlaying: Boolean) -> Unit,
    onPreviousClicked: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 25.dp)
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        var isPlaying by remember {
            mutableStateOf(true)
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_save),
            tint = MaterialTheme.colors.secondaryVariant,
            contentDescription = "save",
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .padding(5.dp)
                .clip(CircleShape)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_previous),
            tint = MaterialTheme.colors.onBackground,
            contentDescription = "previous",
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .padding(5.dp)
                .clickable {
                    onPreviousClicked()
                }
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .clickable {
                    isPlaying = !isPlaying
                }
                .background(MaterialTheme.colors.onBackground)
        ){
            Icon(
                painter = painterResource(
                    if (isPlaying) {
                        R.drawable.ic_pause
                    } else {
                        R.drawable.ic_play
                    }
                ),
                tint = MaterialTheme.colors.background,
                contentDescription = "toggle",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        onPlayingToggle(isPlaying)
                    }
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_next),
            tint = MaterialTheme.colors.onBackground,
            contentDescription = "next",
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .padding(5.dp)
                .clickable {
                    onNextClick()
                }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_lyrics),
            tint = MaterialTheme.colors.secondaryVariant,
            contentDescription = "lyrics",
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .padding(5.dp)
                .clip(CircleShape)
        )
    }
}