package com.smartdev.vkcup2.ui.screens.rating

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RatingViewModel : ViewModel() {

    private val _ratingUiState = MutableStateFlow(RatingUiState())
    val ratingUiState = _ratingUiState.asStateFlow()

    fun onClickNext() {
        with(_ratingUiState) {
            val targetQuestion = value.currentArticle.inc()
            update { currentState ->
                currentState.copy(
                    currentArticle = if (targetQuestion >= value.listArticle.size) 0 else targetQuestion
                )
            }
        }
    }

    fun onClickBack() {
        with(_ratingUiState) {
            val targetQuestion = value.currentArticle.dec()
            update { currentState ->
                currentState.copy(
                    currentArticle = if (targetQuestion <= -1) currentState.listArticle.size - 1 else targetQuestion
                )
            }
        }
    }

    fun ratingArticle(stars: Int) {
        with(_ratingUiState) {
            val updateList = value.listArticle.toMutableList().let { article ->
                article[value.currentArticle] = article[value.currentArticle].copy(
                    userRating = stars,
                )
                article.toList()
            }
            update { currentState ->
                currentState.copy(
                    listArticle = updateList
                )
            }
        }
    }

    fun addComment(comment: String) {
        with(_ratingUiState) {
            val updateList = value.listArticle.toMutableList().let { articles ->
                val currentArticle = articles[value.currentArticle]
                articles[value.currentArticle] = currentArticle.copy(
                    allComment = articles[value.currentArticle].allComment.toMutableList()
                        .let { comments ->
                            comments.add(
                                Comment(
                                    text = comment,
                                    userName = "You",
                                    rating = currentArticle.userRating ?: 0
                                )
                            )
                            comments.toList()
                        }
                )
                articles.toList()
            }
            update { currentState ->
                currentState.copy(
                    listArticle = updateList
                )
            }
        }
    }
}

data class RatingUiState(
    val listArticle: List<Article> = mock,
    val currentArticle: Int = 0,
) {
    companion object {
        val mock = listOf(
            Article(
                authorName = "Leland Richardson",
                publicationDate = "Aug 28, 2020",
                articleName = "Понимание компоновки реактивного ранца — часть 1 из 2",
                text = "В разработке программного обеспечения Композиция - это то, как несколько единиц более простого кода могут объединяться, образуя более сложную единицу кода. В объектно-ориентированной модели программирования одной из наиболее распространенных форм композиции является наследование на основе классов. В мире Jetpack Compose, поскольку мы работаем только с функциями, а не с классами, метод компоновки сильно отличается, но имеет много преимуществ перед наследованием. Давайте рассмотрим пример.\n" +
                        "Допустим, у нас есть представление, и мы хотим добавить входные данные. В модели наследования наш код может выглядеть следующим образом:\n",
                numberAppraisers = 4.8f,
                ratingTable = listOf(124, 82, 32, 24, 8),
                allComment = listOf(
                    Comment(
                        text = "Хорошее объяснение декларативного пользовательского интерфейса.",
                        userName = "Кай Чжу",
                        rating = 5
                    ),
                    Comment(
                        text = "Я не могу сказать, что я поклонник этого визуально. Я думаю, потому что я привык к XML, и мне нравится ясность и простота, которые они обеспечивают. Кроме того, привязка к данным сократила объем кода котельной плиты.",
                        userName = "Джо Сильва- Родригес",
                        rating = 4
                    ),
                )
            ),
            Article(
                authorName = "Мари Катрин Экеберг",
                publicationDate = "Dec 30, 2022",
                articleName = "Часто забываемые функции в Котлине",
                text = "Наиболее известное использование встроенных / анонимных объектов в Kotlin - это места, где нам нужно отправить объект (в смысле Java этого слова), который реализует интерфейс, конкретный объект нужен только один раз в вашем коде, и лямбда-выражения будет недостаточно (потому что интерфейс не работает, то есть у него есть несколько методов). Это, вероятно, немного сбивало с толку, если вы не видели этого раньше. Трудно найти хорошие примеры этого, так как это случается не так часто (вероятно, это происходит чаще на Android с его обработчиками кликов и многим другим). Давайте просто используем Runnable в качестве краткого примера, хотя вы могли бы легко использовать лямбда-выражение",
                numberAppraisers = 3.2f,
                ratingTable = listOf(18, 6, 4, 4, 1),
                allComment = listOf(
                    Comment(
                        text = "Они продолжают смотреть на меня так, как будто говорят: у тебя была одна работа!" +
                                "В любом случае, хорошая статья! Я сохраню это в качестве ориентира. Спасибо!",
                        userName = "Uzi Landsmann",
                        rating = 4
                    ),
                )
            )
        )
    }
}

data class Article(
    val articleName: String,
    val authorName: String,
    val publicationDate: String,
    val text: String,
    val numberAppraisers: Float,
    val ratingTable: List<Int>,
    val totalRating: Int = ratingTable.sum(),
    val userRating: Int? = null,
    val allComment: List<Comment>,
)

data class Comment(
    val text: String,
    val userName: String,
    val rating: Int
)