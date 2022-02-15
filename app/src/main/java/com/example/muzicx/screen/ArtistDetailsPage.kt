package com.example.muzicx.screen

import androidx.activity.ComponentActivity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.muzicx.*
import com.example.muzicx.R
import com.example.muzicx.model.*
import com.example.muzicx.sealed.Screen
import com.example.muzicx.viewmodel.ArtistDetailsVM
import com.example.muzicx.viewmodel.ParentViewModel

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
        val loading by remember { detailsVM.loading }
        val error by remember { detailsVM.error }
        val details by detailsVM.details.observeAsState()
        var menuExpanded by remember { mutableStateOf(false) }

        if (loading)
            detailsVM.getArtistDetails(artistId)

        SecondaryTopBar(
            title = "Artist's Profile",
            expanded = menuExpanded,
            options = listOf(
                OptionsMenuItem(1,"Share",R.drawable.ic_share),
                OptionsMenuItem(2,"Like",R.drawable.ic_like),
                OptionsMenuItem(3,"Dislike",R.drawable.ic_dislike),
                OptionsMenuItem(4,"Report",R.drawable.ic_report),
            ),
            onBackClicked = {
                navHostController.popBackStack()
            },
            onOptionClicked = {
                menuExpanded = !menuExpanded
            },
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
    detailsVM: ArtistDetailsVM = hiltViewModel()
) {
    val parentVM: ParentViewModel = viewModel(LocalContext.current as ComponentActivity)
    var itemExpandedMenuIndex by remember { mutableStateOf(-1) }

    LazyColumn(
        state = rememberLazyListState()
    ){
        itemsIndexed(tracks){index,track->
            val isAddedToCart = remember {
                mutableStateOf(detailsVM.cart.value?.contains(track) ?: false)
            }

            TrackItem(
                track = track,
                isAddedToCart = isAddedToCart.value,
                isMenuExpanded = index == itemExpandedMenuIndex,
                onMenuClicked = {
                    itemExpandedMenuIndex = if(itemExpandedMenuIndex == index) -1 else index
                },
                onMenuOptionSelected = {
                    itemExpandedMenuIndex = -1
                },
                onTrackSelected = {
                    // go to track player page ...
                    parentVM.setQueue(tracks = tracks)
                    parentVM.songToPlayIndex = index
                    navController.navigate(Screen.Player.route)
                },
                onCartClicked = {
                    // update cart state ...
                    detailsVM.updateCart(track = track, alreadyAdded = it)
                    isAddedToCart.value = !it
                }
            )
        }
    }
}

@Composable
fun TrackItem(
    track: MyTrack,
    isAddedToCart: Boolean ,
    isMenuExpanded: Boolean ,
    onMenuClicked: ()-> Unit ,
    onMenuOptionSelected: (option: OptionsMenuItem) -> Unit,
    onTrackSelected: ()-> Unit,
    onCartClicked: (isAddedToCart: Boolean) -> Unit
){
    val options = listOf(
        OptionsMenuItem(1,"Share",R.drawable.ic_share),
        OptionsMenuItem(2,"Add to playlist",R.drawable.ic_add_playlist),
        OptionsMenuItem(3,"Like",R.drawable.ic_like),
        OptionsMenuItem(4,"Dislike",R.drawable.ic_dislike),
        OptionsMenuItem(5,"More like this",R.drawable.ic_justify),
    )
    Row(
        modifier = Modifier
            .background(Color.Transparent)
            .clickable {
                onTrackSelected()
            }
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ){
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(track.cover),
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = track.title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
            )
            Text(
                text = buildAnnotatedString {
                    append(track.artist.name)
                    append("   ")
                    withStyle(TextStyle(color = MaterialTheme.colors.onBackground).toSpanStyle()){
                        append("$5")
                    }
                },
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondaryVariant,
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_cart),
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .clickable {
                    onCartClicked(isAddedToCart)
                }
                .padding(3.dp),
            contentDescription = "cart",
            tint = if (isAddedToCart) MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(){
            Icon(
                painter = painterResource(id = R.drawable.ic_more),
                modifier = Modifier
                    .size(27.dp)
                    .border(
                        width = 2.dp,
                        shape = CircleShape,
                        color = MaterialTheme.colors.secondaryVariant
                    )
                    .clip(CircleShape)
                    .clickable {
                        onMenuClicked()
                    }
                    .padding(5.dp),
                contentDescription = "menu",
                tint = MaterialTheme.colors.secondaryVariant
            )
            OptionsMenu(
                expanded = isMenuExpanded,
                options = options,
                onMenuStateChanged = { onMenuClicked() },
                onMenuOptionSelected = { onMenuOptionSelected(it) }
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
                text = artist.name,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground,
            )
            Text(
                text = "Pro Seller".capitalize(Locale("en")),
                modifier= Modifier
                    .padding(top = 7.dp)
                    .border(2.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.small)
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.subtitle2
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
            text = "Join chat",
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

