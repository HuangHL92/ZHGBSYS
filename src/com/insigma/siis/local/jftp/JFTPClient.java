package com.insigma.siis.local.jftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.log4j.Logger;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.jftp.JFtpStatusEnum.ConnStatus;
import com.insigma.siis.local.jtrans.ZwhzFtpPath;

/**
 * FTP�ͻ��˹�����
 * @author gezh
 *
 */
public class JFTPClient {
	
	private static Logger log = Logger.getLogger(JFTPClient.class);
	
	/**
	 * FTPClient
	 */
	private FTPClient ftpClient;
	
	/**
	 * Ftp�����ļ�����
	 */
	private int fileType;
	
	/**
	 * Ftp���ӳ�ʱ ��λ��
	 */
	private int timeOut = 5;
	
	/**
	 * �ͻ����ַ���
	 */
	private static final String C_CHARSET = "UTF-8";
	
	/**
	 * ���������ַ���
	 */
	private static final String S_CHARSET = "ISO-8859-1";
	
	
	
	/**
	 * ���캯��
	 * @param ftpClient
	 * @param fileType
	 */
	public JFTPClient(FTPClient ftpClient, int fileType) {
		super();
		this.ftpClient = ftpClient;
		this.fileType = fileType;
	}
	
	/**
	 * ���캯��
	 * @param ftpClient
	 * @param fileType
	 * @param timeOut
	 */
	public JFTPClient(FTPClient ftpClient, int fileType, int timeOut) {
		super();
		this.ftpClient = ftpClient;
		this.fileType = fileType;
		this.timeOut = timeOut;
	}
	
	/**
	 * ��ȡ�ͻ��˹�����ʵ��
	 * @return
	 */
	public static JFTPClient getInstance(){
		return new JFTPClient(new FTPClient(),FTP.BINARY_FILE_TYPE);
	}
	
	/**
	 * ��ȡ�ͻ��˹�����ʵ��
	 * @param timeOut
	 * @return
	 */
	public static JFTPClient getInstance(int timeOut){
		return new JFTPClient(new FTPClient(),FTP.BINARY_FILE_TYPE,timeOut);
	}
	
	/**
	 * ��ȡ�ͻ��˹�����ʵ��
	 * @param fileType
	 * @param timeOut
	 * @return
	 */
	public static JFTPClient getInstance(int fileType,int timeOut){
		return new JFTPClient(new FTPClient(),fileType,timeOut);
	}
	
	/**
	 * ·���ͻ����ַ���תΪ���������ַ���
	 * @param pathname
	 * @return
	 */
	private String cdoWithPath(String pathName){
		try {
			pathName = new String(pathName.getBytes(C_CHARSET), S_CHARSET);
		} catch (Exception e) {
			log.error("·������ʧ��",e);
		}
		return pathName;
	}
	
	/**
	 * ·�����������ַ���תΪ�ͻ����ַ���
	 * @param pathname
	 * @return
	 */
	private String sdoWithPath(String pathName){
		try {
			pathName = new String(pathName.getBytes(S_CHARSET),C_CHARSET);
		} catch (Exception e) {
			log.error("·������ʧ��",e);
		}
		return pathName;
	}
	
