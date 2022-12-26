package com.nadeem.demo_animation.ui.home

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.util.LayoutDirection
import android.util.Property
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.HorizontalScrollView
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadeem.demo_animation.R
import com.nadeem.demo_animation.databinding.ItemCardBinding


class ListAdapterHome(
    val list: List<Model>, val recyclerView: RecyclerView, val fragment: HomeFragment
) : ListAdapter<Model, RecyclerView.ViewHolder>(DataDiffUtil<Model>()) {
    val animExpand = AnimationUtils.loadAnimation(
        fragment.context,
        R.anim.expand
    )
    val animCollapse = AnimationUtils.loadAnimation(
        fragment.context,
        R.anim.collapse
    )
    val animNormalFromExpand = AnimationUtils.loadAnimation(
        fragment.context,
        R.anim.normal_from_expand
    )
    val animNormalFromCollapse = AnimationUtils.loadAnimation(
        fragment.context,
        R.anim.normal_from_collapse
    )

    init {
        submitList(list)
    }

    private val layoutInflater = fragment.layoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCardBinding.inflate(
            layoutInflater, parent, false
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
                    var isCollapsed = false
                    var isExpanded = false

                    list.forEachIndexed { index, model ->
                        if (index == position) {
                            list[index].isFirstClicked = if (list[index].isFirstClicked) {
                                // means its already expanded, apply normal_from_expanded on card1,
                                // and normal_from_collapse on card2
                                isExpanded = true
                                false
                            } else {
                                true
                            }
                            list[index].isSecondClicked = false
                            binding.hsv.scrollToStart()

                        } else {
                            list[index].isFirstClicked = false
                            list[index].isSecondClicked = false
                        }
                    }
                    doAnimation(list[position], binding)
                    notifyDataSetChanged()
                }

                binding.card2.setOnClickListener {
                    list.forEachIndexed { index, _ ->
                        if (index == position) {
                            list[index].isFirstClicked = false
                            list[index].isSecondClicked = list[index].isSecondClicked.not()
                            binding.hsv.scrollToEnd()
                        } else {
                            list[index].isFirstClicked = false
                            list[index].isSecondClicked = false
                        }
                    }

                    doAnimation(list[position], binding)
                    notifyDataSetChanged()
                }
                executePendingBindings()
            }
        }


    }

    fun HorizontalScrollView.scrollToEnd() {
        this.postDelayed({ this.fullScroll(HorizontalScrollView.FOCUS_RIGHT) }, 10)
    }

    fun HorizontalScrollView.scrollToStart() {
        this.postDelayed({ this.fullScroll(HorizontalScrollView.FOCUS_LEFT) }, 10)
    }


    fun doAnimation(model: Model, binding: ItemCardBinding) {
        if (model.isFirstClicked) {
            binding.card1.updateLayoutParams {
                width = EXPAND_VALUE
            }
            binding.card2.updateLayoutParams {
                width = NORMAL_VALUE
            }

        } else if (model.isSecondClicked) {
            binding.card1.updateLayoutParams {
                width = NORMAL_VALUE
            }
            binding.card2.updateLayoutParams {
                width = EXPAND_VALUE
            }
        } else {
            binding.card2.updateLayoutParams {
                width = NORMAL_VALUE
            }
            binding.card1.updateLayoutParams {
                width = NORMAL_VALUE
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CarTopViewHolder).bind(position)
    }

    val Float.toPx get() = this * Resources.getSystem().displayMetrics.density

    val Float.toDp get() = this / Resources.getSystem().displayMetrics.density

    val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()

    val NORMAL_VALUE = 169.toPx//.toDp
    val EXPAND_VALUE = 300.toPx//.toDp

}





