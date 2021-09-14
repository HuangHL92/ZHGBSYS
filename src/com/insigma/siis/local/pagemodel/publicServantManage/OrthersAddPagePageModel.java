package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.json.JSONException;
import org.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A71;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class OrthersAddPagePageModel extends PageModel {
	private LogUtil applog = new LogUtil();
	/**
     * 将实体POJO转化为JSON
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static<T> String objectToJson(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();  
        // Convert object to JSON string  
        String jsonStr = "{}";
        try {
             jsonStr =  mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return jsonStr;
    }
	
	@Override
	public int doInit() throws RadowException {
		String a0000 = (String)this.request.getSession().getAttribute("a0000");
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql = "from A01 where a0000='" + a0000
					+ "'";
			List list = sess.createQuery(sql).list();
			A01 a01 = (A01) list.get(0);
			String sql2 = " from A71 where a0000='"+a01.getA0000()+"'";
			List<A71> listA71 = sess.createQuery(sql2).list();
			A71 a71 = null;
			for(A71 a71list:listA71){
				a71 = a71list;
			}
			if (a71 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a71, this);
				//旧值 判断是否修改
				String json = objectToJson(a71);
				this.getExecuteSG().addExecuteCode("parent.A71value="+json+";");
			}
			// 进入管理加载
			A29 a29 = (A29) sess.get(A29.class, a01.getA0000());
			if (a29 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a29, this);
				//旧值 判断是否修改
				String json = objectToJson(a29);
				this.getExecuteSG().addExecuteCode("parent.A29value="+json+";");
			}
	//		else{//选调生建议莫非为否
	//			this.getExecuteSG().addExecuteCode("odin.setSelectValue('a2970', '03');");
	//		}
			// 拟任免加载
			String sqlA53 = "from A53 where a0000='" + a0000 + "' and a5399='"+SysManagerUtils.getUserId()+"'";
			List listA53 = sess.createQuery(sqlA53).list();
			if (listA53 != null && listA53.size() > 0) {
				A53 a53 = (A53) listA53.get(0);
				PMPropertyCopyUtil.copyObjValueToElement(a53, this);
				//旧值 判断是否修改
				String json = objectToJson(a53);
				this.getExecuteSG().addExecuteCode("parent.A53value="+json+";");
			}else{
				String date = DateUtil.getcurdate();
				A53 a53 = new A53();
				a53.setA5321(date);
				a53.setA5323(date);
				a53.setA5327(SysManagerUtils.getUserName());
				//PMPropertyCopyUtil.copyObjValueToElement(a53, this);
			}

			// 住址通讯加载
			A37 a37 = (A37) sess.get(A37.class, a01.getA0000());
			if (a37 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a37, this);
				//旧值 判断是否修改
				String json = objectToJson(a37);
				this.getExecuteSG().addExecuteCode("parent.A37value="+json+";");
			}

			// 离退加载
			A31 a31 = (A31) sess.get(A31.class, a01.getA0000());
			if (a31 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a31, this);
				//旧值 判断是否修改
				String json = objectToJson(a31);
				this.getExecuteSG().addExecuteCode("parent.A31value="+json+";");
			}

			// 退出管理加载
			A30 a30 = (A30) sess.get(A30.class, a01.getA0000());
			if (a30 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a30, this);
				//旧值 判断是否修改
				String json = objectToJson(a30);
				this.getExecuteSG().addExecuteCode("parent.A30value="+json+";");
			}
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
			
			
			//this.getExecuteSG().addExecuteCode("document.getElementById('a0163').innerHTML='"+(HBUtil.getCodeName("ZB126", a01.getA0163()))+"';" );
			
			this.setNextEventName("TrainingInfoGrid.dogridquery");//培训信息列表		
			//初始化扩展信息项
			readInfo_Extend(sess,a0000);
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("selectchange();");
		//this.getExecuteSG().addExecuteCode("onA2970change();");
		this.getExecuteSG().addExecuteCode("onA0160Change();");
		//this.getExecuteSG().addExecuteCode("a2911onchange();a3140change();a3141change();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	private void readInfo_Extend(HBSession sess, String a0000) throws AppException {
		List<Map<String,String>>  Info_Extends = HBUtil.queryforlist("select * from Info_Extend where a0000='"+a0000+"'",null);
			if(Info_Extends!=null&&Info_Extends.size()>0){//
				Map<String,String> entity = Info_Extends.get(0);
				for(String key : entity.keySet()){
					try {
						this.getPageElement(key.toLowerCase()).setValue(entity.get(key));
					} catch (Exception e) {
					}
				}
			}
	}

	/**
	 * 人员其它信息保存
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveOthers.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveOthers(String confirm)throws RadowException, AppException{
		String a0000 = this.getRadow_parent_data();//获取页面人员内码
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			//添加备注
			A71 a71 = new A71();
			this.copyElementsValueToObj(a71, this);
			a71.setA0000(a0000);
			sess.saveOrUpdate(a71);
			//进入管理保存 id生成方式为assigned
			A01 a01 = (A01)sess.get(A01.class, a0000);
			A29 a29 = new A29();
			this.copyElementsValueToObj(a29, this);
			a29.setA0000(a0000);
			A29 a29_old = (A29)sess.get(A29.class, a0000);
			if(a29_old==null){
				a29_old = new A29();
				applog.createLog("3291", "A29", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a29_old,a29));
				
			}else{
				applog.createLog("3292", "A29", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a29_old,a29));
			}
			PropertyUtils.copyProperties(a29_old, a29);
			sess.saveOrUpdate(a29_old);	
			
		/*	//拟任免保存	id生成方式为uuid 。 如果是新增 将id设置为null
			A53 a53 = new A53();
			this.copyElementsValueToObj(a53, this);
			
			if(a53.getA5399()==null||"".equals(a53.getA5399())){
				a53.setA5399(SysManagerUtils.getUserId());
			}
			a53.setA0000(a0000);
			A53 a53_old = null;
			if("".equals(a53.getA5300())){
				a53.setA5300(null);
				a53_old = new A53();
				applog.createLog("3531", "A53", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a53_old,a53));
			}else{
				a53_old = (A53)sess.get(A53.class, a53.getA5300());
				applog.createLog("3532", "A53", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a53_old,a53));
			}
			PropertyUtils.copyProperties(a53_old, a53);
			
			sess.saveOrUpdate(a53_old);
			this.getPageElement("a5300").setValue(a53_old.getA5300());
			this.getPageElement("a5399").setValue(a53_old.getA5399());*/
			/*//住址通讯保存 id生成方式为assigned
			A37 a37 = new A37();
			this.copyElementsValueToObj(a37, this);
			a37.setA0000(a0000);
			
			A37 a37_old = (A37)sess.get(A37.class, a0000);
			if(a37_old==null){
				a37_old = new A37();
				applog.createLog("3371", "A37", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a37_old,a37));
				
			}else{
				applog.createLog("3372", "A37", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a37_old,a37));
			}
			PropertyUtils.copyProperties(a37_old, a37);
			
			sess.saveOrUpdate(a37_old);	*/
			
			//1现职人员 2离退人员 3调出人员 4已去世 5其他人员      0完全删除 1正常 2历史库 3离退人员
			//离退保存 id生成方式为assigned
			A31 a31 = new A31();
			this.copyElementsValueToObj(a31, this);
			String a3101 = a31.getA3101();//离退类别
			if(a3101!=null&&!"".equals(a3101)){
				a01.setA0163("2");
				//if(!"4".equals(a01.getStatus())){//4 临时数据
					a01.setStatus("3");
				//}
				
			}else{
				a01.setA0163("1");
				//if(!"4".equals(a01.getStatus())){
					a01.setStatus("1");
				//}
			}
			a31.setA0000(a0000);
			
			A31 a31_old = (A31)sess.get(A31.class, a0000);
			if(a31_old==null){
				a31_old = new A31();
				applog.createLog("3311", "A31", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a31_old,a31));
				
			}else{
				applog.createLog("3312", "A31", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a31_old,a31));
			}
			PropertyUtils.copyProperties(a31_old, a31);
			
			sess.saveOrUpdate(a31_old);	
			
			//1现职人员 2离退人员 3调出人员 4已去世 5其他人员       0完全删除 1正常 2历史库 3离退人员
			//退出管理保存 id生成方式为assigned
			A30 a30 = new A30();
			this.copyElementsValueToObj(a30, this);
			String a3001 = a30.getA3001();
			if(a3001!=null&&!"".equals(a3001)){
				//调出人员     历史库
				if(a3001.startsWith("1")||a3001.startsWith("2")){
					a01.setA0163("3");
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}else if("35".equals(a3001)){//死亡  显示：已去世。       查询：历史人员
					a01.setA0163("4");
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}else if("31".equals(a3001)){//离退休 显示：离退人员。     查询：离退人员
					a01.setA0163("2");
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("3");
					//}
					
				}else{//【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
					a01.setA0163("5");
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}
			}else{
				//不覆盖【离退】的状态
			}
			a30.setA0000(a0000);
			
			A30 a30_old = (A30)sess.get(A30.class, a0000);
			if(a30_old==null){
				a30_old = new A30();
				applog.createLog("3301", "A30", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a30_old,a30));
				
			}else{
				applog.createLog("3302", "A30", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a30_old,a30));
			}
			PropertyUtils.copyProperties(a30_old, a30);
			
			sess.saveOrUpdate(a30_old);
			/**
			 * @author tongzj 2017/5*29 添加成长地
			 */
			/*String a0115a = this.getPageElement("a0115a").getValue();
			if(a0115a != null){
				a01.setA0115a(a0115a.trim());
			}*/
			String a2949 = this.getPageElement("a2949").getValue();
			if(a2949 != null){
				a01.setA2949(a2949.trim());
			}
			
			sess.update(a01);
			sess.flush();
			//旧值 判断是否修改
			String json = objectToJson(a01);
			this.getExecuteSG().addExecuteCode("parent.A01value="+json+";");
			json = objectToJson(a29_old);
			this.getExecuteSG().addExecuteCode("parent.A29value="+json+";");
			/*json = objectToJson(a53_old);
			this.getExecuteSG().addExecuteCode("parent.A53value="+json+";");
			json = objectToJson(a37_old);
			this.getExecuteSG().addExecuteCode("parent.A37value="+json+";");*/
			json = objectToJson(a31_old);
			this.getExecuteSG().addExecuteCode("parent.A31value="+json+";");
			json = objectToJson(a30_old);
			this.getExecuteSG().addExecuteCode("parent.A30value="+json+";");
			
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('status').value='"+a01.getStatus()+"';");
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0163').value='"+a01.getA0163()+"';");
			//this.getExecuteSG().addExecuteCode("odin.setSelectValue('a0163','"+a01.getA0163()+"');" );
			//this.getExecuteSG().addExecuteCode("parent.document.getElementById('xgsj').value="+a01.getXgsj()+";");//时间末尾被截取 更新页面
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0163Divid').innerHTML='"+(HBUtil.getCodeName("ZB126", a01.getA0163()))+"';" );
			
			
			saveInfo_Extend(sess,a0000);
			this.getExecuteSG().addExecuteCode("if(parent.document.getElementById('IBusinessInfo')){"+
					"var BusinessInfoWindow = parent.document.getElementById('IBusinessInfo').contentWindow;"+
					"if(BusinessInfoWindow){"+
					"	BusinessInfoWindow.radow.doEvent('save.onclick','"+confirm+"--1');"+
					"}else{" +
					"	if('true'=='"+confirm+"'){" +
							"window.parent.parent.tabs.remove(parent.thisTab.tabid);" +
						"}else{" +
							"parent.$h.alert('系统提示','保存成功!');" +
						"}" +
					"" +
					"}"+
				"}else{" +
				"	if('true'=='"+confirm+"'){" +
					"window.parent.parent.tabs.remove(parent.thisTab.tabid);" +
					"}else{" +
						"parent.$h.alert('系统提示','任免表信息、其他信息保存成功!',null,'220');" +
					"}" +
				"}");
		} catch(SQLException be){
			be.printStackTrace();
			String msg = be.getMessage();
			execErrorInfoJs(msg);
			return EventRtnType.FAILD;
		}catch(GenericJDBCException be){
			be.printStackTrace();
			String msg = be.getMessage(1);
			execErrorInfoJs(msg);
			return EventRtnType.FAILD;
		}catch(DataException be){
			be.printStackTrace();
			String msg = be.getMessage(1);
			execErrorMYSQLInfoJs(msg);
			return EventRtnType.FAILD;
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存其它信息失败！");
			return EventRtnType.FAILD;
		}
		//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('persongrid').getStore().reload()");//刷新人员列表
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void execErrorMYSQLInfoJs(String msg) {
		String[] msgs = msg.split("\'");
		String id = msgs[1];
		//String retmsg = msgs[6];
		//this.setMainMessage(msg);
		String js = "var label = odin.ext.get('"+id.toLowerCase()+"').dom.getAttribute('label');" +
				"alert(label+'的值过大');" +
				"var tabs = parent.Ext.getCmp('rmbTabs');" +
				"tabs.activate(tabs.getItem('orthers'));" +
				"Ext.getCmp('"+id.toLowerCase()+"').focus()";
		this.getExecuteSG().addExecuteCode(js);
		
	}

	private void execErrorInfoJs(String msg){
		String[] msgs = msg.split("\"");
		String id = msgs[5];
		String retmsg = msgs[6];
		//this.setMainMessage(msg);
		String js = "var label = odin.ext.get('"+id.toLowerCase()+"').dom.getAttribute('label');" +
				"alert(label+'"+retmsg+"');" +
				"var tabs = parent.Ext.getCmp('rmbTabs');" +
				"tabs.activate(tabs.getItem('orthers'));" +
				"Ext.getCmp('"+id.toLowerCase()+"').focus()";
		this.getExecuteSG().addExecuteCode(js);
	}
	
	private void saveInfo_Extend(HBSession sess, String a0000) throws Exception {
		String sql = getInfoSQL();

		List<Object[]> list = sess.createSQLQuery(sql).list();
		Map<String,String> field = new LinkedHashMap<String, String>();
		if(list!=null&&list.size()>0){
			for(Object[] os : list){
				field.put(os[1].toString(), os[2].toString());
			}
		}
		sess.flush();
		
		
		List<Object> Info_Extends = sess.createSQLQuery("select a0000 from Info_Extend where a0000='"+a0000+"'").list();
		
		if(field.size()>0){//有扩展字段
			if(Info_Extends==null||Info_Extends.size()==0){//新增
				StringBuffer insert_sql = new StringBuffer("insert into Info_Extend(a0000,");
				StringBuffer values = new StringBuffer(" values('"+a0000+"',");
				Object[] args = new Object[field.size()];
				Integer index = 0;
				for(String key : field.keySet()){
					String comment = field.get(key);
					try{
						insert_sql.append(key+",");
						values.append("?,");
						args[index++] = this.getPageElement(key.toLowerCase()).getValue();
						if(DBType.ORACLE == DBUtil.getDBType()){
							HBUtil.executeUpdate("alter table Info_Extend add "+key+" varchar2(200)");
							HBUtil.executeUpdate("comment on column Info_Extend."+key+" is '"+comment+"'");
							HBUtil.executeUpdate("alter table Info_Extend_Temp add "+key+" varchar2(200)");
							HBUtil.executeUpdate("comment on column Info_Extend_Temp."+key+" is '"+comment+"'");
						}else{
							HBUtil.executeUpdate("ALTER TABLE info_extend add "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
							HBUtil.executeUpdate("ALTER TABLE Info_Extend_Temp add "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
						}
					}catch (Exception e) {
					}
				}
				insert_sql.deleteCharAt(insert_sql.length()-1).append(")");
				values.deleteCharAt(values.length()-1).append(")");
				insert_sql.append(values);
				HBUtil.executeUpdate(insert_sql.toString(), args);
			}else{//修改
				StringBuffer update_sql = new StringBuffer("update Info_Extend set ");
				Object[] args = new Object[field.size()];
				Integer index = 0;
				for(String key : field.keySet()){
					String comment = field.get(key);
					try{
						update_sql.append(key+"=?,");
						args[index++] = this.getPageElement(key.toLowerCase()).getValue();
						if(DBType.ORACLE == DBUtil.getDBType()){
							HBUtil.executeUpdate("alter table Info_Extend add "+key+" varchar2(200)");
							HBUtil.executeUpdate("comment on column Info_Extend."+key+" is '"+comment+"'");
							HBUtil.executeUpdate("alter table Info_Extend_Temp add "+key+" varchar2(200)");
							HBUtil.executeUpdate("comment on column Info_Extend_Temp."+key+" is '"+comment+"'");
						}else{
							HBUtil.executeUpdate("ALTER TABLE info_extend add "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
							HBUtil.executeUpdate("ALTER TABLE Info_Extend_Temp add "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
						}
						
					}catch (Exception e) {
					}
				}
				update_sql.deleteCharAt(update_sql.length()-1).append(" where a0000='"+a0000+"'");
				HBUtil.executeUpdate(update_sql.toString(), args);
			}
			
		}
	}

	@PageEvent("count.onclick")
	@NoRequiredValidate
	public int count() throws RadowException, AppException {
		String a1107 = this.getPageElement("a1107").getValue();// 培训开始时间
		String a1111 = this.getPageElement("a1111").getValue();// 培训结束时间
		String a1107c = this.getPageElement("a1107c").getValue();
		String a1108 = this.getPageElement("a1108").getValue();
		if (a1107 != null && !"".equals(a1107) && a1111 != null
				&& !"".equals(a1111)) {
			if (a1107.length() == 6) {
				a1107 += "01";
			}
			if (a1111.length() == 6) {
				a1111 += "01";
			}
			int start = Integer.valueOf(a1107);
			int end = Integer.valueOf(a1111);
			if (start > end) {
				this.setMainMessage("培训开始时间不能大于结束时间");
				return EventRtnType.NORMAL_SUCCESS;
			}
			int days = DateUtil.getDaysBetween(DateUtil.stringToDate(a1107,
					"yyyymmdd"), DateUtil.stringToDate(a1111, "yyyymmdd"));
			int dayA1107c = days;// 总天数
			int hour = days * 8;// 学时
			this.getPageElement("a1107c").setValue("" + dayA1107c);
			this.getPageElement("a1108").setValue("" + hour);
			
		} else {
			this.getPageElement("a1107c").setValue("");
			this.getPageElement("a1108").setValue("");
		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveTrainingInfo()throws RadowException, AppException{
		A11 a11 = new A11();
		this.copyElementsValueToObj(a11, this);
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(a11.getA1131()==null||"".equals(a11.getA1131())){
			this.setMainMessage("培训班名称不能为空");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a11.setA0000(a0000);
		String a1100 = this.getPageElement("a1100").getValue();
		String a1107c = this.getPageElement("a1107c").getValue();
		String a1108 = this.getPageElement("a1108").getValue();
		if(a1107c == null || "".equals(a1107c)){
			a11.setA1107c(null);
		}else{
			a11.setA1107c(Double.parseDouble(this.getPageElement("a1107c").getValue()));
		}
		if(a1108 == null || "".equals(a1108)){
			a11.setA1108(null);
		}else{
			a11.setA1108(Double.parseDouble(this.getPageElement("a1108").getValue()));
		}
		
		String a1107 = a11.getA1107();//培训开始时间
		String a1111 = a11.getA1111();//培训结束时间
		if(a1107!=null&&!"".equals(a1107)&&a1111!=null&&!"".equals(a1111)){
			if(a1107.length()==6){
				a1107 += "01";
			}
			if(a1111.length()==6){
				a1111 += "01";
			}
			int start = Integer.valueOf(a1107);
			int end = Integer.valueOf(a1111);
			if(start>end){
				this.setMainMessage("培训开始时间不能大于结束时间");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//计算培训有几月几天。
			int days = DateUtil.getDaysBetween(DateUtil.stringToDate(a1107, "yyyymmdd"), DateUtil.stringToDate(a1111, "yyyymmdd"));
			int mounthA1107a = days/31;//月
			int dayA1107b = days%31;//天
			a11.setA1107a((long)mounthA1107a);
			a11.setA1107b((long)dayA1107b);
		}else{
			a11.setA1107a(null);
			a11.setA1107b(null);
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			A41 a41 = new A41();
			A11 a11_old = null;
			if(a1100==null||"".equals(a1100)){
				a11_old = new A11();
				applog.createLog("3111", "A11", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a11_old,a11));
				sess.save(a11);	
				sess.flush();
				a41.setA0000(a0000);
				a41.setA1100(a11.getA1100());
				sess.save(a41);	
			}else{
				a11_old = (A11)sess.get(A11.class, a1100);
				applog.createLog("3112", "A11", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a11_old,a11));
				PropertyUtils.copyProperties(a11_old, a11);
				sess.update(a11_old);	
			}
			sess.flush();			
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1100").setValue(a11.getA1100());//保存成功将id返回到页面上。
		this.getPageElement("a1107a").setValue(a11.getA1107a()==null?"":a11.getA1107a().toString());//月
		this.getPageElement("a1107b").setValue(a11.getA1107b()==null?"":a11.getA1107b().toString());//日
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('TrainingInfoGrid').getStore().reload()");//刷新专业技术职务列表
		this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
/***********************************************培训信息A11*********************************************************************/
	
	/**
	 * 培训信息列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int trainingInforGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getRadow_parent_data();
		String sql = "select a1.* from a41 a4,A11 a1 where a4.a1100=a1.a1100 and a4.a0000='"+a0000+"'";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 培训信息新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoAddBtn.onclick")
	@NoRequiredValidate
	public int trainingInforAddBtnWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A11 a11 = new A11();
		PMPropertyCopyUtil.copyObjValueToElement(a11, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 培训信息的修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int trainingInforGridOnRowClick() throws RadowException{  
		int index = this.getPageElement("TrainingInfoGrid").getCueRowIndex();
		String a1100 = this.getPageElement("TrainingInfoGrid").getValue("a1100",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A11 a11 = (A11)sess.get(A11.class, a1100);
			PMPropertyCopyUtil.copyObjValueToElement(a11, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}	
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow()throws RadowException, AppException{
		Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a1100 = this.getPageElement("TrainingInfoGrid").getValue("a1100",index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A11 a11 = (A11)sess.get(A11.class, a1100);
			A01 a01 = (A01)sess.get(A01.class, a11.getA0000());
			applog.createLog("3113", "A11", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A11(),new A11()));
			HBUtil.executeUpdate("delete from a41 where a1100=?", new Object[]{a11.getA1100()});
			sess.delete(a11);
			this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
			a11 = new A11();
			PMPropertyCopyUtil.copyObjValueToElement(a11, this);
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	public static Map<String, List<Object[]>> getInfoExt(){
		String sql = getInfoSQL();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery(sql).list();
			Map<String, List<Object[]>> info_map = new LinkedHashMap<String, List<Object[]>>();
			if(list!=null&&list.size()>0){
				for(Object[] os : list){
					List<Object[]> os_list = info_map.get(os[0]+"___"+os[4]);
					if(os_list==null){
						os_list = new ArrayList<Object[]>();
						os_list.add(os);
						info_map.put(os[0].toString()+"___"+os[4].toString(), os_list);
					}else{
						os_list.add(os);
					}
					
				}
			}
			return info_map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String getInfoSQL(){
		return "select t.table_code, t.col_code,t.col_name,t.code_type,a.add_type_name,t.col_data_type_should " +
		" from (select ctc.table_code, ctc.col_code,ctc.col_name,ctc.code_type," +
		" ctc.col_data_type_should,ctc.is_new_code_col,av.isused,av.ADD_VALUE_SEQUENCE from code_table_col ctc " +
		" left join add_value av on ctc.col_code=av.col_code) t " +
		" left join add_type a on t.table_code=a.table_code where t.is_new_code_col='1' and t.isused='1' and t.table_code " +
		" in('A01','A02','A06','A08','A11','A14','A15','A29','A30','A31','A36','A37','A53') order by t.ADD_VALUE_SEQUENCE";
	}
}
