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

public class GBMainYJListBZPageModel extends PageModel{

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
	  if("年度考核优秀班子".equals(query_type)){
		  sql = " select * from (" + 
		  		" select b01.b0101,ztpj,bzbz,yhjy,b01.b0269 from b01,bzfx t where b01.b0111=t.b0111" + 
		  		" and t.b0111 in (select b0111 from bzndkh where year='2020' and ndkhjg='1')" + 
		  		" union all " + 
		  		" select b01.b0101,ztpj,bzbz,yhjy,b01.b0269 from b01,gqbzfx t where b01.b0111=t.b0111" + 
		  		" and t.b0111 in (select b0111 from bzndkh where year='2020' and ndkhjg='1')" + 
		  		" union all" + 
		  		" select b01.b0101,ztpj,bzbz,yhjy,b01.b0269 from b01,qxsbzfx t where b01.b0111=t.b0111" + 
		  		" and t.b0111 in (select b0111 from qxsbzndkh where year='2020' and ndkhqk='优秀')" + 
		  		" ) order by b0269";
	  }else if("年度考核一般班子".equals(query_type)){
		  sql = " select * from (" + 
			  		" select b01.b0101,ztpj,bzbz,yhjy,b01.b0269 from b01,bzfx t where b01.b0111=t.b0111" + 
			  		" and t.b0111 in (select b0111 from bzndkh where year='2020' and ndkhjg='3')" + 
			  		" union all " + 
			  		" select b01.b0101,ztpj,bzbz,yhjy,b01.b0269 from b01,gqbzfx t where b01.b0111=t.b0111" + 
			  		" and t.b0111 in (select b0111 from bzndkh where year='2020' and ndkhjg='3')" + 
			  		" union all" + 
			  		" select b01.b0101,ztpj,bzbz,yhjy,b01.b0269 from b01,qxsbzfx t where b01.b0111=t.b0111" + 
			  		" and t.b0111 in (select b0111 from qxsbzndkh where year='2020' and ndkhqk='一般')" + 
			  		" ) order by b0269";
		  }
	  
	  
	 // String ordersql = "((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' )  t where rn=1 and t.a0000=a01.a0000))";

	  //sql += " order by " + ordersql;	
	  this.pageQuery(sql, "SQL", start, limit);
	  return EventRtnType.SPE_SUCCESS;
	}
	
	
	

}
