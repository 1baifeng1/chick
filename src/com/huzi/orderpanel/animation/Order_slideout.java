package com.huzi.orderpanel.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Order_slideout extends Animation {
	private int lengthX,lengthY;
	
	/**
	 * 设置要平移的X向量和Y向量
	 * @param lengthX 要平移的X向量（可正负）
	 * @param lengthY 要平移的Y向量（可正负）
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
