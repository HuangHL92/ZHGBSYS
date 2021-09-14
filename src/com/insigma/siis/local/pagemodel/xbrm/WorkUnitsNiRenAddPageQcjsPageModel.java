package com.insigma.siis.local.pagemodel.xbrm;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.*;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.CommonQueryBS;
import com.utils.DBUtils;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 工作单位及职务新增修改页面
 *
 * @author Administrator
 */
public class WorkUnitsNiRenAddPageQcjsPageModel extends PageModel {
    private LogUtil applog = new LogUtil();

    @Override
    public int doInit() throws RadowException {
        this.setNextEventName("initX");
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("initX")
    @NoRequiredValidate
    public int initX() throws RadowException, AppException {
    	String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
    	String prope=param[1];
    	if(!"top".equals(prope)){
    		this.getPageElement("subproperty").setValue(prope);
    	}else{
    		this.getPageElement("subproperty").setValue("");
    	}
        //工作单位及职务列表
        this.setNextEventName("WorkUnitsGrid.dogridquery");
        return EventRtnType.NORMAL_SUCCESS;
    }

    @SuppressWarnings("static-access")
    @PageEvent("save")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int saveWorkUnits() throws RadowException, AppException {

        //get all row
        List<HashMap<String, Object>> hashMapList = this.getPageElement("WorkUnitsGrid").getValueList();

        //primary key
        StringBuffer a0200 = new StringBuffer();

        StringBuffer showWord = new StringBuffer();

        //append value for selected list
        for (int i = 0; i < hashMapList.size(); i++) {
            if ("true".equals(hashMapList.get(i).get("demo").toString())) {
                a0200.append(hashMapList.get(i).get("a0200").toString());
                a0200.append(",");
                showWord.append(hashMapList.get(i).get("a0201a").toString());
                showWord.append(hashMapList.get(i).get("a0215a").toString());
                showWord.append(",");
            }
        }

        if (StringUtils.isEmpty(a0200.toString()) || StringUtils.isEmpty(showWord.toString())) {
            super.setMainMessage("至少选择一个选项");
            return EventRtnType.NORMAL_SUCCESS;
        }
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("a0200", a0200.toString().substring(0, a0200.toString().length() - 1));
        map.put("showWord", showWord.toString().substring(0, showWord.toString().length() - 1));

        super.getExecuteSG().addExecuteCode("setValueToParent(json);".replace("json", JSONObject.fromObject(map).toString()));
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("savecheck")
	public int savecheck() throws RadowException{
    	String a0200s = this.getPageElement("a0200s").getValue();
        HBSession sess = HBUtil.getHBSession();
        String qc = "";
        String jc = "";
    	try {
    		 String sql = "from A02 where a0000 in ('" + (a0200s.replace(",", "','")) + "') order by a0223";//-1 其它单位and a0201b!='-1'
    		 List<A02> list = sess.createQuery(sql).list();
    		 for (A02 a02 : list) {
    			 String zwmc = a02.getA0215a() == null ? "" : a02.getA0215a();//职务名称
                 String jgbm = a02.getA0201b();//机构编码
                 B01 b = (B01) sess.get(B01.class, jgbm);
                 String dwqc = "";
                 String dwjc = "";
                 if(b.getB0194().equals("1")) {
                	 dwqc = b.getB0101();
                	 dwjc = b.getB0104();
                 } else {
                	 dwqc = b.getB0101();
                	 dwjc = b.getB0104();
                	 while (true) {
                		 B01 b01 = (B01) sess.get(B01.class, b.getB0121());
                		 if(b01!=null) {
                			 if(b01.getB0194().equals("1")) {
                				 dwqc = b.getB0101() + dwqc;
                				 dwjc = b.getB0104() + dwjc;
                				 break;
                			 }
                		 } else {
                			 break;
                		 }
					}
                 }
                 qc = qc + dwqc +zwmc+"，";
                 jc = jc + dwjc +zwmc+"，";
    		 }
    		 
    		 Map<String, String> map = new HashMap<String, String>();
             map.put("a0200", a0200s.toString().substring(0, a0200s.toString().length() - 1));
             map.put("showWord", jc);
             map.put("showWord2", qc);

             super.getExecuteSG().addExecuteCode("setValueToParent(json);".replace("json", JSONObject.fromObject(map).toString()));
             
    	} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败!");
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    /**
	 * 更新名称
	 * @return
	 * @throws RadowException
     * @throws AppException 
	 */
	@PageEvent("saveCheck2.onclick")
	@Transaction
	@NoRequiredValidate
	public int saveCheck2(String id) throws RadowException, AppException {
        String a0200s = this.getPageElement("a0200s").getValue();
        String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
    	String js0100=param[3];
    	HBSession sess = HBUtil.getHBSession();
    	
    	Js01 js01 = (Js01) sess.get(Js01.class, js0100);
    	String v_xt = js01.getJs0122();
    	String wheresql = "";
		String tablesql = "";
    	if(v_xt!=null && (v_xt.equals("2") || v_xt.equals("3")|| v_xt.equals("4"))) {
			wheresql = " and v_xt='"+v_xt+"' ";
			tablesql = " v_js_";
		}
        String sql = "select * from "+tablesql+"A02 where a0200 in ('" + (a0200s.replace(",", "','")) + "') "+wheresql+" order by a0223";//-1 其它单位and a0201b!='-1'
        List<A02> list = sess.createSQLQuery(sql).addEntity(A02.class).list();
        if (list != null && list.size() > 0) {
            Map<String, String> desc_full = new LinkedHashMap<String, String>();//描述 全称
            Map<String, String> desc_short = new LinkedHashMap<String, String>();//描述 简称

            String zrqm = "";//全名 在任
            String ymqm = "";//以免
            String zrjc = "";//简称
            String ymjc = "";
            for (A02 a02 : list) {
                String a0255 = a02.getA0255();//任职状态
                String jgbm = a02.getA0201b();//机构编码
                List<String> jgmcList = new ArrayList<String>() {
                    @Override
                    public String toString() {
                        StringBuffer sb = new StringBuffer("");
                        for (int i = this.size() - 1; i >= 0; i--) {
                            sb.append(this.get(i));
                        }
                        return sb.toString();
                    }
                };//机构名称 全名
                jgmcList.add(a02.getA0201a() == null ? "" : a02.getA0201a());


                List<String> jgmc_shortList = new ArrayList<String>() {
                    @Override
                    public String toString() {
                        StringBuffer sb = new StringBuffer("");
                        for (int i = this.size() - 1; i >= 0; i--) {
                            sb.append(this.get(i));
                        }
                        return sb.toString();
                    }
                };
                jgmc_shortList.add(a02.getA0201c() == null ? "" : a02.getA0201c());//机构名称 简称
                String zwmc = a02.getA0215a() == null ? "" : a02.getA0215a();//职务名称
                B01 b01 = null;
                if (jgbm != null && !"".equals(jgbm)) {//导入的数据有些为空。 机构编码不为空。
                    if(v_xt!=null && (v_xt.equals("2") || v_xt.equals("3")|| v_xt.equals("4"))) {
                    	List<B01> listb01 = sess.createSQLQuery("select * from v_js_b01 b01 where v_xt='"+v_xt+"'"
                    			+ " and b0111='"+jgbm+"'").addEntity(B01.class).list();
                    	b01 = listb01.get(0);
            		} else {
            			b01 = (B01) sess.get(B01.class, jgbm);
            		}
                }
                if (b01 != null) {
                    String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
                    if ("2".equals(b0194)) {//2—内设机构
                        while (true) {
                           
                            if(v_xt!=null && (v_xt.equals("2") || v_xt.equals("3")|| v_xt.equals("4"))) {
                            	List<B01> listb01 = sess.createSQLQuery("select * from v_js_b01 b01 where v_xt='"+v_xt+"'"
                            			+ " and b0111='"+b01.getB0121()+"'").addEntity(B01.class).list();
                            	b01 = listb01.get(0);
                    		} else {
                    			 b01 = (B01) sess.get(B01.class, b01.getB0121());
                    		}
                            if (b01 == null) {
                                break;
                            } else {
                                b0194 = b01.getB0194();
                                if ("2".equals(b0194)) {//2—内设机构
                                    //jgmc = b01.getB0101()+jgmc;
                                    jgmcList.add(b01.getB0101());
                                    jgmc_shortList.add(b01.getB0104());
                                } else if ("3".equals(b0194)) {//3—机构分组
                                    continue;
                                } else if ("1".equals(b0194)) {//1—法人单位
                                    //jgmc = b01.getB0101()+jgmc;
                                    //jgmc_short = b01.getB0104()+jgmc_short;
                                    //全称
                                    String key_full = b01.getB0111() + "_$_" + b01.getB0101() + "_$_" + a0255;
                                    String value_full = desc_full.get(key_full);
                                    if (value_full == null) {
                                        desc_full.put(key_full, jgmcList.toString() + zwmc);
                                    } else {//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示
                                        List<String> romvelist = new ArrayList<String>();
                                        for (int i = jgmcList.size() - 1; i >= 0; i--) {
                                            if (value_full.indexOf(jgmcList.get(i)) >= 0) {
                                                romvelist.add(jgmcList.get(i));
                                            }
                                        }
                                        jgmcList.removeAll(romvelist);

                                        desc_full.put(key_full, value_full + "、" + jgmcList.toString() + zwmc);


                                    }
                                    //简称
                                    String key_short = b01.getB0111() + "_$_" + b01.getB0104() + "_$_" + a0255;
                                    String value_short = desc_short.get(key_short);
                                    if (value_short == null) {
                                        desc_short.put(key_short, jgmc_shortList.toString() + zwmc);
                                    } else {
                                        List<String> romvelist = new ArrayList<String>();
                                        for (int i = jgmc_shortList.size() - 1; i >= 0; i--) {
                                            if (value_short.indexOf(jgmc_shortList.get(i)) >= 0) {
                                                romvelist.add(jgmc_shortList.get(i));
                                            }
                                        }
                                        jgmc_shortList.removeAll(romvelist);
                                        desc_short.put(key_short, value_short + "、" + jgmc_shortList.toString() + zwmc);
                                    }
                                    break;
                                } else {
                                    break;
                                }
                            }
                        }
                    } else if ("1".equals(b0194)) {//1—法人单位； 第一次就是法人单位，不往上递归
                        String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
                        String value_full = desc_full.get(key_full);
                        if (value_full == null) {
                            desc_full.put(key_full, zwmc);//key 编码_$_机构名称_$_是否已免
                        } else {
                            desc_full.put(key_full, value_full + "、" + zwmc);
                        }

                        //简称
                        String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + a0255;
                        String value_short = desc_short.get(key_short);
                        if (value_short == null) {
                            desc_short.put(key_short, zwmc);
                        } else {
                            desc_short.put(key_short, value_short + "、" + zwmc);
                        }
                    }

                }

            }

            for (String key : desc_full.keySet()) {//全名
                String[] parm = key.split("_\\$_");
                String jgzw = parm[1] + desc_full.get(key);
                if ("1".equals(parm[2])) {//在任
                    //任职机构 职务名称
                    if (!"".equals(jgzw)) {
                        zrqm += jgzw + "，";
                    }
                } else {//以免

                    if (!"".equals(jgzw)) {
                        ymqm += jgzw + "，";
                    }
                }
            }


            for (String key : desc_short.keySet()) {//简称
                String[] parm = key.split("_\\$_");
                String jgzw = parm[1] + desc_short.get(key);
                if ("1".equals(parm[2])) {//在任
                    //任职机构 职务名称
                    if (!"".equals(jgzw)) {
                        zrjc += jgzw + "，";
                    }
                } else {//以免
                    if (!"".equals(jgzw)) {
                        ymjc += jgzw + "，";
                    }
                }
            }


            if (!"".equals(zrqm)) {
                zrqm = zrqm.substring(0, zrqm.length() - 1);
            }
            if (!"".equals(ymqm)) {
                ymqm = ymqm.substring(0, ymqm.length() - 1);
                ymqm = "(原" + ymqm + ")";
            }
            if (!"".equals(zrjc)) {
                zrjc = zrjc.substring(0, zrjc.length() - 1);
            }
            if (!"".equals(ymjc)) {
                ymjc = ymjc.substring(0, ymjc.length() - 1);
                ymjc = "(原" + ymjc + ")";
            }
           /* A01 a01 = (A01) sess.get(A01.class, a0000);
            a01.setA0192a(zrqm + ymqm);
            a01.setA0192(zrjc + ymjc);
            sess.update(a01);*/
            //人员基本信息界面
            Map<String, String> map = new HashMap<String, String>();
            map.put("a0200", a0200s.toString().substring(0, a0200s.toString().length() - 1));
            map.put("showWord", zrjc);
            map.put("showWord2", zrqm);

            super.getExecuteSG().addExecuteCode("setValueToParent(json);".replace("json", JSONObject.fromObject(map).toString()));
            
        } else {
        	 Map<String, String> map = new HashMap<String, String>();
             map.put("a0200", "");
             map.put("showWord", "");
             map.put("showWord2", "");
        	super.getExecuteSG().addExecuteCode("setValueToParent(json);".replace("json", JSONObject.fromObject(map).toString()));
            
        }

        return EventRtnType.NORMAL_SUCCESS;
    }
    
    private void updateA01(A01 a01, HBSession sess) {
        //更新a01 职务层次。 a0148 a0149     a02 a0221
        String sql = "select a0221 from A02 a where a0000='" + a01.getA0000() + "' and a0255='1'";
        List<String> list = sess.createQuery(sql).list();
        if (list != null && list.size() > 0) {
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    if (o1 == null || "".equals(o1)) {
                        return 1;
                    }
                    if (o2 == null || "".equals(o2)) {
                        return -1;
                    }
                    return o1.compareTo(o2);
                }
            });

            a01.setA0148(list.get(0));
            //人员基本信息界面
            this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0148').value='" + (a01.getA0148() == null ? "" : a01.getA0148()) + "';");
        } else {
            //职务为空
            a01.setA0148("");
            this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0148').value='';");
        }

    }


