package com.insigma.siis.local.pagemodel.DataAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.noggit.JSONUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class DataEchartsPageModel extends PageModel{
	CommQuery cq = new CommQuery();
	@Override
	public int doInit() throws RadowException {
		try {
			getA0165Sql("1");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("getA0165Sql")
	public int getA0165Sql(String a0165) throws RadowException, AppException{
		String userId = SysManagerUtils.getUserId();
		String userSql = "";
		String personqSql = "select b01.b0111,b01.b0101 from b01,COMPETENCE_USERDEPT t where t.userid='"+userId+"' and b01.b0194='3' order by length(b01.b0111),b01.sortid";
		List<HashMap<String,Object>> personList = cq.getListBySQL(personqSql);
		Map<String, Object> personqMap = new LinkedHashMap<String, Object>();
		for(HashMap<String,Object> map:personList){
			personqMap.put(map.get("b0111").toString(), map.get("b0101"));
		}
		((Combo)this.getPageElement("personq")).setValueListForSelect(personqMap);
		if("40288103556cc97701556d629135000f".equals(userId)){
			
		}else{
			userSql = " and exists(select 1 from COMPETENCE_USERDEPT t,b01 where t.userid='"+userId+"' and b01.b0111=t.b0111)";
		}
		String a0165Sql = "";
		if("03".equals(a0165)){//市管
			a0165Sql = " and A01.A0165='03' ";
		}else if("05".equals(a0165)){//部管
			a0165Sql = " and A01.A0165='05' ";
		}
		a0165Sql = a0165Sql+userSql;
		getRyChart(a0165Sql);//全体人员图
		getWorkChart(a0165Sql);//职务层次图
		getAgeChart(a0165Sql);//年龄结构图
		getEduChart(a0165Sql);//学历图
		getAllpeo(a0165Sql);//
		zxc(personList.get(0).get("b0111").toString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	public int getRyChart(String a0165Sql) throws RadowException{
		String rySql = "select count(DISTINCT A01.a0000) as num from a01 where a01.a0165='03' and a0163 = '1' "+a0165Sql
				+ " union all select count(DISTINCT A01.a0000) as num from a01 where a01.a0165='05' and a0163 = '1' "+a0165Sql
				+ " union all select count(DISTINCT A01.a0000) as num from a01 where substr(a01.A0107,1,6)>=to_char(Addmonth(sysdate,-723),'yyyymm') and a0163 = '1' "+a0165Sql
				+ " union all select count(DISTINCT A01.a0000) as num from a01 where a01.a0221 in ('1A98','1B98','1C98','27','7118','7218','7318','7418','7518','911','C98') and a0163 = '1' "+a0165Sql;
		try {
			List<HashMap<String,Object>> rylist = cq.getListBySQL(rySql);
			String ryJson = JSONUtil.toJSON(rylist);
			this.getExecuteSG().addExecuteCode("rychart('"+ryJson+"')");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	public int getWorkChart(String a0165Sql) throws RadowException{
		String workSql="select count(DISTINCT A01.a0000) as num from a01 LEFT JOIN A02 on A02.A0000=a01.a0000  where A01.A0221='1A21' AND A01.A0163 like '1%' and A02.a0219='1' and A02.a0255='1' and A02.a0279='1' "+a0165Sql
        		+ " union all select count(DISTINCT A01.a0000) as num  from a01 LEFT JOIN A02 on A02.A0000=a01.a0000  where A01.A0221 = '1A22' AND A01.A0163 like '1%' and A02.a0219='1' and A02.a0255='1' and A02.a0279='1' "+a0165Sql
        		+ " union all select count(DISTINCT A01.a0000) as num  from a01 LEFT JOIN A02 on A02.A0000=a01.a0000  where A01.A0221='1A31' AND A01.A0163 like '1%' and A02.a0219='1' and A02.a0255='1' and A02.a0279='1' "+a0165Sql
        		+ " union all select count(DISTINCT A01.a0000) as num  from a01 LEFT JOIN A02 on A02.A0000=a01.a0000  where A01.A0221='1A32' AND A01.A0163 like '1%' and A02.a0219='1' and A02.a0255='1' and A02.a0279='1' "+a0165Sql
        		+ " union all select count(DISTINCT A01.a0000) as num  from a01 LEFT JOIN A02 on A02.A0000=a01.a0000  where A01.A0221='1A41' AND A01.A0163 like '1%' and A02.a0219='1' and A02.a0255='1' and A02.a0279='1' "+a0165Sql
        		+ " union all select count(DISTINCT A01.a0000) as num  from a01 LEFT JOIN A02 on A02.A0000=a01.a0000  where A01.A0221='1A42' AND A01.A0163 like '1%' and A02.a0219='1' and A02.a0255='1' and A02.a0279='1' "+a0165Sql;
		try {
			List<HashMap<String,Object>> worklist = cq.getListBySQL(workSql);
			String workJson = JSONUtil.toJSON(worklist);
			this.getExecuteSG().addExecuteCode("workchart('"+workJson+"')");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int getAgeChart(String a0165Sql) throws RadowException{
		String ageSql="select count(1) as num from a01  where substr(a01.A0107,1,6) > to_char(Addmonth(sysdate,-420),'yyyymm') AND a01.A0163 like '1%' "+a0165Sql
	       		+ " union all select count(1) as num from a01   where substr(a01.A0107,1,6) >= to_char(Addmonth(sysdate,-480),'yyyymm') and substr(a01.A0107,1,6) < to_char(Addmonth(sysdate,-420),'yyyymm') AND a01.A0163 like '1%' "+a0165Sql
	       		+ " union all select count(1) as num from a01   where substr(a01.A0107,1,6) >= to_char(Addmonth(sysdate,-540),'yyyymm') and substr(a01.A0107,1,6) < to_char(Addmonth(sysdate,-480),'yyyymm') AND a01.A0163 like '1%' "+a0165Sql
	       		+ " union all select count(1) as num from a01   where substr(a01.A0107,1,6) >= to_char(Addmonth(sysdate,-600),'yyyymm') and substr(a01.A0107,1,6) < to_char(Addmonth(sysdate,-540),'yyyymm') AND a01.A0163 like '1%' "+a0165Sql
	       		+ " union all select count(1) as num from a01   where substr(a01.A0107,1,6) >= to_char(Addmonth(sysdate,-660),'yyyymm') and substr(a01.A0107,1,6) < to_char(Addmonth(sysdate,-600),'yyyymm') AND a01.A0163 like '1%' "+a0165Sql
	       		+ " union all select count(1) as num from a01   where substr(a01.A0107,1,6) < to_char(Addmonth(sysdate,-660),'yyyymm') AND a01.A0163 like '1%' "+a0165Sql;
		try {
			List<HashMap<String,Object>> ageList = cq.getListBySQL(ageSql);
			String ageJson = JSONUtil.toJSON(ageList);
			this.getExecuteSG().addExecuteCode("agechart('"+ageJson+"')");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int getEduChart(String a0165Sql) throws RadowException{
		String eduSql="select ZGXL,ZGXW from a01  where A01.A0163 like '1%' "+a0165Sql;
		try {
			List<HashMap<String,Object>> eduList = cq.getListBySQL(eduSql);
            int num1=0;
            int num2=0;
            int num3=0;
            int num4=0;
            for(int i=0;i<eduList.size();i++){
              Object value=eduList.get(i).get("ZGXL");
              String tag="";
               if(value == null||"".equals(value)){
            	   tag = "小学";
               }else{
            	   tag = value.toString();
               }
              if(tag.indexOf("研究生") != -1){//研究生及以上
                num1=num1+1;
              }else if(tag.indexOf("本科") != -1){//本科
                num2=num2+1;
              }else if(tag.indexOf("中专") != -1 || tag.indexOf("大专") != -1 || tag.indexOf("中技") != -1){//专科
                num3=num3+1;
              }else if(tag.indexOf("高中") != -1 ||tag.indexOf("初中") != -1 ||tag.indexOf("小学") != -1){//高中及以下
                num4=num4+1;
              }else if(tag.indexOf("大学") != -1){
                if(eduList.get(i).get("ZGXW")==null || "".equals(eduList.get(i).get("ZGXW"))){//有学历无学位
                num2=num2+1;
                }else if(eduList.get(i).get("ZGXW").toString().indexOf("学士") != -1){    //本科          
                  num2=num2+1;
                }else if(eduList.get(i).get("ZGXW").toString().indexOf("硕士") != -1 || eduList.get(i).get("ZGXW").toString().indexOf("博士") != -1){//研究生及以上
                  num1=num1+1;
                }
              }
            }
            List<HashMap<String,Object>> newEduList = new ArrayList<HashMap<String,Object>>();
            HashMap<String,Object> map1 = new HashMap<String,Object>();
            map1.put("num", num1);
            HashMap<String,Object> map2 = new HashMap<String,Object>();
            map2.put("num", num2);
            HashMap<String,Object> map3 = new HashMap<String,Object>();
            map3.put("num", num3);
            HashMap<String,Object> map4 = new HashMap<String,Object>();
            map4.put("num", num4);
            newEduList.add(map1);
            newEduList.add(map2);
            newEduList.add(map3);
            newEduList.add(map4);
            String eduJson = JSONUtil.toJSON(newEduList);
            this.getExecuteSG().addExecuteCode("educhart('"+eduJson+"')");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int getAllpeo(String a0165Sql) throws RadowException{
		String peoSql = "select count(1) as num from a01 where A01.A0104='1' AND A01.A0163 like '1%' "+a0165Sql
				+ " union all select count(1) as num from a01 where A01.A0104='2' AND A01.A0163 like '1%' "+a0165Sql
				+ "	union all select count(1) as num from a01 where A01.A0141='01' AND A01.A0163 like '1%' "+a0165Sql
				+ " union all select count(1) as num from a01 where A01.A0141 <> '01' AND A01.A0163 like '1%' "+a0165Sql;
		try {
			List<HashMap<String,Object>> peoList = cq.getListBySQL(peoSql);
			int man = new Integer(peoList.get(0).get("num").toString());
			int woman = new Integer(peoList.get(1).get("num").toString());
			int tot = man+woman;
			Double fg = 0.00;
			Double nx = 0.00;
			if(tot==0){
				
			}else{
				nx = Math.round(woman / tot * 10000) / 100.00;
				fg= Math.round(new Integer(peoList.get(3).get("num").toString()) / tot * 10000) / 100.00;				
			}
			this.getExecuteSG().addExecuteCode("document.getElementById('ryfx').innerHTML='总数："+tot+"人（其中女性"+woman+"人,占"+nx+"%;非党员"+peoList.get(3).get("num").toString()+"人,占"+fg+"%）'");
		} catch (AppException e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("asd")
	public int zxc(String personq) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		List list3 = new ArrayList();
		//根据机构主键查询出所有的法人单位
		//String personq = this.getPageElement("personq").getValue();
		String  unitSql="select b0101,b0111 from B01 t  where b0121='"+personq+"' and b0194='1' ";
		try {
			List<HashMap<String,Object>> eduList = cq.getListBySQL(unitSql);
			 for(int i=0;i<eduList.size();i++){
				 Object value=eduList.get(i).get("b0101");//法人单位名称
				 Object key=eduList.get(i).get("b0111");//法人单位机构主键 
				 //根据法人单位主键获取到应配正职领导职数和应配副职领导职数
				 String  ypzzSql=" select  B0183  from b01  where   b0111='"+key+"' ";//应配正职
					Object ypzz = sess.createSQLQuery(ypzzSql).uniqueResult();
					int ypzzsl = Integer.valueOf(ypzz.toString());
				 String  ypfzSql=" select  B0185  from b01  where   b0111='"+key+"' ";//应配副职
					Object ypfz = sess.createSQLQuery(ypfzSql).uniqueResult();
					int ypfzsl = Integer.valueOf(ypfz.toString());	
			    //根据法人单位主键获取到实配正职领导职数和实配副职领导职数	
				String spzzSql = "select count(*) ZZ from (SELECT ROW_NUMBER() "//实配正职
						+ "OVER(PARTITION BY a01.a0000 ORDER BY a02.a0223 DESC) rn, A01.A0000 FROM a02, a01 "
						+ "WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
						+ "and a02.a0201e='1'  and a02.a0219='1'  and a02.a0201b='"+key+"') tt "
						+ "where tt.rn=1";
				Object spzz = sess.createSQLQuery(spzzSql).uniqueResult();
				int spzzsl = Integer.valueOf(spzz.toString());	
				
				String spfzSql = "select count(*) ZZ from (SELECT ROW_NUMBER() "//实配副职
						+ "OVER(PARTITION BY a01.a0000 ORDER BY a02.a0223 DESC) rn, A01.A0000 FROM a02, a01 "
						+ "WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
						+ "and a02.a0201e='1'  and a02.a0219='1'  and a02.a0201b='"+key+"') tt "
						+ "where tt.rn=1";
				Object spfz = sess.createSQLQuery(spfzSql).uniqueResult();
				int spfzsl = Integer.valueOf(spfz.toString());					
				//计算正职相差数量(实配正职-应配正职),负值为缺配,正值为超配
				int  zz = spzzsl-ypzzsl;
				//计算副职相差数量(实配副职-应配副职),负值为缺配,正值为超配
				int  fz = spfzsl-ypfzsl;
				
				
				list1.add(value);//法人单位名称
				list2.add(zz);//正职相差数量
				list3.add(fz);//副职相差数量
			 }
	            String eduJson1 = JSONUtil.toJSON(list1);
	            String eduJson2 = JSONUtil.toJSON(list2);
	            String eduJson3 = JSONUtil.toJSON(list3);
	            this.getExecuteSG().addExecuteCode("charta('"+eduJson1+"','"+eduJson2+"','"+eduJson3+"')");
			 
		} catch (AppException e) {
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}


}
