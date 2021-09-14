package com.insigma.siis.local.epsoft.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
/*
Java IO ��һ��ʹ��ԭ�� �� 

һ����������Դ��ȥ�򣩷��ࣺ 

1 �����ļ��� FileInputStream, FileOutputStream, ( �ֽ��� )FileReader, FileWriter( �ַ� )

2 ���� byte[] �� ByteArrayInputStream, ByteArrayOutputStream( �ֽ��� )

3 ���� Char[]: CharArrayReader, CharArrayWriter( �ַ��� )

4 ���� String: StringBufferInputStream, StringBufferOuputStream ( �ֽ��� )StringReader, StringWriter( �ַ��� )

5 �������������� InputStream, OutputStream,( �ֽ��� ) Reader, Writer( �ַ��� )

�������Ƿ��ʽ������֣�

1 ��Ҫ��ʽ������� PrintStream, PrintWriter

�������Ƿ�Ҫ����֣�

1 ��Ҫ���壺 BufferedInputStream, BufferedOutputStream,( �ֽ��� ) BufferedReader, BufferedWriter( �ַ��� )

�ġ������ݸ�ʽ�֣�

1 �������Ƹ�ʽ��ֻҪ����ȷ���Ǵ��ı��ģ� : InputStream, OutputStream �������д� Stream ����������

2 �����ı���ʽ������Ӣ���뺺�ֻ��������뷽ʽ���� Reader, Writer �������д� Reader, Writer ������

�塢����������֣�

1 �����룺 Reader, InputStream ���͵�����

2 ������� Writer, OutputStream ���͵�����

����������Ҫ��

1 ���� Stream �� Reader,Writer ��ת���ࣺ InputStreamReader, OutputStreamWriter

2 ��������������� ObjectInputStream, ObjectOutputStream

3 �����̼�ͨ�ţ� PipeInputStream, PipeOutputStream, PipeReader, PipeWriter

4 ���ϲ����룺 SequenceInputStream

5 �����������Ҫ�� PushbackInputStream, PushbackReader, LineNumberInputStream, LineNumberReader

*/

import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * �ļ�������
 * @author kezp
 *
 */
public class FileUtil {
	/**
	 * ��BΪ��λ
	 */
	private static String sign_B = "B";
	/**
	 * ��KΪ��λ
	 */
	private static String sign_K = "K";
	
	/**
	 * ��MΪ��λ
	 */
	private static String sign_M = "M";
	
	/**
	 * ��GΪ��λ
	 */
	private static String sign_G = "G";
	
	/**
	 * �����ļ���
	 * @param dir
	 * @throws Exception
	 */
	public static void createFolder(String dir) throws Exception {
		File file = new File(dir);
		if (!file.exists()) {  //��������ڣ��򴴽�
			file.mkdirs();
		}
	}
	
	
	/**
	 * �����ļ���ȫ�¸���
	 * @param fullname
	 * @param fileContent
	 * @throws Exception
	 */
	public static void createFile(String fullname ,String fileContent) throws Exception {
		createFile(fullname ,fileContent,false);
	}
	

	/**
	 * �����ļ�
	 * @param fullname
	 * @param fileContent
	 * @throws Exception
	 */
	public static void createFile(String fullname ,String fileContent,boolean append) throws Exception {
		File file = new File(fullname);
		if (!file.exists()){
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file,append);   
		fw.append(fileContent);
		fw.close();
	}
	
	
	/**
	 * �����ļ�
	 * @param fullname
	 * @param fileContent
	 * @throws Exception
	 */
	public static void createFile(String fullname ,String fileContent,boolean append,String charset) throws Exception {
		File file = new File(fullname);
		if (!file.exists()){
			file.createNewFile();
		}
		//ת������ŵ� yzk 2019.12.13
		fileContent=StringEscapeUtils.unescapeXml(fileContent);
		FileWriterWithEncoding fw = new FileWriterWithEncoding(file, charset, append);
		fileContent= fileContent.replaceAll("1lt;", "lt;");
		fileContent= fileContent.replaceAll("1gt;", "gt;");
		fileContent= fileContent.replaceAll("&1amp;", "&amp;");
		fw.append(fileContent);
		fw.close();
	}
	
	/**
	 * ɾ���ļ�
	 * @param fullname
	 * @throws Exception
	 */
	public static void delFile(String fullname) throws Exception {
		File file = new File(fullname);
		file.delete();
	}
	
