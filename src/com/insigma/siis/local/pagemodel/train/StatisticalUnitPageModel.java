package com.insigma.siis.local.pagemodel.train;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Train;
import com.insigma.siis.local.business.entity.TrainUnit;

public class StatisticalUnitPageModel extends PageModel{
	/*private String g11027_1 = "('01', '02', '03', '04')";
	private String g11027_2 = "('05', '06', '07', '08', '09')";
	private String g11027_3 = "('10','11','12','13','14')";
	private String g11027_4 = "('10','11','12','13','14')";
	private String g11027_5 = "('10','11','12','13','14')";*/
	/**
	 * 用于过程表的查询sql
	 */
	private String g11027_1 = "('01', '02', '03', '07') and a0199='1'";
	private String g11027_2 = "('04','05', '08', '09') and a0199='1'";
	private String g11027_3 = "('06','07') and a0199='0'";
	private String g11027_4 = "('08','09') and a0199='0'";
	private String g11027_5 = "('10','11','12','13','14')";
	/**
	 * 用于code_value查询sql
	 */
	private String g11027_1c = "('01', '02', '03', '07')";
	private String g11027_2c = "('04','05', '08', '09')";
	private String g11027_3c = "('06','07')";
	private String g11027_4c = "('08','09')";
	private String g11027_5c = "('10','11','12','13','14')";
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		Map<String, Object> map_nd = new LinkedHashMap<String, Object>();
		List list_nd = sess.createSQLQuery("select g11020 from train group by g11020 order by g11020 desc").list();
		for(Object g11020 : list_nd){
			map_nd.put(g11020.toString(), g11020.toString());
		}
		((Combo)this.getPageElement("tj2")).setValueListForSelect(map_nd); 
		this.getPageElement("tj2_combo").setValue(list_nd.get(0).toString());
		this.getPageElement("tj2").setValue(list_nd.get(0).toString()); 
		
