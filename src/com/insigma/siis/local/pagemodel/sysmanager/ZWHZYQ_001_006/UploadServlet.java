package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.AddValue;
import com.insigma.siis.local.business.entity.CodeDownload;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		response.setContentType("text/html;charset=GBK");
		response.setCharacterEncoding("GBK");
		request.setCharacterEncoding("GBK");
		String classPath = getClass().getClassLoader().getResource("/").getPath();
		String rootPath = "";
		String filename = "";
		String filePath = "";
		ServletOutputStream out = response.getOutputStream();
		OutputStreamWriter ow = new OutputStreamWriter(out,"GBK");  
		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		// 上传路径
		String upload_file = rootPath + "upload/";
		try {
			File file = new File(upload_file);
			// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		List<FileItem> list = null;
		try {
			list = uploader.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		File file = null;
		for (FileItem item : list) {
			if (!item.isFormField()){
				// 将文件保存到指定目录
				String path = item.getName();// 文件名称
				try {
					filename = path.substring(path.lastIndexOf("\\") + 1);
					filePath = upload_file + filename;
					file = new File(filePath);
					item.write(file);
					item.getOutputStream().close();
				} catch (Exception e) {
					try {
						throw new AppException("上传失败");
					} catch (AppException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		String type = request.getParameter("type");
		try {
			if (!"xml".equals(filename.split("\\.")[1])) {
				if("codeType".equals(type)) {
					response.sendRedirect("pages/sysmanager/ZWHZYQ_001_006/ZWHZYQ_001_006_002/CodeValueRecieveCue.jsp");
				} else {
					response.sendRedirect("pages/sysmanager/ZWHZYQ_001_006/ZWHZYQ_001_006_001/AddValueRecieveCue.jsp");
				}
				return;
			} 
		} catch (Exception e) {
			if("codeType".equals(type)) {
				response.sendRedirect("pages/sysmanager/ZWHZYQ_001_006/ZWHZYQ_001_006_002/CodeValueRecieveCue.jsp");
			} else {
				response.sendRedirect("pages/sysmanager/ZWHZYQ_001_006/ZWHZYQ_001_006_001/AddValueRecieveCue.jsp");
			}
			return;
		}
		if("codeType".equals(type)) {
			setCodeValue(filePath);
		} else {
			setAddValue(filePath);
		}
		file.delete();
		
		ow.write("成功导入"+filename+"文件");
		ow.flush();
		ow.close();
	}
	
	public void setAddValue(String filename) {
		File file = new File(filename);
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		HBSession session = HBUtil.getHBSession();
		try {
	        reader = new BufferedReader(new FileReader(file));
	        String tempString = null;
	        // 一次读入一行，直到读入null为文件结束
	        while ((tempString = reader.readLine()) != null) {
	            // 显示行号
	            sb.append(tempString);
	        }
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
		        try {
		            reader.close();
		        } catch (IOException e1) {
		        }
	        }
	    }
		Map<AddType,List<AddValue>> map = null;
	    try {
		  map = parseAddTypeXml(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	    for(Map.Entry<AddType, List<AddValue>> entry : map.entrySet()) {
	    	AddType addType = entry.getKey();
	    	List<AddValue> list = entry.getValue();
	    	//存入addType 如果已存在则不存
	    	//存入addValue 如果已存在则不存
	    	Transaction t = session.getTransaction();
	    	t.begin();
	    	AddType addTypeCheck = (AddType) HBUtil.getHBSession().createQuery("from AddType where addTypeId=:addTypeId")
	    			.setParameter("addTypeId", addType.getAddTypeId()).uniqueResult();
	    	if(addTypeCheck == null) {
	    		int seq = bs6.getMaxSeq()+1;
	    		addType.setAddTypeSequence(seq);
	    		session.save(addType);
	    		for(AddValue addValue : list) {
	    			session.save(addValue);
	    		}
	    	} else {
	    		for(AddValue addValue : list) {
	    			AddValue addValueCheck = (AddValue) HBUtil.getHBSession().createQuery("from AddValue where addValueId=:addValueId")
	    	    			.setParameter("addValueId", addValue.getAddValueId()).uniqueResult();
	    			if(addValueCheck == null) {//验证是否已存在
	    				int seq = bs6.getMaxAddValueSeq(addType.getAddTypeId())+1;
	    				addValue.setAddValueSequence(seq);
	    				session.save(addValue);
	    			}
	    		}
	    	}
	    	t.commit();
	    }
	}
	
	public void setCodeValue(String filename){
		File file = new File(filename);
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		HBSession session = HBUtil.getHBSession();
	    try {
	        reader = new BufferedReader(new FileReader(file));
	        String tempString = null;
	        // 一次读入一行，直到读入null为文件结束
	        while ((tempString = reader.readLine()) != null) {
	            // 显示行号
	            sb.append(tempString);
	        }
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
		        try {
		            reader.close();
		        } catch (IOException e1) {
		        }
	        }
	    }
	    CommonQueryBS.systemOut(sb.toString());
	    List<CodeValue> list = null;
	    try {
			list = parseXml(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	    for(CodeValue cv : list ) {
	    	CodeValue checkValue = (CodeValue) session.createQuery("from CodeValue where codeType=:codeType and codeValue=:codeValue")
	    			.setParameter("codeType", cv.getCodeType()).setParameter("codeValue", cv.getCodeValue()).uniqueResult();//检查有没有重复的codeType+codeValue
	    	CodeValue checkSeq = (CodeValue) session.createQuery("from CodeValue where codeValueSeq=:codeValueSeq")
	    			.setParameter("codeValueSeq", cv.getCodeValueSeq()).uniqueResult();//检查有没有重复的seq
	    	if(checkSeq!=null) {
	    		ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	    		int seq = bs6.getMaxCodeValueSeq()+1;
	    		cv.setCodeValueSeq(seq);
	    	}
	    	if(checkValue == null) {//接收的机构没有该codevalue
	    		CodeDownload cd = new CodeDownload();
	    		cd.setCodeValueSeq(cv.getCodeValueSeq());
	    		cd.setDownloadStatus("0");
	    		Transaction t = session.getTransaction();
	    		t.begin();
	    		session.save(cv);
	    		session.save(cd);
	    		t.commit();
	    	}
	    }
	}
	
	public Map<AddType,List<AddValue>> parseAddTypeXml(String xml) throws Exception {
		SAXReader reader = new SAXReader();
		Document  document = reader.read(new ByteArrayInputStream(xml.getBytes("GBK")));
		Element rootElm = document.getRootElement();
		List<Element> tableElms = rootElm.elements("Data");
		Map<AddType,List<AddValue>> map = null;
		if(tableElms!=null && !tableElms.isEmpty()){
			map = new HashMap<AddType,List<AddValue>>();
		}else{
			return null;
		}
		for (int i = 0; i < tableElms.size(); i++) {
			AddType at = new AddType();
			List<AddValue> avList = new ArrayList<AddValue>();
			Element tableElm = tableElms.get(i);
			String addTypeId = tableElm.element("AddTypeId").getText();
			at.setAddTypeId(addTypeId);
			String addTypeName = tableElm.element("AddTypeName").getText();
			at.setAddTypeName(addTypeName);
			String addTypeDetail = tableElm.element("AddTypeDetail").getText();
			at.setAddTypeDetail(addTypeDetail);
			String tableCode = tableElm.element("TableCode").getText();
			at.setTableCode(tableCode);
			Element addValueElement = tableElm.element("AddValue");
			List<Element> addValueElms = addValueElement.elements("Data");
			for(int j=0; j<addValueElms.size(); j++) {
				AddValue av = new AddValue();
				Element addValueElm = addValueElms.get(j);
				String addValueId = addValueElm.element("AddValueId").getText();
				av.setAddValueId(addValueId);
				String addTypeId0 = addValueElm.element("AddTypeId").getText();
				av.setAddTypeId(addTypeId0);
				String addValueName = addValueElm.element("AddValueName").getText();
				av.setAddValueName(addValueName);
				String colCode = addValueElm.element("ColCode").getText();
				av.setColCode(colCode);
				String colType = addValueElm.element("ColType").getText();
				av.setColType(colType);
				String publishStatus = addValueElm.element("PublishStatus").getText();
				av.setPublishStatus(publishStatus);
				String isused = addValueElm.element("Isused").getText();
				av.setIsused(isused);
				String multilineshow = addValueElm.element("Multilineshow").getText();
				av.setMultilineshow(multilineshow);
				String addValueDetail = addValueElm.element("AddValueDetail").getText();
				addValueDetail = "null".equalsIgnoreCase(addValueDetail)?"":addValueDetail;
				av.setAddValueDetail(addValueDetail);
				String codeType = addValueElm.element("CodeType").getText();
				codeType = "null".equalsIgnoreCase(codeType)?"":codeType;
				av.setCodeType(codeType);
				avList.add(av);
			}
			map.put(at, avList);
		}
		return map;
	}
	
	public List<CodeValue> parseXml(String xml) throws Exception {
		SAXReader reader = new SAXReader();
		Document  document = reader.read(new ByteArrayInputStream(xml.getBytes("GBK")));
		Element rootElm = document.getRootElement();
		List<Element> tableElms = rootElm.elements("Data");
		List<CodeValue> list = null;
		if(tableElms!=null && !tableElms.isEmpty()){
			list = new ArrayList<CodeValue>();
		}else{
			return null;
		}
		for (int i = 0; i < tableElms.size(); i++) {
			CodeValue cv = new CodeValue();
			Element tableElm = tableElms.get(i);
			int codeValueSeq = Integer.parseInt(tableElm.element("codeValueSeq").getText());
			cv.setCodeValueSeq(codeValueSeq);
			String codeType = tableElm.element("codeType").getText();
			cv.setCodeType(codeType);
			String codeValue = tableElm.element("codeValue").getText();
			cv.setCodeValue(codeValue);
			String subCodeValue = tableElm.element("subCodeValue").getText();
			cv.setSubCodeValue(subCodeValue);
			String codeName = tableElm.element("codeName").getText();
			cv.setCodeName(codeName);
			String codeName2 = tableElm.element("codeName2").getText();
			cv.setCodeName2(codeName2);
			String codeName3 = tableElm.element("codeName3").getText();
			cv.setCodeName3(codeName3);
			String codeSpelling = tableElm.element("codeSpelling").getText();
			cv.setCodeSpelling(codeSpelling);
			String iscustomize = tableElm.element("iscustomize").getText();
			cv.setIscustomize(iscustomize);
			String codeStatus = tableElm.element("codeStatus").getText();
			cv.setCodeStatus(codeStatus);
			String codeLeaf = tableElm.element("codeLeaf").getText();
			cv.setCodeLeaf(codeLeaf);
			list.add(cv);
		}
		return list;
	}

}
