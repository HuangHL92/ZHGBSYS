package com.insigma.siis.local.pagemodel.customquery;

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
import com.utils.CountCSBySql;
import com.utils.DBUtils;
import com.utils.CountCSBySql.keyWordSet;
import com.utils.StrUtils;

import net.sf.json.JSONObject;

public class GroupPageModel extends PageModel {
	private final static List<String> alist = Arrays.asList("6", "7", "8", "9");// 用于比较最高学历
	private static GroupPageModel gpm;

	@Override
	@AutoNoMask
	public int doInit() throws RadowException {
		this.controlButton();
		// this.setNextEventName("initX");
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
		((Combo) this.getPageElement("a1521")).setValueListForSelect(map);

		this.getExecuteSG().addExecuteCode("radow.doEvent('memberGrid.dogridquery')");

		if(!DBUtils.isNoGbmc(SysManagerUtils.getUserId())) {
			this.getExecuteSG().addExecuteCode("document.getElementById('gbmcQuery').style.display = 'none'");
			this.getExecuteSG().addExecuteCode("document.getElementById('tr_h').style.display = 'none'");
		}
		CommQuery cqbs=new CommQuery();
		try {
			String userid=SysManagerUtils.getUserId();
			List<HashMap<String, Object>> mcCode = cqbs.getListBySQL("select mdid,mdmc from (select mdid,mdmc,createdate "+ 
				"  from historymd y" + 
				" where userid = '"+userid+"'" + 
				" union all" + 
				" select mdid,mdmc,createdate " + 
				"  from historymd y" + 
				" where exists (select 1" + 
				"          from hz_LSMD_userref u" + 
				"         where mnur01 = '"+userid+"'" + 
				"           and u.mdid = y.mdid)) order by createdate desc");
			HashMap<String, Object> mapMC=new LinkedHashMap<String, Object>();
			for(HashMap<String, Object> m : mcCode){
				String id = m.get("mdid")==null?"":m.get("mdid").toString();
				String name = m.get("mdmc")==null?"":m.get("mdmc").toString();
				mapMC.put(id, name);
			}
			((Combo)this.getPageElement("gbgzmd")).setValueListForSelect(mapMC);
		} catch (AppException e) {
			// TODO Auto-generated catch block
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
		((Combo) this.getPageElement("a1521")).setValueListForSelect(map);

		//this.getExecuteSG().addExecuteCode("clearCon();");
		return 0;
	}

	/**
	 * 查询（注：范围查询中代码的大小与字面逻辑的高低正好相反，所以判断逻辑也是相反的处理）
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("mQueryonclick")
	public int query() throws RadowException, AppException {
		// String a0194s=this.getPageElement("a0194s").getValue();
		// String a0193s=this.getPageElement("a0193s").getValue();
		String userID = SysManagerUtils.getUserId();
		// this.request.getSession().setAttribute("queryTypeEX", "新改查询方式");

		String b01String = (String) this.getPageElement("SysOrgTreeIds").getValue();

		StringBuffer a02_a0201b_sb = new StringBuffer("");
		StringBuffer cu_b0111_sb = new StringBuffer("");

		if (b01String != null && !"".equals(b01String)) {// tree!=null && !"".equals(tree.trim()
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if (jsonObject.size() > 0) {
				a02_a0201b_sb.append(" and (1=2 ");
				cu_b0111_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String nodeid = it.next();
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");// [机构名称，是否包含下级，是否本级选中]
				if ("true".equals(types[1]) && "true".equals(types[2])) {
					// a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+"%' ");
					a02_a0201b_sb.append(" or " + CommSQL.subString("a02.a0201b", 1, nodeid.length(), nodeid));
					// cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+"%' ");
					cu_b0111_sb.append(" or " + CommSQL.subString("cu.b0111", 1, nodeid.length(), nodeid));
				} else if ("true".equals(types[1]) && "false".equals(types[2])) {
					// a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+".%' ");
					a02_a0201b_sb.append(" or " + CommSQL.subString2("a02.a0201b", 1, nodeid.length(), nodeid));
					// cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+".%' ");
					cu_b0111_sb.append(" or " + CommSQL.subString2("cu.b0111", 1, nodeid.length(), nodeid));
				} else if ("false".equals(types[1]) && "true".equals(types[2])) {
					a02_a0201b_sb.append(" or a02.a0201b = '" + nodeid + "' ");
					cu_b0111_sb.append(" or cu.b0111 = '" + nodeid + "' ");
				}
			}
			if (jsonObject.size() > 0) {
				a02_a0201b_sb.append(" ) ");
				cu_b0111_sb.append(" ) ");
				this.getExecuteSG()
				.addExecuteCode("realParent.document.getElementById('a02_a0201b_sb').value=\""+a02_a0201b_sb+"\"");
			}else {
				this.getExecuteSG()
				.addExecuteCode("realParent.document.getElementById('a02_a0201b_sb').value=\"\"");
			}
		}
		// 用来判断列表、小资料、照片
		String tableType = this.getPageElement("tableType").getValue();
		System.out.println(a02_a0201b_sb+"00000000");
		return newquery(cu_b0111_sb, a02_a0201b_sb, userID, tableType);

	}

	@SuppressWarnings("static-access")
	private int newquery(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, String userID, String tableType)
			throws RadowException, AppException {
		String finalsql = getSQL(cu_b0111_sb, a02_a0201b_sb, userID); // 拼接页面sql
		System.out.println(finalsql+"sssssssssssss");
		/*
		 * String Contrast = this.getPageElement("ppName").getValue(); // 获取适配人员 if
		 * (Contrast.length() > 0) { // 判断适配人员是否为空 String a0000s =
		 * this.getPageElement("ppA0000").getValue(); // 适配人的id String ppNum =
		 * this.getPageElement("ppNum").getValue(); // 适配人数 String sql =
		 * finalsql.substring(finalsql.indexOf("from"), finalsql.length()); sql =
		 * "select * " + sql; CountCSBySql csBySql = new CountCSBySql(sql,
		 * Integer.parseInt(ppNum)); Map<String, Double> a0000s2 =
		 * csBySql.get_Man_Similarity_Map(a0000s);// 匹配算法后返回一个 key是a0000 value是匹配律的map
		 * // 拼接a000的值和 decode。 decode用来做相识度 的排序
		 * this.request.getSession().setAttribute("Man_Similarity", "1"); String sid =
		 * this.request.getSession().getId(); HBSession sess = HBUtil.getHBSession();
		 * sess.createSQLQuery("DELETE  from Percentage where  sessionID=?").setString(
		 * 0, sid).executeUpdate(); //删除当前用户的百分比临时数据 再次没有查询时候有残留？
		 * sess.createSQLQuery("DELETE  from KEYWORDS where  sid=?").setString(0,
		 * sid).executeUpdate(); //删除当前用户的百分比临时数据 再次没有查询时候有残留？
		 * 
		 * sess.flush(); //拼接sql中的a0000 DECODE排序用的sql，将名族和百分比拼接后存入临时表中 HBSession sess1 =
		 * HBUtil.getHBSession(); List<String> sqls = new ArrayList<String>();
		 * Map<String, keyWordSet> keywords = csBySql.keywords; if (a0000s2.size() != 0)
		 * { String a0000 = ""; String decode = ""; int count = 1; for (Entry<String,
		 * Double> vo : a0000s2.entrySet()) { Double num =vo.getValue(); if(num>0) {
		 * Percentage percentage = new Percentage(); percentage.setA0000(vo.getKey());
		 * percentage.setPercentage_number( "(" + gpm.getPercentFormat(num, 3, 2) +
		 * ")"); percentage.setSessionID(sid);
		 * percentage.setIds(UUID.randomUUID().toString()); sess1.save(percentage);
		 * sess1.flush(); String a1701_key =
		 * StrUtils.getKeys(keywords.get(vo.getKey()).a1701_key,10);
		 * sqls.add(" insert into KEYWORDS (IDS, SID, A0000, NAME, VALUE) " +
		 * "values ('"+UUID.randomUUID().toString()+"','"+sid+"','"+vo.getKey()
		 * +"', 'a1701_key', '"+a1701_key+"') "); a0000 += "'" + vo.getKey() + "',";
		 * count++; decode += "'" + vo.getKey() + "'," + count + ","; }
		 * 
		 * 
		 * } if(StringUtils.isEmpty(a0000)){ throw new AppException("未找到匹配的人员！"); }
		 * a0000 = a0000.substring(0, a0000.lastIndexOf(",")); decode =
		 * decode.substring(0, decode.lastIndexOf(",")); finalsql = finalsql +
		 * "and a01.a0000 in (" + a0000 + ")" + "order by DECODE (a01.a0000," + decode +
		 * ")"; //this.getPageElement("matchingType").setValue("1"); }
		 * HBUtil.batchSQLexqute(sqls); } System.out.println(finalsql);
		 */
		this.getPageElement("sql").setValue(finalsql);
		this.getExecuteSG()
		.addExecuteCode("realParent.document.getElementById('sql').value=document.getElementById('sql').value");
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		m.put("paixu", true);
		this.request.getSession().setAttribute("queryConditionsCQ", m);

		this.getExecuteSG().addExecuteCode("realParent.document.getElementById('checkedgroupid').value=''");
		this.getExecuteSG().addExecuteCode("realParent.document.getElementById('tabn').value='tab2'");
		// this.setNextEventName("peopleInfoGrid.dogridquery");
		if ("1".equals(tableType)) {
			String queryType = (String) request.getSession().getAttribute("queryType");
			if ("define".equals(queryType)) {
				this.getExecuteSG().addExecuteCode("realParent.changeField()");
				request.getSession().removeAttribute("queryType");
			} else {
				this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('peopleInfoGrid.dogridquery');");
			}

		}
		if ("2".equals(tableType)) {
			this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('peopleInfoGrid.dogridquery');");
			this.getExecuteSG().addExecuteCode("realParent.datashow();");
		}
		if ("3".equals(tableType)) {
			this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('peopleInfoGrid.dogridquery');");
			this.getExecuteSG().addExecuteCode("realParent.picshow();");
		}
		this.getExecuteSG().addExecuteCode("collapseGroupWin();");

		this.request.getSession().setAttribute("queryType", "1");
		// TODO Auto-generated method stub
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void xuelixueweiSQL(String a0801b, String a0814, String a0824, StringBuffer orther_sb, String highField,
			String xueliORxuewei, boolean isgbk, String querydb) {
		StringBuffer a0801b_sb = new StringBuffer("");

		if (!"".equals(a0801b)) {
			String[] a0801bArray = a0801b.split(",");
			for (int i = 0; i < a0801bArray.length; i++) {
				a0801b_sb.append(" " + xueliORxuewei + " like '" + a0801bArray[i] + "%' or ");
			}
			a0801b_sb.delete(a0801b_sb.length() - 4, a0801b_sb.length() - 1);
		}

		if (!"".equals(a0801b) || !"".equals(a0814) || !"".equals(a0824)) {
			if (isgbk) {
				orther_sb.append(" and a01.a0000 in (select a0000 from a08 where " + highField + "='1' ");
			} else {
				orther_sb.append(" and a01.a0000 in (select a0000 from v_Js_a08 a08 where a08.v_xt='" + querydb
						+ "' and " + highField + "='1' ");
			}

			if (!"".equals(a0801b)) {
				orther_sb.append(" and (" + a0801b_sb.toString() + ")");
			}

			if (!"".equals(a0814)) {
				orther_sb.append(" and a0814 like '%" + a0814 + "%'");
			}
			if (!"".equals(a0824)) {
				orther_sb.append(" and a0824 like '%" + a0824 + "%'");
			}
			orther_sb.append(")");
		}

	}

	private void xuelixueweiSQL(String byyxzya08, String intersection3, StringBuffer orther_sb, boolean isgbk,
			String querydb) {
		if (!"".equals(byyxzya08)) {
			if (isgbk) {
				orther_sb.append(" and a01.a0000 in (select a0000 from a08 where");
			} else {
				orther_sb.append(
						" and a01.a0000 in (select a0000 from v_Js_a08 a08 where a08.v_xt='" + querydb + "' and ");
			}

			// 毕业院校
			if (byyxzya08.indexOf(";") != -1) {
				String[] newa0814 = byyxzya08.split(";");
				for (int i = 0; i < newa0814.length; i++) {
					if (i == 0) {
						orther_sb.append(" (a08.A0814 like '%" + newa0814[i] + "%'");
					} else {
						orther_sb.append(" or a08.A0814 like '%" + newa0814[i] + "%'");
					}
				}
				orther_sb.append(")");
			} else if (byyxzya08.indexOf("；") != -1) {
				String[] newa0814 = byyxzya08.split("；");
				for (int i = 0; i < newa0814.length; i++) {
					if (i == 0) {
						orther_sb.append(" (a08.A0814 like '%" + newa0814[i] + "%'");
					} else {
						orther_sb.append(" or a08.A0814 like '%" + newa0814[i] + "%'");
					}
				}
				orther_sb.append(")");
			} else {
				orther_sb.append(" a08.A0814 like '%" + byyxzya08 + "%'");
			}

			// 专业
			if (byyxzya08.indexOf(";") != -1) {
				String[] newa0824 = byyxzya08.split(";");
				for (int i = 0; i < newa0824.length; i++) {
					if (i == 0) {
						orther_sb.append(intersection3 + " (a08.A0824 like '%" + newa0824[i] + "%'");
					} else {
						orther_sb.append(" or a08.A0824 like '%" + newa0824[i] + "%'");
					}
				}
				orther_sb.append(" and a08.A0831=1)");
			} else if (byyxzya08.indexOf("；") != -1) {
				String[] newa0824 = byyxzya08.split("；");
				for (int i = 0; i < newa0824.length; i++) {
					if (i == 0) {
						orther_sb.append(intersection3 + " (a08.A0824 like '%" + newa0824[i] + "%'");
					} else {
						orther_sb.append(" or a08.A0824 like '%" + newa0824[i] + "%'");
					}
				}
				orther_sb.append(")");
			} else {
				orther_sb.append(" or a08.A0824 like '%" + byyxzya08 + "%'");
			}
			orther_sb.append(" and a08.A0831=1)");
		}
	}


	private String getSQL(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, String userID)
			throws AppException, RadowException {
		StringBuffer a01sb = new StringBuffer("");
		StringBuffer a02sb = new StringBuffer("");
		StringBuffer orther_sb = new StringBuffer("");
		String sid = this.request.getSession().getId();
		String querydb = this.getPageElement("querydb").getValue();
		boolean isgbk = true;
		if (querydb != null && (querydb.equals("2") || querydb.equals("3") || querydb.equals("4"))) {
			isgbk = false;
		}

		// 人员姓名
		String a0101 = this.getPageElement("a0101").getValue();
		if (!"".equals(a0101)) {
			a01sb.append(" and ");
			a01sb.append("a01.a0101 like '" + a0101 + "%'");
		}
		

		//机构类别	
		String a01sb1 = "";
		String b0131A = this.getPageElement("b0131A").getValue();
		if(b0131A!=null && !"".equals(b0131A)) {
			String sz_flag="((substr(b.b0131,1,2) in ('31','32','34') or b.b0131 in ('1001','1002','1004')) and substr(b.b0111,1,11)='001.001.002') or ";
			String qx_flag="((substr(b.b0131,1,2) in ('31','32','34') or b.b0131 in ('1001','1002','1004')) and substr(b.b0111,1,11)='001.001.004') or ";
			String[] strs = b0131A.split(",");
			a01sb1=a01sb1+" and (";
			if(b0131A.indexOf("01")>-1||b0131A.indexOf("02")>-1) {//市直
				if(b0131A.indexOf("01")==-1) {//党委不存在
					sz_flag=sz_flag.replace("'31','32',", "").replace("'1001','1002',", "");
				}
				if(b0131A.indexOf("02")==-1) {//政府不存在
					sz_flag=sz_flag.replace(",'34'", "").replace(",'1004'", "");
				}
				a01sb1=a01sb1+sz_flag;
			}
			if(b0131A.indexOf("03")>-1||b0131A.indexOf("04")>-1) {//区县
				if(b0131A.indexOf("03")==-1) {//党委不存在
					qx_flag=qx_flag.replace("'31','32',", "").replace("'1001','1002',", "");
				}
				if(b0131A.indexOf("04")==-1) {//政府不存在
					qx_flag=qx_flag.replace(",'34'", "").replace(",'1004'", "");
				}
				a01sb1=a01sb1+qx_flag;
			}
			if(b0131A.indexOf("05")>-1||b0131A.indexOf("06")>-1||b0131A.indexOf("07")>-1) {//乡镇街道
				String xzj_flag="(b0124 in (";
				if(b0131A.indexOf("05")>-1) {//镇存在
					xzj_flag=xzj_flag+"'81',";
				}
				if(b0131A.indexOf("06")>-1) {//乡存在
					xzj_flag=xzj_flag+"'82',";
				}
				if(b0131A.indexOf("07")>-1) {//街道存在
					xzj_flag=xzj_flag+"'83',";
				}
				xzj_flag=xzj_flag.substring(0, xzj_flag.length()-1)+")) or ";
				a01sb1=a01sb1+xzj_flag;
			}
			a01sb1=a01sb1.substring(0, a01sb1.length()-3);
			a01sb1=a01sb1+") ";
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
		}

		// 性别
		String a0104 = this.getPageElement("a0104").getValue();
		if(a0104!=null&&!"".equals(a0104)) {
			a01sb.append(" and (");
			if("0".equals(a0104)) {
				a01sb.append(" (decode(a01.a0104,null,'0','','0',a01.a0104) <> '0') ");
			}else{
				a01sb.append(" a01.a0104 = '" + a0104 + "' ");
			}
			a01sb.append(") ");
		}

		// 民族
		String a0117 = this.getPageElement("a0117").getValue();
		if(a0117!=null&&!"".equals(a0117)) {
			a01sb.append(" and (");
			if("0".equals(a0117)) {
				a01sb.append(" (decode(a01.a0117,null,'0','','0',a01.a0117) <> '0') ");
			}else if("1".equals(a0117)){
				a01sb.append(" a01.a0117 = '01' ");
			}else if("2".equals(a0117)){
				a01sb.append(" a01.a0117 <> '01' ");
			}
			a01sb.append(") ");
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
		}
		if (!"".equals(ageB)) {
			a01sb.append(" and substr("+jiezsj+"-substr(a01.a0107,1,6),1,2)<lpad('" + ageB + "',2,'0')");
		}

		// 出生年月
		String a0107A = this.getPageElement("a0107A").getValue();
		String a0107B = this.getPageElement("a0107B").getValue();// 出生年月
		if (!"".equals(a0107A)) {
			a01sb.append(" and a01.a0107>='" + a0107A + "'");
		}
		if (!"".equals(a0107B)) {
			if(a0107B.length()==6){
				a0107B = a0107B + "31";
			}
			a01sb.append(" and a01.a0107<='" + a0107B + "'");
		}
		

		
		
		//学历学位
		String xlxw = this.getPageElement("xlxw").getValue();
		if(xlxw!=null&&!"".equals(xlxw)) {
			String[] strs = xlxw.split(",");
			a01sb.append(" and (");

			String qrz = this.getPageElement("qrz").getValue();
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
		}
		
		//专业
		String a0824 = this.getPageElement("a0824").getValue();
		if (!"".equals(a0824)) {
			a01sb.append(" and exists (select 1 from a08 where a08.a0000=a01.a0000 and a0824 like '%"+a0824+"%') ");
		}

		//现职务时间
		String a0192fA = this.getPageElement("a0192fA").getValue();// 现职务时间
		String a0192fB = this.getPageElement("a0192fB").getValue();// 现职务时间
		if (!"".equals(a0192fA)) { 
			a01sb.append(" and a01.a0192f >='" + a0192fA + "'"); 
		} 
		if (!"".equals(a0192fB)) { 
			a01sb.append(" and a01.a0192f <='" + a0192fB + "'"); 
		}

		//职务层次
		String a0221A = this.getPageElement("a0221A").getValue();// 职务层次
		if (!"".equals(a0221A)) {
			a01sb.append(" and a01.a0221 in('" + (a0221A.replace(",", "','")) + "')");
		}

		//职务层次时间
		String a0288A = this.getPageElement("a0288A").getValue();// 任现职务层次时间
		String a0288B = this.getPageElement("a0288B").getValue();// 任现职务层次时间
		if (!"".equals(a0288A)) { 
			a01sb.append(" and a01.a0288>='" + a0288A + "'"); 
		} 
		if (!"".equals(a0288B)) { 
			a01sb.append(" and a01.a0288<='" + a0288B + "'"); 
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
		}

		// 任职级时间
		String a0192cA = this.getPageElement("a0192cA").getValue();
		String a0192cB = this.getPageElement("a0192cB").getValue();
		if (!"".equals(a0192cA)) {
			a01sb.append(" and a01.a0192c>='" + a0192cA + "'");
		}
		if (!"".equals(a0192cB)) {
			a01sb.append(" and a01.a0192c<='" + a0192cB + "'");
		}

		// 简历
		String a1701 = this.getPageElement("a1701").getValue();
		if (!"".equals(a1701)) {
			a01sb.append(" and ");
			a01sb.append("a01.a1701 like '%" + a1701 + "%'");
		}
		
		//熟悉领域
		String a0196z = this.getPageElement("a0196z").getValue();
		if(a0196z!=null&&!"".equals(a0196z)) { 
			String[] strs = a0196z.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from attr_lrzw where 1=2 or ");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append(" "+strs[i]+" = '1' ");
				if(i!=strs.length-1) {
					orther_sb.append(" or ");
				}
			}
			orther_sb.append(") ");
		}

		//两头干部
		String a0196c = this.getPageElement("a0196c").getValue();
		if(a0196c!=null&&!"".equals(a0196c)) { 
			String[] strs = a0196c.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from extra_tags where 1=2 or a0196c in (");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append("'"+strs[i]+"'");
				if(i!=strs.length-1) {
					orther_sb.append(",");
				}
			}
			orther_sb.append(")) ");
		}
		
		//熟悉领域
		String A0194_TAG = this.getPageElement("A0194_TAG").getValue();
		if(A0194_TAG!=null&&!"".equals(A0194_TAG)) { 
			String[] strs = A0194_TAG.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from zjs_A0196_TAG where 1=2 or a0196 in (");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append("'"+strs[i]+"'");
				if(i!=strs.length-1) {
					orther_sb.append(",");
				}
			}
			orther_sb.append(")) ");
		}
		
		//工作名单
		String gbgzmd = this.getPageElement("gbgzmd").getValue();
		if(gbgzmd!=null&&!"".equals(gbgzmd)) { 
			String[] strs = gbgzmd.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from historyper where 1=2 or mdid in (");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append("'"+strs[i]+"'");
				if(i!=strs.length-1) {
					orther_sb.append(",");
				}
			}
			orther_sb.append(")) ");
		}
		
		//任职经历新
		String newRZJL = this.getPageElement("newRZJL").getValue();
		int flag1=0;
		int flag2=0;
		if(newRZJL!=null&&!"".equals(newRZJL)) { 
			String[] strs = newRZJL.split(",");
	//		orther_sb.append(" and a01.a0000 in (select a0000 from hz_a17 where 1=2 or a1705 in (");
			//循环类型1
			for(int i=0;i<strs.length;i++) {
				String type=strs[i].split("_")[0];
				String value=strs[i].split("_")[1];
				if("1".equals(type)) {
					if(flag1==0) {
						orther_sb.append(" and (a01.a0000 in (select a0000 from hz_a17 where 1=2 or a1705 in (");
					}
					orther_sb.append("'"+value+"'");
					orther_sb.append(",");
					flag1+=1;		
				}	
			}
			if(flag1>0) {
				orther_sb.deleteCharAt(orther_sb.length()-1);
				orther_sb.append(")) ");
			}
			//循环类型2
			for(int i=0;i<strs.length;i++) {
				String type=strs[i].split("_")[0];
				String value=strs[i].split("_")[1];
				if("2".equals(type)) {
					if(flag2==0 && flag1==0) {
						orther_sb.append(" and a01.a0000 in (select a0000 from zjs_A0193_TAG where 1=2 or a0193 in (");
					}else if(flag2==0 && flag1>0) {
						orther_sb.append(" or a01.a0000 in (select a0000 from zjs_A0193_TAG where 1=2 or a0193 in (");
					}
					orther_sb.append("'"+value+"'");
					orther_sb.append(",");
					flag2+=1;		
				}	
			}
			if(flag2>0 && flag1>0) {
				orther_sb.deleteCharAt(orther_sb.length()-1);
				orther_sb.append("))) ");
			}else if(flag2>0) {
				orther_sb.deleteCharAt(orther_sb.length()-1);
				orther_sb.append(")) ");
			}else {
				orther_sb.append(") ");
			}
		}
		
		//选调生
		String a99z103= this.getPageElement("a99z103").getValue();
		String a99z104A = this.getPageElement("a99z104A").getValue();
		String a99z104B = this.getPageElement("a99z104B").getValue();// 出生年月
		System.out.println("a99z103==================="+a99z103);
		if(a99z103!=null&&!"".equals(a99z103)&&"1".equals(a99z103)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a99z1 where a99z103='1') ");
		}
		if (!"".equals(a99z104A)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a99z1 where a99z104>='"+a99z104A+"') ");
		}
		if (!"".equals(a99z104B)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a99z1 where a99z104<='"+a99z104B+"') ");
		}

		//zdgwq重点岗位
		String zdgwq = this.getPageElement("zdgwq").getValue();
		if(zdgwq!=null&&!"".equals(zdgwq)) {
			String[] strs = zdgwq.split(",");
			orther_sb.append(" and ( a01.a0000 in  ");
			for(int i=0;i<strs.length;i++) {
				List list = HBUtil.getHBSession().createSQLQuery("select code_remark   from code_value where code_type='ZDGWBQ' and  code_value='"+strs[i]+"'").list();
				String ids = org.apache.commons.lang.StringUtils.join(list.toArray(),",");
				if(ids!=null&&!"".equals(ids)) {
					orther_sb.append(" "+ids+"  ");
					if(i!=strs.length-1) {
						orther_sb.append(" or a0000 in ");
					}
				}
				
			}
			orther_sb.append(") ");
		}
		
		
		//党龄
		String a0144age=this.getPageElement("a0144age").getValue();
		if(a0144age!=null&&!"".equals(a0144age)) {
			orther_sb.append(" and  TRUNC((to_char(sysdate, 'yyyyMM') - substr(a01.a0144,0,6)) /100)>="+a0144age+" ");

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
		}
		
		//重点岗位