    public String reverse(String s) {
        char[] array = s.toCharArray();
        String reverse = "";
        for (int i = array.length - 1; i >= 0; i--)
            reverse += array[i];

        return reverse;
    }
/** *********************************************工作单位及职务(a02)******************************************************************** */

    /**
     * 工作单位及职务列表
     *
     * @param start
     * @param limit
     * @return
     * @throws RadowException
     * @throws AppException 
     */
    @PageEvent("WorkUnitsGrid.dogridquery")
    @NoRequiredValidate
    public int workUnitsGridQuery(int start, int limit) throws RadowException, AppException {
    	String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
    	String js0100=param[3];
    	Js01 js01 = (Js01) HBUtil.getHBSession().get(Js01.class, js0100);
        //String sql = "select * from a02 where a0000='" + a0000 + "' order by lpad(a0223,5,'" + 0 + "') ";
    	String id=param[1];
    	String rbId=param[2];
    	String sql = "select * from a02 where a0255='1' and a0000='" + a0000 + "' order by lpad(a0223,5,'" + 0 + "') ";
    	String a0200ssql="select js0123 from js01 where js01.a0000='"+a0000+"' and js01.rb_id='"+rbId+"'";
    	CommonQueryBS cq=new CommonQueryBS();
    	List<HashMap<String, Object>> list = cq.getListBySQL(a0200ssql);
    	String js0123s="";
    	if(list.size()>0){
    		HashMap<String, Object> map = list.get(0);
    		String js0123 = inullback(map.get("js0123"));
    		js0123s=js0123.replaceAll(",", "','");
    	}
    	if(!"top".equals(id)){
    		sql = "select * from a02 where a0255='1' and a0000='" + a0000 + "' and a0200 in('"+js0123s+"') order by lpad(a0223,5,'" + 0 + "') ";
    		if(js01.getJs0122()!=null && (js01.getJs0122().equals("2")
            		|| js01.getJs0122().equals("3") || js01.getJs0122().equals("4"))) {
            	sql = "select * from v_js_a02 where a0255='1' and a0000='" + a0000 + "' and a0200 in('"+js0123s+"') and v_xt='"+js01.getJs0122()+"' order by lpad(a0223,5,'" + 0 + "') ";
    		}
    	} else {
    		if(js01.getJs0122()!=null && (js01.getJs0122().equals("2")
            		|| js01.getJs0122().equals("3") || js01.getJs0122().equals("4"))) {
            	sql = "select * from v_js_a02 where a0255='1' and a0000='" + a0000 + "' and v_xt='"+js01.getJs0122()+"' order by lpad(a0223,5,'" + 0 + "') ";
    		}
    	}
        
        this.pageQuery(sql, "SQL", start, limit); //处理分页查询
        return EventRtnType.SPE_SUCCESS;
    }
    
