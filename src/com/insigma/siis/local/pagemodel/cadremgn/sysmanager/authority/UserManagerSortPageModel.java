package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class UserManagerSortPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		return 1;
	}
	@PageEvent("YesNew")
	@OpLog
	@Transaction
	public int YesNew(String ids) throws AppException, RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SQLException, IntrospectionException {
		String [] arr= ids.split("@@");
		for(int i=0;arr!=null&&i<arr.length;i++){
			String ids2=arr[i];
			if(ids2!=null&&ids2.length()!=0){
				sort(ids2);
			}
		}
		this.getExecuteSG().addExecuteCode("realParent.reloadTree();");
		this.setMainMessage("操作完成！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public int sort(String ids2) throws RadowException{
		Session sess=null;
		Connection conn=null;
		Statement stmt=null;
		try{
			sess = HBUtil.getHBSession().getSession();
			conn=sess.connection();
			String [] arr2=ids2.split(",");
			for(int j=0;arr2!=null&&j<arr2.length;j++){
				String id=arr2[j];
				String []arr3=id.split("\\$\\$");
				id=arr3[0];
				String flag="";
				if(arr3.length>1){
					flag=arr3[1];
				}
				
				stmt=conn.createStatement();
				if("true".equals(flag)){//用户
					String sql=" update smt_user set sortid='"+(j+1)+"' where userid='"+id+"' ";
					stmt.execute(sql);
				}else{//部门 
					String sql=" update smt_usergroup set sortid='"+(j+1)+"' where id='"+id+"' ";
					stmt.execute(sql);
				}
				stmt.close();
			}
		}catch(Exception e){
			try{
				if(stmt!=null){
					stmt.close();
				}
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e1){}
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return 1;
	}
}
