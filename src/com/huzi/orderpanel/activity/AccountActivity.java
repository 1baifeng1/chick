package com.huzi.orderpanel.activity;

import java.util.*;

import com.huzi.orderpanel.customview.AccountMenuShow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

public class AccountActivity extends Activity {
	/**
	 * �����Ĳ˵�
	 */
	ArrayList<AccountMenuShow> al_amount;
	
	/**
	 * ��ʾ���ж�����ListView
	 */
	ListView lvAccount;
	AccountAdapter accountAdapter;
	
	TextView tv_account_yiyouhui,tv_account_favour,tv_account_gongji,tv_account_total;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		
		//���������Ĳ˵�������������ͬ�Ĳ˷ŵ�һ��
		al_amount=(ArrayList<AccountMenuShow>)getIntent().getSerializableExtra("account");
//		al_amount=new ArrayList();//�����Ķ���
//		Iterator it_account=al_account.iterator();
//		while(it_account.hasNext()){
//			OrderMenuShow oms=(OrderMenuShow)it_account.next();//�������Ĳ˵�
//			AccountMenuShow ams=new AccountMenuShow();//�����Ĳ˵�
//			
//			ams.setAccount_menu_name(oms.getOrder_menu_name());
//			ams.setAccount_menu_price(oms.getOrder_menu_price());
//			
//			if(!(al_amount.contains(ams))){//��������ڣ��Ͱ��������
//				ams.setAccount_menu_count(1);
//				al_amount.add(ams);
//			}else{//������ڣ����ҳ�������������+1���Ѿɵ�������������µ����ݼ���
//				Iterator it_amount=al_amount.iterator();
//				while(it_amount.hasNext()){
//					AccountMenuShow tempAMS=(AccountMenuShow) it_amount.next();
//					if(ams.equals(tempAMS)){
//						ams.setAccount_menu_count(tempAMS.getAccount_menu_count()+1);
//						al_amount.remove(tempAMS);
//						al_amount.add(ams);
//					}
//				}
//			}
//		}
		
		//���������������Ƿ���ȷ�������飬��ȷ��
//		Iterator itTemp=al_amount.iterator();
//		StringBuilder sb=new StringBuilder();
//		while(itTemp.hasNext()){
//			AccountMenuShow ams=(AccountMenuShow) itTemp.next();
//			sb.append(ams.getAccount_menu_name()+":"+ams.getAccount_menu_count()+"--"+ams.getAccount_menu_price()+"\t");
//		}
//		System.out.println("!!!"+sb.toString());

		//��������ListView���а�
		lvAccount=(ListView)findViewById(R.id.account);
		accountAdapter=new AccountAdapter(AccountActivity.this);
		lvAccount.setAdapter(accountAdapter);
		
		//���á����Żݡ��͡��ܼơ�����
		tv_account_yiyouhui=(TextView) findViewById(R.id.account_yiyouhui);
		tv_account_favour=(TextView) findViewById(R.id.account_favour);
		tv_account_gongji=(TextView) findViewById(R.id.account_gongji);
		tv_account_total=(TextView) findViewById(R.id.account_total);
		tv_account_yiyouhui.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_account_favour.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_account_gongji.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_account_total.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		
		//�����ܼ�
		setTotalPrice();
		