	/**
	 * ���������Ƿ���ڸ��ļ�
	 * @param pathName
	 * @return
	 * @throws FtpException
	 */
	public boolean checkRemoteFileExists(String pathName) throws FtpException{
		try{
			pathName = FileUtil.resolveLeftPath(pathName);
			FTPFile[] files = ftpClient.listFiles(cdoWithPath(pathName));
			List<FTPFile> filesList = Arrays.asList(files);
			if(filesList.size()>0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			throw new FtpException("���"+pathName+"�ļ��Ƿ����ʧ��:"+e.getMessage());
		}
	}
	
	/**
	 * ���������Ƿ���ڸ��ļ���
	 * @param pathName
	 * @return
	 * @throws FtpException
	 */
	public boolean checkRemoteDirExists(String pathName) throws FtpException{
		pathName = FileUtil.resolveLeftPath(pathName);
		String curDir = null;
		try{
			curDir = ftpClient.printWorkingDirectory();
			return ftpClient.changeWorkingDirectory(pathName);
		}catch(Exception e){
			throw new FtpException("���"+pathName+"�ļ����Ƿ����ʧ��:"+e.getMessage());
		}finally{
			try {
				ftpClient.changeWorkingDirectory(curDir);
			} catch (IOException e) {
			}
		}
		
	}
	
	/**
	 * ����FTP������
	 * @param hostname ������
	 * @param port �˿�
	 * @param username �û���
	 * @param password ����
	 * @throws FTPException 
	 */
	public void connect(String hostname, int port, String username, String password) throws FtpException {
		try {
			log.info("��ʼ����FTP������...");
			ftpClient.setConnectTimeout(this.timeOut*1000); //���ó�ʱʱ��
			ftpClient.connect(hostname, port);//���ӷ�����
			//ftpClient.setControlEncoding("ISO-8859-1");   
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) { //�ж����ӷ������Ƿ�ɹ�
				if (ftpClient.login(username, password)) {
					ftpClient.setFileType(this.fileType);//������FTP����֮�������ļ���������
					ftpClient.changeWorkingDirectory("/");
					log.info(JFtpStatusEnum.getConnMean(ConnStatus.Login_Success));
				}else{
					throw new FtpException(JFtpStatusEnum.getConnMean(ConnStatus.Login_Fail));
				}
			}else{
				throw new FtpException(JFtpStatusEnum.getConnMean(ConnStatus.Connect_Fail));
			}
		}catch (Exception ex) {
			log.error("���ӡ�"+hostname+"��FTP�������쳣",ex);
			throw new FtpException("���ӡ�"+hostname+"��FTP�������쳣:"+ex.getMessage());
		}
	}
	
