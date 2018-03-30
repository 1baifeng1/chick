package com.huzi.orderpanel.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Order_slideout extends Animation {
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
		t.getMatrix().setTranslate(lengthX*interpolatedTime, lengthY);
		
		super.applyTransformation(interpolatedTime, t);
	}
}
