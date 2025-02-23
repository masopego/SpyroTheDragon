package dam.pmdm.spyrothedragon.utils;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class ViewAnimator {

    public static void animateTranslationX(View view, float startValue, float endValue, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", startValue, endValue);
        animator.setDuration(duration);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public static void animateTranslationY(View view, float startValue, float endValue, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", startValue, endValue);
        animator.setDuration(duration);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public static void animateAlpha(View view, float startValue, float endValue, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", startValue, endValue);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }
}
