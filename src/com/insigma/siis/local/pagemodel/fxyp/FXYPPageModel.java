package com.insigma.siis.local.pagemodel.fxyp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Cxtj;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.customquery.GroupPageBS;

public class FXYPPageModel extends PageModel {
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String userID = SysManagerUtils.getUserId();
		String sql="select fxyp02,fxyp03,fxyp00 from fxyp where fxyp05='"+userID+"'  order by fxyp04 desc ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 修改人员信息的双击事件
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("personGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("personGrid").getValue("a0000",this.getPageElement("personGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'personGrid',maximizable:false,resizable:false,draggable:false},true);");
			
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	@PageEvent("pic.dbclick")
	public int picdbclick(String a0000) throws RadowException, AppException{
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
		this.request.getSession().setAttribute("personIdSet", null);
		this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,630,null,"
				+ "{a0000:'"+a0000+"',gridName:'personGrid',maximizable:false,resizable:false,draggable:false},true);");
		
		this.setRadow_parent_data(a0000);
		return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	/**
	 * 人员信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("personGrid.dogridquery")
	public int personGridQuery(int start,int limit) throws RadowException{
		
		String sql=this.getPageElement("sql").getValue();
		this.request.getSession().setAttribute("sql_fxyp",sql);	
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("memberGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("closeWinIfExists();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("allDelete")
	@Transaction
	public int allDelete(String fxyp00) throws RadowException, AppException{
		
		try {
			HBSession sess = HBUtil.getHBSession();
			HBUtil.executeUpdate("delete from cxtj where fxyp00=?",new Object[]{fxyp00});
			HBUtil.executeUpdate("delete from fxyp where fxyp00=?",new Object[]{fxyp00});
			
			sess.flush();
			this.getExecuteSG().addExecuteCode("$('#fxyp00').val(''); $('#fxyp02').val('');infoSearch(false,false)");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveInfo")
	@Transaction
	public int saveInfo(String txt) throws RadowException, AppException{
		
		try {
			String fxyp00 = this.getPageElement("fxyp00").getValue();
			HBSession sess = HBUtil.getHBSession();
			HBUtil.executeUpdate("update fxyp set fxyp02=?  where fxyp00=?",new Object[]{txt,fxyp00});
			
			sess.flush();
			this.getExecuteSG().addExecuteCode("infoSearch(false,false)");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("addInfo")
	@Transaction
	public int addInfo(String txt) throws RadowException, AppException{
		
		try {
			String fxyp00 = UUID.randomUUID().toString();
			String userID = SysManagerUtils.getUserId();
			HBSession sess = HBUtil.getHBSession();
			HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05) values(?,?,?)",new Object[]{fxyp00,txt,userID});
			
			sess.flush();
			this.getExecuteSG().addExecuteCode("infoSearch(false,false)");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	
	
	/**
	 * 查询（注：范围查询中代码的大小与字面逻辑的高低正好相反，所以判断逻辑也是相反的处理）
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("mQueryonclick")
	public int query() throws RadowException, AppException{
		String userID = SysManagerUtils.getUserId();
       
        String sql = getSQL(userID);
        if(sql==null||"".equals(sql)){
        	sql="select 1 from dual";
        }
        this.getPageElement("sql").setValue(sql);
        String tableType = this.getPageElement("tableType").getValue();
        if("1".equals(tableType)){//列表模式
        	this.getExecuteSG().addExecuteCode("gridshow();");
        }else{//照片模式
        	this.getExecuteSG().addExecuteCode("datashow();");
        }
        //this.setNextEventName("personGrid.dogridquery");
        return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void xuelixueweiSQL(String a0801b, String a0814, String a0824, StringBuffer orther_sb, String highField,
			String xueliORxuewei) {
		StringBuffer a0801b_sb = new StringBuffer("");

		if (a0801b!=null&&!"".equals(a0801b)) {
			String[] a0801bArray = a0801b.split(",");
			for (int i = 0; i < a0801bArray.length; i++) {
				a0801b_sb.append(" " + xueliORxuewei + " like '" + a0801bArray[i] + "%' or ");
			}
			a0801b_sb.delete(a0801b_sb.length() - 4, a0801b_sb.length() - 1);
		}

		if ((a0801b!=null&&!"".equals(a0801b)) || (a0814!=null&&!"".equals(a0814)) || (a0824!=null&&!"".equals(a0824))) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a08 where " + highField + "='1' ");
			if (a0801b!=null&&!"".equals(a0801b)) {
				orther_sb.append(" and (" + a0801b_sb.toString() + ")");
			}

			if (a0814!=null&&!"".equals(a0814)) {
				orther_sb.append(" and a0814 like '%" + a0814 + "%'");
			}
			if (a0824!=null&&!"".equals(a0824)) {
				orther_sb.append(" and a0824 like '%" + a0824 + "%'");
			}
			orther_sb.append(")");
		}

	}



	@SuppressWarnings("unchecked")
	 String getSQL(String userID) throws AppException, RadowException{
		StringBuffer a01sb = new StringBuffer("");
		StringBuffer a02sb = new StringBuffer("");
		StringBuffer orther_sb = new StringBuffer("");

		String fxyp00 = this.getPageElement("fxyp00").getValue();
		
		List<Cxtj> cxtjList = HBUtil.getHBSession()
				.createQuery("from Cxtj where fxyp00=?").setString(0, fxyp00).list();
		
		if(cxtjList.size()==0){
			return null;
		}
		Map<String, String> cxtjMap = new HashMap<String, String>();
		for(Cxtj cxtj : cxtjList){
			cxtjMap.put(cxtj.getCxtj07(), cxtj.getCxtj04());
		}
		
		String a0195 = cxtjMap.get("a0195");//统计关系所在单位
		if (a0195!=null&&!"".equals(a0195)){
			a01sb.append(" and ");
			a01sb.append("a01.a0195 = '"+a0195+"'");
		}
		
		
		String a0101 = cxtjMap.get("a0101");//人员姓名
		if (a0101!=null&&!"".equals(a0101)){
			a01sb.append(" and ");
			a01sb.append("a01.a0101 like '"+a0101+"%'");
		}
		
		String a0184 = cxtjMap.get("a0184");//身份证号
		if (a0184!=null&&!"".equals(a0184)){
			a01sb.append(" and ");
			a01sb.append("a01.a0184 like '"+a0184.toUpperCase()+"%'");
		}
		
		String a0111 = cxtjMap.get("a0111");//籍贯
		if (a0111!=null&&!"".equals(a0111)){
			a0111 = a0111.replaceAll("(0){1,}$", "");
			a01sb.append(" and ");
			a01sb.append(" a01.a0111 like '"+a0111+"%' ");
		}
		
		
		
		String a0104 = cxtjMap.get("a0104");//性别
		if (a0104!=null&&!"".equals(a0104)&&!"0".equals(a0104)){
			a01sb.append(" and ");
			a01sb.append("a01.a0104 = '"+a0104+"'");
		}
		
		String ageA = cxtjMap.get("ageA");//年龄
		String ageB = cxtjMap.get("ageB");//年龄
		if(ageB!=null&&!"".equals(ageB) && StringUtils.isNumeric(ageB)){//是否数字
			ageB = Integer.valueOf(ageB)+1+"";
		}
		String jiezsj = cxtjMap.get("jiezsj");//截止时间
		String dateEnd = GroupPageBS.getDateformY(ageA, jiezsj);
		String dateStart = GroupPageBS.getDateformY(ageB, jiezsj);
		if(!"".equals(dateEnd)&&!"".equals(dateStart)&&dateEnd.compareTo(dateStart)<0){
			throw new AppException("年龄范围错误！");
		}
		if(!"".equals(dateStart)){
			a01sb.append(" and a01.a0107>='"+dateStart+"'");
		}
		if(!"".equals(dateStart)){
			a01sb.append(" and a01.a0107<'"+dateEnd+"'");
		}
		
		String a0160 = cxtjMap.get("a0160");//人员类别
		if (a0160!=null&&!"".equals(a0160)){
			a01sb.append(" and ");
			a01sb.append("a01.a0160 = '"+a0160+"'");
		}
		
		
		
		
		String a0107A = cxtjMap.get("a0107A");//出生年月
		String a0107B = cxtjMap.get("a0107B");//出生年月
		if(a0107A!=null&&!"".equals(a0107A)){
			a01sb.append(" and a01.a0107>='"+a0107A+"'");
		}
		if(a0107B!=null&&!"".equals(a0107B)){
			a01sb.append(" and a01.a0107<='"+a0107B+"'");
		}
		
		
		
		
		
		String a0144A = cxtjMap.get("a0144A");//参加中共时间
		String a0144B = cxtjMap.get("a0144B");//参加中共时间
		if(a0144A!=null&&!"".equals(a0144A)){
			a01sb.append(" and a01.a0144>='"+a0144A+"'");
		}
		if(a0144B!=null&&!"".equals(a0144B)){
			a01sb.append(" and a01.a0144<='"+a0144B+"'");
		}
		
		
		
		String a0141 = cxtjMap.get("a0141");//政治面貌
		if(a0141!=null&&!"".equals(a0141)){
			a0141 = a0141.replace(",", "','");
			a01sb.append(" and a01.a0141 in('"+a0141+"')");
			//a01sb.append(" and a01.a0141='"+a0141+"'");
		}
		
		
		String a0192a = cxtjMap.get("a0192a");//职务全称
		if(a0192a!=null&&!"".equals(a0192a)){
			a01sb.append(" and a01.a0192a like '%"+a0192a+"%'");
		}
		
		
		
		String a0134A = cxtjMap.get("a0134A");//参加工作时间
		String a0134B = cxtjMap.get("a0134B");//参加工作时间
		if(a0134A!=null&&!"".equals(a0134A)){
			a01sb.append(" and a01.a0134>='"+a0134A+"'");
		}
		if(a0134B!=null&&!"".equals(a0134B)){
			a01sb.append(" and a01.a0134<='"+a0134B+"'");
		}
		
		
		
		String a0114 = cxtjMap.get("a0114");//出生地
		if (a0114!=null&&!"".equals(a0114)){
			a0114 = a0114.replaceAll("(0){1,}$", "");
			a01sb.append(" and a01.a0114 like '"+a0114+"%' ");
		}
		
		
		
		String a0221A = cxtjMap.get("a0221A");//职务层次
		String a0221B = cxtjMap.get("a0221B");//职务层次
		if(!StringUtil.isEmpty(a0221A) && !StringUtil.isEmpty(a0221B)){
			CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", a0221A);
			CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", a0221B);
			if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
				throw new AppException("职务层次范围不属于同一类别，请检查！");
			}
			//职务层次 值越小 字面意思越高级
			if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
				throw new AppException("职务层次范围不正确，请检查！");
			}
		}
		if(a0221A!=null&&!"".equals(a0221A)){
			a01sb.append(" and a01.a0221<='"+a0221A+"'");
		}
		if(a0221B!=null&&!"".equals(a0221B)){
			a01sb.append(" and a01.a0221>='"+a0221B+"'");
		}
		
		
		
		
		String a0288A = cxtjMap.get("a0288A");//任现职务层次时间
		String a0288B = cxtjMap.get("a0288B");//任现职务层次时间
		if(a0288A!=null&&!"".equals(a0288A)){
			a01sb.append(" and a01.a0288>='"+a0288A+"'");
		}
		if(a0288B!=null&&!"".equals(a0288B)){
			a01sb.append(" and a01.a0288<='"+a0288B+"'");
		}
		
		String a0117 = cxtjMap.get("a0117");//名族
		if(a0117!=null&&!"".equals(a0117)){
			String[] a0117s = a0117.split(",");
			if(a0117s.length==1){
				if("01".equals(a0117s[0]))
					a01sb.append(" and a01.a0117 ='01'");
				else
					a01sb.append(" and a01.a0117 !='01'");
			}
			
		}
		
		
		
		
		String a1701 = cxtjMap.get("a1701");//简历
		if(a1701!=null&&!"".equals(a1701)){
			a01sb.append(" and a01.a1701 like '%"+a1701+"%'");
		}
		
		
		
		
		String a0192e = cxtjMap.get("a0192e");//现职级
		if(a0192e!=null&&!"".equals(a0192e)){
			a01sb.append(" and a01.a0192e='"+a0192e+"'");
		}
		
		
		
		
		String a0192cA = cxtjMap.get("a0192cA");//任职级时间
		String a0192cB = cxtjMap.get("a0192cB");//任职级时间
		if(a0192cA!=null&&!"".equals(a0192cA)){
			a01sb.append(" and a01.a0192c>='"+a0192cA+"'");
		}
		if(a0192cB!=null&&!"".equals(a0192cB)){
			a01sb.append(" and a01.a0192c<='"+a0192cB+"'");
		}
		
		
		
		
		String a0165 = cxtjMap.get("a0165");//人员管理类别
		if(a0165!=null&&!"".equals(a0165)){
			a0165 = a0165.replace(",", "','");
			a01sb.append(" and a01.a0165 in('"+a0165+"')");
		}
		
		
		
		//职务 StringBuffer a02sb = new StringBuffer("");
		
		String a0216a = cxtjMap.get("a0216a");//职务名称
		if(a0216a!=null&&!"".equals(a0216a)){
			a02sb.append(" and a02.a0216a like '%"+a0216a+"%'");
		}
		
		
		
		String a0201d = cxtjMap.get("a0201d");//是否班子成员
		if(a0201d!=null&&!"".equals(a0201d)){
			a02sb.append(" and a02.a0201d='"+a0201d+"'");
		}
		
		
		
		
		String a0219 = cxtjMap.get("a0219");//是否领导职务
		if(a0219!=null&&!"".equals(a0219)){
			a02sb.append(" and a02.a0219='"+a0219+"'");
		}
		
		
		
		
		String a0201e = cxtjMap.get("a0201e");//成员类别
		if(a0201e!=null&&!"".equals(a0201e)){
			a0201e = a0201e.replace(",", "','");
			a02sb.append(" and a02.a0201e in('"+a0201e+"')");
		}
		
		
		//最高学历
		String xla0801b = cxtjMap.get("xla0801b");//最高学历  学历代码
		String xla0814 = cxtjMap.get("xla0814");//毕业院校
		String xla0824 = cxtjMap.get("xla0824");//专业
		xuelixueweiSQL(xla0801b,xla0814,xla0824,orther_sb,"a0834","a0801b");
		
		
		//最高学位
		String xwa0901b = cxtjMap.get("xwa0901b");//最高学位 学位代码
		String xwa0814 = cxtjMap.get("xwa0814");//毕业院校
		String xwa0824 = cxtjMap.get("xwa0824");//专业
		xuelixueweiSQL(xwa0901b,xwa0814,xwa0824,orther_sb,"a0835","a0901b");
		
		
		
		
		
		//职称
		String a0601 = cxtjMap.get("a0601");//专业技术任职资格
			
		if (a0601!=null&&!"".equals(a0601)) {
			boolean is9 = false;
			orther_sb.append(" and (a01.a0000 in (select a0000 from a06 where a0699='true' ");
			StringBuffer like_sb = new StringBuffer("");
			String[] fArray = a0601.split(",");
			for (int i = 0; i < fArray.length; i++) {
				
				if("9".equals(fArray[i])){
					like_sb.append(" a0601 is null or a0601='999' or ");//无职称
					is9 = true;
				}else{
					like_sb.append(" a0601 like '%" + fArray[i] + "' or ");
				}
			}
			like_sb.delete(like_sb.length() - 4, like_sb.length() - 1);
			orther_sb.append(" and (" + like_sb.toString() + ")");
			orther_sb.append(")");
			if(is9){//无职称
				orther_sb.append(" or not exists (select 1 from a06 where a01.a0000=a06.a0000 and  a0699='true')");
			}
			orther_sb.append(")");
		}
		
		
		
		
		
		//家庭成员
		String a3601 = cxtjMap.get("a3601");//姓名
		String a3611 = cxtjMap.get("a3611");//工作单位及职务
		if ((a3601!=null&&!"".equals(a3601)) || (a3611!=null&&!"".equals(a3611))) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a36 where 1=1 ");
			if(a3601!=null&&!"".equals(a3601)){
				orther_sb.append(" and a3601 like '"+a3601+"%'");
			}

			if(a3611!=null&&!"".equals(a3611)){
				orther_sb.append(" and a3611 like '%"+a3611+"%'");
			}
			
			orther_sb.append(")");
		}
		
		
		
		
		
		
		
		
		
		
		String a0163 = cxtjMap.get("a0163")==null?"":cxtjMap.get("a0163");//人员状态
		String qtxzry = cxtjMap.get("qtxzry")==null?"":cxtjMap.get("qtxzry");
		String a02_a0201b_sb = cxtjMap.get("a02_a0201b_sb")==null?"":cxtjMap.get("a02_a0201b_sb");
		String finalsql = getCondiQuerySQL(userID,a01sb,a02sb,a02_a0201b_sb,orther_sb,a0163,qtxzry);
		return finalsql;
	}
	public static String getCondiQuerySQL(String userid, StringBuffer a01sb,StringBuffer a02sb, String a02_a0201b_sb, StringBuffer orther_sb,String a0163, String qtxzry){
		
		  /*离退，当离退管理中选 离休、退休、退职，               显示：离退人员。     查询：离退人员。

			退出管理， 选择 【调出或转出】 下的选项时，            显示：调出人员。     查询：历史人员

				   选择【其它退出方式】中的 【离退休】         显示：离退人员。     查询：离退人员
			【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
							    【死亡】           显示：已去世。       查询：历史人员           */
			String a0163sql = "";
			if("2".equals(a0163)){
				a0163sql = " and a0163 in('2','21','22','23','29')";
			}else if("".equals(a0163)){
				
			}else{
				a0163sql = " and a0163='"+a0163+"'";
			}
			String a02str = "";
			if("0".equals(qtxzry)){
				a02str = " and "+CommSQL.concat("a01.a0000", "''")+" in "+
			               "(select a02.a0000 "+
			                  "from a02 "+
			                "where a02.A0201B in "+
			                       "(select cu.b0111 "+
			                          "from competence_userdept cu "+
			                         "where cu.userid = '"+userid+"') "+
			                   "and a02.a0255='1' " +
			                   a02_a0201b_sb +a02sb+
			                   ") "+a0163sql;
			}else{
				a02str = " and not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0255='1' and a0201b in(select b0111 from b01 where b0111!='-1')  "+a02sb+")   "
						+ " and a01.status!='4' "+a0163sql ;
			}
			//a0000,a0101,a0104,a0107,a0117,a0111a,a0140,a0134,a0196,a0192a
			return "select  a01.a0000,a01.a0101,a01.a0104,a01.a0107,a0117,a0111a,a0140,a0134,a0196,a01.a0192a,a01.zgxw,a01.zgxl,a01.a0192c,a01.a0288  from A01 a01 "+
	         " where  1=1 "+orther_sb+
	         //  xzry +
	           a02str +
	           a01sb ;
		}
	
	
	
	
	
	
	//小资料
	@PageEvent("ShowData")
	public int ShowData() throws RadowException{
		HBSession sess=HBUtil.getHBSession();
		
		String sql=this.getPageElement("sql").getValue();
		
		String size = sess.createSQLQuery("select count(1) from ("+sql+")").uniqueResult().toString();
		
		this.getExecuteSG().addExecuteCode("setlength2('"+size+"')");
		
		if("0".equals(size)){
			this.setMainMessage("无人员信息");
			return EventRtnType.FAILD;
		}
		
		this.setNextEventName("datashow");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//小资料页面数据加载
	@PageEvent("datashow")
	public int datashow() throws RadowException, UnsupportedEncodingException{
		
		
		String sql=this.getPageElement("sql").getValue();
		
		HBSession sess=HBUtil.getHBSession();
		String page=this.getPageElement("page3").getValue();
		int pagesize=6;
		int pages=Integer.valueOf(page);
		int start=(pages-1)*pagesize;
		int end=pages*pagesize;
		
		String newsql = sql;
		
		String fysql = "";
		fysql = "select tt.* from (select t.*,rownum rn from ("+newsql+") t ) tt where rn>"+start+" and rn<="+end;
		
		
		List<Object[]>  list=sess.createSQLQuery(fysql).list();
		for(int i=0;i<6;i++){//list.size()
			if(i>=list.size()){
				this.getExecuteSG().addExecuteCode("showdata('"+i+"','','','',true)");
				continue;
			}
			
			String data = "";
			Object[] info=list.get(i);
			Object a0000=info[0];
			/*String picsql="select photopath,photoname from a57 where a0000='"+a0000+"'";
			Object[] p=(Object[]) sess.createSQLQuery(picsql).uniqueResult();
			String path="";
			if(p!=null&&p.length>0){
				path=AppConfig.PHOTO_PATH+"/"+p[0]+p[1];
			}
			File f=new File(path);//判断照片存在不存在
			if(!f.exists()){
				path="picCut/image/photo.jpg";
			}*/
			Object a0101=info[1];//姓名
			Object a0104=info[2];//性别
			if(a0104!=null){
				String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB2261' AND CODE_VALUE = '"+a0104+"'";
				a0104 = sess.createSQLQuery(s).uniqueResult();
				data = data + a0104.toString() + "，";
			}
			Object a0107=info[3];//出生年月
			if(a0107!=null){
				String reg = "^[0-9]{6}$";
				String reg2 = "^[0-9]{8}$";
				if(a0107.toString().matches(reg) || a0107.toString().matches(reg2)){
					String msg = getAgeNew(a0107.toString());
					data = data + msg + "，";
				}
			}
			Object a0117=info[4];//民族
			if(a0117!=null){
				String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB3304' AND CODE_VALUE = '"+a0117+"'";
				a0117 = sess.createSQLQuery(s).uniqueResult();
				data = data + a0117.toString() + "，";
			}
			Object a0111=info[5];//籍贯
			if(a0111!=null){
				data = data + a0111.toString() + "人，";
			}
			Object a0140=info[6];//入党时间
			if(a0140!=null){
				String reg = "[0-9]{4}\\.[0-9]{2}";
				Pattern p1 = Pattern.compile(reg); 
			    Matcher matcher = p1.matcher(a0140.toString());
			    if (matcher.find()) {
			    	String s = matcher.group();
			    	s = s.substring(0,4) + "年" + s.substring(5,7).replaceAll("^(0+)", "");
			    	//s = s.replace(".", "年");
			    	data = data + s + "月入党，";
				}
			}
			Object a0134=info[7];//参加工作时间
			if(a0134!=null){
				String reg = "^[0-9]{6}$";
				String reg2 = "^[0-9]{8}$";
				if(a0134.toString().matches(reg) || a0134.toString().matches(reg2)){
					String year = a0134.toString().substring(0, 4);
					String month = a0134.toString().substring(4, 6).replaceAll("^(0+)", "");
					data = data + year + "年" + month + "月参加工作";
				}
				
			}
			
			
			//最高学历
			String zgxlSQL = "select A0801A from A08 where A0834 = '1' and a0000 = '"+a0000+"'";
			
			List<Object[]> zgxlS = sess.createSQLQuery(zgxlSQL).list();
			
			if(zgxlS.size() > 0){
				Object zgxl = zgxlS.get(0);
				
				if(zgxl != null){
					data = data  + "，" + zgxl.toString()+"学历";
				}
				
			}
			
			//最高学位
			String zgxwSQL = "select A0901A from A08 where A0835 = '1' and a0000 = '"+a0000+"'";
			
			List<Object[]> zgxwS = sess.createSQLQuery(zgxwSQL).list();
			
			
			if(zgxwS.size() > 0){
				Object zgxw = zgxwS.get(0);
				
				if(zgxw != null){
					zgxw = zgxw.toString().replace("学位", "");
					data = data + "，" + zgxw.toString() + "。";
				}else{
					data = data + "。";
				}
				
				
			}else{
				data = data + "。";
			}
			
			//专业技术职务
			Object zyjs = info[8];	
			//String zyjsStr = zyjs.toString();
			
			String zyjsStr = "";
			if(zyjs != null && !zyjs.equals("")){
				zyjsStr = zyjs.toString();
			}
			
			
			zyjsStr = zyjsStr.replace(" ", "");
			if(zyjsStr != null && !zyjsStr.equals("")){
				data = data + zyjsStr.toString() + "，";
			}
			
			//String s = "select LISTAGG(A0201A || A0216A,'、') WITHIN GROUP( ORDER BY A0200) from A02 where a0000 = '"+a0000+"' and A0255 = '1' and A0281 = 'true'";
			Object zwOb = info[9];		//工作单位及职务
			
			String zw = "";
			if(zwOb != null && !zwOb.equals("")){
				zw = zwOb.toString();
			}
			
			
			zw = zw.replace(" ", "");
			
			if(zw!=null && !zw.equals("")){
				data = data + zw.toString() + "，";
			}
			data = data.substring(0, data.length()-1) + "。";
			//select LISTAGG(A0201A || A0216A,'<br/>') WITHIN GROUP( ORDER BY A0200) from A02 where a0000 = '' and A0255 = '1' and A0281 = 'true'
			this.getExecuteSG().addExecuteCode("document.getElementById('datai"+i+"').src='"+this.request.getContextPath()+"/servlet/DownloadUserHeadImage?a0000="+URLEncoder.encode(URLEncoder.encode(a0000.toString(),"UTF-8"),"UTF-8")+"'");
			this.getExecuteSG().addExecuteCode("showdata('"+i+"','"+a0000+"','"+a0101+"','"+data+"')");
		}
		//this.getExecuteSG().addExecuteCode("strShowData()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String getAgeNew(String value) {
		int returnAge;

		String birthYear = value.toString().substring(0, 4);
		String birthMonth = value.toString().substring(4, 6);
		String birthDay = "";
		if(value.length()==6){
			birthDay = "01";
		}
		if(value.length()==8){
			birthDay = value.toString().substring(6, 8);
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String s = sdf.format(d);
		String nowYear = s.toString().substring(0, 4);
		String nowMonth = s.toString().substring(4, 6);
		String nowDay = s.toString().substring(6, 8);
		if (Integer.parseInt(nowYear) == Integer.parseInt(birthYear)) {
			returnAge = 0; // 同年返回0岁
		} else {
			int ageDiff = Integer.parseInt(nowYear) - Integer.parseInt(birthYear); // 年只差
			if (ageDiff > 0) {
				if (Integer.parseInt(nowMonth) == Integer.parseInt(birthMonth)) {
					int dayDiff = Integer.parseInt(nowDay) - Integer.parseInt(birthDay);// 日之差
					if (dayDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				} else {
					int monthDiff = Integer.parseInt(nowMonth) - Integer.parseInt(birthMonth);// 月之差
					if (monthDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				}
			} else {
				returnAge = -1;// 出生日期错误 晚于今年
			}

		}
		//String msg = value.toString().substring(0, 6) + "(" + returnAge + "岁)";
		String msg = "" + returnAge + "岁（" + birthYear + "年" + (birthMonth.replaceAll("^(0+)", "")) + "月生）";
		return msg;
	}
}
