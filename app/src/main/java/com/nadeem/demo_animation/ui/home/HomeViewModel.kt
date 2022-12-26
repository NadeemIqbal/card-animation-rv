package com.nadeem.demo_animation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val listOfObjects: ArrayList<Model> = arrayListOf(
        Model("Add Card", "Apple Pay"),
        Model("1234", "5678"),
        Model("9101112", "12131415"),
        Model("1617181920", "Google Pay"),
//        Model("Add Card", "Apple Pay"),
//        Model("1234", "5678"),
//        Model("9101112", "12131415"),
//        Model("1617181920", "Google Pay"),
//        Model("Add Card", "Apple Pay"),
//        Model("1234", "5678")
    )


}