package com.insigma.siis.local.pagemodel.edu;


import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Train;

public class pxxxPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {

		this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("editgrid.dogridquery")
	@NoRequiredValidate         
	public int grid1Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String a0101=this.getPageElement("a0101").getValue();
		String a0104=this.getPageElement("a0104").getValue();
		String xrdx05=this.getPageElement("xrdx05").getValue();
		String a0165B = this.getPageElement("a0165B").getValue();
		String a0165C = this.getPageElement("a0165C").getValue();
		String extraSql="";
		String timeSql="";
		String orderSql=" ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' )  t where rn=1 and t.a0000=r.a0000)) ";
		
		//姓名
		a0101 = a0101.replace("，", ",");
		if(a0101!=null && !"".equals(a0101)) {
			String[] names = a0101.trim().split(",|，");
			//如果存在多个名字
			if(names.length>1){
				extraSql+=" and ( ";
				for(int i=0;i<names.length;i++){
					extraSql+= "  (select a0101 from a01 where a01.a0000 = r.a0000) like '%"+names[i]+"%'  ";
					if(i!=names.length-1) {
						extraSql+=" or ";
					}
				}
				extraSql +=" ) ";
			//单个名字
			}else {
				extraSql += " and  (select a0101 from a01 where a01.a0000 = r.a0000) like '%"+a0101+"%' ";
			}
			
		}
		
		//性别
		if(a0104!=null && !"".equals(a0104)) {
			extraSql += " and  (select a0104 from a01 where a01.a0000 = r.a0000)='"+a0104+"' ";
		}
		
		//开班时间大于
		if(xrdx05!=null && !"".equals(xrdx05)) {
			extraSql += " and  t.xrdx05>="+xrdx05.replace("-", "")+" ";
			timeSql += " and  t1.xrdx05>="+xrdx05.replace("-", "")+" ";
		}
		
		//市管干部
		if(a0165B!=null && !"".equals(a0165B)) {
			String[] strs = a0165B.split(",");
			String a0165Bsql="select a0000 from a01 where ( ";
			for (int i = 0; i < strs.length; i++) {
				a0165Bsql+=" a01.a0165 like '%" + strs[i] + "%' ";
				if (i != strs.length - 1) {
					a0165Bsql+=" or ";
				}
			}
			extraSql+=" and r.a0000 in ("+a0165Bsql+"))";
		}
		
		// 年龄
		String a0107A = this.getPageElement("a0107A").getValue();
		String a0107B = this.getPageElement("a0107B").getValue();
//		if (ageB!=null&&!"".equals(ageB) && StringUtils.isNumeric(ageB)) {// 是否数字
//			ageB = Integer.valueOf(ageB) + 1 + "";
//		}
//		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM");
//		String now = sf.format(System.currentTimeMillis()).replace("-", "");
		if (a0107A!=null&&!"".equals(a0107A)) {
			extraSql+=(" and r.a0000 in (select a0000 from a01 where a0163='1' and substr(a01.a0107,0,6)>='" + a0107A.substring(0,6) + "') ");
		}
		if (a0107B!=null&&!"".equals(a0107B)) {
			extraSql+=(" and r.a0000 in (select a0000 from a01 where a0163='1' and substr(a01.a0107,0,6)<='" + a0107B.substring(0,6) +  "')");
		}
		
		//处级干部
		if(a0165C!=null && !"".equals(a0165C)) {
			String[] strs = a0165C.split(",");
			String a0165Csql="select a0000 from a01 where ( ";
			for (int i = 0; i < strs.length; i++) {
				a0165Csql+=" a01.a0165 like '%" + strs[i] + "%' ";
				if (i != strs.length - 1) {
					a0165Csql+=" or ";
				}
			}
			extraSql+=" and r.a0000 in ("+a0165Csql+"))";
		}
		
		//任现职务层次时间
		String a0288 = this.getPageElement("a0288").getValue().replace("-", "");
		if(a0288!=null && !"".equals(a0288)) {
			extraSql += " and  (select substr(a0288,0,6) from a01 where a01.a0000 = r.a0000)<="+a0288.substring(0, 6)+" ";
		}
		
		//学制大于
		String xrdx08 = this.getPageElement("xrdx08").getValue();
		if(xrdx08!=null && !"".equals(xrdx08)) {
			extraSql += " and  t.xrdx08>="+xrdx08+" ";
			timeSql += " and  t1.xrdx08>="+xrdx08+" ";
		}
		
