package com.insigma.siis.local.pagemodel.huiyi;

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
import com.insigma.siis.local.business.entity.HY06;
import com.insigma.siis.local.business.entity.HY07;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author genggaopeng
 * @date 2019-12-18 11:25
 */
public class EditHuiyiPageModel extends PageModel {

    @Override
    public int doInit() throws RadowException {
        return 0;
    }

    @PageEvent("getHuiyiInfo")
    public int getHuiyiInfo() {
        String hy0100 = request.getParameter("hy0100");
        HBSession session = HBUtil.getHBSession();
        // 当hy0100为空时会发生异常，已经由前台逻辑解决了
        HY01 hy01 = (HY01) session.createSQLQuery("select * from hy01 where hy0100 = '" + hy0100 + "'").addEntity(HY01.class).list().get(0);
        List<HY04> hy04List = session.createSQLQuery("select * from hy04 where hy0100 = '" + hy0100 + "' order by hy0401").addEntity(HY04.class).list();
        for (HY04 hy04 : hy04List) {
            String hy0501 = (String) session.createSQLQuery("select hy0501 from hy05 where hy0500 = '" + hy04.getHy0500() + "'").list().get(0);
            hy04.setHy0501(hy0501);
        }
        hy01.setHy04List(hy04List);

        List<HY02> hy02List = session.createSQLQuery("select * from hy02 where hy0100 = '" + hy0100 + "' order by hy0203").addEntity(HY02.class).list();
        for (HY02 hy02 : hy02List) {
            List<HY03> hy03List = session.createSQLQuery("select * from hy03 where hy0200 = '" + hy02.getHy0200() + "' order by hy0303").addEntity(HY03.class).list();
            hy02.setHy03List(hy03List);
            for (HY03 hy03 : hy03List) {
                List<HY06> hy06List = session.createSQLQuery("select * from hy06 where hy0300 = '" + hy03.getHy0300() + "' order by hy0601").addEntity(HY06.class).list();
                for (HY06 hy06 : hy06List) {
                    String hy0501 = (String) session.createSQLQuery("select hy0501 from hy05 where hy0500 = '" + hy06.getHy0500() + "'").list().get(0);
                    hy06.setHy0501(hy0501);
                }
                hy03.setHy06List(hy06List);
                List<HY07> hy07List = session.createSQLQuery("select * from hy07 where hy0300 = '" + hy03.getHy0300() + "' order by hy0703").addEntity(HY07.class).list();
                hy03.setHy07List(hy07List);
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hy01", hy01);
        map.put("hy02List", hy02List);

        JSONObject jsonObject = JSONObject.fromObject(map);
        System.out.println(jsonObject.toString());
        this.setSelfDefResData(jsonObject.toString());
        return EventRtnType.XML_SUCCESS;
    }

    @PageEvent("deleteHuiyi")
    public int deleteHuiyi() {
        String hy0100 = request.getParameter("hy0100");
        HBSession session = HBUtil.getHBSession();
        if (hy0100 != null && !hy0100.isEmpty()) {
            session.createSQLQuery("delete from hy01 where hy0100 = '" + hy0100 + "'").executeUpdate();
            session.createSQLQuery("delete from hy02 where hy0100 = '" + hy0100 + "'").executeUpdate();
            session.createSQLQuery("delete from hy03 where hy0100 = '" + hy0100 + "'").executeUpdate();
            session.createSQLQuery("delete from hy04 where hy0100 = '" + hy0100 + "'").executeUpdate();
            session.createSQLQuery("delete from hy06 where hy0100 = '" + hy0100 + "'").executeUpdate();
            List<HY07> hy07List = session.createSQLQuery("select * from hy07 where hy0100 = '" + hy0100 + "' order by hy0703").addEntity(HY07.class).list();
            String dir = request.getRealPath(HuiyiUploadServlet.dir);
            String path = "";
            File file;
            for (HY07 hy07 : hy07List) {
                path = dir + "/" + hy07.getHy0700() + hy07.getHy0704();
                file = new File(path);
                file.delete();
            }
            session.createSQLQuery("delete from hy07 where hy0100 = '" + hy0100 + "'").executeUpdate();
        }
        return EventRtnType.XML_SUCCESS;
    }
}
