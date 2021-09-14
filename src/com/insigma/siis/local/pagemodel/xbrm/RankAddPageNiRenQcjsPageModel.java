package com.insigma.siis.local.pagemodel.xbrm;

import com.fr.stable.core.UUID;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.*;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.entity.Js22;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.CommonQueryBS;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RankAddPageNiRenQcjsPageModel extends PageModel {
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
       // this.getExecuteSG().addExecuteCode("setValue();");
    	String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
    	String prope=param[1];
    	if(!StringUtil.isEmpty(prope)){
    		this.getPageElement("subproperty").setValue(prope);
    	}else{
    		this.getPageElement("subproperty").setValue("");
    	}
        //拟任单位及职务列表
        this.setNextEventName("NiRenGrid.dogridquery");
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("NiRenGrid.dogridquery")
    @NoRequiredValidate
    public int  niRenGridQuery(int start,int limit) throws RadowException{
    	String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
    	String JS0100=param[2];
    	String sql="select * from js22 where a0000='"+a0000+"' and JS0100='"+JS0100+"' order by sortid";
    	this.pageQuery(sql, "SQL", start, limit);
    	return EventRtnType.SPE_SUCCESS;
    }
    
    
    @PageEvent("save")
    public int save() throws RadowException{
    	String js2201=this.getPageElement("js2201").getValue();
    	String js2202=this.getPageElement("js2202").getValue();
    	String js2203=this.getPageElement("js2203").getValue();
    	String js2204=this.getPageElement("js2204").getValue();
    	String js2207=this.getPageElement("js2207").getValue();
    	
    	String js2200=UUID.randomUUID().toString();
    	String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
    	String JS0100=param[2];
    	HBSession sess =HBUtil.getHBSession();
    	try {
    		B01 b01 = (B01) sess.get(B01.class, js2202);
    		
    		Object o = sess.createSQLQuery("select rb_id from js01 t where JS0100='"+JS0100+"'").uniqueResult();
    		if(o==null || "".equals(o.toString().trim())) {
    			this.setMainMessage("数据异常，请重新打开页面！");
    			return EventRtnType.NORMAL_SUCCESS;
    		}
    		/*if(js2207.equals("1")) {
    			List l = sess.createSQLQuery("select * from js22 t where JS0100='"+JS0100+"' and js2207='1'").list();
    			if(l!=null && l.size()>0) {
    				this.setMainMessage("只能设置一条主职务！");
        			return EventRtnType.NORMAL_SUCCESS;
    			}
    		}*/
    		
    		BigDecimal c = (BigDecimal) sess.createSQLQuery("select max(nvl(sortid,0)) from js22 where "
    				+ " js22.js0100 in ('"+JS0100+"')").uniqueResult();
    		int sortid = (c==null ? 0:c.intValue()) + 1;
    		String b0104 = b01.getB0104();//简称
    		String b0194 = b01.getB0194();//单位类型
    		if(b0194.equals("3")) {
    			this.setMainMessage("不能选择机构分组！");
    			return EventRtnType.NORMAL_SUCCESS;
    		} else if(b0194.equals("2")) {
    			String name[] = new String[] {"",""};
    			name = getFrdwName(js2202 , name);
    			HBUtil.executeUpdate("insert into js22(js2200,js2201,js2202,js2203,a0000,js2204,JS0100,"
    					+ "js2201a,js2201b,js2201c,sortid,js2207,rb_id) values(?,?,?,?,?, ?,?,?,?,?, ?,?,?)",new Object[]{js2200,js2201,js2202
    					,js2203,a0000,js2204,JS0100,b0104,name[1],name[0],sortid,js2207,o+""});
    		} else {
    			HBUtil.executeUpdate("insert into js22(js2200,js2201,js2202,js2203,a0000,js2204,JS0100,"
    					+ "js2201a,js2201b,js2201c,sortid,js2207,rb_id) values(?,?,?,?,?, ?,?,?,?,?, ?,?,?)",new Object[]{js2200,js2201,js2202
    					,js2203,a0000,js2204,JS0100,b0104,b0104,js2201,sortid,js2207,o+""});
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败!");
			return EventRtnType.NORMAL_SUCCESS;
		}
    	this.setNextEventName("NiRenGrid.dogridquery");
    	return EventRtnType.NORMAL_SUCCESS;
    }
    private String[] getFrdwName(String codevalueparameter2, String name[]) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		try {
			B01 b01 = (B01) sess.get(B01.class, codevalueparameter2);
			if(b01.getB0194().equals("1") || codevalueparameter2.equals("001.001")) {
				name[0] = b01.getB0101()+name[0];
				name[1] = b01.getB0104()+name[1];
				return name;
			} else {
				name[0] = b01.getB0101()+name[0];
				name[1] = b01.getB0104()+name[1];
				return getFrdwName(b01.getB0121(), name);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("数据异常!");
		}
	}
    @PageEvent("savecheck")
    public int savecheck() throws RadowException{
    	String js2200s=this.getPageElement("js2200s").getValue();
    	if(!StringUtil.isEmpty(js2200s)){
    		js2200s=js2200s.substring(0,js2200s.length()-1).replaceAll(",", "','");
    	}
    	CommonQueryBS cq=new CommonQueryBS();
    	String js2203s="";
    	String js2201s="";
    	String js2202s="";
    	String js2204s="";
    	try {
			List<HashMap<String, Object>> list = cq.getListBySQL("select a0000,js2201,js2202,js2203,js2200,js2204,js2201b,js2201c from js22 where js2200 in('"+js2200s+"')");
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				String js2201 = (String)map.get("js2201");//单位
				String js2203 = (String)map.get("js2203");//职务
				String js2202 = (String)map.get("js2202");//单位ID
				String js2201b = (String)map.get("js2201b");//单位
				String js2201c = (String)map.get("js2201c");//单位
				js2204s=js2204s+js2201c+js2203+"，";
				js2203s=js2203s+js2201b+js2203+"，";
				if(!js2201s.contains(js2201)){
					js2201s=js2201s+js2201+",";
				}
				if(!js2202s.contains(js2202)){
					js2202s=js2202s+js2202+",";
				}
			}
			if(!StringUtil.isEmpty(js2203s)){
				js2203s=js2203s.substring(0, js2203s.length()-1);
			}
			if(!StringUtil.isEmpty(js2201s)){
				js2201s=js2201s.substring(0, js2201s.length()-1);
			}
			if(!StringUtil.isEmpty(js2202s)){
				js2202s=js2202s.substring(0, js2202s.length()-1);
			}
			this.getExecuteSG().addExecuteCode("saveafter('"+js2201s+"','"+js2203s+"','"+js2202s+"','"+js2204s+"')");
			
    	} catch (AppException e) {
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
	@PageEvent("UpdateTitleBtn.onclick")
	@Transaction
	@NoRequiredValidate
	public int UpdateTitleBtn(String id) throws RadowException, AppException {
		String js2200s=this.getPageElement("js2200s").getValue();
    	if(!StringUtil.isEmpty(js2200s)){
    		js2200s=js2200s.substring(0,js2200s.length()-1).replaceAll(",", "','");
    	}
    	String js2201s="";
    	String js2202s="";
		HBSession sess = HBUtil.getHBSession();
		String params=this.getPageElement("subWinIdBussessId").getValue();
    	String[] param = params.split(",");
    	String a0000=param[0];
    	String JS0100=param[2];
    	Js01 js01 = (Js01) sess.get(Js01.class, JS0100);
    	String v_xt = js01.getJs0122();
    	String wheresql = "";
		String tablesql = "";
    	if(v_xt!=null && (v_xt.equals("2") || v_xt.equals("3")|| v_xt.equals("4"))) {
			wheresql = " and v_xt='"+v_xt+"' ";
			tablesql = " v_js_";
		}
		String sql = "select * from js22 where js2200 in ('"+js2200s+"') order by sortid";//-1 其它单位and a0201b!='-1'
		CommonQueryBS cq=new CommonQueryBS();
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//描述 全称
			Map<String,String> desc_short = new LinkedHashMap<String, String>();//描述 简称
			
			String zrqm = "";//全名 在任
			String ymqm = "";//以免
			String zrjc = "";//简称
			String ymjc = "";
			for(HashMap<String, Object> js22 : list){
				//String a0255 = a02.getA0255();//任职状态
				String jgbm = js22.get("js2202")+"";//机构编码
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//机构名称 全名
				jgmcList.add(js22.get("js2201")==null?"":js22.get("js2201")+"");
				
				
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(js22.get("js2201a")==null?"":js22.get("js2201a")+"");//机构名称 简称
				String zwmc = js22.get("js2203")==null?"":js22.get("js2203")+"";//职务名称
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//导入的数据有些为空。 机构编码不为空。
					b01 = (B01)sess.get(B01.class, jgbm);
					
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
					if("2".equals(b0194)){//2—内设机构
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2—内设机构
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3—机构分组
									continue;
								}else if("1".equals(b0194)){//1—法人单位
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//全称
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + "1";
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										desc_full.put(key_full, jgmcList.toString()+zwmc);
									}else{//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc);
										
										
									}
									//简称
									String key_short = b01.getB0111()+"_$_"+b01.getB0104() + "_$_" + "1";
									String value_short = desc_short.get(key_short);
									if(value_short==null){
										desc_short.put(key_short, jgmc_shortList.toString()+zwmc);
									}else{
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmc_shortList.size()-1;i>=0;i--){
											if(value_short.indexOf(jgmc_shortList.get(i))>=0){
												romvelist.add(jgmc_shortList.get(i));
											}
										}
										jgmc_shortList.removeAll(romvelist);
										desc_short.put(key_short, value_short + "、" + jgmc_shortList.toString()+zwmc);
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1—法人单位； 第一次就是法人单位，不往上递归
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + "1";
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwmc);//key 编码_$_机构名称_$_是否已免
						}else{
							desc_full.put(key_full, value_full + "、" + zwmc);
						}
						
						//简称
						String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + "1";
						String value_short = desc_short.get(key_short);
						if(value_short==null){
							desc_short.put(key_short, zwmc);
						}else{
							desc_short.put(key_short, value_short  + "、" +  zwmc);
						}
					}
					
				}
				
				String js2201 = (String)js22.get("js2201");//单位
				String js2202 = (String)js22.get("js2202");//单位ID
				if(!js2201s.contains(js2201)){
					js2201s=js2201s+js2201+",";
				}
				if(!js2202s.contains(js2202)){
					js2202s=js2202s+js2202+",";
				}
			}
			
			for(String key : desc_full.keySet()){//全名
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_full.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "，";
					}
				}else{//以免
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "，";
					}
				}
			}
			
			
			for(String key : desc_short.keySet()){//简称
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_short.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrjc += jgzw + "，";
					}
				}else{//以免
					if(!"".equals(jgzw)){
						ymjc += jgzw + "，";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "(原"+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "(原"+ymjc+")";
			}
			
			if(!StringUtil.isEmpty(js2201s)){
				js2201s=js2201s.substring(0, js2201s.length()-1);
			}
			if(!StringUtil.isEmpty(js2202s)){
				js2202s=js2202s.substring(0, js2202s.length()-1);
			}
			/*a01.setA0192a(zrqm+ymqm);
			a01.setA0192(zrjc+ymjc);*/
			this.getExecuteSG().addExecuteCode("saveafter('"+js2201s+"','"+zrjc+"','"+js2202s+"','"+zrqm+"')");
		}else{
			
		}
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
    
    @PageEvent("deleterow")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int deleterow(String js2200) {
    	try {
			HBUtil.executeUpdate("delete from js22 where js2200='"+js2200+"'");
			this.setNextEventName("NiRenGrid.dogridquery");
			this.setMainMessage("删除成功!");
		} catch (AppException e) {
			e.printStackTrace();
			this.setMainMessage("删除失败!");
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
    
    
    @PageEvent("saveSort")
    @GridDataRange
    @NoRequiredValidate
    public int saveSort() throws RadowException {
    	List<HashMap<String, String>> list = this.getPageElement("NiRenGrid").getStringValueList();
        HBSession sess = HBUtil.getHBSession();
        Connection con = null;
        try {
            con = sess.connection();
            con.setAutoCommit(false);
            String sql = "update js22 set sortid=? where js2200=?";
            PreparedStatement ps = con.prepareStatement(sql);
            int i = 1;
            for (HashMap<String, String> m : list) {
                String js2200 = m.get("js2200");
                ps.setInt(1, i);
                ps.setString(2, js2200);
                ps.addBatch();
                i++;
            }
            ps.executeBatch();
            con.commit();
            ps.close();
            con.close();
        } catch (Exception e) {
            try {
                con.rollback();
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            this.setMainMessage("排序失败！");
            return EventRtnType.FAILD;
        }
        this.toastmessage("排序已保存！");
        this.setNextEventName("NiRenGrid.dogridquery");
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    @Transaction
    @PageEvent("selectXZ")
    public int selectXZ(String val){
    	String[] vals = val.split(",");
    	String js2200 = vals[0];
    	String js2207 = vals[1];
    	HBSession sess = HBUtil.getHBSession();
        sess.createSQLQuery("update js22 set js2207 = '"+js2207+"' where js2200 = '"+js2200+"'").executeUpdate();
    	return EventRtnType.NORMAL_SUCCESS;
    }
}
