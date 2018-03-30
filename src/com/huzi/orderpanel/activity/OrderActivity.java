package com.huzi.orderpanel.activity;

import java.io.Serializable;
import java.util.*;

import com.huzi.orderpanel.activity.R;
import com.huzi.orderpanel.animation.*;
import com.huzi.orderpanel.customview.AccountMenuShow;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.*;

public class OrderActivity extends Activity {
	String[][] menuB={
			{"���˼�","��ˮ��","ԭζ�Ź�","��ζ�Ź�"},
			{"�׷�"},
			{"������","����","��֮��","����","ѩ��","��Ȫˮ"},//�����˵������������
			{"����˿","��ײ�","�̲�"},
			{"�ϲ˼�����","������","������"}
	};//ģ������˵�
	OrderMenuShow[][] orderMenuB={
			{new OrderMenuShow("���˼�", R.drawable.order_food_1, 14f),new OrderMenuShow("��ˮ��", R.drawable.order_food_1, 15),
				new OrderMenuShow("ԭζ�Ź�", R.drawable.order_food_1, 17),new OrderMenuShow("��ζ�Ź�", R.drawable.order_food_1, 19)},
			{new OrderMenuShow("�׷�", R.drawable.order_rice, 1)},
			{new OrderMenuShow("������", R.drawable.order_food_guolicheng, 4),new OrderMenuShow("����", R.drawable.order_food_maidong, 4),
				new OrderMenuShow("��֮��", R.drawable.order_food_haizhiyan, 4),new OrderMenuShow("����", R.drawable.order_food_cola, 3),
				new OrderMenuShow("ѩ��", R.drawable.order_food_xuebi, 3),new OrderMenuShow("��Ȫˮ", R.drawable.order_food_springwater, 2)},
			{new OrderMenuShow("����˿", R.drawable.order_food_xiancai, 0f),new OrderMenuShow("��ײ�", R.drawable.order_food_xiancai, 0f),
				new OrderMenuShow("�̲�", R.drawable.order_food_xiancai, 0f)},
			{new OrderMenuShow("�ϲ˼�����", R.drawable.order_soup, 0f),new OrderMenuShow("������", R.drawable.order_soup, 0f),
				new OrderMenuShow("������", R.drawable.order_soup, 0f)}
	};//ģ������˵�����
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * һ���˵������ѡ��
	 */
	TextView tv_order_menu_a1,tv_order_menu_a2,tv_order_menu_a3,tv_order_menu_a4,tv_order_menu_a5;
	/**
	 * �����˵��������ѡ��
	 */
	TextView tv_order_menu_b1,tv_order_menu_b2,tv_order_menu_b3,tv_order_menu_b4,tv_order_menu_b5,tv_order_menu_b6;
	TextView[] tv_order_menu_b;
	/** ����һ���˵� */
	LinearLayout ll_order_menu_a;
	/** ���������˵�  */
	LinearLayout ll_order_menu_b;
	/** �����˵����� */
	Order_drop orderDrop;
	/** �Ƿ��ǵ�һ�ε��һ���˵� */
	boolean isFirstClickMenuA=true;
	
	/**
	 * ������Ʒչʾ����
	 */
	LinearLayout ll_order_menu_show;
	/** ��Ʒ���� */
	TextView tv_order_menu_name;
	/** ��ƷչʾͼƬ */
	RelativeLayout rl_order_menu_food;
	/** ����˵�� */
	TextView tv_order_shoplistdetails;
	/** ��Ʒ�۸� */
	TextView tv_order_price_shi,tv_order_price_ge,tv_order_price_jiao;
	/** С��ͼƬ */
	ImageView img_order_menu_relish; 
	/** ��ƷͼƬ */
	ImageView img_order_menu_dishAndDrink;
	/** ��ͼƬ */
	ImageView img_order_menu_soup;
	/** ��ʳͼƬ */
	ImageView img_order_menu_staple;
	
	/** �����嵥����ʵ����Ŀǰѡ�˼����� */
	TextView tv_order_shoppinglist;
	
	static int indexA=0;//����ָ��һ���˵�
	static int indexB=0;//����ָ������˵�
	
