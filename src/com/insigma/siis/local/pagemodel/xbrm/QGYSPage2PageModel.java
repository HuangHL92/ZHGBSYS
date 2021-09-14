package com.insigma.siis.local.pagemodel.xbrm;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.xbrm.pojo.Js13Pojo;
import com.insigma.siis.local.pagemodel.xbrm.pojo.Js99Pojo;
import org.hibernate.transform.Transformers;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class QGYSPage2PageModel extends PageModel {

    JSGLBS bs = new JSGLBS();

    @Override
    public int doInit() throws RadowException {
        this.setNextEventName("initX");
        // 设置下拉框
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("initX")
    public int initX() {
        String rbid = null;
        try {
            rbid = super.getPageElement("rbId").getValue();
        } catch (RadowException e) {
            e.printStackTrace();
        }
        List<Js13Pojo> js13Pojos = getJs13List(rbid);
        List<Js99Pojo> js99Pojos = getJs99List(rbid);
        if (js13Pojos.size() == 0 || js99Pojos.size() == 0) {
            super.getExecuteSG().addExecuteCode("$h.alert('系统提示：','暂无青干数据！',null,180);");
        } else {
            this.setPageValue(js13Pojos.get(0));
            this.setPageValue(js99Pojos.get(0));
        }
        return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * 信息导出
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("ExpGird")
    public int ExpGird(String param) throws RadowException {
        QCJSFileExportBS fileBS = new QCJSFileExportBS();
        // String rbId = this.getPageElement("rbId").getValue();
        String rbId = this.request.getParameter("rbId");// 批次
        String buttontext = this.request.getParameter("buttontext");

        this.request.setAttribute("rbId", rbId);

        fileBS.setPm(this);
        fileBS.setSheetName(buttontext);
        fileBS.setOutputpath(JSGLBS.HZBPATH + "zhgboutputfiles/" + rbId + "/");
        File f = new File(fileBS.getOutputpath());

        if (f.isDirectory()) {// 先清空输出目录
            JSGLBS.deleteDirectory(fileBS.getOutputpath());
        }
        try {
            fileBS.exportZSYS();
            // fileBS.exportJYRX();
        } catch (Exception e) {
            this.setMainMessage(e.getMessage());
            e.printStackTrace();
        }

        List<String> fns = fileBS.getOutputFileNameList();
        String downloadPath = "";
        String downloadName = "";
        if (fns.size() == 1) {// 一个文件直接下载
            downloadPath = fileBS.getOutputpath() + fns.get(0);
            downloadName = fns.get(0);
        } else if (fns.size() > 1) {// 压缩
            downloadPath = fileBS.getOutputpath() + fileBS.getSheetName() + ".zip";
            downloadName = fileBS.getSheetName() + ".zip";
            Zip7z.zip7Z(fileBS.getOutputpath(), downloadPath, null);

        } else {
            return EventRtnType.NORMAL_SUCCESS;
        }
        String downloadUUID = UUID.randomUUID().toString();
        request.getSession().setAttribute(downloadUUID, new String[]{downloadPath, downloadName});
        this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='" + downloadUUID + "';");
        this.getExecuteSG().addExecuteCode("downloadByUUID();");
        return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * 职数预审修改保存
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("saveQG.onclick")
    public int saveZS() {
        String rbId = "";
        try {
            rbId = this.getPageElement("rbId").getValue();
        } catch (RadowException e) {
            e.printStackTrace();
        }

        List<Js13Pojo> js13Pojos = getJs13List(rbId);
        List<Js99Pojo> js99Pojos = getJs99List(rbId);
        if (js13Pojos.size() == 0 || js99Pojos.size() == 0) {
            super.setMainMessage("暂无青干数据！");
            return EventRtnType.NORMAL_SUCCESS;
        }

        bs.setPm(this);
        try {
            bs.updateJs13(js13Pojos.get(0).getJS1300(), rbId, HBUtil.getHBSession());
            bs.updateJs99(js99Pojos.get(0).getJS9900(), rbId, HBUtil.getHBSession());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setMainMessage("保存成功");
        return EventRtnType.NORMAL_SUCCESS;
    }

    // ------------------------------------ set and get ------------------------------------//

    private void setPageValue(Js13Pojo js13) {
        try {
            super.getPageElement("r1c1").setValue(js13.getJS1311());
            super.getPageElement("r1c2").setValue(js13.getJS1312());
            super.getPageElement("r1c3").setValue(js13.getJS1313());
            super.getPageElement("r1c4").setValue(js13.getJS1314());
            super.getPageElement("r1c5").setValue(js13.getJS1315());
            super.getPageElement("r1c6").setValue(js13.getJS1316());

            super.getPageElement("r2c1").setValue(js13.getJS1321());
            super.getPageElement("r2c2").setValue(js13.getJS1322());
            super.getPageElement("r2c3").setValue(js13.getJS1323());
            super.getPageElement("r2c4").setValue(js13.getJS1324());
            super.getPageElement("r2c5").setValue(js13.getJS1325());
            super.getPageElement("r2c6").setValue(js13.getJS1326());

            super.getPageElement("r3c1").setValue(js13.getJS1331());
            super.getPageElement("r3c2").setValue(js13.getJS1332());
            super.getPageElement("r3c3").setValue(js13.getJS1333());
            super.getPageElement("r3c4").setValue(js13.getJS1334());
            super.getPageElement("r3c5").setValue(js13.getJS1335());
            super.getPageElement("r3c6").setValue(js13.getJS1336());

            super.getPageElement("r4c1").setValue(js13.getJS1341());
            super.getPageElement("r4c2").setValue(js13.getJS1342());
            super.getPageElement("r4c3").setValue(js13.getJS1343());
            super.getPageElement("r4c4").setValue(js13.getJS1344());
            super.getPageElement("r4c5").setValue(js13.getJS1345());
            super.getPageElement("r4c6").setValue(js13.getJS1346());

            super.getPageElement("r5c1").setValue(js13.getJS1351());
            super.getPageElement("r5c2").setValue(js13.getJS1352());
            super.getPageElement("r5c3").setValue(js13.getJS1353());
            super.getPageElement("r5c4").setValue(js13.getJS1354());
            super.getPageElement("r5c5").setValue(js13.getJS1355());
            super.getPageElement("r5c6").setValue(js13.getJS1356());

            super.getPageElement("r6c1").setValue(js13.getJS1361());
            super.getPageElement("r6c2").setValue(js13.getJS1362());
            super.getPageElement("r6c3").setValue(js13.getJS1363());
            super.getPageElement("r6c4").setValue(js13.getJS1364());
            super.getPageElement("r6c5").setValue(js13.getJS1365());
            super.getPageElement("r6c6").setValue(js13.getJS1366());

            super.getPageElement("r1c7").setValue(js13.getJS1395());
            super.getPageElement("js1396").setValue(js13.getJS1396());
            super.getPageElement("js1397").setValue(js13.getJS1397());
            super.getPageElement("js1398").setValue(js13.getJS1398());

        } catch (RadowException e) {
            e.printStackTrace();
        }
    }

    private void setPageValue(Js99Pojo js99) {
        try {
            super.getPageElement("t2r1c1").setValue(js99.getJS9911());
            super.getPageElement("t2r1c2").setValue(js99.getJS9912());
            super.getPageElement("t2r1c3").setValue(js99.getJS9913());
            super.getPageElement("t2r1c4").setValue(js99.getJS9914());
            super.getPageElement("t2r1c5").setValue(js99.getJS9915());
            super.getPageElement("t2r1c6").setValue(js99.getJS9916());

            super.getPageElement("t2r2c1").setValue(js99.getJS9921());
            super.getPageElement("t2r2c2").setValue(js99.getJS9922());
            super.getPageElement("t2r2c3").setValue(js99.getJS9923());
            super.getPageElement("t2r2c4").setValue(js99.getJS9924());
            super.getPageElement("t2r2c5").setValue(js99.getJS9925());
            super.getPageElement("t2r2c6").setValue(js99.getJS9926());

            super.getPageElement("t2r3c1").setValue(js99.getJS9931());
            super.getPageElement("t2r3c2").setValue(js99.getJS9932());
            super.getPageElement("t2r3c3").setValue(js99.getJS9933());
            super.getPageElement("t2r3c4").setValue(js99.getJS9934());
            super.getPageElement("t2r3c5").setValue(js99.getJS9935());
            super.getPageElement("t2r3c6").setValue(js99.getJS9936());

            super.getPageElement("t2r4c1").setValue(js99.getJS9941());
            super.getPageElement("t2r4c2").setValue(js99.getJS9942());
            super.getPageElement("t2r4c3").setValue(js99.getJS9943());
            super.getPageElement("t2r4c4").setValue(js99.getJS9944());
            super.getPageElement("t2r4c5").setValue(js99.getJS9945());
            super.getPageElement("t2r4c6").setValue(js99.getJS9946());

            super.getPageElement("t2r5c1").setValue(js99.getJS9951());
            super.getPageElement("t2r5c2").setValue(js99.getJS9952());
            super.getPageElement("t2r5c3").setValue(js99.getJS9953());
            super.getPageElement("t2r5c4").setValue(js99.getJS9954());
            super.getPageElement("t2r5c5").setValue(js99.getJS9955());
            super.getPageElement("t2r5c6").setValue(js99.getJS9956());

            super.getPageElement("t2r6c1").setValue(js99.getJS9961());
            super.getPageElement("t2r6c2").setValue(js99.getJS9962());
            super.getPageElement("t2r6c3").setValue(js99.getJS9963());
            super.getPageElement("t2r6c4").setValue(js99.getJS9964());
            super.getPageElement("t2r6c5").setValue(js99.getJS9965());
            super.getPageElement("t2r6c6").setValue(js99.getJS9966());

            super.getPageElement("t2r1c7").setValue(js99.getJS9995());
            super.getPageElement("js9996").setValue(js99.getJS9996());
            super.getPageElement("js9997").setValue(js99.getJS9997());
            super.getPageElement("js9998").setValue(js99.getJS9998());

        } catch (RadowException e) {
            e.printStackTrace();
        }
    }

    private List<Js13Pojo> getJs13List(String rbid) {
        return HBUtil.getHBSession().createSQLQuery(" select * from JS13 js13 where js13.RB_ID = :rbid ")
                .setParameter("rbid", rbid).setResultTransformer(Transformers.aliasToBean(Js13Pojo.class)).list();
    }

    private List<Js99Pojo> getJs99List(String rbid) {
        return HBUtil.getHBSession().createSQLQuery(" select * from JS99 js99 where js99.RB_ID = :rbid ")
                .setParameter("rbid", rbid).setResultTransformer(Transformers.aliasToBean(Js99Pojo.class)).list();
    }


}
