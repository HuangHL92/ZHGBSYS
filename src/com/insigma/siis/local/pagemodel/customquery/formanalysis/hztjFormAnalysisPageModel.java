package com.insigma.siis.local.pagemodel.customquery.formanalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkSqlPj;

public class hztjFormAnalysisPageModel extends PageModel{

	public hztjFormAnalysisPageModel() {
		
	}

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("gridfc.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("gridfc.dogridquery")
	public int zwxxQuery(int start, int limit) throws RadowException, AppException {
		
		String param=this.getPageElement("subWinIdBussessId2").getValue();
		String arr[]=param.split("\\$");
		String page=arr[0];
		String row=arr[1];
		String col=arr[2];
		String year=arr[4];
		String groupid=arr[3];
		Map<String,String> map2=new HashMap<String, String>();
		map2.put("page",page);
		map2.put("row", row);
		map2.put("col", col);
		map2.put("groupid",groupid);
		map2.put("year", year);
		StringBuffer sb=null;
		if("ssy".equals(page)||"gwynl1".equals(page)||"gwynl2".equals(page)){
			if(Integer.parseInt(row)>=8&&Integer.parseInt(row)<=31){
				sb=returnSql(map2) ;
			}else if(Integer.parseInt(row)==6){
				map2.put("type", "2");
				StringBuffer sb2=returnSql(map2);
				map2.put("type", "3");
				StringBuffer sb3=returnSql(map2);
				map2.put("type", "4");
				StringBuffer sb4=returnSql(map2);
				sb=sb2.append(" union all "+sb3.toString()+" union all "+sb4.toString());
			}else if(Integer.parseInt(row)==7){
				map2.put("type", "2");
				StringBuffer sb2=returnSql(map2);
				map2.put("type", "3");
				StringBuffer sb3=returnSql(map2);
				map2.put("type", "4");
				StringBuffer sb4=returnSql(map2);
				sb=sb2.append(" union all "+sb3.toString()+" union all "+sb4.toString());
			}
		}else if("sse".equals(page)||"gwynl3".equals(page)||"gwynl4".equals(page)){
			sb=returnSql(map2) ;
		}else if("sycz".equals(page)||"swqt".equals(page)){
			if(Integer.parseInt(row)==6){
				map2.put("type", "2");
				StringBuffer sb2=returnSql(map2);
				map2.put("type", "3");
				StringBuffer sb3=returnSql(map2);
				map2.put("type", "4");
				StringBuffer sb4=returnSql(map2);
				sb=sb2.append(" union all "+sb3.toString()+" union all "+sb4.toString());
			}else{
				sb=returnSql(map2) ;
			}
		}else if("swqtnl1".equals(page)||"swqtnl2".equals(page)||"sycznl1".equals(page)||"sycznl2".equals(page)){
			if(Integer.parseInt(row)==5){
				map2.put("type", "2");
				StringBuffer sb2=returnSql(map2);
				map2.put("type", "3");
				StringBuffer sb3=returnSql(map2);
				map2.put("type", "4");
				StringBuffer sb4=returnSql(map2);
				sb=sb2.append(" union all "+sb3.toString()+" union all "+sb4.toString());
			}else{
				sb=returnSql(map2) ;
			}
		}
		System.out.println(sb.toString());
		try{
			this.pageQuery(sb.toString(), "sql", start, limit);
		}catch(Exception e){
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.SPE_SUCCESS;
	}
	
	public StringBuffer returnSql(Map<String,String> map2) throws AppException,RadowException{
		String page=map2.get("page");
		String row=map2.get("row");
		String col=map2.get("col");
		String groupid=map2.get("groupid");
		String sql=(String) this.request.getSession().getAttribute("ry_tj_zy");
		String sid = this.request.getSession().getId();
		String userid = SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		CommQuery cq=new CommQuery();
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
		
		
		
		StringBuffer sb=new StringBuffer();
	//	GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		//拼接sql select
		returnPjGridSql(sb);
		//拼接sql from 
		returnZb(sb);
		Map<String,String> map=RowMap( page);
		map.put("year", map2.get("year"));
		if("ssy".equals(page)||"sse".equals(page)||"gwynl1".equals(page)||"gwynl2".equals(page)
				||"gwynl3".equals(page)||"gwynl4".equals(page)){
			map.put("a0160", " and a01.A0160='1' ");//公务员 
		}else if("swqt".equals(page)||"swqtnl1".equals(page)||"swqtnl2".equals(page)){
			map.put("a0160", " and a01.A0160='5' ");
		}else if("sycz".equals(page)||"sycznl1".equals(page)||"sycznl2".equals(page)){
			map.put("a0160", " and a01.A0160='6' ");
		}
		if("ssy".equals(page)||"gwynl1".equals(page)||"gwynl2".equals(page)){
			if(Integer.parseInt(row)>=8&&Integer.parseInt(row)<=31){
				rowCondition( map, row, page);
			}else if(Integer.parseInt(row)==6){
				String type=map2.get("type");
				if("2".equals(type)){
					map.put("a0219", "");
					map.put("a0279", "");
					map.put("a0221", " and ( a01.a0221 in ('1A11','1A12','1A50','1A60','1A98','1A99') or a01.a0221 like '2%' or a01.a0221 like '3%' or a01.a0221 like '4%' or a01.a0221 like '7%'  )");
					
				}else if("3".equals(type)){
					map.put("a0221", " and a01.a0221 in ('1A21','1A22','1A31','1A32','1A41','1A42') ");
					map.put("a0279", " and a02.a0279='1' ");
					map.put("a0219", " and a02.a0219='1' ");
				}else if("4".equals(type)){
					map.put("a0219", " and (a02.a0219!='1' or a02.a0219 is null) ");
					map.put("a0279", " and a02.a0279='1' ");
					map.put("a0221", " and a01.a0221 in ('1A21','1A22','1A31','1A32','1A41','1A42') ");
				}
			}else if(Integer.parseInt(row)==7){
				String type=map2.get("type");
				if("2".equals(type)){
					map.put("a0219", "");
					map.put("a0279", "");
					map.put("a0221", " and ( a01.a0221 in ('1A11','1A12','1A50','1A60','1A98','1A99')  )");
				}else if("3".equals(type)){
					map.put("a0221", " and a01.a0221 in ('1A21','1A22','1A31','1A32','1A41','1A42') ");
					map.put("a0279", " and a02.a0279='1' ");
					map.put("a0219", " and a02.a0219='1' ");
				}else if("4".equals(type)){
					map.put("a0219", " and (a02.a0219!='1' or a02.a0219 is null) ");
					map.put("a0279", " and a02.a0279='1' ");
					map.put("a0221", " and a01.a0221 in ('1A21','1A22','1A31','1A32','1A41','1A42')");
				}
			}
		}else if("sse".equals(page)||"gwynl3".equals(page)||"gwynl4".equals(page)){
			rowCondition( map, row, page);
		}else if("sycz".equals(page)||"swqt".equals(page)){
			if(Integer.parseInt(row)==6){
				String type=map2.get("type");
				if("2".equals(type)){
					map.put("a0219", "");
					map.put("a0279", "");
					map.put("a0221", " and ( a01.a0221 in ('1A11','1A12','1A50','1A60','1A98','1A99')  )");
				}else if("3".equals(type)){
					map.put("a0221", " and a01.a0221 in ('1A21','1A22','1A31','1A32','1A41','1A42') ");
					map.put("a0279", " and a02.a0279='1' ");
					map.put("a0219", " and a02.a0219='1' ");
				}else if("4".equals(type)){
					map.put("a0219", " and (a02.a0219!='1' or a02.a0219 is null) ");
					map.put("a0279", " and a02.a0279='1' ");
					map.put("a0221", " and a01.a0221 in ('1A21','1A22','1A31','1A32','1A41','1A42')");
				}
			}else{
				rowCondition( map, row, page);
			}
		}else if("swqtnl1".equals(page)||"swqtnl2".equals(page)||"sycznl1".equals(page)||"sycznl2".equals(page)){
			if(Integer.parseInt(row)==5){
				String type=map2.get("type");
				if("2".equals(type)){
					map.put("a0219", "");
					map.put("a0279", "");
					map.put("a0221", " and ( a01.a0221 in ('1A11','1A12','1A50','1A60','1A98','1A99')  )");
				}else if("3".equals(type)){
					map.put("a0221", " and a01.a0221 in ('1A21','1A22','1A31','1A32','1A41','1A42') ");
					map.put("a0279", " and a02.a0279='1' ");
					map.put("a0219", " and a02.a0219='1' ");
				}else if("4".equals(type)){
					map.put("a0219", " and (a02.a0219!='1' or a02.a0219 is null) ");
					map.put("a0279", " and a02.a0279='1' ");
					map.put("a0221", " and a01.a0221 in ('1A21','1A22','1A31','1A32','1A41','1A42')");
				}
			}else{
				rowCondition( map, row, page);
			}
		}
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
                 + "  and a01.a0195 like '"+groupid+"%' "
                 + map.get("a0221")
					);
		}else{
			sb.append(" where "
                     + "  a01.a0163='1' "//人员管理状态   等
                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
                     + "  and a01.a0195 like '"+groupid+"%' "
                     + map.get("a0221")
						);
		}
	      /* if(sql==null||"".equals(sql)||sql.length()==0){
	    	   if("system".equals(username)){
					sb.append(" ,(select  a02.a0000 "
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
							);
				}else{
					sb.append(" ,(select  a02.a0000 "
							+ " from a02, competence_userdept cu "
							+ " where "
							+ " a02.a0255 = '1' "
							+ map.get("a0219")
							+ map.get("a0279")
	                     + " and a02.A0201B = cu.b0111 "
	                     + " and cu.userid = '"+userid+"' "
	                     + " group by a02.a0000"
	                     + " ) a02 where "
	                     + "  a01.a0163='1' "//人员管理状态   等
	                     + " and a01.status!='4' "//点击人员新增时，没有点击保存，系统产生的垃圾数据
	                     + "  and a02.a0000=a01.a0000  "
	                     + map.get("a0221")
							);
				}
			}else{
				String [] arrPersonnelList=sql.split("@@");
				if(arrPersonnelList.length==2){
				}else{
					throw new RadowException("数据异常!");
				}
				String where="";
				if("tempForCount".equals(arrPersonnelList[0])){//临时表
					if("".equals(map.get("a0219"))){
						where =" ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) ";
						sb.append(", "+where+ " a02 where a02.a0000=a01.a0000 "
								+ map.get("a0221")
								+ " ");
					}else{
						where ="(select aa.a0000,bb.a0219,bb.a0279 from ( select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"' ) aa,a02 bb "
								+ " where aa.a0000=bb.a0000 and bb.a0255 = '1')";
						sb.append(", "+where+ " a02 where a02.a0000=a01.a0000 "
								+ map.get("a0221")
								+ map.get("a0219")
								+ map.get("a0279")
								+ " ");
					}
				}else if("conditionForCount".equals(arrPersonnelList[0])){//查询条件
					where=sql.substring(sql.indexOf("from A01 a01"), sql.length());
					if("".equals(map.get("a0219"))){
						
					}else{
						where=where.substring(0, where.indexOf("from a02")+14)+ " and a02.a0255='1' "+ ((String)map.get("a0219")).substring(4,((String)map.get("a0219")).length())+ map.get("a0279")+ " and "+where.substring(where.indexOf("from a02")+14, where.length());
					}
					where = ",( select a0000 "+where + " )  a02 where a02.a0000=a01.a0000"
							+ map.get("a0221")
							+ " ";
					sb.append(where);
				}else{
					throw new RadowException("人员信息界面数据异常!");
				}
			}*/
//	       if(rylb!=null&&rylb.indexOf("\'")!=-1){
//		    	sb.append(" and a01.A0165 not in ( "+rylb+") ");
//		    }
	      /* if("ssy".equals(page)||"gwynl1".equals(page)||"gwynl2".equals(page)){
	    	   sb.append(" and (a01.a0221 like '1A%' "//综合类管理员
	   	       		+ " or a01.a0221 like '2%' "//人民警察警员职务序列
	   	       		+ " or a01.a0221 like '3%' "//法官等级
	   	       		+ " or a01.a0221 like '4%' "//检察官等级
	   	       		+ ") "
	   	    		   );
	       }else if("sse".equals(page)||"gwynl3".equals(page)||"gwynl4".equals(page)){
	    	   sb.append(" and ( a01.a0221 like '2%' "
	   	       		+ " or a01.a0221 like '3%' "
	   	       		+ " or a01.a0221 like '4%') "
	   	    		   );
	       }else if("swqt".equals(page)||"sycz".equals(page)
	    		   ||"swqtnl1".equals(page)||"swqtnl2".equals(page)
	    		   ||"sycznl1".equals(page)||"sycznl2".equals(page)){
	    	   sb.append(" and a01.a0221 like '1A%'  "
		   	    		   );
	       }*/
	       
	       sb.append(map.get("a0160"));//
	       //sb.append("  and a01.a0195 like '"+groupid+"%' ");
	       sb.append(" ) a ");
	       sb.append(" where 1=1 ");
	       //列条件
	       if("ssy".equals(page)||"sse".equals(page)||"swqt".equals(page)||"sycz".equals(page)){
	    	   colCondition(sb,row,col,page,map);
	       }else if("gwynl1".equals(page)||"gwynl3".equals(page)||"swqtnl1".equals(page)||"sycznl1".equals(page)){
	    	   colConditionNl1(sb,row,col,page,map);
	       }else if("gwynl2".equals(page)||"gwynl4".equals(page)||"swqtnl2".equals(page)||"sycznl2".equals(page)){
	    	   colConditionNl2(sb,row,col,page,map);
	       }
	       return sb;
	}
	
