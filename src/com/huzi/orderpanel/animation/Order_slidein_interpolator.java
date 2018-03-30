package com.huzi.orderpanel.animation;

import android.animation.TimeInterpolator;
import android.view.animation.Interpolator;

public class Order_slidein_interpolator implements TimeInterpolator, Interpolator {

	@Override
	public float getInterpolation(float input) {
		// TODO Auto-generated method stub
		if(input<0.7)
			return bounce(input);
		else
			return bounce(input-0.9105f)+0.98234f;
	}

	private static float bounce(float t){
		return t*t*2.20408f;
	}
}