		//合计学时小于
		String xrdx09 = this.getPageElement("xrdx09").getValue();
		if(xrdx09!=null && !"".equals(xrdx09)) {
			extraSql += " and (select sum(t1.xrdx09) from edu_xrdx t1, edu_xrdx_ry t2  where t1.xrdx00=t2.xrdx00  and t2.a0000=r.a0000 and t1.xrdx02<>'0203' "+timeSql+") < "+xrdx09+" ";
		}
		
		//上次培训
		String scpxsc = this.getPageElement("scpxsc").getValue();
		String scpxsj = this.getPageElement("scpxsj").getValue().replace("-", "");
		String sc="14";
		if(scpxsc!=null&&!"".equals(scpxsc)) {
			sc=scpxsc;
		}
		if (scpxsj!=null&&!"".equals(scpxsj)) {
			extraSql +=" and not exists (select 1 from edu_xrdx t3, edu_xrdx_ry t4 " + 
					" where t3.xrdx00=t4.xrdx00 and t4.a0000=r.a0000  and t3.xrdx02<>'0203' and rownum=1 and substr(t3.xrdx06,0,6)>="+scpxsj.substring(0,6)+" and t3.xrdx08>="+sc+") " ;
		}
		
		//省管干部
		String sggb = this.getPageElement("sggb").getValue();
		if(!"1".equals(sggb)) {
			extraSql += " and r.a0000 in (select a0000 from a01 where instr(a01.a0165,'02')<=0  and instr(a01.a0165,'05')<=0  and a01.a0163 = '1') ";
		}
		
		//培训名称和类型
		String xrdx01 = this.getPageElement("xrdx01").getValue();
		String xrdx02 = this.getPageElement("xrdx02").getValue();
		if (xrdx01!=null&&!"".equals(xrdx01)) {
			extraSql += " and  t.xrdx01 like '%"+xrdx01+"%' ";
			timeSql += " and  t1.xrdx01 like '%"+xrdx01+"%' ";
		}
		if (xrdx02!=null&&!"".equals(xrdx02)) {
			xrdx02=xrdx02.replace(",", "','");
			extraSql += " and  t.xrdx02 in ('"+xrdx02+"')";
			timeSql += " and  t1.xrdx02 in ('"+xrdx02+"')";
		}
		//培训类型
		String type = this.getPageElement("type").getValue();
		if (type!=null&&!"".equals(type)) {
			type=type.replace(",", "','");
			extraSql += " and  t.type in ('"+type+"') ";
			timeSql += " and  t1.type in ('"+type+"') ";
		}
		
