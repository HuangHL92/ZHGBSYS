package com.insigma.siis.local.pagemodel.xbrm;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.hibernate.transform.Transformers;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.entity.Js02;
import com.insigma.siis.local.business.entity.Js03;
import com.insigma.siis.local.business.entity.Js04;
import com.insigma.siis.local.business.entity.Js05;
import com.insigma.siis.local.business.entity.Js06;
import com.insigma.siis.local.business.entity.Js07;
import com.insigma.siis.local.business.entity.Js08;
import com.insigma.siis.local.business.entity.Js09;
import com.insigma.siis.local.business.entity.Js10;
import com.insigma.siis.local.business.entity.Js11;
import com.insigma.siis.local.business.entity.Js12;
import com.insigma.siis.local.business.entity.Js13;
import com.insigma.siis.local.business.entity.Js14;
import com.insigma.siis.local.business.entity.Js15;
import com.insigma.siis.local.business.entity.Js19;
import com.insigma.siis.local.business.entity.Js20;
import com.insigma.siis.local.business.entity.Js21;
import com.insigma.siis.local.business.entity.Js33;
import com.insigma.siis.local.business.entity.Js99;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.xbrm.pojo.Js18Pojo;
import com.insigma.siis.local.pagemodel.xbrm.pojo.Js19Pojo;
import com.utils.CommonQueryBS;

public class JSGLBS {

    private PageModel pm;

    public boolean ishasRecord(String tablename, String js0101) throws AppException {
        CommonQueryBS cq = new CommonQueryBS();
        HashMap<String, Object> nummap = cq.getMapBySQL("select count(1) num from " + tablename + " where js0100='" + js0101 + "'");
        String num = (String) nummap.get("num");
        if ("0".equals(num)) {
            //��¼�� insert
            return false;
        } else {
            //update
            return true;
        }
    }


