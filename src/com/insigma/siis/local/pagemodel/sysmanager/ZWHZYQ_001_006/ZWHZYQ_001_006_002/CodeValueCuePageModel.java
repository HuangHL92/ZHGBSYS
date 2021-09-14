package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002;

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
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.CodeDownload;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class CodeValueCuePageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	
	@Override
	public int doInit() throws RadowException {
		String msg = this.getRadow_parent_data();                        //获取在父页面保存的data
		String[] msgs = msg.split("_");
		String mode = msgs[0];
		String codeType = msgs[1];
		String subCodeValueId = msgs[2];
	    if("UPDATE".equals(mode)){
	    	CodeValue cv = bs6.getCodeValueByCodeTypeAndId(codeType, subCodeValueId);
	    	this.getPageElement("codeName").setValue(cv.getCodeName());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("close")
	@NoRequiredValidate
	public int close() throws RadowException {
		String msg = this.getRadow_parent_data();                        //获取在父页面保存的data
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
	 * 新增项保存
	 * @return
	 * @throws RadowException
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@PageEvent("save.onclick")
	public int save() throws RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String msg = this.getRadow_parent_data();                        //获取在父页面保存的data
		String[] msgs = msg.split("_");
		String mode = msgs[0];
		String codeType = msgs[1];
		String subCodeValueId = msgs[2];
		CodeValue cv = new CodeValue();
		String codeName = this.getPageElement("codeName").getValue().trim();
		if(codeName.contains(" ")){
			this.setMainMessage("请不要输入空格。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		ChineseSpelling kit = new ChineseSpelling();
		if("NEW".equals(mode)) {                                        //如果是新增扩展项
			cv.setCodeLeaf("1");
			cv.setCodeName(codeName);
			cv.setCodeName2(codeName);
			cv.setCodeName3(codeName);
			cv.setCodeSpelling(kit.getPYString(codeName));
			cv.setCodeStatus("1");                                    //默认为显示
			cv.setCodeType(codeType);
			String codeValueId = bs6.generateCodeValueId(codeType, subCodeValueId);
			cv.setCodeValue(codeValueId);
			Integer seq = bs6.getMaxCodeValueSeq()+1;
			cv.setCodeValueSeq(seq);
			cv.setIscustomize("1");                                   //默认为是自定义项
			cv.setSubCodeValue(subCodeValueId);
			Integer inino = bs6.getIninoByCode(codeType) ;
			cv.setInino(inino);
			CodeDownload cd = new CodeDownload();
			cd.setCodeValueSeq(seq);
			cd.setDownloadStatus("0");
			Transaction t = bs6.getSession().getTransaction();
			t.begin();
			bs6.getSession().save(cv);
			bs6.getSession().save(cd);
			t.commit();
			try {
				new LogUtil().createLog("76", "CODEVALUE", String.valueOf(cv.getCodeValueSeq()), cv.getCodeName(), "新增标准代码内容", new Map2Temp().getLogInfo(new CodeValue(),cv));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			//将新增节点添加到树上去
			this.getExecuteSG().addExecuteCode("window.parent.addNode('"+codeValueId+"','"+subCodeValueId+"','"+codeName+"')");
			this.closeCueWindow("AddCodeValueCue");
		} else if("UPDATE".equals(mode)) {
			cv = bs6.getCodeValueByCodeTypeAndId(codeType, subCodeValueId);
			
			//创建存放原数据的对象
			CodeValue cv_old = new CodeValue();

			//对象赋值
			PropertyUtils.copyProperties(cv_old, cv);
			
			cv.setCodeName(codeName);
			cv.setCodeName2(codeName);
			cv.setCodeName3(codeName);
			cv.setCodeSpelling(kit.getPYString(codeName));
			Transaction t = bs6.getSession().getTransaction();
			t.begin();
			bs6.getSession().save(cv);
			t.commit();
			try {
				new LogUtil().createLog("77", "CODEVALUE", String.valueOf(cv.getCodeValueSeq()), cv.getCodeName(), "修改标准代码内容", new Map2Temp().getLogInfo(cv_old,cv));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//将新增节点添加到树上去
			this.getExecuteSG().addExecuteCode("window.parent.modifyNode('"+subCodeValueId+"','"+codeName+"')");
			this.closeCueWindow("ModifyCodeValueCue");
		}
		this.getExecuteSG().addExecuteCode("window.parent.clearNodeId()");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
