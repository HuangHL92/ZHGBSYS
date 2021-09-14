package com.insigma.siis.local.pagemodel.sysorg.org;

import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Jggwconf;
import com.insigma.siis.local.business.entity.KnowField;

public class GwCfgInfoPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
				
		this.setNextEventName("initx");
		return EventRtnType.NORMAL_SUCCESS;
	}
     
	@PageEvent("initx")
	@NoRequiredValidate
	public int initx() throws Exception{
		String ids = this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess = HBUtil.getHBSession();
		if(ids!=null && !ids.equals("")) {
			if(ids.contains(",")) {
				String arr[] = ids.split(",");
				Jggwconf conf = (Jggwconf) sess.get(Jggwconf.class, arr[1]);
				if(conf.getA0219()==null || conf.getA0219().equals("")) {
					conf.setA0219("0");
				}
				if(conf.getA0201d()==null || conf.getA0201d().equals("")) {
					conf.setA0201d("0");
				}
				this.copyObjValueToElement(conf, this);
				
				List<KnowField> field = (List<KnowField>) sess.createQuery("from KnowField where jggwconfid = '"+conf.getJggwconfid()+"'").list();
				if(field!=null&&field.size()==1){
					KnowField f = field.get(0);
					this.copyObjValueToElement(f, this);
				}
			} else {
				B01 b01 = (B01) sess.get(B01.class, ids);
				this.getPageElement("b0111").setValue(b01.getB0111());
				this.getPageElement("b0101").setValue(b01.getB0101());
				this.getPageElement("b01id").setValue(HBUtil.getValueFromTab("b01id", "b01", "b0111='"+ids+"'"));
			}
			this.getExecuteSG().addExecuteCode("setA0201eDisabled();");
		} else {
			this.getExecuteSG().addExecuteCode("alert('信息异常');window.close();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 保存
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("save.onclick")
	@Transaction
	public int save() throws RadowException, Exception {
		HBSession sess = HBUtil.getHBSession();
		Jggwconf conf = new Jggwconf();
		try {
			String gwname = this.getPageElement("gwcode_combo").getValue();
			this.copyElementsValueToObj(conf, this);
			if(conf.getA0201d()!=null && conf.getA0201d().equals("1")) {
				String a0201e = this.getPageElement("a0201e").getValue();
				if(a0201e==null || a0201e.equals("") || a0201e.equals("null")) {
					this.setMainMessage("<b>请先修正以下问题后再进行本操作：</b><br/>" + 
							"成员类别不能为空！");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			conf.setGwname(gwname);
			if(conf.getJggwconfid()!=null && !conf.getJggwconfid().equals("")) {
				sess.update(conf);
			} else {
				sess.save(conf);
			}
			sess.flush();
			
			saveKnowField(sess,conf);
			this.getExecuteSG().addExecuteCode("Ext.MessageBox.alert('消息提示', '保存成功！', function(e){ if ('ok' == e){parent.Ext.getCmp('gwcfginfo').close();realParent.refresh();}});");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存数据异常！");
			this.setMessageType(EventMessageType.ERROR);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int saveKnowField(HBSession sess,Jggwconf conf) throws RadowException, Exception {
		KnowField field = new KnowField();
		try {
			this.copyElementsValueToObj(field, this);
			
			field.setJggwconfid(conf.getJggwconfid());
			if(field.getId()!=null && !field.getId().equals("")) {
				sess.update(field);
			} else {
				sess.save(field);
			}
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
			this.setMessageType(EventMessageType.ERROR);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("isExists")
	@NoRequiredValidate
	public int isExists(String code) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String b0111 = this.getPageElement("b0111").getValue();
		String jggwconfid = this.getPageElement("jggwconfid").getValue();
		try {
			List<Jggwconf> list = sess.createQuery(" from Jggwconf where b0111='"+
					b0111+"' and gwcode='"+code+"'").list();
			if(list!=null && list.size()>0) {
				if(jggwconfid==null || jggwconfid.equals("")) {
					this.getPageElement("gwcode_combo").setValue("");
					this.getPageElement("gwcode").setValue("");
					this.setMainMessage("该职位已存在，不能再次新增！");
					this.setMessageType(EventMessageType.ERROR);
				} else {
					Jggwconf jggwconf = list.get(0);
					if(!jggwconf.getJggwconfid().equals(jggwconfid)) {
						Jggwconf conf = (Jggwconf) sess.get(Jggwconf.class, jggwconfid);
						this.getPageElement("gwcode_combo").setValue(conf.getGwname());
						this.getPageElement("gwcode").setValue(conf.getGwcode());
						this.setMainMessage("该职位已存在，请选择其他职位！");
						this.setMessageType(EventMessageType.ERROR);
						
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询数据异常！");
			this.setMessageType(EventMessageType.ERROR);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
