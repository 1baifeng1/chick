package com.huzi.orderpanel.animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

public class Order_drop extends Animation {
	private int lengthX,lengthY;
	
	/**
	 * ����Ҫƽ�Ƶ�X������Y����
	 * @param lengthX Ҫƽ�Ƶ�X��������������
	 * @param lengthY Ҫƽ�Ƶ�Y��������������
	 */
	public void setLength(int lengthX,int lengthY){
		this.lengthX=lengthX;
		this.lengthY=lengthY;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		// TODO Auto-generated method stub
		t.getMatrix().setTranslate(lengthX, lengthY*(1-interpolatedTime));
		
		super.applyTransformation(interpolatedTime, t);
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		// TODO Auto-generated method stub
		super.initialize(width, height, parentWidth, parentHeight);
		
		setInterpolator(new Order_drop_interpolator());
	}
	
}
