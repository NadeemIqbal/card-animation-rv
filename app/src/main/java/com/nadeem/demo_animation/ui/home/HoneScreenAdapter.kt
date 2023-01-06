package com.nadeem.demo_animation.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nadeem.demo_animation.databinding.ItemCardBinding


class HoneScreenAdapter(
    private val copyList: List<Model>,
    private val payHistoryClick: paymentHistoryBtnClick
) :
    RecyclerView.Adapter<HoneScreenAdapter.ViewHolder>() {

    private var list = ArrayList<Model>(copyList)
    inner class ViewHolder(val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = list[position]
        holder.binding.apply {
            tv1.text = current.str1
            tv2.text = current.str2


            if (!list[position].isFirstClicked && !list[position].isSecondClicked){
                (card1.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin = 50
                card1.requestLayout()
                (card2.layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin = 50
                card2.requestLayout()
                val lp = guideline3.layoutParams as ConstraintLayout.LayoutParams
                // get the float value
                lp.guidePercent = 0.5f
                list[position].percent = 0.5f
            }


            card1.setOnClickListener {


                list.forEachIndexed { index, model ->
                    if (index == position) {
                        list[index].isFirstClicked = true
                        list[index].isSecondClicked = false


                        Log.d(
                            "TAG",
                            "bind: $index $position  ${list[index].isFirstClicked}  ${list[index].isSecondClicked}"
                        )
                        //        binding.hsv.scrollToStart(itemView)

                    } else {
                        list[index].isFirstClicked = false
                        list[index].isSecondClicked = false

                    }
                }
                Log.d("TAG", "bind: 1")
                    payHistoryClick.click1(list[position], holder.binding,position)

            }

            card2.setOnClickListener {
                list.forEachIndexed { index, _ ->
                    if (index == position) {
                        list[index].isFirstClicked = false
                        list[index].isSecondClicked = true
                        //  binding.hsv.scrollToEnd(itemView)

                        Log.d(
                            "TAG",
                            "bind: $index $position  ${list[index].isFirstClicked}  ${list[index].isSecondClicked}"
                        )

                    } else {
                        list[index].isFirstClicked = false
                        list[index].isSecondClicked = false
                    }
                }

                //  doAnimation(list[position], binding)
                Log.d("TAG", "bind: 2")

                payHistoryClick.click1(list[position], holder.binding, position)

            }

        }

    }

    fun setData(newLanguageList: List<Model>) {
        val diffUtil = MyDiffUtil(list, newLanguageList)
        // it calculates the different items of the oldLanguageList and newLanguageList
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        // assign oldLanguageList to newLanguageList
        list = newLanguageList as ArrayList<Model>
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    interface paymentHistoryBtnClick {
         fun click1(model: Model, binding: ItemCardBinding, position: Int)
    }


}

