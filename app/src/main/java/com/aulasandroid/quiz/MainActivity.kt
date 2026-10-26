
package com.aulasandroid.quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aulasandroid.quiz.screens.HomeScreen
import com.aulasandroid.quiz.screens.QuizScreen
import com.aulasandroid.quiz.screens.ResultScreen
import com.aulasandroid.quiz.viewmodel.QuizViewModel

class MainActivity : ComponentActivity() {

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            QuizNavigation(
                navController = navController,
                viewModel = quizViewModel
            )
        }
    }
}

@Composable
fun QuizNavigation(
    navController: NavHostController,
    viewModel: QuizViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onStartClick = {
                    viewModel.restartQuiz()
                    navController.navigate("quiz")
                }
            )
        }
        composable("quiz") {
            QuizScreen(
                viewModel = viewModel,
                onFinish = {
                    navController.navigate("result") {
                        // Retira a tela do quiz da pilha.
                        popUpTo("quiz") {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable("result") {
            ResultScreen(
                viewModel = viewModel,

                onRestart = {
                    viewModel.restartQuiz()

                    navController.navigate("quiz") {
                        popUpTo("result") {
                            inclusive = true
                        }
                    }
                },

                onHome = {
                    viewModel.restartQuiz()

                    navController.popBackStack(
                        route = "home",
                        inclusive = false
                    )
                }
            )
        }
    }
}
