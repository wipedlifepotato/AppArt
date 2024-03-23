package world.antebeot.appart

import android.graphics.drawable.Icon
import android.media.Image
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import world.antebeot.appart.ui.theme.AppArtTheme

val darkModeEnabled: MutableState<Boolean> = mutableStateOf(false)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppArtTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                    color = if(darkModeEnabled.value) Color.Black else MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    Column (modifier = Modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                        DarkModeSwitch()
                        Art(modifier=Modifier.fillMaxSize())
                    }

                }
            }
        }
    }
}
@Composable
fun Art(modifier: Modifier = Modifier)
{

    var commentField by remember {
        mutableStateOf("")
    }
    /*val arts = mapOf(
        //"art0" to R.drawable.art0,
        //"art1" to R.drawable.art1,
        //"art2" to R.drawable.art2
    )*/
    var comments by remember { mutableStateOf(mutableListOf("", "", "")) }
    val arts = listOf(R.drawable.art0, R.drawable.art1, R.drawable.art2)
    var currentImg by remember {
        mutableStateOf(0)
    }
    val updateCommentField = {
        commentField = comments[currentImg]
    }
    if (currentImg > arts.size || currentImg < 0) currentImg = 0
    val painter = painterResource(id = arts[currentImg]?: arts[0])
    Column(modifier=modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter, "Art")
        CommentEdit(modifier = modifier,value = commentField, valueChange = { commentField = it; comments[currentImg] = it })
        Row {
            Button(onClick = {
                currentImg--;
                if(currentImg < 0) currentImg=0
                updateCommentField()

            }){
                Text("Previous")
            }
            Spacer(modifier = Modifier.padding(start=16.dp, top = 24.dp))
            Button(onClick={
                if(currentImg < arts.size - 1) currentImg++;
                updateCommentField()
            })
            {
                Text("Next")
            }
        }
        Box(modifier = Modifier.background(color = Color(android.graphics.Color.parseColor("#FFAAFF"))) .width(120.dp).height(32.dp)) // Примерное значение высоты )
        {
            Text("About image")
        }
    }
}

@Composable
fun CommentEdit(modifier: Modifier, value: String, valueChange: (String) -> Unit)
{
        TextField(value = value , onValueChange = valueChange, singleLine = false, keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Done
            ), label = {
                Text("Comment")
                Icon(Icons.Default.Create, "Comment")
        }
        )
}
@Composable
fun DarkModeSwitch(modifier:Modifier = Modifier)
{
    Row(modifier = modifier) {
        Text(stringResource(id = R.string.doEnableDarkMode))
        Switch(checked = darkModeEnabled.value, onCheckedChange = { darkModeEnabled.value = it })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppArtTheme {
        Greeting("Android")
    }
}