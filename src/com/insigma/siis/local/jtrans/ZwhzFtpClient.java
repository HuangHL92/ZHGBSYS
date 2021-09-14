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
 * �������Ftp������
 * @author gezh
 *
 */
public class ZwhzFtpClient {
	
	/**
	 * �ϴ�HZB�����ļ�
	 * @param jfcc Ŀ��FTP�û���Ϣ
	 * @param packFilePath hzb�ܰ�XML�ļ�
	 * @throws Exception
	 */
	public static void uploadHzb(TransConfig jfcc,String packFilePath) throws FtpException{
		upload(jfcc, packFilePath, ZwhzFtpPath.HZB_UP);
	}
	
	/**
	 * �ϴ�FK�����ļ�
	 * @param jfcc Ŀ��FTP�û���Ϣ
	 * @param packFilePath FK�ܰ�XML�ļ�
	 * @throws Exception
	 */
	public static void uploadFk(TransConfig jfcc,String packFilePath) throws FtpException{
		upload(jfcc, packFilePath, ZwhzFtpPath.FK_UP);
	}
	
	/**
	 * �ϴ�CBD�����ļ�
	 * @param jfcc Ŀ��FTP�û���Ϣ
	 * @param packFilePath hzb�ܰ�XML�ļ�
	 * @throws Exception
	 */
	public static void uploadCBD(TransConfig jfcc,String packFilePath) throws FtpException{
		upload(jfcc, packFilePath, ZwhzFtpPath.CBD_UP);
	}
	
