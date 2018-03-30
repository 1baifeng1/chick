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
			{"黄焖鸡","口水鸡","原味排骨","辣味排骨"},
			{"米饭"},
			{"果粒橙","脉动","海之言","可乐","雪碧","矿泉水"},//二级菜单单项最多六个
			{"土豆丝","酸白菜","咸菜"},
			{"紫菜鸡蛋汤","海带汤","冬瓜汤"}
	};//模拟二级菜单
	OrderMenuShow[][] orderMenuB={
			{new OrderMenuShow("黄焖鸡", R.drawable.order_food_1, 14f),new OrderMenuShow("口水鸡", R.drawable.order_food_1, 15),
				new OrderMenuShow("原味排骨", R.drawable.order_food_1, 17),new OrderMenuShow("辣味排骨", R.drawable.order_food_1, 19)},
			{new OrderMenuShow("米饭", R.drawable.order_rice, 1)},
			{new OrderMenuShow("果粒橙", R.drawable.order_food_guolicheng, 4),new OrderMenuShow("脉动", R.drawable.order_food_maidong, 4),
				new OrderMenuShow("海之言", R.drawable.order_food_haizhiyan, 4),new OrderMenuShow("可乐", R.drawable.order_food_cola, 3),
				new OrderMenuShow("雪碧", R.drawable.order_food_xuebi, 3),new OrderMenuShow("矿泉水", R.drawable.order_food_springwater, 2)},
			{new OrderMenuShow("土豆丝", R.drawable.order_food_xiancai, 0f),new OrderMenuShow("酸白菜", R.drawable.order_food_xiancai, 0f),
				new OrderMenuShow("咸菜", R.drawable.order_food_xiancai, 0f)},
			{new OrderMenuShow("紫菜鸡蛋汤", R.drawable.order_soup, 0f),new OrderMenuShow("海带汤", R.drawable.order_soup, 0f),
				new OrderMenuShow("冬瓜汤", R.drawable.order_soup, 0f)}
	};//模拟二级菜单数据
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 一级菜单的五个选项
	 */
	TextView tv_order_menu_a1,tv_order_menu_a2,tv_order_menu_a3,tv_order_menu_a4,tv_order_menu_a5;
	/**
	 * 二级菜单最多六个选项
	 */
	TextView tv_order_menu_b1,tv_order_menu_b2,tv_order_menu_b3,tv_order_menu_b4,tv_order_menu_b5,tv_order_menu_b6;
	TextView[] tv_order_menu_b;
	/** 整个一级菜单 */
	LinearLayout ll_order_menu_a;
	/** 整个二级菜单  */
	LinearLayout ll_order_menu_b;
	/** 二级菜单下落 */
	Order_drop orderDrop;
	/** 是否是第一次点击一级菜单 */
	boolean isFirstClickMenuA=true;
	
	/**
	 * 整个菜品展示部分
	 */
	LinearLayout ll_order_menu_show;
	/** 菜品铭牌 */
	TextView tv_order_menu_name;
	/** 菜品展示图片 */
	RelativeLayout rl_order_menu_food;
	/** 购物说明 */
	TextView tv_order_shoplistdetails;
	/** 菜品价格 */
	TextView tv_order_price_shi,tv_order_price_ge,tv_order_price_jiao;
	/** 小菜图片 */
	ImageView img_order_menu_relish; 
	/** 菜品图片 */
	ImageView img_order_menu_dishAndDrink;
	/** 汤图片 */
	ImageView img_order_menu_soup;
	/** 主食图片 */
	ImageView img_order_menu_staple;
	
	/** 购物清单，其实就是目前选了几个菜 */
	TextView tv_order_shoppinglist;
	
	static int indexA=0;//用来指向一级菜单
	static int indexB=0;//用来指向二级菜单
	
	/**
	 * 目前菜品
	 */
	OrderMenuShow currentMenuShow=orderMenuB[indexA][indexB];
	/**
	 * 已经选择的菜品
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
		tv_order_menu_a5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));//给一级菜单的五个菜品设置字体
		tv_order_menu_name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));//给菜品铭牌设置字体
		tv_order_shoplistdetails.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));//给购物说明设置字体
		tv_order_shoppinglist.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));//给购物说明设置字体
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
		
		//保证每次进入后直接点击“购买”可以选中当前的默认商品
		indexA = 0;
		indexB = 0;
		currentMenuShow = orderMenuB[indexA][indexB];
		
		//设置单价
		setPrice();
		//设置菜品图片的初始显示图片
		img_order_menu_dishAndDrink.setBackgroundResource(orderMenuB[0][0].getOrder_menu_imageId());
		
		//响应一级菜单栏点击事件
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
		
		//从AccountActivity获取更改后的订单
		al_OrderedMenu = (ArrayList<AccountMenuShow>) getIntent().getSerializableExtra("allAccount");
//		if(!(al_OrderedMenu.isEmpty())){
		if(al_OrderedMenu != null){
			setShopDetailsList();
		}else{
			al_OrderedMenu=new ArrayList<AccountMenuShow>();
		}
//		System.out.println("!!!"+al_OrderedMenu.isEmpty());
		
		
		
		//响应 “购买” 事件
		findViewById(R.id.order_buy).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//将当前菜品加入到已购清单中
				AccountMenuShow ams = new AccountMenuShow();
				ams.setAccount_menu_name(currentMenuShow.getOrder_menu_name());
				ams.setAccount_menu_price(currentMenuShow.getOrder_menu_price());
				if (!(al_OrderedMenu.contains(ams))) {//不存在该菜
					ams.setAccount_menu_count(1);
					al_OrderedMenu.add(ams);
				} else {//已经存在该菜
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
				
				//将当前菜品的名字加入到  “购物说明” 中
				StringBuilder oldMenu=new StringBuilder(tv_order_shoplistdetails.getText().toString());
				StringBuilder newMenu=new StringBuilder();
				if(oldMenu==null){//第一次选择购买时直接加入
					newMenu.append(currentMenuShow.getOrder_menu_name()).append("X1\n");
				}else{//第二次及以后要判断这次选择的菜品是否已经选择过
					String[] str_OrderedMenu=oldMenu.toString().split("\n");
					boolean isExist=false;
					for(int i=0;i<str_OrderedMenu.length;i++){
						String[] orderMenu_NameAndCount=str_OrderedMenu[i].split("X");
						if(orderMenu_NameAndCount[0].equals(currentMenuShow.getOrder_menu_name())){
							System.out.println("!!!"+orderMenu_NameAndCount[0]+"..."+orderMenu_NameAndCount[1]);
							newMenu.append(orderMenu_NameAndCount[0]).append("X").append(Integer.parseInt(orderMenu_NameAndCount[1])+1).append("\n");
							isExist=true;//已经选择过了，把数量+1
						}else//这次的不是，直接加进去，进入下次循环
							newMenu.append(str_OrderedMenu[i]+"\n");
					}
					if(!isExist)//经过之前的判断，这次选择的菜品没有购买过
						newMenu.append(currentMenuShow.getOrder_menu_name()+"X1");
				}
				tv_order_shoplistdetails.setText(newMenu);
				
				//将购物清单+1(显示当前购菜数量)
				tv_order_shoppinglist.setBackground(getResources().getDrawable(R.drawable.order_shoppinglist));
				tv_order_shoppinglist.setText(""+al_OrderedMenu.size());
			}
		});
		
		//响应 “已购清单” 点击事件（直接进入结算页）
		findViewById(R.id.order_shoppinglist).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(al_OrderedMenu.size()==0){
					Toast.makeText(getApplicationContext(), "您还没有选择任何菜品", 1).show();
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
	 * 将从AccountActivity传过来的菜单重新解析成字符串
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
	 * 设置一级菜单的五个选项可点与否
	 * @param b true可点；false不可点
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
	 * 点击一级菜单上的菜品
	 * @param v 操作的view
	 */
	private void tv_order_menu_a_click( View v){
		indexA=v.getId()-R.id.order_menu_a1;//点击的是第几个,从0开始数
		setTvOrderMenuAClickable(false);
		
		if(isFirstClickMenuA)
			isFirstClickMenuA=false;
		else{//如果不是第一次进入，就先偷偷地让它转回去
			ll_order_menu_b.setVisibility(View.GONE);
			
			ObjectAnimator orderShakeBack=ObjectAnimator.ofFloat(ll_order_menu_b, "rotation", 0);
			ll_order_menu_b.setPivotX(132);
			ll_order_menu_b.setPivotY(5);
			orderShakeBack.setDuration(10);
			orderShakeBack.start();
		}
		
//		tv_order_menu_b=new TextView[menuB[index].length];
		tv_order_menu_b=new TextView[6];
		for(indexB=0;indexB<orderMenuB[indexA].length;indexB++){//将存在的数据写入并显示
			tv_order_menu_b[indexB]=(TextView)findViewById(R.id.order_menu_b1+indexB);
			tv_order_menu_b[indexB].setVisibility(View.VISIBLE);
			tv_order_menu_b[indexB].setText(orderMenuB[indexA][indexB].getOrder_menu_name());
			tv_order_menu_b[indexB].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
			
			//响应二级菜单中点击菜品事件
			tv_order_menu_b[indexB].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//如果所点击的菜品和目前正在展示的菜品一样则无效果，不一样时才切换菜品
					if(!(orderMenuB[indexA][v.getId()-tv_order_menu_b[0].getId()].getOrder_menu_name().equals(currentMenuShow.getOrder_menu_name())))
						tv_order_menu_b_click(v);
				}
			});
		}
		indexB--;//在上面的循环结束后indexB超出了范围，在这里手动减一可以让其回归正常值
		
		for(int i=orderMenuB[indexA].length;i<6;i++){//把后面的数据清空并隐藏
			tv_order_menu_b[i]=(TextView)findViewById(R.id.order_menu_b1+i);
			tv_order_menu_b[i].setVisibility(View.GONE);
		}
		ll_order_menu_b.setVisibility(View.VISIBLE);
		
		//下落并会上下跳动动画
		orderDrop=new Order_drop();
		orderDrop.setDuration(1500);
		orderDrop.setLength(0, -2000);
		ll_order_menu_b.startAnimation(orderDrop);
		
		//下落完成后左后晃动动画
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
	 * 点击二级菜单上的菜品
	 * @param v 被点的TextView
	 */
	private void tv_order_menu_b_click(final View v){
		
//		Toast.makeText(getApplicationContext(), v.getId()+"", 1).show();//测试用
		//菜品展示部分 滑出界面 动画
		Order_slideout orderSlideOut=new Order_slideout();
		orderSlideOut.setDuration(800);
		orderSlideOut.setLength(800, 0);
		
		
		//选择不同，进行切换到图形也不同
		switch(indexA){
		case 1://主食
			v_sport = img_order_menu_staple;
			break;
		case 3://小菜
			v_sport = img_order_menu_relish;
			break;
		case 4://汤
			v_sport = img_order_menu_soup;
			break;
		case 0://菜品
		case 2://饮料
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
				tv_order_menu_name.setText(orderMenuB[indexA][v.getId()-tv_order_menu_b[0].getId()].getOrder_menu_name());//设置菜品铭牌
				v_sport.setBackgroundResource(orderMenuB[indexA][v.getId()-tv_order_menu_b[0].getId()].getOrder_menu_imageId());
//				rl_order_menu_food.setBackgroundResource(orderMenuB[indexA][v.getId()-tv_order_menu_b[0].getId()].getOrder_menu_imageId());
				
				//菜品展示部分 滑入界面 动画
				Order_slidein orderSlideIn=new Order_slidein();
				orderSlideIn.setDuration(800);
				orderSlideIn.setLength(800, 0);
//				ll_order_menu_show.startAnimation(orderSlideIn);
				v_sport.startAnimation(orderSlideIn);
				
				//将当前的菜品保存起来
				currentMenuShow=orderMenuB[indexA][v.getId()-tv_order_menu_b[0].getId()];
				
				//设置单价
				setPrice();
			}
		});
	}
	
	/**
	 * 设置价签上的十位、个位、角位
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
 * 菜品展示类，专门用于菜单：目前只有1：名称，2：图片id，3：价格
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
