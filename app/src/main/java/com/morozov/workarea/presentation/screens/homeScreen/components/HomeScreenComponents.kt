package com.morozov.workarea.presentation.screens.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.morozov.workarea.R
import com.morozov.workarea.ui.theme.ColorsExtra

@Composable
fun BottomNavigationRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
    list: List<NavigationItem>,
    iconSize: Dp, imageSize: Dp,
    imageUrl: String?
) {

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        items(list) { item ->

            if (item.image != null) {
                HomeNavigationItem(
                    image = item.image,
                    text = item.text,
                    size = iconSize,
                    onClick = { item.onClick() })
            } else {
                CircularShapeWithImage(
                    imageUrl = imageUrl,
                    imageSize = imageSize,
                    onClick = { item.onClick() })
            }
        }
    }
}

data class NavigationItem(
    val image: Int?,
    val onClick: () -> Unit,
    val text: String? = null,
    val iconSize: Dp = 18.dp,
    val shape: @Composable () -> Unit = {}

)

@Composable
fun HomeNavigationItem(
    image: Int,
    onClick: () -> Unit,
    text: String? = null,
    size: Dp = 42.dp
) {
    Row(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        IconButton(
            onClick = { onClick() }

        ) {

            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier.size(size)
            )
        }
        Text(
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            text = text ?: ""
        )
    }
}

@Composable
fun CircularShapeWithImage(imageUrl: String?, imageSize: Dp = 25.dp, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .clip(CircleShape)
            .size(imageSize)
            .background(
                color = Color.White,
                shape = CircleShape
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}


@Composable
fun MainHomeCard(
    listOfIcon: List<NavigationItem>,
    listOfBottomItems: List<NavigationItem>,
    showButtonInMainCard: Boolean,
    showMiddleBoxText: Boolean,
    imageUrl: String?,
    imageUrlForCard: String?,
    titleTextForBottomText: String,
    textForTypeBoxText: String,
    middleTextBoxTitle: String,
    middleTextBoxDescription: String,
    descriptionTextForBottomText: String,
    onButtonClick: () -> Unit,
    onMenuClick: () -> Unit,
    buttonText: String
) {

    val gradient = remember {
        Brush.verticalGradient(
            0.60f to Color.Transparent,
            0.87f to Color.Black
        )
    }

    Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(12.dp),
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Image(
                painter = rememberAsyncImagePainter(imageUrlForCard),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().scale(1f),
                contentScale = ContentScale.Fit
            )
            Box(
                modifier = Modifier
                    .background(brush = gradient)
                    .fillMaxSize()
            ) {

                Column() {
                    Row(
                        modifier = Modifier
                            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TypeOfContentTextBox(textForTypeBoxText)
                        Image(
                            painter = painterResource(id = R.drawable.ion_ellipsis_horizontal),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onMenuClick() }
                        )
                    }

                    if (showMiddleBoxText) {
                        MiddleTextBox(middleTextBoxTitle, middleTextBoxDescription)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {


                    }
                    Row(
                        modifier = Modifier,
                    ) {

                        BottomNavigationRow(
                            list = listOfIcon,
                            iconSize = 18.dp,
                            imageSize = 25.dp,
                            imageUrl = null
                        )

                    }
                    if (showButtonInMainCard) {
                        BaseButton(
                            modifier = Modifier,
                            onButtonClick = {onButtonClick()},
                            buttonText = buttonText
                        )
                    } else {
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(start = 12.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BottomNavigationRow(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.size(43.dp),
                            list = listOfBottomItems,
                            iconSize = 42.dp,
                            imageUrl = imageUrl,
                            imageSize = 42.dp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(
                            modifier = Modifier.wrapContentHeight(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy((-12).dp)
                        ) {


                            TextButton(
                                modifier = Modifier,
                                onClick = { }) {
                                Text(
                                    text = titleTextForBottomText,
                                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),

                                    )
                            }
                            Text(
                                modifier = Modifier.padding(start = 12.dp, bottom = 16.dp),
                                text = descriptionTextForBottomText,
                                style = MaterialTheme.typography.bodySmall.copy(color = ColorsExtra.grey_72),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MiddleTextBox(title: String, description: String) {
    Column(
        modifier = Modifier.padding(top = 132.dp, start = 24.dp, end = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(Color.White)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            description,
            style = MaterialTheme.typography.bodyMedium.copy(Color.White)
        )

    }

}


@Composable
fun BaseButton(
    onButtonClick: (String) -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean = true,
    containerColor: Color = Color.White,
    disabledContainerColor: Color = Color.Gray,
    textColor: Color = Color.Black,
) {
    Button(
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 12.dp,
            bottomEnd = 12.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor
        ),
        enabled = isButtonEnabled,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        onClick = { onButtonClick(buttonText) }
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 32.sp),
            color = textColor
        )
    }
}

@Composable
fun TypeOfContentTextBox(text: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .wrapContentWidth()
            .height(30.dp)
            .background(
                color = ColorsExtra.colorForTypeBoxText,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            textAlign = TextAlign.Center
        )
    }
}