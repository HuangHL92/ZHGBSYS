package com.insigma.siis.local.pagemodel.customquery.formanalysis;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.privilege.util.DateUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkComm;

public class formanalysis_ssygwyPageModel extends PageModel{

	public formanalysis_ssygwyPageModel() {
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
			String groupid=this.getPageElement("subWinIdBussessId2").getValue();
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
			map.put("sid", sid);
			map.put("cq", cq);
			map.put("num", "");
			map.put("a0160", " and a01.A0160='1' ");
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("year", year);
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
			map.put("a0221",   " and a01.a0221 in ('1A11',"//省部级正职
                    + "'1A12', "//省部级副职
                    + "'1A50',"//科员 
                    + "'1A60',"//办事员 
                    + "'1A98',"//试用期人员
                    + "'1A99')"//其他
                    );
			map.put("a0219", "");
			map.put("a0279", "");
			StringBuffer ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str1").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('1')");//
			
			map.put("a0221",   " and a01.a0221 in ('1A21',"//厅局级正职
                    + "'1A22', "//厅局级副职
                    + "'1A31',"//县处级正职
                    + "'1A32',"//县处级副职
                    + "'1A41',"//乡科级正职
                    + "'1A42')"//其乡科级副职
                    );
			map.put("a0279", " and a02.a0279='1' ");
			map.put("a0219", " and a02.a0219='1' ");
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str2").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('2')");//正职
			
			map.put("a0221",   " and a01.a0221 in ('1A21',"//厅局级正职
                    + "'1A22', "//厅局级副职
                    + "'1A31',"//县处级正职
                    + "'1A32',"//县处级副职
                    + "'1A41',"//乡科级正职
                    + "'1A42')"//其乡科级副职
                    );
			map.put("a0219", " and (a02.a0219!='1' or a02.a0219 is null) ");
			map.put("a0279", " and a02.a0279='1' ");
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str3").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func('3')");//副职
			
//			map.put("a0221",   " and a01.a0221 like '1A%' "//公务员
//                    );
//			map.put("a0219", "");
//			map.put("a0279", "");
//			map.put("num", "4");
//			ss=new GbjbqkComm().toJson(returnList(map));
//			this.getPageElement("jsonString_str4").setValue(ss.toString());
//			this.getExecuteSG().addExecuteCode("json_func('4')");//合计
			
