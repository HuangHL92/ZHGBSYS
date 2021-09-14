package com.insigma.siis.local.pagemodel.fxyp;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;


import org.apache.commons.lang.StringUtils;

import com.fr.report.core.A.p;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;

import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Customquery;
import com.insigma.siis.local.business.entity.Percentage;

import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.customquery.GroupPageBS;
import com.utils.CountCSBySql;
import com.utils.DBUtils;
import com.utils.CountCSBySql.keyWordSet;
import com.utils.StrUtils;

import net.sf.json.JSONObject;

public class QxypSetContitionPageModel extends PageModel {
	private final static List<String> alist = Arrays.asList("6", "7", "8", "9");// 用于比较最高学历

	@Override
	@AutoNoMask
	public int doInit() throws RadowException {

		this.setNextEventName("setContionQuery");
		return 0;
	}
	
	@PageEvent("setContionQuery")
	public int setContionQuery() throws RadowException {
		this.controlButton();
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyyMM");
		String datestr = myFmt1.format(new Date());
		this.getPageElement("jiezsj").setValue(datestr);
		this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4) + "." + datestr.substring(4, 6));

		Calendar c = new GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (int i = 0; i < 80; i++) {
			map.put("" + (year - i), year - i);
		}

		String tp00=this.getPageElement("tp00").getValue();
		String sql="select * from QxypContition where tp00='"+tp00+"'";
		
		List<HashMap<String, Object>> list=null;
		try{
			CommQuery commQuery =new CommQuery();
			list = commQuery.getListBySQL(sql); 
			if(list!=null&&list.size()>0) {
				HashMap<String, Object> mapCon=list.get(0);
				this.getPageElement("a0101").setValue(isnull(mapCon.get("a0101")));
				this.getPageElement("a0165A").setValue(isnull(mapCon.get("a0165a")));
				this.getPageElement("a0165B").setValue(isnull(mapCon.get("a0165b")));
				this.getPageElement("a0165C").setValue(isnull(mapCon.get("a0165c")));
				this.getPageElement("a0104").setValue(isnull(mapCon.get("a0104")));
				this.getPageElement("a0117").setValue(isnull(mapCon.get("a0117")));
				
				this.getPageElement("a0141").setValue(isnull(mapCon.get("a0141")));
				this.getPageElement("a0141_combotree").setValue(codevalueFY(isnull(mapCon.get("a0141")),"GB4762"));
				
				this.getPageElement("ageA").setValue(isnull(mapCon.get("agea")));
				this.getPageElement("ageB").setValue(isnull(mapCon.get("ageb")));
				if(mapCon.get("a0107a")!=null&&!"".equals(mapCon.get("a0107a"))) {
					this.getPageElement("a0107A").setValue(isnull(mapCon.get("a0107a")));
					this.getPageElement("a0107A_1").setValue(isnull(mapCon.get("a0107a")).substring(0, 4) + "." + isnull(mapCon.get("a0107a")).substring(4, 6));
				}
				if(mapCon.get("a0107b")!=null&&!"".equals(mapCon.get("a0107b"))) {
					this.getPageElement("a0107B").setValue(isnull(mapCon.get("a0107b")));
					this.getPageElement("a0107B_1").setValue(isnull(mapCon.get("a0107b")).substring(0, 4) + "." + isnull(mapCon.get("a0107b")).substring(4, 6));
				}
				this.getPageElement("qrz").setValue(isnull(mapCon.get("qrz")));
				this.getPageElement("xlxw").setValue(isnull(mapCon.get("xlxw")));
				
				if(mapCon.get("a0192fa")!=null&&!"".equals(mapCon.get("a0192fa"))) {
					this.getPageElement("a0192fA").setValue(isnull(mapCon.get("a0192fa")));
					this.getPageElement("a0192fA_1").setValue(isnull(mapCon.get("a0192fa")).substring(0, 4) + "." + isnull(mapCon.get("a0192fa")).substring(4, 6));
				}
				if(mapCon.get("a0192fb")!=null&&!"".equals(mapCon.get("a0192fb"))) {
					this.getPageElement("a0192fB").setValue(isnull(mapCon.get("a0192fb")));
					this.getPageElement("a0192fB_1").setValue(isnull(mapCon.get("a0192fb")).substring(0, 4) + "." + isnull(mapCon.get("a0192fb")).substring(4, 6));
				}
				
				this.getPageElement("a0221A").setValue(isnull(mapCon.get("a0221a")));
				this.getPageElement("a0221A_combotree").setValue(codevalueFY(isnull(mapCon.get("a0221a")),"ZB09"));

				if(mapCon.get("a0288a")!=null&&!"".equals(mapCon.get("a0288a"))) {
					this.getPageElement("a0288A").setValue(isnull(mapCon.get("a0288a")));
					this.getPageElement("a0288A_1").setValue(isnull(mapCon.get("a0288a")).substring(0, 4) + "." + isnull(mapCon.get("a0288a")).substring(4, 6));
				}
				if(mapCon.get("a0288b")!=null&&!"".equals(mapCon.get("a0288b"))) {
					this.getPageElement("a0288B").setValue(isnull(mapCon.get("a0288b")));
					this.getPageElement("a0288B_1").setValue(isnull(mapCon.get("a0288b")).substring(0, 4) + "." + isnull(mapCon.get("a0288b")).substring(4, 6));
				}
				this.getPageElement("a0192e").setValue(isnull(mapCon.get("a0192e")));
				this.getPageElement("a0192e_combotree").setValue(codevalueFY(isnull(mapCon.get("a0192e")),"ZB148"));
				
				
				if(mapCon.get("a0192ca")!=null&&!"".equals(mapCon.get("a0192ca"))) {
					this.getPageElement("a0192cA").setValue(isnull(mapCon.get("a0192ca")));
					this.getPageElement("a0192cA_1").setValue(isnull(mapCon.get("a0192ca")).substring(0, 4) + "." + isnull(mapCon.get("a0192ca")).substring(4, 6));
				}
				if(mapCon.get("a0192cb")!=null&&!"".equals(mapCon.get("a0192cb"))) {
					this.getPageElement("a0192cB").setValue(isnull(mapCon.get("a0192cb")));
					this.getPageElement("a0192cB_1").setValue(isnull(mapCon.get("a0192cb")).substring(0, 4) + "." + isnull(mapCon.get("a0192cb")).substring(4, 6));
				}
				
				this.getPageElement("a0194z").setValue(isnull(mapCon.get("a0194z")));
				this.getPageElement("a0194c").setValue(isnull(mapCon.get("a0194c")));
				this.getPageElement("a0194c_combotree").setValue(codevalueFY(isnull(mapCon.get("a0194c")),"ATTR_LRZW"));
				this.getPageElement("a0188").setValue(isnull(mapCon.get("a0188")));
				this.getPageElement("a0132").setValue(isnull(mapCon.get("a0132")));
				this.getPageElement("a0133").setValue(isnull(mapCon.get("a0133")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	@AutoNoMask
	public int initX() throws RadowException {
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyyMM");
		// PageElement pe= this.getPageElement("isOnDuty");
		// pe.setValue("1");
		String datestr = myFmt1.format(new Date());
		this.getPageElement("jiezsj").setValue(datestr);
		this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4) + "." + datestr.substring(4, 6));

		Calendar c = new GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (int i = 0; i < 80; i++) {
			map.put("" + (year - i), year - i);
		}
//		((Combo) this.getPageElement("a1521")).setValueListForSelect(map);

		return 0;
	}
	

	@PageEvent("clearReset")
	public int clearReset() throws RadowException {
		this.getPageElement("a0101").setValue("");
		this.getPageElement("a0165A").setValue("");
		this.getPageElement("a0165B").setValue("");
		this.getPageElement("a0165C").setValue("");
		this.getPageElement("a0165A_combo").setValue("");
		this.getPageElement("a0165B_combo").setValue("");
		this.getPageElement("a0165C_combo").setValue("");
		this.getExecuteSG().addExecuteCode("$('#a01040').click();");
		this.getExecuteSG().addExecuteCode("$('#a01170').click();");
		/* this.getExecuteSG().addExecuteCode("$('#a01410').click();"); */
		this.getPageElement("a0141").setValue("");
		this.getPageElement("a0141_combotree").setValue("");
		this.getPageElement("ageA").setValue("");
		this.getPageElement("ageB").setValue("");
		this.getPageElement("a0107A").setValue("");
		this.getPageElement("a0107B").setValue("");
		this.getPageElement("jiezsj").setValue("");
		this.getPageElement("a0192fA").setValue("");
		this.getPageElement("a0192fB").setValue("");
		this.getPageElement("a0221A").setValue("");
		this.getPageElement("a0221A_combotree").setValue("");
		this.getPageElement("xlxw").setValue("");
		this.getPageElement("xlxw_combo").setValue("");
		this.getPageElement("a0288A").setValue("");
		this.getPageElement("a0288B").setValue("");
		this.getPageElement("a0192e").setValue("");
		this.getPageElement("a0192e_combotree").setValue("");
		this.getPageElement("a0192cA").setValue("");
		this.getPageElement("a0192cB").setValue("");
		this.getPageElement("a0194z").setValue("");
		this.getPageElement("a0194z_combo").setValue("");
		this.getPageElement("a0194c").setValue("");
		this.getPageElement("a0194c_combotree").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	/**
	 * 保存班子研判条件
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("mQueryonclick")
	public int query() throws RadowException, AppException {
		String userID = SysManagerUtils.getUserId();

		String b01String = "";

		StringBuffer a02_a0201b_sb = new StringBuffer("");
		StringBuffer cu_b0111_sb = new StringBuffer("");

		if (b01String != null && !"".equals(b01String)) {// tree!=null && !"".equals(tree.trim()

		}
//		this.setMainMessage("保存成功！");
//		this.setNextEventName("rclick");
		return newquery(cu_b0111_sb, a02_a0201b_sb, userID, "1");

	}

	@SuppressWarnings("static-access")
	private int newquery(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, String userID, String tableType)
			throws RadowException, AppException {
		String finalsql = getSQL(cu_b0111_sb, a02_a0201b_sb, userID); // 拼接页面sql
		this.getExecuteSG().addExecuteCode("collapseGroupWin();");

		return EventRtnType.NORMAL_SUCCESS;
	}


	private String getSQL(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, String userID)
			throws AppException, RadowException {
		StringBuffer a01sb = new StringBuffer("");
		StringBuffer a02sb = new StringBuffer("");
		StringBuffer orther_sb = new StringBuffer("");
		String sid = this.request.getSession().getId();
		String querydb = "";
		String tp00=this.getPageElement("tp00").getValue();
		boolean isgbk = true;
		String finalsql="";
		//if()//原来有存放查询的先删除
		HBSession sess = HBUtil.getHBSession();
		try {

		Statement stmt = sess.connection().createStatement();
		String delsql="delete from QxypContition where tp00='"+tp00+"'";
		 stmt.executeUpdate(delsql);
		
		String sql_bf=" insert into QxypContition(contitionid,tp00";
		String sql_af="('"+UUID.randomUUID().toString().replaceAll("-", "")+"','"+tp00+"'";
		// 人员姓名
		String a0101 = this.getPageElement("a0101").getValue();
		if (!"".equals(a0101)) {
			a01sb.append(" and ");
			a01sb.append("a01.a0101 like '" + a0101 + "%'");
			sql_bf=sql_bf+",a0101";
			sql_af=sql_af+",'"+a0101+"'";
		}
		
		// 省管干部
		String a0165A = this.getPageElement("a0165A").getValue();
		if(a0165A!=null&&!"".equals(a0165A)) {
			String[] strs = a0165A.split(",");
			a01sb.append(" and (");
			for(int i=0;i<strs.length;i++) {
				a01sb.append(" a01.a0165 like '%" + strs[i] + "%' ");
				if(i!=strs.length-1) {
					a01sb.append(" or ");
				}
			}
			a01sb.append(") ");
			sql_bf=sql_bf+",a0165A";
			sql_af=sql_af+",'"+a0165A+"'";
		}
		
		// 市管干部
		String a0165B = this.getPageElement("a0165B").getValue();
		if(a0165B!=null&&!"".equals(a0165B)) {
			String[] strs = a0165B.split(",");
			a01sb.append(" and (");
			for(int i=0;i<strs.length;i++) {
				a01sb.append(" a01.a0165 like '%" + strs[i] + "%' ");
				if(i!=strs.length-1) {
					a01sb.append(" or ");
				}
			}
			a01sb.append(") ");
			sql_bf=sql_bf+",a0165B";
			sql_af=sql_af+",'"+a0165B+"'";
		}
		
		// 处级（中层）干部
		String a0165C = this.getPageElement("a0165C").getValue();
		if(a0165C!=null&&!"".equals(a0165C)) {
			String[] strs = a0165C.split(",");
			a01sb.append(" and (");
			for(int i=0;i<strs.length;i++) {
				a01sb.append(" a01.a0165 like '%" + strs[i] + "%' ");
				if(i!=strs.length-1) {
					a01sb.append(" or ");
				}
			}
			a01sb.append(") ");
			sql_bf=sql_bf+",a0165C";
			sql_af=sql_af+",'"+a0165C+"'";
		}
		
		// 性别
		String a0104 = this.getPageElement("a0104").getValue();
		if(a0104!=null&&!"".equals(a0104)) {
			a01sb.append(" and (");
			if("0".equals(a0104)) {
				a01sb.append(" a01.a0104 is not null ");
			}else{
				a01sb.append(" a01.a0104 = '" + a0104 + "' ");
			}
			a01sb.append(") ");
			sql_bf=sql_bf+",a0104";
			sql_af=sql_af+",'"+a0104+"'";
		}
		
		// 民族
		String a0117 = this.getPageElement("a0117").getValue();
		if(a0117!=null&&!"".equals(a0117)) {
			a01sb.append(" and (");
			if("0".equals(a0117)) {
				a01sb.append(" a01.a0117 is not null ");
			}else if("1".equals(a0117)){
				a01sb.append(" a01.a0117 = '01' ");
			}else if("2".equals(a0117)){
				a01sb.append(" a01.a0117 <> '01' ");
			}
			a01sb.append(") ");
			sql_bf=sql_bf+",a0117";
			sql_af=sql_af+",'"+a0117+"'";
		}
		
		// 党派
		String a0141 = this.getPageElement("a0141").getValue();// 党派
		if (a0141!=null&&!"".equals(a0141)) {
			String[] strs = a0141.split(",");
			a01sb.append(" and (");
			for(int i=0;i<strs.length;i++) {
				a01sb.append(" a01.a0141 = '"+strs[i]+"' ");
				if(i!=strs.length-1) {
					a01sb.append(" or ");
				}
			}
			a01sb.append(") ");
			sql_bf=sql_bf+",a0141";
			sql_af=sql_af+",'"+a0141+"'";
		}

		//年龄
		String ageA = this.getPageElement("ageA").getValue();// 年龄
		String ageB = this.getPageElement("ageB").getValue();// 年龄
		if (!"".equals(ageB) && StringUtils.isNumeric(ageB)) {// 是否数字
			ageB = Integer.valueOf(ageB) + 1 + "";
		}
		String jiezsj = this.getPageElement("jiezsj").getValue();// 截止时间
		String dateEnd = GroupPageBS.getDateformY(ageA, jiezsj);
		String dateStart = GroupPageBS.getDateformY(ageB, jiezsj);
		if (!"".equals(dateEnd) && !"".equals(dateStart) && dateEnd.compareTo(dateStart) < 0) {
			throw new AppException("年龄范围错误！");
		}
		if (!"".equals(ageA)) {
			a01sb.append(" and substr("+jiezsj+"-substr(a01.a0107,1,6),1,2)>=lpad('" + ageA + "',2,'0')");
			sql_bf=sql_bf+",ageA";
			sql_af=sql_af+",'"+ageA+"'";
		}
		if (!"".equals(ageB)) {
			a01sb.append(" and substr("+jiezsj+"-substr(a01.a0107,1,6),1,2)<lpad('" + ageB + "',2,'0')");
			sql_bf=sql_bf+",ageB";
			sql_af=sql_af+",'"+ageB+"'";
		}
		
		// 出生年月
		String a0107A = this.getPageElement("a0107A").getValue();
		String a0107B = this.getPageElement("a0107B").getValue();// 出生年月
		if (!"".equals(a0107A)) {
			a01sb.append(" and a01.a0107>='" + a0107A + "'");
			sql_bf=sql_bf+",a0107A";
			sql_af=sql_af+",'"+a0107A+"'";
		}
		if (!"".equals(a0107B)) {
			a01sb.append(" and a01.a0107<='" + a0107B + "'");
			sql_bf=sql_bf+",a0107B";
			sql_af=sql_af+",'"+a0107B+"'";
		}
		
		//学历学位
		String xlxw = this.getPageElement("xlxw").getValue();
		if(xlxw!=null&&!"".equals(xlxw)) {
			String[] strs = xlxw.split(",");
			a01sb.append(" and (");
			
			String qrz = this.getPageElement("qrz").getValue();
			sql_bf=sql_bf+",qrz";
			sql_af=sql_af+",'"+qrz+"'";
			for(int i=0;i<strs.length;i++) {
				if(qrz!=null&&!"".equals(qrz)&&"1".equals(qrz)) {
					a01sb.append(" a01.qrzxl like '%" + strs[i] + "%' or a01.qrzxw like '%" + strs[i] + "%' ");
				}else {
					a01sb.append(" a01.zgxl like '%" + strs[i] + "%' or a01.zgxw like '%" + strs[i] + "%' ");
				}
				if(i!=strs.length-1) {
					a01sb.append(" or ");
				}
			}
			
			a01sb.append(") ");
			sql_bf=sql_bf+",xlxw";
			sql_af=sql_af+",'"+xlxw+"'";
		}
		
		//现职务时间
		String a0192fA = this.getPageElement("a0192fA").getValue();// 现职务时间
		String a0192fB = this.getPageElement("a0192fB").getValue();// 现职务时间
		if (!"".equals(a0192fA)) { 
			a01sb.append(" and a01.a0192f >='" + a0192fA + "'");
			sql_bf=sql_bf+",a0192fA";
			sql_af=sql_af+",'"+a0192fA+"'";
		} 
		if (!"".equals(a0192fB)) { 
			a01sb.append(" and a01.a0192f <='" + a0192fB + "'");
			sql_bf=sql_bf+",a0192fB";
			sql_af=sql_af+",'"+a0192fB+"'";
		}
		
		//职务层次
		String a0221A = this.getPageElement("a0221A").getValue();// 职务层次
		if (!"".equals(a0221A)) {
			a01sb.append(" and a01.a0221 in('" + (a0221A.replace(",", "','")) + "')");
			sql_bf=sql_bf+",a0221A";
			sql_af=sql_af+",'"+a0221A+"'";
		}
		
		//职务层次时间
		String a0288A = this.getPageElement("a0288A").getValue();// 任现职务层次时间
		String a0288B = this.getPageElement("a0288B").getValue();// 任现职务层次时间
		if (!"".equals(a0288A)) { 
			a01sb.append(" and a01.a0288>='" + a0288A + "'"); 
			sql_bf=sql_bf+",a0288A";
			sql_af=sql_af+",'"+a0288A+"'";
		} 
		if (!"".equals(a0288B)) { 
			a01sb.append(" and a01.a0288<='" + a0288B + "'"); 
			sql_bf=sql_bf+",a0288B";
			sql_af=sql_af+",'"+a0288B+"'";
		}
		
		// 现职级
		String a0192e = this.getPageElement("a0192e").getValue();
		if (a0192e!=null&&!"".equals(a0192e)) {
			String[] strs = a0192e.split(",");
			a01sb.append(" and (");
			for(int i=0;i<strs.length;i++) {
				a01sb.append(" a01.a0192e = '"+strs[i]+"' ");
				if(i!=strs.length-1) {
					a01sb.append(" or ");
				}
			}
			a01sb.append(") ");
			sql_bf=sql_bf+",a0192e";
			sql_af=sql_af+",'"+a0192e+"'";
		}

		// 任职级时间
		String a0192cA = this.getPageElement("a0192cA").getValue();
		String a0192cB = this.getPageElement("a0192cB").getValue();
		if (!"".equals(a0192cA)) {
			a01sb.append(" and a01.a0192c>='" + a0192cA + "'");
			sql_bf=sql_bf+",a0192cA";
			sql_af=sql_af+",'"+a0192cA+"'";
		}
		if (!"".equals(a0192cB)) {
			a01sb.append(" and a01.a0192c<='" + a0192cB + "'");
			sql_bf=sql_bf+",a0192cB";
			sql_af=sql_af+",'"+a0192cB+"'";
		}
		
		//专业类型
		String a0194z = this.getPageElement("a0194z").getValue();
		if(a0194z!=null&&!"".equals(a0194z)) { 
			String[] strs = a0194z.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from attr_lrzw where 1=2 or ");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append(" "+strs[i]+" = '1' ");
				if(i!=strs.length-1) {
					orther_sb.append(" or ");
				}
			}
			orther_sb.append(") ");
			sql_bf=sql_bf+",a0194z";
			sql_af=sql_af+",'"+a0194z+"'";
		}
		
		//重要任职经历
		String a0194c = this.getPageElement("a0194c").getValue();
		if(a0194c!=null&&!"".equals(a0194c)) {
			String[] strs = a0194c.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from attr_lrzw where 1=2 or ");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append(" "+strs[i]+" = '1' ");
				if(i!=strs.length-1) {
					orther_sb.append(" or ");
				}
			}
			orther_sb.append(") ");
			sql_bf=sql_bf+",a0194c";
			sql_af=sql_af+",'"+a0194c+"'";
		}
		//具有乡镇（街道）党政正职经历
		String a0188 = this.getPageElement("a0188").getValue();
		if(a0188!=null&&!"".equals(a0188)&&"1".equals(a0188)) {
			orther_sb.append(" and a01.a0188='1' ");
			sql_bf=sql_bf+",a0188";
			sql_af=sql_af+",'"+a0188+"'";
		}
		//现任乡镇（街道）党（工）委书记
		String a0132 = this.getPageElement("a0132").getValue();
		if(a0132!=null&&!"".equals(a0132)&&"1".equals(a0132)) {
			orther_sb.append(" and a01.a0132='1' ");
			sql_bf=sql_bf+",a0132";
			sql_af=sql_af+",'"+a0132+"'";
		}
		//现任乡镇（街道）镇长（主任）
		String a0133 = this.getPageElement("a0133").getValue();
		if(a0133!=null&&!"".equals(a0133)&&"1".equals(a0133)) {
			orther_sb.append(" and a01.a0133='1' ");
			sql_bf=sql_bf+",a0133";
			sql_af=sql_af+",'"+a0133+"'";
		}
		
		String a0163 = this.getPageElement("a0163").getValue();// 人员状态
		String qtxzry = "0";

		
		finalsql = CommSQL.getCondiQuerySQL(userID, a01sb, "", a02sb, a02_a0201b_sb, cu_b0111_sb, orther_sb, a0163,
				qtxzry, sid, isgbk, querydb);
		
		sql_bf=sql_bf+",finsql";
		sql_af=sql_af+",'"+finalsql.replace("a01.a0000, a01.a1701", "a01.a0000,a01.a0101,a01.a0104,substr(a01.a0107,1,4)||'.'||substr(a01.a0107,5,2) a0107,a01.a0221,substr(a01.a0288,1,4)||'.'||substr(a01.a0288,5,2) a0288,a01.a0192a").replaceAll("'", "''")+"'";
		
		String insertSql=sql_bf+") values "+sql_af+")";
		System.out.println("1111111111:长度："+insertSql.length()+";2222222"+insertSql);
		
			stmt.executeUpdate(insertSql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return finalsql;
	}

	CustomQueryBS cbBs = new CustomQueryBS();

	

	/**
	 * 清除条件
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("clearCon.onclick")
	@NoRequiredValidate
	public int clearCon() throws RadowException, AppException {
		String a = this.getPageElement("female").getValue();
		this.getPageElement("a0160").setValue("");
		this.getPageElement("a0160_combo").setValue(""); // 人员类别-fujun
		this.getPageElement("a0101A").setValue("");
		this.getPageElement("a0184A").setValue("");
		this.getPageElement("a0163").setValue("");
		this.getPageElement("a0163_combo").setValue(""); // 人员状态-fujun
		this.getPageElement("ageA").setValue("");
		this.getPageElement("age1").setValue("");
		this.getPageElement("female").setValue("0");
		;
		this.getPageElement("minority").setValue("0");
		this.getPageElement("nonparty").setValue("0");
		this.getPageElement("duty").setValue("");
		this.getPageElement("duty_combo").setValue(""); // 职务层次 ——fujun
		this.getPageElement("duty1").setValue("");
		this.getPageElement("duty1_combo").setValue(""); // 职务层次 ——fujun
		this.getPageElement("dutynow").setValue("");
		this.getPageElement("dutynow_1").setValue(""); // 任现职务层次时间 ——fujun
		this.getPageElement("dutynow1").setValue("");
		this.getPageElement("dutynow1_1").setValue(""); // 任现职务层次时间 ——fujun
		this.getPageElement("a0219").setValue("");
		this.getPageElement("a0219_combo").setValue(""); // 职务类别——fujun
		this.getPageElement("edu").setValue("");
		this.getPageElement("edu_combo").setValue(""); // 学历——fujun
		this.getPageElement("edu1").setValue("");
		this.getPageElement("edu1_combo").setValue(""); // 学历——fujun
		this.getPageElement("allday").setValue("0");
		/*
		 * this.getPageElement("SysOrgTree").setValue("");
		 * this.getPageElement("SysOrgTreeIds").setValue("");
		 */
		this.getPageElement("a0221aS").setValue("");// 职务等级
		this.getPageElement("a0221aS_combo").setValue(""); // 职务等级——fujun
		this.getPageElement("a0221aE").setValue("");// 职务等级
		this.getPageElement("a0221aE_combo").setValue(""); // 职务等级——fujun

		this.getPageElement("a0192dS").setValue("");// 职务职级
		this.getPageElement("a0192dS_combo").setValue(""); // 职务职级——fujun
		this.getPageElement("a0192dE").setValue("");// 职务职级
		this.getPageElement("a0192dE_combo").setValue(""); // 职务职级——fujun

		return EventRtnType.NORMAL_SUCCESS;
	}

	

	/**
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start, int limit) throws RadowException {
		String sql = "select * from Customquery t   order by createtime desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	// 回显
	@PageEvent("rclick")
	public int doMemberrowclick(String queryid) throws RadowException {
		// String
		// queryid=this.getPageElement("memberGrid").getValue("queryid",0).toString();

		try {
			Customquery query = (Customquery) HBUtil.getHBSession().get(Customquery.class, queryid);
			String conds = query.getQuerycond();
			Map<String, String> condMap = JSONObject.fromObject(conds);

			StringBuffer remark = new StringBuffer();
			// 机构填写，先进行SysOrgTreeIds，机构树的回显暂时不实现
			this.getPageElement("SysOrgTreeIds").setValue(condMap.get("b01String"));

			//姓名
			this.getPageElement("a0101").setValue(condMap.get("a0101"));// 人员姓名
			if (condMap.get("a0101") != null && !"".equals(condMap.get("a0101"))) {
				remark.append("  姓名为: " + condMap.get("a0101"));
			}
			
			//人员状态
			this.getPageElement("a0163").setValue("1");
			
			//省管干部
			this.getPageElement("a0165A").setValue(condMap.get("a0165A"));
			if (condMap.get("a0165A") != null && !"".equals(condMap.get("a0165A"))) {
				String[] arr = condMap.get("a0165A").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ZB130", arr[i]) + ";";
				}
				remark.append(" 省管干部类别包含 :" + str);
			}
			//市管干部
			this.getPageElement("a0165B").setValue(condMap.get("a0165B"));
			if (condMap.get("a0165B") != null && !"".equals(condMap.get("a0165B"))) {
				String[] arr = condMap.get("a0165B").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ZB130", arr[i]) + ";";
				}
				remark.append(" 市管干部类别包含 :" + str);
			}
			//处级（中层）干部
			this.getPageElement("a0165C").setValue(condMap.get("a0165C"));
			if (condMap.get("a0165C") != null && !"".equals(condMap.get("a0165C"))) {
				String[] arr = condMap.get("a0165C").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ZB130", arr[i]) + ";";
				}
				remark.append(" 处级（中层）干部类别包含 :" + str);
			}
			
			//性别
			this.getPageElement("a0104").setValue(condMap.get("a0104"));
			if (!"".equals(condMap.get("a0104"))&&!"0".equals(condMap.get("a0104"))) {
				remark.append("  性别为:  " + HBUtil.getCodeName("GB2261", condMap.get("a0104")));
			}

			//民族
			this.getPageElement("a0117").setValue(condMap.get("a0117"));
			if (!"".equals(condMap.get("a0117"))&&!"0".equals(condMap.get("a0117"))) {
				remark.append("  民族为:  " + HBUtil.getCodeName("GB3304", condMap.get("a0117")));

			}
			
			//党派
			this.getPageElement("a0141").setValue(condMap.get("a0141"));
			if (condMap.get("a0141") != null && !"".equals(condMap.get("a0141"))) {
				String[] arr = condMap.get("a0141").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("GB4762", arr[i]) + ";";
				}
				remark.append(" 党派包含 :" + str);
			}
			
			// 年龄
			this.getPageElement("ageA").setValue(condMap.get("ageA"));// 年龄
			this.getPageElement("ageB").setValue(condMap.get("ageB"));// 年龄
			if (condMap.get("ageA")!=null&&!"".equals(condMap.get("ageB")) && !"".endsWith(condMap.get("ageA"))) {
				remark.append("  年龄   " + condMap.get("ageA") + "到" + condMap.get("ageB"));

			}
			if (!"".equals(condMap.get("ageA")) && "".equals(condMap.get("ageB"))) {
				remark.append("  年龄大于等于   " + condMap.get("ageA"));
			}
			if (!"".equals(condMap.get("ageB")) && "".equals(condMap.get("ageA"))) {
				remark.append("  年龄小于 " + condMap.get("ageB"));
			}
			
			// 出生年月
			this.getPageElement("a0107A").setValue(condMap.get("a0107A"));// 出生年月
			this.getPageElement("a0107B").setValue(condMap.get("a0107B"));// 出生年月
			if (condMap.get("a0107B")!=null&&!"".equals(condMap.get("a0107A")) && !"".endsWith(condMap.get("a0107B"))) {
				remark.append("  出生年月 在    " + condMap.get("a0107A") + "  ," + condMap.get("a0107B") + "之间");

			}
			if (!"".equals(condMap.get("a0107A")) && "".equals(condMap.get("a0107B"))) {
				remark.append("  出生年月大于等于    " + condMap.get("a0107A"));
			}
			if (!"".equals(condMap.get("a0107B")) && "".equals(condMap.get("a0107A"))) {
				remark.append("  出生年月小于     " + condMap.get("a0107B"));
			}

			//学历学位
			this.getPageElement("xlxw").setValue(condMap.get("xlxw"));
			if (condMap.get("xlxw")!=null&&!"".equals(condMap.get("xlxw"))) {
				String[] arr = condMap.get("xlxw").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + arr[i] + ";";
				}
				remark.append(" 学历学位为:" + str);
			}
			
			//现职务时间
			this.getPageElement("a0192fA").setValue(condMap.get("a0192fA"));// 现职务时间
			this.getPageElement("a0192fB").setValue(condMap.get("a0192fB"));// 现职务时间
			if (condMap.get("a0192fB")!=null&&!"".equals(condMap.get("a0192fA")) && !"".endsWith(condMap.get("a0192fB"))) {
				remark.append("  现职务时间 在    " + condMap.get("a0192fA") + "  ," + condMap.get("a0192fB") + "之间");

			}
			if (!"".equals(condMap.get("a0192fA")) && "".equals(condMap.get("a0192fB"))) {
				remark.append("  现职务时间大于等于    " + condMap.get("a0192fA"));
			}
			if (!"".equals(condMap.get("a0192fB")) && "".equals(condMap.get("a0192fA"))) {
				remark.append("  现职务时间小于     " + condMap.get("a0192fB"));
			}
			
			this.getPageElement("a0221A").setValue(condMap.get("a0221A"));// 职务层次 暂时不回显
			if (condMap.get("a0221A")!=null&&!"".equals(condMap.get("a0221A"))) {
				String[] arr = condMap.get("a0221A").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ZB09", arr[i]) + ";";
				}
				remark.append(" 职务层次包含 :" + str);
			}

			this.getPageElement("a0192e").setValue(condMap.get("a0192e"));// 现职级
			if (condMap.get("a0192e") != null && !"".equals(condMap.get("a0192e"))) {
				String[] arr = condMap.get("a0192e").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ZB148", arr[i]) + ";";
				}
				remark.append(" 现职级包含 :" + str);
			}

			this.getPageElement("a0192cA").setValue(condMap.get("a0192cA"));// 任职级时间
			this.getPageElement("a0192cB").setValue(condMap.get("a0192cB"));// 任职级时间
			if (condMap.get("a0192cB")!=null&&!"".equals(condMap.get("a0192cA")) && !"".endsWith(condMap.get("a0192cB"))) {
				remark.append("  任职级时间在  " + condMap.get("a0192cA") + "  ," + condMap.get("a0192cB") + "之间");

			}
			if (!"".equals(condMap.get("a0192cA")) && "".equals(condMap.get("a0192cB"))) {
				remark.append(" 任职级时间大于等于   " + condMap.get("a0192cA"));
			}
			if (!"".equals(condMap.get("a0192cB")) && "".equals(condMap.get("a0192cA"))) {
				remark.append("  任职级时间小于  " + condMap.get("a0192cB"));
			}

			//熟悉领域
			this.getPageElement("a0194z").setValue(condMap.get("a0194z"));
			if (condMap.get("a0194z") != null && !"".equals(condMap.get("a0194z"))) {
				String[] arr = condMap.get("a0194z").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("EXTRA_TAGS", arr[i]) + ";";
				}
				remark.append(" 熟悉领域包含 :" + str);
			}
			
			//重要任职经历
			this.getPageElement("a0194c").setValue(condMap.get("a0194c"));
			if (condMap.get("a0194c") != null && !"".equals(condMap.get("a0194c"))) {
				String[] arr = condMap.get("a0194c").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ATTR_LRZW", arr[i]) + ";";
				}
				remark.append(" 重要任职经历包含 :" + str);
			}

			// 最高学历
			this.getPageElement("xla0801bv").setValue(condMap.get("xla0801b"));// 最高学历 学历代码
			this.getExecuteSG().addExecuteCode("setCheckBox('xla0801b','" + condMap.get("xla0801b") + "');");
			this.getPageElement("xla0814").setValue(condMap.get("xla0814"));// 毕业院校
			this.getPageElement("xla0824").setValue(condMap.get("xla0824"));// 专业
			if (condMap.get("xla0801b")!=null&&!"".equals(condMap.get("xla0801b"))) {
				String[] crr = condMap.get("xla0801b").split(",");
				String str = "";
				String a = " ";
				for (int i = 0; i < crr.length; i++) {
					if (alist.contains(crr[i])) {
						a = " 其他 ; ";
					}
					if ("1".equals(crr[i])) {
						str = str + " 研究生 ; ";
					} else if ("2".equals(crr[i])) {
						str = str + " 大学 ; ";
					} else if ("3".equals(crr[i])) {
						str = str + " 大专 ; ";
					} else {
						str = str + " 中专 ; ";
					}

				}
				remark.append(" 最高学历代码包含:" + str + a);
			}
			if (!"".equals(condMap.get("xla0814"))) {

				remark.append("  最高学历毕业院校: " + condMap.get("xla0814"));
			}
			if (!"".equals(condMap.get("xla0824"))) {

				remark.append("  最高学历专业: " + condMap.get("xla0824"));
			}

			// 最高学位
			this.getPageElement("xwa0901bv").setValue(condMap.get("xwa0901b"));// 最高学历 学历代码
			this.getExecuteSG().addExecuteCode("setCheckBox('xwa0901b','" + condMap.get("xwa0901b") + "');");
			this.getPageElement("xwa0814").setValue(condMap.get("xwa0814"));// 毕业院校
			this.getPageElement("xwa0824").setValue(condMap.get("xwa0824"));// 专业

			if (condMap.get("xwa0901b")!=null&&!"".equals(condMap.get("xwa0901b"))) {
				String[] crr = condMap.get("xwa0901b").split(",");
				String str = "";
				String a = "";
				for (int i = 0; i < crr.length; i++) {
					if ("1".equals(crr[i]) || "2".equals(crr[i])) {
						a = " 博士 ; ";
					} else if ("2".equals(crr[i])) {
						str = str + " 硕士 ; ";
					} else {
						str = str + " 学士 ; ";
					}

				}
				remark.append(" 最高学位包含:" + a + str);
			}

			if (!"".equals(condMap.get("xwa0814"))) {

				remark.append("  最高学位毕业院校: " + condMap.get("xwa0814"));
			}
			if (!"".equals(condMap.get("xwa0824"))) {

				remark.append("  最高学位专业: " + condMap.get("xwa0824"));
			}

			// 全日制最高学历
			this.getPageElement("qrzxla0801bv").setValue(condMap.get("qrzxla0801b"));// 最高学历 学历代码
			this.getExecuteSG().addExecuteCode("setCheckBox('qrzxla0801b','" + condMap.get("qrzxla0801b") + "');");
			this.getPageElement("qrzxla0814").setValue(condMap.get("qrzxla0814"));// 毕业院校
			this.getPageElement("qrzxla0824").setValue(condMap.get("qrzxla0824"));// 专业

			if (condMap.get("qrzxla0801b")!=null&&!"".equals(condMap.get("qrzxla0801b"))) {
				String[] crr = condMap.get("qrzxla0801b").split(",");
				String str = "";
				String a = " ";
				for (int i = 0; i < crr.length; i++) {
					if (alist.contains(crr[i])) {
						a = " 其他 ; ";
					}
					if ("1".equals(crr[i])) {
						str = str + " 研究生 ; ";
					} else if ("2".equals(crr[i])) {
						str = str + " 大学 ; ";
					} else if ("3".equals(crr[i])) {
						str = str + " 大专 ; ";
					} else {
						str = str + " 中专 ; ";
					}

				}
				remark.append(" 全日制最高学历代码包含:" + str + a);
			}

			if (!"".equals(condMap.get("qrzxla0814"))) {

				remark.append("  全日制最高学历毕业院校: " + condMap.get("qrzxla0814"));
			}
			if (!"".equals(condMap.get("qrzxla0824"))) {

				remark.append("  全日制最高学历专业: " + condMap.get("qrzxla0824"));
			}

			// 全日制最高学位
			this.getPageElement("qrzxwa0901bv").setValue(condMap.get("qrzxwa0901b"));// 最高学历 学历代码
			this.getExecuteSG().addExecuteCode("setCheckBox('qrzxwa0901b','" + condMap.get("qrzxwa0901b") + "');");
			this.getPageElement("qrzxwa0814").setValue(condMap.get("qrzxwa0814"));// 毕业院校
			this.getPageElement("qrzxwa0824").setValue(condMap.get("qrzxwa0824"));// 专业

			if (condMap.get("qrzxwa0901b")!=null&&!"".equals(condMap.get("qrzxwa0901b"))) {
				String[] crr = condMap.get("qrzxwa0901b").split(",");
				String str = "";
				String a = "";
				for (int i = 0; i < crr.length; i++) {
					if ("1".equals(crr[i]) || "2".equals(crr[i])) {
						a = " 博士 ; ";
					} else if ("2".equals(crr[i])) {
						str = str + " 硕士 ; ";
					} else {
						str = str + " 学士 ; ";
					}

				}
				remark.append(" 全日制最高学位包含:" + a + str);
			}

			if (!"".equals(condMap.get("qrzxwa0814"))) {

				remark.append("  全日制最高学位毕业院校: " + condMap.get("qrzxwa0814"));
			}
			if (!"".equals(condMap.get("qrzxwa0824"))) {

				remark.append("  全日制最高学位专业: " + condMap.get("qrzxwa0824"));
			}

			// 在职最高学历
			this.getPageElement("zzxla0801bv").setValue(condMap.get("zzxla0801b"));// 最高学历 学历代码
			this.getExecuteSG().addExecuteCode("setCheckBox('zzxla0801b','" + condMap.get("zzxla0801b") + "');");
			this.getPageElement("zzxla0814").setValue(condMap.get("zzxla0814"));// 毕业院校
			this.getPageElement("zzxla0824").setValue(condMap.get("zzxla0824"));// 专业

			if (condMap.get("zzxla0801b")!=null&&!"".equals(condMap.get("zzxla0801b"))) {
				String[] crr = condMap.get("zzxla0801b").split(",");
				String str = "";
				String a = " ";
				for (int i = 0; i < crr.length; i++) {
					if (alist.contains(crr[i])) {
						a = " 其他 ; ";
					}
					if ("1".equals(crr[i])) {
						str = str + " 研究生 ; ";
					} else if ("2".equals(crr[i])) {
						str = str + " 大学 ; ";
					} else if ("3".equals(crr[i])) {
						str = str + " 大专 ; ";
					} else {
						str = str + " 中专 ; ";
					}

				}
				remark.append(" 在职最高学历代码包含:" + str + a);
			}

			if (!"".equals(condMap.get("zzxla0814"))) {

				remark.append("  在职最高学历毕业院校:  " + condMap.get("zzxla0814"));
			}
			if (!"".equals(condMap.get("zzxla0824"))) {

				remark.append("  在职最高学历专业:  " + condMap.get("zzxla0824"));
			}

			// 在职最高学位
			this.getPageElement("zzxwa0901bv").setValue(condMap.get("zzxwa0901b"));// 最高学历 学历代码
			this.getExecuteSG().addExecuteCode("setCheckBox('zzxwa0901b','" + condMap.get("zzxwa0901b") + "');");
			this.getPageElement("zzxwa0814").setValue(condMap.get("zzxwa0814"));// 毕业院校
			this.getPageElement("zzxwa0824").setValue(condMap.get("zzxwa0824"));// 专业

			if (condMap.get("zzxwa0901b")!=null&&!"".equals(condMap.get("zzxwa0901b"))) {
				String[] crr = condMap.get("zzxwa0901b").split(",");
				String str = "";
				String a = "";
				for (int i = 0; i < crr.length; i++) {
					if ("1".equals(crr[i]) || "2".equals(crr[i])) {
						a = " 博士 ; ";
					} else if ("2".equals(crr[i])) {
						str = str + " 硕士 ; ";
					} else {
						str = str + " 学士 ; ";
					}

				}
				remark.append(" 在职最高学位包含:" + a + str);
			}

			if (!"".equals(condMap.get("zzxwa0814"))) {

				remark.append("  在职最高学位毕业院校:  " + condMap.get("zzxwa0814"));
			}
			if (!"".equals(condMap.get("zzxwa0824"))) {

				remark.append("  在职最高学位专业:  " + condMap.get("zzxwa0824"));
			}

			// 奖惩
			this.getPageElement("a14z101").setValue(condMap.get("a14z101"));// 奖惩描述
			if (!"".equals(condMap.get("a14z101"))) {
				remark.append("  奖惩描述包含 :  " + condMap.get("a14z101"));

			}
			this.getPageElement("lba1404bv").setValue(condMap.get("lba1404b"));// 奖惩类别
			this.getExecuteSG().addExecuteCode("setCheckBox('lba1404b','" + condMap.get("lba1404b") + "');");
			if (condMap.get("lba1404b")!=null&&!"".equals(condMap.get("lba1404b"))) {
				if ("01".endsWith(condMap.get("lba1404b"))) {
					remark.append("  奖惩类别: 奖励");
				} else if ("02".equals(condMap.get("lba1404b"))) {
					remark.append("  奖惩类别: 惩戒");
				} else {
					remark.append("  奖惩类别包含: 奖励,惩戒");
				}

			}

			this.getPageElement("a1404b").setValue(condMap.get("a1404b"));// 奖惩名称代码
			this.getPageElement("a1404b_combo").setValue(HBUtil.getCodeName("ZB65", condMap.get("a1404b")));// 奖惩名称代码
			if (!"".equals(condMap.get("a1404b"))) {
				remark.append("  奖惩名称为   " + HBUtil.getCodeName("ZB65", condMap.get("a1404b")));
			}

			this.getPageElement("a1415").setValue(condMap.get("a1415"));// 受奖惩时职务层次
			this.getPageElement("a1415_combo").setValue(HBUtil.getCodeName("ZB09", condMap.get("a1415")));// 受奖惩时职务层次
			if (!"".equals(condMap.get("a1415"))) {
				remark.append("  受奖惩时职务层次为: " + HBUtil.getCodeName("ZB09", condMap.get("a1415")));
			}

			this.getPageElement("a1414").setValue(condMap.get("a1414"));// 批准机关级别
			if (!"".equals(condMap.get("a1414"))) {
				remark.append("  批准机关级别为: " + HBUtil.getCodeName("ZB03", condMap.get("a1414")));
			}
			this.getPageElement("a1428").setValue(condMap.get("a1428"));// 批准机关性质
			this.getPageElement("a1428_combo").setValue(HBUtil.getCodeName("ZB128", condMap.get("a1428")));// 批准机关性质
			if (!"".equals(condMap.get("a1428"))) {
				remark.append("  批准机关性质为: " + HBUtil.getCodeName("ZB128", condMap.get("a1428")));
			}
			
			// 年度考核
			this.getPageElement("a15z101").setValue(condMap.get("a15z101"));// 年度考核描述
			this.getPageElement("a1521").setValue(condMap.get("a1521"));// 考核年度
			this.getPageElement("a1517").setValue(condMap.get("a1517"));// 考核结论类别
			this.getPageElement("a1517_combo").setValue(HBUtil.getCodeName("ZB18", condMap.get("a1517")));// 考核结论类别
			if (!"".equals(condMap.get("a15z101"))) {
				remark.append("  年度考核描述包含   " + condMap.get("a15z101"));
			}
			if (!"".equals(condMap.get("a1521"))) {
				remark.append("  考核年度为    " + condMap.get("a1521"));
			}
			if (!"".equals(condMap.get("a1517"))) {
				remark.append("   考核结论类别为   " + HBUtil.getCodeName("ZB18", condMap.get("a1517")));
			}

			// 专业技术
			this.getPageElement("a0601a").setValue(HBUtil.getCodeName("GB8561", condMap.get("a0601a")));
			if (!"".equals(condMap.get("a0601a")) && condMap.get("a0601a") != null) {
				remark.append("  专业技术为: " + HBUtil.getCodeName("GB8561", condMap.get("a0601a")));

			}
			// 熟悉领域
			this.getPageElement("a0194z").setValue(condMap.get("a0194z"));
			this.getPageElement("a0194s").setValue(condMap.get("a0194s"));
			if (!"".equals(condMap.get("a0194z")) && condMap.get("a0194z") != null) {
				remark.append("  熟悉领域包含: " + condMap.get("a0194z"));
			}

			// 重要经历
			this.getPageElement("a0193s").setValue(condMap.get("a0193s"));
			this.getPageElement("a0193z").setValue(condMap.get("a0193z"));
			if (!"".equals(condMap.get("a0193z")) && condMap.get("a0193z") != null) {
				remark.append("  重要经历包含: " + condMap.get("a0193z"));
			}
//			this.getExecuteSG().addExecuteCode("showCheckbox();");

//			this.getPageElement("queryid").setValue(query.getQueryid());
//			this.getPageElement("queryname").setValue(query.getQueryname());

			SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyyMM");
			String datestr = myFmt1.format(new Date());
//			this.getPageElement("jiezsj").setValue(datestr);
//			this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4) + "." + datestr.substring(4, 6));

			// 查询综述的拼接
			if (remark != null && !"".equals(remark)) {
//				String sInfo = "查询综述:   " + remark.toString();
				String sInfo =remark.toString();
//				this.getPageElement("remark").setValue(sInfo);
				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('tptj').value='"+sInfo+"'");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("获取数据异常：" + e.getMessage());
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("deletecond")
	public int deletecond(String id) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.createSQLQuery("delete from Customquery where queryid=?").setString(0, id).executeUpdate();
			this.getExecuteSG().addExecuteCode("isParentLoad();radow.doEvent('memberGrid.dogridquery');");
			this.setMainMessage("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("删除出现异常！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	public static String getPercentFormat(double d, int IntegerDigits, int FractionDigits) {
		NumberFormat nf = java.text.NumberFormat.getPercentInstance();
		nf.setMaximumIntegerDigits(IntegerDigits);// 小数点前保留几位
		nf.setMinimumFractionDigits(FractionDigits);// 小数点后保留几位
		String str = nf.format(d);
		return str;
	}

	//查询，同级or 不同级and
	static Map<String, String> A0165SEARCH = new HashMap<String, String>(){
		{
			put("01", "1");
			put("02", "2");
			put("05", "3");
			put("03", "3");
			put("10", "3");
			put("11", "3");
			put("04", "3");
			put("18", "3");
			put("19", "3");
			put("21", "3");
			put("20", "3");
			put("12", "4");
			put("50", "4");
			put("07", "4");
			put("08", "4");
			put("09", "4");
			put("13", "4");
			put("14", "5");
			put("15", "5");
			put("51", "4");
			put("60", "4");
			put("52", "5");
			put("06", "5");
			put("16", "5");
			put("17", "5");
		}
	};
	
	public String isnull(Object obj) {
		String str="";
		if(obj!=null&&!"".equals(obj)) {
			str=obj.toString();
		}
		return str;
	}
	
	public String codevalueFY(String codevalues,String code_type) {
		String str="";
		if("".equals(codevalues)) {
			
		}else {
			String sql="";
			String[] arr=codevalues.split(",");
			for(int i=0;i<arr.length;i++) {
				sql="select code_name from code_value where code_type='"+code_type+"' and code_value='"+arr[i]+"' ";
				HBSession session = HBUtil.getHBSession();
				str =str+ session.createSQLQuery(sql).uniqueResult().toString()+",";
			}
			str=str.substring(0,str.length()-1);
		}
		return str;
	}
	
	
}