	/**
	 * Ŀǰ��Ʒ
	 */
	OrderMenuShow currentMenuShow=orderMenuB[indexA][indexB];
	/**
	 * �Ѿ�ѡ��Ĳ�Ʒ
	 */
	ArrayList<AccountMenuShow> al_OrderedMenu = null;
	
	View v_sport = null ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		
		tv_order_menu_a1=(TextView)findViewById(R.id.order_menu_a1);
		tv_order_menu_a2=(TextView)findViewById(R.id.order_menu_a2);
		tv_order_menu_a3=(TextView)findViewById(R.id.order_menu_a3);
		tv_order_menu_a4=(TextView)findViewById(R.id.order_menu_a4);
		tv_order_menu_a5=(TextView)findViewById(R.id.order_menu_a5);
		tv_order_menu_name=(TextView)findViewById(R.id.order_menu_name);
		tv_order_shoplistdetails=(TextView)findViewById(R.id.order_shoplistdetails);
		tv_order_shoppinglist=(TextView)findViewById(R.id.order_shoppinglist);
		tv_order_menu_a1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_order_menu_a2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_order_menu_a3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_order_menu_a4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_order_menu_a5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));//��һ���˵��������Ʒ��������
		tv_order_menu_name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));//����Ʒ������������
		tv_order_shoplistdetails.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));//������˵����������
		tv_order_shoppinglist.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));//������˵����������
		tv_order_price_shi=(TextView)findViewById(R.id.order_price_shi);
		tv_order_price_ge=(TextView)findViewById(R.id.order_price_ge);
		tv_order_price_jiao=(TextView)findViewById(R.id.order_price_jiao);
		tv_order_price_jiao.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		tv_order_price_shi.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/SimHei.ttf"));
		tv_order_price_ge.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/SimHei.ttf"));
		tv_order_price_jiao.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/SimHei.ttf"));
		
		img_order_menu_relish = (ImageView)findViewById(R.id.order_relish);
		img_order_menu_soup = (ImageView)findViewById(R.id.order_soup);
		img_order_menu_staple = (ImageView)findViewById(R.id.order_staple);
		img_order_menu_dishAndDrink = (ImageView)findViewById(R.id.order_dishAndDrink);
		
		ll_order_menu_a = (LinearLayout)findViewById(R.id.ll_order_menu_a);
		ll_order_menu_b=(LinearLayout)findViewById(R.id.ll_order_menu_b);
		ll_order_menu_show=(LinearLayout)findViewById(R.id.order_menu_show);
		rl_order_menu_food=(RelativeLayout)findViewById(R.id.order_menu_food);
		
		//��֤ÿ�ν����ֱ�ӵ�������򡱿���ѡ�е�ǰ��Ĭ����Ʒ
		indexA = 0;
		indexB = 0;
		currentMenuShow = orderMenuB[indexA][indexB];
		
		//���õ���
		setPrice();
		//���ò�ƷͼƬ�ĳ�ʼ��ʾͼƬ
		img_order_menu_dishAndDrink.setBackgroundResource(orderMenuB[0][0].getOrder_menu_imageId());
		
		//��Ӧһ���˵�������¼�
		tv_order_menu_a1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_order_menu_a_click(v);
			}
		});
		tv_order_menu_a2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_order_menu_a_click(v);
			}
		});
		tv_order_menu_a3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_order_menu_a_click(v);
			}
		});
		tv_order_menu_a4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_order_menu_a_click(v);
			}
		});
		tv_order_menu_a5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_order_menu_a_click(v);
			}
		});
		
		//��AccountActivity��ȡ���ĺ�Ķ���
		al_OrderedMenu = (ArrayList<AccountMenuShow>) getIntent().getSerializableExtra("allAccount");
//		if(!(al_OrderedMenu.isEmpty())){
		if(al_OrderedMenu != null){
			setShopDetailsList();
		}else{
			al_OrderedMenu=new ArrayList<AccountMenuShow>();
		}
