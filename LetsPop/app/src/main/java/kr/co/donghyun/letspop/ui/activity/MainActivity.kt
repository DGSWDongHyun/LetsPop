package kr.co.donghyun.letspop.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import kr.co.donghyun.letspop.data.PopularData
import kr.co.donghyun.letspop.ui.theme.LetsPopTheme
import kr.co.donghyun.letspop.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LetsPopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainPage(viewModel.popularData.observeAsState().value)
                }
            }
        }
    }
}

@Composable
fun MainPage(popularData: PopularData?) {
    val context = LocalContext.current
    Column {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable {
                    if(popularData != null) {
                        context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(popularData.link)
                            `package` = "com.google.android.youtube"
                        })
                    }
                }
        ) {
            Column(
                modifier = Modifier
                    .height(200.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = if(popularData != null) "\uD83D\uDD25 ${popularData.name}" else "Loading..")
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    LetsPopTheme {
//        Greeting("Android")
//    }
//}