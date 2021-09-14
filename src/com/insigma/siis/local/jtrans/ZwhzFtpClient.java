package com.insigma.siis.local.jtrans;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.ftpserver.ftplet.FtpException;

import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.jftp.JFTPClient;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;


/**
 * 政务汇总Ftp工具类
 * @author gezh
 *
 */
public class ZwhzFtpClient {
	
	/**
	 * 上传HZB数据文件
	 * @param jfcc 目标FTP用户信息
	 * @param packFilePath hzb总包XML文件
	 * @throws Exception
	 */
	public static void uploadHzb(TransConfig jfcc,String packFilePath) throws FtpException{
		upload(jfcc, packFilePath, ZwhzFtpPath.HZB_UP);
	}
	
	/**
	 * 上传FK数据文件
	 * @param jfcc 目标FTP用户信息
	 * @param packFilePath FK总包XML文件
	 * @throws Exception
	 */
	public static void uploadFk(TransConfig jfcc,String packFilePath) throws FtpException{
		upload(jfcc, packFilePath, ZwhzFtpPath.FK_UP);
	}
	
	/**
	 * 上传CBD数据文件
	 * @param jfcc 目标FTP用户信息
	 * @param packFilePath hzb总包XML文件
	 * @throws Exception
	 */
	public static void uploadCBD(TransConfig jfcc,String packFilePath) throws FtpException{
		upload(jfcc, packFilePath, ZwhzFtpPath.CBD_UP);
	}
	
	/**
	 * FTP文件完整性检查
	 * @param jftp ftp客户端工具类对象
	 * @param sfdefines 子包文件信息集合
	 * @param fileDir 文件所在FTP上的文件夹
	 * @param packFileName 总包文件
	 * @return
	 */
	public static boolean remoteCheckComplete(JFTPClient jftp,List<SFileDefine> sfdefines,String fileDir,String packFileName){
		boolean isExists = true;
		SFileDefine sfile = null;
		for (int i = 0; i < sfdefines.size(); i++) {//检查子文件
			sfile = sfdefines.get(i);
			try {
				isExists = jftp.checkRemoteFileExists(fileDir + "/" + sfile.getName());
				if (!isExists) {
					break;
				}
			} catch (FtpException e) {
				isExists = false;
				break;
			}
		}
		if(isExists){//如果子文件存在，检查总包文件
			try {
				isExists = jftp.checkRemoteFileExists(fileDir + "/" + packFileName);
			} catch (FtpException e) {
				isExists = false;
			}
		}
		return isExists;
	}
	
