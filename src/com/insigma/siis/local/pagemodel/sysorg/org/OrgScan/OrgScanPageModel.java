package com.insigma.siis.local.pagemodel.sysorg.org.OrgScan;

import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;

public class OrgScanPageModel extends PageModel{

	public OrgScanPageModel() {
	}

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("init");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("init")
	public int init() throws RadowException{
		String id=this.getPageElement("subWinIdBussessId2").getValue();;
		B01 b01 = new B01();
		String userID = SysManagerUtils.getUserId();
		Map<String, String> map = PublicWindowPageModel.isHasRule(id, userID);
		if (!map.isEmpty()||map==null) {
			if ("2".equals(map.get("type"))){
				this.setMainMessage("您没有该机构的权限");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
			try {
				SysOrgBS.selectCountById(id);
				b01 = SysOrgBS.LoadB01(id);
				//获取上级机构名称与上级机构编码
				B01 b01up = (B01) HBUtil.getHBSession().get(B01.class, b01.getB0121());
				if(b01up!=null){
					this.getPageElement("optionGroup").setValue(b01up.getB0101());//上级机构名称
					this.getPageElement("parentb0114").setValue(b01up.getB0114());//上级机构编码
				}
//				if(!b01.getB0194().equals("3")){
//					//查看是否显示参公 b0238-参照公务员法管理审批时间
//					ResultSet rsInfo = HBUtil.getHBSession().connection().prepareStatement("select b0238 from b01 where b0111='"+id+"'").executeQuery();
//					while(rsInfo.next()){
//						if(rsInfo.getString(1)!=null && rsInfo.getString(1).length()>5){
//							this.getExecuteSG().addExecuteCode("$('.tr3').show();");
//						}else{
//							this.getExecuteSG().addExecuteCode("$('.tr3').hide();");
//						}
//					}
//					
//				}
			
				
				PMPropertyCopyUtil.copyObjValueToElement(b01, this);
				if(b01.getB0117()!=null){//机构所在政区 ZB01
					this.getExecuteSG().addExecuteCode("odin.setSelectValue('b0117','"+b01.getB0117()+"');");
				}else{
					this.getPageElement("b0117").setValue("");
					this.getPageElement("b0117_combo").setValue("");
				}
				if(b01.getB0124()!=null){//单位隶属关系 ZB87
					this.getExecuteSG().addExecuteCode("odin.setSelectValue('b0124','"+b01.getB0124()+"');");
				}else{
					this.getPageElement("b0124").setValue("");
					this.getPageElement("b0124_combo").setValue("");
				}
				if(b01.getB0127()!=null){//机构级别 ZB03   等
					this.getExecuteSG().addExecuteCode("odin.setSelectValue('b0127','"+b01.getB0127()+"');");
				}else{
					this.getPageElement("b0127").setValue("");
					this.getPageElement("b0127_combo").setValue("");
				}
				if(b01.getB0131()!=null){//机构性质类别 ZB04 
					this.getExecuteSG().addExecuteCode("odin.setSelectValue('b0131','"+b01.getB0131()+"');");
				}else{
					this.getPageElement("b0131").setValue("");
					this.getPageElement("b0131_combo").setValue("");
				}
				readInfo_Extend(id);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RadowException(e.getMessage());
			}
			this.getExecuteSG().addExecuteCode("myfunction(2,"+b01.getB0194()+");");
			//显示备注信息
			String b0180 = b01.getB0180();
			if(b0180==null || "".equals(b0180.trim())) {//备注   少
				this.getPageElement("b0180").setValue("");
			}else {
				this.getPageElement("b0180").setValue(b0180.trim());
			}
			//根据机构类型显示和隐藏机构类别
			try {
				if(b01.getB0194().trim().equals("1")){//法人
					this.getExecuteSG().addExecuteCode("$('.type11').show();");
					this.getExecuteSG().addExecuteCode("$('.type22').hide();");
					this.getExecuteSG().addExecuteCode("$('.type33').hide();");
				}else if(b01.getB0194().trim().equals("2")){//内设
					this.getPageElement("b0183_1").setValue((b01.getB0183()==null?0:b01.getB0183())+"");
					this.getPageElement("b0185_1").setValue((b01.getB0185()==null?0:b01.getB0185())+"");
					this.getExecuteSG().addExecuteCode("$('.type11').hide();");
					this.getExecuteSG().addExecuteCode("$('.type22').show();");
					this.getExecuteSG().addExecuteCode("$('.type33').hide();");
				}else{//分组
					this.getExecuteSG().addExecuteCode("$('.type11').hide();");
					this.getExecuteSG().addExecuteCode("$('.type22').hide();");
					this.getExecuteSG().addExecuteCode("$('.type33').show();");
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.setMainMessage("该机构没有机构类型！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void readInfo_Extend(String b0111) throws AppException {
		List<Map<String,String>>  Info_Extends = HBUtil.queryforlist("select * from B01_EXT where b0111='"+b0111+"'",null);
			if(Info_Extends!=null&&Info_Extends.size()>0){//
				Map<String,String> entity = Info_Extends.get(0);
				for(String key : entity.keySet()){
					try {
						this.getPageElement(key.toLowerCase()).setValue(entity.get(key));
					} catch (Exception e) {
						e.printStackTrace();
						throw new AppException(e.getMessage());
					}
				}
			}else{
				DBType cueDBType = DBUtil.getDBType();
				List<String> list =null;
				if(cueDBType==DBType.MYSQL){
					list = HBUtil.getHBSession().createSQLQuery("select COLUMN_NAME from information_schema.COLUMNS where table_name = 'b01_ext' and  column_name!='B0111' and TABLE_SCHEMA = 'ZWHZYQ'").list();
				}else{
					list = HBUtil.getHBSession().createSQLQuery("SELECT column_name FROM all_tab_cols WHERE  table_name = UPPER('b01_ext')  and  column_name!='B0111'").list();
				}
				for(int i=0;i<list.size();i++){
					try {
						this.getPageElement(list.get(i).toLowerCase()).setValue("");
					} catch (Exception e) {
						e.printStackTrace();
						throw new AppException(e.getMessage());
					}
				}
			}
	}

}
