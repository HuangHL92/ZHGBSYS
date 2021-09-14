package com.insigma.siis.local.pagemodel.comm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Session;

import com.insigma.odin.framework.RSACoder;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Infotdrecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class InfoToDoQPageModel extends PageModel{
	/**
	 * 初始化
	 */
	@Override
	public int doInit() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		try {
			String sql="select itdc001,itdc002 from infotodocfg order by itdc001";
			List<Object[]> list=sess.createSQLQuery(sql).list();
			HashMap<String,String> map= new LinkedHashMap<String,String>();
			if(list!=null&&list.size()>0){
				for(Object[] obj:list){
					String TABLE_CODE = obj[0].toString();
					String TABLE_NAME = obj[1].toString();
					map.put(TABLE_CODE, TABLE_NAME);
				}
			}
			((Combo)this.getPageElement("qtype")).setValueListForSelect(map);
			this.setNextEventName("memberGrid.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String deptid = user.getDept();
		String qtype = this.getPageElement("qtype").getValue();
		String itdr003 = this.getPageElement("itdr003").getValue();
		String where = "";
		if(qtype!=null&&!"".equals(qtype)){
			where += " and r.itdr004 = '"+qtype+"'";
		}
		if(itdr003!=null&&!"".equals(itdr003)){
			where += " and to_char(r.itdr003,'yyyymmdd') = '"+itdr003+"'";
		}
		String sql = "select * from infotodorecord r,infotodocfg c where r.ITDR004=c.itdc001 "
				+ " and ITDR007='"+deptid +"' "+ where +" order by ITDR003 desc";
		this.pageQuery(sql, "SQL", start, limit);
		
		return EventRtnType.SPE_SUCCESS;
	}
}
