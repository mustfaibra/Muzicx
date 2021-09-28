package com.example.muzicx

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.muzicx.model.MyTrack
import com.example.muzicx.ui.theme.MuzicxTheme

@Composable
fun SecondaryTopBar(
    title: String = "Page Title",
    onBackClicked: ()-> Unit = {},
    onOptionClicked: ()-> Unit = {}
){
    MuzicxTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 15.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowLeft,
                tint = MaterialTheme.colors.secondaryVariant,
                contentDescription = "back",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .padding(3.dp)
                    .clickable {
                        onBackClicked()
                    }
            )
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = "more",
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .padding(3.dp)
                    .clickable {
                        onOptionClicked()
                    }
                    .border(2.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
                    .padding(3.dp),
                tint = MaterialTheme.colors.secondaryVariant
            )
        }
    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String = "Button",
    textStyle: TextStyle = TextStyle.Default,
    trailingIcon: (@Composable ()->Unit )? = null
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        if (trailingIcon != null) {
            trailingIcon()
        }
        Text(text = text, style = textStyle)
    }
}

@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    title: String = "Commerical",
    current: Boolean = true,
    onTabClicked: ()-> Unit  = {},
    textStyle: TextStyle = MaterialTheme.typography.body1
){
    Column(
        modifier = modifier
            .clip(CircleShape)
            .clickable {
                onTabClicked()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = title,
            style = textStyle,
            color = if(current) MaterialTheme.colors.onBackground else MaterialTheme.colors.secondaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp),
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(4.dp)
                .clip(CircleShape)
                .background(
                    if (current) MaterialTheme.colors.primary
                    else Color.Transparent
                )
        )
    }
}

@Composable
fun TrackItem(
    track: MyTrack,
    isAddedToCart: Boolean = false,
    onMenuClicked: ()-> Unit = {},
    onTrackSelected: ()-> Unit = {},
    onCartClicked: (isAddedToCart: Boolean) -> Unit = {}
){
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
        Image(
            painter = rememberImagePainter(
                data = track.md5Image
            ),
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentDescription = track.titleShort,
            contentScale = ContentScale.Crop
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
                        append("$${listOf(10,15,20,25).shuffled().first()}")
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
        Icon(
            painter = painterResource(id = R.drawable.ic_more),
            modifier = Modifier
                .size(27.dp)
                .border(width = 2.dp, shape = CircleShape, color = MaterialTheme.colors.secondaryVariant)
                .clip(CircleShape)
                .clickable {
                    onMenuClicked()
                }
                .padding(5.dp),
            contentDescription = "menu",
            tint = MaterialTheme.colors.secondaryVariant
        )
    }
}