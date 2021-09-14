package com.insigma.siis.local.pagemodel.zwzc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A02Zwzc;
import com.insigma.siis.local.business.entity.A02ZwzcMx;
import com.insigma.siis.local.business.entity.Gbkh;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class ZWZCWHPageModel extends PageModel {
	
	
	
	/**
	 * 指标主信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("zwzcGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		//定义用来组装sql的变量
		String isContain = this.getPageElement("isContain").getValue();
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		String searchname = this.getPageElement("search").getValue();
		StringBuffer str = new StringBuffer();
		if(checkedgroupid==null || "".equals(checkedgroupid)) {
			str.append("select  ZWZC00,A0192A,(select wm_concat(code_name) from (select * from code_value order by inino) where code_type='ZB130' and A02_ZWZC.a0165 like '%'||code_value||'%' ) as a0165,A0221,A0192E,JZAZ,SORTID,gfhjc,xzzw   from A02_ZWZC where a0192a like '%"+searchname+"%'");	
		}else if(checkedgroupid.length()>0 && "1".equals(isContain)) {
			str.append("select  ZWZC00,A0192A,(select wm_concat(code_name) from (select * from code_value order by inino) where code_type='ZB130' and A02_ZWZC.a0165 like '%'||code_value||'%' ) as a0165,A0221,A0192E,JZAZ,SORTID,gfhjc,xzzw  from A02_ZWZC where zwzc00 in (select zwzc00 from A02_ZWZC_MX where b01id in (select b01id from b01 where b0111 like '"+checkedgroupid+"%'))");	
		}else {
			str.append("select  ZWZC00,A0192A,(select wm_concat(code_name) from (select * from code_value order by inino) where code_type='ZB130' and A02_ZWZC.a0165 like '%'||code_value||'%' ) as a0165,A0221,A0192E,JZAZ,SORTID,gfhjc,xzzw  from A02_ZWZC where zwzc00 in (select zwzc00 from A02_ZWZC_MX where b01id in (select b01id from b01 where b0111='"+checkedgroupid+"'))");	
		}
		if( checkedgroupid.length()>=15) {
			if(!"001.001.002.01O".equals(checkedgroupid) && !"001.001.002.01Q".equals(checkedgroupid) && !"001.001.002.02O".equals(checkedgroupid)) {		
				str.append(" order by A02_ZWZC.sortid ");
			}

		}
		this.pageQuery(str.toString(), "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 指标明细信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("zwzcMxGrid.dogridquery")
	public int doLogQuery(int start,int limit) throws RadowException{
		String zwzc00id = this.getPageElement("zwzc00id").getValue();
		String sql = "select  * from A02_ZWZC_MX where zwzc00='"+zwzc00id+"' order by sortid ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 刷新
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("zwzcGrid.rowclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{  //打开窗口的实例
		String mainid = (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("zwzc00");
		String nameid = (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("a0192a");
		String zwcode= (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("a0221");
		String zjcode= (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("a0192e");
		this.getPageElement("a0192aid").setValue(nameid);
		this.getPageElement("zwzc00id").setValue(mainid);
		this.getPageElement("zwcode").setValue(zwcode);
		this.getPageElement("zjcode").setValue(zjcode);
		this.setNextEventName("zwzcMxGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	@PageEvent("zwzcMxGrid.rowclick")
	@GridDataRange
	public int zwzcMxgridOnRowDbClick() throws RadowException{  //打开窗口的实例
		String zwmx00 = (String)this.getPageElement("zwzcMxGrid").getValueList().get(this.getPageElement("zwzcMxGrid").getCueRowIndex()).get("zwmx00");

		this.getPageElement("zwmx00").setValue(zwmx00);

		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	public int initX(String ets00) throws RadowException, PrivilegeException, AppException{
//		String selSqL = "select ets00,ets01 from EVALUATION_TARGET_scheme order by ets02";
//		CommQuery cqbs=new CommQuery();
//		List<HashMap<String, Object>> listCode;
//		try {
//			listCode = cqbs.getListBySQL(selSqL);
//			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
//			for(int i=0;i<listCode.size();i++){
//				mapCode.put(listCode.get(i).get("ets00").toString(), listCode.get(i).get("ets01"));
//			}
//			((Combo)this.getPageElement("ets01")).setValueListForSelect(mapCode);
//		} 
//		this.setNextEventName("zwzcGrid.dogridquery");
		return 0;
	}
	@PageEvent("addZWZCInfo")
	@Transaction
	public int addInfo() throws Exception{
		A02Zwzc a02zwzc=new A02Zwzc();
		this.copyElementsValueToObj(a02zwzc, this);
		String zwzc00 = this.getPageElement("zwzc00").getValue();
		String b0111=this.getPageElement("checkedgroupid").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			if(StringUtils.isEmpty(zwzc00)) {
				a02zwzc.setZwzc00(UUID.randomUUID().toString().replace("-", ""));
				sess.save(a02zwzc);
				
				A02ZwzcMx a02zwzcmx=new A02ZwzcMx();
				String zwmx00=UUID.randomUUID().toString().replace("-", "");
				a02zwzcmx.setZwmx00(zwmx00);
				String b01id="";
				@SuppressWarnings("unchecked")
				List<String> b01ids = HBUtil.getHBSession().createSQLQuery("select b01id from b01 where b0111='"+b0111+"'").list();
				if(b01ids.size()>0) {
					b01id=b01ids.get(0);
					a02zwzcmx.setB01id(b01id);
				}
				String b0101="";
				@SuppressWarnings("unchecked")
				List<String> b0101s = HBUtil.getHBSession().createSQLQuery("select b0101 from b01 where b0111='"+b0111+"'").list();
				if(b0101s.size()>0) {
					b0101=b0101s.get(0);
					a02zwzcmx.setA0201a(b0101);
				}
				a02zwzcmx.setZwzc00(a02zwzc.getZwzc00());
				sess.save(a02zwzcmx);
			}else {
				@SuppressWarnings("unchecked")
				List<String> sortids = HBUtil.getHBSession().createSQLQuery("select sortid from a02_zwzc where zwzc00='"+zwzc00+"'").list();
				if(sortids.size()>0) {
					if(sortids.get(0)!=null) {
						int sortid=Integer.valueOf(String.valueOf(sortids.get(0)));
						a02zwzc.setSortid(sortid);
					}	
				}
				sess.update(a02zwzc);
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','保存失败！',null,'220')");
			return EventRtnType.FAILD;
		}
//		if(StringUtils.isEmpty(zwzc00)){
//			zwzc00 = UUID.randomUUID().toString();
//			HBUtil.executeUpdate("insert into EVALUATION_TARGET_CLASS(etc00,etc01,ets00,etc03) values(?,?,?,seq_sort.nextval)",
//					new Object[]{etc00,etc01,ets00});
//		}else{
//			HBUtil.executeUpdate("update EVALUATION_TARGET_CLASS set etc01=? where etc00=?",
//					new Object[]{etc01,etc00});
//		}
		
		this.setNextEventName("zwzcGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("addMxInfo")
	@Transaction
	public int addETInfo() throws Exception{
		A02ZwzcMx a02ZwzcMx=new A02ZwzcMx();
		String a0201b=this.getPageElement("a0201b").getValue();
		String a0201a1=this.getPageElement("a0201a").getValue();
		if(a0201a1==null || "".equals(a0201a1)) {
			String a0201a = this.getPageElement("a0201b_combo").getValue();//机构名称 中文。
			this.getPageElement("a0201a").setValue(a0201a);
		}
		this.copyElementsValueToObj(a02ZwzcMx, this);
		String zwcode = this.getPageElement("zwcode").getValue();
		String zjcode = this.getPageElement("zjcode").getValue();
		String zwmx00 = this.getPageElement("zwmx00").getValue();
		String zwzc00id = this.getPageElement("zwzc00id").getValue();
		if(StringUtils.isEmpty(zwzc00id)){
			this.setMainMessage("请选择职务总称！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		try{
			@SuppressWarnings("unchecked")
			List<String> b01ids = HBUtil.getHBSession().createSQLQuery("select b01id from b01 where b0111='"+a0201b+"'").list();
			if(b01ids.size()>0) {
				a02ZwzcMx.setB01id(b01ids.get(0));
			}
			a02ZwzcMx.setZwzc00(zwzc00id);
			a02ZwzcMx.setZjcode(zjcode);
			a02ZwzcMx.setZwcode(zwcode);
			sess = HBUtil.getHBSession();
			if(StringUtils.isEmpty(a02ZwzcMx.getA0201a())){
				this.setMainMessage("请选择机构后保存！");
				return EventRtnType.FAILD;
			}
			if(StringUtils.isEmpty(zwmx00)) {
				sess.save(a02ZwzcMx);
			}else {
				@SuppressWarnings("unchecked")
				List<String> sortids = HBUtil.getHBSession().createSQLQuery("select sortid from a02_zwzc_mx where zwmx00='"+zwmx00+"'").list();
				if(sortids.size()>0) {
					if(sortids.get(0)!=null) {
						int sortid=Integer.valueOf(String.valueOf(sortids.get(0)));
						a02ZwzcMx.setSortid(sortid);
					}		
				}
				sess.update(a02ZwzcMx);
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','保存失败！',null,'220')");
			return EventRtnType.FAILD;
		}
//		if(StringUtils.isEmpty(et00)){
//			et00 = UUID.randomUUID().toString();
//			HBUtil.executeUpdate("insert into EVALUATION_TARGET(et00,et01,et02,et03,et04,et05,etc00) values(?,?,?,?,?,seq_sort.nextval,?)",
//					new Object[]{et00,et01,et02,et03,et04,etc00id});
//		}else{
//			HBUtil.executeUpdate("update EVALUATION_TARGET set et01=?,et02=?,et03=?,et04=? where et00=?",
//					new Object[]{et01,et02,et03,et04,et00});
//		}
		
		this.setNextEventName("zwzcMxGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("zwzcMxGrid.rowdbclick")
	@GridDataRange
	@NoRequiredValidate
	public int zwzcMxGridOnRowDbClick() throws RadowException{  
		String zwzc00= this.getPageElement("zwzc00id").getValue();
		this.getPageElement("zwzc00").setValue(zwzc00);
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String a0201a=this.getPageElement("a0201a").getValue();
		try {
			if(StringUtils.isEmpty(zwzc00)){
				this.setMainMessage("请选择职务总称！");
				return EventRtnType.NORMAL_SUCCESS;
			}
//			@SuppressWarnings("unchecked")
//			List<String> b0111 = HBUtil.getHBSession().createSQLQuery("select b0111 from b01 where b01id='"+b01id+"'").list();
//			if(a0201a!=null && !"".equals(a0201a)) {
//				this.getPageElement("a0201b_combo").setValue(a0201a);
//			}
//			for(int i=0;i<b01ids.size();i++) {
//				@SuppressWarnings("unchecked")
//				List<String> b0101 = HBUtil.getHBSession().createSQLQuery("select b0101 from b01 where b01id='"+b01ids.get(i)+"'").list();
//				map.put(""+b01ids.get(i), b0101.get(0));
//			}
//			((Combo)this.getPageElement("a0201a")).setValueListForSelect(map); 
		}catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	@SuppressWarnings("unchecked")
	@PageEvent("setZB08Code")
	@NoRequiredValidate
	@Transaction
	public int setZB08Code(String id)throws RadowException{
		
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+id+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();
		
		
		if(B0194 != null && B0194.equals("3")) {

			String msg = "不可选择机构分组单位！";

			this.getPageElement("a0201b").setValue("");
			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','" + msg + "');document.getElementById('a0201b').value='';"
					+ "document.getElementById('a0201b_combo').value='';"
			);

			return EventRtnType.NORMAL_SUCCESS;
		}
		
		try {
			String v = HBUtil.getValueFromTab("b0101", "B01", "b0111='"+id+"'");
			if(v!=null){
				this.getPageElement("a0201b_combo").setValue(v);
			}else{
				//this.getPageElement("a0201b_combo").setValue("");
			}
			/*
			 
			 String v = HBUtil.getValueFromTab("b0131", "B01", "b0111='"+id+"'");
			if(v!=null&&("1001".equals(v)||"1002".equals(v)||"1003".equals(v)||"1004".equals(v)
					||"1005".equals(v)||"1006".equals(v)||"1007".equals(v))){
				this.getPageElement("ChangeValue").setValue(v);
				this.getExecuteSG().addExecuteCode("var combo = Ext.getCmp('a0215a_combo');" +
						"combo.show();");
			}else{
				this.getExecuteSG().addExecuteCode("var combo = Ext.getCmp('a0215a_combo');" +
						"combo.setValue('');" +
						"document.getElementById('a0215a').value='';" +
						"combo.hide();");
			}
			 */
			
			
		} catch (AppException e) {
			e.printStackTrace();
		}
		
		//var newStore = a0255f.getStore();
		//newStore.removeAll();
		//newStore.add(new Ext.data.Record({key:'1',value:'在任'}));
		//newStore.add(new Ext.data.Record(item.two));
		
		/*List<String> jsondata = CodeType2js.getCodeTypeList("ZB08", null, null);
		StringBuffer bf = new StringBuffer("var a0215a = Ext.getCmp('a0215a_combo');var newStore = a0215a.getStore();");
		bf.append("newStore.removeAll();");
		for(String s : jsondata){
			bf.append("newStore.add(new Ext.data.Record("+s+"));");
		}
		this.getExecuteSG().addExecuteCode(bf.toString());*/
		
		//将任职机构名称赋值到页面a0201a
		if(id != null && !id.equals("")){
			String a0201a = this.getPageElement("a0201b_combo").getValue();//机构名称 中文。
			//任职机构以省开头，任职机构名称字段加上贵州两字
			/*if(a0201a.substring(0,1).equals("省"))
				a0201a="贵州"+a0201a;*/
			this.getPageElement("a0201a").setValue(a0201a);
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("delMxInfo")
	@Transaction
	public int delMxInfo() throws Exception{
		String zwmx00=this.getPageElement("zwmx00").getValue();
		try{
			HBSession sess = HBUtil.getHBSession();
			A02ZwzcMx a02ZwzcMx=(A02ZwzcMx)sess.get(A02ZwzcMx.class,zwmx00);
			sess.delete(a02ZwzcMx);
			this.getPageElement("zwmx00").setValue("");
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','删除失败！',null,'220')");
			return EventRtnType.FAILD;
		}
//		if(StringUtils.isEmpty(et00)){
//			et00 = UUID.randomUUID().toString();
//			HBUtil.executeUpdate("insert into EVALUATION_TARGET(et00,et01,et02,et03,et04,et05,etc00) values(?,?,?,?,?,seq_sort.nextval,?)",
//					new Object[]{et00,et01,et02,et03,et04,etc00id});
//		}else{
//			HBUtil.executeUpdate("update EVALUATION_TARGET set et01=?,et02=?,et03=?,et04=? where et00=?",
//					new Object[]{et01,et02,et03,et04,et00});
//		}
		
		this.setMainMessage("删除成功！");
		this.setNextEventName("zwzcMxGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("rolesort")
	@Transaction
	public int publishsort() throws RadowException {
		List<HashMap<String, String>> list = this.getPageElement("zwzcGrid").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "update A02_ZWZC set sortid = ? where zwzc00=?";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for (HashMap<String, String> m : list) {
				String zwzc00 = m.get("zwzc00");
				ps.setInt(1, i);
				ps.setString(2, zwzc00);
				ps.addBatch();
				i++;
			}
			ps.executeBatch();
			con.commit();
			ps.close();
			con.close();
		} catch (Exception e) {
			try {
				con.rollback();
				if (con != null) {
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		this.toastmessage("排序已保存！");
		this.setNextEventName("zwzcGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("rolesort1")
	@Transaction
	public int publishsort1() throws RadowException {
		List<HashMap<String, String>> list = this.getPageElement("zwzcMxGrid").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "update A02_ZWZC_MX set sortid = ? where zwmx00=?";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for (HashMap<String, String> m : list) {
				String zwmx00 = m.get("zwmx00");
				ps.setInt(1, i);
				ps.setString(2, zwmx00);
				ps.addBatch();
				i++;
			}
			ps.executeBatch();
			con.commit();
			ps.close();
			con.close();
		} catch (Exception e) {
			try {
				con.rollback();
				if (con != null) {
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		this.toastmessage("排序已保存！");
		this.setNextEventName("zwzcMxGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
}