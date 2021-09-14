package com.insigma.siis.local.pagemodel.customquery;


import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class gbmcQuery_mntpPageModel extends PageModel{
	
	
	@Override
	public int doInit() throws RadowException {
		this.request.getSession().setAttribute("gbmcName","");
		this.request.getSession().setAttribute("gbmcSql","");
		this.setNextEventName("grid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	//刷新列表
	@PageEvent("grid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String type = this.getPageElement("gbquery").getValue();
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String userid = user.getId();
		String sql = "select id as uuid,name,sortid from A01_GBMC a where a.type = '"+type+"' order by to_number(a.sortid)";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("saveBtn.onclick")
	@Transaction
	@Synchronous(true)
	public int savePersonsort()throws RadowException, AppException{
		List<HashMap<String,String>> list = this.getPageElement("grid").getStringValueList();
		try {
			int i = 1;
			for(HashMap<String,String> m : list){
				String uuid = m.get("uuid");
				HBUtil.executeUpdate("update A01_GBMC set sortid= "+i+" where id = '"+uuid+"'");
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		
		this.getExecuteSG().addExecuteCode("radow.doEvent('grid.dogridquery');");
		
		this.setMainMessage("排序成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("closeBtn.onclick")
	public int close() throws AppException, RadowException {
		this.getExecuteSG().addExecuteCode("window.close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String closeCueWindowEX(){
		return "window.close();";
	}
	
	@PageEvent("grid.rowdbclick")
	@GridDataRange
	@NoRequiredValidate
	public int rowdbclick() throws RadowException, AppException{
		//this.getPageElement("checkedgroupid").setValue(null);
		this.request.getSession().setAttribute("queryType", "1");
		this.request.getSession().setAttribute("queryTypeEX", "新改查询方式");
		int i = this.getPageElement("grid").getCueRowIndex();
		String uuid=this.getPageElement("grid").getValue("uuid",i).toString();
		String name=this.getPageElement("grid").getValue("name",i).toString();
		//String querysql = this.getPageElement("grid").getValue("querysql").toString();
		Object[] query = (Object[]) HBUtil.getHBSession().createSQLQuery("select sql,CHECKEDGROUPID from A01_GBMC a where a.id = '"+uuid+"'").uniqueResult();
		String checkedgroupid = "";
		if(query[1]==null||"".equals(query[1])) {
			checkedgroupid = "001.001";
		}else {
			checkedgroupid = query[1].toString();
		}
		this.request.getSession().setAttribute("isA0225", "");
		//this.getExecuteSG().addExecuteCode("realParent.document.getElementById('checkedgroupid').value='"+checkedgroupid+"'");
		
		Clob clob = (Clob)query[0];
		String sql = "";
		try {
			sql = ClobToString(clob);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		this.request.getSession().setAttribute("gbmcName",name);
		this.request.getSession().setAttribute("gbmcSql",sql);
		this.getPageElement("sql").setValue(sql);
		this.getExecuteSG().addExecuteCode("realParent.dosearch();window.close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String ClobToString(Clob clob) throws SQLException, IOException { 
    	
        String reString = ""; 
        java.io.Reader is = clob.getCharacterStream();// 得到流 
        BufferedReader br = new BufferedReader(is); 
        String s = br.readLine(); 
        StringBuffer sb = new StringBuffer(); 
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING 
            sb.append(s); 
            s = br.readLine(); 
        } 
        reString = sb.toString(); 
        return reString; 
    }
}
