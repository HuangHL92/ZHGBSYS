package com.insigma.siis.local.pagemodel.sysmanager.publishmanage;


import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.sysmanager.publishmanage.PublishManageBS;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * ��������
 * @author mengl
 *
 */
public class PublishManagePageModel extends PageModel {
	
	
	/**
	 * ��ʼ��
	 */
	@Override
	public int doInit() {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �������͸ı䴥��:��ʾ��Ӧ��ͬ�������͵Ĳ�ѯ����
	 * ����ֻ����4����ͬ��ѯ����
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("filePublishType.onchange")
	@NoRequiredValidate
	public int filePublishTypeOnChange() throws RadowException{
		String filePublishType = this.getPageElement("filePublishType").getValue();
		PageElement pe1 = this.createPageElement("1", ElementType.NORMAL, false);
		PageElement pe2 = this.createPageElement("2", ElementType.NORMAL, false);
		PageElement pe3 = this.createPageElement("3", ElementType.NORMAL, false);
		PageElement pe4 = this.createPageElement("4", ElementType.NORMAL, false);
		PageElement pe9 = this.createPageElement("9", ElementType.NORMAL, false);
		if(StringUtil.isEmpty(filePublishType)){
			pe1.setDisplay(false); 
			pe2.setDisplay(false); 
			pe3.setDisplay(false); 
			pe4.setDisplay(false); 
			pe9.setDisplay(false); 
			return EventRtnType.NORMAL_SUCCESS;
		}else if("1".equals(filePublishType)){
			pe1.setDisplay(true); 
			pe2.setDisplay(false); 
			pe3.setDisplay(false); 
			pe4.setDisplay(false); 
			pe9.setDisplay(false); 
		}else if("2".equals(filePublishType)){
			pe1.setDisplay(false); 
			pe2.setDisplay(true); 
			pe3.setDisplay(false); 
			pe4.setDisplay(false); 
			pe9.setDisplay(false);
		}else if("3".equals(filePublishType)){
			pe1.setDisplay(false); 
			pe2.setDisplay(false); 
			pe3.setDisplay(true); 
			pe4.setDisplay(false); 
			pe9.setDisplay(false);
		}else if("4".equals(filePublishType)){
			pe1.setDisplay(false); 
			pe2.setDisplay(false); 
			pe3.setDisplay(false); 
			pe4.setDisplay(true); 
			pe9.setDisplay(false);
		}else if("9".equals(filePublishType)){
			pe1.setDisplay(false); 
			pe2.setDisplay(false); 
			pe3.setDisplay(false); 
			pe4.setDisplay(false); 
			pe9.setDisplay(true);
		}
		
		this.setNextEventName("query.onclick");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ���ò�ѯ����
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("reset.onclick")
	@NoRequiredValidate  
	@AutoNoMask
	public int resetonclick()throws RadowException, AppException {
		
		this.getPageElement("ftpid").setValue("");
		
		//TODO ʹ�ø��ֲ�ѯ���͵Ĳ�ѯ����DTO�ն��������ò�ѯ����
		//1-��ϢУ�鷽��
		VerifySchemeFilterDTO verifySchemeFilterDTO = new VerifySchemeFilterDTO();
		copyObjValueToElement(verifySchemeFilterDTO, this);
		
		
		this.getPageElement("filePublishType").setValue("");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��ѯ -�����ѯ��ť�����в�ѯ
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("query.onclick")
	@NoRequiredValidate
	public int queryonclick()throws RadowException, AppException {
		
		
		String filePublishType = this.getPageElement("filePublishType").getValue();
		CodeValue filePublishTypeCodeValue = null;						//�ļ����͵�CodeValue����
		List<HashMap<String,Object>> gridList = null;					//��Ÿ�ֵ��fileGrid�е�����
		if(!StringUtil.isEmpty(filePublishType)){
			 filePublishTypeCodeValue = RuleSqlListBS.getCodeValue("FILE_PUBLISH_TYPE", filePublishType.trim());
		}else{
			throw new AppException("��ѡ��Ҫ�������ļ����ͣ�");
		}
		
		PublishManageBS publishManageBS = new PublishManageBS();
		
		//TODO ��������ʹ�ø��ֲ�ѯ���͵Ĳ�ѯ����DTO����
		//1-��ϢУ�鷽��
		VerifySchemeFilterDTO verifySchemeFilterDTO = new VerifySchemeFilterDTO();
		
		if("1".equals(filePublishType)){//1-��ϢУ�鷽��
			this.copyElementsValueToObj(verifySchemeFilterDTO, this);
			gridList = publishManageBS.queryPublishInfo(verifySchemeFilterDTO);
		}else if("2".equals(filePublishType)){//2-��չ���뼯�·�
			gridList = publishManageBS.queryCodeExtendPublishInfo();
		}else if("3".equals(filePublishType)){//3-������Ϣ���·�
			gridList = publishManageBS.queryAddTypePublishInfo();
		}else if("4".equals(filePublishType)){
			
		}else if("9".equals(filePublishType)){//9-�ļ��ϴ�����
			
		}else{
			throw new AppException("��ѡ�񷢲����͡�"+filePublishTypeCodeValue.getCodeName()+"��û����Ӧ�Ĵ�����룡");
		}
		
		if(gridList==null || gridList.size()<1){
			this.setMainMessage("��"+filePublishTypeCodeValue.getCodeName()+"��������δ�ҵ����ѯ������ƥ������ݣ�");
		}
		
		this.getPageElement("fileGrid").setValueList(gridList);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �Թ�ѡ���ļ�����
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("publish.onclick")
	public int publishClick() throws RadowException, AppException{
		
		List<HashMap<String,Object>> gridList = this.getPageElement("fileGrid").getValueList();
		String ftpid = this.getPageElement("ftpid").getValue();
		
		if(StringUtil.isEmpty(ftpid)){
			throw new AppException("����ѡҪ��������֯��λ��");
		}
		if(gridList==null || gridList.size()<1){
			throw new AppException("���Ȳ�ѯҪ��������Ϣ��");
		}
		
		this.setNextEventName("publish2FTP");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ʵ��ִ���ϴ��ļ���FTP
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("publish2FTP")
	public int publish2FTP() throws RadowException, AppException{
		int count = 0;
		PublishManageBS bs = new PublishManageBS();
		List<HashMap<String,Object>> gridList = this.getPageElement("fileGrid").getValueList();
		String ftpid = this.getPageElement("ftpid").getValue();
		
		if(StringUtil.isEmpty(ftpid)){
			throw new AppException("����ѡҪ��������֯��λ��");
		}
		String[] orgIds = new String[]{ftpid};
		
		if(gridList==null || gridList.size()<1){
			throw new AppException("���Ȳ�ѯҪ��������Ϣ��");
		}
		Iterator<HashMap<String,Object>> it =  gridList.iterator();
		while(it.hasNext()){
			HashMap<String,Object> map = it.next();
			Object mchech = map.get("mcheck");
			if(mchech!=null && (mchech.equals(true)||mchech.toString().equals("1"))){
				count++;
			}else{
				it.remove();
			}
		}
		if(count==0){
			throw new AppException("���ȹ�ѡҪ��������Ϣ��");
		}
		String cacheFilePath = "";
		//������FTP
		try {
//			cacheFilePath = bs.publishFile2FTP(gridList,orgIds,"12345");
			cacheFilePath = bs.publishFile2Local(gridList,orgIds,"12345");
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("�����ļ��쳣��"+e.getMessage());
		} 
		this.setMainMessage("�ϴ��ɹ���");
		CommonQueryBS.systemOut("�ļ�λ�ã�======��"+cacheFilePath);

		//ɾ�������ļ�
		File file = new File(cacheFilePath);
		bs.deleteAllFiles(file);
		this.setNextEventName("reload");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * ѡ����������ͼƬ������ҳ��
	 * @param name
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("searchDeptBtn.ontriggerclick")
	@NoRequiredValidate
	public int searchDept(String name) throws RadowException{
		this.openWindow("deptWin", "pages.repandrec.plat.SearchFtpDown");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ˢ��ҳ��
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("reload")
	@NoRequiredValidate
	public int reload() throws RadowException{
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
