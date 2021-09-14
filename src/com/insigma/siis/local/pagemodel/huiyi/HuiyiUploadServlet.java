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
 * �ϴ��ļ���servlet
 */
public class HuiyiUploadServlet extends HttpServlet {
    // �ϴ��ļ��洢Ŀ¼
    public static final String dir = "HUIYICAILIAO";
    // �ϴ�����
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB����ʱ�ļ��ٽ�ֵ
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB������ļ���С
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB����󵥸������С

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ����Ƿ�Ϊ��ý���ϴ�
        if (!ServletFileUpload.isMultipartContent(request)) {
            // ���������ֹͣ
            PrintWriter writer = response.getWriter();
            writer.println("Error: ��������� enctype=multipart/form-data");
            writer.flush();
            return;
        }
        HBSession session = HBUtil.getHBSession();
        // �����ϴ�����
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // ������ʱ�洢Ŀ¼
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        // ��������ļ��ϴ�ֵ
        upload.setFileSizeMax(MAX_FILE_SIZE);
        // �����������ֵ (�����ļ��ͱ�����)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        // ���Ĵ���
        upload.setHeaderEncoding("UTF-8");
        // ������ʱ·�����洢�ϴ����ļ�
        // ���·����Ե�ǰӦ�õ�Ŀ¼

        String realPath = AppConfig.HZB_PATH;
        String uploadPath = realPath + File.separator + dir;
        // ���Ŀ¼�������򴴽�
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        StringBuilder result = new StringBuilder();
        
        try {
            // ���������������ȡ�ļ�����
            List<FileItem> formItems = upload.parseRequest(request);
            if (formItems != null && formItems.size() > 0) {
                // ����������
                for (int i = 0; i < formItems.size(); i++) {
                    FileItem item = formItems.get(i);
                    // �����ڱ��е��ֶ�
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        String uuid = UUID.randomUUID().toString();
                        String suffix = name.substring(name.lastIndexOf("."));
                        String filePath = uploadPath + File.separator + uuid + suffix;
                        File storeFile = new File(filePath);
                        // �ڿ���̨����ļ����ϴ�·��
                        System.out.println(filePath);
                        // �����ļ���Ӳ��
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
            request.setAttribute("message", "������Ϣ: " + ex.getMessage());
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(result.toString().trim());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
