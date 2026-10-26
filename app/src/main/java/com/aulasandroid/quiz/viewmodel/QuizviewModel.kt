
package com.aulasandroid.quiz.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aulasandroid.quiz.model.Question

class QuizViewModel : ViewModel() {

    // Perguntas e alternativas do jogo.
    private val questions = listOf(
        Question(
            text = "Quem tem o maior número de títulos mundiais pela IBJJF?",
            options = listOf(
                "Mica Galvão",
                "Marcus Buchecha",
                "Leandro Lo",
                "Paulo Miyao"
            ),
            correctAnswer = "Marcus Buchecha"
        ),
        Question(
            text = "Qual desses golpes não é permitido no jiu-jitsu?",
            options = listOf(
                "Botinha",
                "Hell Hook",
                "Bate estaca",
                "Arco e Flecha"
            ),
            correctAnswer = "Bate estaca"
        ),
        Question(
            text = "Se em uma luta, você fizesse os seguintes golpes: raspar, passar a guarda, joelho na barriga, montada e pegar as costas. Quanto estaria o placar?",
            options = listOf(
                "17 a 0",
                "13 a 0",
                "15 a 0",
                "20 a 0"
            ),
            correctAnswer = "15 a 0"
        )
    )
    var questaoAtual by mutableStateOf(0)
        private set

    var score by mutableStateOf(0)
        private set

    var respostaSelect by mutableStateOf<String?>(null)
        private set

    var quizFinalizado by mutableStateOf(false)
        private set

    val perguntaAtual: Question
        get() = questions[questaoAtual]

    val perguntasTotais: Int
        get() = questions.size

    fun answerQuestion(answer: String) {
        if (respostaSelect != null || quizFinalizado) return

        respostaSelect = answer

        if (answer == perguntaAtual.correctAnswer) {
            score++
        }
    }

    fun nextQuestion() {
        if (respostaSelect == null || quizFinalizado) return

        if (questaoAtual < questions.lastIndex) {
            questaoAtual++
            respostaSelect = null
        } else {
            quizFinalizado = true
        }
    }
    fun restartQuiz() {
        questaoAtual = 0
        score = 0
        respostaSelect = null
        quizFinalizado = false
    }
}
