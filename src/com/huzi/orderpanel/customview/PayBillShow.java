package com.huzi.orderpanel.customview;

import java.util.*;

public class PayBillShow {

	private String billTime;//����ʱ��
	private String billId;//������
	private float billSum;//�����ܶ�
	private float billFavor;//�Żݽ��
	private String billFavorType;//�Ż�����
	private float billSumActual;//ʵ�����
	
	private String customer;//�˿���Ϣ
	private String billPayment;//֧����ʽ
	private String diningTableId;//������
	private String orderId;//������ˮ��
	
	private ArrayList allMenu;

	public String getBillTime() {
		return billTime;
	}

	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public float getBillSum() {
		return billSum;
	}

	public void setBillSum(float billSum) {
		this.billSum = billSum;
	}

	public float getBillFavor() {
		return billFavor;
	}

	public void setBillFavor(float billFavor) {
		this.billFavor = billFavor;
	}

	public String getBillFavorType() {
		return billFavorType;
	}

	public void setBillFavorType(String billFavorType) {
		this.billFavorType = billFavorType;
	}

	public float getBillSumActual() {
		return billSumActual;
	}

	public void setBillSumActual(float billSumActual) {
		this.billSumActual = billSumActual;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getBillPayment() {
		return billPayment;
	}

	public void setBillPayment(String billPayment) {
		this.billPayment = billPayment;
	}

	public String getDiningTableId() {
		return diningTableId;
	}

	public void setDiningTableId(String diningTableId) {
		this.diningTableId = diningTableId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public ArrayList getAllMenu() {
		return allMenu;
	}

	public void setAllMenu(ArrayList allMenu) {
		this.allMenu = allMenu;
	}
	
	public PayBillShow(){
		
	}
	
}