	/**
	 * �ļ�������
	 * @param oldname
	 * @param newName
	 * @throws Exception
	 */
	public static void reNameFile(String oldname,String newName) throws Exception {
		File oldfile = new File(oldname);
		File newfile = new File(newName);
		oldfile.renameTo(newfile);
	}
	

	/**
	 * �ļ�����
	 * @param sourceFile
	 * @param targetFile
	 * @throws Exception
	 */
	public static void copyFile(File sourceFile,File targetFile) throws Exception {
 
        FileInputStream input = new FileInputStream(sourceFile);  
        BufferedInputStream inBuff=new BufferedInputStream(input);  
  
        FileOutputStream output = new FileOutputStream(targetFile);  
        BufferedOutputStream outBuff=new BufferedOutputStream(output);  
		          
        //��������   
        byte[] b = new byte[1024 * 5];  
        int len;  
        while ((len =inBuff.read(b)) != -1) {  
            outBuff.write(b, 0, len);  
        }  
        // ˢ�´˻���������   
        outBuff.flush();  
          
        //�ر���   
        inBuff.close();  
        outBuff.close();  
        output.close();  
        input.close();  
	}
	
	/**
	 * �ļ�����
	 * @param sourceFile
	 * @param targetFile
	 * @throws Exception
	 */
	public static void copyFile(String sourceFile,String targetFile) throws Exception {
		copyFile(new File(sourceFile),new File(targetFile));
	}
	
	
     
	/**
	 * �����ļ��п���
	 * @param sourceDir
	 * @param targetDir
	 * @throws Exception
	 */
    public static void copyDirectiory(String sourceDir, String targetDir)  throws Exception {  
        // �½�Ŀ��Ŀ¼   
        (new File(targetDir)).mkdirs();  
        // ��ȡԴ�ļ��е�ǰ�µ��ļ���Ŀ¼   
        File[] file = (new File(sourceDir)).listFiles();  
        for (int i = 0; i < file.length; i++) {  
            if (file[i].isFile()) {  
                // Դ�ļ�   
                File sourceFile=file[i];  
                // Ŀ���ļ�   
                File targetFile=new File(new File(targetDir).getAbsolutePath()+File.separator+file[i].getName());  
                copyFile(sourceFile,targetFile);  
            }  
            if (file[i].isDirectory()) {  
                // ׼�����Ƶ�Դ�ļ���   
                String dir1=sourceDir + "/" + file[i].getName();  
                // ׼�����Ƶ�Ŀ���ļ���   
                String dir2=targetDir + "/"+ file[i].getName();  
                copyDirectiory(dir1, dir2);  
            }  
        }  
    }  

    
    /**
     * ɾ�������ļ����������
     * @param path
     * @throws Exception
     */
	public static void delAllFile(String path) throws Exception{ 
		File file = new File(path); 
		if (!file.exists()) { 
			return; 
		} 
		if (!file.isDirectory()) { 
			return; 
		} 
		String[] tempList = file.list(); 
		File temp = null; 
		for (int i = 0; i < tempList.length; i++) { 
			if (path.endsWith(File.separator)) { 
				temp = new File(path + tempList[i]); 
			} else { 
				temp = new File(path + File.separator + tempList[i]); 
			} 
			if (temp.isFile()) { 
				temp.delete(); 
			} 
			if (temp.isDirectory()) { 
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ� 
				File folderPath = new File(path + "/" + tempList[i]);  // ��ɾ�����ļ���
				folderPath.delete(); 
			} 
		}
		file.delete();  //ɾ�������ļ���
	} 
	
	

	/**
	 * ����·�����⣬���·����ĩβû��"/",������"/"
	 * @param path
	 * @return
	 */
	public static String resolveRigthPath(String path) {
		path = StringUtils.replace(path, "\\", "/");
		int idxLastSlash = path.lastIndexOf("/");
		if (path.length() != (idxLastSlash + 1)) {
			path = path + "/";
		}
		return path;
	}
	
	/**
	 * ȥ���ұ�'/',�������'/';
	 * @param str
	 * @return
	 */
	public static String resolveLeftPath(String path) {
		if (!path.startsWith("/"))
			path = "/" + path;
		if (path.endsWith("/"))
			path = path.substring(0, path.length() - 1);
		return path;
	}
	
