package com.insigma.siis.local.pagemodel.customquery.formanalysis;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkComm;

public class formanalysis_ssegwyPageModel extends PageModel{

	public formanalysis_ssegwyPageModel() {
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
			map.put("sid", sid);
			map.put("num", "");//为空不是合计 总计
			map.put("cq", cq);
			map.put("a0219", "");//为空 不分领导非领导职务
			map.put("a0279", "");
			map.put("year", year);
			map.put("a0160", " and a01.A0160='1' ");
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
			
			StringBuffer ss=null;
//			map.put("a0221"," and a01.a0221 like '2%' "//人民警察警员职务序列 
//                    );
//			map.put("num", "1");//合计
//			ss=new GbjbqkComm().toJson(fsp.returnList(map));
//			this.getPageElement("jsonString_str1").setValue(ss.toString());
//			this.getExecuteSG().addExecuteCode("json_func('1')");//
			
			map.put("a0221"," and a01.a0221 like '2%' "//人民警察警员职务序列 
                    );
			map.put("num", "");//分组
			ss=new GbjbqkComm().toJson(fsp.returnList(map));
			this.getPageElement("jsonString_str2").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('2')");
			
//			map.put("a0221",   " and a01.a0221 like '3%' "//法官
//                    );
//			map.put("num", "3");//合计
//			ss=new GbjbqkComm().toJson(fsp.returnList(map));
//			this.getPageElement("jsonString_str3").setValue(ss.toString());
//			this.getExecuteSG().addExecuteCode("json_func('3')");
			
			map.put("a0221",   " and (a01.a0221 like '3%' "//法官
					+ " or a01.a0221 like '4%') "//检察官等级
                    );
			map.put("num", "");//分组
			ss=new GbjbqkComm().toJson(fsp.returnList(map));
			this.getPageElement("jsonString_str4").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('4')");
			
			map.put("a0221",   " and a01.a0221 like '7%' "//深圳市行政执法类公务员
                    );
			map.put("a0219", "");
			map.put("num", "5");//合计
			ss=new GbjbqkComm().toJson(fsp.returnList(map));
			this.getPageElement("jsonString_str5").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(5)");//合计

		} catch (AppException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