	public void rowCondition(Map<String,String> map,String row,String page)  throws RadowException{
		if("ssy".equals(page)||"gwynl1".equals(page)||"gwynl2".equals(page)){
			if("6".equals(row)){//总  计
				map.put("a0221", "");
			}else if("7".equals(row)){//合  计
				map.put("a0221", " and a01.a0221 like '1A%' ");
			}else if("29".equals(row)){//合  计
				map.put("a0221", " and a01.a0221 like '1B%' ");
			}else if("30".equals(row)){//合  计
				map.put("a0221", " and a01.a0221 like '1C%' ");
			}else if("31".equals(row)){//合  计
				map.put("a0221", " and (a01.a0221 like '5%' or a01.a0221 like '6%') ");
			}else{
				map.put("a0221"," and a01.a0221 = '"+map.get(row)+"' ");
			}
			if("10".equals(row)||"12".equals(row)||"14".equals(row)||"16".equals(row)||"18".equals(row)||"20".equals(row)){
				map.put("a0279", " and a02.a0279='1' ");
				map.put("a0219", " and a02.a0219='1' ");
			}else if("11".equals(row)||"13".equals(row)||"15".equals(row)||"17".equals(row)||"19".equals(row)||"21".equals(row)){
				map.put("a0219", " and (a02.a0219!='1' or a02.a0219 is null) ");
				map.put("a0279", " and a02.a0279='1' ");
			}else{
				map.put("a0219", "");//是否领导职务   等
				map.put("a0279", "");//主职务标识
			}
		}else if("sse".equals(page)||"gwynl3".equals(page)||"gwynl4".equals(page)){
			if("6".equals(row)){//合计
				map.put("a0221", " and a01.a0221 like '2%' ");
			}else if("16".equals(row)){//合计
				map.put("a0221", " and ( a01.a0221 like '3%' or a01.a0221 like '4%' ) ");
			}else if(Integer.parseInt(row)>=7&&Integer.parseInt(row)<=15){
				map.put("a0221", " and a01.a0221 ='"+map.get(row)+"' ");
			}else{
				map.put("a0221", " and a01.a0221 in ("+map.get(row)+") ");
			}
			map.put("a0219", "");//是否领导职务   等
			map.put("a0279", "");//主职务标识
		}else if("swqt".equals(page)||"sycz".equals(page)){
			if("6".equals(row)){//合  计
				map.put("a0221", " and a01.a0221 like '1A%' ");
			}else{
				map.put("a0221"," and a01.a0221 = '"+map.get(row)+"' ");
			}
			if("9".equals(row)||"11".equals(row)||"13".equals(row)||"15".equals(row)||"17".equals(row)||"19".equals(row)){
				map.put("a0279", " and a02.a0279='1' ");
				map.put("a0219", " and a02.a0219='1' ");
			}else if("10".equals(row)||"12".equals(row)||"14".equals(row)||"16".equals(row)||"18".equals(row)||"20".equals(row)){
				map.put("a0219", " and (a02.a0219!='1' or a02.a0219 is null) ");
				map.put("a0279", " and a02.a0279='1' ");
			}else{
				map.put("a0219", "");//是否领导职务   等
				map.put("a0279", "");//主职务标识
			}
		}else if("swqtnl1".equals(page)||"swqtnl2".equals(page)
				||"sycznl1".equals(page)||"sycznl2".equals(page)){
			if("5".equals(row)){//合  计
				map.put("a0221", " and a01.a0221 like '1A%' ");
			}else{
				map.put("a0221"," and a01.a0221 = '"+map.get(row)+"' ");
			}
			if("8".equals(row)||"10".equals(row)||"12".equals(row)||"14".equals(row)||"16".equals(row)||"18".equals(row)){
				map.put("a0279", " and a02.a0279='1' ");
				map.put("a0219", " and a02.a0219='1' ");
			}else if("9".equals(row)||"11".equals(row)||"13".equals(row)||"15".equals(row)||"17".equals(row)||"19".equals(row)){
				map.put("a0219", " and (a02.a0219!='1' or a02.a0219 is null) ");
				map.put("a0279", " and a02.a0279='1' ");
			}else{
				map.put("a0219", "");//是否领导职务   等
				map.put("a0279", "");//主职务标识
			}
		}
		
	}
	
