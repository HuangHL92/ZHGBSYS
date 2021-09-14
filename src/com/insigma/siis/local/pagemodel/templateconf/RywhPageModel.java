package com.insigma.siis.local.pagemodel.templateconf;

import java.util.List;

import com.fr.third.org.apache.poi.hssf.model.Model;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.ModelConfig;
import com.insigma.siis.local.business.entity.NaturalStructure;

public class RywhPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		//this.setNextEventName("huixian");
		/*String aaa =this.getPageElement("sql").getValue();
		System.out.println("=================="+aaa);*/
		// TODO Auto-generated method stub
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public void closeCueWindowEX(){
		this.getExecuteSG().addExecuteCode("window.parent.Ext.WindowMgr.getActive().close();");
	}
	@PageEvent("save")
	public int save(String ryid) throws RadowException{
		String zc = this.getPageElement("zc").getValue();
		String dy = this.getPageElement("dy").getValue();
		String nel = this.getPageElement("nel").getValue();
		String jy = this.getPageElement("jy").getValue();
		String jl = this.getPageElement("jl").getValue();
		String ly = this.getPageElement("ly").getValue();
		String rqz = this.getPageElement("rqz").getValue();
		ModelConfig modeconfig = new ModelConfig();
		 modeconfig.setExpertise(zc);
		 modeconfig.setRegion(dy);
		 modeconfig.setAbility(nel);
		 modeconfig.setExperience(jy);
		 modeconfig.setUndergo(jl);
		 modeconfig.setSource(ly);
		 modeconfig.setTenuresystem(rqz);
		 if(ryid!=null&&!"".equals(ryid)){
			 modeconfig.setId(ryid); 
		 }
		 HBSession sess = HBUtil.getHBSession();
		 sess.saveOrUpdate(modeconfig);
		 sess.flush();
		    this.closeCueWindowEX();
		   this.getExecuteSG().addExecuteCode("realParent.refresh4()");
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("huixian")
	public int huixian(String ryid) throws RadowException{
		//String ryid =this.getPageElement("ryid").getValue();
		HBSession sess = HBUtil.getHBSession();
		String sql="select * from modelconfig where id='"+ryid+"'";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		if(list.size()>0&&list.get(0)!=null){
			Object[] obj=list.get(0);
			System.out.println("==============="+obj);
			String zc =(String) obj[1];
			String dy =(String) obj[2];
			String nel =(String) obj[3];
			String jy =(String) obj[4];
			String jl =(String) obj[5];
			String ly =(String) obj[6];
			String rqz =(String) obj[7];
			this.getPageElement("zc").setValue(zc);
			this.getPageElement("dy").setValue(dy);
			this.getPageElement("nel").setValue(nel);
			this.getPageElement("jy").setValue(jy);
			this.getPageElement("jl").setValue(jl);
			this.getPageElement("ly").setValue(ly);
			this.getPageElement("rqz").setValue(rqz);
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
}
