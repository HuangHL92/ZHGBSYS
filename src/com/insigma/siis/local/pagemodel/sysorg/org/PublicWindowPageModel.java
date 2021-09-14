package com.insigma.siis.local.pagemodel.sysorg.org;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.RSACoder;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.UserOpCode;
import com.insigma.siis.local.business.entity.UserOpCodeId;
import com.insigma.siis.local.business.helperUtil.CodeType2js;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.NewRangePageModel;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class PublicWindowPageModel extends PageModel {
	
	protected static final String ZEROTREE = "0";//机构信息维护树
	protected static final String LEFTTREE = "1";//人员批量转移，左边树
	protected static final String OPENTREE = "2";//弹出树
	protected static final String THREETREE = "3";//邹磊用树
	protected static final String FOURTREE = "4";//名册导出用树
	protected static final String FIVETREE = "5";//人员信息维护树
	protected static final String SIXTREE = "6";//人员信息维护树
	protected static final String SEVENTREE = "7";//人员批量转移，右边树
	public static int treeType = 0;
	
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();

	//岗位类别codevalueparameter 
	public static String codevalueparameter= "";
	//ZB127=01 ZB09=(01,02,09)
	//ZB127=02 ZB09=(08)
	//ZB127=03 ZB09=(03)
	//ZB127=04 ZB09=(04)
	//ZB127=05 ZB09=(05)
	//ZB127=06 ZB09=(06)
	//ZB127=07 ZB09=(07)
	//ZB127=08 ZB09=(全部)
	
	public PublicWindowPageModel(){
	}
	@Override
	public int doInit() throws RadowException{
		this.setNextEventName("commGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询常用代码列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("commGrid.dogridquery")
	public int doCommQuery(int start,int limit) throws RadowException{
		String codetype = this.getPageElement("codetype").getValue();
		String codename = this.getPageElement("codename").getValue();
		//内设机构不查询标示
		String nsjg = this.getPageElement("nsjg").getValue();
		String userId = SysUtil.getCacheCurrentUser().getUserVO().getId();
		StringBuffer sqlYBf = new StringBuffer();
		if(DBType.ORACLE == DBUtil.getDBType()){
			sqlYBf.append("       (Select Code_Value, Code_Type, Code_Count ")
				.append("          From (Select a.Code_Value, a.Code_Count, a.Code_Type ")
				.append("                  From User_Op_Code a ")
				.append("                 Where a.Code_Type = '"+codetype+"' ")
				.append("                   And a.Userid = '"+userId+"' ")
				.append("                 Order By a.Code_Count Desc) ")
				.append("         Where Rownum < 11) y ");
		}else if(DBType.MYSQL == DBUtil.getDBType()){
			sqlYBf.append("  (Select a.Code_Value, a.Code_Count, a.Code_Type ")
			.append("                  From User_Op_Code a ")
			.append("                 Where a.Code_Type = '"+codetype+"' ")
			.append("                   And a.Userid = '"+userId+"' ")
			.append("                 Order By a.Code_Count Desc ")
			.append("         limit 0,10) y ");
		}
		
		String codevalueparameter = this.getPageElement("codevalueparameter").getValue();
		StringBuffer sb = new StringBuffer("");
		if("orgTreeJsonData".equals(codetype)){
			sb.append("Select x.b0111 Code_Value,b0101 code_name ")
			.append("  From B01 x, ")
			.append(sqlYBf)
			.append(" Where x.b0111 = y.Code_Value ")
			.append("   And y.Code_Type='orgTreeJsonData'")
			//内设机构不查询sql
			.append((nsjg!=null && nsjg.equals("0"))?"   And x.b0194<>'2' And x.b0111<>'-1'" :"")
			.append(" Order By y.Code_Count Desc ");
		}else if("GWGLLB".equals(codetype) && codevalueparameter!=null 
				&& !codevalueparameter.equals("") && !codevalueparameter.equals("null")){
			codevalueparameter = getFrdwId(codevalueparameter);
			sb.append("Select x.gwcode Code_Value,gwname code_name ")
			.append("  From Jggwconf x, ")
			.append(sqlYBf)
			.append(" Where x.gwcode = y.Code_Value ")
			.append("   And y.Code_Type='GWGLLB' and b0111='"+codevalueparameter+"'")
			.append(" Order By y.Code_Count Desc ");
		}else if("userGroupTreeData".equals(codetype)){
			sb.append("Select x.id Code_Value,x.usergroupname code_name ")
			.append("  From smt_usergroup x, ")
			.append(sqlYBf)
			.append(" Where x.id = y.Code_Value ")
			.append("   And y.Code_Type='userGroupTreeData' ")
			.append(" Order By y.Code_Count Desc ");
		}else{
			sb.append(" Select x.Code_Value, "+codename+" code_name ")
			.append("  From Code_Value x, ")
			.append(sqlYBf)
			.append(" Where x.Code_Value = y.Code_Value ")
			.append("   And x.Code_Type = y.Code_Type and X.code_status='1' ")
			.append(" Order By y.Code_Count Desc ");
		}
		
		this.pageQuery(sb.toString(), "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	private String getFrdwId(String codevalueparameter2) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		try {
			B01 b01 = (B01) sess.get(B01.class, codevalueparameter2);
			if(b01.getB0194().equals("1") || codevalueparameter2.equals("001.001")) {
				return b01.getB0111();
			} else {
				//return getFrdwId(b01.getB0121());
				return b01.getB0111();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("数据异常!");
		}
	}
	/**
	 * 选择常用代码值，并返回
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("commGrid.rowdbclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	public int selectCommQuery() throws RadowException{
		Grid grid = (Grid)this.getPageElement("commGrid");
		String  codevalue = grid.getValue("code_value")==null?"":grid.getValue("code_value").toString();
		String  codename = grid.getValue("code_name")==null?"":grid.getValue("code_name").toString();
		this.getExecuteSG().addExecuteCode("returnWin ('"+codevalue+","+codename+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 选择快速查找值，并返回
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("memberGrid.rowdbclick")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int returnLikeQuery() throws RadowException, AppException{
		Grid grid = (Grid)this.getPageElement("memberGrid");
		String  codevalue = grid.getValue("code_value")==null?"":grid.getValue("code_value").toString();
		String  codename = grid.getValue("code_name")==null?"":grid.getValue("code_name").toString();
		String codetype = this.getPageElement("codetype").getValue();
		if(codetype.equals("orgTreeJsonData")){
			Boolean b = SysRuleBS.havaRule(codevalue);
			if(!b){
				throw new AppException("您没有权限！");
			}
		}
		dblclickTree(codevalue);
		this.getExecuteSG().addExecuteCode("returnWin ('"+codevalue+","+codename+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException, AppException, RadowException {
		String node = (String)this.getParameter("node");
		String codetype = this.getParameter("codetype");
		String codename = this.getParameter("codename");
		String ChangeValue = this.getParameter("ChangeValue");
		String codevalueparameter = this.getParameter("codevalueparameter");
		String nsjg = this.getParameter("nsjg");
		if("ZB09".equals(codetype)){
			ChangeValue = codevalueparameter;
		}
		if(StringUtil.isEmpty(codename)){
			codename = "code_name";
		}
		if("GWGLLB".equals(codetype) && codevalueparameter!=null 
				&& !codevalueparameter.equals("")) {
			codevalueparameter = getFrdwId(codevalueparameter);
			String jsonStr =CodeType2js.getCodeTypeTREEGWGLLB(codevalueparameter);
			this.setSelfDefResData(jsonStr);
			return EventRtnType.XML_SUCCESS;
		}
		if("userGroupTreeData".equals(codetype)) {
			String jsonStr = CodeType2js.getuserGroupTreeData(node);
			this.setSelfDefResData(jsonStr);
			return EventRtnType.XML_SUCCESS;
		}
		//组织机构树
		if("orgTreeJsonData".equals(codetype)){
			if(node.equals("-1")){
				//node = SysManagerUtils.getUserOrgid();
			}else{
				node = node.split("[|]")[0];
			}
			return getOrgTreeJsonData(nsjg,node);
		}
		
		
		return getCodeValueTree(node,codetype,codename,ChangeValue);
		
		
		//this.setSelfDefResData(jsonStr.toString());
		//return EventRtnType.XML_SUCCESS;
	}
	
	private int getCodeValueTree(String node, String codetype, String codename, String changeValue) {
		String jsonStr =CodeType2js.getCodeTypeTREE(node,codetype,codename,changeValue);
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	@PageEvent("cancelBtn.onclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@NoRequiredValidate
	public int cancel(String value) throws AppException, RadowException {
		String closewin = this.getPageElement("closewin").getValue();
		String property = this.getPageElement("property").getValue();
		String codevalue = this.getPageElement("checkedgroupid").getValue().toString();
		String userID = SysManagerUtils.getUserId();
		if (null!=value&&"orgTreeJsonData".equals(this.getPageElement("codetype").getValue().toString())) {
			Map<String, String> map = new HashMap();
			if ("".equals(codevalue)||codevalue==null) {
				String b0111 = value.substring(0,value.indexOf(","));
				map = isHasRule(b0111, userID);
			}else {
				map = isHasRule(codevalue, userID);
			}
			String nsjg = this.getPageElement("nsjg").getValue();
			if (!map.isEmpty()||map==null) {
				if (treeType==1) {
					if(nsjg!=null && nsjg.equals("0")){
						if (map.get("b0114")==null || "".equals(map.get("b0114"))) {
							this.setMainMessage("该机构没有单位编码，不能导出！");
							return EventRtnType.NORMAL_SUCCESS;
						}
						if ("2".equals(map.get("type"))||"0".equals(map.get("type"))){
							this.setMainMessage("您没有该机构的权限");
							return EventRtnType.NORMAL_SUCCESS;
						}
					} else {
						if ("2".equals(map.get("type"))||"0".equals(map.get("type"))||"3".equals(map.get("b0194"))){
							this.setMainMessage("您没有该机构的权限");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
					
				}else {
					if ("2".equals(map.get("type"))||"0".equals(map.get("type"))||"3".equals(map.get("b0194"))){
						if("3".equals(map.get("b0194"))){
							//this.setMainMessage("不可选择机构分组单位");
						}else{
							this.setMainMessage("您没有该机构的权限");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}
			}
		}
		//this.closeCueWindow(closewin);
		
		if(null!=value){
			if("X001".equals(value.substring(0,value.indexOf(",")))){
				value=value.replaceAll("X001", "-1");
			}
			/*this.getExecuteSG().addExecuteCode("var pthisWin = parent.Ext.getCmp('"+closewin+"').initialConfig.thisWin;"
					+ "if(pthisWin.returnwin"+property+"){"
							+ "pthisWin.returnwin"+property+"('"+value+"');"
							+ "}else{pthisWin.returnwin('"+value+"','"+property+"');}");*/
			this.getExecuteSG().addExecuteCode("var pthisWin = parent;"
					+ "if(pthisWin.returnwin"+property+"){"
							+ "pthisWin.returnwin"+property+"('"+value+"');"
							+ "}else{pthisWin.returnwin('"+value+"','"+property+"');}");
			
		}
		this.getExecuteSG().addExecuteCode("window.close()");
		//System.out.println(value);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("memberGrid.rowdbclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@NoRequiredValidate
	public int rowdbclick() throws RadowException,AppException{
		List<HashMap<String, String>> list = this.getPageElement("memberGrid").getStringValueList();
		HashMap<String,String> map1=list.get(0);
		CommonQueryBS.systemOut("value--------"+map1.get("code_value"));
		return 0;
	}
	
	//刷新列表
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String likeQuery = this.getPageElement("likeQuery").getValue();
		String codetype = this.getPageElement("codetype").getValue();
		String codename = this.getPageElement("codename").getValue();
		String codevalueparameter = this.getPageElement("codevalueparameter").getValue();
		//内设机构不查询标示
		String nsjg = this.getPageElement("nsjg").getValue();
		String nsjgsql = (nsjg!=null && nsjg.equals("0")) ? "And b0194<>'2' " : " ";
		if(StringUtil.isEmpty(codename) || codename.equals("code_name")){
			codename = "code_name";
		}else if(codename.equals("code_name2")){
			codename = "code_name2";
		}else if(codename.equals("code_name3")){
			codename = "code_name3";
		}else{
			codename = "code_name";
		}
		StringBuffer sb = new StringBuffer("");
		String strcon="";
		String strcon1="";
		if(codetype.equals("orgTreeJsonData")){
			sb.append("select b0111 code_value,b0101 code_name from b01 where b0111<>'-1' " +nsjgsql);
			if (!likeQuery.equals("")){
				strcon = "(b0101 like '%" + likeQuery +"%'";
				sb.append(" and ");
				sb.append(strcon);
				strcon1 = "b0107 like '%" + likeQuery.toUpperCase() +"%')";
				sb.append(" or ");
				sb.append(strcon1);
			}
		} else if(codetype.equals("GWGLLB") && codevalueparameter!=null 
				&& !codevalueparameter.equals("")){
			codevalueparameter = getFrdwId(codevalueparameter);
			sb.append("select code_value,"+codename+" code_name from code_value t,Jggwconf f where"
					+ " t.code_value=f.gwcode and f.b0111='"+codevalueparameter+
					"' and code_status='1' and  code_leaf='1' and code_type='"+codetype+"'");
			if (!likeQuery.equals("")){
				strcon = "(code_name3 like '%" + likeQuery +"%'";
				sb.append(" and ");
				sb.append(strcon);
				strcon1 = "code_spelling like '%" + likeQuery.toUpperCase() +"%')";
				sb.append(" or ");
				sb.append(strcon1);
			}
		} else if(codetype.equals("userGroupTreeData") ){
			sb.append("select id code_value,usergroupname code_name from smt_usergroup where 1=1 " );
			if (!likeQuery.equals("")){
				strcon = "(usergroupname like '%" + likeQuery +"%')";
				sb.append(" and ");
				sb.append(strcon);
				/*strcon1 = "b0107 like '%" + likeQuery.toUpperCase() +"%')";
				sb.append(" or ");
				sb.append(strcon1);*/
			}
		} else {
			sb.append("select code_value,"+codename+" code_name from code_value where code_status='1' and  code_leaf='1' and code_type='"+codetype+"'");
			if (!likeQuery.equals("")){
				strcon = "(code_name3 like '%" + likeQuery +"%'";
				sb.append(" and ");
				sb.append(strcon);
				strcon1 = "code_spelling like '%" + likeQuery.toUpperCase() +"%')";
				sb.append(" or ");
				sb.append(strcon1);
			}
		}
		sb.append(" order  by code_value");
	//	System.out.println(sb.toString());
		this.pageQuery(sb.toString(), "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	@PageEvent("likeQuery")
	public int likeQuery(String name) throws AppException, RadowException {
//		System.out.println(name);
		String likeQuery = this.getPageElement("likeQuery").getValue();
//<---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------->			
		if(likeQuery.contains("'")||likeQuery.contains("\"")||likeQuery.contains(" ")){
			this.setMainMessage("不能输入特殊符号");
			return EventRtnType.NORMAL_SUCCESS;
		}
//<---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------->			
		 if(!likeQuery.equals("")){
			this.setNextEventName("memberGrid.dogridquery");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	/**
	 * 双击树事件:添加用户选项到数据库
	 * @author mengl
	 * @param nodeId
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("dblclickTree")
	@Transaction
	public int dblclickTree(String nodeId) throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		sess.beginTransaction();
		Connection conn = sess.connection();
		try {
			String codetype = this.getPageElement("codetype").getValue();
			UserOpCode userOp = null;
			UserOpCodeId id = new UserOpCodeId();
			id.setCodeType(codetype);
			id.setCodeValue(nodeId);
			id.setUserid(SysUtil.getCacheCurrentUser().getUserVO().getId());
			userOp = (UserOpCode) sess.get(UserOpCode.class, id);
			if(userOp==null){
				userOp = new UserOpCode();
				userOp.setId(id);
				userOp.setStatus("1");//新建 有效
				userOp.setCreateTime(new Date());
				userOp.setCodeCount(new BigDecimal(0));
			}else{
				userOp.setCodeCount(userOp.getCodeCount()!=null?userOp.getCodeCount().add(new BigDecimal(1)):new BigDecimal(0));
			}
			sess.saveOrUpdate(userOp);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		CommonQueryBS.systemOut(nodeId);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 切换tab页事件
	 */
	/*@PageEvent("grantTabChange")
	public int grantTabChange(){
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	/**
	 * 清空
	 * @throws RadowException 
	 */
	@PageEvent("resetBtn.onclick")
	public int reset() throws RadowException{
		this.getPageElement("likeQuery").setValue("");
		//清空时，将外边的值也给清空
		String property = this.getPageElement("property").getValue();
		this.getExecuteSG().addExecuteCode("var pthisWin = parent;"
							+ "if(pthisWin.returnwin"+property+"){"
									+ "pthisWin.returnwin"+property+"(',');"
									+ "}else{pthisWin.returnwin(',','"+property+"');}");
		this.getExecuteSG().addExecuteCode("window.close()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//确认
	@PageEvent("yesBtn")
	@GridDataRange
	public int yes() throws RadowException{
		String tabchange = this.getPageElement("tabchange").getValue();
		if(tabchange.equals("1")){
			Map map = this.getRequestParamer();
			int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
			String codevalue = this.getPageElement("commGrid").getValue("code_value",index).toString();
			String codename = this.getPageElement("commGrid").getValue("code_name",index).toString();
			this.getExecuteSG().addExecuteCode("returnWin ('"+codevalue+","+codename+"')");
		}
		if(tabchange.equals("3")){
			Map map = this.getRequestParamer();
			int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
			String codevalue = this.getPageElement("memberGrid").getValue("code_value",index).toString();
			String codename = this.getPageElement("memberGrid").getValue("code_name",index).toString();
			this.getExecuteSG().addExecuteCode("returnWin ('"+codevalue+","+codename+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;

	}
	
	//根据机构编码与userid判断是否有该机构浏览权限
	public static Map<String, String> isHasRuleLook(String b0111,String userid){
		Map<String, String> map = new HashMap();
		try {
			String loginName=SysUtil.getCacheCurrentUser().getLoginname();
			ResultSet rs = HBUtil.getHBSession().connection().prepareStatement("SELECT b.b0194,t.type,b.b0114 FROM competence_userdept t RIGHT JOIN (SELECT b.b0111,b.b0194,b.b0114 FROM b01 b WHERE b.b0111='"+b0111+"') b ON t.b0111=b.b0111 AND t.userid='"+userid+"'").executeQuery();
			if (rs.next()) {
				String b0194 = rs.getString(1);
				String type = rs.getString(2);
				String b0114 = rs.getString(3);
				map.put("b0194", b0194);
				map.put("b0114", b0114);
				if("system".equals(loginName)){
					map.put("type", "1");
				}else if ("".equals(type)||type==null) {
					map.put("type", "2");
				}else {
					map.put("type", type);
				}
			}else {
				if("system".equals(loginName)){
					map.put("type", "1");
				}else{
					map.put("type", "");
					map.put("b0194", "");
				}
				
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//根据机构编码与userid判断是否有该机构权限
	public static Map<String, String> isHasRule(String b0111,String userid){
		Map<String, String> map = new HashMap();
		try {
			String loginName=SysUtil.getCacheCurrentUser().getLoginname();
			ResultSet rs = HBUtil.getHBSession().connection().prepareStatement("SELECT b.b0194,t.type,b.b0114 FROM competence_userdept t RIGHT JOIN (SELECT b.b0111,b.b0194,b.b0114 FROM b01 b WHERE b.b0111='"+b0111+"') b ON t.b0111=b.b0111 AND t.userid='"+userid+"'").executeQuery();
			if (rs.next()) {
				String b0194 = rs.getString(1);
				String type = rs.getString(2);
				String b0114 = rs.getString(3);
				map.put("b0194", b0194);
				map.put("b0114", b0114);
				if("system".equals(loginName)){
					map.put("type", "1");
				}else if ("".equals(type)||type==null) {
					map.put("type", "2");
				}else {
					map.put("type", type);
				}
			}else {
				if("system".equals(loginName)){
					map.put("type", "1");
				}else{
					map.put("type", "");
					map.put("b0194", "");
				}
				
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//确认
	@PageEvent("treeyesBtn")
	public int treeyes() throws RadowException{
		String codevalue = this.getPageElement("checkedgroupid").getValue().toString();
		String codename = this.getPageElement("checkedgroupname").getValue().toString();
		String userID = SysManagerUtils.getUserId();
		if ("orgTreeJsonData".equals(this.getPageElement("codetype").getValue().toString())) {
			Map<String, String> map = isHasRule(codevalue, userID);
			String nsjg = this.getPageElement("nsjg").getValue();
			if (!map.isEmpty()||map==null) {
				if (treeType==1) {
					if(nsjg!=null && nsjg.equals("0")){
						if (map.get("b0114")==null || "".equals(map.get("b0114"))) {
							this.setMainMessage("该机构没有单位编码，不能导出！");
							return EventRtnType.NORMAL_SUCCESS;
						}
						if ("2".equals(map.get("type"))||"0".equals(map.get("type"))){
							this.setMainMessage("您没有该机构的权限");
							return EventRtnType.NORMAL_SUCCESS;
						}
					} else {
						if ("2".equals(map.get("type"))||"0".equals(map.get("type"))||"3".equals(map.get("b0194"))){
							this.setMainMessage("您没有该机构的权限");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}else {
					if ("2".equals(map.get("type"))||"0".equals(map.get("type"))||"3".equals(map.get("b0194"))){
						if("3".equals(map.get("b0194"))){
							//this.setMainMessage("不可选择机构分组单位");
						}else{
							this.setMainMessage("您没有该机构的权限");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}
			}
		}
		if(codevalue.equals("")){
			this.setMainMessage("请选择一条数据");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode("returnWin ('"+codevalue+","+codename+"')");
		return EventRtnType.NORMAL_SUCCESS;

	}
	//点击树查询事件
	@PageEvent("querybyid")
	@NoRequiredValidate
	@AutoNoMask
	public int query(String id) throws RadowException {
		String codetype = this.getPageElement("codetype").getValue();
		String codename = this.getPageElement("codename").getValue();
		List<String[]> list = null;
		try {
			String sql="select code_value,"+codename+" from code_value where code_status='1' and code_type='"+codetype+"' and code_value = '"+id+"' and code_leaf='1'" ;
			list = HBUtil.getHBSession().createSQLQuery(sql).list();
			if(list.size()==0){
				//throw new RadowException("请选择叶子节点");
			}else{
				Object[] obj = list.get(0);
				String checkedgroupname =(String)obj[1];
				this.getPageElement("checkedgroupid").setValue(id);
				this.getPageElement("checkedgroupname").setValue(checkedgroupname);
			}
		} catch (Exception e) {
			throw new RadowException(e.getMessage());
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	//点击机构树查询专用事件
	@PageEvent("querybyidorg")
	@NoRequiredValidate
	@AutoNoMask
	public int query2(String id) throws RadowException {
		String codetype = this.getPageElement("codetype").getValue();
		String codename = this.getPageElement("codename").getValue();
		String[] idtext = id.split("--");
		String select = idtext[0].split("\\|")[1];
		try {
			if("true".equals(select)){
				this.getPageElement("checkedgroupid").setValue(idtext[0].split("\\|")[0]);
				this.getPageElement("checkedgroupname").setValue(idtext[1]);
			}
		} catch (Exception e) {
			throw new RadowException(e.getMessage());
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//设值
	@PageEvent("propertyName")
	public int propertyName() throws RadowException{
		String codetype = this.getParameter("codetype");
		String property = this.getParameter("property");
		String codename = this.getParameter("codename");
		if(codename==null||codename.equals("null")){
			codename="";
		}
		String value = this.getParameter("value");
//		System.out.println(codetype+"=="+property+"=="+codename+"=="+value);
		String sql="select "+codename+" code_name from code_value where code_status='1' and code_type='"+codetype+"' and code_value = '"+value+"' and code_leaf='1'" ;
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		setSelfDefResData(str);
		return EventRtnType.NORMAL_SUCCESS;

	}
	
	
	public int getOrgTreeJsonData(String nsjg, String node) throws PrivilegeException {
//		String jsonStr =OrgNodeTree.getCodeTypeJS("3",nsjg);
		if(nsjg!=null&&nsjg.equals("0")){
			String jsonStr = getJsonNsjg("3",node);
			this.setSelfDefResData(jsonStr);
		} else {
			String jsonStr = getJson("3",node);
			this.setSelfDefResData(jsonStr);
		}
		return EventRtnType.XML_SUCCESS;
	}
	
	public List authority(String userid){
		HBSession sess = HBUtil.getHBSession();
		List<String> b0111s = (List) sess.createSQLQuery("select t.b0111 from COMPETENCE_USERDEPT t where t.userid='"+userid+"'").list();
		return b0111s;
	}
	public boolean isown(List list,String b0111,boolean isadmin){
		if(isadmin){
			return true;
		}
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				if(list.get(i).equals(b0111)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	//设值
	@PageEvent("hasRule")
	public int hasRule() throws RadowException{
		Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String codevalue = this.getPageElement("memberGrid").getValue("code_value",index).toString();
		if(SysRuleBS.havaRule(codevalue)){
			//
		}
		return EventRtnType.NORMAL_SUCCESS;

	}
	
	@PageEvent("linktabstart")
	public int linkTab(String codetype) throws RadowException{
		//String userId = SysUtil.getCacheCurrentUser().getUserVO().getId();
		String  userId = SysManagerUtils.getUserId();
		String sql="Select Count(1) From User_Op_Code t where userid='"+userId+"' and code_type='"+codetype+"'";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		if(str.equals("0")){
			this.getExecuteSG().addExecuteCode("linkTab('tab2');");
		}
		return EventRtnType.NORMAL_SUCCESS;

	}
	
	@PageEvent("orgTreeJsonDataOpenTree")
	public int orgTreeJsonDataOpenTree() throws PrivilegeException {
		String jsonStr = getJson("2","");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	/**
	 * 隶属关系变更左侧机构树
	 * @return
	 * @throws PrivilegeException
	 */
	@PageEvent("orgTreeJsonChange")
	public int orgTreeJsonChange() throws PrivilegeException {
		String jsonStr = getJson("orgTreeJsonChange","");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonInfoImp")
	public int orgTreeJsonInfoImp() throws PrivilegeException {
		String jsonStr = getJson("impOrgImp","");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonNewArray")
	public int orgTreeJsonNewArray() throws PrivilegeException {
		String jsonStr = getJsonQD("newArray","");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonNewArrayLook")
	public int orgTreeJsonNewArrayLook() throws PrivilegeException {
		String jsonStr = getJsonQDLook("newArray","");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	/**
	 * 贵阳浏览权限树（机构范围管理） 
	 * @param type
	 * @param nodeother
	 * @return
	 * @throws PrivilegeException 
	 */
	public String getJsonQDLook(String type,String nodeother) throws PrivilegeException{
		treeType=0;
		//String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		
		String userid = this.getParameter("userid");
		/*//获取上级管理员id
		String overUserid = NewRangePageModel.getOverUserID(userid);*/
		//获取当前用户id(来控制机构树范围)
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//如果overUserid不为空，获得上级管理员的机构树范围权限
		//List<Object> OverB0111List = new ArrayList<Object>();
		
		HBSession sess = HBUtil.getHBSession();
		/*if(userid!=null&&!"".equals(userid)){
			//OverB0111List = getSubB0111(cueUserid);
			OverB0111List = sess.createSQLQuery("select t.b0111 from competence_userdept t where t.userid = '"+cueUserid+"' and t.type = '1'").list();
		}*/
		List<Object> b0111List = new ArrayList<Object>();
		if(userid!=null&&!"".equals(userid)){
			//b0111List = getSubB0111(userid);
			b0111List = sess.createSQLQuery("select t.b0111 from competence_userdept t where t.userid = '"+userid+"' and t.type = '1'").list();
		}
		
		String node="";
		if("".equals(nodeother)){
			node = this.getParameter("node");
		}else{
			node = nodeother;
		}
		if(node.equals("-1")){//首次访问
				//获取用户权限范围内的最高级别单位的id
				List<HashMap> list =null;
				try {
					if(DBType.ORACLE==DBUtil.getDBType()){
						if("40288103556cc97701556d629135000f".equals(cueUserid)){//上级管理用户为system
							String sql="select min(b0111) b0111 from b01";
							list=CommonQueryBS.getQueryInfoByManulSQL(sql);
						}else{
							String sql="select min(b0111) from competence_userdept t where userid='"+cueUserid+"'";
							list=CommonQueryBS.getQueryInfoByManulSQL(sql);
						}
					}/*else if(DBType.MYSQL==DBUtil.getDBType()){
						String sql="select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) asc limit 1";
						list=CommonQueryBS.getQueryInfoByManulSQL(sql);
					}*/
				}catch(AppException e){
					e.printStackTrace();
				}
				if(list!=null&&list.size()>0){
					node=(String)list.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
				}
				if(node.length()<=7){
					node="-1";
				}else{
					node=node.substring(0, node.length()-4);//获取用户权限范围内的最高级别单位的上一级单位id
				}
		}
		//String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		String sql1="";
		String sql="";
		if("40288103556cc97701556d629135000f".equals(cueUserid)){//上级管理用户为system
			sql1=" select count(t1.b0111) from b01 t1 where t1.b0121=t.b0111 ";
			sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from b01 t where t.b0121='"+node+"'  order by t.sortid asc ";
		}else{
			sql1="select count(t1.b0111)  from b01 t1 ,competence_userdept s1 where t1.b0111=s1.b0111 and t1.b0121 = t.b0111 and s1.userid='"+cueUserid+"' ";
			sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from b01 t,competence_userdept s where t.b0111=s.b0111 and t.b0121='"+node+"' and s.userid='"+cueUserid+"' order by t.sortid asc ";
		}
//		String sql1 = "(select substr(b0111,1,"+nodelength1+") b01111,max(length(trim(substr(b0111,"+nodelength2+",3)))) count1 from competence_userdept t where t.b0111 like '"+node+".%' and t.USERID = '"+cueUserid+"' group by substr(b0111,1,"+nodelength1+")) cc";
//		String sql ="select cc.count1,t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from b01 t join "+sql1+" on t.b0111 = cc.b01111 order by t.sortid";
		CommonQueryBS.systemOut(sql);
		List<HashMap> list =null;
		try {
			list = CommonQueryBS.getQueryInfoByManulSQL(sql);
		} catch (AppException e) {
			e.printStackTrace();
		}//得到当前组织信息
//		List<B01> list = null;//得到当前组织信息
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (HashMap group : list) {
				Object o = group.get("b0101");
				if(o!=null){
					group.put("b0101", o.toString().replaceAll("\r|\n|\r\n", ""));
				}
				//判断b是否在subList中，返回true则表示子系统内包含该机构
				Object b = group.get("b0111");
				boolean boo = b0111List.contains(b);
				if(b0111List.size()==0){
					boo = false;//默认不选中
				}
				
				if(i==0 && last==1) {
					jsonStr.append("[");
					appendjsonQD(type, group, jsonStr, boo);
					jsonStr.append("]");
				} else if (i == 0) {
					jsonStr.append("[");
					appendjsonQD(type, group, jsonStr, boo);
				} else if (i == (last - 1)) {
					jsonStr.append(",");
					appendjsonQD(type, group, jsonStr, boo);
					jsonStr.append("]");
				} else {
					jsonStr.append(",");
					appendjsonQD(type, group, jsonStr, boo);
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		//System.out.println(jsonStr.toString());
		return jsonStr.toString();
		
	}
	
	/**
	 * 宁波权限树（机构、人员范围管理） 
	 * @param type
	 * @param nodeother
	 * @return
	 * @throws PrivilegeException 
	 */
	public String getJsonQD(String type,String nodeother) throws PrivilegeException{
		treeType=0;
		//String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		
		String userid = this.getParameter("userid");
		/*//获取上级管理员id
		String overUserid = NewRangePageModel.getOverUserID(userid);*/
		//获取当前用户id(来控制机构树范围)
		String cueUserid = SysUtil.getCacheCurrentUser().getId();
		//如果overUserid不为空，获得上级管理员的机构树范围权限
		/*List<Object> OverB0111List = new ArrayList<Object>();
		if(userid!=null&&!"".equals(userid)){
			OverB0111List = getSubB0111(cueUserid);
		}*/
		List<Object> b0111List = new ArrayList<Object>();
		if(userid!=null&&!"".equals(userid)){
			b0111List = getSubB0111(userid);
		}
		
		String node="";
		if("".equals(nodeother)){
			node = this.getParameter("node");
		}else{
			node = nodeother;
		}
		if(node.equals("-1")){//首次访问
				//获取用户权限范围内的最高级别单位的id
				List<HashMap> list =null;
				try {
					if(DBType.ORACLE==DBUtil.getDBType()){
						if("40288103556cc97701556d629135000f".equals(cueUserid)||"297e9b3367154ab2016716a77abe0ca2".equals(cueUserid)){//上级管理用户为system 或保密员
							String sql="select min(b0111) b0111 from b01";
							list=CommonQueryBS.getQueryInfoByManulSQL(sql);
						}else{
							String sql="select min(b0111) from competence_userdept t where userid='"+cueUserid+"'";
							list=CommonQueryBS.getQueryInfoByManulSQL(sql);
						}
					}/*else if(DBType.MYSQL==DBUtil.getDBType()){
						String sql="select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) asc limit 1";
						list=CommonQueryBS.getQueryInfoByManulSQL(sql);
					}*/
				}catch(AppException e){
					e.printStackTrace();
				}
				if(list!=null&&list.size()>0){
					node=(String)list.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
				}
				if(node.length()<=7){
					node="-1";
				}else{
					node=node.substring(0, node.length()-4);//获取用户权限范围内的最高级别单位的上一级单位id
				}
		}
		//String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		String sql1="";
		String sql="";
		if("40288103556cc97701556d629135000f".equals(cueUserid)||"297e9b3367154ab2016716a77abe0ca2".equals(cueUserid)){//上级管理用户为system
			sql1=" select count(t1.b0111) from b01 t1 where t1.b0121=t.b0111 ";
			sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from b01 t where t.b0121='"+node+"'  order by t.sortid asc ";
		}else{
			sql1="select count(t1.b0111)  from b01 t1 ,competence_userdept s1 where t1.b0111=s1.b0111 and t1.b0121 = t.b0111 and s1.userid='"+cueUserid+"' ";
			sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from b01 t,competence_userdept s where t.b0111=s.b0111 and t.b0121='"+node+"' and s.userid='"+cueUserid+"' order by t.sortid asc ";
		}
//		String sql1 = "(select substr(b0111,1,"+nodelength1+") b01111,max(length(trim(substr(b0111,"+nodelength2+",3)))) count1 from competence_userdept t where t.b0111 like '"+node+".%' and t.USERID = '"+cueUserid+"' group by substr(b0111,1,"+nodelength1+")) cc";
//		String sql ="select cc.count1,t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from b01 t join "+sql1+" on t.b0111 = cc.b01111 order by t.sortid";
		CommonQueryBS.systemOut(sql);
		List<HashMap> list =null;
		try {
			list = CommonQueryBS.getQueryInfoByManulSQL(sql);
		} catch (AppException e) {
			e.printStackTrace();
		}//得到当前组织信息
//		List<B01> list = null;//得到当前组织信息
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (HashMap group : list) {
				Object o = group.get("b0101");
				if(o!=null){
					group.put("b0101", o.toString().replaceAll("\r|\n|\r\n", ""));
				}
				//判断b是否在subList中，返回true则表示子系统内包含该机构
				Object b = group.get("b0111");
				boolean boo = b0111List.contains(b);
				if(b0111List.size()==0){
					boo = false;//默认不选中
				}
				
				if(i==0 && last==1) {
					jsonStr.append("[");
					appendjsonQD(type, group, jsonStr, boo);
					jsonStr.append("]");
				} else if (i == 0) {
					jsonStr.append("[");
					appendjsonQD(type, group, jsonStr, boo);
				} else if (i == (last - 1)) {
					jsonStr.append(",");
					appendjsonQD(type, group, jsonStr, boo);
					jsonStr.append("]");
				} else {
					jsonStr.append(",");
					appendjsonQD(type, group, jsonStr, boo);
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		//System.out.println(jsonStr.toString());
		return jsonStr.toString();
		
	}
	
	private String appendjsonQD(String type,HashMap map,StringBuffer sb_tree,Boolean boo){
		String icon ="";
		if(map.get("b0194").equals("1")){
			icon="./main/images/icons/companyOrgImg2.png";
		}else if(map.get("b0194").equals("2")){
			icon="./main/images/tree/leaf.gif";
		}else if(map.get("b0194").equals("3")){
			icon="./main/images/tree/folder.gif";
		}
		if(type.equals("newArray")){//青岛子系统权限树
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",checked:"+boo+",tag:'"+""+"',icon:'"+icon+"'}");
		}/*else{
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"|true',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+map.get("b0111")+"|true--"+map.get("b0101")+"')\"}");
		}*/
		return sb_tree.toString();
	}
	
	/**
	 * 根据用户ID获取机构树节点 
	 * @param subid
	 * @return
	 */
	public static List<Object> getSubB0111(String subid){
		HBSession sess = HBUtil.getHBSession();
		String sql = "select t.b0111 from competence_userdept t where t.userid = '"+subid+"' and t.type = '1'";
		List<Object> list = sess.createSQLQuery(sql).list();
		return list;
	}
	
	@PageEvent("orgTreeJsonDataPeople")
	public int orgTreeJsonDataPeople() throws PrivilegeException {
		String jsonStr = getJson("5","");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	/**
	 * 左机构信息树
	 */
	@PageEvent("orgTreeJsonDataLeftTree")
	public int orgTreeJsonDataLeftTree() throws PrivilegeException {
		String jsonStr = getJson("1","");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	/**
	 * 机构信息树
	 */
	@PageEvent("orgTreeJsonDataTree")
	public int orgTreeJsonDataTree() throws PrivilegeException {
		String jsonStr = getJson("0","");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	/**
	 * 机构批量转移，加载右边树
	 */
	@PageEvent("orgTreeJsonDataright")
	public int orgTreeJsonDataright() throws PrivilegeException {
		String jsonStr = getJson("7","");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	/**
	 * 机构回收用
	 * @return
	 * @throws PrivilegeException
	 */
	@PageEvent("orgTreeJsonDataLeftTreeOut")
	public int orgTreeJsonDataLeftTreeOut() throws PrivilegeException {
		String jsonStr = getJsonOUT("6","");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	
	public String getJson(String type,String nodeother){
		String sign = this.getParameter("sign");//浏览  编辑  机构树编辑
		String deptTable = "";
		String b01Table = "";
		/*if("look".equals(sign)){
			deptTable = "competence_userdept";
			b01Table = "competence_userdeptPeo_look";
		}else{*/
			deptTable = "competence_userdept";
			b01Table = "B01";
		/* } */
		

		treeType=0;
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String node="";
		if("".equals(nodeother)){
			node = this.getParameter("node");
		}else{
			node = nodeother;
		}
		if(node.equals("-1")){//首次访问
			//获取用户权限范围内的最高级别单位的id
			List<HashMap> list =null;
			try {
				if(DBType.ORACLE==DBUtil.getDBType()){
					String sql="select min(b0111) b0111 from "+deptTable+" t where userid='"+cueUserid+"'";
					list=CommonQueryBS.getQueryInfoByManulSQL(sql);
				}else if(DBType.MYSQL==DBUtil.getDBType()){
					String sql="select b0111 from "+deptTable+" t where userid='"+cueUserid+"' order by length(b0111) asc limit 1";
					list=CommonQueryBS.getQueryInfoByManulSQL(sql);
				}
			}catch(AppException e){
				e.printStackTrace();
			}
			if(list!=null&&list.size()>0){
				node=(String)list.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
			}
			if(node==null||node.length()<=7){
				node="-1";
			}else{
				node=node.substring(0, node.length()-4);//获取用户权限范围内的最高级别单位的上一级单位id
			}
		}
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		String sql1="";
		String sql="";
		//if(("system".equals(loginName)&&"UnPeopleLook".equals(sign)) || "UnPeopleLook".equals(sign)){//system用户不加权限
		if("system".equals(loginName)){//system用户不加权限
			sql1=" select count(t1.b0111) from "+b01Table+" t1 where t1.b0121=t.b0111 ";
			sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from "+b01Table+" t where t.b0121='"+node+"'  order by t.sortid asc ";			
		}else{
			sql1="select count(t1.b0111)  from "+b01Table+" t1 ,"+deptTable+" s1 where t1.b0111=s1.b0111 and t1.b0121 = t.b0111 and s1.userid='"+cueUserid+"' ";
			sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from "+b01Table+" t,"+deptTable+" s where t.b0111=s.b0111 and t.b0121='"+node+"' and s.userid='"+cueUserid+"' order by t.sortid asc ";
		}
//		if(node.equals("-1")&&!("5".equals(type)||"3".equals(type)||"0".equals(type))){//首次访问
//				//获取用户权限范围内的最高级别单位的id
//				List<HashMap> list =null;
//				try {
//					if(DBType.ORACLE==DBUtil.getDBType()){
//						String sql="select min(b0111) b0111 from "+deptTable+" t where userid='"+cueUserid+"'";
//						list=CommonQueryBS.getQueryInfoByManulSQL(sql);
//					}else if(DBType.MYSQL==DBUtil.getDBType()){
//						String sql="select b0111 from "+deptTable+" t where userid='"+cueUserid+"' order by length(b0111) asc limit 1";
//						list=CommonQueryBS.getQueryInfoByManulSQL(sql);
//					}
//				}catch(AppException e){
//					e.printStackTrace();
//				}
//				if(list!=null&&list.size()>0){
//					node=(String)list.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
//				}
//				if(node.length()<=7){
//					node="-1";
//				}else{
//					node=node.substring(0, node.length()-4);//获取用户权限范围内的最高级别单位的上一级单位id
//				}
//		}
//		if(node.equals("-1")&&("5".equals(type)||"3".equals(type)||"0".equals(type)))//干部信息主界面，如果第一次访问，返回机构树最上级的机构
//		{
//			node="-1";//默认的最上级机构
//		}
//		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
//		String sql1="";
//		String sql="";
//		//if(("system".equals(loginName)&&"UnPeopleLook".equals(sign)) || "UnPeopleLook".equals(sign)){//system用户不加权限
//		if("system".equals(loginName)){//system用户不加权限
//			sql1=" select count(t1.b0111) from "+b01Table+" t1 where t1.b0121=t.b0111 ";
//			sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from "+b01Table+" t where t.b0121='"+node+"'  order by t.sortid asc ";			
//		}else{
//			sql1="select count(t1.b0111)  from "+b01Table+" t1 ,"+deptTable+" s1 where t1.b0111=s1.b0111 and t1.b0121 = t.b0111 and s1.userid='"+cueUserid+"' ";
//			if("5".equals(type)||"3".equals(type)||"0".equals(type))
//			sql1=node.equals("-1")?" 1 ":"select count(t1.b0111)  from "+b01Table+" t1 ,"+deptTable+" s1 where t1.b0111=s1.b0111 and t1.b0121 = t.b0111 and s1.userid='"+cueUserid+"' or  substr(t1.b0111,0, 7) = '001.001' ";
//			
//			if(node.length()>=7&&("5".equals(type)||"3".equals(type)||"0".equals(type)))
//			{
//				sql1="select count(t1.b0111)  from "+b01Table+" t1 ,"+deptTable+" s1 where t1.b0111=s1.b0111 and t1.b0111 != t.b0111 and substr(t1.b0111,0,"+(node.length()+4)+")= t.b0111 and s1.userid='"+cueUserid+"' ";
//				String tempsql = " select distinct substr(s.b0111,0,"+(node.length()+4)+") from "+b01Table+" t,"+deptTable+" s where t.b0111=s.b0111 and (substr(s.b0111,0,"+node.length()+")='"+node+"' or t.b0121 = '"+node+"') and   s.b0111 != '"+node+"' and  s.userid='"+cueUserid+"'  ";
//;
//				sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from "+b01Table+" t where   t.b0111 in ("+tempsql+" )  order by t.sortid asc ";
//                 
//			}
//			else if(node.equals("-1")&&("5".equals(type)||"3".equals(type)||"0".equals(type)))//干部信息主界面，如果第一次访问，返回机构树最上级的机构
//			{
//				sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from "+b01Table+" t where t.b0121='"+node+"' order by t.sortid asc ";
//			}
//			else 
//			sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from "+b01Table+" t,"+deptTable+" s where t.b0111=s.b0111 and t.b0121='"+node+"' and s.userid='"+cueUserid+"' order by t.sortid asc ";
//		}
//		String sql1 = "(select substr(b0111,1,"+nodelength1+") b01111,max(length(trim(substr(b0111,"+nodelength2+",3)))) count1 from competence_userdept t where t.b0111 like '"+node+".%' and t.USERID = '"+cueUserid+"' group by substr(b0111,1,"+nodelength1+")) cc";
//		String sql ="select cc.count1,t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from b01 t join "+sql1+" on t.b0111 = cc.b01111 order by t.sortid";
		CommonQueryBS.systemOut(sql);
		List<HashMap> list =null;
		try {
			list = CommonQueryBS.getQueryInfoByManulSQL(sql);
		} catch (AppException e) {
			e.printStackTrace();
		}//得到当前组织信息
//		List<B01> list = null;//得到当前组织信息
		StringBuffer jsonStr = new StringBuffer();
		String unsort="";
		if(this.request!=null){
			unsort = this.request.getParameter("unsort");
		}
		
		
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			
			if("5".equals(type)&&"1".equals(unsort)) {
				for (HashMap group : list) {
					Object o = group.get("b0101");
					if(o!=null){
						group.put("b0101", o.toString().replaceAll("\r|\n|\r\n", ""));
					}
					if(i==0 && last==1) {
						jsonStr.append("[");
						appendjson(type, group, jsonStr);
						jsonStr.append("]");
					}else if (i == 0) {
						jsonStr.append("[");
						appendjson(type, group, jsonStr);
					}else if (i == (last - 1)) {
						jsonStr.append(",");
						appendjson(type, group, jsonStr);
						jsonStr.append("]");
					} else {
						jsonStr.append(",");
						appendjson(type, group, jsonStr);
					}
					i++;
				}
			}else {
			
				for (HashMap group : list) {
					Object o = group.get("b0101");
					if(o!=null){
						group.put("b0101", o.toString().replaceAll("\r|\n|\r\n", ""));
					}
					if(i==0 && last==1) {
						jsonStr.append("[");
						appendjson(type, group, jsonStr);
						jsonStr.append("]");
					}else if (i == 0) {
						jsonStr.append("[");
						appendjson(type, group, jsonStr);
					}else if (i == (last - 1)) {
						jsonStr.append(",");
						appendjson(type, group, jsonStr);
						jsonStr.append("]");
					} else {
						jsonStr.append(",");
						appendjson(type, group, jsonStr);
					}
					i++;
				}
			}
		} else {
			jsonStr.append("{}");
		}
		//System.out.println(jsonStr.toString());
		return jsonStr.toString();
		
	}
	public String getJson(String type,String nodeother,String sign){
		//String sign = this.getParameter("sign");//浏览  编辑  机构树编辑
		String deptTable = "";
		/*
		 * if("look".equals(sign)){ deptTable = "competence_userdept"; }else{
		 */
			deptTable = "competence_userdept";
		/* } */
		
		String b01Table = "";
		/*if ("UnPeopleLook".equals(sign)) {
			b01Table = "competence_userdeptPeo_look";
			deptTable = "competence_userdept";
		}else {*/
			b01Table = "B01";
		/* } */
		treeType=0;
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String node="";
		if("".equals(nodeother)){
			node = this.getParameter("node");
		}else{
			node = nodeother;
		}
		if(node.equals("-1")){//首次访问
				//获取用户权限范围内的最高级别单位的id
				List<HashMap> list =null;
				try {
					if(DBType.ORACLE==DBUtil.getDBType()){
						String sql="select min(b0111) b0111 from "+deptTable+" t where userid='"+cueUserid+"'";
						list=CommonQueryBS.getQueryInfoByManulSQL(sql);
					}else if(DBType.MYSQL==DBUtil.getDBType()){
						String sql="select b0111 from "+deptTable+" t where userid='"+cueUserid+"' order by length(b0111) asc limit 1";
						list=CommonQueryBS.getQueryInfoByManulSQL(sql);
					}
				}catch(AppException e){
					e.printStackTrace();
				}
				if(list!=null&&list.size()>0){
					node=(String)list.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
				}
				if(node==null||node.length()<=7){
					node="-1";
				}else{
					node=node.substring(0, node.length()-4);//获取用户权限范围内的最高级别单位的上一级单位id
				}
		}
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		String sql1="";
		String sql="";
		//if(("system".equals(loginName)&&"UnPeopleLook".equals(sign)) || "UnPeopleLook".equals(sign)){//system用户不加权限
		if("system".equals(loginName)){//system用户不加权限
			sql1=" select count(t1.b0111) from "+b01Table+" t1 where t1.b0121=t.b0111 ";
			sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from "+b01Table+" t where t.b0121='"+node+"'  order by t.sortid asc ";			
		}else{
			sql1="select count(t1.b0111)  from "+b01Table+" t1 ,"+deptTable+" s1 where t1.b0111=s1.b0111 and t1.b0121 = t.b0111 and s1.userid='"+cueUserid+"' ";
			sql=" select ("+sql1+") count1, t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from "+b01Table+" t,"+deptTable+" s where t.b0111=s.b0111 and t.b0121='"+node+"' and s.userid='"+cueUserid+"' order by t.sortid asc ";
		}
//		String sql1 = "(select substr(b0111,1,"+nodelength1+") b01111,max(length(trim(substr(b0111,"+nodelength2+",3)))) count1 from competence_userdept t where t.b0111 like '"+node+".%' and t.USERID = '"+cueUserid+"' group by substr(b0111,1,"+nodelength1+")) cc";
//		String sql ="select cc.count1,t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from b01 t join "+sql1+" on t.b0111 = cc.b01111 order by t.sortid";
		CommonQueryBS.systemOut(sql);
		List<HashMap> list =null;
		try {
			list = CommonQueryBS.getQueryInfoByManulSQL(sql);
		} catch (AppException e) {
			e.printStackTrace();
		}//得到当前组织信息
//		List<B01> list = null;//得到当前组织信息
		StringBuffer jsonStr = new StringBuffer();
		String unsort="";
		if(this.request!=null){
			unsort = this.request.getParameter("unsort");
		}
		
		
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			
			if("5".equals(type)&&"1".equals(unsort)) {
				for (HashMap group : list) {
					Object o = group.get("b0101");
					if(o!=null){
						group.put("b0101", o.toString().replaceAll("\r|\n|\r\n", ""));
					}
					if(i==0 && last==1) {
						jsonStr.append("[");
						appendjson(type, group, jsonStr);
						jsonStr.append("]");
					}else if (i == 0) {
						jsonStr.append("[");
						appendjson(type, group, jsonStr);
					}else if (i == (last - 1)) {
						jsonStr.append(",");
						appendjson(type, group, jsonStr);
						jsonStr.append("]");
					} else {
						jsonStr.append(",");
						appendjson(type, group, jsonStr);
					}
					i++;
				}
			}else {
			
				for (HashMap group : list) {
					Object o = group.get("b0101");
					if(o!=null){
						group.put("b0101", o.toString().replaceAll("\r|\n|\r\n", ""));
					}
					if(i==0 && last==1) {
						jsonStr.append("[");
						appendjson(type, group, jsonStr);
						jsonStr.append("]");
					}else if (i == 0) {
						jsonStr.append("[");
						appendjson(type, group, jsonStr);
					}else if (i == (last - 1)) {
						jsonStr.append(",");
						appendjson(type, group, jsonStr);
						jsonStr.append("]");
					} else {
						jsonStr.append(",");
						appendjson(type, group, jsonStr);
					}
					i++;
				}
			}
		} else {
			jsonStr.append("{}");
		}
		//System.out.println(jsonStr.toString());
		return jsonStr.toString();
		
	}
	/**
	 * 根据用户ID获取人员管理类别     (专门用来查询上级用户)
	 * @param userid
	 * @return
	 */
	public static List<Object[]> getOverZB130(String userid){
		HBSession sess = HBUtil.getHBSession();
		List<Object[]> list = new ArrayList<Object[]>();
		String sql = "";
		if("40288103556cc97701556d629135000f".equals(userid)){//上级管理员用户为system
			String sqlZB130 = "select code_value,code_name3 from code_value where code_type = 'ZB130' and code_leaf = '1' and code_status = '1' order by code_value";
			list = sess.createSQLQuery(sqlZB130).list();
		}else{
			sql = "select t.managerid from COMPETENCE_USERMANAGER t where t.userid = '"+userid+"' and t.type = '1'";
			String zb130s = (String) sess.createSQLQuery(sql).uniqueResult();
			String zbs = "";
			if(zb130s!=null){
				String[] v = zb130s.split(",");
				for(String vv : v){
					zbs = zbs + "'" + vv + "',";
				}
				zbs = zbs.substring(0, zbs.length()-1);
			}
			sql = "select code_value,code_name3 from code_value where code_type = 'ZB130' "+("".equals(zbs)?"":"and code_value in ("+zbs+")")+" and code_leaf = '1' and code_status = '1' order by code_value";
			list = sess.createSQLQuery(sql).list();
		}
		
		return list;
	}
	/**
	 * 根据用户ID获取人员管理类别     (普通用户)
	 * @param userid
	 * @return
	 */
	public static List<Object> getSubZB130(String userid){
		HBSession sess = HBUtil.getHBSession();
		List<Object> list = new ArrayList<Object>();
		String sql = "select t.managerid from COMPETENCE_USERMANAGER t where t.userid = '"+userid+"' and t.type = '1'";
		String zb130s = (String) sess.createSQLQuery(sql).uniqueResult();
		String zbs = "";
		if(zb130s!=null){
			String[] v = zb130s.split(",");
			for(String vv : v){
				zbs = zbs + "'" + vv + "',";
			}
			zbs = zbs.substring(0, zbs.length()-1);
		}
		sql = "select code_value from code_value where code_type = 'ZB130' "+("".equals(zbs)?"":"and code_value in ("+zbs+")")+" and code_leaf = '1' and code_status = '1' order by code_value";
		list = sess.createSQLQuery(sql).list();
		
		return list;
	}

	/**
	 * 根据用户ID获取是否有非现职人员标记 
	 * @param userid
	 * @return
	 */
	public static String getlaidoff(String userid){
		HBSession sess = HBUtil.getHBSession();
		String laidoff = "";
		if("40288103556cc97701556d629135000f".equals(userid)){//上级管理员用户为system
			laidoff = "1";
		}else{
			String sql = "select t.laidoff from COMPETENCE_USERMANAGER t where t.userid = '"+userid+"' and t.type = '1'";
			laidoff = (String) sess.createSQLQuery(sql).uniqueResult();
		}
		return laidoff;
	}
	/**
	 * 机构回收用
	 * @param type
	 * @param nodeother
	 * @return
	 */
	public String getJsonOUT(String type,String nodeother){
		treeType=0;
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String node="";
		if("".equals(nodeother)){
			node = this.getParameter("node");
		}else{
			node = nodeother;
		}
		int nodelength = 0;
		if(node.equals("-1")){
			nodelength=3;
			node="001";
		}else{
			nodelength=node.length();
		}
		int nodelength1 =nodelength+4;
		int nodelength2 = nodelength1+2;
		String sql1 = "";
		sql1 = "select substr(b0111,1,"+nodelength1+") b01111,max(length(trim(substr(b0111,"+nodelength2+",3)))) count1 from competence_userdeptcpb01 t where t.b0111 like '"+node+".%' and t.USERID = '"+cueUserid+"' group by substr(b0111,1,"+nodelength1+")";
		List<Object[]> sqllist1 = HBUtil.getHBSession().createSQLQuery(sql1).list();
		String b0111 = (String) sqllist1.get(0)[0];
		String sql01 ="select b0111 from cpb01 where b0111 = '"+b0111+"'";
		int mess = HBUtil.getHBSession().createSQLQuery(sql01).executeUpdate();
		if(mess == 0 ){
			while(true){
				nodelength1 += 4;
				sql1 = "select substr(b0111,1,"+nodelength1+") b01111,max(length(trim(substr(b0111,"+nodelength2+",3)))) count1 from competence_userdeptcpb01 t where t.b0111 like '"+node+".%' and t.USERID = '"+cueUserid+"' group by substr(b0111,1,"+nodelength1+")";
				int messe = HBUtil.getHBSession().createSQLQuery(sql1).executeUpdate();
				if(messe >0 ){
					break;
				}
			}
			
		}
		String sql ="select cc.count1,t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from cpb01 t join ("+sql1+") cc on t.b0111 = cc.b01111 order by t.sortid";
		System.out.println(sql);
		CurrentUser user = SysUtil.getCacheCurrentUser();
		System.out.println(user.getId());
//		String sql ="select t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194 from cpb01 t where userid  = '"+ user.getId() +"' GROUP BY t.b0121,t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,t.b0194  order by t.sortid";
		CommonQueryBS.systemOut(sql);
		List<HashMap> list =null;
		try {
			list = CommonQueryBS.getQueryInfoByManulSQL(sql);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//得到当前组织信息
//		List<B01> list = null;//得到当前组织信息
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (HashMap group : list) {
				Object o = group.get("b0101");
				if(o!=null){
					group.put("b0101", o.toString().replaceAll("\r|\n|\r\n", ""));
				}
				if(i==0 && last==1) {
					jsonStr.append("[");
					appendjson(type, group, jsonStr);
					jsonStr.append("]");
				}else if (i == 0) {
					jsonStr.append("[");
					appendjson(type, group, jsonStr);
				}else if (i == (last - 1)) {
					jsonStr.append(",");
					appendjson(type, group, jsonStr);
					jsonStr.append("]");
				} else {
					jsonStr.append(",");
					appendjson(type, group, jsonStr);
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		//System.out.println(jsonStr.toString());
		return jsonStr.toString();
		
	}
	
	private String hasChildren(String id){
		/*String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String sql="select b.b0111 from B01 b,competence_userdept t where t.b0111=b.b0111 and t.USERID = '"+cueUserid+"' and b.B0111 like '"+id+"%' and b.b0111!='"+id+"' order by sortid";// -1其它现职人员
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();*/
//		if("3".equals(id)){
//			return "false";
//		}
//			return "true";
			
			if("0".equals(id)){
				return "true";
			}
				return "false";
		
	}
	
	private String appendjson(String type,HashMap map,StringBuffer sb_tree){
		String icon ="";
		if(map.get("b0194").equals("1")){
			icon="./main/images/icons/companyOrgImg2.png";
		}else if(map.get("b0194").equals("2")){
			icon="./main/images/tree/leaf.gif";
		}else if(map.get("b0194").equals("3")){
			icon="./main/images/tree/folder.gif";
		}
		if(type.equals(ZEROTREE)){
			//sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"dblclick\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");//双击
			//根据客户要求，改为单机
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");//双击
		}else if(type.equals(LEFTTREE)){
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");//单击
		}else if(type.equals("orgTreeJsonChange")){//隶属关闭变更
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',checked:false}");//单击
		}else if(type.equals(OPENTREE)){
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",tag:'"+""+"',icon:'"+icon+"'}");
		}else if(type.equals(FOURTREE)){
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
		}else if(type.equals(FIVETREE)){
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",editable:'"+map.get("userrule")+"',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
		}else if(type.equals(SIXTREE)){
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
		}else if(type.equals(THREETREE)){
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"|true',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+map.get("b0111")+"|true--"+map.get("b0101")+"')\"}");
		}else if(type.equals(SEVENTREE)){
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidright','"+map.get("b0111")+"')\"}");//单击
		}else if(type.equals("impOrgImp")){//机构信息界面，导出机构信息
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",checked:false,tag:'"+""+"',icon:'"+icon+"'}");
		} else{
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"|true',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+map.get("b0111")+"|true--"+map.get("b0101")+"')\"}");
			
		}
		return sb_tree.toString();
	}
	
	private String getJsonNsjg(String type, String nodeother) {
		treeType=1;
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String node="";
		if("".equals(nodeother)){
			node = this.getParameter("node");
		}else{
			node = nodeother;
		}
		int nodelength = 0;
		if(node.equals("-1")){
			nodelength=3;
			node="001";
		}else{
			nodelength=node.length();
		}
		int nodelength1 =nodelength+4;
		int nodelength2 = nodelength1+2;
		//String likeSql = DBUtil.getDBType()==DBType.ORACLE?"t.b0111||'%' and b.b0111!=t.b0111":"CONCAT(t.b0111,'.%')";
		String sql1 = "(select substr(b0111,1,"+nodelength1+") b01111,max(length(trim(substr(b0111,"+nodelength2+",3)))) count1 from competence_userdept t where t.b0111 like '"+node+".%' and t.USERID = '"+cueUserid+"' group by substr(b0111,1,"+nodelength1+")) cc";
		String sql ="select cc.count1,t.b0111,t.b0121,t.b0101,t.b0107,t.b0114,t.sortid,t.b0194 from b01 t join "+sql1+" on t.b0111 = cc.b01111 order by t.sortid";
		
		CommonQueryBS.systemOut(sql);
		List<HashMap> list =null;
		try {
			list = CommonQueryBS.getQueryInfoByManulSQL(sql);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//得到当前组织信息
//		List<B01> list = null;//得到当前组织信息
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (HashMap group : list) {
				if(i==0 && last==1) {
					jsonStr.append("[");
					appendjsonNsjg(type, group, jsonStr);
					jsonStr.append("]");
				}else if (i == 0) {
					jsonStr.append("[");
					appendjsonNsjg(type, group, jsonStr);
				}else if (i == (last - 1)) {
					jsonStr.append(",");
					appendjsonNsjg(type, group, jsonStr);
					jsonStr.append("]");
				} else {
					jsonStr.append(",");
					appendjsonNsjg(type, group, jsonStr);
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		//System.out.println(jsonStr.toString());
		return jsonStr.toString();
	}
	private String appendjsonNsjg(String type,HashMap map,StringBuffer sb_tree){
		String icon ="";
//		if(map.get("type").equals("")){
//			map.put("type", "2");
//		}
		if(map.get("b0194").equals("1")){
			icon="./main/images/icons/companyOrgImg2.png";
		}else if(map.get("b0194").equals("2")){
			icon="./main/images/tree/leaf.gif";
		}else if(map.get("b0194").equals("3")){
			icon="./main/images/tree/folder.gif";
		}
		if(type.equals(LEFTTREE)){
//			if(map.get("type").equals("2")){
//				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:alert('您没有权限')\"}");
//			}else{
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
//			}
		}else if(type.equals(OPENTREE)){
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",tag:'"+""+"',icon:'"+icon+"'}");
		}else if(type.equals(FOURTREE)){
			sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
		}else if(type.equals(FIVETREE)){
//			if(map.get("type").equals("2")){
//				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',disabled:true,leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"'}");
//			}else{
				//sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",editable:'"+map.get("userrule")+"',icon:'"+icon+"',\"dblclick\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
				//根据客户要求，改为单击
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",editable:'"+map.get("userrule")+"',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
//			}
		}else if(type.equals(SIXTREE)){
//			if(map.get("type").equals("2")){
//				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:alert('您没有权限')\"}");
//			}else{
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
//			}
		}else if(type.equals(THREETREE)){
//			if(map.get("type").equals("2")||"0".equals(map.get("type"))){//机构授权 2无权限 0查看权限 不能在职务中被选择。机构类型为机构分组不能在机构中被选择
//				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"|false',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+map.get("b0111")+"|false--"+map.get("b0101")+"')\"}");
//			}else 
				if(map.get("b0114")==null || "".equals(map.get("b0114"))){//内设机构 无组织机构编码，弹出提示框
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"|false',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:alert('该机构没有单位编码，不能导出！')\"}");
			}else{
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"|true',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+map.get("b0111")+"|true--"+map.get("b0101")+"')\"}");
			}
		}else{
//			if(map.get("type").equals("2")){
//				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"|false',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+map.get("b0111")+"|false--"+map.get("b0101")+"')\"}");
//			}else{
				sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"|true',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+map.get("b0111")+"|true--"+map.get("b0101")+"')\"}");
//			}
			
		}
		return sb_tree.toString();
	}
	
	private String appendjson1(String type,HashMap map,StringBuffer sb_tree){
		String icon ="";
		if(map.get("b0194").equals("1")){
			icon="./main/images/icons/companyOrgImg2.png";
			//map.put("b0101", map.get("b0101")+"<button onclick=openSortWin(\""+map.get("b0111")+"\") style=\"width:65px;height:20px;font-size:12px;background-color:#F5F5F5;\">人员排序</button>");
			//map.put("b0101", map.get("b0101")+"&nbsp;<a  onclick=openSortWin(\""+map.get("b0111")+"\") style=\"width:50px;height:14px;background-color:#F5F5F5;border:1px solid #A9A9A9;color:black;text-align:center;text-decoration:none;display:inline-block;font-size:11px;margin: 1px 1px;cursor:pointer;box-shadow: 0 2px 2px #A9A9A9;\">人员排序</a>");
			map.put("b0101", map.get("b0101")+"&nbsp;<img alt=\"人员排序\" width=\"10px\" height=\"10px\" onclick=openSortWin(\""+map.get("b0111")+"\") src=\"./main/images/tree/713kxN2yqn.png\"> ");   
		}else if(map.get("b0194").equals("2")){
			icon="./main/images/tree/leaf.gif";
			//map.put("b0101", map.get("b0101")+"<button onclick=openSortWin(\""+map.get("b0111")+"\") style=\"width:65px;height:20px;font-size:12px;background-color:#F5F5F5;\">人员排序</button>");
		    //map.put("b0101", map.get("b0101")+"&nbsp;<a  onclick=openSortWin(\""+map.get("b0111")+"\") style=\"width:50px;height:14px;background-color:#F5F5F5;border:1px solid #A9A9A9;color:black;text-align:center;text-decoration:none;display:inline-block;font-size:11px;margin: 1px 1px;cursor:pointer;box-shadow: 0 2px 2px #A9A9A9;\">人员排序</a>");
			map.put("b0101", map.get("b0101")+"&nbsp;<img alt=\"人员排序\" width=\"10px\" height=\"10px\" onclick=openSortWin(\""+map.get("b0111")+"\") src=\"./main/images/tree/713kxN2yqn.png\"> ");   
		}else if(map.get("b0194").equals("3")){
			icon="./main/images/tree/folder.gif";
		}
		//sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",editable:'"+map.get("userrule")+"',icon:'"+icon+"',\"dblclick\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
		//根据客户要求，改为单击
		sb_tree.append(" {text: '"+map.get("b0101")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",editable:'"+map.get("userrule")+"',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
		return sb_tree.toString();
	}
	
	
	
	
	//点击树查询事件
	@PageEvent("gbjdLogin")
	@NoRequiredValidate
	@AutoNoMask
	public int gbjdLogin(String id) throws RadowException {
		String url = this.request.getParameter("url");
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		try {
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery("select loginname, sec  from smt_user where userid='"+user.getId()+"'").list();
			String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJTrQ2uKF1wb6SQmvMqCjhsd0WRvWRK/nrhYj5mZ3D0ZHJ2mqEypXh0Xac8GrgTwbEF1NKKFJyKS7Wl5jHIkoTUCAwEAAQ==";
			
			Object[] o = list.get(0);
			byte[] code2 = RSACoder.encryptByPublicKey(o[0].toString().getBytes(), Base64.decodeBase64(publicKey.getBytes()));
			String username = new String(Base64.encodeBase64(code2));
			this.getExecuteSG().addExecuteCode("var username=encodeURI('"+username+"').replace(/\\+/g, '%2B');"+
						"var password=encodeURI('"+o[1]+"').replace(/\\+/g, '%2B');"
						+ "var url = 'http://"+url+this.contextPath+"/LogonDialog2.jsp?u='+username+'&p='+password;"
								+ "window.open(url)");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
}
