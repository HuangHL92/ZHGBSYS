package com.insigma.siis.local.pagemodel.publicServantManage;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;




import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.A99Z1;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.business.helperUtil.CodeType2js;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.publicServantManage.QueryPersonListBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class AddPersonPageModel extends PageModel {
	private LogUtil applog = new LogUtil();
	/**
	 * 人员基本信息保存
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int savePerson(String confirm)throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		
		
		A01 a01 = new A01();
		this.copyElementsValueToObj(a01, this);
		String a0000 = this.getPageElement("a0000").getValue();
		
		//对人员工作单位及职务信息进行判断，校验;（默认新增为现职人员）
		//现职人员：必须有一条在任主职务
		//非现职人员：有一条主职务
		//共性：必须要有一条输出职务
		
		String sqlOne = null;
		String msg = null;
		if(a01.getA0163().equals("1")){					//人员管理状态为1，现职人员
			msg = "现职人员必须有一条在任主职务！";
			sqlOne = "from A02 where a0000='"+a0000+"' and a0255='1' and a0279='1' order by a0223";//在任、主职务的职务
		}else{			//非现职人员
			msg = "非现职人员必须有一条主职务！";
			sqlOne = "from A02 where a0000='"+a0000+"' and a0279='1' order by a0223";//主职务的职务
		}
		
		
		String sqlOut = "from A02 where a0000='"+a0000+"' and a0281='true'";   //输出职务
		List<A02> listOut = sess.createQuery(sqlOut).list();
		if(listOut.size() == 0){
			this.setMainMessage("必须要有一条输出职务");
			return EventRtnType.FAILD;
		}
		
		
		List<A02> list = sess.createQuery(sqlOne).list();
		if(list.size() == 0){
			this.setMainMessage(msg);
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//参加工作时间不能小于出生年月
		String a0134 = a01.getA0134();//参加工作时间
		String a0107 = a01.getA0107();//出生年月
		if(a0134!=null&&!"".equals(a0134)&&a0107!=null&&!"".equals(a0107)){
			if(a0134.length()==6){
				a0134 += "00";
			}
			if(a0107.length()==6){
				a0107 += "00";
			}
			if(a0134.compareTo(a0107)<=0){
				this.setMainMessage("参加工作时间不能小于等于出生年月");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		//专长a0187a
		String a0187a = this.getPageElement("a0187a").getValue();
		if(a0187a != null || "".equals(a0187a)){
			if(a0187a.length() > 60){
				this.setMainMessage("专长不能超过60字！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		String tbsj = this.getPageElement("tbsj").getValue();
		/*if(IdCardManageUtil.trueOrFalseIdCard(a01.getA0184()))
			a01.setAge(Long.valueOf(IdCardManageUtil.getAge(a01.getA0184())));*/
		//HBSession sess = null;
		A01 a01_old = null;
		JSONObject jsonbject = null;
		try {
			
			
			String idcard = a01.getA0184();//身份证号 重名校验//将身份证的最后一位x转换为大写字符 add by lizs 20161110
			if(idcard!=null){
				idcard = idcard.toUpperCase();
				a01.setA0184(idcard);
			}
			String sql = "select count(1) from A01 where  a0000!='"+a0000+"' and a0184='"+idcard+"'";//and a0101='"+a01.getA0101()+"'
			Object c = sess.createSQLQuery(sql).uniqueResult();
			if(!String.valueOf(c).equals("0")){
						this.setMainMessage("系统中已存在具有相同身份证号码人员,请修改！");
						return EventRtnType.NORMAL_SUCCESS;
			}
			
			a01.setA0102(new ChineseSpelling().getPYString(a01.getA0101()));//拼音简称
			
			//对可能存在空格数据的文本进行去空处理
			a01.setA0101(this.getPageElement("a0101").getValue().replaceAll("\\s*", ""));
			a01.setComboxArea_a0114(this.getPageElement("comboxArea_a0114").getValue().replaceAll("\\s*", ""));
			a01.setA0114(this.getPageElement("a0114").getValue().replaceAll("\\s*", ""));
			a01.setQrzxlxx(this.getPageElement("qrzxlxx").getValue().replaceAll("\\s*", ""));
			a01.setQrzxwxx(this.getPageElement("qrzxwxx").getValue().replaceAll("\\s*", ""));
			a01.setZzxlxx(this.getPageElement("zzxlxx").getValue().replaceAll("\\s*", ""));
			a01.setZzxwxx(this.getPageElement("zzxwxx").getValue().replaceAll("\\s*", ""));
			
			if(a0000==null||"".equals(a0000)){
				//职务为空  1现职人员2离退人员3调出人员4已去世5其他人员
				a01.setA0163("1");
				this.getPageElement("a0163").setValue("1");
				sess.save(a01);	
			}else{
				a01_old = (A01)sess.get(A01.class, a0000);
				if("4".equals(a01.getStatus())){//如果是临时数据，保存时状态设为现职人员  日志记为新增
					a01.setStatus("1");
					String sql2 = "select t.a0201b from a02 t where t.a0000 = '"+a0000+"'";
					List<String> list2 = sess.createSQLQuery(sql2).list();
					for(int i=0;i<list2.size();i++){
						CreateSysOrgBS.updateB01UpdatedWithZero(list2.get(i));
					}
					this.getPageElement("status").setValue(a01.getStatus());
					//new LogUtil("31", "A01", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A01(), a01)).start();
					
					applog.createLog("31", "A01", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A01(), a01));
				}else{
					a01.setXgr(null);
					a01.setXgsj(null);
					a01_old.setXgr(null);
					a01_old.setXgsj(null);
					//new LogUtil("32", "A01", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a01_old, a01)).start();
					
					applog.createLog("32", "A01", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a01_old, a01));
					//a01.setXgr(SysManagerUtils.getUserId());
					//a01.setXgsj(DateUtil.getTimestamp().getTime());
				}
				
				
				//补充信息需要填充
				a01.setA0115a(a01_old.getA0115a());			//成长的
				a01.setA0122(a01_old.getA0122()); 			//专业技术类公务员任职资格
				a01.setA0120(a01_old.getA0120());  			//级别
				a01.setA2949(a01_old.getA2949()); 			//公务员登记时间
				a01.setA0197(a01_old.getA0197()); 			//具有两年以上基层工作经历
				
				PropertyUtils.copyProperties(a01_old, a01);
				//简历格式化
				StringBuffer originaljl = new StringBuffer("");
				String jianli = formatJL(a01_old.getA1701(),originaljl);
				jsonbject = objectToJson(a01_old);
				//a01_old.setA1701(originaljl.toString());
				this.getPageElement("a1701").setValue(jianli);
				sess.update(a01_old);	
				//this.getExecuteSG().addExecuteCode("document.getElementById('xgsj').value="+a01_old.getXgsj()+";");
			}
			
			
			
			//通过a0000查询出“补充信息集”A99Z1对象
			A99Z1 a99Z1 = new A99Z1();
			this.copyElementsValueToObj(a99Z1, this);
			
			if(a99Z1.getA99z101() == null || a99Z1.getA99z101().equals("")){		//"是否考录"如果为空，则设置为0：否
				a99Z1.setA99z101("0");
			}
			if(a99Z1.getA99z103() == null || a99Z1.getA99z103().equals("")){		//"是否选调生"如果为空，则设置为0：否
				a99Z1.setA99z103("0");
			}
			
			//判断录用时间：与出生日期进行比较，一般应大于18周岁。
			//String a0107 = a01.getA0107();//出生年月
			String a99z102 = a99Z1.getA99z102();//录用时间
			if(a0107!=null&&!"".equals(a0107)&&a99z102!=null&&!"".equals(a99z102)){
				int age = getAgeNew(a99z102,a0107);
				if(age<18){
					this.setMainMessage("录用时间与出生日期进行比较，应大于18周岁！");
					return EventRtnType.FAILD;
				}
			}
			
			//判断进入选调生时间：与出生日期进行比较，一般应大于18周岁。
			
			String a99z104 = a99Z1.getA99z104();//进入选调生时间
			if(a0107!=null&&!"".equals(a0107)&&a99z104!=null&&!"".equals(a99z104)){
				int age = getAgeNew(a99z104,a0107);
				if(age<18){
					this.setMainMessage("进入选调生时间与出生日期进行比较，应大于18周岁！");
					return EventRtnType.FAILD;
				}
			}
			
			
			a99Z1.setA0000(a0000);
			A99Z1 a99Z1_old = null;
			if("".equals(a99Z1.getA99Z100())){
				a99Z1.setA99Z100(null);
				a99Z1_old = new A99Z1();
				/*applog.createLog("3531", "A99Z1", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a99Z1_old,a99Z1));*/
				applog.createLogNew("3A99Z1","选调生、考录信息新增","选调生、考录信息集",a0000,a01.getA0101(),new Map2Temp().getLogInfo(new A99Z1(),a99Z1));
				
			}else{
				a99Z1_old = (A99Z1)sess.get(A99Z1.class, a99Z1.getA99Z100());
				/*applog.createLog("3532", "A53", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a99Z1_old,a99Z1));*/
				applog.createLogNew("3A99Z1","选调生、考录信息修改","选调生、考录信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(a99Z1_old,a99Z1));
			}
			PropertyUtils.copyProperties(a99Z1_old, a99Z1);
			
			sess.saveOrUpdate(a99Z1_old);
			this.getPageElement("a99Z100").setValue(a99Z1_old.getA99Z100());
			
			
			
			
			//新增修改保存时父页面设置人员内码参数。更新title
			this.getExecuteSG().addExecuteCode("radow.doEvent('tabClick','"+a01.getA0000()+"');");
			a0000 = a01.getA0000();
			Integer rowLength = Integer.valueOf(this.getPageElement("rowLength").getValue());//行数
			StringBuffer sb = new StringBuffer("{");
			for(int i=1;i<=rowLength;i++){
				A36 a36 = new A36();
				String a3600 = this.getPageElement("a3600_"+i).getValue();
				String a3604a = this.getPageElement("a3604a_"+i+"_combo").getValue();		//称谓名称
				String a3601 = this.getPageElement("a3601_"+i).getValue().replaceAll("\\s*", "");//家庭成员姓名去空 
				String a3607 = this.getPageElement("a3607_"+i).getValue();
				String a3627 = this.getPageElement("a3627_"+i+"_combo").getValue();			//政治面貌id
				String a3611 = this.getPageElement("a3611_"+i).getValue().replaceAll("\\s*", "");//家庭成员工作去空 
				
				//if(a3600 != null && !a3600.equals("")){
				if((a3604a != null && !a3604a.equals("")) || (a3601 != null && !a3601.equals("")) || (a3607 != null && !a3607.equals(""))
						|| (a3627 != null && !a3627.equals("")) || (a3611 != null && !a3611.equals(""))){
					
					//信息验证
					if(a3604a == null || "".equals(a3604a)){
						this.getExecuteSG().addExecuteCode("parent.$h.alert('系统提示','家庭成员称谓不能为空！',null,220);");
						//this.setMainMessage("家庭成员称谓不能为空！");
						return EventRtnType.NORMAL_SUCCESS;
					}
					if(a3601 == null || "".equals(a3601)){
						this.getExecuteSG().addExecuteCode("parent.$h.alert('系统提示','家庭成员姓名不能为空！',null,220);");
						//this.setMainMessage("家庭成员姓名不能为空！");
						return EventRtnType.NORMAL_SUCCESS;
					}
					/*if(a3607 == null || "".equals(a3607)){
						this.getExecuteSG().addExecuteCode("parent.$h.alert('系统提示','家庭成员出生年月不能为空！',null,220);");
						//this.setMainMessage("家庭成员出生年月不能为空！");
						return EventRtnType.NORMAL_SUCCESS;
					}*/
					if(a3627 == null || "".equals(a3627)){
						this.getExecuteSG().addExecuteCode("parent.$h.alert('系统提示','家庭成员政治面貌不能为空！',null,220);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					if(a3611 == null || "".equals(a3611)){
						this.getExecuteSG().addExecuteCode("parent.$h.alert('系统提示','家庭成员工作单位及职务不能为空！',null,220);");
						//this.setMainMessage("家庭成员工作单位及职务不能为空！");
						return EventRtnType.NORMAL_SUCCESS;
					}
					
				}
				
				sb
				.append("a3604a_"+i+":'"+a3604a+"',")
				.append("a3601_"+i+":'"+a3601+"',")
				.append("a3607_"+i+":'"+a3607+"',")
				.append("a3627_"+i+":'"+a3627+"',")
				.append("a3611_"+i+":'"+a3611+"',");
				a36.setA0000(a0000);
				
				
				//全部信息去除，则删除
				if((a3604a==null||"".equals(a3604a))&&
						(a3601==null||"".equals(a3601))&&
						(a3607==null||"".equals(a3607))&&
						(a3627==null||"".equals(a3627))&&
										(a3611==null||"".equals(a3611))){
					sb.append("a3600_"+i+":'',");
					this.getPageElement("a3600_"+i).setValue("");
					if(a3600==null||"".equals(a3600)){
						
						continue;
					}else{
						a36.setA3600(a3600);
						//new LogUtil("3363", "A36", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A36(), new A36())).start();
						applog.createLog("3363", "A36", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A36(), new A36()));
						sess.delete(a36);
						continue;
					}
					
				}
				
				a36.setA3604a(a3604a);
				a36.setA3601(a3601);
				a36.setA3607(a3607);
				a36.setA3627(a3627);
				a36.setA3611(a3611);
				a36.setSortid(BigDecimal.valueOf((long)i));
				if(a3600==null||"".equals(a3600)){
					a36.setA3600(null);
					sess.save(a36);
					sess.flush();
					//new LogUtil("3361", "A36", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A36(), a36)).start();
					applog.createLog("3361", "A36", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A36(), a36));
					this.getPageElement("a3600_"+i).setValue(a36.getA3600());
					
				}else{
					a36.setA3600(a3600);
					A36 a36_old = (A36)sess.get(A36.class, a3600);
					//new LogUtil("3362", "A36", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a36_old, a36)).start();
					applog.createLog("3362", "A36", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a36_old, a36));
					PropertyUtils.copyProperties(a36_old, a36);
					sess.update(a36_old);
				}
				sb.append("a3600_"+i+":'"+a36.getA3600()+"',");
			}
			for(int i=rowLength+1;i<=30;i++){
				sb.append("a3600_"+i+":'',")
				.append("a3604a_"+i+":'',")
				.append("a3601_"+i+":'',")
				.append("a3607_"+i+":'',")
				.append("a3627_"+i+":'',")
				.append("a3611_"+i+":'',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("}");
			sess.flush();			
			//旧值 判断是否修改
			
			
			// 家庭成员
			String sqla36 = "from A36 where a0000='"+a0000+"' order by SORTID";
			List lista36 = sess.createQuery(sqla36).list();
			Integer rowLength2 = lista36.size();//行数
			
			//赋值到页面
			this.getPageElement("total").setValue(rowLength2.toString());
			
			
			String json = jsonbject.toString();
			this.getExecuteSG().addExecuteCode("A01value="+json+";A36value="+sb+";");
			//this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("任免表信息保存失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a0000").setValue(a01_old.getA0000());//保存成功将id返回到页面上。
		this.getPageElement("age").setValue(a01_old.getAge()+"");
		//this.getPageElement("xgr").setValue(a01_old.getXgr());
		//this.getExecuteSG().addExecuteCode("window.parent.parent.Ext.getCmp('persongrid').getStore().reload();");//刷新人员列表
		this.setRadow_parent_data(a01_old.getA0000());
		//判断父页面是否是人员信息维护列表页面，是 则重新加载列表
		this.getExecuteSG().addExecuteCode("try{" +
				"if(parent.document.getElementById('I'+thisTab.initialConfig.personListTabId)){" +
				"	var personListWindow = parent.document.getElementById('I'+thisTab.initialConfig.personListTabId).contentWindow;" +
				"	personListWindow.Ext.getCmp('persongrid').store.reload();}" +
				"}catch(e){} "
				);
		
		this.getExecuteSG().addExecuteCode(" if(document.getElementById('Iorthers')){"+
					"var orthersWindow = document.getElementById('Iorthers').contentWindow;"+
					"if(orthersWindow){"+
					"	orthersWindow.radow.doEvent('saveOthers.onclick','"+confirm+"');"+
					"}else{" +
					"	if('true'=='"+confirm+"'){" +
							"window.parent.tabs.remove(thisTab.tabid);" +
						"}else{" +
							"$h.alert('系统提示','保存成功!');" +
						"}" +
					"" +
					"}"+
				"}else if(document.getElementById('IBusinessInfo')){"+
					"var BusinessInfoWindow = document.getElementById('IBusinessInfo').contentWindow;"+
					"if(BusinessInfoWindow){"+
					"	BusinessInfoWindow.radow.doEvent('save.onclick','"+confirm+"');"+
					"}else{" +
					"	if('true'=='"+confirm+"'){" +
							"window.parent.tabs.remove(thisTab.tabid);" +
						"}else{" +
							"$h.alert('系统提示','保存成功!');" +
						"}" +
					"" +
					"}"+
				"}else{" +
				"	if('true'=='"+confirm+"'){" +
					"window.parent.tabs.remove(thisTab.tabid);" +
					"}else{" +
						"parent.$h.alert('系统提示','任免表信息保存成功!',null,220);" +
					"}" +
				"}");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("printFalse")
	public int printFalse(String a0000)throws AppException, RadowException, IOException{
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null || StringUtil.isEmpty(a01.getA0184())){
			this.setMainMessage("请先保存人员信息");
			return EventRtnType.NORMAL_SUCCESS;	
		}
		pdfView2(a0000+",false");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * 打开PDF预览界面
	 * 
	 * @param a0000AndFlag a0000 （人员ID）与 flag（是否打印拟任免信息）拼接的参数，用逗号分隔
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @author mengl
	 * @throws IOException 
	 * @date 2016-06-03
	 */
	@PageEvent("printView")
	public int pdfView2(String a0000AndFlag) throws RadowException, AppException, IOException{
		String[] params = a0000AndFlag.split(",");
		String a0000 = params[0]; 										//人员ID
		Boolean flag = params[1].equalsIgnoreCase("true")?true:false;  	//是否打印拟任免信息
		String pdfPath = "";  											//pdf文件路径
		
		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		list.add(a0000);
		List<String> pdfPaths = null;
		try {
			pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","pdf",a0000,list2, params);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String newPDFPath = ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".Pdf";
		QueryPersonListBS.mergePdfs(pdfPaths,newPDFPath);
		newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
		newPDFPath = "/hzb"+ newPDFPath;
		this.getPageElement("pdfPath").setValue(newPDFPath);
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','打印任免表',700,500,1,'"+ctxPath+"')");
		/*List<String> pdfPaths = new QueryPersonListBS().getPdfsByA000s(list,flag);
		
		pdfPath = pdfPaths.get(0);
		pdfPath = pdfPath.substring(pdfPath.indexOf("ziploud")-1).replace("\\", "/");
		pdfPath = "/hzb"+pdfPath;
//		pdfPath = pdfPath.substring(pdfPath.indexOf("insiis6")-1).replace("\\", "/");
//		String contextStr = this.request.getContextPath().replace("/", "\\");
//		pdfPath = pdfPath.substring(pdfPath.indexOf(contextStr)).replace("\\", "/");
		
		//this.setRadow_parent_data(pdfPath);
		//this.openWindow("pdfViewWin", "pages.publicServantManage.PdfView");
		this.getPageElement("pdfPath").setValue(pdfPath);
		this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','打印任免表',700,500,'"+a0000+"',ctxPath)");
		//this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','任免表预览界面','"+pdfPath+"')");
	*/	return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 人员其它信息保存 废弃
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveOthers.onclick")
	@Transaction
	@Synchronous(true)
	public int saveOthers()throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			//进入管理保存 id生成方式为assigned
			A29 a29 = new A29();
			this.copyElementsValueToObj(a29, this);
			sess.saveOrUpdate(a29);	
			
			//拟任免保存	id生成方式为uuid 。 如果是新增 将id设置为null
			A53 a53 = new A53();
			this.copyElementsValueToObj(a53, this);
			if("".equals(a53.getA5300())){
				a53.setA5300(null);
			}
			sess.saveOrUpdate(a53);
			this.getPageElement("a5300").setValue(a53.getA5300());
			
			//住址通讯保存 id生成方式为assigned
			A37 a37 = new A37();
			this.copyElementsValueToObj(a37, this);
			sess.saveOrUpdate(a37);	
			
			//离退保存 id生成方式为assigned
			A31 a31 = new A31();
			this.copyElementsValueToObj(a31, this);
			sess.saveOrUpdate(a31);	
			
			//退出管理保存 id生成方式为assigned
			A30 a30 = new A30();
			this.copyElementsValueToObj(a30, this);
			sess.saveOrUpdate(a30);	
			sess.flush();
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('persongrid').getStore().reload()");//刷新人员列表
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * a0184身份证号验证
	 * 
	 */
	//@PageEvent("a0184.onblur")
	@NoRequiredValidate
	public int a0184onblur(String v)throws RadowException, AppException{
		String idcardno = this.getPageElement("a0184").getValue().toUpperCase();//将身份证的最后一位x转换为大写字符 add by lizs 20161110
		if(idcardno==null || idcardno.equals("")){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!IdCardManageUtil.trueOrFalseIdCard(idcardno)){
			this.setMainMessage("身份证格式有误");
		}else{
			this.getPageElement("a0107").setValue(IdCardManageUtil.getBirthdayFromIdCard(idcardno));//出身年月
			this.getPageElement("a0104").setValue(IdCardManageUtil.getSexFromIdCard(idcardno));//性别
			
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private static void parseJL(String line2, StringBuffer jlsb, boolean isStart){
		int llength = line2.length();//总长
		//25个字一行。
		int oneline = 100000000;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//不足 50个字节往后偏移，直到足够为止。
    		boolean hass = false;
    		while((end+offset)<llength){//25个字一行，换行符分割
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//25个字一行但不足50个字节 往右移
    				loffset++;
    				if((end+offset+loffset)>llength){//超过总长度 退出循环
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//可能会出现一行51个字节，往前退一格。
    					loffset--;
    					l = line2.substring(oneline*j+offset,end+offset+loffset);
    					break;
    				}
    			}
    			offset = offset+loffset;
    			if(isStart&&!hass){
    				//jlsb.append(l);
    				jlsb.append(l).append("\r\n");
    				hass = true;
    			}else{
    				//jlsb.append("                  ").append(l);
    				jlsb.append("                  ").append(l).append("\r\n");
    			}
    			
    			j++;
    		}
    	}else{
    		if(isStart){
    			//jlsb.append(line2);
    			jlsb.append(line2).append("\r\n");
    		}else{
    			//jlsb.append("                  ").append(line2);
    			jlsb.append("                  ").append(line2).append("\r\n");
    		}
    	}
	}
	
	@Override
	@Transaction
	public int doInit() throws RadowException {
		
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){
			a0000 = (String)this.request.getSession().getAttribute("a0000");
		}
		if(a0000==null||"".equals(a0000)||"add".equals(a0000)){//打开新增页面，检查是否有人员内码，如果有则是新增，否则是修改。
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//通过"nowNumber"是否有值，来判断 人员上下翻页 按钮是否显示
		Object obj = this.request.getSession().getAttribute("nowNumber");
		if(obj==null || "".equals(obj.toString())){
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('lastp').hide();odin.ext.getCmp('nextp').hide();");
		}
		
		try {
			HBSession sess = HBUtil.getHBSession();
			
			String sql = "from A01 where a0000='"+a0000+"'";
			List list = sess.createQuery(sql).list();
			A01 a01 = (A01) list.get(0);	
			
			//判断是否有修改权限。c.type：机构权限类型(0：浏览，1：维护)
			String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='0' ";
			String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
			" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
			" and c.type='1' ";
			List elist = sess.createSQLQuery(editableSQL).list();		//是否有浏览权限，有值则表示：有浏览权限(目前根据权限的设计，浏览权限判断无意义，以下程序将不对他进行判断，2017-9-20)
			List elist2 = sess.createSQLQuery(editableSQL2).list();		//是否有维护权限，有值则表示：有维护权限
/*			判断该人员的管理类别浏览权限------------------------------------------------------------------------------------------------------*/
			String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
			if(type == null || !type.contains("'")){			//如果type为空，则表示当前用户有：管理类别维护权限
				type ="'zz'";//替换垃圾数据，让下面的elist3查询无结果，表示此用户有当前操作人员的管理类别维护权限
			}
			List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
			if(elist3.size()>0){//无管理类别维护权限,即人员信息不可编辑
				this.getExecuteSG().addExecuteCode("buttonDisabled=true;$h.setDisabled($h.disabledButtons.a01);"
						+ "document.getElementById('isUpdate').value = '2'"
						);
			}
			if(elist2==null||elist2.size()==0){//无维护权限
				/*if(elist!=null&&elist.size()>0){//有浏览权限
					this.getExecuteSG().addExecuteCode("buttonDisabled=true;$h.setDisabled($h.disabledButtons.a01);"
							+ "document.getElementById('isUpdate').value = '2'"
							);
				}else{
					//有两种情况：非现职人员，其他现职人员。非现职人员只能查看，不能编辑；其他现职人员可查看，可编辑
					if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//非现职人员
						this.getExecuteSG().addExecuteCode("buttonDisabled=true;$h.setDisabled($h.disabledButtons.a01);"
								+ "document.getElementById('isUpdate').value = '2'"
								);
					}
					
				}*/
			}
			
			
				
			//处理简历格式
			String a1701 = a01.getA1701();//简历
			this.getPageElement("jlnr").setValue(a1701);
			a01.setA1701(formatJL(a1701,new StringBuffer("")));
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
			JSONObject jsonbject = objectToJson(a01);
			a01.setA1701(a1701);
			sess.update(a01);
			if(a01.getA0195()!=null){
				String a0195 = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");
				if(a0195!=null){
					this.getPageElement("a0195_combo").setValue(a0195);//机构名称 中文。
				}
			}
			
			this.getPageElement("comboxArea_a0114").setValue(a01.getComboxArea_a0114());
			//照片
			this.getExecuteSG().addExecuteCode("document.getElementById('personImg').src='"+this.request.getContextPath()+"/servlet/DownloadUserHeadImage?a0000="+a0000+"'");
			A57 a57 = (A57)sess.get(A57.class, a0000);
			if(a57!=null&&a57.getPhotopath()!=null&&!"".equals(a57.getPhotopath())){
				//Blob img = a57.getPhotodata();
				//InputStream inStream = img.getBinaryStream();
				String photourl = a57.getPhotopath();
				File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
				if(fileF.isFile()){
					long nLen = fileF.length();
					String imageSize = nLen/1024 + "K";
					this.getExecuteSG().addExecuteCode("document.getElementById('personImg').alt='照片("+imageSize+")'");
				}
			}
			
			//A99Z1中的信息项
			String sqlA99Z1 = "from A99Z1 where a0000='" + a0000 + "'";
			List listA99Z1 = sess.createQuery(sqlA99Z1).list();
			if (listA99Z1 != null && listA99Z1.size() > 0) {
				A99Z1 a99Z1 = (A99Z1) listA99Z1.get(0);
				PMPropertyCopyUtil.copyObjValueToElement(a99Z1, this);		//数据赋值到页面
			}
			
			
			// 家庭成员
			String sqla36 = "from A36 where a0000='"+a0000+"' order by SORTID";
			List lista36 = sess.createQuery(sqla36).list();
			
			Integer rowLength = lista36.size();//行数
			
			
			//赋值到页面
			//this.getExecuteSG().addExecuteCode("document.getElementById('Total').alt='照片("+imageSize+")'");
			this.getPageElement("total").setValue(rowLength.toString());
			
			if(rowLength>10){
				for(int i=0;i<rowLength-10;i++){
					this.getExecuteSG().addExecuteCode("addA36row();");
				}
			}
			StringBuffer sb = new StringBuffer("{");
			if(lista36!=null&&rowLength>0){
				for(int i=1;i<=rowLength;i++){
					A36 a36 = (A36) lista36.get(i-1);		
					this.getPageElement("a3600_"+i).setValue(a36.getA3600());
					this.getPageElement("a3604a_"+i).setValue(a36.getA3604a());
					this.getPageElement("a3601_"+i).setValue(a36.getA3601());
					this.getPageElement("a3607_"+i).setValue(a36.getA3607());
					
					//处理显示时间
					String showTime = a36.getA3607();
					if(showTime != null && !showTime.equals("") && showTime.length() > 4){
						showTime = a36.getA3607().substring(0,4) + "." + a36.getA3607().substring(4,6);
					}
					this.getPageElement("a3607_"+i+"F").setValue(showTime);
					
					this.getPageElement("a3627_"+i).setValue(a36.getA3627());
					this.getPageElement("a3611_"+i).setValue(a36.getA3611());
					//this.getExecuteSG().addExecuteCode("a3607_"+i+"onblur();");
					sb.append("a3600_"+i+":'"+a36.getA3600()+"',")
					.append("a3604a_"+i+":'"+a36.getA3604a()+"',")
					.append("a3601_"+i+":'"+a36.getA3601()+"',")
					.append("a3607_"+i+":'"+a36.getA3607()+"',")
					.append("a3607_"+i+"F:'"+a36.getA3607()+"',")
					.append("a3627_"+i+":'"+a36.getA3627()+"',")
					.append("a3611_"+i+":'"+a36.getA3611()+"',");
				}
			}
			
			for(int i=rowLength+1;i<=30;i++){
			
				this.getPageElement("a3600_"+i).setValue("");
				this.getPageElement("a3604a_"+i).setValue("");
				this.getPageElement("a3601_"+i).setValue("");
				this.getPageElement("a3607_"+i).setValue("");
				this.getPageElement("a3607_"+i+"F").setValue("");
				this.getPageElement("a3627_"+i).setValue("");
				this.getPageElement("a3611_"+i).setValue("");
				
				
				sb.append("a3600_"+i+":'',")
				.append("a3604a_"+i+":'',")
				.append("a3601_"+i+":'',")
				.append("a3607_"+i+":'',")
				.append("a3607_"+i+"F:'',")
				.append("a3627_"+i+":'',")
				.append("a3611_"+i+":'',");
			}
			
			sb.deleteCharAt(sb.length()-1);
			sb.append("}");
			genResume();
			
			//旧值 判断是否修改
			
			String json = jsonbject.toString();
			this.getExecuteSG().addExecuteCode("A01value="+json+";A36value="+sb+";");
			this.getExecuteSG().addExecuteCode("document.getElementById('a0163Divid').innerHTML='"+(HBUtil.getCodeName("ZB126", (a01.getA0163()==null?"":a01.getA0163())))+"';" );
			
			//生成日志记录
			LogMain logmain = new LogMain();
			logmain.setSystemlogid(UUID.randomUUID().toString().trim().replaceAll("-", "")); 							//主键id
			logmain.setUserlog(SysManagerUtils.getUserName()); 	//操作用户（此处为用户登录名）。
			logmain.setSystemoperatedate(new Date()); 			//系统操作时间
			logmain.setEventtype("进入人员信息录入页面"); 		//操作
			logmain.setEventobject("进入个人信息"); 				//操作项类型
			logmain.setObjectid(a0000); 						//操作涉及对象编码
			logmain.setObjectname(a01.getA0101());   			//当前操作所涉及对象的名称
			sess.save(logmain);
			sess.flush();
			
			
			//如果统计所在单位为-1，则页面不显示
			if(a01.getA0195() != null && !a01.getA0195().equals("") && a01.getA0195().equals("-1")){	
				
				((Combo)this.getPageElement("a0195")).setValue("");
				this.getExecuteSG().addExecuteCode("document.getElementById('a0195').value='';"
						+ "document.getElementById('a0195_combo').value='';"
						);
				
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		
		
		
		this.getExecuteSG().addExecuteCode("bhua();");
		
		this.setRadow_parent_data(a0000);
		this.getExecuteSG().addExecuteCode("selectchange();" +
				"var orthersWindow = document.getElementById('Iorthers');"
				+ "setVisiable();" +
				"if(orthersWindow){" +
					"orthersWindow.contentWindow.radow.doEvent('doInit');" +
				"}");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("compose")
	@NoRequiredValidate
	public int compose() throws RadowException{
		String a1701 = this.getPageElement("a1701").getValue();
		if(a1701!=null&&!"".equals(a1701)){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}"); 
				Matcher matcher = pattern.matcher(jl);
				if(matcher.find()){
					if(i==0){
						jlsb.append(jl);
					}else{
						jlsb.append("\r\n").append(jl);
					}
				}else if(i==jianli.length-1){
					jlsb.append("\r\n").append(jl);
				}else{
					jlsb.append(jl);
				} 
			}
			this.getPageElement("a1701").setValue(jlsb.toString());
		}
			
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public static String formatJL(String a1701,StringBuffer originaljl) {
		if(a1701!=null&&!"".equals(a1701)){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}[ ]{2}");     
		        Matcher matcher = pattern.matcher(jl);     
		        if (matcher.find()) {
		        	String line1 = matcher.group(0);  
		        	int index = jl.indexOf(line1);
		        	if(index==0){//以日期开头  (一段)
		        		jlsb.append(line1);
		        		String line2 = jl.substring(line1.length()).trim();
			        	parseJL(line2, jlsb,true);
			        	//originaljl.append(jl);
			        	originaljl.append(jl).append("\r\n");//一段简历结束拼上回车
		        	}else{
		        		parseJL(jl, jlsb,false);
		        		if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
			        		originaljl.delete(originaljl.length()-2, originaljl.length());
			        	}
		        		originaljl.append(jl).append("\r\n");
		        	}
		        }else{
		        	parseJL(jl, jlsb,false);
		        	if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
		        		originaljl.delete(originaljl.length()-2, originaljl.length());
		        	}
		        	originaljl.append(jl).append("\r\n");
		        }
			}
			if(jlsb.lastIndexOf("\r\n")==jlsb.length()-2 ){
				jlsb.delete(jlsb.length()-2, jlsb.length());
        	}
			return jlsb.toString();
			
		}
		return a1701;
	}



	/**
	 * 生成简历
	 */
	@PageEvent("genResume")
	@NoRequiredValidate
	public int genResume() throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null){
			a0000 = (String)this.request.getSession().getAttribute("a0000");
		}
		//自动生成简历
		try {
			HBSession sess = HBUtil.getHBSession();
			String sqlA02 = "from A02 where a0000='"+a0000+"'";
			List<A02> listA02 = sess.createQuery(sqlA02).list();
			Collections.sort(listA02, new Comparator<A02>(){
				@Override
				public int compare(A02 o1, A02 o2) {
					String o1sj = o1.getA0243()==null?"":o1.getA0243();//任职时间
					String o2sj = o2.getA0243()==null?"":o2.getA0243();//任职时间
					if("".equals(o1sj)){
						return -1;
					}else if("".equals(o2sj)){
						return 1;
					}else{
						if(o1sj.length()>=6){
							String d1 = o1sj.substring(0,6);
							String d2 = o2sj.substring(0,6);
							return d1.compareTo(d2);
						}
						
					}
					return 0;
				}
			});
			StringBuffer sb = new StringBuffer("");
			if(listA02!=null&&listA02.size()>0){
				
				for(int i=0;i<listA02.size();i++){
					A02 a02 = listA02.get(i);
					A02 a02Next = new A02();
					if(listA02.size()>i+1){
						a02Next = listA02.get(i+1);
					}
					String a0201a = a02.getA0201a()==null?"":a02.getA0201a();//任职机构
					//String a03015 = a02.getA0216a()==null?"":a02.getA0216a();//职务名称
					String a03015 = a02.getA0215a()==null?"":a02.getA0215a();	//职务名称
					
					String a0203 = a02.getA0243()==null?"":a02.getA0243();//任职时间
					String a0255 = a02.getA0255()==null?"":a02.getA0255();//任职状态
					String a0265 = a02.getA0265()==null?"":a02.getA0265();//免职时间
					
					B01 b01 = null;
					if(a02.getA0201b()!=null){
						b01 = (B01)sess.get(B01.class, a02.getA0201b());
					}
					
					if(b01 != null){//机构拼接规则 与 工作单位与职务全称 机构 拼接一致
						String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
						if("2".equals(b0194)){//2—内设机构
							while(true){
								b01 = (B01)sess.get(B01.class, b01.getB0121());
								if(b01==null){
									break;
								}else{
									b0194 = b01.getB0194();
									if("2".equals(b0194)){//2—内设机构
										a0201a = b01.getB0101()+a0201a;
									}else if("3".equals(b0194)){//3—机构分组
										continue;
									}else if("1".equals(b0194)){//1—法人单位
										a0201a = b01.getB0101()+a0201a;
										break;
									}else{
										break;
									}
								}
							}
						}
					}
					
					
					
					String a0203Next = null;//a02Next.getA0243()==null?"":a02Next.getA0243();//任职时间
					if("1".equals(a0255)){//在任
						a0203Next = "";
					}else{
						a0203Next = a0265;
					}
					if("".equals(a0203)){
						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					}else{
						if(a0203.length()>=6){
							String year = a0203.substring(0,4);
							String month = a0203.substring(4,6);
							sb.append(year+"."+month);
						}
					}
					sb.append("--");
					if("".equals(a0203Next)){
						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					}else{
						if(a0203Next.length()>=6){
							String year = a0203Next.substring(0,4);
							String month = a0203Next.substring(4,6);
							sb.append(year+"."+month);
						}
					}
					sb.append("&nbsp;&nbsp;"+a0201a+a03015+"<br/>");
				}
			}
			
			this.getExecuteSG().addExecuteCode("document.getElementById('contenttext2').innerHTML='"+sb.toString()+"'");
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	 /**
     * 将实体POJO转化为JSON
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static<T> JSONObject objectToJson(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();  
        // Convert object to JSON string  
        String jsonStr = "";
        try {
             jsonStr =  mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return new JSONObject(jsonStr);
    }
    
	@PageEvent("codetype2js.onclick")
	@Transaction
	@Synchronous(true)
	public int codetype2js()throws RadowException, AppException{
		CodeType2js.getCodeTypeJS();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 专业技术职务窗口
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	//@PageEvent("a0196.onclick")
	@NoRequiredValidate
	public int a0196ondbclick()throws RadowException, AppException{
		this.setRadow_parent_data(this.getPageElement("a0000").getValue());
		this.openWindow("professSkill", "pages.publicServantManage.ProfessSkillAddPage");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * 新增保存时设置人员内码。
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("tabClick")
	@NoRequiredValidate
	public int tabClick(String id) throws RadowException {
		String tabid = id;
		String a0000check = (String)this.request.getSession().getAttribute("a0000");//第一次打开人员信息页面
		String a0000 = id;
		
		try {
			HBSession sess = HBUtil.getHBSession();
			if(a0000check==null||"".equals(a0000check)){
				//deletePerson(sess);
			}
			if(a0000==null||"".equals(a0000)||"add".equals(a0000)){
				this.setMainMessage("查询失败！");
				return EventRtnType.FAILD;
			}
			A01 a01 = null;
			if(a0000.indexOf("addTab-")!=-1){//打开新增页面，检查是否有人员内码，如果有则是新增，否则是修改。
				//this.getExecuteSG().addExecuteCode("window.parent.win_addwin.setTitle('人员新增窗口');");
				//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('"+a0000+"').setTitle('人员新增窗口');");
				a0000 = a0000.split("addTab-")[1];
				a01 = (A01)sess.get(A01.class, a0000);
				if(a01==null){
					a01 = new A01();
					a01.setA0000(a0000);
					a01.setA0163("1");//默认现职人员
					//a01.setA0104("1");//默认男
					a01.setA14z101("无");//奖惩描述
					a01.setStatus("4");
					a01.setA0197("0");//基层工作经历时间两年以上
					addUserInfo(a01);
					sess.save(a01);
					sess.flush();
					this.request.getSession().setAttribute("a0000", a0000);
					this.getExecuteSG().addExecuteCode("thisTab.initialConfig.personid='"+a0000+"';");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			//更改窗口名称updateWin.setTitle(title);
			if(a01==null){
				a01 = (A01)sess.get(A01.class, a0000);
			}
			//姓名 性别 年龄
			String a0101 = a01.getA0101()==null?"":a01.getA0101();//姓名
			//String a0184 = a01.getA0184().toUpperCase();//身份证号//将身份证的最后一位x转换为大写字符 add by lizs 20161110
			String a0107 = a01.getA0107();//出生日期
			String sex = HBUtil.getCodeName("GB2261", a01.getA0104());
			String age = "";
			int agei = 0;
			/*if(IdCardManageUtil.trueOrFalseIdCard(a0184)){
				age = IdCardManageUtil.getAge(a0184)+"";
			}*/
			if((agei = IdCardManageUtil.getAgefrombirth(a0107))!=-1){
				age = agei + "";
			}
			String title = a0101 + "，" + sex + "，" + age+"岁";
			this.getExecuteSG().addExecuteCode("window.parent.tabs.getItem(thisTab.initialConfig.tabid).setTitle('"+title.replaceAll("<", "&lt;").replaceAll("'", "&acute;")+"');");
		} catch (Exception e) {
			this.setRadow_parent_data("");
			this.request.getSession().setAttribute("a0000", "");
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.setRadow_parent_data(a0000);
		//this.createPageElement("a0000n", ElementType.NORMAL, true).setValue(a0000);
		this.request.getSession().setAttribute("a0000", a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void addUserInfo(A01 a01) {
		a01.setTbr(SysManagerUtils.getUserId());
		//a01.setTbrjg(SysManagerUtils.getUserOrgid());
		a01.setTbsj(DateUtil.getTimestamp().getTime());
		a01.setA0155(DateUtil.getTimestamp().toString());
		a01.setA0128("健康");
	}

	private void deletePerson(HBSession sess) throws Exception {
		String userid = SysManagerUtils.getUserId();
		HBUtil.executeUpdate("delete from a02  where exists (select a0000 from a01 t where t.a0000=a02.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a06  where exists (select a0000 from a01 t where t.a0000=a06.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a08  where exists (select a0000 from a01 t where t.a0000=a08.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a14  where exists (select a0000 from a01 t where t.a0000=a14.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a15  where exists (select a0000 from a01 t where t.a0000=a15.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a36  where exists (select a0000 from a01 t where t.a0000=a36.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		
		HBUtil.executeUpdate("delete from a11  where exists (select a1100 from a41 t where a11.a1100=t.a1100 and exists (select a0000 from a01 t1 where t1.a0000=t.a0000 and t1.status='4' and t1.tbr=? )) ",new Object[]{userid});
		HBUtil.executeUpdate("delete from a41  where exists (select a0000 from a01 t where t.a0000=a41.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		
		
		
		HBUtil.executeUpdate("delete from a29  where exists (select a0000 from a01 t where t.a0000=a29.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a53  where exists (select a0000 from a01 t where t.a0000=a53.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a37  where exists (select a0000 from a01 t where t.a0000=a37.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a31  where exists (select a0000 from a01 t where t.a0000=a31.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a30  where exists (select a0000 from a01 t where t.a0000=a30.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a57  where exists (select a0000 from a01 t where t.a0000=a57.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});

		HBUtil.executeUpdate("delete from a01 where status='4' and tbr='"+userid+"' ");
		sess.flush();
	}
	
	
	//暂时不用
	@PageEvent("isChange")
	@NoRequiredValidate
	public int isChange(String id) throws RadowException {
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			//this.setMainMessage("当前信息已经修改,是否需要保存");
			//this.setMessageType(EventMessageType.QUESTION);
			//addNextBackFunc(NextEventValue.YES, "alert();");
			this.getExecuteSG().addExecuteCode("$h.confirm3btn('系统提示','当前信息已经修改,是否需要保存',200,function(id){" +
				"if(id=='yes'){" +
					"	" +
					"}else if(id=='no'){" +
					"	window.parent.tabs.remove('"+id+"');" +
					"}else if(id=='cancel'){" +
					"	" +
					"}" +
				"});");
		} catch (Exception e) {
			e.printStackTrace();
			//this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 打开PDF预览界面
	 * 
	 * @param a0000AndFlag a0000 （人员ID）与 flag（是否打印拟任免信息）拼接的参数，用逗号分隔
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @author mengl
	 * @date 2016-06-03
	 */
	@PageEvent("exportLrmBtnNrm.onclick")
	@NoRequiredValidate
	public int pdfViewNrmBefore() throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null || StringUtil.isEmpty(a01.getA0184())){
			throw new AppException("请先保存人员信息！");
		}
		pdfView("true");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("exportLrmBtn.onclick")
	@NoRequiredValidate
	public int pdfViewBefore() throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null || StringUtil.isEmpty(a01.getA0184())){
			throw new AppException("请先保存人员信息！");
		}
		pdfView("false");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("printView")
	public int pdfView(String nrmFlag) throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		Boolean flag = nrmFlag.equalsIgnoreCase("true")?true:false;  	//是否打印拟任免信息
		
		String pdfPath = "";  											//pdf文件路径
		
		List<String> list = new ArrayList<String>();
		list.add(a0000);
		
		List<String> pdfPaths = new QueryPersonListBS().getPdfsByA000s(list,flag);
		
		pdfPath = pdfPaths.get(0);
		pdfPath = pdfPath.substring(pdfPath.indexOf("ziploud")-1).replace("\\", "/");
		pdfPath = "/hzb"+pdfPath;
//		pdfPath = pdfPath.substring(pdfPath.indexOf("insiis6")-1).replace("\\", "/");
//		String contextStr = this.request.getContextPath().replace("/", "\\");
//		pdfPath = pdfPath.substring(pdfPath.indexOf(contextStr)).replace("\\", "/");
		this.getPageElement("pdfPath").setValue(pdfPath);
		
		this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','打印任免表','','',document.getElementById('a0000').value,ctxPath)");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("nameCheck")
	@NoRequiredValidate
	@AutoNoMask
	public int nameCheck(String name) throws RadowException, AppException, UnsupportedEncodingException{
		String a0000 = this.getPageElement("a0000").getValue();
		String size = HBUtil.getValueFromTab("count(1)", "A01", " a0101='"+name+"' and status!='4' and a0000!='"+a0000+"'");
		int sz = Integer.valueOf(size);
		if(sz>0){
			this.getExecuteSG().addExecuteCode("");
			String n = URLEncoder.encode(URLEncoder.encode(name, "utf8"), "utf8");
			//this.openWindow("nameCheck", "pages.publicServantManage.NameCheck&namecheck="+n);
			this.getExecuteSG().addExecuteCode("$h.openWin('nameCheck','pages.publicServantManage.NameCheck&namecheck="+ n +"','打印任免表',850,450,document.getElementById('a0000').value,ctxPath)");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("setA0221Value")
	@NoRequiredValidate
	public int setA0221Value(String text) throws RadowException{
		 ((Combo)this.getPageElement("a0221")).setValue(text);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("setA0192eValue")
	@NoRequiredValidate
	public int setA0192eValue(String text) throws RadowException{
		 ((Combo)this.getPageElement("a0192e")).setValue(text);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("setA0195Value")
	@NoRequiredValidate
	public int setA0195Value(String text) throws RadowException{
		String[] s = text.split("\\|");
		if(s != null){
			String key = s[0];
			String value = s[1];
			((Combo)this.getPageElement("a0195")).setValue(value);
			this.getExecuteSG().addExecuteCode("document.getElementById('a0195').value='"+key+"';");
		}
		 
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//检查统计关系所在单位是否为“内设机构”
	@PageEvent("a0195Change")
	@NoRequiredValidate
	public int a0195Change(String a0195) throws RadowException, AppException, UnsupportedEncodingException{
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+a0195+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();
		if(B0194 != null && B0194.equals("2")){
			((Combo)this.getPageElement("a0195")).setValue("");
			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','不可选择内设机构单位！');document.getElementById('a0195').value='';");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("setA0288Value")
	@NoRequiredValidate
	public int setA0288Value(String text) throws RadowException{
		this.getPageElement("a0288").setValue(text);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("setA0192cValue")
	@NoRequiredValidate
	public int setA0192cValue(String text) throws RadowException{
		this.getPageElement("a0192c").setValue(text);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("setA0163Value")
	@NoRequiredValidate
	public int setA0163Value(String a0163) throws AppException{
		this.getExecuteSG().addExecuteCode("document.getElementById('a0163Divid').innerHTML='"+(HBUtil.getCodeName("ZB126", (a0163==null?"":a0163)))+"';" );
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("lastp.onclick")
	@NoRequiredValidate
	public int lastp() throws RadowException{
		Map<Integer, Object> map = (Map)this.request.getSession().getAttribute("a0000Map");
		Object o = this.request.getSession().getAttribute("nowNumber");
		Integer num = 0;
		if(o == null || "".equals(o.toString()) ){
			num = (Integer)this.request.getSession().getAttribute("nowNumber2");
		}else{
			num = (Integer) this.request.getSession().getAttribute("nowNumber");
		}
		if(num-1<0){
			//throw new RadowException("已经是第一位人员！");
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','已经是第一位人员！',null,170);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String nextA0000 = (String)map.get(num-1);
		this.request.getSession().setAttribute("nowNumber",num-1);//设置num
		this.request.getSession().setAttribute("nowNumber2",num-1);
		this.request.getSession().setAttribute("a0000",nextA0000);
		tabClick(nextA0000);
		doInit();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("nextp.onclick")
	@NoRequiredValidate
	public int nextp() throws RadowException{
		Map<Integer, Object> map = (Map)this.request.getSession().getAttribute("a0000Map");
		Object o = this.request.getSession().getAttribute("nowNumber");
		Integer num = 0;
		if(o == null || "".equals(o.toString()) ){
			num = (Integer)this.request.getSession().getAttribute("nowNumber2");
		}else{
			num = (Integer) this.request.getSession().getAttribute("nowNumber");
		}
		Integer bigNumber = (Integer) this.request.getSession().getAttribute("bigNumber");
		if(num + 1 >= bigNumber){
			//throw new RadowException("已经是最后一位人员！");
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','已经是最后一位人员！',null,170);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String nextA0000 = (String)map.get(num+1);
		this.request.getSession().setAttribute("nowNumber",num+1);//设置num
		this.request.getSession().setAttribute("nowNumber2",num+1);
		this.request.getSession().setAttribute("a0000",nextA0000);
		tabClick(nextA0000);
		doInit();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveA36F")
	@NoRequiredValidate
	public int saveA36F() throws AppException, RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IntrospectionException{
		HBSession sess = HBUtil.getHBSession();
		A01 a01 = new A01();
		this.copyElementsValueToObj(a01, this);
		String a0000 = this.getPageElement("a0000").getValue();
		
		
		Integer rowLength = Integer.valueOf(this.getPageElement("rowLength").getValue());//行数
		StringBuffer sb = new StringBuffer("{");
		for(int i=1;i<=rowLength;i++){
			A36 a36 = new A36();
			String a3600 = this.getPageElement("a3600_"+i).getValue();
			String a3604a = this.getPageElement("a3604a_"+i).getValue();
			String a3601 = this.getPageElement("a3601_"+i).getValue().replaceAll("\\s*", "");//家庭成员姓名去空 
			String a3607 = this.getPageElement("a3607_"+i).getValue();
			String a3627 = this.getPageElement("a3627_"+i).getValue();
			String a3611 = this.getPageElement("a3611_"+i).getValue().replaceAll("\\s*", "");//家庭成员工作去空 
			
			if(a3600 != null && !a3600.equals("")){
				//信息验证
				if(a3604a == null || "".equals(a3604a)){
					this.getExecuteSG().addExecuteCode("parent.$h.alert('系统提示','家庭成员称谓不能为空！',null,220);");
					//this.setMainMessage("家庭成员称谓不能为空！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(a3601 == null || "".equals(a3601)){
					this.getExecuteSG().addExecuteCode("parent.$h.alert('系统提示','家庭成员姓名不能为空！',null,220);");
					//this.setMainMessage("家庭成员姓名不能为空！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				/*if(a3607 == null || "".equals(a3607)){
					this.getExecuteSG().addExecuteCode("parent.$h.alert('系统提示','家庭成员出生年月不能为空！',null,220);");
					//this.setMainMessage("家庭成员出生年月不能为空！");
					return EventRtnType.NORMAL_SUCCESS;
				}*/
				if(a3627 == null || "".equals(a3627)){
					this.getExecuteSG().addExecuteCode("parent.$h.alert('系统提示','家庭成员政治面貌不能为空！',null,220);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(a3611 == null || "".equals(a3611)){
					this.getExecuteSG().addExecuteCode("parent.$h.alert('系统提示','家庭成员工作单位及职务不能为空！',null,220);");
					//this.setMainMessage("家庭成员工作单位及职务不能为空！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				
			}
			
			sb
			.append("a3604a_"+i+":'"+a3604a+"',")
			.append("a3601_"+i+":'"+a3601+"',")
			.append("a3607_"+i+":'"+a3607+"',")
			.append("a3627_"+i+":'"+a3627+"',")
			.append("a3611_"+i+":'"+a3611+"',");
			a36.setA0000(a0000);
			if((a3604a==null||"".equals(a3604a))&&
					(a3601==null||"".equals(a3601))&&
					(a3607==null||"".equals(a3607))&&
					(a3627==null||"".equals(a3627))&&
									(a3611==null||"".equals(a3611))){
				sb.append("a3600_"+i+":'',");
				this.getPageElement("a3600_"+i).setValue("");
				if(a3600==null||"".equals(a3600)){
					
					continue;
				}else{
					a36.setA3600(a3600);
					new LogUtil("3363", "A36", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A36(), new A36())).start();
					sess.delete(a36);
					continue;
				}
				
			}
			a36.setA3604a(a3604a);
			a36.setA3601(a3601);
			a36.setA3607(a3607);
			a36.setA3627(a3627);
			a36.setA3611(a3611);
			a36.setSortid(BigDecimal.valueOf((long)i));
			if(a3600==null||"".equals(a3600)){
				a36.setA3600(null);
				sess.save(a36);
				sess.flush();
				new LogUtil("3361", "A36", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A36(), a36)).start();
				
				this.getPageElement("a3600_"+i).setValue(a36.getA3600());
				
			}else{
				a36.setA3600(a3600);
				A36 a36_old = (A36)sess.get(A36.class, a3600);
				new LogUtil("3362", "A36", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a36_old, a36)).start();
				PropertyUtils.copyProperties(a36_old, a36);
				sess.update(a36_old);
			}
			sb.append("a3600_"+i+":'"+a36.getA3600()+"',");
		}
		for(int i=rowLength+1;i<=30;i++){
			sb.append("a3600_"+i+":'',")
			.append("a3604a_"+i+":'',")
			.append("a3601_"+i+":'',")
			.append("a3607_"+i+":'',")
			.append("a3627_"+i+":'',")
			.append("a3611_"+i+":'',");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("}");
		sess.flush();			
		//旧值 判断是否修改
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public int getAgeNew(String entryDate,String birth) {
		int returnAge;

		String birthYear = birth.substring(0, 4);
		String birthMonth = birth.substring(4, 6);
		String birthDay = "";
		if(birth.length()==6){
			birthDay = "01";
		}
		if(birth.length()==8){
			birthDay = birth.substring(6, 8);
		}
		
		String nowYear = entryDate.substring(0, 4);
		String nowMonth = entryDate.substring(4, 6);
		String nowDay = "";
		if(entryDate.length()==6){
			nowDay = "01";
		}
		if(entryDate.length()==8){
			nowDay = entryDate.substring(6, 8);
		}
		if (Integer.parseInt(nowYear) == Integer.parseInt(birthYear)) {
			returnAge = 0; // 同年返回0岁
		} else {
			int ageDiff = Integer.parseInt(nowYear) - Integer.parseInt(birthYear); // 年只差
			if (ageDiff > 0) {
				if (Integer.parseInt(nowMonth) == Integer.parseInt(birthMonth)) {
					int dayDiff = Integer.parseInt(nowDay) - Integer.parseInt(birthDay);// 日之差
					if (dayDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				} else {
					int monthDiff = Integer.parseInt(nowMonth) - Integer.parseInt(birthMonth);// 月之差
					if (monthDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				}
			} else {
				returnAge = -1;// 出生日期错误 晚于今年
			}

		}
		//String msg = value.toString().substring(0, 6) + "(" + returnAge + "岁)";
		int msg = returnAge ;
		return msg;
	}
	
	
	
	@PageEvent("deleteA36F")
	@NoRequiredValidate
	public int deleteA36F(String a3600) throws AppException, RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IntrospectionException{
		HBSession session = HBUtil.getHBSession();
		
		//删除家庭成员
		if(a3600 != null && !"".equals(a3600)){
			A36 policy = (A36) session.get(A36.class, a3600);
			if(policy!=null && !"".equals(policy)){
				session.delete(policy);
				session.flush();
			}
		}
		
		doInit();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
