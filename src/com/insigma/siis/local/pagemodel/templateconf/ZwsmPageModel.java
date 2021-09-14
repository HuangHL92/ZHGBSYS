package com.insigma.siis.local.pagemodel.templateconf;

import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.PositionExplain;

public class ZwsmPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return  EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX()throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String id = this.getPageElement("id").getValue();
		if(!"kong".equals(id)){
			String sql="select * from position_explain where id='"+id+"'";
			List<Object[]> list = session.createSQLQuery(sql).list();
			Object[] obj=list.get(0);
			String huixian="";
			for (Object object : obj) {
				if(object==null){
					object="";
				}
				//System.out.print(object+",");
				huixian+=object+"@";
			}
			huixian=huixian.substring(0, huixian.length()-1);
			String huixian1 = huixian;
			String[] paramArr=huixian1.split("@");
			String xb=paramArr[8];
			String xl=paramArr[9];
			String zy=paramArr[10];
			String zc=paramArr[11];
			String dp=paramArr[12];
			String dy=paramArr[13];
			String nlyq=paramArr[17];
			this.getPageElement("xb").setValue(xb);
			this.getPageElement("xl").setValue(xl);
			this.getPageElement("zy").setValue(zy);
			this.getPageElement("zc").setValue(zc);
			this.getPageElement("dp").setValue(dp);
			this.getPageElement("nlyq").setValue(nlyq);
			this.getPageElement("dy").setValue(dy);
			this.getExecuteSG().addExecuteCode("huixian('"+huixian+"');");
			
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("saveData")
	public int saveData(String param)throws  RadowException{
		String[] split = param.split("@");
		/*var param=xh+","+zwmc+","+zwfg+","+zgtj+","+pbzk+","+age+","+xb+","+xl+","+zy+","+nc+","+nlyq+","
				+dp+","+dy+","+csfg+","+level+","+lb;*/
		System.out.println(param);
		String xhhh=split[0];
		String zwmc=split[1];
		String zwfg=split[2];
		String zgtj=split[3];
		String pbzk=split[4];
		String age=split[5];
		String xb=split[6];
		String xl=split[7];
		String zy=split[8];
		String zc=split[9];
		String nlyq=split[10];
		String dp=split[11];
		String dy=split[12];
		String csfg=split[13];
		String level=split[14];
		String lb=split[15];
		String dw=split[16];
		String id=split[17];
		String condition=split[18];
		
		
		
		PositionExplain positionExplain = new PositionExplain();
		if(id != null && !"kong".equals(id)){
			positionExplain.setId(id);
		}
		positionExplain.setOrderNumber(Integer.parseInt(xhhh));
		positionExplain.setUnit(dw);
		positionExplain.setPositionName(zwmc);
		positionExplain.setPositionDivisionOfLabor(zwfg);
		positionExplain.setQualificationCondition(zgtj);
		positionExplain.setEquipState(pbzk);
		positionExplain.setAge(age);
		positionExplain.setGender(xb);
		positionExplain.setEducation(xl);
		positionExplain.setMajor(zy);
		positionExplain.setJobNamed(zc);
		positionExplain.setPoliticalParty(dp);
		positionExplain.setTerritory(dy);
		positionExplain.setWorkOn(csfg);
		positionExplain.setJobLevel(level);
		positionExplain.setClasses(lb);
		positionExplain.setAbilityRequired(nlyq);
		positionExplain.setConditionScreen(condition);
		
		HBSession session = HBUtil.getHBSession();
		session.saveOrUpdate(positionExplain);
		session.flush();
		closeCueWindowEX();
		this.getExecuteSG().addExecuteCode("realParent.refresh2();");
		
		return  EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("close")
	public void closeCueWindowEX(){
		this.getExecuteSG().addExecuteCode("window.parent.Ext.WindowMgr.getActive().close();");
	}

}
