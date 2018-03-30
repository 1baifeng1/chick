package com.huzi.orderpanel.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Order_slidein extends Animation {
	private int lengthX, lengthY;

	/**
	 * ����Ҫ�����ĺ᷽����ݷ��򣬻��������ٵ�
	 * @param lengthX ���򻬶�����
	 * @param lengthY ���򻬶�����
	 */
	public void setLength(int lengthX, int lengthY) {
		this.lengthX = lengthX;
		this.lengthY = lengthY;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		// TODO Auto-generated method stub
		t.getMatrix().setTranslate(lengthX*(1-interpolatedTime), lengthY);
		
		super.applyTransformation(interpolatedTime, t);
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		// TODO Auto-generated method stub
		super.initialize(width, height, parentWidth, parentHeight);
		
		setInterpolator(new Order_slidein_interpolator());
	}
}
