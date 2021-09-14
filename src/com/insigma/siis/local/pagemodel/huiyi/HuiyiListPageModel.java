package com.insigma.siis.local.pagemodel.huiyi;

import com.alibaba.fastjson.JSONArray;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.HY01;
import com.insigma.siis.local.business.entity.HY02;
import com.insigma.siis.local.business.entity.HY03;
import com.insigma.siis.local.business.entity.HY04;
import com.insigma.siis.local.business.entity.HY05;
import com.insigma.siis.local.business.entity.HY06;
import com.insigma.siis.local.business.entity.HY07;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.insigma.siis.local.pagemodel.sysmanager.role.addRolePageModel.log;

/**
 * @author genggaopeng
 * @date 2019-12-10 17:24
 * <p>
 * 代开会议列表
 */
public class HuiyiListPageModel extends PageModel {
    @Override
    public int doInit() throws RadowException {
        return 0;
    }

    //查询列表
    @PageEvent("getHuiyiList")
    public int getHuiyiList() {
        HBSession session = HBUtil.getHBSession();
        List<HY01> hy01List = session.createSQLQuery("select h.*,'部务会' AS hy0107 from HY01 h WHERE TO_DATE(hy0103,'yyyy-MM-dd') > sysdate ORDER BY hy0105 desc").addEntity(HY01.class).list();
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(hy01List);
        this.setSelfDefResData(jsonArray.toString());
        return EventRtnType.XML_SUCCESS;
    }

