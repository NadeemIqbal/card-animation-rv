package com.nadeem.demo_animation.ui.home

data class Model(
    val str1: String,
    val str2: String,
    var isFirstClicked: Boolean = false,
    var isSecondClicked: Boolean = false,
    var percent: Float = 0.5f
)