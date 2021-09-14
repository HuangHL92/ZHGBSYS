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
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.sysorg.org.GbjbqkSubPageModel;

public class GbjbqkxzccSubPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("initX")
	public int initX() throws RadowException{
		this.getPageElement("xy_zwlb").setValue("1");
		String subWinIdBussessId2 = this.getPageElement("subWinIdBussessId").getValue();
		
		if(subWinIdBussessId2==null||subWinIdBussessId2.length()==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] param_arr = subWinIdBussessId2.split("\\$");
		String groupid = param_arr[0].trim().replace("|", "'").split("group")[0];
		
//		if(subWinIdBussessId2==null||subWinIdBussessId2.length()==0){
//			return EventRtnType.NORMAL_SUCCESS;
//		}else{
//			groupid=groupid.substring(12, groupid.length());
//			groupid=groupid.substring(0, groupid.length()-26);
//			groupid=" and ( "+groupid+ " ";
//		}
		
		String col="";
		String row="";
		String query_tj="";
		col=param_arr[param_arr.length-3];
		row=param_arr[param_arr.length-2];
		query_tj=param_arr[param_arr.length-4];
		String title=param_arr[param_arr.length-1];
		if(param_arr.length==6){//1 第一次反查
		}else{//大于一次反查
			int m=(param_arr.length-6)/4;
			for(int i=1;i<=m;i++){
				col=col+","+param_arr[param_arr.length-3-4*i];
				row=row+","+param_arr[param_arr.length-2-4*i];
				title=title+"-"+param_arr[param_arr.length-1-4*i];
				query_tj=query_tj+","+param_arr[param_arr.length-4-4*i];
			}
			
		}
		
		this.getPageElement("title_h").setValue(title);
		this.getPageElement("dwid_h").setValue(groupid);
		this.getPageElement("col_num_h").setValue(col);
		this.getPageElement("row_num_h").setValue(row);
		this.getPageElement("query_tj_h").setValue(query_tj);
		String userid=SysUtil.getCacheCurrentUser().getId();
		this.getPageElement("userid_h").setValue(userid);
		this.setNextEventName("gridfc.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("gridfc.dogridquery")
	public int zwxxQuery(int start, int limit) throws RadowException {
		String groupid = this.getPageElement("dwid_h").getValue();
		String col=this.getPageElement("col_num_h").getValue();
		String row=this.getPageElement("row_num_h").getValue();
		String query_tj=this.getPageElement("query_tj_h").getValue();
		query_tj="'"+query_tj.replace(",", "','")+"'";
		query_tj=query_tj.replace("请您选择...", "");
		query_tj=query_tj.replace("全部", "");
		query_tj=query_tj.replace(" ", "");
		if(col==null){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(row==null){
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] param_arr_col = col.split("\\,");
		String[] param_arr_row = row.split("\\,");
		if(param_arr_col.length<=0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(param_arr_row.length<=0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		StringBuffer sb=new StringBuffer();
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		//拼接sql select
		gbjbqksqlpj.returnPjGridSql(sb);
		sb.append(" FROM A01 a01  "//人员基本信息表
	         );
		gbjbqksqlpj.sqlPjExists(sb, groupid);
		  //条件 a0221 过滤
	  	  gbjbqksqlpj.sqlPjA01(sb);
          for(int i=0;i<param_arr_row.length;i++){
        	  new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb);
          }
          if(DBType.ORACLE==DBUtil.getDBType()){
        	  for(int i=0;i<param_arr_col.length;i++){
 	        	 if(Integer.valueOf(param_arr_col[i])<3){//
 	        		 sb.append(" and 1=0 ");
 	        	 }else if(Integer.valueOf(param_arr_col[i])==3){//合计 不加列条件
 	        		 
 	        	 }else if(Integer.valueOf(param_arr_col[i])==4){//不满1年
 	        		 sb.append(" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-11),'yyyy-mm-dd') "
 		           		+" ),'yyyy-mm-dd'),12)>trunc(sysdate) and to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,1),'yyyy-mm-dd')), 'yyyy-mm-dd')"
 		           		+ "<trunc(sysdate) ");
 	        	 }else if(Integer.valueOf(param_arr_col[i])>4&&Integer.valueOf(param_arr_col[i])<=42&&Integer.valueOf(param_arr_col[i])%2==0){//偶数列
 	        		 sb.append(" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-"+((Integer.valueOf(param_arr_col[i])-2)/2*12+1)+"),'yyyy-mm-dd') "
 			           +" ),'yyyy-mm-dd'),"+((Integer.valueOf(param_arr_col[i])-2)/2*12)+")>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-"+(((Integer.valueOf(param_arr_col[i])-2)/2-1)*12-1)+"),'yyyy-mm-dd') "
 			           +" ),'yyyy-mm-dd'),"+(((Integer.valueOf(param_arr_col[i])-2)/2-1)*12)+")<=trunc(sysdate) ");
 	        	 }else if(Integer.valueOf(param_arr_col[i])==44){//20年及以上
 	        		 sb.append(" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-239),'yyyy-mm-dd') "
 	        			+" ),'yyyy-mm-dd'),240)<=trunc(sysdate) ");
 	        	 } else{
 	        		 sb.append(" and 1=0 ");
 	        	 }
 	          }
			}else if(DBType.MYSQL==DBUtil.getDBType()){
				for(int i=0;i<param_arr_col.length;i++){
		        	 if(Integer.valueOf(param_arr_col[i])<3){//
		        		 sb.append(" and 1=0 ");
		        	 }else if(Integer.valueOf(param_arr_col[i])==3){//合计 不加列条件
		        		 
		        	 }else if(Integer.valueOf(param_arr_col[i])==4){//不满1年
		        		 sb.append(" and date_add(str_to_date(if(A0288='' or a0288 is null,date_format(date_sub(curdate(), interval 11 month),'%Y%M%D'),a0288 "
			           		+" ),'%Y%M%D'),interval 12 month)>curdate() and str_to_date(if(A0288='' or a0288 is null,date_format(date_add(curdate(),interval 1 month),'%Y%M%D'),a0288), '%Y%M%D')"
			           		+ "<curdate() ");
		        	 }else if(Integer.valueOf(param_arr_col[i])>4&&Integer.valueOf(param_arr_col[i])<=42&&Integer.valueOf(param_arr_col[i])%2==0){//偶数列
		        		 sb.append(" and date_add(str_to_date(if(A0288='' or a0288 is null,date_formar(date_sub(curdate(),interval "+((Integer.valueOf(param_arr_col[i])-2)/2*12+1)+" month),'%Y%M%D'),a0288 "
				           +" ),'%Y%M%D'),interval "+((Integer.valueOf(param_arr_col[i])-2)/2*12)+" month)>curdate() and  date_add(to_date(if(A0288='' or a0288 is null,date_format(date_sub(curdate(),interval "+(((Integer.valueOf(param_arr_col[i])-2)/2-1)*12-1)+" month),'%Y%M%D'),a0288 "
				           +" ),'%Y%M%D'), interval "+(((Integer.valueOf(param_arr_col[i])-2)/2-1)*12)+" month)<=curdate() ");
		        	 }else if(Integer.valueOf(param_arr_col[i])==44){//20年及以上
		        		 sb.append(" and date_add(str_to_date(if(A0288='' or a0288 is null,date_fromat(date_sub(curdate(),interval 239 month),'%Y%M%D'),a0288 "
		        			+" ),'%Y%M%D'),interval 240 month)<=curdate() ");
		        	 } else{
		        		 sb.append(" and 1=0 ");
		        	 }
		          }
			}else{
				throw new RadowException("发现未知数据源，请联系系统管理员!");
			}
          
          GbjbqkSqlZs gbjbqksqlzs=new GbjbqkSqlZs();
          gbjbqksqlzs.query_tj(sb,query_tj);//职务等级  查询条件
		this.pageQuery(sb.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 统计，反查结果
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
		String groupid = this.getPageElement("dwid_h").getValue();
		String col=this.getPageElement("col_num_h").getValue();
		String row=this.getPageElement("row_num_h").getValue();
		String query_tj=this.getPageElement("query_tj_h").getValue();
		query_tj="'"+query_tj.replace(",", "','")+"'";
		query_tj=query_tj.replace("请您选择...", "");
		query_tj=query_tj.replace(" ", "");
		query_tj=query_tj.replace("全部", "");
		String[] param_arr_col = col.split("\\,");
		String[] param_arr_row = row.split("\\,");
		if(param_arr_col.length>6){
			this.setMainMessage("请不要循环做过多的反查!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(param_arr_col.length<=0){
			this.setMainMessage("列参数错误!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(param_arr_row.length<=0){
			this.setMainMessage("行参数错误!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
	
		StringBuffer sb=new StringBuffer();
		GbjbqkxzccSql GbjbqkxzccSql=new GbjbqkxzccSql();
		GbjbqkxzccSql.returnSqlSelect(sb);
		sb.append(" "
		  +" FROM (SELECT a01.a0000, "//人员统一标识符
					  + " a01.a0221, "//当前职务层次
					  + " a01.a0288 "//任现职务层次时间   多
		          +" from a01 a01 "//人员基本信息表
			         );
		 GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		 gbjbqksqlpj.sqlPjExists(sb, groupid);
		 //条件 a0221 过滤
	  	 gbjbqksqlpj.sqlPjA01(sb);
				for(int i=0;i<param_arr_row.length;i++){
					new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb);
				}
//				 for(int i=0;i<param_arr_col.length;i++){
//					 if(Integer.valueOf(param_arr_col[i])<3){//
//		        		 sb.append(" and 1=0 ");
//		        	 }else if(Integer.valueOf(param_arr_col[i])==3){//合计 不加列条件
//		        		 
//		        	 }else if(Integer.valueOf(param_arr_col[i])==4){//不满1年
//		        		 sb.append(" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-11),'yyyy-mm-dd') "
//			           		+" ),'yyyy-mm-dd'),12)>trunc(sysdate) and to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,1),'yyyy-mm-dd')), 'yyyy-mm-dd')"
//			           		+ "<trunc(sysdate) ");
//		        	 }else if(Integer.valueOf(param_arr_col[i])>4&&Integer.valueOf(param_arr_col[i])<=42&&Integer.valueOf(param_arr_col[i])%2==0){//偶数列
//		        		 sb.append(" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-"+((Integer.valueOf(param_arr_col[i])-2)/2*12+1)+"),'yyyy-mm-dd') "
//				           +" ),'yyyy-mm-dd'),"+((Integer.valueOf(param_arr_col[i])-2)/2*12)+")>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-"+(((Integer.valueOf(param_arr_col[i])-2)/2-1)*12-1)+"),'yyyy-mm-dd') "
//				           +" ),'yyyy-mm-dd'),"+(((Integer.valueOf(param_arr_col[i])-2)/2-1)*12)+")<=trunc(sysdate) ");
//		        	 }else if(Integer.valueOf(param_arr_col[i])==44){//20年及以上
//		        		 sb.append(" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-239),'yyyy-mm-dd') "
//		        			+" ),'yyyy-mm-dd'),240)<=trunc(sysdate) ");
//		        	 } else{
//		        		 sb.append(" and 1=0 ");
//		        	 }
//				 }
		 if(DBType.ORACLE==DBUtil.getDBType()){
        	  for(int i=0;i<param_arr_col.length;i++){
 	        	 if(Integer.valueOf(param_arr_col[i])<3){//
 	        		 sb.append(" and 1=0 ");
 	        	 }else if(Integer.valueOf(param_arr_col[i])==3){//合计 不加列条件
 	        		 
 	        	 }else if(Integer.valueOf(param_arr_col[i])==4){//不满1年
 	        		 sb.append(" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-11),'yyyy-mm-dd') "
 		           		+" ),'yyyy-mm-dd'),12)>trunc(sysdate) and to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,1),'yyyy-mm-dd')), 'yyyy-mm-dd')"
 		           		+ "<trunc(sysdate) ");
 	        	 }else if(Integer.valueOf(param_arr_col[i])>4&&Integer.valueOf(param_arr_col[i])<=42&&Integer.valueOf(param_arr_col[i])%2==0){//偶数列
 	        		 sb.append(" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-"+((Integer.valueOf(param_arr_col[i])-2)/2*12+1)+"),'yyyy-mm-dd') "
 			           +" ),'yyyy-mm-dd'),"+((Integer.valueOf(param_arr_col[i])-2)/2*12)+")>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-"+(((Integer.valueOf(param_arr_col[i])-2)/2-1)*12-1)+"),'yyyy-mm-dd') "
 			           +" ),'yyyy-mm-dd'),"+(((Integer.valueOf(param_arr_col[i])-2)/2-1)*12)+")<=trunc(sysdate) ");
 	        	 }else if(Integer.valueOf(param_arr_col[i])==44){//20年及以上
 	        		 sb.append(" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-239),'yyyy-mm-dd') "
 	        			+" ),'yyyy-mm-dd'),240)<=trunc(sysdate) ");
 	        	 } else{
 	        		 sb.append(" and 1=0 ");
 	        	 }
 	          }
			}else if(DBType.MYSQL==DBUtil.getDBType()){
				for(int i=0;i<param_arr_col.length;i++){
		        	 if(Integer.valueOf(param_arr_col[i])<3){//
		        		 sb.append(" and 1=0 ");
		        	 }else if(Integer.valueOf(param_arr_col[i])==3){//合计 不加列条件
		        		 
		        	 }else if(Integer.valueOf(param_arr_col[i])==4){//不满1年
		        		 sb.append(" and date_add(str_to_date(if(A0288='' or a0288 is null,date_format(date_sub(curdate(), interval 11 month),'%Y%M%D'),a0288 "
			           		+" ),'%Y%M%D'),interval 12 month)>curdate() and str_to_date(if(A0288='' or a0288 is null,date_format(date_add(curdate(),interval 1 month),'%Y%M%D'),a0288), '%Y%M%D')"
			           		+ "<curdate() ");
		        	 }else if(Integer.valueOf(param_arr_col[i])>4&&Integer.valueOf(param_arr_col[i])<=42&&Integer.valueOf(param_arr_col[i])%2==0){//偶数列
		        		 sb.append(" and date_add(str_to_date(if(A0288='' or a0288 is null,date_formar(date_sub(curdate(),interval "+((Integer.valueOf(param_arr_col[i])-2)/2*12+1)+" month),'%Y%M%D'),a0288 "
				           +" ),'%Y%M%D'),interval "+((Integer.valueOf(param_arr_col[i])-2)/2*12)+" month)>curdate() and  date_add(to_date(if(A0288='' or a0288 is null,date_format(date_sub(curdate(),interval "+(((Integer.valueOf(param_arr_col[i])-2)/2-1)*12-1)+" month),'%Y%M%D'),a0288 "
				           +" ),'%Y%M%D'), interval "+(((Integer.valueOf(param_arr_col[i])-2)/2-1)*12)+" month)<=curdate() ");
		        	 }else if(Integer.valueOf(param_arr_col[i])==44){//20年及以上
		        		 sb.append(" and date_add(str_to_date(if(A0288='' or a0288 is null,date_fromat(date_sub(curdate(),interval 239 month),'%Y%M%D'),a0288 "
		        			+" ),'%Y%M%D'),interval 240 month)<=curdate() ");
		        	 } else{
		        		 sb.append(" and 1=0 ");
		        	 }
		          }
			}else{
				throw new RadowException("发现未知数据源，请联系系统管理员!");
			}
		 GbjbqkSqlZs gbjbqksqlzs=new GbjbqkSqlZs();
          gbjbqksqlzs.query_tj(sb,query_tj);//职务等级  查询条件
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
	 * 职务类别下拉选 复选
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("zwlb.onchange")
	public int zwlb() throws RadowException{
		
		String zwlb_l=this.getPageElement("zwlb").getValue();
		if(zwlb_l==null||"".equals(zwlb_l)||zwlb_l.length()==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("zwlb_l").setValue(zwlb_l);
		this.getExecuteSG().addExecuteCode("zwlb_xy()");
		
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

}