    //如果未null返回空字符串
    public String inullback(Object obj) {
        return obj == null ? "" : obj.toString();
    }
    
    /**
     * 工作单位及职务新增按钮
     *
     * @param
     * @param
     * @return
     * @throws RadowException
     */
    @PageEvent("WorkUnitsAddBtn.onclick")
    @NoRequiredValidate
    public int workUnitsWin(String id) throws RadowException {
    	String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
        if (a0000 == null || "".equals(a0000)) {//
            this.setMainMessage("请先保存人员基本信息！");
            return EventRtnType.NORMAL_SUCCESS;
        }
        A02 a02 = new A02();
        this.getPageElement("a0201b_combo").setValue("");

        PMPropertyCopyUtil.copyObjValueToElement(a02, this);
        this.getExecuteSG().addExecuteCode("$('#a0201d').attr('disabled','');document.getElementById('a0201d').checked=false;document.getElementById('a0251b').checked=false;"
                + "document.getElementById('a0219').checked=false;document.getElementById('a0255').value='1';document.getElementById('a0279').checked=false;"
                + "document.getElementById('a02551').checked=true;setA0201eDisabled();a0255SelChange()");
        return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * 输出复选框选中事件
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("workUnitsgridchecked")
    @Transaction
    @NoRequiredValidate
    public int workUnitsgridchecked(String listString) throws RadowException {
        Map map = this.getRequestParamer();

        String[] listF = listString.split(",");

        String a0200 = listF[0];
        String a0281 = listF[1];

        try {
            if (a0200 != null) {
                HBSession sess = HBUtil.getHBSession();
                A02 a02 = (A02) sess.get(A02.class, a0200);

                a02.setA0281(a0281);
//                sess.save(a02);
                PMPropertyCopyUtil.copyObjValueToElement(a02, this);
                this.getPageElement("a0201b_combo").setValue(a02.getA0201a());//机构名称 中文。

                String params=this.getPageElement("subWinIdBussessId").getValue();
            	String[] param = params.split(",");
            	String a0000=param[0];
                String sqlOut = "from A02 where a0000='" + a0000 + "' and a0281='true'";   //输出职务
                List<A02> list = sess.createQuery(sqlOut).list();


            }
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("保存失败！");
            return EventRtnType.FAILD;
        }

        return EventRtnType.NORMAL_SUCCESS;
    }


    /**
     * 更新名称
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("UpdateTitleBtn.onclick")
    @Transaction
    @NoRequiredValidate
    public int UpdateTitleBtn(String id) throws RadowException {
        boolean isEvent = false;

        String a0000 = null;
        try {
        	String params=this.getPageElement("subWinIdBussessId").getValue();
        	String[] param = params.split(",");
        	a0000=param[0];
        } catch (RuntimeException e) {
            //e.printStackTrace();
        }
        if (a0000 == null || "".equals(a0000)) {
            a0000 = id;
        } else {
            isEvent = true;
        }
        HBSession sess = HBUtil.getHBSession();
        String sql = "from A02 where a0000='" + a0000 + "' and a0281='true' order by a0223";//-1 其它单位and a0201b!='-1'
        List<A02> list = sess.createQuery(sql).list();
        if (list != null && list.size() > 0) {
            Map<String, String> desc_full = new LinkedHashMap<String, String>();//描述 全称
            Map<String, String> desc_short = new LinkedHashMap<String, String>();//描述 简称

            String zrqm = "";//全名 在任
            String ymqm = "";//以免
            String zrjc = "";//简称
            String ymjc = "";
            for (A02 a02 : list) {
                String a0255 = a02.getA0255();//任职状态
                String jgbm = a02.getA0201b();//机构编码
                List<String> jgmcList = new ArrayList<String>() {
                    @Override
                    public String toString() {
                        StringBuffer sb = new StringBuffer("");
                        for (int i = this.size() - 1; i >= 0; i--) {
                            sb.append(this.get(i));
                        }
                        return sb.toString();
                    }
                };//机构名称 全名
                jgmcList.add(a02.getA0201a() == null ? "" : a02.getA0201a());


                List<String> jgmc_shortList = new ArrayList<String>() {
                    @Override
                    public String toString() {
                        StringBuffer sb = new StringBuffer("");
                        for (int i = this.size() - 1; i >= 0; i--) {
                            sb.append(this.get(i));
                        }
                        return sb.toString();
                    }
                };
                jgmc_shortList.add(a02.getA0201c() == null ? "" : a02.getA0201c());//机构名称 简称
                String zwmc = a02.getA0215a() == null ? "" : a02.getA0215a();//职务名称
                B01 b01 = null;
                if (jgbm != null && !"".equals(jgbm)) {//导入的数据有些为空。 机构编码不为空。
                    b01 = (B01) sess.get(B01.class, jgbm);
                }
                if (b01 != null) {
                    String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
                    if ("2".equals(b0194)) {//2—内设机构
                        while (true) {
                            b01 = (B01) sess.get(B01.class, b01.getB0121());
                            if (b01 == null) {
                                break;
                            } else {
                                b0194 = b01.getB0194();
                                if ("2".equals(b0194)) {//2—内设机构
                                    //jgmc = b01.getB0101()+jgmc;
                                    jgmcList.add(b01.getB0101());
                                    jgmc_shortList.add(b01.getB0104());
                                } else if ("3".equals(b0194)) {//3—机构分组
                                    continue;
                                } else if ("1".equals(b0194)) {//1—法人单位
                                    //jgmc = b01.getB0101()+jgmc;
                                    //jgmc_short = b01.getB0104()+jgmc_short;
                                    //全称
                                    String key_full = b01.getB0111() + "_$_" + b01.getB0101() + "_$_" + a0255;
                                    String value_full = desc_full.get(key_full);
                                    if (value_full == null) {
                                        desc_full.put(key_full, jgmcList.toString() + zwmc);
                                    } else {//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示
                                        List<String> romvelist = new ArrayList<String>();
                                        for (int i = jgmcList.size() - 1; i >= 0; i--) {
                                            if (value_full.indexOf(jgmcList.get(i)) >= 0) {
                                                romvelist.add(jgmcList.get(i));
                                            }
                                        }
                                        jgmcList.removeAll(romvelist);

                                        desc_full.put(key_full, value_full + "、" + jgmcList.toString() + zwmc);


                                    }
                                    //简称
                                    String key_short = b01.getB0111() + "_$_" + b01.getB0104() + "_$_" + a0255;
                                    String value_short = desc_short.get(key_short);
                                    if (value_short == null) {
                                        desc_short.put(key_short, jgmc_shortList.toString() + zwmc);
                                    } else {
                                        List<String> romvelist = new ArrayList<String>();
                                        for (int i = jgmc_shortList.size() - 1; i >= 0; i--) {
                                            if (value_short.indexOf(jgmc_shortList.get(i)) >= 0) {
                                                romvelist.add(jgmc_shortList.get(i));
                                            }
                                        }
                                        jgmc_shortList.removeAll(romvelist);
                                        desc_short.put(key_short, value_short + "、" + jgmc_shortList.toString() + zwmc);
                                    }
                                    break;
                                } else {
                                    break;
                                }
                            }
                        }
                    } else if ("1".equals(b0194)) {//1—法人单位； 第一次就是法人单位，不往上递归
                        String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
                        String value_full = desc_full.get(key_full);
                        if (value_full == null) {
                            desc_full.put(key_full, zwmc);//key 编码_$_机构名称_$_是否已免
                        } else {
                            desc_full.put(key_full, value_full + "、" + zwmc);
                        }

                        //简称
                        String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + a0255;
                        String value_short = desc_short.get(key_short);
                        if (value_short == null) {
                            desc_short.put(key_short, zwmc);
                        } else {
                            desc_short.put(key_short, value_short + "、" + zwmc);
                        }
                    }

                }

            }

            for (String key : desc_full.keySet()) {//全名
                String[] parm = key.split("_\\$_");
                String jgzw = parm[1] + desc_full.get(key);
                if ("1".equals(parm[2])) {//在任
                    //任职机构 职务名称
                    if (!"".equals(jgzw)) {
                        zrqm += jgzw + "，";
                    }
                } else {//以免

                    if (!"".equals(jgzw)) {
                        ymqm += jgzw + "，";
                    }
                }
            }


            for (String key : desc_short.keySet()) {//简称
                String[] parm = key.split("_\\$_");
                String jgzw = parm[1] + desc_short.get(key);
                if ("1".equals(parm[2])) {//在任
                    //任职机构 职务名称
                    if (!"".equals(jgzw)) {
                        zrjc += jgzw + "，";
                    }
                } else {//以免
                    if (!"".equals(jgzw)) {
                        ymjc += jgzw + "，";
                    }
                }
            }


            if (!"".equals(zrqm)) {
                zrqm = zrqm.substring(0, zrqm.length() - 1);
            }
            if (!"".equals(ymqm)) {
                ymqm = ymqm.substring(0, ymqm.length() - 1);
                ymqm = "(原" + ymqm + ")";
            }
            if (!"".equals(zrjc)) {
                zrjc = zrjc.substring(0, zrjc.length() - 1);
            }
            if (!"".equals(ymjc)) {
                ymjc = ymjc.substring(0, ymjc.length() - 1);
                ymjc = "(原" + ymjc + ")";
            }
            A01 a01 = (A01) sess.get(A01.class, a0000);
            a01.setA0192a(zrqm + ymqm);
            a01.setA0192(zrjc + ymjc);
            sess.update(a01);
            //人员基本信息界面
            if (isEvent) {
                this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192a').value='" + (a01.getA0192a() == null ? "" : a01.getA0192a()) + "';");
                this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192').value='" + (a01.getA0192() == null ? "" : a01.getA0192()) + "';");
                this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192a').value='" + (a01.getA0192a() == null ? "" : a01.getA0192a()) + "';");
                this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192').value='" + (a01.getA0192() == null ? "" : a01.getA0192()) + "';");
            }
        } else {
            A01 a01 = (A01) sess.get(A01.class, a0000);
            a01.setA0192a(null);
            a01.setA0192(null);
            sess.update(a01);
            //人员基本信息界面
            if (isEvent) {
                this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192a').value='';");
                this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192').value='';");
                this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192a').value='" + (a01.getA0192a() == null ? "" : a01.getA0192a()) + "';");
                this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192').value='" + (a01.getA0192() == null ? "" : a01.getA0192()) + "';");
            }

        }

        this.UpdateTimeBtn(id);
        return EventRtnType.NORMAL_SUCCESS;
    }


    /**
     * 更新名称对应的时间
     *
     * @return
     * @throws RadowException
     */
    @Transaction
    @NoRequiredValidate
    public int UpdateTimeBtn(String id) throws RadowException {

        boolean isEvent = false;

        String a0000 = null;
        try {
        	String params=this.getPageElement("subWinIdBussessId").getValue();
        	String[] param = params.split(",");
        	a0000=param[0];
        } catch (RuntimeException e) {

        }
        if (a0000 == null || "".equals(a0000)) {
            a0000 = id;
        } else {
            isEvent = true;
        }
        HBSession sess = HBUtil.getHBSession();
        String sql = "from A02 where a0000='" + a0000 + "' and a0281='true' order by a0223";//-1 其它单位and a0201b!='-1'
        List<A02> list = sess.createQuery(sql).list();
        if (list != null && list.size() > 0) {
            Map<String, String> desc_full = new LinkedHashMap<String, String>();//描述 全称


            String zrqm = "";//全名 在任
            String ymqm = "";//以免
            String zrjc = "";//简称
            String ymjc = "";
            for (A02 a02 : list) {
                String a0255 = a02.getA0255();//任职状态
                String jgbm = a02.getA0201b();//机构编码
                List<String> jgmcList = new ArrayList<String>() {
                    @Override
                    public String toString() {
                        StringBuffer sb = new StringBuffer("");
                        for (int i = this.size() - 1; i >= 0; i--) {
                            sb.append(this.get(i));
                        }
                        return sb.toString();
                    }
                };//机构名称 全名
                //jgmcList.add(a02.getA0201a()==null?"":a02.getA0201a());
                jgmcList.add("");


                List<String> jgmc_shortList = new ArrayList<String>() {
                    @Override
                    public String toString() {
                        StringBuffer sb = new StringBuffer("");
                        for (int i = this.size() - 1; i >= 0; i--) {
                            sb.append(this.get(i));
                        }
                        return sb.toString();
                    }
                };
                jgmc_shortList.add(a02.getA0201c() == null ? "" : a02.getA0201c());//机构名称 简称
                String zwmc = a02.getA0215a() == null ? "" : a02.getA0215a();//职务名称

                String zwrzshj = "";//职务任职时间
                if (a02.getA0243() != null && a02.getA0243().length() >= 6 && a02.getA0243().length() <= 8) {
                    zwrzshj = a02.getA0243().substring(0, 4) + "." + a02.getA0243().substring(4, 6);
                }


                B01 b01 = null;
                if (jgbm != null && !"".equals(jgbm)) {//导入的数据有些为空。 机构编码不为空。
                    b01 = (B01) sess.get(B01.class, jgbm);
                }
                if (b01 != null) {
                    String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
                    if ("2".equals(b0194)) {//2—内设机构
                        while (true) {
                            b01 = (B01) sess.get(B01.class, b01.getB0121());
                            if (b01 == null) {
                                break;
                            } else {
                                b0194 = b01.getB0194();
                                if ("2".equals(b0194)) {//2—内设机构
                                    //jgmc = b01.getB0101()+jgmc;
                                    jgmcList.add(b01.getB0101());
                                    jgmc_shortList.add(b01.getB0104());
                                } else if ("3".equals(b0194)) {//3—机构分组
                                    continue;
                                } else if ("1".equals(b0194)) {//1—法人单位
                                    //jgmc = b01.getB0101()+jgmc;
                                    //jgmc_short = b01.getB0104()+jgmc_short;
                                    //全称
                                    String key_full = b01.getB0111() + "_$_" + b01.getB0101() + "_$_" + a0255;
                                    String value_full = desc_full.get(key_full);
                                    if (value_full == null) {
                                        //desc_full.put(key_full, jgmcList.toString()+zwmc+zwrzshj);
                                        desc_full.put(key_full, zwrzshj);
                                    } else {//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示
                                        List<String> romvelist = new ArrayList<String>();
                                        for (int i = jgmcList.size() - 1; i >= 0; i--) {
                                            if (value_full.indexOf(jgmcList.get(i)) >= 0) {
                                                romvelist.add(jgmcList.get(i));
                                            }
                                        }
                                        jgmcList.removeAll(romvelist);

                                        //desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc+zwrzshj);
                                        desc_full.put(key_full, value_full + "、" + zwrzshj);

                                    }

                                    break;
                                } else {
                                    break;
                                }
                            }
                        }
                    } else if ("1".equals(b0194)) {//1—法人单位； 第一次就是法人单位，不往上递归
                        String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
                        String value_full = desc_full.get(key_full);
                        if (value_full == null) {
                            //desc_full.put(key_full, zwmc+zwrzshj);//key 编码_$_机构名称_$_是否已免
                            desc_full.put(key_full, zwrzshj);//key 编码_$_机构名称_$_是否已免
                        } else {
                            //desc_full.put(key_full, value_full + "、" + zwmc+zwrzshj);
                            desc_full.put(key_full, value_full + "、" + zwrzshj);
                        }


                    }

                }

            }

            for (String key : desc_full.keySet()) {//全名
                String[] parm = key.split("_\\$_");
                //String jgzw = parm[1]+desc_full.get(key);
                String jgzw = desc_full.get(key);
                if ("1".equals(parm[2])) {//在任
                    //任职机构 职务名称
                    if (!"".equals(jgzw)) {
                        zrqm += jgzw + "，";
                    }
                } else {//以免

                    if (!"".equals(jgzw)) {
                        ymqm += jgzw + "，";
                    }
                }
            }


            if (!"".equals(zrqm)) {
                zrqm = zrqm.substring(0, zrqm.length() - 1);
            }
            if (!"".equals(ymqm)) {
                ymqm = ymqm.substring(0, ymqm.length() - 1);
                ymqm = "(" + ymqm + ")";
            }
            if (!"".equals(zrjc)) {
                zrjc = zrjc.substring(0, zrjc.length() - 1);
            }
            if (!"".equals(ymjc)) {
                ymjc = ymjc.substring(0, ymjc.length() - 1);
                ymjc = "(" + ymjc + ")";
            }

            A01 a01 = (A01) sess.get(A01.class, a0000);
            a01.setA0192f(zrqm + ymqm);
            sess.update(a01);

            if (isEvent) {
                this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192f').value='" + (a01.getA0192f() == null ? "" : a01.getA0192f()) + "';");
            }
        } else {

            A01 a01 = (A01) sess.get(A01.class, a0000);
            a01.setA0192f(null);
            sess.update(a01);

            if (isEvent) {
                this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192f').value='" + (a01.getA0192f() == null ? "" : a01.getA0192f()) + "';");
            }

        }
        return EventRtnType.NORMAL_SUCCESS;
    }


    @SuppressWarnings("static-access")
    @PageEvent("deleteRow")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int deleteRow(String a0200) throws RadowException, AppException {
        HBSession sess = null;
        try {
            sess = HBUtil.getHBSession();
            A02 a02 = (A02) sess.get(A02.class, a0200);

            A01 a01 = (A01) sess.get(A01.class, a02.getA0000());
            //applog.createLog("3023", "A02", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A02(), new A02()));

            //记录被删除了哪条信息
            applog.createLog("3023", "A02", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(a02, new A02()));

            sess.delete(a02);
            sess.flush();
            //更新人员状态
            updateA01(a01, sess);

            //刷新列表，并且更新简历信息
            this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery');" +
                    "window.realParent.ajaxSubmit('genResume');");

            a02 = new A02();
            this.getPageElement("a0201b_combo").setValue("");
            PMPropertyCopyUtil.copyObjValueToElement(a02, this);

            CustomQueryBS.setA01(a01.getA0000());
            A01 a01F = (A01) sess.get(A01.class, a01.getA0000());
         //   this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));

        } catch (Exception e) {
            this.setMainMessage("删除失败！");
            return EventRtnType.FAILD;
        }

        return EventRtnType.NORMAL_SUCCESS;
    }