//		String a1705 = this.getPageElement("a1705").getValue();
//		System.out.println(a1705);
//		if(a1705!=null&&!"".equals(a1705)) {
//			String[] strs = a1705.split(","); 
//			orther_sb.append(" and a01.a0000 in (select a0000 from hz_a17 where 1=2 or ");
//			for(int i=0;i<strs.length;i++) {
//				orther_sb.append(" a1705 = '"+strs[i]+"' ");
//				if(i!=strs.length-1) {
//					orther_sb.append(" or ");
//				}
//			}
//			orther_sb.append(") ");
//		}
		
		//分管工作类型
		String a1706 = this.getPageElement("a1706").getValue();
		if(a1706!=null&&!"".equals(a1706)) {
			String[] strs = a1706.split(","); 
			orther_sb.append(" and a01.a0000 in (select a0000 from hz_a17 where 1=2 or ");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append(" a1706 = '"+strs[i]+"' ");
				if(i!=strs.length-1) {
					orther_sb.append(" or ");
				}
			}
			orther_sb.append(") ");
		}
		
		//是否为现任
		String sfwxr = this.getPageElement("sfwxr").getValue();
		if(sfwxr!=null&&!"".equals(sfwxr)&&"01".equals(sfwxr)) {
			String a=orther_sb.toString().replaceAll("from hz_a17 where","from hz_a17 where a1701 is not null and a1702 is null and ");
			orther_sb=new StringBuffer(a);
		}
