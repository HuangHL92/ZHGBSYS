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
	 * 初始化
	 */
	@Override
	public int doInit() {
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
		this.getPageElement("filePublishType").setValue("");
		this.getPageElement("startCreateTime").setValue("");
		this.getPageElement("endCreateTime").setValue("");
		this.getPageElement("createPerson").setValue("");
		this.getPageElement("fileName").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询 -点击查询按钮，进行查询
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("query.onclick")
	public int queryonclick()throws RadowException, AppException {
		
		VerificationSchemePublishBS vspBS = new VerificationSchemePublishBS();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//目录下获取的文件的属性
		Date lastModefyTimeMap = null;
		String fileNameMap	= null;
		String fileSuffixMap = null;
		
		//过滤条件
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
			throw new AppException("日期转换异常：【最后修改时间】格式应为【yyyyMMdd】!");
		}
		
		
		Aa01 filePublishTypeAa01 = null;								//文件类型的Aa01对象,文件配置地址
		CodeValue filePublishTypeCodeValue = null;						//文件类型的CodeValue对象
		String dirPath = null;											//文件类型对应配置的目录
		List<HashMap<String,Object>> gridList = null;					//存放赋值在fileGrid中的数据
		
		if(!StringUtil.isEmpty(filePublishType)){
			 filePublishTypeCodeValue = RuleSqlListBS.getCodeValue("FILE_PUBLISH_TYPE", filePublishType.trim());
		}else{
			throw new AppException("请选择要发布的文件类型！");
		}
		filePublishTypeAa01 = RuleSqlListBS.getAa01("FILE_PUBLISH_TYPE", filePublishType);
		if(filePublishTypeAa01==null || StringUtil.isEmpty(filePublishTypeAa01.getAaa005())){
			throw new AppException("【"+filePublishTypeCodeValue.getCodeName()+"】的文件类型未配置发布的文件目录！");
		}
		dirPath = filePublishTypeAa01.getAaa005();
//		dirPath = this.request.getRealPath("")+"\\uploads\\"+SysUtil.getCacheCurrentUser().getUserVO().getId();
		
		//遍历指定路径下文件
		gridList = vspBS.getFilesByDirFile(dirPath);
		if(gridList==null || gridList.size()<1){
			throw new AppException("【"+filePublishTypeCodeValue.getCodeName()+"】的文件类型配置目录下未找到文件！");
		}
		//根据筛选条件过滤
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
			this.setMainMessage("【"+filePublishTypeCodeValue.getCodeName()+"】的文件类型配置目录下未找到与查询条件相匹配的文件！");
		}
		
		this.getPageElement("fileGrid").setValueList(gridList);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//刷新本页面
	@PageEvent("reloadButton.onclick")
	@NoRequiredValidate
	public int reloadWin() throws RadowException{
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
}
