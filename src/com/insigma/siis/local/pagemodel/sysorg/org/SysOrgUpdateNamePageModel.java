package com.insigma.siis.local.pagemodel.sysorg.org;


import java.util.Hashtable;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.publicServantManage.WorkUnitsAddPagePageModel;

public class SysOrgUpdateNamePageModel extends PageModel {
	/**
	 * 系统区域信息
	 */
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String tag="0";//0未执行1执行
	public static String b0101stauts="0";//0未改变1改变
	public static String b0104stauts="0";//0未改变1改变
	
	public SysOrgUpdateNamePageModel(){

	}
	
	//页面初始化
	@Override
	public int doInit() {
 		String parentid ="";
		try {
			parentid = this.getRadow_parent_data();
			String parentids[] = parentid.split(",");
//			System.out.println(parentid);
//			System.out.println(parentids[0]+"==========="+parentids[1]);
			this.getPageElement("b0111").setValue(parentids[0]);
			this.getPageElement("a0201a").setValue(parentids[3]);
			String jgjc="";
			if(parentids.length==5){
				jgjc=parentids[4];
			}
			this.getPageElement("a0201c").setValue(jgjc);
			this.getExecuteSG().addExecuteCode("myfunction("+parentids[1]+","+parentids[2]+");");
		} catch (RadowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//取消
	@PageEvent("CancelBtn.onclick")
	public int CancelBtn() throws AppException, RadowException {
		this.closeCueWindow("updateNameWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//确定
	@PageEvent("YesBtn.onclick")
	@Transaction
	public int YesBtn() throws AppException, RadowException {
		String b0111 = this.getPageElement("b0111").getValue();
		String b0101radio = this.getPageElement("b0101").getValue();
		String b0104radio = this.getPageElement("b0104").getValue();
		String a0201a = this.getPageElement("a0201a").getValue();
		String a0201c = this.getPageElement("a0201c").getValue();
		if(b0101radio.equals("1")&&b0104radio.equals("1")){
			//String sql="update a02 set a0201a='"+a0201a+"' where a0201b='"+b0111+"'  and a0201a=(select b0101 from b01 where b01.b0111='"+b0111+"')";
			String sql="update a02 set a0201a='"+a0201a+"' where a0201b='"+b0111+"'  ";
			String sql2="update a02 set a0201c='"+a0201c+"' where a0201b='"+b0111+"' ";//and a0255='1'
			HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sql2).executeUpdate();
			toUpdate(b0111);
		}else if (b0101radio.equals("1")){
			//String sql="update a02 set a0201a='"+a0201a+"' where a0201b='"+b0111+"'  and a0201a=(select b0101 from b01 where b01.b0111='"+b0111+"')";
			String sql="update a02 set a0201a='"+a0201a+"' where a0201b='"+b0111+"'  ";
			HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
			toUpdate(b0111);
		}else if(b0104radio.equals("1")){
			String sql="update a02 set a0201c='"+a0201c+"' where a0201b='"+b0111+"' ";
			HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
			toUpdate(b0111);
		}else{
			
		}
		this.closeCueWindow("updateNameWin");
		this.createPageElement("isstauts", ElementType.HIDDEN, true).setValue("1");
		this.getExecuteSG().addExecuteCode("window.parent.setSuccess('"+b0111+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public static void toUpdate(String b0111) throws RadowException{
		WorkUnitsAddPagePageModel workUnitsAddPagePageModel =new WorkUnitsAddPagePageModel();
		String a0000s="select t.a0000 from a01 t,a02 b where t.a0000=b.a0000 and  b.a0201b like '"+b0111+"%'";
		List<String> list = HBUtil.getHBSession().createSQLQuery(a0000s).list();
		for(int i=0;i<list.size();i++){
			workUnitsAddPagePageModel.UpdateTitleBtn(list.get(i));
		}
	}

}
