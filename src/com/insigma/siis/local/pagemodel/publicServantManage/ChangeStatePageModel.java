package com.insigma.siis.local.pagemodel.publicServantManage;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ChangeStatePageModel extends PageModel{
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		
		this.getExecuteSG().addExecuteCode("$h.dateDisable('xgsj');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	

	//取消按钮，关闭弹窗
	@PageEvent("cancelBtn.onclick")
	public int cancel() throws AppException, RadowException {
		//this.closeCueWindow("changeStateWin");
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('changeStateWin').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//状态变更
	@PageEvent("changeState")
	@Transaction
	public int changeState()throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException{
		
		//获取被勾选的人员编号
		HttpSession session=request.getSession(); 
		String a0000s = session.getAttribute("a0000s").toString(); 	
		
		String a0163Odl = this.getPageElement("a0163Odl").getValue();   //原人员管理状态
		//将最后面的逗号截掉
		a0000s = a0000s.substring(0,a0000s.length()-1);
		String a0163 = this.getPageElement("a0163").getValue();		//人员管理状态
		
		String xgsj = this.getPageElement("xgsj").getValue();		//变化时间
		
		
		HBSession sess = HBUtil.getHBSession();
		String[] values = a0000s.split(",");
		
		A01 a01 = new A01();
		
		//循环处理每个用户
		for (String a0000 : values) {
			
			//A01 a01 = (A01)sess.get(A01.class, a0000);
			
			//更新A01表a0163和xgsj
			A01 a01_old = (A01)sess.get(A01.class, a0000);
			try {
				a01 = a01_old.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			a01_old.setA0163(a0163);   					//人员管理状态
			/*if(a01_old==null){
				a01_old = new A01();
				//applog.createLog("3301", "A01", a01_old.getA0000(), a01_old.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A01(),a01_old));
				
				applog.createLogNew("3A0163","状态变更","人员管理状态",a0000,a01_old.getA0101(), new Map2Temp().getLogInfo(a01_old,a01_old));
			}else{
				//applog.createLog("3302", "A01", a01_old.getA0000(), a01_old.getA0101(), "修改记录", new Map2Temp().getLogInfo(a01_old,a01_old));
				
				applog.createLogNew("3A0163","状态变更","人员管理状态",a0000,a01_old.getA0101(), new Map2Temp().getLogInfo(a01_old,a01_old));
			}*/
			
			applog.createLogNew("3A0163","状态变更","人员管理状态",a0000,a01_old.getA0101(), new Map2Temp().getLogInfo(a01_old,a01));
			
			
			
			if(!a0163Odl.equals(a0163) && !a0163.equals("1")){
				
				UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
				//a01_old.setXgsj(Long.valueOf(xgsj));  		//变化时间
				Date date = new Date();//获得系统时间.
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
		        Timestamp dates =Timestamp.valueOf(nowTime);//把时间转换
				
				
				a01_old.setXgr(user.getId());			//修改人(存id)
				a01_old.setXgsj(dates.getTime());		    //修改时间
			}
			
			sess.saveOrUpdate(a01_old);	
			sess.flush();
			
			//如果改为非现职人员（21,22,23,29），则人员所有的职务信息（A02表）全部更新为"以免",免职时间填写
			
			
			if(a0163.equals("21") || a0163.equals("22") || a0163.equals("23") || a0163.equals("29")){
				
				HBUtil.executeUpdate("update a02 set A0265 = '"+xgsj+"' where a0000 = '"+a0000+"' and A0255 = '1'");
				HBUtil.executeUpdate("update a02 set A0255 = '"+0+"' where a0000 = '"+a0000+"'");
				
				sess.flush();
			}
			
			//如果变更为现职人员（1），则人员的主职务需要变为在任,并且免职时间清空
			if(a0163.equals("1")){
				HBUtil.executeUpdate("update a02 set A0255 = '"+1+"',A0265 = '' where a0000 = '"+a0000+"' and a0279 = '"+1+"'");
				sess.flush();
			}
			
		}
		
		
		String tableType = this.getPageElement("type").getValue();
		//String tableType = session.getAttribute("type").toString(); 	//从session获取到列表类型
		//列表
		if("1".equals(tableType)){
			//this.closeCueWindow("changeStateWin");
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('changeStateWin').close();");
			this.getExecuteSG().addExecuteCode("window.realParent.reloadGrid();");		//刷新人员信息列表页面
		}
		//小资料
		if("2".equals(tableType)){
			//this.closeCueWindow("changeStateWin");
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('changeStateWin').close();");
			this.getExecuteSG().addExecuteCode("window.realParent.datashow();");		//刷新小资料页面
		}
		//照片
		if("3".equals(tableType)){
			//this.closeCueWindow("changeStateWin");
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('changeStateWin').close();");
			this.getExecuteSG().addExecuteCode("window.realParent.picshow();");			//刷新照片页面
		}
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	
	
	//拼接confirm提示
	@PageEvent("changeStateYY")
	@Transaction
	public int changeStateYY()throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException{
		
		//拼接提示字段
		HttpSession session=request.getSession(); 
		String a0000s = session.getAttribute("a0000s").toString(); 	
		//将最后面的逗号截掉
		a0000s = a0000s.substring(0,a0000s.length()-1);
		String[] values = a0000s.split(",");

		HBSession sess = HBUtil.getHBSession();
		A01 a01 = (A01)sess.get(A01.class, values[0]);		//查询第一个人信息 
		String name = "";
		if(a01 != null){
			name = a01.getA0101();			//获得名字 
		}

		String msg = "确认将" + name;

		if(values.length > 1){		//判断修改更改状态的人员数量是否大于1
			msg += "等"+ values.length + "人";
		}
		msg += "管理状态变更为：";
		
		String a0163_combo = this.getPageElement("a0163_combo").getValue();
		
		if(msg != null){			//追加msg信息，将要改变的人员状态追加入提示 
			msg = msg + a0163_combo+"?";
		}
		
		this.getExecuteSG().addExecuteCode("$h.confirm('系统提示：','"+msg+"',290,function(id) { if('ok'==id){radow.doEvent('changeState');}else{return false;}});");
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
}

   

    

