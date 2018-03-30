package com.huzi.orderpanel.customview;

import java.io.*;

public class SerializeUtil {
	
	
	public static byte[] serialize(Object obj){
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		
		try{
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			
			oos.writeObject(obj);
			byte[] data = baos.toByteArray();
			
			return data;
		}catch(Exception e){
			System.out.println("!!!!序列化出错");
		}
		
		return null;
	}
	
	public static Object unserialize(byte[] data){
		ByteArrayInputStream bais = null;
		
		try{
			bais = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		}catch(Exception e){
			System.out.println("!!!反序列化出错");
		}
		
		return null;
	}
}
