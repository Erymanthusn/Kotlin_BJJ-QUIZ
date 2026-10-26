
package com.aulasandroid.quiz.screens

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.aulasandroid.quiz.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aulasandroid.quiz.viewmodel.QuizViewModel

private val Roxo = Color(0xFF4C288C)
private val Rosa = Color(0xFFF47BC8)
private val Amarelo = Color(0xFFFFDF32)
private val Verde = Color(0xFF70D6A7)
private val Vermelho = Color(0xFFFF9999)
private val Preto = Color(0xFF171717)
private val Branco = Color.White

@Composable
fun QuizLogo() {
    Image(
        painter = painterResource(id = R.drawable.judo),
        contentDescription = "Logo do Quizatron 3000",
        modifier = Modifier.size(200.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun QuizButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Amarelo,
            contentColor = Preto,
            disabledContainerColor = Color(0xFFE4DCA6),
            disabledContentColor = Color.DarkGray
        )
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HomeScreen(
    onStartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Preto)
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        QuizLogo()

        Spacer(modifier = Modifier.height(90.dp))

        Text(
            text = "BJJ QUIZ",
            fontSize = 29.sp,
            fontWeight = FontWeight.Medium,
            color = Branco,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(45.dp))

        QuizButton(
            text = "COMEÇAR!",
            onClick = onStartClick
        )
    }
}
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onFinish: () -> Unit
) {
    val question = viewModel.perguntaAtual

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Preto)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuizLogo()

        Spacer(modifier = Modifier.height(24.dp))

        // Mostra em qual pergunta o jogador está.
        Card(
            modifier = Modifier.fillMaxWidth(0.85f),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Verde
            )
        ) {
            Text(
                text = "Pergunta ${viewModel.questaoAtual + 1} " +
                        "de ${viewModel.perguntasTotais}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                fontSize = 20.sp,
                color = Preto,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Branco
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = question.text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    fontSize = 21.sp,
                    color = Preto,
                    textAlign = TextAlign.Center
                )

                question.options.forEach { option ->

                    val isSelected =
                        viewModel.respostaSelect == option

                    val optionColor = when {
                        isSelected &&
                                option == question.correctAnswer -> Verde

                        isSelected -> Vermelho

                        else -> Branco
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                enabled =
                                    viewModel.respostaSelect == null
                            ) {
                                viewModel.answerQuestion(option)
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = optionColor
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color.LightGray
                        )
                    ) {
                        Text(
                            text = option,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            fontSize = 18.sp,
                            color = Preto
                        )
                    }
                }

                if (viewModel.respostaSelect != null) {
                    Text(
                        text = if (
                            viewModel.respostaSelect ==
                            question.correctAnswer
                        ) {
                            "Resposta correta!"
                        } else {
                            "Resposta correta: ${question.correctAnswer}"
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Preto
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                QuizButton(
                    text = if (
                        viewModel.questaoAtual ==
                        viewModel.perguntasTotais - 1
                    ) {
                        "VER RESULTADO"
                    } else {
                        "PRÓXIMA PERGUNTA"
                    },
                    enabled = viewModel.respostaSelect != null,
                    onClick = {
                        viewModel.nextQuestion()

                        if (viewModel.quizFinalizado) {
                            onFinish()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ResultScreen(
    viewModel: QuizViewModel,
    onRestart: () -> Unit,
    onHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Preto)
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        QuizLogo()

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "QUIZ FINALIZADO!",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = Preto,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Branco
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sua pontuação foi:",
                    fontSize = 21.sp,
                    color = Preto
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "${viewModel.score} / " +
                            "${viewModel.perguntasTotais}",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = Preto
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = when (viewModel.score) {
                        viewModel.perguntasTotais ->
                            "Parabéns! Você possui bastante tecnica!"

                        0 ->
                            "Vai treinar e paga 10 ai faixa branca!"

                        else ->
                            "Quase lá! Vai treinar!"
                    },
                    fontSize = 17.sp,
                    color = Preto,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        QuizButton(
            text = "JOGAR NOVAMENTE",
            onClick = onRestart
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Verde,
                contentColor = Preto
            )
        ) {
            Text(
                text = "VOLTAR AO INÍCIO",
                fontSize = 16.sp
            )
        }
    }
}
