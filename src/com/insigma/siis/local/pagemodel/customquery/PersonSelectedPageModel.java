package com.insigma.siis.local.pagemodel.customquery;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;


/**  

* <p>Title: PersonSelectedPageModel.java</p>  

* <p>Description: 选人页面控制层</p>  

* @author rxl 

* @date 2019年6月21日  

*/  
public class PersonSelectedPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("peopleInfoGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException, AppException{
		String b0111 = this.getPageElement("b0111").getValue();
		String stype = this.getPageElement("stype").getValue();//查询类型
		String colsm = this.getPageElement("colsm").getValue(); //查询条件
		String userID = SysManagerUtils.getUserId();
		String sql = "select distinct a.a0000 , a.a0101 , a.a0104 ,a.a0184,a.a0192a ,a.a0163 from A01 a inner join A02 a2 on a.a0000 = a2.a0000 where a2.a0201b like '"+b0111+"%' and a.a0163 = 1 and a2.a0201b in (select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"')" ;
		if(!StringUtils.isEmpty(stype)&&!StringUtils.isEmpty(colsm)){

			if(stype.equals("name")){
				colsm = colsm.toUpperCase();//全部大写
				sql+=" and  (a.a0101 like '"+colsm+"%'";
				sql+=" or  a.a0102 like '"+colsm+"%')";
			}else if(stype.equals("idCard")){
				sql+=" and  a.a0184 = '"+colsm+"'";

			}
		}

		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	@PageEvent("querybyid")
	public int querybyid() throws RadowException, AppException{
		this.getExecuteSG().addExecuteCode("serach()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("peopleInfoGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{ 
		String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
		String a0101=this.getPageElement("peopleInfoGrid").getValue("a0101",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
	   //	this.request.getSession().setAttribute("a0000", a0000);
		this.getExecuteSG().addExecuteCode("	window.realParent.receiveId('"+a0000+"','"+a0101+"')");
		this.getExecuteSG().addExecuteCode("	window.close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
