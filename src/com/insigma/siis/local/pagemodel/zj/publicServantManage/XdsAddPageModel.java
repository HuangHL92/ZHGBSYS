package com.insigma.siis.local.pagemodel.zj.publicServantManage;

import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A99Z1;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * 选调生
 * @author huyl
 *
 */
public class XdsAddPageModel extends PageModel{

	
	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public  int  initX() throws RadowException{
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		this.getExecuteSG().addExecuteCode("document.getElementById('a0000').value='"+a0000+"';");
		if (a0000 == null || "".equals(a0000)) {//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql = "from A99Z1 where a0000='" + a0000+ "'";
			List<A99Z1> list = sess.createQuery(sql).list();
			A99Z1 a99Z1 = null;
			if(list != null && list.size()>0){
				a99Z1 =  list.get(0);
			}

			if (a99Z1 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a99Z1, this);
				if(a99Z1.getXxmc()!=null && !"".equals(a99Z1.getXxmc())){
					this.getPageElement("xxmc_combo").setValue(a99Z1.getXxmc());
				}else{
					this.getPageElement("xxmc_combo").setValue("");
				}
				
				if(a99Z1.getZy()!=null && !"".equals(a99Z1.getZy())){
					this.getPageElement("zy_combo").setValue(a99Z1.getZy());
				}else{
					this.getPageElement("zy_combo").setValue("");
				}	
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("setDisabled();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveA99Z1Xds() throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		String a99z100=this.getPageElement("a99Z100").getValue();
		A99Z1 a99z1 = (A99Z1)sess.get(A99Z1.class, a99z100);
		if(a99z1!=null){
			String a99z103=this.getPageElement("a99z103").getValue();
			String a99z103name="是";
			if("1".equals(a99z103)){
				String xdsgwcj=this.getPageElement("xdsgwcj").getValue();
				String dbjjggzsj=this.getPageElement("dbjjggzsj").getValue();
				String a99z104=this.getPageElement("a99z104").getValue();
				String a99zxdslb=this.getPageElement("a99zxdslb").getValue();
				String xdsdwjzw=this.getPageElement("xdsdwjzw").getValue();
				String xdsly=this.getPageElement("xdsly").getValue();
				String xxmc=this.getPageElement("xxmc_combo").getValue();
				String xxmccode=this.getPageElement("xxmc").getValue();
				String zy=this.getPageElement("zy_combo").getValue();
				String zycode=this.getPageElement("zy").getValue();
				String dsjggzsj=this.getPageElement("dsjggzsj").getValue();
				String jcgzjlsj=this.getPageElement("jcgzjlsj").getValue();
				a99z1.setA99z103(a99z103);
				/*
				 * a99z1.setA99zxdslb(a99zxdslb); a99z1.setDbjjggzsj(dbjjggzsj);
				 * a99z1.setXdsdwjzw(xdsdwjzw); a99z1.setXdsgwcj(xdsgwcj);
				 * a99z1.setXdsly(xdsly); a99z1.setXxmc(xxmc); a99z1.setXxmccode(xxmccode);
				 * a99z1.setZy(zy); a99z1.setZycode(zycode); a99z1.setDsjggzsj(dsjggzsj);
				 * a99z1.setJcgzjlsj(jcgzjlsj);
				 */
				a99z1.setA99z104(a99z104);
			}else{
				a99z103name="";
				a99z1.setA99z103(a99z103);
				/*
				 * a99z1.setA99zxdslb(""); a99z1.setDbjjggzsj(""); a99z1.setA99z104("");
				 * a99z1.setXdsdwjzw(""); a99z1.setXdsgwcj(""); a99z1.setXdsly("");
				 * a99z1.setXxmc(""); a99z1.setXxmccode(""); a99z1.setZy("");
				 * a99z1.setZycode(""); a99z1.setDsjggzsj(""); a99z1.setJcgzjlsj("");
				 */
			}
			sess.saveOrUpdate(a99z1);
			sess.flush();
			//页面设置 是否选调生
			this.getExecuteSG().addExecuteCode("realParent.setA99z103Value('"+a99z103+"','"+a99z103name+"')");
			this.getExecuteSG().addExecuteCode("odin.info('保存成功！')");
		}else{
			this.setMainMessage("请先保存人员基本信息！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
