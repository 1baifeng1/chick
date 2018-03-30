package com.huzi.orderpanel.activity;

import com.huzi.orderpanel.activity.R;
import com.huzi.orderpanel.animation.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
	LinearLayout ll_main_logo;
	ImageView imv_diancan;
	/**
	 * 左右摇摆动画
	 */
	Main_pendular mPendular;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//招牌左右摇摆动画
		mPendular=new Main_pendular();
		mPendular.setDuration(3000);
		ll_main_logo=(LinearLayout)findViewById(R.id.ll_main_logo);
		
		//获取摇摆原点
		ll_main_logo.measure(View.MeasureSpec.makeMeasureSpec(0 ,View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		int x=ll_main_logo.getMeasuredWidth();//控件的左上角坐标
//		mPendular.setRotatePoint(x/2, 2);
//		System.out.println("x/2="+(x/2));
		mPendular.setRotatePoint(544, 2);
		
		ll_main_logo.startAnimation(mPendular);
		
		//点餐按钮
        imv_diancan=(ImageView)findViewById(R.id.imv_diancan);
        imv_diancan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(),OrderActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
