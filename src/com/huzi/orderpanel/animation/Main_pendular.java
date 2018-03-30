package com.huzi.orderpanel.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 欢迎页招牌的左右摇摆动画
 * @author SXH
 */
public class Main_pendular extends Animation {
	private int x,y;

	/**
	 * 设置要旋转的中心点
	 * @param x 中心点横坐标（已经除过4）
	 * @param y 中心点纵坐标
	 */
	public void setRotatePoint(int x,int y){
		this.x=x;
		this.y=y;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		// TODO Auto-generated method stub
		super.applyTransformation(interpolatedTime, t);
		
		t.getMatrix().setRotate((float)(20*Math.cos((1/2+interpolatedTime/2)*Math.PI))*(float)(Math.cos(11*interpolatedTime)), x, y);
	}
	
}
