package com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class GztzztPageModel extends PageModel{

	public GztzztPageModel() {
	}

	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 加载柱状图数据
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("loadDate")
	public int loadDate() throws RadowException{
	try {
		if(SysUtil.getCacheCurrentUser().getUsertype().equals("0")){
			String loginname=SysUtil.getCacheCurrentUser().getLoginname();
			CommQuery cq=new CommQuery();
			String userid=SysUtil.getCacheCurrentUser().getId();
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
			sb.append(""
					+ " SELECT  "
						+ " count(a.a0000) num, "
	              );
			sb.append(" "
					+ " ("
					+ " select sub_code_value from code_value t where code_type='ZB09' and code_status='1' and sub_code_value!='-1' and code_value=a.a0221) sub_code_value,"
					+ " ( select code_name3 from code_value t where t.code_type='ZB09' and code_leaf='1' and t.code_status='1' and code_value = a.a0221) code_name, "
			            +" a.A0221 "
						);//当前职位层次
				sb.append(" FROM (SELECT a01.a0000, "//人员统一标识符
						  + " a01.A0221 "//当前职务层次
			          +" FROM A01 a01 "
			         + "  ");//人员统一标识符 关联 a08 记录可能为空
				if("system".equals(loginname)){
					sb.append(" ,(select  a02.a0000 "
							+ " from a02"
							+ " where "
							+ " a02.a0255 = '1' "
							+ " group by a02.a0000"
							+ " ) a02 where a01.a0163 = '1' "
							+ " and a02.a0000=a01.a0000  "
							);

				}else{
					sb.append(" ,(select  a02.a0000 "
							+ " from a02, competence_userdept cu "
							+ " where "
							+ " a02.a0255 = '1' "
							+ " and a02.A0201B = cu.b0111 "
							+ " and cu.userid = '"+userid+"' "
							+ " group by a02.a0000"
							+ " ) a02 where a01.a0163 = '1' "
							+ " and a02.a0000=a01.a0000  "
							);
				}
				sb.append(" and a01.a0101 is not null and length(a01.a0101)!=0 and a01.status!='4' ");
				if(rylb!=null&&rylb.indexOf("\'")!=-1){
			    	sb.append(" and a01.A0165 not in ( "+rylb+") ");
			    }
			sb.append(" ) a group by A0221 order by a0221  asc "
			          );
			//获取职务等级类别
			
			List<HashMap<String, Object>> list=null;
			
			list=cq.getListBySQL(sb.toString());
			
			StringBuffer ss=new GbjbqkComm().toJson(list);
			this.setSelfDefResData("1@"+ss.toString());
			//this.getPageElement("jsonString_str").setValue(ss.toString());
			//this.getExecuteSG().addExecuteCode("json_func();");
		}else{
			String otherinfo = SysUtil.getCacheCurrentUser().getOtherinfo();
			String sql_0 = "select count(*) from smt_user where usertype='0' and INSTR(otherinfo,'"+otherinfo+"')>0";
			String sql_1 = "select count(*) from smt_user where usertype='1' and INSTR(otherinfo,'"+otherinfo+"')>0";
			String sql_2 = "select count(*) from smt_user where usertype='2' and INSTR(otherinfo,'"+otherinfo+"')>0";
			String sql_3 = "select count(*) from smt_user where usertype='3' and INSTR(otherinfo,'"+otherinfo+"')>0";
			String sql_4 = "select count(*) from smt_user where usertype='4' and INSTR(otherinfo,'"+otherinfo+"')>0";
			String num_0 = HBUtil.getHBSession().createSQLQuery(sql_0).uniqueResult().toString();
			String num_1 = HBUtil.getHBSession().createSQLQuery(sql_1).uniqueResult().toString();
			String num_2 = HBUtil.getHBSession().createSQLQuery(sql_2).uniqueResult().toString();
			String num_3 = HBUtil.getHBSession().createSQLQuery(sql_3).uniqueResult().toString();
			String num_4 = HBUtil.getHBSession().createSQLQuery(sql_4).uniqueResult().toString();
			this.setSelfDefResData("1@"+"[{\"num\":"+num_0+",\"code_name\":\"普通用户\",\"sub_code_value\":\"AAA\",\"a0221\":\"0\"},{\"num\":"+num_1+",\"code_name\":\"系统管理员\",\"sub_code_value\":\"AAA\",\"a0221\":\"1\"},{\"num\":"+num_2+",\"code_name\":\"安全管理员\",\"sub_code_value\":\"AAA\",\"a0221\":\"2\"},{\"num\":"+num_3+",\"code_name\":\"审计管理员\",\"sub_code_value\":\"AAA\",\"a0221\":\"3\"},{\"num\":"+num_4+",\"code_name\":\"未定义\",\"sub_code_value\":\"AAA\",\"a0221\":\"4\"}]");
		}
		
	} catch (AppException e) {
		this.setSelfDefResData("2@统计异常!");
		e.printStackTrace();
	}
	return EventRtnType.NORMAL_SUCCESS;
	}

}
