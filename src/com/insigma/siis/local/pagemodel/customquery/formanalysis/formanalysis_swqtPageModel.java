package com.insigma.siis.local.pagemodel.customquery.formanalysis;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkComm;

public class formanalysis_swqtPageModel extends PageModel{

	public formanalysis_swqtPageModel() {
	}

	@Override
	public int doInit() throws RadowException {
		Calendar cal = Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		year =year-1;
		String date=year+"-12"+"-31";
		this.getPageElement("tjtime").setValue(date);
		this.getPageElement("timetj").setValue(date);
		this.setNextEventName("init");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("init")
	public int init() throws RadowException{
		try {
			String groupid=this.getPageElement("subWinIdBussessId").getValue();
			if(StringUtil.isEmpty(groupid)){
				groupid="001.001";
			}
			String year=this.getPageElement("tjtime").getValue();
			this.getPageElement("timetj").setValue(year);
			this.getPageElement("groupid").setValue(groupid);
			formanalysis_ssygwyPageModel fsp=new formanalysis_ssygwyPageModel();
			CommQuery cq=new CommQuery();
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("groupid", groupid);
			String sid = this.request.getSession().getId();
			map.put("sid",sid);
			map.put("num", "");
			map.put("cq", cq);
			map.put("a0279", "");
			map.put("a0219", "");
			map.put("year", year);
			map.put("a0160", " and a01.A0160='5' ");
			String userid=SysUtil.getCacheCurrentUser().getId();
			map.put("userid", userid);
			String username=SysUtil.getCacheCurrentUser().getLoginname();
			map.put("username", username);
			List<HashMap<String, Object>> list_user=cq.getListBySQL(" select rate,empid from smt_user t where t.userid = '"+userid+"' ");
			String rylb="";
			if(list_user!=null&&list_user.size()>0){
				String temp=(String)list_user.get(0).get("rate");//人员类别 不可浏览项
				if(temp!=null&&temp.length()>0){
					rylb=temp;
				}
				temp=(String)list_user.get(0).get("empid");//人员类别 不可维护项
				if(temp!=null&&temp.length()>0){
					rylb=rylb+","+temp;
				}
			}
			map.put("rylb", rylb);
			String sql=(String) this.request.getSession().getAttribute("ry_tj_zy");//人员信息查询sql
			map.put("sql", sql);
			StringBuffer ss=new StringBuffer();
//			map.put("a0221",   " and a01.a0221 like '1A%' "
//                    );
//			map.put("a0219", "");//是否领导职务 1是 2否
//			map.put("num", "1");//num 合计标志
//			ss=new GbjbqkComm().toJson(fsp.returnList(map));
//			this.getPageElement("jsonString_str1").setValue(ss.toString());
//			this.getExecuteSG().addExecuteCode("json_func('1')");//
			
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
			ss=new GbjbqkComm().toJson(fsp.returnList(map));
			this.getPageElement("jsonString_str2").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('2')");//分组
			
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
			ss=new GbjbqkComm().toJson(fsp.returnList(map));
			this.getPageElement("jsonString_str3").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('3')");//领导
			
			map.put("a0221",   " and a01.a0221 in ('1A21',"//厅局级正职
                    + "'1A22', "//厅局级副职
                    + "'1A31',"//县处级正职
                    + "'1A32',"//县处级副职
                    + "'1A41',"//乡科级正职
                    + "'1A42')"//其乡科级副职
                    );
			map.put("a0219", " and (a02.a0219!='1' or a02.a0219 is null) ");
			map.put("a0279", " and a02.a0279='1' ");
			map.put("num", "");//num 合计标志
			ss=new GbjbqkComm().toJson(fsp.returnList(map));
			this.getPageElement("jsonString_str4").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('4')");//非领导
			
		
		} catch (AppException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	

}
