package com.insigma.siis.local.pagemodel.xbrm2;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;

import com.JUpload.JUpload;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.local.business.entity.TpbAtt;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.insigma.siis.local.pagemodel.xbrm2.impexcel.ImpExcel;
import com.insigma.siis.local.pagemodel.xbrm2.zsrm.ZipUtil;

/******
 * ʵ�ֿ�����ϡ��ĵ����ϴ�����
 * ������Ŀ
 * @author ��־��  20190901
 *
 */
public class UploadKcclPageModel extends PageModel implements JUpload {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}

	/***
	 * ��ʼ��
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("initX")
	public int initX() throws RadowException, AppException {
		String a0000 = this.getPageElement("a0000").getValue();
		String ynId = this.getPageElement("ynId").getValue();
		HBSession sess = HBUtil.getHBSession();

		if (a0000 == null || "".equals(a0000)) {
			// ����Excel���Ͳ�����ļ�
			return EventRtnType.NORMAL_SUCCESS;
		}

		try {
			// �����ļ���Ϣ
			List<TpbAtt> tpbAttlist = sess
					.createQuery("from TpbAtt where rb_id='" + ynId + "' and js0100='" + a0000 + "'").list();

			if (tpbAttlist != null) {
				List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
				for (TpbAtt jsatt : tpbAttlist) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", jsatt.getJsa00());
					map.put("name", jsatt.getJsa04());
					map.put("fileSize", jsatt.getJsa08());
					listmap.add(map);
				}
				this.setFilesInfo("file5", listmap);

			}
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			e.printStackTrace();
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/****
	 * ȡ����Ա����
	 * @param a0000
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	private String getPersonName(String a0000) throws RadowException, AppException {
		String personName = "";
		HBSession sess = HBUtil.getHBSession();
		try { 
			CommQuery htmlbs = new CommQuery();
			String sql = "select tp0101 from tphj1 where a0000 = '"+a0000+"' and rownum<2";
			List<HashMap<String, Object>> lstPerson = htmlbs.getListBySQL(sql);
			if (lstPerson.size()>0) {
				HashMap<String, Object> map = lstPerson.get(0);
				personName = (String)map.get("tp0101");
				 
			}
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			e.printStackTrace(); 
		}
		return personName;
	}
	
	/***
	 * �ϴ��ļ�
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("impBtn.onclick")
	public int impBtn() throws RadowException, AppException {
//		if ("1".equals(this.getPageElement("isKccl").getValue())){
//			String a0000 = this.getPageElement("a0000").getValue();
//			String ynId = this.getPageElement("ynId").getValue();
//			String ynType = this.getPageElement("tp0116").getValue();
//			
//			HBSession sess = HBUtil.getHBSession();
//			List<TpbAtt> tpbAttlist = sess.createQuery("from TpbAtt where rb_id='"+ynId+"' and js0100='"+a0000+"'").list();
//			if(tpbAttlist!=null){
//				for(TpbAtt jsatt : tpbAttlist){
//					this.deleteFile(jsatt.getJsa00());
//				}
//			} 
//		}
		this.getExecuteSG().addExecuteCode("wait();");
		this.upLoadFile("file5");
		this.getExecuteSG().addExecuteCode(" for (var i=0;i<=10;i++) setTimeout(\"onUploadSuccess_new(i==10)\",5000);");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/***
	 * ���ز���
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("downBtn.onclick")
	public int downBtn() throws RadowException, AppException {
		String a0000 = this.getPageElement("a0000").getValue();
		String ynId = this.getPageElement("ynId").getValue();
		HBSession sess = HBUtil.getHBSession();

		if (a0000 == null || "".equals(a0000)) {
			// ����Excel���Ͳ�����ļ�
			return EventRtnType.NORMAL_SUCCESS;
		}

		List<Map<String,String>> lstFilePath = new ArrayList<Map<String,String>>();
		try {
			// �����ļ���Ϣ
			List<TpbAtt> tpbAttlist = sess
					.createQuery("from TpbAtt where rb_id='" + ynId + "' and js0100='" + a0000 + "'").list();

			if (tpbAttlist != null) {
				for (TpbAtt jsatt : tpbAttlist) {
					String JSA04 = jsatt.getJsa04();
					String JSA07 = jsatt.getJsa07();
					String rb_id = jsatt.getRbId();
					String JSA00 = jsatt.getJsa00();

					int extIndex = JSA04.lastIndexOf(".");
					String extName = JSA04.substring(extIndex, JSA04.length());
					String filePath = disk + "zhgbuploadfiles" + "/" + rb_id + "/" + JSA00
							+ extName;
					Map<String,String> row = new HashMap<String,String>();
					row.put("filePath", filePath);
					row.put("fileName", JSA04);
					lstFilePath.add(row);
				}

			}
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			e.printStackTrace();
			return EventRtnType.FAILD;
		} 
		try { 
			String downloadPath = "";
			String downloadName = "";
			
			if (lstFilePath.size() == 1) {// һ���ļ�ֱ������
				downloadPath = lstFilePath.get(0).get("filePath");
				downloadName = lstFilePath.get(0).get("fileName");
			} else if (lstFilePath.size() > 1) {// ѹ��
				String s = lstFilePath.get(0).get("filePath");
				if (s.lastIndexOf("\\")>0) {
					s = s.substring(0,s.lastIndexOf("\\")+1);
				}else {
					s = s.substring(0,s.lastIndexOf("/")+1);
				}
				SimpleDateFormat d = new SimpleDateFormat("yyyyMMddHHmmss");
				downloadPath = s + UUID.randomUUID().toString().toUpperCase().replaceAll("\\-", "")+ d.format(new Date()) + ".zip";
				//System.out.println(outDir);
				List<String> lstDir = new ArrayList<String>();
				for (Map<String,String> row :lstFilePath) {
					lstDir.add(row.get("filePath"));
				}
				String[] array = new String[lstDir.size()]; 
				lstDir.toArray(array);
				ZipUtil.toZip(array, downloadPath, true);
				
				String personName = getPersonName(a0000);
				//downloadName 
				downloadName = personName+ "�������"+d.format(new Date())+".zip";
			} else {
				return EventRtnType.NORMAL_SUCCESS;
			}
			String downloadUUID = UUID.randomUUID().toString();
			request.getSession().setAttribute(downloadUUID, new String[] { downloadPath, downloadName });
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='" + downloadUUID + "';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ��" + e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/****
	 * �õ��ϴ��ļ��б�
	 */
	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) {
		Map<String, String> map = new HashMap<String, String>();
		// ����ļ�����
		String isfile = formDataMap.get("Filename");
		// �ж��Ƿ��ϴ��˸�����û���ϴ��򲻽����ļ�����
		if (isfile != null && !isfile.equals("")) {
			try {
				String a0000 = formDataMap.get("a0000");
				String ynId = formDataMap.get("ynId");
				String ynType = formDataMap.get("tp0116");

//				HBSession sess = HBUtil.getHBSession();
//				List<TpbAtt> tpbAttlist = sess.createQuery("from TpbAtt where rb_id='"+ynId+"' and js0100='"+a0000+"'").list();
//				if(tpbAttlist!=null){
//					for(TpbAtt jsatt : tpbAttlist){
//						this.deleteFile(jsatt.getJsa00());
//					}
//				} 

				// ��ȡ����Ϣ
				for (FileItem fi : fileItem) {
					// FileItem fi = fileItem.get(0);
					DecimalFormat df = new DecimalFormat("#.00");
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
					// ����ļ�����1M����ʾM��С������ʾkb
					if (fi.getSize() < 1048576) {
						fileSize = (int) fi.getSize() / 1024 + "KB";
					}
					if (fi.getSize() < 1024) {
						fileSize = (int) fi.getSize() / 1024 + "B";
					}
					String id = saveFile(formDataMap, fi, fileSize);
					map.put("file_pk", id);
					map.put("file_name", isfile);
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return map;
	}

	public static String disk = YNTPFileExportBS.HZBPATH;

	/***
	 * �����ļ�
	 * @param formDataMap
	 * @param fi
	 * @param fileSize
	 * @return
	 * @throws Exception
	 */
	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// �����Ա��Ϣid
		String a0000 = formDataMap.get("a0000");
		String ynId = formDataMap.get("ynId");
		String ynType = formDataMap.get("tp0116");
		String postfix = "pdf";
		if (a0000 == null || "".equals(a0000)) {
			postfix = "xlsx";
		} else {
			postfix = "pdf";
		}

		if (a0000 == null || "".equals(a0000)) {
			// ����Excel�ļ�
			String filename = formDataMap.get("Filename");
			int extIndex = filename.lastIndexOf(".");
			String extName = filename.substring(extIndex, filename.length());

			String fName = filename.substring(0, extIndex);
			if (!("." + postfix).equalsIgnoreCase(extName)) {
				// wordתpdf
				filename = fName + "." + postfix;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			String directory = "zhgbuploadfiles" + File.separator + ynId + File.separator;
			String filePath = directory + UUID.randomUUID() + filename;
			File f = new File(disk + directory);
			if (!f.isDirectory()) {
				f.mkdirs();
			}
//			if(!("."+postfix).equalsIgnoreCase(extName)){
//
//			}else{
			fi.write(new File(disk + filePath));
//			}
			ImpExcel objImpExcel = new ImpExcel();
			objImpExcel.setYn_type(ynType);
			objImpExcel.setYnId(ynId);
			// ��ʼ�������ݿ�
			objImpExcel.doImpExcelData(disk + filePath);
			System.out.println(disk + filePath);

			// this.getExecuteSG().addExecuteCode("onUploadSuccess();");

			return "";
		} else {
			//���ļ�����һ��zip��ѹ����
			HBSession sess = HBUtil.getHBSession();
//			List<TpbAtt> tpbAttlist = sess.createQuery("from TpbAtt where rb_id='"+ynId+"' and js0100='"+a0000+"'").list();
//			if(tpbAttlist!=null){
//				for(TpbAtt jsatt : tpbAttlist){
//					this.deleteFile(jsatt.getJsa00());
//				}
//			} 
			String filename = formDataMap.get("Filename");
			int extIndex = filename.lastIndexOf(".");
			String extName = filename.substring(extIndex, filename.length());

			String fName = filename.substring(0, extIndex);
//			if(!("."+postfix).equalsIgnoreCase(extName)){
//				//wordתpdf
//				filename = fName+"."+postfix;
//			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			TpbAtt tat = new TpbAtt();
			tat.setJs0100(a0000);// ��Ա��Ϣ
			tat.setJsa00(UUID.randomUUID().toString());// ����
			tat.setRbId(ynId);// ����id
			tat.setJsa05(SysManagerUtils.getUserId());// �û�id
			tat.setJsa06(sdf.format(new Date()));// �ϴ�ʱ��
			tat.setJsa04(filename);

			String directory = "zhgbuploadfiles" + File.separator + ynId + File.separator;
			String filePath = directory + tat.getJsa00() + extName;

			File f = new File(disk + directory);

			if (!f.isDirectory()) {
				f.mkdirs();
			}
			if (!("." + postfix).equalsIgnoreCase(extName)) {
				// wordתpdf
				// ==========?????????????????=======
				// TestAspose2Pdf.doc2pdf(fi.getInputStream(), disk + filePath);
			} else {
				fi.write(new File(disk + filePath));
			}
			fi.write(new File(disk + filePath));
			tat.setJsa07(directory);
			tat.setJsa08(fileSize);
			sess.save(tat);
			sess.flush();
			return tat.getJsa00();
		}
	}
	/****
	 * ������Ϻ�ɾ���ļ�
	 */
	@Override
	public String deleteFile(String id) {
		try {
			HBSession sess = HBUtil.getHBSession();
			TpbAtt ja = (TpbAtt) sess.get(TpbAtt.class, id);
			if (ja == null) {
				return null;// ɾ��ʧ��
			}
			String directory = disk + ja.getJsa07();
			File f = new File(directory + id);
			if (f.isFile()) {
				f.delete();
			}
			sess.delete(ja);
			sess.flush();

			return id;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