		//党派
		String a0141 = this.getPageElement("a0141").getValue();
//		if(a0141!=null && !"".equals(a0141)) {
//			extraSql += " and  (select a0141 from a01 where a01.a0000 = r.a0000)='"+a0141+"' ";
//		}
		if (a0141!=null&&!"".equals(a0141)) {
			String[] strs = a0141.split(",");
			extraSql+=" and (";
			for(int i=0;i<strs.length;i++) {
				extraSql+=" (select a0141 from a01 where a01.a0000 = r.a0000) = '"+strs[i]+"' ";
				if(i!=strs.length-1) {
					extraSql+=" or ";
				}
			}
			extraSql+=") ";
		}
  
		
		String sql=" select r.a0000," + 
				" (select a0101 from a01 where a01.a0000=r.a0000) a0101," + 
				" (select a0184 from a01 where a01.a0000=r.a0000) a0184," + 
				" (select a0140 from a01 where a01.a0000=r.a0000) a0140," + 
				" (select a3707c from a37 where a37.a0000=r.a0000) a3707c," + 
				" (select b0101 from b01,a02 where a02.a0000=r.a0000 and b01.b0111=a02.a0201b and a0279='1' and a0281='true' ) b0101,"+
				"  (select to_char(wm_concat(code_name)) " + 
				"          from (select * from code_value order by inino),a01 " + 
				"         where code_type = 'ZB130' and a01.a0000=r.a0000 " + 
				"           and a01.a0165 like '%' || code_value || '%') a0165 ," + 
				" (select code_name from code_value,a01 where a01.a0000=r.a0000 and a01.a0104=code_value and code_type='GB2261') a0104," + 
				" (select substr(a0107,0,4) || '.'||  substr(a0107,5,2) from a01 where a01.a0000=r.a0000) a0107," + 
				" (select decode(substr(a01.a0288, 0, 4) || '.' || substr(a01.a0288, 5, 2),  '.', null,substr(a01.a0288, 0, 4) || '.' || substr(a01.a0288, 5, 2))  from a01 where a01.a0000=r.a0000 " + 
				" ) a0288," + 
				" (select a0192a from a01 where a01.a0000=r.a0000) a0192a," + 
				" listagg (nvl(t.xrdx00,' '), ',') WITHIN GROUP (ORDER BY t.xrdx00) xrdx00," + 
				" listagg (nvl(t.xrdx01,' '), '@') WITHIN GROUP (ORDER BY t.xrdx00) xrdx01," + 
				" listagg (nvl((select code_name from code_value where code_type='PXLX' and code_value=t.xrdx02),' '), ',') WITHIN GROUP (ORDER BY t.xrdx00) xrdx02," + 
				" listagg (nvl((select code_name from code_value where code_type='TRANORG' and code_value=t.xrdx03),' '), ',') WITHIN GROUP (ORDER BY t.xrdx00) xrdx03," + 
				" listagg (nvl((select code_name from code_value where code_type='TRANCRJ' and code_value=t.xrdx04),' '), ',') WITHIN GROUP (ORDER BY t.xrdx00) xrdx04," + 
				" listagg (nvl(substr(t.xrdx05,0,4) || '.'||  substr(t.xrdx05,5,2) || '.' || substr(t.xrdx05,7,2) ,' '), ',') WITHIN GROUP (ORDER BY t.xrdx00) xrdx05," + 
				" listagg (nvl(substr(t.xrdx06,0,4) || '.'||  substr(t.xrdx06,5,2) || '.' || substr(t.xrdx06,7,2),' '), ',') WITHIN GROUP (ORDER BY t.xrdx00) xrdx06," + 
				" listagg (nvl(t.xrdx07,' '), ',') WITHIN GROUP (ORDER BY t.xrdx00) xrdx07," + 
				" listagg (nvl(t.xrdx08,' '), ',') WITHIN GROUP (ORDER BY t.xrdx00) xrdx08," + 
				" listagg (nvl(t.xrdx09,' '), ',') WITHIN GROUP (ORDER BY t.xrdx00) xrdx09," + 
				" listagg (nvl(t.xrdx10,' '), ',') WITHIN GROUP (ORDER BY t.xrdx00) xrdx10," + 
				" listagg (nvl((select code_name from code_value where code_type='TRANINS' and code_value=t.xrdx11),' '), ',') WITHIN GROUP (ORDER BY t.xrdx00) xrdx11," + 
				" listagg (nvl((select code_name from code_value where code_type='ZB27' and code_value=t.xrdx12),' '), ',') WITHIN GROUP (ORDER BY t.xrdx00) xrdx12," + 
				" (select sum(t1.xrdx08) from edu_xrdx t1, edu_xrdx_ry t2  where t1.xrdx00=t2.xrdx00  and t2.a0000=r.a0000 and t1.xrdx02<>'0203' "+timeSql+") zts," +
				" (select sum(t1.xrdx09) from edu_xrdx t1, edu_xrdx_ry t2  where t1.xrdx00=t2.xrdx00  and t2.a0000=r.a0000 and t1.xrdx02<>'0203' "+timeSql+") zxs" +
//				" sum(t.xrdx08) zts, sum(t.xrdx09) zxs" +  
				" from edu_xrdx t,edu_xrdx_ry r" + 
				" where r.a0000 in (select a0000 from a01 where a0163='1' ) and t.xrdx00=r.xrdx00 " + extraSql+
				" group by r.a0000   ";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	/**
	 * 修改人员信息的双击事件
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("editgrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		//获得当前页面是浏览  还是  编辑  机构树
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();

		String a0000=this.getPageElement("editgrid").getValue("a0000",this.getPageElement("editgrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			String rmbs=this.getPageElement("rmbs").getValue();
			/*if(rmbs.contains(a0000)) {
				this.setMainMessage("已经打开了");
				return EventRtnType.FAILD;
			}*/
			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+a0000+"');}}, 500);");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}

	
}



