package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.epsoft.util.StringUtil;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

public class SaveA57 {

	public static String save(List<Param> params,HBSession session) {
		String photodata = null;
		String a0000 = null;
		for (Param param : params) {
			// 判断A01中有没有这条数据
			if (param.getName().equals("A0184") && !"".equals(param.getValue())) {

				List<A01> list = HBUtil.getHBSession().createQuery(
						"from A01 where a0184='"+param.getValue()+"'  ").list();
				if(list==null||list.size()==0){
					return "错误：系统中不存在该人员信息:"+param.getValue()+"，请先在人员基本信息中插入";
				}else if(list.size()>1){
					return "错误：系统中存在多条该人员信息:"+param.getValue()+"，无法加入";
				}
				a0000 = list.get(0).getA0000();
			}
			
			if(param.getName().equals("PHOTODATA") ) {
				if("".equals(param.getValue())){
					return "错误：图片数据不能为空";
				}
				photodata = StringUtil.newLineDecoder(param.getValue());
			}
		}

		A57 a57 = new A57();
		a57.setA0000(a0000);
		try {
			PhotosUtil.savePhotoData(a57, session, CodeTypeUtil.getPhotoBase64(photodata));
		} catch (Exception e) {
			e.printStackTrace();
			return "错误：图片新增错误："+e.getMessage();
		}
		
		return "select 1 from dual";
	}


	

}
