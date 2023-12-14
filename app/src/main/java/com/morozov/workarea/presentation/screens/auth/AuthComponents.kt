package com.morozov.workarea.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.morozov.workarea.R
import com.morozov.workarea.presentation.components.BaseOutlinedButton
import com.morozov.workarea.presentation.components.CloseIcon
import com.morozov.workarea.presentation.components.DefaultClickableText
import com.morozov.workarea.presentation.components.rememberKeyboardState
import com.morozov.workarea.ui.theme.libreFranklin


@Composable
fun AuthActions(
    modifier: Modifier = Modifier,
    primaryButtonText: String = "",
    primaryButtonClick: (String) -> Unit = {},
    primaryButtonEnabled: Boolean = true,
    secondaryButtonText: String = "",
    secondaryButtonClick: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val textStyle = TextStyle(
            fontFamily = libreFranklin,
            fontWeight = FontWeight.W600,
            fontSize = 16.sp,
            lineHeight = 20.sp,
        )
        if (primaryButtonText.isNotEmpty()) {
            BaseOutlinedButton(
                onButtonClick =
                { primaryButtonClick(primaryButtonText) },
                buttonText = primaryButtonText,
                isButtonEnabled = primaryButtonEnabled,
                disabledContainerColor = Color.Gray, //ColorsExtra.SolidInactive100,
                radius = 6.dp,
                textStyle = textStyle
            )
        }
        if (secondaryButtonText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            BaseOutlinedButton(
                onButtonClick = { secondaryButtonClick(secondaryButtonText) },
                buttonText = secondaryButtonText,
                containerColor = Color.Transparent,
                textColor = Color.White, //ColorsExtra.SolidLight100,
                textStyle = textStyle,
                borderColor = Color.White //ColorsExtra.SolidLight100
            )
        }
    }
}


@Composable
fun AuthForm(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
    headerTextAlignment: Alignment.Horizontal = Alignment.Start,
    showCloseButton: Boolean = true,
    onExit: (String) -> Unit = {},
    topBarExtra: @Composable() (RowScope.() -> Unit) = {
        if (showCloseButton) {
            CloseIcon(onClosedIconClicked = { buttonName -> onExit(buttonName) })
        }
    },
    showToBarLogo: Boolean = true,
    content: @Composable() (LazyItemScope.() -> Unit),
    buttonsAnchored: Boolean = true,
    primaryButtonText: String = "",
    primaryButtonClick: (String) -> Unit = {},
    primaryButtonEnabled: Boolean = true,
    secondaryButtonText: String = "",
    secondaryButtonClick: (String) -> Unit = {},
    footer: @Composable ColumnScope.() -> Unit = {},
    preActions: @Composable ColumnScope.() -> Unit = {},
) {
    val isKeyboardOpen by      rememberKeyboardState()
    val bottomPadding =
        if (isKeyboardOpen) 24.dp else dimensionResource(id = R.dimen.auth_spacing_bottom)

    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.auth_padding_horizontal))
            .padding(bottom = bottomPadding)
    ) {
        AuthTopBar(additionalContent = topBarExtra, showLogo = showToBarLogo)
        LazyColumn(
            modifier = Modifier.run {
                if (buttonsAnchored) {
                    weight(1f)
                } else {
                    padding(bottom = 24.dp)
                }
            },
            contentPadding = PaddingValues(top = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                if (title.isNotEmpty() || description.isNotEmpty()) {
                    AuthHeader(
                        title = title,
                        description = description,
                        textAlignment = headerTextAlignment
                    )
                }
                content()
            }
            item {
                preActions()
            }
        }
        AuthActions(
            modifier = Modifier.imePadding(),
            primaryButtonText = primaryButtonText,
            primaryButtonClick = { buttonName -> primaryButtonClick(buttonName) },
            primaryButtonEnabled = primaryButtonEnabled,
            secondaryButtonText = secondaryButtonText,
            secondaryButtonClick = { buttonName -> secondaryButtonClick(buttonName) },
        )
        if (!isKeyboardOpen) {
            footer()
        }
    }
}

@Composable
fun AuthTopBar(
    modifier: Modifier = Modifier,
    logoColor: Color = Color.White,
    dividerColor: Color = Color.White.copy(alpha = 0.2f), //ColorsExtra.TransparentLight20,
    showLogo: Boolean = false,
    title: String = "",
    titleStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    additionalContent: @Composable RowScope.() -> Unit,
) {
    var textStyle by remember { mutableStateOf(titleStyle) }
    var readyToDraw by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            if (title.isNotEmpty() && !showLogo) {
                Text(
                    text = title,
                    style = textStyle,
                    color = Color.White,  //ColorsExtra.SolidLight100,
                    modifier = Modifier
                        .weight(1f)
                        .width(0.dp)
                        .padding(end = 8.dp)
                        .drawWithContent { if (readyToDraw) drawContent() },
                    onTextLayout = { textLayoutResult ->
                        if (textLayoutResult.lineCount > 1) {
                            textStyle = titleStyle.copy(
                                fontSize = textStyle.fontSize.times(0.9f)
                            )
                        } else {
                            readyToDraw = true
                        }
                    }
                )
            }
            if (showLogo) {
             //   Image(
//                    painter = painterResource(R.drawable.bentkey_logo),
//                    contentDescription = "logo",
//                    modifier = Modifier
//                        .width(93.dp)
//                        .height(24.dp),
//                    colorFilter = ColorFilter.tint(logoColor)
//                )
//                Spacer(modifier = Modifier.weight(1f))
            }

            additionalContent()
        }
//        HorizontalDivider(
//            thickness = Dp.Hairline,
//            color = dividerColor,
//        )
    }
}

@Composable
fun ClickableLink(
    linkText: String,
    onLinkClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    text: String = "",
    tag: String = "link",
) {
    DefaultClickableText(
        modifier = modifier,
        text = text,
        clickableTexts = listOf(linkText to tag),
        onTextClick = {
            if (it == tag) {
                onLinkClicked(it)
            }
        }
    )
}

@Composable
fun AuthHeader(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
    textAlignment: Alignment.Horizontal = Alignment.Start,
) {
    Column(
        modifier = modifier
            .padding(bottom = 32.dp)
            .fillMaxWidth(),
        horizontalAlignment = textAlignment
    ) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
              //  color = ColorsExtra.SolidLight100,
                modifier = Modifier
                    .align(Alignment.Start)
            )
        }
        if (description.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
          //      color = ColorsExtra.TransparentLight50,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .align(Alignment.Start)
            )
        }
    }
}










