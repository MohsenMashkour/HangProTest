package com.mkrdeveloper.hangprotest

import android.content.Context
import android.widget.Toast

data class HangmanGameState(
    val currentWord: String,
    val currentWordState: CharArray,
    var incorrectGuesses: Int
)

class HangmanGame(private val words: Array<String>, private val context: Context) {
    private var gameState: HangmanGameState? = null

    fun startNewGame() {
        val randomWord = words.random()
        Toast.makeText(context, randomWord, Toast.LENGTH_SHORT).show()
        gameState = HangmanGameState(
            currentWord = randomWord,
            currentWordState = CharArray(randomWord.length) { '_' },
            incorrectGuesses = 0
        )
    }

    fun getCurrentGameState(): HangmanGameState? {
        return gameState
    }

    fun makeGuess(guess: Char) {
        val currentGameState = gameState ?: return
        if (!currentGameState.currentWord.contains(guess)) {
            currentGameState.incorrectGuesses++
        } else {
            for (i in currentGameState.currentWord.indices) {
                if (currentGameState.currentWord[i] == guess) {
                    currentGameState.currentWordState[i] = guess
                }
            }
        }
        gameState = currentGameState
       // Toast.makeText(context, "$gameState", Toast.LENGTH_SHORT).show()
    }
}