	/**
	 * �Ͽ���Զ�̷�����������
	 */
	public void disconnect() {
		try {
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
				log.info("�Ͽ�FTP�������ɹ�");
			}
		} catch (Exception ex) {
			log.info("�Ͽ�FTP����������" + ex.getMessage());
		}
	}
	
	/**
	 * Զ�����أ������ߵ����ļ�
	 * 
	 * @param remotefile Զ���ļ�����·��
	 * @param localfile �����ļ�����·��
	 * @throws FtpException 
	 */
	public void downSingleFile(String remotefile, String localfilepath) throws FtpException {
		remotefile = FileUtil.resolveLeftPath(remotefile);
		if (!localfilepath.equals("") || localfilepath != null) {
			String localFolderPath = localfilepath.substring(0,localfilepath.lastIndexOf("/"));
			File fLocalFolderPath = new File(localFolderPath);
			if (!fLocalFolderPath.exists()) {
				fLocalFolderPath.mkdirs();
			}
		}
		String fileName = remotefile.substring(remotefile.lastIndexOf("/") + 1);
		try {
			// ȥ������remote�����'/'
			if (remotefile.endsWith("/"))
				remotefile = remotefile.substring(0, remotefile.length() - 1);
			// ����·���ַ��������л�FTP����Ŀ¼
			ftpClient.changeWorkingDirectory(cdoWithPath(remotefile.substring(0,remotefile.lastIndexOf("/"))));
			downLoadFile(fileName, localfilepath);
		} catch (Exception ex) {
			log.error("�����ļ��쳣",ex);
			throw new FtpException("�����ļ��쳣:"+ex.getMessage());
		}
	}
	
	/**
	 * ��FTP�������������ļ�
	 * @param remoteFileName Զ���ļ�����
	 * @param localFilePath �����ļ�·��
	 * @return �ϴ���״̬
	 */
	public boolean downLoadFile(String remoteFileName, String localFilePath) throws FtpException{
		boolean result = false;
		OutputStream out = null;
		InputStream in = null;
		try {
			ftpClient.enterLocalPassiveMode();
			// ���ô��䷽ʽ
			ftpClient.setFileType(fileType);
			File localFile = new File(localFilePath);
			log.info("��ʼ����"+remoteFileName+"�ļ�...");
			out = new FileOutputStream(localFile);
			in = ftpClient.retrieveFileStream(cdoWithPath(remoteFileName));
			byte[] bytes = new byte[1024];
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
			}
			in.close();
			out.close();
			result = ftpClient.completePendingCommand();//�ж������Ƿ����
			log.info(remoteFileName+"�ļ����ؽ�����");
			return result;
		} catch (Exception ex) {
			log.error("�����ļ�����:" + ex.getMessage() + "\n" + "FTP�ļ���:" + remoteFileName + "\n" + "����·��:" + localFilePath,ex);
			throw new FtpException("�����ļ�����:" + ex.getMessage() + "\n" + "FTP�ļ���:" + remoteFileName + "\n" + "����·��:" + localFilePath);
		} finally {
			try{
				if(in!=null){in.close();}
				if(out!=null){out.close();}
			}catch(Exception e){}
		}

	}
	
	/**
	 * �ݹ鴴��Զ�̷�����Ŀ¼
	 * 
	 * @param remote  Զ�̷������ļ�����·��
	 * @param filetype 1-�ļ� 2-�ļ���
	 * @return Ŀ¼�����Ƿ�ɹ�
	 */
	public void createDirecroty(String remote, int filetype) throws FtpException{
		String directory = "";
		if (filetype == 1)// �ļ�
			directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (filetype == 2) {// �ļ���
			directory = FileUtil.resolveRigthPath(remote);
		}
		try {
			
			if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(cdoWithPath(directory))) {
				// ���Զ��Ŀ¼�����ڣ���ݹ鴴��Զ�̷�����Ŀ¼
				int start = 0;
				int end = 0;
				if (directory.startsWith("/")) {
					start = 1;
				} else {
					start = 0;
				}
				end = directory.indexOf("/", start);
				while (true) {
					String subDirectory = cdoWithPath(remote.substring(start, end));
					if (!ftpClient.changeWorkingDirectory(subDirectory)) {
						if (ftpClient.makeDirectory(subDirectory)) {
							ftpClient.changeWorkingDirectory(subDirectory);
						} else {
							log.error(directory + ":����Ŀ¼ʧ��");
							throw new FtpException(directory + ":����Ŀ¼ʧ��");
						}
					}
					start = end + 1;
					end = directory.indexOf("/", start);

					// �������Ŀ¼�Ƿ񴴽����
					if (end <= start) {
						break;
					}
				}
				log.info(directory + ":����Ŀ¼�ɹ�!");
			} 
		} catch (Exception ex) {
			log.error("����"+remote+"Ŀ¼ʧ��!",ex);
			throw new FtpException("����"+remote+"Ŀ¼ʧ��:"+ex.getMessage());
		}
	}

	
	/**
	 * �ϴ��ļ���FTP������
	 * 
	 * @param local �����ļ�����
	 * @param remote Զ���ļ�·��
	 *  ����Linux�ϵ�·��ָ����ʽ��֧�ֶ༶Ŀ¼Ƕ�ף�֧�ֵݹ鴴�������ڵ�Ŀ¼�ṹ
	 * @throws FtpException 
	 */
	public void upload(String local, String remote) throws FtpException {
		try {
			// ����PassiveMode����
			ftpClient.enterLocalPassiveMode();
			// �����Դ��䷽ʽ
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			// ��Զ��Ŀ¼�Ĵ���
			//String remoteFileName = remote;
			if (remote == null || remote.equals("")) {
				throw new FtpException("Զ���ļ�·������Ϊ��");
			}
			if (remote.contains("/")) {
				//remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
				// ����������Զ��Ŀ¼�ṹ������ʧ��ֱ�ӷ���
				createDirecroty(remote, 1);
			}
			ftpClient.changeWorkingDirectory(remote);

			File f = new File(local);
			InputStream input = new FileInputStream(f);
			ftpClient.storeFile(cdoWithPath(remote), input);
			input.close();
			log.info(local + "�ϴ��ɹ�!");
		} catch (Exception ex) {
			throw new FtpException("�ϴ�"+local+"�ļ�ʧ��:"+ex.getMessage());
		}
	}
	
	/**
	 * �޸��ļ���
	 * 
	 * @param oldname
	 * @param newname
	 * @return
	 */
	public boolean reName(String oldname, String newname) {
		boolean result = false;
		try {
			result = ftpClient.rename(cdoWithPath(oldname), cdoWithPath(newname));  
			return result;
		} catch (Exception ex) {
			log.error("�������ļ�����" + ex.getMessage(),ex);
			return false;
		}
	}

	
    /** 
     * ɾ���ļ���
     * @param pathname  �ļ��еĵ�ַ 
     * @return true ���Ƴɹ���false ʧ�� 
     */  
    public boolean removeDirectory(String pathName){ 
		boolean rtnBln = false;
		try {
			pathName = FileUtil.resolveLeftPath(pathName);
			FTPFile[] files = ftpClient.listFiles(cdoWithPath(pathName));
			for (FTPFile f : files) {
				String path = pathName + File.separator + f.getName();
				if (f.isFile()) {// ���ļ���ɾ���ļ�
					ftpClient.deleteFile(cdoWithPath(path));
				} else if (f.isDirectory()) {//�ļ���������ݹ�
					removeDirectory(path);
				}
			}
			// ÿ��ɾ���ļ����Ժ��ȥ�鿴���ļ��������Ƿ����ļ���û�о�ɾ���ÿ��ļ���
			FTPFile[] isExistsFiles = ftpClient.listFiles(cdoWithPath(pathName));
			if (isExistsFiles.length == 0) {
				rtnBln = ftpClient.removeDirectory(cdoWithPath(pathName));
			} else {
			    rtnBln = false;
			}
		} catch (Exception e) {
			log.error(pathName + ":ɾ��ʧ��:" + e.getMessage());
			return false;
		}
		return rtnBln;
    }  
	
    /** 
     * ɾ���ļ�
     * @param pathname  �ļ��еĵ�ַ 
     * @return true ���Ƴɹ���false ʧ�� 
     */  
    public boolean removeFile(String pathName){ 
		boolean rtnBln = false;
		try {
			pathName = FileUtil.resolveLeftPath(pathName);
			rtnBln = ftpClient.deleteFile(cdoWithPath(pathName));
		} catch (Exception e) {
			log.error(pathName + ":ɾ��ʧ��:" + e.getMessage());
			return false;
		}
		return rtnBln;
    }  
    
    /**
	 * �ж�Ftp�����Ƿ�ɹ�
	 * @return
	 */
	public boolean getConBln() {
		boolean bln = ftpClient.isConnected();
		if (!bln) {
			log.error("����FTP������ʧ��!");
		}
		return bln;
	}
    
	/**
	 * �����ļ���
	 * @param pathName
	 * @return
	 * @throws FtpException
	 */
	public List<String> findPackFilesByPath(String pathName) throws FtpException{
		List<String> mFileList = new ArrayList<String>();
		pathName = FileUtil.resolveLeftPath(pathName);
		String ftpFileName = null;
		try{
			FTPFile[] files = ftpClient.listFiles(cdoWithPath(pathName));
			for (FTPFile ftpFile : files) {
				if(ftpFile.isFile()){
					ftpFileName = sdoWithPath(ftpFile.getName()).toLowerCase();
					if(ftpFileName.startsWith("pack_") && ftpFileName.endsWith(".xml")){
//						mFileList.add(pathName + "/" + sdoWithPath(ftpFile.getName()));
						mFileList.add(sdoWithPath(ftpFile.getName()));
					}
				}
			}
		}catch(Exception e){
			log.error("����"+pathName+"�ļ�Ŀ¼ʧ��",e);
		}
		return mFileList;
	}
	
	
	/**
	 * �����ļ��б�
	 * @param pathName
	 * @return
	 * @throws FtpException
	 */
	public List<String> findFilesByPath(String pathName) throws FtpException{
		List<String> mFileList = new ArrayList<String>();
		pathName = FileUtil.resolveLeftPath(pathName);
		try{
			FTPFile[] files = ftpClient.listFiles(cdoWithPath(pathName));
			for (FTPFile ftpFile : files) {
				if(ftpFile.isFile()){
					mFileList.add(pathName + "/" + sdoWithPath(ftpFile.getName()));
				}
			}
		}catch(Exception e){
			log.error("����"+pathName+"�ļ�Ŀ¼ʧ��",e);
		}
		return mFileList;
	}
	
	
	/**
	 * ��ȡ�ļ������������ļ��Ĵ�С
	 * 
	 * @param remoteFolder
	 * @return
	 * @throws FtpException 
	 */
	public int getFolderTotal(String remoteFolder) throws FtpException {
		if (!this.getConBln())
			return 0;
		int lRtn = 0;
		int tmpFileSize = 0;
		int tmpFolderSize = 0;
		remoteFolder = FileUtil.resolveLeftPath(remoteFolder);
		try {
			FTPFile[] files = ftpClient.listFiles(cdoWithPath(remoteFolder));
			if (files.length > 0) {
				FTPFile ftpFile = null;
				List<FTPFile> fileList = Arrays.asList(files);
				for (int i = 0; i < fileList.size(); i++) {
					ftpFile = fileList.get(i);
					if (!ftpFile.getName().equals("..") && !ftpFile.getName().equals(".")) {
						if (ftpFile.isFile()) {
							tmpFileSize = Integer.parseInt(String.valueOf(ftpFile.getSize()));
							log.info(remoteFolder + "/" + ftpFile.getName() + ":�ļ���С:" + ftpFile.getSize());
						}
						if (ftpFile.isDirectory()) {
							tmpFolderSize = getFolderTotal(remoteFolder + "/" + ftpFile.getName());
						}
						lRtn = lRtn + tmpFolderSize + tmpFileSize;
						tmpFolderSize = 0;
						tmpFileSize = 0;
					}

				}
			} else {
				log.error(remoteFolder + ":·������!");
				throw new FtpException(remoteFolder + ":·������!");
			}
			return lRtn;
		} catch (Exception e) {
			log.error("��ȡ"+remoteFolder + "�ļ���Сʧ��",e);
			throw new FtpException("��ȡ"+remoteFolder + "�ļ���Сʧ��:"+e.getMessage());
		}
	}

	
	/**
	 * ��ȡ�ļ���С
	 * 
	 * @param path
	 * @return
	 */
	public long getFileSize(String pathName) {
		long rtnValue = 0;
		pathName = FileUtil.resolveLeftPath(pathName);
		try {
			if (ftpClient.changeWorkingDirectory(pathName)) {
				FTPFile[] files = ftpClient.listFiles(cdoWithPath(pathName));
				if (files.length == 1) {
					rtnValue = files[0].getSize();
					log.info("�ļ���С:" + rtnValue);
				} else {
					log.error("��ȡ�ļ���Сʧ��:" + files.length);
				}
			}
		} catch (IOException e) {
			log.error("��ȡ�ļ���Сʧ��" + e.getMessage());
		}
		return rtnValue;
	}
	
	/**
	 * ��FTP�������ϴ����ļ���
	 * 
	 * @param remotepath
	 * @throws IOException 
	 */
	public boolean createFtpDirecroty(String remotepath) throws FtpException {
		boolean rtnBln = false;
		if (!this.getConBln() || remotepath == null || remotepath.equals(""))
			return rtnBln;
		if (this.getConBln()) {
			try{
				createDirecroty(remotepath, 2);
			}catch(Exception e){
				try{
					ftpClient.changeWorkingDirectory("/");
				}catch(Exception ex){}
				return false;
			}
		}
		return rtnBln;
	}
	
	/**
	 * ��ȡ��ǰ����Ŀ¼
	 * @return
	 */
	public String printWorkingDirectory(){
		String workDir = null;
		try{
			workDir = ftpClient.printWorkingDirectory();
		}catch(Exception e){
			log.error("��ȡ��ǰ����Ŀ¼ʧ��",e);
		}
		return workDir;
	}
	
	/**
	 * ��ȡ�ļ��е�״̬ true ����գ�false����Ϊ��
	 * 
	 * @param remoteFolder
	 * @return
	 */
	public boolean getFolderStatus(String remoteFolder) {
		if (!this.getConBln()) {
			log.info("����FTP������ʧ��!");
			return false;
		}
		int totalSize = 0;
		int tmpFileSize = 0;
		int tmpFolderSize = 0;
		remoteFolder = FileUtil.resolveLeftPath(remoteFolder);
		try {
			FTPFile[] files = ftpClient.listFiles(cdoWithPath(remoteFolder));
			if (files.length > 0) {
				FTPFile ftpFile = null;
				List<FTPFile> fileList = Arrays.asList(files);
				for (int i = 0; i < fileList.size(); i++) {
					ftpFile = fileList.get(i);
					if (!ftpFile.getName().equals("..") && !ftpFile.getName().equals(".")) {
						if (ftpFile.isFile()) {
							tmpFileSize = Integer.parseInt(String.valueOf(ftpFile.getSize()));
							log.info(remoteFolder + "/" + ftpFile.getName()+ ":�ļ���С:" + ftpFile.getSize());
						}
						if (ftpFile.isDirectory()) {
							tmpFolderSize = getFolderTotal(remoteFolder + "/" + ftpFile.getName());
						}
						totalSize = totalSize + tmpFolderSize + tmpFileSize;
						if (totalSize > 0)
							return false;
						tmpFolderSize = 0;
						tmpFileSize = 0;
					}

				}
			}
		} catch (Exception e) {
			log.error("��ȡ�ļ���״̬ʧ��",e);
			return false;
		}
		return true;
	}
	
	
	public static void main(String[] args) throws FtpException {
		JFTPClient ft = JFTPClient.getInstance();
		ft.connect("127.0.0.1", 21, "test", "test");
		ft.findPackFilesByPath(ZwhzFtpPath.DM_DOWN);
		ft.disconnect();
	}
}
