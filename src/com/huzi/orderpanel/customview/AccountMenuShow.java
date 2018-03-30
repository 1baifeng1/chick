package com.huzi.orderpanel.customview;

import java.io.Serializable;


/**
 * ��ѡ��Ĳ�Ʒ��1�����ƣ�2�����ۣ�3������
 * ˵����Ϊ����activity��ʹ��ArrayList��ֵ����Ʒչʾ����Ҫʵ��Serializable��Parcelable�ӿ�
 * ��JAVA����ҪԶ�̵���ʱ��Ҫ��������ݶ���binary���͵ģ�������Ҫ���������л�
 * @author SXH
 */
public class AccountMenuShow implements Serializable {
	
	/**
	 * ��JAVA����ҪԶ�̵���ʱ��Ҫ��������ݶ���binary���͵ģ�������Ҫ���������л�
	 * ����һ�����Խ����Ժ���Ҫ�����л�Ȼ���������
	 * ������Ӹ�serial ID �������ڷ����л�ʱ�ٴ���֤��id��ȷ����ͬһ��object
	 */
	private static final long serialVersionUID = -9098246061154946901L;
	
	private String account_menu_name;
	private float account_menu_price;
	private int account_menu_count;
	
	public String getAccount_menu_name() {
		return account_menu_name;
	}
	public void setAccount_menu_name(String account_menu_name) {
		this.account_menu_name = account_menu_name;
	}
	public float getAccount_menu_price() {
		return account_menu_price;
	}
	public void setAccount_menu_price(float account_menu_price) {
		this.account_menu_price = account_menu_price;
	}
	public int getAccount_menu_count() {
		return account_menu_count;
	}
	public void setAccount_menu_count(int account_menu_count) {
		this.account_menu_count = account_menu_count;
	}
	
	@Override
	public boolean equals(Object obj) {
		//�Զ������������Ƿ���ͬ��������ֻ�ǱȽϲ�Ʒ������
		// TODO Auto-generated method stub
		AccountMenuShow ams=(AccountMenuShow)obj;
		return ams.account_menu_name.equals(this.account_menu_name);
	}
}
