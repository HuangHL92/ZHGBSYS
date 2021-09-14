package com.insigma.siis.local.pagemodel.yntp;


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

public class UploadKcclPageModel extends PageModel implements JUpload {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		String ynId = this.getPageElement("ynId").getValue();
		HBSession sess = HBUtil.getHBSession();
		
		try {
			//设置文件信息
			List<TpbAtt> tpbAttlist = sess.createQuery("from TpbAtt where rb_id='"+ynId+"' and js0100='"+a0000+"'").list();
			
			if(tpbAttlist!=null){
				List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
				for(TpbAtt jsatt : tpbAttlist){
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", jsatt.getJsa00());
					map.put("name", jsatt.getJsa04());
					map.put("fileSize", jsatt.getJsa08());
					listmap.add(map);
				}
				this.setFilesInfo("file5",listmap);
				
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("impBtn.onclick")
	public int impBtn() throws RadowException, AppException{
		this.getExecuteSG().addExecuteCode("wait();");
		this.upLoadFile("file5");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) {
		Map<String, String> map = new HashMap<String, String>();
		// 获得文件名称
		String isfile = formDataMap.get("Filename");
		// 判断是否上传了附件，没有上传则不进行文件处理
		if (isfile != null && !isfile.equals("")) {
			try {
				// 获取表单信息
				FileItem fi = fileItem.get(0);
				DecimalFormat df = new DecimalFormat("#.00");
				String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
				// 如果文件大于1M则显示M，小于则显示kb
				if (fi.getSize() < 1048576) {
					fileSize = (int) fi.getSize() / 1024 + "KB";
				}
				if (fi.getSize() < 1024) {
					fileSize = (int) fi.getSize() / 1024 + "B";
				}
				String id = saveFile(formDataMap, fi,fileSize);
				map.put("file_pk", id);
				map.put("file_name", isfile);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return map;
	}
	public static String  disk = YNTPFileExportBS.HZBPATH;
	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// 获得人员信息id
		String a0000 = formDataMap.get("a0000");
		String ynId = formDataMap.get("ynId");
		HBSession sess = HBUtil.getHBSession();
		List<TpbAtt> tpbAttlist = sess.createQuery("from TpbAtt where rb_id='"+ynId+"' and js0100='"+a0000+"'").list();
		if(tpbAttlist!=null){
			for(TpbAtt jsatt : tpbAttlist){
				this.deleteFile(jsatt.getJsa00());
			}
		}
		
		
		String filename = formDataMap.get("Filename");
		int extIndex = filename.lastIndexOf(".");
		String extName = filename.substring(extIndex,filename.length());
		
		String fName = filename.substring(0,extIndex);
		if(!".pdf".equalsIgnoreCase(extName)){
			//word转pdf
			filename = fName+".pdf";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		TpbAtt tat = new TpbAtt();
		tat.setJs0100(a0000);//人员信息
		tat.setJsa00(UUID.randomUUID().toString());//主键
		tat.setRbId(ynId);//批次id
		tat.setJsa05(SysManagerUtils.getUserId());//用户id
		tat.setJsa06(sdf.format(new Date()));//上传时间
		tat.setJsa04(filename);
		
		String directory = "zhgbuploadfiles" + File.separator +ynId+ File.separator;
		String filePath = directory  + tat.getJsa00();
		
		File f = new File(disk + directory);
		
		if(!f.isDirectory()){
			f.mkdirs();
		}
		if(!".pdf".equalsIgnoreCase(extName)){
			//word转pdf
			TestAspose2Pdf.doc2pdf(fi.getInputStream(), disk + filePath);
		}else{
			fi.write(new File(disk + filePath));
		}
		
		tat.setJsa07(directory);
		tat.setJsa08(fileSize);
		sess.save(tat);
		sess.flush();
		
		return tat.getJsa00();
	}

	@Override
	public String deleteFile(String id) {
		try {
			HBSession sess = HBUtil.getHBSession();
			TpbAtt ja = (TpbAtt)sess.get(TpbAtt.class, id);
			if(ja==null){
				return null;//删除失败
			}
			String directory = disk+ja.getJsa07();
			File f = new File(directory+id);
			if(f.isFile()){
				f.delete();
			}
			sess.delete(ja);
			sess.flush();
			
			return id;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
}
