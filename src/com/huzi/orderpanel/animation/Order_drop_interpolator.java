package com.huzi.orderpanel.animation;

import android.animation.TimeInterpolator;
import android.view.animation.Interpolator;

public class Order_drop_interpolator implements TimeInterpolator, Interpolator {

	@Override
	public float getInterpolation(float t) {
		// TODO Auto-generated method stub
//		t *= 1.1226f;
        if (t < 0.4) return bounce(t);
        else if (t < 0.8f) return bounce(t - 0.60909f) + 0.79943f;
        else return bounce(t - 0.91818f) + 0.95398f;
	}

    private static float bounce(float t) {
        return t * t * 6.875f;
    }
}
