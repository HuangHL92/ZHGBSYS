/**
 * Copyright (C), 2019-2019, INSIGMACompany
 * FileName: JinQian
 * Author:   Qian_INSIGMA
 * Date:     2019/8/6 14:34
 * Description: �ɲ���ز�������
 */
package com.insigma.siis.local.pagemodel.meeting;



import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.WJGLAdd;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.picCut.servlet.UpLoadLrmServlet;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @author Qian_INSIGMA CratedBy  2019/8/614:34
 * Description :  �ɲ���ز�������
 */
public class DownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String separator = System.getProperty("file.separator");
	private static Logger log = Logger.getLogger(UpLoadLrmServlet.class);
	private static final String sys = System.getProperty("os.name").toUpperCase();
	private static final String UPLODA_PATH = GlobalNames.sysConfig.get("UPLODA_PATH");
	private static String UPLODA_PATH_WIN = null;

    public DownloadServlet() {
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("*************���뷽��");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String method = request.getParameter("method");
        String uuid1;
        String uuid2;
        if (method != null && method.equals("download")) {
            uuid1 = URLDecoder.decode(request.getParameter("fileName"), "UTF-8");
            uuid2 = request.getParameter("downloadfileName");
            this.download(request, response, uuid1, uuid2);
        } else {
            if (method != null && method.equals("search")) {
                uuid1 = URLDecoder.decode(request.getParameter("keyword"), "UTF-8");
                List<String> listData = this.getListDate(uuid1);
                response.getWriter().write(JSONArray.fromObject(listData).toString());
            } else if (method == null || !method.equals("addadmonishing")) {
                if ("YearCheckFile".equals(method)) {
                    this.downloadYearCheckFileFile(request, response);
                } else if ("PersionFile".equals(method)) {
                    this.downloadYearCheckFileFile(request, response);
                } else {
                    String filepaths;
                    String filepath1;
                    if ("Impfile".equals(method)) {
                        uuid2 = request.getParameter("sh000");
                        filepaths = this.impfile(request, response);
                        filepath1 = this.saveimp(filepaths, uuid2);
                        PrintWriter out = response.getWriter();
                        out.write(filepath1);
                    } else if("appendixUpload".equals(method)) {
                        //uuid1 = request.getParameter("p0200");
                        this.impAppendixFile(request, response);
                        // filepath1 = this.saveimpAppendix(filepaths, uuid1);
                        // PrintWriter out = response.getWriter();
                        // out.write(filepath1);
                    }
                }
            }

        }
    }

	public List<String> getListDate(String keyword) {
        List<String> list = new ArrayList();
        HBSession sess = HBUtil.getHBSession();
        String sql = " from A01 where (a0101 like '" + keyword + "%' or a0102 like '" + keyword + "%')";
        List<A01> a01list = sess.createQuery(sql).list();
        Iterator var7 = a01list.iterator();

        while (var7.hasNext()) {
            A01 a01 = (A01) var7.next();
            list.add(a01.getA0101() + "(" + a01.getA0192a() + ")");
        }

        return list;
    }

    public void download(HttpServletRequest request, HttpServletResponse response, String fileName, String downloadfileName) {
        ServletContext event = request.getSession().getServletContext();
        File outFile2;
        outFile2 = new File(downloadfileName);

        try {
            BufferedInputStream fis;
//            if ("ygcgsld".equals(downloadfileName)) {
//                fis = new BufferedInputStream(new FileInputStream(event.getRealPath("pages/goAbroad/onDuty/ygcgsld.xls")));
//            }
            fis = new BufferedInputStream(new FileInputStream(downloadfileName));

            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            response.addHeader("Content-Length", "" + outFile2.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (FileNotFoundException var10) {
            var10.printStackTrace();
        } catch (IOException var11) {
            var11.printStackTrace();
        }

    }

    public void downloadYearCheckFileFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = URLDecoder.decode(URLDecoder.decode(request.getParameter("filePath"), "UTF-8"), "UTF-8");
        String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
        File file = new File(filePath);
        if (file.exists()) {
            response.reset();
            response.setHeader("pragma", "no-cache");
            response.setDateHeader("Expires", 0L);
            response.setContentType("application/octet-stream;charset=GBK");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("GBK"), "ISO8859-1"));
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
            if (fileLength != 0) {
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                ServletOutputStream servletOS = response.getOutputStream();

                int readLength;
                while ((readLength = inStream.read(buf)) != -1) {
                    servletOS.write(buf, 0, readLength);
                }

                inStream.close();
                servletOS.flush();
                servletOS.close();
            }
        }

        file = null;
    }

    private String impfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload uploader = new ServletFileUpload(factory);
        uploader.setHeaderEncoding("ISO8859_1");
        String content = request.getContentType();
        List<FileItem> fileItems = null;
        String filePath = "";
        System.out.println(content);
        try {
            fileItems = uploader.parseRequest(request);
        } catch (FileUploadException var16) {
            var16.printStackTrace();
        }

        if (!fileItems.isEmpty()) {
            Iterator<FileItem> iter = fileItems.iterator();
            String uuid = UUID.randomUUID().toString();
            String upload_file = AppConfig.HZB_PATH + "/CadreRelated/document" + uuid + "/";
            File file = new File(upload_file);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }

            while (iter.hasNext()) {
                FileItem fi = (FileItem) iter.next();
                if (fi.getSize() != 0L) {
                    String namecode = new String(fi.getName().getBytes("ISO8859_1"), "gb2312");
                    String name = namecode.substring(namecode.lastIndexOf(separator) + 1);
                    filePath = upload_file + name;
                    try {
                        fi.write(new File(filePath));
                    } catch (Exception var15) {
                        var15.printStackTrace();
                    }
                    fi.getOutputStream().close();
                }
            }
        }
        return filePath;
    }


    @Transaction
    private String saveimp(String filepath,String sh000) {
    	HBSession sess = HBUtil.getHBSession();
		// ��������
		WJGLAdd wjgladd=new WJGLAdd();
//		GbkcFile adAppendFile=(GbkcFile)sess.get(GbkcFile.class,xfoid);
//		YearCheckFile adAppendFile = (YearCheckFile) sess.get(YearCheckFile.class, xfoid);

		wjgladd.setAdd00(sh000);
		
		File file=new File(filepath);
		wjgladd.setFilename(file.getName()); // �ļ�����
		wjgladd.setFilesize(String.valueOf(file.length()/1024) +" KB"); // �ļ���С
		wjgladd.setFileurl(filepath); // �ļ��洢���·��
		// ִ�����ݿ����
		sess.save(wjgladd); // ԭ���ļ���Ϣ
		sess.flush();
		return "success";
    
    }
    
	private void impAppendixFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		JSONObject json = new JSONObject();
		UPLODA_PATH_WIN = request.getRealPath("/");
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html");

		HBSession sess = HBUtil.getHBSession();
		// ��ȡϵͳ���û����ļ��洢·��
		List<String> pathlist = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'HZB_PATH'").list();
		String picPath = (pathlist.get(0).toString() + "/CadreRelated/document");
		picPath = picPath.replace("/", separator);
		// �õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ��ʣ���֤�ϴ��ļ��İ�ȫ
		String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
		// �ϴ�ʱ���ɵ���ʱ�ļ�����Ŀ¼
		String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
		// ��ȡ����ϵͳ����
		log.info(sys);
		if (sys.startsWith("WIN")) {
			savePath = UPLODA_PATH_WIN;
			tempPath = UPLODA_PATH_WIN + "temp";
		} else {
			savePath = UPLODA_PATH;
			tempPath = UPLODA_PATH + "temp";
		}
		File tmpFile = new File(tempPath);
		if (!tmpFile.exists()) {
			// ������ʱĿ¼
			tmpFile.mkdir();
		}
		// ��Ϣ��ʾ
		String message = "";
		try {
			// ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
			// 1������һ��DiskFileItemFactory����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// ���ù����Ļ������Ĵ�С�����ϴ����ļ���С�����������Ĵ�Сʱ���ͻ�����һ����ʱ�ļ���ŵ�ָ������ʱĿ¼���С�
			// ���û������Ĵ�СΪ20M�������ָ������ô�������Ĵ�СĬ����10KB
			factory.setSizeThreshold(1024 * 2);
			// �����ϴ�ʱ���ɵ���ʱ�ļ��ı���Ŀ¼
			factory.setRepository(tmpFile);
			// 2������һ���ļ��ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			// �����ļ��ϴ�����
			upload.setProgressListener(new ProgressListener() {
				public void update(long pBytesRead, long pContentLength, int arg2) {
					// log.info("�ļ���СΪ��" + pContentLength + ",��ǰ�Ѵ���" + pBytesRead);
				}
			});
			// ����ϴ��ļ�������������
			// upload.setHeaderEncoding("UTF-8");
			upload.setHeaderEncoding("GBK");
			// 3���ж��ύ�����������Ƿ����ϴ���������
			if (!ServletFileUpload.isMultipartContent(request)) {
				// ���մ�ͳ��ʽ��ȡ����
				return;
			}
			// �����ϴ������ļ��Ĵ�С�����ֵΪ20MB
			upload.setFileSizeMax(1024 * 1024 * 20);
			// �����ϴ��ļ����������ֵ�����ֵ=ͬʱ�ϴ��Ķ���ļ��Ĵ�С�����ֵ�ĺͣ�Ŀǰ����Ϊ200MB
			// upload.setSizeMax(1024 * 1024 * 20 * 10);
			// 4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {// ���item�з�װ������ͨ�����������
					String name = item.getFieldName();
					// �����ͨ����������ݵ�������������
					String value = item.getString("GBK");
					log.info(name + "=" + value);
				} else {// ���fileitem�з�װ�����ϴ��ļ� �õ��ϴ����ļ����ƣ�
					String filename = item.getName();
					log.info(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺
					// c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
					// �����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
					filename = filename.substring(filename.lastIndexOf(separator) + 1);
					// �õ��ϴ��ļ�����չ��
					String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
					// �����Ҫ�����ϴ����ļ����ͣ���ô����ͨ���ļ�����չ�����ж��ϴ����ļ������Ƿ�Ϸ�
					log.info("�ϴ����ļ�����չ���ǣ�" + fileExtName);
					// ��ȡitem�е��ϴ��ļ���������
					InputStream in = item.getInputStream();
					// �õ��ļ����������
					// String saveFilename = filename;
					String updateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()); //���������޸�ʱ��
					String savePicName = filename; //SysManagerUtils.getUserId().substring(28) + updateDate + "." + fileExtName;
					// �õ��ļ��ı���Ŀ¼
					String realSavePath = picPath + separator;
					realSavePath = makePath(savePicName + updateDate, realSavePath);
					// ����һ���ļ������
					String saveFullpath = realSavePath + separator + savePicName;
					String saveTablePath = saveFullpath.split("document")[1];
					System.out.println(saveTablePath);
					FileOutputStream out = new FileOutputStream(saveFullpath);
					// ����һ��������
					byte buffer[] = new byte[1024];
					// �ж��������е������Ƿ��Ѿ�����ı�ʶ
					int len = 0;
					// ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
					while ((len = in.read(buffer)) > 0) {
						// ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
						out.write(buffer, 0, len);
					}
					// �ر�������
					in.close();
					// �ر������
					out.close();
					// ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
					item.delete();
					message = "�ļ��ϴ��ɹ���";
					json.put("attachmentname", saveFullpath);
					json.put("status", 200);
				}
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			message = "�ϴ��ļ�������������ֵ20M";
		} catch (Exception e) {
			message = "�ļ��ϴ�ʧ��" + e.getMessage();
		} finally {
			try {
				if (!json.has("status")) {
					json.put("status", "500");
				}
				json.put("message", message);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.getWriter().write(json.toString());
		}
	}
    
    private String saveimpAppendix(String filepaths, String uuid1) {
		// TODO Auto-generated method stub
		return null;
	}

    /****************************
	 * Ϊ��ֹһ��Ŀ¼�������̫���ļ���Ҫʹ��hash�㷨��ɢ�洢
	 * 
	 * @param filename
	 *            �ļ���
	 * @param savePath
	 *            �洢·��
	 * @return �µĴ洢Ŀ¼
	 */
	private String makePath(String filename, String savePath) {
		// �õ��ļ�����hashCode��ֵ���õ��ľ���filename����ַ����������ڴ��еĵ�ַ
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xf; // 0--15
		int dir2 = (hashcode & 0xf0) >> 4; // 0-15
		// �����µı���Ŀ¼
		// String dir = savePath + "\\" + dir1 + "\\" + dir2; //upload\2\3
															// upload\3\5
		String dir = savePath + dir1 + separator + dir2; // upload\2\3
															// upload\3\5
		// File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
		File file = new File(dir);
		// ���Ŀ¼������
		if (!file.exists()) {
			// ����Ŀ¼
			file.mkdirs();
		}
		return dir;
	}
}