//		System.out.println("!!!"+al_OrderedMenu.isEmpty());
		
		
		
		//��Ӧ ������ �¼�
		findViewById(R.id.order_buy).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//����ǰ��Ʒ���뵽�ѹ��嵥��
				AccountMenuShow ams = new AccountMenuShow();
				ams.setAccount_menu_name(currentMenuShow.getOrder_menu_name());
				ams.setAccount_menu_price(currentMenuShow.getOrder_menu_price());
				if (!(al_OrderedMenu.contains(ams))) {//�����ڸò�
					ams.setAccount_menu_count(1);
					al_OrderedMenu.add(ams);
				} else {//�Ѿ����ڸò�
//					Iterator it = al_OrderedMenu.iterator();
//					while(it.hasNext()){
//						AccountMenuShow tempAms = (AccountMenuShow)it.next();
//						if (ams.equals(tempAms)) {
//							ams.setAccount_menu_count(tempAms.getAccount_menu_count()+1);
//							al_OrderedMenu.remove(tempAms);
//							al_OrderedMenu.add(ams);
//						}
//					}
					
					AccountMenuShow tempAms = al_OrderedMenu.get(al_OrderedMenu.indexOf(ams));
					tempAms.setAccount_menu_count(tempAms.getAccount_menu_count()+1);
					al_OrderedMenu.remove(al_OrderedMenu.indexOf(ams));
					al_OrderedMenu.add(tempAms);
				}
				
				//����ǰ��Ʒ�����ּ��뵽  ������˵���� ��
				StringBuilder oldMenu=new StringBuilder(tv_order_shoplistdetails.getText().toString());
				StringBuilder newMenu=new StringBuilder();
				if(oldMenu==null){//��һ��ѡ����ʱֱ�Ӽ���
					newMenu.append(currentMenuShow.getOrder_menu_name()).append("X1\n");
				}else{//�ڶ��μ��Ժ�Ҫ�ж����ѡ��Ĳ�Ʒ�Ƿ��Ѿ�ѡ���
					String[] str_OrderedMenu=oldMenu.toString().split("\n");
					boolean isExist=false;
					for(int i=0;i<str_OrderedMenu.length;i++){
						String[] orderMenu_NameAndCount=str_OrderedMenu[i].split("X");
						if(orderMenu_NameAndCount[0].equals(currentMenuShow.getOrder_menu_name())){
							System.out.println("!!!"+orderMenu_NameAndCount[0]+"..."+orderMenu_NameAndCount[1]);
							newMenu.append(orderMenu_NameAndCount[0]).append("X").append(Integer.parseInt(orderMenu_NameAndCount[1])+1).append("\n");
							isExist=true;//�Ѿ�ѡ����ˣ�������+1
						}else//��εĲ��ǣ�ֱ�Ӽӽ�ȥ�������´�ѭ��
							newMenu.append(str_OrderedMenu[i]+"\n");
					}
					if(!isExist)//����֮ǰ���жϣ����ѡ��Ĳ�Ʒû�й����
						newMenu.append(currentMenuShow.getOrder_menu_name()+"X1");
				}
				tv_order_shoplistdetails.setText(newMenu);
				
				//�������嵥+1(��ʾ��ǰ��������)
				tv_order_shoppinglist.setBackground(getResources().getDrawable(R.drawable.order_shoppinglist));
				tv_order_shoppinglist.setText(""+al_OrderedMenu.size());
			}
		});
		
		//��Ӧ ���ѹ��嵥�� ����¼���ֱ�ӽ������ҳ��
		findViewById(R.id.order_shoppinglist).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(al_OrderedMenu.size()==0){
					Toast.makeText(getApplicationContext(), "����û��ѡ���κβ�Ʒ", 1).show();
				}else{
					Intent intAccount=new Intent();
//					intAccount.putParcelableArrayListExtra("account", (ArrayList<? extends Parcelable>) al_OrderedMenu);
					intAccount.putExtra("account", al_OrderedMenu);
					
					intAccount.setClass(OrderActivity.this, AccountActivity.class);
					startActivity(intAccount);
					finish();
				}
			}
		});
	}

	/**
	 * ����AccountActivity�������Ĳ˵����½������ַ���
	 */
	private void setShopDetailsList() {
		// TODO Auto-generated method stub
		StringBuilder sb_menu = new StringBuilder();
		Iterator it = al_OrderedMenu.iterator();
		while(it.hasNext()){
			AccountMenuShow ams = (AccountMenuShow)it.next();
			sb_menu.append(ams.getAccount_menu_name()+"X"+ams.getAccount_menu_count()+"\n");
			tv_order_shoplistdetails.setText(sb_menu.toString());
		}
	}
	
	/**
	 * ����һ���˵������ѡ��ɵ����
	 * @param b true�ɵ㣻false���ɵ�
	 */
	private void setTvOrderMenuAClickable(boolean b){
		tv_order_menu_a1.setClickable(b);
		tv_order_menu_a2.setClickable(b);
		tv_order_menu_a3.setClickable(b);
		tv_order_menu_a4.setClickable(b);
		tv_order_menu_a5.setClickable(b);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order, menu);
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
	 * ���һ���˵��ϵĲ�Ʒ
	 * @param v ������view
	 */
	private void tv_order_menu_a_click( View v){
		indexA=v.getId()-R.id.order_menu_a1;//������ǵڼ���,��0��ʼ��
		setTvOrderMenuAClickable(false);
		
		if(isFirstClickMenuA)
			isFirstClickMenuA=false;
		else{//������ǵ�һ�ν��룬����͵͵������ת��ȥ
			ll_order_menu_b.setVisibility(View.GONE);
			
			ObjectAnimator orderShakeBack=ObjectAnimator.ofFloat(ll_order_menu_b, "rotation", 0);
			ll_order_menu_b.setPivotX(132);
			ll_order_menu_b.setPivotY(5);
			orderShakeBack.setDuration(10);
			orderShakeBack.start();
		}
		
//		tv_order_menu_b=new TextView[menuB[index].length];
		tv_order_menu_b=new TextView[6];
		for(indexB=0;indexB<orderMenuB[indexA].length;indexB++){//�����ڵ�����д�벢��ʾ
			tv_order_menu_b[indexB]=(TextView)findViewById(R.id.order_menu_b1+indexB);
			tv_order_menu_b[indexB].setVisibility(View.VISIBLE);
			tv_order_menu_b[indexB].setText(orderMenuB[indexA][indexB].getOrder_menu_name());
			tv_order_menu_b[indexB].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
			
			//��Ӧ�����˵��е����Ʒ�¼�
			tv_order_menu_b[indexB].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//���������Ĳ�Ʒ��Ŀǰ����չʾ�Ĳ�Ʒһ������Ч������һ��ʱ���л���Ʒ
					if(!(orderMenuB[indexA][v.getId()-tv_order_menu_b[0].getId()].getOrder_menu_name().equals(currentMenuShow.getOrder_menu_name())))
						tv_order_menu_b_click(v);
				}
			});
		}
		indexB--;//�������ѭ��������indexB�����˷�Χ���������ֶ���һ��������ع�����ֵ
		
		for(int i=orderMenuB[indexA].length;i<6;i++){//�Ѻ����������ղ�����
			tv_order_menu_b[i]=(TextView)findViewById(R.id.order_menu_b1+i);
			tv_order_menu_b[i].setVisibility(View.GONE);
		}
		ll_order_menu_b.setVisibility(View.VISIBLE);
		
		//���䲢��������������
		orderDrop=new Order_drop();
		orderDrop.setDuration(1500);
		orderDrop.setLength(0, -2000);
		ll_order_menu_b.startAnimation(orderDrop);
		
		//������ɺ����ζ�����
		orderDrop.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				setTvOrderMenuAClickable(true);
				
