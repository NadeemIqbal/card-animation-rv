package com.nadeem.demo_animation.ui.home

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.util.Property
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
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

//                doAnimation(list[position], binding)

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
                        } else {
                            list[index].isFirstClicked = false
                            list[index].isSecondClicked = false
                        }
                    }

                    if (isExpanded) {
                        binding.card1.startAnimation(animNormalFromExpand)
                        binding.card2.startAnimation(animNormalFromCollapse)
                    } else {
//                        binding.card1.startAnimation(animExpand)
//                        binding.card2.startAnimation(animCollapse)

                        animateHeightTo(binding.card1, MATCH_PARENT)
                        animateHeightTo(binding.card2, 290.toDp)
                    }
                    //                    doAnimation(list[position], binding)
                    notifyDataSetChanged()
                }

                binding.card2.setOnClickListener {
                    list.forEachIndexed { index, _ ->
                        if (index == position) {
                            list[index].isFirstClicked = false
                            list[index].isSecondClicked = list[index].isSecondClicked.not()
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

    fun doAnimation(model: Model, binding: ItemCardBinding) {
        if (model.isFirstClicked) {
//            binding.card2.updateLayoutParams {
//                width = 290.toDp
//            }
//            binding.card1.updateLayoutParams {
//                width = 0.toDp
//            }

            binding.card2.animateHeightFromTo(binding.card2.width, 290.toDp)
            binding.card1.animateHeightFromTo(binding.card1.width, MATCH_PARENT)

        } else if (model.isSecondClicked) {
            binding.card1.updateLayoutParams {
                width = 290.toDp
            }
            binding.card2.updateLayoutParams {
                width = 0.toDp
            }
        } else {
            binding.card2.updateLayoutParams {
                width = 0.toDp
            }
            binding.card1.updateLayoutParams {
                width = 0.toDp
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CarTopViewHolder).bind(position)
    }


    private fun View.animateHeightFromTo(initialWidth: Int, finalWidth: Int) {
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


//        val resizeAnimation = ResizeAnimation(
//            this, finalWidth, initialWidth
//        )
//        resizeAnimation.duration = 500
//        this.startAnimation(resizeAnimation)
    }

    val Float.toPx get() = this * Resources.getSystem().displayMetrics.density

    val Float.toDp get() = this / Resources.getSystem().displayMetrics.density

    val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    private fun animateHeightTo(view: View, height: Int) {
        val currentHeight = view.height
        val animator: ObjectAnimator =
            ObjectAnimator.ofInt(view, HeightProperty(), currentHeight, height)
        animator.duration = 300
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }

    internal class HeightProperty :
        Property<View, Int>(Int::class.java, "width") {
        override operator fun get(view: View): Int {
            return view.width
        }

        override operator fun set(view: View, value: Int) {
            view.layoutParams.width = value
            view.layoutParams = view.layoutParams
        }
    }

}





