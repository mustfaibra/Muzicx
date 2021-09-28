package com.example.muzicx.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.muzicx.viewmodel.NavigatorPageVM
import com.example.muzicx.R
import com.example.muzicx.TabItem
import com.example.muzicx.model.MyGenre
import com.example.muzicx.sealed.Screen

val tabs = listOf("Commercial","Free license")

@ExperimentalFoundationApi
@Composable
fun NavigatorPage(
    navController: NavHostController,
    navigatorVM: NavigatorPageVM = hiltViewModel(),
){

    val genres by navigatorVM.getGenres().let {
        navigatorVM.genres.observeAsState()
    }
    val loading by remember {
        navigatorVM.loading
    }
    val error by remember {
        navigatorVM.error
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent)
        .padding(20.dp)
    ) {
        var current by remember {
            mutableStateOf(0)
        }
        var state by remember {
            mutableStateOf("")
        }

        Text(
            text = "Find the best music\nfor your banger",
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.onBackground,
            fontWeight =FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.secondary,
                    shape = MaterialTheme.shapes.large
                )
                .padding(horizontal = 12.dp, vertical = 15.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search",
                    Modifier.size(20.dp),
                    tint = MaterialTheme.colors.onBackground
                )
            },
            paddingLeadingIconEnd = 10.dp,
            paddingTrailingIconStart = 10.dp,
            state = state,
            onValueChanged = {
                state = it
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ){
            itemsIndexed(tabs){ index,title->
                TabItem(
                    title = title,
                    current = index == current,
                    onTabClicked = {
                        if (index != current){
                            current = index
                        }
                    },
                    modifier = Modifier.weight(1f),
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
        ) {
            if (genres == null) {
                if (loading){
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
                // now we are sure that its not null , we gonna use !!
                GenresGrid(genres!!,navController)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun GenresGrid(genres: List<MyGenre>, navController: NavHostController) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(bottom = 60.dp)
    ){
        items(genres){genre->
            GenreItem(
                genre = genre,
                modifier = Modifier.fillMaxWidth(0.5f),
                onGenreClicked = {
                    val genreName = genre.name.replace("/"," & ",true)
                    navController.navigate("${Screen.GenreDetails.route}/${genre.id}/$genreName")
                }
            )
        }
    }
}

@Composable
fun GenreItem(
    modifier: Modifier = Modifier ,
    genre: MyGenre,
    onGenreClicked: ()-> Unit
){
    Box(
        modifier = modifier
            .padding(horizontal = 5.dp, vertical = 15.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .clickable {
                onGenreClicked()
            },
        contentAlignment = Alignment.Center,
    ){
        Image(
            painter = rememberImagePainter(
                data = genre.picture,
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = genre.name,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            contentScale = ContentScale.Fit,
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(CircleShape)
            .background(MaterialTheme.colors.secondary.copy(alpha = 0.5f)))
        Text(
            text = genre.name,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CustomTextField(
    modifier: Modifier = Modifier,
    paddingLeadingIconEnd: Dp = 0.dp,
    paddingTrailingIconStart: Dp = 0.dp,
    leadingIcon: (@Composable() () -> Unit)? = null,
    trailingIcon: (@Composable() () -> Unit)? = null,
    placeHolder: String = "Search",
    state: String,
    onValueChanged: (String)-> Unit
) {

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (leadingIcon != null) {
            leadingIcon()
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = paddingLeadingIconEnd, end = paddingTrailingIconStart)
        ) {
            BasicTextField(
                value = state,
                onValueChange = { onValueChanged(it) },
                modifier = Modifier
                    .background(MaterialTheme.colors.secondary),
                textStyle = TextStyle(color = MaterialTheme.colors.onBackground,fontSize = 15.sp,fontWeight = FontWeight.Medium),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                maxLines = 1,
            )
            if (state.isEmpty()) {
                Text(
                    text = placeHolder,
                    modifier = Modifier.align(Alignment.CenterStart),
                    color = MaterialTheme.colors.secondaryVariant
                )
            }
        }
        if (trailingIcon != null) {
            trailingIcon()
        }
    }
}
