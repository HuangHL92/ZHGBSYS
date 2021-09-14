package com.insigma.siis.local.pagemodel.customquery;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class QueryHistoryPeoplePageModel extends PageModel {

	public static String getcommSQL(){
		return "select  a01.a0000, a0101, a0104, GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " +
		",a0107,a0140,a0134,a0163,a0165,a0121,a0184,";
	}
	
	@Override
	public int doInit() throws RadowException {
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		map.put("x001", 1);
//		map.put("x002", 2);
//		((Combo)this.getPageElement("type")).setValueListForSelect(map);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 点击查询事件
	 * @return
	 * @throws RadowException
	 * @throws PrivilegeException
	 */
	@PageEvent("btn1.onclick")
	public int selectAll() throws RadowException, PrivilegeException{
	    String id=this.getPageElement("personType").getValue();
	    String a0101 = this.getPageElement("a0101A").getValue();
	    String a0184 = this.getPageElement("a0184A").getValue().toUpperCase();
	    String a0201b = this.getPageElement("a0201b").getValue();
		StringBuffer sql=new StringBuffer(); 
		sql.append(getcommSQL());
		//机构
		if(!a0201b.equals("") && a0201b != null){
			sql.append("(select b0101 from b01 where b0111 = '"+a0201b+"') orgid from A01 a01 ");
			sql.append("where a01.orgid ='"+ a0201b +"'");
		}else{
			sql.append("(select b0101 from b01 where b0111 = '"+a0201b+"') orgid from A01 a01 ");
			sql.append("where (a01.orgid is null or orgid ='')");
		}
		if("X002".equals(id)){//离退人员
			 sql.append(" and a01.status='3' ");
		}else if("X003".equals(id)){//历史人员
			sql.append(" and a01.status='2' ");
		}else if("".equals(id) || id == null){
			sql.append(" and a01.status in ('2','3')");
		}
		//姓名
		if(!a0101.equals("") && a0101 !=null){
			sql.append(" and a01.a0101 like '%"+ a0101 +"%'");
		}
		//身份证号码
		if(!a0184.equals("") && a0184 !=null){
			sql.append(" and a01.a0184 like '%"+ a0184 +"%'");
		}
        this.getPageElement("sql").setValue(sql.toString());
		this.getPageElement("checkList").setValue("");
		this.setNextEventName("persongrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 刷新列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("persongrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
	    String sql=this.getPageElement("sql").getValue();
        String personViewSQL = " select 1 from COMPETENCE_USERPERSON cu ";
        
      //人员查看权限
        sql=sql+ "  and not exists ("+personViewSQL+" where cu.a0000=a01.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') ";
        
        sql = sql + " order by a01.a0148,(select max(sortid) from b01,a02  where a01.a0000 = a02.a0000 and a02.a0201b = b01.b0111 and a02.a0255 = '1' ) desc";

        this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	
}
	
	
	/**
	 * 选择事件
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("getCheckList")
	public int getCheckList() throws RadowException, AppException{
		String listString="";
		int cnt=0;
		PageElement pe = this.getPageElement("persongrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			
			if(!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				listString=listString+hm.get("a0000")+",";
				++cnt;
			}
		}
//		if(!"".equals(listString)&&listString!=null)
//		    listString=listString.substring(listString.indexOf("@")+1,listString.length());
 		this.getPageElement("checkList").setValue(listString
 				);
        
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 将人员转移到选中机构
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("transform")
	public int transform() throws RadowException{
		String a0000String = this.getPageElement("checkList").getValue();
		String a0201b = this.getPageElement("bo1Value").getValue();
		String sql = "update a01 set orgid= '"+a0201b+"' where a01.a0000=?";
		String[] a0000 = a0000String.split(",");
		for (int i = 0; i < a0000.length; i++) {
			try {
				HBUtil.executeUpdate(sql, new Object[]{a0000[i]});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.setMainMessage("操作成功！");
		this.getPageElement("checkList").setValue("");
		this.setNextEventName("btn1.onclick");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("reset.onclick")
	public int reset() throws RadowException{
		this.getPageElement("a0101A").setValue("");
		this.getPageElement("a0184A").setValue("");
		//this.getPageElement("personType").setValue("");
		this.getPageElement("a0201b").setValue("");
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
}
