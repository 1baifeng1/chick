package com.huzi.orderpanel.activity;

import java.util.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class PayWeixinActivity extends Activity {
	TextView tv_pay_zhifudingdan,tv_pay_pleasepay,tv_pay_countdown;
	
	/**
	 * 倒计时
	 */
	Handler handler=new Handler();
	Runnable countdown=new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int currentNum=Integer.parseInt(tv_pay_countdown.getText().toString())-1;
			if(currentNum==0){
				Intent intentPayFinish=new Intent();
				intentPayFinish.setClass(getApplicationContext(), PayFinishActivity.class);
				startActivity(intentPayFinish);
				finish();
			}else{
				tv_pay_countdown.setText(currentNum+"");
				handler.postDelayed(countdown, 1000);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_zhifubao_weixin);
		
		tv_pay_zhifudingdan=(TextView)findViewById(R.id.pay_zhifudingdan);
		tv_pay_pleasepay=(TextView)findViewById(R.id.pay_pleasepay);
		tv_pay_countdown=(TextView)findViewById(R.id.pay_countdown);
		tv_pay_zhifudingdan.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_pay_pleasepay.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_pay_countdown.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		
		//倒计时
		tv_pay_countdown.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				handler.postDelayed(countdown, 1000);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pay_weixin, menu);
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
