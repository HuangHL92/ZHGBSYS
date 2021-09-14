package com.insigma.siis.local.pagemodel.sysorg.org;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class XzjgSysOrgPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return 0;
	}
	public static List<HashMap<String, Object>> querybyid_Bzzs(String UNITID) throws Exception{
		CommQuery cq = new CommQuery();
		UNITID = UNITID.replace("[", "'");
		UNITID = UNITID.replace("]", "'");
		UNITID = UNITID.replace(",", "','");
		String sql = "select b.b0101 b0101," + 
				"               j.spw_num spwnum," + 
				"               j.update_time updatetime," + 
				"               h.*," + 
				"               (h.s10 + h.s11 + h.s14 + h.s17) sum1," + 
				"               (h.s20 + h.s21 + h.s22 + h.s23 + h.s25 + h.s26) sum2," + 
				"               (h.s28 + h.s29 + h.s30 + h.s31 + h.s32 + h.s33 + h.s34) sum3," + 
				"               (h.s28 + h.s29 + h.s30) sum4," + 
				"               (h.s31 + h.s32) sum5" + 
				"          from gzdba.hdsy_table h" + 
				"          join b01 b on h.unitid = b.unitid" + 
				"          join gzdba.jgsy_common j on b.unitid = j.unitid" + 
				"         where b.unitid in ("+UNITID+")"; 
		List<HashMap<String, Object>> listCode = cq.getListBySQL(sql);
		return listCode;
		
	}
}
