package com.huzi.orderpanel.customview;

import java.io.Serializable;


/**
 * 已选择的菜品，1：名称，2：单价，3：数量
 * 说明：为了在activity间使用ArrayList传值，菜品展示类需要实现Serializable或Parcelable接口
 * 当JAVA中需要远程调用时需要传输的数据都是binary类型的，所以需要把数据序列化
 * @author SXH
 */
public class AccountMenuShow implements Serializable {
	
	/**
	 * 当JAVA中需要远程调用时需要传输的数据都是binary类型的，所以需要把数据序列化
	 * 当另一条电脑接收以后需要反序列化然后读出数据
	 * 给该类加个serial ID ，这样在反序列化时再次验证该id以确定是同一个object
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
		//自定义两个对象是否相同，我这里只是比较菜品的名字
		// TODO Auto-generated method stub
		AccountMenuShow ams=(AccountMenuShow)obj;
		return ams.account_menu_name.equals(this.account_menu_name);
	}
}
