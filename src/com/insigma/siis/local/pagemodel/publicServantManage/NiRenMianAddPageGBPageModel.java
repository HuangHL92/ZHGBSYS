package com.insigma.siis.local.pagemodel.publicServantManage;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.*;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.hibwrap.WrapSessionFactoryImpl;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.lbs.leaf.persistence.HibernateHelper;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NiRenMianAddPageGBPageModel extends PageModel {

    private LogUtil log = new LogUtil();

    @Override
    @NoRequiredValidate
    public int doInit() throws RadowException {
        this.getExecuteSG().addExecuteCode("reShowMsg();");
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("initX")
    @NoRequiredValidate
    public int initX() throws RadowException {

        String a0000 = super.getPageElement("a0000").getValue();
        String date = DateUtil.getcurdate();
        refreshGrid(a0000);
        super.getExecuteSG().addExecuteCode("hideSave();");

        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("saveNRM")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int saveNRMinfo() {

        // 人员统一标识符
        String a0000 = "";
        // 拟任免信息ID
        String a5300 = "";
        // 拟任职务 value
        String a5304 = "";
        // 拟免职务 value
        String a5315 = "";
        // 任免理由
        String a5317 = "";
        // 呈报单位
        String a5319 = "";
        // 计算年龄时间
        String a5321 = "";
        // 填表时间
        String a5323 = "";
        // 填表人
        String a5327 = "";
        // 拟任免信息ID(primary key)
        String a5399 = "";
        // 拟任职务层次
        String a5366 = "";
        // 是否领导职务
       // String a5367 = "";
        // 拟任单位
        String a5368 = "";
        // 拟免职务id
        String a5369 = "";
        // 拟任状态
        String a5365 = "";
        try {
            a0000 = super.getPageElement("a0000").getValue().trim();
            a5300 = super.getPageElement("a5300").getValue().trim();
            a5304 = super.getPageElement("a5315").getValue().trim();
            a5315 = super.getPageElement("a0192a").getValue().trim();
            a5317 = super.getPageElement("a5317").getValue().trim();
            a5319 = super.getPageElement("a5319").getValue().trim();
            a5321 = super.getPageElement("a5321").getValue().trim();
            a5323 = super.getPageElement("a5323").getValue().trim();
            a5327 = super.getPageElement("a5327").getValue().trim();
            a5399 = super.getPageElement("a5399").getValue().trim();
            a5366 = super.getPageElement("a0501b_val").getValue().trim();
           // a5367 = super.getPageElement("a39067").getValue().trim();
            a5368 = super.getPageElement("a0195_val").getValue().trim();
            a5369 = super.getPageElement("nmzwid").getValue().trim();
            a5365 = super.getPageElement("a5365").getValue().trim();
        } catch (RadowException e) {
            e.printStackTrace();
        }
        HBSession session = HBUtil.getHBSession();

        A53 a53 = (A53) session.get(A53.class, a5300);

        boolean isSave = false;
        A53 a53_old = new A53();

        if (a53 == null) {
            a53 = new A53();
            isSave = true;
        } else {
            a53_old.setRbId(a53.getRbId());
            a53_old.setUpdated(a53.getUpdated());
            a53_old.setRbSysno(a53.getRbSysno());
            a53_old.setA0000(a53.getA0000());
            a53_old.setA5300(a53.getA5300());
            a53_old.setA5304(a53.getA5304());
            a53_old.setA5315(a53.getA5315());
            a53_old.setA5317(a53.getA5317());
            a53_old.setA5319(a53.getA5319());
            a53_old.setA5321(a53.getA5321());
            a53_old.setA5323(a53.getA5323());
            a53_old.setA5327(a53.getA5327());
            a53_old.setA5399(a53.getA5399());
            a53_old.setA5366(a53.getA5366());
            a53_old.setA5367(a53.getA5367());
            a53_old.setA5368(a53.getA5368());
            a53_old.setA5369(a53.getA5369());
            a53_old.setA5365(a53.getA5365());
        }

        a53.setA0000(a0000);
        a53.setA5300(a5300);
        a53.setA5304(a5304);
        a53.setA5315(a5315);
        a53.setA5317(a5317);
        a53.setA5319(a5319);
        a53.setA5321(a5321);
        a53.setA5323(a5323);
        a53.setA5327(a5327);
        a53.setA5366(a5366);
       // a53.setA5367(a5367);
        a53.setA5368(a5368);
        a53.setA5369(a5369);
        a53.setA5399(a5399);
        a53.setA5365(a5365);

        A01 a01 = (A01) session.get(A01.class, a53.getA0000());

        //if a5365 status is true(1) change other true(1) to false(0)
        if ("1".equals(a5365)) {

            // hb session use old , need new hb session
            HBSession hbSession = new HBSession();
            hbSession.setSession(new WrapSessionFactoryImpl(HibernateHelper.getSessionFactory()).openSession());

            List<A53> a53Lists = hbSession.createQuery(" from A53 where 1=1 and a5365 = '1' and a0000 = :A0000 ").setParameter("A0000", a0000).list();

            for (A53 changeA53 : a53Lists) {
                changeA53.setA5365("0");
                // don't use hbSession because finally submit session
                session.update(changeA53);
            }

        }

        if (isSave) {
            try {
                log.createLogNew("3A05", "拟任免记录新增", "拟任免信息集", a53.getA0000(), a01.getA0101(), Map2Temp.getLogInfo(new A05(), a53));
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (AppException e) {
                e.printStackTrace();
            }
            session.save(a53);
        } else {

            try {
                log.createLogNew("3A05", "拟任免记录更新", "拟任免信息集", a53.getA0000(), a01.getA0101(), Map2Temp.getLogInfo(a53_old, a53));
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (AppException e) {
                e.printStackTrace();
            }
            session.update(a53);
        }

        session.flush();

        refreshGrid(a0000);
        removeValue();

        return EventRtnType.NORMAL_SUCCESS;

    }

    @PageEvent("addNew")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int addNew() {
        removeValue();
        return EventRtnType.NORMAL_SUCCESS;

    }

    @PageEvent("save")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int save() throws RadowException, AppException {
        String a0000 = this.getPageElement("a0000").getValue();

        try {
            HBSession session = HBUtil.getHBSession();
            A01 a01 = (A01) session.get(A01.class, a0000);
            if (a01 == null) {
                this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'拟任免信息集','请先保存人员基本信息！');");
                return EventRtnType.NORMAL_SUCCESS;
            }
            String a5300 = this.getPageElement("a5300").getValue();
            String a5304 = this.getPageElement("a5304").getValue();
            String a5315 = this.getPageElement("a5315").getValue();
            String a5317 = this.getPageElement("a5317").getValue();
            String a5319 = this.getPageElement("a5319").getValue();
            String a5321 = this.getPageElement("a5321").getValue();
            String a5327 = this.getPageElement("a5327").getValue();
            String a5323 = this.getPageElement("a5323").getValue();
            A53 a53 = (A53) session.get(A53.class, a5300);
            if (a53 == null) {
                // 增加日志
                a53 = new A53();
                a53.setA0000(a0000);
                a53.setA5399(SysManagerUtils.getUserId());
            } else {
                // 更新纪实 拟任免信息
                String rbid = a53.getRbId();
                if (rbid != null && !"".equals(rbid)) {
                    session.createSQLQuery("update js01 set js0111=?,js0117=? where rb_id=? and a0000=?")
                            .setString(0, a5304).setString(1, a5315).setString(2, rbid).setString(3, a0000)
                            .executeUpdate();
                }
            } // 修改日志
            a53.setA5304(a5304);
            a53.setA5315(a5315);
            a53.setA5317(a5317);
            a53.setA5319(a5319);
            a53.setA5321(a5321);
            a53.setA5327(a5327);
            a53.setA5323(a5323);
            session.saveOrUpdate(a53);
            session.flush();

            this.getExecuteSG().addExecuteCode("parent.saveAlert(0,'拟任免信息集','保存成功！！');");
        } catch (Exception e) {
            this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'拟任免信息集','保存失败," + e.getMessage() + "');");
            e.printStackTrace();
        }

        return EventRtnType.NORMAL_SUCCESS;

    }

    @PageEvent("deleteRow")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int deleteRow(String a5300) throws RadowException, AppException {

        String a0000 = super.getPageElement("a0000").getValue();

        HBSession session = HBUtil.getHBSession();

        A53 a53 = (A53) session.get(A53.class, a5300);
        A01 a01 = (A01) session.get(A01.class, a53.getA0000());

        try {
            log.createLogNew("3A05", "拟任免记录删除", "拟任免信息集", a53.getA0000(), a01.getA0101(), Map2Temp.getLogInfo(a53, new A05()));
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        session.delete(a53);

        session.flush();

        refreshGrid(a0000);
        removeValue();

        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("TrainingInfoGrid.rowclick")
    @GridDataRange
    @NoRequiredValidate
    public int choose() {
        CommQuery commQuery = new CommQuery();

        try {
            int index = super.getPageElement("TrainingInfoGrid").getCueRowIndex();
            String js2300 = super.getPageElement("TrainingInfoGrid").getValue("js2300", index).toString();

            HashMap<String, Object> mapA53 = commQuery.getListBySQL(" select * from js23 where js2300 = '" + js2300 + "' ").get(0);
            super.getPageElement("js2302").setValue(mapA53.get("js2302").toString());
            super.getPageElement("js2303").setValue(mapA53.get("js2303").toString());
            super.getPageElement("js2304").setValue(mapA53.get("js2304").toString());
            super.getPageElement("js2305").setValue(mapA53.get("js2305").toString());
            super.getPageElement("js2306").setValue(mapA53.get("js2306").toString());
            super.getPageElement("js2307").setValue(mapA53.get("js2307").toString());
            super.getPageElement("js2309").setValue(mapA53.get("js2309").toString());
            String js2308flag=mapA53.get("js2308").toString();
            if("1".equals(js2308flag)){
            	super.getPageElement("js2308").setValue("有效");
            }else{
            	super.getPageElement("js2308").setValue("无效");
            }
        } catch (RadowException e) {
            e.printStackTrace();
        } catch (
                AppException e) {
            e.printStackTrace();
        }


        return EventRtnType.NORMAL_SUCCESS;
    }

    // ----------------------------------- private -----------------------//

    /**
     * remove page value
     */
    private void removeValue() {
        try {
            super.getPageElement("a5315").setValue("");
            super.getPageElement("a0192a").setValue("");
            super.getPageElement("a5317").setValue("");
            super.getPageElement("a5319").setValue("");
            super.getPageElement("a0501b_val").setValue("");
            super.getPageElement("a39067").setValue("");
            super.getPageElement("a0195_val").setValue("");
            super.getPageElement("nmzwid").setValue("");
            super.getPageElement("a0221").setValue("");
            super.getPageElement("a5300").setValue("");
            super.getPageElement("a5365").setValue("");
            super.getPageElement("a5365_combo").setValue("请您选择...");
        } catch (RadowException e) {
            e.printStackTrace();
        }
    }

    /**
     * refresh grid
     */
    private void refreshGrid(String a0000) {
        CommQuery commQuery = new CommQuery();
        String sqlA53 = " select * from js23 where A0000 = '" + a0000 + "' order by js2309 desc ";
        List<HashMap<String, Object>> listA53 = new ArrayList<HashMap<String, Object>>();
        try {
            // 拟任免加载
            listA53 = commQuery.getListBySQL(sqlA53);
            super.getPageElement("TrainingInfoGrid").setValueList(listA53);
        } catch (AppException e) {
            e.printStackTrace();
        } catch (RadowException e) {
            e.printStackTrace();
        }
    }

}
