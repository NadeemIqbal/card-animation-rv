package com.nadeem.demo_animation.ui.home

import android.animation.ValueAnimator
import android.content.res.Resources
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadeem.demo_animation.databinding.ItemCardBinding

class ListAdapterHome(
    val list: List<Model>,
    val recyclerView: RecyclerView,
    val fragment: HomeFragment
) :
    ListAdapter<Model, RecyclerView.ViewHolder>(DataDiffUtil<Model>()) {

    init {
        submitList(list)
    }

    private val layoutInflater = fragment.layoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCardBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return CarTopViewHolder(binding)
    }

    private inner class CarTopViewHolder(val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                binding.tv1.text = list[position].str1
                binding.tv2.text = list[position].str2

                doAnimation(list[position], binding)

                binding.card1.setOnClickListener {
                    list.forEachIndexed { index, model ->
                        if (index == position) {
                            list[index].isFirstClicked = true
                            list[index].isSecondClicked = false
                        } else {
                            list[index].isFirstClicked = false
                            list[index].isSecondClicked = false
                        }
                    }
//                    TransitionManager.endTransitions(binding.root as ViewGroup?)
//                    TransitionManager.beginDelayedTransition(
//                        binding.root as ViewGroup?,
//                        AutoTransition()
//                    )
                    doAnimation(list[position], binding)
                    notifyDataSetChanged()
                }

                binding.card2.setOnClickListener {
                    list.forEachIndexed { index, _ ->
                        if (index == position) {
                            list[index].isFirstClicked = false
                            list[index].isSecondClicked = true
                        } else {
                            list[index].isFirstClicked = false
                            list[index].isSecondClicked = false
                        }
                    }

//                    TransitionManager.endTransitions(binding.root as ViewGroup?)
//                    TransitionManager.beginDelayedTransition(
//                        binding.root as ViewGroup?,
//                        AutoTransition()
//                    )
                    doAnimation(list[position], binding)
                    notifyDataSetChanged()
                }
                executePendingBindings()
            }
        }


    }

    fun doAnimation(model: Model, binding: ItemCardBinding) {
        if (model.isFirstClicked) {
            binding.card2.updateLayoutParams {
                width = 290.toDp
            }
            binding.card1.updateLayoutParams {
                width = MATCH_PARENT
            }
        } else if (model.isSecondClicked) {
            binding.card1.updateLayoutParams {
                width = 290.toDp
            }
            binding.card2.updateLayoutParams {
                width = MATCH_PARENT
            }
        } else {
            binding.card2.updateLayoutParams {
                width = MATCH_PARENT
            }
            binding.card1.updateLayoutParams {
                width = MATCH_PARENT
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CarTopViewHolder).bind(position)
    }


    fun View.animateHeightFromTo(initialWidth: Int, finalWidth: Int) {
        val animator = ValueAnimator.ofInt(initialWidth, finalWidth)
        animator.duration = 250
        animator.addUpdateListener {
            val value = it.animatedValue as Int
            val lp = this.layoutParams
            lp.width = value
            this.layoutParams = lp
            isVisible = value != 0
        }
        animator.start()
    }

    val Float.toPx get() = this * Resources.getSystem().displayMetrics.density

    val Float.toDp get() = this / Resources.getSystem().displayMetrics.density

    val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()
}





