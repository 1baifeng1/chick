package com.huzi.orderpanel.customview;

import com.huzi.orderpanel.activity.R;

import android.content.Context;
import android.graphics.*;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 自定义控件：上方为横条图片，下方为文字
 * @author SXH
 */
public class Order_menuB extends TextView {
	private static final String namespace="http://com.huzi.orderpanel.customview";
	Bitmap bmpHengtiao;//横条图片

	public Order_menuB(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		bmpHengtiao=((BitmapDrawable)context.getResources().getDrawable(R.drawable.hengtiao)).getBitmap();
		
//		this.setTextColor(0xff4d2f00);
//		this.setTextSize(13);//这个单位不是像素
//		this.setGravity(Gravity.CENTER);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		Rect rect=new Rect();
		rect.left=0;
		rect.top=0;
		rect.right=150;
		rect.bottom=3;
		canvas.drawBitmap(bmpHengtiao, null, rect, getPaint());
		canvas.translate(0, 3);
		
		super.onDraw(canvas);
	}

}
