package com.insigma.siis.local.pagemodel.publicServantManage;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.*;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.Jggwconf;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class RankAddPageNiRenPageModel extends PageModel {
    private LogUtil applog = new LogUtil();

    @Override
    @NoRequiredValidate
    public int doInit() throws RadowException {
        this.setNextEventName("initX");
        return EventRtnType.NORMAL_SUCCESS;
    }


    @PageEvent("initX")
    @NoRequiredValidate
    public int initX() throws RadowException {
        this.getExecuteSG().addExecuteCode("setValue();");
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("deleteRow")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int deleteRow(String a0500) throws RadowException, AppException {
/*		Map map = this.getRequestParamer();
		int index = map.get("eventParameter") == null ? null
				: Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0500 = this.getPageElement("TrainingInfoGrid").getValue("a0500", index).toString();*/
        String a0000 = this.getPageElement("subWinIdBussessId").getValue();
        HBSession sess = null;
        try {
            sess = HBUtil.getHBSession();
            A05 a05 = (A05) sess.get(A05.class, a0500);
            a05.setA0000(a0000);
            A01 a01 = (A01) sess.get(A01.class, a05.getA0000());

            //applog.createLogNew("3A05","现职务层次删除","现职务层次",a0000,a01.getA0101(), new ArrayList<Object[]>());

            //记录删除哪个
            applog.createLogNew("3A05", "现职务层次删除", "现职务层次", a0000, a01.getA0101(), new Map2Temp().getLogInfo(a05, new A05()));


            String a0524 = a05.getA0524();
            if ("1".equals(a0524)) {
                a01.setA0288(null);
                a01.setA0221(null);//设置 人物信息表的现职务层次 为空
                sess.saveOrUpdate(a01);
                sess.flush();
                this.getExecuteSG().addExecuteCode("realParent.setA0221Value('','')");//页面设置 人物信息表的现职务为空
                this.getExecuteSG().addExecuteCode("realParent.setA0288Value('')");//页面设置 人物信息表的现职务层次时间
            }

            sess.delete(a05);
            this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
            a05 = new A05();
            PMPropertyCopyUtil.copyObjValueToElement(a05, this);

            CustomQueryBS.setA01(a01.getA0000());
            A01 a01F = (A01) sess.get(A01.class, a01.getA0000());
            this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));

        } catch (Exception e) {
            this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','删除失败！',null,'220')");
            return EventRtnType.FAILD;
        }
        return EventRtnType.NORMAL_SUCCESS;
    }

    
    @PageEvent("selectFun.click")
    @Transaction
    public int selectFuns(String index) throws RadowException {
        //String a0000 = this.getPageElement("a0000").getValue();
        String a0525 = this.getPageElement("a0525").getValue();
        //int index = this.getPageElement("TrainingInfoGrid").getCueRowIndex();
        String[] arr = index.split("_");
        String a0500 = this.getPageElement("TrainingInfoGrid").getValue("a0500", Integer.parseInt(arr[0])).toString();
        String a0000 = this.getPageElement("subWinIdBussessId").getValue();
        HBSession sess = null;
        try {
            sess = HBUtil.getHBSession();
            A05 a05 = (A05) sess.get(A05.class, a0500);
            a05.setA0000(a0000);
            String sql = "update a05 set a0525 = '0' where a0000='" + a05.getA0000() + "'";
            sess.createSQLQuery(sql).executeUpdate();
            sess.flush();
            a05.setA0525(a0525);
            sess.saveOrUpdate(a05);
            sess.flush();
            A01 a01 = (A01) sess.get(A01.class, a0000);
            if (arr[1] != null && arr[1].equals("0")) {
                a01.setA0221(null);
                sess.saveOrUpdate(a01);
                sess.flush();
            } else if (arr[1] != null && arr[1].equals("1")) {
                String a0501b = a05.getA0501b();
                a01.setA0221(a0501b);
                sess.saveOrUpdate(a01);
                sess.flush();
            }

            String a0501b = a05.getA0501b();

            //获得职务层次名称
            String a0501bName = "";

            if (a0501b != null) {
                a0501bName = HBUtil.getValueFromTab("CODE_NAME", "CODE_VALUE", " code_type='ZB09' and code_value = '" + a0501b + "'");

            }
            this.getExecuteSG().addExecuteCode("realParent.setA0221Value('" + (a0501b == null ? "" : a0501b) + "','" + a0501bName + "')");//页面设置 人物信息表的现职务层次

            //this.getExecuteSG().addExecuteCode("realParent.setA0221Value('"+(a01.getA0221()==null?"":a01.getA0221())+"')");
            return EventRtnType.NORMAL_SUCCESS;
        } catch (Exception e) {
            this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','设置输出标识失败！',null,'220')");
            return EventRtnType.FAILD;
        }
    }
    
    /**
     * 显示职务职级grid表格
     *
     * @param start
     * @param limit
     * @return
     * @throws RadowException
     */
    @PageEvent("TrainingInfoGrid.dogridquery")
    @NoRequiredValidate
    public int trainingInforGridQuery(int start, int limit) throws RadowException {
        // String a0000 = this.getPageElement("a0000").getValue();
        String a0000 = this.getPageElement("subWinIdBussessId").getValue();
        String sql = "select * from A05 where a0000='" + a0000 + "' and a0531='0'";
        this.pageQuery(sql, "SQL", start, limit); // 处理分页查询
        return EventRtnType.SPE_SUCCESS;
    }

    /**
     * 点击新增按钮事件
     *
     * @param
     * @param
     * @return
     * @throws RadowException
     */
    @PageEvent("TrainingInfoAddBtn.onclick")
    @NoRequiredValidate
    public int trainingInforAddBtnWin(String id) throws RadowException {
        // String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
        String a0000 = this.getPageElement("subWinIdBussessId").getValue();
        if (a0000 == null || "".equals(a0000)) {//
            this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','请先保存人员基本信息！',null,'220')");
            return EventRtnType.NORMAL_SUCCESS;
        }
        A05 a05 = new A05();
        a05.setA0000(a0000);
        PMPropertyCopyUtil.copyObjValueToElement(a05, this);
        this.getExecuteSG().addExecuteCode("setA0517Disabled();");
        return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * 修改事件
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("TrainingInfoGrid.rowclick")
    @GridDataRange
    @NoRequiredValidate
    public int trainingInforGridOnRowClick() throws RadowException {
        int index = this.getPageElement("TrainingInfoGrid").getCueRowIndex();
        String a0500 = this.getPageElement("TrainingInfoGrid").getValue("a0500", index).toString();
        String a0000 = this.getPageElement("subWinIdBussessId").getValue();
        HBSession sess = null;
        try {
            sess = HBUtil.getHBSession();
            A05 a05 = (A05) sess.get(A05.class, a0500);
            a05.setA0000(a0000);
            PMPropertyCopyUtil.copyObjValueToElement(a05, this);
        } catch (Exception e) {
            this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','查询失败！',null,'220')");
            return EventRtnType.FAILD;
        }
        this.getExecuteSG().addExecuteCode("setA0517Disabled();");
        //this.getExecuteSG().addExecuteCode("a0501bChange();");
        return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * 将实体POJO转化为JSON
     *
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static <T> String objectToJson(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Convert object to JSON string
        String jsonStr = "{}";
        try {
            jsonStr = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return jsonStr;
    }

}
