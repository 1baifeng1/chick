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
//		tvGetPay.setText(totalPrice+"");//�����ܼ۴�����û�У���ȷ
		
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
				
				//Ϊ������֧�����ӿڣ����԰�totalPrice����ȥ�������Ҫ���еĲ˵�����AccountActivity�Ǵ������Ϳ�����
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
				
				//Ϊ������΢�Žӿڣ����԰�totalPrice����ȥ�������Ҫ���еĲ˵�����AccountActivity�Ǵ������Ϳ�����
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//��PayZhifubaoActivity��PayWeixinActivity��������PayFinishActivity����
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
