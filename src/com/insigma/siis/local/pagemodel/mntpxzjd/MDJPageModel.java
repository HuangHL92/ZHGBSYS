package com.insigma.siis.local.pagemodel.mntpxzjd;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.cadremgn.util.ExtTreeNodeStr;
import com.insigma.siis.local.pagemodel.cadremgn.util.JsonUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;

public class MDJPageModel extends PageModel {
	
	
	@PageEvent("initX")
	@Transaction
	public int initX(String yn_id) throws RadowException, AppException{
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@Override
	public int doInit() throws RadowException {
		String userid = SysManagerUtils.getUserId();
		String sql = " select * "
  +" from (select y.*, "
  +"              (select username from smt_user where userid = y.userid) mnur02, "
  +"              '1' type "
  +"         from historymd y "
  +"        where userid = '"+userid+"' "
  +"       union all "
  +"       select y.*, "
  +"              (select (select username from smt_user where userid = u.mnur02) "
  +"                 from hz_LSMD_userref u "
  +"                where mnur01 = '"+userid+"' "
  +"                  and y.mdid = u.mdid) mnur02, "
  +"              '2' type "
  +"         from historymd y "
  +"        where exists (select 1 "
  +"                 from hz_LSMD_userref u "
  +"                where mnur01 = '"+userid+"' "
  +"                  and u.mdid = y.mdid) "
  +"         ) "
  +" order by createdate desc";
		
		try {
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, String>> list = cqbs.getListBySQL2(sql);
			Map<String, String> map = new LinkedHashMap<String, String>();
			if (list != null && list.size() > 0) {
				for (HashMap<String, String> map1 : list) {
					map.put(map1.get("mdid"), map1.get("mdmc"));
				}
			}
			((Combo)this.getPageElement("mdid")).setValueListForSelect(map);
			
			
			
		}catch (Exception e) {
			this.setMainMessage("下拉框数据报错！");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * 标题树更新
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("TreeJsonData")
	public int updateTree() throws RadowException, AppException{
		String node=request.getParameter("node");

		String userid = SysManagerUtils.getUserId();
		String sql =" select t.*,(select count(1) from HZ_MNTP_LWXJ x where  x.lwxj02=t.lwxj00) c "
				+ " from HZ_MNTP_LWXJ t where lwxj02='"+node+"'  and userid='"+userid+"' order by lwxj03";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, String>> list=cqbs.getListBySQL2(sql);
		List<ExtTreeNodeStr> listTree=new ArrayList<ExtTreeNodeStr>();
		if(list!=null&&list.size()>0){
			for(HashMap<String, String> a:list){
				ExtTreeNodeStr etn=new ExtTreeNodeStr();
				etn.setId(a.get("lwxj00"));
				if(Integer.valueOf(a.get("c"))>0){
					etn.setLeaf(false);
				}else{
					etn.setLeaf(true);
				}
				etn.setText(a.get("lwxj01").toString());
				listTree.add(etn);
			}
		}
		String json = JsonUtil.toJSONString(listTree);
		//System.out.println(json);
		this.setSelfDefResData(json);
		return EventRtnType.XML_SUCCESS;
	}
	
	
	/**
	 * 保存
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("saveInfo")
	@NoRequiredValidate
	public int saveGW()throws RadowException{
		String lwxj00 = this.getPageElement("lwxj00").getValue();//当前id
		String lwxj02 = this.getPageElement("lwxj02").getValue();//上级id
		String lwxj01=this.getPageElement("lwxj01").getValue();//名单描述
		
		String lwxj05 = this.getPageElement("lwxj05").getValue();//备注
		String mdid = this.getPageElement("mdid").getValue();//名单引用
		String userid = SysManagerUtils.getUserId();
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql="";
			if(StringUtils.isEmpty(lwxj00)) {
				//新增
				lwxj00=UUID.randomUUID().toString();
				String pxh = sess.createSQLQuery("select nvl(max(lwxj03),0)+1 from HZ_MNTP_LWXJ where lwxj02='"+lwxj02+"'" ).uniqueResult().toString();

				sql="insert into HZ_MNTP_LWXJ  (lwxj00,lwxj01,lwxj02,lwxj03,lwxj04,mdid,lwxj05,userid) values "
					+ " (?,?,?,?,'1',?,?,?)";
				HBUtil.executeUpdate(sql, new Object[]{lwxj00,lwxj01,lwxj02,pxh,mdid,lwxj05,userid});

				this.getExecuteSG().addExecuteCode("var otree=Ext.getCmp('group'); var otreenode=otree.getNodeById('"+lwxj02+"');otree.getLoader().load(otreenode,function(){otreenode.expand();});");


			}else {
				//修改
				sql="update HZ_MNTP_LWXJ set lwxj01=?,lwxj05=?,mdid=?  where lwxj00=? ";
				HBUtil.executeUpdate(sql, new Object[]{lwxj01,lwxj05,mdid,lwxj00});
				this.getExecuteSG().addExecuteCode("var otree=Ext.getCmp('group'); var otreenode=otree.getNodeById('"+lwxj00+"');otreenode.setText('"+lwxj01+"');");
				//this.getExecuteSG().addExecuteCode("var otree=Ext.getCmp('group'); otree.expandPath('/-1/1/76687ee0-575c-4238-b0cd-dddc2ad0457f/e13e9c25-1ac6-40f0-aa1c-536e2e586a9d/016c515a-4ccb-486d-afe5-14b2fb0b7530/8aba8046-ab6c-4157-bc4c-8bcabd34a027')");

			}

			this.toastmessage("保存成功！");
			this.getExecuteSG().addExecuteCode("Ext.getCmp('mdjwinid').hide()");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 维护回显
	 * @param fxyp00
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("setInfo")
	@NoRequiredValidate
	public int setGWInfo(String lwxj00) throws RadowException, AppException{ 
		CommQuery cqbs=new CommQuery();
		
		try {
			String sql="select lwxj00,lwxj01,lwxj02,lwxj03,lwxj04,mdid,lwxj05,userid  "
					+ " from HZ_MNTP_LWXJ b where "
					+ " lwxj00='"+lwxj00+"'";
			List<HashMap<String, String>> list = cqbs.getListBySQL2(sql);	
			if(list.size()>0){
				HashMap<String, String> fxypMap = list.get(0);
				this.getPageElement("lwxj01").setValue(fxypMap.get("lwxj01"));
				this.getPageElement("lwxj05").setValue(fxypMap.get("lwxj05"));
				this.getPageElement("mdid").setValue(fxypMap.get("mdid"));
				this.getPageElement("lwxj00").setValue(fxypMap.get("lwxj00"));
				this.getPageElement("lwxj02").setValue(fxypMap.get("lwxj02"));
			}else{
				this.setMainMessage("查询失败");
				return EventRtnType.NORMAL_SUCCESS;		
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("openMDJWin(false)");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	/**
	 * 删除节点
	 * @param fxyp00
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("delNode")
	@NoRequiredValidate
	public int delNode(String lwxj00) throws RadowException, AppException{ 
		CommQuery cqbs=new CommQuery();
		try {
			String sql="delete from HZ_MNTP_LWXJ t where lwxj00 in ( select lwxj00 from HZ_MNTP_LWXJ "
					+ "start with lwxj00='"+lwxj00+"'   "
					+ " connect by prior lwxj00=lwxj02)";
			HBUtil.executeUpdate(sql);
			this.getExecuteSG().addExecuteCode("var otree=Ext.getCmp('group'); var otreenode=otree.getNodeById('"+lwxj00+"');otreenode.remove();");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	/**
	 * 查询
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("nodeclick")
	@NoRequiredValidate
	public int nodeclick(String nodeid)throws RadowException, AppException{
		List<List<Map<String, String>>> tableData = new ArrayList<List<Map<String, String>>>();
		
		String userid = SysManagerUtils.getUserId();
		CommQuery cqbs=new CommQuery();
		String sql = "select mdid,lwxj01,lwxj05 from HZ_MNTP_LWXJ where lwxj00='"+nodeid+"' and userid='"+userid+"' ";
		List<HashMap<String, String>> mdlist = cqbs.getListBySQL2(sql);
		if(mdlist.size()==1){
			HashMap<String, String> m = mdlist.get(0);
			String mdid = m.get("mdid");
			String lwxj01 = m.get("lwxj01");
			String lwxj05 = m.get("lwxj05");
			rcnode(nodeid,userid,cqbs,mdid,lwxj01,lwxj05,tableData);
			
		}
		JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(tableData);
		this.getExecuteSG().addExecuteCode("showData("+updateunDataStoreObject2.toString()+");");
		return EventRtnType.NORMAL_SUCCESS;		
	}


	private void rcnode(String nodeid, String userid, CommQuery cqbs, String mdid, String lwxj01, String lwxj05, List<List<Map<String, String>>> tableData) {
		
		
		
		
		try {
			//读取名册信息
			List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
			tableData.add(rows);
			Map<String, String> cellMap = new HashMap<String, String>();
			if(!StringUtils.isEmpty(lwxj05)){
				lwxj01 = lwxj01 + "（"+lwxj05+"）";
			}
			cellMap.put("text", lwxj01);
			cellMap.put("colspan", "7");
			cellMap.put("mdid", mdid);
			
			rows.add(cellMap);
			
			if(!StringUtils.isEmpty(mdid)){
				cellMap.put("sclass", "opbtn titleColor tableTile classBR");
				cellMap.put("style", "position:relative;background-clip:padding-box;");
				getPersonInfo(mdid,userid,tableData,cqbs);
			}else{
				cellMap.put("sclass", "titleColor tableTile classBR");
			}
			
			
			
			//递归下级
			String childsql = "select mdid,lwxj00,lwxj01,lwxj05 from HZ_MNTP_LWXJ where lwxj02='"+nodeid+"' and userid='"+userid+"' order by lwxj03 ";
			List<HashMap<String, String>> childmdlist = cqbs.getListBySQL2(childsql,true);
			for(HashMap<String, String> m : childmdlist){
				String cmdid = m.get("mdid");
				String clwxj01 = m.get("lwxj01");
				String clwxj00 = m.get("lwxj00");
				String clwxj05 = m.get("lwxj05");
				rcnode(clwxj00,userid,cqbs,cmdid,clwxj01,clwxj05,tableData);
			}
			
		} catch (AppException e) {
			this.setMainMessage("查询出错！");
			e.printStackTrace();
		}
		
	}


	private void addtitle(List<List<Map<String, String>>> tableData) {
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		tableData.add(rows);
		Map<String, String> cellMap = new HashMap<String, String>();
		
		
		cellMap.put("text", "姓名");
		cellMap.put("sclass", "titleColor");
		cellMap.put("style", "width:8%;");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "时任职务");
		cellMap.put("sclass", "titleColor");
		cellMap.put("style", "width:24%;");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "现任职务");
		cellMap.put("sclass", "titleColor");
		cellMap.put("style", "width:24%;");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "性别");
		cellMap.put("sclass", "titleColor");
		cellMap.put("style", "width:4%;");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "出生年月");
		cellMap.put("sclass", "titleColor");
		cellMap.put("style", "width:8%;");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "党派");
		cellMap.put("sclass", "titleColor");
		cellMap.put("style", "width:8%;");
		rows.add(cellMap);
		
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "备注");
		cellMap.put("sclass", "titleColor classBR");
		cellMap.put("style", "width:24%;");
		rows.add(cellMap);
		
	}


	private void getPersonInfo(String mdid, String userid, List<List<Map<String, String>>> tableData, CommQuery cqbs) throws AppException {
		StringBuilder sb = new StringBuilder("select t.*,"
				+ " (select code_name from code_value where code_value=t.a0141 and code_type='GB4762') a0141_n,"
				+ "  (select max('1') from hz_a17 b where b.a0000=a01.a0000 and a1705 in ('0901','0902')) dzzz,"
				+	" a01.a0104 a01a0104,a01.a0141 a01a0141, "
				+ " a01.a0192a a0192anow,(select username from smt_user c where t.a0188=c.userid) username " + 
				" from HISTORYPER t,a01 where t.mdid='"+mdid+"' and t.a0000=a01.a0000(+) " + 
				" order by t.sortid");	
		List<HashMap<String, String>> childmdlist = cqbs.getListBySQL2(sb.toString(),true);
		if(childmdlist.size()>0){
			addtitle(tableData);
		}
		
		for(HashMap<String, String> m : childmdlist){
			
			List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
			tableData.add(rows);
			Map<String, String> cellMap = new HashMap<String, String>();
			
			
			String a0101 = m.get("a0101")==null?"":m.get("a0101");
			String a0000 = m.get("a0000")==null?"":m.get("a0000");
			String a0107 = m.get("a0107")==null?"":m.get("a0107");
			String dzzz = m.get("dzzz")==null?"":m.get("dzzz");
			String a01a0104 = m.get("a01a0104")==null?"":m.get("a01a0104");
			String a01a0141 = m.get("a01a0141")==null?"":m.get("a01a0141");
			cellMap.put("text", a0101);
			if(!StringUtils.isEmpty(a0000)){
				cellMap.put("a0000", a0000);
				cellMap.put("dzzz", dzzz);
				cellMap.put("a01a0104", a01a0104);
				cellMap.put("a01a0141", a01a0141);
				cellMap.put("a0107", a0107);
				cellMap.put("sclass", "name");
			}
			
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			String a0192a = m.get("a0192a")==null?"":m.get("a0192a");
			cellMap.put("text", a0192a);
			cellMap.put("style", "text-align:left;");
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			String a0192anow = m.get("a0192anow")==null?"":m.get("a0192anow");
			cellMap.put("text", a0192anow);
			cellMap.put("style", "text-align:left;");
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			String a0104 = m.get("a0104")==null?"":m.get("a0104");
			cellMap.put("text", "2".equals(a0104)?"女":"男");
			rows.add(cellMap);
			

			cellMap = new HashMap<String, String>();
			if(!StringUtils.isEmpty(a0107)){
			    if (a0107.length() == 6 || a0107.length() == 8) {
			    	a0107 = a0107.substring(0, 4) + "." + a0107.substring(4, 6);
			    }
			}
			cellMap.put("text", a0107);
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			String a0141 = m.get("a0141_n")==null?"":m.get("a0141_n");
			cellMap.put("text", a0141);
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			String remark = m.get("remark")==null?"":m.get("remark");
			cellMap.put("sclass", "classBR");
			cellMap.put("text", remark);
			rows.add(cellMap);
			
			
			
		}
		
	}
	
	/**
	 * 节点排序
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PageEvent("sortNode")
	@Transaction
	public int sortNode(String param) throws Exception{
		String[] params = param.split("@@");
		String nodeid = params[0];
		String psnodeid = params[1];
		String prtnodeid = params[2];
		
		if(StringUtils.isEmpty(psnodeid)){//第一个
			HBUtil.executeUpdate("update HZ_MNTP_LWXJ set lwxj03=lwxj03+1 where lwxj02=?",
					new Object[]{prtnodeid});
			HBUtil.executeUpdate("update HZ_MNTP_LWXJ set lwxj03=1 where lwxj00=?",
					new Object[]{nodeid});
		}else{//中间
			HBUtil.executeUpdate("update HZ_MNTP_LWXJ x set lwxj03=lwxj03+1 where lwxj02=?"
					+ " and lwxj03>(select lwxj03 from HZ_MNTP_LWXJ where lwxj00=?)",
					new Object[]{prtnodeid,psnodeid});
			HBUtil.executeUpdate("update HZ_MNTP_LWXJ set lwxj03="
					+ "(select lwxj03+1 from HZ_MNTP_LWXJ where lwxj00=?) where lwxj00=?",
					new Object[]{psnodeid,nodeid});
		}
		HBUtil.executeUpdate("update HZ_MNTP_LWXJ set lwxj02=? where lwxj00=?",
				new Object[]{prtnodeid,nodeid});
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
}
