package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_003;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Transaction;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.CodeType;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class NewCodeValueCuePageModel extends PageModel{
	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	
	@Override
	public int doInit() throws RadowException {
		String msg = this.getRadow_parent_data();                        //��ȡ�ڸ�ҳ�汣���data
		String[] msgs = msg.split("_");
		String mode = msgs[0];
		String codeType = msgs[1];
		String node = msgs[2];
	    if("UPDATE".equals(mode)){
	    	CodeValue cv = bs6.getCodeValueByCodeTypeAndId(codeType, node);
	    	this.getPageElement("codeName").setValue(cv.getCodeName());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("close")
	@NoRequiredValidate
	public int close() throws RadowException {
		String msg = this.getRadow_parent_data();                        //��ȡ�ڸ�ҳ�汣���data
		String[] msgs = msg.split("_");
		String mode = msgs[0];
		if("NEW".equals(mode)) {  
			this.closeCueWindow("AddCodeValueCue");
		} else if("UPDATE".equals(mode)){
			this.closeCueWindow("ModifyCodeValueCue");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * �������
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("save.onclick")
	public int save() throws RadowException {
		String msg = this.getRadow_parent_data();                        //��ȡ�ڸ�ҳ�汣���data
		String[] msgs = msg.split("_");
		String mode = msgs[0];
		String codeType = msgs[1];
		String node = msgs[2];
		CodeValue cv = new CodeValue();
		String codeName = this.getPageElement("codeName").getValue();
		ChineseSpelling kit = new ChineseSpelling();
		if("NEW".equals(mode)) {                                        //�����������չ��
			cv.setCodeLeaf("1");
			cv.setCodeName(codeName);
			cv.setCodeName2(codeName);
			cv.setCodeName3(codeName);
			cv.setCodeSpelling(kit.getPYString(codeName));
			cv.setCodeStatus("1");                                    //Ĭ��Ϊ��ʾ
			cv.setCodeType(codeType);
			String codeValueId="";
			if("ROOT".equals(node)) {
				codeValueId = bs6.generateRootCodeValue(codeType);
				cv.setCodeValue(codeValueId);
				cv.setSubCodeValue("-1");
			} else {
				codeValueId = bs6.generateCodeValueId(codeType, node);
				cv.setCodeValue(codeValueId);
				cv.setSubCodeValue(node);
			}
			Integer seq = bs6.getMaxCodeValueSeq()+1;
			cv.setCodeValueSeq(seq);
			cv.setIscustomize("1");                                   //Ĭ��Ϊ���Զ�����
			Transaction t = bs6.getSession().getTransaction();
			t.begin();
			bs6.getSession().save(cv);
			t.commit();
			//��¼��־
			try {
				new LogUtil().createLog("82", "CODEVALUE", String.valueOf(cv.getCodeValueSeq()), cv.getCodeName(), "�½�������", new Map2Temp().getLogInfo(new CodeValue(),cv));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			//�������ڵ���ӵ�����ȥ
			this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
			this.closeCueWindow("AddCodeValueCue");
		} else if("UPDATE".equals(mode)) {
			cv = bs6.getCodeValueByCodeTypeAndId(codeType, node);
			
			//���ԭ���ݵĶ���
			CodeValue cv_old = new CodeValue();
			try {
				PropertyUtils.copyProperties(cv_old, cv);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			
			cv.setCodeName(codeName);
			cv.setCodeName2(codeName);
			cv.setCodeName3(codeName);
			cv.setCodeSpelling(kit.getPYString(codeName));
			Transaction t = bs6.getSession().getTransaction();
			t.begin();
			bs6.getSession().save(cv);
			t.commit();
			
			//��¼��־
			try {
				new LogUtil().createLog("83", "CODEVALUE", String.valueOf(cv.getCodeValueSeq()), cv.getCodeName(), "�޸Ĵ�����", new Map2Temp().getLogInfo(cv_old,cv));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//�������ڵ���ӵ�����ȥ
			this.getExecuteSG().addExecuteCode("window.parent.modifyNode('"+node+"','"+codeName+"')");
			this.closeCueWindow("ModifyCodeValueCue");
		}
		this.getExecuteSG().addExecuteCode("window.parent.clearNodeId()");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
