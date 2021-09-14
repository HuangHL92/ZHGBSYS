package com.insigma.siis.local.pagemodel.huiyi;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.HY07;
import com.insigma.siis.local.epsoft.config.AppConfig;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

/**
 * @author genggaopeng
 * @date 2019-12-9 11:34
 * <p>
 * 上传文件的servlet
 */
public class HuiyiUploadServlet extends HttpServlet {
    // 上传文件存储目录
    public static final String dir = "HUIYICAILIAO";
    // 上传配置
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB，临时文件临界值
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB，最大文件大小
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB，最大单个请求大小

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }
        HBSession session = HBUtil.getHBSession();
        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        // 中文处理
        upload.setHeaderEncoding("UTF-8");
        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录

        String realPath = AppConfig.HZB_PATH;
        String uploadPath = realPath + File.separator + dir;
        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        StringBuilder result = new StringBuilder();
        
        try {
            // 解析请求的内容提取文件数据
            List<FileItem> formItems = upload.parseRequest(request);
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (int i = 0; i < formItems.size(); i++) {
                    FileItem item = formItems.get(i);
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        String uuid = UUID.randomUUID().toString();
                        String suffix = name.substring(name.lastIndexOf("."));
                        String filePath = uploadPath + File.separator + uuid + suffix;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);

                        HY07 hy07 = new HY07();
                        hy07.setHy0700(uuid);
                        hy07.setHy0701(name);
                        hy07.setHy0702("/" + dir);
                        hy07.setHy0703(i);
                        hy07.setHy0704(suffix);
                        session.save(hy07);
                        session.flush();

                        result.append(uuid + " ");
                    } else {
                        String[] fileIds = item.getString().split(" ");
                        for (String fileId : fileIds) {
                            HY07 hy07 = (HY07) session.createSQLQuery("select * from hy07 where hy0700 = '" + fileId + "'").addEntity(HY07.class).list().get(0);
                            String filePath = request.getRealPath("/" + dir + "/" + hy07.getHy0700() + hy07.getHy0704());
                            File file = new File(filePath);
                            file.delete();
                            session.createSQLQuery("delete from hy07 where hy0700 = '" + fileId + "'").executeUpdate();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message", "错误信息: " + ex.getMessage());
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(result.toString().trim());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
