package com.example.muzicx.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muzicx.R
import com.example.muzicx.model.FakeArtist
import com.example.muzicx.model.FakeTrack
import com.example.muzicx.ui.theme.MuzicxTheme

val songs = listOf(
    FakeTrack(name = "Shape Of You", artist = FakeArtist(name ="Ed Sheeran", profile = R.drawable.id_sheeran), cover = R.drawable.shape_of_you),
    FakeTrack(name = "Wrecked", artist = FakeArtist(name ="Imagine Dragons", profile = R.drawable.imagine_dragons), cover = R.drawable.wrecked),
    FakeTrack(name = "Perfect", artist = FakeArtist(name ="Ed Sheeran", profile = R.drawable.id_sheeran), cover = R.drawable.perfect),
    FakeTrack(name = "1,2,3", artist = FakeArtist(name ="Benny Blanco", profile = R.drawable.benny_blanco), cover = R.drawable.despacito),
    FakeTrack(name = "sugar", artist = FakeArtist(name ="Maroon 5", profile = R.drawable.maroon_5), cover = R.drawable.sugar),
    FakeTrack(name = "One More Night", artist = FakeArtist(name ="Maroon 5", profile = R.drawable.maroon_5), cover = R.drawable.one_more_night)
)

val artists = listOf(
    FakeArtist(name = "Ed Sheeran", profile = R.drawable.id_sheeran),
    FakeArtist(name = "Imagine Dragons", profile = R.drawable.imagine_dragons),
    FakeArtist(name = "Benny Blanco", profile = R.drawable.benny_blanco),
    FakeArtist(name = "Maroon 5", profile = R.drawable.maroon_5),
    FakeArtist(name = "Taylor Swift", profile = R.drawable.taylor_swift),
    FakeArtist(name = "Justin Bieber", profile = R.drawable.justin_bieber),
    FakeArtist(name = "The Weeknd", profile = R.drawable.the_weeknd),
)

@Preview
@Composable
fun HomePage(){
    MuzicxTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .verticalScroll(
                    state = rememberScrollState()
                ),
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Explore",
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "Welcome to Muzicx\nBad mode? say no more .",
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
            )
            Spacer(modifier = Modifier.height(40.dp))
            ArtistStoriesSection(artists = artists)
            Spacer(modifier = Modifier.height(20.dp))
            TracksSection(title = "Latest Song", tracks = songs)
            Spacer(modifier = Modifier.height(25.dp))
            ArtistsOfMonthSection(artists = artists)
            Spacer(modifier = Modifier.height(25.dp))
            TracksSection(title = "Popular Songs", tracks = songs)
            Spacer(modifier = Modifier.height(20.dp))
            TracksSection(title = "Just For you", tracks = songs)
        }
    }
}

@Composable
fun ArtistsOfMonthSection(artists: List<FakeArtist>) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 10.dp))
            .background(MaterialTheme.colors.secondary)
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Month's Artists",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onBackground,
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "The top artists of this month is here , don't forget to check them out !",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(25.dp))
        LazyRow{
            items(artists){ artist ->
                Image(
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(CircleShape)
                        .size(32.dp),
                    painter = painterResource(id = artist.profile),
                    contentDescription = artist.name,
                    contentScale = ContentScale.Crop
                )
            }
        }

    }
}

@Composable
fun ArtistStoriesSection(artists: List<FakeArtist>) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        contentPadding = PaddingValues(start = 20.dp)
    ){
        items(artists){ artist ->
            ArtistStoryItem(artist)
        }
    }
}

@Composable
fun ArtistStoryItem(artist: FakeArtist) {
    Column(
        modifier = Modifier
            .padding(end = 7.5.dp)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.secondary)
            .padding(horizontal = 7.5.dp, vertical = 10.dp)
            .width(64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
                .padding(3.dp)
                .size(64.dp)
                .clip(CircleShape),
            painter = painterResource(id = artist.profile),
            contentDescription = artist.name,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = artist.name,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }
}

@Composable
fun TracksSection(title: String,tracks: List<FakeTrack>) {
    Text(
        modifier = Modifier.padding(start = 20.dp),
        text = title,
        style = MaterialTheme.typography.h3,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colors.onBackground,
    )
    Divider(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 15.dp)
            .clip(CircleShape)
            .width(20.dp),
        thickness = 5.dp
    )
    LazyRow(contentPadding = PaddingValues(start = 20.dp)){
        items(tracks){ track ->
            HomeSongItem(track = track)
        }
    }
}

@Composable
fun HomeSongItem(track: FakeTrack) {
    Column(
        modifier = Modifier
            .clickable {  }
            .padding(10.dp)
            .width(100.dp)
            .padding(5.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = track.cover),
            contentDescription = "image",
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = track.name,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = track.artist.name,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
