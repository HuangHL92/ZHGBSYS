package com.insigma.siis.local.pagemodel.pakh;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class PakhMaintainPageModel extends PageModel {
	

	public static String getEts00SelData(){
		String selSqL = "select ets00,ets01 from PAKH_SCHEMES order by ets02";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode;
		try {
			listCode = cqbs.getListBySQL(selSqL);
			if(listCode!=null&&listCode.size()>0){
				HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
				StringBuffer sb = new StringBuffer("");
				//sb.append("[");
				for(int i=0;i<listCode.size();i++){
					sb.append("\r\n['"+listCode.get(i).get("ets00").toString()+"','"+listCode.get(i).get("ets01")+"'],");
				}
				sb.deleteCharAt(sb.length()-1);
				//sb.append("],");
				
				//sb.deleteCharAt(sb.length()-1);
				return sb.toString();
			}
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "[]";
	}
	
	
	@Override
	public int doInit() throws RadowException {
		String selSqL = "select ets00,ets01 from PAKH_SCHEMES order by ets02";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode;
		try {
			listCode = cqbs.getListBySQL(selSqL);
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				mapCode.put(listCode.get(i).get("ets00").toString(), listCode.get(i).get("ets01")==null?"":listCode.get(i).get("ets01"));
			}
			((Combo)this.getPageElement("ets00")).setValueListForSelect(mapCode);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 指标主信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		
		//定义用来组装sql的变量
		StringBuffer str = new StringBuffer();
		str.append("select  * from PAKH_LIST  where egl02='"+this.getCurUserid()+"'");
		
		
		
		this.pageQuery(str.toString(), "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	@PageEvent("addEGLInfo")
	@Transaction
	public int addEGLInfo() throws Exception{
		String egl01 = this.getPageElement("egl01").getValue();
		String ets00 = this.getPageElement("ets00").getValue();
		String egl04 = this.getPageElement("egl04").getValue();
		String egl00 = this.getPageElement("egl00").getValue();
		if(StringUtils.isEmpty(ets00)){
			this.setMainMessage("请选择指标方案！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtils.isEmpty(egl00)){
			egl00 = UUID.randomUUID().toString();
			HBUtil.executeUpdate("insert into PAKH_LIST(egl00,egl01,egl02,ets00,egl04) values(?,?,?,?,?)",
					new Object[]{egl00,egl01,this.getCurUserid(),ets00,egl04});
		}else{
			HBUtil.executeUpdate("update PAKH_LIST set egl01=?,egl04=? where egl00=?",
					new Object[]{egl01,egl04,egl00});
		}
		this.getExecuteSG().addExecuteCode("Ext.getCmp('addEGL').hide();");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	//保存分值
	@PageEvent("saveGrade")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveGrade(String confirm)throws RadowException, AppException{
		String grade = this.request.getParameter("grade");
		String b01id = this.request.getParameter("b01id");	
		String et00 = this.request.getParameter("et00");	
		String etc00 = this.request.getParameter("etc00");	
		String eg00 = this.request.getParameter("eg00");	
		String egl00 = this.request.getParameter("egl00");	
		
		if(!"".equals(grade)){
			try{
				Float.valueOf(grade);
			}catch (Exception e) {
				this.setMainMessage("非法数字:"+grade+"！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}
		
		HBSession hbSession = HBUtil.getHBSession();
		List<Object> egList = hbSession.createSQLQuery("select 1 from PAKH_GERDEN where eg00=?").setString(0, eg00).list();
		if(egList.size()==0){//新增
			hbSession.createSQLQuery("insert into PAKH_GERDEN(eg00,b01id,grade,etc00,et00,egl00) values(?,?,?,?,?,?)")
			.setString(0, eg00).setString(1, b01id).setString(2, grade).setString(3, etc00).setString(4, et00).setString(5, egl00)
			.executeUpdate();
		}else{//更新
			if(StringUtils.isEmpty(grade)){
				hbSession.createSQLQuery("delete from PAKH_GERDEN where eg00 ='"+eg00+"'").executeUpdate();
			}else{
				hbSession.createSQLQuery("update PAKH_GERDEN set grade = '"+grade+"' where eg00 ='"+eg00+"'").executeUpdate();
			}
			
		}
		
		hbSession.flush();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("allDelete")
	@Transaction
	public int allDelete(String egl00) throws RadowException, AppException{
		
		try {
			HBSession sess = HBUtil.getHBSession();
			//HBUtil.executeUpdate("delete from EVALUATION_grade where egl00=?",new Object[]{egl00});
			HBUtil.executeUpdate("delete from PAKH_LIST where egl00=?",new Object[]{egl00});
			sess.flush();
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
