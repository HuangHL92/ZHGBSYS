package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeDownload;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.ImpProcess;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
/**
 * 码表内容树
 * @author huangc
 *
 */
public class CodeValueSortPageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	
	@Override
	public int doInit() throws RadowException {
		String subCodeValue=this.getRadow_parent_data();
		String a[]=subCodeValue.split(",");
		this.getPageElement("subCodeValue").setValue(a[1]);
		this.getPageElement("codetype").setValue(a[0]);
		this.getExecuteSG().addExecuteCode("InitGrid()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * @$Comment 查询获得汇总单位下的单位
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("gridCodevalue.dogridquery")
	public int querygridB01(int start,int limit) throws RadowException{
		String subCodeValue= this.getPageElement("subCodeValue").getValue();
		String codetype= this.getPageElement("codetype").getValue();
		
		Session sess = HBUtil.getHBSession().getSession();
		String sql="select code_value codevalue,code_name codename from code_value where code_type='"+codetype+"' and sub_code_value='"+subCodeValue+"' and code_status='1' order by inino,code_value";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	
	@PageEvent("closeBtn.onclick")
	public int closeBtn() throws RadowException {
		this.closeCueWindow("CodeValueCueSort");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveBtn.onclick")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int Save() throws RadowException, AppException {
		Session sess = HBUtil.getHBSession().getSession();
		String codetype= this.getPageElement("codetype").getValue();
		for (int i = 0; i < this.getPageElement("gridCodevalue").getValueList().size(); i++) {
			HashMap<String, Object> map = this.getPageElement("gridCodevalue").getValueList().get(i);
				String codevalue = map.get("codevalue").toString();
				CodeValue cv = (CodeValue) sess.createQuery(" from CodeValue where codeValue='"+codevalue+"' and codeType='" + codetype+ "'").list().get(0);
				cv.setInino(i+1);
				sess.update(cv);
		}
		sess.flush();
		this.closeCueWindow("CodeValueCueSort");
		this.getExecuteSG().addExecuteCode("window.parent.parent.checkNode('"+codetype+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
