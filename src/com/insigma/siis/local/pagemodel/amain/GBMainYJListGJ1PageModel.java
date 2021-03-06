package com.insigma.siis.local.pagemodel.amain;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;

public class GBMainYJListGJ1PageModel extends PageModel{

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
	  String query_type = this.getPageElement("query_type").getValue();
	  String sql = "";
	  String ordersql = " order by  ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' )  t where rn=1 and t.a0000=a01.a0000))";

	  if("edugzmd".equals(query_type)){
		  sql = " select a0101,(select code_name from code_value where code_type='GB2261' and code_value=a01.a0104) a0104," + 
		  		" substr(a0107,0,4)|| '.'|| substr(a0107,5,2) a0107," + 
		  		" a0192a,t.* from EDU_GZMD t,a01" + 
		  		" where t.a0000=a01.a0000 and substr(a0107,0,6)>196312 " + ordersql ;
	  }else if("edu36".equals(query_type)){
		  sql = " select a0101,(select code_name from code_value where code_type='GB2261' and code_value=a01.a0104) a0104," + 
			  		" substr(a0107,0,4)|| '.'|| substr(a0107,5,2) a0107," + 
			  		" a0192a,t.* from edu36 t,a01" + 
			  		" where t.a0000=a01.a0000  and substr(a0107,0,6)>196312 " + ordersql ;
	  }else if("fc5t".equals(query_type)){
		  sql = " select z.a0000,z.a0101,(select code_name from code_value where code_type='GB2261' and code_value=a01.a0104) a0104," + 
			  		" substr(a0107,0,4)|| '.'|| substr(a0107,5,2) a0107," + 
			  		" a0192a,z.fcsl zxs from DJDFC_5 z,a01" + 
			  		" where z.a0000=a01.a0000   order by fcsl desc"  ;
	  }else if("jsb2000w".equals(query_type)){
		  sql = " select z.a0000,z.xm a0101,qtry || '(' || cw || ')' zxs,(select code_name from code_value where code_type='GB2261' and code_value=a01.a0104) a0104, 	" + 
			  		" substr(a0107,0,4)|| '.'|| substr(a0107,5,2) a0107," + 
			  		" a01.a0192a from DJDQY2000W z,a01" + 
			  		" where z.a0000=a01.a0000   " + ordersql ;
	  }else if("jttz2000w".equals(query_type)){
		  sql = " select z.a0000,z.xm a0101,(select code_name" + 
		  		"          from code_value" + 
		  		"         where code_type = 'GB2261'" + 
		  		"           and code_value = a01.a0104) a0104,a01.a0184 zxs,a0192a" + 
		  		"  from DJD2000W z, a01" + 
		  		"  where z.a0000=a01.a0000 "  + ordersql ;
	  }
	  

	 // String ordersql = "((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' )  t where rn=1 and t.a0000=a01.a0000))";

	  //sql += " order by " + ordersql;	
	  this.pageQuery(sql, "SQL", start, limit);
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
