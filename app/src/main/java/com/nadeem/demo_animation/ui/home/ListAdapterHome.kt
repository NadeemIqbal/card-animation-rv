package com.nadeem.demo_animation.ui.home

import android.R.attr.category
import android.R.attr.mode
import android.animation.ValueAnimator
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.HorizontalScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.nadeem.demo_animation.R
import com.nadeem.demo_animation.databinding.ItemCardBinding
import kotlinx.coroutines.*


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
    var isCollapsed = false
    var isExpanded = false

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

                if (!list[position].isFirstClicked && list[position].isSecondClicked){
                    (binding.card1.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin = 50
                    binding.card1.requestLayout()
                    (binding.card2.layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin = 50
                    binding.card2.requestLayout()
                    val lp = binding.guideline3.layoutParams as ConstraintLayout.LayoutParams
                    // get the float value
                    lp.guidePercent = 0.5f
                    list[position].percent = 0.5f
                }

         //       doAnimation(list[position], binding)
              /*  val param = binding.card1.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(20.toPx,0,20.toPx,0)
                binding.card1.layoutParams = param

                val param1 = binding.card2.layoutParams as ViewGroup.MarginLayoutParams
                param1.setMargins(20.toPx,0,20.toPx,0)
                binding.card2.layoutParams = param1*/
//                binding.card1.updateLayoutParams<ViewGroup.MarginLayoutParams> {
//                    setMargins(20,0,0,0)
//                }
               /* binding.card2.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    setMargins(0,0,20,0)
                }*/
                doAnimation1(list[position], binding)

                binding.card1.setOnClickListener {


                    list.forEachIndexed { index, model ->
                        if (index == position) {
                            list[index].isFirstClicked = true
                            list[index].isSecondClicked = false

                            Log.d("TAG", "bind: $index $position  ${list[index].isFirstClicked}  ${list[index].isSecondClicked}")
                    //        binding.hsv.scrollToStart(itemView)

                        } else {
                            list[index].isFirstClicked = false
                            list[index].isSecondClicked = false

                        }
                    }
                    Log.d("TAG", "bind: 1")
                 doAnimation1(list[position], binding)
                    notifyDataSetChanged()
                }

                binding.card2.setOnClickListener {
                    list.forEachIndexed { index, _ ->
                        if (index == position) {
                            list[index].isFirstClicked = false
                            list[index].isSecondClicked = true
                          //  binding.hsv.scrollToEnd(itemView)
                            Log.d("TAG", "bind: $index $position  ${list[index].isFirstClicked}  ${list[index].isSecondClicked}")

                        } else {
                            list[index].isFirstClicked = false
                            list[index].isSecondClicked = false
                        }
                    }

                  //  doAnimation(list[position], binding)
                    Log.d("TAG", "bind: 2")
                    doAnimation1(list[position], binding)
                  notifyDataSetChanged()

                }
              //  executePendingBindings()
            }
        }


    }

    private fun doAnimation1(model: Model, binding: ItemCardBinding) {
        Log.d("TAG", "bind 3: ${model.isFirstClicked} ${model.isSecondClicked}")

        if (model.isFirstClicked) {


            binding.img1.animate()
                .x(binding.img2.x)
                .y(binding.img2.y)
                .setDuration(500)
                .withEndAction {
                    //to make sure that it arrives,
                    //but not needed actually these two lines
                    binding.img1.visibility = View.GONE
                }
                .start()
            val end = (binding.guideline3.layoutParams as ConstraintLayout.LayoutParams).guidePercent
            if (model.percent == end)
            {
                // get end percent. start at 0
                val valueAnimator = ValueAnimator.ofFloat(end, 0.7f)
                valueAnimator.duration = 3000
                // set duration
                valueAnimator.interpolator = AccelerateDecelerateInterpolator()
                // set interpolator and  updateListener to get the animated value
                valueAnimator.addUpdateListener { valueAnimator ->
                    val lp = binding.guideline3.layoutParams as ConstraintLayout.LayoutParams
                    // get the float value
                    lp.guidePercent = valueAnimator.animatedValue as Float
                    // update layout params
                    binding.guideline3.layoutParams = lp
                }
                valueAnimator.start()
                model.percent = 0.7f
            }else if(model.percent == 0.7f)
            {
                // get end percent. start at 0
                val valueAnimator = ValueAnimator.ofFloat(model.percent, end)
                valueAnimator.duration = 3000
                // set duration
                valueAnimator.interpolator = AccelerateDecelerateInterpolator()
                // set interpolator and  updateListener to get the animated value
                valueAnimator.addUpdateListener { valueAnimator ->
                    val lp = binding.guideline3.layoutParams as ConstraintLayout.LayoutParams
                    // get the float value
                    lp.guidePercent = valueAnimator.animatedValue as Float
                    // update layout params
                    binding.guideline3.layoutParams = lp
                }
                valueAnimator.start()
                model.percent = 0.5f
            }


           /* binding.card1.updateLayoutParams<ViewGroup.MarginLayoutParams> {
//                 width = EXPAND_VALUE
//                setMargins(20,0,0,0)
                val va = ValueAnimator.ofInt(NORMAL_VALUE, EXPAND_VALUE)
                va.duration = 1000
                va.addUpdateListener { animation ->
                    val value = animation.animatedValue as Int
                    binding.card1.getLayoutParams().width = value
                    binding.card1.requestLayout()
                }
                va.start()

            }
            binding.card2.updateLayoutParams<ViewGroup.MarginLayoutParams> {
//                   width = NORMAL_VALUE
               setMargins(0,0, (-20).toPx,0)
                val va = ValueAnimator.ofInt(NORMAL_VALUE, 100.toPx)
                va.duration = 1000
                va.addUpdateListener { animation ->
                    val value = animation.animatedValue as Int
                    binding.card2.getLayoutParams().width = value
                    binding.card2.requestLayout()
                }
                va.start()

            }*/

        } else if (model.isSecondClicked) {

            binding.img2.animate()
                .x(binding.img1.x)
                .y(binding.img1.y)
                .setDuration(500)
                .withEndAction {
                    //to make sure that it arrives,
                    //but not needed actually these two lines
                    binding.img2.visibility = View.GONE
                }
                .start()

            val end = (binding.guideline3.layoutParams as ConstraintLayout.LayoutParams).guidePercent
            if (model.percent == end)
            {
                // get end percent. start at 0
                val valueAnimator = ValueAnimator.ofFloat(end, 0.3f)
                valueAnimator.duration = 3000
                // set duration
                valueAnimator.interpolator = AccelerateDecelerateInterpolator()
                // set interpolator and  updateListener to get the animated value
                valueAnimator.addUpdateListener { valueAnimator ->
                    val lp = binding.guideline3.layoutParams as ConstraintLayout.LayoutParams
                    // get the float value
                    lp.guidePercent = valueAnimator.animatedValue as Float
                    // update layout params
                    binding.guideline3.layoutParams = lp
                }
                valueAnimator.start()
                model.percent = 0.3f
                model.isSecondClicked = false
            }else if(model.percent == 0.3f)
            {
                // get end percent. start at 0
                val valueAnimator = ValueAnimator.ofFloat(model.percent, end)
                valueAnimator.duration = 3000
                // set duration
                valueAnimator.interpolator = AccelerateDecelerateInterpolator()
                // set interpolator and  updateListener to get the animated value
                valueAnimator.addUpdateListener { valueAnimator ->
                    val lp = binding.guideline3.layoutParams as ConstraintLayout.LayoutParams
                    // get the float value
                    lp.guidePercent = valueAnimator.animatedValue as Float
                    // update layout params
                    binding.guideline3.layoutParams = lp
                }
                valueAnimator.start()
                model.percent = 0.5f
                model.isSecondClicked = false
            }

         /*   binding.card1.updateLayoutParams<ViewGroup.MarginLayoutParams> {
//                   width = 100
               setMargins((-20).toPx,0,0,0)
                val va = ValueAnimator.ofInt(NORMAL_VALUE, 70.toPx)
                va.duration = 1000
                va.addUpdateListener { animation ->
                    val value = animation.animatedValue as Int
                    binding.card1.getLayoutParams().width = value
                    binding.card1.requestLayout()
                }
                va.start()

            }
            binding.card2.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                //  width = EXPAND_VALUE
//               setMargins(20,0,20,0)
                val va = ValueAnimator.ofInt(NORMAL_VALUE, EXPAND_VALUE)
                va.duration = 1000
                va.addUpdateListener { animation ->
                    val value = animation.animatedValue as Int
                    binding.card2.getLayoutParams().width = value
                    binding.card2.requestLayout()
                }
                va.start()


            }*/
        } else {
            if (!model.isFirstClicked && model.percent == 0.5f)
            {
                binding.card1.updateLayoutParams {
                    width = NORMAL_VALUE
                }
            }
            if (!model.isSecondClicked && model.percent == 0.5f)
            {
                binding.card2.updateLayoutParams {
                    width = NORMAL_VALUE
                }
            }


        }
    }

    fun HorizontalScrollView.scrollToEnd(itemView: View) {
       /* this.postDelayed({

            this.fullScroll(HorizontalScrollView.FOCUS_RIGHT) }, 100)*/
    }

    fun HorizontalScrollView.scrollToStart(itemView: View) {
       /* this.postDelayed({ this.fullScroll(HorizontalScrollView.FOCUS_LEFT) }, 100)*/
    }


    fun doAnimation(model: Model, binding: ItemCardBinding) {
        if (model.isFirstClicked) {
            binding.card1.updateLayoutParams {
               // width = EXPAND_VALUE
                    TransitionManager.beginDelayedTransition(
                        binding.card1, TransitionSet()
                            .addTransition(ChangeBounds())
                    )
                    val params: ViewGroup.LayoutParams = binding.card1.layoutParams
                    params.width = EXPAND_VALUE
                binding.card1.layoutParams = params
            }
            binding.card2.updateLayoutParams {
             //   width = NORMAL_VALUE
                TransitionManager.beginDelayedTransition(
                    binding.card2, TransitionSet()
                        .addTransition(ChangeBounds())
                )
                val params: ViewGroup.LayoutParams = binding.card2.layoutParams
                params.width = NORMAL_VALUE
                binding.card2.layoutParams = params
            }

        } else if (model.isSecondClicked) {
            binding.card1.updateLayoutParams {
            //    width = NORMAL_VALUE
                TransitionManager.beginDelayedTransition(
                    binding.card1, TransitionSet()
                        .addTransition(ChangeBounds())
                )
                val params: ViewGroup.LayoutParams = binding.card1.layoutParams
                params.width = NORMAL_VALUE
                binding.card1.layoutParams = params
            }
            binding.card2.updateLayoutParams {
              //  width = EXPAND_VALUE
                TransitionManager.beginDelayedTransition(
                    binding.card2, TransitionSet()
                        .addTransition(ChangeBounds())
                )
                val params: ViewGroup.LayoutParams = binding.card2.layoutParams
                params.width = EXPAND_VALUE
                binding.card2.layoutParams = params

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

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    val Float.toPx get() = this * Resources.getSystem().displayMetrics.density

    val Float.toDp get() = this / Resources.getSystem().displayMetrics.density

    val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()

    val NORMAL_VALUE = 189.toPx//.toDp
    val EXPAND_VALUE = 300.toPx//.toDp
//    val NORMAL_VALUE = Resources.getSystem().displayMetrics.widthPixels / 2

}





