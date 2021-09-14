package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.AddValue;
import com.insigma.siis.local.business.entity.CodeDownload;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;

/**
 * Servlet implementation class AddTypeDownServlet
 */
public class AddTypeDownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTypeDownServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void destroy() {
		super.destroy();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if (method.equals("downFile")) {// 单个文件下载
			downFile(request, response);
		} else if (method.equals("downCodeValueFile")){
			downCodeValueFile(request, response);
		}
	}

	public void downCodeValueFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String dateStr = sdf.format(new Date());
//		System.out.println(dateStr);
		String param = request.getParameter("param");
//		System.out.println(param);
		String filename = dateStr+"_代码信息集导出.xml";
//		System.out.println(filename);
		response.reset();
		response.setHeader("pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/octet-stream;charset=GBK");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(filename.getBytes("GBK"), "ISO8859-1"));
		String temppath = request.getSession().getServletContext().getRealPath(File.separator);
		filename = temppath+filename;
		File tempf = new File(filename);
		if(!tempf.exists()) {
			tempf.createNewFile();                      //创建临时文件
		}
		Document document = generateCodeValueXml(param);
	    OutputFormat format = OutputFormat.createPrettyPrint();
        /** 指定XML编码 */
        format.setEncoding("GBK");
        /** 将document中的内容写入文件中 */
        XMLWriter writer = new XMLWriter(new FileWriter(tempf), format);
        writer.write(document);
        writer.close();
		int fileLength = (int) tempf.length();
        response.setContentLength(fileLength);
        /** 如果文件长度大于0*/
        if (fileLength != 0) {
            /** 创建输入流*/
            InputStream inStream = new FileInputStream(tempf);
            byte[] buf = new byte[4096];
            /** 创建输出流*/
            ServletOutputStream servletOS = response.getOutputStream();
            int readLength;
            while (((readLength = inStream.read(buf)) != -1)) {
                servletOS.write(buf, 0, readLength);
            }
            inStream.close();
            servletOS.flush();
            servletOS.close();
        }
		tempf.delete();//删除文件
	}
	
	/**
	 * 下载文件(单个文件下载)方法
	 */
	public void downFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String filename = "代码信息集导出.xml";
        /*读取文件*/
        File file = new File("d:/1.zzb");
        /*如果文件存在*/
        if (file.exists()) {
            response.reset();
            response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("GBK"), "ISO8859-1"));
           
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
            /*如果文件长度大于0*/
            if (fileLength != 0) {
                /*创建输入流*/
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                /*创建输出流*/
                ServletOutputStream servletOS = response.getOutputStream();
                int readLength;
                while (((readLength = inStream.read(buf)) != -1)) {
                    servletOS.write(buf, 0, readLength);
                }
                inStream.close();
                servletOS.flush();
                servletOS.close();
            }
        }
	}
	
	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	/**
	 * 生成下发补充信息集的XML
	 * @return
	 */
	public String generateAddTypeXml(String str) {
		String[] addTypeIds = str.split(",");
		Document document = DocumentHelper.createDocument(); 
		Element root = document.addElement("AddType");
		for(int i=0; i<addTypeIds.length; i++) {
			String addTypeId = addTypeIds[i];
			AddType addType = bs6.getAddTypeById(addTypeId);
			Element typeDataElement = root.addElement("Data");
			typeDataElement.addAttribute("num", i+"");
			Element addTypeIdElement = typeDataElement.addElement("AddTypeId");
			addTypeIdElement.setText(addType.getAddTypeId());
			Element addTypeNameElement = typeDataElement.addElement("AddValueName");
			addTypeNameElement.setText(addType.getAddTypeName());
			Element addTypeDetailElement = typeDataElement.addElement("AddTypeDetail");
			addTypeDetailElement.setText(addType.getAddTypeDetail());
			List<AddValue> list = bs6.getValidAddValueById(addTypeId);
			Element addValueElement = typeDataElement.addElement("AddValue");
			for(int j=0; j<list.size(); j++) {
				AddValue addValue = list.get(j);
				Element valueDataElement = addValueElement.addElement("Data");
				valueDataElement.addAttribute("num", j+"");
				Element addValueIdElement = valueDataElement.addElement("AddValueId");
				addValueIdElement.setText(addValue.getAddValueId());
			}
		}
		return null;
	}
	/**
	 * 生成导出的代码信息集
	 * @param str
	 * @return
	 */
	public Document generateCodeValueXml(String str) {
		String[] codeValues = str.split(",");
		List<CodeValue> list = new ArrayList<CodeValue>();
		//查询所有需要导出的CodeValue并放到list当中去,并将对应的Code_Download里的Download_status设置为1
		for(int i=0; i<codeValues.length; i++) {
			String codeType = codeValues[i].split(":")[0];
			String codeValueId = codeValues[i].split(":")[1];
			CodeValue cv = (CodeValue) HBUtil.getHBSession().createQuery("from CodeValue where codeType=:codeType and codeValue=:codeValue")
                    .setParameter("codeType", codeType).setParameter("codeValue", codeValueId).uniqueResult();
			HBUtil.getHBSession().createSQLQuery("update code_download set download_status='1' where code_value_seq='"+cv.getCodeValueSeq()+"'").executeUpdate();
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
}
