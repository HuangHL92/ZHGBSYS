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

public class GbjbqkfeiPageModel extends PageModel{

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
		//拼接sql from 
		sb.append("  "//是否具有两年以上基层工作经历
	          +" FROM A01 a01 "
				);
		 gbjbqksqlpj.sqlPjExists(sb, groupid);//
	    sb.append(" "
		         + " and a01.a0141!='01' "         //非中共党员10
		         );
	   
	    gbjbqksqlpj.sqlPjA01(sb);////条件 a0221 过滤

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
		//设置复选框选中
		this.getPageElement("xianyin").setValue("1");
		//隐藏占比选中
		this.getPageElement("yczb").setValue("1");
		
		this.getPageElement("xy_zwlb").setValue("1");
		
		
		//单位id
		String subWinIdBussessId = this.getPageElement("subWinIdBussessId").getValue();
		
		
		String[] param_arr = subWinIdBussessId.split("\\$");
		String groupid = param_arr[0].trim().replace("|", "'").split("group")[0];

		StringBuffer sb=new StringBuffer();
		
		
		GbjbqkSqlZs gbjbqksqlzs=new GbjbqkSqlZs();
		//拼接sql select
		gbjbqksqlzs.returnGbjbqkSqlZs1(sb,"fei");
		//拼接sql from
		gbjbqksqlzs.returnGbjbqkSqlZs11(sb);
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		gbjbqksqlpj.sqlPjExists(sb, groupid);
		sb.append(" and a01.a0141!='01' ");//非中共党员
		//条件 a0221 过滤
  	    gbjbqksqlpj.sqlPjA01(sb);
	    sb.append(" ) a group by A0221 "//当前职务层次 分组
	    		);
		
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		StringBuffer sb1=new StringBuffer();
		//拼接sql select
		gbjbqksqlzs.returnGbjbqkSqlZs2(sb1);
		//拼接sqlfrom
		gbjbqksqlzs.returnGbjbqkSqlZs21(sb1);
		gbjbqksqlpj.sqlPjExists(sb1, groupid);
		sb1.append(" and a01.a0141!='01' ");//非中共党员
		//条件 a0221 过滤
  	    gbjbqksqlpj.sqlPjA01(sb1);
	
        sb1.append(" ) a group by A0221 "//当前职务层次 分组);
        		);
		List<HashMap<String, Object>> list1=cq.getListBySQL(sb1.toString());
		list=combine(list,list1);
		StringBuffer ss=new GbjbqkComm().toJson(list);
		this.getPageElement("jsonString_str").setValue(ss.toString());
		
		this.getExecuteSG().addExecuteCode("json_func()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 合并两个 List<HashMap<String, Object>> list1中的数据合并到list中
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list1==null||list1.size()==0||list.size()==0){
			return list;
		}
		String temp="";
		if(list.size()<=list1.size()){
			for(int i=0;i<list.size();i++){
				if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
					continue;
				}
				temp=(String)list.get(i).get("a0221");
				if(temp.equals((String)list1.get(i).get("a0221"))){
					list.get(i).put("bs", list1.get(i).get("bs"));//博士
					list.get(i).put("ss", list1.get(i).get("ss"));//硕士
					list.get(i).put("xs", list1.get(i).get("xs"));//学士
				}else{
					combine_jy(list,list1,temp,i);
				}
			}
		}else{
			for(int i=0;i<list1.size();i++){
				if(list1.get(i).get("a0221")==null||"".equals(list1.get(i).get("a0221"))){
					continue;
				}
				temp=(String)list1.get(i).get("a0221");
				if(temp.equals((String)list.get(i).get("a0221"))){
					list.get(i).put("bs", list1.get(i).get("bs"));//博士
					list.get(i).put("ss", list1.get(i).get("ss"));//硕士
					list.get(i).put("xs", list1.get(i).get("xs"));//学士
				}else{
					combine_jy_f(list,list1,temp,i);
				}
			}
		}
		return list;
	}
	
	public void combine_jy_f(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list.size();j++){
			if(temp.equals((String)list.get(j).get("a0221"))){
				list.get(j).put("bs", list1.get(i).get("bs"));//博士
				list.get(j).put("ss", list1.get(i).get("ss"));//硕士
				list.get(j).put("xs", list1.get(i).get("xs"));//学士
			}
		}
	}
	
	public void combine_jy(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("bs", list1.get(j).get("bs"));//博士
				list.get(i).put("ss", list1.get(j).get("ss"));//硕士
				list.get(i).put("xs", list1.get(j).get("xs"));//学士
			}
		}
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
