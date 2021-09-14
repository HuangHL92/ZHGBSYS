package com.insigma.siis.local.pagemodel.huiyi;

import com.alibaba.fastjson.JSONArray;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.HY01;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author genggaopeng
 * @date 2019-12-10 17:25
 */
public class PastHuiyiListPageModel extends PageModel {
    @Override
    public int doInit() throws RadowException {
        return 0;
    }

    //获取总数，以便前台进行分页
    @PageEvent("getCount")
    public int getCount() {
        HBSession session = HBUtil.getHBSession();
        BigDecimal count = (BigDecimal) session.createSQLQuery("select count(*) from HY01 h WHERE TO_DATE(hy0103,'yyyy-MM-dd') <= sysdate").list().get(0);
        this.setSelfDefResData(count);
        return EventRtnType.XML_SUCCESS;
    }

    //根据传进来的分页信息，查询列表
    @PageEvent("getPastHuiyiList")
    public int getPastHuiyiList() {
        int curr = Integer.parseInt(request.getParameter("curr"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        int begin = (curr - 1) * limit;
        int end = curr * limit;
        HBSession session = HBUtil.getHBSession();
        List<HY01> hy01List = session.createSQLQuery("SELECT * FROM ( SELECT a.*, rownum rowno FROM ( SELECT h.* FROM HY01 h WHERE TO_DATE(hy0103, 'yyyy-MM-dd') <= SYSDATE ORDER BY hy0105 DESC) a WHERE rownum <= " + end + ") b WHERE b.rowno > " + begin).addEntity(HY01.class).list();
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(hy01List);
        this.setSelfDefResData(jsonArray.toString());
        return EventRtnType.XML_SUCCESS;
    }
}
