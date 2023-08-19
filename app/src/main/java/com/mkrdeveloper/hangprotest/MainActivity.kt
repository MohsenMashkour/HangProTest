package com.mkrdeveloper.hangprotest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mkrdeveloper.hangprotest.ui.theme.HangProTestTheme

class MainActivity : ComponentActivity() {

    // private lateinit var words : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // words = resources.getStringArray(R.array.words)
            HangProTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val modifier: Modifier = Modifier
                    MainScreen(modifier)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    /*var currentWord by remember { mutableStateOf("") }
    var currentWordState by remember { mutableStateOf("") }
    var incorrectGuesses by remember { mutableStateOf(0) }
    var isGameFinished by remember { mutableStateOf(false) }*/


    val context = LocalContext.current
    val wordList = context.resources.getStringArray(R.array.words)
    val game = remember { HangmanGame(wordList, context) }
    game.startNewGame()

    var guessText by remember { mutableStateOf("") }

    val gameState = game.getCurrentGameState()
    val wordState = gameState?.currentWordState?.joinToString(" ") ?: ""
    val incorrectGuesses = gameState?.incorrectGuesses ?: 0

    val x by remember {
        mutableStateOf(0)
    }
    

    val imageList = listOf(
        R.drawable.hangman0,
        R.drawable.hangman1,
        R.drawable.hangman2,
        R.drawable.hangman3,
        R.drawable.hangman4,
        R.drawable.hangman5,
        R.drawable.hangman6,
        R.drawable.hangman7,
        R.drawable.hangman8,
        R.drawable.hangman9,
        R.drawable.hangman10
    )
    Scaffold(
        bottomBar = {
            BottomAppBar {
                Box(
                    modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    FloatingActionButton(onClick = {
                        /// val randomNumber = Random.nextInt(0, wordList.size)

                        game.startNewGame()

                        /*  Toast
                              .makeText(context, wordList[randomNumber], Toast.LENGTH_SHORT)
                              .show()*/
                    }) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "play icon"
                        )
                    }
                }
            }
        }
    ) { contentPadding ->
        Column(
            modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageList[incorrectGuesses]),
                contentDescription = "hanger"
            )

            Text(text = "Hangman Game", fontSize = 30.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Word: $wordState")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Incorrect guesses: $incorrectGuesses")
            Text(text = "x: $x")
            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Adaptive(48.dp),
                contentPadding = PaddingValues(5.dp)
            )
            {
                val keyboardList = listOf(
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                    "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
                )

                items(keyboardList.size) { index ->
                    Box(
                        modifier
                            .padding(3.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color.LightGray)
                            .clickable {
                                guessText = keyboardList[index].lowercase()
                                game.makeGuess(guessText[0])
                                x

                                /* Toast
                                     .makeText(context, wordState, Toast.LENGTH_SHORT)
                                     .show()*/

                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = keyboardList[index])
                    }
                }
            }
        }
    }
}