    @SuppressWarnings("unchecked")
    @PageEvent("worksort")
    @NoRequiredValidate
    @Transaction
    public int upsort(String id) throws RadowException {

        List<HashMap<String, String>> list = this.getPageElement("WorkUnitsGrid").getStringValueList();
        try {
            int i = 0, j = 0;
            for (HashMap<String, String> m : list) {
                String a0200 = m.get("a0200");//a02 id
                String a0255 = m.get("a0255");//任职 状态

                HBUtil.executeUpdate("update a02 set a0223=" + j++ + " where a0200='" + a0200 + "'");

            }


            this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表
        } catch (Exception e) {
            this.setMainMessage("排序失败！");
            return EventRtnType.FAILD;
        }


        return EventRtnType.NORMAL_SUCCESS;
    }


    @SuppressWarnings("unchecked")
    @PageEvent("sortUseTime")
    @NoRequiredValidate
    @Transaction
    public int sortUseTime(String id) throws RadowException {
    	String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
        String sql = "From A02 where a0000='" + a0000 + "' order by a0243 desc ";//任职状态  任职时间降序
        HBSession sess = null;
        try {
            sess = HBUtil.getHBSession();

            List<A02> list = sess.createQuery(sql).list();
            int i = 0, j = 0;
            if (list != null && list.size() > 0) {
                for (A02 a02 : list) {
                    String a0200 = a02.getA0200();//a02 id
                    String a0255 = a02.getA0255();//任职 状态

                    HBUtil.executeUpdate("update a02 set a0223=" + j++ + " where a0200='" + a0200 + "'");
                }
            }

            this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表
        } catch (Exception e) {
            this.setMainMessage("排序失败！");
            return EventRtnType.FAILD;
        }


        return EventRtnType.NORMAL_SUCCESS;
    }

