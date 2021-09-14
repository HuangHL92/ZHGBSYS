package com.insigma.siis.local.lrmx;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;


public class Test {

	public static void main(String[] args) throws Exception {
		PersonXml a = new PersonXml();
		JiaTingChengYuanXml c = new JiaTingChengYuanXml();
		ItemXml b =new ItemXml();
		List<JiaTingChengYuanXml> jiaTingChengYuanXml = new ArrayList<JiaTingChengYuanXml>();
		List<ItemXml> itemlist = new ArrayList<ItemXml>();
		a.setXingMing("姓A");
		a.setCanJiaGongZuoShiJian("李四");
		a.setJiangChengQingKuang("王武");
		a.setChuShengDi("张三");
		//数据库取到list15.get(0).getPhotodata() 这个值转换成byte[]
		//List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57_temp where a0000 ='2c935181542c8c4901542daed9e80008'").addEntity(A57temp.class).list();
		//a.setZhaoPian(toByte(list15.get(0).getPhotodata()));
		a.setJiaTingChengYuan(jiaTingChengYuanXml);
		jiaTingChengYuanXml.add(c);
		c.setItem(itemlist);
		b.setChengWei("策划能够为");
		itemlist.add(b);
//		List<A01> list =  HBUtil.getHBSession().createQuery("from A01 where a0000 = '7fb33ebe-6b69-4ee1-a300-f2f529c795d8'").list();
//		A01 a01 = list.get(0);
		System.out.println(JXUtil.Object2Xml(a,true));
//		FileUtil.createFile("D:/temp/"+"11"+".Lrmx", JXUtil.Object2Xml(a01,true), false, "UTF-8");

//		Runtime rt = Runtime.getRuntime();
//		Process p = null;
//		try {
//			p = rt.exec("\"C:\\Program Files (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\" D:/temp/姓A.Lrmx D:/temp");
//		} catch (Exception e) {
//			System.out.println("fail");
//		}
	}
	
	 public static byte[] toByte(Blob blob) throws SQLException, IOException{
		  byte[] im = new byte[(int) blob.length()];
		  BufferedInputStream is = new BufferedInputStream(blob.getBinaryStream());
		  int len = (int) blob.length();
		  int offset = 0;
		  int read = 0;
		  
		  try {
		   while (offset < len && (read = is.read(im, offset, len - offset)) >= 0) {
		     offset += read;
		    }
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }finally{
		   is.close();
		   is=null;
		  }
		  
		  return im;
		 }

	 //读取照片
	 public void photo(){
		 File file = new File("d:/11.jpg");  
		    long fileSize = file.length();  
		    if (fileSize > Integer.MAX_VALUE) {  
		        System.out.println("file too big...");  
		    }  
		    FileInputStream fi;
			try {
				fi = new FileInputStream(file);
			    byte[] buffer = new byte[(int) fileSize];  
			    int offset = 0;  
			    int numRead = 0;  
			    while (offset < buffer.length  
			    && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
			        offset += numRead;  
			    }  
			    // 确保所有数据均被读取  
			    if (offset != buffer.length) {  
			    throw new IOException("Could not completely read file "  
			                + file.getName());  
			    }  
			    fi.close();  
				PersonXml a = new PersonXml();
				JiaTingChengYuanXml c = new JiaTingChengYuanXml();
				ItemXml b =new ItemXml();
				List<JiaTingChengYuanXml> jiaTingChengYuanXml = new ArrayList<JiaTingChengYuanXml>();
				List<ItemXml> itemlist = new ArrayList<ItemXml>();
				a.setXingMing("姓A");
				a.setCanJiaGongZuoShiJian("李四");
				a.setJiangChengQingKuang("王武");
				a.setChuShengDi("张三");
				a.setZhaoPian(buffer);
				a.setJiaTingChengYuan(jiaTingChengYuanXml);
				jiaTingChengYuanXml.add(c);
				c.setItem(itemlist);
				b.setChengWei("策划能够为");
				itemlist.add(b);
				System.out.println(JXUtil.Object2Xml(a,true));
				FileUtil.createFile("D:/temp/"+a.getXingMing()+".Lrmx", JXUtil.Object2Xml(a,true), false, "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	 } 
}
