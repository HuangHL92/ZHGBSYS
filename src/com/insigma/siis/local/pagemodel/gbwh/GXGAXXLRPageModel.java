package com.insigma.siis.local.pagemodel.gbwh;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.AttrLrzw;
import com.insigma.siis.local.business.entity.GBTH;
import com.insigma.siis.local.business.entity.GBXLH;
import com.insigma.siis.local.business.entity.extra.ExtraTags;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;

public class GXGAXXLRPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException{
		
		this.setNextEventName("xlhgrid.dogridquery");
		this.setNextEventName("thgrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	//心里话
	@PageEvent("xlhgrid.dogridquery")
	public int doxlhgridquery(int start,int limit) throws Exception{
		String a0000= this.getPageElement("a0000").getValue();				
		String sql  = "select * from GBXLH where a0000='"+a0000+"' order by xlhtime desc";
		this.pageQuery(sql, "SQL", start, limit);
    	return EventRtnType.SPE_SUCCESS;
	}
	//谈话
	@PageEvent("thgrid.dogridquery")
	public int dothgridquery(int start,int limit) throws Exception{
		String a0000= this.getPageElement("a0000").getValue();				
		String sql  = "select * from GBTH where a0000='"+a0000+"' order by thtime desc";
		this.pageQuery(sql, "SQL", start, limit);
    	return EventRtnType.SPE_SUCCESS;
	}
	//刷新
	@PageEvent("flash01")
	public int flash01() throws RadowException, AppException{
		
		this.setNextEventName("xlhgrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("flash02")
	public int flash02() throws RadowException, AppException{
		
		this.setNextEventName("thgrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*
	 * @PageEvent("initX") public int expData01() throws RadowException{ String
	 * a0000= this.getPageElement("a0000").getValue(); try { String sql =
	 * "select * from GBXLH where a0000="+a0000+"order by xlhtime desc"; CommQuery
	 * cqbs=new CommQuery(); List<HashMap<String, Object>>
	 * listCode=cqbs.getListBySQL(sql.toString()); JSONArray updateunDataStoreObject
	 * = JSONArray.fromObject(listCode);
	 * this.getPageElement("data01").setValue(updateunDataStoreObject.toString()); }
	 * catch (Exception e) { e.printStackTrace(); } return
	 * EventRtnType.NORMAL_SUCCESS; }
	 * 
	 * //谈话
	 * 
	 * @PageEvent("initX") public int expData02() throws RadowException{ String
	 * a0000= this.getPageElement("a0000").getValue(); try { String sql =
	 * "select * from GBTH where a0000="+a0000+"order by thtime desc"; CommQuery
	 * cqbs=new CommQuery(); List<HashMap<String, Object>>
	 * listCode=cqbs.getListBySQL(sql.toString()); JSONArray updateunDataStoreObject
	 * = JSONArray.fromObject(listCode);
	 * 
	 * this.getPageElement("data02").setValue(updateunDataStoreObject.toString()); }
	 * catch (Exception e) { e.printStackTrace(); } return
	 * EventRtnType.NORMAL_SUCCESS; }
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("save01")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int save01() throws RadowException, AppException {
		String a0000 = this.getPageElement("a0000").getValue();
		//System.out.println(a0000);
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String gbxlhtext = this.getPageElement("gbxlhtext").getValue();
		String gbxlhtime = this.getPageElement("gbxlhtime").getValue();
		if(gbxlhtime.length()!=8) {
			this.setMainMessage("记录时间存入8位数据！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String name=null;
		try {
			HBSession sess = HBUtil.getHBSession();
			int index = this.getPageElement("xlhgrid").getCueRowIndex();
			//System.out.println(index);
			
			A01 a01 = (A01) sess.get(A01.class, a0000);
			if(a01==null){
				this.setMainMessage("请先保存人员基本信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			name=a01.getA0101();
			
			String id=null;
			GBXLH gbxlh = new GBXLH();
			gbxlh.setXlhtext(gbxlhtext);
			gbxlh.setXlhtime(gbxlhtime);
			gbxlh.setName(name);
			gbxlh.setA0000(a0000);
			if(index>=0 && Integer.parseInt(this.getPageElement("gridsize1").getValue())>0) {
				/*
				 * List<GBXLH> list =
				 * sess.createQuery("from GBXLH where a0000='"+a0000+"' order by xlhtime desc").
				 * list(); id=list.get(index).getId();
				 */
				id = this.getPageElement("xlhgrid").getValue("id", index).toString();
				//System.out.println(id);
				gbxlh.setId(id);
			}
			
			if(id!=null)
				sess.update(gbxlh);
			else
				sess.save(gbxlh);
			this.setNextEventName("xlhgrid.dogridquery");
			sess.flush();
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@SuppressWarnings("unchecked")
	@PageEvent("save02")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int save02() throws RadowException, AppException {
		String a0000 = this.getPageElement("a0000").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String gbthtime = this.getPageElement("gbthtime").getValue();
		String gbthdx = this.getPageElement("gbthdx").getValue();
		String gbthtext = this.getPageElement("gbthtext").getValue();
		if(gbthtime.length()!=8) {
			this.setMainMessage("记录时间存入8位数据！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String name=null;
		
		try {
			HBSession sess = HBUtil.getHBSession();
			int index = this.getPageElement("thgrid").getCueRowIndex();
			
			A01 a01 = (A01) sess.get(A01.class, a0000);
			if(a01==null){
				this.setMainMessage("请先保存人员基本信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			name=a01.getA0101();
			
			String id=null;
			GBTH gbth = new GBTH();
			gbth.setThdx(gbthdx);
			gbth.setThtext(gbthtext);
			gbth.setThtime(gbthtime);
			gbth.setName(name);
			gbth.setA0000(a0000);
			if(index>=0 && Integer.parseInt(this.getPageElement("gridsize2").getValue())>0) {
				/*
				 * List<GBTH> list =
				 * sess.createQuery("from GBTH where a0000='"+a0000+"' order by thtime desc").
				 * list(); id=list.get(index).getId();
				 */
				id = this.getPageElement("thgrid").getValue("id", index).toString();
				gbth.setId(id);
			}
			
			if(id!=null)
				sess.update(gbth);
			else
				sess.save(gbth);
			this.setNextEventName("thgrid.dogridquery");
			sess.flush();
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("xlhgrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int xlhGridOnRowClick() throws RadowException{ 
		
		int index = this.getPageElement("xlhgrid").getCueRowIndex();
		String a0000 = this.getPageElement("a0000").getValue();
		String id = this.getPageElement("xlhgrid").getValue("id", index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			List<GBXLH> list = sess.createQuery("from GBXLH where a0000='"+a0000+"' and id='"+id+"' order by xlhtime desc").list();
			
			this.getPageElement("gbxlhtime").setValue(list.get(0).getXlhtime());
			this.getPageElement("gbxlhtext").setValue(list.get(0).getXlhtext());
			sess.flush();
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("thgrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int thGridOnRowClick() throws RadowException{ 
		
		int index = this.getPageElement("thgrid").getCueRowIndex();
		String a0000 = this.getPageElement("a0000").getValue();
		String id = this.getPageElement("thgrid").getValue("id", index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			List<GBTH> list = sess.createQuery("from GBTH where a0000='"+a0000+"' and id='"+id+"' order by thtime desc").list();
			
			this.getPageElement("gbthtime").setValue(list.get(0).getThtime());
			this.getPageElement("gbthtext").setValue(list.get(0).getThtext());
			this.getPageElement("gbthdx").setValue(list.get(0).getThdx());
			sess.flush();
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		
		return EventRtnType.NORMAL_SUCCESS;		
	}
	@PageEvent("deleteRow01")
	public int deleterow01() throws RadowException{
		int index = this.getPageElement("xlhgrid").getCueRowIndex();
		String a0000 = this.getPageElement("a0000").getValue();
		String id = this.getPageElement("xlhgrid").getValue("id", index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			List<GBXLH> list = sess.createQuery("from GBXLH where a0000='"+a0000+"' and id='"+id+"' order by xlhtime desc").list();
			GBXLH gbxlh=list.get(0);
			gbxlh.setId(id);
			sess.delete(gbxlh);
			this.setNextEventName("xlhgrid.dogridquery");
			sess.flush();
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;		
	}
	@PageEvent("deleteRow02")
	public int deleterow02() throws RadowException{
		int index = this.getPageElement("thgrid").getCueRowIndex();
		String a0000 = this.getPageElement("a0000").getValue();
		String id = this.getPageElement("thgrid").getValue("id", index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			List<GBTH> list = sess.createQuery("from GBTH where a0000='"+a0000+"' and id='"+id+"' order by thtime desc").list();
			GBTH gbth=list.get(0);
			gbth.setId(id);
			sess.delete(gbth);
			this.setNextEventName("thgrid.dogridquery");
			sess.flush();
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;			
	}
}
