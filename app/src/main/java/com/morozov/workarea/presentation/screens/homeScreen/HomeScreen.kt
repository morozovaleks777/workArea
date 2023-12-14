package com.morozov.workarea.presentation.screens.homeScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.morozov.workarea.R
import com.morozov.workarea.presentation.components.DWAppBar
import com.morozov.workarea.presentation.navigation.AppScreens
import com.morozov.workarea.presentation.navigation.SharedViewModel
import com.morozov.workarea.presentation.screens.homeScreen.components.BottomNavigationRow
import com.morozov.workarea.presentation.screens.homeScreen.components.CircularShapeWithImage
import com.morozov.workarea.presentation.screens.homeScreen.components.MainHomeCard
import com.morozov.workarea.presentation.screens.homeScreen.components.NavigationItem
import timber.log.Timber

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    sharedViewModel: SharedViewModel,
    showUpdateAppBanner: Boolean,
    isWideScreen: Boolean
) {
    val state = homeViewModel.state.collectAsState()


    val imageUrl =
        "https://s3-alpha-sig.figma.com/img/bb5c/5490/259d27be867059853967a4373509cf8c?Expires=1703462400&Signature=imOCWsGO0sIolXKh3iGbfV4dOr6r57~EH5Bj-kHWersAxXMf8rgEO21pBffSZFoY2M6Hu5fcqHISvVApPupJGpPz7eJbUODkriHHe3GnamCEocNJuMmhAwIrl~d86tyPu5BObv8A43Az~xbPPyb8xObfNZsJJP6D-lQhksP68QIYAaAhilKEmkJd1QUux1jXXz61X74rQIlTe1yWo3aasa7F23D~bRhdSwBQCHYCMyak5gokDjmS-MFhG3FK8SIPuaeD7vtr0ZJrhn-gsFCoiTA6AX9J0MPV9IVktBzlUD34JJEogLFjjdkF3DaKzvHALVmcvKbnU~Wi8KpMBgN0rw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
    val imageUrlForCard = remember {
       mutableStateOf(imageUrl)
    }

    val titleTextForBottomText by remember {
        mutableStateOf("@Michael.Knowles")
    }
    val descriptionTextForBottomText by remember {
        mutableStateOf("Men and the Roman empire")
    }
    val textForTypeBoxText by remember {
        mutableStateOf("Investigative Article")
    }
    var middleTextBoxTitle by remember {
        mutableStateOf("The Destruction Of Statues Is A Proxy In The War On American History")
    }
    val middleTextBoxDescription by remember {
        mutableStateOf("For nearly a century, a statue of confederate general Robert E. Lee stood in Charlottesville, Virginia. In 1997, it was listed on the National Register of Historic Places, which meant that at the time — and this again was in the 90s")
    }
    val showMiddleBoxText by remember {
        mutableStateOf(true)
    }
    val showButtonInMainCard by remember {
        mutableStateOf(true)
    }
    val buttonText by remember {
        mutableStateOf("Read full article")
    }
    val vector1Text by remember {
        mutableStateOf("5.5M")
    }
    val vector2Text by remember {
        mutableStateOf("250K")
    }
    val vector3Text by remember {
        mutableStateOf("64K")
    }
    val vector4Text by remember {
        mutableStateOf("12K")
    }
    LaunchedEffect( state.value.readerPassPosts){
        Log.d("home", "HomeScreen: ${state.value} ")
        if(state.value.readerPassPosts.isNotEmpty()){
imageUrlForCard.value = state.value.readerPassPosts.get(0).image
            middleTextBoxTitle = state.value.readerPassPosts.get(0).title
        }}

    Timber.tag("home").d("HomeScreen2: %s", state.value)
    Scaffold(
        modifier = Modifier.padding(horizontal = 12.dp),
        containerColor = Color.Black,
        bottomBar = {

            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Black.copy(alpha = 0.8f)
            ) {

                val navigationItems = listOf(
                    NavigationItem(image = R.drawable.home, onClick = { }),
                    NavigationItem(image = R.drawable.albums_outline, onClick = {}),
                    NavigationItem(image = R.drawable.film_outline, onClick = {}),
                    NavigationItem(image = null, onClick = {}) {
                        CircularShapeWithImage(imageUrl = imageUrl, onClick = {})

                    },
                )
                BottomNavigationRow(
                    list = navigationItems,
                    iconSize = 42.dp,
                    imageSize = 30.dp,
                    imageUrl = imageUrl
                )
            }
        },
        topBar = {

            DWAppBar(
                title = {
                    Row(
                        modifier = Modifier.height(100.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(R.drawable.logo_small),
                            contentDescription = "logo",
                            modifier = Modifier
                        )

                    }
                },
                search = { Timber.tag("home").d("HomeScreen: search is clicked") },
                showProfile = true,
                navController = navController,
                getNotification = {
                    navController.navigate(AppScreens.SplashScreen.name)
                    Timber.tag("home").d("HomeScreen: getNotification  is clicked")
                }

            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = Color.Transparent)
        ) {
            val navigationItemsForMainCard = listOf(
                NavigationItem(image = R.drawable.vector1, text = vector1Text, onClick = { }),
                NavigationItem(image = R.drawable.vector2, text = vector2Text, onClick = {}),
                NavigationItem(image = R.drawable.group_998, text = vector3Text, onClick = {}),
                NavigationItem(image = R.drawable.vector, text = vector4Text, onClick = {}) {
                    CircularShapeWithImage(imageUrl = null, onClick = {})

                })
            val listOfBottomItems = listOf(
                NavigationItem(image = null, text = null, onClick = {}) {
                    CircularShapeWithImage(imageUrl = null, onClick = {})

                }
            )



            MainHomeCard(
                listOfIcon = navigationItemsForMainCard,
                showButtonInMainCard = showButtonInMainCard,
                showMiddleBoxText = showMiddleBoxText,
                buttonText = buttonText,
                listOfBottomItems = listOfBottomItems,
                imageUrl = imageUrl,
                imageUrlForCard = imageUrlForCard.value,
                titleTextForBottomText = titleTextForBottomText,
                descriptionTextForBottomText = descriptionTextForBottomText,
                textForTypeBoxText = textForTypeBoxText,
                onMenuClick = {},
                onButtonClick = {},
                middleTextBoxTitle = middleTextBoxTitle,
                middleTextBoxDescription = middleTextBoxDescription
            )
        }

    }
}