    @SuppressWarnings("unchecked")
    @PageEvent("setZB08Code")
    @NoRequiredValidate
    @Transaction
    public int setZB08Code(String id) throws RadowException {

        HBSession sess = HBUtil.getHBSession();
        String sql = "select B0194 from B01 where  B0111 ='" + id + "'";
        Object B0194 = sess.createSQLQuery(sql).uniqueResult();


        if (B0194 != null && B0194.equals("3")) {

            String msg = "不可选择机构分组单位！";

            ((Combo) this.getPageElement("a0201b")).setValue("");
            this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','" + msg + "');document.getElementById('a0201b').value='';"
                    + "document.getElementById('a0201b_combo').value='';"
            );

            return EventRtnType.NORMAL_SUCCESS;
        }


        if (B0194 != null && B0194.equals("2")) {                                //任职机构如果选择了内设机构，则任职状态和职务名称不可选


            this.getExecuteSG().addExecuteCode(""
                    + "document.getElementById('a0201e_combo').disabled=true;document.getElementById('a0201e_combo').style.backgroundColor='#EBEBE4';"
                    + "document.getElementById('a0201e_combo').style.backgroundImage='none';Ext.query('#a0201e_combo+img')[0].style.display='none';"
                    + "document.getElementById('b0194Type').value='2';changea0201d(2);"
            );
            this.getExecuteSG().addExecuteCode("setA0201eDisabled()");
        } else {
            if (B0194 != null && B0194.equals("1")) {
                this.getExecuteSG().addExecuteCode("changea0201d(1);document.getElementById('b0194Type').value='1';");
            } else {
                this.getExecuteSG().addExecuteCode("changea0201d(2);document.getElementById('b0194Type').value='3';");
            }

            this.getExecuteSG().addExecuteCode(""
                    + "document.getElementById('a0201e_combo').readOnly=false;document.getElementById('a0201e_combo').disabled=false;"
                    + "document.getElementById('a0201e_combo').style.backgroundColor='#fff';"
                    + "Ext.query('#a0201e_combo+img')[0].style.display='block';"
            );
            this.getExecuteSG().addExecuteCode("setA0201eDisabled()");
        }

