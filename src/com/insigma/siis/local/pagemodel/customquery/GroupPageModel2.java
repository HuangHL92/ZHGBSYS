package com.insigma.siis.local.pagemodel.customquery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.CheckBox;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Radio;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Customquery;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GroupPageModel2 extends PageModel{

	@Override
	@AutoNoMask
	public int doInit() throws RadowException {
		//this.setNextEventName("initX");
		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMM");
		//PageElement pe= this.getPageElement("isOnDuty");
		//pe.setValue("1");
        String datestr = myFmt1.format(new Date());
        this.getPageElement("jiezsj").setValue(datestr);
        this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4)+"."+datestr.substring(4, 6));
        
        Calendar  c = new  GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<80;i++){
			map.put(""+(year-i), year-i);
		}
		((Combo)this.getPageElement("a1521")).setValueListForSelect(map); 
        
		this.getExecuteSG().addExecuteCode("clearCon();");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	@AutoNoMask
	public int initX() throws RadowException{		
		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMM");
		//PageElement pe= this.getPageElement("isOnDuty");
		//pe.setValue("1");
        String datestr = myFmt1.format(new Date());
        this.getPageElement("jiezsj").setValue(datestr);
        this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4)+"."+datestr.substring(4, 6));
        
        Calendar  c = new  GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<80;i++){
			map.put(""+(year-i), year-i);
		}
		((Combo)this.getPageElement("a1521")).setValueListForSelect(map); 
        
		this.getExecuteSG().addExecuteCode("clearCon();");
        return 0;
	}
	
	
	
	
	
	
	
	
	/**
	 * 查询（注：范围查询中代码的大小与字面逻辑的高低正好相反，所以判断逻辑也是相反的处理）
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("mQueryonclick")
	public int query() throws RadowException, AppException{
		
		
		String userID = SysManagerUtils.getUserId();
		//this.request.getSession().setAttribute("queryTypeEX", "新改查询方式");
		
		String b01String = (String)this.getPageElement("SysOrgTreeIds").getValue();
		 
		StringBuffer a02_a0201b_sb = new StringBuffer("");
        StringBuffer cu_b0111_sb = new StringBuffer("");
       
        if(b01String!=null && !"".equals(b01String)){//tree!=null && !"".equals(tree.trim()
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" and (1=2 ");
				cu_b0111_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String nodeid = it.next(); 
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");//[机构名称，是否包含下级，是否本级选中]
				if("true".equals(types[1])&&"true".equals(types[2])){
					//a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+"%' ");
					a02_a0201b_sb.append(" or "+CommSQL.subString("a02.a0201b", 1, nodeid.length(), nodeid));
					//cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+"%' ");
					cu_b0111_sb.append(" or "+CommSQL.subString("cu.b0111", 1, nodeid.length(), nodeid));
				}else if("true".equals(types[1])&&"false".equals(types[2])){
					//a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+".%' ");
					a02_a0201b_sb.append(" or "+CommSQL.subString2("a02.a0201b", 1, nodeid.length(), nodeid));
					//cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+".%' ");
					cu_b0111_sb.append(" or "+CommSQL.subString2("cu.b0111", 1, nodeid.length(), nodeid));
				}else if("false".equals(types[1])&&"true".equals(types[2])){
					a02_a0201b_sb.append(" or a02.a0201b = '"+nodeid+"' ");
					cu_b0111_sb.append(" or cu.b0111 = '"+nodeid+"' ");
				}
			}
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" ) ");
				cu_b0111_sb.append(" ) ");
			}
        }    
        //用来判断列表、小资料、照片
        String tableType = this.getPageElement("tableType").getValue();
        return newquery(cu_b0111_sb,a02_a0201b_sb,userID,tableType);
        
	}
	
	
	
	private int newquery(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, String userID,String tableType) throws RadowException, AppException {
		
		 Map<String, String> ret =  getSQL(cu_b0111_sb, a02_a0201b_sb, userID);
        String finalsql = ret.get("sql");
        finalsql = "select * from ("+finalsql+") a01 where 1=1 ";
        this.getPageElement("sql").setValue(finalsql);
        this.getExecuteSG().addExecuteCode("realParent.document.getElementById('sql').value=document.getElementById('sql').value");
        Map<String, Boolean> m = new HashMap<String, Boolean>();
        m.put("paixu", true);
        this.request.getSession().setAttribute("queryConditionsCQ",m);
        
        this.getExecuteSG().addExecuteCode("realParent.document.getElementById('checkedgroupid').value=''");
        this.getExecuteSG().addExecuteCode("realParent.document.getElementById('tabn').value='tab2'");
		//this.setNextEventName("peopleInfoGrid.dogridquery");
        if("1".equals(tableType)){
        	String queryType = (String)request.getSession().getAttribute("queryType");
        	if("define".equals(queryType)){
				this.getExecuteSG().addExecuteCode("realParent.changeField()");
				request.getSession().removeAttribute("queryType");
			}else{
				this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('peopleInfoGrid.dogridquery');");
			}
        	
        }
        if("2".equals(tableType)){
        	this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('peopleInfoGrid.dogridquery');");
        	this.getExecuteSG().addExecuteCode("realParent.datashow();");
        }
        if("3".equals(tableType)){
        	this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('peopleInfoGrid.dogridquery');");
        	this.getExecuteSG().addExecuteCode("realParent.picshow();");
        }
        this.getExecuteSG().addExecuteCode("collapseGroupWin();");
        
        this.request.getSession().setAttribute("queryType", "1");
		// TODO Auto-generated method stub
		return EventRtnType.NORMAL_SUCCESS;
	}




	private void xuelixueweiSQL(String a0801b, String a0814, String a0824, StringBuffer orther_sb, String highField,
			String xueliORxuewei) {
		StringBuffer a0801b_sb = new StringBuffer("");

		if (!"".equals(a0801b)) {
			String[] a0801bArray = a0801b.split(",");
			for (int i = 0; i < a0801bArray.length; i++) {
				a0801b_sb.append(" " + xueliORxuewei + " like '" + a0801bArray[i] + "%' or ");
			}
			a0801b_sb.delete(a0801b_sb.length() - 4, a0801b_sb.length() - 1);
		}

		if (!"".equals(a0801b) || !"".equals(a0814) || !"".equals(a0824)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a08 where " + highField + "='1' ");
			if (!"".equals(a0801b)) {
				orther_sb.append(" and (" + a0801b_sb.toString() + ")");
			}

			if (!"".equals(a0814)) {
				orther_sb.append(" and a0814 like '%" + a0814 + "%'");
			}
			if (!"".equals(a0824)) {
				orther_sb.append(" and a0824 like '%" + a0824 + "%'");
			}
			orther_sb.append(")");
		}

	}



	private Map<String, String> getSQL(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb,
			String userID) throws AppException, RadowException{
		Map<String, String> condMap = new HashMap<String, String>();
		StringBuffer a01sb = new StringBuffer("");
		StringBuffer a02sb = new StringBuffer("");
		StringBuffer orther_sb = new StringBuffer("");
		String sid = this.request.getSession().getId();
		
		
		String a0195 = this.getPageElement("a0195").getValue();//统计关系所在单位
		if (!"".equals(a0195)){
			a01sb.append(" and ");
			a01sb.append("a01.a0195 = '"+a0195+"'");
			condMap.put("a0195", a0195);
		}
		
		
		String a0101 = this.getPageElement("a0101").getValue();//人员姓名
		if (!"".equals(a0101)){
			a01sb.append(" and ");
			a01sb.append("a01.a0101 like '"+a0101+"%'");
			condMap.put("a0101", a0101);
		}
		
		String a0184 = this.getPageElement("a0184").getValue().toUpperCase();//身份证号
		if (!"".equals(a0184)){
			a01sb.append(" and ");
			a01sb.append("a01.a0184 like '"+a0184+"%'");
			condMap.put("a0184", a0184);
		}
		
		String a0111 = this.getPageElement("a0111").getValue();//籍贯
		String a0111_combo = this.getPageElement("a0111_combo").getValue();//籍贯
		if (!"".equals(a0111)){
			a0111 = a0111.replaceAll("(0){1,}$", "");
			a01sb.append(" and ");
			a01sb.append(" a01.a0111 like '"+a0111+"%' ");
			condMap.put("a0111", a0111);
		}else{
			if(!"".equals(a0111_combo)){
				a01sb.append(" and ");
				a01sb.append(" a01.a0111a like '%"+a0111_combo+"%' ");
				condMap.put("a0111_combo", a0111_combo);
			}
		}
		
		
		
		String a0104 = this.getPageElement("a0104").getValue();//性别
		if (!"".equals(a0104)&&!"0".equals(a0104)){
			a01sb.append(" and ");
			a01sb.append("a01.a0104 = '"+a0104+"'");
			condMap.put("a0104", a0104);
		}
		
		String ageA = this.getPageElement("ageA").getValue();//年龄
		String ageB = this.getPageElement("ageB").getValue();//年龄
		if(!"".equals(ageB) && StringUtils.isNumeric(ageB)){//是否数字
			ageB = Integer.valueOf(ageB)+1+"";
		}
		String jiezsj = this.getPageElement("jiezsj").getValue();//截止时间
		String dateEnd = GroupPageBS.getDateformY(ageA, jiezsj);
		String dateStart = GroupPageBS.getDateformY(ageB, jiezsj);
		if(!"".equals(dateEnd)&&!"".equals(dateStart)&&dateEnd.compareTo(dateStart)<0){
			throw new AppException("年龄范围错误！");
		}
		if(!"".equals(dateStart)){
			a01sb.append(" and a01.a0107>='"+dateStart+"'");
			condMap.put("ageB", ageB);
			condMap.put("jiezsj", jiezsj);
		}
		if(!"".equals(dateEnd)){
			a01sb.append(" and a01.a0107<'"+dateEnd+"'");
			condMap.put("ageA", ageA);
			condMap.put("jiezsj", jiezsj);
		}
		
		String a0160 = this.getPageElement("a0160").getValue();//人员类别
		if (!"".equals(a0160)){
			a01sb.append(" and ");
			a01sb.append("a01.a0160 = '"+a0160+"'");
			condMap.put("a0160", a0160);
		}
		
		
		
		
		String a0107A = this.getPageElement("a0107A").getValue();//出生年月
		String a0107B = this.getPageElement("a0107B").getValue();//出生年月
		if(!"".equals(a0107A)){
			a01sb.append(" and a01.a0107>='"+a0107A+"'");
			condMap.put("a0107A", a0107A);
		}
		if(!"".equals(a0107B)){
			a01sb.append(" and a01.a0107<='"+a0107B+"'");
			condMap.put("a0107B", a0107B);
		}
		
		
		
		/*String a0163 = this.getPageElement("a0163").getValue();//人员状态
		if(!"".equals(a0163)){
			a01sb.append(" and a01.a0163='"+a0163+"'");
		}*/
		
		
		
		
		String a0144A = this.getPageElement("a0144A").getValue();//参加中共时间
		String a0144B = this.getPageElement("a0144B").getValue();//参加中共时间
		if(!"".equals(a0144A)){
			a01sb.append(" and a01.a0144>='"+a0144A+"'");
			condMap.put("a0144A", a0144A);
		}
		if(!"".equals(a0144B)){
			a01sb.append(" and a01.a0144<='"+a0144B+"'");
			condMap.put("a0144B", a0144B);
		}
		
		
		
		String a0141 = this.getPageElement("a0141").getValue();//政治面貌
		if(!"".equals(a0141)){
			a01sb.append(" and a01.a0141='"+a0141+"'");
			condMap.put("a0141", a0141);
		}
		
		
		String a0192a = this.getPageElement("a0192a").getValue();//职务全称
		if(!"".equals(a0192a)){
			a01sb.append(" and a01.a0192a like '%"+a0192a+"%'");
			condMap.put("a0192a", a0192a);
		}
		
		
		
		String a0134A = this.getPageElement("a0134A").getValue();//参加工作时间
		String a0134B = this.getPageElement("a0134B").getValue();//参加工作时间
		if(!"".equals(a0134A)){
			a01sb.append(" and a01.a0134>='"+a0134A+"'");
			condMap.put("a0134A", a0134A);
		}
		if(!"".equals(a0134B)){
			a01sb.append(" and a01.a0134<='"+a0134B+"'");
			condMap.put("a0134B", a0134B);
		}
		
		
		
		String a0114 = this.getPageElement("a0114").getValue();//出生地
		if (!"".equals(a0114)){
			a0114 = a0114.replaceAll("(0){1,}$", "");
			a01sb.append(" and a01.a0114 like '"+a0114+"%' ");
			condMap.put("a0114", a0114);
		}
		
		
		
		String a0221A = this.getPageElement("a0221A").getValue();//职务层次
		String a0221B = this.getPageElement("a0221B").getValue();//职务层次
		if(!StringUtil.isEmpty(a0221A) && !StringUtil.isEmpty(a0221B)){
			CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", a0221A);
			CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", a0221B);
			if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
				throw new AppException("职务层次范围不属于同一类别，请检查！");
			}
			//职务层次 值越小 字面意思越高级
			if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
				throw new AppException("职务层次范围不正确，请检查！");
			}
		}
		if(!"".equals(a0221A)){
			a01sb.append(" and a01.a0221<='"+a0221A+"'");
			condMap.put("a0221A", a0221A);
		}
		if(!"".equals(a0221B)){
			a01sb.append(" and a01.a0221>='"+a0221B+"'");
			condMap.put("a0221B", a0221B);
		}
		
		
		
		
		String a0288A = this.getPageElement("a0288A").getValue();//任现职务层次时间
		String a0288B = this.getPageElement("a0288B").getValue();//任现职务层次时间
		if(!"".equals(a0288A)){
			a01sb.append(" and a01.a0288>='"+a0288A+"'");
			condMap.put("a0288A", a0288A);
		}
		if(!"".equals(a0288B)){
			a01sb.append(" and a01.a0288<='"+a0288B+"'");
			condMap.put("a0288B", a0288B);
		}
		
		
		
		
		String xgsjA = this.getPageElement("xgsjA").getValue();//最后维护时间
		String xgsjB = this.getPageElement("xgsjB").getValue();//最后维护时间
		if(!"".equals(xgsjA)){
			a01sb.append(" and a01.xgsj>="+CommSQL.to_date(xgsjA));
			condMap.put("xgsjA", xgsjA);
		}
		if(!"".equals(a0288B)){
			a01sb.append(" and a01.xgsj<="+CommSQL.adddate(CommSQL.to_date(xgsjB)));
			condMap.put("xgsjB", xgsjB);
		}
		
		
		
		
		String a0117 = this.getPageElement("a0117v").getValue();//人员管理类别
		if(!"".equals(a0117)){
			String[] a0117s = a0117.split(",");
			if(a0117s.length==1){
				if("01".equals(a0117s[0]))
					a01sb.append(" and a01.a0117 ='01'");
				else
					a01sb.append(" and a01.a0117 !='01'");
			}
			condMap.put("a0117", a0117);
		}
		
		
		
		
		String a1701 = this.getPageElement("a1701").getValue();//简历
		if(!"".equals(a1701)){
			a01sb.append(" and a01.a1701 like '%"+a1701+"%'");
			condMap.put("a1701", a1701);
		}
		
		
		
		
		String a0192e = this.getPageElement("a0192e").getValue();//现职级
		if(!"".equals(a0192e)){
			a01sb.append(" and a01.a0192e='"+a0192e+"'");
			condMap.put("a0192e", a0192e);
		}
		
		
		
		
		String a0192cA = this.getPageElement("a0192cA").getValue();//任职级时间
		String a0192cB = this.getPageElement("a0192cB").getValue();//任职级时间
		if(!"".equals(a0192cA)){
			a01sb.append(" and a01.a0192c>='"+a0192cA+"'");
			condMap.put("a0192cA", a0192cA);
		}
		if(!"".equals(a0192cB)){
			a01sb.append(" and a01.a0192c<='"+a0192cB+"'");
			condMap.put("a0192cB", a0192cB);
		}
		
		
		
		
		String a0165 = this.getPageElement("a0165v").getValue();//人员管理类别
		condMap.put("a0165", a0165);
		if(!"".equals(a0165)){
			a0165 = a0165.replace(",", "','");
			a01sb.append(" and a01.a0165 in('"+a0165+"')");
		}
		
		
		
		//职务 StringBuffer a02sb = new StringBuffer("");
		
		String a0216a = this.getPageElement("a0216a").getValue();//职务名称
		if(!"".equals(a0216a)){
			a02sb.append(" and a02.a0216a like '%"+a0216a+"%'");
			condMap.put("a0216a", a0216a);
		}
		
		
		
		String a0201d = this.getPageElement("a0201d").getValue();//是否班子成员
		if(!"".equals(a0201d)){
			a02sb.append(" and a02.a0201d='"+a0201d+"'");
			condMap.put("a0201d", a0201d);
		}
		
		
		
		
		String a0219 = this.getPageElement("a0219").getValue();//是否领导职务
		if(!"".equals(a0219)){
			a02sb.append(" and a02.a0219='"+a0219+"'");
			condMap.put("a0219", a0219);
		}
		
		
		
		
		String a0201e = this.getPageElement("a0201ev").getValue();//成员类别
		condMap.put("a0201e", a0201e);
		if(!"".equals(a0201e)){
			a0201e = a0201e.replace(",", "','");
			a02sb.append(" and a02.a0201e in('"+a0201e+"')");
		}
		
		
		//最高学历
		String xla0801b = this.getPageElement("xla0801bv").getValue();//最高学历  学历代码
		String xla0814 = this.getPageElement("xla0814").getValue();//毕业院校
		String xla0824 = this.getPageElement("xla0824").getValue();//专业
		xuelixueweiSQL(xla0801b,xla0814,xla0824,orther_sb,"a0834","a0801b");
		condMap.put("xla0801b", xla0801b);
		condMap.put("xla0814", xla0814);
		condMap.put("xla0824", xla0824);
		
		
		//最高学位
		String xwa0901b = this.getPageElement("xwa0901bv").getValue();//最高学位 学位代码
		String xwa0814 = this.getPageElement("xwa0814").getValue();//毕业院校
		String xwa0824 = this.getPageElement("xwa0824").getValue();//专业
		xuelixueweiSQL(xwa0901b,xwa0814,xwa0824,orther_sb,"a0835","a0901b");
		condMap.put("xwa0901b", xwa0901b);
		condMap.put("xwa0814", xwa0814);
		condMap.put("xwa0824", xwa0824);
		
		//全日制最高学历
		String qrzxla0801b = this.getPageElement("qrzxla0801bv").getValue();//最高学历  学历代码
		String qrzxla0814 = this.getPageElement("qrzxla0814").getValue();//毕业院校
		String qrzxla0824 = this.getPageElement("qrzxla0824").getValue();//专业
		xuelixueweiSQL(qrzxla0801b,qrzxla0814,qrzxla0824,orther_sb,"a0831","a0801b");
		condMap.put("qrzxla0801b", qrzxla0801b);
		condMap.put("qrzxla0814", qrzxla0814);
		condMap.put("qrzxla0824", qrzxla0824);
		
		
		//全日制最高学位
		String qrzxwa0901b = this.getPageElement("qrzxwa0901bv").getValue();//最高学位 学位代码
		String qrzxwa0814 = this.getPageElement("qrzxwa0814").getValue();//毕业院校
		String qrzxwa0824 = this.getPageElement("qrzxwa0824").getValue();//专业
		xuelixueweiSQL(qrzxwa0901b,qrzxwa0814,qrzxwa0824,orther_sb,"a0832","a0901b");
		condMap.put("qrzxwa0901b", qrzxwa0901b);
		condMap.put("qrzxwa0814", qrzxwa0814);
		condMap.put("qrzxwa0824", qrzxwa0824);
		
		
		//在职最高学历
		String zzxla0801b = this.getPageElement("zzxla0801bv").getValue();//最高学历  学历代码
		String zzxla0814 = this.getPageElement("zzxla0814").getValue();//毕业院校
		String zzxla0824 = this.getPageElement("zzxla0824").getValue();//专业
		xuelixueweiSQL(zzxla0801b,zzxla0814,zzxla0824,orther_sb,"a0838","a0801b");
		condMap.put("zzxla0801b", zzxla0801b);
		condMap.put("zzxla0814", zzxla0814);
		condMap.put("zzxla0824", zzxla0824);
		
		
		//在职最高学位
		String zzxwa0901b = this.getPageElement("zzxwa0901bv").getValue();//最高学位 学位代码
		String zzxwa0814 = this.getPageElement("zzxwa0814").getValue();//毕业院校
		String zzxwa0824 = this.getPageElement("zzxwa0824").getValue();//专业
		xuelixueweiSQL(zzxwa0901b,zzxwa0814,zzxwa0824,orther_sb,"a0839","a0901b");
		condMap.put("zzxwa0901b", zzxwa0901b);
		condMap.put("zzxwa0814", zzxwa0814);
		condMap.put("zzxwa0824", zzxwa0824);
		
		
		//奖惩
		String a14z101 = this.getPageElement("a14z101").getValue();//奖惩描述
		String lba1404b = this.getPageElement("lba1404bv").getValue();//奖惩类别
		String a1404b = this.getPageElement("a1404b").getValue();//奖惩名称代码
		String a1415 = this.getPageElement("a1415").getValue();//受奖惩时职务层次
		String a1414 = this.getPageElement("a1414").getValue();//批准机关级别
		String a1428 = this.getPageElement("a1428").getValue();//批准机关性质
		condMap.put("a14z101", a14z101);
		condMap.put("lba1404b", lba1404b);
		condMap.put("a1404b", a1404b);
		condMap.put("a1415", a1415);
		condMap.put("a1414", a1414);
		condMap.put("a1428", a1428);
		
		if (!"".equals(a14z101) || !"".equals(lba1404b) || !"".equals(a1404b) || !"".equals(a1415) || !"".equals(a1414) || !"".equals(a1428)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a14 where 1=1 ");
			if(!"".equals(a14z101)){
				orther_sb.append(" and a14z101 like '%"+a14z101+"%'");
			}

			if (!"".equals(lba1404b)) {
				StringBuffer like_sb = new StringBuffer("");
				String[] fArray = lba1404b.split(",");
				for (int i = 0; i < fArray.length; i++) {
					like_sb.append(" a1404b like '" + fArray[i] + "%' or ");
				}
				like_sb.delete(like_sb.length() - 4, like_sb.length() - 1);
				orther_sb.append(" and (" + like_sb.toString() + ")");
			}
			if (!"".equals(a1404b)) {
				orther_sb.append(" and a1404b='" + a1404b + "'");
			}
			if (!"".equals(a1415)) {
				orther_sb.append(" and a1415='" + a1415 + "'");
			}
			if (!"".equals(a1414)) {
				orther_sb.append(" and a1414='" + a1414 + "'");
			}
			if (!"".equals(a1428)) {
				orther_sb.append(" and a1428='" + a1428 + "'");
			}
			orther_sb.append(")");
		}
		
		//职称
		String a0601 = this.getPageElement("a0601v").getValue();//专业技术任职资格
		condMap.put("a0601", a0601);
		
		if (!"".equals(a0601)) {
			boolean is9 = false;
			orther_sb.append(" and (a01.a0000 in (select a0000 from a06 where a0699='true' ");
			StringBuffer like_sb = new StringBuffer("");
			String[] fArray = a0601.split(",");
			for (int i = 0; i < fArray.length; i++) {
				
				if("9".equals(fArray[i])){
					like_sb.append(" a0601 is null or a0601='999' or ");//无职称
					is9 = true;
				}else{
					like_sb.append(" a0601 like '%" + fArray[i] + "' or ");
				}
			}
			like_sb.delete(like_sb.length() - 4, like_sb.length() - 1);
			orther_sb.append(" and (" + like_sb.toString() + ")");
			orther_sb.append(")");
			if(is9){//无职称
				orther_sb.append(" or not exists (select 1 from a06 where a01.a0000=a06.a0000 and  a0699='true')");
			}
			orther_sb.append(")");
		}
		
		//年度考核
		String a15z101 = this.getPageElement("a15z101").getValue();//年度考核描述
		String a1521 = this.getPageElement("a1521").getValue();//考核年度
		String a1517 = this.getPageElement("a1517").getValue();//考核结论类别
		condMap.put("a15z101", a15z101);
		condMap.put("a1521", a1521);
		condMap.put("a1517", a1517);
		if (!"".equals(a15z101) || !"".equals(a1521) || !"".equals(a1517)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a15 where 1=1 ");
			if(!"".equals(a15z101)){
				orther_sb.append(" and a15z101 like '%"+a15z101+"%'");
			}

			if(!"".equals(a1521)){
				a1521 = a1521.replace(",", "','");
				orther_sb.append(" and a1521 in('"+a1521+"')");
			}

			if (!"".equals(a1517)) {
				orther_sb.append(" and a1517='" + a1517 + "'");
			}
			
			orther_sb.append(")");
		}
		
		
		
		
		//家庭成员
		String a3601 = this.getPageElement("a3601").getValue();//姓名
		String a3684 = this.getPageElement("a3684").getValue();//身份证号
		String a3611 = this.getPageElement("a3611").getValue();//工作单位及职务
		condMap.put("a3601", a3601);
		condMap.put("a3684", a3684);
		condMap.put("a3611", a3611);
		if (!"".equals(a3601) || !"".equals(a3684) || !"".equals(a3611)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a36 where 1=1 ");
			if(!"".equals(a3601)){
				orther_sb.append(" and a3601 like '"+a3601+"%'");
			}

			if(!"".equals(a3684)){
				orther_sb.append(" and a3684 like '"+a3684+"%'");
			}

			if(!"".equals(a3611)){
				orther_sb.append(" and a3611 like '%"+a3611+"%'");
			}
			
			orther_sb.append(")");
		}
		
		
		
		
		
		
		
		String radioC = this.getPageElement("radioC").getValue();
		/*String sql = this.getPageElement("sql").getValue();
		
		
		if(!"1".equals(radioC)){
			if("".equals(sql)||sql==null)
				throw new AppException("未进行过查询请先查询!");
		}*/
		
		
		
		String a0163 = this.getPageElement("a0163").getValue();//人员状态
		String qtxzry = this.getPageElement("qtxzry").getValue();
		condMap.put("a0163", a0163);
		condMap.put("qtxzry", qtxzry);
		
		String finalsql = CommSQL.getCondiQuerySQL(userID,a01sb,a02sb,a02_a0201b_sb,cu_b0111_sb,orther_sb,a0163,qtxzry,sid);
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("sql", finalsql);
		retMap.put("cond", JSONObject.fromObject(condMap).toString());
		return retMap;
	}












	CustomQueryBS cbBs=new CustomQueryBS();
	/**
	 * 保存：（注：范围查询中代码的大小与字面逻辑的高低正好相反，所以判断逻辑也是相反的处理）
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveCon.onclick")
    @Transaction
	public int saveCon(String name) throws RadowException, AppException{
		//this.request.getSession().setAttribute("queryType", "1");
		String userID = SysManagerUtils.getUserId();
		//this.request.getSession().setAttribute("queryTypeEX", "新改查询方式");
		String b01String = (String)this.getPageElement("SysOrgTreeIds").getValue();
		 
		StringBuffer a02_a0201b_sb = new StringBuffer("");
        StringBuffer cu_b0111_sb = new StringBuffer("");
       
        if(b01String!=null && !"".equals(b01String)){//tree!=null && !"".equals(tree.trim()
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" and (1=2 ");
				cu_b0111_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String nodeid = it.next(); 
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");//[机构名称，是否包含下级，是否本级选中]
				if("true".equals(types[1])&&"true".equals(types[2])){
					//a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+"%' ");
					a02_a0201b_sb.append(" or "+CommSQL.subString("a02.a0201b", 1, nodeid.length(), nodeid));
					//cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+"%' ");
					cu_b0111_sb.append(" or "+CommSQL.subString("cu.b0111", 1, nodeid.length(), nodeid));
				}else if("true".equals(types[1])&&"false".equals(types[2])){
					//a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+".%' ");
					a02_a0201b_sb.append(" or "+CommSQL.subString2("a02.a0201b", 1, nodeid.length(), nodeid));
					//cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+".%' ");
					cu_b0111_sb.append(" or "+CommSQL.subString2("cu.b0111", 1, nodeid.length(), nodeid));
				}else if("false".equals(types[1])&&"true".equals(types[2])){
					a02_a0201b_sb.append(" or a02.a0201b = '"+nodeid+"' ");
					cu_b0111_sb.append(" or cu.b0111 = '"+nodeid+"' ");
				}
			}
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" ) ");
				cu_b0111_sb.append(" ) ");
			}
        }      
        
        Map<String, String> ret =  getSQL(cu_b0111_sb, a02_a0201b_sb, userID);
        String finalsql = ret.get("sql");
        //finalsql = "select * from ("+finalsql+") a01 where 1=1 ";
        if(name.trim()=="") {
        	name = "常用条件";
        }
        
        cbBs.delComm();
        String queryid = this.getPageElement("queryid").getValue();
        cbBs.saveOrUodateCq(queryid, name, finalsql, "", SysUtil.getCacheCurrentUser() .getLoginname(), "[]", ret.get("cond")); 
        this.getExecuteSG().addExecuteCode("var cwin = $h.getTopParent().document.getElementById('iframe_conditionwin');"
        		+ "if(cwin){cwin.contentWindow.radow.doEvent('gridcq');};radow.doEvent('memberGrid.dogridquery');");
        //this.setMainMessage("保存成功，可在【查询方案】中的【常用条件】进行查询！");
        this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	/**
	 * 清除条件
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("clearCon.onclick")
	@NoRequiredValidate
	public int clearCon()throws RadowException, AppException{
		String a= this.getPageElement("female").getValue();
		this.getPageElement("a0160").setValue("");
		this.getPageElement("a0160_combo").setValue("");		//人员类别-fujun
		this.getPageElement("a0101A").setValue("");
		this.getPageElement("a0184A").setValue("");
		this.getPageElement("a0163").setValue("");
		this.getPageElement("a0163_combo").setValue("");		//人员状态-fujun
		this.getPageElement("ageA").setValue("");
		this.getPageElement("age1").setValue("");
		this.getPageElement("female").setValue("0");;
		this.getPageElement("minority").setValue("0");
		this.getPageElement("nonparty").setValue("0");
		this.getPageElement("duty").setValue("");
		this.getPageElement("duty_combo").setValue("");			//职务层次 ——fujun
		this.getPageElement("duty1").setValue("");
		this.getPageElement("duty1_combo").setValue("");		//职务层次 ——fujun
		this.getPageElement("dutynow").setValue("");
		this.getPageElement("dutynow_1").setValue("");			//任现职务层次时间 ——fujun
		this.getPageElement("dutynow1").setValue("");
		this.getPageElement("dutynow1_1").setValue("");			//任现职务层次时间 ——fujun
		this.getPageElement("a0219").setValue("");
		this.getPageElement("a0219_combo").setValue("");		//职务类别——fujun
		this.getPageElement("edu").setValue("");
		this.getPageElement("edu_combo").setValue("");		//学历——fujun
		this.getPageElement("edu1").setValue("");
		this.getPageElement("edu1_combo").setValue("");		//学历——fujun
		this.getPageElement("allday").setValue("0");
		/*this.getPageElement("SysOrgTree").setValue("");
		this.getPageElement("SysOrgTreeIds").setValue("");*/
	    this.getPageElement("a0221aS").setValue("");//职务等级
		this.getPageElement("a0221aS_combo").setValue("");		//职务等级——fujun
		this.getPageElement("a0221aE").setValue("");//职务等级
		this.getPageElement("a0221aE_combo").setValue("");		//职务等级——fujun
		
		this.getPageElement("a0192dS").setValue("");//职务职级
		this.getPageElement("a0192dS_combo").setValue("");		//职务职级——fujun
		this.getPageElement("a0192dE").setValue("");//职务职级	
		this.getPageElement("a0192dE_combo").setValue("");			//职务职级——fujun	
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String sql="select * from Customquery t   order by createtime desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	@PageEvent("rclick")
	public int doMemberrowclick(String queryid) throws RadowException{
		//String queryid=this.getPageElement("memberGrid").getValue("queryid",0).toString();
		try {
			Customquery query = (Customquery) HBUtil.getHBSession().get(Customquery.class, queryid);
			String conds = query.getQuerycond();
			Map<String, String> condMap = JSONObject.fromObject(conds);
			
			String a0195 = condMap.get("a0195");
			if(a0195!=null && !a0195.equals("")) {
				B01 b01 = (B01) HBUtil.getHBSession().get(B01.class, a0195);
				this.getPageElement("a0195").setValue(condMap.get("a0195"));//统计关系所在单位
				this.getPageElement("a0195_combo").setValue(b01!=null ?b01.getB0101():"");//统计关系所在单位
			} else {
				this.getPageElement("a0195").setValue("");//统计关系所在单位
				this.getPageElement("a0195_combo").setValue("");//统计关系所在单位
			}
			
			
			this.getPageElement("a0101").setValue(condMap.get("a0101"));//人员姓名
			this.getPageElement("a0184").setValue(condMap.get("a0184"));//身份证号
			String a0111 = condMap.get("a0111");
			this.getPageElement("a0111").setValue(a0111);//籍贯
			if(a0111!=null && !a0111.trim().equals("")) {
				this.getPageElement("a0111_combo").setValue(HBUtil.getCodeName("ZB01", a0111, "code_name3"));//籍贯
			} else {
				this.getPageElement("a0111_combo").setValue(condMap.get("a0111_combo"));//籍贯
			}
			String a0104 = condMap.get("a0104");
			if(a0104!=null && !a0104.equals("")) {
				this.getPageElement("a0104").setValue(a0104);//性别
			}
			
			this.getPageElement("ageA").setValue(condMap.get("ageA"));//年龄
			String ageb = condMap.get("ageB")!=null && !condMap.get("ageB").equals("")?
					Integer.parseInt(condMap.get("ageB"))-1+"" :"";
			this.getPageElement("ageB").setValue(ageb);//年龄
			this.getPageElement("a0160").setValue(condMap.get("a0160"));//人员类别
			this.getPageElement("a0107A").setValue(condMap.get("a0107A"));//出生年月
			this.getPageElement("a0107B").setValue(condMap.get("a0107B"));//出生年月
			this.getPageElement("a0144A").setValue(condMap.get("a0144A"));//参加中共时间
			this.getPageElement("a0144B").setValue(condMap.get("a0144B"));//参加中共时间
			this.getPageElement("a0141").setValue(condMap.get("a0141"));//政治面貌
			this.getPageElement("a0192a").setValue(condMap.get("a0192a"));//职务全称
			this.getPageElement("a0134A").setValue(condMap.get("a0134A"));//参加工作时间
			this.getPageElement("a0134B").setValue(condMap.get("a0134B"));//参加工作时间
			this.getPageElement("a0114").setValue(condMap.get("a0114"));//出生地
			this.getPageElement("a0114_combo").setValue(HBUtil.getCodeName("ZB01", condMap.get("a0114"), "code_name3"));//出生地
			
			this.getPageElement("a0221A").setValue(condMap.get("a0221A"));//职务层次
			this.getPageElement("a0221A_combo").setValue(HBUtil.getCodeName("ZB09", condMap.get("a0221A")));
			this.getPageElement("a0221B").setValue(condMap.get("a0221B"));//职务层次
			this.getPageElement("a0221B_combo").setValue(HBUtil.getCodeName("ZB09", condMap.get("a0221B")));
			this.getPageElement("a0288A").setValue(condMap.get("a0288A"));//任现职务层次时间
			this.getPageElement("a0288B").setValue(condMap.get("a0288B"));//任现职务层次时间
			this.getPageElement("xgsjA").setValue(condMap.get("xgsjA"));//最后维护时间
			this.getPageElement("xgsjB").setValue(condMap.get("xgsjB"));//最后维护时间
			this.getPageElement("a0117v").setValue(condMap.get("a0117"));//民族
			this.getExecuteSG().addExecuteCode("setCheckBox('a0117','" + condMap.get("a0117") + "');");
			
			this.getPageElement("a1701").setValue(condMap.get("a1701"));//简历
			this.getPageElement("a0192e").setValue(condMap.get("a0192e"));//现职级
			this.getPageElement("a0192e_combo").setValue(HBUtil.getCodeName("ZB148", condMap.get("a0192e")));//现职级
			this.getPageElement("a0192cA").setValue(condMap.get("a0192cA"));//任职级时间
			this.getPageElement("a0192cB").setValue(condMap.get("a0192cB"));//任职级时间
			this.getPageElement("a0165v").setValue(condMap.get("a0165"));//人员管理类别
			this.getExecuteSG().addExecuteCode("setCheckBox('a0165','" + condMap.get("a0165") + "');");
			this.getPageElement("a0216a").setValue(condMap.get("a0216a"));//职务名称
			this.getPageElement("a0201d").setValue(condMap.get("a0201d"));//是否班子成员
			this.getPageElement("a0219").setValue(condMap.get("a0219"));//是否领导职务
			this.getPageElement("a0201ev").setValue(condMap.get("a0201e"));//成员类别
			this.getExecuteSG().addExecuteCode("setCheckBox('a0201e','" + condMap.get("a0201e") + "');");
			//最高学历
			this.getPageElement("xla0801bv").setValue(condMap.get("xla0801b"));//最高学历  学历代码
			this.getExecuteSG().addExecuteCode("setCheckBox('xla0801b','" + condMap.get("xla0801b") + "');");
			this.getPageElement("xla0814").setValue(condMap.get("xla0814"));//毕业院校
			this.getPageElement("xla0824").setValue(condMap.get("xla0824"));//专业
			//最高学位
			this.getPageElement("xwa0901bv").setValue(condMap.get("xwa0901b"));//最高学历  学历代码
			this.getExecuteSG().addExecuteCode("setCheckBox('xwa0901b','" + condMap.get("xwa0901b") + "');");
			this.getPageElement("xwa0814").setValue(condMap.get("xwa0814"));//毕业院校
			this.getPageElement("xwa0824").setValue(condMap.get("xwa0824"));//专业
			//全日制最高学历
			this.getPageElement("qrzxla0801bv").setValue(condMap.get("qrzxla0801b"));//最高学历  学历代码
			this.getExecuteSG().addExecuteCode("setCheckBox('qrzxla0801b','" + condMap.get("qrzxla0801b") + "');");
			this.getPageElement("qrzxla0814").setValue(condMap.get("qrzxla0814"));//毕业院校
			this.getPageElement("qrzxla0824").setValue(condMap.get("qrzxla0824"));//专业
			//全日制最高学位
			this.getPageElement("qrzxwa0901bv").setValue(condMap.get("qrzxwa0901b"));//最高学历  学历代码
			this.getExecuteSG().addExecuteCode("setCheckBox('qrzxwa0901b','" + condMap.get("qrzxwa0901b") + "');");
			this.getPageElement("qrzxwa0814").setValue(condMap.get("qrzxwa0814"));//毕业院校
			this.getPageElement("qrzxwa0824").setValue(condMap.get("qrzxwa0824"));//专业
			//在职最高学历
			this.getPageElement("zzxla0801bv").setValue(condMap.get("zzxla0801b"));//最高学历  学历代码
			this.getExecuteSG().addExecuteCode("setCheckBox('zzxla0801b','" + condMap.get("zzxla0801b") + "');");
			this.getPageElement("zzxla0814").setValue(condMap.get("zzxla0814"));//毕业院校
			this.getPageElement("zzxla0824").setValue(condMap.get("zzxla0824"));//专业
			//在职最高学位
			this.getPageElement("zzxwa0901bv").setValue(condMap.get("zzxwa0901b"));//最高学历  学历代码
			this.getExecuteSG().addExecuteCode("setCheckBox('zzxwa0901b','" + condMap.get("zzxwa0901b") + "');");
			this.getPageElement("zzxwa0814").setValue(condMap.get("zzxwa0814"));//毕业院校
			this.getPageElement("zzxwa0824").setValue(condMap.get("zzxwa0824"));//专业
			//奖惩
			this.getPageElement("a14z101").setValue(condMap.get("a14z101"));//奖惩描述
			this.getPageElement("lba1404bv").setValue(condMap.get("lba1404b"));//奖惩类别
			this.getExecuteSG().addExecuteCode("setCheckBox('lba1404b','" + condMap.get("lba1404b") + "');");
			this.getPageElement("a1404b").setValue(condMap.get("a1404b"));//奖惩名称代码
			this.getPageElement("a1404b_combo").setValue(HBUtil.getCodeName("ZB65", condMap.get("a1404b")));//奖惩名称代码
			this.getPageElement("a1415").setValue(condMap.get("a1415"));//受奖惩时职务层次
			this.getPageElement("a1415_combo").setValue(HBUtil.getCodeName("ZB09", condMap.get("a1415")));//受奖惩时职务层次
			this.getPageElement("a1414").setValue(condMap.get("a1414"));//批准机关级别
			this.getPageElement("a1428").setValue(condMap.get("a1428"));//批准机关性质
			this.getPageElement("a1428_combo").setValue(HBUtil.getCodeName("ZB128", condMap.get("a1428")));//批准机关性质
			//职称
			this.getPageElement("a0601v").setValue(condMap.get("a0601"));//专业技术任职资格
			this.getExecuteSG().addExecuteCode("setCheckBox('a0601','" + condMap.get("a0601") + "');");
			//年度考核
			this.getPageElement("a15z101").setValue(condMap.get("a15z101"));//年度考核描述
			this.getPageElement("a1521").setValue(condMap.get("a1521"));//考核年度
			this.getPageElement("a1517").setValue(condMap.get("a1517"));//考核结论类别
			this.getPageElement("a1517_combo").setValue(HBUtil.getCodeName("ZB18", condMap.get("a1517")));//考核结论类别
			//家庭成员
			this.getPageElement("a3601").setValue(condMap.get("a3601"));//姓名
			this.getPageElement("a3684").setValue(condMap.get("a3684"));//身份证号
			this.getPageElement("a3611").setValue(condMap.get("a3611"));//工作单位及职务
			this.getPageElement("a0163").setValue(condMap.get("a0163"));//人员状态
			this.getPageElement("qtxzry").setValue(condMap.get("qtxzry"));
			
			this.getPageElement("queryid").setValue(query.getQueryid());
			this.getPageElement("queryname").setValue(query.getQueryname());
			
			SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMM");
			String datestr = myFmt1.format(new Date());
	        this.getPageElement("jiezsj").setValue(datestr);
	        this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4)+"."+datestr.substring(4, 6));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("获取数据异常："+e.getMessage());
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("deletecond")
	public int deletecond(String id) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.createSQLQuery("delete from Customquery where queryid=?").setString(0, id).executeUpdate();
			this.getExecuteSG().addExecuteCode("radow.doEvent('memberGrid.dogridquery');");
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("删除出现异常！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}




