package com.insigma.siis.local.pagemodel.fxyp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A02Zwzc;
import com.insigma.siis.local.business.entity.A02ZwzcMx;
import com.insigma.siis.local.business.entity.Gbkh;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class MNMDADDPageModel extends PageModel {
	

	@Override
	public int doInit() throws RadowException {
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 指标主信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("a01Grid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		//定义用来组装sql的变量
		String isContain = this.getPageElement("isContain").getValue();
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer str = new StringBuffer();
		if(checkedgroupid==null || "".equals(checkedgroupid)) {
			str.append("select  a0000 a0000_1,a0101 a0101_1,a0104 a0104_1,substr(a0107,1,4)||'.'||substr(a0107,5,2) a0107_1,a0192a a0192a_1  from a01 "
					+ " order by  (select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')"
					+ "                     from (select a02.a0000,b0269,a0225,"
					+ "			row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn"
					+ "		from a02, b01 where a02.a0201b = b01.b0111 and a0281 = 'true' and a0201b like '"+checkedgroupid+"%') t "
					+ "	where rn = 1 and t.a0000 = a01.a0000)");	
		}else if(checkedgroupid.length()>0 && "1".equals(isContain)) {
			str.append("select  a0000 a0000_1,a0101 a0101_1,a0104 a0104_1,substr(a0107,1,4)||'.'||substr(a0107,5,2) a0107_1,a0192a a0192a_1  from a01 where a0000 in (select a02.a0000 from a02 where a0201b  like '"+checkedgroupid+"%') "
					+ " order by  (select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')"
					+ "                     from (select a02.a0000,b0269,a0225,"
					+ "			row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn"
					+ "		from a02, b01 where a02.a0201b = b01.b0111 and a0281 = 'true' and a0201b like '"+checkedgroupid+"%') t "
					+ "	where rn = 1 and t.a0000 = a01.a0000)");	
		}else {
			str.append("select  a0000 a0000_1,a0101 a0101_1,a0104 a0104_1,substr(a0107,1,4)||'.'||substr(a0107,5,2) a0107_1,a0192a a0192a_1  from a01 where a0000 in (select a02.a0000 from a02 where a0201b='"+checkedgroupid+"') "
					+ "order by (select lpad(max(a0225), 25, '0')  from a02 where a01.a0000 = a02.a0000  and a02.a0281 = 'true' and a02.A0201B = '"+checkedgroupid+"')");	
		}
		str.append("  ");
		this.pageQuery(str.toString(), "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 指标明细信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("a02Grid.dogridquery")
	public int doLogQuery(int start,int limit) throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select a0000 a0000_2,a0200 a0200_2,(select b0101 from b01 where a02.a0201b=b01.b0111) a0201b_2,a0215a a0215a_2,"
				+ "decode((select count(1) from HZ_MNTP_HHMD b where b.a0200=a02.a0200 and b.mdtype='1'),0,'false','true') blackcheck,"
				+ "decode((select count(1) from HZ_MNTP_HHMD b where b.a0200=a02.a0200 and b.mdtype='2'),0,'false','true') graycheck"
				+ "  from a02 where a0000='"+a0000+"' order by a0223 ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 刷新
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("a01Grid.rowclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{  //打开窗口的实例
		String a0000 = (String)this.getPageElement("a01Grid").getValueList().get(this.getPageElement("a01Grid").getCueRowIndex()).get("a0000_1");
		this.getPageElement("a0000").setValue(a0000);
		this.setNextEventName("a02Grid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("updateHHMD")
	@GridDataRange
	public int updateHHMD(String str) throws RadowException{  
		String userid = SysManagerUtils.getUserId();
		String[] arr=str.split("##");
		String sql="";
		if("insert".equals(arr[1])) {
			sql="insert into HZ_MNTP_HHMD (a0200,a0000,mdtype,userid) select a0200,a0000,'"+arr[2]+"','"+userid+"' from a02 where a0200='"+arr[0]+"' ";
		}else {
			sql="delete from HZ_MNTP_HHMD where a0200='"+arr[0]+"' and mdtype='"+arr[2]+"' ";
		}
		HBSession sess = HBUtil.getHBSession();
		Statement stmt;
		try {
			stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
}