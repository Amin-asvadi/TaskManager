package com.saba.design_system.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun CustomText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color =  MaterialTheme.colorScheme.surfaceContainerHigh,
    fontSize: Int = 12,
    textDecoration: TextDecoration? = null,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    lineHeight : TextUnit = TextUnit.Unspecified,
    style: TextStyle = TextStyle(
        textDirection = TextDirection.Content,
        fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
    ),
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        textDecoration =textDecoration ,
        textAlign = textAlign,
        fontSize = fontSize.sp,
        fontWeight = fontWeight,
        lineHeight = lineHeight,
        maxLines = maxLines,
        style = style,
        minLines = minLines,
        overflow = overflow,
    )


}

@Composable
fun CustomText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color =  MaterialTheme.colorScheme.surfaceContainerHigh,
    fontSize: Int = 12,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    textDecoration: TextDecoration? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    lineHeight : TextUnit = TextUnit.Unspecified,
    style: TextStyle = TextStyle(
        textDirection = TextDirection.Content,
        fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
    ),
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        textAlign = textAlign,
        fontSize = fontSize.sp,
        fontWeight = fontWeight,
        maxLines = maxLines,
        textDecoration=textDecoration,
        style = style,
        minLines = minLines,
        overflow = overflow,
        lineHeight =lineHeight
    )


}