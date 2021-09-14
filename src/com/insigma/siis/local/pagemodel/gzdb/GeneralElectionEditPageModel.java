package com.insigma.siis.local.pagemodel.gzdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.A02Zwzc;
import com.insigma.siis.local.business.entity.A02ZwzcMx;
import com.insigma.siis.local.business.entity.Gbkh;
import com.insigma.siis.local.business.entity.HJXJ;
import com.insigma.siis.local.business.entity.JD;
import com.insigma.siis.local.business.entity.ZB;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class GeneralElectionEditPageModel extends PageModel {
	
	
	@PageEvent("deleteBtn")	
	@NoRequiredValidate
	public int delete() throws RadowException{
		
		List<HashMap<String,Object>> list = this.getPageElement("zwzcGrid").getValueList();
		List<HashMap<String,Object>> list2=new ArrayList<HashMap<String,Object>>();

		for (HashMap<String, Object> map : list) {
			if(map.get("checked").toString().equals("true")){
				list2.add(map);	
			}
		}
			
			for(int i=0;i<list2.size();i++){
				String jdid=list2.get(i).get("jdid").toString();
				this.deleJD(jdid);
				this.deleZB(jdid);
		}
		this.getExecuteSG().addExecuteCode("gg();");
		this.getExecuteSG().addExecuteCode("gg1();");
		//this.setNextEventName("zwzcGrid.dogridquery");
		//this.setNextEventName("zwzcMxGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	@PageEvent("deleteBtn1")	
	@NoRequiredValidate
	public int delete1() throws RadowException{
		
		List<HashMap<String,Object>> list = this.getPageElement("zwzcMxGrid").getValueList();
		List<HashMap<String,Object>> list2=new ArrayList<HashMap<String,Object>>();

		for (HashMap<String, Object> map : list) {
			if(map.get("checked").toString().equals("true")){
				list2.add(map);	
			}
		}
			
			for(int i=0;i<list2.size();i++){
				String zbid=list2.get(i).get("zbid").toString();
				this.deleZB1(zbid);
				this.deleZBB1(zbid);
				this.upZBB1(zbid);
			}
			
		this.getExecuteSG().addExecuteCode("gg();");
		//this.setNextEventName("zwzcMxGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	@PageEvent("deleteBtnn1")	
	@NoRequiredValidate
	public int delete2() throws RadowException{
		
		List<HashMap<String,Object>> list = this.getPageElement("zxGrid").getValueList();
		List<HashMap<String,Object>> list2=new ArrayList<HashMap<String,Object>>();

		for (HashMap<String, Object> map : list) {
			if(map.get("checked").toString().equals("true")){
				list2.add(map);	
			}
		}
			for(int i=0;i<list2.size();i++){
				String zbid=list2.get(i).get("zbid").toString();
				this.deleZBB1(zbid);
				this.upZBB1(zbid);
				
			}
			
		this.getExecuteSG().addExecuteCode("gg2();");
		//this.setNextEventName("zwzcMxGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	
	public void deleJD(String jdid){
		HBUtil.getHBSession().createSQLQuery("delete from JD where jdid = '"+jdid+"'").executeUpdate();
	}
	public void deleZB(String jdid){
		HBUtil.getHBSession().createSQLQuery("delete from ZB where jdid = '"+jdid+"'").executeUpdate();
	}
	public void deleZB1(String zbid){
		HBUtil.getHBSession().createSQLQuery("delete from ZB where zbid = '"+zbid+"'").executeUpdate();
	}
	public void deleZBB1(String zbid){
		HBUtil.getHBSession().createSQLQuery("delete from HJXJADD where add00 = '"+zbid+"'").executeUpdate();
	}
	public void upZBB1(String zbid){
		HBUtil.getHBSession().createSQLQuery("delete from ZB_FJ  where zbid='"+zbid+"'").executeUpdate();
	}
	
	@PageEvent("pd")
	@Transaction
	public int pd(String zbid) throws RadowException, AppException {
		CommQuery cqbs=new CommQuery();
		String ctxPath = request.getContextPath();
		String count="select count(zbid) count from ZB_FJ a where a.zbid='"+zbid+"'  ";
		
		List<HashMap<String, Object>> list1;
			list1 = cqbs.getListBySQL(count);
			HashMap<String, Object> map1=list1.get(0);
			String count1 = map1.get("count")==null?"":map1.get("count").toString();
			if(Integer.parseInt(count1)<1){
				this.getExecuteSG().addExecuteCode("$h.openWin('CJWJGLdw','pages.gzdb.CJWJGLdw','材料列表',550,300,'"+zbid+"','"+ctxPath+"');");
			}else {
				this.setMainMessage("只能上传一个材料");
			}

			return EventRtnType.NORMAL_SUCCESS;
		
	}
	/**
	 * 更变审批状态，控制按钮
	 * @param p0500
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("sumbitRegister")
	@Transaction
	public int sumbitRegister() throws RadowException, AppException {
		CommQuery cq = new CommQuery();
		String jdid = (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("jdid");
		String hjxjid = (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("hjxjid");
		String jdmc = (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("jdmc");
		String jd = (String)this.getPageElement("zwzcMxGrid").getValueList().get(this.getPageElement("zwzcMxGrid").getCueRowIndex()).get("jdbt");
		HBSession sess = HBUtil.getHBSession();
		JD jdj = (JD) sess.get(JD.class, jdid);
		jdj.setWcbz("1");
		sess.update(jdj);
		sess.flush();
		this.setMainMessage("确认成功！");
		this.getPageElement("wcbz").setValue("1");
		String zbwcbz ="1";
		try {
			HBUtil.executeUpdate("update zb set zbwcbz=?where jdid=?",
					new Object[]{zbwcbz,jdid});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql ="select sum(zbjd) zbjd from zb where jdid='" + jdid + "'";
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		String sum=list.get(0).get("zbjd") == null ? "" : list.get(0).get("zbjd").toString();
		String r1="0";
		String r2="1";
		String r3="2";
		try {
			
			
		if(!"".equals(sum)){
			if(Integer.parseInt(sum)>=100) {
				HBUtil.executeUpdate("update jd set jd=?where jdid=?",
						new Object[]{"100",jdid});
				
				String sqlg ="select sum(jd) jd from jd where hjxjid='"+hjxjid+"'";
				List<HashMap<String, Object>> list1 = cq.getListBySQL(sqlg);
				String summ=list1.get(0).get("jd") == null ? "" : list1.get(0).get("jd").toString();
				if(Integer.parseInt(summ)>=100) {
					
					HBUtil.executeUpdate("update hjxj set jd=?where hjxjid=?",
							new Object[]{"100",hjxjid});
				}else {
					
					HBUtil.executeUpdate("update hjxj set jd=?where hjxjid=?",
						new Object[]{summ,hjxjid});
				}
			}else {
			HBUtil.executeUpdate("update jd set jd=?where jdid=?",
					new Object[]{sum,jdid});
			String sqlg ="select sum(jd) jd from jd where  hjxjid='"+hjxjid+"'";
			List<HashMap<String, Object>> list1 = cq.getListBySQL(sqlg);
			String summ=list1.get(0).get("jd") == null ? "" : list1.get(0).get("jd").toString();
			HBUtil.executeUpdate("update hjxj set jd=?where hjxjid=?",
					new Object[]{summ,hjxjid});
			}
		}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    addNextBackFunc(NextEventValue.YES, "afterSubmit();");
	    this.getExecuteSG().addExecuteCode("gg1();");
	    this.getExecuteSG().addExecuteCode("gg3();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 更变审批状态，控制按钮
	 * @param p0500
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("sumbitRegisterl")
	@Transaction
	public int sumbitRegisterl() throws RadowException, AppException {
		CommQuery cq = new CommQuery();
		String jdid =  (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("jdid");
		String jdbt = (String)this.getPageElement("zwzcMxGrid").getValueList().get(this.getPageElement("zwzcMxGrid").getCueRowIndex()).get("jdbt");
		String zbwcbz ="1";
		try {
			HBUtil.executeUpdate("update zb set zbwcbz=?where jdbt=?",
					new Object[]{zbwcbz,jdbt});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql ="select sum(zbjd) zbjd from zb where jdbt='" + jdbt + "' and jdid='"+jdid+"'";
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		String sum=list.get(0).get("zbjd") == null ? "" : list.get(0).get("zbjd").toString();
		try {
			if(!"".equals(sum)){
				if(Integer.parseInt(sum)>=100) {
					HBUtil.executeUpdate("update jd set jd=?where jdid=?",
							new Object[]{"100",jdid});
					addNextBackFunc(NextEventValue.YES, "afterSubmit();");
				}else {
				HBUtil.executeUpdate("update jd set jd=?where jdid=?",
						new Object[]{sum,jdid});
				}
			}else {
				HBUtil.executeUpdate("update jd set jd=?where jdid=?",
						new Object[]{sum,jdid});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    this.getExecuteSG().addExecuteCode("gg1();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 指标主信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("zwzcGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String hjxjid = this.getPageElement("hjxjid").getValue();
		//String hjxjid = this.getPageElement("hjxjid").getValue();
		//this.getPageElement("hjxjid").setValue(hjxjid);
		String sql = "select  * from JD where hjxjid='"+hjxjid+"'  order by jdmc ";
		this.pageQuery(sql, "SQL", start, limit);
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
		String jdid = (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("jdid");
		String sql = "select b.zbid,b.jdbt,b.z0808,b.z0809,b.zbjd,b.zbwcbz from ZB b where b.jdid='"+jdid+"' order by b.jdbt  ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	@PageEvent("zwzcMxGrid.rowclick")
	@GridDataRange
	public int personOnRowDbClick() throws RadowException{  //打开窗口的实例
		String zbid = (String)this.getPageElement("zwzcMxGrid").getValueList().get(this.getPageElement("zwzcMxGrid").getCueRowIndex()).get("zbid");
		this.getPageElement("zbid").setValue(zbid);
		this.setNextEventName("zxGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 附件
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("zxGrid.dogridquery")
	public int doLogpQuery(int start,int limit) throws RadowException{
		String zbid = this.getPageElement("zbid").getValue();
		String sql = "select b.fjid,b.zbid,b.zbmc,t.aaa005 || '/' || b.FILEURL FILEURL,b.filename from ZB_FJ b,aa01 t where b.zbid='"+zbid+"' and  t.aaa001='HZB_PATH' order by b.CREATEDON desc ";
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
		String jdid = (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("jdid");
		String wcbz = (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("wcbz");
		String jdmc = (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("jdmc");
		this.getPageElement("wcbz").setValue(wcbz);
		this.getPageElement("jdid").setValue(jdid);
		this.getPageElement("jdmc").setValue(jdmc);
		if(wcbz.equals("1")) {
			this.getExecuteSG().addExecuteCode("afterSubmit('" + wcbz + "')");
		}else {
			this.getExecuteSG().addExecuteCode("Submit()");
		}
		this.setNextEventName("zwzcMxGrid.dogridquery");
		this.getPageElement("zbid").setValue("");
		this.getExecuteSG().addExecuteCode("gg2()");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	public int initX(String hjxjid) throws RadowException, PrivilegeException, AppException{
		this.setNextEventName("zwzcGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	@PageEvent("addZWZCInfo")
	@Transaction
	public int addInfo() throws Exception{
		JD jd=new JD();
		JD jd1=new JD();
		this.copyElementsValueToObj(jd, this);
		StringBuffer bjylxt=new StringBuffer();
		String jdid = this.getPageElement("jdid").getValue();
		String jdmc = this.getPageElement("jdmc").getValue();
		String j0808 = this.getPageElement("j0808").getValue();
		String j0809 = this.getPageElement("j0809").getValue();
		String wcbz = this.getPageElement("wcbz").getValue();
		String hjxjid = this.getPageElement("hjxjid").getValue();
		CommQuery cqbs=new CommQuery();
		
		//String hjxjid = this.getPageElement("subWinIdBussessId2").getValue();
		//String hjxjid = (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("hjxjid");
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			if(StringUtils.isEmpty(jdid)) {
				String sql = "select jdmc from JD where hjxjid='"+hjxjid+"'";
				List<HashMap<String, Object>> list = cqbs.getListBySQL(sql);
						for(int i=0;i<list.size();i++) {
							bjylxt.append(String.valueOf(list.get(i).get("jdmc"))+",");
							}
							if(bjylxt.length()>0){
								bjylxt.deleteCharAt(bjylxt.length()-1);
							}
						boolean status = bjylxt.toString().contains(jdmc);
				if(status){
						this.setMainMessage("新增阶段名称已存在");
				}else{
				String jk=UUID.randomUUID().toString().replace("-", "");
				jd.setJdid(jk);
				jd.setJ0808(j0808);
				jd.setJ0809(j0809);
				jd.setJdmc(jdmc);
				jd.setWcbz(wcbz);
				jd.setHjxjid(hjxjid);
				if(jdmc.equals("D")) {
					jd.setJd("20");
				}else if(!jdmc.equals("D")) {
					jd.setJd("10");
				}
				sess.save(jd);
				}
			}else {
				jd1 = (JD) HBUtil.getHBSession().get(JD.class, jdid);
				jd1.setJdmc(jdmc);
				jd1.setJ0808(j0808);
				jd1.setJ0809(j0809);
				jd1.setWcbz(wcbz);
				
				sess.save(jd1);
				if(wcbz.equals("0")) {
					HBUtil.executeUpdate("update jd set wcbz=? where jdid=?",
							new Object[]{0,jdid});
					String sqlg ="select sum(jd) jd from jd where  hjxjid='"+hjxjid+"' and wcbz='1'";
					List<HashMap<String, Object>> list1 = cqbs.getListBySQL(sqlg);
					String summ=list1.get(0).get("jd") == null ? "" : list1.get(0).get("jd").toString();
					HBUtil.executeUpdate("update hjxj set jd=? where hjxjid=?",
							new Object[]{summ,hjxjid});
					
				}else if(wcbz.equals("2")) {
					HBUtil.executeUpdate("update jd set wcbz=? where jdid=?",
							new Object[]{2,jdid});
					String sqlg ="select sum(jd) jd from jd where  hjxjid='"+hjxjid+"' and wcbz='1'";
					List<HashMap<String, Object>> list1 = cqbs.getListBySQL(sqlg);
					String summ=list1.get(0).get("jd") == null ? "" : list1.get(0).get("jd").toString();
					HBUtil.executeUpdate("update hjxj set jd=? where hjxjid=?",
							new Object[]{summ,hjxjid});
					
				}else if(wcbz.equals("1")){
					HBUtil.executeUpdate("update jd set wcbz=? where jdid=?",
							new Object[]{1,jdid});
					String sqlg ="select sum(jd) jd from jd where  hjxjid='"+hjxjid+"' and wcbz='1'";
					List<HashMap<String, Object>> list1 = cqbs.getListBySQL(sqlg);
					String summ=list1.get(0).get("jd") == null ? "" : list1.get(0).get("jd").toString();
					HBUtil.executeUpdate("update hjxj set jd=? where hjxjid=?",
							new Object[]{summ,hjxjid});
				}
				
				/*try {
					HBUtil.executeUpdate("update zb set zbwcbz=?where jdid=?",
							new Object[]{wcbz,jdid});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
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
		if(wcbz.equals("1")) {
			this.getExecuteSG().addExecuteCode("afterSubmit('" + wcbz + "')");
		}else {
			this.getExecuteSG().addExecuteCode("Submit()");
		}
		this.getExecuteSG().addExecuteCode("gg3();");
		this.setNextEventName("zwzcGrid.dogridquery");
		this.setNextEventName("zwzcMxGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("addf")
	@Transaction
	public int addf(String g1) throws Exception{
		CommQuery cqbs=new CommQuery();
		if(g1.equals("A")) {
			String sql="select code_value ,code_name from code_value where sub_code_value='A' and code_type='JDBT'  order by code_value";
			List<HashMap<String, Object>> list;
			try {
				list = cqbs.getListBySQL(sql);
				Map<String, String> map = new LinkedHashMap<String, String>();
				if (list != null && list.size() > 0) {
					for (HashMap<String, Object> map1 : list) {
						map.put(map1.get("code_value").toString(), map1.get("code_name").toString());
					}
				}
				((Combo)this.getPageElement("jdbt")).setValueListForSelect(map);
			}catch (Exception e) {
				this.setMainMessage("设置重点岗位下拉框数据报错！");
				e.printStackTrace();
			}
		}else if (g1.equals("B")) {
			String sql="select code_value ,code_name from code_value where sub_code_value='B' and code_type='JDBT'  order by code_value";
			List<HashMap<String, Object>> list;
			try {
				list = cqbs.getListBySQL(sql);
				Map<String, String> map = new LinkedHashMap<String, String>();
				if (list != null && list.size() > 0) {
					for (HashMap<String, Object> map1 : list) {
						map.put(map1.get("code_value").toString(), map1.get("code_name").toString());
					}
				}
				((Combo)this.getPageElement("jdbt")).setValueListForSelect(map);
			}catch (Exception e) {
				this.setMainMessage("设置重点岗位下拉框数据报错！");
				e.printStackTrace();
			}
		}else if (g1.equals("C")) {
			String sql="select code_value ,code_name from code_value where sub_code_value='C' and code_type='JDBT'  order by code_value";
			List<HashMap<String, Object>> list;
			try {
				list = cqbs.getListBySQL(sql);
				Map<String, String> map = new LinkedHashMap<String, String>();
				if (list != null && list.size() > 0) {
					for (HashMap<String, Object> map1 : list) {
						map.put(map1.get("code_value").toString(), map1.get("code_name").toString());
					}
				}
				((Combo)this.getPageElement("jdbt")).setValueListForSelect(map);
			}catch (Exception e) {
				this.setMainMessage("设置重点岗位下拉框数据报错！");
				e.printStackTrace();
			}
		}else if (g1.equals("D")) {
			String sql="select code_value ,code_name from code_value where sub_code_value='D' and code_type='JDBT'  order by code_value";
			List<HashMap<String, Object>> list;
			try {
				list = cqbs.getListBySQL(sql);
				Map<String, String> map = new LinkedHashMap<String, String>();
				if (list != null && list.size() > 0) {
					for (HashMap<String, Object> map1 : list) {
						map.put(map1.get("code_value").toString(), map1.get("code_name").toString());
					}
				}
				((Combo)this.getPageElement("jdbt")).setValueListForSelect(map);
			}catch (Exception e) {
				this.setMainMessage("设置重点岗位下拉框数据报错！");
				e.printStackTrace();
			}
		}else if (g1.equals("E")) {
			String sql="select code_value ,code_name from code_value where sub_code_value='E' and code_type='JDBT'  order by code_value";
			List<HashMap<String, Object>> list;
			try {
				list = cqbs.getListBySQL(sql);
				Map<String, String> map = new LinkedHashMap<String, String>();
				if (list != null && list.size() > 0) {
					for (HashMap<String, Object> map1 : list) {
						map.put(map1.get("code_value").toString(), map1.get("code_name").toString());
					}
				}
				((Combo)this.getPageElement("jdbt")).setValueListForSelect(map);
			}catch (Exception e) {
				this.setMainMessage("设置重点岗位下拉框数据报错！");
				e.printStackTrace();
			}
		}else if (g1.equals("F")) {
			String sql="select code_value ,code_name from code_value where sub_code_value='F' and code_type='JDBT'  order by code_value";
			List<HashMap<String, Object>> list;
			try {
				list = cqbs.getListBySQL(sql);
				Map<String, String> map = new LinkedHashMap<String, String>();
				if (list != null && list.size() > 0) {
					for (HashMap<String, Object> map1 : list) {
						map.put(map1.get("code_value").toString(), map1.get("code_name").toString());
					}
				}
				((Combo)this.getPageElement("jdbt")).setValueListForSelect(map);
			}catch (Exception e) {
				this.setMainMessage("设置重点岗位下拉框数据报错！");
				e.printStackTrace();
			}
		}else if (g1.equals("G")) {
			String sql="select code_value ,code_name from code_value where sub_code_value='G' and code_type='JDBT'  order by code_value";
			List<HashMap<String, Object>> list;
			try {
				list = cqbs.getListBySQL(sql);
				Map<String, String> map = new LinkedHashMap<String, String>();
				if (list != null && list.size() > 0) {
					for (HashMap<String, Object> map1 : list) {
						map.put(map1.get("code_value").toString(), map1.get("code_name").toString());
					}
				}
				((Combo)this.getPageElement("jdbt")).setValueListForSelect(map);
			}catch (Exception e) {
				this.setMainMessage("设置重点岗位下拉框数据报错！");
				e.printStackTrace();
			}
		}else if (g1.equals("H")) {
			String sql="select code_value ,code_name from code_value where sub_code_value='H' and code_type='JDBT'  order by code_value";
			List<HashMap<String, Object>> list;
			try {
				list = cqbs.getListBySQL(sql);
				Map<String, String> map = new LinkedHashMap<String, String>();
				if (list != null && list.size() > 0) {
					for (HashMap<String, Object> map1 : list) {
						map.put(map1.get("code_value").toString(), map1.get("code_name").toString());
					}
				}
				((Combo)this.getPageElement("jdbt")).setValueListForSelect(map);
			}catch (Exception e) {
				this.setMainMessage("设置重点岗位下拉框数据报错！");
				e.printStackTrace();
			}
		}else if (g1.equals("I")) {
			String sql="select code_value ,code_name from code_value where sub_code_value='I' and code_type='JDBT'  order by code_value";
			List<HashMap<String, Object>> list;
			try {
				list = cqbs.getListBySQL(sql);
				Map<String, String> map = new LinkedHashMap<String, String>();
				if (list != null && list.size() > 0) {
					for (HashMap<String, Object> map1 : list) {
						map.put(map1.get("code_value").toString(), map1.get("code_name").toString());
					}
				}
				((Combo)this.getPageElement("jdbt")).setValueListForSelect(map);
			}catch (Exception e) {
				this.setMainMessage("设置重点岗位下拉框数据报错！");
				e.printStackTrace();
			}
		}
		
		this.getExecuteSG().addExecuteCode("gg6();");
		return EventRtnType.NORMAL_SUCCESS;
		
		
	}
	
	
	@PageEvent("addMxInfo")
	@Transaction
	public int addETInfo() throws Exception{
		ZB zb=new ZB();
		ZB zb1=new ZB();
		String zbid =this.getPageElement("zbid").getValue();
		//String zbmc = this.getPageElement("zbmc").getValue();
		String z0808 = this.getPageElement("z0808").getValue();
		String z0809 = this.getPageElement("z0809").getValue();
		String jdid = (String)this.getPageElement("zwzcGrid").getValueList().get(this.getPageElement("zwzcGrid").getCueRowIndex()).get("jdid");
		//String jdid = this.getPageElement("jdid").getValue();
		/*if("".equals(jdid)) {
			this.setMainMessage("请先选择要新增的阶段");
    		return EventRtnType.NORMAL_SUCCESS;
		}*/
		StringBuffer bjylxtr=new StringBuffer();
		String jdbt = this.getPageElement("jdbt").getValue();
		//String zbjd = this.getPageElement("zbjd").getValue()==null?"":this.getPageElement("zbjd").getValue();
		CommQuery cqbs=new CommQuery();
		
			//if(!zbjd.equals("")&&Integer.parseInt(zbjd)>100) {
				//this.setMainMessage("最大值100");
				//return EventRtnType.NORMAL_SUCCESS;
			//}
		//String filename = this.getPageElement("filename").getValue()==null?"":this.getPageElement("filename").getValue();
		HBSession sess = HBUtil.getHBSession();
			if(StringUtils.isEmpty(zbid)){
				String sql = "select jdbt from ZB where jdid='"+jdid+"'";
				List<HashMap<String, Object>> list = cqbs.getListBySQL(sql);
						for(int i=0;i<list.size();i++) {
							bjylxtr.append(String.valueOf(list.get(i).get("jdbt"))+",");
							}
							if(bjylxtr.length()>0){
								bjylxtr.deleteCharAt(bjylxtr.length()-1);
							}
							boolean status = bjylxtr.toString().contains(jdbt);
				if(status){
						this.setMainMessage("新增阶段名称已存在");
				}else{
				zb.setZbid(UUID.randomUUID().toString().replace("-", ""));
				//zb.setZbmc(zbmc);
				zb.setZ0808(z0808);
				zb.setZ0809(z0809);
				zb.setJdid(jdid);
				zb.setJdbt(jdbt);
				//zb.setZbjd(zbjd);
				sess.save(zb);
				}
			}else {
				zb1 = (ZB) HBUtil.getHBSession().get(ZB.class, zbid);
				//zb1.setZbmc(zbmc);
				zb1.setZ0808(z0808);
				zb1.setZ0809(z0809);
				zb1.setJdbt(jdbt);
				//zb1.setZbjd(zbjd);
				sess.save(zb1);
			
			}
		this.setNextEventName("zwzcMxGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/*@PageEvent("zwzcMxGrid.rowdbclick")
	@GridDataRange
	@NoRequiredValidate
	public int zwzcMxGridOnRowDbClick() throws RadowException{  
		String zbid= this.getPageElement("zbid").getValue();
		this.getPageElement("zbid").setValue(zbid);
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			if(StringUtils.isEmpty(zbid)){
				this.setMainMessage("请选择！");
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
	}*/
	
	
	@PageEvent("delMxInfo")
	@Transaction
	public int delMxInfo() throws Exception{
		String jdid=this.getPageElement("jdid").getValue();
		try{
			HBSession sess = HBUtil.getHBSession();
			JD jd=(JD)sess.get(JD.class,jdid);
			sess.delete(jd);
			this.getPageElement("jdid").setValue("");
			HBUtil.executeUpdate("delete from zb where jdid=? ",
					new Object[]{jdid});
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
		this.setNextEventName("zwzcGrid.dogridquery");
		this.setNextEventName("zwzcMxGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("delMmInfo")
	@Transaction
	public int delMmInfo() throws Exception{
		String zbid=this.getPageElement("zbid").getValue();
		try{
			HBSession sess = HBUtil.getHBSession();
			ZB zb=(ZB)sess.get(ZB.class,zbid);
			sess.delete(zb);
			this.getPageElement("zbid").setValue("");
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