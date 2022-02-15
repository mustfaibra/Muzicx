package com.example.muzicx.screen

import androidx.activity.ComponentActivity
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.muzicx.CustomButton
import com.example.muzicx.OptionsMenu
import com.example.muzicx.R
import com.example.muzicx.model.MyTrack
import com.example.muzicx.model.OptionsMenuItem
import com.example.muzicx.viewmodel.ParentViewModel
import com.example.muzicx.viewmodel.PlayerScreenVM

@Composable
fun PlayScreen(
    navController: NavHostController,
    playerVM: PlayerScreenVM = hiltViewModel()
){
    val parentVM: ParentViewModel = viewModel(LocalContext.current as ComponentActivity)
    val tracks by parentVM.tracks.observeAsState()
    playerVM.setTrackQueue(tracks = tracks!!)

    val currentTrackIndex by remember {
        playerVM.currentPlayingIndex
    }

    val track = playerVM.getTrackPlaying(currentTrackIndex)

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {
        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(track.cover.copy(alpha = 0.3f))
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val isPlaying by remember { playerVM.playing }
            var favorited by remember{ mutableStateOf(false) }
            var isAddedToCart by remember { mutableStateOf(false) }
            Spacer(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .height(5.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(track.cover)
            )
            TrackInfoSection(
                modifier = Modifier.weight(1f) ,
                track = track ,
                progress = 0f,
                favorited = favorited,
                onFavoriteClicked = {
                    favorited = !favorited
                }
            )
            ControlSection(
                color = track.cover,
                isPlaying = isPlaying,
                onPlayingToggle = {
                    playerVM.playing.value = !isPlaying
                },
                onPreviousClicked = {
                    isAddedToCart = false
                    playerVM.playPrevious()
                },
                onNextClick = {
                    isAddedToCart = false
                    playerVM.playNext()
                }
            )
            CustomButton(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(track.cover)
                    .clickable { isAddedToCart = !isAddedToCart }
                    .padding(horizontal = 35.dp, vertical = 20.dp),
                text = if(isAddedToCart) "Added to cart" else "Add to cart",
                textStyle = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.background),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "add",
                        tint = MaterialTheme.colors.background,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )

        }
    }
}

@Composable
fun TrackInfoSection(
    modifier: Modifier = Modifier,
    track: MyTrack,
    progress: Float,
    favorited: Boolean,
    onFavoriteClicked: ()-> Unit,
){
    val options = listOf(
        OptionsMenuItem(1,"Share",R.drawable.ic_share),
        OptionsMenuItem(2,"Add to playlist",R.drawable.ic_add_playlist),
        OptionsMenuItem(3,"Like",R.drawable.ic_like),
        OptionsMenuItem(4,"Dislike",R.drawable.ic_dislike),
        OptionsMenuItem(5,"More like this",R.drawable.ic_justify),
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        /** Track cover */
        Box(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(track.cover),
        )
        Spacer(modifier = Modifier.height(15.dp))
        /** Track info */
        Column {
            /** Name , artist , and fav & option icons */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                var isMenuExpanded by remember { mutableStateOf(false) }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = track.title,
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = track.artist.name,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.secondaryVariant
                    )
                }
                Row(
                    modifier = Modifier.background(Color.Transparent),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(
                            if(favorited) R.drawable.ic_favorite
                            else R.drawable.ic_favorite_empty
                        ),
                        contentDescription = "favorite",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable {
                                onFavoriteClicked()
                            }
                            .padding(5.dp),
                        tint = track.cover
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Box{
                        Icon(
                            painter = painterResource(id = R.drawable.ic_more),
                            contentDescription = "more",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .clickable {
                                    isMenuExpanded = !isMenuExpanded
                                },
                            tint = track.cover
                        )
                        OptionsMenu(
                            expanded = isMenuExpanded,
                            options = options,
                            onMenuStateChanged = { isMenuExpanded = !isMenuExpanded },
                            onMenuOptionSelected = { isMenuExpanded = !isMenuExpanded }
                        )
                    }

                }
            }
            /** Progress bar with time indicators */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var currentProgress by remember { mutableStateOf(progress)}
                val time = track.duration
                val animatedProgress by animateFloatAsState(
                    targetValue = currentProgress,
                    animationSpec = tween(
                        durationMillis = time * 1000,
                        easing = LinearEasing
                    )
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(CircleShape),
                    backgroundColor = MaterialTheme.colors.onBackground,
                    color = track.cover,
                    progress = animatedProgress
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
                /** Now we should animate our linear progress bar */
                currentProgress = 1f
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
    color: Color = Color.White,
    isPlaying: Boolean,
    onPlayingToggle: () -> Unit,
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
        Icon(
            painter = painterResource(id = R.drawable.ic_save),
            tint = color.copy(alpha = 0.7f),
            contentDescription = "save",
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .clickable {

                }
                .padding(5.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_previous),
            tint = color,
            contentDescription = "previous",
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .clickable {
                    onPreviousClicked()
                }
                .padding(5.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .clickable {
                    onPlayingToggle()
                }
                .background(color = color)
        ){
            Icon(
                painter = painterResource(
                    if (isPlaying) {
                        R.drawable.ic_pause
                    } else {
                        R.drawable.ic_play
                    }
                ),
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "toggle",
                modifier = Modifier
                    .size(30.dp)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_next),
            tint = color,
            contentDescription = "next",
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .clickable {
                    onNextClick()
                }
                .padding(5.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_lyrics),
            tint = color.copy(alpha = 0.7f),
            contentDescription = "lyrics",
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .clickable {

                }
                .padding(5.dp)
        )
    }
}