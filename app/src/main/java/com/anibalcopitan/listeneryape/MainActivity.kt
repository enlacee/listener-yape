package com.anibalcopitan.listeneryape

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anibalcopitan.listeneryape.ui.theme.ListenerYapeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListenerYapeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TheTitle()
                }
            }
        }
    }
}

@Composable
fun TheTitle(modifier: Modifier = Modifier) {
    Column(
        modifier=Modifier
//            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 24.sp,
//            modifier = Modifier.align(Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(30.dp))
        EditURLfield()
        Spacer(modifier = Modifier.height(8.dp))
        ButtonSaveData()
        DevelopedByText()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditURLfield(){
    var text by remember { mutableStateOf("") }
    Text("Ingresa la URL", style = MaterialTheme.typography.labelSmall)
    TextField(
        value = text,
        onValueChange = {text = it},
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun ButtonSaveData(){
    Button(
        onClick = { /* Aquí colocas la lógica para guardar la URL */ },
    ) {
        Text("Guardar")
    }
}

@Composable
fun DevelopedByText() {
//    Text(
//        text = "Developed by escribesoft",
//        color = Color.Gray,
//        fontSize = 12.sp,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .wrapContentSize(Alignment.BottomCenter)
//    )
    HyperlinkText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentSize(Alignment.BottomCenter),
        fullText = "Developed by Escribesoft",
        linkText = listOf("Escribesoft"),
        hyperlinks = listOf("https://escribesoft.blogspot.com"),
        fontSize = MaterialTheme.typography.labelSmall.fontSize
    )

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListenerYapeTheme {
        TheTitle()
    }
}

@Composable
fun HyperlinkText(
    modifier: Modifier = Modifier,
    fullText: String,
    linkText: List<String>,
    linkTextColor: Color = Color.Blue,
    linkTextFontWeight: FontWeight = FontWeight.Medium,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
    hyperlinks: List<String> = listOf("https://stevdza-san.com"),
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        linkText.forEachIndexed { index, link ->
            val startIndex = fullText.indexOf(link)
            val endIndex = startIndex + link.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = hyperlinks[index],
                start = startIndex,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize
            ),
            start = 0,
            end = fullText.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}