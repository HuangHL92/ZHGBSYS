package com.insigma.siis.local.pagemodel.zwzc;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;

public class JzcxPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("editgrid.dogridquery")
	@NoRequiredValidate
	public int editgridGridQuery(int start, int limit) throws RadowException {
		StringBuilder sb = new StringBuilder("select distinct d.*,t.a0101,t.a0117,t.a0104,t.a0107,t.a0192a from A01 t,association d,a02 where t.a0000=d.a0000 and t.a0000=a02.a0000 ");
		String codeValue = this.getPageElement("checkedgroupid").getValue();
		// 判断单位是否选择
		if (StringUtils.isNotEmpty(codeValue)) {
			// 是否包含下级
			String isContain = this.getPageElement("isContain").getValue();
			if ("1".equals(isContain)) {
				sb.append(" and (a02.a0201b ='" + codeValue + "' or a02.a0201b like '" + codeValue + "%') ");
			} else {
				sb.append(" and a02.a0201b = '" + codeValue + "' ");
			}
		}
		String a0101=this.getPageElement("a0101").getValue();
		String stname=this.getPageElement("stname").getValue();
		String stjob=this.getPageElement("stjob").getValue();
		String stnature=this.getPageElement("stnature").getValue();
		String status=this.getPageElement("status").getValue();
		String approvaldate=this.getPageElement("approvaldate").getValue();
		String startdate=this.getPageElement("startdate").getValue();
		String closingdate=this.getPageElement("closingdate").getValue();
		String sessionsnum=this.getPageElement("sessionsnum").getValue();
		String salary=this.getPageElement("salary").getValue();
		if(a0101.length()>0) {
			sb.append(" and t.a0101 like '%"+a0101+"%' ");
		}
		if(stname.length()>0) {
			sb.append(" and d.stname like '%"+stname+"%' ");
		}
		if(stjob.length()>0) {
			sb.append(" and d.stjob like '%"+stjob+"%' ");
		}
		if(stnature.length()>0) {
			sb.append(" and d.stnature = '"+stnature+"' ");
		}
		if(status.length()>0) {
			sb.append(" and d.status = '"+status+"' ");
		}
		if(approvaldate.length()>0) {
			sb.append(" and d.approvaldate = '"+approvaldate+"' ");
		}
		if(startdate.length()>0) {
			sb.append(" and d.startdate = '"+startdate+"' ");
		}
		if(closingdate.length()>0) {
			sb.append(" and d.closingdate = '"+closingdate+"'");
		}
		if(sessionsnum.length()>0) {
			sb.append(" and d.sessionsnum = '"+sessionsnum+"'");
		}
		if(salary.length()>0) {
			sb.append(" and d.salary = '"+salary+"'");
		}
		this.pageQuery(sb.toString(), "SQL", start, limit); // 处理分页查询
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 修改人员信息的双击事件
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("editgrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		//获得当前页面是浏览  还是  编辑  机构树
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();

		String a0000=this.getPageElement("editgrid").getValue("a0000",this.getPageElement("editgrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			String rmbs=this.getPageElement("rmbs").getValue();
			/*if(rmbs.contains(a0000)) {
				this.setMainMessage("已经打开了");
				return EventRtnType.FAILD;
			}*/
			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+a0000+"');}}, 500);");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
}
