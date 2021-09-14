package com.insigma.siis.local.pagemodel.customquery.formanalysis;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkComm;

public class Cgqtnlqk1PageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		Calendar cal = Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		year =year-1;
		String date=year+"-12"+"-31";
		this.getPageElement("tjtime").setValue(date);
		this.getPageElement("timetj").setValue(date);
		this.setNextEventName("initx");
		return 0;
	}

	
	@PageEvent("initx")
	public int initx() throws RadowException{

		try {
			String groupid=this.getPageElement("subWinIdBussessId").getValue();
			if(StringUtil.isEmpty(groupid)){
				groupid="001.001";
			}
			String year=this.getPageElement("tjtime").getValue();
			this.getPageElement("timetj").setValue(year);
			this.getPageElement("groupid").setValue(groupid);
			CommQuery cq=new CommQuery();
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("groupid", groupid);
			String sid = this.request.getSession().getId();
			map.put("cq", cq);
			map.put("num", "");
			map.put("a0279", "");
			map.put("a0219", "");
			map.put("year", year);
			map.put("a0160", " and a01.A0160='5' ");
			StringBuffer ss=new StringBuffer();
			map.put("a0221",   " and a01.a0221 in ('1A11',"//省部级正职
                    + "'1A12', "//省部级副职
                    + "'1A50',"//科员 
                    + "'1A60',"//办事员 
                    + "'1A98',"//试用期人员
                    + "'1A99')"//其他
                    );
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "");
			ss=new GbjbqkComm().toJson(Gwynlqk1PageModel.returnList(map));
			this.getPageElement("jsonString_str1").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('1')");//分组
			
			map.put("a0221",   " and a01.a0221 in ('1A21',"//厅局级正职
                    + "'1A22', "//厅局级副职
                    + "'1A31',"//县处级正职
                    + "'1A32',"//县处级副职
                    + "'1A41',"//乡科级正职
                    + "'1A42')"//其乡科级副职
                    );
			map.put("a0219", " and a02.a0219='1' ");
			map.put("a0279", " and a02.a0279='1' ");
			map.put("num", "");//num 合计标志
			ss=new GbjbqkComm().toJson(Gwynlqk1PageModel.returnList(map));
			this.getPageElement("jsonString_str2").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('2')");//领导
			
			map.put("a0221",   " and a01.a0221 in ('1A21',"//厅局级正职
                    + "'1A22', "//厅局级副职
                    + "'1A31',"//县处级正职
                    + "'1A32',"//县处级副职
                    + "'1A41',"//乡科级正职
                    + "'1A42')"//乡科级副职
                    );
			map.put("a0219", " and a02.a0219!='1' ");
			map.put("a0279", " and a02.a0279='1' ");
			map.put("num", "");//num 合计标志
			ss=new GbjbqkComm().toJson(Gwynlqk1PageModel.returnList(map));
			this.getPageElement("jsonString_str3").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('3')");//非领导
			
		
		} catch (AppException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	
		
	}
}
