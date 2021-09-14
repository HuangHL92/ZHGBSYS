package com.insigma.siis.local.pagemodel.zwzc;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Gbkc;
import com.insigma.siis.local.business.entity.Supervision;
import com.insigma.siis.local.business.entity.WJGL;
import com.insigma.siis.local.business.entity.WJGLAdd;
import com.insigma.siis.local.business.entity.YearCheck;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class CJWJGLAddPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
				
		this.setNextEventName("initx");
		return EventRtnType.NORMAL_SUCCESS;
	}
     
	@PageEvent("initx")
	@NoRequiredValidate
	public int initx() throws Exception{	
		Calendar  c = new  GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<100;i++){
			map.put(""+(year-i), year-i);
			if((year-i)==2017) {
				break;
			}
		}
		((Combo)this.getPageElement("year")).setValueListForSelect(map); 
		String now=String.valueOf(year);
		this.getPageElement("year").setValue(now); 

		this.getPageElement("xfMainOid").setValue(UUID.randomUUID().toString().replaceAll("-", ""));		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 关闭
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	/*@PageEvent("close")
	public int close() throws RadowException, Exception {
				
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('grid1').store.reload();");
		this.closeCueWindow("Check");
		return EventRtnType.NORMAL_SUCCESS;		
	}*/
	
	
	/**
	 * 保存
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("save")
	public int save() throws RadowException, Exception {
		HBSession sess = HBUtil.getHBSession();
	
		sess.flush();

		//reloadCustomQuery();
		this.setNextEventName("insert");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("insert")
	public int insert() throws RadowException, Exception {
		HBSession sess = HBUtil.getHBSession();
		String year=this.getPageElement("year").getValue();
		String wj02=this.getPageElement("wj02").getValue();
		String a0000=this.getPageElement("a0000").getValue();
		String b0111=this.getPageElement("b0111").getValue();
		String a0101="";
		//正职核定职数
		@SuppressWarnings("unchecked")
		List<String> a0101s= HBUtil.getHBSession().createSQLQuery("select a0101 from a01 where a0000='"+a0000+"'").list();
		if(a0101s.size()>0) {
			a0101=a0101s.get(0);
		}
//		YearCheck yearcheck =  new YearCheck();
		String orifileid = this.getPageElement("orifileid").getValue();
		WJGLAdd wjgladd=(WJGLAdd)sess.get(WJGLAdd.class,orifileid);
		if(wjgladd==null) {
			this.setMainMessage("保存失败");
			//reloadCustomQuery();

			return EventRtnType.NORMAL_SUCCESS;
		}
		WJGL wjgl=new WJGL();
		wjgl.setWj00(UUID.randomUUID().toString().replaceAll("-", ""));
		wjgl.setAdd00(orifileid);
		wjgl.setType("2");
		wjgl.setWj01(a0101);
		wjgl.setA0000(a0000);
		wjgl.setB0111(b0111);
		wjgl.setYear(year);
		wjgl.setWj02(wj02);
		wjgl.setWj03(wjgladd.getFilename());
		wjgl.setWj04(wjgladd.getFilesize());
		wjgl.setWj05(wjgladd.getFileurl());
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String wj06 = sf.format(System.currentTimeMillis());
		wjgl.setWj06(wj06);
		sess.save(wjgl);		
		sess.flush();
		//this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('grid1').store.reload();");
		//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('grid1.dogridquery')");
		//this.setMainMessage("保存成功！");
		
		
		this.getExecuteSG().addExecuteCode("saveCallBack('保存成功');");
		//reloadCustomQuery();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
