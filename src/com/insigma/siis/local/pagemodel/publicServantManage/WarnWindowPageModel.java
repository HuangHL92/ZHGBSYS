package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.rowset.serial.SerialException;

import org.hsqldb.lib.StringUtil;

import com.fr.report.core.A.t;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;

public class WarnWindowPageModel extends PageModel{
	
	public static int syDayCount = 0;
	public static int birthDaycount = 0;
	private CustomQueryBS cbBs=new CustomQueryBS();
	
	public WarnWindowPageModel(){
		HBSession sess = HBUtil.getHBSession();
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
		Object syday =  sess.getSession().createSQLQuery("select t.syday from a32 t where t.userid = '"+userid+"'").uniqueResult();
		WarnWindowPageModel.setSyDayCount(syday!=null && !StringUtil.isEmpty(syday.toString())?Integer.parseInt(syday.toString()):30);
		Object birthday = sess.getSession().createSQLQuery("select t.birthday from a32 t where t.userid = '"+userid+"'").uniqueResult();
		WarnWindowPageModel.setBirthDaycount(birthday!=null&&!StringUtil.isEmpty(birthday.toString())?Integer.parseInt(birthday.toString()):30);
	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException {
		String type = this.getPageElement("subWinIdBussessId").getValue();
		this.getExecuteSG().addExecuteCode("tabHidden('"+type+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//提醒设置
	@PageEvent("setBtn.onclick")
	public int warnWin() throws RadowException {
	    this.openWindow("setWarnWin", "pages.publicServantManage.SetWarnWindow");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 刷新
	 */
	@PageEvent("reload")
	public int reload() throws RadowException {
	    this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("type1")
	public int change() throws RadowException{
		HBSession sess = null;
		sess = HBUtil.getHBSession();
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();//获取用户id
		String sqlA32 = "from A32 t where t.userid = '"+userid+"'";
		List list = sess.createQuery(sqlA32).list();
		String number = this.getPageElement("type1").getValue();
		String personViewSQL = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			personViewSQL = " and exists (select t.b0111 "+
			          "from COMPETENCE_USERDEPT t,a02 b "+
			          "where t.userid = '"+userid+"' "+
			          "and (t.type = '1' or t.type = '0') "+
			          "and b.a0201b = t.b0111 and b.a0000 = a01.a0000) ";
		}
		if(DBType.MYSQL== DBUtil.getDBType()){
			personViewSQL = "AND a01.a0000 IN (SELECT b.a0000 FROM a02 b WHERE b.A0201B IN "
					+ "(SELECT t.b0111 FROM competence_userdept t WHERE t.userid = '"+userid+"'))";
		}
		personViewSQL = personViewSQL + "and not exists(select 1 from COMPETENCE_USERPERSON cu "+
				"where cu.a0000 = a01.a0000 and cu.userid = '"+userid+"')";
		if("0".equals(number)){
			//已超过退休时间
			if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					//将查询结果赋值到grid
					StringBuffer sbretire1 = new StringBuffer();
					sbretire1.append(" select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                                      ")
				    .append("from (select isdate2(Substr(t.a0107, 0, 6)) Birthday,             ")
				    .append("             isdate2(To_Char(sysdate, 'yyyyMM')) Today,           ")
				    .append("             Months_between(isdate2(to_char(sysdate, 'yyyyMM')),  ")
				    .append("                            isdate2(substr(t.a0107, 0, 6))) age,  ")
				    .append("             t.a0104 sex,                                                    ")
				    .append("             t.a0000                                                        ")
				    .append("        from a01 t) Aa,a32,a01                                              ")
				    .append("where Aa.Birthday <> to_date('180001','yyyyMM') and decode(sex, '1', a32.mage*12,a32.fmage*12) < Aa.age and a32.userid = '"+userid+"'     ")
				    .append("                 and aa.a0000 = a01.a0000 and a01.a0163 = '1'              ");
					this.getPageElement("retireTime").setValue(sbretire1.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
					
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					//将查询结果赋值到grid
					StringBuffer sbretire1 = new StringBuffer();
					sbretire1.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                  ")
					.append("  from (select date_format(t.a0107, '%Y-%m-%d') Birthday,        ")
					.append("               date_format(now(), '%Y-%m-%d') Today,            ")
					.append("               TIMESTAMPDIFF(month,                              ")
					.append("                             date_format(t.a0107, '%Y-%m-%d'),   ")
					.append("                             date_format(now(), '%Y-%m-%d')) age,")
					.append("               t.a0104 sex,                                      ")
					.append("               t.a0000                                           ")
					.append("          from a01 t) Aa,                                        ")
					.append("       a32,                                                      ")
					.append("       a01                                                       ")
					.append(" where case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          a32.mage * 12                                          ")
					.append("         when sex != '1' then                                    ")
					.append("          a32.fmage * 12                                         ")
					.append("       end < Aa.age                                              ")
					.append("   and a32.userid = '"+userid+"'                                       ")
					.append("   and aa.a0000 = a01.a0000                                      ")
					.append("   and a01.a0163 = '1'                                          ");
					this.getPageElement("retireTime").setValue(sbretire1.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");	
					
				}

			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire2 = new StringBuffer();
					sbretire2.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                                  ")
					.append("from (select isdate2(Substr(t.a0107, 0, 6)) Birthday,             ")
					.append("             isdate2(To_Char(sysdate, 'yyyyMM')) Today,           ")
					.append("             Months_between(isdate2(to_char(sysdate, 'yyyyMM')),  ")
					.append("                            isdate2(substr(t.a0107, 0, 6))) age,  ")
					.append("             t.a0104 sex,                                                   ")
					.append("             t.a0000                                                        ")
					.append("        from a01 t) Aa,a01                                                  ")
					.append("where Aa.Birthday <> to_date('180001','yyyyMM') and decode(sex, '1', 60 * 12, 55 * 12) < Aa.age and aa.a0000 = a01.a0000 and a01.a0163 = '1' ");
					this.getPageElement("retireTime").setValue(sbretire2.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
					
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					//将查询结果赋值到grid
					StringBuffer sbretire1 = new StringBuffer();
					sbretire1.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                  ")
					.append("  from (select date_format(t.a0107, '%Y-%m-%d') Birthday,        ")
					.append("               date_format(now(), '%Y-%m-%d') Today,            ")
					.append("               TIMESTAMPDIFF(month,                              ")
					.append("                             date_format(t.a0107, '%Y-%m-%d'),   ")
					.append("                             date_format(now(), '%Y-%m-%d')) age,")
					.append("               t.a0104 sex,                                      ")
					.append("               t.a0000                                           ")
					.append("          from a01 t) Aa,                                        ")
					/*.append("       a32,                                                      ")*/
					.append("       a01                                                       ")
					.append(" where case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          60 * 12                                          ")
					.append("         when sex != '1' then                                    ")
					.append("          55 * 12                                         ")
					.append("       end < Aa.age                                              ")
					.append("   and aa.a0000 = a01.a0000                                      ")
					.append("   and a01.a0163 = '1'                                          ");
					this.getPageElement("retireTime").setValue(sbretire1.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
					
					
				}
				
			}
		}else if("1".equals(number)){
			//一年内退休
			if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire3 = new StringBuffer();
					sbretire3.append(" select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                            ")
					.append("   from (select isdate2(Substr(t.a0107, 0, 6)) Birthday,            ")
					.append("                isdate2(To_Char(sysdate, 'yyyyMM')) Today,          ")
					.append("                Months_between(isdate2(to_char(sysdate, 'yyyyMM')),            ")
					.append("                               isdate2(substr(t.a0107, 0, 6))) age, ")
					.append("                t.a0104 sex,                                                  ")
					.append("                t.a0000                                                       ")
					.append("           from a01 t) Aa,                                                    ")
					.append("        a01,                                                                  ")
					.append("        a32                                                                   ")
					.append("  where decode(sex, '1', (a32.mage - 1) * 12, (a32.fmage - 1) * 12) <=        ")
					.append("        Aa.age                                                                ")
					.append("    and decode(sex, '1', a32.mage * 12, a32.fmage * 12) >= aa.age             ")
					.append("    and a01.a0000 = aa.a0000                                                  ")
					.append("    and a32.userid = '"+userid+"' and a01.a0163 = '1'                        ");
					this.getPageElement("retireTime").setValue(sbretire3.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
				    
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire3 = new StringBuffer();
					sbretire3.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                  ")
					.append("  from (select date_format(t.a0107, '%Y-%m-%d') Birthday,        ")
					.append("               date_format(now(), '%Y-%m-%d') Today,            ")
					.append("               TIMESTAMPDIFF(month,                              ")
					.append("                             date_format(t.a0107, '%Y-%m-%d'),   ")
					.append("                             date_format(now(), '%Y-%m-%d')) age,")
					.append("               t.a0104 sex,                                      ")
					.append("               t.a0000                                           ")
					.append("          from a01 t) Aa,                                        ")
					.append("       a32,                                                      ")
					.append("       a01                                                       ")
					.append(" where case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          (a32.mage - 1) * 12                                          ")
					.append("         when sex != '1' then                                    ")
					.append("          (a32.fmage - 1)* 12                                         ")
					.append("       end <= Aa.age                                              ")
					.append(" and case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          a32.mage * 12                                          ")
					.append("         when sex != '1' then                                    ")
					.append("          a32.fmage * 12                                         ")
					.append("       end >= Aa.age                                              ")
					.append("   and a32.userid = '"+userid+"'                                       ")
					.append("   and aa.a0000 = a01.a0000                                      ")
					.append("   and a01.a0163 = '1'                                          ");
					this.getPageElement("retireTime").setValue(sbretire3.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
				    
				    
				}
				

			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire4 = new StringBuffer();
					sbretire4.append(" select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                             ") 
					.append("   from (select isdate2(Substr(t.a0107, 0, 6)) Birthday,             ") 
					.append("                isdate2(To_Char(sysdate, 'yyyyMM')) Today,           ") 
					.append("                Months_between(isdate2(to_char(sysdate, 'yyyyMM')),             ")
					.append("                               isdate2(substr(t.a0107, 0, 6))) age,  ") 
					.append("                t.a0104 sex,                                                   ") 
					.append("                t.a0000                                                        ") 
					.append("           from a01 t) Aa,                                                     ") 
					.append("        a01                                                                    ") 
					.append("  where decode(sex, '1', 59 * 12, 54 * 12) <= Aa.age                           ") 
					.append("    and decode(sex, '1', 60 * 12, 55 * 12) >= aa.age                           ") 
					.append("    and a01.a0000 = aa.a0000 and a01.a0163 = '1'                              "); 
					this.getPageElement("retireTime").setValue(sbretire4.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
					
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire4 = new StringBuffer();
					sbretire4.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                  ")
					.append("  from (select date_format(t.a0107, '%Y-%m-%d') Birthday,        ")
					.append("               date_format(now(), '%Y-%m-%d') Today,            ")
					.append("               TIMESTAMPDIFF(month,                              ")
					.append("                             date_format(t.a0107, '%Y-%m-%d'),   ")
					.append("                             date_format(now(), '%Y-%m-%d')) age,")
					.append("               t.a0104 sex,                                      ")
					.append("               t.a0000                                           ")
					.append("          from a01 t) Aa,                                        ")
					/*.append("       a32,                                                      ")*/
					.append("       a01                                                       ")
					.append(" where case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          59 * 12                                          ")
					.append("         when sex != '1' then                                    ")
					.append("          54 * 12                                         ")
					.append("       end <= Aa.age                                              ")
					.append(" and case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          60 * 12                                          ")
					.append("         when sex != '1' then                                    ")
					.append("          55 * 12                                         ")
					.append("       end >= Aa.age                                              ")
					.append("   and aa.a0000 = a01.a0000                                      ")
					.append("   and a01.a0163 = '1'                                          "); 
					this.getPageElement("retireTime").setValue(sbretire4.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
					
					
				}
				

			}
		}else if("2".equals(number)){
			//下月退休
			if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire5 = new StringBuffer();
					sbretire5.append(" select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                             ") 
					.append("   from (select isdate2(Substr(t.a0107, 0, 6)) Birthday,             ") 
					.append("                isdate2(To_Char(sysdate, 'yyyyMM')) Today,           ") 
					.append("                Months_between(isdate2(to_char(sysdate, 'yyyyMM')),             ")
					.append("                               isdate2(substr(t.a0107, 0, 6))) age,  ") 
					.append("                t.a0104 sex,                                                   ") 
					.append("                t.a0000                                                        ") 
					.append("           from a01 t) Aa,                                                     ") 
					.append("        a01,                                                                   ") 
					.append("        a32                                                                    ") 
					.append("  where decode(sex, '1', a32.mage * 12 - 1, a32.fmage * 12 - 1) =              ") 
					.append("        aa.age                                                                 ") 
					.append("    and a01.a0000 = aa.a0000                                                   ") 
					.append("    and a32.userid = '"+userid+"' and a01.a0163 = '1'                         ");
					this.getPageElement("retireTime").setValue(sbretire5.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
				   
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire5 = new StringBuffer();
					sbretire5.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                  ")
					.append("  from (select date_format(t.a0107, '%Y-%m-%d') Birthday,        ")
					.append("               date_format(now(), '%Y-%m-%d') Today,            ")
					.append("               TIMESTAMPDIFF(month,                              ")
					.append("                             date_format(t.a0107, '%Y-%m-%d'),   ")
					.append("                             date_format(now(), '%Y-%m-%d')) age,")
					.append("               t.a0104 sex,                                      ")
					.append("               t.a0000                                           ")
					.append("          from a01 t) Aa,                                        ")
					.append("       a32,                                                      ")
					.append("       a01                                                       ")
					.append(" where case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          a32.mage * 12 -1                                         ")
					.append("         when sex != '1' then                                    ")
					.append("          a32.fmage * 12 -1                                        ")
					.append("       end = Aa.age                                              ")
					.append("   and a32.userid = '"+userid+"'                                       ")
					.append("   and aa.a0000 = a01.a0000                                      ")
					.append("   and a01.a0163 = '1'                                          ");
					this.getPageElement("retireTime").setValue(sbretire5.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
				    
				    
				}
				

			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire6 = new StringBuffer();
					sbretire6.append(" select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                                      ")
					.append("   from (select isdate2(Substr(t.a0107, 0, 6)) Birthday,             ")
					.append("                isdate2(To_Char(sysdate, 'yyyyMM')) Today,           ")
					.append("                Months_between(isdate2(to_char(sysdate, 'yyyyMM')),             ")
					.append("                               isdate2(substr(t.a0107, 0, 6))) age,  ")
					.append("                t.a0104 sex,                                                   ")
					.append("                t.a0000                                                        ")
					.append("           from a01 t) Aa,                                                     ")
					.append("        a01                                                                    ")
					.append("  where decode(sex, '1', 60 * 12 - 1, 55 * 12 - 1) =                           ")
					.append("        aa.age                                                                 ")
					.append("    and a01.a0000 = aa.a0000 and a01.a0163 = '1'                              ");
					this.getPageElement("retireTime").setValue(sbretire6.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
				    
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire6 = new StringBuffer();
					sbretire6.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                  ")
					.append("  from (select date_format(t.a0107, '%Y-%m-%d') Birthday,        ")
					.append("               date_format(now(), '%Y-%m-%d') Today,            ")
					.append("               TIMESTAMPDIFF(month,                              ")
					.append("                             date_format(t.a0107, '%Y-%m-%d'),   ")
					.append("                             date_format(now(), '%Y-%m-%d')) age,")
					.append("               t.a0104 sex,                                      ")
					.append("               t.a0000                                           ")
					.append("          from a01 t) Aa,                                        ")
					/*.append("       a32,                                                      ")*/
					.append("       a01                                                       ")
					.append(" where case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          60 * 12 -1                                         ")
					.append("         when sex != '1' then                                    ")
					.append("          55 * 12 -1                                        ")
					.append("       end = Aa.age                                              ")
					.append("   and aa.a0000 = a01.a0000                                      ")
					.append("   and a01.a0163 = '1'                                          ");
					this.getPageElement("retireTime").setValue(sbretire6.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
				    
				}
			}
		}else{
			//本月退休
            if(list.size()>0){
            	if(DBType.ORACLE == DBUtil.getDBType()){
            		StringBuffer sbretire7 = new StringBuffer();
                	sbretire7.append(" select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                             ") 
    				.append("   from (select isdate2(Substr(t.a0107, 0, 6)) Birthday,             ") 
    				.append("                isdate2(To_Char(sysdate, 'yyyyMM')) Today,           ") 
    				.append("                Months_between(isdate2(to_char(sysdate, 'yyyyMM')),   ") 
    				.append("                               isdate2(substr(t.a0107, 0, 6))) age,  ") 
    				.append("                t.a0104 sex,                                                   ") 
    				.append("                t.a0000                                                        ") 
    				.append("           from a01 t) Aa,                                                     ") 
    				.append("        a01,                                                                   ") 
    				.append("        a32                                                                    ") 
    				.append("  where decode(sex, '1', a32.mage * 12, a32.fmage * 12) =                      ") 
    				.append("        aa.age                                                                 ") 
    				.append("    and a01.a0000 = aa.a0000                                                   ")
    				.append("    and a32.userid = '"+userid+"' and a01.a0163 = '1'                         ");
                	this.getPageElement("retireTime").setValue(sbretire7.toString()+personViewSQL);
                	this.setNextEventName("persongrid2.dogridquery");
    			    
            	}else if(DBType.MYSQL == DBUtil.getDBType()){
            		StringBuffer sbretire7 = new StringBuffer();
                	sbretire7.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                  ")
					.append("  from (select date_format(t.a0107, '%Y-%m-%d') Birthday,        ")
					.append("               date_format(now(), '%Y-%m-%d') Today,            ")
					.append("               TIMESTAMPDIFF(month,                              ")
					.append("                             date_format(t.a0107, '%Y-%m-%d'),   ")
					.append("                             date_format(now(), '%Y-%m-%d')) age,")
					.append("               t.a0104 sex,                                      ")
					.append("               t.a0000                                           ")
					.append("          from a01 t) Aa,                                        ")
					.append("       a32,                                                      ")
					.append("       a01                                                       ")
					.append(" where case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          a32.mage * 12                                         ")
					.append("         when sex != '1' then                                    ")
					.append("          a32.fmage * 12                                        ")
					.append("       end = Aa.age                                              ")
					.append("   and a32.userid = '"+userid+"'                                       ")
					.append("   and aa.a0000 = a01.a0000                                      ")
					.append("   and a01.a0163 = '1'                                          ");
                	this.getPageElement("retireTime").setValue(sbretire7.toString()+personViewSQL);
                	this.setNextEventName("persongrid2.dogridquery");
    			    
    			    
            	}
            	
			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire8 = new StringBuffer();
					sbretire8.append(" select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                             ")
					.append("   from (select isdate2(Substr(t.a0107, 0, 6)) Birthday,             ")
					.append("                isdate2(To_Char(sysdate, 'yyyyMM')) Today,           ")
					.append("                Months_between(isdate2(to_char(sysdate, 'yyyyMM')),       ")
					.append("                               isdate2(substr(t.a0107, 0, 6))) age,  ")
					.append("                t.a0104 sex,                                                   ")
					.append("                t.a0000                                                        ")
					.append("           from a01 t) Aa,                                                     ")
					.append("        a01                                                                    ")
					.append("  where decode(sex, '1', 60 * 12, 55 * 12) =                                   ")
					.append("        aa.age                                                                 ")
					.append("    and a01.a0000 = aa.a0000 and a01.a0163 = '1'                              ");
					this.getPageElement("retireTime").setValue(sbretire8.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
				    
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire8 = new StringBuffer();
					sbretire8.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                  ")
					.append("  from (select date_format(t.a0107, '%Y-%m-%d') Birthday,        ")
					.append("               date_format(now(), '%Y-%m-%d') Today,            ")
					.append("               TIMESTAMPDIFF(month,                              ")
					.append("                             date_format(t.a0107, '%Y-%m-%d'),   ")
					.append("                             date_format(now(), '%Y-%m-%d')) age,")
					.append("               t.a0104 sex,                                      ")
					.append("               t.a0000                                           ")
					.append("          from a01 t) Aa,                                        ")
					/*.append("       a32,                                                      ")*/
					.append("       a01                                                       ")
					.append(" where case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          60 * 12                                         ")
					.append("         when sex != '1' then                                    ")
					.append("          55 * 12                                        ")
					.append("       end = Aa.age                                              ")
					.append("   and aa.a0000 = a01.a0000                                      ")
					.append("   and a01.a0163 = '1'                                          ");
					this.getPageElement("retireTime").setValue(sbretire8.toString()+personViewSQL);
					this.setNextEventName("persongrid2.dogridquery");
				    
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//切换tab页时触发事件
	@PageEvent("tab.tabchange")
	public int grantTabChange(String Index) throws RadowException{
		HBSession sess = null;
		sess = HBUtil.getHBSession();
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();//获取用户id
		String sqlA32 = "from A32 t where t.userid = '"+userid+"'";
		String personViewSQL = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			personViewSQL = " and exists (select t.b0111 "+
			          "from COMPETENCE_USERDEPT t,a02 b "+
			          "where t.userid = '"+userid+"' "+
			          "and (t.type = '1' or t.type = '0') "+
			          "and b.a0201b = t.b0111 and b.a0000 = a01.a0000) ";
		}
		if(DBType.MYSQL== DBUtil.getDBType()){
			personViewSQL = "AND a01.a0000 IN (SELECT b.a0000 FROM a02 b WHERE b.A0201B IN "
					+ "(SELECT t.b0111 FROM competence_userdept t WHERE t.userid = '"+userid+"'))";
		}
		personViewSQL = personViewSQL + "and not exists(select 1 from COMPETENCE_USERPERSON cu "+
				"where cu.a0000 = a01.a0000 and cu.userid = '"+userid+"')";
		List list = sess.createQuery(sqlA32).list();
		this.getPageElement("tabName").setValue(Index);
		/*if(Index.equals("tab1")){
			this.getPageElement("gridNum").setValue("");
			//this.getExecuteSG().addExecuteCode("window.check1()");
			
		}*/
		if(Index.equals("tab2")){
			this.getPageElement("gridNum").setValue("persongrid1");
			/*//this.getExecuteSG().addExecuteCode("window.check()");
			//试用期到期提醒
			if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					//在A32表中有数据的情况
					//试用期到期提醒
					StringBuffer sbpro1 = new StringBuffer();
					sbpro1.append("select count(1)                                                         ") 
					.append("  from (select To_Date(t.a0134, 'yyyyMMdd') Workday,                 ") 
					.append("               Add_Months(To_Date(t.a0134, 'yyyyMMdd'), 12) EndDay,  ") 
					.append("               t.a0000                                               ") 
					.append("          from a01 t                                                 ") 
					.append("         where length(t.a0134) = 8) Aa,                              ") 
					.append("       a32,a01                                                       ") 
					.append(" where Aa.EndDay - Trunc(SYSDATE) <= a32.syday                       ") 
					.append("   and Aa.EndDay - Trunc(SYSDATE) >= 0 and a32.userid = '"+userid+"'          ")
					.append("   and aa.a0000 = a01.a0000 and a01.a0163 = '1'                              ");
					ResultSet rs2 = null;
					try {
						rs2 = sess.connection().prepareStatement(sbpro1.toString()+personViewSQL).executeQuery();
						if(rs2 != null && rs2.next()){
							//this.getPageElement("total1").setValue(rs2.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					//在A32表中有数据的情况
					//试用期到期提醒
					StringBuffer sbpro1 = new StringBuffer();
					sbpro1.append("select count(1)                                                               ") 
					.append("  from (select date_format(t.a0134, '%Y-%m-%d') Workday,                            ") 
					.append("               date_add(date_format(t.a0134, '%Y-%m-%d'), interval 12 month) EndDay,") 
					.append("               t.a0000                                                              ") 
					.append("          from a01 t                                                                ") 
					.append("         where length(t.a0134) = 8) Aa,                                             ") 
					.append("       a32,                                                                         ") 
					.append("       a01                                                                          ") 
					.append(" where TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) <=                                  ") 
					.append("       a32.syday                                                                    ") 
					.append("   and TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) >= 0                                ") 
					.append("   and a32.userid = '"+userid+"'                                                    ") 
					.append("   and aa.a0000 = a01.a0000                                                         ") 
					.append("   and a01.a0163 = '1'                                                             ");
					ResultSet rs2 = null;
					try {
						rs2 = sess.connection().prepareStatement(sbpro1.toString()+personViewSQL).executeQuery();
						if(rs2 != null && rs2.next()){
							//this.getPageElement("total1").setValue(rs2.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					//在A32中无数据的情况
					//试用期到期提醒
					StringBuffer sbpro2 = new StringBuffer();
					sbpro2.append("select count(1)                                               ")
					.append("  from (select To_Date(t.a0134, 'yyyyMMdd') Workday,                ")
					.append("               Add_Months(To_Date(t.a0134, 'yyyyMMdd'), 12) EndDay, ")
					.append("               t.a0000                                              ")
					.append("          from a01 t                                                ")
					.append("         where length(t.a0134) = 8) Aa,a01                          ")
					.append(" where Aa.EndDay - Trunc(SYSDATE) <= 30                             ")
					.append("   and Aa.EndDay - Trunc(SYSDATE) >= 0 and aa.a0000 = a01.a0000 and a01.a0163 = '1' ");
					ResultSet rs2 = null;
					try {
						rs2 = sess.connection().prepareStatement(sbpro2.toString()+personViewSQL).executeQuery();
						if(rs2!=null && rs2.next()){
							//this.getPageElement("total1").setValue(rs2.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					//在A32中无数据的情况
					//试用期到期提醒
					StringBuffer sbpro2 = new StringBuffer();
					sbpro2.append("select count(1)                                                               ") 
					.append("  from (select date_format(t.a0134, '%Y-%m-%d') Workday,                            ") 
					.append("               date_add(date_format(t.a0134, '%Y-%m-%d'), interval 12 month) EndDay,") 
					.append("               t.a0000                                                              ") 
					.append("          from a01 t                                                                ") 
					.append("         where length(t.a0134) = 8) Aa,                                             ") 
					.append("       a32,                                                                         ") 
					.append("       a01                                                                          ") 
					.append(" where TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) <=                                  ") 
					.append("       30                                                                    ") 
					.append("   and TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) >= 0                                ") 
					.append("   and aa.a0000 = a01.a0000                                                         ") 
					.append("   and a01.a0163 = '1'                                                             ");
					ResultSet rs2 = null;
					try {
						rs2 = sess.connection().prepareStatement(sbpro2.toString()+personViewSQL).executeQuery();
						if(rs2!=null && rs2.next()){
							//this.getPageElement("total1").setValue(rs2.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}*/
			this.setNextEventName("persongrid1.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(Index.equals("tab3")){
			this.getPageElement("gridNum").setValue("persongrid2");
			/*//this.getExecuteSG().addExecuteCode("window.check()");
			//退休人员提醒
			//已超过退休时间
			if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					//在A32表中有数据的情况 
					//已超过退休时间人员
					StringBuffer sbretire1 = new StringBuffer();
					sbretire1.append("select count(1)                                                                    ")
					.append("from (select To_Date(Substr(t.a0107, 0, 6), 'yyyyMM') Birthday,             ")
					.append("             To_Date(To_Char(sysdate, 'yyyyMM'), 'yyyyMM') Today,           ")
					.append("             Months_between(to_date(to_char(sysdate, 'yyyyMM'), 'yyyyMM'),  ")
					.append("                            to_date(substr(t.a0107, 0, 6), 'yyyyMM')) age,  ")
					.append("             t.a0104 sex,                                                    ")
					.append("             t.a0000                                                        ")
					.append("        from a01 t) Aa,a32,a01                                              ")
					.append("where decode(sex, '1', a32.mage*12,a32.fmage*12) < Aa.age and a32.userid = '"+userid+"'     ")
					.append("                 and aa.a0000 = a01.a0000 and a01.a0163 = '1'                              ");
					ResultSet rs3 = null;
					try {
						rs3 = sess.connection().prepareStatement(sbretire1.toString()+personViewSQL).executeQuery();
						if(rs3 != null && rs3.next()){
							//this.getPageElement("total2").setValue(rs3.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					//在A32表中有数据的情况 
					//已超过退休时间人员
					StringBuffer sbretire1 = new StringBuffer();
					sbretire1.append("select count(1)                                                  ")
					.append("  from (select date_format(t.a0107, '%Y-%m-%d') Birthday,        ")
					.append("               date_format(now(), '%Y-%m-%d') Today,            ")
					.append("               TIMESTAMPDIFF(month,                              ")
					.append("                             date_format(t.a0107, '%Y-%m-%d'),   ")
					.append("                             date_format(now(), '%Y-%m-%d')) age,")
					.append("               t.a0104 sex,                                      ")
					.append("               t.a0000                                           ")
					.append("          from a01 t) Aa,                                        ")
					.append("       a32,                                                      ")
					.append("       a01                                                       ")
					.append(" where case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          a32.mage * 12                                          ")
					.append("         when sex != '1' then                                    ")
					.append("          a32.fmage * 12                                         ")
					.append("       end < Aa.age                                              ")
					.append("   and a32.userid = '"+userid+"'                                       ")
					.append("   and aa.a0000 = a01.a0000                                      ")
					.append("   and a01.a0163 = '1'                                          ");
					ResultSet rs3 = null;
					try {
						rs3 = sess.connection().prepareStatement(sbretire1.toString()+personViewSQL).executeQuery();
						if(rs3 != null && rs3.next()){
							//this.getPageElement("total2").setValue(rs3.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					//在A32中无数据的情况
					//已超过退休时间人员
					StringBuffer sbretire2 = new StringBuffer();
					sbretire2.append("select count(1)                                                    ")
					.append("from (select To_Date(Substr(t.a0107, 0, 6), 'yyyyMM') Birthday,             ")
					.append("             To_Date(To_Char(sysdate, 'yyyyMM'), 'yyyyMM') Today,           ")
					.append("             Months_between(to_date(to_char(sysdate, 'yyyyMM'), 'yyyyMM'),  ")
					.append("                            to_date(substr(t.a0107, 0, 6), 'yyyyMM')) age,  ")
					.append("             t.a0104 sex,t.a0000                                            ")
					.append("        from a01 t) Aa,a01                                                  ")
					.append("where decode(sex, '1', 60 * 12, 55 * 12) < Aa.age and aa.a0000 = a01.a0000 and a01.a0163 = '1' ");
					ResultSet rs3 = null;
					try {
						rs3 = sess.connection().prepareStatement(sbretire2.toString()+personViewSQL).executeQuery();
						if(rs3!=null && rs3.next()){
							//this.getPageElement("total2").setValue(rs3.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					//在A32中无数据的情况
					//已超过退休时间人员
					StringBuffer sbretire2 = new StringBuffer();
					sbretire2.append("select count(1)                                                  ")
					.append("  from (select date_format(t.a0107, '%Y-%m-%d') Birthday,        ")
					.append("               date_format(now(), '%Y-%m-%d') Today,            ")
					.append("               TIMESTAMPDIFF(month,                              ")
					.append("                             date_format(t.a0107, '%Y-%m-%d'),   ")
					.append("                             date_format(now(), '%Y-%m-%d')) age,")
					.append("               t.a0104 sex,                                      ")
					.append("               t.a0000                                           ")
					.append("          from a01 t) Aa,                                        ")
					.append("       a32,                                                      ")
					.append("       a01                                                       ")
					.append(" where case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          60 * 12                                          ")
					.append("         when sex != '1' then                                    ")
					.append("          55 * 12                                         ")
					.append("       end < Aa.age                                              ")
					.append("   and aa.a0000 = a01.a0000                                      ")
					.append("   and a01.a0163 = '1'                                          ");
					ResultSet rs3 = null;
					try {
						rs3 = sess.connection().prepareStatement(sbretire2.toString()+personViewSQL).executeQuery();
						if(rs3!=null && rs3.next()){
							//this.getPageElement("total2").setValue(rs3.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}*/
			this.setNextEventName("persongrid2.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(Index.equals("tab4")){
			this.getPageElement("gridNum").setValue("persongrid3");
			//this.getExecuteSG().addExecuteCode("window.check()");
			//生日到期提醒
			/*if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					//在A32表中有数据的情况
					//生日到期人数提醒
					StringBuffer sbbirth1 = new StringBuffer();
					sbbirth1.append("Select count(1)                                                                 ") 
					.append("  From (Select Case                                                             ")
					.append("                 When Days < 0 Then                                             ")
					.append("                  Add_Months(Birthday, 12)                                      ")
					.append("                 Else                                                           ")
					.append("                  Birthday                                                      ")
					.append("               End As Birthday,                                                 ")
					.append("               A0000                                                            ")
					.append("          From (Select To_Date(To_Char(Sysdate, 'yyyy') || Substr(A0107, 5),    ")
					.append("                               'yyyyMMdd') Birthday,                            ")
					.append("                       To_Date(To_Char(Sysdate, 'yyyy') || Substr(A0107, 5),    ")
					.append("                               'yyyyMMdd') - Trunc(Sysdate) Days,               ")
					.append("                       A0107,                                                   ")
					.append("                       A0000                                                    ")
					.append("                  From A01 a                                                    ")
					.append("                 Where Length(a.A0107) = 8)) Yy,a32,a01                         ")
					.append(" Where Yy.Birthday - a32.birthday <= Trunc(Sysdate) and a32.userid = '"+userid+"'       ")
					.append("                 and yy.a0000 = a01.a0000 and a01.a0163 = '1'                          ");
					ResultSet rs1 = null;
					try {
						rs1 = sess.connection().prepareStatement(sbbirth1.toString()+personViewSQL).executeQuery();
						if(rs1 != null && rs1.next()){
							//this.getPageElement("total3").setValue(rs1.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					//在A32表中有数据的情况
					//生日到期人数提醒
					StringBuffer sbbirth1 = new StringBuffer();
					sbbirth1.append("Select count(1)                                                              ")
					.append("  From (Select Case                                                          ")
					.append("                 When Days < 0 Then                                          ")
					.append("                  date_add(Birthday, interval 12 month)                      ")
					.append("                 Else                                                        ")
					.append("                  date_format(Birthday, '%Y-%m-%d')                          ")
					.append("               End As Birthday,                                              ")
					.append("               A0000                                                         ")
					.append("          From (Select date_format(concat(date_format(now(), '%Y'),          ")
					.append("                                          Substring(A0107, 5)),              ")
					.append("                                   '%Y-%m-%d') Birthday,                     ")
					.append("                       TIMESTAMPDIFF(day,                                    ")
					.append("                                     date_format(now(), '%Y-%m-%d'),         ")
					.append("                                     date_format(concat(date_format(now(),   ")
					.append("                                                                    '%Y'),   ")
					.append("                                                        Substring(A0107, 5)),")
					.append("                                                 '%Y-%m-%d')) Days,          ")
					.append("                                                                             ")
					.append("                       A0107,                                                ")
					.append("                       A0000                                                 ")
					.append("                  From A01 a                                                 ")
					.append("                 where Length(a.A0107) = 8) xx) Yy,                          ")
					.append("       a32,                                                                  ")
					.append("       a01                                                                   ")
					.append(" Where TIMESTAMPDIFF(day, date_format(now(), '%Y-%m-%d'), Yy.Birthday) <=    ")
					.append("       a32.birthday                                                          ")
					.append("   and a32.userid = '"+userid+"'                                             ")
					.append("   and yy.a0000 = a01.a0000                                                  ")
					.append("   and a01.a0163 = '1'                                                      ");
					ResultSet rs1 = null;
					try {
						rs1 = sess.connection().prepareStatement(sbbirth1.toString()+personViewSQL).executeQuery();
						if(rs1 != null && rs1.next()){
							//this.getPageElement("total3").setValue(rs1.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					//在A32中无数据的情况
					//生日到期人数提醒
					StringBuffer sbbirth2 = new  StringBuffer();
					sbbirth2.append("Select count(1)                                                                 ") 
					.append("  From (Select Case                                                             ")
					.append("                 When Days < 0 Then                                             ")
					.append("                  Add_Months(Birthday, 12)                                      ")
					.append("                 Else                                                           ")
					.append("                  Birthday                                                      ")
					.append("               End As Birthday,                                                 ")
					.append("               A0000                                                            ")
					.append("          From (Select To_Date(To_Char(Sysdate, 'yyyy') || Substr(A0107, 5),    ")
					.append("                               'yyyyMMdd') Birthday,                            ")
					.append("                       To_Date(To_Char(Sysdate, 'yyyy') || Substr(A0107, 5),    ")
					.append("                               'yyyyMMdd') - Trunc(Sysdate) Days,               ")
					.append("                       A0107,                                                   ")
					.append("                       A0000                                                    ")
					.append("                  From A01 a                                                    ")
					.append("                 Where Length(a.A0107) = 8)) Yy,a01                             ")
					.append(" Where Yy.Birthday - 30 <= Trunc(Sysdate) and yy.a0000 = a01.a0000 and a01.a0163 = '1' ");
					ResultSet rs1 = null;
					try {
						rs1 = sess.connection().prepareStatement(sbbirth2.toString()+personViewSQL).executeQuery();
						if(rs1!=null && rs1.next()){
							//this.getPageElement("total3").setValue(rs1.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					//在A32中无数据的情况
					//生日到期人数提醒
					StringBuffer sbbirth2 = new  StringBuffer();
					sbbirth2.append("Select count(1)                                                              ")
					.append("  From (Select Case                                                          ")
					.append("                 When Days < 0 Then                                          ")
					.append("                  date_add(Birthday, interval 12 month)                      ")
					.append("                 Else                                                        ")
					.append("                  date_format(Birthday, '%Y-%m-%d')                          ")
					.append("               End As Birthday,                                              ")
					.append("               A0000                                                         ")
					.append("          From (Select date_format(concat(date_format(now(), '%Y'),          ")
					.append("                                          Substring(A0107, 5)),              ")
					.append("                                   '%Y-%m-%d') Birthday,                     ")
					.append("                       TIMESTAMPDIFF(day,                                    ")
					.append("                                     date_format(now(), '%Y-%m-%d'),         ")
					.append("                                     date_format(concat(date_format(now(),   ")
					.append("                                                                    '%Y'),   ")
					.append("                                                        Substring(A0107, 5)),")
					.append("                                                 '%Y-%m-%d')) Days,          ")
					.append("                                                                             ")
					.append("                       A0107,                                                ")
					.append("                       A0000                                                 ")
					.append("                  From A01 a                                                 ")
					.append("                 where Length(a.A0107) = 8) xx) Yy,                          ")
					.append("       a32,                                                                  ")
					.append("       a01                                                                   ")
					.append(" Where TIMESTAMPDIFF(day, date_format(now(), '%Y-%m-%d'), Yy.Birthday) <=    ")
					.append("       30                                                          ")
					.append("   and yy.a0000 = a01.a0000                                                  ")
					.append("   and a01.a0163 = '1'                                                      ");
					ResultSet rs1 = null;
					try {
						rs1 = sess.connection().prepareStatement(sbbirth2.toString()+personViewSQL).executeQuery();
						if(rs1!=null && rs1.next()){
							//this.getPageElement("total3").setValue(rs1.getString(1));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}*/
			this.setNextEventName("persongrid3.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(Index.equals("tab5")){
			
			this.setNextEventName("persongrid4.dogridquery");
		}
		if(Index.equals("tab6")){
			
			this.setNextEventName("persongrid5.dogridquery");
		}
		if(Index.equals("tab7")){
			
			this.setNextEventName("persongrid6.dogridquery");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("persongrid1.rowdbclick")
	@GridDataRange
	public int persongrid1OnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("persongrid1").getValue("a0000",this.getPageElement("persongrid1").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			//this.closeCueWindow("warnWin");
			this.request.getSession().setAttribute("nowNumber","");//为了去除上一人员、下一人员的按钮
			this.getExecuteSG().addExecuteCode("realParent.addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	@PageEvent("persongrid2.rowdbclick")
	@GridDataRange
	public int persongrid2OnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("persongrid2").getValue("a0000",this.getPageElement("persongrid2").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			//this.closeCueWindow("warnWin");
			this.request.getSession().setAttribute("nowNumber","");//为了去除上一人员、下一人员的按钮
			this.getExecuteSG().addExecuteCode("realParent.addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	@PageEvent("persongrid3.rowdbclick")
	@GridDataRange
	public int persongrid3OnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("persongrid3").getValue("a0000",this.getPageElement("persongrid3").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			//this.closeCueWindow("warnWin");
			this.request.getSession().setAttribute("nowNumber","");//为了去除上一人员、下一人员的按钮
			this.getExecuteSG().addExecuteCode("realParent.addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	@PageEvent("persongrid4.rowdbclick")
	@GridDataRange
	public int persongrid4OnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("persongrid4").getValue("a0000",this.getPageElement("persongrid4").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			//this.closeCueWindow("warnWin");
			this.request.getSession().setAttribute("nowNumber","");//为了去除上一人员、下一人员的按钮
			this.getExecuteSG().addExecuteCode("realParent.addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	@PageEvent("persongrid5.rowdbclick")
	@GridDataRange
	public int persongrid5OnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("persongrid5").getValue("a0000",this.getPageElement("persongrid5").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			//this.closeCueWindow("warnWin");
			this.request.getSession().setAttribute("nowNumber","");//为了去除上一人员、下一人员的按钮
			this.getExecuteSG().addExecuteCode("realParent.addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	/*@PageEvent("persongrid6.rowdbclick")
	@GridDataRange
	public int persongrid6OnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("persongrid6").getValue("a0000",this.getPageElement("persongrid6").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			//this.closeCueWindow("warnWin");
			this.request.getSession().setAttribute("nowNumber","");//为了去除上一人员、下一人员的按钮
			this.getExecuteSG().addExecuteCode("realParent.addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}*/
	
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("persongrid1.dogridquery")
	@NoRequiredValidate         
	public int dogridQuery1(int start,int limit) throws RadowException{
		HBSession sess = null;
		sess = HBUtil.getHBSession();
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();//获取用户id
		String sqlA32 = "from A32 t where t.userid = '"+userid+"'";
		List list = sess.createQuery(sqlA32).list();
		String personViewSQL = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			personViewSQL = " and exists (select t.b0111 "+
			          "from COMPETENCE_USERDEPT t,a02 b "+
			          "where t.userid = '"+userid+"' "+
			          "and (t.type = '1' or t.type = '0') "+
			          "and b.a0201b = t.b0111 and b.a0000 = a01.a0000) ";
		}
		if(DBType.MYSQL== DBUtil.getDBType()){
			personViewSQL = "AND a01.a0000 IN (SELECT b.a0000 FROM a02 b WHERE b.A0201B IN "
					+ "(SELECT t.b0111 FROM competence_userdept t WHERE t.userid = '"+userid+"'))";
		}
		personViewSQL = personViewSQL + "and not exists(select 1 from COMPETENCE_USERPERSON cu "+
				"where cu.a0000 = a01.a0000 and cu.userid = '"+userid+"')";
		//试用期到期提醒
			if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					 //在a32中有数据的情况下
					StringBuffer sbpro1 = new StringBuffer();
					sbpro1.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                     ")
					.append("  from (select isdate3(t.a0134) Workday,                 ") 
					.append("               Add_Months(isdate3(t.a0134), 12) EndDay,  ") 
					.append("               t.a0000                                               ") 
					.append("          from a01 t                                                 ") 
					.append("         where length(t.a0134) = 8) Aa,                              ") 
					.append("       a32,a01                                                       ") 
					.append(" where Aa.EndDay - Trunc(SYSDATE) <= a32.syday                       ") 
					.append("   and Aa.EndDay - Trunc(SYSDATE) >= 0 and Aa.Workday <> TO_DATE('18000101','yyyyMMdd') and a32.userid = '"+userid+"'          ")
					.append("                 and aa.a0000 = a01.a0000 and a01.a0163 = '1'                ");
					this.request.getSession().setAttribute("listName_swtx", "试用期到期提醒");
					this.request.getSession().setAttribute("sql_swtx", sbpro1.toString()+personViewSQL);
					this.pageQuery(sbpro1.toString()+personViewSQL,"SQL", start, limit);

				}else if(DBType.MYSQL == DBUtil.getDBType()){
					 //在a32中有数据的情况下
					StringBuffer sbpro1 = new StringBuffer();
					sbpro1.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                    ") 
					.append("  from (select date_format(t.a0134, '%Y-%m-%d') Workday,                            ") 
					.append("               date_add(date_format(t.a0134, '%Y-%m-%d'), interval 12 month) EndDay,") 
					.append("               t.a0000                                                              ") 
					.append("          from a01 t                                                                ") 
					.append("         where length(t.a0134) = 8) Aa,                                             ") 
					.append("       a32,                                                                         ")
					.append("       a01                                                                          ") 
					.append(" where TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) <=                                  ") 
					.append("       a32.syday                                                                    ") 
					.append("   and TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) >= 0                                ") 
					.append("   and a32.userid = '"+userid+"'                                                    ") 
					.append("   and aa.a0000 = a01.a0000                                                         ") 
					.append("   and a01.a0163 = '1'                                                             ");	
					this.request.getSession().setAttribute("listName_swtx", "试用期到期提醒");
					this.request.getSession().setAttribute("sql_swtx", sbpro1.toString()+personViewSQL);
					this.pageQuery(sbpro1.toString()+personViewSQL,"SQL", start, limit);
				}
		   
				
			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbpro2 = new StringBuffer();
					sbpro2.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                     ")
					.append("  from (select isdate3(t.a0134) Workday,                ")
					.append("               Add_Months(isdate3(t.a0134), 12) EndDay, ")
					.append("               t.a0000                                              ")
					.append("          from a01 t                                                ")
					.append("         where length(t.a0134) = 8) Aa,a01                          ")
//				.append("         a01                                                        ")
					.append(" where Aa.EndDay - Trunc(SYSDATE) <= 30                             ")
					.append("   and Aa.EndDay - Trunc(SYSDATE) >= 0 and Aa.Workday <> TO_DATE('18000101','yyyyMMdd') and aa.a0000 = a01.a0000 and a01.a0163 = '1' ");
					this.request.getSession().setAttribute("listName_swtx", "试用期到期提醒");
					this.request.getSession().setAttribute("sql_swtx", sbpro2.toString()+personViewSQL);
					this.pageQuery(sbpro2.toString()+personViewSQL,"SQL", start, limit);
					
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbpro2 = new StringBuffer();
					sbpro2.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                    ") 
					.append("  from (select date_format(t.a0134, '%Y-%m-%d') Workday,                            ") 
					.append("               date_add(date_format(t.a0134, '%Y-%m-%d'), interval 12 month) EndDay,") 
					.append("               t.a0000                                                              ") 
					.append("          from a01 t                                                                ") 
					.append("         where length(t.a0134) = 8) Aa,                                             ") 
					/*.append("       a32,                                                                         ") */
					.append("       a01                                                                          ") 
					.append(" where TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) <=                                  ") 
					.append("       30                                                                    ") 
					.append("   and TIMESTAMPDIFF(DAY,date_format(now(), '%Y-%m-%d'),Aa.EndDay) >= 0                                ") 
					.append("   and aa.a0000 = a01.a0000                                                         ") 
					.append("   and a01.a0163 = '1'                                                             ");
					this.request.getSession().setAttribute("listName_swtx", "试用期到期提醒");
					this.request.getSession().setAttribute("sql_swtx", sbpro2.toString()+personViewSQL);
					this.pageQuery(sbpro2.toString()+personViewSQL,"SQL", start, limit);
				}
				
			}
		 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("persongrid2.dogridquery")
	@NoRequiredValidate         
	public int dogridQuery2(int start,int limit) throws RadowException{
		String hidden = this.getPageElement("retireTime").getValue();
//		System.out.println(hidden);
		if("".equals(hidden) || hidden.isEmpty()){
			//在hidden控件为空时执行
			HBSession sess = null;
			sess = HBUtil.getHBSession();
			String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();//获取用户id
			String sqlA32 = "from A32 t where t.userid = '"+userid+"'";
			List list = sess.createQuery(sqlA32).list();
			String personViewSQL = "";
			if(DBType.ORACLE == DBUtil.getDBType()){
				personViewSQL = " and exists (select t.b0111 "+
				          "from COMPETENCE_USERDEPT t,a02 b "+
				          "where t.userid = '"+userid+"' "+
				          "and (t.type = '1' or t.type = '0') "+
				          "and b.a0201b = t.b0111 and b.a0000 = a01.a0000) ";
			}
			if(DBType.MYSQL== DBUtil.getDBType()){
				personViewSQL = "AND a01.a0000 IN (SELECT b.a0000 FROM a02 b WHERE b.A0201B IN "
						+ "(SELECT t.b0111 FROM competence_userdept t WHERE t.userid = '"+userid+"'))";
			}
			personViewSQL = personViewSQL + "and not exists(select 1 from COMPETENCE_USERPERSON cu "+
					"where cu.a0000 = a01.a0000 and cu.userid = '"+userid+"')";
			//退休人员提醒
			//已超过退休时间
			if(list.size()>0){
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire1 = new StringBuffer();
					sbretire1.append(" select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                                      ")
				     .append("from (select isdate2(Substr(t.a0107, 0, 6)) Birthday,             ")
				     .append("             isdate2(To_Char(sysdate, 'yyyyMM')) Today,           ")
				     .append("             Months_between(isdate2(to_char(sysdate, 'yyyyMM')),  ")
				     .append("                            isdate2(substr(t.a0107, 0, 6))) age,  ")
				     .append("             t.a0104 sex,                                                    ")
				     .append("             t.a0000                                                        ")
				     .append("        from a01 t) Aa,a32,a01                                              ")
				     .append("where Aa.Birthday <> to_date('180001','yyyyMM') and decode(sex, '1', a32.mage*12,a32.fmage*12) < Aa.age and a32.userid = '"+userid+"'     ")
				     .append("                 and aa.a0000 = a01.a0000 and a01.a0163 = '1'              ");
					this.request.getSession().setAttribute("listName_swtx", "退休人员提醒");
					this.request.getSession().setAttribute("sql_swtx", sbretire1.toString()+personViewSQL);
					this.pageQuery(sbretire1.toString()+personViewSQL,"SQL", start, limit);
					 
				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire1 = new StringBuffer();
					sbretire1.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                  ")
					.append("  from (select date_format(t.a0107, '%Y-%m-%d') Birthday,        ")
					.append("               date_format(now(), '%Y-%m-%d') Today,            ")
					.append("               TIMESTAMPDIFF(month,                              ")
					.append("                             date_format(t.a0107, '%Y-%m-%d'),   ")
					.append("                             date_format(now(), '%Y-%m-%d')) age,")
					.append("               t.a0104 sex,                                      ")
					.append("               t.a0000                                           ")
					.append("          from a01 t) Aa,                                        ")
					.append("       a32,                                                      ")
					.append("       a01                                                       ")
					.append(" where case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          a32.mage * 12                                          ")
					.append("         when sex != '1' then                                    ")
					.append("          a32.fmage * 12                                         ")
					.append("       end < Aa.age                                              ")
					.append("   and a32.userid = '"+userid+"'                                       ")
					.append("   and aa.a0000 = a01.a0000                                      ")
					.append("   and a01.a0163 = '1'                                          ");
					this.request.getSession().setAttribute("listName_swtx", "退休人员提醒");
					this.request.getSession().setAttribute("sql_swtx", sbretire1.toString()+personViewSQL);
					this.pageQuery(sbretire1.toString()+personViewSQL,"SQL", start, limit);
				}
				
			}else{
				if(DBType.ORACLE == DBUtil.getDBType()){
					StringBuffer sbretire2 = new StringBuffer();
					sbretire2.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                                  ")
					.append("from (select isdate2(Substr(t.a0107, 0, 6)) Birthday,             ")
					.append("             isdate2(To_Char(sysdate, 'yyyyMM')) Today,           ")
					.append("             Months_between(isdate2(to_char(sysdate, 'yyyyMM')),  ")
					.append("                            isdate2(substr(t.a0107, 0, 6))) age,  ")
					.append("             t.a0104 sex,                                                   ")
					.append("             t.a0000                                                        ")
					.append("        from a01 t) Aa,a01                                                  ")
					.append("where Aa.Birthday <> to_date('180001','yyyyMM') and decode(sex, '1', 60 * 12, 55 * 12) < Aa.age and aa.a0000 = a01.a0000 and a01.a0163 = '1' ");
					this.request.getSession().setAttribute("listName_swtx", "退休人员提醒");
					this.request.getSession().setAttribute("sql_swtx", sbretire2.toString()+personViewSQL);
					this.pageQuery(sbretire2.toString()+personViewSQL,"SQL", start, limit);

				}else if(DBType.MYSQL == DBUtil.getDBType()){
					StringBuffer sbretire2 = new StringBuffer();
					sbretire2.append("select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                  ")
					.append("  from (select date_format(t.a0107, '%Y-%m-%d') Birthday,        ")
					.append("               date_format(now(), '%Y-%m-%d') Today,            ")
					.append("               TIMESTAMPDIFF(month,                              ")
					.append("                             date_format(t.a0107, '%Y-%m-%d'),   ")
					.append("                             date_format(now(), '%Y-%m-%d')) age,")
					.append("               t.a0104 sex,                                      ")
					.append("               t.a0000                                           ")
					.append("          from a01 t) Aa,                                        ")
					/*.append("       a32,                                                      ")*/
					.append("       a01                                                       ")
					.append(" where case                                                      ")
					.append("         when sex = '1' then                                     ")
					.append("          60 * 12                                          ")
					.append("         when sex != '1' then                                    ")
					.append("          55 * 12                                         ")
					.append("       end < Aa.age                                              ")
					.append("   and aa.a0000 = a01.a0000                                      ")
					.append("   and a01.a0163 = '1'                                          ");
					this.request.getSession().setAttribute("listName_swtx", "退休人员提醒");
					this.request.getSession().setAttribute("sql_swtx", sbretire2.toString()+personViewSQL);
					 this.pageQuery(sbretire2.toString()+personViewSQL,"SQL", start, limit);
				}
				
			}
		}else{
			this.request.getSession().setAttribute("listName_swtx", "退休人员提醒");
			this.request.getSession().setAttribute("sql_swtx", hidden);
			this.pageQuery(hidden, "SQL", start, limit);
		}
		
		 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("persongrid3.dogridquery")
	@NoRequiredValidate         
	public int dogridQuery3(int start,int limit) throws RadowException{
		HBSession sess = null;
		sess = HBUtil.getHBSession();
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();//获取用户id
		String sqlA32 = "from A32 t where t.userid = '"+userid+"'";
		List list = sess.createQuery(sqlA32).list();
		String personViewSQL = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			personViewSQL = " and exists (select t.b0111 "+
			          "from COMPETENCE_USERDEPT t,a02 b "+
			          "where t.userid = '"+userid+"' "+
			          "and (t.type = '1' or t.type = '0') "+
			          "and b.a0201b = t.b0111 and b.a0000 = a01.a0000) ";
		}
		if(DBType.MYSQL== DBUtil.getDBType()){
			personViewSQL = "AND a01.a0000 IN (SELECT b.a0000 FROM a02 b WHERE b.A0201B IN "
					+ "(SELECT t.b0111 FROM competence_userdept t WHERE t.userid = '"+userid+"'))";
		}
		personViewSQL = personViewSQL + "and not exists(select 1 from COMPETENCE_USERPERSON cu "+
				"where cu.a0000 = a01.a0000 and cu.userid = '"+userid+"')";
		//生日提醒
		if(list.size()>0){
			if(DBType.ORACLE == DBUtil.getDBType()){
				StringBuffer sbbirth1 = new  StringBuffer();
				sbbirth1.append(" Select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                       ")
				  .append("  From (Select Case                                                             ")
				  .append("                 When Days < 0 Then                                             ")
				  .append("                  Add_Months(Birthday, 12)                                      ")
				  .append("                 Else                                                           ")
				  .append("                  Birthday                                                      ")
				  .append("               End As Birthday,                                                 ")
				  .append("               A0000                                                            ")
				  .append("           From (Select isdate3(To_Char(Sysdate, 'yyyy') ||           ")
				  .append("                                Substr(A0107, 5)) Birthday,           ")
				  .append("                        isdate3(To_Char(Sysdate, 'yyyy') ||           ")
				  .append("                                Substr(A0107, 5)) - Trunc(Sysdate) Days,    ")
				  .append("                        isdate3(A0107) newDdays,                        ")
				  .append("                        A0000                                         ")
				  .append("                   From A01 a                                         ")
				  .append("                  Where Length(a.A0107) = 8) b WHERE b.NEWDDAYS <> TO_DATE('18000101','yyyyMMdd')) Yy, ")
				  .append("        a01,a32                                                       ")
				  .append(" Where Yy.Birthday - a32.birthday <= Trunc(Sysdate) and a32.userid = '"+userid+"'       ")
				  .append("                 and yy.a0000 = a01.a0000 and a01.a0163 = '1'                          ");
					this.request.getSession().setAttribute("listName_swtx", "生日提醒");
					this.request.getSession().setAttribute("sql_swtx", sbbirth1.toString()+personViewSQL);
					this.pageQuery(sbbirth1.toString()+personViewSQL,"SQL", start, limit);

			}else if(DBType.MYSQL == DBUtil.getDBType()){
				StringBuffer sbbirth1 = new  StringBuffer();
				sbbirth1.append("Select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                         ")
				.append("  From (Select Case                                                          ")
				.append("                 When Days < 0 Then                                          ")
				.append("                  date_add(Birthday, interval 12 month)                      ")
				.append("                 Else                                                        ")
				.append("                  date_format(Birthday, '%Y-%m-%d')                          ")
				.append("               End As Birthday,                                              ")
				.append("               A0000                                                         ")
				.append("          From (Select date_format(concat(date_format(now(), '%Y'),          ")
				.append("                                          Substring(A0107, 5)),              ")
				.append("                                   '%Y-%m-%d') Birthday,                     ")
				.append("                       TIMESTAMPDIFF(day,                                    ")
				.append("                                     date_format(now(), '%Y-%m-%d'),         ")
				.append("                                     date_format(concat(date_format(now(),   ")
				.append("                                                                    '%Y'),   ")
				.append("                                                        Substring(A0107, 5)),")
				.append("                                                 '%Y-%m-%d')) Days,          ")
				.append("                                                                             ")
				.append("                       A0107,                                                ")
				.append("                       A0000                                                 ")
				.append("                  From A01 a                                                 ")
				.append("                 where Length(a.A0107) = 8) xx) Yy,                          ")
			    .append("       a32,                                                                  ")
				.append("       a01                                                                   ")
				.append(" Where TIMESTAMPDIFF(day, date_format(now(), '%Y-%m-%d'), Yy.Birthday) <=    ")
				.append("       a32.birthday                                                          ")
				.append("   and a32.userid = '"+userid+"'                                             ")
				.append("   and yy.a0000 = a01.a0000                                                  ")
				.append("   and a01.a0163 = '1'                                                      ");
				this.request.getSession().setAttribute("listName_swtx", "生日提醒");
				this.request.getSession().setAttribute("sql_swtx", sbbirth1.toString()+personViewSQL);
				this.pageQuery(sbbirth1.toString()+personViewSQL,"SQL", start, limit);
			}
		}else{
			if(DBType.ORACLE == DBUtil.getDBType()){
				StringBuffer sbbirth2 = new  StringBuffer();
				sbbirth2.append(" Select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                       ")
				 .append("  From (Select Case                                                             ")
				.append("                 When Days < 0 Then                                             ")
				.append("                  Add_Months(Birthday, 12)                                      ")
				.append("                 Else                                                           ")
				.append("                  Birthday                                                      ")
				.append("               End As Birthday,                                                 ")
				.append("               A0000                                                            ")
				.append("           From (Select isdate3(To_Char(Sysdate, 'yyyy') ||           ")
				  .append("                                Substr(A0107, 5)) Birthday,           ")
				  .append("                        isdate3(To_Char(Sysdate, 'yyyy') ||           ")
				  .append("                                Substr(A0107, 5)) - Trunc(Sysdate) Days,    ")
				  .append("                        isdate3(A0107) newDdays,                        ")
				  .append("                        A0000                                         ")
				  .append("                   From A01 a                                         ")
				  .append("                  Where Length(a.A0107) = 8) b WHERE b.NEWDDAYS <> TO_DATE('18000101','yyyyMMdd')) Yy, ")
				  .append("        a01                                                      ")
//				.append("                 a01                                                            ")
				.append(" Where Yy.Birthday - 30 <= Trunc(Sysdate) and yy.a0000 = a01.a0000 and a01.a0163 = '1' ");
					this.request.getSession().setAttribute("listName_swtx", "生日提醒");
					this.request.getSession().setAttribute("sql_swtx", sbbirth2.toString()+personViewSQL);
					this.pageQuery(sbbirth2.toString()+personViewSQL,"SQL", start, limit);

			}else if(DBType.MYSQL == DBUtil.getDBType()){
				StringBuffer sbbirth2 = new  StringBuffer();
				sbbirth2.append("Select a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a                                                         ")
				.append("  From (Select Case                                                          ")
				.append("                 When Days < 0 Then                                          ")
				.append("                  date_add(Birthday, interval 12 month)                      ")
				.append("                 Else                                                        ")
				.append("                  date_format(Birthday, '%Y-%m-%d')                          ")
				.append("               End As Birthday,                                              ")
				.append("               A0000                                                         ")
				.append("          From (Select date_format(concat(date_format(now(), '%Y'),          ")
				.append("                                          Substring(A0107, 5)),              ")
				.append("                                   '%Y-%m-%d') Birthday,                     ")
				.append("                       TIMESTAMPDIFF(day,                                    ")
				.append("                                     date_format(now(), '%Y-%m-%d'),         ")
				.append("                                     date_format(concat(date_format(now(),   ")
				.append("                                                                    '%Y'),   ")
				.append("                                                        Substring(A0107, 5)),")
				.append("                                                 '%Y-%m-%d')) Days,          ")
				.append("                                                                             ")
				.append("                       A0107,                                                ")
				.append("                       A0000                                                 ")
				.append("                  From A01 a                                                 ")
				.append("                 where Length(a.A0107) = 8) xx) Yy,                          ")
				/*.append("       a32,                                                                  ")*/
				.append("       a01                                                                   ")
				.append(" Where TIMESTAMPDIFF(day, date_format(now(), '%Y-%m-%d'), Yy.Birthday) <=    ")
				.append("       30                                                          ")
				.append("   and yy.a0000 = a01.a0000                                                  ")
				.append("   and a01.a0163 = '1'                                                      ");
				this.request.getSession().setAttribute("listName_swtx", "生日提醒");
				this.request.getSession().setAttribute("sql_swtx", sbbirth2.toString()+personViewSQL);
				this.pageQuery(sbbirth2.toString()+personViewSQL,"SQL", start, limit);
			}
		}
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("persongrid4.dogridquery")
	@NoRequiredValidate         
	public int dogridQuery4(int start,int limit) throws RadowException{
		String userid = SysManagerUtils.getUserId();//获取用户id
		
		//String sqlIn = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE LOGINID = '"+userid+"' AND OVERDAY >= (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		String sqlIn = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			sqlIn = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE LOGINID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY >= (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		}
		if(DBType.MYSQL == DBUtil.getDBType()){
			sqlIn = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE LOGINID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY >= (select date_format(now(),'%Y%m%d'))";
		}
		String sql = "SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM("+sqlIn+") b,A01 a01 WHERE A01.A0000 = b.A0000";
		this.request.getSession().setAttribute("listName_swtx", "待转入人员");
		this.request.getSession().setAttribute("sql_swtx", sql);
		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("persongrid5.dogridquery")
	@NoRequiredValidate         
	public int dogridQuery5(int start,int limit) throws RadowException{
		String userid = SysManagerUtils.getUserId();//获取用户id
		
		//String sqlOut = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE USEID = '"+userid+"' AND OVERDAY >= (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		String sqlOut = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			sqlOut = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE USEID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY >= (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		}
		if(DBType.MYSQL == DBUtil.getDBType()){
			sqlOut = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE USEID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY >= (select date_format(now(),'%Y%m%d'))";
		}
		String sql = "SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM("+sqlOut+") b,A01 a01 WHERE A01.A0000 = b.A0000";
		this.request.getSession().setAttribute("listName_swtx", "待转出人员");
		this.request.getSession().setAttribute("sql_swtx", sql);
		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("persongrid6.dogridquery")
	@NoRequiredValidate         
	public int dogridQuery6(int start,int limit) throws RadowException{
		String userid = SysManagerUtils.getUserId();//获取用户id
		
		//String sqlBack = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE USEID = '"+userid+"' AND OVERDAY < (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		String sqlBack = "";
		if(DBType.ORACLE == DBUtil.getDBType()){
			sqlBack = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE USEID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY < (SELECT TO_CHAR(SYSDATE,'YYYYMMDD') FROM DUAL)";
		}
		if(DBType.MYSQL == DBUtil.getDBType()){
			sqlBack = "SELECT A0000 FROM PEOPLE_TRANSFER WHERE USEID = '"+SysManagerUtils.getUserId()+"' "
					+ "AND OVERDAY < (select date_format(now(),'%Y%m%d'))";
		}
		
		String sql = "SELECT a01.a0000,a01.a0101,a01.a0104,a01.A0107,a01.A0192a FROM("+sqlBack+") b,A01 a01 WHERE A01.A0000 = b.A0000";
		this.request.getSession().setAttribute("listName_swtx", "退回人员");
		this.request.getSession().setAttribute("sql_swtx", sql);
		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("revokeWarn")
	public int revokeWarn(String a0000){
		trueRevoke("revoke","调转撤销后，您可在'人员信息'——'非现职人员'——原任职机构下查询到该人员。是否确认撤销？",a0000);
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	private void trueRevoke(String fnDelte, String strHint,String str){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName(fnDelte);
		ne.setNextEventParameter(str);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage(strHint); // 窗口提示信息
	}
	
	@PageEvent("revoke")
	@Transaction
	public int revoke(String a0000)throws AppException, RadowException{
		HBSession sess = HBUtil.getHBSession();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null ){
			throw new AppException("人员信息有误！");
		}
		
		//从people_transfer中删除记录，将其改为现职人员（在其他人员中可以查询到）
		String sql = "DELETE FROM PEOPLE_TRANSFER WHERE USEID = '"+SysManagerUtils.getUserId()+"' AND A0000 = '"+a0000+"'";
		sess.createSQLQuery(sql).executeUpdate();
		
		//a01.setA0163("1");
		//a01.setStatus("1");
		//a01.setOrgid("");
		//sess.update(a01);
		
		this.getExecuteSG().addExecuteCode("revokeSuccess()");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	//关闭窗口
	@PageEvent("closeBtn.onclick")
	public int close() throws AppException, RadowException {
		this.closeCueWindow("warnWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//查看事件
	@PageEvent("viewBtn.onclick")
	public int view() throws AppException, RadowException {
		StringBuffer sb = new StringBuffer();
		String tabName = this.getPageElement("tabName").getValue();
		if("tab1".equals(tabName)){
			
		}else if("tab2".equals(tabName)){
			List<HashMap<String,Object>> gridList = this.getPageElement("persongrid1").getValueList();
			if(gridList == null || gridList.isEmpty()){
				this.setMainMessage("无可查看人员");
			}else{
				for(HashMap<String,Object> gridMap : gridList){
					Object obj = gridMap.get("a0000");
					if(obj!=null){
						sb = sb.append(obj.toString()+",");
					}
				}
				String a0000s = sb.substring(0, sb.length()-1);
				this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('view','"+a0000s+"')");
				this.closeCueWindow("warnWin");
			}
			
		}else if("tab3".equals(tabName)){
			List<HashMap<String,Object>> gridList = this.getPageElement("persongrid2").getValueList();
			if(gridList == null || gridList.isEmpty()){
				this.setMainMessage("无可查看人员");
			}else{
			for(HashMap<String,Object> gridMap : gridList){
				Object obj = gridMap.get("a0000");
				if(obj!=null){
					sb = sb.append(obj.toString()+",");
				}
			}
			String a0000s = sb.substring(0, sb.length()-1);
			this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('view','"+a0000s+"')");
			this.closeCueWindow("warnWin");
			}
		}else{
			List<HashMap<String,Object>> gridList = this.getPageElement("persongrid3").getValueList();
			if(gridList == null || gridList.isEmpty()){
				this.setMainMessage("无可查看人员");
			}else{
			for(HashMap<String,Object> gridMap : gridList){
				Object obj = gridMap.get("a0000");
				if(obj!=null){
					sb = sb.append(obj.toString()+",");
				}
			}
			String a0000s = sb.substring(0, sb.length()-1);
			this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('view','"+a0000s+"')");
			this.closeCueWindow("warnWin");
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	public static int getSyDayCount() {
		return syDayCount;
	}

	public static void setSyDayCount(int syDayCount) {
		WarnWindowPageModel.syDayCount = syDayCount;
	}

	public static int getBirthDaycount() {
		return birthDaycount;
	}

	public static void setBirthDaycount(int birthDaycount) {
		WarnWindowPageModel.birthDaycount = birthDaycount;
	}
	
	public void closeCueWindow(String windowId){
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('"+windowId+"').close();");
	}
	
	/**
	 * 保存列表
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 * @throws UnsupportedEncodingException 
	 * @throws SQLException 
	 * @throws SerialException 
	 */
	@PageEvent("dataSave.onclick")
	@NoRequiredValidate
	@Transaction
	public int dataSave() throws UnsupportedEncodingException, SerialException, AppException, SQLException, RadowException{
		String saveName = this.request.getSession().getAttribute("listName_swtx").toString();
		String sql = this.request.getSession().getAttribute("sql_swtx").toString();
		
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		sql = "select a0000 from ("+sql+") a";
		cbBs.saveSWTXList(saveName, "", "", loginName,sql);
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
