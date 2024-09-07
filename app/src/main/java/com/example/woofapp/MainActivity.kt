package com.example.woofapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.woof.ui.theme.WoofTheme
import com.example.woofapp.data.Dog
import com.example.woofapp.data.dogs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WoofTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    WoofApp()
                }
            }
        }
    }
}


@Composable
fun WoofApp() {

    Scaffold(
        topBar = {
            WoofTopBar()
        }
    ) { it ->
        LazyColumn(contentPadding = it) {
            items(dogs.size) {
                DogItem(dogs[it])
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofTopBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(64.dp)
                        .width(50.dp),
                    painter = painterResource(R.drawable.ic_woof_logo),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }

        },
        modifier = modifier
    )
}


@Composable
fun DogItem(
    dog:Dog,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }

    Card(
    modifier = modifier
        .padding(8.dp)
        .clip(
            RoundedCornerShape(
                bottomStart = 35.dp,
                topEnd = 35.dp
            )
        )
    ){
        Column(
            modifier = modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                DogIcon(dog.imageResourceId)
                DogInformation(dog.name, dog.age)
                Spacer(modifier = Modifier.weight(1f))
                DogItemButton(
                    expanded = expanded,
                    onclick = { expanded = !expanded }
                )
            }
            if (expanded) {
                DogHobby(
                    dog.hobbies,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 8.dp,
                        bottom = 16.dp,
                        end = 16.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun DogItemButton(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onclick: () -> Unit
){
    IconButton(
        onClick = onclick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if(expanded){
                              Icons.Filled.KeyboardArrowUp
                          }else{
                              Icons.Filled.KeyboardArrowDown
                          },
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}


@Composable
fun DogIcon(
    @DrawableRes dogIcon:Int,
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(dogIcon),
        contentDescription = null,
        modifier = modifier
            .padding(8.dp)
            .height(64.dp)
            .width(64.dp)
            .clip(RoundedCornerShape(50))
    )
}



@Composable
fun DogInformation(
    @StringRes dogName:Int,
    dogAge:Int,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(8.dp)
    ){
        Text(
            text = stringResource(dogName),
            style = MaterialTheme.typography.displayMedium,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = stringResource(R.string.years_old, dogAge),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun DogHobby(
    @StringRes dogHobby: Int,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = stringResource(dogHobby),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Preview(showBackground = true,showSystemUi = true)
@Composable
fun WoofAppPreview() {
    WoofTheme {
        WoofApp()
    }
}

@Preview(showBackground = true,showSystemUi = true)
@Composable
fun WoofDarkThemePreview(){
    WoofTheme(darkTheme = true) {
        WoofApp()
    }

}