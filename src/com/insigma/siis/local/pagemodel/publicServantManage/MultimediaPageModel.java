package com.insigma.siis.local.pagemodel.publicServantManage;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.TpbAtt;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.xbrm2.YNTPFileExportBS;
import com.insigma.siis.local.pagemodel.xbrm2.impexcel.ImpExcel;
import com.insigma.siis.local.pagemodel.xbrm2.zsrm.ZipUtil;
import com.insigma.siis.local.util.StringUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.utils.DBUtils;

public class MultimediaPageModel extends PageModel implements JUpload {
	private LogUtil applog = new LogUtil();

	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String a0000 = this.getPageElement("a0000").getValue();//String a0000 = this.getRadow_parent_data();
		String a1700 = this.getPageElement("subWinIdBussessId2").getValue();
		if (a0000 == null || "".equals(a0000)) {//
			//this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//人员已审核则锁定
		/*if(DBUtils.isAudit(a0000)){
			this.getExecuteSG().addExecuteCode("lockINFO()");
		}*/
		//System.out.println("执行"+System.currentTimeMillis());
		this.setNextEventName("TrainInfoGrid.dogridquery");
		this.setNextEventName("TrainAddBtn.onclick");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/***
	 * 上传文件
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("impBtn.onclick")
	public int impBtn() throws RadowException, AppException {
		this.getExecuteSG().addExecuteCode("wait();");
		this.saveProfessSkill();
		this.upLoadFile("file5");
		this.getExecuteSG().addExecuteCode(" for (var i=0;i<=10;i++) setTimeout(\"onUploadSuccess_new(i==10)\",5000);");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 保存修改
	 */
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveProfessSkill()throws RadowException, AppException{		
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a1700 = this.getPageElement("subWinIdBussessId2").getValue();
		
		String jsa00 = this.getPageElement("jsa00").getValue();
		String jsa01 = this.getPageElement("jsa01").getValue();
		String jsa02 = this.getPageElement("jsa02").getValue();
		
		if(jsa00==null||"".equals(jsa00)){
			this.setMainMessage("请先选择一条多媒体信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select jsa00 from tpb_media where jsa00 = '"+jsa00+"'").uniqueResult();
			if(obj!=null){
				//判断a1700是否已经存在，如果存在，update；如果不存在，insert。
				sess.createSQLQuery("update tpb_media set jsa01 = '"+jsa01+"',jsa02 = '"+jsa02+"' where jsa00 = '"+jsa00+"'").executeUpdate();
			}else{
				sess.createSQLQuery("insert into tpb_media(jsa00,a0000,a1700,jsa01,jsa02) values ('"+jsa00+"','"+a0000+"','"+a1700+"','"+jsa01+"','"+jsa02+"')").executeUpdate();
			}
			sess.flush();
			//this.setMainMessage("保存成功!");
			
		} catch (Exception e) {
			e.printStackTrace();
			//this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("TrainInfoGrid.dogridquery");//刷新
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String closeCueWindowEX(){
		return "window.close();";
	}
	/**
	 * 多媒体列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainInfoGrid.dogridquery")
	@NoRequiredValidate
	public int professSkillgridQuery(int start,int limit) throws RadowException{
		String a1700 = this.getPageElement("subWinIdBussessId2").getValue();
		String sql = "select t.jsa00, t.jsa01, t.jsa02, t.jsa03, t.jsa04, t.jsa05, t.jsa06, t.jsa07 "
				+ "from tpb_media t where t.a1700 = '"+a1700+"' order by t.jsa01";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 多媒体新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainAddBtn.onclick")
	@NoRequiredValidate
	public int openprofessSkillWin(String id)throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String jsa00 = UUID.randomUUID().toString().replaceAll("-", "");
		this.getPageElement("jsa00").setValue(jsa00);
		this.getPageElement("jsa01").setValue("");
		this.getPageElement("jsa02").setValue("");

		String a1700 = this.getPageElement("subWinIdBussessId2").getValue();
		// 设置文件信息
		List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("a1700", a1700);
		map.put("jsa00", jsa00);	
		listmap.add(map);
			
		this.setFilesInfo("file5", listmap);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int professSkillOnRowClick() throws RadowException{ 
		//this.openWindow("professSkillAddPage", "pages.publicServantManage.ProfessSkillAddPage");
		//获取选中行index
		int index = this.getPageElement("TrainInfoGrid").getCueRowIndex();
		String jsa00 = this.getPageElement("TrainInfoGrid").getValue("jsa00",index).toString();
		
		HBSession sess = HBUtil.getHBSession();
		Object[] objs = (Object[]) sess.createSQLQuery("select t.jsa01,t.jsa02,t.jsa00,t.jsa03,t.jsa07 from tpb_media t where t.jsa00 = '"+jsa00+"'").uniqueResult();
		if(objs!=null){
			this.getPageElement("jsa01").setValue(""+objs[0]);
			this.getPageElement("jsa02").setValue(""+objs[1]);
		}
		this.getPageElement("jsa00").setValue(jsa00);
		
		try {
			// 设置文件信息
			List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", ""+objs[2]);
			map.put("name", ""+objs[3]);
			map.put("fileSize", ""+objs[4]);
			listmap.add(map);
			this.setFilesInfo("file5", listmap);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String jsa00)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0600 = this.getPageElement("professSkillgrid").getValue("a0600",index).toString();*/
		String a0000 = this.getPageElement("a0000").getValue();
		HBSession sess = null;
		try {
			this.deleteFile(jsa00);
			
			sess = HBUtil.getHBSession();
			sess.createSQLQuery("delete from tpb_media where jsa00 = '"+jsa00+"'").executeUpdate();
			sess.flush();
			this.getExecuteSG().addExecuteCode("radow.doEvent('TrainInfoGrid.dogridquery')");
			
			this.getPageElement("jsa00").setValue("");
			this.getPageElement("jsa01").setValue("");
			this.getPageElement("jsa02").setValue("");
			
			this.setMainMessage("删除成功！");
			this.setNextEventName("TrainAddBtn.onclick");
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}


	/****
	 * 得到上传文件列表
	 */
	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) {
		Map<String, String> map = new HashMap<String, String>();
		// 获得文件名称
		String isfile = formDataMap.get("Filename");
		// 判断是否上传了附件，没有上传则不进行文件处理
		if (isfile != null && !isfile.equals("")) {
			try {
				// 获取表单信息
				for (FileItem fi : fileItem) {
					// FileItem fi = fileItem.get(0);
					DecimalFormat df = new DecimalFormat("#.00");
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
					// 如果文件大于1M则显示M，小于则显示kb
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
	 * 保存文件
	 * @param formDataMap
	 * @param fi
	 * @param fileSize
	 * @return
	 * @throws Exception
	 */
	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		System.out.println("执行了保存文件方法");
		// 获得人员信息id
		String a0000 = formDataMap.get("a0000");
		String jsa00 = formDataMap.get("jsa00");
		String subWinIdBussessId2 = formDataMap.get("subWinIdBussessId2");

		//多文件生成一个zip的压缩包
		HBSession sess = HBUtil.getHBSession();

		String filename = formDataMap.get("Filename");
		int extIndex = filename.lastIndexOf(".");
		String extName = filename.substring(extIndex, filename.length());
		//判断用户上传的文件是否是MP4类型的，如果不是则强转为MP4
		if(!extName.equals(".mp4")&&!extName.equals(".pdf")) {
			extName=".mp4";
		}
		String fName = filename.substring(0, extIndex);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		String directory = "multimediaFiles" + File.separator + jsa00 + File.separator;
		String filePath = directory + jsa00 + extName;
		//JSA03 附件名称   JSA04  上传人   JSA05  上传时间  JSA06   附件地址  JSA07
		sess.createSQLQuery("update tpb_media set JSA03 = '"+filename+"',JSA04 = '',"
				+ "JSA05 = '"+sdf.format(new Date())+"',JSA06 = '"+directory+"',JSA07 = '"+fileSize+"' where jsa00 = '"+jsa00+"'").executeUpdate();
		
		/*TpbAtt tat = new TpbAtt();
		tat.setJs0100(a0000);// 人员信息
		tat.setJsa00(UUID.randomUUID().toString());// 主键
		tat.setRbId(ynId);// 批次id
		tat.setJsa05(SysManagerUtils.getUserId());// 用户id
		tat.setJsa06(sdf.format(new Date()));// 上传时间
		tat.setJsa04(filename);*/

		File f = new File(disk + directory);
		if (!f.isDirectory()) {
			f.mkdirs();
		}

		fi.write(new File(disk + filePath));
		
		/**
		 * 上传成功后判断后缀是否是pdf，如果是就进行缩略图转换
		 */
		if(extName.equals(".pdf")) {
			getThumbnailAndPath(disk+filePath);
		}
		/*tat.setJsa07(directory);
		tat.setJsa08(fileSize);
		sess.save(tat);*/
		sess.flush();
		return jsa00;
	}
	/****
	 * 下载完毕后删除文件
	 */
	@Override
	public String deleteFile(String id) {
		try {
			HBSession sess = HBUtil.getHBSession();
			/*TpbAtt ja = (TpbAtt) sess.get(TpbAtt.class, id);
			if (ja == null) {
				return null;// 删除失败
			}*/
			Object obj = sess.createSQLQuery("select t.jsa06 from tpb_media t where t.jsa00 = '"+id+"'").uniqueResult();
			
			String directory = disk + obj;
			File f = new File(directory);
			if (f.exists()) {
				deleteFile(f);
			}

			return id;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	/***
	 * 下载材料
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("downBtn")
	public int downBtn(String jsa00) throws RadowException, AppException {

		String downloadUUID = genericFile(jsa00);
		if(!StringUtil.isEmpty(this.getMainMessage())) {
			return EventRtnType.FAILD;
		}
		if(request.getSession().getAttribute(downloadUUID)!=null) {
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='" + downloadUUID + "';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/***
	 * 播放材料
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("playBtn")
	public int playBtn(String jsa00) throws RadowException, AppException {
		System.out.println("视频ID："+jsa00);
		String downloadUUID = genericFile(jsa00);
		if(!StringUtil.isEmpty(this.getMainMessage())) {
			return EventRtnType.FAILD;
		}
		if(request.getSession().getAttribute(downloadUUID)!=null) {
			//this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='" + downloadUUID + "';");
			//获取播放路径
			String [] str=(String[]) request.getSession().getAttribute(downloadUUID);
			String fileName=str[0].substring(str[0].indexOf("s")+1, str[0].length());
			System.out.println(fileName);
			//获取人员ID
			String a0000= this.getPageElement("a0000").getValue();
			this.getExecuteSG().addExecuteCode("showExtPadData('"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"
					+"playVideo"+fileName+"?method=playFile&jsa00="+jsa00+"&a0000="+a0000+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private String genericFile(String jsa00) throws RadowException {
		String downloadUUID = UUID.randomUUID().toString();
		String a0000 = this.getPageElement("a0000").getValue();
		HBSession sess = HBUtil.getHBSession();

		List<Map<String,String>> lstFilePath = new ArrayList<Map<String,String>>();
		try {
			// 设置文件信息
			List<Object[]> tpbAttlist = sess.createSQLQuery("select t.jsa00,t.jsa03,t.jsa06 from tpb_media t where t.jsa00 = '"+jsa00+"'").list();

			if (tpbAttlist != null) {
				for (Object[] jsatt : tpbAttlist) {
					String JSA03 = ""+jsatt[1];//附件名称
					String JSA06 = ""+jsatt[2];//附件地址
					System.out.println("JSA06"+JSA06);
					int extIndex = JSA03.lastIndexOf(".");
					String extName = JSA03.substring(extIndex, JSA03.length());
					String filePath = disk + "multimediaFiles" + "/" + jsa00 + "/" + jsa00 + extName;
					Map<String,String> row = new HashMap<String,String>();
					row.put("filePath", filePath);
					row.put("fileName", JSA03);
					lstFilePath.add(row);
				}

			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
			return downloadUUID;
		} 
		try { 
			String downloadPath = "";
			String downloadName = "";
			
			if (lstFilePath.size() == 1) {// 一个文件直接下载
				downloadPath = lstFilePath.get(0).get("filePath");
				downloadName = lstFilePath.get(0).get("fileName");
			} else if (lstFilePath.size() > 1) {// 压缩
				/*String s = lstFilePath.get(0).get("filePath");
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
				downloadName = personName+ "考察材料"+d.format(new Date())+".zip";*/
			} else {
				return downloadUUID;
			}
			request.getSession().setAttribute(downloadUUID, new String[] { downloadPath, downloadName });
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败" + e.getMessage());
		}
		return downloadUUID;
	}
	/**
	 * 先根遍历序递归删除文件夹
	 *
	 * @param dirFile 要被删除的文件或者目录
	 * @return 删除成功返回true, 否则返回false
	 */
	public static boolean deleteFile(File dirFile) {
	    // 如果dir对应的文件不存在，则退出
	    if (!dirFile.exists()) {
	        return false;
	    }
	    if (dirFile.isFile()) {
	        return dirFile.delete();
	    } else {
	        for (File file : dirFile.listFiles()) {
	            deleteFile(file);
	        }
	    }
	    return dirFile.delete();
	}
	/**
	 * 获取PDF缩略图码与路径
	 * @param filePath 文件路径
	 * @return
	 */
	private void getThumbnailAndPath(String filePath){
		// load a pdf from a byte buffer
		ByteBuffer buf=null;
		FileChannel channel=null;
		RandomAccessFile raf = null;
		FileOutputStream out=null;
			try {
				File file = new File(filePath);
				raf = new RandomAccessFile(file, "r");
				channel = raf.getChannel();
				//这句代码通道建立了map映射,如果要删除file那么得接触映射
				 buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
				channel.size());
				PDFFile pdffile = new PDFFile(buf);
				int totalpage =pdffile.getNumPages();
				for (int i = 1; i <= totalpage; i++) {
					if (i == 1) {
						// draw the first page to an image
						// 以图片的形式来描绘首页
						PDFPage page = pdffile.getPage(i);
						Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());
						// generate the image
						// 生成图片
						Image img = page.getImage(rect.width, rect.height, // width &
							// height
							rect, // clip rect
							null, // null for the ImageObserver
							true, // fill background with white
							true // block until drawing is done
							);
						BufferedImage tag = new BufferedImage(rect.width, rect.height,
						BufferedImage.TYPE_INT_RGB);
						tag.getGraphics().drawImage(img.getScaledInstance(rect.width, rect.height, Image.SCALE_SMOOTH), 0, 0, rect.width, rect.height,null);
					    	/*
					 * ByteArrayOutputStream out = new ByteArrayOutputStream();
					 * System.out.println("out:"+out); ImageIO.write(tag, "jpg", out);
					 * System.out.println("写入完毕"); byte[] smallContent = out.toByteArray();
					 * System.out.println(smallContent.length); //进行base64加密 data = new
					 * String(Base64.decodeBase64(smallContent));
					 */
					 
					
					   String outPath = filePath.substring(0,filePath.lastIndexOf("."));
					   out = new FileOutputStream( outPath+ ".jpg"); //
					  //输出到文件流 
					  JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					  encoder.encode(tag); // JPEG编码
						break;
					}
				}
			}catch (IOException e) {
				// TODO: handle exception
			}finally {
			  try {
				if(buf!=null) {
					buf.clear();
				}
				if(channel!=null) {
					channel.close();
				}
				if(raf!=null) {
					raf.close();
				}
				if(out!=null) {
					out.close();
				}
			  }catch (IOException e) {
				// TODO: handle exception
			}
			}
				
	}
	//获取网页标题
		private String getDocumentTitle(String a1700,String a0000) {
			HBSession session = HBUtil.getHBSession();
			Object[] obj = (Object[])session.createSQLQuery("select a0101,start_date,end_date,entry_content from a01,a17 where a01.a0000='"+a0000+"' and a1700='"+a1700+"'").uniqueResult();
			String name = (String)obj[0];//获取姓名
			StringBuilder start = new StringBuilder((String)obj[1]);//获取开始时间
			StringBuilder end = new StringBuilder((String)obj[2]);//获取结束时间
			String entfyContent=(String)obj[3];//获取详情内容
			//拼接字符串
			String str = name+" "+start.insert(4, ".")+"--"+end.insert(4, ".")+" "+entfyContent;
			return str;
		}
}
