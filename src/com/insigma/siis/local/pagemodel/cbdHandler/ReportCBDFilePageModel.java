package com.insigma.siis.local.pagemodel.cbdHandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import org.hibernate.Transaction;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.AttachmentInfo;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CBDInfo;
import com.insigma.siis.local.business.entity.Cbdstatus;
import com.insigma.siis.local.business.entity.Reportftp;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzFtpClient;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class ReportCBDFilePageModel extends PageModel {

	public static String cbdid = "";
	public static String cbdname = "";
	public static String bj_cbdid = "";
	public static String personname = "";
	public static String personid = "";
	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		String cbd_id = this.getRadow_parent_data();
		cbdid = cbd_id.split("@")[0];
		cbdname = cbd_id.split("@")[1];
		bj_cbdid = cbd_id.split("@")[2];
		personname = cbd_id.split("@")[3];
		personid = cbd_id.split("@")[4];
		return 0;
	}

	/**
	 *  打开ftp选择窗口
	 * @param name
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("searchDeptBtn.ontriggerclick")
	@NoRequiredValidate
	public int searchDept(String name) throws RadowException {
		this.openWindow("deptWin", "pages.repandrec.plat.SearchFtpUp");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 *  上报呈报单（ftp传送）
	 * @param name
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("reppackagebtn.onclick")
	public int reppackagebtn() throws RadowException, AppException {
		
		//先进行上报附件的处理（整理/压缩）
		String zipPath = collectAttachment(cbdid,cbdname);
		//判断附件情况，如果附件不足，不可以上报呈报单
		if("noCBDAtta".equals(zipPath)){
			this.setMainMessage("还没有上传呈报单附件，不能上报！");
			return EventRtnType.FAILD;
		}else if("noPersonAtta".equals(zipPath.split("@")[0])){
			this.setMainMessage(zipPath.split("@")[1]+"还没有上传任何附件，不能上报！");
			return EventRtnType.FAILD;
		}
		String zipPath1 = zipPath.split("@")[2];
		Map<String, String> map = new HashMap<String, String>();
		
		HBSession sess = HBUtil.getHBSession();
		try {
			//获取登录用户的信息
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0111 ='"+user.getOtherinfo()+"' ").list();
			B01 b01 = null;
			if (b01s != null && b01s.size() > 0) {
				b01 = b01s.get(0);
			} else {
				this.setMainMessage("系统出错，请联系管理员！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String searchDeptid = b01.getB0111();
			//获取页面中ftp信息
			String ftpid = this.getPageElement("ftpid").getValue();
			//获取页面中联系人信息
			String linkpsn = this.getPageElement("linkpsn").getValue();
			//获取页面中联系人电话信息
			String linktel = this.getPageElement("linktel").getValue();
			//获取页面中备注信息
			String remark = this.getPageElement("remark").getValue();

			int count = 1;
			
			//定义时间变量
			java.sql.Timestamp now = DateUtil.getTimestamp();
			String time = DateUtil.timeToString(now);
			String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
			//创建ftp上报信息xml对象
			ZwhzPackDefine info = new ZwhzPackDefine();
			
			//String sid = UUID.randomUUID().toString().replace("-", "");
			info.setId(cbdid);
			info.setB0101(b01.getB0101());
			info.setB0111(b01.getB0111());
			info.setB0114(b01.getB0114());
			info.setB0194(b01.getB0194());

			info.setDataversion(DateUtil.dateToString(DateUtil.getSysDate(),
					"yyyyMMdd"));
			info.setLinkpsn(linkpsn);
			info.setLinktel(linktel);
			info.setRemark(remark);
			info.setStype("11");
			info.setStypename("呈报单上报");
			info.setTime(time);
			info.setTranstype("up");
			info.setErrortype("无");
			info.setErrorinfo("无");
			info.setCbdStatus("0");//设置呈报单状态  0：未接收
			info.setPersonid(personid);
			info.setPersonname(personname);
			
			List<SFileDefine> sfile = new ArrayList<SFileDefine>();
			//定义xml文件名
			String packageFile = "Pack_" +zipPath.split("@")[3] + ".xml";
			
			for (int i = 1; i <= count; i++) {
				
				SFileDefine sf = new SFileDefine();

				map.put("type", "11");
				map.put("time", time);
				map.put("dataversion", "20121221");
				map.put("photodir", "Photos");
				map.put("B0101", b01.getB0101());
				map.put("B0111", b01.getB0111());
				map.put("B0114", b01.getB0114());
				map.put("B0194", b01.getB0194());
				map.put("linkpsn", linkpsn);
				map.put("linktel", linktel);
				map.put("remark", remark);
				sf.setTime(time);

				String number = ("000" + i).substring(("000" + i).length() - 3);
				//要上报的文件
				String zipfile =zipPath.split("@")[0];
				
				//SevenZipUtil.zip7z(zippath, zipfile, "1234");
				File file0 = new File(zipfile);
				sf.setName(file0.getName());
				// InputStream f1 = new FileInputStream(zipfile);
				// int size = f1.available();
				sf.setSize(getFileSize(file0));
				sfile.add(sf);
			}
			//记录文件信息
			info.setDatainfo("呈报单附件1个，文件夹"+ zipPath.split("@")[1] + "个。");
			
			info.setSfile(sfile);
			
			CommonQueryBS.systemOut(JXUtil.Object2Xml(info, true));
			//创建xml文件
			FileUtil.createFile(zipPath1 + "/" + packageFile,
					JXUtil.Object2Xml(info, true), false, "UTF-8");
			//判断是否已选择ftp地址
			if (StringUtil.isEmpty(ftpid)) {
				throw new AppException("上报单位为空，请重新选择！");
			}
			//创建ftp连接对象
			TransConfig jfcc = (TransConfig) HBUtil.getHBSession().get(
					TransConfig.class, ftpid);

			Reportftp rfpt = new Reportftp();
			rfpt.setFilename("");
			rfpt.setPackageindex(cbdid);
			rfpt.setPackagename(packageFile);
			rfpt.setRecieveftpuserid(jfcc.getId());
			rfpt.setRecieveftpusername(jfcc.getName());
			rfpt.setReporttime(DateUtil.getTimestamp());
			rfpt.setB0111(b01.getB0111());
			rfpt.setReporttype("1");
			rfpt.setReportuser(SysUtil.getCacheCurrentUser().getId());
			rfpt.setReportusername(SysUtil.getCacheCurrentUser().getName());
			
			//开始上报
			try {
				sess.beginTransaction();
				sess.save(rfpt);
				
				//记录呈报单步骤
				String uuid = UUID.randomUUID().toString();
				//获取时间
				SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
				String CBD_STATUS_TIME = sdf.format(new Date());
				Cbdstatus cbdstatus = new Cbdstatus();
				cbdstatus.setStatusid(uuid);
				cbdstatus.setCbdstatusstep("5");
				cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
				cbdstatus.setCbdid(bj_cbdid);
				sess.save(cbdstatus);
				
				//上报方法
				ZwhzFtpClient.uploadCBD(jfcc, zipPath1 + "/" + packageFile);
				sess.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				sess.getTransaction().rollback();
				throw new AppException(e.getMessage());

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("上报异常！异常信息：" + e.getMessage());

		}
		this.setMainMessage("呈报单上报成功");
		this.getExecuteSG().addExecuteCode("parent.setNextStep('5')");
		this.closeCueWindow("ReportCBD");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 工具方法：获取路径
	 * @return
	 */
	private String getPath() {
		// TODO Auto-generated method stub
		String classPath = getClass().getClassLoader().getResource("/")
				.getPath();
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath = "";

		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1,
					classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0,
					classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}
	
	public static Long getFileSize(File f) {
		FileChannel fc = null;
		try {
			if (f.exists() && f.isFile()) {
				FileInputStream fis = new FileInputStream(f);
				fc = fis.getChannel();
				return fc.size();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fc) {
				try {
					fc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 0L;
	}
	
	/**
	 *  整理呈报单相关的附件，并压缩，将压缩包路径作为返回值
	 * @return
	 */
	public String collectAttachment(String cbd_id,String cbd_name){
		
		//获取服务器路径
		String path = getPath();
		//压缩路径
		String zipPath = path +"CBDUpload/CBDZip";
		//创建vector，用来存放附件路径信息
		Vector vector = new Vector();
		//执行数据库操作
		HBSession sess = HBUtil.getHBSession();
		//查询呈报单对应的附件信息
		String hql = "from AttachmentInfo ai where ai.objectid = '"+cbd_id+"'";
		List<AttachmentInfo> attach_list =  sess.createQuery(hql).list();
		if(attach_list.size()<=0){
			return "noCBDAtta";
		}
		//获取呈报单名称
		String name = attach_list.get(0).getFilename();
		String cbdAttachPath = attach_list.get(0).getFilepath();
		
		//查询呈报单信息
		hql = "from CBDInfo ci where ci.cbd_id = '"+cbd_id+"'";
		CBDInfo cbdinfo = (CBDInfo) sess.createQuery(hql).list().get(0);
		//获取呈报单对应人员编号
		String personid = cbdinfo.getCbd_personid();
		//截取人员编号
		String[] personids = personid.split(",");
		String[] personnames = cbdinfo.getCbd_personname().split(",");
		
		//循环查询人员的附件信息
		for(int i =0;i<personids.length;i++){
			hql = "from AttachmentInfo ai where ai.personid = '"+personids[i]+"'";
			List<AttachmentInfo> atta = sess.createQuery(hql).list();
			//判断是否有人员的附件信息，如果有人员没有上传过附件，不能上报
			if(atta.size()<=0){
				//return "noPersonAtta@"+personnames[i];
				continue;
			}
			vector.add(atta.get(0).getPersonname()+atta.get(0).getIdcard());
		}
		
		//定义时间变量
		java.sql.Timestamp now = DateUtil.getTimestamp();
		String time = DateUtil.timeToString(now);
		String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
		
		//压缩路径添加呈报单名称的文件夹，用以存放呈报单相关的所有附件
		zipPath = zipPath+"/"+cbd_name+time1;
		//判断目录是否存在，如果不存在则创建目录
		try {
			File file = new File(zipPath);
			if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//获取呈报单附件的后辍
		String houzhui = cbdAttachPath.substring(cbdAttachPath.lastIndexOf("."), cbdAttachPath.length());
		//将呈报单附件拷贝到压缩路径下
		try {
			copyFile(new File(path+cbdAttachPath),new File(zipPath+"/"+name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//复制人员的文件夹
		for(int i=0;i<vector.size();i++){
			try {
				copyDirectiory(path+"CBDUpload/DJBOrOtherAttachment/"+vector.get(i),zipPath+"/"+vector.get(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//压缩文件路径
		String zip = zipPath+"/"+cbd_name+"_"+time1+".zip";
		//将文件夹压缩（zip压缩包）
		SevenZipUtil.zip7z(zipPath, zip, null);
		//删除原文件和文件夹
		File file_oldcbd = new File(zipPath+"/"+name);
		file_oldcbd.getAbsoluteFile();
		file_oldcbd.delete();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//复制人员的文件夹
		for(int i=0;i<vector.size();i++){
			try {
				delDirectiory(zipPath+"/"+vector.get(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.gc();
		
		//保存呈报单压缩文件信息
		String uuid =  UUID.randomUUID().toString();//获取数据表的主键值
		AttachmentInfo atta= new AttachmentInfo();
		atta.setId(uuid);
		atta.setObjectid(cbd_id+"u");//呈报单主键后面加标志符“u”，标识该条记录为呈报单数据压缩包
		atta.setUploaddate(time);
		atta.setFilepath("CBDUpload/CBDZip"+"/"+name+time1+"/"+cbd_name+"_"+time1+".zip");
		atta.setFiletype("2");
		atta.setFilename(cbd_name);
		
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		atta.setUserid(user.getId());
		atta.setUsername(user.getName());
		sess.saveOrUpdate(atta);
		
		return zip+"@"+personids.length+"@"+zipPath+"@"+cbd_name+"_"+time1;
	}
	
	/**
	 * 复制文件
	 * @param sourcefile 源文件路径
	 * @param targetFile  目标文件路径
	 * @throws IOException
	 */
	public static void copyFile(File sourcefile, File targetFile)throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourcefile);
		BufferedInputStream inbuff = new BufferedInputStream(input);
		// 新建文件输出流并对它进行缓冲
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);
		
		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outbuff.flush();
		// 关闭流
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();
		
	}
	
	/**
	 * 复制整个文件夹
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static void copyDirectiory(String sourceDir, String targetDir)
	throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(new File(targetDir)
						.getAbsolutePath()
						+ File.separator + file[i].getName());
		
				copyFile(sourceFile, targetFile);
		
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
		
	}
	
	/**
	 * 删除整个文件夹
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static void delDirectiory(String sourceDir)
	throws IOException {
		// 新建目标目录
		// 获取源文件夹当下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				sourceFile.getAbsoluteFile();
				sourceFile.delete();
		
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + file[i].getName();
				delDirectiory(dir1);
			}
		}
		File file_d = new File(sourceDir);
		file_d.getAbsoluteFile();
		file_d.delete();
	}
			
	
}