        try {
            String v = HBUtil.getValueFromTab("b0101", "B01", "b0111='" + id + "'");
            if (v != null) {
                this.getPageElement("a0201b_combo").setValue(v);
            } else {
            }

        } catch (AppException e) {
            e.printStackTrace();
        }


        //将任职机构名称赋值到页面a0201a
        if (id != null && !id.equals("")) {
            String a0201a = this.getPageElement("a0201b_combo").getValue();//机构名称 中文。
            this.getPageElement("a0201a").setValue(a0201a);
        }

        return EventRtnType.NORMAL_SUCCESS;
    }


    //检查统计关系所在单位是否为“内设机构”
    @PageEvent("a0195Change")
    @NoRequiredValidate
    public int a0195Change(String a0195) throws RadowException, AppException, UnsupportedEncodingException {
        HBSession sess = HBUtil.getHBSession();
        String sql = "select B0194 from B01 where  B0111 ='" + a0195 + "'";
        Object B0194 = sess.createSQLQuery(sql).uniqueResult();

        if (B0194 == null) {
            B0194 = "";
        }

        if (B0194 != null && B0194.equals("2") || B0194.equals("3")) {

            String msg = "不可选择内设机构单位！";
            if (B0194.equals("3")) {
                msg = "不可选择机构分组单位！";
            }

            ((Combo) this.getPageElement("a0195")).setValue("");
            this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','" + msg + "');document.getElementById('a0195').value='';"
                    + "document.getElementById('a0195key').value = '';document.getElementById('a0195value').value = '';document.getElementById('a0195_val').value='';"
            );
        } else {
            //修改父页面的统计关系所在单位
            String key = this.getPageElement("a0195key").getValue();
            String value = this.getPageElement("a0195value").getValue();
            if (!("".equals(key) && "".equals(value))) {
                this.getExecuteSG().addExecuteCode("realParent.setA0195Value('" + key + "','" + value + "');");
            }
        }

        return EventRtnType.NORMAL_SUCCESS;
    }


    @PageEvent("a0201bChange")
    @NoRequiredValidate
    public int a0201bChange(String a0201b) throws RadowException, AppException, UnsupportedEncodingException {
        HBSession sess = HBUtil.getHBSession();
        String sql = "select B0194 from B01 where  B0111 ='" + a0201b + "'";
        Object B0194 = sess.createSQLQuery(sql).uniqueResult();                //机构类型

        //没有职务信息时，做联动
        int num = this.getPageElement("WorkUnitsGrid").getStringValueList().size();
        if (num == 0) {

            String a0195 = this.getPageElement("a0195").getValue();        //统计所在单位


            if (B0194 != null && !B0194.equals("2")) {                //如果“任职机构”为内设机构，则不作联动


                if ((a0195 != null && a0195.equals("")) || num == 0) {            //如果“统计所在单位”已经存在，则不作联动

                    this.getPageElement("a0195").setValue(a0201b);
                    String v = HBUtil.getValueFromTab("b0101", "B01", "b0111='" + a0201b + "'");
                    if (v != null) {
                        this.getPageElement("a0195_val").setValue(v);
                        this.getPageElement("a0195value").setValue(v);
                    } else {
                        this.getPageElement("a0195_val").setValue("");
                        this.getPageElement("a0195value").setValue("");
                    }


                    this.getPageElement("a0195key").setValue(a0201b);

                    this.a0195Change(a0201b);

                }

            } else if (B0194.equals("2")) {        //如果为内设机构则将上一级法人单位赋值给：统计所在单位

                //获取机构树结构
                List<Object[]> listres = sess.createSQLQuery("select b0111,b0194,b0121,B0101 from b01").list();

                Map<String, TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
                for (Object[] treedata : listres) {
                    TreeNode rootnode = genNode(treedata);
                    nodemap.put(rootnode.getId(), rootnode);
                }

                TreeNode bcn = nodemap.get(a0201b);

                //String sql = "select B0121 from B01 where  B0111 ='"+a0201b+"'";
                String b0194 = "";
                if (bcn != null) {
                    b0194 = bcn.getText();


                    while (true) {
                        TreeNode cn = nodemap.get(a0201b);
                        TreeNode n = nodemap.get(cn.getParentid());
                        if (n == null) {
                            throw new RadowException("机构读取异常");
                        }
                        if (n.getText() == null || "".equals(n.getText())) {
                            throw new RadowException("机构读取异常");
                        }
                        a0201b = n.getId();
                        if (!"1".equals(n.getText())) {//不是法人单位,继续往上找机构
                            continue;
                        } else {            //法人单位

                            String B0111 = n.getId();            //机构id
                            String B0101 = n.getLink();        //机构名称


                            this.getPageElement("a0195").setValue(B0111);
                            this.getPageElement("a0195key").setValue(B0111);
                            this.getPageElement("a0195_val").setValue(B0101);
                            this.getPageElement("a0195value").setValue(B0101);

                            this.a0195Change(B0111);

                            return EventRtnType.NORMAL_SUCCESS;
                        }
                    }

                } else {
                    throw new RadowException("机构读取异常");
                }

            } else {
                this.getPageElement("a0195").setValue("");
                this.getPageElement("a0195_val").setValue("");

                this.getPageElement("a0195key").setValue("");
                this.getPageElement("a0195value").setValue("");
                this.a0195Change("");
            }


        }

        return EventRtnType.NORMAL_SUCCESS;
    }

    //判断统计机构关系是否合理
    @PageEvent("check")
    @NoRequiredValidate
    public int check() throws RadowException, AppException, UnsupportedEncodingException {
        HBSession sess = HBUtil.getHBSession();
        String a0195 = this.getPageElement("a0195").getValue();
        String a0201b = this.getPageElement("a0201b").getValue();

        if (a0195 == null || "".equals(a0195)) {
            this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( "
                    + " '提示', "
                    + " '统计关系所在单位为空，是否继续保存？', "
                    + " function (btn){ "
                    + "	if(btn=='yes'){ "
                    + "		radow.doEvent('save'); "
                    + "	} "
                    + "} "
                    + ");");
            return EventRtnType.NORMAL_SUCCESS;
        }
        //获取机构树结构
        List<Object[]> listres = sess.createSQLQuery("select b0111,b0194,b0121,B0101 from b01").list();

        Map<String, TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
        for (Object[] treedata : listres) {
            TreeNode rootnode = genNode(treedata);
            nodemap.put(rootnode.getId(), rootnode);
        }
        TreeNode bcn = nodemap.get(a0201b);
        String b0194 = "";
        if (bcn != null) {
            b0194 = bcn.getText();
            if (!"2".equals(b0194)) {
                this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( "
                        + " '提示', "
                        + " '统计关系所在单位和任职机构名称不相同，是否继续保存？', "
                        + " function (btn){ "
                        + "	if(btn=='yes'){ "
                        + "		radow.doEvent('save'); "
                        + "	} "
                        + "} "
                        + ");");
                return EventRtnType.NORMAL_SUCCESS;
            } else {
                while (true) {
                    TreeNode cn = nodemap.get(a0201b);
                    TreeNode n = nodemap.get(cn.getParentid());
                    if (n == null) {
                        throw new RadowException("机构读取异常");
                    }
                    if (n.getText() == null || "".equals(n.getText())) {
                        throw new RadowException("机构读取异常");
                    }
                    a0201b = n.getId();
                    if (!"1".equals(n.getText())) {//不是法人单位,继续往上找机构
                        continue;
                    } else {//法人单位
                        if (a0195.equals(n.getId())) {
                            this.getExecuteSG().addExecuteCode("radow.doEvent('save');");
                            return EventRtnType.NORMAL_SUCCESS;
                        } else {
                            this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( "
                                    + " '提示', "
                                    + " '统计关系所在单位和任职机构名称不相同，是否继续保存？', "
                                    + " function (btn){ "
                                    + "	if(btn=='yes'){ "
                                    + "		radow.doEvent('save'); "
                                    + "	} "
                                    + "} "
                                    + ");");
                            return EventRtnType.NORMAL_SUCCESS;
                        }
                    }
                }
            }
        } else {
            throw new RadowException("机构读取异常");
        }
    }

    private static TreeNode genNode(Object[] treedata) {
        TreeNode node = new TreeNode();
        node.setId(treedata[0].toString());
        node.setText(treedata[1].toString());
        node.setLink(treedata[3].toString());
        node.setLeaf(true);
        if (treedata[2] != null)
            node.setParentid(treedata[2].toString());
        node.setOrderno((short) 1);

        return node;
    }

    private static void genTree(Map<String, TreeNode> nodemap) {
        for (String key : nodemap.keySet()) {
            TreeNode node = nodemap.get(key);
            if (!"-1".equals(node.getParentid())) {
                String a = node.getParentid();

            }
        }
    }
}
