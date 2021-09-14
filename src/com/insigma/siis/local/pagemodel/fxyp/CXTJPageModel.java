package com.insigma.siis.local.pagemodel.fxyp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Cxtj;
import com.insigma.siis.local.business.entity.Fxyp;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.customquery.GroupPageBS;

import net.sf.json.JSONObject;

public class CXTJPageModel extends PageModel{

	@Override
	@AutoNoMask
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
        return 0;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	@AutoNoMask
	public int initX(String a) throws RadowException{		
		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMM");
		//PageElement pe= this.getPageElement("isOnDuty");
		//pe.setValue("1");
        String datestr = myFmt1.format(new Date());
        this.getPageElement("jiezsj").setValue(datestr);
        this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4)+"."+datestr.substring(4, 6));
		this.getExecuteSG().addExecuteCode("document.getElementById('existsCheckbox').click();odin.setSelectValue('a0163', '1');");
		
		
		
		String fxyp00 = this.getPageElement("fxyp00").getValue();
		Fxyp fxyp = (Fxyp)HBUtil.getHBSession().get(Fxyp.class, fxyp00);
		if(fxyp!=null&&!"clear".equals(a)){
			this.setNextEventName("initData");
		}
		
        return 0;
	}
	
	
	@SuppressWarnings("unchecked")
	@PageEvent("initData")
	@NoRequiredValidate
	@AutoNoMask
	public int initData() throws RadowException{		
		String fxyp00 = this.getPageElement("fxyp00").getValue();
		HBSession sess = HBUtil.getHBSession();
		List<Cxtj> cxtjlist = sess.createSQLQuery("select * from Cxtj "
				+ "where fxyp00='"+fxyp00+"' order by cxtj01")
				.addEntity(Cxtj.class).list();
		if(cxtjlist.size()>0){
			for(Cxtj cxtj:cxtjlist){
				if("textEdit".equals(cxtj.getCxtj08())||"NewDateEditTag".equals(cxtj.getCxtj08())
						||"numberEdit".equals(cxtj.getCxtj08())||"select2".equals(cxtj.getCxtj08())){
					this.getPageElement(cxtj.getCxtj07()).setValue(cxtj.getCxtj04());
				}else if("PublicTextIconEdit".equals(cxtj.getCxtj08())||"PublicTextIconEdit3".equals(cxtj.getCxtj08())){
					this.getPageElement(cxtj.getCxtj07()).setValue(cxtj.getCxtj04());
					this.getPageElement(cxtj.getCxtj07()+"_combo").setValue(cxtj.getCxtj09());
				}else if("radio".equals(cxtj.getCxtj08())){
					this.getExecuteSG().addExecuteCode("$('#"+cxtj.getCxtj07()+(cxtj.getCxtj04().replace(",", ""))+"').click();");
				}else if("checkbox".equals(cxtj.getCxtj08())&&"1".equals(cxtj.getCxtj04())){
					this.getExecuteSG().addExecuteCode("$('#"+cxtj.getCxtj07()+"').click();");
				}
			}
		}
		
		
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
        StringBuffer b0111desc = new StringBuffer("");
       
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
				b0111desc.append(types[0]+"，");
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
				b0111desc.deleteCharAt(b0111desc.length()-1);
			}
        }    
        return newquery(cu_b0111_sb,a02_a0201b_sb,b0111desc,userID);
        
	}
	
	
	
	private int newquery(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, StringBuffer b0111desc, String userID) throws RadowException, AppException {
		
        String fxyp00 = saveCondition(cu_b0111_sb, a02_a0201b_sb,b0111desc);
        
        	
        this.getExecuteSG().addExecuteCode("collapseGroupWin('"+fxyp00+"');");
        
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



	private String getSQL(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, String userID) throws AppException, RadowException{
		StringBuffer a01sb = new StringBuffer("");
		StringBuffer a02sb = new StringBuffer("");
		StringBuffer orther_sb = new StringBuffer("");
		String sid = this.request.getSession().getId();
		
		
		String a0195 = this.getPageElement("a0195").getValue();//统计关系所在单位
		if (!"".equals(a0195)){
			a01sb.append(" and ");
			a01sb.append("a01.a0195 = '"+a0195+"'");
		}
		
		
		String a0101 = this.getPageElement("a0101").getValue();//人员姓名
		if (!"".equals(a0101)){
			a01sb.append(" and ");
			a01sb.append("a01.a0101 like '"+a0101+"%'");
		}
		
		String a0184 = this.getPageElement("a0184").getValue().toUpperCase();//身份证号
		if (!"".equals(a0184)){
			a01sb.append(" and ");
			a01sb.append("a01.a0184 like '"+a0184+"%'");
		}
		
		String a0111 = this.getPageElement("a0111").getValue();//籍贯
		String a0111_combo = this.getPageElement("a0111_combo").getValue();//籍贯
		if (!"".equals(a0111)){
			a0111 = a0111.replaceAll("(0){1,}$", "");
			a01sb.append(" and ");
			a01sb.append(" a01.a0111 like '"+a0111+"%' ");
		}else{
			if(!"".equals(a0111_combo)){
				a01sb.append(" and ");
				a01sb.append(" a01.a0111a like '%"+a0111_combo+"%' ");
			}
		}
		
		
		
		String a0104 = this.getPageElement("a0104").getValue();//性别
		if (!"".equals(a0104)&&!"0".equals(a0104)){
			a01sb.append(" and ");
			a01sb.append("a01.a0104 = '"+a0104+"'");
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
		}
		if(!"".equals(dateStart)){
			a01sb.append(" and a01.a0107<'"+dateEnd+"'");
		}
		
		String a0160 = this.getPageElement("a0160").getValue();//人员类别
		if (!"".equals(a0160)){
			a01sb.append(" and ");
			a01sb.append("a01.a0160 = '"+a0160+"'");
		}
		
		
		
		
		String a0107A = this.getPageElement("a0107A").getValue();//出生年月
		String a0107B = this.getPageElement("a0107B").getValue();//出生年月
		if(!"".equals(a0107A)){
			a01sb.append(" and a01.a0107>='"+a0107A+"'");
		}
		if(!"".equals(a0107B)){
			a01sb.append(" and a01.a0107<='"+a0107B+"'");
		}
		
		
		
		/*String a0163 = this.getPageElement("a0163").getValue();//人员状态
		if(!"".equals(a0163)){
			a01sb.append(" and a01.a0163='"+a0163+"'");
		}*/
		
		
		
		
		String a0144A = this.getPageElement("a0144A").getValue();//参加中共时间
		String a0144B = this.getPageElement("a0144B").getValue();//参加中共时间
		if(!"".equals(a0144A)){
			a01sb.append(" and a01.a0144>='"+a0144A+"'");
		}
		if(!"".equals(a0144B)){
			a01sb.append(" and a01.a0144<='"+a0144B+"'");
		}
		
		
		
		String a0141 = this.getPageElement("a0141").getValue();//政治面貌
		if(!"".equals(a0141)){
			a01sb.append(" and a01.a0141='"+a0141+"'");
		}
		
		
		String a0192a = this.getPageElement("a0192a").getValue();//职务全称
		if(!"".equals(a0192a)){
			a01sb.append(" and a01.a0192a like '%"+a0192a+"%'");
		}
		
		
		
		String a0134A = this.getPageElement("a0134A").getValue();//参加工作时间
		String a0134B = this.getPageElement("a0134B").getValue();//参加工作时间
		if(!"".equals(a0134A)){
			a01sb.append(" and a01.a0134>='"+a0134A+"'");
		}
		if(!"".equals(a0134B)){
			a01sb.append(" and a01.a0134<='"+a0134B+"'");
		}
		
		
		
		String a0114 = this.getPageElement("a0114").getValue();//出生地
		if (!"".equals(a0114)){
			a0114 = a0114.replaceAll("(0){1,}$", "");
			a01sb.append(" and a01.a0114 like '"+a0114+"%' ");
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
		}
		if(!"".equals(a0221B)){
			a01sb.append(" and a01.a0221>='"+a0221B+"'");
		}
		
		
		
		
		String a0288A = this.getPageElement("a0288A").getValue();//任现职务层次时间
		String a0288B = this.getPageElement("a0288B").getValue();//任现职务层次时间
		if(!"".equals(a0288A)){
			a01sb.append(" and a01.a0288>='"+a0288A+"'");
		}
		if(!"".equals(a0288B)){
			a01sb.append(" and a01.a0288<='"+a0288B+"'");
		}
		
		
		
		
		String xgsjA = this.getPageElement("xgsjA").getValue();//最后维护时间
		String xgsjB = this.getPageElement("xgsjB").getValue();//最后维护时间
		if(!"".equals(xgsjA)){
			a01sb.append(" and a01.xgsj>="+CommSQL.to_date(xgsjA));
		}
		if(!"".equals(a0288B)){
			a01sb.append(" and a01.xgsj<="+CommSQL.adddate(CommSQL.to_date(xgsjB)));
		}
		
		
		
		
		String a0117 = this.getPageElement("a0117v").getValue();//名族
		if(!"".equals(a0117)){
			String[] a0117s = a0117.split(",");
			if(a0117s.length==1){
				if("01".equals(a0117s[0]))
					a01sb.append(" and a01.a0117 ='01'");
				else
					a01sb.append(" and a01.a0117 !='01'");
			}
			
		}
		
		
		
		
		String a1701 = this.getPageElement("a1701").getValue();//简历
		if(!"".equals(a1701)){
			a01sb.append(" and a01.a1701 like '%"+a1701+"%'");
		}
		
		
		
		
		String a0192e = this.getPageElement("a0192e").getValue();//现职级
		if(!"".equals(a0192e)){
			a01sb.append(" and a01.a0192e='"+a0192e+"'");
		}
		
		
		
		
		String a0192cA = this.getPageElement("a0192cA").getValue();//任职级时间
		String a0192cB = this.getPageElement("a0192cB").getValue();//任职级时间
		if(!"".equals(a0192cA)){
			a01sb.append(" and a01.a0192c>='"+a0192cA+"'");
		}
		if(!"".equals(a0192cB)){
			a01sb.append(" and a01.a0192c<='"+a0192cB+"'");
		}
		
		
		
		
		String a0165 = this.getPageElement("a0165v").getValue();//人员管理类别
		if(!"".equals(a0165)){
			a0165 = a0165.replace(",", "','");
			a01sb.append(" and a01.a0165 in('"+a0165+"')");
		}
		
		
		
		//职务 StringBuffer a02sb = new StringBuffer("");
		
		String a0216a = this.getPageElement("a0216a").getValue();//职务名称
		if(!"".equals(a0216a)){
			a02sb.append(" and a02.a0216a like '%"+a0216a+"%'");
		}
		
		
		
		String a0201d = this.getPageElement("a0201d").getValue();//是否班子成员
		if(!"".equals(a0201d)){
			a02sb.append(" and a02.a0201d='"+a0201d+"'");
		}
		
		
		
		
		String a0219 = this.getPageElement("a0219").getValue();//是否领导职务
		if(!"".equals(a0219)){
			a02sb.append(" and a02.a0219='"+a0219+"'");
		}
		
		
		
		
		String a0201e = this.getPageElement("a0201ev").getValue();//成员类别
		if(!"".equals(a0201e)){
			a0201e = a0201e.replace(",", "','");
			a02sb.append(" and a02.a0201e in('"+a0201e+"')");
		}
		
		
		//最高学历
		String xla0801b = this.getPageElement("xla0801bv").getValue();//最高学历  学历代码
		String xla0814 = this.getPageElement("xla0814").getValue();//毕业院校
		String xla0824 = this.getPageElement("xla0824").getValue();//专业
		xuelixueweiSQL(xla0801b,xla0814,xla0824,orther_sb,"a0834","a0801b");
		
		
		//最高学位
		String xwa0901b = this.getPageElement("xwa0901bv").getValue();//最高学位 学位代码
		String xwa0814 = this.getPageElement("xwa0814").getValue();//毕业院校
		String xwa0824 = this.getPageElement("xwa0824").getValue();//专业
		xuelixueweiSQL(xwa0901b,xwa0814,xwa0824,orther_sb,"a0835","a0901b");
		
		
		//全日制最高学历
		String qrzxla0801b = this.getPageElement("qrzxla0801bv").getValue();//最高学历  学历代码
		String qrzxla0814 = this.getPageElement("qrzxla0814").getValue();//毕业院校
		String qrzxla0824 = this.getPageElement("qrzxla0824").getValue();//专业
		xuelixueweiSQL(qrzxla0801b,qrzxla0814,qrzxla0824,orther_sb,"a0831","a0801b");
		
		
		//全日制最高学位
		String qrzxwa0901b = this.getPageElement("qrzxwa0901bv").getValue();//最高学位 学位代码
		String qrzxwa0814 = this.getPageElement("qrzxwa0814").getValue();//毕业院校
		String qrzxwa0824 = this.getPageElement("qrzxwa0824").getValue();//专业
		xuelixueweiSQL(qrzxwa0901b,qrzxwa0814,qrzxwa0824,orther_sb,"a0832","a0901b");
		
		
		
		//在职最高学历
		String zzxla0801b = this.getPageElement("zzxla0801bv").getValue();//最高学历  学历代码
		String zzxla0814 = this.getPageElement("zzxla0814").getValue();//毕业院校
		String zzxla0824 = this.getPageElement("zzxla0824").getValue();//专业
		xuelixueweiSQL(zzxla0801b,zzxla0814,zzxla0824,orther_sb,"a0838","a0801b");
		
		
		//在职最高学位
		String zzxwa0901b = this.getPageElement("zzxwa0901bv").getValue();//最高学位 学位代码
		String zzxwa0814 = this.getPageElement("zzxwa0814").getValue();//毕业院校
		String zzxwa0824 = this.getPageElement("zzxwa0824").getValue();//专业
		xuelixueweiSQL(zzxwa0901b,zzxwa0814,zzxwa0824,orther_sb,"a0839","a0901b");
		
		
		
		//奖惩
		String a14z101 = this.getPageElement("a14z101").getValue();//奖惩描述
		String lba1404b = this.getPageElement("lba1404bv").getValue();//奖惩类别
		String a1404b = this.getPageElement("a1404b").getValue();//奖惩名称代码
		String a1415 = this.getPageElement("a1415").getValue();//受奖惩时职务层次
		String a1414 = this.getPageElement("a1414").getValue();//批准机关级别
		String a1428 = this.getPageElement("a1428").getValue();//批准机关性质
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
		
		
		
		
		
		
		
		
		
		
		String a0163 = this.getPageElement("a0163").getValue();//人员状态
		String qtxzry = this.getPageElement("qtxzry").getValue();
		
		String finalsql = CommSQL.getCondiQuerySQL(userID,a01sb,a02sb,a02_a0201b_sb,cu_b0111_sb,orther_sb,a0163,qtxzry,sid);
		return finalsql;
	}
	
	
	@SuppressWarnings("unchecked")
	private String saveCondition(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, StringBuffer b0111desc) throws AppException, RadowException{
		List<Map<String,String>> condList = new ArrayList<Map<String,String>>();
		String fxyp00 =  this.getPageElement("fxyp00").getValue();//分析研判id
		
		if (!"".equals(a02_a0201b_sb.toString())){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "机构范围");
			m.put("cxtj07", "a02_a0201b_sb");
			m.put("cxtj04", a02_a0201b_sb.toString());
			m.put("cxtj08", "org");
			m.put("cxtj09", b0111desc.toString());
			condList.add(m);
		}else{
			List<Cxtj> cxtjlist = HBUtil.getHBSession().createQuery("from Cxtj where fxyp00=? and cxtj08='org'")
					.setString(0, fxyp00).list();
			if(cxtjlist.size()>0){
				Cxtj cxtj = cxtjlist.get(0);
				Map<String,String> m = new HashMap<String, String>();
				m.put("cxtj02", "机构范围");
				m.put("cxtj07", "a02_a0201b_sb");
				m.put("cxtj04", cxtj.getCxtj04());
				m.put("cxtj08", "org");
				m.put("cxtj09", cxtj.getCxtj09());
				condList.add(m);
			}
		}
		
		
		
		
		String a0195 = this.getPageElement("a0195").getValue();//统计关系所在单位
		if (!"".equals(a0195)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "统计关系所在单位");
			m.put("cxtj07", "a0195");
			m.put("cxtj04", a0195);
			m.put("cxtj08", "PublicTextIconEdit3");
			String a0195_combo = this.getPageElement("a0195_combo").getValue();//统计关系所在单位
			m.put("cxtj09", a0195_combo);
			condList.add(m);
		}
		
		
		String a0101 = this.getPageElement("a0101").getValue();//人员姓名
		if (!"".equals(a0101)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "人员姓名");
			m.put("cxtj07", "a0101");
			m.put("cxtj04", a0101);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a0101);
			condList.add(m);
		}
		
		String a0184 = this.getPageElement("a0184").getValue().toUpperCase();//身份证号
		if (!"".equals(a0184)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "身份证号");
			m.put("cxtj07", "a0184");
			m.put("cxtj04", a0184);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a0184);
			condList.add(m);
		}
		
		String a0111 = this.getPageElement("a0111").getValue();//籍贯
		String a0111_combo = this.getPageElement("a0111_combo").getValue();//籍贯
		if (!"".equals(a0111)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "籍贯");
			m.put("cxtj07", "a0111");
			m.put("cxtj04", a0111);
			m.put("cxtj08", "PublicTextIconEdit");
			m.put("cxtj09", a0111_combo);
			condList.add(m);
		}else{
			if(!"".equals(a0111_combo)){
				Map<String,String> m = new HashMap<String, String>();
				m.put("cxtj02", "籍贯");
				m.put("cxtj07", "a0111_combo");
				m.put("cxtj04", a0111_combo);
				m.put("cxtj08", "PublicTextIconEditText");
				condList.add(m);
			}
		}
		
		
		
		String a0104 = this.getPageElement("a0104").getValue();//性别
		if (!"".equals(a0104)&&!"0".equals(a0104)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "性别");
			m.put("cxtj07", "a0104");
			m.put("cxtj04", a0104);
			m.put("cxtj08", "radio");
			m.put("cxtj09", "1".equals(a0104)?"男":"女");
			condList.add(m);
		}
		
		String ageA = this.getPageElement("ageA").getValue();//年龄
		String ageB = this.getPageElement("ageB").getValue();//年龄
		if (!"".equals(ageA)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "年龄大于");
			m.put("cxtj07", "ageA");
			m.put("cxtj04", ageA);
			m.put("cxtj08", "numberEdit");
			m.put("cxtj09", ageA);
			condList.add(m);
		}
		if (!"".equals(ageB)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "年龄小于");
			m.put("cxtj07", "ageB");
			m.put("cxtj04", ageB);
			m.put("cxtj08", "numberEdit");
			m.put("cxtj09", ageB);
			condList.add(m);
		}
		
		
		String jiezsj = this.getPageElement("jiezsj").getValue();//截止时间
		if (!"".equals(jiezsj)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "截止时间");
			m.put("cxtj07", "jiezsj");
			m.put("cxtj04", jiezsj);
			m.put("cxtj08", "NewDateEditTag");
			//m.put("cxtj09", jiezsj);
			condList.add(m);
		}
		
		
		String a0160 = this.getPageElement("a0160").getValue();//人员类别
		if (!"".equals(a0160)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "人员类别");
			m.put("cxtj07", "a0160");
			m.put("cxtj04", a0160);
			m.put("cxtj08", "select2");
			String a0160_combo = this.getPageElement("a0160_combo").getValue();
			m.put("cxtj09", a0160_combo);
			condList.add(m);
		}
		
		
		
		
		String a0107A = this.getPageElement("a0107A").getValue();//出生年月
		String a0107B = this.getPageElement("a0107B").getValue();//出生年月
		if(!"".equals(a0107A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "出生年月大于");
			m.put("cxtj07", "a0107A");
			m.put("cxtj04", a0107A);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0107A);
			condList.add(m);
		}
		if(!"".equals(a0107B)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "出生年月小于");
			m.put("cxtj07", "a0107B");
			m.put("cxtj04", a0107B);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0107B);
			condList.add(m);
		}
		
		String a0144A = this.getPageElement("a0144A").getValue();//参加中共时间
		String a0144B = this.getPageElement("a0144B").getValue();//参加中共时间
		if(!"".equals(a0144A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "参加中共时间小于");
			m.put("cxtj07", "a0144A");
			m.put("cxtj04", a0144A);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0144A);
			condList.add(m);
		}
		if(!"".equals(a0144B)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "参加中共时间大于");
			m.put("cxtj07", "a0144B");
			m.put("cxtj04", a0144B);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0144B);
			condList.add(m);
		}
		
		
		
		String a0141 = this.getPageElement("a0141").getValue();//政治面貌
		if(!"".equals(a0141)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "政治面貌");
			m.put("cxtj07", "a0141");
			m.put("cxtj04", a0141);
			m.put("cxtj08", "select2");
			String a0141_combo = this.getPageElement("a0141_combo").getValue();//政治面貌
			m.put("cxtj09", a0141_combo);
			condList.add(m);
		}
		
		
		String a0192a = this.getPageElement("a0192a").getValue();//职务全称
		if(!"".equals(a0192a)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "职务全称");
			m.put("cxtj07", "a0192a");
			m.put("cxtj04", a0192a);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a0192a);
			condList.add(m);
		}
		
		
		
		String a0134A = this.getPageElement("a0134A").getValue();//参加工作时间
		String a0134B = this.getPageElement("a0134B").getValue();//参加工作时间
		if(!"".equals(a0134A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "参加工作时间大于");
			m.put("cxtj07", "a0134A");
			m.put("cxtj04", a0134A);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0134A);
			condList.add(m);
		}
		if(!"".equals(a0134B)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "参加工作时间大于");
			m.put("cxtj07", "a0134B");
			m.put("cxtj04", a0134B);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0134B);
			condList.add(m);
		}
		
		
		
		String a0114 = this.getPageElement("a0114").getValue();//出生地
		if (!"".equals(a0114)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "出生地");
			m.put("cxtj07", "a0114");
			m.put("cxtj04", a0114);
			m.put("cxtj08", "PublicTextIconEdit");
			String a0114_combo = this.getPageElement("a0114_combo").getValue();//出生地
			m.put("cxtj09", a0114_combo);
			condList.add(m);
		}
		
		
		
		String a0221A = this.getPageElement("a0221A").getValue();//职务层次
		String a0221B = this.getPageElement("a0221B").getValue();//职务层次
		if (!"".equals(a0221A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "职务层次高于");
			m.put("cxtj07", "a0221A");
			m.put("cxtj04", a0221A);
			m.put("cxtj08", "PublicTextIconEdit");
			String a0221A_combo = this.getPageElement("a0221A_combo").getValue();//职务层次
			m.put("cxtj09", a0221A_combo);
			condList.add(m);
		}
		if (!"".equals(a0221B)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "职务层次低于");
			m.put("cxtj07", "a0221B");
			m.put("cxtj04", a0221B);
			m.put("cxtj08", "PublicTextIconEdit");
			String a0221B_combo = this.getPageElement("a0221B_combo").getValue();//职务层次
			m.put("cxtj09", a0221B_combo);
			condList.add(m);
		}
		
		
		
		
		String a0288A = this.getPageElement("a0288A").getValue();//任现职务层次时间
		String a0288B = this.getPageElement("a0288B").getValue();//任现职务层次时间
		if(!"".equals(a0288A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "任现职务层次时间大于");
			m.put("cxtj07", "a0288A");
			m.put("cxtj04", a0288A);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0288A);
			condList.add(m);
		}
		if(!"".equals(a0288B)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "任现职务层次时间小于");
			m.put("cxtj07", "a0288B");
			m.put("cxtj04", a0288B);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0288B);
			condList.add(m);
		}
		
		String a0117 = this.getPageElement("a0117").getValue();//名族
		if(!"".equals(a0117)&&!"0".equals(a0117)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "名族");
			m.put("cxtj07", "a0117");
			m.put("cxtj04", a0117);
			m.put("cxtj08", "radio");
			m.put("cxtj09", "01".equals(a0117)?"汉族":"少数民族");
			condList.add(m);
		}
		
		
		
		
		String a1701 = this.getPageElement("a1701").getValue();//简历
		if(!"".equals(a1701)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "简历");
			m.put("cxtj07", "a1701");
			m.put("cxtj04", a1701);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a1701);
			condList.add(m);
		}
		
		
		
		
		String a0192e = this.getPageElement("a0192e").getValue();//现职级
		if(!"".equals(a0192e)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "现职级");
			m.put("cxtj07", "a0192e");
			m.put("cxtj04", a0192e);
			m.put("cxtj08", "PublicTextIconEdit");
			String a0192e_combo = this.getPageElement("a0192e_combo").getValue();//现职级
			m.put("cxtj09", a0192e_combo);
			condList.add(m);
		}
		
		
		
		
		String a0192cA = this.getPageElement("a0192cA").getValue();//任职级时间
		String a0192cB = this.getPageElement("a0192cB").getValue();//任职级时间
		if(!"".equals(a0192cA)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "任职级时间小于");
			m.put("cxtj07", "a0192cA");
			m.put("cxtj04", a0192cA);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0192cA);
			condList.add(m);
		}
		if(!"".equals(a0192cB)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "任职级时间小于");
			m.put("cxtj07", "a0192cB");
			m.put("cxtj04", a0192cB);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0192cB);
			condList.add(m);
		}
		
		
		
		
		String a0165 = this.getPageElement("a0165").getValue();//人员管理类别
		if(!"".equals(a0165)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "人员管理类别");
			m.put("cxtj07", "a0165");
			m.put("cxtj04", a0165);
			m.put("cxtj08", "select2");
			String a0165_combo = this.getPageElement("a0165_combo").getValue();//人员管理类别
			m.put("cxtj09", a0165_combo);
			condList.add(m);
		}
		
		
		
		//职务 StringBuffer a02sb = new StringBuffer("");
		
		String a0216a = this.getPageElement("a0216a").getValue();//职务名称
		if(!"".equals(a0216a)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "职务名称");
			m.put("cxtj07", "a0216a");
			m.put("cxtj04", a0216a);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a0216a);
			condList.add(m);
		}
		
		
		
		String a0201d = this.getPageElement("a0201d").getValue();//是否班子成员
		if(!"".equals(a0201d)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "是否班子成员");
			m.put("cxtj07", "a0201d");
			m.put("cxtj04", a0201d);
			m.put("cxtj08", "select2");
			String a0201d_combo = this.getPageElement("a0201d_combo").getValue();//是否班子成员
			m.put("cxtj09", a0201d_combo);
			condList.add(m);
		}
		
		
		
		
		String a0219 = this.getPageElement("a0219").getValue();//是否领导职务
		if(!"".equals(a0219)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "是否领导职务");
			m.put("cxtj07", "a0219");
			m.put("cxtj04", a0219);
			m.put("cxtj08", "select2");
			String a0219_combo = this.getPageElement("a0219_combo").getValue();//是否领导职务
			m.put("cxtj09", a0219_combo);
			condList.add(m);
		}
		
		
		
		
		String a0201e = this.getPageElement("a0201e").getValue();//成员类别
		if(!"".equals(a0201e)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "成员类别");
			m.put("cxtj07", "a0201e");
			m.put("cxtj04", a0201e);
			m.put("cxtj08", "select2");
			String a0201e_combo = this.getPageElement("a0201e_combo").getValue();//成员类别
			m.put("cxtj09", a0201e_combo);
			condList.add(m);
		}
		
		
		//最高学历
		String xla0801b = this.getPageElement("xla0801b").getValue();//最高学历  学历代码
		String xla0814 = this.getPageElement("xla0814").getValue();//毕业院校
		String xla0824 = this.getPageElement("xla0824").getValue();//专业
		if(!"".equals(xla0801b)&&!"0".equals(xla0801b)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "最高学历");
			m.put("cxtj07", "xla0801b");
			m.put("cxtj04", xla0801b);
			m.put("cxtj08", "radio");
			String xla0801bdesc = "";
			if("1".equals(xla0801b)){
				xla0801bdesc = "研究生";
			}else if("2".equals(xla0801b)){
				xla0801bdesc = "大学";
			}else if("3".equals(xla0801b)){
				xla0801bdesc = "大专";
			}else if("4".equals(xla0801b)){
				xla0801bdesc = "中专";
			}else if("6,7,8,9".equals(xla0801b)){
				xla0801bdesc = "其他";
			}
			m.put("cxtj09", xla0801bdesc);
			condList.add(m);
		}
		if(!"".equals(xla0814)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "最高学历毕业院校");
			m.put("cxtj07", "xla0814");
			m.put("cxtj04", xla0814);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", xla0814);
			condList.add(m);
		}
		if(!"".equals(xla0824)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "最高学历专业");
			m.put("cxtj07", "xla0824");
			m.put("cxtj04", xla0824);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", xla0824);
			condList.add(m);
		}
		
		
		
		//最高学位
		String xwa0901b = this.getPageElement("xwa0901b").getValue();//最高学位 学位代码
		String xwa0814 = this.getPageElement("xwa0814").getValue();//毕业院校
		String xwa0824 = this.getPageElement("xwa0824").getValue();//专业
		if(!"".equals(xwa0901b)&&!"0".equals(xwa0901b)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "最高学位");
			m.put("cxtj07", "xwa0901b");
			m.put("cxtj04", xwa0901b);
			m.put("cxtj08", "radio");
			String xwa0901bdesc = "";
			if("1,2".equals(xwa0901b)){
				xwa0901bdesc = "博士";
			}else if("3".equals(xwa0901b)){
				xwa0901bdesc = "硕士";
			}else if("4".equals(xwa0901b)){
				xwa0901bdesc = "学士";
			}
			m.put("cxtj09", xwa0901bdesc);
			condList.add(m);
		}
		if(!"".equals(xwa0814)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "最高学位毕业院校");
			m.put("cxtj07", "xwa0814");
			m.put("cxtj04", xwa0814);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", xwa0814);
			condList.add(m);
		}
		if(!"".equals(xwa0824)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "最高学位专业");
			m.put("cxtj07", "xwa0824");
			m.put("cxtj04", xwa0824);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", xwa0824);
			condList.add(m);
		}
		
		
		
		//职称
		String a0601 = this.getPageElement("a0601").getValue();//专业技术任职资格
			
		if (!"".equals(a0601)&&!"0".equals(a0601)) {
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "职称");
			m.put("cxtj07", "a0601");
			m.put("cxtj04", a0601);
			m.put("cxtj08", "radio");
			String a0601desc = "";
			if("1".equals(a0601)){
				a0601desc = "正高";
			}else if("2".equals(a0601)){
				a0601desc = "副高";
			}else if("3".equals(a0601)){
				a0601desc = "中级";
			}else if("4,5".equals(a0601)){
				a0601desc = "初级";
			}else if("9".equals(a0601)){
				a0601desc = "无职称";
			}
			m.put("cxtj09", a0601desc);
			condList.add(m);
		}
		
		
		
		//家庭成员
		String a3601 = this.getPageElement("a3601").getValue();//姓名
		String a3611 = this.getPageElement("a3611").getValue();//工作单位及职务
		if(!"".equals(a3601)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "家庭成员姓名");
			m.put("cxtj07", "a3601");
			m.put("cxtj04", a3601);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a3601);
			condList.add(m);
		}


		if(!"".equals(a3611)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "家庭成员工作单位及职务");
			m.put("cxtj07", "a3611");
			m.put("cxtj04", a3611);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a3611);
			condList.add(m);
		}
			
		
		
		
		
		
		
		
		
		
		
		String a0163 = this.getPageElement("a0163").getValue();//人员状态
		if(!"".equals(a0163)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "人员状态");
			m.put("cxtj07", "a0163");
			m.put("cxtj04", a0163);
			m.put("cxtj08", "select2");
			String a0163_combo = this.getPageElement("a0163_combo").getValue();//人员状态
			m.put("cxtj09", a0163_combo);
			condList.add(m);
		}
		String qtxzry = this.getPageElement("qtxzry").getValue();
		if(!"".equals(qtxzry)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "其他现职人员（无职务）");
			m.put("cxtj07", "qtxzry");
			m.put("cxtj04", qtxzry);
			m.put("cxtj08", "checkbox");
			if("1".equals(qtxzry)){
				m.put("cxtj09", "是");
			}
			condList.add(m);
		}
		HBSession sess = null;
		PreparedStatement ps = null;
		Connection conn = null;
		
		
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			
			
			if(condList.size()>0){
				
				StringBuilder desc = new StringBuilder();
				int tiaoJianXuHao = 1;
				for(Map<String, String> m : condList){
					if(m.get("cxtj09")!=null){
						desc.append(tiaoJianXuHao++ +"、"+m.get("cxtj02")+"："+m.get("cxtj09")+"\n");
					}
				}
				if(fxyp00==null||"".equals(fxyp00)){//新增
					ps = conn.prepareStatement("insert into fxyp(fxyp00,fxyp03) values(?,?)");
					fxyp00 = UUID.randomUUID().toString();
					ps.setString(1, fxyp00);
					ps.setString(2, desc.toString());
					ps.executeUpdate();
				}else{//清除条件
					ps = conn.prepareStatement("update fxyp set fxyp03=? where fxyp00=?");
					ps.setString(1, desc.toString());
					ps.setString(2, fxyp00);
					ps.executeUpdate();
					
					ps = conn.prepareStatement("delete from cxtj where fxyp00=?");
					ps.setString(1, fxyp00);
					ps.executeUpdate();
				}
				
				
				int paixu = 0;
				StringBuilder sql = new StringBuilder("insert into cxtj(cxtj00,cxtj01,cxtj02,cxtj04,"
						+ "cxtj07,cxtj08,cxtj09,fxyp00)"
						+ "values(?,?,?,?,?,?,?,?) ");
				ps = conn.prepareStatement(sql.toString());
				for(Map<String, String> m : condList){
					int fsize = 1;
					ps.setString(fsize++, UUID.randomUUID().toString());
					ps.setInt(fsize++, paixu);
					ps.setString(fsize++, m.get("cxtj02"));
					ps.setString(fsize++, m.get("cxtj04"));
					ps.setString(fsize++, m.get("cxtj07"));
					ps.setString(fsize++, m.get("cxtj08"));
					ps.setString(fsize++, m.get("cxtj09"));
					ps.setString(fsize++, fxyp00);
					ps.addBatch();
					paixu++;
				}
				ps.executeBatch();
					
				ps.close();
				conn.commit();
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			try{
				if(conn!=null)
					conn.rollback();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				e.printStackTrace();
				throw new AppException("保存失败");
				
			}
			e.printStackTrace();
			throw new AppException("保存失败");
		}
		return fxyp00;
	}
	
	
}




