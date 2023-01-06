package com.nadeem.demo_animation.ui.home

import android.animation.ValueAnimator
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nadeem.demo_animation.databinding.FragmentHomeBinding
import com.nadeem.demo_animation.databinding.ItemCardBinding

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private lateinit var mAdapter: HoneScreenAdapter
    var previous: Int? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

     //   homeViewModel.listOfObjects.map{it.copy()}
        mAdapter = HoneScreenAdapter(homeViewModel.listOfObjects,object : HoneScreenAdapter.paymentHistoryBtnClick {
            override fun click1(model: Model, binding: ItemCardBinding, position: Int) {
                doAnimation1(model, binding)
                mAdapter.notifyDataSetChanged()

         /*       if (homeViewModel.previous != null)
                {
                    if (homeViewModel.previous != position)
                    {
                        mAdapter.notifyItemChanged(homeViewModel.previous!!)
                        homeViewModel.previous = position
                    }else{
                  //     mAdapter.notifyItemChanged(position)
                        homeViewModel.previous = position
                    }

                }else
                {
                  //  mAdapter.notifyItemChanged(position)
                    homeViewModel.previous = position
                }*/

                /*for (pos in 0..homeViewModel.listOfObjects.size)
                {
                    if (pos != position)
                        mAdapter.notifyItemChanged(pos)
                }*/
              //  mAdapter.setData(homeViewModel.listOfObjects.map{it.copy()} )
            }
        })
        binding.rv.apply {

            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        // mAdapter.setData(homeViewModel.listOfObjects)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun doAnimation1(model: Model, binding: ItemCardBinding) {
        Log.d("TAG", "bind 3: ${model.isFirstClicked} ${model.isSecondClicked}")

        if (model.isFirstClicked) {

            (binding.card2.layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin = -70
            binding.card2.requestLayout()

          /*  binding.img1.animate()
                .x(binding.img2.x)
                .y(binding.img2.y)
                .setDuration(500)
                .withEndAction {
                    //to make sure that it arrives,
                    //but not needed actually these two lines
                    binding.img1.visibility = View.GONE
                }
                .start()*/
            val end =
                (binding.guideline3.layoutParams as ConstraintLayout.LayoutParams).guidePercent
            if (model.percent == 0.5f) {
                (binding.card2.layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin = -70
                binding.card2.requestLayout()
                // get end percent. start at 0
                val valueAnimator = ValueAnimator.ofFloat(model.percent, 0.7f)
                valueAnimator.duration = 200
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
            } else if (model.percent == 0.7f) {
                // get end percent. start at 0
                (binding.card2.layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin = 50
                binding.card2.requestLayout()
                val valueAnimator = ValueAnimator.ofFloat(model.percent, 0.5f)
                valueAnimator.duration = 200
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
            } else if (model.percent == 0.3f) {
                // get end percent. start at 0
                (binding.card2.layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin = -70
                binding.card2.requestLayout()
                (binding.card1.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin = 50
                binding.card1.requestLayout()
                val valueAnimator = ValueAnimator.ofFloat(model.percent, 0.7f)
                valueAnimator.duration = 200
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
            }

        } else if (model.isSecondClicked) {
            (binding.card1.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin = -70
            binding.card1.requestLayout()
           /* binding.img2.animate()
                .x(binding.img1.x)
                .y(binding.img1.y)
                .setDuration(500)
                .withEndAction {
                    //to make sure that it arrives,
                    //but not needed actually these two lines
                    binding.img2.visibility = View.GONE
                }
                .start()*/

            val end =
                (binding.guideline3.layoutParams as ConstraintLayout.LayoutParams).guidePercent
            if (model.percent == 0.5f) {
                (binding.card1.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin = -70
                binding.card1.requestLayout()
                // get end percent. start at 0
                val valueAnimator = ValueAnimator.ofFloat(model.percent, 0.3f)
                valueAnimator.duration = 200
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
            } else if (model.percent == 0.3f) {
                (binding.card1.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin = 50
                binding.card1.requestLayout()
                // get end percent. start at 0
                val valueAnimator = ValueAnimator.ofFloat(model.percent, 0.5f)
                valueAnimator.duration = 200
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
            } else if (model.percent == 0.7f) {
                (binding.card1.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin = -70
                binding.card1.requestLayout()
                (binding.card2.layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin = 50
                binding.card2.requestLayout()
                // get end percent. start at 0
                val valueAnimator = ValueAnimator.ofFloat(model.percent, 0.3f)
                valueAnimator.duration = 200
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
            }
        } else {
            (binding.card1.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin = 50
            binding.card1.requestLayout()
            (binding.card2.layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin = 50
            binding.card2.requestLayout()
            val lp = binding.guideline3.layoutParams as ConstraintLayout.LayoutParams
            // get the float value
            lp.guidePercent = 0.5f

        }
    }


    val Float.toPx get() = this * Resources.getSystem().displayMetrics.density

    val Float.toDp get() = this / Resources.getSystem().displayMetrics.density

    val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()

    val NORMAL_VALUE = 189.toPx//.toDp
    val EXPAND_VALUE = 300.toPx//.toDp
}