		Map<String, Object> mapunit = new LinkedHashMap<String, Object>();
		List<TrainUnit> list_unit = sess.createSQLQuery("select * from train_unit").addEntity(TrainUnit.class).list();
		mapunit.put("", "所有单位");
		for(TrainUnit unit : list_unit){
			mapunit.put(unit.getUnitid(), unit.getUnitname());
		}
		((Combo)this.getPageElement("tj1")).setValueListForSelect(mapunit); 
		this.getPageElement("tj1_combo").setValue("所有单位");
		//String b0101 = sess.createSQLQuery("select b0101 from b01 where b0111='001.001'").uniqueResult().toString();
		//this.getPageElement("tj1").setValue("001.001");
		//this.getPageElement("tj1_combo").setValue(b0101);//机构名称 中文。
		//this.getExecuteSG().addExecuteCode("Query();");
		this.setNextEventName("statData");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("statData")
	public int statData() throws RadowException{
		/*HBSession sess = HBUtil.getHBSession();
		DecimalFormat df = new DecimalFormat("0.00");
		String b0111 = this.getPageElement("tj1").getValue();
		String nd = this.getPageElement("tj2").getValue();
		String personClass = this.getPageElement("personClass").getValue();
		String a0501b = "";
		if(personClass.equals("1")){//处级及以上
			a0501b = a0501b_1;
		}else{//科级及以下
			a0501b = a0501b_2;
		}
		String zpnum = sess.createSQLQuery("select count(*) from A02 where A0201B like '%"+b0111+"%'").uniqueResult().toString();
		//计算每年每单位人均脱产培训学时数
		String tczxsnum = sess.createSQLQuery("select sum(t.a)                                "+
"  from (select nvl(sum(a1108), 0) a                                "+
"          from train_personnel                                "+
"         where a0184 in                                "+
"               (select distinct (p.a0184) a0184                                "+
"                  from Train t                                "+
 "                 join Train_Personnel p                                "+
"                    on t.trainid = p.trainid                                "+
"                 where p.a0192a in                                "+
"                       (select b0114 from b01 where b0111 = '%"+b0111+"%')                                "+
"                   and t.g11020 =                                "+nd+
"                   and p.g11027 in "+a0501b+")                                "+
"        union all                                "+
"        select nvl(sum(a1108), 0) a                                "+
"          from Train_Leader                                "+
"         where a0184 in                                "+
"               (select distinct (l.a0184) a0184                                "+
"                  from Train t                                "+
 "                 join Train_Leader l                                "+
 "                   on t.trainid = l.trainid                                "+
 "                where l.a0192a in                                "+
 "                      (select b0114 from b01 where b0111 = '%"+b0111+"%')                                "+
 "                  and t.g11020 =                                 "+b0111+
 "                  and l.g11027 in "+a0501b+")) t").uniqueResult().toString();//脱产培训总学时
		String param1 = String.valueOf(df.format((Double.valueOf(tczxsnum))/(Double.valueOf(zpnum))));
		//计算每年每单位脱产培训调训率(%)
		String cjtpnum = sess.createSQLQuery("select count(*) from (select distinct a0184 from Train_Personnel where a0192a like '%"+b0111+"%' UNION select distinct a0184 from Train_Leader where a0192a like '%"+b0111+"%')").uniqueResult().toString();//参加脱产培训人数
		String param2 = String.valueOf(df.format((Double.valueOf(cjtpnum))/(Double.valueOf(zpnum))*100));
		//计算每年每单位干部参训率(%)
		String cjpxnum = sess.createSQLQuery("select count(*) from (select distinct a0184 from Train_Personnel where a0192a like '%"+b0111+"%' UNION select distinct a0184 from Train_Leader where a0192a like '%"+b0111+"%' UNION select distinct a0184 from Train_Score where a0192a like '%"+b0111+"%' UNION select distinct a0184 from Train_Elearning where a0192a like '%"+b0111+"%')").uniqueResult().toString();//参加培训人数
		String param3 = String.valueOf(df.format((Double.valueOf(cjpxnum))/(Double.valueOf(zpnum))*100));
		//计算每人每单位网络培训学时数
		String kszxsnum = sess.createSQLQuery("select sum(a1108) from Train_Score where a0192a like '%"+b0111+"%'").uniqueResult().toString();//考试总学时
		String wlzxsnum = sess.createSQLQuery("select sum(a1108) from Train_Elearning where a0192a like '%"+b0111+"%'").uniqueResult().toString();//网络总学时
		String param4 = String.valueOf(df.format((Double.valueOf(kszxsnum)+(Double.valueOf(wlzxsnum)))/(Double.valueOf(zpnum))));
		this.getPageElement("param1").setValue(param1);
		this.getPageElement("param2").setValue(param2);
		this.getPageElement("param3").setValue(param3);
		this.getPageElement("param4").setValue(param4);
		this.getExecuteSG().addExecuteCode("Query();");
		return EventRtnType.NORMAL_SUCCESS;*/
		HBSession sess = HBUtil.getHBSession();
		DecimalFormat df = new DecimalFormat("0.00");
		String unitid = this.getPageElement("tj1").getValue();
		String nd = this.getPageElement("tj2").getValue();
		String personClass = this.getPageElement("personClass").getValue();
		String zpnum = "";//总人数
		//计算每年每单位人均脱产培训学时数
		String param1 = "";
		//计算每年每单位脱产培训调训率(%)
		String param2 = "";
		//计算每年每单位干部参训率(%)
		String param3 = "";
		//计算每人每单位网络培训学时数
		String param4 = "";
		if(StringUtils.isEmpty(unitid)){//全部单位统计
			String g11027 = "";
			if(personClass.equals("1")){//市管干部(正职)
				int num1 = Integer.valueOf(sess.createSQLQuery("select nvl(sum(pnum1),0) from train_unit where g11020="+nd).uniqueResult().toString());//局级人数
				zpnum = String.valueOf(num1);
				g11027=g11027_1;
			}else if(personClass.equals("2")){//市管干部(副职)
				int num2 = Integer.valueOf(sess.createSQLQuery("select nvl(sum(pnum2),0) from train_unit where g11020="+nd).uniqueResult().toString());//处级人数
				zpnum = String.valueOf(num2);
				g11027=g11027_2;
			}else if(personClass.equals("3")){//处级干部(正职)
				zpnum = sess.createSQLQuery("select nvl(sum(pnum3),0) from train_unit where g11020="+nd).uniqueResult().toString();
				g11027=g11027_3;
			}else if(personClass.equals("4")){//处级干部(副职)
				zpnum = sess.createSQLQuery("select nvl(sum(pnum4),0) from train_unit where g11020="+nd).uniqueResult().toString();
				g11027=g11027_4;
			}else{//科以下
				zpnum = sess.createSQLQuery("select nvl(sum(pnum5),0) from train_unit where g11020="+nd).uniqueResult().toString();
				g11027=g11027_5;
			}
			String tczxs = sess.createSQLQuery("select nvl(sum(m.a), 0)          "+
					"								  from (select p.a1108 a         "+
					"								          from Train t         "+
					"								          join Train_Personnel p         "+
					"								            on t.trainid = p.trainid         "+
					"								         where t.g11020 =          "+nd+
					"                                         and p.g11027 in "+g11027+
					"								        union all         "+
					"								        select l.a1108 a         "+
					"								          from Train t         "+
					"								          join Train_Leader l         "+
					"								            on t.trainid = l.trainid         "+
					"								         where t.g11020 =          "+nd+
					"                                         and l.g11027 in "+g11027+
					") m").uniqueResult().toString();//脱产培训总学时(人员和领导)
			
			String cjtcrs = sess.createSQLQuery("select count(m.a)               "+
					"  from (select distinct(p.a0184) a               "+
					"          from Train t               "+
					"          join Train_Personnel p               "+
					"            on t.trainid = p.trainid               "+
					"         where t.g11020 =                "+nd+
					"           and p.g11027 in               "+g11027+
					"        union               "+
					"        select distinct(l.a0184) a               "+
					"          from Train t               "+
					"          join Train_Leader l               "+
					"            on t.trainid = l.trainid               "+
					"         where t.g11020 =                "+nd+
					"           and l.g11027 in               "+g11027+
					"               ) m").uniqueResult().toString();//参加脱产培训人数
			String cjpxrs = sess.createSQLQuery("select count(m.a)                       "+
					"  from (select distinct (p.a0184) a                       "+
					"          from Train t                       "+
					"          join Train_Personnel p                       "+
					"            on t.trainid = p.trainid                       "+
					"         where t.g11020 =                        "+nd+
					"           and p.g11027 in                       "+g11027+
					"        union                       "+
					"        select distinct (l.a0184) a                       "+
					"          from Train t                       "+
					"          join Train_Leader l                       "+
					"            on t.trainid = l.trainid                       "+
					"         where t.g11020 =                        "+nd+
					"           and l.g11027 in                       "+g11027+
					"        union                       "+
					"        select distinct (e.a0184) a                       "+
					"          from train_elearning e                       "+
					"         where e.g11020 =                        "+nd+
					"           and e.g11027 in                       "+g11027+
					"        union                       "+
					"        select distinct (s.a0184) a                       "+
					"          from Train_Score s                       "+
					"         where s.g11020 =                        "+nd+
					"           and s.g11027 in                       "+g11027+
					"               ) m").uniqueResult().toString();//参加培训人数
			String wlpxrs = sess.createSQLQuery("select nvl(sum(m.a), 0)               "+
					"  from (               "+
					"        select e.a1108 a               "+
					"          from train_elearning e               "+
					"         where e.g11020 =                "+nd+
					"           and e.g11027 in               "+g11027+
					"        union all               "+
					"        select sc.a1108 a               "+
					"          from train_score sc               "+
					"         where sc.g11020 =                "+nd+
					"           and sc.g11027 in               "+g11027+
					"        ) m").uniqueResult().toString();//参加网络培训人数
			param1 = String.valueOf(df.format((Double.valueOf(tczxs))/(Double.valueOf(zpnum))));
			param2 = String.valueOf(df.format((Double.valueOf(cjtcrs)*100)/(Double.valueOf(zpnum))));
			param3 = String.valueOf(df.format((Double.valueOf(cjpxrs)*100)/(Double.valueOf(zpnum))));
			param4 = String.valueOf(df.format((Double.valueOf(wlpxrs))/(Double.valueOf(zpnum))));
		}else{//个体单位统计
			String g11027 = "";
			if(personClass.equals("1")){//市管干部(正职)
				int num1 = Integer.valueOf(sess.createSQLQuery("select nvl(sum(pnum1),0) from train_unit where g11020="+nd).uniqueResult().toString());//局级人数
				zpnum = String.valueOf(num1);
				g11027=g11027_1;
			}else if(personClass.equals("2")){//市管干部(副职)
				int num2 = Integer.valueOf(sess.createSQLQuery("select nvl(sum(pnum2),0) from train_unit where g11020="+nd).uniqueResult().toString());//处级人数
				zpnum = String.valueOf(num2);
				g11027=g11027_2;
			}else if(personClass.equals("3")){//处级干部(正职)
				zpnum = sess.createSQLQuery("select nvl(sum(pnum3),0) from train_unit where g11020="+nd).uniqueResult().toString();
				g11027=g11027_3;
			}else if(personClass.equals("4")){//处级干部(副职)
				zpnum = sess.createSQLQuery("select nvl(sum(pnum4),0) from train_unit where g11020="+nd).uniqueResult().toString();
				g11027=g11027_4;
			}else{//科以下
				zpnum = sess.createSQLQuery("select nvl(sum(pnum5),0) from train_unit where g11020="+nd).uniqueResult().toString();
				g11027=g11027_5;
			}
			String tczxs = sess.createSQLQuery("select nvl(sum(m.a), 0)          "+
					"								  from (select p.a1108 a         "+
					"								          from Train t         "+
					"								          join Train_Personnel p         "+
					"								            on t.trainid = p.trainid         "+
					"								         where t.g11020 =          "+nd+
					"                                         and p.g11027 in "+g11027+
					"                                         and p.a0177='"+unitid+"'     "+
					"								        union all         "+
					"								        select l.a1108 a         "+
					"								          from Train t         "+
					"								          join Train_Leader l         "+
					"								            on t.trainid = l.trainid         "+
					"								         where t.g11020 =          "+nd+
					"                                         and l.g11027 in "+g11027+
					"								           and l.a0177 = '"+unitid+"') m").uniqueResult().toString();//脱产培训总学时(人员和领导)
			String cjtcrs = sess.createSQLQuery("select count(m.a)               "+
					"  from (select distinct(p.a0184) a               "+
					"          from Train t               "+
					"          join Train_Personnel p               "+
					"            on t.trainid = p.trainid               "+
					"         where t.g11020 =                "+nd+
					"           and p.a0177='"+unitid+"'     "+
					"           and p.g11027 in               "+g11027+
					"        union               "+
					"        select distinct(l.a0184) a               "+
					"          from Train t               "+
					"          join Train_Leader l               "+
					"            on t.trainid = l.trainid               "+
					"         where t.g11020 =                "+nd+
					"           and l.a0177='"+unitid+"'     "+
					"           and l.g11027 in               "+g11027+
					"               ) m").uniqueResult().toString();//参加脱产培训人数
			String cjpxrs = sess.createSQLQuery("select count(m.a)                       "+
					"  from (select distinct (p.a0184) a                       "+
					"          from Train t                       "+
					"          join Train_Personnel p                       "+
					"            on t.trainid = p.trainid                       "+
					"         where t.g11020 =                        "+nd+
					"           and p.a0177='"+unitid+"'     "+
					"           and p.g11027 in                       "+g11027+
					"        union                       "+
					"        select distinct (l.a0184) a                       "+
					"          from Train t                       "+
					"          join Train_Leader l                       "+
					"            on t.trainid = l.trainid                       "+
					"         where t.g11020 =                        "+nd+
					"           and l.a0177='"+unitid+"'     "+
					"           and l.g11027 in                       "+g11027+
					"        union                       "+
					"        select distinct (e.a0184) a                       "+
					"          from train_elearning e                       "+
					"         where e.g11020 =                        "+nd+
					"           and e.a0177='"+unitid+"'     "+
					"           and e.g11027 in                       "+g11027+
					"        union                       "+
					"        select distinct (s.a0184) a                       "+
					"          from Train_Score s                       "+
					"         where s.g11020 =                        "+nd+
					"           and s.a0177='"+unitid+"'     "+
					"           and s.g11027 in                       "+g11027+
					"               ) m").uniqueResult().toString();//参加培训人数
			String wlpxrs = sess.createSQLQuery("select nvl(sum(m.a), 0)               "+
					"  from (               "+
					"        select e.a1108 a               "+
					"          from train_elearning e               "+
					"         where e.g11020 =                "+nd+
					"           and e.a0177='"+unitid+"'     "+
					"           and e.g11027 in               "+g11027+
					"        union all               "+
					"        select sc.a1108 a               "+
					"          from train_score sc               "+
					"         where sc.g11020 =                "+nd+
					"           and sc.a0177='"+unitid+"'     "+
					"           and sc.g11027 in               "+g11027+
					"        ) m").uniqueResult().toString();//参加网络培训人数
			param1 = String.valueOf(df.format((Double.valueOf(tczxs))/(Double.valueOf(zpnum))));
			param2 = String.valueOf(df.format((Double.valueOf(cjtcrs)*100)/(Double.valueOf(zpnum))));
			param3 = String.valueOf(df.format((Double.valueOf(cjpxrs)*100)/(Double.valueOf(zpnum))));
			param4 = String.valueOf(df.format((Double.valueOf(wlpxrs))/(Double.valueOf(zpnum))));
		}
		this.getPageElement("param1").setValue(param1);
		this.getPageElement("param2").setValue(param2);
		this.getPageElement("param3").setValue(param3);
		this.getPageElement("param4").setValue(param4);
		this.getExecuteSG().addExecuteCode("Query();");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	@PageEvent("grid11.dogridquery")
	@NoRequiredValidate         
	public int grid11Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		/*String unitid = this.getPageElement("tj1").getValue();
		String nd = this.getPageElement("tj2").getValue();
		String personClass = this.getPageElement("personClass").getValue();
		String a0501b = "";
		if(personClass.equals("1")){//处级及以上
			a0501b = a0501b_1;
		}else{//科级及以下
			a0501b = a0501b_2;
		}
		String sql = "select o.b0111, o.b0101, o.rs, o.zxs, decode(o.rs, 0, 0, o.zxs / o.rs) jxs                   "+
"  from (select b0111,                   "+
"               b0101,                   "+
"               (select count(distinct a0000)                   "+
"                  from a02                   "+
"                 where A0201B like b0111 || '%') rs,                   "+
"               (select sum(m.a)                   "+
"                  from (select nvl(sum(a1108), 0) a                   "+
"                          from train_personnel                   "+
"                         where a0184 in                   "+
"                               (select distinct (p.a0184) a0184                   "+
"                                  from Train t                   "+
"                                  join Train_Personnel p                   "+
"                                    on t.trainid = p.trainid                   "+
"                                 where p.a0192a in                   "+
"                                       (select b0114                   "+
"                                          from b01                   "+
"                                         where b0111 like b0111 || '%')                   "+
"                                   and t.g11020 =                   "+nd+
"                                   and p.g11027 in                   "+
"                                       "+a0501b+")                   "+
"                        union all                   "+
"                        select nvl(sum(a1108), 0) a                   "+
"                          from Train_Leader                   "+
"                         where a0184 in                   "+
"                               (select distinct (l.a0184) a0184                   "+
"                                  from Train t                   "+
"                                  join Train_Leader l                   "+
"                                    on t.trainid = l.trainid                   "+
"                                 where l.a0192a in                   "+
"                                       (select b0114                   "+
"                                          from b01                   "+
"                                         where b0111 like b0111 || '%')                   "+
"                                   and t.g11020 =                    "+nd+
"                                   and l.g11027 in                   "+
"                                       "+a0501b+")) m) zxs                   "+
"          from b01                   "+
"         where b0121 like '"+b0111+"%'                   "+
"           and b0194 = '1') o";

		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;*/
		String unitid = this.getPageElement("tj1").getValue();
		String nd = this.getPageElement("tj2").getValue();
		String personClass = this.getPageElement("personClass").getValue();
		String g11027 = "";
		if(personClass.equals("1")){//市管干部(正职)
			g11027=g11027_1;
		}else if(personClass.equals("2")){//市管干部(副职)
			g11027=g11027_2;
		}else if(personClass.equals("3")){//处级干部(正职)
			g11027=g11027_3;
		}else if(personClass.equals("4")){//处级干部(副职)
			g11027=g11027_4;
		}else{//科以下
			g11027=g11027_5;
		}
		String sql ="";
		if(StringUtils.isEmpty(unitid)){
			sql="select g.unitid unitid,                                  "+
"       g.unitname unitname,                                 "+
"       g.rs,                                 "+
"       (g.num1) + (g.num2) zxs,                                 "+
"       decode(g.rs, 0, 0, ROUND(((g.num1) + (g.num2)) / g.rs,2)) jxs                                 "+
"  from (select u.unitid unitid,                                 "+
"               u.unitname unitname,                                 "+
"               (u.pnum1) + (u.pnum2) rs,                                 "+
"               (select nvl(sum(p.a1108), 0)                                 "+
"                  from train t                                 "+
"                  join train_personnel p                                 "+
"                    on t.trainid = p.trainid                                 "+
"                 where t.g11020 =                                  "+nd+
"                   and p.a0177 = u.unitid                                 "+
"                   and p.g11027 in                                 "+g11027+
"                       ) num1,                                 "+
"               (select nvl(sum(l.a1108), 0)                                 "+
"                  from train t                                 "+
"                  join Train_Leader l                                 "+
"                    on t.trainid = l.trainid                                 "+
"                 where t.g11020 =                                  "+nd+
"                   and l.a0177 = u.unitid                                 "+
"                  and l.g11027 in                                 "+g11027+
"                       ) num2                                 "+
"          from train_unit u where u.g11020="+nd+") g order by jxs desc";
		}else{
			sql="select g.unitid unitid,                                  "+
					"       g.unitname unitname,                                 "+
					"       g.rs,                                 "+
					"       (g.num1) + (g.num2) zxs,                                 "+
					"       decode(g.rs, 0, 0, ROUND(((g.num1) + (g.num2)) / g.rs,2)) jxs                                 "+
					"  from (select u.unitid unitid,                                 "+
					"               u.unitname unitname,                                 "+
					"               (u.pnum1) + (u.pnum2) rs,                                 "+
					"               (select nvl(sum(p.a1108), 0)                                 "+
					"                  from train t                                 "+
					"                  join train_personnel p                                 "+
					"                    on t.trainid = p.trainid                                 "+
					"                 where t.g11020 =                                  "+nd+
					"                   and p.a0177 = u.unitid                                 "+
					"                   and p.g11027 in                                 "+g11027+
					"                       ) num1,                                 "+
					"               (select nvl(sum(l.a1108), 0)                                 "+
					"                  from train t                                 "+
					"                  join Train_Leader l                                 "+
					"                    on t.trainid = l.trainid                                 "+
					"                 where t.g11020 =                                  "+nd+
					"                   and l.a0177 = u.unitid                                 "+
					"                  and l.g11027 in                                 "+g11027+
					"                       ) num2                                 "+
					"          from train_unit u where u.unitid='"+unitid+"' and u.g11020="+nd+") g order by jxs desc";
		}
		this.pageQuery(sql, "SQL", start, limit); 
		initGrid2g11027();
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("grid12.dogridquery")
	@NoRequiredValidate         
	public int grid12Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		//初始化grid2中的下拉值
		String unitid = this.getPageElement("tj1").getValue();
		String nd = this.getPageElement("tj2").getValue();
		String personClass = this.getPageElement("personClass").getValue();
		String g11027 = "";
		if(personClass.equals("1")){//市管干部(正职)
			g11027=g11027_1;
		}else if(personClass.equals("2")){//市管干部(副职)
			g11027=g11027_2;
		}else if(personClass.equals("3")){//处级干部(正职)
			g11027=g11027_3;
		}else if(personClass.equals("4")){//处级干部(副职)
			g11027=g11027_4;
		}else{//科以下
			g11027=g11027_5;
		}
		String sql ="";
		if(StringUtils.isEmpty(unitid)){
			sql="select u.unitid unitid,                         "+
"       u.unitname unitname,                         "+
"       (u.pnum1) + (u.pnum2) rs,                         "+
"       nvl(m.n,0) ctr,                         "+
"       decode((u.pnum1) + (u.pnum2),                         "+
"              0,                         "+
"              0,                         "+
"              ROUND(nvl(m.n,0) / ((u.pnum1) + (u.pnum2)) * 100, 2)) dxl                         "+
"  from train_unit u                         "+
"  left join (select count(s.a) n, s.a0177                         "+
"               from (select distinct (p.a0184) a, p.a0177                         "+
"                       from train t                         "+
"                       join train_personnel p                         "+
"                         on t.trainid = p.trainid                         "+
"                      where t.g11020 =                          "+nd+
"                        and p.g11027 in "+g11027+
"                     union                         "+
"                     select distinct (l.a0184) a, l.a0177                         "+
"                       from train t                         "+
"                      join Train_Leader l                         "+
"                         on t.trainid = l.trainid                         "+
"                      where t.g11020 =                          "+nd+
"                        and l.g11027 in "+g11027+") s                         "+
"              group by s.a0177) m                         "+
"    on u.unitid = m.a0177                         "+
" where u.g11020 = "+nd+" order by dxl desc";
		}else{
			sql="select u.unitid unitid,                         "+
					"       u.unitname unitname,                         "+
					"       (u.pnum1) + (u.pnum2) rs,                         "+
					"       nvl(m.n,0) ctr,                         "+
					"       decode((u.pnum1) + (u.pnum2),                         "+
					"              0,                         "+
					"              0,                         "+
					"              ROUND(nvl(m.n,0) / ((u.pnum1) + (u.pnum2)) * 100, 2)) dxl                         "+
					"  from train_unit u                         "+
					"  left join (select count(s.a) n, s.a0177                         "+
					"               from (select distinct (p.a0184) a, p.a0177                         "+
					"                       from train t                         "+
					"                       join train_personnel p                         "+
					"                         on t.trainid = p.trainid                         "+
					"                      where t.g11020 =                          "+nd+
					"                        and p.g11027 in "+g11027+
					"                     union                         "+
					"                     select distinct (l.a0184) a, l.a0177                         "+
					"                       from train t                         "+
					"                      join Train_Leader l                         "+
					"                         on t.trainid = l.trainid                         "+
					"                      where t.g11020 =                          "+nd+
					"                        and l.g11027 in "+g11027+") s                         "+
					"              group by s.a0177) m                         "+
					"    on u.unitid = m.a0177                         "+
					" where u.g11020 = "+nd+"and u.unitid='"+unitid+"' order by dxl desc";
		}
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("grid13.dogridquery")
	@NoRequiredValidate         
	public int grid13Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String unitid = this.getPageElement("tj1").getValue();
		String nd = this.getPageElement("tj2").getValue();
		String personClass = this.getPageElement("personClass").getValue();
		String g11027 = "";
		if(personClass.equals("1")){//市管干部(正职)
			g11027=g11027_1;
		}else if(personClass.equals("2")){//市管干部(副职)
			g11027=g11027_2;
		}else if(personClass.equals("3")){//处级干部(正职)
			g11027=g11027_3;
		}else if(personClass.equals("4")){//处级干部(副职)
			g11027=g11027_4;
		}else{//科以下
			g11027=g11027_5;
		}
		String sql ="";
		if(StringUtils.isEmpty(unitid)){
			sql="select u.unitid unitid,                         "+
"       u.unitname unitname,                         "+
"       (u.pnum1) + (u.pnum2) rs,                         "+
"       nvl(m.n,0) cgr,                         "+
"       decode((u.pnum1) + (u.pnum2),                         "+
"              0,                         "+
"              0,                         "+
"              ROUND(nvl(m.n,0) / ((u.pnum1) + (u.pnum2)) * 100, 2)) cxl                         "+
"  from train_unit u                         "+
"  left join (select count(s.a) n, s.a0177                         "+
"               from (select distinct (p.a0184) a, p.a0177                         "+
"                       from train t                         "+
"                       join train_personnel p                         "+
"                         on t.trainid = p.trainid                         "+
"                      where t.g11020 =                          "+nd+
"                        and p.g11027 in "+g11027+
"                     union                         "+
"                     select distinct (l.a0184) a, l.a0177                         "+
"                       from train t                         "+
"                      join Train_Leader l                         "+
"                         on t.trainid = l.trainid                         "+
"                      where t.g11020 =                          "+nd+
"                        and l.g11027 in "+g11027+
" 					union                         "+
"                      select distinct (e.a0184) a, e.a0177                         "+
"                        from Train_Elearning e                         "+
"                      where e.g11020 =                          "+nd+
"                         and e.g11027 in                          "+g11027+
"                      union                         "+
"                      select distinct (sc.a0184) a, sc.a0177                         "+
"                        from Train_Score sc                         "+
"                       where sc.g11020 =                          "+nd+
"                         and sc.g11027 in                          "+g11027+
"                                  ) s                         "+
"              group by s.a0177) m                         "+
"    on u.unitid = m.a0177                         "+
" where u.g11020 = "+nd+" order by cxl desc";
		}else{
			sql="select u.unitid unitid,                         "+
					"       u.unitname unitname,                         "+
					"       (u.pnum1) + (u.pnum2) rs,                         "+
					"       nvl(m.n,0) wps,                         "+
					"       decode((u.pnum1) + (u.pnum2),                         "+
					"              0,                         "+
					"              0,                         "+
					"              ROUND(nvl(m.n,0) / ((u.pnum1) + (u.pnum2)) * 100, 2)) rws                         "+
					"  from train_unit u                         "+
					"  left join (select count(s.a) n, s.a0177                         "+
					"               from (select distinct (p.a0184) a, p.a0177                         "+
					"                       from train t                         "+
					"                       join train_personnel p                         "+
					"                         on t.trainid = p.trainid                         "+
					"                      where t.g11020 =                          "+nd+
					"                        and p.g11027 in "+g11027+
					"                     union                         "+
					"                     select distinct (l.a0184) a, l.a0177                         "+
					"                       from train t                         "+
					"                      join Train_Leader l                         "+
					"                         on t.trainid = l.trainid                         "+
					"                      where t.g11020 =                          "+nd+
					"                        and l.g11027 in "+g11027+") s                         "+
					"              group by s.a0177) m                         "+
					"    on u.unitid = m.a0177                         "+
					" where u.g11020 = "+nd+"and u.unitid='"+unitid+"' order by cxl desc";
		}
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("grid14.dogridquery")
	@NoRequiredValidate         
	public int grid14Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String unitid = this.getPageElement("tj1").getValue();
		String nd = this.getPageElement("tj2").getValue();
		String personClass = this.getPageElement("personClass").getValue();
		String g11027 = "";
		if(personClass.equals("1")){//市管干部(正职)
			g11027=g11027_1;
		}else if(personClass.equals("2")){//市管干部(副职)
			g11027=g11027_2;
		}else if(personClass.equals("3")){//处级干部(正职)
			g11027=g11027_3;
		}else if(personClass.equals("4")){//处级干部(副职)
			g11027=g11027_4;
		}else{//科以下
			g11027=g11027_5;
		}
		String sql ="";
		if(StringUtils.isEmpty(unitid)){
			sql="select g.unitid unitid,                    "+
"       g.unitname unitname,                   "+
"       g.rs,                   "+
"       (g.num1) + (g.num2) wps,                   "+
"       decode(g.rs, 0, 0, ROUND(((g.num1) + (g.num2)) / g.rs, 2)) rws                   "+
"  from (select u.unitid unitid,                   "+
"               u.unitname unitname,                   "+
"               (u.pnum1) + (u.pnum2) rs,                   "+
"               (select nvl(sum(e.a1108), 0)                   "+
"                  from Train_Elearning e                   "+
"                 where e.g11020 =                    "+nd+
"                   and e.a0177 = u.unitid                   "+
"                   and e.g11027 in "+g11027+") num1,                   "+
"               (select nvl(sum(sc.a1108), 0)                   "+
"                  from Train_Score sc                   "+
"                 where sc.g11020 =                    "+nd+
"                   and sc.a0177 = u.unitid                   "+
"                   and sc.g11027 in "+g11027+") num2                   "+
"          from train_unit u where u.g11020="+nd+") g order by rws desc";
		}else{
			sql="select g.unitid unitid,                    "+
					"       g.unitname unitname,                   "+
					"       g.rs,                   "+
					"       (g.num1) + (g.num2) wps,                   "+
					"       decode(g.rs, 0, 0, ROUND(((g.num1) + (g.num2)) / g.rs, 2)) rws                   "+
					"  from (select u.unitid unitid,                   "+
					"               u.unitname unitname,                   "+
					"               (u.pnum1) + (u.pnum2) rs,                   "+
					"               (select nvl(sum(e.a1108), 0)                   "+
					"                  from Train_Elearning e                   "+
					"                 where e.g11020 =                    "+nd+
					"                   and e.a0177 = u.unitid                   "+
					"                   and e.g11027 in "+g11027+") num1,                   "+
					"               (select nvl(sum(sc.a1108), 0)                   "+
					"                  from Train_Score sc                   "+
					"                 where sc.g11020 =                    "+nd+
					"                   and sc.a0177 = u.unitid                   "+
					"                   and sc.g11027 in "+g11027+") num2                   "+
					"          from train_unit u where u.g11020="+nd+" and u.unitid='"+unitid+"') g order by rws desc";
		}
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("grid2.dogridquery")
	@NoRequiredValidate         
	public int grid2Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String pname=this.getPageElement("seachName").getValue();
		String seachG11027=this.getPageElement("seachG11027").getValue();
		if(!StringUtils.isEmpty(seachG11027)){
			seachG11027 = "("+seachG11027+")";
		}
		String unitid = this.getPageElement("unitid").getValue();
		String nd = this.getPageElement("tj2").getValue();
		String personClass = this.getPageElement("personClass").getValue();
		String g11027 = "";
		if(personClass.equals("1")){//市管干部(正职)
			g11027=g11027_1;
		}else if(personClass.equals("2")){//市管干部(副职)
			g11027=g11027_2;
		}else if(personClass.equals("3")){//处级干部(正职)
			g11027=g11027_3;
		}else if(personClass.equals("4")){//处级干部(副职)
			g11027=g11027_4;
		}else{//科以下
			g11027=g11027_5;
		}
		String sql ="";
		String type = this.getPageElement("type").getValue();
		if("1".equals(type)){//
			sql ="select n.* from                        "+
					" (select x.a0184, z.a0101, z.a0104, z.a0192a, x.a1108 ,row_number() over(partition by x.a0184 order by z.updatetime desc) rn  "+
					"  from (select m.a0184, sum(m.a1108) a1108                   "+
					"          from (select p.a0184,                   "+
					"                       p.a0101,                   "+
					"                       p.a0104,                   "+
					"                       p.a0192a,                   "+
					"                       p.a1108,                   "+
					"                       p.updatetime                   "+
					"                  from train t                   "+
					"                  join train_personnel p                   "+
					"                    on t.trainid = p.trainid                   "+
					"                 where t.g11020 =                    "+nd+
					"                   and p.a0177 = '"+unitid+"'                   "+
					"                   and p.g11027 in                   "+g11027;
			if(!StringUtils.isEmpty(pname)){
				sql = sql+" and p.a0101 like '%"+pname+"%' ";
			}
			if(!StringUtils.isEmpty(seachG11027)){
				sql = sql+" and p.g11027 in "+seachG11027;
			}
			sql=sql+		"                union all                   "+
					"                select l.a0184,                   "+
					"                       l.a0101,                   "+
					"                       l.a0104,                   "+
					"                       l.a0192a,                   "+
					"                       l.a1108,                   "+
					"                       l.updatetime                   "+
					"                  from train t                   "+
					"                  join Train_Leader l                   "+
					"                    on t.trainid = l.trainid                   "+
					"                 where t.g11020 =                    "+nd+
					"                   and l.a0177 = '"+unitid+"'                   "+
					"                   and l.g11027 in                   "+g11027;
					if(!StringUtils.isEmpty(pname)){
						sql = sql+" and l.a0101 like '%"+pname+"%' ";
					}
					if(!StringUtils.isEmpty(seachG11027)){
						sql = sql+" and l.g11027 in "+seachG11027;
					}
					sql=sql+		
					"                       ) m                   "+
					"         group by m.a0184) x                   "+
					"  left join (select p.a0184,                   "+
					"                    p.a0101,                   "+
					"                    p.a0104,                   "+
					"                    p.a0192a,                   "+
					"                    p.a1108,                   "+
					"                    p.updatetime                   "+
					"               from train t                   "+
					"               join train_personnel p                   "+
					"                 on t.trainid = p.trainid                   "+
					"              where t.g11020 =                    "+nd+
					"                and p.a0177 = '"+unitid+"'                   "+
					"                and p.g11027 in                   "+g11027; 
					if(!StringUtils.isEmpty(pname)){
						sql = sql+" and p.a0101 like '%"+pname+"%' ";
					}
					if(!StringUtils.isEmpty(seachG11027)){
						sql = sql+" and p.g11027 in "+seachG11027;
					}
					sql=sql+		
					"             union all                   "+
					"             select l.a0184,                   "+
					"                    l.a0101,                   "+
					"                    l.a0104,                   "+
					"                    l.a0192a,                   "+
					"                    l.a1108,                   "+
					"                    l.updatetime                   "+
					"               from train t                   "+
					"               join Train_Leader l                   "+
					"                 on t.trainid = l.trainid                   "+
					"              where t.g11020 =                    "+nd+
					"                and l.a0177 = '"+unitid+"'                   "+
					"                and l.g11027 in                   "+g11027;
					if(!StringUtils.isEmpty(pname)){
						sql = sql+" and l.a0101 like '%"+pname+"%' ";
					}
					if(!StringUtils.isEmpty(seachG11027)){
						sql = sql+" and l.g11027 in "+seachG11027;
					}
					sql=sql+		
					"                    ) z                   "+
					"    on x.a0184 = z.a0184) n where rn=1 order by n.a1108 desc";
		}else if("2".equals(type)){
			sql ="select n.* from                        "+
					" (select x.a0184, z.a0101, z.a0104, z.a0192a, x.a1108 ,row_number() over(partition by x.a0184 order by z.updatetime desc) rn  "+
					"  from (select m.a0184, sum(m.a1108) a1108                   "+
					"          from (select p.a0184,                   "+
					"                       p.a0101,                   "+
					"                       p.a0104,                   "+
					"                       p.a0192a,                   "+
					"                       p.a1108,                   "+
					"                       p.updatetime                   "+
					"                  from train t                   "+
					"                  join train_personnel p                   "+
					"                    on t.trainid = p.trainid                   "+
					"                 where t.g11020 =                    "+nd+
					"                   and p.a0177 = '"+unitid+"'                   "+
					"                   and p.g11027 in                   "+g11027;
			if(!StringUtils.isEmpty(pname)){
				sql = sql+" and p.a0101 like '%"+pname+"%' ";
			}
			if(!StringUtils.isEmpty(seachG11027)){
				sql = sql+" and p.g11027 in "+seachG11027;
			}
			sql=sql+		
					"                union all                   "+
					"                select l.a0184,                   "+
					"                       l.a0101,                   "+
					"                       l.a0104,                   "+
					"                       l.a0192a,                   "+
					"                       l.a1108,                   "+
					"                       l.updatetime                   "+
					"                  from train t                   "+
					"                  join Train_Leader l                   "+
					"                    on t.trainid = l.trainid                   "+
					"                 where t.g11020 =                    "+nd+
					"                   and l.a0177 = '"+unitid+"'                   "+
					"                   and l.g11027 in                   "+g11027;
			if(!StringUtils.isEmpty(pname)){
				sql = sql+" and l.a0101 like '%"+pname+"%' ";
			}
			if(!StringUtils.isEmpty(seachG11027)){
				sql = sql+" and l.g11027 in "+seachG11027;
			}
			sql=sql+		
					"       union all                          "+
	                "     select e.a0184,                 "+
	                "           e.a0101,                 "+
	                "           e.a0104,                 "+
	                "           e.a0192a,                 "+
	                "           e.a1108,                 "+
	                "           e.updatetime                 "+
	                "      from Train_Elearning e                  "+
	                "     where e.g11020 = "+nd+
	                "       and e.a0177 = '"+unitid+"'"+
	                "       and e.g11027 in                   "+g11027;
			if(!StringUtils.isEmpty(pname)){
				sql = sql+" and e.a0101 like '%"+pname+"%' ";
			}
			if(!StringUtils.isEmpty(seachG11027)){
				sql = sql+" and e.g11027 in "+seachG11027;
			}
			sql=sql+		
	                "       union all                  "+
	                "     select sc.a0184,                  "+
	                "           sc.a0101,                  "+
	                "           sc.a0104,                  "+
	                "           sc.a0192a,                  "+
	                "           sc.a1108,                  "+
	                "           sc.updatetime                  "+
	                "      from Train_Score sc                   "+
	                "     where sc.g11020 =                   "+nd+
	                "       and sc.a0177 =                   '"+unitid+"'"+
	                "       and sc.g11027 in "+g11027;
			if(!StringUtils.isEmpty(pname)){
				sql = sql+" and sc.a0101 like '%"+pname+"%' ";
			}
			if(!StringUtils.isEmpty(seachG11027)){
				sql = sql+" and sc.g11027 in "+seachG11027;
			}
			sql=sql+		
					"                       ) m                   "+
					"         group by m.a0184) x                   "+
					"  left join (select p.a0184,                   "+
					"                    p.a0101,                   "+
					"                    p.a0104,                   "+
					"                    p.a0192a,                   "+
					"                    p.a1108,                   "+
					"                    p.updatetime                   "+
					"               from train t                   "+
					"               join train_personnel p                   "+
					"                 on t.trainid = p.trainid                   "+
					"              where t.g11020 =                    "+nd+
					"                and p.a0177 = '"+unitid+"'                   "+
					"                and p.g11027 in                   "+g11027;
					if(!StringUtils.isEmpty(pname)){
						sql = sql+" and p.a0101 like '%"+pname+"%' ";
					}
					if(!StringUtils.isEmpty(seachG11027)){
						sql = sql+" and p.g11027 in "+seachG11027;
					}
					sql=sql+		
					"             union all                   "+
					"             select l.a0184,                   "+
					"                    l.a0101,                   "+
					"                    l.a0104,                   "+
					"                    l.a0192a,                   "+
					"                    l.a1108,                   "+
					"                    l.updatetime                   "+
					"               from train t                   "+
					"               join Train_Leader l                   "+
					"                 on t.trainid = l.trainid                   "+
					"              where t.g11020 =                    "+nd+
					"                and l.a0177 = '"+unitid+"'                   "+
					"                and l.g11027 in                   "+g11027;
					if(!StringUtils.isEmpty(pname)){
						sql = sql+" and l.a0101 like '%"+pname+"%' ";
					}
					if(!StringUtils.isEmpty(seachG11027)){
						sql = sql+" and l.g11027 in "+seachG11027;
					}
					sql=sql+	
					"       union all                          "+
	                "     select e.a0184,                 "+
	                "           e.a0101,                 "+
	                "           e.a0104,                 "+
	                "           e.a0192a,                 "+
	                "           e.a1108,                 "+
	                "           e.updatetime                 "+
	                "      from Train_Elearning e                  "+
	                "     where e.g11020 = "+nd+
	                "       and e.a0177 = '"+unitid+"'"+
	                "       and e.g11027 in                   "+g11027;
					if(!StringUtils.isEmpty(pname)){
						sql = sql+" and e.a0101 like '%"+pname+"%' ";
					}
					if(!StringUtils.isEmpty(seachG11027)){
						sql = sql+" and e.g11027 in "+seachG11027;
					}
					sql=sql+	
	                "       union all                  "+
	                "     select sc.a0184,                  "+
	                "           sc.a0101,                  "+
	                "           sc.a0104,                  "+
	                "           sc.a0192a,                  "+
	                "           sc.a1108,                  "+
	                "           sc.updatetime                  "+
	                "      from Train_Score sc                   "+
	                "     where sc.g11020 =                   "+nd+
	                "       and sc.a0177 =                   '"+unitid+"'"+
	                "       and sc.g11027 in "+g11027;
					if(!StringUtils.isEmpty(pname)){
						sql = sql+" and sc.a0101 like '%"+pname+"%' ";
					}
					if(!StringUtils.isEmpty(seachG11027)){
						sql = sql+" and sc.g11027 in "+seachG11027;
					}
					sql=sql+	
					"                    ) z                   "+
					"    on x.a0184 = z.a0184) n where rn=1 order by n.a1108 desc";
		}else{
			sql ="select n.* from                        "+
					" (select x.a0184, z.a0101, z.a0104, z.a0192a, x.a1108 ,row_number() over(partition by x.a0184 order by z.updatetime desc) rn  "+
					"  from (select m.a0184, sum(m.a1108) a1108                   "+
					"          from (                 "+
	                "     select e.a0184,                 "+
	                "           e.a0101,                 "+
	                "           e.a0104,                 "+
	                "           e.a0192a,                 "+
	                "           e.a1108,                 "+
	                "           e.updatetime                 "+
	                "      from Train_Elearning e                  "+
	                "     where e.g11020 = "+nd+
	                "       and e.a0177 = '"+unitid+"'"+
	                "       and e.g11027 in                   "+g11027;
			if(!StringUtils.isEmpty(pname)){
				sql = sql+" and e.a0101 like '%"+pname+"%' ";
			}
			if(!StringUtils.isEmpty(seachG11027)){
				sql = sql+" and e.g11027 in "+seachG11027;
			}
			sql=sql+	
	                "       union all                  "+
	                "     select sc.a0184,                  "+
	                "           sc.a0101,                  "+
	                "           sc.a0104,                  "+
	                "           sc.a0192a,                  "+
	                "           sc.a1108,                  "+
	                "           sc.updatetime                  "+
	                "      from Train_Score sc                   "+
	                "     where sc.g11020 =                   "+nd+
	                "       and sc.a0177 =                   '"+unitid+"'"+
	                "       and sc.g11027 in "+g11027;
			if(!StringUtils.isEmpty(pname)){
				sql = sql+" and sc.a0101 like '%"+pname+"%' ";
			}
			if(!StringUtils.isEmpty(seachG11027)){
				sql = sql+" and sc.g11027 in "+seachG11027;
			}
			sql=sql+	
					"                       ) m                   "+
					"         group by m.a0184) x                   "+
					"  left join (                 "+
	                "     select e.a0184,                 "+
	                "           e.a0101,                 "+
	                "           e.a0104,                 "+
	                "           e.a0192a,                 "+
	                "           e.a1108,                 "+
	                "           e.updatetime                 "+
	                "      from Train_Elearning e                  "+
	                "     where e.g11020 = "+nd+
	                "       and e.a0177 = '"+unitid+"'"+
	                "       and e.g11027 in                   "+g11027;
			if(!StringUtils.isEmpty(pname)){
				sql = sql+" and e.a0101 like '%"+pname+"%' ";
			}
			if(!StringUtils.isEmpty(seachG11027)){
				sql = sql+" and e.g11027 in "+seachG11027;
			}
			sql=sql+	
	                "       union all                  "+
	                "     select sc.a0184,                  "+
	                "           sc.a0101,                  "+
	                "           sc.a0104,                  "+
	                "           sc.a0192a,                  "+
	                "           sc.a1108,                  "+
	                "           sc.updatetime                  "+
	                "      from Train_Score sc                   "+
	                "     where sc.g11020 =                   "+nd+
	                "       and sc.a0177 =                   '"+unitid+"'"+
	                "       and sc.g11027 in "+g11027;
			if(!StringUtils.isEmpty(pname)){
				sql = sql+" and sc.a0101 like '%"+pname+"%' ";
			}
			if(!StringUtils.isEmpty(seachG11027)){
				sql = sql+" and sc.g11027 in "+seachG11027;
			}
			sql=sql+	
					"                    ) z                   "+
					"    on x.a0184 = z.a0184) n where rn=1 order by n.a1108 desc";
		}
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@Transaction
	@PageEvent("QueryData")
	@NoRequiredValidate         
	public int clearg11027() throws RadowException, AppException, PrivilegeException{
		initGrid2g11027();
		this.setNextEventName("grid2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initGrid2g11027")
	public void initGrid2g11027() throws RadowException{
		this.getPageElement("seachName").setValue("");
		this.getPageElement("seachG11027").setValue("");
		this.getPageElement("seachG11027_combo").setValue("");
		String personClass = this.getPageElement("personClass").getValue();
		String g11027 = "";
		if(personClass.equals("1")){//市管干部(正职)
			g11027=g11027_1c;
		}else if(personClass.equals("2")){//市管干部(副职)
			g11027=g11027_2c;
		}else if(personClass.equals("3")){//处级干部(正职)
			g11027=g11027_3c;
		}else if(personClass.equals("4")){//处级干部(副职)
			g11027=g11027_4c;
		}else{//科以下
			g11027=g11027_5c;
		}
		HBSession sess = HBUtil.getHBSession();
		Map<String, Object> map_g11027 = new LinkedHashMap<String, Object>();
		List<Object[]> list = sess.createSQLQuery("select code_value,code_name from CODE_VALUE where code_type='TrainZB09' and code_value in "+g11027).list();
		for(Object[] o : list){
			map_g11027.put(o[0].toString(), o[1].toString());
		}
		((Combo)this.getPageElement("seachG11027")).setValueListForSelect(map_g11027);
	}

}
