package com.insigma.siis.local.pagemodel.customquery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class PeopleTransferPageModel extends PageModel{
	private LogUtil applog = new LogUtil();
	
	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		//String param = request.getParameter("initParams");
		String param = this.getPageElement("subWinIdBussessId").getValue();
		
		if(param!=null){
			String[] s = param.split(",");
			String a0000 = s[0];
			String a0101 = s[1];
			//判断人员是否已经被调转过
			String sql = "SELECT COUNT(1) FROM PEOPLE_TRANSFER WHERE A0000 = '"+a0000+"'";
			String num = HBUtil.getHBSession().createSQLQuery(sql).uniqueResult().toString();
			Integer n = Integer.parseInt(num);
			if(n>0){
				this.getExecuteSG().addExecuteCode("doubleA0000('"+a0101+"')");
			}else{
				this.getPageElement("a0000").setValue(a0000);
				this.getPageElement("a0101").setValue(a0101);
				this.getExecuteSG().addExecuteCode("setAllValue('"+a0101+"')");
			}
		}
		return 0;
				
	}
	
	@PageEvent("closeWin.onclick")
	public int close() throws AppException, RadowException {
		this.closeCueWindow("peopleTrans");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("trueTransfer")
	public int deleteconfirm1(String str)throws AppException, RadowException{
		String[] s = str.split(",");
		String name = s[0];
		String loginName = s[1];
		String days = s[2];
		
		trueTransfer("transfer","现将   "+name+" 的个人信息调转给用户    "+loginName+" ，并会将其转为非现职人员，是否确认您的操作？",loginName+","+days);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void trueTransfer(String fnDelte, String strHint,String str){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName(fnDelte);
		ne.setNextEventParameter(str);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage(strHint); // 窗口提示信息
	}
	
	@PageEvent("transfer")
	@Transaction
	public int transfer(String str) throws AppException, RadowException {
		String a0000 = this.getPageElement("a0000").getValue();
		String[] s = str.split(",");
		try {
			HBSession sess = HBUtil.getHBSession();
			
			String loginName = s[0];//用户登录名
			String day = s[1];//有效期
			
			String daySql = "";
			if(DBUtil.getDBType()==DBType.ORACLE){
				daySql = "SELECT TO_CHAR(SYSDATE+'"+day+"','YYYYMMDD') FROM DUAL";
			}else{
				daySql = "SELECT date_format(SYSDATE()+'"+day+"','%Y%m%d') FROM DUAL";
			}
			
			
			String days = (String) sess.createSQLQuery(daySql).uniqueResult();
		
			if(loginName != null && !"".equals(loginName)){
				String sql = "SELECT a.USERID FROM SMT_USER a WHERE a.LOGINNAME = '"+loginName+"' AND a.USEFUL = '1'";
				String loginid = (String)sess.createSQLQuery(sql).uniqueResult();
				if(loginid != null && !"".equals(loginid.trim())){
					String uuid = UUID.randomUUID().toString();
					String insertSql = "INSERT INTO PEOPLE_TRANSFER(UUID,USEID,A0000,LOGINID,OVERDAY) "
							+ "VALUES ('"+uuid+"','"+SysManagerUtils.getUserId()+"','"+a0000+"','"+loginid+"','"+days+"')";
					sess.createSQLQuery(insertSql).executeUpdate();
					
					//将人员a0000的所有职位做已免处理，并改为非现职人员
					Date d = new Date(); 
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
			        String time = sdf.format(d);  			//当前系统时间
					
			        String updateA0265 = "UPDATE A02 SET A0265 = '"+time+"' WHERE A02.A0000 = '"+a0000+"' AND A02.A0255 = '1'";
			        sess.createSQLQuery(updateA0265).executeUpdate();
					String updateA0255 = "UPDATE A02 SET A02.A0255 = '0' WHERE A02.A0000 = '"+a0000+"'";
					sess.createSQLQuery(updateA0255).executeUpdate();
					
					A01 a01_odl = (A01)sess.get(A01.class, a0000);
					
					A01 a01 = (A01)sess.get(A01.class, a0000);
					a01.setA0163("22");
					//a01.setStatus("2");
					//获取a0201b（后期可能需要修改）
					/*String sqlA0201b="SELECT a0201b FROM a02,(SELECT cu.b0111 FROM competence_userdept cu WHERE cu.userid = '"+SysManagerUtils.getUserId()+"') b "
							+ "WHERE a0000 = '"+a0000+"' AND A02.A0201B = b.B0111 ORDER BY a0255 DESC, a0223";
					List<Object> l = sess.createSQLQuery(sqlA0201b).list();
					if(l!=null){
						a01.setOrgid(l.get(0).toString());
					}*/
					
					applog.createLogNew("3A0163","人员调出","人员状态",a0000,a01.getA0101(), new Map2Temp().getLogInfo(a01_odl,a01));
					
					sess.update(a01);
					
					//记录日志
					//applog.createLogNewF("人员调出","人员状态",a0000,a01.getA0101());
					
					this.getExecuteSG().addExecuteCode("transferSucces();");
					/*
					this.setMainMessage("人员调转成功！");
					
					this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('peopleTrans').close();");
					this.getExecuteSG().addExecuteCode("window.realParent.reloadGrid();");		//刷新人员信息列表页面
*/				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("人员调转失败！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
