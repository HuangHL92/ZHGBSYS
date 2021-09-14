package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_001;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.business.sysmanager.publishmanage.PublishManageBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.lrmx.PersonXml;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.repandrec.plat.DataOrgRepPageModel;

public class DeliverCuePageModel extends PageModel {

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();

	@Override
	public int doInit() throws RadowException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		String dateStr = sdf.format(new Date());
		String filename = dateStr+"_������Ϣ�����.xml";
		this.getPageElement("fileName").setValue(filename);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* ��������:getTreeJsonData.���ɲ�����Ϣ�����νṹ<br>
	* ������������:2016��03��23��<br>
	* ����������Ա:�Ƴ�<br>
	* ��������޸�����:2016��03��23��<br>
	* ��������޸���Ա:�Ƴ�<br>
	* ������������:���ɲ�����Ϣ�����Ľṹ<br>
	* ��Ʋο��ĵ�:����Ա������Ϣϵͳ���ܰ�һ�����-��ϸ���˵���顾ZWHZYQ_001 ϵͳ����<br>
	* �������
	* <table>
	*  �������				��������				��������				������������
	*  <li>(01)
	* </table>
	* ���ؽ��
	* <table>
	*  ������				�������				�������				�����������
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   ���سɹ�״̬				  int
	* </table>
	* ����ṹ����:���ɲ�����Ϣ�����νṹҳ��
	*====================================================================================================
	*/
	@PageEvent("getTreeJsonData")
	public int getTreeJsonData() throws RadowException{                               //��ȡ���еĲ�����Ϣ���ͼ���
		List<AddType> addTypeList = bs6.getValidAddType();  
		if(addTypeList == null || addTypeList.size()==0) {
			this.getExecuteSG().addExecuteCode("alert('1')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		StringBuffer jsonStr = new StringBuffer();							          //�����ַ������������������ݸ�EXT��
		if(addTypeList != null && !addTypeList.isEmpty()){
			if(addTypeList.size() > 0){
				for(int i = 0; i < addTypeList.size(); i++){
					AddType addType = addTypeList.get(i);                             //�����е�ÿһ��������Ϣ����
					String title =addType.getAddTypeName();
					String isn = addType.getAddTypeId();                              //��Ϣ���͵�id
					if(i==0) {
						jsonStr.append("[{\"text\" :\"" + title + "\" ,\"id\" :\"" + isn + "\"");
					} else {
						jsonStr.append(",{\"text\" :\"" + title + "\" ,\"id\" :\"" + isn + "\"");
					}
					jsonStr.append(",\"leaf\":true}");
				}
				jsonStr.append("]");
			}
		}
		this.setSelfDefResData(jsonStr.toString());                           //���ַ������󴫵ݸ������ķ���
		return EventRtnType.XML_SUCCESS;
	}

	
	//����
	@PageEvent("export")
	@GridDataRange
	public int exportLrmx(String ids) throws RadowException{ 
		try {
			String filesPath = ExpRar.expFile();
	        String filePath = writeAddTypeById(ids, filesPath, "12345");
			this.getPageElement("downfile").setValue(filePath.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.reloadTree()");
		} catch (Exception e) {
			this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//����FILE
	private String writeAddTypeById(String infoIds,String dirPath,String pass) throws IOException{
		String filePath = "";
		SFileDefine fileDetail = new SFileDefine();//����ļ���ϸ��Ϣ
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		String dateStr = sdf.format(new Date());
		String fileName = dateStr+"_������Ϣ�����.xml";
		filePath = dirPath+fileName;
		File file = new File(filePath);
		file.createNewFile();
		Document document = PublishManageBS.generateAddTypeXml(infoIds);
	    OutputFormat format = OutputFormat.createPrettyPrint();
        /** ָ��XML���� */
        format.setEncoding("GBK");
        /** ��document�е�����д���ļ��� */
        XMLWriter writer = new XMLWriter(new FileWriter(file), format);
        writer.write(document);
        writer.close();
		return filePath;
	}
}
