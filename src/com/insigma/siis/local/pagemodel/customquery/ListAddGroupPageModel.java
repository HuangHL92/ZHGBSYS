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
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ListAddGroupPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		//this.setNextEventName("initX");
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
				return 0;
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
	 * ????????????????????????????????????????????????????????????????????????????????????
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("mQueryonclick")
	public int query() throws RadowException, AppException{
		
		this.request.getSession().setAttribute("queryType", "1");
		String userID = SysManagerUtils.getUserId();
        //??????????????????????????
        String tableType = this.getPageElement("tableType").getValue();
        String appendSql = this.getPageElement("sql").getValue();
        if(appendSql == null || "".equals(appendSql)){
        	this.getExecuteSG().addExecuteCode("$h.alert('??????????','??????????????????????',null,200);");
			return EventRtnType.FAILD;
        }
        if(appendSql.indexOf("sessionid") > 0){
        	appendSql=appendSql.replace(appendSql.substring(appendSql.indexOf(","), appendSql.indexOf("sessionid")+"sessionid".length()), " ");
        }
        
        String middleSql = getSQL(userID,appendSql);
        this.getPageElement("sql").setValue(middleSql);
        this.getExecuteSG().addExecuteCode("realParent.document.getElementById('sql').value=document.getElementById('sql').value");
        this.request.getSession().setAttribute("listAddGroupSession", "finalsql.toString()");
        Map<String, Boolean> m = new HashMap<String, Boolean>();
        m.put("paixu", true);
        this.request.getSession().setAttribute("queryConditionsCQ",m);
        this.getExecuteSG().addExecuteCode("realParent.document.getElementById('checkedgroupid').value=''");
        this.getExecuteSG().addExecuteCode("realParent.document.getElementById('tabn').value='tab2'");
        if("1".equals(tableType)){
        	this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('peopleInfoGrid.dogridquery');");
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
		return EventRtnType.NORMAL_SUCCESS;
        
	}
	/**
	 * 
	 * @param userID
	 * @param appendSql
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	private String getSQL(String userID, String appendSql) throws AppException, RadowException{
		StringBuffer a01sb = new StringBuffer("");
		StringBuffer a02sb = new StringBuffer("");
		StringBuffer orther_sb = new StringBuffer("");
		String sid = this.request.getSession().getId();
		
		
		String a0195 = this.getPageElement("a0195").getValue();//????????????????
		if (!"".equals(a0195)){
			a01sb.append(" and ");
			a01sb.append("a01.a0195 = '"+a0195+"'");
		}
		
		String a0101 = this.getPageElement("a0101").getValue();//????????
		if (!"".equals(a0101)){
			a01sb.append(" and ");
			a01sb.append("a01.a0101 like '"+a0101+"%'");
		}
		
		String a0184 = this.getPageElement("a0184").getValue().toUpperCase();//????????
		if (!"".equals(a0184)){
			a01sb.append(" and ");
			a01sb.append("a01.a0184 like '"+a0184+"%'");
		}
		
		String a0111_combo = this.getPageElement("a0111_combo").getValue();//????
		if(!"".equals(a0111_combo)){
			a01sb.append(" and ");
			a01sb.append(" a01.a0111a like '%"+a0111_combo+"%' ");
		}
		
		String a0104 = this.getPageElement("a0104").getValue();//????
		if (!"".equals(a0104)&&!"0".equals(a0104)){
			a01sb.append(" and ");
			a01sb.append("a01.a0104 = '"+a0104+"'");
		}
		
		String ageA = this.getPageElement("ageA").getValue();//????
		String ageB = this.getPageElement("ageB").getValue();//????
		if(!"".equals(ageB) && StringUtils.isNumeric(ageB)){//????????
			ageB = Integer.valueOf(ageB)+1+"";
		}
		String jiezsj = this.getPageElement("jiezsj").getValue();//????????
		String dateEnd = GroupPageBS.getDateformY(ageA, jiezsj);
		String dateStart = GroupPageBS.getDateformY(ageB, jiezsj);
		if(!"".equals(dateEnd)&&!"".equals(dateStart)&&dateEnd.compareTo(dateStart)<0){
			throw new AppException("??????????????");
		}
		if(!"".equals(dateStart)){
			a01sb.append(" and a01.a0107>='"+dateStart+"'");
		}
		if(!"".equals(dateStart)){
			a01sb.append(" and a01.a0107<'"+dateEnd+"'");
		}
		
		String a0160 = this.getPageElement("a0160").getValue();//????????
		if (!"".equals(a0160)){
			a01sb.append(" and ");
			a01sb.append("a01.a0160 = '"+a0160+"'");
		}
		
		
		
		
		String a0107A = this.getPageElement("a0107A").getValue();//????????
		String a0107B = this.getPageElement("a0107B").getValue();//????????
		if(!"".equals(a0107A)){
			a01sb.append(" and a01.a0107>='"+a0107A+"'");
		}
		if(!"".equals(a0107B)){
			a01sb.append(" and a01.a0107<='"+a0107B+"'");
		}
		
		String a0144A = this.getPageElement("a0144A").getValue();//????????????
		String a0144B = this.getPageElement("a0144B").getValue();//????????????
		if(!"".equals(a0144A)){
			a01sb.append(" and a01.a0144>='"+a0144A+"'");
		}
		if(!"".equals(a0144B)){
			a01sb.append(" and a01.a0144<='"+a0144B+"'");
		}
		
		
		
		String a0141 = this.getPageElement("a0141").getValue();//????????
		if(!"".equals(a0141)){
			a01sb.append(" and a01.a0141='"+a0141+"'");
		}
		
		
		String a0192a = this.getPageElement("a0192a").getValue();//????????
		if(!"".equals(a0192a)){
			a01sb.append(" and a01.a0192a like '%"+a0192a+"%'");
		}
		
		
		
		String a0134A = this.getPageElement("a0134A").getValue();//????????????
		String a0134B = this.getPageElement("a0134B").getValue();//????????????
		if(!"".equals(a0134A)){
			a01sb.append(" and a01.a0134>='"+a0134A+"'");
		}
		if(!"".equals(a0134B)){
			a01sb.append(" and a01.a0134<='"+a0134B+"'");
		}
		
		
		
		String a0114_combo = this.getPageElement("a0114_combo").getValue();//??????
		if (!"".equals(a0114_combo)){
			a01sb.append(" and a01.a0114A like '%"+a0114_combo+"%' ");
		}
		
		
		
		String a0221A = this.getPageElement("a0221A").getValue();//????????
		String a0221B = this.getPageElement("a0221B").getValue();//????????
		if(!StringUtil.isEmpty(a0221A) && !StringUtil.isEmpty(a0221B)){
			CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", a0221A);
			CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", a0221B);
			if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
				throw new AppException("????????????????????????????????????");
			}
			//???????? ?????? ??????????????
			if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
				throw new AppException("????????????????????????????");
			}
		}
		if(!"".equals(a0221A)){
			a01sb.append(" and a01.a0221<='"+a0221A+"'");
		}
		if(!"".equals(a0221B)){
			a01sb.append(" and a01.a0221>='"+a0221B+"'");
		}
		
		String a0288A = this.getPageElement("a0288A").getValue();//????????????????
		String a0288B = this.getPageElement("a0288B").getValue();//????????????????
		if(!"".equals(a0288A)){
			a01sb.append(" and a01.a0288>='"+a0288A+"'");
		}
		if(!"".equals(a0288B)){
			a01sb.append(" and a01.a0288<='"+a0288B+"'");
		}
		
		String xgsjA = this.getPageElement("xgsjA").getValue();//????????????
		String xgsjB = this.getPageElement("xgsjB").getValue();//????????????
		if(!"".equals(xgsjA)){
			a01sb.append(" and a01.xgsj>="+CommSQL.to_date(xgsjA));
		}
		if(!"".equals(a0288B)){
			a01sb.append(" and a01.xgsj<="+CommSQL.adddate(CommSQL.to_date(xgsjB)));
		}
		
		String a0117 = this.getPageElement("a0117v").getValue();//????????????
		if(!"".equals(a0117)){
			String[] a0117s = a0117.split(",");
			if(a0117s.length==1){
				if("01".equals(a0117s[0]))
					a01sb.append(" and a01.a0117 ='01'");
				else
					a01sb.append(" and a01.a0117 !='01'");
			}
			
		}
		
		String a1701 = this.getPageElement("a1701").getValue();//????
		if(!"".equals(a1701)){
			a01sb.append(" and a01.a1701 like '%"+a1701+"%'");
		}
		
		String a0192e = this.getPageElement("a0192e").getValue();//??????
		if(!"".equals(a0192e)){
			a01sb.append(" and a01.a0192e='"+a0192e+"'");
		}
		
		String a0192cA = this.getPageElement("a0192cA").getValue();//??????????
		String a0192cB = this.getPageElement("a0192cB").getValue();//??????????
		if(!"".equals(a0192cA)){
			a01sb.append(" and a01.a0192c>='"+a0192cA+"'");
		}
		if(!"".equals(a0192cB)){
			a01sb.append(" and a01.a0192c<='"+a0192cB+"'");
		}
		
		String a0165 = this.getPageElement("a0165v").getValue();//????????????
		if(!"".equals(a0165)){
			a0165 = a0165.replace(",", "','");
			a01sb.append(" and a01.a0165 in('"+a0165+"')");
		}
		
		//???? StringBuffer a02sb = new StringBuffer("");
		
		String a0216a = this.getPageElement("a0216a").getValue();//????????
		if(!"".equals(a0216a)){
			a02sb.append(" and a02.a0216a like '%"+a0216a+"%'");
		}
		
		String a0201d = this.getPageElement("a0201d").getValue();//????????????
		if(!"".equals(a0201d)){
			a02sb.append(" and a02.a0201d='"+a0201d+"'");
		}
		
		String a0219 = this.getPageElement("a0219").getValue();//????????????
		if(!"".equals(a0219)){
			a02sb.append(" and a02.a0219='"+a0219+"'");
		}
		
		String a0201e = this.getPageElement("a0201ev").getValue();//????????
		if(!"".equals(a0201e)){
			a0201e = a0201e.replace(",", "','");
			a02sb.append(" and a02.a0201e in('"+a0201e+"')");
		}
		
		
		//????????
		String xla0801b = this.getPageElement("xla0801bv").getValue();//????????  ????????
		String xla0814 = this.getPageElement("xla0814").getValue();//????????
		String xla0824 = this.getPageElement("xla0824").getValue();//????
		xuelixueweiSQL(xla0801b,xla0814,xla0824,orther_sb,"a0834","a0801b");
		
		
		//????????
		String xwa0901b = this.getPageElement("xwa0901bv").getValue();//???????? ????????
		String xwa0814 = this.getPageElement("xwa0814").getValue();//????????
		String xwa0824 = this.getPageElement("xwa0824").getValue();//????
		xuelixueweiSQL(xwa0901b,xwa0814,xwa0824,orther_sb,"a0835","a0901b");
		
		
		//??????????????
		String qrzxla0801b = this.getPageElement("qrzxla0801bv").getValue();//????????  ????????
		String qrzxla0814 = this.getPageElement("qrzxla0814").getValue();//????????
		String qrzxla0824 = this.getPageElement("qrzxla0824").getValue();//????
		xuelixueweiSQL(qrzxla0801b,qrzxla0814,qrzxla0824,orther_sb,"a0831","a0801b");
		
		
		//??????????????
		String qrzxwa0901b = this.getPageElement("qrzxwa0901bv").getValue();//???????? ????????
		String qrzxwa0814 = this.getPageElement("qrzxwa0814").getValue();//????????
		String qrzxwa0824 = this.getPageElement("qrzxwa0824").getValue();//????
		xuelixueweiSQL(qrzxwa0901b,qrzxwa0814,qrzxwa0824,orther_sb,"a0832","a0901b");
		
		//????????????
		String zzxla0801b = this.getPageElement("zzxla0801bv").getValue();//????????  ????????
		String zzxla0814 = this.getPageElement("zzxla0814").getValue();//????????
		String zzxla0824 = this.getPageElement("zzxla0824").getValue();//????
		xuelixueweiSQL(zzxla0801b,zzxla0814,zzxla0824,orther_sb,"a0838","a0801b");
		
		//????????????
		String zzxwa0901b = this.getPageElement("zzxwa0901bv").getValue();//???????? ????????
		String zzxwa0814 = this.getPageElement("zzxwa0814").getValue();//????????
		String zzxwa0824 = this.getPageElement("zzxwa0824").getValue();//????
		xuelixueweiSQL(zzxwa0901b,zzxwa0814,zzxwa0824,orther_sb,"a0839","a0901b");
		
		//????
		String a14z101 = this.getPageElement("a14z101").getValue();//????????
		String lba1404b = this.getPageElement("lba1404bv").getValue();//????????
		String a1404b = this.getPageElement("a1404b").getValue();//????????????
		String a1415 = this.getPageElement("a1415").getValue();//????????????????
		String a1414 = this.getPageElement("a1414").getValue();//????????????
		String a1428 = this.getPageElement("a1428").getValue();//????????????
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
		
		//????
		String a0601 = this.getPageElement("a0601v").getValue();//????????????????
			
		if (!"".equals(a0601)) {
			boolean is9 = false;
			orther_sb.append(" and (a01.a0000 in (select a0000 from a06 where a0699='true' ");
			StringBuffer like_sb = new StringBuffer("");
			String[] fArray = a0601.split(",");
			for (int i = 0; i < fArray.length; i++) {
				
				if("9".equals(fArray[i])){
					like_sb.append(" a0601 is null or a0601='999' or ");//??????
					is9 = true;
				}else{
					like_sb.append(" a0601 like '%" + fArray[i] + "' or ");
				}
			}
			like_sb.delete(like_sb.length() - 4, like_sb.length() - 1);
			orther_sb.append(" and (" + like_sb.toString() + ")");
			orther_sb.append(")");
			if(is9){//??????
				orther_sb.append(" or not exists (select 1 from a06 where a01.a0000=a06.a0000 and  a0699='true')");
			}
			orther_sb.append(")");
		}
		
		//????????
		String a15z101 = this.getPageElement("a15z101").getValue();//????????????
		String a1521 = this.getPageElement("a1521").getValue();//????????
		String a1517 = this.getPageElement("a1517").getValue();//????????????
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
		
		//????????
		String a3601 = this.getPageElement("a3601").getValue();//????
		String a3684 = this.getPageElement("a3684").getValue();//????????
		String a3611 = this.getPageElement("a3611").getValue();//??????????????
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
				throw new AppException("????????????????????!");
		}*/
		
		
		String a0163 = this.getPageElement("a0163").getValue();//????????
		String qtxzry = this.getPageElement("qtxzry").getValue();
		
		String finalsql = CommSQL.getCondiQuerySQL(userID,a01sb,a02sb,new StringBuffer(""),new StringBuffer(""),orther_sb,a0163,qtxzry,sid);
		String replace = finalsql.replace(finalsql.substring(finalsql.indexOf(","), finalsql.indexOf("sessionid")+"sessionid".length()), " ");
		return finalsql.replace(finalsql.substring(finalsql.indexOf(","), finalsql.indexOf("sessionid")+"sessionid".length()), " ")+ " and a01.a0000 in ("+appendSql+")";
	}
	
	//????????
	private void xuelixueweiSQL(String a0801b, String a0814, String a0824, StringBuffer orther_sb, String highField, String xueliORxuewei) {
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
}