//			map.put("a0221",   " and (a01.a0221 like '1A%' "//公务员
//					+ " or a01.a0221 like '2%' "//人民警察警员职务序列 
//					+ " or a01.a0221 like '3%' "//法官
//					+ " or a01.a0221 like '7%' ) "//深圳市行政执法类公务员
//                    );
//			map.put("a0219", "");
//			map.put("a0279", "");
//			map.put("num", "5");
//			ss=new GbjbqkComm().toJson(returnList(map));
//			this.getPageElement("jsonString_str5").setValue(ss.toString());
//			this.getExecuteSG().addExecuteCode("json_func(5)");//合计
			
			
			map.put("a0221", " and a01.a0221 like '1B%' ");//专业技术类
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "4");//合计
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str4").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(4)");
			
			
			map.put("a0221", " and a01.a0221 like '1C%' ");//专业技术类
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "5");//合计
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str5").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(5)");
			
			map.put("a0221", " and (a01.a0221 like '5%' or a01.a0221 like '6%')");//专业技术类
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "7");//合计
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str7").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(7)");
			
			map.put("a0221",   " and (a01.a0221 like '7%' or "//
					+ "  a01.a0221 like '3%' or "//法官
					+ "  a01.a0221 like '4%' or "//检察官等级
					+ " a01.a0221 like '2%' )"//深圳市行政执法类公务员
                    );
			map.put("a0219", "");
			map.put("a0279", "");
			map.put("num", "6");//合计
			ss=new GbjbqkComm().toJson(returnList(map));
			this.getPageElement("jsonString_str6").setValue(ss.toString());
			this.getExecuteSG().addExecuteCode("json_func(6)");//2表合计
		
		} catch (AppException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public List<HashMap<String, Object>> returnList(Map<String,Object> map) throws RadowException, AppException{
		CommQuery cq=(CommQuery)map.get("cq");
		StringBuffer sb=new StringBuffer();
		returnGbjbqkSql(sb,map);//拼接select 
		returnZb(sb);
		/*if(map.get("sql")==null||"".equals(map.get("sql"))||((String)map.get("sql")).length()==0){*///列表未查询数据
			/*if("system".equals(map.get("username"))){*/
				/*sb.append(" ,(select  a02.a0000 "
						+ " from a02 "
						+ " where "
						+ " a02.a0255 = '1' "
						+ map.get("a0219")
						+ map.get("a0279")
						+ " group by a02.a0000"
                     + " ) a02 where "
                     + "  a01.a0163='1' "//人员管理状态   等
                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
                     + "  and a02.a0000=a01.a0000  "
                     + map.get("a0221")
						);*/
			if(map.get("a0219")!=null&&!"".equals(map.get("a0219"))){
				sb.append(" ,(select  a02.a0000 "
						+ " from a02 "
						+ " where "
						+ " a02.a0281 = 'true' "
						+ map.get("a0219")
						+ map.get("a0279")
						+ " group by a02.a0000"
                     + " ) a02 where "
                     + "  a01.a0163='1' "//人员管理状态   等
                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
                     + "  and a02.a0000=a01.a0000  "
                     + "  and a01.a0195 like '"+map.get("groupid")+"%' "
                     + map.get("a0221")
						);
			}else{
				sb.append(" where "
	                     + "  a01.a0163='1' "//人员管理状态   等
	                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
	                     + "  and a01.a0195 like '"+map.get("groupid")+"%' "
	                     + map.get("a0221")
							);
			}
				
			/*}else{
				sb.append(" ,(select  a02.a0000 "
						+ " from a02, competence_userdept cu "
						+ " where "
						+ " a02.a0255 = '1' "
						+ map.get("a0219")
						+ map.get("a0279")
                     + " and a02.A0201B = cu.b0111 "
                     + " and cu.userid = '"+map.get("userid")+"' "
                     + " group by a02.a0000"
                     + " ) a02 where "
                     + "  a01.a0163='1' "//人员管理状态   等
                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
                     + "  and a02.a0000=a01.a0000  "
                     + map.get("a0221")
						);*/
			/*}*/
		/*}else{//列表查询数据
			String [] arrPersonnelList=((String)map.get("sql")).split("@@");
			if(arrPersonnelList.length==2){
			}else{
				throw new RadowException("数据异常!");
			}
			String where="";
			if("tempForCount".equals(arrPersonnelList[0])){//临时表
				String sid = (String)map.get("sid");
				if("".equals(map.get("a0219"))){
					where =" ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) ";
					sb.append(", "+where+ " a02 where a02.a0000=a01.a0000 "
							+ map.get("a0221")
							+ " ");
				}else{
					where ="(select aa.a0000,bb.a0219,bb.a0279 from ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) aa,a02 bb where aa.a0000=bb.a0000)";
					sb.append(", "+where+ " a02 where a02.a0000=a01.a0000 "
							+ map.get("a0221")
							+ map.get("a0219")
							+ map.get("a0279")
							+ " ");
				}
			}else if("conditionForCount".equals(arrPersonnelList[0])){//查询条件
				where=((String)map.get("sql")).substring(((String)map.get("sql")).indexOf("from A01 a01"), ((String)map.get("sql")).length());
				if("".equals(map.get("a0219"))){
					
				}else{
					where=where.substring(0, where.indexOf("from a02")+14)+ " "+ ((String)map.get("a0219")).substring(4,((String)map.get("a0219")).length())+ map.get("a0279")+ " and "+where.substring(where.indexOf("from a02")+14, where.length());
				}
				where = ",( select a0000 "+where + " )  a02 where a02.a0000=a01.a0000"
						+ map.get("a0221")
						+ " ";
				sb.append(where);
			}else{
				throw new RadowException("人员信息界面数据异常!");
			}
			
		}*/
		/*if(((String)map.get("rylb"))!=null&&((String)map.get("rylb")).indexOf("\'")!=-1){
	    	sb.append(" and a01.A0165 not in ( "+((String)map.get("rylb"))+") ");
	    }*/
		sb.append(map.get("a0160"));//
		sb.append(" ) a "+(map.get("num").equals("")?" group by A0221 ":"") //当前职务层次 分组
		          );
		//分组查询(包含学历)()
		System.out.println(sb.toString());
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		
//		StringBuffer sb1=new StringBuffer();
//		returnGbjbqkSql2(sb1,map);//拼接select 
//		returnGbjbqkSql2sub(sb1);
//		if(((String)map.get("sql"))==null||"".equals(((String)map.get("sql")))||((String)map.get("sql")).length()==0){//列表未查询数据
//			if("system".equals(((String)map.get("username")))){
//				sb1.append(" ,(select  a02.a0000 "
//						+ " from a02 "
//						+ " where "
//						+ " a02.a0255 = '1' "
//						+ map.get("a0219")
//						+ map.get("a0279")
//						+ " group by a02.a0000"
//                     + " ) a02 where "
//                     + "  a01.a0163='1' "//人员管理状态   等
//                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
//                     + "  and a02.a0000=a01.a0000  "
//                     + map.get("a0221")
//						);
//			}else{
//				sb1.append(" ,(select  a02.a0000 "
//						+ " from a02, competence_userdept cu "
//						+ " where "
//						+ " a02.a0255 = '1' "
//						+ map.get("a0219")
//						+ map.get("a0279")
//                     + " and a02.A0201B = cu.b0111 "
//                     + " and cu.userid = '"+((String)map.get("userid"))+"' "
//                     + " group by a02.a0000"
//                     + " ) a02 where "
//                     + "  a01.a0163='1' "//人员管理状态   等
//                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
//                     + "  and a02.a0000=a01.a0000  "
//                     + map.get("a0221")
//						);
//			}
//		}else{//列表查询数据
//			String [] arrPersonnelList=((String)map.get("sql")).split("@@");
//			if(arrPersonnelList.length==2){
//			}else{
//				throw new RadowException("数据异常!");
//			}
//			String where="";
//			if("tempForCount".equals(arrPersonnelList[0])){//临时表
//				String sid = (String)map.get("sid");
//				if("".equals(map.get("a0219"))){
//					where =" ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) ";
//					sb1.append(", "+where+ " a02 where a02.a0000=a01.a0000 "
//							+ map.get("a0221")
//							+ " ");
//				}else{
//					where ="(select aa.a0000,bb.a0219,bb.a0279 from ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) aa,a02 bb where aa.a0000=bb.a0000)";
//					sb1.append(", "+where+ " a02 where a02.a0000=a01.a0000 "
//							+ map.get("a0221")
//							+ map.get("a0219")
//							+ map.get("a0279")
//							+ " ");
//				}
//				
//			}else if("conditionForCount".equals(arrPersonnelList[0])){//查询条件
//				where=((String)map.get("sql")).substring(((String)map.get("sql")).indexOf("from A01 a01"), ((String)map.get("sql")).length());
//				if("".equals(map.get("a0219"))){
//					
//				}else{
//					where=where.substring(0, where.indexOf("from a02")+14)+ " "+ ((String)map.get("a0219")).substring(4,((String)map.get("a0219")).length())+ map.get("a0279")+ " and "+where.substring(where.indexOf("from a02")+14, where.length());
//				}
//				where = ",( select a0000 "+where + " )  a02 where a02.a0000=a01.a0000"
//						+ map.get("a0221")
//						+ " ";
//				sb1.append(where);
//			}else{
//				throw new RadowException("人员信息界面数据异常!");
//			}
//			
//		}
//		if(((String)map.get("rylb"))!=null&&((String)map.get("rylb")).indexOf("\'")!=-1){
//	    	sb1.append(" and a01.A0165 not in ( "+((String)map.get("rylb"))+") ");
//	    }
//		sb1.append(map.get("a0160"));//
//		sb1.append(" ) a "+(map.get("num").equals("")?" group by A0221 ":"")//当前职务层次 分组
//		          );
//		List<HashMap<String, Object>> list1=cq.getListBySQL(sb1.toString());
		//list=combine(list,list1);
		return list;
	}
	
	/**
	 * 合并两个 List<HashMap<String, Object>> list1中的数据合并到list中
	 * @param list
	 * @param list1
	 * @return
	 */
//	public List<HashMap<String, Object>> combine(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
//		if(list==null||list1==null||list1.size()==0||list.size()==0){
//			return list;
//		}
//		String temp="";
//		if(list.size()<=list1.size()){
//			for(int i=0;i<list.size();i++){
//				if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
//					list.get(i).put("bs", list1.get(i).get("bs"));//博士
//					list.get(i).put("ss", list1.get(i).get("ss"));//硕士
//					list.get(i).put("xs", list1.get(i).get("xs"));//学士
//					continue;
//				}
//				temp=(String)list.get(i).get("a0221");
//				if(temp.equals((String)list1.get(i).get("a0221"))){
//					list.get(i).put("bs", list1.get(i).get("bs"));//博士
//					list.get(i).put("ss", list1.get(i).get("ss"));//硕士
//					list.get(i).put("xs", list1.get(i).get("xs"));//学士
//				}else{
//					combine_jy(list,list1,temp,i);
//				}
//			}
//		}else{
//			for(int i=0;i<list1.size();i++){
//				if(list1.get(i).get("a0221")==null||"".equals(list1.get(i).get("a0221"))){
//					list.get(i).put("bs", list1.get(i).get("bs"));//博士
//					list.get(i).put("ss", list1.get(i).get("ss"));//硕士
//					list.get(i).put("xs", list1.get(i).get("xs"));//学士
//					continue;
//				}
//				
//				temp=(String)list1.get(i).get("a0221");
//				if(temp.equals((String)list.get(i).get("a0221"))){
//					list.get(i).put("bs", list1.get(i).get("bs"));//博士
//					list.get(i).put("ss", list1.get(i).get("ss"));//硕士
//					list.get(i).put("xs", list1.get(i).get("xs"));//学士
//				}else{
//					combine_jy_f(list,list1,temp,i);
//				}
//			}
//		}
//		
//		return list;
//	}
	
//	public void combine_jy(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
//		for(int j=0;j<list1.size();j++){
//			if(temp.equals((String)list1.get(j).get("a0221"))){
//				list.get(i).put("bs", list1.get(j).get("bs"));//博士
//				list.get(i).put("ss", list1.get(j).get("ss"));//硕士
//				list.get(i).put("xs", list1.get(j).get("xs"));//学士
//			}
//		}
//	}
	
//	public void combine_jy_f(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
//		for(int j=0;j<list.size();j++){
//			if(temp.equals((String)list.get(j).get("a0221"))){
//				list.get(j).put("bs", list1.get(i).get("bs"));//博士
//				list.get(j).put("ss", list1.get(i).get("ss"));//硕士
//				list.get(j).put("xs", list1.get(i).get("xs"));//学士
//			}
//		}
//	}
	
//	public int returnGbjbqkSql2(StringBuffer sb1,Map<String,Object> map) throws RadowException{
//		if(DBType.ORACLE==DBUtil.getDBType()){
//			sb1.append(  " select  "
//	                + (map.get("num").equals("")?" a.A0221, ":"")
//	                +" SUM(CASE "
//			                +" WHEN nvl(a.A0901B,0) like '2%' or nvl(a.A0901B,0)=1  THEN "
//			                 +" 1 "
//			                +" ELSE "
//			                 +" 0 "
//			              +" END) bs, "//--  博士
//			       +" SUM(CASE "
//			             +" WHEN nvl(a.A0901B,0) like '3%'  THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			              +" END) ss, "//--硕士
//			       +" SUM(CASE "
//			             +" WHEN nvl(a.A0901B,0) like '4%'  THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			              +" END) xs "//--学士
//	                +" FROM"
//	                + " (SELECT "
//	                + " a01.a0221,"
//					  + " a08.A0901B ");//学位代码   等
//		}else if(DBType.MYSQL==DBUtil.getDBType()){
//			sb1.append(  " select  "
//	                + (map.get("num").equals("")?" a.A0221, ":"")
//	                +" SUM(CASE "
//			                +" WHEN if(a.A0901B='',0,a.A0901B) like '2%' or if(a.A0901B='',0,a.A0901B)=1  THEN "
//			                 +" 1 "
//			                +" ELSE "
//			                 +" 0 "
//			              +" END) bs, "//--  博士
//			       +" SUM(CASE "
//			             +" WHEN if(a.A0901B='',0,a.A0901B) like '3%'  THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			              +" END) ss, "//--硕士
//			       +" SUM(CASE "
//			             +" WHEN if(a.A0901B='',0,a.A0901B) like '4%'  THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			              +" END) xs "//--学士
//	                +" FROM"
//	                + " (SELECT "
//	                + " a01.a0221,"
//					  + " a08.A0901B ");//学位代码   等
//		}else{
//			throw new RadowException("数据源错误，请联系系统管理员检查原因!");
//		}
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
//	public int returnGbjbqkSql2sub(StringBuffer sb1) throws RadowException{
//		 sb1.append( " from A01 a01 left join ");
//		 
//		 if(DBType.ORACLE==DBUtil.getDBType()){
//		    	 sb1.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08 ");
//		 }else if(DBType.MYSQL==DBUtil.getDBType()){
//		    	 sb1.append( " ( select A0801B,a0000,A0901B from a08 where A0835='1' group by a0000 ) a08 ");
//		 }
//        sb1.append(" on a01.a0000=a08.a0000 "//
//       + "  "//人员统一标识符 关联
//	         );
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	public int returnZb(StringBuffer sb){
		sb.append(" FROM (SELECT a01.a0000, "//人员统一标识符
				  + " a01.a0104, "//性别
				  + " a01.a0117, "//民族
				  + " a01.a0141, "//政治面貌
				  + " a01.a0221, "//当前职务层次
				  + " a08.a0801b, "//学历代码   等
				  + " a08_2.a0901b, "//学位代码   等
				  + " a01.a0107, "//出生日期
				  + " a01.a0288, "//任现职务层次时间   多
				  + " a01.a0197 "//是否具有两年以上基层工作经历
	          +" FROM A01 a01 "
	          + " left join  ");//人员基本信息表
		if(DBType.ORACLE==DBUtil.getDBType()){
		    sb.append( " (select * from  ( select A0801B,a0000,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0834='1' ) a08_1 where  a08_1.rank=1) a08 ");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
		    sb.append( " ( select A0801B,a0000 from a08 where A0834='1' group by a0000 ) a08 ");
		}
	      sb.append(" on a01.a0000=a08.a0000 "
	      + "  ");//人员统一标识符 关联 a08 记录可能为空
	      

			 if(DBType.ORACLE==DBUtil.getDBType()){
			    	 sb.append( "  left join   (select * from  ( select a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08_2 ");
			 }else if(DBType.MYSQL==DBUtil.getDBType()){
			    	 sb.append( "  left join   ( select a0000,A0901B from a08 where A0835='1' group by a0000 ) a08_2 ");
			 }
			 sb.append(" on a01.a0000=a08_2.a0000 "//
				       + "  "//人员统一标识符 关联
					         );
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int returnGbjbqkSql(StringBuffer sb,Map<String,Object> map){
		if(DBType.ORACLE==DBUtil.getDBType()){
			sb.append(" SELECT  "
					+ "count(a.a0000) heji,"
					+ " SUM(CASE "
		             +" WHEN nvl(a.a0104,0) = 2 THEN "
		             +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) nv, "//性别 2 女
				       +" SUM(CASE "
			             +" WHEN nvl(a.a0117,0) = 01 THEN "
			              +" 0 "
			             +" ELSE "
			              +" 1 "
			           +" END) sm, "//少数名族  !=01
			       +" SUM(CASE "
			             +" WHEN nvl(a.a0141,0) in ('01','02') THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) zd, "//中共党员
	            +(map.get("num").equals("")?" a.A0221, ":"")//当前职位层次
	             +" SUM(CASE "
			                +" WHEN nvl(a.A0901B,0) like '2%' or nvl(a.A0901B,0)=1  THEN "
			                 +" 1 "
			                +" ELSE "
			                 +" 0 "
			              +" END) bs, "//--  博士
			       +" SUM(CASE "
			             +" WHEN nvl(a.A0901B,0) like '3%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) ss, "//--硕士
			       +" SUM(CASE "
			             +" WHEN nvl(a.A0901B,0) like '4%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) xs, "//--学士
	            + " SUM(CASE "
		             + " WHEN nvl(a.A0801B,0) like '1%' THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		           + " END) yjs,"//研究生
		        + "  SUM(CASE "
		             + " WHEN nvl(a.A0801B,0) like '2%' THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		           + " END) dxbk,"//大学本科
		        +" SUM(CASE "
		             +" WHEN nvl(a.A0801B,0) like '3%' THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) dxzz,"//大学专科
		        +" SUM(CASE "
		             +" WHEN (nvl(a.A0801B,0) like '4%' or nvl(a.A0801B,0) in('61','71','81','91') )THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) zz,"//中专及以下
           +" sum( case when add_months(to_date((nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-25),'yyyy-mm-dd') "
           		+" ) ),'yyyy-mm-dd'),24)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end ) zhccxy2, "//不满2年
           +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-37),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),36)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-23),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),24)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy3, "//2至不满3年
           +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-49),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),48)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-35),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),36)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy4, "//3至不满4年
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-61),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),60)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-47),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),48)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy5, "//4至不满5年
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-73),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),72)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-59),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),60)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy6, "//5至不满6年  
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-85),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),84)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-59),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),72)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy7, "//6至不满7年
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-97),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),96)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-59),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),84)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy8, "//7至不满8年
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-145),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),144)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-59),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),96)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy12, "//8至不满12年
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-181),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),180)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-59),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),144)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end) zhccxy15, "//12至不满15年
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-170),'yyyy-mm-dd') "
          	   +" ),'yyyy-mm-dd'),180)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" then 1 else 0 end ) zhccxy16 "//15年及以上
          	   );
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			sb.append("SELECT  "
					+ " count(a.a0000) heji,"
					+ "	SUM(     "
					+ "		CASE "
					+ "		WHEN a.a0104 = 2 THEN  "
					+ "			1 " 
					+ "		ELSE  " 
					+ "			0 " 
					+ "		END   " 
					+ "	) nv,     " //女
					+ "	SUM(      " 
					+ "		CASE  " 
					+ "		WHEN a.a0117 = 01 THEN "
					+ "			0 "
					+ "		ELSE  "
					+ "			1 "
					+ "		END "
					+ "	) sm,   "//少
					+ "	SUM(    "
					+ "		CASE  "
					+ "		WHEN a.a0141 in ('01','02') THEN "
					+ "			1 "
					+ "		ELSE  "
					+ "			0 "
					+ "		END   "
					+ "	) zd,     "//党员
					+ (map.get("num").equals("")?" a.A0221, ":"")
					+" SUM(CASE "
			                +" WHEN if(a.A0901B='',0,a.A0901B) like '2%' or if(a.A0901B='',0,a.A0901B)=1  THEN "
			                 +" 1 "
			                +" ELSE "
			                 +" 0 "
			              +" END) bs, "//--  博士
			       +" SUM(CASE "
			             +" WHEN if(a.A0901B='',0,a.A0901B) like '3%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) ss, "//--硕士
			       +" SUM(CASE "
			             +" WHEN if(a.A0901B='',0,a.A0901B) like '4%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) xs, "//--学士
					+ "	SUM(     "
					+ "		CASE "
					+ "		WHEN a.A0801B LIKE '1%' THEN   "
					+ "			1 "
					+ "		ELSE  "
					+ "			0 "
					+ "		END  "
					+ "	) yjs,   "//研究生
					+ "	SUM(     "
					+ "		CASE  "
					+ "		WHEN a.A0801B LIKE '2%' THEN    "
					+ "			1 "
					+ "		ELSE  "
					+ "			0 "
					+ "		END  "
					+ "	) dxbk,  "//大学本科
					+ "	SUM(     "
					+ "		CASE  "
					+ "		WHEN a.A0801B LIKE '3%' THEN    "
					+ "			1 "
					+ "		ELSE  "
					+ "			0 "
					+ "		END "
					+ "	) dxzz, "//大学专科
					+ "	SUM(    "
					+ "		CASE  "
					+ "		WHEN (a.A0801B LIKE '4%' or a.A0801B IN ('61', '71', '81', '91') ) THEN    "
					+ "			1 "
					+ "		ELSE  "
					+ "			0 "
					+ "		END "
					+ "	) zz,   "//中专及以下
					+ "sum(           "
					+ "		CASE      "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 25 month) ,  "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 24 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN   "
					+ "			1  "
					+ "		ELSE   "
					+ "			0  "
					+ "		END    "
					+ "	) zhccxy2, "//不满2年
					+ "	sum(       "
					+ "		CASE   "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if (  "
					+ "						A0288 is null,       "
					+ "						DATE_FORMAT(    "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 37 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 36 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if (  "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 23 month) ,  "
					+ "							'%Y%m%d'  "
					+ "						),            "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)         "
					+ "					),'%Y%m%d'),INTERVAL 24 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN  "
					+ "			1  "
					+ "		ELSE   "
					+ "			0  "
					+ "		END    "
					+ "	) zhccxy3, "//2年至不满3年
					+ "	sum(       "
					+ "		CASE   "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if (  "
					+ "						A0288 is null,       "
					+ "						DATE_FORMAT(    "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 61 month) ,  "
					+ "							'%Y%m%d'  "
					+ "						),            "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)         "
					+ "					),'%Y%m%d'),INTERVAL 48 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 35 month) ,  "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 36 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN  "
					+ "			1 "
					+ "		ELSE  "
					+ "			0 "
					+ "		END   "
					+ "	) zhccxy4,"//3年至不满4年
					+ "	sum(      "
					+ "		CASE  "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 61 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 60 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 47 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 48 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1   "
					+ "		ELSE    "
					+ "			0   "
					+ "		END     "
					+ "	) zhccxy5, "//4年至不满5年
					+ "	sum(      "
					+ "		CASE  "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 73 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 72 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 59 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 60 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1   "
					+ "		ELSE    "
					+ "			0   "
					+ "		END     "
					+ "	) zhccxy6, "//5年至不满6年
					+ "	sum(      "
					+ "		CASE  "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 85 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 84 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 59 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 72 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1   "
					+ "		ELSE    "
					+ "			0   "
					+ "		END     "
					+ "	) zhccxy7, "//6年至不满7年
					+ "	sum(      "
					+ "		CASE  "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 97 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 96 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 59 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 84 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1   "
					+ "		ELSE    "
					+ "			0   "
					+ "		END     "
					+ "	) zhccxy8, "//7年至不满8年
					+ "	sum(      "
					+ "		CASE  "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 145 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 144 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 59 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 96 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1   "
					+ "		ELSE    "
					+ "			0   "
					+ "		END     "
					+ "	) zhccxy12, "//8年至不满12年
					+ "	sum(      "
					+ "		CASE  "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,      "
					+ "						DATE_FORMAT(   "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 181 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 180 month) > "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "
					+ "		AND DATE_ADD(STR_TO_DATE(if ( "
					+ "						A0288 is null,     "
					+ "						DATE_FORMAT(  "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 59 month) , "
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 144 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1   "
					+ "		ELSE    "
					+ "			0   "
					+ "		END     "
					+ "	) zhccxy15, "//12年至不满15年
					+ "	sum(        "
					+ "		CASE    "
					+ "		WHEN DATE_ADD(STR_TO_DATE(if (  "
					+ "						A0288 is null,       "
					+ "						DATE_FORMAT(    "
					+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 179 month) ,"
					+ "							'%Y%m%d' "
					+ "						),           "
					+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
					+ "					),'%Y%m%d'),INTERVAL 180 month) <= "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" THEN "
					+ "			1  "
					+ "		ELSE   "
					+ "			0  "
					+ "		END    "
					+ "	) zhccxy16 "//15年及以上
					);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}