    private static String getPath() {
        String upload_file = AppConfig.HZB_PATH + "/";
        try {
            File file = new File(upload_file);
            //����ļ��в������򴴽�
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //��ѹ·��
        return upload_file;
    }

    public final static String HZBPATH = getPath();

    public PageModel getPm() {
        return pm;
    }

    public void setPm(PageModel pm) {
        this.pm = pm;
    }

    public JSGLBS() {

    }

    public JSGLBS(PageModel pm) {
        super();
        this.pm = pm;
    }

    public String getPersonInfo(List<Object[]> list, HBSession sess) {
        String data = "(lt;span style=\"color:rgb(121,0,255);\">��ǰ��Ա��(lt;/span>";
        Object[] info = list.get(0);
        Object a0000 = info[0];
        Object a0101 = info[1];//����
        if (a0101 != null) {
            data = data + a0101.toString() + "��";
        }
        Object a0104 = info[2];//�Ա�
        if (a0104 != null) {
            String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB2261' AND CODE_VALUE = '" + a0104 + "'";
            a0104 = sess.createSQLQuery(s).uniqueResult();
            data = data + a0104.toString() + "��";
        }
        Object a0107 = info[3];//��������
        if (a0107 != null) {
            String reg = "^[0-9]{6}$";
            String reg2 = "^[0-9]{8}$";
            if (a0107.toString().matches(reg) || a0107.toString().matches(reg2)) {
                String msg = getAgeNew(a0107.toString());
                data = data + msg + "��";
            }
        }
        Object a0117 = info[4];//����
        if (a0117 != null) {
            String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB3304' AND CODE_VALUE = '" + a0117 + "'";
            a0117 = sess.createSQLQuery(s).uniqueResult();
            data = data + a0117.toString() + "��";
        }
        Object a0111 = info[5];//����
        if (a0111 != null) {
            data = data + a0111.toString() + "�ˣ�";
        }
        Object a0140 = info[6];//�뵳ʱ��
        if (a0140 != null) {
            String reg = "[0-9]{4}\\.[0-9]{2}";
            Pattern p1 = Pattern.compile(reg);
            Matcher matcher = p1.matcher(a0140.toString());
            if (matcher.find()) {
                String s = matcher.group();
                s = s.replace(".", "��");
                data = data + s + "���뵳��";
            }
        }
        Object a0134 = info[7];//�μӹ���ʱ��
        if (a0134 != null) {
            String reg = "^[0-9]{6}$";
            String reg2 = "^[0-9]{8}$";
            if (a0134.toString().matches(reg) || a0134.toString().matches(reg2)) {
                String year = a0134.toString().substring(0, 4);
                String month = a0134.toString().substring(4, 6);
                data = data + year + "��" + month + "�²μӹ���";
            }

        }

        //���ѧ��
        String zgxlSQL = "select A0801A from A08 where A0834 = '1' and a0000 = '" + a0000 + "'";

        List<Object[]> zgxlS = sess.createSQLQuery(zgxlSQL).list();

        if (zgxlS.size() > 0) {
            Object zgxl = zgxlS.get(0);

            if (zgxl != null) {
                data = data + "��" + zgxl.toString() + "ѧ��";
            }
        }

        //���ѧλ
        String zgxwSQL = "select A0901A from A08 where A0835 = '1' and a0000 = '" + a0000 + "'";
        List<Object[]> zgxwS = sess.createSQLQuery(zgxwSQL).list();
        if (zgxwS.size() > 0) {
            Object zgxw = zgxwS.get(0);

            if (zgxw != null) {
                zgxw = zgxw.toString().replace("ѧλ", "");
                data = data + "��" + zgxw.toString() + "��";
            } else {
                data = data + "��";
            }
        } else {
            data = data + "��";
        }
        //רҵ����ְ��
        Object zyjs = info[8];
        String zyjsStr = "";
        if (zyjs != null && !zyjs.equals("")) {
            zyjsStr = zyjs.toString();
        }
        zyjsStr = zyjsStr.replace(" ", "");
        if (zyjsStr != null && !zyjsStr.equals("")) {
            data = data + zyjsStr.toString() + "��(lt;br/>";
        }
        Object zwOb = info[9];        //������λ��ְ��
        String zw = "";
        if (zwOb != null && !zwOb.equals("")) {
            zw = zwOb.toString();
        }
        zw = zw.replace(" ", "");
        if (zw != null && !zw.equals("")) {
            data = data + "����" + zw.toString() + "��";
        }
        data = data.substring(0, data.length() - 1) + "��";
        return data;
    }
    
    public String getPersonInfo(List<Object[]> list, HBSession sess, String xt) {
        String data = "(lt;span style=\"color:rgb(121,0,255);\">��ǰ��Ա��(lt;/span>";
        Object[] info = list.get(0);
        Object a0000 = info[0];
        Object a0101 = info[1];//����
        if (a0101 != null) {
            data = data + a0101.toString() + "��";
        }
        Object a0104 = info[2];//�Ա�
        if (a0104 != null) {
            String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB2261' AND CODE_VALUE = '" + a0104 + "'";
            a0104 = sess.createSQLQuery(s).uniqueResult();
            data = data + a0104.toString() + "��";
        }
        Object a0107 = info[3];//��������
        if (a0107 != null) {
            String reg = "^[0-9]{6}$";
            String reg2 = "^[0-9]{8}$";
            if (a0107.toString().matches(reg) || a0107.toString().matches(reg2)) {
                String msg = getAgeNew(a0107.toString());
                data = data + msg + "��";
            }
        }
        Object a0117 = info[4];//����
        if (a0117 != null) {
            String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB3304' AND CODE_VALUE = '" + a0117 + "'";
            a0117 = sess.createSQLQuery(s).uniqueResult();
            data = data + a0117.toString() + "��";
        }
        Object a0111 = info[5];//����
        if (a0111 != null) {
            data = data + a0111.toString() + "�ˣ�";
        }
        Object a0140 = info[6];//�뵳ʱ��
        if (a0140 != null) {
            String reg = "[0-9]{4}\\.[0-9]{2}";
            Pattern p1 = Pattern.compile(reg);
            Matcher matcher = p1.matcher(a0140.toString());
            if (matcher.find()) {
                String s = matcher.group();
                s = s.replace(".", "��");
                data = data + s + "���뵳��";
            }
        }
        Object a0134 = info[7];//�μӹ���ʱ��
        if (a0134 != null) {
            String reg = "^[0-9]{6}$";
            String reg2 = "^[0-9]{8}$";
            if (a0134.toString().matches(reg) || a0134.toString().matches(reg2)) {
                String year = a0134.toString().substring(0, 4);
                String month = a0134.toString().substring(4, 6);
                data = data + year + "��" + month + "�²μӹ���";
            }

        }

        //���ѧ��
        String zgxlSQL = "select A0801A from A08 where A0834 = '1' and a0000 = '" + a0000 + "'";
        if(!xt.equals("1")) {
        	zgxlSQL = "select A0801A from v_js_A08 where A0834 = '1' and a0000 = '" + a0000 + "' and v_xt='"+xt+"'";
        }
        List<Object[]> zgxlS = sess.createSQLQuery(zgxlSQL).list();

        if (zgxlS.size() > 0) {
            Object zgxl = zgxlS.get(0);

            if (zgxl != null) {
                data = data + "��" + zgxl.toString() + "ѧ��";
            }
        }

        //���ѧλ
        String zgxwSQL = "select A0901A from A08 where A0835 = '1' and a0000 = '" + a0000 + "'";
        if(!xt.equals("1")) {
        	zgxwSQL = "select A0901A from v_js_A08 where A0835 = '1' and a0000 = '" + a0000 + "' and v_xt='"+xt+"'";
        }
        List<Object[]> zgxwS = sess.createSQLQuery(zgxwSQL).list();
        if (zgxwS.size() > 0) {
            Object zgxw = zgxwS.get(0);

            if (zgxw != null) {
                zgxw = zgxw.toString().replace("ѧλ", "");
                data = data + "��" + zgxw.toString() + "��";
            } else {
                data = data + "��";
            }
        } else {
            data = data + "��";
        }
        //רҵ����ְ��
        Object zyjs = info[8];
        String zyjsStr = "";
        if (zyjs != null && !zyjs.equals("")) {
            zyjsStr = zyjs.toString();
        }
        zyjsStr = zyjsStr.replace(" ", "");
        if (zyjsStr != null && !zyjsStr.equals("")) {
            data = data + zyjsStr.toString() + "��(lt;br/>";
        }
        Object zwOb = info[9];        //������λ��ְ��
        String zw = "";
        if (zwOb != null && !zwOb.equals("")) {
            zw = zwOb.toString();
        }
        zw = zw.replace(" ", "");
        if (zw != null && !zw.equals("")) {
            data = data + "����" + zw.toString() + "��";
        }
        data = data.substring(0, data.length() - 1) + "��";
        return data;
    }

    public static String getAgeNew(String value) {
        int returnAge;

        String birthYear = value.toString().substring(0, 4);
        String birthMonth = value.toString().substring(4, 6);
        String birthDay = "";
        if (value.length() == 6) {
            birthDay = "01";
        }
        if (value.length() == 8) {
            birthDay = value.toString().substring(6, 8);
        }
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String s = sdf.format(d);
        String nowYear = s.toString().substring(0, 4);
        String nowMonth = s.toString().substring(4, 6);
        String nowDay = s.toString().substring(6, 8);
        if (Integer.parseInt(nowYear) == Integer.parseInt(birthYear)) {
            returnAge = 0; // ͬ�귵��0��
        } else {
            int ageDiff = Integer.parseInt(nowYear) - Integer.parseInt(birthYear); // ��ֻ��
            if (ageDiff > 0) {
                if (Integer.parseInt(nowMonth) == Integer.parseInt(birthMonth)) {
                    int dayDiff = Integer.parseInt(nowDay) - Integer.parseInt(birthDay);// ��֮��
                    if (dayDiff < 0) {
                        returnAge = ageDiff - 1;
                    } else {
                        returnAge = ageDiff;
                    }
                } else {
                    int monthDiff = Integer.parseInt(nowMonth) - Integer.parseInt(birthMonth);// ��֮��
                    if (monthDiff < 0) {
                        returnAge = ageDiff - 1;
                    } else {
                        returnAge = ageDiff;
                    }
                }
            } else {
                returnAge = -1;// �������ڴ��� ���ڽ���
            }

        }
        //String msg = value.toString().substring(0, 6) + "(" + returnAge + "��)";
        String msg = "" + returnAge + "��(" + birthYear + "��" + birthMonth + "����)";
        return msg;
    }

    public void saveJs13(String rbId, HBSession sess) throws Exception {
        Js13 js13 = new Js13();
        js13.setJs1300(UUID.randomUUID().toString());
        setJs13Value(rbId, js13);
        sess.save(js13);
    }

    public void updateJs13(String js1300, String rbId, HBSession sess) throws Exception {
        sess.flush();
        Js13 js13 = new Js13();
        js13.setJs1300(js1300);
        setJs13Value(rbId, js13);
        sess.update(js13);
        sess.flush();
    }

    public void saveJs99(String rbId, HBSession sess) throws Exception {
        Js99 js99 = new Js99();
        js99.setJs9900(UUID.randomUUID().toString());
        setJs99Value(rbId, js99);
        sess.save(js99);
    }

    public void updateJs99(String js9900, String rbId, HBSession sess) throws Exception {
        sess.flush();
        Js99 js99 = new Js99();
        js99.setJs9900(js9900);
        setJs99Value(rbId, js99);
        sess.update(js99);
        sess.flush();
    }

    private void setJs99Value(String rbId, Js99 js99) throws RadowException {
        js99.setRbId(rbId);
        js99.setJs9911(pm.getPageElement("t2r1c1").getValue());
        js99.setJs9912(pm.getPageElement("t2r1c2").getValue());
        js99.setJs9913(pm.getPageElement("t2r1c3").getValue());
        js99.setJs9914(pm.getPageElement("t2r1c4").getValue());
        js99.setJs9915(pm.getPageElement("t2r1c5").getValue());
        js99.setJs9916(pm.getPageElement("t2r1c6").getValue());
        js99.setJs9921(pm.getPageElement("t2r2c1").getValue());
        js99.setJs9922(pm.getPageElement("t2r2c2").getValue());
        js99.setJs9923(pm.getPageElement("t2r2c3").getValue());
        js99.setJs9924(pm.getPageElement("t2r2c4").getValue());
        js99.setJs9925(pm.getPageElement("t2r2c5").getValue());
        js99.setJs9926(pm.getPageElement("t2r2c6").getValue());
        js99.setJs9931(pm.getPageElement("t2r3c1").getValue());
        js99.setJs9932(pm.getPageElement("t2r3c2").getValue());
        js99.setJs9933(pm.getPageElement("t2r3c3").getValue());
        js99.setJs9934(pm.getPageElement("t2r3c4").getValue());
        js99.setJs9935(pm.getPageElement("t2r3c5").getValue());
        js99.setJs9936(pm.getPageElement("t2r3c6").getValue());
        js99.setJs9941(pm.getPageElement("t2r4c1").getValue());
        js99.setJs9942(pm.getPageElement("t2r4c2").getValue());
        js99.setJs9943(pm.getPageElement("t2r4c3").getValue());
        js99.setJs9944(pm.getPageElement("t2r4c4").getValue());
        js99.setJs9945(pm.getPageElement("t2r4c5").getValue());
        js99.setJs9946(pm.getPageElement("t2r4c6").getValue());
        js99.setJs9951(pm.getPageElement("t2r5c1").getValue());
        js99.setJs9952(pm.getPageElement("t2r5c2").getValue());
        js99.setJs9953(pm.getPageElement("t2r5c3").getValue());
        js99.setJs9954(pm.getPageElement("t2r5c4").getValue());
        js99.setJs9955(pm.getPageElement("t2r5c5").getValue());
        js99.setJs9956(pm.getPageElement("t2r5c6").getValue());
        js99.setJs9961(pm.getPageElement("t2r6c1").getValue());
        js99.setJs9962(pm.getPageElement("t2r6c2").getValue());
        js99.setJs9963(pm.getPageElement("t2r6c3").getValue());
        js99.setJs9964(pm.getPageElement("t2r6c4").getValue());
        js99.setJs9965(pm.getPageElement("t2r6c5").getValue());
        js99.setJs9966(pm.getPageElement("t2r6c6").getValue());
        js99.setJs9995(pm.getPageElement("t2r1c7").getValue());
        js99.setJs9996(pm.getPageElement("js9996").getValue());
        js99.setJs9997(pm.getPageElement("js9997").getValue());
        js99.setJs9998(pm.getPageElement("js9998").getValue());
    }

    private void setJs13Value(String rbId, Js13 js13) throws RadowException {
        js13.setRbId(rbId);
        js13.setJs1311(pm.getPageElement("r1c1").getValue());
        js13.setJs1312(pm.getPageElement("r1c2").getValue());
        js13.setJs1313(pm.getPageElement("r1c3").getValue());
        js13.setJs1314(pm.getPageElement("r1c4").getValue());
        js13.setJs1315(pm.getPageElement("r1c5").getValue());
        js13.setJs1316(pm.getPageElement("r1c6").getValue());
        js13.setJs1321(pm.getPageElement("r2c1").getValue());
        js13.setJs1322(pm.getPageElement("r2c2").getValue());
        js13.setJs1323(pm.getPageElement("r2c3").getValue());
        js13.setJs1324(pm.getPageElement("r2c4").getValue());
        js13.setJs1325(pm.getPageElement("r2c5").getValue());
        js13.setJs1326(pm.getPageElement("r2c6").getValue());
        js13.setJs1331(pm.getPageElement("r3c1").getValue());
        js13.setJs1332(pm.getPageElement("r3c2").getValue());
        js13.setJs1333(pm.getPageElement("r3c3").getValue());
        js13.setJs1334(pm.getPageElement("r3c4").getValue());
        js13.setJs1335(pm.getPageElement("r3c5").getValue());
        js13.setJs1336(pm.getPageElement("r3c6").getValue());
        js13.setJs1341(pm.getPageElement("r4c1").getValue());
        js13.setJs1342(pm.getPageElement("r4c2").getValue());
        js13.setJs1343(pm.getPageElement("r4c3").getValue());
        js13.setJs1344(pm.getPageElement("r4c4").getValue());
        js13.setJs1345(pm.getPageElement("r4c5").getValue());
        js13.setJs1346(pm.getPageElement("r4c6").getValue());
        js13.setJs1351(pm.getPageElement("r5c1").getValue());
        js13.setJs1352(pm.getPageElement("r5c2").getValue());
        js13.setJs1353(pm.getPageElement("r5c3").getValue());
        js13.setJs1354(pm.getPageElement("r5c4").getValue());
        js13.setJs1355(pm.getPageElement("r5c5").getValue());
        js13.setJs1356(pm.getPageElement("r5c6").getValue());
        js13.setJs1361(pm.getPageElement("r6c1").getValue());
        js13.setJs1362(pm.getPageElement("r6c2").getValue());
        js13.setJs1363(pm.getPageElement("r6c3").getValue());
        js13.setJs1364(pm.getPageElement("r6c4").getValue());
        js13.setJs1365(pm.getPageElement("r6c5").getValue());
        js13.setJs1366(pm.getPageElement("r6c6").getValue());
        js13.setJs1395(pm.getPageElement("r1c7").getValue());
        js13.setJs1396(pm.getPageElement("js1396").getValue());
        js13.setJs1397(pm.getPageElement("js1397").getValue());
        js13.setJs1398(pm.getPageElement("js1398").getValue());
    }

    public void saveJs12(String block, String rbId, HBSession sess) throws Exception {
        List list = sess.createSQLQuery("select js1200 from js12 where rb_id='" + rbId + "'").list();
        int number = Integer.parseInt(block);
        if (list.size() > 0) {
            //��ɾ�ٱ���
            sess.createSQLQuery("delete from js12 where rb_id='" + rbId + "'").executeUpdate();
            for (int i = 1; i <= number; i++) {
                String js1201 = pm.getPageElement("js1201_" + i).getValue();

                // ʲô�߼��� ��Ҫ�������ġ�������������÷�����������ƥ�����ݡ�������
                /*if (js1201 == null || js1201.equals("")) {
                    continue;
                } else {
                    //��ѯ��λ����
                    String sqlb01 = "select b0101 from b01 where b0111 = '" + js1201 + "'";
                    Object uniqueResult = sess.createSQLQuery(sqlb01).uniqueResult();
                    if (uniqueResult != null) {
                        js1201 = uniqueResult.toString();
                    }
                }*/
                Js12 js12 = new Js12();
                String js1200 = UUID.randomUUID().toString();
                String js1202 = pm.getPageElement("js1202_" + i).getValue();
                String js1203 = pm.getPageElement("js1203_" + i).getValue();
                String js1204 = pm.getPageElement("js1204_" + i).getValue();
                String js1205 = pm.getPageElement("js1205_" + i).getValue();
                String js1206 = pm.getPageElement("js1206_" + i).getValue();
                String js1207 = pm.getPageElement("js1207_" + i).getValue();
                String js1208 = pm.getPageElement("js1208_" + i).getValue();
                String js1209 = pm.getPageElement("js1209_" + i).getValue();
                String js1210 = pm.getPageElement("js1210_" + i).getValue();
                String js1211 = pm.getPageElement("js1211_" + i).getValue();
                String js1212 = pm.getPageElement("js1212_" + i).getValue();
                String js1213 = pm.getPageElement("js1213_" + i).getValue();
                js12.setJs1200(js1200);
                js12.setRbId(rbId);
                js12.setJs1201(js1201);
                js12.setJs1202(js1202);
                js12.setJs1203(js1203);
                js12.setJs1204(js1204);
                js12.setJs1205(js1205);
                js12.setJs1206(js1206);
                js12.setJs1207(js1207);
                js12.setJs1208(js1208);
                js12.setJs1209(js1209);
                js12.setJs1210(js1210);
                js12.setJs1211(js1211);
                js12.setJs1212(js1212);
                js12.setJs1213(js1213);
                sess.save(js12);
            }
        } else {
            for (int i = 1; i <= number; i++) {
                String js1201 = pm.getPageElement("js1201_" + i).getValue();

                /*if (js1201 == null || js1201.equals("")) {
                    continue;
                } else {
                    //��ѯ��λ����
                    String sqlb01 = "select b0101 from b01 where b0111 = '" + js1201 + "'";
                    Object uniqueResult = sess.createSQLQuery(sqlb01).uniqueResult();
                    if (uniqueResult != null) {
                        js1201 = uniqueResult.toString();
                    }
                }*/
                Js12 js12 = new Js12();
                String js1200 = UUID.randomUUID().toString();
                String js1202 = pm.getPageElement("js1202_" + i).getValue();
                String js1203 = pm.getPageElement("js1203_" + i).getValue();
                String js1204 = pm.getPageElement("js1204_" + i).getValue();
                String js1205 = pm.getPageElement("js1205_" + i).getValue();
                String js1206 = pm.getPageElement("js1206_" + i).getValue();
                String js1207 = pm.getPageElement("js1207_" + i).getValue();
                String js1208 = pm.getPageElement("js1208_" + i).getValue();
                String js1209 = pm.getPageElement("js1209_" + i).getValue();
                String js1210 = pm.getPageElement("js1210_" + i).getValue();
                String js1211 = pm.getPageElement("js1211_" + i).getValue();
                String js1212 = pm.getPageElement("js1212_" + i).getValue();
                String js1213 = pm.getPageElement("js1213_" + i).getValue();
                js12.setJs1200(js1200);
                js12.setRbId(rbId);
                js12.setJs1201(js1201);
                js12.setJs1202(js1202);
                js12.setJs1203(js1203);
                js12.setJs1204(js1204);
                js12.setJs1205(js1205);
                js12.setJs1206(js1206);
                js12.setJs1207(js1207);
                js12.setJs1208(js1208);
                js12.setJs1209(js1209);
                js12.setJs1210(js1210);
                js12.setJs1211(js1211);
                js12.setJs1212(js1212);
                js12.setJs1213(js1213);
                sess.save(js12);
            }
        }
    }


    public void saveJs11(String js0100, HBSession sess) throws Exception {
        Js11 js11 = (Js11) sess.get(Js11.class, js0100);
        if (js11 == null) {
            js11 = new Js11();
            js11.setJs0100(js0100);
            String rbId = pm.getPageElement("rbId").getValue();//����id
            js11.setRbId(rbId);
        }
        String js1102 = pm.getPageElement("js1102").getValue();//�Ƿ�ѡ�ɲ�
        js11.setJs1102(js1102);

        String js1103 = pm.getPageElement("js1103").getValue();//�Ǻβ�κ󱸸ɲ�
        js11.setJs1103(js1103);

        String js1104 = pm.getPageElement("js1104").getValue();//�з���ǰ��ѵ
        js11.setJs1104(js1104);

        String js1105 = pm.getPageElement("js1105").getValue();//�з��л��㹤������
        js11.setJs1105(js1105);

        String js1106 = pm.getPageElement("js1106").getValue();//������Ҫ����˵�����
        js11.setJs1106(js1106);
        sess.saveOrUpdate(js11);
    }

    public void saveJs10(String js0100, HBSession sess) throws Exception {
        Js10 js10 = (Js10) sess.get(Js10.class, js0100);
        if (js10 == null) {
            js10 = new Js10();
            js10.setJs0100(js0100);
            String rbId = pm.getPageElement("rbId").getValue();//����id
            js10.setRbId(rbId);
        }
        String js1002 = pm.getPageElement("js1002").getValue();//������
        js10.setJs1002(js1002);

        String js1003 = pm.getPageElement("js1003").getValue();//ת�����
        js10.setJs1003(js1003);

        String js1004 = pm.getPageElement("js1004").getValue();//�������
        js10.setJs1004(js1004);
        sess.saveOrUpdate(js10);
    }

    public void saveJs09(String js0100, HBSession sess) throws Exception {
        Js09 js09 = (Js09) sess.get(Js09.class, js0100);
        if (js09 == null) {
            js09 = new Js09();
            js09.setJs0100(js0100);
            String rbId = pm.getPageElement("rbId").getValue();//����id
            js09.setRbId(rbId);
        }
        String js0902 = pm.getPageElement("js0902").getValue();//Ӧ������
        js09.setJs0902(js0902);

        String js0903 = pm.getPageElement("js0903").getValue();//ʵ������
        js09.setJs0903(js0903);

        String js0904 = pm.getPageElement("js0904").getValue();//�޳�Ʊ
        js09.setJs0904(js0904);

        String js0905 = pm.getPageElement("js0905").getValue();//����Ʊ
        js09.setJs0905(js0905);

        String js0906 = pm.getPageElement("js0906").getValue();//��ȨƱ
        js09.setJs0906(js0906);
        sess.saveOrUpdate(js09);

    }

    public void saveJs08(String js0100, HBSession sess) throws Exception {
        Js08 js08 = (Js08) sess.get(Js08.class, js0100);
        if (js08 == null) {
            js08 = new Js08();
            js08.setJs0100(js0100);
            String rbId = pm.getPageElement("rbId").getValue();//����id
            js08.setRbId(rbId);
        }
        String js0802 = pm.getPageElement("js0802").getValue();//��ʾʱ��
        js08.setJs0802(js0802);

        String js0803 = pm.getPageElement("js0803").getValue();//��ʾ���
        js08.setJs0803(js0803);

        String js0804 = pm.getPageElement("js0804").getValue();//��ʾ���
        js08.setJs0804(js0804);
        
        String js0806=pm.getPageElement("gsxs").getValue();//��ʾ��ʽ
        js08.setJs0806(js0806);
        
        String js0805=pm.getPageElement("js0805").getValue();//�÷�
        js08.setJs0805(js0805);
        
        String js0807=pm.getPageElement("gsjywjb").getValue();//��ʾ�ڼ����޾ٱ�
        js08.setJs0807(js0807);
        
        String js0808=pm.getPageElement("chqk").getValue();//������
        js08.setJs0808(js0808);
        
        String js0809 = pm.getPageElement("js0809").getValue();//����ʱ��
        js08.setJs0809(js0809);
        
        sess.saveOrUpdate(js08);
    }

    /*public void saveJs07(String js0100, HBSession sess) throws Exception {
        Js07 js07 = (Js07)sess.get(Js07.class, js0100);
        if(js07==null){
            js07 = new Js07();
            js07.setJs0100(js0100);
        }
        String js0702= pm.getPageElement("js0702").getValue();//��������о�ʱ��
        js07.setJs0702(js0702);

        String js0703= pm.getPageElement("js0703").getValue();//��ί���ר����о����
        js07.setJs0703(js0703);

        String js0704= pm.getPageElement("js0704").getValue();//��ί��ί���о����
        js07.setJs0704(js0704);

        String js0705= pm.getPageElement("js0705").getValue();//�μ�������ȫί�����������
        js07.setJs0705(js0705);

        String js0706= pm.getPageElement("js0706").getValue();//ͬ�⣨ȫί�����������
        js07.setJs0706(js0706);

        String js0707= pm.getPageElement("js0707").getValue();//��ͬ�⣨ȫί�����������
        js07.setJs0707(js0707);

        String js0708= pm.getPageElement("js0708").getValue();//��Ȩ��ȫί�����������
        js07.setJs0708(js0708);

        String js0709= pm.getPageElement("js0709").getValue();//�μ���������ίȫί���������
        js07.setJs0709(js0709);

        String js0710= pm.getPageElement("js0710").getValue();//ͬ�⣨��ίȫί���������
        js07.setJs0710(js0710);

        String js0711= pm.getPageElement("js0711").getValue();//��ͬ�⣨��ίȫί���������
        js07.setJs0711(js0711);

        String js0712= pm.getPageElement("js0712").getValue();//��Ȩ����ίȫί���������
        js07.setJs0712(js0712);
        sess.saveOrUpdate(js07);

    }*/
    public void saveJs15(String js0100, HBSession sess) throws Exception {
        // TODO Auto-generated method stub
        Js15 js15 = (Js15) sess.get(Js15.class, js0100);
        if (js15 == null) {
            js15 = new Js15();
            js15.setJs0100(js0100);
            String rbId = pm.getPageElement("rbId").getValue();//����id
            js15.setRbId(rbId);
        }
        String js1501 = pm.getPageElement("js1501").getValue();//�μ����������ᣩ
        js15.setJs1501(js1501);
        String js1502 = pm.getPageElement("js1502").getValue();//ͬ�⣨���ᣩ
        js15.setJs1502(js1502);
        String js1503 = pm.getPageElement("js1503").getValue();//��ͬ�⣨���ᣩ
        js15.setJs1503(js1503);
        String js1504 = pm.getPageElement("js1504").getValue();//���飨���ᣩ
        js15.setJs1504(js1504);
        String js1505 = pm.getPageElement("js1505").getValue();//�μ���������ί�ᣩ
        js15.setJs1505(js1505);
        String js1506 = pm.getPageElement("js1506").getValue();//ͬ�⣨��ί�ᣩ
        js15.setJs1506(js1506);
        String js1507 = pm.getPageElement("js1507").getValue();//��ͬ�⣨��ί�ᣩ
        js15.setJs1507(js1507);
        String js1508 = pm.getPageElement("js1508").getValue();//���飨��ί�ᣩ
        js15.setJs1508(js1508);
        String js1509 = pm.getPageElement("js1509").getValue();//��ע�����ᣩ
        js15.setJs1509(js1509);
        String js1510 = pm.getPageElement("js1510").getValue();//��ע����ί�ᣩ
        js15.setJs1510(js1510);
        String js1511 = pm.getPageElement("bhtljg").getValue();//ʱ�䣨���ᣩ
        js15.setJs1511(js1511);
        String js1512 = pm.getPageElement("cwtljg").getValue();//ʱ�䣨��ί�ᣩ
        js15.setJs1512(js1512);
        String js1513 = pm.getPageElement("bhbjqk").getValue();//�����������ᣩ
        js15.setJs1513(js1513);
        String js1514 = pm.getPageElement("cwbjqk").getValue();//����������ί�ᣩ
        js15.setJs1514(js1514);
        
        sess.saveOrUpdate(js15);

    }

    public void saveJs06(String js0100, HBSession sess) throws Exception {
        Js06 js06 = (Js06) sess.get(Js06.class, js0100);
        if (js06 == null) {
            js06 = new Js06();
            js06.setJs0100(js0100);
            String rbId = pm.getPageElement("rbId").getValue();//����id
            js06.setRbId(rbId);
        }
        String js0602 = pm.getPageElement("js0602").getValue();//��λ��ί�������
        js06.setJs0602(js0602);

        String js0603 = pm.getPageElement("js0603").getValue();//�ֹ����쵼���
        js06.setJs0603(js0603);

        String js0604 = pm.getPageElement("js0604").getValue();//˫�ع��������
        js06.setJs0604(js0604);

        String js0605 = pm.getPageElement("js0605").getValue();//�ͼ��첿�����
        js06.setJs0605(js0605);
        sess.saveOrUpdate(js06);
    }

	/*public void saveJs05(String js0100, HBSession sess) throws Exception {
		Js05 js05 = (Js05)sess.get(Js05.class, js0100);
		if(js05==null){
			js05 = new Js05();
			js05.setJs0100(js0100);
		}
		String js0502= pm.getPageElement("js0502").getValue();//����ʱ��
		js05.setJs0502(js0502);

		String js0503= pm.getPageElement("js0503").getValue();//����̸������
		js05.setJs0503(js0503);

		String js0504= pm.getPageElement("js0504").getValue();//�Ƿ񿼲�Ԥ��
		js05.setJs0504(js0504);

		String js0505= pm.getPageElement("js0505").getValue();//�����������
		js05.setJs0505(js0505);

		String js0506= pm.getPageElement("js0506").getValue();//����
		js05.setJs0506(js0506);

		String js0507= pm.getPageElement("js0507").getValue();//�Ƚ�����
		js05.setJs0507(js0507);

		String js0508= pm.getPageElement("js0508").getValue();//������
		js05.setJs0508(js0508);

		String js0509= pm.getPageElement("js0509").getValue();//���˽�
		js05.setJs0509(js0509);

		String js0510= pm.getPageElement("js0510").getValue();//�μӲ�������
		js05.setJs0510(js0510);

		String js0511= pm.getPageElement("js0511").getValue();//����
		js05.setJs0511(js0511);

		String js0512= pm.getPageElement("js0512").getValue();//��ְ
		js05.setJs0512(js0512);

		String js0513= pm.getPageElement("js0513").getValue();//������ְ
		js05.setJs0513(js0513);

		String js0514= pm.getPageElement("js0514").getValue();//����ְ
		js05.setJs0514(js0514);

		String js0515= pm.getPageElement("js0515").getValue();//�μ�����
		js05.setJs0515(js0515);

		String js0516= pm.getPageElement("js0516").getValue();//ͬ��
		js05.setJs0516(js0516);

		String js0517= pm.getPageElement("js0517").getValue();//��ͬ��
		js05.setJs0517(js0517);

		String js0518= pm.getPageElement("js0518").getValue();//��Ȩ
		js05.setJs0518(js0518);

		String js0519= pm.getPageElement("js0519").getValue();//�����з�ӳ������
		js05.setJs0519(js0519);

		String js0520= pm.getPageElement("js0520").getValue();//������
		js05.setJs0520(js0520);

		String js0521= pm.getPageElement("js0521").getValue();//���������
		js05.setJs0521(js0521);

		sess.saveOrUpdate(js05);
	}*/

    //��֯�������Ϣ�洢
    public void saveJs14(String js0100, HBSession sess) throws Exception {
        // TODO Auto-generated method stub
        Js14 js14 = (Js14) sess.get(Js14.class, js0100);
        if (js14 == null) {
            js14 = new Js14();
            js14.setJs0100(js0100);
            String rbId = pm.getPageElement("rbId").getValue();//����id
            js14.setRbId(rbId);
        }

        String js1401 = pm.getPageElement("js1401").getValue();//���쵥λ
        js14.setJs1401(js1401);

        String js1402 = pm.getPageElement("js1402").getValue();//����ʱ��
        js14.setJs1402(js1402);

        String js1403 = pm.getPageElement("js1403").getValue();//�������������鷶Χ��Ҫ��
        js14.setJs1403(js1403);

        String js1404 = pm.getPageElement("js1404").getValue();//����̸������Χ
        js14.setJs1404(js1404);

        String js1405 = pm.getPageElement("js1405").getValue();//�������Ա
        js14.setJs1405(js1405);

        String js1406 = pm.getPageElement("js1406").getValue();//��ע
        js14.setJs1406(js1406);
        
        String js1407 = pm.getPageElement("sfjtyj").getValue();
        js14.setJs1407(js1407);
        
        String js1408 = pm.getPageElement("cpycl").getValue();
        js14.setJs1408(js1408);
        
        String js1409 = pm.getPageElement("zqyjqk").getValue();
        js14.setJs1409(js1409);
        
        String js1410 = pm.getPageElement("jjjdyj").getValue();
        js14.setJs1410(js1410);
        
        String js1411 = pm.getPageElement("kcqjjb").getValue();
        js14.setJs1411(js1411);
        
        String js1412 = pm.getPageElement("hcqk").getValue();
        js14.setJs1412(js1412);
        
        String js1413 = pm.getPageElement("jsnkhqk").getValue();
        js14.setJs1413(js1413);
        
        String js1414 = pm.getPageElement("slllshqk").getValue();
        js14.setJs1414(js1414);
        String js1414a = pm.getPageElement("slllshqklx").getValue();
        js14.setJs1414a(js1414a);
        
        String js1415 = pm.getPageElement("sqbgqk").getValue();
        js14.setJs1415(js1415);
        
        String js1416 = pm.getPageElement("grsxchqk").getValue();
        js14.setJs1416(js1416);
        
        String js1417 = pm.getPageElement("js1417").getValue();
        js14.setJs1417(js1417);
        
        String js1418 = pm.getPageElement("js1418").getValue();
        js14.setJs1418(js1418);
        
        String js1419 = pm.getPageElement("js1419").getValue();
        js14.setJs1419(js1419);
        
        String js1420 = pm.getPageElement("js1420").getValue();
        js14.setJs1420(js1420);
        
        String js1421 = pm.getPageElement("js1421").getValue();
        js14.setJs1421(js1421);
        
        String js1422 = pm.getPageElement("js1422").getValue();
        js14.setJs1422(js1422);
        
        String js1423 = pm.getPageElement("js1423").getValue();
        js14.setJs1423(js1423);
        
        String js1424 = pm.getPageElement("js1424").getValue();
        js14.setJs1424(js1424);
        
        String js1425 = pm.getPageElement("js1425").getValue();
        js14.setJs1425(js1425);
        
        String js1426 = pm.getPageElement("js1426").getValue();
        js14.setJs1426(js1426);
        
        String js1427 = pm.getPageElement("js1427").getValue();
        js14.setJs1427(js1427);
        
        String js1428 = pm.getPageElement("js1428").getValue();
        js14.setJs1428(js1428);
        
        String js1429 = pm.getPageElement("js1429").getValue();
        js14.setJs1429(js1429);
        
        String js1430 = pm.getPageElement("js1430").getValue();
        js14.setJs1430(js1430);
        
        String js1431 = pm.getPageElement("js1431").getValue();
        js14.setJs1431(js1431);
        
        String js1432 = pm.getPageElement("js1432").getValue();
        js14.setJs1432(js1432);
        
        String js1433 = pm.getPageElement("js1433").getValue();
        js14.setJs1433(js1433);
        
        String js1434 = pm.getPageElement("js1434").getValue();
        js14.setJs1434(js1434);
        
        String js1435 = pm.getPageElement("js1435").getValue();
        js14.setJs1435(js1435);
        
        String js1436 = pm.getPageElement("js1436").getValue();
        js14.setJs1436(js1436);
        
        String js1437 = pm.getPageElement("js1437").getValue();
        js14.setJs1437(js1437);
        
        String js1438 = pm.getPageElement("js1438").getValue();
        js14.setJs1438(js1438);
        
        String js1439 = pm.getPageElement("js1439").getValue();
        js14.setJs1439(js1439);
        
        String js1440 = pm.getPageElement("js1440").getValue();
        js14.setJs1440(js1440);
        
        String js1441 = pm.getPageElement("js1441").getValue();
        js14.setJs1441(js1441);
        
        String js1442 = pm.getPageElement("js1442").getValue();
        js14.setJs1442(js1442);
        
        String js1443 = pm.getPageElement("js1443").getValue();
        js14.setJs1443(js1443);
        
        String js1444 = pm.getPageElement("js1444").getValue();
        js14.setJs1444(js1444);
        
        String js1445 = pm.getPageElement("js1445").getValue();
        js14.setJs1445(js1445);
        
        String js1446 = pm.getPageElement("js1446").getValue();
        js14.setJs1446(js1446);
        
        String js1447 = pm.getPageElement("js1447").getValue();
        js14.setJs1447(js1447);
        
        String js1448 = pm.getPageElement("js1448").getValue();
        js14.setJs1448(js1448);
        
        String js1449 = pm.getPageElement("js1449").getValue();
        js14.setJs1449(js1449);
        
        //���������js14��������
        sess.saveOrUpdate(js14);

    }

    public void saveJs04(String js0100, HBSession sess) throws Exception {
        Js04 js04 = (Js04) sess.get(Js04.class, js0100);
        if (js04 == null) {
            js04 = new Js04();
            js04.setJs0100(js0100);
            String rbId = pm.getPageElement("rbId").getValue();//����id
            js04.setRbId(rbId);
        }
        String js0402 = pm.getPageElement("js0402").getValue();//�¼���ί�����飩���
        js04.setJs0402(js0402);
        String js0403 = pm.getPageElement("js0403").getValue();//����������
        js04.setJs0403(js0403);

        sess.saveOrUpdate(js04);
    }

    public void saveJs03(String js0100, HBSession sess) throws Exception {
        Js03 js03 = (Js03) sess.get(Js03.class, js0100);
        if (js03 == null) {
            js03 = new Js03();
            js03.setJs0100(js0100);
            String rbId = pm.getPageElement("rbId").getValue();//����id
            js03.setRbId(rbId);
        }
        String js0302 = pm.getPageElement("js0302").getValue();//�Ƽ�ʱ�䣨�����Ƽ���
        js03.setJs0302(js0302);

        String js0303 = pm.getPageElement("js0303").getValue();//�μ������������Ƽ���
        js03.setJs0303(js0303);

        String js0304 = pm.getPageElement("js0304").getValue();//��Ʊ���������Ƽ���
        js03.setJs0304(js0304);

        String js0305 = pm.getPageElement("js0305").getValue();//���Σ������Ƽ���
        js03.setJs0305(js0305);

        String js0306 = pm.getPageElement("js0306").getValue();//��ע�������Ƽ���
        js03.setJs0306(js0306);

        String js0307 = pm.getPageElement("js0307").getValue();//�Ƽ�ʱ�䣨̸���Ƽ���
        js03.setJs0307(js0307);

        String js0308 = pm.getPageElement("js0308").getValue();//�μ�������̸���Ƽ���
        js03.setJs0308(js0308);

        String js0309 = pm.getPageElement("js0309").getValue();//��Ʊ����̸���Ƽ���
        js03.setJs0309(js0309);

        String js0310 = pm.getPageElement("js0310").getValue();//���Σ�̸���Ƽ���
        js03.setJs0310(js0310);

        String js0311 = pm.getPageElement("js0311").getValue();//��ע��̸���Ƽ���
        js03.setJs0311(js0311);
        sess.saveOrUpdate(js03);
    }

    public void saveJs02(String js0100, HBSession sess) throws Exception {
        Js02 js02 = (Js02) sess.get(Js02.class, js0100);
        if (js02 == null) {
            js02 = new Js02();
            js02.setJs0100(js0100);
            PMPropertyCopyUtil.copyElementsValueToObj(js02, pm);
            if("1".equals(js02.getJs0208())){
            	//js02.setJs0211("");
            }else if("2".equals(js02.getJs0208())){
            	js02.setJs0209("");
            	js02.setJs0210("");
            	js02.setJs0211("");
            }
            sess.save(js02);
            sess.flush();
        }else{
            PMPropertyCopyUtil.copyElementsValueToObj(js02, pm);
            if("1".equals(js02.getJs0208())){
            	//js02.setJs0211("");
            }else if("2".equals(js02.getJs0208())){
            	js02.setJs0209("");
            	js02.setJs0210("");
            	js02.setJs0211("");
            }
            sess.update(js02);
            sess.flush();
        }
        
        
       /* String js0202 = pm.getPageElement("js0202").getValue();//��ȱְλ
        js02.setJs0202(js0202);

        String js0203 = pm.getPageElement("js0203").getValue();//��λҪ��
        js02.setJs0203(js0203);

        String js0204 = pm.getPageElement("js0204").getValue();//�䱸����
        js02.setJs0204(js0204);

        String js0205 = pm.getPageElement("js0205").getValue();//�䱸����
        js02.setJs0205(js0205);

        String js0206 = pm.getPageElement("js0206").getValue();//��ע
        js02.setJs0206(js0206);

        String js0208 = pm.getPageElement("js0208").getValue();
        js02.setJs0208(js0208);
        
        String js0209 = pm.getPageElement("js0209").getValue();
        js02.setJs0209(js0209);
        
        String js0210 = pm.getPageElement("js0210").getValue();
        js02.setJs0208(js0210);*/
        
        //String js0207= pm.getPageElement("js0207").getValue();//���Σ�ȫί����ְ��ѡ��ʼ�Ƽ�������
        //js02.setJs0207(js0207);
        
    }

    public void saveJs01(Js01 js01, HBSession sess) throws Exception {
        String js0106 = pm.getPageElement("js0106").getValue();//ѧ��ѧλ
        js01.setJs0106(js0106);

        String js0108 = pm.getPageElement("js0108").getValue();//����ְ��
        js01.setJs0108(js0108);

        String js0109 = pm.getPageElement("js0109").getValue();//����ְʱ��
        js01.setJs0109(js0109);

        String js0111 = pm.getPageElement("js0111").getValue();//����ְ��
        js01.setJs0111(js0111);

        String js0110 = pm.getPageElement("js0110").getValue();//��ͬ��ʱ��
        js01.setJs0110(js0110);
        String js0115 = pm.getPageElement("js0115").getValue();//���ε�λ
        js01.setJs0115(js0115);
        String js0116 = pm.getPageElement("js0116").getValue();//���ε�λ
        js01.setJs0116(js0116);
        String js0117 = pm.getPageElement("js0117").getValue();//����ְ��
        js01.setJs0117(js0117);
        String js0123 = pm.getPageElement("js0123").getValue();//����ְ��A0200s
        js01.setJs0123(js0123);
        String js0111a = pm.getPageElement("js0111a").getValue();//����ְ��A0200s
        js01.setJs0111a(js0111a);
        String js0117a = pm.getPageElement("js0117a").getValue();//����ְ��A0200s
        js01.setJs0117a(js0117a);
        String js0124 = pm.getPageElement("js0124").getValue();//����ְ��A0200s
        js01.setJs0124(js0124);
        //sess.save(js01);
        sess.update(js01);
    }

	public void setPageInfo(Js01 js01, HBSession sess,String js0111,String js0117) throws Exception {
		String xt = js01.getJs0122();
    	String a0000 = js01.getA0000();
        //pm.getPageElement("js0102").setValue(js01.getJs0102());//����
        //pm.getPageElement("js0103").setValue(js01.getJs0103());//��������
        //pm.getPageElement("js0104").setValue(js01.getJs0104());//����ʱ��
        //pm.getPageElement("js0105").setValue(js01.getJs0105());//�뵳ʱ��
        //pm.getPageElement("js0106").setValue(js01.getJs0106());//ѧ��ѧλ
        pm.getPageElement("js0108").setValue(js01.getJs0108());//����ְ��
        pm.getPageElement("js0109").setValue(js01.getJs0109());//����ְʱ��
        pm.getPageElement("js0110").setValue(js01.getJs0110());//��ͬ��ʱ��
        if(js0111==null||"".equals(js0111)) {
        	pm.getPageElement("js0111").setValue(js01.getJs0111());//����ְ��
        }else {
        	pm.getPageElement("js0111").setValue(js0111);//����ְ��
        }
        //pm.getPageElement("js0111").setValue(js0111);//������ְ��
        pm.getPageElement("js0111a").setValue(js01.getJs0111a());//������ְ��
        pm.getPageElement("js0117a").setValue(js01.getJs0117a());//������ְ��
        pm.getPageElement("js0123").setValue(js01.getJs0123());//������ְ��
        pm.getPageElement("js0124").setValue(js01.getJs0124());//������ְ��
        if(StringUtil.isEmpty(js01.getJs0115())){
        	//�ա���ѯͳ�ƹ�ϵ���ڵ�λ
        	String sql = "";
        	if(xt!=null && (xt.equals("2") || xt.equals("3") || xt.equals("4"))) {
        		sql = "select B0101 from v_js_B01 where v_xt='"+xt+"' and B0111=(select A0195 from v_js_A01 where A0000='"+js01.getA0000()+"' and v_xt='"+xt+"')";
        	} else {
        		sql = "select B0101 from B01 where B0111=(select A0195 from A01 where A0000='"+js01.getA0000()+"')";
        	}
        	List list = sess.createSQLQuery(sql).list();
        	if(list.size()>0){
        		String js0115=list.get(0).toString();
        		pm.getPageElement("js0115").setValue(js0115);
        	}
        }else{
        	pm.getPageElement("js0115").setValue(js01.getJs0115());//���ε�λ
        }
        
        pm.getPageElement("js0116").setValue(js01.getJs0116());//���ε�λID
        
        //�������ε�λID��ʾ���ε�λ
        String sql_js0116value = "";
    	if(xt!=null && (xt.equals("2") || xt.equals("3") || xt.equals("4"))) {
    		sql_js0116value = "select B0101 from v_js_B01 where B0111 in ('"+(js01.getJs0116()!=null ?js01.getJs0116().replaceAll(",", "','"):"")+"')  and v_xt='"+xt+"'";
    	} else {
    		sql_js0116value = "select B0101 from B01 where B0111 in ('"+(js01.getJs0116()!=null ?js01.getJs0116().replaceAll(",", "','"):"")+"')";
    	}
        List list = sess.createSQLQuery(sql_js0116value).list();
        if(list.size()>0){
        	String js0116value="";
        	for (Object object : list) {
        		js0116value = js0116value + object+"��";
			}
        	if(js0116value.length()>1) {
        		js0116value = js0116value.substring(0, js0116value.length()-1);
        	}
        	pm.getPageElement("js0116value").setValue(js0116value);//���ε�λ����
        }else{
        	pm.getPageElement("js0116value").setValue("");
        }
        if(js0117==null||"".equals(js0117)) {
        	pm.getPageElement("js0117").setValue(js01.getJs0117());//����ְ��
        }else {
        	pm.getPageElement("js0117").setValue(js0117);//����ְ��
        }        
        //pm.getPageElement("js0114").setValue(js01.getJs0114());//�Ա�
        A01 a01 = null;
        if(xt!=null && (xt.equals("2") || xt.equals("3") || xt.equals("4"))) {
        	List<A01> list_a = sess.createSQLQuery("select * from v_js_A01 v where v_xt='"+xt+"' and a0000='"+a0000+"'").addEntity(A01.class).list();
        	if(list_a.size()>0) {
        		a01 = list_a.get(0);
        	} else {
        		a01 = new A01();
        	}
        } else {
        	a01 = (A01) sess.get(A01.class, js01.getA0000());
            if (a01 == null) {
                a01 = new A01();
            }
        }
        pm.getPageElement("js0102").setValue(a01.getA0101());//����
        pm.getPageElement("js0103").setValue(a01.getA0107());//��������
        pm.getPageElement("js0104").setValue(a01.getA0134());//����ʱ��
        pm.getPageElement("js0105").setValue(a01.getA0140());//�뵳ʱ��
        pm.getPageElement("js0106").setValue((a01.getZgxl() == null ? "" : a01.getZgxl()) + (a01.getZgxw() == null ? "" : a01.getZgxw()));//ѧ��ѧλ
        pm.getPageElement("js0114").setValue(a01.getA0104());//�Ա�
        pm.getPageElement("js0114_combo").setValue(HBUtil.getCodeName("GB2261", a01.getA0104()));//�Ա�

        Js02 js02 = (Js02) sess.get(Js02.class, js01.getJs0100());
        if (js02 == null) {
            js02 = new Js02();
        }
        pm.getPageElement("js0202").setValue(js02.getJs0202());//��ȱְλ
        pm.getPageElement("js0203").setValue(js02.getJs0203());//��λҪ��
        pm.getPageElement("js0204").setValue(js02.getJs0204());//�䱸����
        String Js0205 = js02.getJs0205();
        if (StringUtil.isEmpty(Js0205)) {
            //����䱸�����ֶ�Ϊ�����Զ�ƴ��
            String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB2261' AND CODE_VALUE = '" + a01.getA0104() + "'";
            Object a0104 = sess.createSQLQuery(s).uniqueResult();//�Ա�
            Object a0107 = a01.getA0107();//��������
            String birthYear = "";
            String birthMonth = "";
            if (a0107 != null) {
                String reg = "^[0-9]{6}$";
                String reg2 = "^[0-9]{8}$";
                if (a0107.toString().matches(reg) || a0107.toString().matches(reg2)) {
                    birthYear = a0107.toString().substring(0, 4);
                    birthMonth = a0107.toString().substring(4, 6);
                }
            }
            String a0140 = a01.getA0140();//�뵳ʱ��
            if (a0140 != null) {
                String reg = "[0-9]{4}\\.[0-9]{2}";
                Pattern p1 = Pattern.compile(reg);
                Matcher matcher = p1.matcher(a0140);
                if (matcher.find()) {
                    a0140 = matcher.group();
                    a0140 = a0140.replace(".", "��");
                    a0140 = a0140 + "���뵳";
                }
            }
            Js0205 = a01.getA0101() + " (" + a0104.toString() + " " + a0140 + " " + birthYear + "��" + birthMonth + "���� " + js01.getJs0108() + ")";
            pm.getPageElement("js0205").setValue(Js0205);//�䱸����
        } else {
            pm.getPageElement("js0205").setValue(js02.getJs0205());//�䱸����
        }

        pm.getPageElement("js0206").setValue(js02.getJs0206());//��ע
        
        
        
        List list2 = sess.createSQLQuery("select RB_APPROVE from record_batch where RB_ID='"+js02.getRbId()+"'").list();
        if(list2.size()>0){
        	String approve=list2.get(0).toString();//����1����ʾ�������ζ���Ҫ��ǰ�����ˣ�ÿ�˾Ͳ���Ҫ��
        	
        	pm.getPageElement("js0209").setValue("");
        	pm.getPageElement("js0209_combo").setValue("");
        	pm.getPageElement("js0210").setValue("");
        	pm.getPageElement("js0211").setValue("");
        	pm.getPageElement("js0211_combo").setValue("");
        	if("1".equals(approve)){
        		pm.getExecuteSG().addExecuteCode("this.hidesp();");
        	}else{
        		if(js02.getJs0208()!=null && ("1".equals(js02.getJs0208()) || "2".equals(js02.getJs0208()))){
        			pm.getPageElement("js0208").setValue(js02.getJs0208());
	               	pm.getPageElement("js0208_combo").setValue(js02.getJs0208().equals("1")?"��":"��");
	               	if("1".equals(js02.getJs0208())){
	               		//pm.getExecuteSG().addExecuteCode("hideaproveTr();");
	               		pm.getExecuteSG().addExecuteCode("showaproveTr();");
	               		pm.getExecuteSG().addExecuteCode("showreportTr();");
	               		pm.getPageElement("js0209").setValue(js02.getJs0209());
	               		pm.getPageElement("js0209_combo").setValue(HBUtil.getCodeName("PSYESREASON", js02.getJs0209()));
	               		if("610".equals(js02.getJs0209())){
	               			pm.getExecuteSG().addExecuteCode("showreportvalueTr();");
	               			pm.getPageElement("js0210").setValue(js02.getJs0210());
	               		}else{
	               			pm.getExecuteSG().addExecuteCode("hidereportvalueTr();");
	               		}
	               		pm.getPageElement("js0211").setValue(js02.getJs0211());
	               		pm.getPageElement("js0211_combo").setValue(js02.getJs0211()==null ?"":
	               			js02.getJs0211().equals("1")?"ͨ��":"δͨ��");
	               	}else if("2".equals(js02.getJs0208())){
	               		//pm.getExecuteSG().addExecuteCode("showaproveTr();");
	               		pm.getExecuteSG().addExecuteCode("hideaproveTr();");
	               		pm.getExecuteSG().addExecuteCode("hidereportTr();");
	               		
	               	}
        		}else{
            	   pm.getPageElement("js0208").setValue("2");
            	   pm.getPageElement("js0208_combo").setValue("��");
            	   pm.getExecuteSG().addExecuteCode("hidereportTr();");
        		}
        	}
        }
        
        //pm.getPageElement("js0207").setValue(js02.getJs0207());//���Σ�ȫί����ְ��ѡ��ʼ�Ƽ�������
        Js03 js03 = (Js03) sess.get(Js03.class, js01.getJs0100());
        if (js03 == null) {
            js03 = new Js03();

        }
        //pm.getPageElement("js0302").setValue(js03.getJs0302());//�Ƽ�ʱ�䣨�����Ƽ���
//		pm.getPageElement("js0303").setValue(js03.getJs0303());//�μ������������Ƽ���
//		pm.getPageElement("js0304").setValue(js03.getJs0304());//��Ʊ���������Ƽ���
//		pm.getPageElement("js0305").setValue(js03.getJs0305());//���Σ������Ƽ���
//		pm.getPageElement("js0306").setValue(js03.getJs0306());//��ע�������Ƽ���
//		pm.getPageElement("js0307").setValue(js03.getJs0307());//�Ƽ�ʱ�䣨̸���Ƽ���
//		pm.getPageElement("js0308").setValue(js03.getJs0308());//�μ�������̸���Ƽ���
//		pm.getPageElement("js0309").setValue(js03.getJs0309());//��Ʊ����̸���Ƽ���
//		pm.getPageElement("js0310").setValue(js03.getJs0310());//���Σ�̸���Ƽ���
//		pm.getPageElement("js0311").setValue(js03.getJs0311());//��ע��̸���Ƽ���
        Js04 js04 = (Js04) sess.get(Js04.class, js01.getJs0100());
        if (js04 == null) {
            js04 = new Js04();
        }
        pm.getPageElement("js0402").setValue(js04.getJs0402());//�¼���ί�����飩���
        pm.getPageElement("js0403").setValue(js04.getJs0403());//����������
		
		/*Js05 js05 = (Js05)sess.get(Js05.class, js01.getJs0100());
		if(js05==null){
			js05 = new Js05();

		}
		pm.getPageElement("js0502").setValue(js05.getJs0502());//����ʱ��
		pm.getPageElement("js0503").setValue(js05.getJs0503());//����̸������
		pm.getPageElement("js0504").setValue(js05.getJs0504());//�Ƿ񿼲�Ԥ��
		pm.getPageElement("js0505").setValue(js05.getJs0505());//�����������
		pm.getPageElement("js0506").setValue(js05.getJs0506());//����
		pm.getPageElement("js0507").setValue(js05.getJs0507());//�Ƚ�����
		pm.getPageElement("js0508").setValue(js05.getJs0508());//������
		pm.getPageElement("js0509").setValue(js05.getJs0509());//���˽�
		pm.getPageElement("js0510").setValue(js05.getJs0510());//�μӲ�������
		pm.getPageElement("js0511").setValue(js05.getJs0511());//����
		pm.getPageElement("js0512").setValue(js05.getJs0512());//��ְ
		pm.getPageElement("js0513").setValue(js05.getJs0513());//������ְ
		pm.getPageElement("js0514").setValue(js05.getJs0514());//����ְ
		pm.getPageElement("js0515").setValue(js05.getJs0515());//�μ�����
		pm.getPageElement("js0516").setValue(js05.getJs0516());//ͬ��
		pm.getPageElement("js0517").setValue(js05.getJs0517());//��ͬ��
		pm.getPageElement("js0518").setValue(js05.getJs0518());//��Ȩ
		pm.getPageElement("js0519").setValue(js05.getJs0519());//�����з�ӳ������
		pm.getPageElement("js0520").setValue(js05.getJs0520());//������
		pm.getPageElement("js0521").setValue(js05.getJs0521());//���������   */
        
        
        Js14 js14 = (Js14) sess.get(Js14.class, js01.getJs0100());
        if (js14 == null) {
            js14 = new Js14();
        }
        pm.getPageElement("js1401").setValue(js14.getJs1401());//���쵥λ
        pm.getPageElement("js1402").setValue(js14.getJs1402());//����ʱ��
        pm.getPageElement("js1403").setValue(js14.getJs1403());//�������������鷶Χ��Ҫ��
        pm.getPageElement("js1404").setValue(js14.getJs1404());//����̸������Χ
        pm.getPageElement("js1405").setValue(js14.getJs1405());//�������Ա
        pm.getPageElement("js1406").setValue(js14.getJs1406());//��ע
        pm.getPageElement("sfjtyj").setValue(js14.getJs1407());
        pm.getPageElement("cpycl").setValue(js14.getJs1408());
        pm.getPageElement("zqyjqk").setValue(js14.getJs1409());
        pm.getPageElement("jjjdyj").setValue(js14.getJs1410());
        pm.getPageElement("kcqjjb").setValue(js14.getJs1411());
        pm.getPageElement("hcqk").setValue(js14.getJs1412());
        pm.getPageElement("js1417").setValue(js14.getJs1417());
        pm.getPageElement("js1418").setValue(js14.getJs1418());
        pm.getPageElement("js1419").setValue(js14.getJs1419());
        pm.getPageElement("js1420").setValue(js14.getJs1420());
        pm.getPageElement("js1421").setValue(js14.getJs1421());
        pm.getPageElement("js1422").setValue(js14.getJs1422());
        pm.getPageElement("js1423").setValue(js14.getJs1423());
        pm.getPageElement("js1424").setValue(js14.getJs1424());
        pm.getPageElement("js1425").setValue(js14.getJs1425());
        pm.getPageElement("js1426").setValue(js14.getJs1426());
        pm.getPageElement("js1427").setValue(js14.getJs1427());
        pm.getPageElement("js1428").setValue(js14.getJs1428());
        pm.getPageElement("js1429").setValue(js14.getJs1429());
        pm.getPageElement("js1430").setValue(js14.getJs1430());
        pm.getPageElement("js1431").setValue(js14.getJs1431());
        pm.getPageElement("js1432").setValue(js14.getJs1432());
        pm.getPageElement("js1433").setValue(js14.getJs1433());
        pm.getPageElement("js1434").setValue(js14.getJs1434());
        pm.getPageElement("js1435").setValue(js14.getJs1435());
        pm.getPageElement("js1436").setValue(js14.getJs1436());
        pm.getPageElement("js1437").setValue(js14.getJs1437());
        pm.getPageElement("js1438").setValue(js14.getJs1438());
        pm.getPageElement("js1439").setValue(js14.getJs1439());
        pm.getPageElement("js1440").setValue(js14.getJs1440());
        pm.getPageElement("js1441").setValue(js14.getJs1441());
        pm.getPageElement("js1442").setValue(js14.getJs1442());
        pm.getPageElement("js1443").setValue(js14.getJs1443());
        pm.getPageElement("js1444").setValue(js14.getJs1444());
        pm.getPageElement("js1445").setValue(js14.getJs1445());
        pm.getPageElement("js1446").setValue(js14.getJs1446());
        pm.getPageElement("js1447").setValue(js14.getJs1447());
        pm.getPageElement("js1448").setValue(js14.getJs1448());
        pm.getPageElement("js1449").setValue(js14.getJs1449());
        
        //��ѯ �����꿼��
        /* ��a01ȡֵ��ֱ��ȡ�����ȡ��a01����Ϣ zxf
        List list6 = sess.createSQLQuery("select A15Z101 from A01 where A0000='"+js01.getA0000()+"'").list();
        String jsnkhqk="";
        if(list6.size()>0){
        	jsnkhqk=list6.get(0).toString();
        }*/
        String jsnkhqk= a01.getA15z101();
        pm.getPageElement("jsnkhqk").setValue(jsnkhqk);
        
        pm.getPageElement("slllshqk").setValue(js14.getJs1414());
        String js1414a = js14.getJs1414a()==null || js14.getJs1414a().trim().equals("")
        		? "1" :js14.getJs1414a().trim();
        pm.getPageElement("slllshqklx").setValue(js1414a);
        pm.getExecuteSG().addExecuteCode("slllshcheck('"+js1414a+"')");
        pm.getPageElement("sqbgqk").setValue(js14.getJs1415());
        pm.getPageElement("grsxchqk").setValue(js14.getJs1416());
        
        
        Js06 js06 = (Js06) sess.get(Js06.class, js01.getJs0100());
        if (js06 == null) {
            js06 = new Js06();
        }
        pm.getPageElement("js0602").setValue(js06.getJs0602());//��λ��ί�������
        pm.getPageElement("js0603").setValue(js06.getJs0603());//�ֹ����쵼���
        pm.getPageElement("js0604").setValue(js06.getJs0604());//˫�ع��������
        pm.getPageElement("js0605").setValue(js06.getJs0605());//�ͼ��첿�����
		
		/*Js07 js07 = (Js07)sess.get(Js07.class, js01.getJs0100());
		if(js07==null){
			js07 = new Js07();

		}
		pm.getPageElement("js0702").setValue(js07.getJs0702());//��������о�ʱ��
		pm.getPageElement("js0703").setValue(js07.getJs0703());//��ί���ר����о����
		pm.getPageElement("js0704").setValue(js07.getJs0704());//��ί��ί���о����
		pm.getPageElement("js0705").setValue(js07.getJs0705());//�μ�������ȫί�����������
		pm.getPageElement("js0706").setValue(js07.getJs0706());//ͬ�⣨ȫί�����������
		pm.getPageElement("js0707").setValue(js07.getJs0707());//��ͬ�⣨ȫί�����������
		pm.getPageElement("js0708").setValue(js07.getJs0708());//��Ȩ��ȫί�����������
		pm.getPageElement("js0709").setValue(js07.getJs0709());//�μ���������ίȫί���������
		pm.getPageElement("js0710").setValue(js07.getJs0710());//ͬ�⣨��ίȫί���������
		pm.getPageElement("js0711").setValue(js07.getJs0711());//��ͬ�⣨��ίȫί���������
		pm.getPageElement("js0712").setValue(js07.getJs0712());//��Ȩ����ίȫί���������*/

        //���۾�����
        Js15 js15 = (Js15) sess.get(Js15.class, js01.getJs0100());
        if (js15 == null) {
            js15 = new Js15();
        }
        pm.getPageElement("js1501").setValue(js15.getJs1501());//�μ����������ᣩ
        pm.getPageElement("js1502").setValue(js15.getJs1502());//ͬ�⣨���ᣩ
        pm.getPageElement("js1503").setValue(js15.getJs1503());//��ͬ�⣨���ᣩ
        pm.getPageElement("js1504").setValue(js15.getJs1504());//���飨���ᣩ
        pm.getPageElement("js1509").setValue(js15.getJs1509());//��ע�����ᣩ
        pm.getPageElement("js1505").setValue(js15.getJs1505());//�μ���������ί�ᣩ
        pm.getPageElement("js1506").setValue(js15.getJs1506());//ͬ�⣨��ί�ᣩ
        pm.getPageElement("js1507").setValue(js15.getJs1507());//��ͬ�⣨��ί�ᣩ
        pm.getPageElement("js1508").setValue(js15.getJs1508());//���飨��ί�ᣩ
        pm.getPageElement("js1510").setValue(js15.getJs1510());//��ע����ί�ᣩ
        pm.getPageElement("bhtljg").setValue(js15.getJs1511());//
        pm.getPageElement("cwtljg").setValue(js15.getJs1512());//
        pm.getPageElement("bhbjqk").setValue(js15.getJs1513());//
        pm.getPageElement("cwbjqk").setValue(js15.getJs1514());//
        
        
        Js08 js08 = (Js08) sess.get(Js08.class, js01.getJs0100());
        if (js08 == null) {
            js08 = new Js08();

        }
        pm.getPageElement("js0802").setValue(js08.getJs0802());//��ʾʱ��
        pm.getPageElement("js0809").setValue(js08.getJs0809());//����ʱ��
        pm.getPageElement("js0803").setValue(js08.getJs0803());//��ʾ���
        pm.getPageElement("js0804").setValue(js08.getJs0804());//��ʾ���
        pm.getPageElement("gsxs").setValue(js08.getJs0806());//��ʾ��ʽ
        pm.getPageElement("js0805").setValue(js08.getJs0805());//�÷�
        pm.getPageElement("gsjywjb").setValue(js08.getJs0807());//��ʾ�ڼ����޾ٱ�
        pm.getPageElement("chqk").setValue(js08.getJs0808());//������
        pm.getPageElement("js0810").setValue(js08.getJs0810());//����֪ʶ���Գɼ�
        pm.getPageElement("js0811").setValue(js08.getJs0811());//���µ��Ϳ��Գɼ�
        
        Js09 js09 = (Js09) sess.get(Js09.class, js01.getJs0100());
        if (js09 == null) {
            js09 = new Js09();

        }
        pm.getPageElement("js0902").setValue(js09.getJs0902());//Ӧ������
        pm.getPageElement("js0903").setValue(js09.getJs0903());//ʵ������
        pm.getPageElement("js0904").setValue(js09.getJs0904());//�޳�Ʊ
        pm.getPageElement("js0905").setValue(js09.getJs0905());//����Ʊ
        pm.getPageElement("js0906").setValue(js09.getJs0906());//��ȨƱ
        Js10 js10 = (Js10) sess.get(Js10.class, js01.getJs0100());
        if (js10 == null) {
            js10 = new Js10();

        }
        pm.getPageElement("js1002").setValue(js10.getJs1002());//������
        pm.getPageElement("js1003").setValue(js10.getJs1003());//ת�����
        pm.getPageElement("js1004").setValue(js10.getJs1004());//�������
        Js11 js11 = (Js11) sess.get(Js11.class, js01.getJs0100());
        if (js11 == null) {
            js11 = new Js11();
            js11.setJs1102("1");
            js11.setJs1103("0");
            js11.setJs1104("1");
            js11.setJs1105("1");
        }
        pm.getPageElement("js1102").setValue(js11.getJs1102());//�Ƿ�ѡ�ɲ�
        pm.getPageElement("js1103").setValue(js11.getJs1103());//�Ǻβ�κ󱸸ɲ�
        pm.getPageElement("js1104").setValue(js11.getJs1104());//�з���ǰ��ѵ
        pm.getPageElement("js1105").setValue(js11.getJs1105());//�з��л��㹤������
        pm.getPageElement("js1106").setValue(js11.getJs1106());//������Ҫ����˵�����
        
        
        //��������ȡ���
        String rbid=js01.getRbId();
        String js0100=js01.getJs0100();
        List list3 = sess.createSQLQuery("select js2100 from JS21 where JS0100='"+js0100+"' and RB_ID='"+rbid+"'").list();
        if(list3.size()>0){
        	String js2100=list3.get(0).toString();
        	Js21 js21=(Js21)sess.get(Js21.class, js2100);
        	pm.getPageElement("qtsy").setValue(js21.getJs2109());
        	pm.getPageElement("gzsy").setValue(js21.getJs2108());
        	pm.getExecuteSG().addExecuteCode("tqyjche('name6','"+js21.getJs2106()+"')");
        	pm.getExecuteSG().addExecuteCode("tqyjche('name5','"+js21.getJs2105()+"')");
        	pm.getExecuteSG().addExecuteCode("tqyjche('name4','"+js21.getJs2104()+"')");
        	pm.getExecuteSG().addExecuteCode("tqyjche('name3','"+js21.getJs2103()+"')");
        	pm.getExecuteSG().addExecuteCode("tqyjche('name2','"+js21.getJs2102()+"')");
        	pm.getExecuteSG().addExecuteCode("tqyjche('name1','"+js21.getJs2101()+"')");
        	pm.getExecuteSG().addExecuteCode("grsxche('"+js21.getJs2107()+"')");
        	
        }else{
        	pm.getPageElement("qtsy").setValue("");
        	pm.getPageElement("gzsy").setValue("");
        	pm.getExecuteSG().addExecuteCode("khcheck()");
        }
        
        //�����Ƽ� JS18  19  20
        List<Js18Pojo> list4 = sess.createSQLQuery("select js1802,js1803,js1804,js1805 from js18").setResultTransformer(Transformers.aliasToBean(Js18Pojo.class)).list();
        if(list4.size()>0){
        	Js18Pojo js18pojo=list4.get(0);
        	pm.getPageElement("tjsj").setValue(js18pojo.getJS1802());
        	pm.getPageElement("tjcjrs").setValue(js18pojo.getJS1803());
        	pm.getPageElement("tjother").setValue(js18pojo.getJS1804());
        	pm.getPageElement("tjrs").setValue(js18pojo.getJS1805());
        }else{
        	pm.getPageElement("tjsj").setValue("");
        	pm.getPageElement("tjcjrs").setValue("");
        	pm.getPageElement("tjother").setValue("");
        	pm.getPageElement("tjrs").setValue("");
        }
 /*       List<Js19Pojo> list5 = sess.createSQLQuery("select js1902,js1903,js1904,js1905,js1906,js1907,js1908,js1909,js1910,js1911,js1912 from js19 where js0100='"+js0100+"' and rb_id='"+rbid+"'").setResultTransformer(Transformers.aliasToBean(Js19Pojo.class)).list();
        if(list5.size()>0){
        	Js19Pojo js19Pojo=list5.get(0);
        	pm.getPageElement("hytjp").setValue(js19Pojo.getJS1904());
        	pm.getPageElement("hyyxp").setValue(js19Pojo.getJS1907());
        	pm.getPageElement("thyxp").setValue(js19Pojo.getJS1908());
        	pm.getPageElement("thtjp").setValue(js19Pojo.getJS1905());
        	pm.getPageElement("xbzh").setValue(js19Pojo.getJS1909());
        	pm.getPageElement("zztjqk").setValue(js19Pojo.getJS1910());
        	pm.getPageElement("thtjfw").setValue(js19Pojo.getJS1911());
        	pm.getPageElement("hytjfw").setValue(js19Pojo.getJS1912());
        }else{
        	pm.getPageElement("hytjp").setValue("");
        	pm.getPageElement("hyyxp").setValue("");
        	pm.getPageElement("thyxp").setValue("");
        	pm.getPageElement("thtjp").setValue("");
        	pm.getPageElement("xbzh").setValue("");
        	pm.getPageElement("zztjqk").setValue("");
        	pm.getPageElement("thtjfw").setValue("");
        	pm.getPageElement("hytjfw").setValue("");
        }                  */
        //�µ������Ƽ���ʾ
        List<Js33> list5=sess.createSQLQuery("select * from js33 where js0100='"+js0100+"' and rb_id='"+rbid+"'").addEntity(Js33.class).list();
        if(list5.size()>0){
        	//����ʾ��¼
        	for(int i=0;i<list5.size();i++) {
        		Js33 js33=list5.get(i);
        		int j=i+1;
        		pm.getPageElement("js3302_"+j).setValue(js33.getJs3302());
        		pm.getPageElement("js3312_"+j).setValue(js33.getJs3312());
            	pm.getPageElement("thyxp_"+j).setValue(js33.getJs3304());
            	pm.getPageElement("thtjp_"+j).setValue(js33.getJs3305());
            	pm.getPageElement("thtjfw_"+j).setValue(js33.getJs3306());
            	pm.getPageElement("hyyxp_"+j).setValue(js33.getJs3307());
            	pm.getPageElement("hytjp_"+j).setValue(js33.getJs3308());
            	pm.getPageElement("hytjfw_"+j).setValue(js33.getJs3309());
            	pm.getPageElement("xbzh_"+j).setValue(js33.getJs3310());
            	pm.getPageElement("zztjqk_"+j).setValue(js33.getJs3311());
        		pm.getPageElement("js33row").setValue(j+"");
        		pm.getExecuteSG().addExecuteCode("$('#js33tr_"+j+"').show()");
        	}
        	//�ٰѶ����������ղ�����
        	if(list5.size()<20) {
        		for(int i=list5.size()+1;i<=20;i++) {
        			pm.getPageElement("js3302_"+i).setValue("");
        			pm.getPageElement("js3312_"+i).setValue("");
                	pm.getPageElement("thyxp_"+i).setValue("");
                	pm.getPageElement("thtjp_"+i).setValue("");
                	pm.getPageElement("thtjfw_"+i).setValue("");
                	pm.getPageElement("hyyxp_"+i).setValue("");
                	pm.getPageElement("hytjp_"+i).setValue("");
                	pm.getPageElement("hytjfw_"+i).setValue("");
                	pm.getPageElement("xbzh_"+i).setValue("");
                	pm.getPageElement("zztjqk_"+i).setValue("");
                	pm.getExecuteSG().addExecuteCode("$('#js33tr_"+i+"').hide()");
        		}
        	}
        }else {
        	//ȫ��������ݲ����µ�һ��
        	for(int i=1;i<=20;i++){
        		pm.getPageElement("js3302_"+i).setValue("");
        		pm.getPageElement("js3312_"+i).setValue("");
            	pm.getPageElement("thyxp_"+i).setValue("");
            	pm.getPageElement("thtjp_"+i).setValue("");
            	pm.getPageElement("thtjfw_"+i).setValue("");
            	pm.getPageElement("hyyxp_"+i).setValue("");
            	pm.getPageElement("hytjp_"+i).setValue("");
            	pm.getPageElement("hytjfw_"+i).setValue("");
            	pm.getPageElement("xbzh_"+i).setValue("");
            	pm.getPageElement("zztjqk_"+i).setValue("");
        		if(i!=1) {
        			pm.getExecuteSG().addExecuteCode("$('#js33tr_"+i+"').hide()");
        		}
        		
        	}
        	pm.getPageElement("js33row").setValue("1");
        }
        
        
        
        Js20 js20 = (Js20)sess.get(Js20.class,js0100);
        if(js20!=null){
        	pm.getPageElement("js2002").setValue(js20.getJs2002());
            pm.getPageElement("js2003").setValue(js20.getJs2003());
            pm.getPageElement("js2004").setValue(js20.getJs2004());
            pm.getPageElement("js2005").setValue(js20.getJs2005());
            pm.getPageElement("js2006").setValue(js20.getJs2006());
            pm.getPageElement("js2007").setValue(js20.getJs2007());
        }else{
        	pm.getPageElement("js2002").setValue("");
            pm.getPageElement("js2003").setValue("");
            pm.getPageElement("js2004").setValue("");
            pm.getPageElement("js2005").setValue("");
            pm.getPageElement("js2006").setValue("");
            pm.getPageElement("js2007").setValue("");
        }
        
        //js23
        String rbId=pm.getPageElement("rbId").getValue();
        CommonQueryBS cq=new CommonQueryBS();
        List<HashMap<String, Object>> js23list = cq.getListBySQL("select js0100,a0000,rb_id,js2300,js2301,js2302,js2303,js2302a,js2303a,js2302b,js2303b,js2310 from js23 where js0100='"+js0100+"' and rb_id='"+rbId+"'");
        if(js23list.size()>0){
        	for(int i=1;i<=js23list.size();i++){
        		HashMap<String, Object> map = js23list.get(i-1);
        		String js2300 = objtoString(map.get("js2300"));
        		String js2301 = objtoString(map.get("js2301"));
        		String js2302 = objtoString(map.get("js2302"));
        		String js2303 = objtoString(map.get("js2303"));
        		String js2302a = objtoString(map.get("js2302a"));
        		String js2303a = objtoString(map.get("js2303a"));
        		String js2302b = objtoString(map.get("js2302b"));
        		String js2303b = objtoString(map.get("js2303b"));
        		String js2310 = objtoString(map.get("js2310"));
        		//msgTr_1
        		pm.getPageElement("fwh"+i).setValue(js2301);
        		pm.getPageElement("nrzw"+i).setValue(js2302);
        		pm.getPageElement("nmzw"+i).setValue(js2303);
        		pm.getPageElement("fwsj"+i).setValue(js2310);
        		
        		pm.getPageElement("nrzw"+i+"a").setValue(js2302a);
        		pm.getPageElement("nmzw"+i+"a").setValue(js2303a);
        		pm.getPageElement("nrzw"+i+"b").setValue(js2302b);
        		pm.getPageElement("nmzw"+i+"b").setValue(js2303b);
        	}
        	for(int j=js23list.size()+1;j<16;j++){
        		pm.getPageElement("fwh"+j).setValue("");
        		pm.getPageElement("nrzw"+j).setValue("");
        		pm.getPageElement("nmzw"+j).setValue("");
        		pm.getPageElement("fwsj"+j).setValue("");
        		pm.getPageElement("nrzw"+j+"a").setValue("");
        		pm.getPageElement("nmzw"+j+"a").setValue("");
        		pm.getPageElement("nrzw"+j+"b").setValue("");
        		pm.getPageElement("nmzw"+j+"b").setValue("");
        	}
        	if(js23list.size()<=3){
        		pm.getExecuteSG().addExecuteCode("showMsg('3')");
        	}else{
        		pm.getExecuteSG().addExecuteCode("showMsg('"+js23list.size()+"')");
        	}
        }else{
        	for(int i=1;i<16;i++){
        		pm.getPageElement("fwh"+i).setValue("");
        		pm.getPageElement("nrzw"+i).setValue("");
        		pm.getPageElement("nmzw"+i).setValue("");
        		pm.getPageElement("fwsj"+i).setValue("");
        	}
        	pm.getExecuteSG().addExecuteCode("showMsg('3')");
        }
    }

    public String objtoString(Object str){
    	if(str == null){
    		return "";
    	}else{
    		return str.toString();
    	}
    }
    

    public static void outPrintln(PrintWriter out, String message) {
        out.println(message + "<br/>");
        out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
        out.flush();
    }

    public static void outPrint(PrintWriter out, String message) {
        out.println(message);
        out.flush();
    }

    public static void outPrintlnErr(PrintWriter out, String message) {
        out.println("<FONT  COLOR=\"#FF0066\">" + message + "</FONT><br/>");
        out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
        out.flush();
    }

    public static void outPrintlnSuc(PrintWriter out, String message) {
        out.println("<FONT  COLOR=\"#669900\">" + message + "</FONT>" + "<br/>");
        out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
        out.flush();
    }

    public static void outDownZip(PrintWriter out, String downloadUUID) {
        out.println("<script type=\"text/javascript\">function downloadFile(){"
                + "window.location='PublishFileServlet?method=downloadFile&uuid=" + downloadUUID + "';"
                + "}</script>");
        out.println("<button onclick='downloadFile()'>����������ݰ�</button>");
        out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
        out.flush();
    }

    public static PrintWriter initOutPut(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        response.setHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>wu</title><style type=\"text/css\">\t.{\t\tfont-size: 12px;\t}\t.red{\t\tcolor: red;\t}</style></head><body>");
        out.println("<script type=\"text/javascript\">document.oncontextmenu=rightMouse;function rightMouse() {return false;} </script>");
        out.flush();
        return out;
    }

