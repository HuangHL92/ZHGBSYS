package com.insigma.siis.local.pagemodel.huiyi;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.HY01;
import com.insigma.siis.local.business.entity.HY02;
import com.insigma.siis.local.business.entity.HY03;
import com.insigma.siis.local.business.entity.HY04;
import com.insigma.siis.local.business.entity.HY06;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

import org.hibernate.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author genggaopeng
 * @date 2019-12-10 11:07
 */
public class SaveHuiyiServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HBSession session = HBUtil.getHBSession();
        String oldHy0100 = req.getParameter("oldHy0100");
        if (oldHy0100 != null && !oldHy0100.isEmpty()) {
            session.createSQLQuery("delete from hy01 where hy0100 = '" + oldHy0100 + "'").executeUpdate();
            session.createSQLQuery("delete from hy02 where hy0100 = '" + oldHy0100 + "'").executeUpdate();
            session.createSQLQuery("delete from hy03 where hy0100 = '" + oldHy0100 + "'").executeUpdate();
            session.createSQLQuery("delete from hy04 where hy0100 = '" + oldHy0100 + "'").executeUpdate();
            session.createSQLQuery("delete from hy06 where hy0100 = '" + oldHy0100 + "'").executeUpdate();
            //由于文件先上传，下面使用id进行更新，所以这里不能删除文件表的数据，会导致hy07表有少量垃圾数据
//            session.createSQLQuery("delete from hy07 where hy0100 = '" + oldHy0100 + "'").executeUpdate();
        }

        try {
            HBTransaction tx = session.beginTransaction();

            // 首先保存会议
            String hy0100 = UUID.randomUUID().toString();
            HY01 hy01 = new HY01();
            hy01.setHy0100(hy0100);
            hy01.setHy0101(req.getParameter("hy0101"));
            hy01.setHy0102(req.getParameter("hy0102"));
            hy01.setHy0103(req.getParameter("hy0103"));
            hy01.setHy0104("0");
            hy01.setHy0105(new Date());
            hy01.setHy0106(SysManagerUtils.getUserId());
            hy01.setHy0107(req.getParameter("hy0107"));
            session.save(hy01);
            // 保存出席人员
            String[] hy0500s = req.getParameter("hy0500").split(" ");
            for (int i = 0; i < hy0500s.length; i++) {
                if (!"".equals(hy0500s[i])) {
                    HY04 hy04 = new HY04();
                    String hy0400 = UUID.randomUUID().toString();
                    hy04.setHy0400(hy0400);
                    hy04.setHy0100(hy0100);
                    hy04.setHy0500(hy0500s[i]);
                    hy04.setHy0401(i);
                    session.save(hy04);
                }
            }
            String[] hy0202s = req.getParameterValues("hy0202");
            //这个值代表每个阶段包含多少议题
            String[] hy0202Intro = req.getParameter("hy0202Intro").split(" ");
            String[] hy0301s = req.getParameterValues("hy0301");
            String[] hy0600s = req.getParameterValues("hy0600");
            String[] hy0700s = req.getParameterValues("hy0700");
            for (int i = 0; i < hy0202s.length; i++) {
                // 保存阶段
                HY02 hy02 = new HY02();
                String hy0200 = UUID.randomUUID().toString();
                hy02.setHy0200(hy0200);
                hy02.setHy0100(hy0100);
                hy02.setHy0202(hy0202s[i]);
                hy02.setHy0203(i);
                session.save(hy02);
                // 保存议题
                int begin = 0;
                if (i != 0) {
                    //计算阶段内议题数组的初始值
                    begin = Integer.parseInt(hy0202Intro[i - 1]);
                }
                for (int j = begin; j < begin + Integer.parseInt(hy0202Intro[i]); j++) {
                    HY03 hy03 = new HY03();
                    String hy0300 = UUID.randomUUID().toString();
                    hy03.setHy0300(hy0300);
                    hy03.setHy0100(hy0100);
                    hy03.setHy0200(hy0200);
                    hy03.setHy0301(hy0301s[j]);
                    hy03.setHy0303(j);
                    session.save(hy03);
                    // 保存列席人员
                    if (!"".equals(hy0600s[j])) {
                        String[] hy0600 = hy0600s[j].split(" ");
                        for (int k = 0; k < hy0600.length; k++) {
                            HY06 hy06 = new HY06();
                            String hy0600True = UUID.randomUUID().toString();
                            hy06.setHy0600(hy0600True);
                            hy06.setHy0100(hy0100);
                            hy06.setHy0200(hy0200);
                            hy06.setHy0300(hy0300);
                            hy06.setHy0500(hy0600[k]);
                            hy06.setHy0601(k);
                            session.save(hy06);
                        }
                    }
                    // 更新附件内容
                    if (!"".equals(hy0700s[j])) {
                        String[] hy0700 = hy0700s[j].split(" ");
                        for (int k = 0; k < hy0700.length; k++) {
                            String sql = "update hy07 set hy0100 = '" + hy0100 + "',hy0200 = '" + hy0200 + "',hy0300 = '" + hy0300 + "' where hy0700 = '" + hy0700[k] + "'";
                            session.createSQLQuery(sql).executeUpdate();
                        }
                    }
                }
            }
            session.flush();
            tx.commit();
        } catch (AppException e) {
            e.printStackTrace();
        }
        resp.setContentType("text/plain;charset=utf-8");
        if (oldHy0100 != null && !oldHy0100.isEmpty()) {
            resp.getWriter().print("会议更新成功");
        } else {
            resp.getWriter().print("会议新建成功");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
