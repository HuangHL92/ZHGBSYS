package com.insigma.siis.local.pagemodel.sysmanager.verificationschemepublish;


import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Aa01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.business.sysmanager.verificationschemepublish.VerificationSchemePublishBS;

public class VerificationSchemePublishPageModel extends PageModel {
	
	
	/**
	 * ��ʼ��
	 */
	@Override
	public int doInit() {
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
		this.getPageElement("filePublishType").setValue("");
		this.getPageElement("startCreateTime").setValue("");
		this.getPageElement("endCreateTime").setValue("");
		this.getPageElement("createPerson").setValue("");
		this.getPageElement("fileName").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��ѯ -�����ѯ��ť�����в�ѯ
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("query.onclick")
	public int queryonclick()throws RadowException, AppException {
		
		VerificationSchemePublishBS vspBS = new VerificationSchemePublishBS();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//Ŀ¼�»�ȡ���ļ�������
		Date lastModefyTimeMap = null;
		String fileNameMap	= null;
		String fileSuffixMap = null;
		
		//��������
		String filePublishType = this.getPageElement("filePublishType").getValue();
		String startLastModefyTimeStr = this.getPageElement("startLastModefyTime").getValue();
		String endLastModefyTimeStr = this.getPageElement("endLastModefyTime").getValue();
		String fileSuffix = this.getPageElement("fileSuffix").getValue();		
		String fileName = this.getPageElement("fileName").getValue();		
		Date startLastModefyTime = null;
		Date endLastModefyTime = null;
		try{
			if(!StringUtil.isEmpty(startLastModefyTimeStr)){
				startLastModefyTime = sdf.parse(startLastModefyTimeStr);
			}
			if(!StringUtil.isEmpty(endLastModefyTimeStr)){
				endLastModefyTime = sdf.parse(endLastModefyTimeStr);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("����ת���쳣��������޸�ʱ�䡿��ʽӦΪ��yyyyMMdd��!");
		}
		
		
		Aa01 filePublishTypeAa01 = null;								//�ļ����͵�Aa01����,�ļ����õ�ַ
		CodeValue filePublishTypeCodeValue = null;						//�ļ����͵�CodeValue����
		String dirPath = null;											//�ļ����Ͷ�Ӧ���õ�Ŀ¼
		List<HashMap<String,Object>> gridList = null;					//��Ÿ�ֵ��fileGrid�е�����
		
		if(!StringUtil.isEmpty(filePublishType)){
			 filePublishTypeCodeValue = RuleSqlListBS.getCodeValue("FILE_PUBLISH_TYPE", filePublishType.trim());
		}else{
			throw new AppException("��ѡ��Ҫ�������ļ����ͣ�");
		}
		filePublishTypeAa01 = RuleSqlListBS.getAa01("FILE_PUBLISH_TYPE", filePublishType);
		if(filePublishTypeAa01==null || StringUtil.isEmpty(filePublishTypeAa01.getAaa005())){
			throw new AppException("��"+filePublishTypeCodeValue.getCodeName()+"�����ļ�����δ���÷������ļ�Ŀ¼��");
		}
		dirPath = filePublishTypeAa01.getAaa005();
//		dirPath = this.request.getRealPath("")+"\\uploads\\"+SysUtil.getCacheCurrentUser().getUserVO().getId();
		
		//����ָ��·�����ļ�
		gridList = vspBS.getFilesByDirFile(dirPath);
		if(gridList==null || gridList.size()<1){
			throw new AppException("��"+filePublishTypeCodeValue.getCodeName()+"�����ļ���������Ŀ¼��δ�ҵ��ļ���");
		}
		//����ɸѡ��������
		Iterator<HashMap<String,Object>> it = gridList.iterator();
		while(it.hasNext()){
			HashMap<String,Object> map = it.next();
			if(map==null){
				it.remove();
			}
			
			lastModefyTimeMap = (Date)map.get("lastModefyTime");
			fileNameMap	= map.get("fileName").toString();
			fileSuffixMap = map.get("fileSuffix").toString();
			
			map.put("filePublishType", filePublishType);
			map.put("lastModefyTime", sdfHMS.format(lastModefyTimeMap));
			
			if(!StringUtil.isEmpty(fileName) && !fileNameMap.trim().toUpperCase().contains(fileName.toUpperCase()) ){
				it.remove();
				continue;
			}
			if(!StringUtil.isEmpty(fileSuffix) && !fileSuffix.equals(fileSuffixMap.trim()) ){
				it.remove();
				continue;
			}
			if(startLastModefyTime!=null && startLastModefyTime.after(lastModefyTimeMap) ){
				it.remove();
				continue;
			}
			if(endLastModefyTime!=null && endLastModefyTime.before(lastModefyTimeMap) ){
				it.remove();
				continue;
			}
		}
		
		if(gridList==null || gridList.size()<1){
			this.setMainMessage("��"+filePublishTypeCodeValue.getCodeName()+"�����ļ���������Ŀ¼��δ�ҵ����ѯ������ƥ����ļ���");
		}
		
		this.getPageElement("fileGrid").setValueList(gridList);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//ˢ�±�ҳ��
	@PageEvent("reloadButton.onclick")
	@NoRequiredValidate
	public int reloadWin() throws RadowException{
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
}