	/**
	 * 根据总包文件清理FTP文件
	 * @param sfdefines 子包文件信息集合
	 * @param fileDir 文件所在FTP上的文件夹
	 * @return
	 */
	public static void remoteClearByPack(JFTPClient jftp,List<SFileDefine> sfdefines,String fileDir,String packFileName){
		boolean isExists = true;
		SFileDefine sfile = null;
		for (int i = 0; i < sfdefines.size(); i++) {//检查子文件
			sfile = sfdefines.get(i);
			try {
				isExists = jftp.checkRemoteFileExists(fileDir + "/" + sfile.getName());
				if (isExists) {
					jftp.removeFile(fileDir + "/" + sfile.getName());
				}
			} catch (FtpException e) {
				 e.printStackTrace();
			}
		}
		try {
			isExists = jftp.checkRemoteFileExists(fileDir + "/" + packFileName);
			if (isExists) {
				jftp.removeFile(fileDir + "/" + packFileName);
			}
		} catch (FtpException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 本地文件完整性检查
	 * @param sfdefines 子包文件信息集合
	 * @param fileDir 文件所在本地的文件夹
	 * @return
	 */
	public static boolean localCheckComplete(List<SFileDefine> sfdefines,String fileDir){
		File sfile = null;
		boolean isExists = true;
		fileDir = FileUtil.resolveRigthPath(fileDir);
		for (int i = 0; i < sfdefines.size(); i++) {
			sfile = new File(fileDir + sfdefines.get(i).getName());
			if(!sfile.exists()){
				isExists = false; 
				break;
			} 
		}
		return isExists;
	}
	
	/**
	 * 根据总包文件清理本地文件
	 * @param sfdefines 子包文件信息集合
	 * @param fileDir 文件所在本地的文件夹
	 * @return
	 */
	public static void localClearByPack(List<SFileDefine> sfdefines,String fileDir,String packFileName){
		File sfile = null;
		fileDir = FileUtil.resolveRigthPath(fileDir);
		for (int i = 0; i < sfdefines.size(); i++) {
			sfile = new File(fileDir + sfdefines.get(i).getName());
			if(sfile.exists()){
				 try {
					FileUtil.delFile(fileDir + sfdefines.get(i).getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		}
		
		try {
			FileUtil.delFile(fileDir + packFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 上传公用方法
	 * @param jfcc 目标FTP用户信息
	 * @param packFilePath 总包XML文件
	 * @param targetDir FTP目标目录
	 * @throws Exception
	 */
	private static void upload(TransConfig jfcc,String packFilePath,String targetDir) throws FtpException{
		JFTPClient jftp = null; 
		String packFileDir = null; //总包文件目录
		String packXmlStr = null; //总包文件字符串
		ZwhzPackDefine zpdefine = null;//总包文件解析对象
		List<SFileDefine> sfdefines = null;//子包文件信息集合
		File sfile = null;//子文件
		try{
			File packFile = new File(packFilePath);
			packFileDir = packFilePath.substring(0, packFilePath.lastIndexOf("/")+1);
			if(packFile.exists()){ //判断本地文件是否存在
				jftp = JFTPClient.getInstance(); //获取FTP工具类实例
				jftp.connect(jfcc.getHostname(), jfcc.getPort().intValue(), jfcc.getUsername(), jfcc.getPassword());//连接FTP
				packXmlStr = FileUtil.readFileByChars(packFilePath,"UTF-8"); //读取总包文件内容
				if(packXmlStr!=null){
					zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //解析总包文件
					sfdefines = zpdefine.getSfile();//总包文件中解析出来的文件名称集合
					if(!localCheckComplete(sfdefines, packFileDir)){//上传之前本地文件完整性检查
						localClearByPack(sfdefines, packFileDir,packFile.getName());
						throw new FtpException("本地数据文件完整性检查不通过!");
					}
					for (int i = 0; i < sfdefines.size(); i++) { //遍历上传子文件
						sfile = new File(packFileDir + sfdefines.get(i).getName());
						if(sfile.exists()){
							jftp.upload(packFileDir + sfdefines.get(i).getName(), targetDir + "/" + sfdefines.get(i).getName()+".tmp"); 
							jftp.reName(targetDir + "/" + sfdefines.get(i).getName()+".tmp", targetDir + "/" + sfdefines.get(i).getName());
						}else{
							throw new FtpException(sfdefines.get(i).getName()+"子包文件不存在!");
						}
					}
					jftp.upload(packFilePath, targetDir + "/" + packFile.getName()+".tmp"); //上传总包文件
					jftp.reName(targetDir + "/" + packFile.getName()+".tmp", targetDir + "/" + packFile.getName());
					if(!remoteCheckComplete(jftp, sfdefines, targetDir, packFile.getName())){
						remoteClearByPack(jftp, sfdefines, targetDir,  packFile.getName());
						throw new FtpException("数据文件上传完成，服务器端完整性检查不通过!");
					}
				}else{
					throw new FtpException("总包文件内容为空!");
				}
			}else{
				throw new FtpException(packFile.getName()+"总包文件中不存在！");
			}
		}catch(Exception e){
			throw new FtpException(e.getMessage());
		}finally{
			if(jftp!=null){
				jftp.disconnect();
			}
		}
	}
	
	
	
	
	/**
	 * 连通性测试
	 * @param jfcc 目标FTP用户信息
	 * @throws FtpException
	 */
	public static void conntest(TransConfig jfcc) throws FtpException{
		JFTPClient jftp = null;
		try{
			jftp = JFTPClient.getInstance();
			jftp.connect(jfcc.getHostname(), jfcc.getPort().intValue(), jfcc.getUsername(), jfcc.getPassword());//连接FTP
		}catch(FtpException e){
			throw new FtpException(e.getMessage());
		}finally{
			if(jftp!=null){jftp.disconnect();}
		}
	}
	
	/**
	 * 初始化待轮询目录
	 * @return
	 */
	private static List<String> initPolingPath(){
		List<String> list = new ArrayList<String>();
		list.add(ZwhzFtpPath.DM_DOWN);
		list.add(ZwhzFtpPath.FK_DOWN);
		list.add(ZwhzFtpPath.HZB_DOWN);
		list.add(ZwhzFtpPath.JC_DOWN);
		list.add(ZwhzFtpPath.ZB_DOWN);
		list.add(ZwhzFtpPath.OTHER_DOWN);
		list.add(ZwhzFtpPath.CBD_DOWN);
		return list;
	}
	
	/**
	 * 轮询下载上级下发目录
	 * @param jfccs
	 */
	public static void hlPoling(List<TransConfig> jfccs){
		List<String> polingPathList = initPolingPath();//初始化待轮询目录
		TransConfig jfcc = null; //上行FTP配置信息
		JFTPClient jftp = null; 
		String packXmlStr = null; //总包文件字符串内容
		String polingDir = null; //当前轮询目录
		String localPackFilePath = null; //本地总包文件路径
		String localFilePath = null; //本地文件路径
		File localFile = null; //本地文件
		List<String> packFileList = null; //总包文件集合
		List<String> othersFileList = null; //其他文件集合
		ZwhzPackDefine zpdefine = null;//总包文件解析对象
		List<SFileDefine> sfdefines = null;//子包文件信息集合
		File sfile = null;//子文件
		for (int i = 0; i < jfccs.size(); i++) { //遍历上行数据库连接
			try{
				jfcc = jfccs.get(i);
				jftp = JFTPClient.getInstance(); //获取FTP工具类实例
				jftp.connect(jfcc.getHostname(), jfcc.getPort().intValue(), jfcc.getUsername(), jfcc.getPassword());//连接FTP
				for (int j = 0; j < polingPathList.size(); j++) { //遍历待轮询目录
					polingDir = polingPathList.get(j);
					if(ZwhzFtpPath.OTHER_DOWN.equals(polingDir)){//其他文件目录 
						othersFileList = jftp.findFilesByPath(ZwhzFtpPath.OTHER_DOWN);
						for (int k = 0; k < othersFileList.size(); k++) {
							try{
								FileUtil.createFolder(AppConfig.LOCAL_FILE_BASEURL + polingDir);
								localFile = new File(AppConfig.LOCAL_FILE_BASEURL + othersFileList.get(k));
								if(!localFile.exists()){
									//文件名先重命名
									localFilePath =  AppConfig.LOCAL_FILE_BASEURL + othersFileList.get(k)+".tmp";
									jftp.downSingleFile(othersFileList.get(k),localFilePath);
								    FileUtil.reNameFile(localFilePath, localFilePath.substring(0,localFilePath.lastIndexOf(".tmp")));
								    //检测文件是否下载到本地，如果已下载下来则删除FTP上文件
								    if(localFile.exists()){
								    	//jftp.removeFile(othersFileList.get(k));
								    }
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}else{ //除其他目录之外的轮询目录
						packFileList = jftp.findPackFilesByPath(polingDir);
						for (int k = 0; k < packFileList.size(); k++) {
							try{
								/***************************下载总包文件************************************/
								FileUtil.createFolder(AppConfig.LOCAL_FILE_BASEURL + polingDir);
								localFile = new File(AppConfig.LOCAL_FILE_BASEURL + polingDir + "/" + packFileList.get(k));
								if(!localFile.exists()){
									localPackFilePath =  AppConfig.LOCAL_FILE_BASEURL + polingDir + "/" + packFileList.get(k)+".tmp";//文件名先重命名
									jftp.downSingleFile(polingDir + "/" +packFileList.get(k), localPackFilePath);
									packXmlStr = FileUtil.readFileByChars(localPackFilePath,"UTF-8"); //读取总包文件内容
									if(packXmlStr!=null){
										zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //解析总包文件
										sfdefines = zpdefine.getSfile();//总包文件中解析出来的文件名称集合
										//下载前先检测FTP上文件是否齐全
										if(!remoteCheckComplete(jftp, sfdefines,polingDir, packFileList.get(k))){
											FileUtil.delFile(localPackFilePath);
											CommonQueryBS.systemOut(packFileList.get(k)+"总包文件下载完成,服务器端完整性检查不通过!");
										}else{
											for (int m = 0; m < sfdefines.size(); m++) { //遍历下发子文件
												sfile = new File(AppConfig.LOCAL_FILE_BASEURL + polingDir + "/" + sfdefines.get(m).getName());
												if(!sfile.exists()){
													//文件名先重命名
													localFilePath =  AppConfig.LOCAL_FILE_BASEURL + polingDir + "/" + sfdefines.get(m).getName()+".tmp";
													jftp.downSingleFile(polingDir + "/" + sfdefines.get(m).getName(),localFilePath);
												    FileUtil.reNameFile(localFilePath, localFilePath.substring(0,localFilePath.lastIndexOf(".tmp")));
												}
											}
											//总包重命名回来
											FileUtil.reNameFile(localPackFilePath, localPackFilePath.substring(0,localPackFilePath.lastIndexOf(".tmp")));
										}
									}
									//下载后根据总包文件检测本地文件是否齐全
									if(!localCheckComplete(sfdefines, AppConfig.LOCAL_FILE_BASEURL + polingDir)){
										localClearByPack(sfdefines, AppConfig.LOCAL_FILE_BASEURL + polingDir, packFileList.get(k));
										CommonQueryBS.systemOut(packFileList.get(k)+"总包文件及其子文件已下载,但客户端完整性检查不通过!");
									}else{
										//如果本地文件齐全，则将FTP服务器上的文件清除
										//remoteClearByPack(jftp, sfdefines, polingDir, packFileList.get(k));
									}
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(jftp!=null){
					jftp.disconnect();
				}
			}
		}
		
	}
	
	
	
	public static void main(String[] args) throws Exception {
		List<TransConfig> jfccs = new ArrayList<TransConfig>();
		TransConfig jfcc = new TransConfig();
		jfcc.setHostname("127.0.0.1");
		jfcc.setUsername("test");
		jfcc.setPassword("test");
		jfcc.setPort(Long.valueOf(21));
		jfccs.add(jfcc);
		hlPoling(jfccs);
//		uploadHzb(jfcc, "G:/LocalFileStore/Pack_按机构导出文件_3307003_郑州市_20160420205529.xml");
	}
}
