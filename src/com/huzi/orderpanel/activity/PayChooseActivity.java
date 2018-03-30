package com.huzi.orderpanel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.util.*;

public class PayChooseActivity extends Activity {
	
	public static final int REQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_choose);
		
		Intent intentAccount=getIntent();
		final float totalPrice=intentAccount.getFloatExtra("total", 0);
		final ArrayList allAccount = (ArrayList) intentAccount.getSerializableExtra("allAccount");
//		TextView tvGetPay =(TextView) findViewById(R.id.getpay);
//		tvGetPay.setText(totalPrice+"");//测试总价传过来没有，正确
		
		findViewById(R.id.paychoose_zhifubao).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentZhifubao=new Intent(PayChooseActivity.this,PayZhifubaoActivity.class);
				intentZhifubao.putExtra("total", totalPrice);
				intentZhifubao.putParcelableArrayListExtra("allAccount", allAccount);
//				startActivity(intentZhifubao);
				startActivityForResult(intentZhifubao, REQUEST_CODE);
				overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
				
				//为了连接支付宝接口，可以把totalPrice传过去，如果需要所有的菜单，从AccountActivity那传过来就可以了
			}
		});
		findViewById(R.id.paychoose_weixin).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentWeixin=new Intent(PayChooseActivity.this,PayWeixinActivity.class);
				intentWeixin.putExtra("total", totalPrice);
				intentWeixin.putExtra("allAccount", allAccount);
				startActivity(intentWeixin);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				
				//为了连接微信接口，可以把totalPrice传过去，如果需要所有的菜单，从AccountActivity那传过来就可以了
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//从PayZhifubaoActivity或PayWeixinActivity过来，到PayFinishActivity结束
		Intent intentPayFinish = new Intent();
		intentPayFinish.setClass(getApplicationContext(), PayFinishActivity.class);
		startActivity(intentPayFinish);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pay_choose, menu);
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