    public static void endOutPut(PrintWriter out) throws IOException {
        out.println("</body></html>");
    }


    /**
     * ɾ�������ļ�
     *
     * @param fileName Ҫɾ�����ļ����ļ���
     * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // ����ļ�·������Ӧ���ļ����ڣ�������һ���ļ�����ֱ��ɾ��
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                //System.out.println("ɾ�������ļ�" + fileName + "�ɹ���");
                return true;
            } else {
                System.out.println("ɾ�������ļ�" + fileName + "ʧ�ܣ�");
                return false;
            }
        } else {
            System.out.println("ɾ�������ļ�ʧ�ܣ�" + fileName + "�����ڣ�");
            return false;
        }
    }

    /**
     * ɾ��Ŀ¼��Ŀ¼�µ��ļ�
     *
     * @param dir Ҫɾ����Ŀ¼���ļ�·��
     * @return Ŀ¼ɾ���ɹ�����true�����򷵻�false
     */
    public static boolean deleteDirectory(String dir) {
        /*// ���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;*/
        File dirFile = new File(dir);
        // ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("ɾ��Ŀ¼ʧ�ܣ�" + dir + "�����ڣ�");
            return false;
        }
        boolean flag = true;
        // ɾ���ļ����е������ļ�������Ŀ¼
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // ɾ�����ļ�
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // ɾ����Ŀ¼
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("ɾ��Ŀ¼ʧ�ܣ�");
            return false;
        }
        // ɾ����ǰĿ¼
        if (dirFile.delete()) {
            //System.out.println("ɾ��Ŀ¼" + dir + "�ɹ���");
            return true;
        } else {
            return false;
        }
    }

    public void deletePersonInfo(String js0100, HBSession sess) throws Exception {

        Js01 js01 = (Js01) sess.get(Js01.class, js0100);
        if (js01 == null) {
            pm.setMainMessage("��ѡ����Ա��");
            throw new AppException("��ѡ����Ա��");
        }
        sess.createSQLQuery("delete from js_hj where js0100 in(select js0100 from js01 where rb_id=? and js0100=?)").
                setString(0, js01.getRbId()).setString(1, js0100).executeUpdate();

        Js02 js02 = (Js02) sess.get(Js02.class, js0100);
        if (js02 != null) {
            sess.delete(js02);
        }
        Js03 js03 = (Js03) sess.get(Js03.class, js0100);
        if (js03 != null) {
            sess.delete(js03);
        }
        Js04 js04 = (Js04) sess.get(Js04.class, js0100);
        if (js04 != null) {
            sess.delete(js04);
        }
        Js05 js05 = (Js05) sess.get(Js05.class, js0100);
        if (js05 != null) {
            sess.delete(js05);
        }
        Js06 js06 = (Js06) sess.get(Js06.class, js0100);
        if (js06 != null) {
            sess.delete(js06);
        }
        Js07 js07 = (Js07) sess.get(Js07.class, js0100);
        if (js07 != null) {
            sess.delete(js07);
        }
        Js08 js08 = (Js08) sess.get(Js08.class, js0100);
        if (js08 != null) {
            sess.delete(js08);
        }
        Js09 js09 = (Js09) sess.get(Js09.class, js0100);
        if (js09 != null) {
            sess.delete(js09);
        }
        Js10 js10 = (Js10) sess.get(Js10.class, js0100);
        if (js10 != null) {
            sess.delete(js10);
        }
        Js11 js11 = (Js11) sess.get(Js11.class, js0100);
        if (js11 != null) {
            sess.delete(js11);
        }
        Js15 js15 = (Js15) sess.get(Js15.class, js0100);
        if (js15 != null) {
            sess.delete(js15);
        }
//        Js19 js19 = (Js19) sess.get(Js19.class, js0100);
//        if (js19 != null) {
//            sess.delete(js19);
//        }
        
        Js20 js20 = (Js20) sess.get(Js20.class, js0100);
        if (js20 != null) {
            sess.delete(js20);
        }
        
        Js21 js21 = (Js21) sess.get(Js21.class, js0100);
        if (js21 != null) {
            sess.delete(js21);
        }
        
        if (js01 != null) {
            sess.delete(js01);
        }
        
    }

    public void deletePersonInfoByRB_ID(String rb_id, HBSession sess) throws Exception {
        //

        HBUtil.executeUpdate("delete from js02 where js0100 in(select js0100 from js01 where rb_id=?)", new Object[]{rb_id});
        HBUtil.executeUpdate("delete from js03 where js0100 in(select js0100 from js01 where rb_id=?)", new Object[]{rb_id});
        HBUtil.executeUpdate("delete from js04 where js0100 in(select js0100 from js01 where rb_id=?)", new Object[]{rb_id});
        HBUtil.executeUpdate("delete from js05 where js0100 in(select js0100 from js01 where rb_id=?)", new Object[]{rb_id});
        HBUtil.executeUpdate("delete from js06 where js0100 in(select js0100 from js01 where rb_id=?)", new Object[]{rb_id});
        HBUtil.executeUpdate("delete from js07 where js0100 in(select js0100 from js01 where rb_id=?)", new Object[]{rb_id});
        HBUtil.executeUpdate("delete from js08 where js0100 in(select js0100 from js01 where rb_id=?)", new Object[]{rb_id});
        HBUtil.executeUpdate("delete from js09 where js0100 in(select js0100 from js01 where rb_id=?)", new Object[]{rb_id});
        HBUtil.executeUpdate("delete from js10 where js0100 in(select js0100 from js01 where rb_id=?)", new Object[]{rb_id});
        HBUtil.executeUpdate("delete from js11 where js0100 in(select js0100 from js01 where rb_id=?)", new Object[]{rb_id});
        HBUtil.executeUpdate("delete from js_hj where js0100 in(select js0100 from js01 where rb_id=?)", new Object[]{rb_id});

        HBUtil.executeUpdate("delete from deploy_classify where rb_id=? and dc005!='3'", new Object[]{rb_id});
        HBUtil.executeUpdate("delete from js_att where js0100 in(select js0100 from js01 where rb_id=?)", new Object[]{rb_id});

        HBUtil.executeUpdate("delete from js01 where rb_id=?", new Object[]{rb_id});

        HBUtil.executeUpdate("delete from record_batch where rb_id=?", new Object[]{rb_id});
    }

    /**
     * �������¸������µ���Ա������Ϣ��������Ϣ��
     *
     * @param rbId
     * @param sess
     */
    public void updateNRM(String rbId, HBSession sess) {
        RecordBatch rb = (RecordBatch) HBUtil.getHBSession().get(RecordBatch.class, rbId);
        String userid = SysManagerUtils.getUserId();
        String username = SysManagerUtils.getUserName();
        String date = DateUtil.getcurdate();
        BigDecimal RB_SYSNO = rb.getRbSysno();
        //��ɾ����������Ϣ  ����ǰ�û������ι����������εġ�
        String deletesql = "delete from a53 where ((a5399=? and rb_id=?) or (a5399=? and rb_id is null)) "
                + " and a0000 in (select a0000 from js01 where rb_id=?)";
        sess.createSQLQuery(deletesql).setString(0, userid).setString(1, rbId)
                .setString(2, userid).setString(3, rbId).executeUpdate();
        String insertsql = "insert into a53(a0000,a5300,a5304,a5315,a5321,a5323,a5327,a5399,rb_id,RB_SYSNO) "
                + " (select a0000,sys_guid(),js0111,js0117,'" + date + "','" + date + "',"
                + " '" + username + "','" + userid + "','" + rbId + "'," + RB_SYSNO + " from js01 where rb_id=?)";
        sess.createSQLQuery(insertsql).setString(0, rbId).executeUpdate();
        rb.setRbUpdated("1");
        sess.update(rb);
    }

    /**
     * ������������Ϣ��
     *
     * @param js01
     * @param sess
     */
    public void saveA053(Js01 js01, HBSession sess) {
        RecordBatch rb = (RecordBatch) sess.get(RecordBatch.class, js01.getRbId());
        if (rb != null && "1".equals(rb.getRbUpdated())) {
            String sqlA53 = "from A53 where a0000='" + js01.getA0000() + "' and a5399='" + SysManagerUtils.getUserId() + "' "
                    + " and rb_id='" + js01.getRbId() + "'";
            List<A53> listA53 = sess.createQuery(sqlA53).list();
            if (listA53.size() > 0) {
                A53 a53 = listA53.get(0);
                a53.setA5304(js01.getJs0111());
                a53.setA5315(js01.getJs0117());
                sess.save(a53);
            }
        }

    }

    


}
