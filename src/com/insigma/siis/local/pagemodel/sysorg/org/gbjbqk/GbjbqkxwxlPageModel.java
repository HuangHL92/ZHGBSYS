package com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class GbjbqkxwxlPageModel extends PageModel{

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
		sb.append(" "
	          +" FROM A01 a01 "
				);
		gbjbqksqlpj.sqlPjExists(sb, groupid);
   
        gbjbqksqlpj.sqlPjA01(sb);//条件 a0221 过滤

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

		/////////////最高学历///////////////
		StringBuffer sb=new StringBuffer();
		GbjbqkxwxlSql gbjbqkxwxlsql=new GbjbqkxwxlSql();
		// 拼接 select
		gbjbqkxwxlsql.returnSqlSelect(sb);
		sb.append( " (select * from  ( select A0801B,a0000,A0834,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0834='1' ) a08_1 where  a08_1.rank=1) a08  ");
		sb.append(""
				  +" on a01.a0000=a08.a0000  "
				+ " ");
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		gbjbqksqlpj.sqlPjExists(sb, groupid);
		gbjbqksqlpj.sqlPjA01(sb);

	    sb.append(" ) a group by A0221 "//当前职务层次 分组
	      );
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		
		///////////最高学位///////////
		StringBuffer sb_zgxw=new StringBuffer();
		// 拼接 select
		gbjbqkxwxlsql.returnSqlSelect_zgxw(sb_zgxw);
		sb_zgxw.append( " (select * from  ( select A0901B,a0000,A0835,row_number() over(partition by a0000 order by A0901B asc) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08  ");
		sb_zgxw.append(""
				  +" on a01.a0000=a08.a0000  "
				+ " ");
		gbjbqksqlpj.sqlPjExists(sb_zgxw, groupid);
		gbjbqksqlpj.sqlPjA01(sb_zgxw);

        sb_zgxw.append(" ) a group by A0221 "//当前职务层次 分组
	      );
		List<HashMap<String, Object>> list_zgxw=cq.getListBySQL(sb_zgxw.toString());
		combine_zgxw(list,list_zgxw);
		
		//////////////全日制学历//////////////////////////
		StringBuffer sb_zrzxl=new StringBuffer();
		// 拼接 select
		gbjbqkxwxlsql.returnSqlSelect_qrzxl(sb_zrzxl);
		sb_zrzxl.append( " (select * from  ( select A0801B,a0000,A0831,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0831='1' ) a08_1 where  a08_1.rank=1) a08  ");
		sb_zrzxl.append(""
				  +" on a01.a0000=a08.a0000 "
				+ " ");
		gbjbqksqlpj.sqlPjExists(sb_zrzxl, groupid);
		gbjbqksqlpj.sqlPjA01(sb_zrzxl);

        sb_zrzxl.append(" ) a group by A0221 "//当前职务层次 分组
	      );
		List<HashMap<String, Object>> list_qzrxl=cq.getListBySQL(sb_zrzxl.toString());
		combine_qrzxl(list,list_qzrxl);
		
		/////////////全日制学位//////////////////
		StringBuffer sb_zrzxw=new StringBuffer();
		// 拼接 select
		gbjbqkxwxlsql.returnSqlSelect_qrzxw(sb_zrzxw);
		sb_zrzxw.append( " (select * from  ( select A0901B,a0000,A0832,row_number() over(partition by a0000 order by A0901B asc) rank from a08 where A0832='1' ) a08_1 where  a08_1.rank=1) a08  ");
		sb_zrzxw.append(""
				  +" on a01.a0000=a08.a0000  "
				+ " ");
		gbjbqksqlpj.sqlPjExists(sb_zrzxw, groupid);
		gbjbqksqlpj.sqlPjA01(sb_zrzxw);

        sb_zrzxw.append(" ) a group by A0221 "//当前职务层次 分组
	      );
		List<HashMap<String, Object>> list_qzrxw=cq.getListBySQL(sb_zrzxw.toString());
		combine_qrzxw(list,list_qzrxw);
		
		/////////////////在职学历///////////////
		StringBuffer sb_zzxl=new StringBuffer();
		// 拼接 select
		gbjbqkxwxlsql.returnSqlSelect_zzxl(sb_zzxl);
		sb_zzxl.append( " (select * from  ( select A0801B,a0000,A0838,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0838='1' ) a08_1 where  a08_1.rank=1) a08  ");
		sb_zzxl.append(""
				  +" on a01.a0000=a08.a0000 "
				+ " ");
		gbjbqksqlpj.sqlPjExists(sb_zzxl, groupid);
		gbjbqksqlpj.sqlPjA01(sb_zzxl);

        sb_zzxl.append(" ) a group by A0221 "//当前职务层次 分组
	      );
		List<HashMap<String, Object>> list_zzxl=cq.getListBySQL(sb_zzxl.toString());
		combine_zzxl(list,list_zzxl);
		
		/////////////在职学位////////////
		StringBuffer sb_zzxw=new StringBuffer();
		// 拼接 select
		gbjbqkxwxlsql.returnSqlSelect_zzxw(sb_zzxw);
		sb_zzxw.append( " (select * from  ( select A0901B,a0000,A0839,row_number() over(partition by a0000 order by A0801B asc) rank from a08 where A0839='1' ) a08_1 where  a08_1.rank=1) a08  ");
		sb_zzxw.append(""
				  +" on a01.a0000=a08.a0000 "
				+ " ");
		gbjbqksqlpj.sqlPjExists(sb_zzxw, groupid);
		gbjbqksqlpj.sqlPjA01(sb_zzxw);

        sb_zzxw.append(" ) a group by A0221 "//当前职务层次 分组
	      );
		List<HashMap<String, Object>> list_zzxw=cq.getListBySQL(sb_zzxw.toString());
		combine_zzxw(list,list_zzxw);
		
		//合计
		StringBuffer sb1=new StringBuffer();
		sb1.append(
				" select a.A0221,"
					  +" count(a0000) heji "//合计
				 +" FROM (SELECT a01.a0000, "//人员统一标识符
				  		+ " a01.A0221 "//当前职务层次
			          +" FROM A01 a01 "//人员基本信息表
			       //  +" WHERE a01.status = '1' "
			         );//任职状态 1 在任
		gbjbqksqlpj.sqlPjExists(sb1, groupid);
		gbjbqksqlpj.sqlPjA01(sb1);
        sb1.append(" ) a group by A0221 "//当前职务层次 分组
			);
		List<HashMap<String, Object>> list1=cq.getListBySQL(sb1.toString());
		combine(list,list1);
		StringBuffer ss=new GbjbqkComm().toJson(list);
		this.getPageElement("jsonString_str").setValue(ss.toString());
		this.getExecuteSG().addExecuteCode("json_func()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 合并两个 List<HashMap<String, Object>> 在职学位合并到list中
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_zzxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
				continue;
			}
			temp=(String)list.get(i).get("a0221");
			if(temp.equals((String)list1.get(i).get("a0221"))){
				list.get(i).put("bsz", list1.get(i).get("bsz"));//博士
				list.get(i).put("ssz", list1.get(i).get("ssz"));//硕士
				list.get(i).put("xsz", list1.get(i).get("xsz"));//学士
			}else{
				combine_zzxw(list,list1,temp,i);
			}
		}
		return list;
	}
	public void combine_zzxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("bsz", list1.get(j).get("bsz"));//博士
				list.get(i).put("ssz", list1.get(j).get("ssz"));//硕士
				list.get(i).put("xsz", list1.get(j).get("xsz"));//学士
			}
		}
	}
	
	/**
	 * 合并两个 List<HashMap<String, Object>> 在职学历合并到list中
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_zzxl(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
				continue;
			}
			temp=(String)list.get(i).get("a0221");
			if(temp.equals((String)list1.get(i).get("a0221"))){
				list.get(i).put("yjsz", list1.get(i).get("yjsz"));//研究生
				list.get(i).put("dxbkz", list1.get(i).get("dxbkz"));//大学本科
				list.get(i).put("dxzzz", list1.get(i).get("dxzzz"));//大学专科
				list.get(i).put("zzz", list1.get(i).get("zzz"));//中专
				list.get(i).put("gzjyxz", list1.get(i).get("gzjyxz"));//高中及以下
			}else{
				combine_zzxl(list,list1,temp,i);
			}
		}
		return list;
	}
	public void combine_zzxl(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("yjsz", list1.get(j).get("yjsz"));//研究生
				list.get(i).put("dxbkz", list1.get(j).get("dxbkz"));//大学本科
				list.get(i).put("dxzzz", list1.get(j).get("dxzzz"));//大学专科
				list.get(i).put("zzz", list1.get(j).get("zzz"));//中专
				list.get(i).put("gzjyxz", list1.get(j).get("gzjyxz"));//高中及以下
			}
		}
	}
	
	/**
	 * 合并两个 List<HashMap<String, Object>> 全日制学位合并到list中
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_qrzxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
				continue;
			}
			if(list1.size()-1<i){
				return list;
			}
			temp=(String)list.get(i).get("a0221");
			if(temp.equals((String)list1.get(i).get("a0221"))){
				list.get(i).put("bsq", list1.get(i).get("bsq"));//博士
				list.get(i).put("ssq", list1.get(i).get("ssq"));//硕士
				list.get(i).put("xsq", list1.get(i).get("xsq"));//学士
			}else{
				combine_qrzxw(list,list1,temp,i);
			}
		}
		return list;
	}
	public void combine_qrzxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("bsq", list1.get(j).get("bsq"));//博士
				list.get(i).put("ssq", list1.get(j).get("ssq"));//硕士
				list.get(i).put("xsq", list1.get(j).get("xsq"));//学士
			}
		}
	}
	
	/**
	 * 合并两个 List<HashMap<String, Object>> 全日制学历合并到list中
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_qrzxl(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
				continue;
			}
			if(list1.size()-1<i){
				return list;
			}
			temp=(String)list.get(i).get("a0221");
			if(temp.equals((String)list1.get(i).get("a0221"))){
				list.get(i).put("yjsq", list1.get(i).get("yjsq"));//研究生
				list.get(i).put("dxbkq", list1.get(i).get("dxbkq"));//大学本科
				list.get(i).put("dxzzq", list1.get(i).get("dxzzq"));//大学专科
				list.get(i).put("dxzzq", list1.get(i).get("zzq"));//中专
				list.get(i).put("dxzzq", list1.get(i).get("gzjyxq"));//高中及以下
			}else{
				combine_qrzxl(list,list1,temp,i);
			}
		}
		return list;
	}
	public void combine_qrzxl(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("yjsq", list1.get(j).get("yjsq"));//研究生
				list.get(i).put("dxbkq", list1.get(j).get("dxbkq"));//大学本科
				list.get(i).put("dxzzq", list1.get(j).get("dxzzq"));//大学专科
				list.get(i).put("dxzzq", list1.get(j).get("zzq"));//中专
				list.get(i).put("dxzzq", list1.get(j).get("gzjyxq"));//高中及以下
			}
		}
	}
	
	/**
	 * 合并两个 List<HashMap<String, Object>> 最高学位合并到list中
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine_zgxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
				continue;
			}
			if(list1.size()-1<i){
				return list;
			}
			temp=(String)list.get(i).get("a0221");
			if(temp.equals((String)list1.get(i).get("a0221"))){
				list.get(i).put("bs", list1.get(i).get("bs"));//博士
				list.get(i).put("ss", list1.get(i).get("ss"));//硕士
				list.get(i).put("xs", list1.get(i).get("xs"));//学士
			}else{
				combine_zgxw(list,list1,temp,i);
			}
		}
		return list;
	}
	public void combine_zgxw(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("bs", list1.get(j).get("bs"));//博士
				list.get(i).put("ss", list1.get(j).get("ss"));//硕士
				list.get(i).put("xs", list1.get(j).get("xs"));//学士
			}
		}
	}
	/**
	 * 合并两个 List<HashMap<String, Object>> 合计合并到list中
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list.size()==0){
			return list;
		}
		String temp="";
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
				continue;
			}
			if(list1.size()-1<i){
				return list;
			}
			temp=(String)list.get(i).get("a0221");
			if(temp.equals((String)list1.get(i).get("a0221"))){
				list.get(i).put("heji", list1.get(i).get("heji"));//合计
			}else{
				combine_jy(list,list1,temp,i);
			}
		}
		return list;
	}
	
	public void combine_jy(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("heji", list1.get(j).get("heji"));//合计
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
		String xy=this.getPageElement("xianyin").getValue();
		this.getPageElement("xy_zwlb").setValue(xy);
		if("1".equals(xy)){//隐藏
			this.getExecuteSG().addExecuteCode("displayzero('"+xy+"')");
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
		String zwlb_l=this.getPageElement("zwlb").getValue();
		if(zwlb_l==null||"".equals(zwlb_l)||zwlb_l.length()==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("zwlb_l").setValue(zwlb_l);
		this.getExecuteSG().addExecuteCode("zwlb_xy()");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
