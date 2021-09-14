package com.insigma.siis.local.pagemodel.statisticalanalysis.simpleanalysis;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;

import net.sf.json.JSONObject;

public class TestForTjPageModel extends PageModel {
	public static String b01String = "";
	
	public static String b0111 = "";
	/**
	 * 系统区域信息
	 */
	public Hashtable<String, Object> areaInfo = new Hashtable<String, Object>();
	public static String queryType2 = "0";// 1点击机构树查询2点击按钮查询
	public static String tag = "0";// 0未执行1执行

	public TestForTjPageModel() {
		try {
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname = user.getLoginname();
			List<GroupVO> groups = PrivilegeManager.getInstance()
					.getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo = PrivilegeManager.getInstance().getIUserControl()
					.findUserByUserId(cueUserid);
			boolean issupermanager = new DefaultPermission().isSuperManager(vo);

			HBSession sess = HBUtil.getHBSession();
			Object[] area = null;
			if (groups.isEmpty() || issupermanager
					|| loginnname.equals("admin")) {
				area = SysOrgBS.queryInit();
				areaInfo.put("manager", "true");
			} else {
				area = SysOrgBS.queryInit();
				areaInfo.put("manager", "false");
			}
			if (area != null) {
				if (area[2].equals("1")) {
					area[2] = "picOrg";
				} else if (area[2].equals("2")) {
					area[2] = "picInnerOrg";
				} else {
					area[2] = "picGroupOrg";
				}
				areaInfo.put("areaname", area[0]);
				areaInfo.put("areaid", area[1]);
				areaInfo.put("picType", area[2]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public int doInit() throws RadowException {
		//String SQ005 = SysManagerUtils.getUserId();
		String sql="select SQ001,SQ002,SQ003,SQ004 from statistics_query "
				+ " where "
				//+ " (sq005 = '"+SQ005+"' or sq005 = '1')"
				//+ " and "
				+ " SQ001 in ('12','13','14','15','16','17','18') order by SQ001 asc ";
		List<Object[]> Objects=HBUtil.getHBSession().createSQLQuery(sql).list();
		for(Object[] obj:Objects){
			String id=(String) obj[0];
			String name=(String) obj[1];
			String i=(String) obj[2];
			String SQ004=(String) obj[3];
			if(id.length()<5){
				this.getExecuteSG().addExecuteCode("addChild1('"+i+"','"+name+"','"+id+"','"+SQ004+"')");
			}else{
				this.getExecuteSG().addExecuteCode("addChild2('"+i+"','"+name+"','"+id+"','"+SQ004+"')");
			}
			
		}
		// this.getExecuteSG().addExecuteCode("odin.ext.getCmp('peopleInfoGrid').view.refresh();");
		// this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	

	/**
	 * 界面清屏
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("clear.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int clear() throws RadowException {
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}



	// 统计分析获取机构id
	@PageEvent("cytj")
	public int cytjfx(String value) throws RadowException {
		String[] ss = value.split("@");
		String b01String = ss[0];//父节点  1节点名称+ 2false不包含下级+ 3true本及是否被选中
		StringBuffer a02_a0201b_sb = new StringBuffer("");
		b0111 = "";
		if (b01String != null && !"".equals(b01String)) {
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if (jsonObject.size() > 0) {
				a02_a0201b_sb.append(" and (1=2 ");
				b0111 += " and ";
			}else{
				this.setMainMessage("请勾选机构!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			Iterator<String> it = jsonObject.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {//循环单位
				String nodeid = it.next();//根结点 id
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");// [机构名称，是否包含下级，是否本级选中]
				if ("true".equals(types[1]) && "true".equals(types[2])) {
					b0111 += CommSQL.subString("b0111", 1,
							nodeid.length(), nodeid);
					a02_a0201b_sb.append(" or "
							+ CommSQL.subString("a02.a0201b", 1,
									nodeid.length(), nodeid));
				} else if ("true".equals(types[1]) && "false".equals(types[2])) {
					b0111 += CommSQL.subString2("b0111", 1,
							nodeid.length(), nodeid);
					a02_a0201b_sb.append(" or "
							+ CommSQL.subString2("a02.a0201b", 1,
									nodeid.length(), nodeid));
				} else if ("false".equals(types[1]) && "true".equals(types[2])) {
					b0111 += (" b0111 = '" + nodeid + "' ");
					a02_a0201b_sb.append(" or a02.a0201b = '" + nodeid + "' ");
				}
			}
			if (jsonObject.size() > 0) {
				a02_a0201b_sb.append(" ) and a01.a0000 = a02.a0000 group by a01.a0000 ");
			}
		}
		/*if (b01String != null && !"".equals(b01String)) {
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if (jsonObject.size() > 0) {
				a02_a0201b_sb.append(" and (1=2 ");
				b0111 += " and ";
			}else{
				this.setMainMessage("请勾选机构!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			Iterator<String> it = jsonObject.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {//循环单位
				String nodeid = it.next();//根结点 id
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");// [机构名称，是否包含下级，是否本级选中]
				if ("true".equals(types[1]) && "true".equals(types[2])) {
					b0111 += CommSQL.subString("b0111", 1,
							nodeid.length(), nodeid);
					a02_a0201b_sb.append(" or "
							+ CommSQL.subString("a01.a0195", 1,
									nodeid.length(), nodeid));
				} else if ("true".equals(types[1]) && "false".equals(types[2])) {
					b0111 += CommSQL.subString2("b0111", 1,
							nodeid.length(), nodeid);
					a02_a0201b_sb.append(" or "
							+ CommSQL.subString2("a01.a0195", 1,
									nodeid.length(), nodeid));
				} else if ("false".equals(types[1]) && "true".equals(types[2])) {
					b0111 += (" b0111 = '" + nodeid + "' ");
					a02_a0201b_sb.append(" or a01.a0195 = '" + nodeid + "' ");
				}
			}
			if (jsonObject.size() > 0) {
				a02_a0201b_sb.append(" ) and a01.a0000 = a02.a0000 group by a01.a0000 ");
			}
		}*/
		if("1".equals(ss[1])){//统计
			starttj(a02_a0201b_sb.toString().replace("'", "|"),ss[2]);
		}else if("2".equals(ss[1])){//编辑
			editorBtn(a02_a0201b_sb.toString().replace("'", "|"));
		}else if("3".equals(ss[1])){//新建
			this.setRadow_parent_data(a02_a0201b_sb.toString());
			this.openWindow("zdytjfxWin","pages.statisticalanalysis.simpleanalysis.CustomizeStatistics");
		}else{
			this.setMainMessage("错误操作");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}


	@PageEvent("deleteBtn.onclick")
	public int delete() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String queryid=this.getPageElement("checknodeid2").getValue();
		String[] Str=queryid.split(",");
		String SQ001=Str[0];
		String qc_sql = "DELETE FROM query_condition WHERE SQ001 = '"+SQ001+"'";
		sess.createSQLQuery(qc_sql).executeUpdate();
		String sq_sql = "DELETE FROM statistics_query WHERE SQ001 = '"+SQ001+"'";
		sess.createSQLQuery(sq_sql).executeUpdate();
		//this.getExecuteSG().addExecuteCode("Ext.msg.alert(删除成功);");
		this.setMainMessage("删除成功");
		this.reloadPageByYes();
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	/**
	 * 统计分析
	 * @param groupid
	 * @param type
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("starttj")
	public int starttj(String groupid,String type) throws RadowException{
		//String groupid=this.getRadow_parent_data();//机构id
		String queryid;
		HBSession sess = HBUtil.getHBSession();
		if("1".equals(type)){//统计
			queryid=this.getPageElement("checknodeid1").getValue();//常用统计、目录id
		}else {
			queryid=this.getPageElement("checknodeid2").getValue();
		}
		String[] Str=queryid.split(",");
		String Stype=Str[1];
		String SQ001=Str[0];
		String sql1 = "select sq002 from statistics_query where sq001 = '"+SQ001+"'";
		String SQ002 = sess.createSQLQuery(sql1).uniqueResult().toString();
		if( "6".equals(SQ001) ||  "7".equals(SQ001) ||  "8".equals(SQ001)){
			fixed_query(groupid);
			return EventRtnType.NORMAL_SUCCESS;
		}else if("12".equals(SQ001)||"13".equals(SQ001)||"14".equals(SQ001)||
				"15".equals(SQ001)||"16".equals(SQ001)||"17".equals(SQ001)||"18".equals(SQ001)){
			fixed_query_tj(groupid);
			return EventRtnType.NORMAL_SUCCESS;
		}else if(SQ001.length()<5){
			gdcy_query(groupid);
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("1".equals(Stype)){
			this.getExecuteSG().addExecuteCode("tjfx('"+groupid+"\\$"+SQ001+"&"+SQ002+"')");
		}
		if("2".equals(Stype)){
			groupid = groupid.replace("|", "'");			
			String querysql = "SELECT a01.a0000 FROM a01,a02,a08,competence_userdept cu "
							+ "WHERE a01.a0000 = a02.a0000 "
							+ "and not exists (select 1 from COMPETENCE_USERPERSON cu "
							+ "				   where cu.a0000 = a01.a0000 "
							+ "				   and cu.userid = '"+SysManagerUtils.getUserId()+"' ) "
							+ "and a02.A0201B=cu.B0111 "
							+ "and cu.userid = '"+SysManagerUtils.getUserId()+"' "
							+ "and a01.a0000=a08.a0000 "
							+ "and a02.a0255 = '1'  "
							+ "and a01. STATUS = '1' ";
			Integer vert_num = 0;
			Integer tran_num = 0;
			String vert_name = "";
			String tran_name = "";
			String data = "";
			String querysql_do = null;
			String querysql_con = null;
			Integer maxvalue = 0;
			String sql = "select qc003 from query_condition where sq001 = '"+SQ001+"'";
			String qc003 = sess.createSQLQuery(sql).uniqueResult().toString();
			String[] qc003_arr = qc003.split("\\|");
			String vert_data = qc003_arr[0];
			String tran_data = qc003_arr[1];
			String[] vert_data_arr = vert_data.split("@");
			String[] tran_data_arr = tran_data.split("@");
			for(int i=1;i<vert_data_arr.length;i++){
				String vert_i = vert_data_arr[i];
				if(vert_i !=null && vert_i.length()>0){
					String vert_i_sql = "select ti002,ti003 from TWODSTATISTICSINFO_ITEM where ti001 = '"+vert_i+"' ";
					List<Object[]>  list_vert = HBUtil.getHBSession().createSQLQuery(vert_i_sql).list();
					Object[] oo_vert=null;
					if(list_vert != null && ! list_vert.isEmpty()){
						oo_vert = list_vert.get(0);
						querysql_con = querysql + oo_vert[1]+" ";
						vert_name += oo_vert[0]+"@";
						vert_num += 1;
					}else{
						this.setMainMessage("数据异常");
					}
					for(int j=1;j<tran_data_arr.length;j++){
						String tran_j = tran_data_arr[j];
						if(tran_j !=null && tran_j.length()>0){
							String tran_j_sql = "select ti002,ti003 from TWODSTATISTICSINFO_ITEM where ti001 = '"+tran_j+"' ";
							List<Object[]>  list_tran = HBUtil.getHBSession().createSQLQuery(tran_j_sql).list();
							Object[] oo_tran=null;
							if(list_tran != null && ! list_tran.isEmpty()){
								oo_tran = list_tran.get(0);
								querysql_do = querysql_con + oo_tran[1];
								if(vert_num==1){
									tran_name += oo_tran[0]+"@";
									tran_num += 1;
								}
							}else{
								this.setMainMessage("数据异常");
							}
							String number = "";
							if(DBUtil.getDBType()==DBType.ORACLE){
								//number = sess.createSQLQuery("select count(1) from ( "+tj+" )").uniqueResult().toString();
								number = sess.createSQLQuery("select count(1) from ( "+querysql_do+groupid+")").uniqueResult().toString();
							}else{
								//number = sess.createSQLQuery("select count(1) from ( "+tj+" ) as t").uniqueResult().toString();
								number = sess.createSQLQuery("select count(1) from ( "+querysql_do+groupid+") as t").uniqueResult().toString();
							}
							data += number + "@";
							if(Integer.parseInt(number)>maxvalue){
								maxvalue = Integer.parseInt(number);
							}
						}
					}
					if(data.length()>0 && data.substring(data.length()-1).equals("@")){
						data = data.substring(0,data.length()-1);
						data += "#";
					}
				}
			}	
			/*String pass1 = "tran_name:'"+tran_name.substring(0,tran_name.length()-1)+"','tran_num':'"+tran_num+"',"
						+ "'vert_name':'"+vert_name.substring(0,vert_name.length()-1)+"','vert_num':'"+vert_num+"',"
						+ "'data':'"+data.substring(0,data.length()-1)+""; */
			String pass = tran_name.substring(0,tran_name.length()-1)+"$"+tran_num+"$"
						+vert_name.substring(0,vert_name.length()-1)+"$"+vert_num+"$"
						+data.substring(0,data.length()-1)+"$"+maxvalue+"&"+SQ002;
			this.getExecuteSG().addExecuteCode("tjfx2('"+pass+"');");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("editor")
	public int editorBtn(String groupid) throws RadowException{
		//String groupid=this.getRadow_parent_data();
		//获取树的id（查询条件id+统计类型）
		HBSession sess = HBUtil.getHBSession();
		String queryid=this.getPageElement("checknodeid2").getValue();
		String[] Str=queryid.split(",");
		String Stype=Str[1];
		String SQ001=Str[0];
		String sql1 = "select sq002 from statistics_query where sq001 = '"+SQ001+"'";
		String SQ002 = sess.createSQLQuery(sql1).uniqueResult().toString();
		if("1".equals(Stype)){
			this.getExecuteSG().addExecuteCode("tytj('"+groupid+"@"+SQ001+"@"+SQ002+"&"+SQ002+"')");
		}
		if("2".equals(Stype)){
			String sql = "select QC001,QC002,QC003 from query_condition where sq001 = '"+SQ001+"'";
			List<Object[]> Objects=HBUtil.getHBSession().createSQLQuery(sql).list();
			String name = "";
			String str = "";
			String qc001 = "";
			for(Object[] obj:Objects){
				qc001=(String) obj[0];
				name=(String) obj[1];
				str=(String) obj[2];
			}
			String param = name+"#"+str+"#"+groupid+"#"+qc001+"#"+SQ002+"&"+SQ002;
			this.getExecuteSG().addExecuteCode("ewedit('"+param+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	public int fixed_query_tj(String groupid) throws RadowException{
		//String groupid = this.getRadow_parent_data();
		HBSession sess = HBUtil.getHBSession();
		String queryid=this.getPageElement("checknodeid1").getValue();
		String[] Str=queryid.split(",");
		String SQ001=Str[0];
		String sql1 = "select sq002 from statistics_query where sq001 = '"+SQ001+"'";
		String SQ002 = sess.createSQLQuery(sql1).uniqueResult().toString();
		if("12".equals(SQ001)){
			this.getExecuteSG().addExecuteCode("fixed_query_tj('"+groupid+"\\$"+SQ001+"&"+SQ002+"')");
		}else if("13".equals(SQ001)){//女
			this.getExecuteSG().addExecuteCode("fixed_query_nv_tj('"+groupid+"\\$"+SQ001+"&"+SQ002+"')");
		}else if("14".equals(SQ001)){//少
			this.getExecuteSG().addExecuteCode("fixed_query_shao_tj('"+groupid+"\\$"+SQ001+"&"+SQ002+"')");
		}else if("15".equals(SQ001)){//非
			this.getExecuteSG().addExecuteCode("fixed_query_fei_tj('"+groupid+"\\$"+SQ001+"&"+SQ002+"')");
		}else if("16".equals(SQ001)){//学历学位
			this.getExecuteSG().addExecuteCode("fixed_query_xwxl_tj('"+groupid+"\\$"+SQ001+"&"+SQ002+"')");
		}else if("17".equals(SQ001)){//年龄
			this.getExecuteSG().addExecuteCode("fixed_query_nl_tj('"+groupid+"\\$"+SQ001+"&"+SQ002+"')");
		}else if("18".equals(SQ001)){//现职层次
			this.getExecuteSG().addExecuteCode("fixed_query_xzcc_tj('"+groupid+"\\$"+SQ001+"&"+SQ002+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 统计分析 
	 * 职务层次/女少非分布
	 * 职务层次/学历分布
	 * 职务层次/年龄分布
	 * 干部基本情况统计
	 * @param groupid
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("fixed_query")
	public int fixed_query(String groupid) throws RadowException{
		//String groupid = this.getRadow_parent_data();
		HBSession sess = HBUtil.getHBSession();
		String queryid=this.getPageElement("checknodeid1").getValue();
		String[] Str=queryid.split(",");
		String SQ001=Str[0];
		String sql1 = "select sq002 from statistics_query where sq001 = '"+SQ001+"'";
		String SQ002 = sess.createSQLQuery(sql1).uniqueResult().toString();
		this.getExecuteSG().addExecuteCode("fixed_query('"+groupid+"\\$"+SQ001+"&"+SQ002+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("gdcy_query")
	public int gdcy_query(String groupid) throws RadowException{
		//String groupid = this.getRadow_parent_data();
		HBSession sess = HBUtil.getHBSession();
		String queryid=this.getPageElement("checknodeid1").getValue();
		String[] Str=queryid.split(",");
		String SQ001=Str[0];
		String sql1 = "select sq002 from statistics_query where sq001 = '"+SQ001+"'";
		String SQ002 = sess.createSQLQuery(sql1).uniqueResult().toString();
		this.getExecuteSG().addExecuteCode("tjfx('"+groupid+"\\$"+SQ001+"\\$gdcy&"+SQ002+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("reload")
	public int reload()throws RadowException{
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
}
