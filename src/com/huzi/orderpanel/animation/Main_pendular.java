package com.huzi.orderpanel.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * ��ӭҳ���Ƶ�����ҡ�ڶ���
 * @author SXH
 */
public class Main_pendular extends Animation {
	private int x,y;

	/**
	 * ����Ҫ��ת�����ĵ�
	 * @param x ���ĵ�����꣨�Ѿ�����4��
	 * @param y ���ĵ�������
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
