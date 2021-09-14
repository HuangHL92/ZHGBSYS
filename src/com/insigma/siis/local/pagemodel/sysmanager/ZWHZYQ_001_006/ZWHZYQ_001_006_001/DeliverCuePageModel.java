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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String dateStr = sdf.format(new Date());
		String filename = dateStr+"_新增信息项集导出.xml";
		this.getPageElement("fileName").setValue(filename);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* 方法名称:getTreeJsonData.生成补充信息集树形结构<br>
	* 方法创建日期:2016年03月23日<br>
	* 方法创建人员:黄程<br>
	* 方法最后修改日期:2016年03月23日<br>
	* 方法最后修改人员:黄程<br>
	* 方法功能描述:生成补充信息集树的结构<br>
	* 设计参考文档:公务员管理信息系统汇总版一期软件-详细设计说明书【ZWHZYQ_001 系统管理】<br>
	* 输入参数
	* <table>
	*  参数序号				参数名称				参数描述				参数数据类型
	*  <li>(01)
	* </table>
	* 返回结果
	* <table>
	*  结果序号				结果名称				结果描述				结果数据类型
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   返回成功状态				  int
	* </table>
	* 结果结构详述:生成补充信息集树形结构页面
	*====================================================================================================
	*/
	@PageEvent("getTreeJsonData")
	public int getTreeJsonData() throws RadowException{                               //获取所有的补充信息类型集合
		List<AddType> addTypeList = bs6.getValidAddType();  
		if(addTypeList == null || addTypeList.size()==0) {
			this.getExecuteSG().addExecuteCode("alert('1')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		StringBuffer jsonStr = new StringBuffer();							          //创建字符串容器对象，用来传递给EXT树
		if(addTypeList != null && !addTypeList.isEmpty()){
			if(addTypeList.size() > 0){
				for(int i = 0; i < addTypeList.size(); i++){
					AddType addType = addTypeList.get(i);                             //链表中的每一个补充信息类型
					String title =addType.getAddTypeName();
					String isn = addType.getAddTypeId();                              //信息类型的id
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
		this.setSelfDefResData(jsonStr.toString());                           //将字符串对象传递给框架里的方法
		return EventRtnType.XML_SUCCESS;
	}

	
	//导出
	@PageEvent("export")
	@GridDataRange
	public int exportLrmx(String ids) throws RadowException{ 
		try {
			String filesPath = ExpRar.expFile();
	        String filePath = writeAddTypeById(ids, filesPath, "12345");
			this.getPageElement("downfile").setValue(filePath.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.reloadTree()");
		} catch (Exception e) {
			this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//生成FILE
	private String writeAddTypeById(String infoIds,String dirPath,String pass) throws IOException{
		String filePath = "";
		SFileDefine fileDetail = new SFileDefine();//打包文件详细信息
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String dateStr = sdf.format(new Date());
		String fileName = dateStr+"_新增信息项集导出.xml";
		filePath = dirPath+fileName;
		File file = new File(filePath);
		file.createNewFile();
		Document document = PublishManageBS.generateAddTypeXml(infoIds);
	    OutputFormat format = OutputFormat.createPrettyPrint();
        /** 指定XML编码 */
        format.setEncoding("GBK");
        /** 将document中的内容写入文件中 */
        XMLWriter writer = new XMLWriter(new FileWriter(file), format);
        writer.write(document);
        writer.close();
		return filePath;
	}
}