		//��Ӧ ��ȷ���� ����¼�
		findViewById(R.id.acount_confirm).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strTotalPrice=tv_account_total.getText().toString();
				float totalPrice=Float.parseFloat(strTotalPrice.substring(1, strTotalPrice.length()));
				Intent intentPayChoose=new Intent();
				intentPayChoose.putExtra("total", totalPrice);
				intentPayChoose.putExtra("allAccount", al_amount);
				intentPayChoose.setClass(getApplicationContext(), PayChooseActivity.class);
				startActivity(intentPayChoose);
				finish();
			}
		});
		
		findViewById(R.id.acount_return).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentOrderActivity = new Intent();
				intentOrderActivity.putExtra("allAccount", al_amount);
				intentOrderActivity.setClass(getApplicationContext(), OrderActivity.class);
				startActivity(intentOrderActivity);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
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
	
	/**
	 * �����ܼ�
	 */
	private void setTotalPrice(){
		float total=0;
		Iterator it=al_amount.iterator();
		while(it.hasNext()){
			AccountMenuShow ams=(AccountMenuShow)it.next();
			total+=ams.getAccount_menu_count()*ams.getAccount_menu_price();
		}
		tv_account_total.setText("��"+total);
	}
	
	/**
	 * �������ҳ������ListView��ʾ����ѡ��Ʒ
	 * @author SXH
	 */
	class AccountAdapter extends BaseAdapter{
		TextView tvAccountMenuName,tvAccountMenuCount,tvAccountMenuPrice;
		
		ImageView imvAccountAdd,imvAccountSubtract;
		
		private Context context;
		AccountAdapter(Context context){
			this.context=context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return al_amount.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return al_amount.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout llEveryOrder=null;
			ViewHolder vh;
			
			//����Ѿ����ˣ��Ͱ��µķŽ�ȥ�����û�У����½�һ��
			if(convertView!=null){
				llEveryOrder=(LinearLayout)convertView;
				
				vh=(ViewHolder)convertView.getTag();
			}else{
				llEveryOrder=(LinearLayout)LayoutInflater.from(context).inflate(R.layout.account_listview, null);
				
				vh=new ViewHolder();
				vh.substract=(ImageView)llEveryOrder.findViewById(R.id.account_subtract);
				vh.add=(ImageView)llEveryOrder.findViewById(R.id.account_add);
				llEveryOrder.setTag(vh);
			}
			tvAccountMenuName=(TextView)llEveryOrder.findViewById(R.id.account_menu_name);
			tvAccountMenuCount=(TextView)llEveryOrder.findViewById(R.id.account_menu_count);
			tvAccountMenuPrice=(TextView)llEveryOrder.findViewById(R.id.account_menu_price);
//			imvAccountSubtract=(ImageView)llEveryOrder.findViewById(R.id.account_subtract);
//			imvAccountAdd=(ImageView)llEveryOrder.findViewById(R.id.account_add);
			AccountMenuShow ams=(AccountMenuShow) getItem(position);

			tvAccountMenuName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
			tvAccountMenuCount.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
			tvAccountMenuPrice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
			tvAccountMenuName.setText(ams.getAccount_menu_name().toString());
			tvAccountMenuCount.setText(ams.getAccount_menu_count()+"");
			tvAccountMenuPrice.setText("��"+ams.getAccount_menu_price());

//			vh.substract.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					tvAccountMenuCount.setText("0");
//					
//				}
//			});
			vh.substract.setOnClickListener(new SubAndAddClick(position));
			vh.add.setOnClickListener(new SubAndAddClick(position));
			
			return llEveryOrder;
		}
		
	}
	
	public static class ViewHolder{
		ImageView substract;
		ImageView add;
	}
	
	class SubAndAddClick implements OnClickListener{
		int position;
		public SubAndAddClick(int position){
			this.position=position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			Toast.makeText(AccountActivity.this, position+"", 1).show();//����position��
			
			AccountMenuShow ams=(AccountMenuShow)al_amount.get(position);
			al_amount.remove(position);
			if(v.getId()==R.id.account_subtract){//��
				if((ams.getAccount_menu_count()-1)!=0){//���-1����0���Ϳ��԰��µ����ݼ���
					ams.setAccount_menu_count(ams.getAccount_menu_count()-1);
					al_amount.add(position, ams);
				}
			}else if(v.getId()==R.id.account_add){//��
				ams.setAccount_menu_count(ams.getAccount_menu_count()+1);
				al_amount.add(position, ams);
			}
			accountAdapter.notifyDataSetChanged();
			setTotalPrice();
		}
		
	}
}

