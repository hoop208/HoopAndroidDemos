package com.example.hoopandroiddemos

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView

class CustomAddDelAnimation : DefaultItemAnimatorOpen() {


    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        //重写item添加的动画
        resetAnimation(holder)
        holder.itemView.rotationX = 90f
        mPendingAdditions.add(holder)
        return true
    }

    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        //添加item时执行绕x轴90->0度的旋转
        mAddAnimations.add(holder)
        holder.itemView.animate().apply {
            rotationX(0f)
            duration = addDuration
            interpolator = DecelerateInterpolator(3f)
            setListener(object : AnimatorListenerAdapter() {

                override fun onAnimationStart(animation: Animator?) {
                    dispatchAddStarting(holder)
                }

                override fun onAnimationCancel(animation: Animator?) {
                    ViewHelper.clear(holder.itemView)
                }

                override fun onAnimationEnd(animation: Animator) {
                    ViewHelper.clear(holder.itemView)
                    dispatchAddFinished(holder)
                    mAddAnimations.remove(holder)
                    dispatchFinishedWhenDone()
                }
            })
        }.start()
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        //重写item删除的动画
        resetAnimation(holder)
        mPendingRemovals.add(holder)
        return true
    }

    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        //删除item时执行绕x轴0->90度的旋转
        mRemoveAnimations.add(holder)
        holder.itemView.animate().apply {
            rotationX(90f)
            duration = addDuration
            interpolator = DecelerateInterpolator(3f)
            setListener(object : AnimatorListenerAdapter() {

                override fun onAnimationStart(animation: Animator?) {
                    dispatchRemoveStarting(holder)
                }

                override fun onAnimationCancel(animation: Animator?) {
                    ViewHelper.clear(holder.itemView)
                }

                override fun onAnimationEnd(animation: Animator) {
                    ViewHelper.clear(holder.itemView)
                    dispatchRemoveFinished(holder)
                    mRemoveAnimations.remove(holder)
                    dispatchFinishedWhenDone()
                }
            })
        }.start()
    }

}