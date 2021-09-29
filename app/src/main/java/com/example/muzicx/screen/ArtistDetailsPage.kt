package com.example.muzicx.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.muzicx.CustomButton
import com.example.muzicx.R
import com.example.muzicx.SecondaryTopBar
import com.example.muzicx.TabItem
import com.example.muzicx.TrackItem
import com.example.muzicx.model.*
import com.example.muzicx.sealed.Screen
import com.example.muzicx.viewmodel.ArtistDetailsVM
import com.example.muzicx.viewmodel.PlayerScreenVM

@Composable
fun ArtistDetailsPage(
    artistId: Int,
    detailsVM: ArtistDetailsVM = hiltViewModel(),
    navHostController: NavHostController
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ){
        val loading by remember {
            detailsVM.loading
        }
        val error by remember {
            detailsVM.error
        }
        val details by detailsVM.details.observeAsState()

        if (loading) {
            detailsVM.getArtistDetails(artistId)
        }
        SecondaryTopBar(
            title = "Artist's Profile",
            onBackClicked = {
                navHostController.popBackStack()
            },
            onOptionClicked = {

            }
        )
        // Here is the actual content of the page :
        Box(modifier = Modifier.fillMaxSize()) {
            if (details == null) {
                // if its null , it mean that its still loading or network error occured !
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
                // We got our details response from the server , we should use it now !
                DetailsPageContent(details!!,navHostController)
            }
        }
    }
}

@Composable
fun DetailsPageContent(details: ArtistDetails, navHostController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        val artist = details.artist
        val albumsCount = details.albums.data.size
        var tracksCount = 0
        details.albums.data.forEach {
            tracksCount +=it.tracks.data.size
        }
        val subscribersCount = artist.fans

        var isSubscribed by remember {
            mutableStateOf(false)
        }
        var current by remember {
            mutableStateOf(0)
        }

        ProfileHeaderSection(
            artist = artist,
            tracksCount = tracksCount,
            albumsCount = albumsCount,
            subscribersCount = subscribersCount
        )
        ProfileButtonsSection(
            isSubscribed = isSubscribed,
            onSubscribeChange = {
                isSubscribed = !isSubscribed
            }
        )
        TabsSection(
            current = current,
            onTabChanged = { newIndex->
                current = newIndex
            }
        )
        // now show the content depending on the current tab
        TabsContent(current,details,navHostController)
    }
}

@Composable
fun TabsContent(current: Int, details: ArtistDetails,navController: NavHostController) {
    val albums = details.albums.data
    val reviews = details.reviews ?: mutableListOf()
    val contacts = details.contacts ?: mutableListOf()
    when(current){
        0 -> {
            // show all songs
            val tracks = mutableListOf<MyTrack>()
            // Iterate through our songs of all albums
            albums.map { album->
                album.tracks.data.forEach {
                    tracks.add(it)
                }
            }
            SongsTab(tracks = tracks,navController = navController)
        }
        1 -> {
            // show all albums
            AlbumsTab(albums)
        }
        2 -> {
            // show all reviews
            ReviewsTab(reviews)
        }
        3 -> {
            // show all contacts
            ContactsTab(contacts)
        }
    }
}

@Composable
fun ContactsTab(contacts: List<Contact>) {
    if (contacts.isEmpty()){
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "No contacts for this artist !",
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ReviewsTab(reviews: List<Review>) {
    LazyColumn{
        items(reviews){review->
            ReviewItem(review = review )
        }
    }
}

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: Review,
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .clickable {
            }
            .padding(horizontal = 20.dp, vertical = 10.dp) ,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(review.profile)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = review.reviewerName,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = review.review,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondaryVariant,
                maxLines = 1
            )
        }
    }
}

