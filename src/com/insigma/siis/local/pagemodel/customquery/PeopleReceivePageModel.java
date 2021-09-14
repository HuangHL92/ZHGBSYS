package com.insigma.siis.local.pagemodel.customquery;

import java.util.List;
import java.util.UUID;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class PeopleReceivePageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		String param = request.getParameter("initParams");
		if(param!=null){
			String[] s = param.split(",");
			String a0000 = s[0];
			String a0101 = s[1];
			
			this.getPageElement("a0000").setValue(a0000);
			this.getPageElement("a0101").setValue(a0101);
			this.getExecuteSG().addExecuteCode("setAllValue('"+a0101+"')");
		}
		return 0;
	}
	
	@PageEvent("receive")
	@Transaction
	public int receive(String param) throws AppException, RadowException {
		String[] s = param.split(",");
		String a0000 = s[0];
		String a0201a = s[1];
		String a0201b = s[2];
		HBSession sess = HBUtil.getHBSession();
		try {
			A01 a01 = (A01)sess.get(A01.class, a0000);
			if(StringUtil.isEmpty(a0000) || a01==null ){
				throw new AppException("人员信息有误！");
			}
			//从people_transfer中删除记录，将其改为现职人员，并在a02添加一条在任的空数据，同时处理统计关系所在单位
			a01.setA0163("1");
			a01.setStatus("1");
			a01.setOrgid("");
			//处理统计关系所在单位
			String newA0201b = "";
			try {
				getA0195(a0201b);
			} catch (RuntimeException e) {
				// TODO: handle exception
				newA0201b = e.getMessage();
			}
			a01.setA0195(newA0201b);
			sess.update(a01);
			
			String b0104Sql = "SELECT B0104 FROM B01 WHERE B0111 = '"+a0201b+"'";
			Object o = sess.createSQLQuery(b0104Sql).uniqueResult();
			String a0201C = "";
			if(o!=null){
				a0201C = o.toString();
			}
			String sql = "DELETE FROM PEOPLE_TRANSFER WHERE LOGINID = '"+SysManagerUtils.getUserId()+"' AND A0000 = '"+a0000+"'";
			sess.createSQLQuery(sql).executeUpdate();
			
			String updateA0255 = "UPDATE A02 SET A02.A0281 = 'false' WHERE A02.A0000 = '"+a0000+"'";
			sess.createSQLQuery(updateA0255).executeUpdate();
			String insertA02Sql = "INSERT INTO A02(A0000,A0200,A0201A,A0201B,A0255,A0201C,A0201D,A0251B,A0219,A0279,A0281) VALUES('"+a0000+"','"+UUID.randomUUID().toString()+"',"
					+ "'"+a0201a+"','"+a0201b+"','"+1+"','"+a0201C+"','0','0','0','0','true')";
			sess.createSQLQuery(insertA02Sql).executeUpdate();
			
			this.getExecuteSG().addExecuteCode("receiveSuccess()");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("人员接收失败！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private String getA0195(String a0201b)throws RuntimeException{
		HBSession sess = HBUtil.getHBSession();
		String b0194sql = "select B0194 from B01 where  B0111 ='"+a0201b+"'";
		Object B0194 = sess.createSQLQuery(b0194sql).uniqueResult();
		if(B0194 == null || !B0194.equals("1")){
			if(a0201b.equals("001.001")){
				a0201b = "";
			}else{
				a0201b = a0201b.substring(0, a0201b.length()-4);
				getA0195(a0201b);
			}
		}else{
			throw new RuntimeException(a0201b);
		}
		return a0201b;
	}
	
	@PageEvent("closeWin.onclick")
	public int close() throws AppException, RadowException {
		this.closeCueWindow("peopleReceive");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
