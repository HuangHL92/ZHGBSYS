package com.insigma.siis.local.pagemodel.xbrm;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.utils.CommonQueryBS;

public class QgysfcPageModel extends PageModel {
	
	
	
	
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String rb_id = this.getPageElement("rbId").getValue();
		String id = this.getPageElement("rbId").getValue();
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		try {
			String sql="";
			if(id.equals("r1c1") || id.equals("r1c2") || id.equals("r1c3") || id.equals("r1c4") || id.equals("r1c5")
					|| id.equals("r2c1") || id.equals("r2c2") || id.equals("r2c3") || id.equals("r2c4") || id.equals("r2c5")
					|| id.equals("r3c1") || id.equals("r3c2") || id.equals("r3c3") || id.equals("r3c4") || id.equals("r3c5")) {
				sql= " select a01.a0000,a01.a0101,a01.a0104,a01.a0107,a01.a0117,a01.a0141,a01.a0192a,a01.a0184,a01.a0148 " +
	                    " nvl(SUM(case when a01.A0221 = '1A31' and a02.a0219='1'  then 1 else 0 end),0) JS1311 , " +
	                    " nvl(SUM(case when a01.A0221 = '1A32' and a02.a0219='1'  then 1 else 0 end),0) JS1312 , " +
	                    " nvl(SUM(case when a01.A0221 = '1A31' and a02.a0219='2'  then 1 else 0 end),0) JS1313 , " +
	                    " nvl(SUM(case when a01.A0221 = '1A32' and a02.a0219='2'  then 1 else 0 end),0) JS1314 , " +
	                    // 现配备40岁以上
	                    " nvl(SUM(case when a01.A0221 = '1A31' and a02.a0219='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0  then 1 else 0 end),0) JS1321, " +
	                    " nvl(SUM(case when a01.A0221 = '1A32' and a02.a0219='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0  then 1 else 0 end),0) JS1322, " +
	                    " nvl(SUM(case when a01.A0221 = '1A31' and a02.a0219='2' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0  then 1 else 0 end),0) JS1323, " +
	                    " nvl(SUM(case when a01.A0221 = '1A32' and a02.a0219='2' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0  then 1 else 0 end),0) JS1324, " +
	                    // 现配备80后(1980-1989)
	                    " nvl(SUM(case when a01.A0221 = '1A31' and a02.a0219='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1331, " +
	                    " nvl(SUM(case when a01.A0221 = '1A32' and a02.a0219='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1332, " +
	                    " nvl(SUM(case when a01.A0221 = '1A31' and a02.a0219='2' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1333, " +
	                    " nvl(SUM(case when a01.A0221 = '1A32' and a02.a0219='2' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1334 " +
	                    
	                    " from A01 a01 left join A02 a02 on a01.a0000 = a02.A0000 left join A53 a53 on a53.A0000 = a01.A0000 right join (select b0111,(case when b0194='1' or b0194='3' then b0124 else get_B0124NS(b0111) end) b0124 from B01) b01 on b01.b0111 = a02.A0201B where 1=1"
	                    + " and a02.a0279='1' and a02.a0255='1' and b0124 not in ('7','71','72','73','74','75','76','8','81','82','83')";
//				if() {
//					
//				}
			
			} else if(id.equals("r1c2")) {
				
			}
			this.pageQueryByAsynchronous(sql, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