//				ObjectAnimator orderShake=ObjectAnimator.ofFloat(ll_order_menu_b, "rotation", 0,-12,0,8,0,-3);
//				ll_order_menu_b.setPivotX(132);
//				ll_order_menu_b.setPivotY(5);
//				orderShake.setDuration(1500);
//				orderShake.start();
//				
//				orderDrop.setAnimationListener(new Animation.AnimationListener() {
//					@Override
//					public void onAnimationStart(Animation animation) {
//						// TODO Auto-generated method stub
//					}
//					@Override
//					public void onAnimationRepeat(Animation animation) {
//						// TODO Auto-generated method stub
//					}
//					@Override
//					public void onAnimationEnd(Animation animation) {
//						// TODO Auto-generated method stub
//						
//					}
//				});
			}
		});
	}
	
	/**
	 * ��������˵��ϵĲ�Ʒ
	 * @param v �����TextView
	 */
	private void tv_order_menu_b_click(final View v){
		
//		Toast.makeText(getApplicationContext(), v.getId()+"", 1).show();//������
		//��Ʒչʾ���� �������� ����
		Order_slideout orderSlideOut=new Order_slideout();
		orderSlideOut.setDuration(800);
		orderSlideOut.setLength(800, 0);
		
		
		//ѡ��ͬ�������л���ͼ��Ҳ��ͬ
		switch(indexA){
		case 1://��ʳ
			v_sport = img_order_menu_staple;
			break;
		case 3://С��
			v_sport = img_order_menu_relish;
			break;
		case 4://��
			v_sport = img_order_menu_soup;
			break;
		case 0://��Ʒ
		case 2://����
			v_sport = img_order_menu_dishAndDrink;
			break;
		}
		
//		ll_order_menu_show.startAnimation(orderSlideOut);
		v_sport.startAnimation(orderSlideOut);
		
		orderSlideOut.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				tv_order_menu_name.setText(orderMenuB[indexA][v.getId()-tv_order_menu_b[0].getId()].getOrder_menu_name());//���ò�Ʒ����
				v_sport.setBackgroundResource(orderMenuB[indexA][v.getId()-tv_order_menu_b[0].getId()].getOrder_menu_imageId());
//				rl_order_menu_food.setBackgroundResource(orderMenuB[indexA][v.getId()-tv_order_menu_b[0].getId()].getOrder_menu_imageId());
				
				//��Ʒչʾ���� ������� ����
				Order_slidein orderSlideIn=new Order_slidein();
				orderSlideIn.setDuration(800);
				orderSlideIn.setLength(800, 0);
//				ll_order_menu_show.startAnimation(orderSlideIn);
				v_sport.startAnimation(orderSlideIn);
				
				//����ǰ�Ĳ�Ʒ��������
				currentMenuShow=orderMenuB[indexA][v.getId()-tv_order_menu_b[0].getId()];
				
				//���õ���
				setPrice();
			}
		});
	}
	
	/**
	 * ���ü�ǩ�ϵ�ʮλ����λ����λ
	 */
	private void setPrice(){
		float currentPrice=currentMenuShow.getOrder_menu_price();
		int price=(int)currentPrice;
		int price_ge=price%10;
		int price_shi=(price-price_ge)/10;
		float price_jiao=currentPrice-price;
		int price_jiao2=(int)(price_jiao*10);
		
		tv_order_price_shi.setText(price_shi+"");
		tv_order_price_ge.setText(price_ge+"");
		tv_order_price_jiao.setText("."+price_jiao2+"0");
	}
}

/**
 * ��Ʒչʾ�࣬ר�����ڲ˵���Ŀǰֻ��1�����ƣ�2��ͼƬid��3���۸�
 * @author SXH
 */
class OrderMenuShow implements Serializable{
	private String order_menu_name;
	private int order_menu_imageId;
	private float order_menu_price;
	
	public OrderMenuShow(String order_menu_name,int order_menu_imageId,float order_menu_price){
		this.order_menu_name=order_menu_name;
		this.order_menu_imageId=order_menu_imageId;
		this.order_menu_price=order_menu_price;
	}

	public String getOrder_menu_name() {
		return order_menu_name;
	}

	public void setOrder_menu_name(String order_menu_name) {
		this.order_menu_name = order_menu_name;
	}

	public int getOrder_menu_imageId() {
		return order_menu_imageId;
	}

	public void setOrder_menu_imageId(int order_menu_imageId) {
		this.order_menu_imageId = order_menu_imageId;
	}

	public float getOrder_menu_price() {
		return order_menu_price;
	}

	public void setOrder_menu_price(float order_menu_price) {
		this.order_menu_price = order_menu_price;
	}
}