//		else {
//			String a=orther_sb.toString().replaceAll("from hz_a17 where","from hz_a17 where a1702 is not null and ");
//			orther_sb=new StringBuffer(a);
//		}
		//是否为历任
//		if(sfwxr!=null&&!"".equals(sfwxr)&&"02".equals(sfwxr)) {
//			
//		}
		
		//具有乡镇（街道）党政正职经历
		String a0188 = this.getPageElement("a0188").getValue();
		System.out.println("==================="+a0188);
		if(a0188!=null&&!"".equals(a0188)&&"1".equals(a0188)) {
			orther_sb.append(" and a01.a0188='1' ");
		}
		//现任乡镇（街道）党（工）委书记
		String a0132 = this.getPageElement("a0132").getValue();
		System.out.println("---------------"+a0132);
		if(a0132!=null&&!"".equals(a0132)&&"1".equals(a0132)) {
			orther_sb.append(" and a01.a0132='1' ");
		}
		//现任乡镇（街道）镇长（主任）
		String a0133 = this.getPageElement("a0133").getValue();
		System.out.println(a0133+"---------------");
		if(a0133!=null&&!"".equals(a0133)&&"1".equals(a0133)) {
			orther_sb.append(" and a01.a0133='1' ");
		}


		// 最高学历
		String xla0801b = this.getPageElement("xla0801bv").getValue();// 最高学历 学历代码
		String xla0814 = this.getPageElement("xla0814").getValue();// 毕业院校
		String xla0824 = this.getPageElement("xla0824").getValue();// 专业
		xuelixueweiSQL(xla0801b, xla0814, xla0824, orther_sb, "a0834", "a0801b", isgbk, querydb);

		// 最高学位
		String xwa0901b = this.getPageElement("xwa0901bv").getValue();// 最高学位 学位代码
		String xwa0814 = this.getPageElement("xwa0814").getValue();// 毕业院校
		String xwa0824 = this.getPageElement("xwa0824").getValue();// 专业
		xuelixueweiSQL(xwa0901b, xwa0814, xwa0824, orther_sb, "a0835", "a0901b", isgbk, querydb);

		// 全日制最高学历
		String qrzxla0801b = this.getPageElement("qrzxla0801bv").getValue();// 最高学历 学历代码
		String qrzxla0814 = this.getPageElement("qrzxla0814").getValue();// 毕业院校
		String qrzxla0824 = this.getPageElement("qrzxla0824").getValue();// 专业
		xuelixueweiSQL(qrzxla0801b, qrzxla0814, qrzxla0824, orther_sb, "a0831", "a0801b", isgbk, querydb);

		// 全日制最高学位
		String qrzxwa0901b = this.getPageElement("qrzxwa0901bv").getValue();// 最高学位 学位代码
		String qrzxwa0814 = this.getPageElement("qrzxwa0814").getValue();// 毕业院校
		String qrzxwa0824 = this.getPageElement("qrzxwa0824").getValue();// 专业
		xuelixueweiSQL(qrzxwa0901b, qrzxwa0814, qrzxwa0824, orther_sb, "a0832", "a0901b", isgbk, querydb);

		// 在职最高学历
		String zzxla0801b = this.getPageElement("zzxla0801bv").getValue();// 最高学历 学历代码
		String zzxla0814 = this.getPageElement("zzxla0814").getValue();// 毕业院校
		String zzxla0824 = this.getPageElement("zzxla0824").getValue();// 专业
		xuelixueweiSQL(zzxla0801b, zzxla0814, zzxla0824, orther_sb, "a0838", "a0801b", isgbk, querydb);

		// 在职最高学位
		String zzxwa0901b = this.getPageElement("zzxwa0901bv").getValue();// 最高学位 学位代码
		String zzxwa0814 = this.getPageElement("zzxwa0814").getValue();// 毕业院校
		String zzxwa0824 = this.getPageElement("zzxwa0824").getValue();// 专业
		xuelixueweiSQL(zzxwa0901b, zzxwa0814, zzxwa0824, orther_sb, "a0839", "a0901b", isgbk, querydb);

		// 奖惩
		String a14z101 = this.getPageElement("a14z101").getValue();// 奖惩描述
		String lba1404b = this.getPageElement("lba1404bv").getValue();// 奖惩类别
		String a1404b = this.getPageElement("a1404b").getValue();// 奖惩名称代码
		String a1415 = this.getPageElement("a1415").getValue();// 受奖惩时职务层次
		String a1414 = this.getPageElement("a1414").getValue();// 批准机关级别
		String a1428 = this.getPageElement("a1428").getValue();// 批准机关性质
		if (!"".equals(a14z101) || !"".equals(lba1404b) || !"".equals(a1404b) || !"".equals(a1415) || !"".equals(a1414)
				|| !"".equals(a1428)) {
			if (isgbk) {
				orther_sb.append(" and a01.a0000 in (select a0000 from a14 where 1=1 ");
			} else {
				orther_sb.append(" and a01.a0000 in (select a0000 from v_js_a14 a14 where a14.v_xt='" + querydb + "' ");
			}

			if (!"".equals(a14z101)) {
				orther_sb.append(" and a14z101 like '%" + a14z101 + "%'");
			}

			if (!"".equals(lba1404b)) {
				StringBuffer like_sb = new StringBuffer("");
				String[] fArray = lba1404b.split(",");
				for (int i = 0; i < fArray.length; i++) {
					like_sb.append(" a1404b like '" + fArray[i] + "%' or ");
				}
				like_sb.delete(like_sb.length() - 4, like_sb.length() - 1);
				orther_sb.append(" and (" + like_sb.toString() + ")");
			}
			if (!"".equals(a1404b)) {
				orther_sb.append(" and a1404b='" + a1404b + "'");
			}
			if (!"".equals(a1415)) {
				orther_sb.append(" and a1415='" + a1415 + "'");
			}
			if (!"".equals(a1414)) {
				orther_sb.append(" and a1414='" + a1414 + "'");
			}
			if (!"".equals(a1428)) {
				orther_sb.append(" and a1428='" + a1428 + "'");
			}
			orther_sb.append(")");
		}

		// 职称
		/*
		 * String a0601 = this.getPageElement("a0601v").getValue();// 专业技术任职资格
		 * 
		 * if (!"".equals(a0601)) { boolean is9 = false; if (isgbk) { orther_sb.
		 * append(" and (a01.a0000 in (select a0000 from a06 where a0699='true' "); }
		 * else { orther_sb.
		 * append(" and (a01.a0000 in (select a0000 from v_js_a06 a06 where a06.v_xt='"
		 * + querydb + "' and a0699='true' "); } StringBuffer like_sb = new
		 * StringBuffer(""); String[] fArray = a0601.split(","); for (int i = 0; i <
		 * fArray.length; i++) {
		 * 
		 * if ("9".equals(fArray[i])) {
		 * like_sb.append(" a0601 is null or a0601='999' or ");// 无职称 is9 = true; } else
		 * { like_sb.append(" a0601 like '%" + fArray[i] + "' or "); } }
		 * like_sb.delete(like_sb.length() - 4, like_sb.length() - 1);
		 * orther_sb.append(" and (" + like_sb.toString() + ")"); orther_sb.append(")");
		 * if (is9) {// 无职称 if (isgbk) { orther_sb.
		 * append(" or not exists (select 1 from a06 where a01.a0000=a06.a0000 and  a0699='true')"
		 * ); } else { orther_sb.
		 * append(" or not exists (select 1 from v_js_a06 a06 where a06.v_xt='" +
		 * querydb +
		 * " and a01.v_xt=a06.v_xt and a01.a0000=a06.a0000 and  a0699='true')"); }
		 * 
		 * } orther_sb.append(")"); }
		 */

		// 年度考核
		String a15z101 = this.getPageElement("a15z101").getValue();// 年度考核描述
		String a1521 = this.getPageElement("a1521").getValue();// 考核年度
		String a1517 = this.getPageElement("a1517").getValue();// 考核结论类别
		if (!"".equals(a15z101) || !"".equals(a1521) || !"".equals(a1517)) {
			if (isgbk) {
				orther_sb.append(" and a01.a0000 in (select a0000 from a15 where 1=1 ");
			} else {
				orther_sb.append(" and a01.a0000 in (select a0000 from v_js_a15 a15 where a15.v_xt='" + querydb + " ");
			}

			if (!"".equals(a15z101)) {
				orther_sb.append(" and a15z101 like '%" + a15z101 + "%'");
			}

			if (!"".equals(a1521)) {
				a1521 = a1521.replace(",", "','");
				orther_sb.append(" and a1521 in('" + a1521 + "')");
			}

			if (!"".equals(a1517)) {
				orther_sb.append(" and a1517='" + a1517 + "'");
			}

			orther_sb.append(")");
		}

		// 家庭成员
		/*
		 * String a3601 = this.getPageElement("a3601").getValue();// 姓名 String a3684 =
		 * this.getPageElement("a3684").getValue();// 身份证号 String a3611 =
		 * this.getPageElement("a3611").getValue();// 工作单位及职务 if (!"".equals(a3601) ||
		 * !"".equals(a3684) || !"".equals(a3611)) {
		 * 
		 * if (isgbk) {
		 * orther_sb.append(" and a01.a0000 in (select a0000 from a36 where 1=1 "); }
		 * else { orther_sb.
		 * append(" and a01.a0000 in (select a0000 from v_js_a36 a36 where a36.v_xt='" +
		 * querydb + " "); } if (!"".equals(a3601)) {
		 * orther_sb.append(" and a3601 like '" + a3601 + "%'"); }
		 * 
		 * if (!"".equals(a3684)) { orther_sb.append(" and a3684 like '" + a3684 +
		 * "%'"); }
		 * 
		 * if (!"".equals(a3611)) { orther_sb.append(" and a3611 like '%" + a3611 +
		 * "%'"); }
		 * 
		 * orther_sb.append(")"); }
		 */
		// 专业技术
		String a0601a = this.getPageElement("a0601a").getValue();
		if (!"".equals(a0601a)) {
			orther_sb.append(" and a01.a0000 = ( select a0000 from a06 where a0601='" + a0601a + "')");

		}

		// 重大经历
		String a0193s = this.getPageElement("a0193s").getValue();
		if (!"".equals(a0193s)) {
			a0193s = a0193s.replace("；", "','");
			orther_sb.append(" and a01.a0000 in (select a0000 from a0193_tag where 1=1 ");
			orther_sb.append(" ann a0193 in ('" + a0193s + "')) ");
		}

		// 熟悉领域
		String a0194s = this.getPageElement("a0194s").getValue();
		if (!"".equals(a0194s)) {
			a0194s = a0194s.replace("；", "','");
			orther_sb.append(" and a01.a0000 in (select a0000 from a0194_tag where 1=1 ");
			orther_sb.append(" and a0194 in ('" + a0194s + "'))");

		}
				
	
		
		String radioC = this.getPageElement("radioC").getValue();
		/*
		 * String sql = this.getPageElement("sql").getValue();
		 *
		 *
		 * if(!"1".equals(radioC)){ if("".equals(sql)||sql==null) throw new
		 * AppException("未进行过查询请先查询!"); }
		 */

		String a0163 = this.getPageElement("a0163").getValue();// 人员状态
		String qtxzry = "0";

		String finalsql = CommSQL.getCondiQuerySQL(userID, a01sb, a01sb1 ,a02sb, a02_a0201b_sb, cu_b0111_sb, orther_sb, a0163,
				qtxzry, sid, isgbk, querydb);

		return finalsql;
	}

	CustomQueryBS cbBs = new CustomQueryBS();

	/**
	 * 保存：（注：范围查询中代码的大小与字面逻辑的高低正好相反，所以判断逻辑也是相反的处理）
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveCon.onclick")
	@Transaction
	public int saveCon(String name) throws RadowException, AppException {
		// this.request.getSession().setAttribute("queryType", "1");
		String userID = SysManagerUtils.getUserId();
		// this.request.getSession().setAttribute("queryTypeEX", "新改查询方式");
		String b01String = (String) this.getPageElement("SysOrgTreeIds").getValue();

		StringBuffer a02_a0201b_sb = new StringBuffer("");
		StringBuffer cu_b0111_sb = new StringBuffer("");

		if (b01String != null && !"".equals(b01String)) {// tree!=null && !"".equals(tree.trim()
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if (jsonObject.size() > 0) {
				a02_a0201b_sb.append(" and (1=2 ");
				cu_b0111_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String nodeid = it.next();
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");// [机构名称，是否包含下级，是否本级选中]
				if ("true".equals(types[1]) && "true".equals(types[2])) {
					// a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+"%' ");
					a02_a0201b_sb.append(" or " + CommSQL.subString("a02.a0201b", 1, nodeid.length(), nodeid));
					// cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+"%' ");
					cu_b0111_sb.append(" or " + CommSQL.subString("cu.b0111", 1, nodeid.length(), nodeid));
				} else if ("true".equals(types[1]) && "false".equals(types[2])) {
					// a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+".%' ");
					a02_a0201b_sb.append(" or " + CommSQL.subString2("a02.a0201b", 1, nodeid.length(), nodeid));
					// cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+".%' ");
					cu_b0111_sb.append(" or " + CommSQL.subString2("cu.b0111", 1, nodeid.length(), nodeid));
				} else if ("false".equals(types[1]) && "true".equals(types[2])) {
					a02_a0201b_sb.append(" or a02.a0201b = '" + nodeid + "' ");
					cu_b0111_sb.append(" or cu.b0111 = '" + nodeid + "' ");
				}
			}
			if (jsonObject.size() > 0) {
				a02_a0201b_sb.append(" ) ");
				cu_b0111_sb.append(" ) ");
			}
		}

		Map<String, String> ret = getSQL2(cu_b0111_sb, a02_a0201b_sb, userID, b01String);
		String finalsql = ret.get("sql");
		// finalsql = "select * from ("+finalsql+") a01 where 1=1 ";
		if (name.trim() == "") {
			name = "常用条件";
		}

		cbBs.delComm();
		String queryid = this.getPageElement("queryid").getValue();
		cbBs.saveOrUodateCq(queryid, name, finalsql, "", SysUtil.getCacheCurrentUser().getLoginname(), "[]",
				ret.get("cond"));
		/*
		 * this.getExecuteSG().
		 * addExecuteCode("var cwin = $h.getTopParent().document.getElementById('iframe_conditionwin');"
		 * + "if(cwin){cwin.contentWindow.radow.doEvent('gridcq');}");
		 */
		// this.setMainMessage("保存成功，可在【查询方案】中的【常用条件】进行查询！");
		this.setMainMessage("保存成功！");
		this.getExecuteSG().addExecuteCode("isParentLoad();radow.doEvent('memberGrid.dogridquery');");
		return EventRtnType.NORMAL_SUCCESS;
	}

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

	private Map<String, String> getSQL2(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, String userID,
			String b01String) throws AppException, RadowException {
		Map<String, String> condMap = new HashMap<String, String>();
		StringBuffer a01sb = new StringBuffer("");
		StringBuffer a02sb = new StringBuffer("");
		StringBuffer orther_sb = new StringBuffer("");
		String sid = this.request.getSession().getId();
		String querydb = this.getPageElement("querydb").getValue();
		boolean isgbk = true;
		if (querydb != null && (querydb.equals("2") || querydb.equals("3") || querydb.equals("4"))) {
			isgbk = false;
		}

		condMap.put("b01String", b01String);

		// 人员姓名
		String a0101 = this.getPageElement("a0101").getValue();// 人员姓名
		condMap.put("a0101", a0101);
		if (!"".equals(a0101)) {
			a01sb.append(" and ");
			a01sb.append("a01.a0101 like '" + a0101 + "%'");
		}

		//机构类别	
		String a01sb1 = "";
		String b0131A = this.getPageElement("b0131A").getValue();
		if(b0131A!=null && !"".equals(b0131A)) {
			String sz_flag="((substr(b.b0131,1,2) in ('31','32','34') or b.b0131 in ('1001','1002','1004')) and substr(b.b0111,1,11)='001.001.002') or ";
			String qx_flag="((substr(b.b0131,1,2) in ('31','32','34') or b.b0131 in ('1001','1002','1004')) and substr(b.b0111,1,11)='001.001.004') or ";
			String[] strs = b0131A.split(",");
			a01sb1=a01sb1+" and (";
			if(b0131A.indexOf("01")>-1||b0131A.indexOf("02")>-1) {//市直
				if(b0131A.indexOf("01")==-1) {//党委不存在
					sz_flag=sz_flag.replace("'31','32',", "").replace("'1001','1002',", "");
				}
				if(b0131A.indexOf("02")==-1) {//政府不存在
					sz_flag=sz_flag.replace(",'34'", "").replace(",'1004'", "");
				}
				a01sb1=a01sb1+sz_flag;
			}
			if(b0131A.indexOf("03")>-1||b0131A.indexOf("04")>-1) {//区县
				if(b0131A.indexOf("03")==-1) {//党委不存在
					qx_flag=qx_flag.replace("'31','32',", "").replace("'1001','1002',", "");
				}
				if(b0131A.indexOf("04")==-1) {//政府不存在
					qx_flag=qx_flag.replace(",'34'", "").replace(",'1004'", "");
				}
				a01sb1=a01sb1+qx_flag;
			}
			if(b0131A.indexOf("05")>-1||b0131A.indexOf("06")>-1||b0131A.indexOf("07")>-1) {//乡镇街道
				String xzj_flag="(b0124 in (";
				if(b0131A.indexOf("05")>-1) {//镇存在
					xzj_flag=xzj_flag+"'81',";
				}
				if(b0131A.indexOf("06")>-1) {//乡存在
					xzj_flag=xzj_flag+"'82',";
				}
				if(b0131A.indexOf("07")>-1) {//街道存在
					xzj_flag=xzj_flag+"'83',";
				}
				xzj_flag=xzj_flag.substring(0, xzj_flag.length()-1)+")) or ";
				a01sb1=a01sb1+xzj_flag;
			}
			a01sb1=a01sb1.substring(0, a01sb1.length()-3);
			a01sb1=a01sb1+") ";
		}
		//xugai
		condMap.put("b0131A", b0131A);

		// 省管干部
		String a0165A = this.getPageElement("a0165A").getValue();
		condMap.put("a0165A", a0165A);
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
		}

		// 市管干部
		String a0165B = this.getPageElement("a0165B").getValue();
		condMap.put("a0165B", a0165B);
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
		}

		// 处级（中层）干部
		String a0165C = this.getPageElement("a0165C").getValue();
		condMap.put("a0165C", a0165C);
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
		}

		// 性别
		String a0104 = this.getPageElement("a0104").getValue();
		condMap.put("a0104", a0104);
		if(a0104!=null&&!"".equals(a0104)) {
			a01sb.append(" and (");
			if("0".equals(a0104)) {
				a01sb.append("  (decode(a01.a0104,null,'0','','0',a01.a0104) <> '0') ");
			}else{
				a01sb.append(" a01.a0104 = '" + a0104 + "' ");
			}
			a01sb.append(") ");
		}

		// 民族
		String a0117 = this.getPageElement("a0117").getValue();
		condMap.put("a0117", a0117);
		if(a0117!=null&&!"".equals(a0117)) {
			a01sb.append(" and (");
			if("0".equals(a0117)) {
				a01sb.append(" (decode(a01.a0117,null,'0','','0',a01.a0117) <> '0') ");
			}else if("1".equals(a0117)){
				a01sb.append(" a01.a0117 = '01' ");
			}else if("2".equals(a0117)){
				a01sb.append(" a01.a0117 <> '01' ");
			}
			a01sb.append(") ");
		}

		// 党派
		String a0141 = this.getPageElement("a0141").getValue();// 党派
		condMap.put("a0141", a0141);
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
		}

		//年龄
		String ageA = this.getPageElement("ageA").getValue();// 年龄
		String ageB = this.getPageElement("ageB").getValue();// 年龄
		condMap.put("ageA", ageA);
		condMap.put("ageB", ageB);
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
		}
		if (!"".equals(ageB)) {
			a01sb.append(" and substr("+jiezsj+"-substr(a01.a0107,1,6),1,2)<lpad('" + ageB + "',2,'0')");
		}

		// 出生年月
		String a0107A = this.getPageElement("a0107A").getValue();
		String a0107B = this.getPageElement("a0107B").getValue();// 出生年月
		condMap.put("a0107A", a0107A);
		condMap.put("a0107B", a0107B);
		if (!"".equals(a0107A)) {
			a01sb.append(" and a01.a0107>='" + a0107A + "'");
		}
		if (!"".equals(a0107B)) {
			a01sb.append(" and a01.a0107<='" + a0107B + "'");
		}

		//学历学位
		String xlxw = this.getPageElement("xlxw").getValue();
		condMap.put("xlxw", xlxw);
		if(xlxw!=null&&!"".equals(xlxw)) {
			String[] strs = xlxw.split(",");
			a01sb.append(" and (");

			String qrz = this.getPageElement("qrz").getValue();
			condMap.put("qrz", qrz);
			for(int i=0;i<strs.length;i++) {
				if(qrz!=null&&!"".equals(qrz)&&"1".equals(qrz)) {
					a01sb.append(" a01.qrzxl like '%" + strs[i] + "%' ");
				}else {
					a01sb.append(" a01.zgxl like '%" + strs[i] + "%' ");
				}
				if(i!=strs.length-1) {
					a01sb.append(" or ");
				}
			}

			a01sb.append(") ");
		}
		
		String a0824 = this.getPageElement("a0824").getValue();
		if(a0824==null) {
			condMap.put("a0824", "");
		}else {
			condMap.put("a0824", a0824);
		}
		if(a0824!=null&&!"".equals(a0824)) {
			a01sb.append(" and exists (select 1 from a08 where a08.a0000=a01.a0000 and a0824 like '%"+a0824+"%') ");
		}

		//现职务时间
		String a0192fA = this.getPageElement("a0192fA").getValue();// 现职务时间
		String a0192fB = this.getPageElement("a0192fB").getValue();// 现职务时间
		condMap.put("a0192fA", a0192fA);
		condMap.put("a0192fB", a0192fB);
		if (!"".equals(a0192fA)) { 
			a01sb.append(" and a01.a0192fA>='" + a0192fA + "'"); 
		} 
		if (!"".equals(a0192fB)) { 
			a01sb.append(" and a01.a0192fB<='" + a0192fB + "'"); 
		}

		//职务层次
		String a0221A = this.getPageElement("a0221A").getValue();// 职务层次
		condMap.put("a0221A", a0221A);
		if (!"".equals(a0221A)) {
			a01sb.append(" and a01.a0221 in('" + (a0221A.replace(",", "','")) + "')");
		}

		//职务层次时间
		String a0288A = this.getPageElement("a0288A").getValue();// 任现职务层次时间
		String a0288B = this.getPageElement("a0288B").getValue();// 任现职务层次时间
		condMap.put("a0288A", a0288A);
		condMap.put("a0288B", a0288B);
		if (!"".equals(a0288A)) { 
			a01sb.append(" and a01.a0288>='" + a0288A + "'"); 
		} 
		if (!"".equals(a0288B)) { 
			a01sb.append(" and a01.a0288<='" + a0288B + "'"); 
		}

		// 现职级
		String a0192e = this.getPageElement("a0192e").getValue();
		condMap.put("a0192e", a0192e);
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
		}

		// 任职级时间
		String a0192cA = this.getPageElement("a0192cA").getValue();
		String a0192cB = this.getPageElement("a0192cB").getValue();
		condMap.put("a0192cA", a0192cA);
		condMap.put("a0192cB", a0192cB);
		if (!"".equals(a0192cA)) {
			a01sb.append(" and a01.a0192c>='" + a0192cA + "'");
		}
		if (!"".equals(a0192cB)) {
			a01sb.append(" and a01.a0192c<='" + a0192cB + "'");
		}
		
		//简历
		String a1701 = this.getPageElement("a1701").getValue();
		if(a1701==null) {
			condMap.put("a1701", "");
		}else {
			condMap.put("a1701", a1701);
		}
		if(a1701!=null&&!"".equals(a1701)) {
			a01sb.append(" a01.a1701 like '%" +a1701+ "%' ");
		}
		
		//熟悉领域
		String a0196z = this.getPageElement("a0196z").getValue();
		condMap.put("a0196z", a0196z);
		if(a0196z!=null&&!"".equals(a0196z)) { 
			String[] strs = a0196z.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from attr_lrzw where 1=2 or ");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append(" "+strs[i]+" = '1' ");
				if(i!=strs.length-1) {
					orther_sb.append(" or ");
				}
			}
			orther_sb.append(") ");
		}

		//两头干部
		String a0196c = this.getPageElement("a0196c").getValue();
		condMap.put("a0196c", a0196c);
		if(a0196c!=null&&!"".equals(a0196c)) { 
			String[] strs = a0196c.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from extra_tags where 1=2 or a0196c in (");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append("'"+strs[i]+"'");
				if(i!=strs.length-1) {
					orther_sb.append(",");
				}
			}
			orther_sb.append(")) ");
		}
		
		
		//熟悉领域
		String A0194_TAG = this.getPageElement("A0194_TAG").getValue();
		condMap.put("A0194_TAG", A0194_TAG);
		if(A0194_TAG!=null&&!"".equals(A0194_TAG)) { 
			String[] strs = A0194_TAG.split(",");
			orther_sb.append(" and a01.a0000 in (select a0000 from zjs_A0196_TAG where 1=2 or a0196 in (");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append("'"+strs[i]+"'");
				if(i!=strs.length-1) {
					orther_sb.append(",");
				}
			}
			orther_sb.append(")) ");
		}
		//任职经历新
		String newRZJL = this.getPageElement("newRZJL").getValue();
		condMap.put("newRZJL", newRZJL);
		int flag1=0;
		int flag2=0;
		if(newRZJL!=null&&!"".equals(newRZJL)) { 
			String[] strs = newRZJL.split(",");
	//		orther_sb.append(" and a01.a0000 in (select a0000 from hz_a17 where 1=2 or a1705 in (");
			//循环类型1
			for(int i=0;i<strs.length;i++) {
				String type=strs[i].split("_")[0];
				String value=strs[i].split("_")[1];
				if("1".equals(type)) {
					if(flag1==0) {
						orther_sb.append(" and (a01.a0000 in (select a0000 from hz_a17 where 1=2 or a1705 in (");
					}
					orther_sb.append("'"+value+"'");
					orther_sb.append(",");
					flag1+=1;		
				}	
			}
			if(flag1>0) {
				orther_sb.deleteCharAt(orther_sb.length()-1);
				orther_sb.append(")) ");
			}
			//循环类型2
			for(int i=0;i<strs.length;i++) {
				String type=strs[i].split("_")[0];
				String value=strs[i].split("_")[1];
				if("2".equals(type)) {
					if(flag2==0 && flag1==0) {
						orther_sb.append(" and a01.a0000 in (select a0000 from zjs_A0193_TAG where 1=2 or a0193 in (");
					}else if(flag2==0 && flag1>0) {
						orther_sb.append(" or a01.a0000 in (select a0000 from zjs_A0193_TAG where 1=2 or a0193 in (");
					}
					orther_sb.append("'"+value+"'");
					orther_sb.append(",");
					flag2+=1;		
				}	
			}
			if(flag2>0 && flag1>0) {
				orther_sb.deleteCharAt(orther_sb.length()-1);
				orther_sb.append("))) ");
			}else if(flag2>0) {
				orther_sb.deleteCharAt(orther_sb.length()-1);
				orther_sb.append(")) ");
			}else {
				orther_sb.append(") ");
			}		
		}
		//选调生
		String a99z103= this.getPageElement("a99z103").getValue();
		if(a99z103==null) {
			condMap.put("a99z103", "");
		}else {
			condMap.put("a99z103", a99z103);
		}
		String a99z104A = this.getPageElement("a99z104A").getValue();
		condMap.put("a99z104A", a99z104A);
		String a99z104B = this.getPageElement("a99z104B").getValue();// 出生年月
		condMap.put("a99z104B", a99z104B);
		
		if(a99z103!=null&&!"".equals(a99z103)&&"1".equals(a99z103)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a99z1 where a99z103='1') ");
		}
		if (!"".equals(a99z104A)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a99z1 where a99z104>='"+a99z104A+"') ");
		}
		if (!"".equals(a99z104B)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a99z1 where a99z104<='"+a99z104B+"') ");
		}
		


		String a0188 = this.getPageElement("a0188").getValue();
		System.out.println(a0188+"aaaaaaaaaaaaaaaa");
		
		if(a0188==null) {
			condMap.put("a0188", "");
		}else {
			condMap.put("a0188", a0188);
		}
		if(a0188!=null&&!"".equals(a0188)&&"1".equals(a0188)) {
			a01sb.append(" a01.a0188='" +a0188+ "' ");
		}

		String a0132 = this.getPageElement("a0132").getValue();
		if(a0132==null) {
			condMap.put("a0132", "");
		}else {
			condMap.put("a0132", a0132);
		}
		if(a0132!=null&&!"".equals(a0132)&&"1".equals(a0132)) {
			a01sb.append(" a01.a0132='" +a0132+ "' ");
		}

		String a0133 = this.getPageElement("a0133").getValue();
		if(a0133==null) {
			condMap.put("a0133", "");
		}else {
			condMap.put("a0133", a0133);
		}
		if(a0133!=null&&!"".equals(a0133)&&"1".equals(a0133)) {
			a01sb.append(" a01.a0133='" +a0133+ "' ");
		}
		
		//党龄
		String a0144age=this.getPageElement("a0144age").getValue();
		condMap.put("a0144age", a0144age);
		if(a0144age!=null&&!"".equals(a0144age)) {
			orther_sb.append(" and TRUNC((to_char(sysdate, 'yyyyMM') - substr(a01.a0144,0,6)) /100)>="+a0144age+"  ");

		}

		//重要任职经历
		String a0194c = this.getPageElement("a0194c").getValue();
		condMap.put("a0194c", a0194c);
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
		}
		
		//重点岗位
