package com.insigma.siis.local.pagemodel.fxyp;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class MNMDGLPageModel extends PageModel {
	

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("blackGrid.dogridquery");
		this.setNextEventName("grayGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("blackGrid.dogridquery")
	public int doBlackGrid(int start,int limit) throws RadowException{
		String userid = SysManagerUtils.getUserId();
		String sql="select a.a0200 a0200_b,a.a0000 a0000_b,c.a0101 a0101_b,c.a0104 a0104_b,substr(c.a0107,1,4)||'.'||substr(c.a0107,5,2) a0107_b,(select b0101 from b01 where b.a0201b=b01.b0111) a0201b_b,b.a0215a a0215a_b "
				+ "  from HZ_MNTP_HHMD a,a02 b,a01 c "
				+ " where a.a0200=b.a0200 and a.a0000=c.a0000 and a.mdtype ='1'  "
				+ " order by (select rpad(b01.b0269, 25, '.') || lpad(b.a0225, 25, '0') from  b01 where b01.b0111=b.a0201b)";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("grayGrid.dogridquery")
	public int doGrayGrid(int start,int limit) throws RadowException{
		String userid = SysManagerUtils.getUserId();
		String sql="select a.a0200 a0200_g,a.a0000 a0000_g,c.a0101 a0101_g,c.a0104 a0104_g,substr(c.a0107,1,4)||'.'||substr(c.a0107,5,2) a0107_g,(select b0101 from b01 where b.a0201b=b01.b0111) a0201b_g,b.a0215a a0215a_g "
				+ "  from HZ_MNTP_HHMD a,a02 b,a01 c "
				+ " where a.a0200=b.a0200 and a.a0000=c.a0000 and a.mdtype ='2' "
				+ " order by (select rpad(b01.b0269, 25, '.') || lpad(b.a0225, 25, '0') from  b01 where b01.b0111=b.a0201b)";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("deletepel")
	public int deletepel(String str) throws RadowException{
		String[] arr=str.split("##");
		String sql="delete from HZ_MNTP_HHMD where a0200='"+arr[0]+"' and mdtype='"+arr[1]+"' ";
		HBSession sess = HBUtil.getHBSession();
		Statement stmt;
		try {
			stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if("1".equals(arr[1])) {
			this.setNextEventName("blackGrid.dogridquery");
		}
		if("2".equals(arr[1])) {
			this.setNextEventName("grayGrid.dogridquery");
		}
		return EventRtnType.NORMAL_SUCCESS;		
	}

}
