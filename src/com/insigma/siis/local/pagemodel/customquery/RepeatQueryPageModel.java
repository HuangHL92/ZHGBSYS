package com.insigma.siis.local.pagemodel.customquery;

import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class RepeatQueryPageModel extends PageModel {

	public static String getcommSQL(){
		return "select  a.a0000, a.a0101, a.a0104,a.a0111a,  a.a0117,a.a0141, a.a0192a, a.A0160 " +
		",a.a0107 from A01 a,";
	}
	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("btn1.onclick")
	public int select() throws RadowException, PrivilegeException{
		StringBuffer sql = new StringBuffer();

		sql.append(getcommSQL());
	    String name=this.getPageElement("a0101A").getValue(); //获取姓名
	    String gender = this.getPageElement("a0104A").getValue();//获取性别
	    String birth = this.getPageElement("a0107A").getValue();//获取出生日期
	    String birthPlace = this.getPageElement("a0111").getValue();//获取籍贯
	    String condition = "";
	    String where= "  where a.A0101 = b.a0101";
	    String sql2="";
	    	condition = "a0101";
	    String	orderby = "a0101";
	    	//sql.append(" and a0101 =(select a0101 from a01 group by a0101 having count(1) >= 2 and a0101='"+name+"')");
	    
	    if(!gender.equals("")){
	    	if(gender.equals("1")){
	    		condition += ",a0104";
		    	//sql.append(" and a0104 =(select a0104 from a01 group by a0104 having count(a0104)>= 2)");
	    		where += " and (a.a0104 = b.a0104 or b.a0104 is null) ";
	    		orderby+=",a.a0104";
	    	}
	    }
		if(!birth.equals("")){
			if(birth.equals("1")){
				condition += ",a0107";
				//sql.append(" and a0107 =(select a0107 from a01 group by a0107 having count(a0107)>= 2)");
				where += " and (a.a0107 = b.a0107 or b.a0107 is null) ";
				orderby+=",a.a0107";
			}
		}
		if(!birthPlace.equals("")){
			if(birthPlace.equals("1")){
				condition += ",a0111a";
				//sql.append(" and a0111 =(select a0111 from a01 group by a0111 having count(a0111)>= 2)");
				where += " and (a.a0111a = b.a0111a or b.a0111a is null) ";
				orderby+=",a.a0111a";
			}
		}
		if(name.trim().toString().equals("")){
//			sql.append(" and a0101 = any(select a0101 from (select "+ condition +" from a01 group by "+ condition +" having count(1)>= 2 ) t) order by "+ condition +"");
			sql2 = getcommSQL()+"(select "+ condition +" from (select "+ condition +" from a01 group by "+ condition +" having count(1)>= 2) t ) b" + where +" order by " + orderby;
		}else{
//			sql.append(" and a0101 = any(select a0101 from (select "+ condition +" from a01 group by "+ condition +" having count(1)>= 2 and a0101='"+name+"') t) order by "+ condition +"");
			sql2 = getcommSQL()+"(select "+ condition +" from (select "+ condition +" from a01 group by "+ condition +" having count(1)>= 2 and a0101='"+name+"') t ) b" + where +" order by " + orderby;
		}
				
        this.getPageElement("sql").setValue(sql2.toString());
		this.setNextEventName("persongrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	/**
	 * 分页查询
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("persongrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
	    String sql=this.getPageElement("sql").getValue();
        //String personViewSQL = " select 1 from COMPETENCE_USERPERSON cu ";
        
      //人员查看权限
        //sql=sql+ "  and not exists ("+personViewSQL+" where cu.a0000=a01.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') ";
         
        this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	
}
	
	/**
	 * 删除操作
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PageEvent("dogriddelete")
	public int dogriddelete(String id) throws Exception {
//		String sql = " select 1 from COMPETENCE_USERPERSON cu  where cu.a0000='"+id+"' and cu.userid='"+SysManagerUtils.getUserId()+"'";
//		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		
		//判断是否有修改权限。
		
		if(!verifyAuth(id)){
			this.setMainMessage("您没有操作该人员的权限！");
			return EventRtnType.FAILD;
		}

//		NextEvent ne = new NextEvent();
//		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
//		ne.setNextEventName("suretodelete");
//		ne.setNextEventParameter(id);
//		this.addNextEvent(ne);
//		NextEvent nec = new NextEvent();
//		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
//		this.addNextEvent(nec);
//		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		//this.setMainMessage("您确实要删除该记录？"); // 窗口提示信息
		this.setRadow_parent_data("'"+id+"',");
	    this.openWindow("deletePersonWin", "pages.publicServantManage.DeletePersonPage");
		return EventRtnType.NORMAL_SUCCESS;		
	}

	
	@PageEvent("suretodelete")
	public int delete(String id) throws RadowException {
		String name = HBUtil.getHBSession().createSQLQuery("select a0101 from a01 where a0000='"+id+"'").list().get(0).toString();
		try {
			//记录日志
			A01 a01log = new A01();
			new LogUtil().createLog("33", "A01", id, name, "人员信息删除", new Map2Temp().getLogInfo(new A01(), a01log));
			HBUtil.executeUpdate("delete from a01 where a0000 = ?", new Object[]{id});
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			e.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setMainMessage("删除成功！");
		this.setNextEventName("btn1.onclick");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	/**
	 * 修改人员信息的双击事件
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("persongrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("persongrid").getValue("a0000",this.getPageElement("persongrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			CommonQueryBS.systemOut(this.getPageElement("persongrid").getCueRowIndex()+"");
		this.getExecuteSG().addExecuteCode("addTab('','"+this.getPageElement("persongrid").getValue("a0000",this.getPageElement("persongrid").getCueRowIndex()).toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		this.setRadow_parent_data(this.getPageElement("persongrid").getValue("a0000",this.getPageElement("persongrid").getCueRowIndex()).toString());
		return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	
	/**
	 * 人员操作权限控制
	 * 返回false 无权限  返回true 有权限
	 * @param id
	 * @return
	 */
	public static boolean verifyAuth(String id){
		try {
			HBSession sess = HBUtil.getHBSession();
			String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000='"+id+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
			String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
			" b.a0201b=c.b0111 and a.a0000='"+id+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
			" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
			List elist = sess.createSQLQuery(editableSQL).list();
			List elist2 = sess.createSQLQuery(editableSQL2).list();
			if(elist2==null||elist2.size()==0){
				if(elist!=null&&elist.size()>0){
					
					return false;
				}
			}
		} catch (Exception e) {
			
		}
		return true;
	
	}
	
}
