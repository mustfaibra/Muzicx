package com.example.muzicx.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.muzicx.R
import com.example.muzicx.sealed.Screen
import com.example.muzicx.ui.theme.MuzicxTheme


val bottomNavItems = listOf(
    Screen.Home,
    Screen.Navigator,
    Screen.Chat,
    Screen.Profile
)
val startDestination = Screen.Splash

@ExperimentalFoundationApi
@Composable
fun HolderPage(){
    MuzicxTheme {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
        ) {
            val navHostController = rememberNavController()
            val scaffoldState = rememberScaffoldState()
            var activeIndex by remember { mutableStateOf(0) }
            var shouldShowMainScaffoldComposables by remember {
                mutableStateOf(true)
            }
            shouldShowMainScaffoldComposables = getActiveRoute(navController = navHostController) in bottomNavItems.map { screen->
                screen.route
            }
            Scaffold(
                topBar = {
                    if (shouldShowMainScaffoldComposables) MainHomeTopBar()
                },
                scaffoldState = scaffoldState,
                bottomBar = {
                    if (shouldShowMainScaffoldComposables) {
                        BottomBar(
                            activeIndex = activeIndex,
                            onActiveNavItemChanged = { newActiveIndex ->
                                activeIndex = newActiveIndex
                                navHostController.navigate(bottomNavItems[activeIndex].route) {
                                    // clear our backstack except our home screen
                                    popUpTo(Screen.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop =
                                        true // to keep only instance of this route in our backstack
                                    restoreState = true // restore saved screen's state if exist
                                }
                            }
                        )
                    }
                }
            ) {
                NavHost(
                    navController = navHostController,
                    startDestination = startDestination.route,
                    modifier = Modifier
                        .padding(bottom = if (shouldShowMainScaffoldComposables) 100.dp else 0.dp),
                ){
                    composable(Screen.Splash.route){
                        SplashScreen(controller = navHostController)
                    }
                    composable(Screen.Home.route){
                        HomePage()
                    }
                    composable(Screen.Navigator.route){
                        NavigatorPage(navHostController)
                    }
                    composable(Screen.Chat.route){
                        ChatListPage()
                    }
                    composable(Screen.Profile.route){
                        ProfilePage()
                    }
                    composable(
                        "${Screen.GenreDetails.route}/{genreId}/{genreName}",
                        arguments = listOf(
                            navArgument(name = "genreId"){
                                type = NavType.IntType
                            },
                            navArgument(name = "genreName"){
                                type = NavType.StringType
                            },
                        )
                    ){
                        val genreId = it.arguments?.getInt("genreId") ?: 0
                        val genreName = it.arguments?.getString("genreName") ?: "Genre Name"

                        GenreDetailsPage(
                            genreId = genreId,
                            genreName = genreName,
                            navController = navHostController
                        )
                    }
                    composable(
                        "${Screen.ArtistDetails.route}/{artistId}",
                        arguments = listOf(
                            navArgument("artistId"){
                                type = NavType.IntType
                            }
                        )
                    ){
                        val artistId = it.arguments?.getInt("artistId") ?: 0
                        ArtistDetailsPage(
                            artistId = artistId,
                            navHostController = navHostController
                        )
                    }
                    composable(route = Screen.Player.route){
                        PlayScreen(navController = navHostController)
                    }
                }
            }
        }
    }
}

@Composable
fun MainHomeTopBar(
    onSearchClick: ()-> Unit = {},
    onCartClick: ()-> Unit = {}
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_note),
            contentDescription = "logo",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "search",
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .clickable {
                    onSearchClick()
                }
                .padding(4.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        BadgedIcon(
            count = 4,
            onIconClick = {
                onCartClick()
            }
        )
    }
}

@Composable
fun BottomBar(
    activeIndex: Int,
    onActiveNavItemChanged: (currentItemIndex: Int) -> Unit = {}
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.secondary)
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(color = MaterialTheme.colors.background)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            bottomNavItems.forEachIndexed{ index, screen->
                BottomNavItem(
                    screen = screen ,
                    current = index == activeIndex,
                    onItemSelect = {
                        if (index != activeIndex) {
                            onActiveNavItemChanged(index)
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomNavItem(
    modifier: Modifier = Modifier,
    screen: Screen = startDestination,
    current: Boolean = true,
    onItemSelect: ()-> Unit = {}
){
    Box(
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = if (current) MaterialTheme.colors.primary else Color.Transparent)
            .clickable {
                onItemSelect()
            }
            .padding(5.dp)
    ){
        Image(
            modifier = Modifier.size( if(current) 36.dp else 24.dp ).align(Alignment.Center),
            painter = painterResource(id = screen.icon),
            contentDescription = screen.title,
            colorFilter = ColorFilter.tint(color = if (current) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground)
        )
    }
}

@Composable
fun BadgedIcon(count: Int = 4, onIconClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onIconClick()
            }
            .padding(3.dp)
            .size(30.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_cart),
            contentDescription = "cart",
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterStart)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(18.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
                .padding(2.dp)
        ){
            Text(
                text = "$count",
                fontSize = 12.sp,
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun getActiveRoute(navController: NavHostController) : String?{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}