//		String a1705 = this.getPageElement("a1705").getValue();
//		condMap.put("a1705", a1705);
//		if(a1705!=null&&!"".equals(a1705)) {
//			String[] strs = a1705.split(","); 
//			orther_sb.append(" and a01.a0000 in (select a0000 from hz_a17 where 1=2 or ");
//			for(int i=0;i<strs.length;i++) {
//				orther_sb.append(" a1705 = '"+strs[i]+"' ");
//				if(i!=strs.length-1) {
//					orther_sb.append(" or ");
//				}
//			}
//			orther_sb.append(") ");
//		}
		
		//zdgwq重点岗位
		String zdgwq = this.getPageElement("zdgwq").getValue();
		condMap.put("zdgwq", zdgwq);
		if(zdgwq!=null&&!"".equals(zdgwq)) {
			String[] strs = zdgwq.split(",");
			orther_sb.append(" and ( a01.a0000 in  ");
			for(int i=0;i<strs.length;i++) {
				List list = HBUtil.getHBSession().createSQLQuery("select code_remark   from code_value where code_type='ZDGWBQ' and  code_value='"+strs[i]+"'").list();
				String ids = org.apache.commons.lang.StringUtils.join(list.toArray(),",");
				if(ids!=null&&!"".equals(ids)) {
					orther_sb.append(" "+ids+"  ");
					if(i!=strs.length-1) {
						orther_sb.append(" or a0000 in ");
					}
				}
			}
			orther_sb.append(") ");
		}
		
		//分管工作类型
		String a1706 = this.getPageElement("a1706").getValue();
		condMap.put("a1706", a1706);
		if(a1706!=null&&!"".equals(a1706)) {
			String[] strs = a1706.split(","); 
			orther_sb.append(" and a01.a0000 in (select a0000 from hz_a17 where 1=2 or ");
			for(int i=0;i<strs.length;i++) {
				orther_sb.append(" a1706 = '"+strs[i]+"' ");
				if(i!=strs.length-1) {
					orther_sb.append(" or ");
				}
			}
			orther_sb.append(") ");
		}
		
		
		//是否为现任
		String sfwxr = this.getPageElement("sfwxr").getValue();
		if(sfwxr==null) {
			condMap.put("sfwxr", "");
		}else {
			condMap.put("sfwxr", sfwxr);
		}
		if(sfwxr!=null&&!"".equals(sfwxr)&&"01".equals(sfwxr)) {
			String a=orther_sb.toString().replaceAll("from hz_a17 where","from hz_a17 where a1701 is not null and a1702 is null and ");
			orther_sb=new StringBuffer(a);
		}
