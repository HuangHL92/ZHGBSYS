package com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class GbjbqkxzccPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("gridfc.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("gridfc.dogridquery")
	public int zwxxQuery(int start, int limit) throws RadowException {
		//单位id
		String subWinIdBussessId = this.getPageElement("subWinIdBussessId").getValue();
		
		
		String[] param_arr = subWinIdBussessId.split("\\$");
		String groupid = param_arr[0].trim().replace("|", "'").split("group")[0];
	
		
		
		StringBuffer sb=new StringBuffer();
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		//拼接sql select
		gbjbqksqlpj.returnPjGridSql(sb);
		sb.append(" "
	          +" FROM A01 a01 "
				);
        gbjbqksqlpj.sqlPjExists(sb, groupid);
        gbjbqksqlpj.sqlPjA01(sb);

	    sb.append(" ");
		this.pageQuery(sb.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 统计分析
	 * @return
	 * @throws RadowException 
	 * @throws UnsupportedEncodingException 
	 * @throws AppException 
	 */
	@PageEvent("initTj")
	public int init() throws RadowException, UnsupportedEncodingException, AppException{
		CommQuery cq=new CommQuery();
		//设置复选框选中 全0行
		this.getPageElement("xianyin").setValue("1");
		//隐藏占比选中
		this.getPageElement("yczb").setValue("1");
		
		this.getPageElement("xy_zwlb").setValue("1");//显隐 标志 1 隐藏
		
		
		//单位id
		String subWinIdBussessId = this.getPageElement("subWinIdBussessId").getValue();
		
		
		String[] param_arr = subWinIdBussessId.split("\\$");
		String groupid = param_arr[0].trim().replace("|", "'").split("group")[0];
		// and (1=2  or substr(a02.a0201b, 1, 7)='001.001' ) and a01.a0000 = a02.a0000 
//		if(groupid.length()>=12){
//			groupid=groupid.substring(12, groupid.length());
//		}else{
//			return EventRtnType.NORMAL_SUCCESS;
//		}
//		if(groupid.length()>=26){
//			groupid=groupid.substring(0, groupid.length()-26);
//		}else{
//			return EventRtnType.NORMAL_SUCCESS;
//		}
//		//
//		groupid=" and ( "+groupid+ " ";
	
		StringBuffer sb=new StringBuffer();
		
		GbjbqkxzccSql GbjbqkxzccSql=new GbjbqkxzccSql();
		GbjbqkxzccSql.returnSqlSelect(sb);
	    sb.append(" FROM (SELECT a01.a0000, "//人员统一标识符
				  + " a01.A0221, "//当前职务层次
				  + " a01.A0288 "//任现职务层次时间   多
	          +" FROM A01 a01 "//人员基本信息表
	         +"  ");
        GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
        gbjbqksqlpj.sqlPjExists(sb, groupid);
        gbjbqksqlpj.sqlPjA01(sb);

        sb.append(" ) a group by A0221 "//当前职务层次 分组
        		);
		
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		
		StringBuffer ss=new GbjbqkComm().toJson(list);
		this.getPageElement("jsonString_str").setValue(ss.toString());
		this.getExecuteSG().addExecuteCode("json_func()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 隐藏全零行
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("xianyin.onclick")
	public int xianyin() throws RadowException{
		//设置下拉选不选
		//this.getPageElement("zwlb").setValue("");
		String xy=this.getPageElement("xianyin").getValue();
		this.getPageElement("xy_zwlb").setValue(xy);
		if("1".equals(xy)){//隐藏
			this.getExecuteSG().addExecuteCode("displayzero('"+xy+"')");
			//this.getExecuteSG().addExecuteCode("distot()");
		}else{//显示
			this.getExecuteSG().addExecuteCode("xs_zwlb_zero()");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 隐藏占比
	 * @throws RadowException 
	 */
	@PageEvent("yczb.onclick")
	public int yczb() throws RadowException{
		String xy=this.getPageElement("yczb").getValue();
		if("1".equals(xy)){//隐藏
			this.getExecuteSG().addExecuteCode("yincangzb()");
		}else{//显示
			this.getExecuteSG().addExecuteCode("xszhanbi()");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 职务类别下拉选 复选
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("zwlb.onchange")
	public int zwlb() throws RadowException{
		//设置复选框不选中
		//this.getPageElement("xianyin").setValue("0");
		String zwlb_l=this.getPageElement("zwlb").getValue();
		if(zwlb_l==null||"".equals(zwlb_l)||zwlb_l.length()==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("zwlb_l").setValue(zwlb_l);
		this.getExecuteSG().addExecuteCode("zwlb_xy()");
		//String[] arr=zwlb_l.split(",");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