	/**
	 * 列条件
	 * @return
	 */
	public void colCondition(StringBuffer sb,String row,String col,String page,Map<String,String> map)  throws RadowException{
		if("4".equals(col)){
			sb.append(" and a.a0104 = 2 "//性别 2 女
					+ "");
		}else if("5".equals(col)){
			sb.append(" and a.a0117 != 01 "//少数名族  !=01
					+ "");
		}else if("6".equals(col)){
			sb.append(" and a.a0141 in ('01','02')  "//中共党员
					+ "");
		}else if("7".equals(col)){
			sb.append(" and ( a.A0901B like '2%' or a.A0901B=1 )   "//--  博士
					+ "");
		}else if("8".equals(col)){
			sb.append(" and a.A0901B like '3%'   "//--硕士
					+ "");
		}else if("9".equals(col)){
			sb.append(" and a.A0901B like '4%' "//--学士
					+ "");
		}else if("11".equals(col)){
			sb.append(" and a.A0801B like '1%'  "//研究生
					+ "");
		}else if("12".equals(col)){
			sb.append(" and a.A0801B like '2%' "//大学本科
					+ "");
		}else if("13".equals(col)){
			sb.append(" and a.A0801B like '3%' "//大学专科
					+ "");
		}else if("14".equals(col)){
			sb.append(" and ( a.A0801B like '4%' or a.A0801B in('61','71','81','91') ) "//中专及以下
					+ "");
		}else{
			if(DBType.ORACLE==DBUtil.getDBType()){
				if("15".equals(col)){
					sb.append(" and add_months(to_date((nvl(decode(length(a.A0288),6,a.a0288||'01',a.a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-25),'yyyy-mm-dd') "
				           		+" ) ),'yyyy-mm-dd'),24)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+"  "//不满2年
							+ "");
					
				}else if(Integer.parseInt(col)>=16&&Integer.parseInt(col)<=21){
					sb.append(" and add_months(to_date(nvl(decode(length(a.a0288),6,a.a0288||'01',a.a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-"+(((Integer.parseInt(col)-13)*12)+1)+"),'yyyy-mm-dd') "
					           +" ),'yyyy-mm-dd'),"+((Integer.parseInt(col)-13)*12)+" )>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(a.a0288),6,a.a0288||'01',a.a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-"+(((Integer.parseInt(col)-14)*12)-1)+"),'yyyy-mm-dd') "
					           +" ),'yyyy-mm-dd'),"+((Integer.parseInt(col)-14)*12)+")<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+"  "//2至不满3年
							+ "");
				}else if("22".equals(col)){
					sb.append(" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-145),'yyyy-mm-dd') "
					           +" ),'yyyy-mm-dd'),144)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-59),'yyyy-mm-dd') "
					           +" ),'yyyy-mm-dd'),96)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" "//8至不满12年
						+ "");
				}else if("23".equals(col)){
					sb.append(" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-181),'yyyy-mm-dd') "
					           +" ),'yyyy-mm-dd'),180)>"+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-59),'yyyy-mm-dd') "
					           +" ),'yyyy-mm-dd'),144)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" "//12至不满15年
						+ "");
				}else if("24".equals(col)){
					sb.append(" and add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months("+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+",-170),'yyyy-mm-dd') "
				          	   +" ),'yyyy-mm-dd'),180)<="+"to_date('"+map.get("year")+"','yyyy-mm-dd')"+" "//15年及以上
						+ "");
				}
			}else if(DBType.MYSQL==DBUtil.getDBType()){
				if("15".equals(col)){
					sb.append(" and DATE_ADD(STR_TO_DATE(if ( "
							+ "						A0288 is null,      "
							+ "						DATE_FORMAT(   "
							+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 25 month) ,  "
							+ "							'%Y%m%d' "
							+ "						),           "
							+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
							+ "					),'%Y%m%d'),INTERVAL 24 month) >  "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "//不满2年
							+ "");
					
				}else if(Integer.parseInt(col)>=16&&Integer.parseInt(col)<=21){
					sb.append(" and DATE_ADD(STR_TO_DATE(if (  "
							+ "						A0288 is null,       "
							+ "						DATE_FORMAT(    "
							+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL "+(((Integer.parseInt(col)-13)*12)+1)+" month) , "
							+ "							'%Y%m%d' "
							+ "						),           "
							+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
							+ "					),'%Y%m%d'),INTERVAL "+((Integer.parseInt(col)-13)*12)+" month) >  "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"   "
							+ "		AND DATE_ADD(STR_TO_DATE(if (  "
							+ "						A0288 is null,      "
							+ "						DATE_FORMAT(   "
							+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL "+(((Integer.parseInt(col)-14)*12)-1)+" month) ,  "
							+ "							'%Y%m%d'  "
							+ "						),            "
							+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)         "
							+ "					),'%Y%m%d'),INTERVAL "+((Integer.parseInt(col)-14)*12)+" month) <=  "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"   "//2年至不满3年
							+ "");
				}else if("22".equals(col)){
					sb.append(" and DATE_ADD(STR_TO_DATE(if ( "
							+ "						A0288 is null,      "
							+ "						DATE_FORMAT(   "
							+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 145 month) , "
							+ "							'%Y%m%d' "
							+ "						),           "
							+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
							+ "					),'%Y%m%d'),INTERVAL 144 month) >  "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"   "
							+ "		AND DATE_ADD(STR_TO_DATE(if ( "
							+ "						A0288 is null,     "
							+ "						DATE_FORMAT(  "
							+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 59 month) , "
							+ "							'%Y%m%d' "
							+ "						),           "
							+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
							+ "					),'%Y%m%d'),INTERVAL 96 month) <=  "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "//8年至不满12年
						+ "");
				}else if("23".equals(col)){
					sb.append(" and DATE_ADD(STR_TO_DATE(if ( "
							+ "						A0288 is null,      "
							+ "						DATE_FORMAT(   "
							+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 181 month) , "
							+ "							'%Y%m%d' "
							+ "						),           "
							+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
							+ "					),'%Y%m%d'),INTERVAL 180 month) >  "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"   "
							+ "		AND DATE_ADD(STR_TO_DATE(if ( "
							+ "						A0288 is null,     "
							+ "						DATE_FORMAT(  "
							+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 59 month) , "
							+ "							'%Y%m%d' "
							+ "						),           "
							+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
							+ "					),'%Y%m%d'),INTERVAL 144 month) <=  "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "//12年至不满15年
						+ "");
				}else if("24".equals(col)){
					sb.append(" and DATE_ADD(STR_TO_DATE(if (  "
							+ "						A0288 is null,       "
							+ "						DATE_FORMAT(    "
							+ "							DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 179 month) ,"
							+ "							'%Y%m%d' "
							+ "						),           "
							+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
							+ "					),'%Y%m%d'),INTERVAL 180 month) <=  "+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+"  "//15年及以上
						+ "");
				}
			}
		}
		
		
	}
	/**
	 * 列条件
	 * @return
	 */
	public void colConditionNl1(StringBuffer sb,String row,String col,String page,Map<String,String> map)  throws RadowException{
	
		if(DBType.ORACLE==DBUtil.getDBType()){
			if("3".equals(col)){//合计
			}else if("4".equals(col)){
				sb.append(" and floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char(add_months("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",-264),'yyyy-mm')),'yyyy-mm'))/12)<=20 ");
			}else{//5 21 6 22
				sb.append(" and  floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)="+(16+Integer.parseInt(col))+" ");
			}
		
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			if("3".equals(col)){//合计
			}else if("4".equals(col)){
				sb.append(  "  and  floor( "
						+ " 		timestampdiff ( "
						+ " 			month, "
						+ " 				if ( "
						+ " 					a.a0107='', "
						+ " 					DATE_FORMAT(DATE_SUB("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,INTERVAL 264 month),'%y%m%d'), "
						+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
						+ " 				),DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
						+ " 		) / 12 "
						+ " 	) <= 20  ");
			}else{//5 21 6 22
				sb.append(" and  floor( "
						+ " 		timestampdiff ( "
						+ " 			month,		 "	
						+ " 				if ( "
						+ " 					a.a0107='', "
						+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
						+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
						+ " 				), "
						+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
						+ " 		) / 12 "
						+ " 	) = "+(16+Integer.parseInt(col))+" ");
			}
		}
		
	}
	/**
	 * 列条件
	 * @return
	 */
	public void colConditionNl2(StringBuffer sb,String row,String col,String page,Map<String,String> map) throws RadowException{
	
		if(DBType.ORACLE==DBUtil.getDBType()){
			if(Integer.parseInt(col)>=3&&Integer.parseInt(col)<=24){
				sb.append( " and floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)="+(41+Integer.parseInt(col))+" ");
			}else if(Integer.parseInt(col)==25){
				sb.append( " and  floor(months_between("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",to_date(nvl(substr(a.a0107,1,6),to_char("+"to_date('"+map.get("year").toString().substring(0, 7)+"','yyyy-mm')"+",'yyyy-mm')),'yyyy-mm'))/12)>=66 ");
			}
		
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			 if(Integer.parseInt(col)>=3&&Integer.parseInt(col)<=24){
				sb.append( " and  floor( "
						+ " 		timestampdiff (   "
						+ " 			month,  "
						+ " 				if (  "
						+ " 					a.a0107='',  "
						+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'),   "
						+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
						+ " 				),  "
						+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
						+ " 		) / 12  "
						+ " 	) = "+(41+Integer.parseInt(col))+"  ");
			}else if(Integer.parseInt(col)==25){
				sb.append( "  and floor( "
						+ " 		timestampdiff ( "
						+ " 			month, "
						+ " 				if ( "
						+ " 					a.a0107='', "
						+ " 					DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d'), "
						+ " 					if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
						+ " 				), "
						+ " 				DATE_FORMAT("+"STR_TO_DATE('"+map.get("year")+"','%Y-%m-%d')"+" ,'%y%m%d') "
						+ " 		) / 12 "
						+ " 	) >= 66 ");
			}
		}
		
	}
	/**
	 * 公务员基本情况表一 行条件
	 */
	public Map<String,String> RowMap(String page)  throws RadowException{
		Map<String,String> map=new HashMap<String,String>();
		map.put("a0219", "");//是否领导职务   等
		map.put("a0279", "");//主职务标识
		if("ssy".equals(page)||"gwynl1".equals(page)||"gwynl2".equals(page)){//基本情况表一
			returnSsyMap(map);
		}else if("sse".equals(page)||"gwynl3".equals(page)||"gwynl4".equals(page)){
			returnSseMap(map);
		}else if("swqt".equals(page)||"sycz".equals(page)){
			returnSwSyMap(map);
		}else if("swqtnl1".equals(page)||"swqtnl2".equals(page)
				||"sycznl1".equals(page)||"sycznl2".equals(page)){
			 returnSwSyNlMap( map);
		}
		return map;
	}
	public void returnSwSyNlMap(Map<String,String> map)  throws RadowException{
		map.put("6", "1A11");
		map.put("7", "1A12");
		map.put("20", "1A50");
		map.put("21", "1A60");
		map.put("22", "1A98");
		map.put("23", "1A99");
		
		//领导职务
		map.put("8", "1A21");
		map.put("10", "1A22");
		map.put("12", "1A31");
		map.put("14", "1A32");
		map.put("16", "1A41");
		map.put("18", "1A42");
		
		//非领导职务
		map.put("9", "1A21");
		map.put("11", "1A22");
		map.put("13", "1A31");
		map.put("15", "1A32");
		map.put("17", "1A41");
		map.put("19", "1A42");
	}
	public void returnSwSyMap(Map<String,String> map)  throws RadowException{
		map.put("7", "1A11");
		map.put("8", "1A12");
		map.put("21", "1A50");
		map.put("22", "1A60");
		map.put("23", "1A98");
		map.put("24", "1A99");
		
		//领导职务
		map.put("9", "1A21");
		map.put("11", "1A22");
		map.put("13", "1A31");
		map.put("15", "1A32");
		map.put("17", "1A41");
		map.put("19", "1A42");
		
		//非领导职务
		map.put("10", "1A21");
		map.put("12", "1A22");
		map.put("14", "1A31");
		map.put("16", "1A32");
		map.put("18", "1A41");
		map.put("20", "1A42");
	}
	public void returnSseMap(Map<String,String> map)  throws RadowException{
		//人民警察警员职务序列 
		map.put( "7" ,"20");
		map.put( "8" ,"21");
		map.put( "9" ,"22");
		map.put( "10","23");
		map.put( "11","24");
		map.put( "12","25");
		map.put( "13","26");
		map.put( "14","27");
		map.put( "15","28");

		map.put( "17","'301','401'");
		map.put( "18","'302','402'");
		map.put( "19","'303','403'");
		map.put( "20","'304','404'");
		map.put( "21","'305','405'");
		map.put( "22","'306','406'");
		map.put( "23","'307','407'");
		map.put( "24","'308','408'");
		map.put( "25","'309','409'");
		map.put( "26","'310','410'");
		map.put( "27","'311','411'");
		map.put( "28","'312','412'");
		
	}
	
	public void returnSsyMap(Map<String,String> map)  throws RadowException{
		map.put("8", "1A11");
		map.put("9", "1A12");
		map.put("22", "1A50");
		map.put("23", "1A60");
		map.put("24", "1A98");
		map.put("25", "1A99");
		
		//领导职务
		map.put("10", "1A21");
		map.put("12", "1A22");
		map.put("14", "1A31");
		map.put("16", "1A32");
		map.put("18", "1A41");
		map.put("20", "1A42");
		
		//非领导职务
		map.put("11", "1A21");
		map.put("13", "1A22");
		map.put("15", "1A31");
		map.put("17", "1A32");
		map.put("19", "1A41");
		map.put("21", "1A42");
	}
	
	
	public int returnZb(StringBuffer sb)  throws RadowException{
		sb.append(" FROM ( SELECT "
				 + " a01.a0101, "//姓名   等
				  + " a01.a0104, "//性别
				  + " a01.a0184, "//公民身份号码   等
				  + " a01.a0117a, "//民族
				  + " a01.a0117,"
				  + " a01.a0141, "//政治面貌
				  + " a01.a0149,"//职务层次 名称
				  + " a01.a0192a,"//任职机构名称 及职务全称  等
				  + " a01.a0221, "//当前职务层次
				  + " a01.a0000, "//人员统一标识符
				  + " a01.a0107, "//出生日期
				  + " a08.a0801b, "//学历代码   等
				  + " a08_2.a0901b, "//学位代码   等
				  + " a01.A0288, "//任现职务层次时间   多
				  + " a01.A0197, "
				  + " (select b0101 from b01 where a01.A0195=b01.b0111) a0195 "
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
	
	/**
	 * grid 列表查询字段
	 * @param sb
	 * @param lb
	 * @return
	 */
	public StringBuffer returnPjGridSql(StringBuffer sb)  throws RadowException{
		sb.append(" SELECT "
				  + " a.a0101, "//姓名   等
				  + " a.a0104, "//性别
				  + " a.a0184, "//公民身份号码   等
				  + " a.a0117a, "//民族
				  + " a.a0117,"
				  + " a.a0141, "//政治面貌
				  + " a.a0149,"//职务层次 名称
				  + " a.a0192a,"//任职机构名称 及职务全称  等
				  + " a.a0221, "//当前职务层次
				  //+ " a08.a0801b, "//学历代码   等
				  //+ " a08.a0901b, "//学位代码   等
				  + " a.a0000, "//人员统一标识符
				  + " a.a0107, "//出生日期
				  + " a.A0288, "//任现职务层次时间   多
				  + " a.A0197,  "
				  + " a.a0195  "//统计关系所在单位
				  );//是否具有两年以上基层工作经历
		    
		return sb;
	}

}
