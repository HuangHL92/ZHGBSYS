package com.insigma.siis.local.pagemodel.huiyi;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.HY05;

import net.sf.json.JSONArray;

import java.util.List;

/**
 * @author genggaopeng
 * @date 2019-12-5 16:17
 */
public class GetPersonsPageModel extends PageModel {
    //前台把值放入固定框，这里取出来放入jsp中。odin写法
    @Override
    public int doInit() throws RadowException {
        String childrenStr = this.getRadow_parent_data();
        this.getExecuteSG().addExecuteCode("$('#inputDivId').val('" + childrenStr + "')");
        return 0;
    }

    //查询所有处室信息
    @PageEvent("getHY0502")
    public int getHY0502() {
        HBSession session = HBUtil.getHBSession();
        List<String> hy0502List = session.createSQLQuery("select hy0502 from HY05 group by hy0502").list();
        JSONArray jsonArray = JSONArray.fromObject(hy0502List);
        this.setSelfDefResData(jsonArray.toString());
        return EventRtnType.XML_SUCCESS;
    }

    //查询处室下的人员
    @PageEvent("getHY05")
    public int getHY05() {
        String hy0502 = request.getParameter("hy0502");
        HBSession session = HBUtil.getHBSession();
        List<HY05> hy05List = session.createSQLQuery("select * from HY05 where hy0502 = '" + hy0502 + "'").addEntity(HY05.class).list();
        JSONArray jsonArray = JSONArray.fromObject(hy05List);
        this.setSelfDefResData(jsonArray.toString());
        return EventRtnType.XML_SUCCESS;
    }
}
