package c.ponom.pocketlibrary.Utils;


import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class Animations {

    public interface EndAnimationListener {
        void animationDone(int offset);
        void updateAnimation(int offset);
    }

    public static void expand(final View v, int duration, int targetHeight, EndAnimationListener endListener) {
        int prevHeight = v.getHeight();
        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        /*
        valueAnimator.addUpdateListener(animation -> {
            v.getLayoutParams().height = (int) animation.getAnimatedValue();
            v.requestLayout();
            if (endListener != null) {
                endListener.updateAnimation(v.getLayoutParams().height);
            }
            if (v.getLayoutParams().height == targetHeight) {
                if (endListener != null) {
                    endListener.animationDone(v.getLayoutParams().height);
                }
            }
        });

        */
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, 0);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        /*
        valueAnimator.addUpdateListener(animation -> {
            v.getLayoutParams().height = (int) animation.getAnimatedValue();
            v.requestLayout();
        });
        */
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }
}
