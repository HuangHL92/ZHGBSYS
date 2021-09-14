package com.insigma.siis.local.pagemodel.xbrm;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.xbrm.pojo.Js13Pojo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.transform.Transformers;

import java.util.List;

public class QGYSPageModel extends PageModel {

    JSGLBS bs = new JSGLBS();

    /**
     * 批次信息
     *
     * @param start
     * @param limit
     * @return
     * @throws RadowException
     */
    @PageEvent("memberGrid.dogridquery")
    public int doMemberQuery(int start, int limit) throws RadowException {
        String rb_name1 = this.getPageElement("rb_name1").getValue();
        String rb_date1 = this.getPageElement("rb_date1").getValue();
        String where = "";
        if (StringUtils.isNotBlank(rb_name1)) {
            where += " and t.rb_name like '%" + rb_name1 + "%'";
        }
        if (StringUtils.isNotBlank(rb_date1)) {
            where += " and t.rb_date like '%" + rb_date1 + "%'";
        }
        String sql = "select * from RECORD_BATCH t where 1=1 " + where + " order by rb_sysno desc";
        this.pageQuery(sql, "SQL", start, limit);
        return EventRtnType.SPE_SUCCESS;
    }

    @PageEvent("openWindow")
    public int openWindow() {
        String rc = null;
        try {
            rc = this.getPageElement("rb_id").getValue();
        } catch (RadowException e) {
            e.printStackTrace();
        }
        List<Js13Pojo> js13Pojos = HBUtil.getHBSession()
                .createSQLQuery(" select * from JS13 js13 where js13.RB_ID = :rbid ").setParameter("rbid", rc)
                .setResultTransformer(Transformers.aliasToBean(Js13Pojo.class)).list();
        if (js13Pojos.size() == 0) {
            super.getExecuteSG().addExecuteCode("parent.saveAlert(0,'青干预审','未有青干预审数据');");
        } else {
            String s = "$h.openPageModeWin('zsys','pages.xbrm.QGYSPage2','青干预审',900,600,{rb_id:" + rc + "},g_contextpath);";
            super.getExecuteSG().addExecuteCode(s);
        }
        return EventRtnType.NORMAL_SUCCESS;
    }

    @Override
    public int doInit() throws RadowException {
        this.setNextEventName("memberGrid.dogridquery");
        return EventRtnType.NORMAL_SUCCESS;
    }

}
