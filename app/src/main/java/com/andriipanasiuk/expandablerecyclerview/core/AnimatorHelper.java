package com.andriipanasiuk.expandablerecyclerview.core;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by andriipanasiuk on 19.09.15.
 */
public class AnimatorHelper {

    public static final int DURATION = 600;

    public static void crossfadeViews(View container, final View viewToFadeIn, final View viewToFadeOut,
                                      int currentHeight, int desiredHeight) {
        Animator heightAnimator = createHeightAnimator(container, currentHeight, desiredHeight);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(viewToFadeIn, "alpha", 0f, 1f);
        fadeIn.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                viewToFadeIn.setAlpha(0f);
                viewToFadeIn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(viewToFadeOut, "alpha", 1f, 0f);
        fadeOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewToFadeOut.setVisibility(View.GONE);
                viewToFadeOut.setAlpha(1f);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setDuration(DURATION);
        set.playTogether(heightAnimator, fadeIn, fadeOut);
        set.start();
    }

    private static Animator createHeightAnimator(final View view, int from, int to) {
        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                view.getLayoutParams().height = value;
                view.requestLayout();
            }
        });
        return animator;
    }

    public static Animator createRecyclerViewAnimator(final LinearLayoutManager layoutManager,
                                                      View item,
                                                      final int position) {
        ValueAnimator animator = ValueAnimator.ofInt(
                item.getTop() - ((ViewGroup.MarginLayoutParams) item.getLayoutParams()).topMargin, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                layoutManager.scrollToPositionWithOffset(position, value);
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(DURATION);
        return animator;
    }
}
