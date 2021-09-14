package com.insigma.siis.local.pagemodel.huiyi;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author genggaopeng
 * @date 2019-12-12 16:11
 *
 * 将导出阶段生成的zip包传到前台以供下载
 */
public class GetHYZipServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filePath = req.getRealPath("/HY.zip");
        File downloadFile = new File(filePath);
        if (downloadFile.exists()) {
            String mimeType = getServletContext().getMimeType("HY.zip");
            resp.setContentType(mimeType);
            Long length = downloadFile.length();
            resp.setContentLength(length.intValue());
            String fileName = URLEncoder.encode(downloadFile.getName(), "utf-8");
            resp.addHeader("Content-Disposition", "attachment; filename=" + fileName);

            ServletOutputStream servletOutputStream = resp.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(downloadFile);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            int size = 0;
            byte[] b = new byte[4096];
            while ((size = bufferedInputStream.read(b)) != -1) {
                servletOutputStream.write(b, 0, size);
            }
            servletOutputStream.flush();
            servletOutputStream.close();
            bufferedInputStream.close();
        }
        //传输完毕后删除zip包，清理垃圾
        downloadFile.delete();
    }
}
