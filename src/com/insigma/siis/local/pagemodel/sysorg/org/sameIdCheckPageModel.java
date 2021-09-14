package com.insigma.siis.local.pagemodel.sysorg.org;

import java.beans.IntrospectionException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class sameIdCheckPageModel extends PageModel {
	
	public static void main(String[] args) {
		CommonQueryBS.systemOut(20/69+"");
	}
	public static String jiaoyan = "0";//0是打开 1是点击
	
	@Override
	public int doInit() {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		return 0;
	}
	
	@PageEvent("query.onclick")
	@NoRequiredValidate
	public int query()throws RadowException, AppException{
		this.setNextEventName("repeatInfogrid.dogridquery");
		return 0;
	}

	@PageEvent("repeatInfogrid.rowdbclick")
	@GridDataRange
	public int persongrid1OnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("repeatInfogrid").getValue("a0000",this.getPageElement("repeatInfogrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'repeatInfogrid',maximizable:false,resizable:false,draggable:false},true);");*/
			this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	@PageEvent("repeatInfogrid.dogridquery")
	@NoRequiredValidate
	public int repeatInfogridQuery(int start,int limit)throws RadowException, AppException{
		String name = this.getPageElement("a0101").getValue();
		String id = this.getPageElement("a0184").getValue();
		String sql2="";
		String where1 = "";
	    String condition = " a0184 ";
	    String where= "  where a.a0195 = v.b0111 and a.A0184 = b.a0184 ";
	    condition = " a0184 ";
	    String	orderby = " a.a0184 ";
		if(name != null && !"".equals(name.trim())){
			condition += ",a0101 ";
    		orderby +=",a.a0101";
    		where += " and a.a0101 = b.a0101 ";
    		where1 += " and a0101 = '"+name+"' ";
		}
		String sql_com = "select a.a0000, a.a0101, a.a0104, a.a0184, a.a0117, v.b0101 a0195, a.a0192a from A01 a,b01 v,";
		if(id != null && !"".equals(id.trim())){
			sql2 = sql_com+"(select "+ condition +" from (select "+ condition +" from a01 group by "+ condition +" having count(1)>= 2 "+where1+" and a0184='"+id+"') t ) b" + where +" order by " + orderby;
		}else{
			sql2 = sql_com+"(select "+ condition +" from (select "+ condition +" from a01 group by "+ condition +" having count(1)>= 2 "+where1+" ) t ) b" + where +" order by " + orderby;
		}
		this.request.getSession().setAttribute("peopleQueryRepeat", sql2);
		this.pageQuery(sql2, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	private void dialog_set(String fnDelte, String strHint,String a0000){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName(fnDelte);
		ne.setNextEventParameter(a0000);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage(strHint); // 窗口提示信息
	}
	
	@PageEvent("deleteEvent")
	@NoRequiredValidate
	public int deleteEvent(String a0000)throws RadowException, AppException{
		//判断是否有修改权限。
		if(!verifyAuth(a0000)){
			this.setMainMessage("您没有操作该人员的权限！");
			return EventRtnType.FAILD;
		}
		dialog_set("deleteconfirm1","您确定进行您所选择的操作吗？",a0000.toString().replace("'", "^"));
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("deleteconfirm1")
	public int deleteconfirm1(String a0000)throws AppException, RadowException{
		dialog_set("deleteconfirm","请再次确认您的操作！",a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//删除事件
	@PageEvent("deleteconfirm")
	@Transaction
	public int deleteconfirm(String peopleId)throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException{
		//String radio = this.getPageElement("radio").getValue();
		//获取被勾选的人员编号
		String sa0000 = "";
		String a0000s = peopleId.replace("^", "'");
		HBSession sess = HBUtil.getHBSession();
		String[] values = a0000s.split("@");
		if (values.length > 1) {
			StringBuffer sb = new StringBuffer();
			String sql = values[0];
			if(!sql.contains("*")){
				sql="select * from ("+sql+")";
			}
			String newsql = sql.replace("*", "a0000");
			List allSelect = sess.createSQLQuery(newsql).list();
			if (allSelect.size() > 0) {
				for (int i = 0; i < allSelect.size(); i++) {
					//判断是否有删除权限。c.type：机构权限类型(0：浏览，1：维护)
					String a0000 = allSelect.get(i).toString();
					A01 a01 = (A01)sess.get(A01.class, a0000);
					String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
							" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
							" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
					String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
					List elist = sess.createSQLQuery(editableSQL).list();
					List elist2 = sess.createSQLQuery(editableSQL2).list();
		/*			判断该人员的管理类别浏览权限------------------------------------------------------------------------------------------------------*/
					String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
					if(type == null || !type.contains("'")){
						type ="'zz'";//替换垃圾数据
					}
					List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
					if(elist3.size()>0){//无管理类别维护权限,即人员信息不可编辑
						continue;
					}
					if(elist2==null||elist2.size()==0){//维护权限
						if(elist!=null&&elist.size()>0){//有浏览权限
							continue;
						}else{
							//有两种情况：非现职人员，其他现职人员。非现职人员只能查看，不能编辑；其他现职人员可查看，可编辑
							if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//非现职人员
								continue;
							}else {
								sb.append("'").append(a0000).append("',");
							}
							
						}
					}else {
						sb.append("'").append(a0000).append("',");
					}
				}
				if (sb.length() == 0) {
					throw new AppException("所选人员不可操作！");
				}
				sa0000 = sb.substring(0, sb.length() - 1);
			} else {
				throw new AppException("请先选中要删除的人员！");
			}
		}else{
			String[] s =  a0000s.split(",");
			for(String str : s){
				str = "'"+str+"'";
				A01 a01 = (A01)sess.get(A01.class, str.replace("'", ""));
				String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
						" b.a0201b=c.b0111 and a.a0000="+str+" and c.userid='"+SysManagerUtils.getUserId()+"' " +
						" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
				String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
				" b.a0201b=c.b0111 and a.a0000="+str+" and c.userid='"+SysManagerUtils.getUserId()+"' " +
				" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
				List elist = sess.createSQLQuery(editableSQL).list();
				List elist2 = sess.createSQLQuery(editableSQL2).list();
	/*			判断该人员的管理类别浏览权限------------------------------------------------------------------------------------------------------*/
				String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
				if(type == null || !type.contains("'")){
					type ="'zz'";//替换垃圾数据
				}
				List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000="+str+" and a01.a0165 in ("+type+")").list();
				if(elist3.size()>0){//无管理类别维护权限,即人员信息不可编辑
					
				}
				if(elist2==null||elist2.size()==0){//维护权限
					if(elist!=null&&elist.size()>0){//有浏览权限
						
					}else{
						//有两种情况：非现职人员，其他现职人员。非现职人员只能查看，不能编辑；其他现职人员可查看，可编辑
						if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//非现职人员
							
						}else {
							sa0000 = sa0000 + str + ","; 
						}
						
					}
				}else {
					sa0000 = sa0000 + str + ","; 
				}
			}
			if(sa0000.length() > 0){
				sa0000 = sa0000.substring(0, sa0000.length() - 1);
			}
		}
		//判断：如果有值被选中
		if(!"".equals(sa0000) && sa0000 != null){

				//a01.setStatus("0");//修改完全删除
				//sess.saveOrUpdate(a01);
				deletePerson(sa0000);
				this.setMainMessage("删除成功！");
				 this.getExecuteSG().addExecuteCode("reloadGird()");
				//sess.flush();
				//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('delete');");
				
			
		}else{
			throw new AppException("所选人员不可操作！");
		}
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	private void deletePerson(String a0000) throws AppException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		List<A01> a01_list = (List<A01>) HBUtil.getHBSession().createQuery("from A01 where a0000 in("+a0000+")").list();
		
		List<A57> a57_list = (List<A57>) HBUtil.getHBSession().createQuery("from A57 where a0000 in("+a0000+")").list();
		for(A57 a57 : a57_list){
			if(a57!=null&&a57.getPhotopath()!=null&&!"".equals(a57.getPhotopath())){
				String photourl = a57.getPhotopath();
				File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
				if(fileF.isFile()){
					fileF.delete();
				}
				
			}
		}
		Statement stmt=null;
		Connection conn = HBUtil.getHBSession().connection();
		try{
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			//String a0000 = a01.getA0000();
			stmt.executeUpdate("delete from a02 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a05 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a06 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a08 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a14 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a15 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a36 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a11 where a1100 in(select a1100 from a41 where a0000 in ("+a0000+"))");
			stmt.executeUpdate("delete from a41 where a0000 in ("+a0000+")");
			
			stmt.executeUpdate("delete from a29 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a53 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a37 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a31 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a30 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a57 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a60 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a61 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a62 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a63 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a64 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a01 where a0000 in("+a0000+")");
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			throw new AppException("数据库处理异常！",e);
		}finally{
			if (stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new AppException("数据库处理异常！", e);
				}
			}
				
		}
		
		for(int i=0;i<a01_list.size();i++){
			
			new LogUtil("33", "A01", a01_list.get(i).getA0000(), a01_list.get(i).getA0101(), "删除记录", new ArrayList()).start();
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
			/*String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000='"+id+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";*/
			String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
			" b.a0201b=c.b0111 and a.a0000='"+id+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
			" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
			//List elist = sess.createSQLQuery(editableSQL).list();
			List elist2 = sess.createSQLQuery(editableSQL2).list();
			if(elist2==null||elist2.size()==0){	
				return false;
			}
		} catch (Exception e) {
			
		}
		return true;
	
	}
}	

