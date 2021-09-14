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
 * 发布管理
 * @author mengl
 *
 */
public class PublishManagePageModel extends PageModel {
	
	
	/**
	 * 初始化
	 */
	@Override
	public int doInit() {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 发布类型改变触发:显示对应不同发布类型的查询条件
	 * 现在只控制4个不同查询类型
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
	 * 重置查询条件
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("reset.onclick")
	@NoRequiredValidate  
	@AutoNoMask
	public int resetonclick()throws RadowException, AppException {
		
		this.getPageElement("ftpid").setValue("");
		
		//TODO 使用各种查询类型的查询条件DTO空对象，来重置查询界面
		//1-信息校验方案
		VerifySchemeFilterDTO verifySchemeFilterDTO = new VerifySchemeFilterDTO();
		copyObjValueToElement(verifySchemeFilterDTO, this);
		
		
		this.getPageElement("filePublishType").setValue("");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询 -点击查询按钮，进行查询
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("query.onclick")
	@NoRequiredValidate
	public int queryonclick()throws RadowException, AppException {
		
		
		String filePublishType = this.getPageElement("filePublishType").getValue();
		CodeValue filePublishTypeCodeValue = null;						//文件类型的CodeValue对象
		List<HashMap<String,Object>> gridList = null;					//存放赋值在fileGrid中的数据
		if(!StringUtil.isEmpty(filePublishType)){
			 filePublishTypeCodeValue = RuleSqlListBS.getCodeValue("FILE_PUBLISH_TYPE", filePublishType.trim());
		}else{
			throw new AppException("请选择要发布的文件类型！");
		}
		
		PublishManageBS publishManageBS = new PublishManageBS();
		
		//TODO 过滤条件使用各种查询类型的查询条件DTO对象
		//1-信息校验方案
		VerifySchemeFilterDTO verifySchemeFilterDTO = new VerifySchemeFilterDTO();
		
		if("1".equals(filePublishType)){//1-信息校验方案
			this.copyElementsValueToObj(verifySchemeFilterDTO, this);
			gridList = publishManageBS.queryPublishInfo(verifySchemeFilterDTO);
		}else if("2".equals(filePublishType)){//2-扩展代码集下发
			gridList = publishManageBS.queryCodeExtendPublishInfo();
		}else if("3".equals(filePublishType)){//3-补充信息项下发
			gridList = publishManageBS.queryAddTypePublishInfo();
		}else if("4".equals(filePublishType)){
			
		}else if("9".equals(filePublishType)){//9-文件上传发布
			
		}else{
			throw new AppException("您选择发布类型【"+filePublishTypeCodeValue.getCodeName()+"】没有相应的处理代码！");
		}
		
		if(gridList==null || gridList.size()<1){
			this.setMainMessage("【"+filePublishTypeCodeValue.getCodeName()+"】的类型未找到与查询条件相匹配的数据！");
		}
		
		this.getPageElement("fileGrid").setValueList(gridList);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 对勾选的文件发布
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("publish.onclick")
	public int publishClick() throws RadowException, AppException{
		
		List<HashMap<String,Object>> gridList = this.getPageElement("fileGrid").getValueList();
		String ftpid = this.getPageElement("ftpid").getValue();
		
		if(StringUtil.isEmpty(ftpid)){
			throw new AppException("请先选要发布的组织单位！");
		}
		if(gridList==null || gridList.size()<1){
			throw new AppException("请先查询要发布的信息！");
		}
		
		this.setNextEventName("publish2FTP");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 实际执行上传文件到FTP
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
			throw new AppException("请先选要发布的组织单位！");
		}
		String[] orgIds = new String[]{ftpid};
		
		if(gridList==null || gridList.size()<1){
			throw new AppException("请先查询要发布的信息！");
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
			throw new AppException("请先勾选要发布的信息！");
		}
		String cacheFilePath = "";
		//发布到FTP
		try {
//			cacheFilePath = bs.publishFile2FTP(gridList,orgIds,"12345");
			cacheFilePath = bs.publishFile2Local(gridList,orgIds,"12345");
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("发布文件异常："+e.getMessage());
		} 
		this.setMainMessage("上传成功！");
		CommonQueryBS.systemOut("文件位置：======》"+cacheFilePath);

		//删除缓存文件
		File file = new File(cacheFilePath);
		bs.deleteAllFiles(file);
		this.setNextEventName("reload");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * 选择机构，点击图片，弹出页面
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
	 * 刷新页面
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
