package com.huzi.orderpanel.activity;

import java.io.OutputStream;
import java.net.Socket;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huzi.orderpanel.customview.AccountMenuShow;
import com.huzi.orderpanel.customview.PayBillShow;

public class PayZhifubaoActivity extends Activity {
	TextView tv_pay_zhifudingdan,tv_pay_pleasepay,tv_pay_countdown;
	/** 二维码  */
	ImageView  imv_pay_qrcode;
	/** 总价 */
	float totalPrice;
	/** 菜品订单 */
	ArrayList allAccount;
	/** 拼接菜品 */
	StringBuilder sbAccountInfo;
	/** 账单信息 */
	PayBillShow pbs;
	
	/** 餐桌号  */
	public static final String diningTableId = "15";
	
	/** 订单计数  */
	int billCount;
	private static final String EDIT_BILL_COUNT = "editBillCount";
	private static final String BILL_COUNT = "currentBillCount";
	private static final String TODAY_DATE = "todayDate";

	/**
	 * 倒计时
	 */
	Handler handler=new Handler();
	Runnable pay_countdown=new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int currentNum=Integer.parseInt(tv_pay_countdown.getText().toString())-1;
			if(currentNum==0){
//				Intent intentPayFinish=new Intent();
//				intentPayFinish.setClass(getApplicationContext(), PayFinishActivity.class);
//				startActivity(intentPayFinish);
				finish();//倒计时结束后，经过finish（）会跳回PayChooseActivity，那里再finish跳至PayFinishActivity，保证每个定单结束后开始新的界面，不会再跳回这里
			}else{
				tv_pay_countdown.setText(currentNum+"");
				handler.postDelayed(pay_countdown, 1000);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_zhifubao_weixin);
		
		Intent intentPayChoose = getIntent();
		totalPrice = intentPayChoose.getFloatExtra("total", 0);//总价
		allAccount = (ArrayList)intentPayChoose.getSerializableExtra("allAccount");//本次订单
//		byte[] data = (byte[])allAccount.toArray(typeof(byte));
//		byte[] data = (byte[])allAccount.to

		tv_pay_zhifudingdan=(TextView)findViewById(R.id.pay_zhifudingdan);
		tv_pay_pleasepay=(TextView)findViewById(R.id.pay_pleasepay);
		tv_pay_countdown=(TextView)findViewById(R.id.pay_countdown);
		tv_pay_zhifudingdan.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_pay_pleasepay.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_pay_countdown.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		
		imv_pay_qrcode = (ImageView)findViewById(R.id.pay_qrcode);
		
		//将整个订单进行封装
		setPayBillShow();
		
		
		
		//点击二维码
		imv_pay_qrcode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//将订单通过网络首先传输至服务器，再传到后厨面板
				sendAllAccount(getAllAccountInfo(allAccount,1),getAllAccountInfo(allAccount, 2));
				
