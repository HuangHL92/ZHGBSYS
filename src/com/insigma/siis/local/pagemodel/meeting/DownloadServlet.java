/**
 * Copyright (C), 2019-2019, INSIGMACompany
 * FileName: JinQian
 * Author:   Qian_INSIGMA
 * Date:     2019/8/6 14:34
 * Description: 干部相关材料下载
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
 * Description :  干部相关材料下载
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
        System.out.println("*************进入方法");
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
		// 创建对象
		WJGLAdd wjgladd=new WJGLAdd();
//		GbkcFile adAppendFile=(GbkcFile)sess.get(GbkcFile.class,xfoid);
//		YearCheckFile adAppendFile = (YearCheckFile) sess.get(YearCheckFile.class, xfoid);

		wjgladd.setAdd00(sh000);
		
		File file=new File(filepath);
		wjgladd.setFilename(file.getName()); // 文件名称
		wjgladd.setFilesize(String.valueOf(file.length()/1024) +" KB"); // 文件大小
		wjgladd.setFileurl(filepath); // 文件存储相对路径
		// 执行数据库操作
		sess.save(wjgladd); // 原件文件信息
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
		// 获取系统配置缓存文件存储路径
		List<String> pathlist = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'HZB_PATH'").list();
		String picPath = (pathlist.get(0).toString() + "/CadreRelated/document");
		picPath = picPath.replace("/", separator);
		// 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
		// 上传时生成的临时文件保存目录
		String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
		// 获取操作系统名称
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
			// 创建临时目录
			tmpFile.mkdir();
		}
		// 消息提示
		String message = "";
		try {
			// 使用Apache文件上传组件处理文件上传步骤：
			// 1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
			// 设置缓冲区的大小为20M，如果不指定，那么缓冲区的大小默认是10KB
			factory.setSizeThreshold(1024 * 2);
			// 设置上传时生成的临时文件的保存目录
			factory.setRepository(tmpFile);
			// 2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 监听文件上传进度
			upload.setProgressListener(new ProgressListener() {
				public void update(long pBytesRead, long pContentLength, int arg2) {
					// log.info("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
				}
			});
			// 解决上传文件名的中文乱码
			// upload.setHeaderEncoding("UTF-8");
			upload.setHeaderEncoding("GBK");
			// 3、判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(request)) {
				// 按照传统方式获取数据
				return;
			}
			// 设置上传单个文件的大小的最大值为20MB
			upload.setFileSizeMax(1024 * 1024 * 20);
			// 设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为200MB
			// upload.setSizeMax(1024 * 1024 * 20 * 10);
			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {// 如果item中封装的是普通输入项的数据
					String name = item.getFieldName();
					// 解决普通输入项的数据的中文乱码问题
					String value = item.getString("GBK");
					log.info(name + "=" + value);
				} else {// 如果fileitem中封装的是上传文件 得到上传的文件名称，
					String filename = item.getName();
					log.info(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf(separator) + 1);
					// 得到上传文件的扩展名
					String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
					// 如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
					log.info("上传的文件的扩展名是：" + fileExtName);
					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					// 得到文件保存的名称
					// String saveFilename = filename;
					String updateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()); //新增或者修改时间
					String savePicName = filename; //SysManagerUtils.getUserId().substring(28) + updateDate + "." + fileExtName;
					// 得到文件的保存目录
					String realSavePath = picPath + separator;
					realSavePath = makePath(savePicName + updateDate, realSavePath);
					// 创建一个文件输出流
					String saveFullpath = realSavePath + separator + savePicName;
					String saveTablePath = saveFullpath.split("document")[1];
					System.out.println(saveTablePath);
					FileOutputStream out = new FileOutputStream(saveFullpath);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while ((len = in.read(buffer)) > 0) {
						// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						out.write(buffer, 0, len);
					}
					// 关闭输入流
					in.close();
					// 关闭输出流
					out.close();
					// 删除处理文件上传时生成的临时文件
					item.delete();
					message = "文件上传成功！";
					json.put("attachmentname", saveFullpath);
					json.put("status", 200);
				}
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			message = "上传文件不允许超过上限值20M";
		} catch (Exception e) {
			message = "文件上传失败" + e.getMessage();
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
	 * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
	 * 
	 * @param filename
	 *            文件名
	 * @param savePath
	 *            存储路径
	 * @return 新的存储目录
	 */
	private String makePath(String filename, String savePath) {
		// 得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xf; // 0--15
		int dir2 = (hashcode & 0xf0) >> 4; // 0-15
		// 构造新的保存目录
		// String dir = savePath + "\\" + dir1 + "\\" + dir2; //upload\2\3
															// upload\3\5
		String dir = savePath + dir1 + separator + dir2; // upload\2\3
															// upload\3\5
		// File既可以代表文件也可以代表目录
		File file = new File(dir);
		// 如果目录不存在
		if (!file.exists()) {
			// 创建目录
			file.mkdirs();
		}
		return dir;
	}
}
