package com.insigma.siis.local.jtrans;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.Ftpuser;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;

/**
 * ����������Ŀ¼��
 * @author gezh
 *
 */
public class IteratorUD {
	
	
	/**
	 * ��ʼ����������Ŀ¼
	 * @param param 
	 * @return
	 */
	private static List<String> initDirPaths(String ftpHomeDir, List<String> param){
		List<String> dirPaths = new ArrayList<String>();
		if(param.contains("ALL")){
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.HZB_UP);
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.FK_UP);		
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.OTHER_UP);
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.DM_UP);
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.JC_UP);
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.ZB_UP);
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.CBD_UP);
			return dirPaths;
		}
		if(param.contains("HZB")){
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.HZB_UP);
		}
		if(param.contains("FK")){
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.FK_UP);		
		}
		if(param.contains("OTHER")){
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.OTHER_UP);
		}
		if(param.contains("DM")){
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.DM_UP);
		}
		if(param.contains("JC")){
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.JC_UP);
		}
		if(param.contains("ZB")){
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.ZB_UP);
		}
		if(param.contains("CBD")){
			dirPaths.add(ftpHomeDir+ZwhzFtpPath.CBD_UP);
		}
		return dirPaths;
	}
	private static List<String> initDirPaths2(List<String> param){
		List<String> dirPaths = new ArrayList<String>();
		if(param.contains("ALL")){
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.HZB_DOWN);
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.FK_DOWN);
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_DOWN);
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.DM_DOWN);
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.JC_DOWN);
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.ZB_DOWN);
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.CBD_DOWN);
			return dirPaths;
		}
		if(param.contains("HZB")){
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.HZB_DOWN);
		}
		if(param.contains("FK")){
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.FK_DOWN);
		}
		if(param.contains("OTHER")){
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_DOWN);
		}
		if(param.contains("DM")){
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.DM_DOWN);
		}
		if(param.contains("JC")){
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.JC_DOWN);
		}
		if(param.contains("ZB")){
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.ZB_DOWN);
		}
		if(param.contains("CBD")){
			dirPaths.add(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.CBD_DOWN);
		}
		return dirPaths;
	}
	/**
	 * ���������ļ�Ŀ¼
	 * @return
	 * @throws Exception
	 */
	public static List<UDPackFile> iteratorDir() throws Exception{
		List<UDPackFile> udpackFiles = new ArrayList<UDPackFile>();
		String packXmlStr = null;//�ܰ��ļ������ַ���
		ZwhzPackDefine zpdefine = null;//�ܰ��ļ���������
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    Calendar cal = null;
	    List<String> dirpaths = null;//����Ŀ¼����Ϣ
		try{
			HBSession sess = HBUtil.getHBSession();
			List<Ftpuser> ftpus = sess.createQuery("from Ftpuser t").list();
			for (Ftpuser ftpuser : ftpus) {
				dirpaths = initDirPaths(ftpuser.getHomedirectory(),null);
				for (String dirPath : dirpaths) {
					File subDir = new File(dirPath);
					if(!subDir.exists()){
						continue;
					}
					if(dirPath.indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_DOWN)<0 && dirPath.indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_UP)<0){
						File[] subFiles = subDir.listFiles(new PackFilter("pack_", ".xml"));
						for (File file : subFiles) {
							try{
								UDPackFile udpf = new UDPackFile();
								udpf.setFileName(file.getName());
								udpf.setFilePath(StringUtils.replace(file.getPath(), "\\", "/"));
								packXmlStr = FileUtil.readFileByChars(file.getPath(),"UTF-8"); //��ȡ�ܰ��ļ�����
								if(packXmlStr!=null){
									zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //�����ܰ��ļ�
									udpf.setCreateTime(zpdefine.getTime());
									udpf.setOrgId(zpdefine.getB0111());
									udpf.setOrgName(zpdefine.getB0101());
									udpf.setTransType(zpdefine.getTranstype());
									udpf.setDataInfo(zpdefine.getDatainfo());
									udpf.setFilePublishType(zpdefine.getStype());
									udpf.setIsOther("0");//��otherĿ¼�ļ�
									if("10".equals(zpdefine.getStype())){//���ϱ�����Ϊ10���ʱ���ʱ����ȡ�ʱ���״̬��Ϣ
										udpf.setStatus(zpdefine.getCbdStatus());
										udpf.setCbd_id(zpdefine.getId());
									}
								}
								udpackFiles.add(udpf);
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}else{
						File[] subFiles = subDir.listFiles();
						for (File file : subFiles) {
							UDPackFile udpf = new UDPackFile();
							udpf.setFileName(file.getName());
							udpf.setFilePath(file.getPath());
							udpf.setIsOther("1");
							if(file.getPath().indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_DOWN)>0){
								udpf.setTransType("down");
							}else if(file.getPath().indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_UP)>0){
								udpf.setTransType("up");
							}
							cal = Calendar.getInstance();  
							long time = file.lastModified();  
						    cal.setTimeInMillis(time);  
						    udpf.setFilePublishType("");
							udpf.setCreateTime(sdf.format(cal.getTime()));
							udpackFiles.add(udpf);
						}
					}
				}
			}
			 List<String> dirpaths2 = initDirPaths2(null);
			 for (String dirPath : dirpaths2) {
					File subDir = new File(dirPath);
					if(!subDir.exists()){
						continue;
					}
					if(dirPath.indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_DOWN)<0 && dirPath.indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_UP)<0){
						File[] subFiles = subDir.listFiles(new PackFilter("pack_", ".xml"));
						for (File file : subFiles) {
							try{
								UDPackFile udpf = new UDPackFile();
								udpf.setFileName(file.getName());
								udpf.setFilePath(StringUtils.replace(file.getPath(), "\\", "/"));
								packXmlStr = FileUtil.readFileByChars(file.getPath(),"UTF-8"); //��ȡ�ܰ��ļ�����
								if(packXmlStr!=null){
									zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //�����ܰ��ļ�
									udpf.setCreateTime(zpdefine.getTime());
									udpf.setOrgId(zpdefine.getB0111());
									udpf.setOrgName(zpdefine.getB0101());
									udpf.setTransType(zpdefine.getTranstype());
									udpf.setDataInfo(zpdefine.getDatainfo());
									udpf.setFilePublishType(zpdefine.getStype());
									udpf.setIsOther("0");//��otherĿ¼�ļ�
								}
								udpackFiles.add(udpf);
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}else{
						File[] subFiles = subDir.listFiles();
						for (File file : subFiles) {
							UDPackFile udpf = new UDPackFile();
							udpf.setFileName(file.getName());
							udpf.setFilePath(file.getPath());
							udpf.setIsOther("1");
							if(file.getPath().indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_DOWN)>0){
								udpf.setTransType("down");
							}else if(file.getPath().indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_UP)>0){
								udpf.setTransType("up");
							}
							cal = Calendar.getInstance();  
							long time = file.lastModified();  
						    cal.setTimeInMillis(time);  
						    udpf.setFilePublishType("");
							udpf.setCreateTime(sdf.format(cal.getTime()));
							udpackFiles.add(udpf);
						}
					}
				}
			return udpackFiles;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("�����ļ�Ŀ¼ʧ��:"+e.getMessage());
		}
	}
	/**
	 * �����ض�����������������Ӧ�µ��ļ�
	 * @param param  ����ָ���ļ����� ����HZB,FK,OTHER,DM,JC,ZB,CBD,
	 * @return
	 * @throws Exception
	 */
	public static List<UDPackFile> iteratorDir(List<String> param) throws Exception{
		List<UDPackFile> udpackFiles = new ArrayList<UDPackFile>();
		String packXmlStr = null;//�ܰ��ļ������ַ���
		ZwhzPackDefine zpdefine = null;//�ܰ��ļ���������
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    Calendar cal = null;
	    List<String> dirpaths = null;//����Ŀ¼����Ϣ
		try{
			HBSession sess = HBUtil.getHBSession();
			List<Ftpuser> ftpus = sess.createQuery("from Ftpuser t").list();
			for (Ftpuser ftpuser : ftpus) {
				dirpaths = initDirPaths(ftpuser.getHomedirectory(),param);
				for (String dirPath : dirpaths) {
					File subDir = new File(dirPath);
					if(!subDir.exists()){
						continue;
					}
					if(dirPath.indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_DOWN)<0 && dirPath.indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_UP)<0){
						File[] subFiles = subDir.listFiles(new PackFilter("pack_", ".xml"));
						for (File file : subFiles) {
							try{
								UDPackFile udpf = new UDPackFile();
								udpf.setFileName(file.getName());
								udpf.setFilePath(StringUtils.replace(file.getPath(), "\\", "/"));
								packXmlStr = FileUtil.readFileByChars(file.getPath(),"UTF-8"); //��ȡ�ܰ��ļ�����
								if(packXmlStr!=null){
									zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //�����ܰ��ļ�
									udpf.setCreateTime(zpdefine.getTime());
									udpf.setOrgId(zpdefine.getB0111());
									udpf.setOrgName(zpdefine.getB0101());
									udpf.setTransType(zpdefine.getTranstype());
									udpf.setDataInfo(zpdefine.getDatainfo());
									udpf.setFilePublishType(zpdefine.getStype());
									udpf.setIsOther("0");//��otherĿ¼�ļ�
									if("11".equals(zpdefine.getStype())){//���ϱ�����Ϊ10���ʱ���ʱ����ȡ�ʱ���״̬��Ϣ
										udpf.setStatus(zpdefine.getCbdStatus());
										udpf.setCbd_id(zpdefine.getId());
										udpf.setSfile(zpdefine.getSfile());
										udpf.setPersonid(zpdefine.getPersonid());
										udpf.setPersonname(zpdefine.getPersonname());
									}
								}
								udpackFiles.add(udpf);
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}else{
						File[] subFiles = subDir.listFiles();
						for (File file : subFiles) {
							UDPackFile udpf = new UDPackFile();
							udpf.setFileName(file.getName());
							udpf.setFilePath(file.getPath());
							udpf.setIsOther("1");
							if(file.getPath().indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_DOWN)>0){
								udpf.setTransType("down");
							}else if(file.getPath().indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_UP)>0){
								udpf.setTransType("up");
							}
							cal = Calendar.getInstance();  
							long time = file.lastModified();  
						    cal.setTimeInMillis(time);  
						    udpf.setFilePublishType("");
							udpf.setCreateTime(sdf.format(cal.getTime()));
							udpackFiles.add(udpf);
						}
					}
				}
			}
			 List<String> dirpaths2 = initDirPaths2(param);
			 for (String dirPath : dirpaths2) {
					File subDir = new File(dirPath);
					if(!subDir.exists()){
						continue;
					}
					if(dirPath.indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_DOWN)<0 && dirPath.indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_UP)<0){
						File[] subFiles = subDir.listFiles(new PackFilter("pack_", ".xml"));
						for (File file : subFiles) {
							try{
								UDPackFile udpf = new UDPackFile();
								udpf.setFileName(file.getName());
								udpf.setFilePath(StringUtils.replace(file.getPath(), "\\", "/"));
								packXmlStr = FileUtil.readFileByChars(file.getPath(),"UTF-8"); //��ȡ�ܰ��ļ�����
								if(packXmlStr!=null){
									zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //�����ܰ��ļ�
									udpf.setCreateTime(zpdefine.getTime());
									udpf.setOrgId(zpdefine.getB0111());
									udpf.setOrgName(zpdefine.getB0101());
									udpf.setTransType(zpdefine.getTranstype());
									udpf.setDataInfo(zpdefine.getDatainfo());
									udpf.setFilePublishType(zpdefine.getStype());
									udpf.setIsOther("0");//��otherĿ¼�ļ�
									if("11".equals(zpdefine.getStype())){//���ϱ�����Ϊ10���ʱ���ʱ����ȡ�ʱ���״̬��Ϣ
										udpf.setStatus(zpdefine.getCbdStatus());
										udpf.setCbd_id(zpdefine.getId());
										udpf.setSfile(zpdefine.getSfile());
									}
								}
								udpackFiles.add(udpf);
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}else{
						File[] subFiles = subDir.listFiles();
						for (File file : subFiles) {
							UDPackFile udpf = new UDPackFile();
							udpf.setFileName(file.getName());
							udpf.setFilePath(file.getPath());
							udpf.setIsOther("1");
							if(file.getPath().indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_DOWN)>0){
								udpf.setTransType("down");
							}else if(file.getPath().indexOf(AppConfig.LOCAL_FILE_BASEURL+ZwhzFtpPath.OTHER_UP)>0){
								udpf.setTransType("up");
							}
							cal = Calendar.getInstance();  
							long time = file.lastModified();  
						    cal.setTimeInMillis(time);  
						    udpf.setFilePublishType("");
							udpf.setCreateTime(sdf.format(cal.getTime()));
							udpackFiles.add(udpf);
						}
					}
				}
			return udpackFiles;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("�����ļ�Ŀ¼ʧ��:"+e.getMessage());
		}
	}
	
}
