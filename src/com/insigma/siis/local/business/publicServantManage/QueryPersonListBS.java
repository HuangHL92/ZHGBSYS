package com.insigma.siis.local.business.publicServantManage;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.demo.TestPdf;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.exportexcel.ExportDataBuilder;
import com.insigma.siis.local.pagemodel.exportexcel.FiledownServlet;
import com.insigma.siis.local.pagemodel.publicServantManage.AddPersonPageModel;
import com.insigma.siis.local.pagemodel.publicServantManage.QueryPersonListPageModel;
import com.lbs.leaf.util.DateUtil;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;


/**
 * ��Ա��Ϣά��BS
 * @author mengl
 *
 */
public class QueryPersonListBS extends HttpServlet{
	
	/**
	 * �ϲ�PDF�ļ�
	 * @param pdfs
	 * @return
	 * @author mengl
	 */
	public static void mergePdfs(List<String> files,String newFilePath){
		Document document = null;  
        try {  
            document = new Document(new PdfReader(files.get(0)).getPageSize(1));  
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(newFilePath));  
            document.open();  
            for (int i = 0; i < files.size(); i++) {  
                PdfReader reader = new PdfReader(files.get(i));  
                int n = reader.getNumberOfPages();  
                for (int j = 1; j <= n; j++) {  
                    document.newPage();  
                    PdfImportedPage page = copy.getImportedPage(reader, j);  
                    copy.addPage(page);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            document.close();  
        }  
	}
	
	public List<String> getPdfsByA000s(List<String> a0000s,boolean flag) throws AppException{
		return getPdfsByA000s(a0000s,flag,null);
	}
	
	/**
	 * ��ȡ��ԱID��ȡPDF
	 * @param a0000s ��ԱID
	 * @param flag �Ƿ�ƴ����������Ϣ
	 * @return ���ɵĸ�����Ա��PDF����·��
	 * @throws AppException 
	 * @author mengl
	 */
	public List<String>  getPdfsByA0000sThreads(List<String> a0000s,boolean flag,String printTime) throws AppException{
		List<String> pdfPaths = new ArrayList<String>();	
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
		CountDownLatch countLatch;
		String zippath =ExpRar.expFile();					//�ļ����Ŀ¼��·��
		int totalCount = a0000s.size();		//������
		/*int pageCount = 20;					//�������߳�
		int timeCount = totalCount%pageCount==0?totalCount/pageCount:(totalCount/pageCount+1); //����������������̼߳������
		int lastCount = totalCount%pageCount==0?pageCount:totalCount%pageCount;	//���һ��ѭ������
		
		for(int j = 0 ;j<timeCount;j++){
			if(j!=timeCount-1){//�������һ��
				countLatch = new CountDownLatch(pageCount);
				for(int i=j*pageCount;i<(j+1)*pageCount;i++){
					new GetPdfThread( a0000s.get(i), flag, printTime, zippath,userid ,countLatch).start();
				}
			}else{
				countLatch = new CountDownLatch(lastCount);
				for(int i=j*pageCount;i<(j*pageCount+lastCount);i++){
					new GetPdfThread( a0000s.get(i), flag, printTime, zippath,userid ,countLatch).start();
				}
			}
			//�ȴ��������߳�ִ���� 
			try {
				countLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		countLatch = new CountDownLatch(totalCount);
		for(int i=0;i<totalCount;i++){
			new GetPdfThread( a0000s.get(i), flag, printTime, zippath,userid ,countLatch).start();
		}
		//�ȴ��������߳�ִ���� 
		try {
			countLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//5.ɾ��Ŀ¼�·�PDF�ļ������ܸ���PDF�ļ�·����������
		File dec = new File(zippath);
		File[] files = dec.listFiles();
		CommonQueryBS.systemOut("------------->ɾ��Ŀ¼�·�PDF�ļ���ʼ:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		for (File f0 : files) {
			int indexPoint = f0.getName().lastIndexOf(".")+1;
			String extStr = f0.getName().length()>=indexPoint?f0.getName().substring(indexPoint):"";
			if(extStr.equalsIgnoreCase("pdf")){
				pdfPaths.add(f0.getAbsolutePath());
			}else{
				f0.delete();
			}
		}
		CommonQueryBS.systemOut("------------->ɾ��Ŀ¼�·�PDF�ļ���ʼ:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		
		
		return pdfPaths;
	}
	
	
	public List<String> getPdfsByA000s(List<String> a0000s,boolean flag,String printTime) throws AppException{
		List<String> pdfPaths = new ArrayList<String>();	
		String zippath =ExpRar.expFile();					//�ļ����Ŀ¼��·��
		int i = 1;
//		List<String> names = new ArrayList<String>();
		for(String a0000 : a0000s){
			CommonQueryBS.systemOut("------------->��ȡ�����˵���Ƭ��ʼ:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
			String name = ""+i; //�ļ�����
			i++;
			//1.������Ա��Ϣ��ƴ��lrm��Ϣ��
			String lrm = new QueryPersonListPageModel().createLrmStr(a0000,flag,printTime);
			
			try {
				
				//2.����lrm�ļ�����Ա�ı���Ϣ
				FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
				
				//3.����pic�ļ�����Ա��Ƭ��Ϣ
				@SuppressWarnings("unchecked")
				List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
				if(list15.size()>0){
					A57 a57 = list15.get(0);
					byte[] data = PhotosUtil.getPhotoData(a57);
					if(data!=null){
						File f = new File(zippath + name+".pic");
						FileOutputStream fos = new FileOutputStream(f);
						fos.write(data);
						fos.flush();
						fos.close();
					}
				}
				//4.����.lrm�ļ���.pic�ļ�������PDF�ļ�
				CommonQueryBS.systemOut("------------->����PDF�ļ���ʼ:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
				SevenZipUtil.zzbRmb(zippath, name);
				CommonQueryBS.systemOut("------------->����PDF�ļ�����:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
//				names.add(name);
			} catch (Exception e) {
				throw new AppException("�����ļ���������ϵ����Ա!�쳣��Ϣ��"+e.getMessage()); 
			}
			
			CommonQueryBS.systemOut("------------->��ȡ�����˵���Ƭ����:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		}
		
		/*System.out.println("------------->��ȡ�����˵�PDF��ʼ:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		for(String name : names){
			//4.����.lrm�ļ���.pic�ļ�������PDF�ļ�
			SevenZipUtil.zzbRmb(zippath, name);
		}
		System.out.println("------------->��ȡ�����˵�PDF����:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));*/
		//5.ɾ��Ŀ¼�·�PDF�ļ������ܸ���PDF�ļ�·����������
		File dec = new File(zippath);
		File[] files = dec.listFiles();
		CommonQueryBS.systemOut("------------->ɾ��Ŀ¼�·�PDF�ļ���ʼ:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		for (File f0 : files) {
			int indexPoint = f0.getName().lastIndexOf(".")+1;
			String extStr = f0.getName().length()>=indexPoint?f0.getName().substring(indexPoint):"";
			if(extStr.equalsIgnoreCase("pdf")){
				pdfPaths.add(f0.getAbsolutePath());
			}else{
				f0.delete();
			}
		}
		CommonQueryBS.systemOut("------------->ɾ��Ŀ¼�·�PDF�ļ�����:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		
		
		return pdfPaths;
	}
	
	public List<String> getPdfsByA000s_new(List<String> a0000s,boolean flag,String printTime) throws AppException{
		ExportDataBuilder edb = new ExportDataBuilder();
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		Template template = null;
		String classPath = SevenZipUtil.class.getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath  = ""; 
		
		//windows�� 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		//linux�� 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		rootPath = rootPath+"pages/exportexcel/";
		// ��ʲô�ط�����freemarkerģ���ļ�
		try {
			configuration.setDirectoryForTemplateLoading(new File(rootPath));
		    // ���ö����װ��
		    configuration.setObjectWrapper(new DefaultObjectWrapper());
		    // �����쳣������
		    configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		    
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		List<String> pdfPaths = new ArrayList<String>();	
		String zippath =ExpRar.expFile();					//�ļ����Ŀ¼��·��
		int i = 1;
//		List<String> names = new ArrayList<String>();
		for(String a0000 : a0000s){
			CommonQueryBS.systemOut("------------->��ȡ�����˵���Ƭ��ʼ:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
			String name = ""+i; //�ļ�����
			Map<String, Object> dataMap = edb.getGbrmspbMap(a0000);
			String a0101 = (String)dataMap.get("a0101");
			String QRZXLXX = (String)dataMap.get("qrzxlxx");
			String QRZXWXX = (String)dataMap.get("qrzxwxx");
			String ZZXLXX = (String)dataMap.get("zzxlxx");
			String ZZXWXX = (String)dataMap.get("zzxwxx");
			//��ʽ��ʱ�����
			String a0107 = (String)dataMap.get("a0107");
			String a0134 = (String)dataMap.get("a0134");
			String a1701 = (String)dataMap.get("a1701");
			if(a1701 != null){
				//������ʽ��
				StringBuffer originaljl = new StringBuffer("");
				String jianli = AddPersonPageModel.formatJL(a1701,originaljl);
				a1701 = jianli.replaceAll("\r\n|\r|\n", "</w:t><w:br/><w:t>");
				//a1701 = jianli;
				dataMap.put("a1701", a1701);
			}
			if(a0107 != null && !"".equals(a0107)){
				a0107 = a0107.substring(0,4)+"."+a0107.substring(4,6);
				dataMap.put("a0107", a0107);
			}
			if(a0134 != null && !"".equals(a0134)){
				a0134 = a0134.substring(0,4)+"."+a0134.substring(4,6);
				dataMap.put("a0134", a0134);
			}
			//����2���ּӿո�
			if(a0101 != null){
				if(a0101.length() == 2){
					StringBuilder sb = new StringBuilder();
				    int length = a0101.length();
				    for (int i1 = 0; i1 < length; i1++) {
				        if (length - i1 <= 2) {      //��ֹArrayIndexOutOfBoundsException
				            sb.append(a0101.substring(i1, i1 + 1)).append("  ");
				            sb.append(a0101.substring(i1 + 1));
				            break;
				        }
				        sb.append(a0101.substring(i1, i1 + 1)).append("  ");
				    }
				    dataMap.put("a0101", sb.toString());
				}
			}
			if(QRZXLXX.equals(QRZXWXX)&&!ZZXLXX.equals(ZZXWXX)){
				// ����Template������һ���¶�
			    try {
					template = configuration.getTemplate("gbrmspb_3.xml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(!QRZXLXX.equals(QRZXWXX)&&ZZXLXX.equals(ZZXWXX)){
				// ����Template�����϶�����һ
			    try {
					template = configuration.getTemplate("gbrmspb_2.xml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(!QRZXLXX.equals(QRZXWXX)&&!ZZXLXX.equals(ZZXWXX)){
				// ����Template�����϶����¶�
			    try {
					template = configuration.getTemplate("gbrmspb_4.xml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(QRZXLXX.equals(QRZXWXX)&&ZZXLXX.equals(ZZXWXX)){
				// ����Template������һ����һ
			    try {
					template = configuration.getTemplate("gbrmspb_1.xml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			File outFile = new File(zippath+i+".doc");
			Writer out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(outFile), "utf-8"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try {
				template.process(dataMap, out);
				out.close();
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//TestPdf.convert2PDF(zippath+i+"_"+a0101+".doc", zippath+i+"_"+a0101+".pdf");
			TestAspose2Pdf.doc2pdf(zippath+i+".doc", zippath+i+".pdf");
			i++;
		}
		//5.ɾ��Ŀ¼�·�PDF�ļ������ܸ���PDF�ļ�·����������
		File dec = new File(zippath);
		File[] files = dec.listFiles();
		CommonQueryBS.systemOut("------------->ɾ��Ŀ¼�·�PDF�ļ���ʼ:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		for (File f0 : files) {
			int indexPoint = f0.getName().lastIndexOf(".")+1;
			String extStr = f0.getName().length()>=indexPoint?f0.getName().substring(indexPoint):"";
			if(extStr.equalsIgnoreCase("pdf")){
				pdfPaths.add(f0.getAbsolutePath());
			}else{
				f0.delete();
			}
		}
		CommonQueryBS.systemOut("------------->ɾ��Ŀ¼�·�PDF�ļ�����:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));	
		return pdfPaths;
	}
	
	public String batchPrint(List<HashMap<String,Object>> list,boolean flag) throws AppException{
		return batchPrint(list,flag,null);
	}
	
	/**
	 * ������ӡ
	 * @param list ��Ա�б���Ϣ
	 * @param flag �Ƿ�ƴ����������Ϣ
	 * @return �ϲ���PDF�ļ�����·��
	 * @throws AppException
	 * @author mengl
	 */
	public String batchPrint(List<HashMap<String,Object>> list,boolean flag,String printTime) throws AppException{
		long startTime = System.currentTimeMillis();
		CommonQueryBS.systemOut("------->������ӡ��ʼ:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		//�趨�ϲ���PDF�ļ�·��
		String newPdfFile = ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".Pdf";  
		
		//��ѡ����ԱID
		List<String> a0000s = new ArrayList<String>();	
		for(HashMap<String,Object> map : list){
			if(map!=null && map.get("personcheck").toString().equalsIgnoreCase("true")){
				a0000s.add((String)map.get("a0000"));
			}
		}
		if(a0000s.size()<1){
			throw new AppException("�빴ѡҪ������ӡ����Ա��");
		}
		
		CommonQueryBS.systemOut("----------->��ȡÿ���˵�PDF��ʼ:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		//1.��ȡÿ���˵�PDF
		List<String> files = getPdfsByA000s_new(a0000s,flag,printTime);
//		List<String> files = getPdfsByA0000sThreads(a0000s,flag,printTime);
		CommonQueryBS.systemOut("----------->��ȡÿ���˵�PDF����:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		
		CommonQueryBS.systemOut("----------->�ϲ�PDF��ʼ:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		//2.�ϲ�PDF
		mergePdfs(files,newPdfFile);
		CommonQueryBS.systemOut("----------->�ϲ�PDF����:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		
		CommonQueryBS.systemOut("------->������ӡ����:"+DateUtil.getCurrentDate_String("yyyyMMdd HH:mi:ss"));
		CommonQueryBS.systemOut("------->"+a0000s.size()+"����ʱ:"+(System.currentTimeMillis()-startTime)+"milliseconds");
		return newPdfFile;
	}
	
	/**
	 * ����Lrm�ļ�
	 * @param ids ��ԱID a0000
	 * @param falg �Ƿ��ӡ��������Ϣ��true-��ӡ��������Ϣ
	 * @return
	 */
	public static String createLrmStr(String ids,boolean falg,String printTime,String useridPar){
		HBSession sess = HBUtil.getHBSession();
		String useTime = printTime;
		String a0000 = ids;
		String userid = useridPar;
		try {
			userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
		} catch (Exception e) {
			userid = useridPar;
		}
		DBType cueDBType = DBUtil.getDBType();
		String str ="";
		String jiatingchengyuan="";
		String laststr2 = "";
		String laststr1 ="";
		if(cueDBType==DBType.MYSQL){
			str = (String) sess.createSQLQuery("select CONCAT_WS('','\"',t.a0101,'\",\"',( select code_name from code_value where code_value = t.a0104 and code_type = 'GB2261' and code_status = '1'),'\",\"',T.A0107,'\",\"',( select code_name from code_value where code_value = t.A0117 and code_type = 'GB3304' and code_status = '1'),'\",\"',T.A0111A,'\",\"',t.a0140,'\",\"',t.a0128,'\",\"', T.a0114A,'\",\"', T.a0134,'\",\"',t.qrzxl,'#',t.qrzxw,'@',t.zzxl,'#',t.zzxw,'\",\"',t.qrzxlxx,'#',t.qrzxwxx,'@',t.zzxlxx,'#',t.zzxwxx,'\",\"',T.A0196,'\",\"\",\"\",\"\",\"\",\"',t.a0187a,'\",\"',t.A1701,'\",\"',t.A14Z101,'\",\"',t.A15Z101,'\"') "
					+"from a01 t  where t.a0000='"+a0000+"'").uniqueResult();	
			jiatingchengyuan = (String) sess.createSQLQuery("select CONCAT_WS('','\"' , replace(group_concat(t.a3604a), ',', '@') , '|\",\"' , replace(group_concat(t.a3601), ',', '@') , '|\",\"' ,       replace(group_concat(t.a3607), ',', '@') , '|\",\"' ,       replace(group_concat(t.a3627), ',', '@') , '|\",\"' ,       replace(group_concat(t.a3611), ',', '@') , '|\"')  "
				 +"from (select a0000, ifnull((select b.code_name from code_value b where b.code_type = 'GB4761' and b.code_value = a3604a and b.code_status = '1'), '#') a3604a, ifnull(a3601, '#') a3601, ifnull(a3607, '#') a3607, ifnull((select b.code_name from code_value b where b.code_type = 'GB4762' and b.code_value = a3627 and b.code_status = '1'), '#') a3627, ifnull(a3611, '#') a3611, sortid from a36) t"
				 +" where t.a0000 = '"+a0000+"'"
				 +"order by t.sortid").uniqueResult();
			if(StringUtil.isEmpty(useTime)){
				laststr2 = (String) sess.createSQLQuery("select CONCAT_WS('','\"' , max(ifnull(t.a5304, '')) , '\",\"' , max(ifnull(t.a5315, '')) ,'\",\"' , max(ifnull(t.a5317, '')) , '\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , max(ifnull(t.a5321, '')) , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max(ifnull(t.a5323, '')) , '\"')from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
			}else{
				laststr2 = (String) sess.createSQLQuery("select CONCAT_WS('','\"' , max(ifnull(t.a5304, '')) , '\",\"' , max(ifnull(t.a5315, '')) ,'\",\"' , max(ifnull(t.a5317, '')) , '\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , max(ifnull(t.a5321, '')) , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max("+useTime+") , '\"')from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
			}
			
			laststr1 = (String) sess.createSQLQuery("select CONCAT_WS('','\"',max(ifnull(t.a0192a,'')), '\"') from a01 t where t.a0000='"+a0000+"'").uniqueResult();
		}else{
			str = (String) sess.createSQLQuery("select '\"'||t.a0101||'\",\"'||( select code_name from code_value where code_value = t.a0104 and code_type = 'GB2261' and code_status = '1')||'\",\"'||T.A0107||'\",\"'||"
					+"( select code_name from code_value where code_value = t.A0117 and code_type = 'GB3304' and code_status = '1')||'\",\"'||T.A0111A||'\",\"'||t.a0140||'\",\"'||"
					+"t.a0128||'\",\"'|| T.a0114A||'\",\"'|| T.a0134||'\",\"'||"
					+"t.qrzxl||'#'||t.qrzxw||'@'||t.zzxl||'#'||t.zzxw||"
					+"'\",\"'||t.qrzxlxx||'#'||t.qrzxwxx||'@'||t.zzxlxx||'#'||t.zzxwxx||'\",\"'||"
					+"T.A0196||'\",\"\",\"\",\"\",\"\",\"'||t.a0187a||'\",\"'||"
					+"t.A1701||'\",\"'||t.A14Z101||'\",\"'||t.A15Z101||'\"' "
					+"from a01 t  where t.a0000='"+a0000+"'").uniqueResult();
			jiatingchengyuan = (String) sess.createSQLQuery("select '\"' || replace(wm_concat(t.a3604a), ',', '@') || '|\",\"' ||"
				       +"replace(wm_concat(t.a3601), ',', '@') || '|\",\"' ||"
				       +"replace(wm_concat(t.a3607), ',', '@') || '|\",\"' ||"
				       +"replace(wm_concat(t.a3627), ',', '@') || '|\",\"' ||"
				       +"replace(wm_concat(t.a3611), ',', '@') || '|\"'"
				 +"from (select a0000,  nvl( (select b.code_name from code_value b where b.code_type = 'GB4761'  and b.code_value = a3604a and b.code_status = '1'),'#') a3604a,  nvl(a3601,'#')a3601,  nvl(a3607,'#')a3607, nvl( (select b.code_name from code_value b  where b.code_type = 'GB4762' and b.code_value = a3627 and b.code_status = '1'),'#') a3627,nvl(a3611,'#')a3611,sortid from a36)  t"
				 +" where t.a0000 = '"+a0000+"'"
				 +"order by t.sortid").uniqueResult();
			if(StringUtil.isEmpty(useTime)){
				laststr2 = (String) sess.createSQLQuery("select '\"'||max(nvl(t.a5304,''))||'\",\"'||max(nvl(t.a5315,''))||'\",\"'||max(nvl(t.a5317,''))||'\",\"'||max(nvl(t.a5319,''))||'\",\"'||max(nvl(t.a5321,''))||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max(nvl(t.a5323,''))||'\"' from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
			}else{
				laststr2 = (String) sess.createSQLQuery("select '\"'||max(nvl(t.a5304,''))||'\",\"'||max(nvl(t.a5315,''))||'\",\"'||max(nvl(t.a5317,''))||'\",\"'||max(nvl(t.a5319,''))||'\",\"'||max(nvl(t.a5321,''))||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max("+useTime+")||'\"' from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
			}
			
			laststr1 = (String) sess.createSQLQuery("select '\"'||max(nvl(t.a0192a,''))|| '\"' from a01 t where t.a0000='"+a0000+"'").uniqueResult();
		}
//		System.out.println(str);
		String count =    sess.createSQLQuery("Select Count(1) From a36 t where t.a0000='"+a0000+"'").list().get(0).toString();
		String append ="";
		if(Integer.valueOf(count)<13){
			for(int j=12 ;j>Integer.valueOf(count)-1;j--){
				append+="@";
			}
		}
		jiatingchengyuan=jiatingchengyuan.replace("#", "");
		CommonQueryBS.systemOut(jiatingchengyuan.replace("|",append));
//		System.out.println(laststr1);
//		System.out.println(str+","+jiatingchengyuan.replace("|",append)+","+laststr);
		//�Ƿ��ӡ��������Ϣ
		if(!falg){
			laststr2 = "\"\",\"\",\"\",\"\",\"\",\"\",\"\"";
		}
		
		String lrm = str+","+jiatingchengyuan.replace("|",append)+","+laststr1+","+laststr2;
		return lrm;
	}
	
}