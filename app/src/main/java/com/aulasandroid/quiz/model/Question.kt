package com.aulasandroid.quiz.model

// Representa uma pergunta do jogo.
data class Question(

    // Enunciado da pergunta.
    val text: String,

    // Lista com as alternativas disponíveis.
    val options: List<String>,

    // Alternativa que representa a resposta correta.
    val correctAnswer: String
)
