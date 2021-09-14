package com.insigma.siis.local.pagemodel.cadremgn.infmtionquery;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Qryview;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class ResultQueryPageModel extends PageModel {
	
	public static void main(String[] args) {
		CommonQueryBS.systemOut(20/69+"");
	}
	public static String jiaoyan = "0";//0是打开 1是点击
	
	@Override
	public int doInit() throws RadowException {
//		this.setNextEventName("resultListGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("tempFunc")
	public int tempFunc(){
		this.setNextEventName("resultListGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("resultListGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException, AppException{
		String dual="select 1 from dual where 1<>1";
		if(this.getPageElement("vru000").getValue()!=null&&!"".equals(this.getPageElement("vru000").getValue())
					&&!"null".equals(this.getPageElement("vru000").getValue())){
		    HBSession session = HBUtil.getHBSession();
			String qvid=this.getPageElement("vru000").getValue();
			Qryview qryview = (Qryview) session.get(Qryview.class, qvid);
			this.pageQuery(qryview.getQrysql(), "SQL", start,limit);
		}else{
			this.pageQuery(dual, "SQL", start,limit);
		}
		return EventRtnType.SPE_SUCCESS;
	}
	
}