    //导出数据
    @PageEvent("importDB")
    public int importDB() throws Exception {
        HBSession session = HBUtil.getHBSession();
        //根据传递进来的id，更改导出标志
        String[] hy0100s = request.getParameter("hy0100s").split(" ");
        session.createSQLQuery("update hy01 set hy0104 = '0'").executeUpdate();
        for (String hy0100 : hy0100s) {
            String sql = "update hy01 set hy0104 = '1' where hy0100 = '" + hy0100 + "'";
            session.createSQLQuery(sql).executeUpdate();
        }
        session.flush();

        List<HY01> hy01List = session.createSQLQuery("select * from hy01 where hy0104 = 1").addEntity(HY01.class).list();
        List<HY02> hy02List = session.createSQLQuery("select * from hy02").addEntity(HY02.class).list();
        List<HY03> hy03List = session.createSQLQuery("select * from hy03").addEntity(HY03.class).list();
        List<HY04> hy04List = session.createSQLQuery("select * from hy04").addEntity(HY04.class).list();
        List<HY05> hy05List = session.createSQLQuery("select * from hy05").addEntity(HY05.class).list();
        List<HY06> hy06List = session.createSQLQuery("select * from hy06").addEntity(HY06.class).list();
        List<HY07> hy07List = session.createSQLQuery("select * from hy07").addEntity(HY07.class).list();

        Class.forName("org.sqlite.JDBC");
        //存储db文件路径
        String dbPath = request.getRealPath("/HY.db");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

        Statement stat = conn.createStatement();
        stat.executeUpdate("DROP TABLE IF EXISTS 'HY01'");
        stat.executeUpdate("DROP TABLE IF EXISTS 'HY02'");
        stat.executeUpdate("DROP TABLE IF EXISTS 'HY03'");
        stat.executeUpdate("DROP TABLE IF EXISTS 'HY04'");
        stat.executeUpdate("DROP TABLE IF EXISTS 'HY05'");
        stat.executeUpdate("DROP TABLE IF EXISTS 'HY06'");
        stat.executeUpdate("DROP TABLE IF EXISTS 'HY07'");
        //建表
        stat.executeUpdate("CREATE TABLE 'HY01' ( 'HY0100' TEXT NOT NULL, 'HY0101' TEXT NOT NULL, 'HY0102' TEXT NOT NULL, 'HY0103' TEXT NOT NULL, 'HY0104' TEXT NOT NULL, 'HY0105' DATE NOT NULL, 'HY0106' TEXT NOT NULL, 'HY0107' TEXT  )");
        stat.executeUpdate("CREATE TABLE 'HY02' ( 'HY0200' TEXT NOT NULL, 'HY0100' TEXT NOT NULL, 'HY0201' TEXT, 'HY0202' TEXT, 'HY0203' NUMBER(8,0) NOT NULL )");
        stat.executeUpdate("CREATE TABLE 'HY03' ( 'HY0300' TEXT NOT NULL, 'HY0100' TEXT NOT NULL, 'HY0200' TEXT NOT NULL, 'HY0301' TEXT NOT NULL, 'HY0303' NUMBER(8,0) NOT NULL )");
        stat.executeUpdate("CREATE TABLE 'HY04' ( 'HY0400' TEXT NOT NULL, 'HY0100' TEXT NOT NULL, 'HY0500' TEXT NOT NULL, 'HY0401' NUMBER(8,0) NOT NULL )");
        stat.executeUpdate("CREATE TABLE 'HY05' ( 'HY0500' TEXT NOT NULL, 'HY0501' TEXT NOT NULL, 'HY0502' TEXT NOT NULL )");
        stat.executeUpdate("CREATE TABLE 'HY06' ( 'HY0600' TEXT NOT NULL, 'HY0100' TEXT NOT NULL, 'HY0200' TEXT NOT NULL, 'HY0300' TEXT NOT NULL, 'HY0500' TEXT NOT NULL, 'HY0601' NUMBER(8,0) NOT NULL )");
        stat.executeUpdate("CREATE TABLE 'HY07' ( 'HY0700' TEXT NOT NULL, 'HY0100' TEXT, 'HY0200' TEXT, 'HY0300' TEXT, 'HY0701' TEXT NOT NULL, 'HY0702' TEXT NOT NULL, 'HY0703' NUMBER(8,0) NOT NULL, 'HY0704' TEXT NOT NULL )");

        String sql;
        if (hy01List.size() > 0) {
            for (HY01 hy01 : hy01List) {
                sql = "insert into hy01 values ('" + hy01.getHy0100() + "','" + hy01.getHy0101() + "','" + hy01.getHy0102() + "','" + hy01.getHy0103() + "','" + hy01.getHy0104() + "','" + hy01.getHy0105() + "','" + hy01.getHy0106() + "','" + hy01.getHy0107() + "')";
                stat.executeUpdate(sql);
            }
        }
        if (hy02List.size() > 0) {
            for (HY02 hy02 : hy02List) {
                sql = "INSERT INTO HY02 (HY0200, HY0100, HY0201, HY0202, HY0203) VALUES('" + hy02.getHy0200() + "', '" + hy02.getHy0100() + "', '" + checkNull(hy02.getHy0201()) + "', '" + checkNull(hy02.getHy0202()) + "', " + hy02.getHy0203() + ")";
                stat.executeUpdate(sql);
            }
        }
        if (hy03List.size() > 0) {
            for (HY03 hy03 : hy03List) {
                sql = "INSERT INTO HY03 (HY0300, HY0100, HY0200, HY0301, HY0303) VALUES('" + hy03.getHy0300() + "', '" + hy03.getHy0100() + "', '" + hy03.getHy0200() + "', '" + hy03.getHy0301() + "', " + hy03.getHy0303() + ")";
                stat.executeUpdate(sql);
            }
        }
        if (hy04List.size() > 0) {
            for (HY04 hy04 : hy04List) {
                sql = "INSERT INTO HY04 (HY0400, HY0100, HY0500, HY0401) VALUES('" + hy04.getHy0400() + "', '" + hy04.getHy0100() + "', '" + hy04.getHy0500() + "', " + hy04.getHy0401() + ")";
                stat.executeUpdate(sql);
            }
        }
        if (hy05List.size() > 0) {
            for (HY05 hy05 : hy05List) {
                sql = "INSERT INTO HY05 (HY0500, HY0501, HY0502) VALUES('" + hy05.getHy0500() + "', '" + hy05.getHy0501() + "', '" + hy05.getHy0502() + "')";
                stat.executeUpdate(sql);
            }
        }
        if (hy06List.size() > 0) {
            for (HY06 hy06 : hy06List) {
                sql = "INSERT INTO HY06 (HY0600, HY0100, HY0200, HY0300, HY0500, HY0601) VALUES('" + hy06.getHy0600() + "', '" + hy06.getHy0100() + "', '" + hy06.getHy0200() + "', '" + hy06.getHy0300() + "', '" + hy06.getHy0500() + "', " + hy06.getHy0601() + ")";
                stat.executeUpdate(sql);
            }
        }
        if (hy07List.size() > 0) {
            for (HY07 hy07 : hy07List) {
                sql = "INSERT INTO HY07 (HY0700, HY0100, HY0200, HY0300, HY0701, HY0702, HY0703, HY0704) VALUES('" + hy07.getHy0700() + "', '" + checkNull(hy07.getHy0100()) + "', '" + checkNull(hy07.getHy0200()) + "', '" + checkNull(hy07.getHy0300()) + "', '" + hy07.getHy0701() + "', '" + hy07.getHy0702() + "', " + hy07.getHy0703() + ", '" + hy07.getHy0704() + "')";
                stat.executeUpdate(sql);
            }
        }
        stat.close();
        conn.close();

        String filePath = request.getRealPath("/" + HuiyiUploadServlet.dir);
        String file = request.getRealPath("/HY.zip");
        //db文件和会议附件一起放入zip包中
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File(file))));
        zos.setEncoding("gbk");
        writeZip(new File(dbPath), "", zos);
        writeZip(new File(filePath), "", zos);
        //再搞一个发布时间
        File releaseTime = new File("releaseTime");
        FileWriter writer = new FileWriter(releaseTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        writer.write(sdf.format(new Date()));
        writer.flush();
        writer.close();
        writeZip(releaseTime, "", zos);
        releaseTime.delete();
        zos.close();
        //删除db文件
        File dbFile = new File(dbPath);
        dbFile.delete();
        this.setSelfDefResData("导出成功，请点击确定进行下载");
        return EventRtnType.XML_SUCCESS;
    }

    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.exists()) {
            if (file.isDirectory()) {
                parentPath += file.getName() + File.separator;
                File[] files = file.listFiles();
                if (files.length != 0) {
                    for (File f : files) {
                        writeZip(f, parentPath, zos);
                    }
                } else {
                    try {
                        zos.putNextEntry(new ZipEntry(parentPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte[] content = new byte[1024];
                    int len;
                    while ((len = fis.read(content)) != -1) {
                        zos.write(content, 0, len);
                        zos.flush();
                    }
                } catch (FileNotFoundException e) {
                    log.error("创建ZIP文件失败", e);
                } catch (IOException e) {
                    log.error("创建ZIP文件失败", e);
                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {
                        log.error("创建ZIP文件失败", e);
                    }
                }
            }
        }
    }

    private String checkNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }
}
