package com.insigma.siis.local.pagemodel.zwzc;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.amain.GBMainBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;

public class LsmdListPageModel extends PageModel {


	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("noticeSetgrid.dogridquery");
		return 0;
	}

	
	
	/**
	 *  查询未匹配人员信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("noticeSetgrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
	  String userid = SysManagerUtils.getUserId();
	  String mdid=this.getPageElement("mdid").getValue();
	  StringBuilder sb = new StringBuilder("select a01.a0000,decode(a01.a0101,null,t.a0101,a01.a0101) a0101,a01.a0104,a01.a0117,a01.a0107,a01.a0111a,a01.a0134,a01.a0144,a01.a0196,a01.a0184,a01.a0141,'' a0193,'' a1705," + 
				"     case when a01.a0192a=t.a0192a then '' else t.a0192a end a0192a,case when a01.a0192a=t.a0192a then a01.a0192a else '<font color=\"red\">'|| ltrim(a01.a0192a) ||'</font>' end  as a0192anow," + 
				"     case when a01.a0221=t.a0221 then '' else (select g.code_name from code_value g where g.code_type='ZB09' and g.code_value=t.a0221) end a0221,case when a01.a0221=t.a0221 then (select g.code_name from code_value g where g.code_type='ZB09' and g.code_value=a01.a0221) else '<font color=\"red\">'|| ltrim((select g.code_name from code_value g where g.code_type='ZB09' and g.code_value=a01.a0221)) ||'</font>' end  as a0221now," + 
				"     case when a01.a0192f=t.a0192f then '' else t.a0192f end a0192f,case when a01.a0192f=t.a0192f then a01.a0192f else '<font color=\"red\">'|| ltrim(a01.a0192f) ||'</font>' end  as a0192fnow," + 
				"       t.mdid," + 
				"       t.sortid," + 
				"       t.remark," + 
				"       t.ZZXLXWXX," + 
				"       t.ZZXLXW," + 
				"       t.QRZXLXW," + 
				"       t.QRZXLXWXX,"+ 
				"(select username from smt_user c where t.a0188=c.userid) username " + 
				" from HISTORYPER t,a01 where t.mdid='"+mdid+"' and t.a0000=a01.a0000(+) " + 
				" order by t.sortid");	
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
	@PageEvent("noticeSetgrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		//获得当前页面是浏览  还是  编辑  机构树
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();

		String a0000=this.getPageElement("noticeSetgrid").getValue("a0000",this.getPageElement("noticeSetgrid").getCueRowIndex()).toString();
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
