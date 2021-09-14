package com.insigma.siis.local.business.sysmanager.publishmanage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.ftpserver.ftplet.FtpException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.hsqldb.lib.StringUtil;

import com.fr.stable.core.UUID;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.commform.BSSupport;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.util.DateUtil;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.AddValue;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Ftpuser;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.business.utils.CustomExcelUtil;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzFtpClient;
import com.insigma.siis.local.jtrans.ZwhzFtpPath;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.repandrec.plat.DataOrgRepPageModel;
import com.insigma.siis.local.pagemodel.sysmanager.publishmanage.VerifySchemeFilterDTO;

/**
 * 发布文件BS类
 * @author mengl
 *
 */
public class PublishManageBS extends BSSupport{
	
	/**
	 * 根据过滤条件查询
	 * @param filterDTO
	 * @return
	 * @throws AppException 
	 */
	public List<HashMap<String,Object>> queryPublishInfo(Object filterDTO) throws AppException{
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		HBSession sess = HBUtil.getHBSession();
		String sql = "";
		StringBuffer sqlbf = null;
		if(filterDTO==null){
			return list;
		}else if(filterDTO instanceof VerifySchemeFilterDTO){//息校验方案查询
			VerifySchemeFilterDTO verifySchemeFilterDTO = (VerifySchemeFilterDTO)filterDTO;
			String vsc002 = verifySchemeFilterDTO.getVsc002();
			String vsc006Start = verifySchemeFilterDTO.getVsc006Start();
			String vsc006End = verifySchemeFilterDTO.getVsc006End();
			String vsc005Name = verifySchemeFilterDTO.getVsc005Name();
			String vsc004 = verifySchemeFilterDTO.getVsc004();
			
			sqlbf = new StringBuffer()
			.append("Select  Vsc001  publishInfoId, ")
			.append("       Vsc002  publishName, ")
			.append("'"+verifySchemeFilterDTO.getFilePublishType()+"'  filePublishType ")
			.append("  From Verify_Scheme ")
			.append(" Where 1 = 1 ");
		
			if(!StringUtil.isEmpty(vsc002)){
				sqlbf.append(" and vsc002 like '%" +vsc002 +"%' ");
			}
			if(!StringUtil.isEmpty(vsc006Start)){
				sqlbf.append(" and to_char(vsc006,'yyyymmdd') >='" +vsc006Start+ "' ");
			}
			if(!StringUtil.isEmpty(vsc006End)){
				sqlbf.append(" and to_char(vsc006,'yyyymmdd') <='" +vsc006End+ "' ");
			}
			if(!StringUtil.isEmpty(vsc004)){
				sqlbf.append(" and vsc004 ='" +vsc004+ "' ");
			}
			if(!StringUtil.isEmpty(vsc005Name)){
				sqlbf.append(" and vsc005 in (select userid from Smt_User where Description like '%" +vsc005Name+ "%') ");
			}
			//处理MySQL
			if(DBUtil.getDBType() == DBType.MYSQL){
				sql = sqlbf.toString().replace("to_char(vsc006,'yyyymmdd')", "DATE_FORMAT(Vsc006,'%Y%m%d')");
			}else{
				sql = sqlbf.toString();
			}
			
			PreparedStatement ps;
			try {
				ps = sess.connection().prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs!=null && rs.next()){
					HashMap<String,Object> map = new HashMap<String,Object>();
					map.put("publishInfoId", rs.getString("publishInfoId"));
					map.put("publishName", rs.getString("publishName"));
					map.put("filePublishType", rs.getString("filePublishType"));
					list.add(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new AppException("查询异常："+e.getMessage());
			}
		}
		
		return list;
	}
	
	/**
	 * 根据过滤条件查询
	 * @param codeExtendDTO
	 * @return
	 * @throws AppException 
	 */
	public List<HashMap<String,Object>> queryCodeExtendPublishInfo() throws AppException{
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		HBSession sess = HBUtil.getHBSession();
		String	sql = "select "
				    + "a.code_value_seq publishInfoId,concat(concat(b.type_name,'_'),a.code_name) publishName "
				    + "from code_value a,code_type b "
				    + "where "
				    + "a.code_value_seq in (select code_value_seq from code_download) "
				    + "and "
				    + "a.code_type=b.code_type";
		PreparedStatement ps;
		try {
			ps = sess.connection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs!=null && rs.next()){
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("publishInfoId", rs.getString("publishInfoId"));
				map.put("publishName", rs.getString("publishName"));
				map.put("filePublishType", "2");
			    list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("查询异常："+e.getMessage());
		}
		return list;
	}
	
	public List<HashMap<String,Object>> queryAddTypePublishInfo() throws AppException{
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		HBSession sess = HBUtil.getHBSession();
		String	sql =  "select "
				     + "a.add_type_id publishInfoId,"
				     + "a.add_type_name publishName "
				     + "from "
				     + "add_type a "
				     + "where "
				     + "a.add_type_id "
				     + "in(select distinct(add_type_id) from add_value b where b.isused='1') order by a.add_type_sequence";
		PreparedStatement ps;
		try {
			ps = sess.connection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs!=null && rs.next()){
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("publishInfoId", rs.getString("publishInfoId"));
				map.put("publishName", rs.getString("publishName"));
				map.put("filePublishType", "3");
			    list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("查询异常："+e.getMessage());
		}
		return list;
	}
	
	/**
	 * 发布信息到FTP(主动上传到选择的FTP用户)
	 * @return
	 * @throws AppException 
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws WriteException 
	 */
	public String publishFile2FTP(List<HashMap<String,Object>> gridList,String[] orgIds,String pass) throws AppException{
		String filesPath = ExpRar.expFile();		//存放文件路径
		String currTime = DateUtil.toString(new Date(), "yyyyMMddHHmmss"); //当前时间
		ZwhzPackDefine info = new ZwhzPackDefine();	//打包文件信息
		List<SFileDefine> fileDetails = null;		//打包文件详细信息
 		info.setTime(currTime);
		info.setTranstype("up");
		String xmlName =  currTime + ".xml";;		//打包文件转为XML的文件名
		File files = new File(filesPath);
		files.mkdir();
		Object filePublishType = null;
		StringBuffer infoIds = new StringBuffer();
		for(HashMap<String,Object> map : gridList){
			Object publishInfoId = map.get("publishInfoId");
			filePublishType = map.get("filePublishType");
			if(publishInfoId!=null){
				infoIds.append(publishInfoId).append(",");
			}
		}
		if(infoIds==null || StringUtil.isEmpty(infoIds.toString())){
			throw new AppException("发布信息到FTP失败：发布信息ID缺失！");
		}else if(  infoIds.toString().contains(",")){
			infoIds = infoIds.deleteCharAt(infoIds.lastIndexOf(","));
		}
		if(filePublishType == null){
			throw new AppException("发布信息到FTP失败：发布类型缺失！");
		}
		
		//TODO 拓展其他发布信息
		try {
			//1-信息校验方案处理
			if("1".equals(filePublishType.toString())){
				fileDetails =	this.writeVerifySchemeByIds(infoIds.toString(), filesPath, pass);
			} else if("2".equals(filePublishType.toString())) {
				fileDetails = this.writeCodeExtendById(infoIds.toString(), filesPath, pass);
			} else if("3".equals(filePublishType.toString())) {
				fileDetails = this.writeAddTypeById(infoIds.toString(), filesPath, pass);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new AppException("发布信息到FTP失败：从数据库中取数据写入文件错误。异常信息："+e.getMessage());
		}
		
		info.setSfile(fileDetails);
		
		//创建xml文件
		try {
			FileUtil.createFile(filesPath + "/"+ xmlName, JXUtil.Object2Xml(info,true), false, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new AppException("上传FTP异常："+e1.getMessage());
		}
		
		//发送FTP
		TransConfig jfcc = null;
		 jfcc = (TransConfig) HBUtil.getHBSession().get(TransConfig.class, orgIds[0]);
		 try {
			ZwhzFtpClient.uploadHzb(jfcc, filesPath + "/"+  xmlName);
		} catch (FtpException e) {
			e.printStackTrace();
			throw new AppException("上传FTP失败："+e.getMessage());
		}
		 
		 
		//记录日志
		 SFileDefine newFileDatail = new SFileDefine();
		 HBSession sess = HBUtil.getHBSession();
		 TransConfig  tc= (TransConfig) sess.get(TransConfig.class, orgIds[0]);
		for(SFileDefine fileDatail : fileDetails){
			try {
				new LogUtil().createLog("641", "TRANS_CONFIG", orgIds[0],"", "为【"+tc.getName()+"】FTP用户发送文件【"+fileDatail.getName()+"】", Map2Temp.getLogInfo(fileDatail,newFileDatail ) );
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		 
		return filesPath;
	}
	
	
	
	
	/**
	 * 发布信息到FTP(生成文件到本地，等待其他FTP来轮循下载)
	 * @return
	 * @throws AppException 
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws WriteException 
	 */
	public String publishFile2Local(List<HashMap<String,Object>> gridList,String[] orgIds,String pass) throws AppException{
		String filesPath = ExpRar.expFile();		//存放文件路径
		String currTime = DateUtil.toString(new Date(), "yyyyMMddHHmmss"); //当前时间
		String appendDir = "";						//根据发布类型不同：拼接子目录
		ZwhzPackDefine info = new ZwhzPackDefine();	//打包文件信息
		List<SFileDefine> fileDetails = null;		//打包文件详细信息
		
		HBSession sess = HBUtil.getHBSession();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		info.setId(UUID.randomUUID().toString().replace("-", ""));
 		info.setTime(currTime);
		info.setTranstype("down");
		info.setDataversion(currTime);
//		System.out.println(user.getCheckip()+","+user.getDept()+","+user.getDesc()+","+user.getEmpid()+","+user.getHashcode()+","+user.getId()+","+user.getIplist()+","+user.getIsleader()+","+user.getLoginname()+","+user.getMacaddr()+","+user.getName()+","+user.getOtherinfo()+","+user.getOwnerId()+","+user.getRate()+","+user.getPasswd()+","+user.getRegionid()+","+user.getStatus());
		info.setB0111(user.getOtherinfo());
		B01 b01 = (B01)sess.get(B01.class, user.getOtherinfo());
		info.setB0101(b01.getB0101());
		info.setB0114(b01.getB0114());
		info.setB0194(b01.getB0194());
		
		
		File files = new File(filesPath);
		files.mkdir();
		Object filePublishType = null;
		StringBuffer infoIds = new StringBuffer();
		for(HashMap<String,Object> map : gridList){
			Object publishInfoId = map.get("publishInfoId");
			filePublishType = map.get("filePublishType");
			if(publishInfoId!=null){
				infoIds.append(publishInfoId).append(",");
			}
		}
		if(infoIds==null || StringUtil.isEmpty(infoIds.toString())){
			throw new AppException("发布信息到FTP失败：发布信息ID缺失！");
		}else if(  infoIds.toString().contains(",")){
			infoIds = infoIds.deleteCharAt(infoIds.lastIndexOf(","));
		}
		if(filePublishType == null){
			throw new AppException("发布信息到FTP失败：发布类型缺失！");
		}
		
		//TODO 拓展其他发布信息
		if("1".equals(filePublishType)){
			appendDir = ZwhzFtpPath.JC_DOWN;
		}else if("2".equals(filePublishType)){
			appendDir = ZwhzFtpPath.DM_DOWN;
		}else if("3".equals(filePublishType)){
			appendDir = ZwhzFtpPath.ZB_DOWN;
			
		}
		
		info.setStype((String)filePublishType);
		CodeValue typeCodeValue = RuleSqlListBS.getCodeValue("FILE_PUBLISH_TYPE",(String)filePublishType);
		info.setStypename(typeCodeValue.getCodeName());
		String xmlName =  "Pack_"+typeCodeValue.getCodeName() + "_"+b01.getB0114()+"_"+b01.getB0101()+"_"+currTime+".xml";;		//打包文件转为XML的文件名
		
		//TODO 拓展其他发布信息
		try {
			//1-信息校验方案处理
			if("1".equals(filePublishType.toString())){
				fileDetails =	this.writeVerifySchemeByIds(infoIds.toString(), filesPath, pass);
			} else if("2".equals(filePublishType.toString())) {
				fileDetails = this.writeCodeExtendById(infoIds.toString(), filesPath, pass);
			} else if("3".equals(filePublishType.toString())) {
				fileDetails = this.writeAddTypeById(infoIds.toString(), filesPath, pass);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new AppException("发布信息到FTP失败：从数据库中取数据写入文件错误。异常信息："+e.getMessage());
		}
		
		info.setSfile(fileDetails);
		
		//创建xml文件
		try {
			FileUtil.createFile(filesPath + "/"+ xmlName, JXUtil.Object2Xml(info,true), false, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new AppException("上传FTP异常："+e1.getMessage());
		}
		//发送文件到各个选择用户的文件夹
		for(String ftpid:orgIds){
			if(!StringUtil.isEmpty(ftpid)){
				Ftpuser ftpuser = (Ftpuser)sess.get(Ftpuser.class, ftpid);
				try {
					FileUtil.copyDirectiory(filesPath, ftpuser.getHomedirectory()+appendDir);
				} catch (Exception e) {
					e.printStackTrace();
					throw new AppException("复制文件异常："+e.getMessage());
				}
			}
		}
		 
		//记录日志
		 SFileDefine newFileDatail = new SFileDefine();
		for(SFileDefine fileDatail : fileDetails){
			try {
				new LogUtil().createLog("641", "TRANS_CONFIG", orgIds[0],"", "为【"+user.getName()+"】FTP用户发送文件【"+fileDatail.getName()+"】", Map2Temp.getLogInfo(fileDatail,newFileDatail ) );
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		 
		return filesPath;
	}
	
	private List<SFileDefine> writeAddTypeById(String infoIds,String dirPath,String pass) throws IOException{
		List<SFileDefine> fileDetails = new ArrayList<SFileDefine>();
		String filePath = "";
		SFileDefine fileDetail = new SFileDefine();//打包文件详细信息
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String dateStr = sdf.format(new Date());
		String fileName = dateStr+"_新增信息项集导出.xml";
		filePath = dirPath+fileName;
		File file = new File(filePath);
		file.createNewFile();
		Document document = generateAddTypeXml(infoIds);
	    OutputFormat format = OutputFormat.createPrettyPrint();
        /** 指定XML编码 */
        format.setEncoding("GBK");
        /** 将document中的内容写入文件中 */
        XMLWriter writer = new XMLWriter(new FileWriter(file), format);
        writer.write(document);
        writer.close();
		fileDetail.setName(fileName);
		fileDetail.setSize(DataOrgRepPageModel.getFileSize(file));
		fileDetails.add(fileDetail);
		return fileDetails;
	}
	
	
	private List<SFileDefine> writeCodeExtendById(String infoIds,String dirPath,String pass) throws IOException{
		List<SFileDefine> fileDetails = new ArrayList<SFileDefine>();
		String filePath = "";
		SFileDefine fileDetail = new SFileDefine();//打包文件详细信息
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String dateStr = sdf.format(new Date());
		String fileName = dateStr+"_代码信息集导出.xml";
		filePath = dirPath+fileName;
		File file = new File(filePath);
		file.createNewFile();
		Document document = generateCodeValueXml(infoIds);
	    OutputFormat format = OutputFormat.createPrettyPrint();
        /** 指定XML编码 */
        format.setEncoding("GBK");
        /** 将document中的内容写入文件中 */
        XMLWriter writer = new XMLWriter(new FileWriter(file), format);
        writer.write(document);
        writer.close();
		fileDetail.setName(fileName);
		fileDetails.add(fileDetail);
		fileDetail.setSize(DataOrgRepPageModel.getFileSize(file));
		return fileDetails;
	}
	
	/**
	 * 发布校验规则信息
	 * @param infoIds
	 * @param dirPath
	 * @return
	 * @throws AppException 
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws WriteException 
	 */
	private  List<SFileDefine> writeVerifySchemeByIds(String infoIds,String dirPath,String pass) throws AppException, IOException, WriteException, IllegalArgumentException, IllegalAccessException{
		List<SFileDefine> fileDetails = new ArrayList<SFileDefine>();
		Map<String, List<List<Object>>> listContentMap = CustomExcelUtil.getExcelMap(infoIds.toString(),"信息校验方案");
		Set<String> keySet = listContentMap.keySet();
		String filePath = "";
		String fileName = "";
		String currTime = DateUtil.toString(new Date(), "yyyyMMddHHmmss"); //当前时间
		for(String key :keySet){
			SFileDefine fileDetail = new SFileDefine();//打包文件详细信息
			if (!StringUtil.isEmpty(key)) {
				fileName = key;
			}else{
				fileName = "导出文件";
			}
			fileName = fileName.replace(".xls", "") + "_"
					+ currTime + ".xls";	
			filePath = dirPath+fileName;
			File file = new File(filePath);
			file.createNewFile();
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			CustomExcelUtil.writeWorkBook(workbook, listContentMap.get(key), pass);
			fileDetail.setSize(DataOrgRepPageModel.getFileSize(file));
			fileDetail.setName(fileName);
			fileDetail.setTime(currTime);
			fileDetails.add(fileDetail);
		}
		return fileDetails;
		
	} 
	
	 /**
	  * 删除该目录下所有文件及该目录本身
	  * @return
	 * @throws AppException 
	  */
	 public boolean deleteAllFiles(File dirFile) throws AppException{
		 boolean deleteFlag = true;
		 if(dirFile!=null && dirFile.isDirectory()){
			 File[] files = dirFile.listFiles();
			 for(File file : files){
				 if(file.isFile()){
					 deleteFlag =  file.delete();
				 }else{
					 deleteFlag = deleteAllFiles(file);
				 }
				 if(!deleteFlag){
					 throw new AppException("删除缓存文件异常：删除文件【"+file.getAbsolutePath()+"】失败！");
				 }
			 }
			 //删除该目录
			 deleteFlag = dirFile.delete();
			 if(!deleteFlag){
				 throw new AppException("删除缓存文件异常：删除文件【"+dirFile.getAbsolutePath()+"】失败！");
			 }
		 }else{
			 throw new AppException("删除缓存文件异常：传入参数为空 或 不是目录文件！");
		 }
		 return deleteFlag ;
	 }
	 
 	/**
	 * 生成导出的代码信息集
	 * @param str
	 * @return
	 */
	public Document generateCodeValueXml(String infoIds) {
		String[] codeValueSeqs = infoIds.split(",");
		List<CodeValue> list = new ArrayList<CodeValue>();
		//查询所有需要导出的CodeValue并放到list当中去,并将对应的Code_Download里的Download_status设置为1
		for(int i=0; i<codeValueSeqs.length; i++) {
			Integer codeValueSeq = Integer.parseInt(codeValueSeqs[i]);
			CodeValue cv = (CodeValue) HBUtil.getHBSession().createQuery("from CodeValue where codeValueSeq=:codeValueSeq")
                    .setParameter("codeValueSeq", codeValueSeq).uniqueResult();
			HBUtil.getHBSession().createSQLQuery("update code_download set download_status='1' where code_value_seq='"+codeValueSeq+"'").executeUpdate();
			list.add(cv);
		}
		Document document = DocumentHelper.createDocument(); 
		Element root = document.addElement("Table");
		for(int i=0; i<list.size(); i++) {
			CodeValue codeValue = list.get(i);
			Element codeData = root.addElement("Data");                      //每一个导出的CodeValue代表一个Data元素
			codeData.addAttribute("rownum", i+"");                           //每一个CodeValue的序号，从0开始
			Element codeValueSeqElement = codeData.addElement("codeValueSeq");
			codeValueSeqElement.setText(codeValue.getCodeValueSeq()+"");
			Element codeTypeElement = codeData.addElement("codeType");
			codeTypeElement.setText(codeValue.getCodeType());
			Element codeValueElement = codeData.addElement("codeValue");
			codeValueElement.setText(codeValue.getCodeValue());
			Element subCodeValueElement = codeData.addElement("subCodeValue");
			subCodeValueElement.setText(codeValue.getSubCodeValue());
			Element codeNameElement = codeData.addElement("codeName");
			codeNameElement.setText(codeValue.getCodeName());
			Element codeNameElement2 = codeData.addElement("codeName2");
			codeNameElement2.setText(codeValue.getCodeName2());
			Element codeNameElement3 = codeData.addElement("codeName3");
			codeNameElement3.setText(codeValue.getCodeName3());
			Element codeSpellingElement = codeData.addElement("codeSpelling");
			codeSpellingElement.setText(codeValue.getCodeSpelling());
			Element iscustomizeElement = codeData.addElement("iscustomize");
			iscustomizeElement.setText(codeValue.getIscustomize());
			Element codeStatusElement = codeData.addElement("codeStatus");
			codeStatusElement.setText(codeValue.getCodeStatus());
			Element codeLeafElement = codeData.addElement("codeLeaf");
			codeLeafElement.setText(codeValue.getCodeLeaf());
		}
		return document;
	}
	
	@SuppressWarnings("unchecked")
	public static Document generateAddTypeXml(String infoIds) {
		String[] addTypeIds = infoIds.split(",");
		Document document = DocumentHelper.createDocument(); 
		Element root = document.addElement("AddType");
		for(int i=0; i<addTypeIds.length; i++) {
			String addTypeId = addTypeIds[i];
			AddType addType = (AddType)HBUtil.getHBSession().createQuery("from AddType where addTypeId=:addTypeId order by addTypeSequence")
					.setParameter("addTypeId", addTypeId).uniqueResult();
			Element typeDataElement = root.addElement("Data");
			typeDataElement.addAttribute("num", i+"");
			Element addTypeIdElement = typeDataElement.addElement("AddTypeId");
			addTypeIdElement.setText(addType.getAddTypeId());
			Element addTypeNameElement = typeDataElement.addElement("AddTypeName");
			addTypeNameElement.setText(addType.getAddTypeName());
			Element addTypeDetailElement = typeDataElement.addElement("AddTypeDetail");
			addTypeDetailElement.setText(addType.getAddTypeDetail()+"");
			Element addTypeTableCode = typeDataElement.addElement("TableCode");
			addTypeTableCode.setText(addType.getTableCode());
			List<AddValue> list = HBUtil.getHBSession().createQuery("from AddValue where addTypeId=:addTypeId and isused='1' order by addValueSequence")
					.setParameter("addTypeId", addTypeId).list();
			Element addValueElement = typeDataElement.addElement("AddValue");
			for(int j=0; j<list.size(); j++) {
				AddValue addValue = list.get(j);
				Element valueDataElement = addValueElement.addElement("Data");
				valueDataElement.addAttribute("num", j+"");
				Element addValueIdElement = valueDataElement.addElement("AddValueId");
				addValueIdElement.setText(addValue.getAddValueId());
				Element addTypeId0Element = valueDataElement.addElement("AddTypeId");
				addTypeId0Element.setText(addValue.getAddTypeId());
				Element addValueNameElement = valueDataElement.addElement("AddValueName");
				addValueNameElement.setText(addValue.getAddValueName());
				Element colCodeElement = valueDataElement.addElement("ColCode");
				colCodeElement.setText(addValue.getColCode());
				Element colTypeElement = valueDataElement.addElement("ColType");
				colTypeElement.setText(addValue.getColType());
				Element publishStatusElement = valueDataElement.addElement("PublishStatus");
				publishStatusElement.setText("0");
				Element isusedElement = valueDataElement.addElement("Isused");
				isusedElement.setText(addValue.getIsused());
				Element multilineshowElement = valueDataElement.addElement("Multilineshow");
				multilineshowElement.setText(addValue.getMultilineshow());
				Element addValueDetailElement = valueDataElement.addElement("AddValueDetail");
				addValueDetailElement.setText(addValue.getAddValueDetail()+"");
				Element codeTypeElement = valueDataElement.addElement("CodeType");
				codeTypeElement.setText(addValue.getCodeType()+"");
			}
		}
		return document;
	}
}