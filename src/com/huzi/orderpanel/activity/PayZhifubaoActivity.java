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
	/** ��ά��  */
	ImageView  imv_pay_qrcode;
	/** �ܼ� */
	float totalPrice;
	/** ��Ʒ���� */
	ArrayList allAccount;
	/** ƴ�Ӳ�Ʒ */
	StringBuilder sbAccountInfo;
	/** �˵���Ϣ */
	PayBillShow pbs;
	
	/** ������  */
	public static final String diningTableId = "15";
	
	/** ��������  */
	int billCount;
	private static final String EDIT_BILL_COUNT = "editBillCount";
	private static final String BILL_COUNT = "currentBillCount";
	private static final String TODAY_DATE = "todayDate";

	/**
	 * ����ʱ
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
				finish();//����ʱ�����󣬾���finish����������PayChooseActivity��������finish����PayFinishActivity����֤ÿ������������ʼ�µĽ��棬��������������
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
		totalPrice = intentPayChoose.getFloatExtra("total", 0);//�ܼ�
		allAccount = (ArrayList)intentPayChoose.getSerializableExtra("allAccount");//���ζ���
//		byte[] data = (byte[])allAccount.toArray(typeof(byte));
//		byte[] data = (byte[])allAccount.to

		tv_pay_zhifudingdan=(TextView)findViewById(R.id.pay_zhifudingdan);
		tv_pay_pleasepay=(TextView)findViewById(R.id.pay_pleasepay);
		tv_pay_countdown=(TextView)findViewById(R.id.pay_countdown);
		tv_pay_zhifudingdan.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_pay_pleasepay.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		tv_pay_countdown.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fangzhengxuanzhenzhuanbianjianti.ttf"));
		
		imv_pay_qrcode = (ImageView)findViewById(R.id.pay_qrcode);
		
		//�������������з�װ
		setPayBillShow();
		
		
		
		//�����ά��
		imv_pay_qrcode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//������ͨ���������ȴ��������������ٴ���������
				sendAllAccount(getAllAccountInfo(allAccount,1),getAllAccountInfo(allAccount, 2));
				
				//���õ���ʱ5���Ӻ�����֧�����ҳ
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
	 * �������Ķ�����Ϣ��װ��һ��
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
		pbs.setBillSumActual(totalPrice);//����Ŀǰû�������Żݣ����ǽ��Żݲ�����Ϊ0
		pbs.setCustomer("baifeng");
		pbs.setBillPayment("֧����");
		pbs.setDiningTableId(diningTableId);
		pbs.setOrderId(getCurrentTime(3)+diningTableId+billCount);
		pbs.setAllMenu(allAccount);
	}
	
	/**
	 * ���ӷ����������ͱ��ζ�����Ϣ
	 * ���Ӻ����壨����ˣ������ͱ��ζ�����Ϣ
	 * @param stringHouchu Ҫ���͸�������ַ���
	 * @param stringServer Ҫ���͸����������ַ���
	 */
	private void sendAllAccount(final String stringHouchu,final String stringServer) {
		// TODO Auto-generated method stub
		try{
			//��������Ĵ��������߳��������粻����������ͻῨ�����⣬��Ҫ������һ��
			//����Ҫ�����ĵ�һ���߳���ִ��
			//���Ϸ���˷�������ȷ�Ϸ�����յ����ݣ����������׳��쳣����һ��
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
//						s = new Socket("192.168.1.149",11011);//ʵ��¥B303-������ip
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
//			Socket sServer = new Socket("192.168.1.149",11011);//ʵ��¥B303-������ip
//			Socket sServer = new Socket("192.168.1.101",11011);//ʵ��¥B303-������ip
//			Socket sServer = new Socket("192.168.199.163",11011);//ABC-������ip
//			Socket sServer = new Socket("192.168.191.2",11011);//WiFi-������ip
			OutputStream osServer = sServer.getOutputStream();
			int lenServer = 0;
			byte[] bufServer = new byte[1024]; 
			StringBuilder sb_AllAccountServer = new StringBuilder();
			//ʹ��socket��Android�ͻ��˷�����������ʱ�����룬��Ҫʹ��URLEncoder���ַ������б���
			String newString = URLEncoder.encode("<#bill#>"+stringServer, "utf-8");
			osServer.write(newString.getBytes());
			sServer.close();
			
//			Socket sHouchu = new Socket("192.168.191.3",11001);
//			Socket sHouchu = new Socket("192.168.1.125",11001);//ʵ��¥B303-���ip
//			Socket sHouchu = new Socket("192.168.1.104",11001);//ʵ��¥B303-���ip-i9100
//			Socket sHouchu = new Socket("192.168.199.163",11001);//ABC-���ip
			Socket sHouchu = new Socket("192.168.191.2",11001);//WiFi-���ip
			OutputStream osHouchu = sHouchu.getOutputStream();
			int lenHouchu = 0;
			byte[] bufHouchu = new byte[1024];
			StringBuilder sb_AllAccountHouchu = new StringBuilder();
			//int i=0;
			//os.write((""+(i++)).getBytes());
			osHouchu.write(stringHouchu.getBytes());
			sHouchu.close();
			
			
		}catch(Exception e){
			System.out.println("!!!��֧��ҳ��������˵�����������");
			Toast.makeText(getApplicationContext(), "��֧��ҳ��������˵�����������", 0).show();
		}
	}
	
	/**
	 * ����AccountActivity����PayChooseActivity�������ٴ�������󣬽�������
	 * @param al �����˵���Ϣ(���ơ����ۡ�����)
	 * @param index ָ�����ص��Ǻ����Ҫ�����ݣ�index==1�������Ƿ�������Ҫ�����ݣ�index��=1��
	 * @return ����Ҫ���������ַ�������
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
				sbAccountInfo.append(ams.getAccount_menu_name()+":"+ams.getAccount_menu_count()+"��;");
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
	 * ��ȡ��ǰʱ��
	 * @param index 1����2015-12-23 21:16:34��
	 * 				2����201512232116��
	 * 				3����151223��
	 * @return time ��ǰʱ��
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
	 * ��ȡ�������ǵڼ���
	 * @return �ڼ���
	 */
	private int getBillCount(){
		String newTime = getCurrentTime(3);//ֻҪ��ȷ����
		SharedPreferences sp = getSharedPreferences("EDIT_BILL_COUNT", MODE_PRIVATE); 
		int cBillCount = sp.getInt(BILL_COUNT, 0);
		String today = sp.getString(TODAY_DATE, "20160503");
		
		Editor et = sp.edit();
		if(!(today.equals(newTime))){//�µ�һ�쿪ʼ��
			et.putString(TODAY_DATE, newTime);
		}
		et.putInt(BILL_COUNT, ++cBillCount);
		et.commit();
		
		return cBillCount;
	}
}