	/**
	 * �޸ĳ���<br>
	 * �ڲ��ݹ���ã�������Ŀ¼�ĸ���
	 * 
	 * @param path
	 *            ·��
	 * @param from
	 *            ԭʼ�ĺ�׺���������Ǹ�(.��)
	 * @param to
	 *            �����ĺ�׺��Ҳ�����Ǹ�(.��)
	 */
	public static void reName(String path, String from, String to) {
		File f = new File(path);
		File[] fs = f.listFiles();
		for (int i = 0; i < fs.length; ++i) {
			File f2 = fs[i];
			if (f2.isDirectory()) {
				reName(f2.getPath(), from, to);
			} else {
				String name = f2.getName();
				if (name.endsWith(from)) {
					f2.renameTo(new File(f2.getParent() + "/"
							+ name.substring(0, name.indexOf(from)) + to));
				}
			}
		}
	}

	
	/**
	 * ȡ�ļ���С
	 * @param fileName �ļ���
	 * @param cal_Sign B K M G
	 * @return
	 */
	public static double getFileSize(String fileName,String cal_Sign){
		
		long sign = 1;
		if (cal_Sign.equals("K")){
			sign = sign*1024;
		} else if (cal_Sign.equals("M")){
			sign = sign*1024*1024;
		}else if (cal_Sign.equals("G")){
			sign = sign*1024*1024*1024;
		}
		File file = new File(fileName);
		
		BigDecimal bd1 = new BigDecimal(new Long(file.length()).toString());
		BigDecimal bd2 = new BigDecimal(new Long(sign).toString());
		return bd1.divide(bd2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	
    /**
     * ���ַ�Ϊ��λ��ȡ�ļ��������ڶ��ı������ֵ����͵��ļ�
     */
    public static String readFileByChars(String fileName,String charset) {
        Reader reader = null;
        try {
        	StringBuffer sb = new StringBuffer();
            // һ�ζ�����ַ�
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName),charset);
            // �������ַ����ַ������У�charreadΪһ�ζ�ȡ�ַ���
            while ((charread = reader.read(tempchars)) != -1) {
                // ͬ�����ε�\r����ʾ
                if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
                	sb.append(tempchars);
                    //System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            sb.append(tempchars[i]);
                        }
                    }
                }
            }
            return sb.toString();
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ�
     */
    public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            //System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
            reader = new BufferedReader(new FileReader(file));
            StringBuffer sb = new StringBuffer();
            String tempString = null;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
    
    
    /**
     * ����BufferedInputStream��ʽ��ȡ�ļ�����
     * 
     * @param filename
     * @return
     * @throws IOException
     */
    public static int count(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        while ((readChars = is.read(c)) != -1) {
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n')
                    ++count;
            }
        }
        is.close();
        return count;
    }
    /**
     * 
     **/
    public List<File> getFileList(String filePath,String fileName) throws Exception{
    	File dir = new File(filePath);
    	List<File> list = new ArrayList<File>();
		if (!(dir.isDirectory() && dir.exists())) {
			CommonQueryBS.systemOut(filePath+"������,���´�����");
			FileUtil.createFolder(filePath);
		} 
		File[] files = dir.listFiles();  //�г������ļ�	
		for (int i=0;i<files.length;i++){
			if(files[i].getName().equals(fileName+".DBF")){
				//return files[i];
				list.add(files[i]);
			}
		}
    	return list;
    }
    
	public static boolean checkFile(String filename){
		File file = new File(filename);
		if(file.exists()){
			return true;
		}else{
			return false;
		}
	}
	
    /**
     * ����
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
/*		StringBuffer sb = new StringBuffer();
		sb.append("fasdfasd\rkezp adf");
		System.out.println(sb.toString());
		String str= sb.toString();
		if (str.length()>4){
			str=str.substring(0,4);
		}
		System.out.println(str);*/
		
		//System.out.println(count("d:/JYJHK119900DY012011040120110403.xml"));
		//System.out.println(count("d:/01JHK201102330100IY01.xml"));
		
		//OracleExp exp = new OracleExp();
		//System.out.println(exp.isErrorXML("d:/test_lwjc.xml"));
		reNameFile("d:/test_lwjc2.xml","d:/test_lwjc2_rename.xml");
		
		
	}

}
