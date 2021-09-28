package com.example.muzicx.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.muzicx.CustomButton
import com.example.muzicx.SecondaryTopBar
import com.example.muzicx.R
import com.example.muzicx.model.Artist
import com.example.muzicx.sealed.Screen
import com.example.muzicx.viewmodel.GenreDetailsVM

@Composable
fun GenreDetailsPage(
    genreId: Int ,
    genreName: String ,
    navController: NavHostController,
    genresVM: GenreDetailsVM = hiltViewModel()
){
    val artists by genresVM.getGenreArtists(genreId).let{
        genresVM.artists.observeAsState()
    }
    val loading by remember {
        genresVM.loading
    }
    val error by remember {
        genresVM.error
    }

    // declaring the genre image url
    val genreImage = "https://api.deezer.com/genre/${genreId}/image"
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
    ) {

        var isFollowed by remember{
            mutableStateOf(false)
        }
        var artistsCount by remember {
            mutableStateOf(0)
        }
        SecondaryTopBar(
            title = "$genreName's artists",
            onBackClicked = {
                navController.popBackStack()
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(
                painter = rememberImagePainter(
                    data = genreImage,
                    builder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = "cover",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = genreName,
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (artistsCount == 0) "" else "$artistsCount artists",

                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body1
                )
            }
        }

        CustomButton(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colors.primary)
                .clickable {
                    isFollowed = !isFollowed
                }
                .padding(horizontal = 30.dp, vertical = 15.dp),
            text = if (isFollowed) "Unfollow" else "Follow",
            textStyle = TextStyle(
                color = MaterialTheme.colors.background,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
        )
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            if (artists == null) {
                if (loading) {
                    // show loading progress indicator
                    CircularProgressIndicator(
                        modifier = Modifier
                            .scale(0.5f)
                            .align(Alignment.Center),
                        color = MaterialTheme.colors.primary
                    )
                } else {
                    // here we will show the error message
                    Text(
                        text = error,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colors.onBackground,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                // update artist count
                artistsCount = artists!!.size
                // our music list
                LazyColumn(
                    state = rememberLazyListState()
                ){
                    items(artists!!){artist->
                        ArtistItem(
                            artist = artist,
                            onArtistSelected = {
                                    navController.navigate(
                                        "${Screen.ArtistDetails.route}/${artist.id}"
                                    )
                            },
                            onOptionsClicked = {

                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArtistItem(
    modifier: Modifier = Modifier,
    artist: Artist,
    onArtistSelected: ()-> Unit ,
    onOptionsClicked: ()-> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .clickable {
                onArtistSelected()
            }
            .padding(horizontal = 20.dp, vertical = 10.dp) ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Image(
            painter = rememberImagePainter(
                data = artist.pictureMedium
            ),
            contentDescription = "profile",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .shadow(elevation = 2.dp, shape = CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = artist.name,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1
            )
            Text(
                text = "${artist.fans} fans",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondaryVariant,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_more),
            modifier = Modifier
                .size(30.dp)
                .border(width = 2.dp, shape = CircleShape, color = MaterialTheme.colors.secondaryVariant)
                .clip(CircleShape)
                .clickable {
                    onOptionsClicked()
                }
                .padding(3.dp),
            contentDescription = "menu",
            tint = MaterialTheme.colors.secondaryVariant
        )
    }
}
