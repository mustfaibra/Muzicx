package com.example.muzicx.model

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class Review(
    val id: Int ,
    val profile: Color = Color(
        Random.nextFloat(),
        Random.nextFloat(),
        Random.nextFloat(),
        1f
    ),
    val reviewerName: String = listOf(
        "Ahmed Omer",
        "Mark M twin",
        "Luther johnson",
        "Tamer King",
        "Mustafa Ibrahim",
        "Omer Mohammed",
        "Tony Chopper",
        "Max Maximilian",
        "Magda Kans",
        "Ali Osman",
        "Abdo Faisal",
        "Hassan Ahmed",
        "Omer Ibrahim",
        "Suha Ahmed",
        "Reem Majdi",
        "Razan Abdallah",
        "Omer Abdo",
        "Mohammed Hatem"
    ).shuffled().shuffled().last(),
    val review: String = listOf(
        "The best artist ever , love from UK",
        "The best artist ever , love from USA",
        "The best artist ever , love from KSA",
        "GOAT !",
        "The best artist ever , love from UAE",
        "The best artist ever , love from Sudan",
        "The best artist ever , love from Russia",
        "Perfect one .",
        "The best artist ever , love from Libya",
        "The best artist ever , love from Egypt",
        "So talented , Much love !",
        "The only one I listen to when I am upset",
        "Can't express my feelings !",
        "Sorry , but OVERRATED ! ",
        "What an artist , AWESOME !",
    ).shuffled().shuffled().last()
)
