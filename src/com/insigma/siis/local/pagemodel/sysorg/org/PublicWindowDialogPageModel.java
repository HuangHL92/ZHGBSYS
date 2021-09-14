package com.insigma.siis.local.pagemodel.sysorg.org;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
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
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.UserOpCode;
import com.insigma.siis.local.business.entity.UserOpCodeId;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.publicServantManage.QueryPersonListPageModel;

public class PublicWindowDialogPageModel extends PageModel {
	
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
	
	public PublicWindowDialogPageModel(){
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
	public int getOrgTreeJsonData() throws PrivilegeException, AppException {
		String node = (String)this.getParameter("node");
		String codetype = this.getParameter("codetype");
		String codename = this.getParameter("codename");
		String codevalueparameter = this.getParameter("codevalueparameter");
		String nsjg = this.getParameter("nsjg");
		if(StringUtil.isEmpty(codename) || codename.equals("code_name")){
			codename = "code_name";
		}else if(codename.equals("code_name2")){
			codename = "code_name2";
		}else if(codename.equals("code_name3")){
			codename = "code_name3";
		}else{
			throw new AppException("系统错误！错误信息：codename参数设定错误！");
		}
		//组织机构树
		if("orgTreeJsonData".equals(codetype)){
			if(node.equals("-1")){
				//node = SysManagerUtils.getUserOrgid();
			}else{
				node = node.split("[|]")[0];
			}
			return getOrgTreeJsonData(codetype,node,nsjg);
		}
		
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		
		if(node.equals("-1")){
			sql.append("select t.code_value,t.sub_code_value,t."+codename+",t.code_leaf from code_value t where t.code_status='1' and t.sub_code_value ='-1' and t.code_type='"+codetype+"' ");
			sql2.append("select t.code_value,t.sub_code_value,t."+codename+",t.code_leaf from code_value t where t.code_status='1' and t.code_type='9999' ");
			if("ZB09".equals(codetype)
					&&codevalueparameter!=null&&!"undefined".equals(codevalueparameter)&&!"".equals(codevalueparameter)){
				if(codevalueparameter.equals("01")){
					sql.append(" and (t.code_value like '01%' or t.code_value like '02%' or t.code_value like '09%') ");
				}else if(codevalueparameter.equals("08")||codevalueparameter.equals("codevalueparameter")){
				}else if(codevalueparameter.equals("02")){
					sql.append(" and t.code_value like '08%' ");
				}else if(!codevalueparameter.equals("")){
					sql.append(" and t.code_value like '"+codevalueparameter+"%' ");
				}
				
				
				if(codevalueparameter.equals("01")){
					sql2.append(" and (t.code_value like '01%' or t.code_value like '02%' or t.code_value like '09%') ");
				}else if(codevalueparameter.equals("08")||codevalueparameter.equals("codevalueparameter")){
				}else if(codevalueparameter.equals("02")){
					sql2.append(" and t.code_value like '08%' ");
				}else if(!codevalueparameter.equals("")){
					sql2.append(" and t.code_value like '"+codevalueparameter+"%' ");
				}
			}
			sql.append(" order by t.code_value ");
			sql2.append(" order by t.code_value ");
		}else{
			//以|分割code_value | code_leaf
			node = node.split("[|]")[0];
			sql.append("select t.code_value,t.sub_code_value,t."+codename+",t.code_leaf from code_value t where t.code_status='1' and t.sub_code_value='"+node+"' and t.code_type='"+codetype+"' ");
			sql2.append("select t.code_value,t.sub_code_value,t."+codename+",t.code_leaf from code_value t where t.code_status='1' and  t.code_type= '"+codetype+"' and t.code_value='"+node+"'");
			if("ZB09".equals(codetype)&&codevalueparameter!=null&&!"undefined".equals(codevalueparameter)&&!"".equals(codevalueparameter)){
				if(codevalueparameter.equals("01")){
					sql.append(" and (t.code_value like '01%' or t.code_value like '02%' or t.code_value like '09%') ");
				}else if(codevalueparameter.equals("08")||codevalueparameter.equals("codevalueparameter")){
				}else if(codevalueparameter.equals("02")){
					sql.append(" and t.code_value like '08%' ");
				}else if(!codevalueparameter.equals("")){
					sql.append(" and t.code_value like '"+codevalueparameter+"%' ");
				}
				
				
				if(codevalueparameter.equals("01")){
					sql2.append(" and (t.code_value like '01%' or t.code_value like '02%' or t.code_value like '09%') ");
				}else if(codevalueparameter.equals("08")||codevalueparameter.equals("codevalueparameter")){
				}else if(codevalueparameter.equals("02")){
					sql2.append(" and t.code_value like '08%' ");
				}else if(!codevalueparameter.equals("")){
					sql2.append(" and t.code_value like '"+codevalueparameter+"%' ");
				}
			}
			sql.append(" order by t.code_value ");
			sql2.append(" order by t.code_value ");
		} 
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql.toString()).list();//得到当前组织信息
		List<Object[]> groups = HBUtil.getHBSession().createSQLQuery(sql2.toString()).list();//得到当前组织信息
		//只显示所在的组织及下级组织 不在组织中 则显示全部
		List<Object[]> choose = new ArrayList();
		for(int i=0;i<groups.size();i++){
			for(int j=0;j<groups.size();j++){
				if(groups.get(j)[0].equals(groups.get(i)[1])){
					groups.remove(i);
					i--;
				}
			}
		}
		boolean equel = false;
		if(!groups.isEmpty()){
			for(int i = 0;i<list.size();i++){
				for(int j = 0;j<groups.size();j++){
					if(groups.get(j)[0].equals(list.get(i)[0])){
						choose.add(groups.get(j));
						equel = true;
					}
				}
			}
		}
		if(equel){
			list = choose;
		}
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (Object[] group : list) {
				if(i==0 && last==1) {
					//是否可选
					//((group[3]==null||StringUtil.isEmpty(group[3].toString())||group[3].toString().trim().equals("1"))?true:false)
					jsonStr.append("[{\"text\" :\"" + group[2]
							+ "\" ,\"id\" :\"" + group[0] +"|" +((group[3]==null||StringUtil.isEmpty(group[3].toString())||group[3].toString().trim().equals("1"))?true:false)
							+ "\" ,\"leaf\" :" + hasChildren(codetype,group[0].toString())
//							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
							+ " ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group[0] + "')\"");
					jsonStr.append("}]");
				}else if (i == 0) {
					jsonStr.append("[{\"text\" :\"" + group[2]
							+ "\" ,\"id\" :\"" + group[0] +"|" +((group[3]==null||StringUtil.isEmpty(group[3].toString())||group[3].toString().trim().equals("1"))?true:false)
							+ "\" ,\"leaf\" :" + hasChildren(codetype,group[0].toString())
//							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
							+ " ,\"cls\" :\"folder\",");
							jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group[0] + "')\"");
					jsonStr.append("}");
				}else if (i == (last - 1)) {
					jsonStr.append(",{\"text\" :\"" + group[2]
							+ "\" ,\"id\" :\"" + group[0] +"|" +((group[3]==null||StringUtil.isEmpty(group[3].toString())||group[3].toString().trim().equals("1"))?true:false)
							+ "\" ,\"leaf\" :" + hasChildren(codetype,group[0].toString())
							+ " ,\"cls\" :\"folder\",");
//							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group[0] + "')\"");
					jsonStr.append("}]");
				} else {
					
					jsonStr.append(",{\"text\" :\"" + group[2]
							+ "\" ,\"id\" :\"" + group[0] +"|" +((group[3]==null||StringUtil.isEmpty(group[3].toString())||group[3].toString().trim().equals("1"))?true:false)
							+ "\" ,\"leaf\" :" + hasChildren(codetype,group[0].toString())
							+ " ,\"cls\" :\"folder\",");
//							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group[0] + "')\"");
					jsonStr.append("}");
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	@PageEvent("cancelBtn.onclick")
	public int cancel(String value) throws AppException, RadowException {
		String closewin = this.getPageElement("closewin").getValue();
		String property = this.getPageElement("property").getValue();
		this.closeCueWindow(closewin);
		//System.out.println(value);
		this.getExecuteSG().addExecuteCode("parent.returnwin"+property+"('"+value+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//刷新列表
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String likeQuery = this.getPageElement("likeQuery").getValue();
		String codetype = this.getPageElement("codetype").getValue();
		String codename = this.getPageElement("codename").getValue();
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
		} else {
			sb.append("select code_value,"+codename+" code_name from code_value where code_status='1' and  code_leaf='1' and code_type='"+codetype+"'");
			if (!likeQuery.equals("")){
				strcon = "(code_name like '%" + likeQuery +"%'";
				sb.append(" and ");
				sb.append(strcon);
				strcon1 = "code_spelling like '%" + likeQuery.toUpperCase() +"%')";
				sb.append(" or ");
				sb.append(strcon1);
			}
		}
		
	//	System.out.println(sb.toString());
		this.pageQuery(sb.toString(), "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	@PageEvent("likeQuery")
	public int likeQuery(String name) throws AppException, RadowException {
//		System.out.println(name);
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	/**
	 * 是否有子数据
	 * @param codetype
	 * @param id
	 * @return
	 */
	private String hasChildren(String codetype,String id){
		String sql="select * from code_value  where code_status='1' and sub_code_value='"+id+"' and code_type='"+codetype+"'";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(list!=null && list.size()>0){
			return "false";
		}else{
			return "true";
		}
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
	
	//确认
	@PageEvent("treeyesBtn")
	public int treeyes() throws RadowException{
		String codevalue = this.getPageElement("checkedgroupid").getValue().toString();
		String codename = this.getPageElement("checkedgroupname").getValue().toString();
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
	
	
	
	
	
	private String hasChildren(String id, String nsjg, String nsjg1){
		//内设机构不查询标示
		String nsjgsql = (nsjg!=null && nsjg.equals("0")) ? "And b.b0194<>'2' " : " ";
		String sql="from B01 b where B0121='"+id+"' "+nsjgsql+" order by sortid";// -1其它现职人员
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		if(list!=null && list.size()>0){
			return "false";
		}else{
			return "true";
		}
	}
	//初始化组织机构树
	public int getOrgTreeJsonData(String codetype,String rootid, String nsjg) throws PrivilegeException {//+ "\" ,\"leaf\" :\"" + hasChildren	
		//内设机构不查询标示
		String nsjgsql = (nsjg!=null && nsjg.equals("0")) ? "And b.b0194<>'2' " : " ";
		String node = rootid;
		String sql="from B01 b where B0121='"+node+"' "+nsjgsql+" order by sortid";// B0121='-1' 其它现职人员
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();//得到当前组织信息
		//只显示所在的组织及下级组织 不在组织中 则显示全部
		List<B01> choose = new ArrayList<B01>();
		String sql2="from B01 where B0111='"+node+"'";
		List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();//得到当前组织信息
		for(int i=0;i<groups.size();i++){
			for(int j=0;j<groups.size();j++){
				if(groups.get(j).getB0111().equals(groups.get(i).getB0121())){
					groups.remove(i);
					i--;
				}
			}
		}
		boolean equel = false;
		if(!groups.isEmpty()){
			for(int i = 0;i<list.size();i++){
				for(int j = 0;j<groups.size();j++){
					if(groups.get(j).getB0111().equals(list.get(i).getB0111())){
						choose.add(groups.get(j));
						equel = true;
					}
				}
			}
		}
		if(equel){
			list = choose;
		}
		StringBuffer jsonStr = new StringBuffer();
		String companyOrgImg = request.getContextPath()+"/pages/sysorg/org/images/companyOrgImg2.png";
		String insideOrgImg = request.getContextPath()+"/pages/sysorg/org/images/insideOrgImg1.png";
		String groupOrgImg = request.getContextPath()+"/pages/sysorg/org/images/groupOrgImg1.png";
		String wrongImg = request.getContextPath()+"/pages/sysorg/org/images/wrong.gif";
		String path = companyOrgImg;
		
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		String loginnname=user.getLoginname();
		List<GroupVO> groups1 = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
		UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
		boolean issupermanager=new DefaultPermission().isSuperManager(vo);
		List<String> b0111s =new ArrayList();
		boolean isadmin =false;
		if(groups1.isEmpty() || issupermanager ||loginnname.equals("admin")){
			isadmin =true;
		}else{
			b0111s=authority(cueUserid);
		}
		
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (B01 group : list) {
				String select = "true";
				Boolean own = isown(b0111s,group.getB0111(),isadmin);
				if(i==0 && last==1) {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
						
					}else if (group.getB0194().equals("3")){
						select = "false";
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					if(!own){
						path=wrongImg;
					}
					jsonStr.append("[{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()+"|" + select
							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111(),nsjg,nsjg)
//							+ "\" ,\"cls\" :\"folder\",");
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\"");
					if(own){
						jsonStr.append(",\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyidorg','"
								+ group.getB0111()+"|" + select+"--"+group.getB0101() + "')\"");
					}
					jsonStr.append("}]");
				}else if (i == 0) {//f
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
						
					}else if (group.getB0194().equals("3")){
						select = "false";
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					if(!own){
						path=wrongImg;
					}
					jsonStr.append("[{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()+"|" + select
							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111(),nsjg,nsjg)
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\"");
					if(own){
						jsonStr.append(",\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyidorg','"
								+ group.getB0111()+"|" + select+"--"+group.getB0101() + "')\"");
					}
					jsonStr.append("}");
				}else if (i == (last - 1)) {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
						
					}else if (group.getB0194().equals("3")){
						select = "false";
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					if(!own){
						path=wrongImg;
					}
					jsonStr.append(",{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()+"|" + select
							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111(),nsjg,nsjg)
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\"");
					if(own){
						jsonStr.append(",\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyidorg','"
								+ group.getB0111()+"|" + select+"--"+group.getB0101() + "')\"");
					}
					jsonStr.append("}]");
				} else {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
						
					}else if (group.getB0194().equals("3")){
						select = "false";
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					if(!own){
						path=wrongImg;
					}
					jsonStr.append(",{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()+"|" + select
							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111(),nsjg,nsjg)
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\"");
					if(own){
						jsonStr.append(",\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyidorg','"
								+ group.getB0111()+"|" + select+"--"+group.getB0101() + "')\"");
					}
					jsonStr.append("}");
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
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
}