@Composable
fun SongsTab(
    tracks: MutableList<MyTrack>,
    navController: NavHostController,
    playerScreenVM: PlayerScreenVM = hiltViewModel()
) {
    LazyColumn(
        state = rememberLazyListState()
    ){
        items(tracks){track->

            var isAddedToCart by remember {
                mutableStateOf(false)
            }

            TrackItem(
                track = track,
                isAddedToCart = isAddedToCart,
                onMenuClicked = {
                    // show option menu ...
                },
                onTrackSelected = {
                    // go to track player page ...
                    playerScreenVM.setSongToPlay(track = track)
                    navController.navigate(Screen.Player.route)
                },
                onCartClicked = {
                    // update cart state ...
                    isAddedToCart = !it
                }
            )
        }
    }
}

@Composable
fun AlbumsTab(albums: List<MyAlbum>) {
    LazyColumn{
        items(albums){album->
            AlbumItem(
                album = album,
                onAlbumSelected = {
                }
            )
        }
    }
}

@Composable
fun AlbumItem(
    modifier: Modifier = Modifier,
    album: MyAlbum,
    onAlbumSelected: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .clickable {
                onAlbumSelected()
            }
            .padding(horizontal = 20.dp, vertical = 10.dp) ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(album.cover)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = album.title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1
            )
            Text(
                text = "${album.tracks.data.size} tracks",
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
                .border(
                    width = 2.dp,
                    shape = CircleShape,
                    color = MaterialTheme.colors.secondaryVariant
                )
                .clip(CircleShape)
                .clickable {
                }
                .padding(3.dp),
            contentDescription = "menu",
            tint = MaterialTheme.colors.secondaryVariant
        )
    }
}


@Composable
fun ProfileHeaderSection(
    artist: Artist,
    tracksCount: Int,
    albumsCount: Int,
    subscribersCount: Int,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Image(
            painter = rememberImagePainter(
                data = artist.pictureBig,
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = "profile",
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .aspectRatio(1f)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Text(
                text = "Pro Seller".capitalize(Locale("en")),
                modifier= Modifier
                    .border(2.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.small)
                    .padding(horizontal = 15.dp, vertical = 7.dp),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body2
            )
            Text(
                text = artist.name,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(vertical = 5.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                ArtistRecord(title = "Tracks", count = tracksCount )
                ArtistRecord(title = "Albums", count = albumsCount)
                ArtistRecord(title = "Subscribers", count = subscribersCount)
            }
        }
    }
}

@Composable
fun ArtistRecord(title: String, count: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text =
                if (count < 1000) "$count"
                else "${count/1000}K",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.secondaryVariant,
            maxLines = 1
        )
    }
}

@Composable
fun ProfileButtonsSection(
    isSubscribed: Boolean,
    onSubscribeChange: ()-> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ){
        CustomButton(
            modifier = Modifier
                .padding(10.dp)
                .weight(1f)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colors.primary)
                .clickable {
                    onSubscribeChange()
                }
                .padding(horizontal = 30.dp, vertical = 15.dp),
            text = if (isSubscribed) "Subscribed" else "Subscribe",
            textStyle = TextStyle(
                color = MaterialTheme.colors.background,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
        )
        CustomButton(
            modifier = Modifier
                .padding(end = 10.dp)
                .weight(1f)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Transparent)
                .border(
                    2.dp,
                    MaterialTheme.colors.secondaryVariant,
                    MaterialTheme.shapes.medium
                )
                .clickable {

                }
                .padding(horizontal = 30.dp, vertical = 15.dp),
            text = "Start a chat",
            textStyle = TextStyle(
                color = MaterialTheme.colors.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp
            ),
        )
    }
}

@Composable
fun TabsSection(
    current: Int,
    onTabChanged: (current: Int)-> Unit,
){
    val tabs = listOf("Tracks","Albums","Reviews","Contacts")

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = 10.dp,vertical = 20.dp)
    ){
        itemsIndexed(tabs){ index,title->
            TabItem(
                title = title,
                current = index == current,
                onTabClicked = {
                    if (index != current){
                        onTabChanged(index)
                    }
                },
                textStyle = MaterialTheme.typography.body1
            )
        }
    }
}

