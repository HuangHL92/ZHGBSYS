package com.insigma.siis.local.pagemodel.gbmc;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.utils.DBUtils;

import java.io.File;
import java.util.List;

public class YNTPGLPageModel extends PageModel {
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String yn_name1 = this.getPageElement("yn_name1").getValue();
		String yn_type1 = this.getPageElement("yn_type1").getValue();
		String where = "";
		if(yn_name1!=null&&!"".equals(yn_name1)){
			where += " and y.yn_name like '%"+yn_name1+"%'";
		}
		if(yn_type1!=null&&!"".equals(yn_type1)){
			where += " and y.yn_type = '"+yn_type1+"'";
		}
		
		String sql="select y.*,i.info02,i.info01,GET_gname(i.info02) gname from JS2_YNTP y left join JS2_YNTP_INFO i on y.yn_id=i.yn_id and i.info02 is not null where y.yn_userid='"+SysManagerUtils.getUserId()+"' "+where+" order by yn_sysno desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("setParaToSession")
	public int setParaToSession() throws RadowException {
		String key = this.getPageElement("ParaKey").getValue();
		String value = this.getPageElement("ParaValue").getValue();
		request.getSession().setAttribute(key, value);
		return EventRtnType.SPE_SUCCESS;
	}
	@PageEvent("allDelete")
	@Transaction
	public int allDelete(String yn_id) throws RadowException, AppException{
		
		try {
			HBSession sess = HBUtil.getHBSession();
			HBUtil.executeUpdate("delete from JS2_YNTP where yn_id=?",new Object[]{yn_id});
			HBUtil.executeUpdate("delete from tpb_att where rb_id=?",new Object[]{yn_id});
			String directory = YNTPFileExportBS.HZBPATH+"zhgbuploadfiles" + File.separator +yn_id+ File.separator;
			JSGLBS.deleteDirectory(directory);
			
			HBUtil.executeUpdate("delete from TPHJ1 where yn_id=?",new Object[]{yn_id});
			HBUtil.executeUpdate("delete from js2_yntp_info where yn_id=?",new Object[]{yn_id});
			sess.flush();
			this.getExecuteSG().addExecuteCode("$('#yn_id').val(''); $('#yn_name').val('');");
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveShareInfo")
	@Transaction
	public int saveShareInfo(String yn_id) throws RadowException, AppException{
		String tphjselectH = this.getPageElement("tphjselectH").getValue();
		String unameH = this.getPageElement("unameH").getValue();
		
		String username = SysManagerUtils.getUserloginName();
		
		try {
			if(username.equals(unameH)){
				this.setMainMessage("无法共享当前用户！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			String sql = "select t.username from SMT_USER t where t.loginname='"+unameH+"'";
			List<Object> list = sess.createSQLQuery(sql).list();
			if(list.size()==0){
				this.setMainMessage("用户名不存在！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			sess.createSQLQuery("update JS2_YNTP_INFO set info02=?,info03=sysdate where yn_id=? and info01=?")
						.setString(0, unameH).setString(1, yn_id).setString(2, tphjselectH).executeUpdate();
			sess.flush();
			this.setNextEventName("memberGrid.dogridquery");
			this.getExecuteSG().addExecuteCode("Ext.getCmp('expFile').close()");
		}catch (Exception e) {
			this.setMainMessage("保存失败！"+e.getMessage());
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("unsaveShareInfo")
	@Transaction
	public int unsaveShareInfo(String yn_id) throws RadowException, AppException{
		
		
		
		try {
			HBSession sess = HBUtil.getHBSession();
			sess.createSQLQuery("update JS2_YNTP_INFO set info02=null,info03=null where yn_id=? and info02 is not null")
						.setString(0, yn_id).executeUpdate();
			sess.flush();
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			this.setMainMessage("保存失败！"+e.getMessage());
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		
		String node = this.getParameter("node");//部门ID
		String isFirst = node;
		StringBuffer jsonStr = new StringBuffer();
		HBSession sess = HBUtil.getHBSession();
		jsonStr.append("[");
		
		String sql = "";
		boolean leaf = false;
		if("-1".equals(node)){//第一次加载
			jsonStr.append("{\"text\" :\"" + "干部名册"
					+ "\" ,\"id\" :\"" + "001"
					+ "\" ,\"cls\" :\"folder\",");
			jsonStr.append("\"children\" :[");
			
			sql = "select '0','干部总名册' from dual "
					+ " union all "
					+"select '1','重点岗位' from dual "
					+ " union all "
					+"select '8','两头干部名册' from dual "
					+ " union all "
					+"select '3','年轻干部' from dual "
					+ " union all "
					+"select '4','专业干部' from dual "
					+ " union all "
					+"select '5','干部梯队' from dual "
					+ " union all "
					+"select '6','结构性干部' from dual "
					+ " union all "
					+"select '7','年龄段干部' from dual "
					+ " union all "
					+ "select '2','职级晋升、到龄等相关名册' from dual ";
		}else {
			sql = "select id,name from A01_GBMC a where a.type = '"+node+"' order by to_number(a.sortid)";
			leaf = true;
		}
		
		List<Object[]> users_groups = sess.createSQLQuery(sql).list();
		if(users_groups!=null&&users_groups.size()>0){
			for(Object[] ug : users_groups){
				String id = ""+ug[0];
				String name = ""+ug[1];
				/*
				 * if("group".equals(type)){ icon=""; }else if("-1".equals(type)){
				 * icon="image/u49.png"; leaf="true"; }else if("2".equals(type)){
				 * icon="images/insideOrgImg1.png"; leaf="true"; }else{
				 * icon="image/icon021a6.gif"; leaf="true"; }
				 */
				jsonStr.append("{\"text\" :\"" + name  + "\" ,"
						+ "\"id\" :\"" + id + "\" ,"
						/* + "\"icon\" :\"" + icon + "\" ," */
						+ "\"leaf\" :"+leaf+","
				/* + "\"cls\" :\"folder\"," */
						);
				jsonStr.append("\"href\":");
				jsonStr.append("\"javascript:radow.doEvent('querybyid','"+ id + "')\"");
				jsonStr.append("},");
			}
		}
		
		if("-1".equals(node)){//第一次加载
			jsonStr.deleteCharAt(jsonStr.length()-1);
			jsonStr.append("]},");
		}
		jsonStr.deleteCharAt(jsonStr.length()-1);
		jsonStr.append("]");
		
		System.out.println(jsonStr.toString());
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
}
