package com.insigma.siis.local.pagemodel.edu;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.EduXrdx;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.entity.Train;
import com.insigma.siis.local.business.entity.TrainAtt;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;

public class pxxxListPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("editgrid.dogridquery")
	@NoRequiredValidate         
	public int grid1Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String listtype=this.getPageElement("listtype").getValue();
		String extrasql="";
		if("1".equals(listtype)) {
			extrasql=" and r.a0000 in (select a01.a0000 from a02,b01,a01 where a01.a0000=a02.a0000  and instr(a01.a0165,'02')<=0  and instr(a01.a0165,'05')<=0 and a01.a0163='1' and b01.b0111=a02.a0201b and b0111 like '001.001.004%' and length(b0111)=19 and b0131 in ('1001','1004') and a0279='1' and a0281='true') ";
		}
		String sql="select r.a0000," + 
				"       (select a0101 from a01 where a01.a0000 = r.a0000) a0101," + 
				"       (select a0184 from a01 where a01.a0000 = r.a0000) a0184," + 
				"       (select a3707c from a37 where a37.a0000 = r.a0000) a3707c," + 
				"       (select b0101" + 
				"          from b01, a02" + 
				"         where a02.a0000 = r.a0000" + 
				"           and b01.b0111 = a02.a0201b" + 
				"           and a0279 = '1'" + 
				"           and a0281 = 'true') b0101," + 
				"       (select to_char(wm_concat(code_name))" + 
				"          from (select * from code_value order by inino), a01" + 
				"         where code_type = 'ZB130'" + 
				"           and a01.a0000 = r.a0000" + 
				"           and a01.a0165 like '%' || code_value || '%') a0165," + 
				"       (select code_name" + 
				"          from code_value, a01" + 
				"         where a01.a0000 = r.a0000" + 
				"           and a01.a0104 = code_value" + 
				"           and code_type = 'GB2261') a0104," + 
				"       (select substr(a0107, 0, 4) || '.' || substr(a0107, 5, 2)" + 
				"          from a01" + 
				"         where a01.a0000 = r.a0000) a0107," + 
				"       (select decode(substr(a01.a0288, 0, 4) || '.' ||" + 
				"                      substr(a01.a0288, 5, 2)," + 
				"                      '.'," + 
				"                      null," + 
				"                      substr(a01.a0288, 0, 4) || '.' ||" + 
				"                      substr(a01.a0288, 5, 2))" + 
				"          from a01" + 
				"         where a01.a0000 = r.a0000) a0288," + 
				"       (select a0192a from a01 where a01.a0000 = r.a0000) a0192a," + 
				"       t.xrdx00," + 
				"       xrdx01," + 
				"       (select code_name" + 
				"          from code_value" + 
				"         where code_type = 'PXLX'" + 
				"           and code_value = t.xrdx02) xrdx02," + 
				"       (select code_name" + 
				"          from code_value" + 
				"         where code_type = 'TRANORG'" + 
				"           and code_value = t.xrdx03) xrdx03," + 
				"       (select code_name" + 
				"          from code_value" + 
				"         where code_type = 'TRANCRJ'" + 
				"           and code_value = t.xrdx04) xrdx04," + 
				"       substr(t.xrdx05, 0, 4) || '.' || substr(t.xrdx05, 5, 2) || '.' ||" + 
				"       substr(t.xrdx05, 7, 2) xrdx05," + 
				"       substr(t.xrdx06, 0, 4) || '.' || substr(t.xrdx06, 5, 2) || '.' ||" + 
				"       substr(t.xrdx06, 7, 2) xrdx06," + 
				"       xrdx08," + 
				"       xrdx09,year," + 
				"       xrdx10" + 
				"  from edu_xrdx t, edu_xrdx_ry r" + 
				" where r.a0000 in (select a0000 from a01 where a0163 = '1')" + 
				"   and t.xrdx00 = r.xrdx00 "+extrasql+" " + 
				" order by ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
				"              from (select a02.a0000," + 
				"                           b0269," + 
				"                           a0225," + 
				"                           row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
				"                      from a02, b01" + 
				"                     where a02.a0201b = b01.b0111" + 
				"                       and a0281 = 'true') t" + 
				"             where rn = 1" + 
				"               and t.a0000 = r.a0000)),xrdx05 desc" ;
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	


}