//		else {
//			String a=orther_sb.toString().replaceAll("from hz_a17 where","from hz_a17 where a1702 is not null and ");
//			orther_sb=new StringBuffer(a);
//		}
		//是否为历任
//		if(sfwxr!=null&&!"".equals(sfwxr)&&"02".equals(sfwxr)) {
//			
//		}
		// 最高学历
		String xla0801b = this.getPageElement("xla0801bv").getValue();// 最高学历 学历代码
		String xla0814 = this.getPageElement("xla0814").getValue();// 毕业院校
		String xla0824 = this.getPageElement("xla0824").getValue();// 专业
		xuelixueweiSQL(xla0801b, xla0814, xla0824, orther_sb, "a0834", "a0801b", isgbk, querydb);
		condMap.put("xla0801b", xla0801b);
		condMap.put("xla0814", xla0814);
		condMap.put("xla0824", xla0824);

		// 最高学位
		String xwa0901b = this.getPageElement("xwa0901bv").getValue();// 最高学位 学位代码
		String xwa0814 = this.getPageElement("xwa0814").getValue();// 毕业院校
		String xwa0824 = this.getPageElement("xwa0824").getValue();// 专业
		xuelixueweiSQL(xwa0901b, xwa0814, xwa0824, orther_sb, "a0835", "a0901b", isgbk, querydb);
		condMap.put("xwa0901b", xwa0901b);
		condMap.put("xwa0814", xwa0814);
		condMap.put("xwa0824", xwa0824);

		// 全日制最高学历
		String qrzxla0801b = this.getPageElement("qrzxla0801bv").getValue();// 最高学历 学历代码
		String qrzxla0814 = this.getPageElement("qrzxla0814").getValue();// 毕业院校
		String qrzxla0824 = this.getPageElement("qrzxla0824").getValue();// 专业
		xuelixueweiSQL(qrzxla0801b, qrzxla0814, qrzxla0824, orther_sb, "a0831", "a0801b", isgbk, querydb);
		condMap.put("qrzxla0801b", qrzxla0801b);
		condMap.put("qrzxla0814", qrzxla0814);
		condMap.put("qrzxla0824", qrzxla0824);

		// 全日制最高学位
		String qrzxwa0901b = this.getPageElement("qrzxwa0901bv").getValue();// 最高学位 学位代码
		String qrzxwa0814 = this.getPageElement("qrzxwa0814").getValue();// 毕业院校
		String qrzxwa0824 = this.getPageElement("qrzxwa0824").getValue();// 专业
		xuelixueweiSQL(qrzxwa0901b, qrzxwa0814, qrzxwa0824, orther_sb, "a0832", "a0901b", isgbk, querydb);
		condMap.put("qrzxwa0901b", qrzxwa0901b);
		condMap.put("qrzxwa0814", qrzxwa0814);
		condMap.put("qrzxwa0824", qrzxwa0824);

		// 在职最高学历
		String zzxla0801b = this.getPageElement("zzxla0801bv").getValue();// 最高学历 学历代码
		String zzxla0814 = this.getPageElement("zzxla0814").getValue();// 毕业院校
		String zzxla0824 = this.getPageElement("zzxla0824").getValue();// 专业
		xuelixueweiSQL(zzxla0801b, zzxla0814, zzxla0824, orther_sb, "a0838", "a0801b", isgbk, querydb);
		condMap.put("zzxla0801b", zzxla0801b);
		condMap.put("zzxla0814", zzxla0814);
		condMap.put("zzxla0824", zzxla0824);

		// 在职最高学位
		String zzxwa0901b = this.getPageElement("zzxwa0901bv").getValue();// 最高学位 学位代码
		String zzxwa0814 = this.getPageElement("zzxwa0814").getValue();// 毕业院校
		String zzxwa0824 = this.getPageElement("zzxwa0824").getValue();// 专业
		xuelixueweiSQL(zzxwa0901b, zzxwa0814, zzxwa0824, orther_sb, "a0839", "a0901b", isgbk, querydb);
		condMap.put("zzxwa0901b", zzxwa0901b);
		condMap.put("zzxwa0814", zzxwa0814);
		condMap.put("zzxwa0824", zzxwa0824);

		// 奖惩
		String a14z101 = this.getPageElement("a14z101").getValue();// 奖惩描述
		String lba1404b = this.getPageElement("lba1404bv").getValue();// 奖惩类别
		String a1404b = this.getPageElement("a1404b").getValue();// 奖惩名称代码
		String a1415 = this.getPageElement("a1415").getValue();// 受奖惩时职务层次
		String a1414 = this.getPageElement("a1414").getValue();// 批准机关级别
		String a1428 = this.getPageElement("a1428").getValue();// 批准机关性质
		condMap.put("a14z101", a14z101);
		condMap.put("lba1404b", lba1404b);
		condMap.put("a1404b", a1404b);
		condMap.put("a1415", a1415);
		condMap.put("a1414", a1414);
		condMap.put("a1428", a1428);

		if (!"".equals(a14z101) || !"".equals(lba1404b) || !"".equals(a1404b) || !"".equals(a1415) || !"".equals(a1414)
				|| !"".equals(a1428)) {
			if (isgbk) {
				orther_sb.append(" and a01.a0000 in (select a0000 from a14 where 1=1 ");
			} else {
				orther_sb.append(" and a01.a0000 in (select a0000 from v_js_a14 a14 where a14.v_xt='" + querydb + "' ");
			}
			if (!"".equals(a14z101)) {
				orther_sb.append(" and a14z101 like '%" + a14z101 + "%'");
			}

			if (!"".equals(lba1404b)) {
				StringBuffer like_sb = new StringBuffer("");
				String[] fArray = lba1404b.split(",");
				for (int i = 0; i < fArray.length; i++) {
					like_sb.append(" a1404b like '" + fArray[i] + "%' or ");
				}
				like_sb.delete(like_sb.length() - 4, like_sb.length() - 1);
				orther_sb.append(" and (" + like_sb.toString() + ")");
			}
			if (!"".equals(a1404b)) {
				orther_sb.append(" and a1404b='" + a1404b + "'");
			}
			if (!"".equals(a1415)) {
				orther_sb.append(" and a1415='" + a1415 + "'");
			}
			if (!"".equals(a1414)) {
				orther_sb.append(" and a1414='" + a1414 + "'");
			}
			if (!"".equals(a1428)) {
				orther_sb.append(" and a1428='" + a1428 + "'");
			}
			orther_sb.append(")");
		}

		// 年度考核
		String a15z101 = this.getPageElement("a15z101").getValue();// 年度考核描述
		String a1521 = this.getPageElement("a1521").getValue();// 考核年度
		String a1517 = this.getPageElement("a1517").getValue();// 考核结论类别
		condMap.put("a15z101", a15z101);
		condMap.put("a1521", a1521);
		condMap.put("a1517", a1517);
		if (!"".equals(a15z101) || !"".equals(a1521) || !"".equals(a1517)) {
			if (isgbk) {
				orther_sb.append(" and a01.a0000 in (select a0000 from a15 where 1=1 ");
			} else {
				orther_sb.append(" and a01.a0000 in (select a0000 from v_js_a15 a15 where a15.v_xt='" + querydb + " ");
			}
			if (!"".equals(a15z101)) {
				orther_sb.append(" and a15z101 like '%" + a15z101 + "%'");
			}

			if (!"".equals(a1521)) {
				a1521 = a1521.replace(",", "','");
				orther_sb.append(" and a1521 in('" + a1521 + "')");
			}

			if (!"".equals(a1517)) {
				orther_sb.append(" and a1517='" + a1517 + "'");
			}

			orther_sb.append(")");
		}

		// 专业技术
		String a0601a = this.getPageElement("a0601a").getValue();
		condMap.put("a0601a", a0601a);
		if (!"".equals(a0601a)) {
			orther_sb.append(" and a01.a0000 = ( select a0000 from a06 where a0601='" + a0601a + "')");

		}

		// 重大经历
		/*
		 * String a0193s = this.getPageElement("a0193s").getValue(); String a0193z =
		 * this.getPageElement("a0193z").getValue(); condMap.put("a0193s", a0193s);
		 * condMap.put("a0193z", a0193z); if (!"".equals(a0193s)) { a0193s =
		 * a0193s.replace("；", "','");
		 * orther_sb.append(" and a01.a0000 in (select a0000 from a0193_tag where 1=1 "
		 * ); orther_sb.append(" ann a0193 in ('" + a0193s + "')) "); }
		 */

		// 熟悉领域
		/*
		 * String a0194s = this.getPageElement("a0194s").getValue(); String a0194z =
		 * this.getPageElement("a0194z").getValue(); condMap.put("a0194s", a0194s);
		 * condMap.put("a0194z", a0194z); if (!"".equals(a0194s)) { a0194s =
		 * a0194s.replace("；", "','");
		 * orther_sb.append(" and a01.a0000 in (select a0000 from a0194_tag where 1=1 "
		 * ); orther_sb.append(" and a0194 in ('" + a0194s + "'))");
		 * 
		 * }
		 */

		String a0163 = this.getPageElement("a0163").getValue();// 人员状态
		String qtxzry = "0";
		condMap.put("a0163", a0163);
		condMap.put("qtxzry", qtxzry);
		condMap.put("b0131A", b0131A);
		System.out.println("555555555"+condMap);
		String finalsql = CommSQL.getCondiQuerySQL(userID, a01sb, a01sb1 ,a02sb, a02_a0201b_sb, cu_b0111_sb, orther_sb, a0163,
				qtxzry, sid, isgbk, querydb);
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("sql", finalsql);
		retMap.put("cond", JSONObject.fromObject(condMap).toString());
		return retMap;
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
				remark.append(" 姓名为: " + condMap.get("a0101")+";");
			}

			//人员状态
			this.getPageElement("a0163").setValue("1");

			//机构分类
			this.getPageElement("b0131A").setValue(condMap.get("b0131A"));
			if(condMap.get("b0131A")!=null && !"".equals(condMap.get("b0131A"))) {
				System.out.println("eeeeeeeeeeeee"+condMap.get("b0131A"));
				String [] arr = condMap.get("b0131A").split(",");
				String str = "";
				if(condMap.get("b0131A").equals("01")) {
					str= "市直党委部门";
				}else if(condMap.get("b0131A").equals("02")) {
					str= "市直政府部门";	
				}else if(condMap.get("b0131A").equals("03")) {
					str= "区县市党委部门";	
				}else if(condMap.get("b0131A").equals("04")) {
					str= "区县市政府部门";	
				}else if(condMap.get("b0131A").equals("01,02") ) {
					str= "市直党委部门,市直政府部门";	
				}else if(condMap.get("b0131A").equals("01,03") ) {
					str= "市直党委部门,区县市党委部门";	
				}else if(condMap.get("b0131A").equals("01,04") ) {
					str= "市直政府,区县市政府部门";	
				}else if(condMap.get("b0131A").equals("02,03") ) {
					str= "市直政府部门,区县市党委部门";	
				}else if(condMap.get("b0131A").equals("02,04") ) {
					str= "市直政府部门,区县市政府部门";	
				}else if(condMap.get("b0131A").equals("03,04")) {
					str= "区县市党委部门,区县市政府部门";	
				}else if(condMap.get("b0131A").equals("01,02,03") ) {
					str= "市直党委部门,市直政府部门,区县市党委部门";	
				}else if(condMap.get("b0131A").equals("01,02,04") ) {
					str= "市直党委部门,市直政府部门,区县市政府部门";		
				}else if(condMap.get("b0131A").equals("01,03,04") ) {
					str= "市直党委部门,区县市党委部门,区县市政府部门";		
				}else if(condMap.get("b0131A").equals("02,03,04")) {
					str= "市直政府部门,区县市党委部门,区县市政府部门";			
				}else {
					str= "市直党委部门,市直政府部门,区县市党委部门,区县市政府部门";	
				}
				System.out.println(str+"----------");
				remark.append("机构类别包含"+str+";");
			}

			//省管干部
			this.getPageElement("a0165A").setValue(condMap.get("a0165A"));
			if (condMap.get("a0165A") != null && !"".equals(condMap.get("a0165A"))) {
				String[] arr = condMap.get("a0165A").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ZB130", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				System.out.println(str+"----------");
				remark.append(" 省管干部类别包含: " + str+";");
			}
			//市管干部
			this.getPageElement("a0165B").setValue(condMap.get("a0165B"));
			if (condMap.get("a0165B") != null && !"".equals(condMap.get("a0165B"))) {
				String[] arr = condMap.get("a0165B").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ZB130", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				remark.append(" 市管干部类别包含: " + str+";");
			}
			//处级（中层）干部
			this.getPageElement("a0165C").setValue(condMap.get("a0165C"));
			if (condMap.get("a0165C") != null && !"".equals(condMap.get("a0165C"))) {
				String[] arr = condMap.get("a0165C").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ZB130", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				remark.append(" 处级（中层）干部类别包含: " + str+";");
			}

			//性别
			this.getPageElement("a0104").setValue(condMap.get("a0104"));
			if (!"".equals(condMap.get("a0104"))&&!"0".equals(condMap.get("a0104"))) {
				remark.append(" 性别为: " + HBUtil.getCodeName("GB2261", condMap.get("a0104"))+";");
			}

			//民族
			this.getPageElement("a0117").setValue(condMap.get("a0117"));
			if (!"".equals(condMap.get("a0117"))&&!"0".equals(condMap.get("a0117"))) {
				if("1".equals(condMap.get("a0117"))) {
					remark.append(" 民族为: 汉族;");
				}else if("2".equals(condMap.get("a0117"))) {
					remark.append(" 民族为: 少数民族;");
				}
			}

			//党派
			this.getPageElement("a0141").setValue(condMap.get("a0141"));
			if (condMap.get("a0141") != null && !"".equals(condMap.get("a0141"))) {
				String[] arr = condMap.get("a0141").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("GB4762", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				this.getPageElement("a0141_combotree").setValue(str);
				remark.append(" 党派包含: " + str+";");
			}

			// 年龄
			this.getPageElement("ageA").setValue(condMap.get("ageA"));// 年龄
			this.getPageElement("ageB").setValue(condMap.get("ageB"));// 年龄
			if (condMap.get("ageA")!=null&&!"".equals(condMap.get("ageB")) && !"".endsWith(condMap.get("ageA"))) {
				remark.append(" 年龄: " + condMap.get("ageA") + "到" + condMap.get("ageB")+";");

			}
			if (!"".equals(condMap.get("ageA")) && "".equals(condMap.get("ageB"))) {
				remark.append(" 年龄: 大于等于" + condMap.get("ageA")+";");
			}
			if (!"".equals(condMap.get("ageB")) && "".equals(condMap.get("ageA"))) {
				remark.append(" 年龄: 小于" + condMap.get("ageB")+";");
			}

			// 出生年月
			this.getPageElement("a0107A").setValue(condMap.get("a0107A"));// 出生年月
			this.getPageElement("a0107B").setValue(condMap.get("a0107B"));// 出生年月
			if (condMap.get("a0107B")!=null&&!"".equals(condMap.get("a0107A")) && !"".endsWith(condMap.get("a0107B"))) {
				remark.append(" 出生年月: 在" + condMap.get("a0107A") + "," + condMap.get("a0107B") + "之间;");

			}
			if (!"".equals(condMap.get("a0107A")) && "".equals(condMap.get("a0107B"))) {
				remark.append(" 出生年月: 大于等于" + condMap.get("a0107A")+";");
			}
			if (!"".equals(condMap.get("a0107B")) && "".equals(condMap.get("a0107A"))) {
				remark.append(" 出生年月: 小于" + condMap.get("a0107B")+";");
			}

			//学历学位
			this.getPageElement("xlxw").setValue(condMap.get("xlxw"));
			if (condMap.get("xlxw")!=null&&!"".equals(condMap.get("xlxw"))) {
				String[] arr = condMap.get("xlxw").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + arr[i] + ",";
				}
				str=str.substring(0,str.length()-1);
				this.getPageElement("qrz").setValue(condMap.get("qrz"));
				if(condMap.get("qrz")!=null&&!"".equals(condMap.get("qrz"))&&"1".equals(condMap.get("qrz"))) {
					this.getExecuteSG().addExecuteCode("document.getElementById('qrz').checked='true'");
					remark.append(" 全日制学历学位为: " + str+";");
				}else {
					remark.append(" 学历学位为: " + str+";");
				}
			}

			//专业
			this.getPageElement("a0824").setValue(condMap.get("a0824"));
			if (condMap.get("a0824") != null && !"".equals(condMap.get("a0824"))) {
				remark.append(" 专业包含: " + condMap.get("a0824")+";");
			}
			
			//现职务时间
			this.getPageElement("a0192fA").setValue(condMap.get("a0192fA"));// 现职务时间
			this.getPageElement("a0192fB").setValue(condMap.get("a0192fB"));// 现职务时间
			if (condMap.get("a0192fB")!=null&&!"".equals(condMap.get("a0192fA")) && !"".endsWith(condMap.get("a0192fB"))) {
				remark.append(" 现职务时间: 在" + condMap.get("a0192fA") + "," + condMap.get("a0192fB") + "之间;");
			}
			if (!"".equals(condMap.get("a0192fA")) && "".equals(condMap.get("a0192fB"))) {
				remark.append(" 现职务时间: 大于等于" + condMap.get("a0192fA")+";");
			}
			if (!"".equals(condMap.get("a0192fB")) && "".equals(condMap.get("a0192fA"))) {
				remark.append(" 现职务时间: 小于" + condMap.get("a0192fB")+";");
			}

			this.getPageElement("a0221A").setValue(condMap.get("a0221A"));// 职务层次 暂时不回显
			if (condMap.get("a0221A")!=null&&!"".equals(condMap.get("a0221A"))) {
				String[] arr = condMap.get("a0221A").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ZB09", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				this.getPageElement("a0221A_combotree").setValue(str);
				remark.append(" 职务层次包含: " + str+";");
			}

			this.getPageElement("a0288A").setValue(condMap.get("a0288A"));// 任职务层次时间
			this.getPageElement("a0288B").setValue(condMap.get("a0288B"));// 任职务层次时间
			if (condMap.get("a0288B")!=null&&!"".equals(condMap.get("a0288A")) && !"".endsWith(condMap.get("a0288B"))) {
				remark.append(" 任职务层次时间: 在" + condMap.get("a0288A") + "," + condMap.get("a0288B") + "之间;");

			}
			if (!"".equals(condMap.get("a0288A")) && "".equals(condMap.get("a0288B"))) {
				remark.append(" 任职务层次时间: 大于等于" + condMap.get("a0288A")+";");
			}
			if (!"".equals(condMap.get("a0288B")) && "".equals(condMap.get("a0288A"))) {
				remark.append(" 任职务层次时间: 小于" + condMap.get("a0288B")+";");
			}

			this.getPageElement("a0192e").setValue(condMap.get("a0192e"));// 现职级
			if (condMap.get("a0192e") != null && !"".equals(condMap.get("a0192e"))) {
				String[] arr = condMap.get("a0192e").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ZB148", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				this.getPageElement("a0192e_combotree").setValue(str);
				remark.append(" 现职级包含: " + str+";");
			}

			this.getPageElement("a0192cA").setValue(condMap.get("a0192cA"));// 任职级时间
			this.getPageElement("a0192cB").setValue(condMap.get("a0192cB"));// 任职级时间
			if (condMap.get("a0192cB")!=null&&!"".equals(condMap.get("a0192cA")) && !"".endsWith(condMap.get("a0192cB"))) {
				remark.append(" 任职级时间: 在" + condMap.get("a0192cA") + "," + condMap.get("a0192cB") + "之间;");

			}
			if (!"".equals(condMap.get("a0192cA")) && "".equals(condMap.get("a0192cB"))) {
				remark.append(" 任职级时间: 大于等于" + condMap.get("a0192cA")+";");
			}
			if (!"".equals(condMap.get("a0192cB")) && "".equals(condMap.get("a0192cA"))) {
				remark.append(" 任职级时间: 小于  " + condMap.get("a0192cB")+";");
			}

			//简历
			this.getPageElement("a1701").setValue(condMap.get("a1701"));// 人员简历
			if (condMap.get("a1701") != null && !"".equals(condMap.get("a1701"))) {
				remark.append(" 简历包含: " + condMap.get("a1701")+";");
			}
			
			//熟悉领域
			this.getPageElement("a0196z").setValue(condMap.get("a0196z"));
			if (condMap.get("a0196z") != null && !"".equals(condMap.get("a0196z"))) {
				String[] arr = condMap.get("a0196z").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("EXTRA_TAGS", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				remark.append(" 专业类型包含: " + str+";");
			}
			//两头干部
			this.getPageElement("a0196c").setValue(condMap.get("a0196c"));
			if (condMap.get("a0196c") != null && !"".equals(condMap.get("a0196c"))) {
				String[] arr = condMap.get("a0196c").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("EXTRA_A0196C", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				remark.append(" 两头干部包含: " + str+";");
			}
			
			//熟悉领域
			this.getPageElement("A0194_TAG").setValue(condMap.get("A0194_TAG"));
			if (condMap.get("A0194_TAG") != null && !"".equals(condMap.get("A0194_TAG"))) {
				String[] arr = condMap.get("A0194_TAG").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("SXLY2", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				this.getPageElement("A0194_TAG_combotree").setValue(str);
				remark.append(" 熟悉领域包含: " + str+";");
			}
			
			//任职经历
			this.getPageElement("newRZJL").setValue(condMap.get("newRZJL"));
			if (condMap.get("newRZJL") != null && !"".equals(condMap.get("newRZJL"))) {
				String[] arr = condMap.get("newRZJL").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("RZJL2", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				this.getPageElement("newRZJL_combotree").setValue(str);
				remark.append(" 任职经历（新）包含: " + str+";");
			}
			//选调生
			this.getPageElement("a99z103").setValue(condMap.get("a99z103"));
			this.getPageElement("a99z104A").setValue(condMap.get("a99z104A"));
			this.getPageElement("a99z104B").setValue(condMap.get("a99z104B"));
//			String a99z103= this.getPageElement("a99z103").getValue();
//			String a99z104A = this.getPageElement("a99z104A").getValue();
//			String a99z104B = this.getPageElement("a99z104B").getValue();// 出生年月
			if(condMap.get("a99z103")!=null&&!"".equals(condMap.get("a99z103"))&&"1".equals(condMap.get("a99z103"))) {
				this.getExecuteSG().addExecuteCode("document.getElementById('a99z103').checked='true';");
				remark.append(" 为选调生");
			}
			
			//是否为现任
			this.getPageElement("sfwxr").setValue(condMap.get("sfwxr"));
			if(condMap.get("sfwxr")!=null&&!"".equals(condMap.get("sfwxr"))&&"01".equals(condMap.get("sfwxr"))) {
				this.getExecuteSG().addExecuteCode("document.getElementById('sfwxr').checked='true';");
				remark.append(" 是否为现任");
			}
			//是否为历任
			if(condMap.get("sfwxr")!=null&&!"".equals(condMap.get("sfwxr"))&&"02".equals(condMap.get("sfwxr"))) {
				this.getExecuteSG().addExecuteCode("document.getElementById('sfwxr').checked='true';");
				remark.append(" 是否为历任");
			}
			
			if (!"".equals(condMap.get("a99z104A"))) {
				remark.append(" 选调为选调生时间大于"+condMap.get("a99z104A")+";");
			}
			if (!"".equals(condMap.get("a99z104B"))) {
				remark.append(" 选调为选调生时间小于"+condMap.get("a99z104B")+";");
			}
			
			
			this.getPageElement("a0188").setValue(condMap.get("a0188"));
			if(condMap.get("a0188")!=null&&!"".equals(condMap.get("a0188"))&&"1".equals(condMap.get("a0188"))) {
				this.getExecuteSG().addExecuteCode("document.getElementById('a0188').checked='true';");
				remark.append(" 具有乡镇（街道）党政正职经历;");
			}

			this.getPageElement("a0132").setValue(condMap.get("a0132"));
			if(condMap.get("a0132")!=null&&!"".equals(condMap.get("a0132"))&&"1".equals(condMap.get("a0132"))) {
				this.getExecuteSG().addExecuteCode("document.getElementById('a0132').checked='true';");
				remark.append(" 现任乡镇（街道）党（工）委书记;");
			}

			this.getPageElement("a0133").setValue(condMap.get("a0133"));
			if(condMap.get("a0133")!=null&&!"".equals(condMap.get("a0133"))&&"1".equals(condMap.get("a0133"))) {
				this.getExecuteSG().addExecuteCode("document.getElementById('a0133').checked='true';");
				remark.append(" 现任乡镇（街道）镇长（主任）;");
			}
			
			
			//党龄
			if (condMap.get("a0144age") != null && !"".equals(condMap.get("a0144age"))) {
				String a0144age= condMap.get("a0144age");
				this.getPageElement("a0144age").setValue(a0144age);
				remark.append(" 党龄大于等于: " + a0144age+"年;");
			}

			//重要任职经历

			if (condMap.get("a0194c") != null && !"".equals(condMap.get("a0194c"))) {
				String[] arr = condMap.get("a0194c").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ATTR_LRZW", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				this.getPageElement("a0194c_combotree").setValue(str);
				remark.append(" 重要任职经历包含: " + str+";");
			}
			
			//重点岗位
//			this.getPageElement("a1705").setValue(condMap.get("a1705"));
//			if (condMap.get("a1705") != null && !"".equals(condMap.get("a1705"))) {
//				String[] arr = condMap.get("a1705").split(",");
//				String str = "";
//				for (int i = 0; i < arr.length; i++) {
//					str = str + HBUtil.getCodeName("JL02", arr[i]) + ",";
//				}
//				str=str.substring(0,str.length()-1);
//				this.getPageElement("a1705_combotree").setValue(str);
//				remark.append(" 重点岗位包含: " + str+";");
//			}
			
			//zdgwq重点岗位
			this.getPageElement("zdgwq").setValue(condMap.get("zdgwq"));
			if(condMap.get("zdgwq") != null && !"".equals(condMap.get("zdgwq"))) {
				String[] arr = condMap.get("zdgwq").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("ZDGWBQ", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				this.getPageElement("zdgwq_combotree").setValue(str);
				remark.append(" 重点岗位包含: " + str+";");
			}
			
			//分管工作类型
			this.getPageElement("a1706").setValue(condMap.get("a1706"));
			if (condMap.get("a1706") != null && !"".equals(condMap.get("a1706"))) {
				String[] arr = condMap.get("a1706").split(",");
				String str = "";
				for (int i = 0; i < arr.length; i++) {
					str = str + HBUtil.getCodeName("EXTRA_TAGS", arr[i]) + ",";
				}
				str=str.substring(0,str.length()-1);
				this.getPageElement("a1706_combotree").setValue(str);
				remark.append(" 分管工作类型: " + str+";");
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

			// 重要经历
			this.getPageElement("a0193s").setValue(condMap.get("a0193s"));
			this.getPageElement("a0193z").setValue(condMap.get("a0193z"));
			if (!"".equals(condMap.get("a0193z")) && condMap.get("a0193z") != null) {
				remark.append("  重要经历包含: " + condMap.get("a0193z"));
			}
			this.getExecuteSG().addExecuteCode("showCheckbox();");

			this.getPageElement("queryid").setValue(query.getQueryid());
			this.getPageElement("queryname").setValue(query.getQueryname());

			SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyyMM");
			String datestr = myFmt1.format(new Date());
			this.getPageElement("jiezsj").setValue(datestr);
			this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4) + "." + datestr.substring(4, 6));

			// 查询综述的拼接
			if (remark != null && !"".equals(remark)) {
				String sInfo = "查询综述:   " + remark.toString();
				this.getPageElement("remark").setValue(sInfo);
				this.getExecuteSG().addExecuteCode("odin.ext.getCmp('remark').show();");
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




}
