package com.nadeem.demo_animation.ui.home

import androidx.recyclerview.widget.DiffUtil

class DataDiffUtil<T>() : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(
        oldItem: T, newItem: T
    ): Boolean {
//        val oldItem = oldList?.get(oldItemPosition)
//        val newItem = newList?.get(newItemPosition)
        var oldItemString = oldItem?.toString()
        var newItemString = newItem?.toString()
//        if (oldItem is String) {
//            oldItemString = oldItemPosition.toString() + "_" + oldItemString
//            newItemString = newItemPosition.toString() + "_" + newItemString
//        }
        val newArray = newItemString?.split("_")?.toTypedArray()
        val oldArray = oldItemString?.split("_")?.toTypedArray()
        if (newArray?.size ?: 1 > 1 && oldArray?.size ?: 1 > 1) {
            val newString = newArray?.get(0)
            val oldValue = oldArray?.get(0)
            val value = oldItemString != null && newString != null && newString == oldValue
//            Log.e(
//                "areItemsTheSame", "value=$value newString=$newString oldValue=$oldValue " +
//                        "oldItemPosition=$oldItemPosition newItemPosition=$newItemPosition"
//            )
            return value
        }
        return true
    }

    override fun areContentsTheSame(
        oldItem: T, newItem: T
    ): Boolean {
//        val oldItem = oldList?.get(oldItemPosition)
//        val newItem = newList?.get(newItemPosition)
        var oldItemString = oldItem.toString()
        var newItemString = newItem.toString()
//        if (oldItem is String) {
//            oldItemString = oldItemPosition.toString() + "_" + oldItemString
//            newItemString = newItemPosition.toString() + "_" + newItemString
//        }
        val value = oldItemString == newItemString
//        Log.e(
//            "areContentsTheSame", "value=$value newString=$newString oldValue=$oldValue " +
//                    "oldItemPosition=$oldItemPosition newItemPosition=$newItemPosition"
//        )
        return value
    }

}