	/**
	 * FTP�ļ������Լ��
	 * @param jftp ftp�ͻ��˹��������
	 * @param sfdefines �Ӱ��ļ���Ϣ����
	 * @param fileDir �ļ�����FTP�ϵ��ļ���
	 * @param packFileName �ܰ��ļ�
	 * @return
	 */
	public static boolean remoteCheckComplete(JFTPClient jftp,List<SFileDefine> sfdefines,String fileDir,String packFileName){
		boolean isExists = true;
		SFileDefine sfile = null;
		for (int i = 0; i < sfdefines.size(); i++) {//������ļ�
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
		if(isExists){//������ļ����ڣ�����ܰ��ļ�
			try {
				isExists = jftp.checkRemoteFileExists(fileDir + "/" + packFileName);
			} catch (FtpException e) {
				isExists = false;
			}
		}
		return isExists;
	}
	
	/**
	 * �����ܰ��ļ�����FTP�ļ�
	 * @param sfdefines �Ӱ��ļ���Ϣ����
	 * @param fileDir �ļ�����FTP�ϵ��ļ���
	 * @return
	 */
	public static void remoteClearByPack(JFTPClient jftp,List<SFileDefine> sfdefines,String fileDir,String packFileName){
		boolean isExists = true;
		SFileDefine sfile = null;
		for (int i = 0; i < sfdefines.size(); i++) {//������ļ�
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
	 * �����ļ������Լ��
	 * @param sfdefines �Ӱ��ļ���Ϣ����
	 * @param fileDir �ļ����ڱ��ص��ļ���
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
	 * �����ܰ��ļ��������ļ�
	 * @param sfdefines �Ӱ��ļ���Ϣ����
	 * @param fileDir �ļ����ڱ��ص��ļ���
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
	 * �ϴ����÷���
	 * @param jfcc Ŀ��FTP�û���Ϣ
	 * @param packFilePath �ܰ�XML�ļ�
	 * @param targetDir FTPĿ��Ŀ¼
	 * @throws Exception
	 */
	private static void upload(TransConfig jfcc,String packFilePath,String targetDir) throws FtpException{
		JFTPClient jftp = null; 
		String packFileDir = null; //�ܰ��ļ�Ŀ¼
		String packXmlStr = null; //�ܰ��ļ��ַ���
		ZwhzPackDefine zpdefine = null;//�ܰ��ļ���������
		List<SFileDefine> sfdefines = null;//�Ӱ��ļ���Ϣ����
		File sfile = null;//���ļ�
		try{
			File packFile = new File(packFilePath);
			packFileDir = packFilePath.substring(0, packFilePath.lastIndexOf("/")+1);
			if(packFile.exists()){ //�жϱ����ļ��Ƿ����
				jftp = JFTPClient.getInstance(); //��ȡFTP������ʵ��
				jftp.connect(jfcc.getHostname(), jfcc.getPort().intValue(), jfcc.getUsername(), jfcc.getPassword());//����FTP
				packXmlStr = FileUtil.readFileByChars(packFilePath,"UTF-8"); //��ȡ�ܰ��ļ�����
				if(packXmlStr!=null){
					zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //�����ܰ��ļ�
					sfdefines = zpdefine.getSfile();//�ܰ��ļ��н����������ļ����Ƽ���
					if(!localCheckComplete(sfdefines, packFileDir)){//�ϴ�֮ǰ�����ļ������Լ��
						localClearByPack(sfdefines, packFileDir,packFile.getName());
						throw new FtpException("���������ļ������Լ�鲻ͨ��!");
					}
					for (int i = 0; i < sfdefines.size(); i++) { //�����ϴ����ļ�
						sfile = new File(packFileDir + sfdefines.get(i).getName());
						if(sfile.exists()){
							jftp.upload(packFileDir + sfdefines.get(i).getName(), targetDir + "/" + sfdefines.get(i).getName()+".tmp"); 
							jftp.reName(targetDir + "/" + sfdefines.get(i).getName()+".tmp", targetDir + "/" + sfdefines.get(i).getName());
						}else{
							throw new FtpException(sfdefines.get(i).getName()+"�Ӱ��ļ�������!");
						}
					}
					jftp.upload(packFilePath, targetDir + "/" + packFile.getName()+".tmp"); //�ϴ��ܰ��ļ�
					jftp.reName(targetDir + "/" + packFile.getName()+".tmp", targetDir + "/" + packFile.getName());
					if(!remoteCheckComplete(jftp, sfdefines, targetDir, packFile.getName())){
						remoteClearByPack(jftp, sfdefines, targetDir,  packFile.getName());
						throw new FtpException("�����ļ��ϴ���ɣ��������������Լ�鲻ͨ��!");
					}
				}else{
					throw new FtpException("�ܰ��ļ�����Ϊ��!");
				}
			}else{
				throw new FtpException(packFile.getName()+"�ܰ��ļ��в����ڣ�");
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
	 * ��ͨ�Բ���
	 * @param jfcc Ŀ��FTP�û���Ϣ
	 * @throws FtpException
	 */
	public static void conntest(TransConfig jfcc) throws FtpException{
		JFTPClient jftp = null;
		try{
			jftp = JFTPClient.getInstance();
			jftp.connect(jfcc.getHostname(), jfcc.getPort().intValue(), jfcc.getUsername(), jfcc.getPassword());//����FTP
		}catch(FtpException e){
			throw new FtpException(e.getMessage());
		}finally{
			if(jftp!=null){jftp.disconnect();}
		}
	}
	
	/**
	 * ��ʼ������ѯĿ¼
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
	 * ��ѯ�����ϼ��·�Ŀ¼
	 * @param jfccs
	 */
	public static void hlPoling(List<TransConfig> jfccs){
		List<String> polingPathList = initPolingPath();//��ʼ������ѯĿ¼
		TransConfig jfcc = null; //����FTP������Ϣ
		JFTPClient jftp = null; 
		String packXmlStr = null; //�ܰ��ļ��ַ�������
		String polingDir = null; //��ǰ��ѯĿ¼
		String localPackFilePath = null; //�����ܰ��ļ�·��
		String localFilePath = null; //�����ļ�·��
		File localFile = null; //�����ļ�
		List<String> packFileList = null; //�ܰ��ļ�����
		List<String> othersFileList = null; //�����ļ�����
		ZwhzPackDefine zpdefine = null;//�ܰ��ļ���������
		List<SFileDefine> sfdefines = null;//�Ӱ��ļ���Ϣ����
		File sfile = null;//���ļ�
		for (int i = 0; i < jfccs.size(); i++) { //�����������ݿ�����
			try{
				jfcc = jfccs.get(i);
				jftp = JFTPClient.getInstance(); //��ȡFTP������ʵ��
				jftp.connect(jfcc.getHostname(), jfcc.getPort().intValue(), jfcc.getUsername(), jfcc.getPassword());//����FTP
				for (int j = 0; j < polingPathList.size(); j++) { //��������ѯĿ¼
					polingDir = polingPathList.get(j);
					if(ZwhzFtpPath.OTHER_DOWN.equals(polingDir)){//�����ļ�Ŀ¼ 
						othersFileList = jftp.findFilesByPath(ZwhzFtpPath.OTHER_DOWN);
						for (int k = 0; k < othersFileList.size(); k++) {
							try{
								FileUtil.createFolder(AppConfig.LOCAL_FILE_BASEURL + polingDir);
								localFile = new File(AppConfig.LOCAL_FILE_BASEURL + othersFileList.get(k));
								if(!localFile.exists()){
									//�ļ�����������
									localFilePath =  AppConfig.LOCAL_FILE_BASEURL + othersFileList.get(k)+".tmp";
									jftp.downSingleFile(othersFileList.get(k),localFilePath);
								    FileUtil.reNameFile(localFilePath, localFilePath.substring(0,localFilePath.lastIndexOf(".tmp")));
								    //����ļ��Ƿ����ص����أ����������������ɾ��FTP���ļ�
								    if(localFile.exists()){
								    	//jftp.removeFile(othersFileList.get(k));
								    }
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}else{ //������Ŀ¼֮�����ѯĿ¼
						packFileList = jftp.findPackFilesByPath(polingDir);
						for (int k = 0; k < packFileList.size(); k++) {
							try{
								/***************************�����ܰ��ļ�************************************/
								FileUtil.createFolder(AppConfig.LOCAL_FILE_BASEURL + polingDir);
								localFile = new File(AppConfig.LOCAL_FILE_BASEURL + polingDir + "/" + packFileList.get(k));
								if(!localFile.exists()){
									localPackFilePath =  AppConfig.LOCAL_FILE_BASEURL + polingDir + "/" + packFileList.get(k)+".tmp";//�ļ�����������
									jftp.downSingleFile(polingDir + "/" +packFileList.get(k), localPackFilePath);
									packXmlStr = FileUtil.readFileByChars(localPackFilePath,"UTF-8"); //��ȡ�ܰ��ļ�����
									if(packXmlStr!=null){
										zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //�����ܰ��ļ�
										sfdefines = zpdefine.getSfile();//�ܰ��ļ��н����������ļ����Ƽ���
										//����ǰ�ȼ��FTP���ļ��Ƿ���ȫ
										if(!remoteCheckComplete(jftp, sfdefines,polingDir, packFileList.get(k))){
											FileUtil.delFile(localPackFilePath);
											CommonQueryBS.systemOut(packFileList.get(k)+"�ܰ��ļ��������,�������������Լ�鲻ͨ��!");
										}else{
											for (int m = 0; m < sfdefines.size(); m++) { //�����·����ļ�
												sfile = new File(AppConfig.LOCAL_FILE_BASEURL + polingDir + "/" + sfdefines.get(m).getName());
												if(!sfile.exists()){
													//�ļ�����������
													localFilePath =  AppConfig.LOCAL_FILE_BASEURL + polingDir + "/" + sfdefines.get(m).getName()+".tmp";
													jftp.downSingleFile(polingDir + "/" + sfdefines.get(m).getName(),localFilePath);
												    FileUtil.reNameFile(localFilePath, localFilePath.substring(0,localFilePath.lastIndexOf(".tmp")));
												}
											}
											//�ܰ�����������
											FileUtil.reNameFile(localPackFilePath, localPackFilePath.substring(0,localPackFilePath.lastIndexOf(".tmp")));
										}
									}
									//���غ�����ܰ��ļ���Ȿ���ļ��Ƿ���ȫ
									if(!localCheckComplete(sfdefines, AppConfig.LOCAL_FILE_BASEURL + polingDir)){
										localClearByPack(sfdefines, AppConfig.LOCAL_FILE_BASEURL + polingDir, packFileList.get(k));
										CommonQueryBS.systemOut(packFileList.get(k)+"�ܰ��ļ��������ļ�������,���ͻ��������Լ�鲻ͨ��!");
									}else{
										//��������ļ���ȫ����FTP�������ϵ��ļ����
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
//		uploadHzb(jfcc, "G:/LocalFileStore/Pack_�����������ļ�_3307003_֣����_20160420205529.xml");
	}
}
