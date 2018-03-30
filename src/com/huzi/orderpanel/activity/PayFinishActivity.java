package com.huzi.orderpanel.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

public class PayFinishActivity extends Activity {
	TextView tv_payfinish_thankyou,tv_payfinish_pleaseget,tv_payfinish_enjoy,tv_payfinish_countdown;

	ImageView imv_payfinish_arrow;
	
	Handler handler=new Handler();
	Runnable payfinish_countdown=new  Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int currentNum=Integer.parseInt(tv_payfinish_countdown.getText().toString())-1;
			if(currentNum==0){
				Intent intentMain =new Intent();
				intentMain.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intentMain);
				finish();
			}else{
				tv_payfinish_countdown.setText(currentNum+"");
				handler.postDelayed(payfinish_countdown, 1000);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_finish);
		
		tv_payfinish_thankyou=(TextView)findViewById(R.id.payfinish_thankyou);
		tv_payfinish_pleaseget=(TextView)findViewById(R.id.payfinish_pleaseget);
		tv_payfinish_enjoy=(TextView)findViewById(R.id.payfinish_enjoy);
		tv_payfinish_countdown=(TextView)findViewById(R.id.payfinish_countdowm);
		tv_payfinish_thankyou.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_payfinish_pleaseget.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_payfinish_enjoy.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_payfinish_countdown.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		
		//箭头的跳动动画
		imv_payfinish_arrow=(ImageView)findViewById(R.id.payfinish_arrow);
		ObjectAnimator payfinishShake=ObjectAnimator.ofFloat(imv_payfinish_arrow, "translationY", 0,-40,40,-40,40,-40,40,-40,40,-40,40,0);
		payfinishShake.setDuration(4500);
		payfinishShake.start();
		
		//倒计时
		handler.postDelayed(payfinish_countdown, 1000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pay_finish, menu);
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