				//设置倒计时5秒钟后跳至支付完成页
				handler.postDelayed(pay_countdown, 1000);
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pay_zhifubao, menu);
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
	 * 将整个的订单信息封装在一起
	 */
	private void setPayBillShow() {
		// TODO Auto-generated method stub
		billCount = getBillCount();
		pbs = new PayBillShow();
		
		pbs.setBillTime(getCurrentTime(1));
		pbs.setBillId(getCurrentTime(2)+diningTableId+billCount);
		pbs.setBillSum(totalPrice);
		pbs.setBillFavor(0);
		pbs.setBillFavorType("");
		pbs.setBillSumActual(totalPrice);//这里目前没有设置优惠，于是将优惠部分设为0
		pbs.setCustomer("baifeng");
		pbs.setBillPayment("支付宝");
		pbs.setDiningTableId(diningTableId);
		pbs.setOrderId(getCurrentTime(3)+diningTableId+billCount);
		pbs.setAllMenu(allAccount);
	}
	
	/**
	 * 连接服务器，发送本次订单信息
	 * 连接后厨面板（服务端），发送本次订单信息
	 * @param stringHouchu 要发送给后厨的字符串
	 * @param stringServer 要发送给服务器的字符串
	 */
	private void sendAllAccount(final String stringHouchu,final String stringServer) {
		// TODO Auto-generated method stub
		try{
			//如果联网的代码在主线程里，如果网络不好整个程序就会卡死在这，需要加上这一句
			//后面要把他改到一个线程里执行
			//加上服务端返回数据确认服务端收到数据，可以试试抛出异常给上一层
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
			
			
//			new Thread(){
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					super.run();
//					
//					Socket s = null;
//					DataOutputStream dos = null;
//					DataInputStream dis = null;
//					System.out.println("!!!12345678");
//					try{
//						s = new Socket("192.168.1.149",11011);//实验楼B303-服务器ip
//						dos = new DataOutputStream(s.getOutputStream());
//						dis = new DataInputStream(s.getInputStream());
//						
//						dos.writeUTF("<#bill#>"+string);
//						System.out.println("!!!1233456787990");
//						String msg = dis.readUTF();
//						System.out.println("!!!"+msg);
////						if(msg.equals("ABCD")){
////							s = new Socket("192.168.1.125",11001);
////						}
//						
//					}catch(Exception e){
//						e.printStackTrace();
//					}finally{
//						try{
//							if(dis != null){
//								dis.close();
//								dis = null;
//							}
//							if(dos != null){
//								dos.close();
//								dos = null;
//							}
//							if(s != null){
//								s.close();
//								s = null;
//							}
//						}catch(Exception e){
//							e.printStackTrace();
//						}
//					}
//				}
//			}.start();
			
			
			
			Socket sServer = new Socket("192.168.191.1",11011);
//			Socket sServer = new Socket("192.168.1.149",11011);//实验楼B303-服务器ip
//			Socket sServer = new Socket("192.168.1.101",11011);//实验楼B303-服务器ip
//			Socket sServer = new Socket("192.168.199.163",11011);//ABC-服务器ip
//			Socket sServer = new Socket("192.168.191.2",11011);//WiFi-服务器ip
			OutputStream osServer = sServer.getOutputStream();
			int lenServer = 0;
			byte[] bufServer = new byte[1024]; 
			StringBuilder sb_AllAccountServer = new StringBuilder();
			//使用socket从Android客户端发往服务器端时会乱码，需要使用URLEncoder对字符串进行编码
			String newString = URLEncoder.encode("<#bill#>"+stringServer, "utf-8");
			osServer.write(newString.getBytes());
			sServer.close();
			
//			Socket sHouchu = new Socket("192.168.191.3",11001);
//			Socket sHouchu = new Socket("192.168.1.125",11001);//实验楼B303-后厨ip
//			Socket sHouchu = new Socket("192.168.1.104",11001);//实验楼B303-后厨ip-i9100
//			Socket sHouchu = new Socket("192.168.199.163",11001);//ABC-后厨ip
			Socket sHouchu = new Socket("192.168.191.2",11001);//WiFi-后厨ip
			OutputStream osHouchu = sHouchu.getOutputStream();
			int lenHouchu = 0;
			byte[] bufHouchu = new byte[1024];
			StringBuilder sb_AllAccountHouchu = new StringBuilder();
			//int i=0;
			//os.write((""+(i++)).getBytes());
			osHouchu.write(stringHouchu.getBytes());
			sHouchu.close();
			
			
		}catch(Exception e){
			System.out.println("!!!从支付页向后厨传输菜单出错，待处理！");
			Toast.makeText(getApplicationContext(), "从支付页向后厨传输菜单出错，待处理！", 0).show();
		}
	}
	
	/**
	 * 将从AccountActivity传到PayChooseActivity的数据再传到这个后，解析出来
	 * @param al 包含菜的信息(名称、单价、数量)
	 * @param index 指定返回的是后厨需要的数据（index==1），还是服务器需要的数据（index！=1）
	 * @return 将需要的数据用字符串返回
	 */
	private String getAllAccountInfo(ArrayList al,int index){
		Iterator it = al.iterator();
		sbAccountInfo = new StringBuilder();
		
		if(index == 1){
			sbAccountInfo.append(pbs.getOrderId()+";");
			sbAccountInfo.append(pbs.getBillTime()+";");
			sbAccountInfo.append(pbs.getDiningTableId()+";");
			
			sbAccountInfo.append("<>");
			
			while(it.hasNext()){
				AccountMenuShow ams = (AccountMenuShow)it.next();
				sbAccountInfo.append(ams.getAccount_menu_name()+":"+ams.getAccount_menu_count()+"个;");
			}
			sbAccountInfo.append("\n");
		}else{
			sbAccountInfo.append(pbs.getBillTime()+";");
			sbAccountInfo.append(pbs.getBillId()+";");
			sbAccountInfo.append(pbs.getBillSum()+";");
			sbAccountInfo.append(pbs.getBillFavor()+";").append(pbs.getBillFavorType()+";");
			sbAccountInfo.append(pbs.getBillSumActual()+";");
			
			sbAccountInfo.append(pbs.getCustomer()+";");
			sbAccountInfo.append(pbs.getBillPayment()+";");
			sbAccountInfo.append(pbs.getDiningTableId()+";");
			sbAccountInfo.append(pbs.getOrderId()+";");
			
			sbAccountInfo.append("<>");
			
			while(it.hasNext()){
				AccountMenuShow ams = (AccountMenuShow)it.next();
				sbAccountInfo.append(ams.getAccount_menu_name()+";");
				sbAccountInfo.append(ams.getAccount_menu_price()+";");
				sbAccountInfo.append(ams.getAccount_menu_count()+"#");
			}
		}
		
		return sbAccountInfo.toString();
	}
	
	/**
	 * 获取当前时间
	 * @param index 1——2015-12-23 21:16:34；
	 * 				2——201512232116；
	 * 				3——151223；
	 * @return time 当前时间
	 */
	private String getCurrentTime(int index) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf;
		String format = null;
		
		if(index == 1){
			format = "yyyy-MM-dd HH:mm:ss";
		}else if(index == 2){
			format = "yyyyMMddHHmm";
		}else if(index == 3){
			format = "yyMMdd";
		}
		sdf = new SimpleDateFormat(format);
		String time = sdf.format(new Date());
		
		return time;
	}
	
	/** 
	 * 获取今天这是第几单
	 * @return 第几单
	 */
	private int getBillCount(){
		String newTime = getCurrentTime(3);//只要精确到天
		SharedPreferences sp = getSharedPreferences("EDIT_BILL_COUNT", MODE_PRIVATE); 
		int cBillCount = sp.getInt(BILL_COUNT, 0);
		String today = sp.getString(TODAY_DATE, "20160503");
		
		Editor et = sp.edit();
		if(!(today.equals(newTime))){//新的一天开始了
			et.putString(TODAY_DATE, newTime);
		}
		et.putInt(BILL_COUNT, ++cBillCount);
		et.commit();
		
		return cBillCount;
	}
}
