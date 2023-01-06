package com.nadeem.demo_animation.ui.home

import android.view.Display
import androidx.recyclerview.widget.DiffUtil

// pass two list one oldList and second newList
class MyDiffUtil(
    private val oldList : List<Model>,
    private val newList : List<Model>
) :DiffUtil.Callback() {
    // implement methods
    override fun getOldListSize(): Int {
        // return oldList size
        return oldList.size
    }

    override fun getNewListSize(): Int {
        // return newList size
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // compare items based on their unique id
        return oldList[oldItemPosition].isFirstClicked == newList[newItemPosition].isFirstClicked
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // in here compare each item if they are same or different
        // return false if any data is same else return true
        return when{
            oldList[oldItemPosition].isFirstClicked != newList[newItemPosition].isFirstClicked -> false
            oldList[oldItemPosition].isSecondClicked != newList[newItemPosition].isSecondClicked -> false
            else -> true
        }
    }
}
