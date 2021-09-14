package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.epsoft.util.StringUtil;

public class QueryPhoto {
	public static String save(List<Param> params){
		byte[] photodata = null;
		String a0000 = null;
		String str = "";
		for (Param param : params) {
			// 判断A01中有没有这条数据
			if (param.getName().equals("A0184") && !"".equals(param.getValue())) {
				List<A01> list = HBUtil.getHBSession().createQuery(
						"from A01 where a0184='"+param.getValue()+"'  ").list();
				if(list==null||list.size()==0){
					return "错误：系统中不存在该人员信息:"+param.getValue()+"，请先在人员基本信息中插入";
				}else if(list.size()>1){
					return "错误：系统中存在多条该人员信息:"+param.getValue()+"，无法查询";
				}
				a0000 = list.get(0).getA0000();
			}
			
			if(param.getName().equals("A0184") ) {
				if("".equals(param.getValue())){
					return "错误：身份证号码不能为空！";
				}
			}
		}
		HBSession sess = HBUtil.getHBSession();
		A57 a57 = (A57) sess.get(A57.class, a0000);
		try {
			photodata = PhotosUtil.getPhotoData(a57);
		} catch (Exception e) {
			e.printStackTrace();
			return "错误：图片查询错误："+e.getMessage();
		}
		try {
			InputStream sbs = new ByteArrayInputStream(photodata); 
			str = StringUtil.BASE64Encoder(sbs);                                  //将图片的字节码编码用BASE64编码
		} catch (Exception e) {
		}
		
		return a0000 + "," + str;
	}
	
	//照片数据转换string类型
	public static String byteArrayToStr(byte[] byteArray) {
	    if (byteArray == null) {
	        return null;
	    }
	    String str = new String(byteArray);
	    return str;
	}
}
	
