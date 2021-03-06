package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Cxtj;
import com.insigma.siis.local.business.entity.FxypSJFA;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;

import net.sf.json.JSONObject;

public class RenXuanTiaoJianPageModel extends PageModel{

	@Override
	@AutoNoMask
	public int doInit() throws RadowException {
		//设置岗位名册下拉选
//		CommQuery cqbs=new CommQuery();
//		try {
//			List<HashMap<String, Object>> TPQCode = cqbs.getListBySQL("select id,name "
//					+ " from hz_GwMC   order by sortid  ");
//			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
//			for(HashMap<String, Object> m : TPQCode){
//				String id = m.get("id")==null?"":m.get("id").toString();
//				String name = m.get("name")==null?"":m.get("name").toString();
//				mapCode.put(id, name);
//			}
//			((Combo)this.getPageElement("gwmc")).setValueListForSelect(mapCode);
//			
//			String userid=SysManagerUtils.getUserId();
//			List<HashMap<String, Object>> mcCode = cqbs.getListBySQL("select mdid,mdmc from (select mdid,mdmc,createdate "+ 
//				"  from historymd y" + 
//				" where userid = '"+userid+"'" + 
//				" union all" + 
//				" select mdid,mdmc,createdate " + 
//				"  from historymd y" + 
//				" where exists (select 1" + 
//				"          from hz_LSMD_userref u" + 
//				"         where mnur01 = '"+userid+"'" + 
//				"           and u.mdid = y.mdid)) order by createdate desc");
//			HashMap<String, Object> mapMC=new LinkedHashMap<String, Object>();
//			for(HashMap<String, Object> m : mcCode){
//				String id = m.get("mdid")==null?"":m.get("mdid").toString();
//				String name = m.get("mdmc")==null?"":m.get("mdmc").toString();
//				mapMC.put(id, name);
//			}
//			((Combo)this.getPageElement("gbgzmd")).setValueListForSelect(mapMC);
//		} catch (AppException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String sessionid=this.request.getSession().getId();
		
		HBSession sess = HBUtil.getHBSession();
		Statement stmt;
		try {
			stmt = sess.connection().createStatement();
			String sql="delete from ZDGW_ZGMX_QUERY where sessionid='"+sessionid+"'";
			stmt.executeUpdate(sql);
			sql="delete from ZDGW_QZMX_QUERY where sessionid='"+sessionid+"'";
			stmt.executeUpdate(sql);
			stmt.close();
			
//			CommQuery cqbs=new CommQuery();
//			List<HashMap<String, Object>> TPQCode = cqbs.getListBySQL("select wayid id,wayname name "
//					+ " from zdgw_way   order by sortid  ");
//			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
//			for(HashMap<String, Object> m : TPQCode){
//				String id = m.get("id")==null?"":m.get("id").toString();
//				String name = m.get("name")==null?"":m.get("name").toString();
//				mapCode.put(id, name);
//			}
//			((Combo)this.getPageElement("wayid")).setValueListForSelect(mapCode);
			this.createPageElement("zdgwid", ElementType.SELECT, false).setDisabled(true);
			this.createPageElement("wayid", ElementType.SELECT, false).setDisabled(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getPageElement("query_type").setValue("9");
		this.setNextEventName("initX");
		this.setNextEventName("DXcondGrid.dogridquery");
        return 0;
	}
	
	@PageEvent("setgwmc")
	public int gwmcclick(String id) throws RadowException, AppException{
		String uuid = this.getPageElement("gwmc").getValue();
		String name = this.getPageElement("gwmc_combo").getValue();
		Object[] query = (Object[]) HBUtil.getHBSession().createSQLQuery("select sql,name from hz_gwmc a where a.id = '"+uuid+"'").uniqueResult();
		
		String sql = query[0].toString();
		
		this.request.getSession().setAttribute("gbmcName",name);
		this.request.getSession().setAttribute("gbmcSql",sql);
		this.getExecuteSG().addExecuteCode("collapseGroupWin($('#fxyp00').val());");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	@AutoNoMask
	public int initX(String a) throws RadowException, AppException{
		
//		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMM");
//		//PageElement pe= this.getPageElement("isOnDuty");
//		//pe.setValue("1");
//        String datestr = myFmt1.format(new Date());
//        this.getPageElement("jiezsj").setValue(datestr);
//        this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4)+"."+datestr.substring(4, 6));
//		this.getExecuteSG().addExecuteCode("document.getElementById('existsCheckbox').click();odin.setSelectValue('a0163', '1');");
//		
//		
//		
		String fxyp00 = this.getPageElement("fxyp00").getValue();
		FxypSJFA fxyp = (FxypSJFA)HBUtil.getHBSession().get(FxypSJFA.class, fxyp00);

		this.getExecuteSG().addExecuteCode("$('#existsCheckbox').prop('checked',true);");
		this.request.getSession().setAttribute("gbmcName","");
		this.request.getSession().setAttribute("gbmcSql","");
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
				}else if("checkbox".equals(cxtj.getCxtj08())&&cxtj.getCxtj07().contains(",")){
					String[] lableids = cxtj.getCxtj07().split(",");
					for(String lid : lableids){
						if(!"".equals(lid)){
							this.getExecuteSG().addExecuteCode("$('#"+lid+"').click();");
						}
					}
					
				}else if("comboTree".equals(cxtj.getCxtj08())){
					this.getPageElement(cxtj.getCxtj07()).setValue(cxtj.getCxtj04());
					this.getPageElement(cxtj.getCxtj07()+"_combotree").setValue(cxtj.getCxtj09());
				}
			}
		}
		
		
        return 0;
	}
	
	
	@PageEvent("clearReset")
	public int clearReset() throws RadowException {
		String sessionid=this.request.getSession().getId();
		
		HBSession sess = HBUtil.getHBSession();
		Statement stmt;
		try {
			stmt = sess.connection().createStatement();
			String sql="delete from ZDGW_ZGMX_QUERY where sessionid='"+sessionid+"'";
			stmt.executeUpdate(sql);
			sql="delete from ZDGW_QZMX_QUERY where sessionid='"+sessionid+"'";
			stmt.executeUpdate(sql);
			stmt.close();
			
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> TPQCode = cqbs.getListBySQL("select wayid id,wayname name "
					+ " from zdgw_way   order by sortid  ");
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(HashMap<String, Object> m : TPQCode){
				String id = m.get("id")==null?"":m.get("id").toString();
				String name = m.get("name")==null?"":m.get("name").toString();
				mapCode.put(id, name);
			}
			((Combo)this.getPageElement("wayid")).setValueListForSelect(mapCode);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setNextEventName("ZGcondGrid.dogridquery");
		this.setNextEventName("QZcondGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 查询（注：范围查询中代码的大小与字面逻辑的高低正好相反，所以判断逻辑也是相反的处理）
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("mQueryonclick")
	public int query(String param) throws RadowException, AppException{
		
		
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
        return newquery(cu_b0111_sb,a02_a0201b_sb,b0111desc,userID,param);
        
	}
	
	
	
	private int newquery(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, StringBuffer b0111desc, String userID, String param) throws RadowException, AppException {
		
        String fxyp00 = saveCondition(cu_b0111_sb, a02_a0201b_sb,b0111desc,param);
        
        	
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
				StringBuffer a0824_sb = new StringBuffer("");
				String[] a0824Array = a0824.split(",");
				for (int i = 0; i < a0824Array.length; i++) {
					if("1".equals(a0824Array[i])){
						a0824_sb.append(" a0827 like '101%' or ");
					}else if("10".equals(a0824Array[i])){
						a0824_sb.append(" a0827 like '100%' or ");
					}else{
						a0824_sb.append(" a0827 like '" + a0824Array[i] + "%' or ");
					}
				}
				a0824_sb.delete(a0824_sb.length() - 4, a0824_sb.length() - 1);
				orther_sb.append(" and (" + a0824_sb.toString() + ")");
			}
			orther_sb.append(")");
		}

	}



	
	
	
	@SuppressWarnings("unchecked")
	private String saveCondition(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, StringBuffer b0111desc, String param) throws AppException, RadowException{
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
		
		//人员姓名
		String a0101 = this.getPageElement("a0101").getValue();//人员姓名
		if (a0101!=null&&!"".equals(a0101)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "人员姓名");
			m.put("cxtj07", "a0101");
			m.put("cxtj04", a0101);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a0101);
			condList.add(m);
		}
		
		//人员状态
		String a0163 = this.getPageElement("a0163").getValue();//人员状态
		if(a0163!=null&&!"".equals(a0163)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "人员状态");
			m.put("cxtj07", "a0163");
			m.put("cxtj04", a0163);
			m.put("cxtj08", "select2");
			String a0163_combo = this.getPageElement("a0163_combo").getValue();//人员状态
			m.put("cxtj09", a0163_combo);
			condList.add(m);
		}
		
		//省管干部
		String a0165A = this.getPageElement("a0165A").getValue();
		if(a0165A!=null&&!"".equals(a0165A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "省管干部");
			m.put("cxtj07", "a0165A");
			m.put("cxtj04", a0165A);
			m.put("cxtj08", "select2");
			String a0165_combo = this.getPageElement("a0165A_combo").getValue();
			m.put("cxtj09", a0165_combo);
			condList.add(m);
		}
		
		//市管干部
		String a0165B = this.getPageElement("a0165B").getValue();
		if(a0165B!=null&&!"".equals(a0165B)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "市管干部");
			m.put("cxtj07", "a0165B");
			m.put("cxtj04", a0165B);
			m.put("cxtj08", "select2");
			String a0165_combo = this.getPageElement("a0165B_combo").getValue();
			m.put("cxtj09", a0165_combo);
			condList.add(m);
		}
		
		//处级（中层）干部
		String a0165C = this.getPageElement("a0165C").getValue();
		if(a0165C!=null&&!"".equals(a0165C)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "处级（中层）干部");
			m.put("cxtj07", "a0165C");
			m.put("cxtj04", a0165C);
			m.put("cxtj08", "select2");
			String a0165_combo = this.getPageElement("a0165C_combo").getValue();
			m.put("cxtj09", a0165_combo);
			condList.add(m);
		}
		
		//性别
		String a0104 = this.getPageElement("a0104").getValue();
		if (a0104!=null&&!"".equals(a0104)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "性别");
			m.put("cxtj07", "a0104");
			m.put("cxtj04", a0104);
			m.put("cxtj08", "radio");
			m.put("cxtj09", "1".equals(a0104)?"男":("0".equals(a0104)?"全部":"女"));
			condList.add(m);
		}
		
		//民族
		String a0117 = this.getPageElement("a0117").getValue();
		if (a0117!=null&&!"".equals(a0117)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "民族");
			m.put("cxtj07", "a0117");
			m.put("cxtj04", a0117);
			m.put("cxtj08", "radio");
			m.put("cxtj09", "1".equals(a0117)?"汉族":("0".equals(a0117)?"全部":"少数民族"));
			condList.add(m);
		}
		
		//党派
		String a0141 = this.getPageElement("a0141").getValue();//政治面貌
		if (a0141!=null&&!"".equals(a0141)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "党派");
			m.put("cxtj07", "a0141");
			m.put("cxtj04", a0141);
			m.put("cxtj08", "comboTree");
			String a0141_combo = this.getPageElement("a0141_combotree").getValue();//党派
			m.put("cxtj09", a0141_combo);
			
			condList.add(m);
		}
		
		//年龄
		String ageA = this.getPageElement("ageA").getValue();//年龄
		String ageB = this.getPageElement("ageB").getValue();//年龄
		if (ageA!=null&&!"".equals(ageA)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "年龄大于");
			m.put("cxtj07", "ageA");
			m.put("cxtj04", ageA);
			m.put("cxtj08", "numberEdit");
			m.put("cxtj09", ageA);
			condList.add(m);
		}
		if (ageB!=null&&!"".equals(ageB)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "年龄小于");
			m.put("cxtj07", "ageB");
			m.put("cxtj04", ageB);
			m.put("cxtj08", "numberEdit");
			m.put("cxtj09", ageB);
			condList.add(m);
		}
		
		//出生年月
		String a0107A = this.getPageElement("a0107A").getValue();//出生年月
		String a0107B = this.getPageElement("a0107B").getValue();//出生年月
		if(a0107A!=null&&!"".equals(a0107A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "出生年月大于");
			m.put("cxtj07", "a0107A");
			m.put("cxtj04", a0107A);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0107A);
			condList.add(m);
		}
		if(a0107B!=null&&!"".equals(a0107B)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "出生年月小于");
			m.put("cxtj07", "a0107B");
			m.put("cxtj04", a0107B);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0107B);
			condList.add(m);
		}
		
		//截止时间
		String jiezsj = this.getPageElement("jiezsj").getValue();//截止时间
		if (jiezsj!=null&&!"".equals(jiezsj)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "截止时间");
			m.put("cxtj07", "jiezsj");
			m.put("cxtj04", jiezsj);
			m.put("cxtj08", "NewDateEditTag");
			//m.put("cxtj09", jiezsj);
			condList.add(m);
		}
		
		//学历学位
		String xlxw = this.getPageElement("xlxw").getValue();
		if(xlxw!=null&&!"".equals(xlxw)){
			Map<String,String> m = new HashMap<String, String>();
			
			String qrz = this.getPageElement("qrz").getValue();
			if(qrz!=null&&!"".equals(qrz)&&"1".equals(qrz)) {
				m.put("cxtj02", "全日制学历学位");
				
				Map<String,String> m2 = new HashMap<String, String>();
				m2.put("cxtj02", "全日制标识");
				m2.put("cxtj07", "qrz");
				m2.put("cxtj04", qrz);
				m2.put("cxtj08", "checkbox");
				m2.put("cxtj09", "已选择");
				condList.add(m2);
			}else {
				m.put("cxtj02", "最高学历学位");
			}
			m.put("cxtj07", "xlxw");
			m.put("cxtj04", xlxw);
			m.put("cxtj08", "select2");
			String xlxw_combo = this.getPageElement("xlxw_combo").getValue();
			m.put("cxtj09", xlxw_combo);
			condList.add(m);
		}
		
		//现职务时间
		String a0192fA = this.getPageElement("a0192fA").getValue();//现职务时间
		String a0192fB = this.getPageElement("a0192fB").getValue();//现职务时间
		if(a0192fA!=null&&!"".equals(a0192fA)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "现职务时间大于");
			m.put("cxtj07", "a0192fA");
			m.put("cxtj04", a0192fA);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0192fA);
			condList.add(m);
		}
		if(a0192fB!=null&&!"".equals(a0192fB)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "现职务时间小于");
			m.put("cxtj07", "a0192fB");
			m.put("cxtj04", a0192fB);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0192fB);
			condList.add(m);
		}
		
		//现职务层次
		String a0221A = this.getPageElement("a0221A").getValue();//现职务层次
		if(a0221A!=null&&!"".equals(a0221A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "现职务层次");
			m.put("cxtj07", "a0221A");
			m.put("cxtj04", a0221A);
			m.put("cxtj08", "comboTree");
			String a0221A_combo = this.getPageElement("a0221A_combotree").getValue();//现职务层次
			m.put("cxtj09", a0221A_combo);
			condList.add(m);
		}
		
		//任现职务层次时间
		String a0288A = this.getPageElement("a0288A").getValue();//任现职务层次时间
		String a0288B = this.getPageElement("a0288B").getValue();//任现职务层次时间
		if(a0288A!=null&&!"".equals(a0288A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "任现职务层次时间大于");
			m.put("cxtj07", "a0288A");
			m.put("cxtj04", a0288A);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0288A);
			condList.add(m);
		}
		if(a0288B!=null&&!"".equals(a0288B)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "任现职务层次时间小于");
			m.put("cxtj07", "a0288B");
			m.put("cxtj04", a0288B);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0288B);
			condList.add(m);
		}
		
		//现职级
		String a0192e = this.getPageElement("a0192e").getValue();//现职级
		if(a0192e!=null&&!"".equals(a0192e)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "现职级");
			m.put("cxtj07", "a0192e");
			m.put("cxtj04", a0192e);
			m.put("cxtj08", "comboTree");
			String a0192e_combo = this.getPageElement("a0192e_combotree").getValue();//现职级
			m.put("cxtj09", a0192e_combo);
			condList.add(m);
		}
		
		//任职级时间
		String a0192cA = this.getPageElement("a0192cA").getValue();//任职级时间
		String a0192cB = this.getPageElement("a0192cB").getValue();//任职级时间
		if(a0192cA!=null&&!"".equals(a0192cA)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "任职级时间小于");
			m.put("cxtj07", "a0192cA");
			m.put("cxtj04", a0192cA);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0192cA);
			condList.add(m);
		}
		if(a0192cB!=null&&!"".equals(a0192cB)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "任职级时间小于");
			m.put("cxtj07", "a0192cB");
			m.put("cxtj04", a0192cB);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a0192cB);
			condList.add(m);
		}
		
		//熟悉领域
		/*
		 * String a0194z = this.getPageElement("a0194z").getValue();//熟悉领域
		 * if(a0194z!=null&&!"".equals(a0194z)){ Map<String,String> m = new
		 * HashMap<String, String>(); m.put("cxtj02", "熟悉领域"); m.put("cxtj07",
		 * "a0194z"); m.put("cxtj04", a0194z); m.put("cxtj08", "select2"); String
		 * a0194z_combo = this.getPageElement("a0194z_combo").getValue();
		 * m.put("cxtj09", a0194z_combo); condList.add(m); }
		 */
		
		//党龄
		String a0144age = this.getPageElement("a0144age").getValue();
		if(a0144age!=null&&!"".equals(a0144age)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "党龄");
			m.put("cxtj07", "a0144age");
			m.put("cxtj04", a0144age);
			m.put("cxtj08", "select2");
			String a0144age_combo = this.getPageElement("a0144age_combo").getValue();
			m.put("cxtj09", a0144age_combo);
			condList.add(m);
		}
		
		//重要任职经历
		String a0194c = this.getPageElement("a0194c").getValue();
		if(a0194c!=null&&!"".equals(a0194c)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "重要任职经历");
			m.put("cxtj07", "a0194c");
			m.put("cxtj04", a0194c);
			m.put("cxtj08", "comboTree");
			String a0194c_combo = this.getPageElement("a0194c_combotree").getValue();
			m.put("cxtj09", a0194c_combo);
			condList.add(m);
		}
		
		//20210223新增查询条件
		//机构类别
		String b0131A = this.getPageElement("b0131A").getValue();
		if(b0131A!=null&&!"".equals(b0131A)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "机构类别");
			m.put("cxtj07", "b0131A");
			m.put("cxtj04", b0131A);
			m.put("cxtj08", "select2");
			String b0131A_combo = this.getPageElement("b0131A_combo").getValue();
			m.put("cxtj09", b0131A_combo);
			condList.add(m);
		}
		
		//专业（包含）
		String a0824 = this.getPageElement("a0824").getValue();//人员姓名
		if (a0824!=null&&!"".equals(a0824)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "专业（包含）");
			m.put("cxtj07", "a0824");
			m.put("cxtj04", a0824);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a0824);
			condList.add(m);
		}
		
		//具有乡镇（街道）党政正职经历
		String a0188 = this.getPageElement("a0188").getValue();
		if(a0188!=null&&!"".equals(a0188)&&"1".equals(a0188)) {	
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "具有乡镇（街道）党政正职经历");
			m2.put("cxtj07", "a0188");
			m2.put("cxtj04", a0188);
			m2.put("cxtj08", "checkbox");
			m2.put("cxtj09", "已选择");
			condList.add(m2);
		}
		
		//简历（包含）
		String a1701 = this.getPageElement("a1701").getValue();//人员姓名
		if (a1701!=null&&!"".equals(a1701)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "简历（包含）");
			m.put("cxtj07", "a1701");
			m.put("cxtj04", a1701);
			m.put("cxtj08", "textEdit");
			m.put("cxtj09", a1701);
			condList.add(m);
		}
		
		//现任乡镇（街道）党（工）委书记
		String a0132 = this.getPageElement("a0132").getValue();
		if(a0132!=null&&!"".equals(a0132)&&"1".equals(a0132)) {	
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "现任乡镇（街道）党（工）委书记");
			m2.put("cxtj07", "a0132");
			m2.put("cxtj04", a0132);
			m2.put("cxtj08", "checkbox");
			m2.put("cxtj09", "已选择");
			condList.add(m2);
		}
		
		//专业类型
		String a0196z = this.getPageElement("a0196z").getValue();
		if(a0196z!=null&&!"".equals(a0196z)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "专业类型");
			m.put("cxtj07", "a0196z");
			m.put("cxtj04", a0196z);
			m.put("cxtj08", "select2");
			String a0196z_combo = this.getPageElement("a0196z_combo").getValue();
			m.put("cxtj09", a0196z_combo);
			condList.add(m);
		}
		
		//两头干部
		String a0196c = this.getPageElement("a0196c").getValue();
		if(a0196c!=null&&!"".equals(a0196c)){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "两头干部");
			m.put("cxtj07", "a0196c");
			m.put("cxtj04", a0196c);
			m.put("cxtj08", "select2");
			String a0196c_combo = this.getPageElement("a0196c_combo").getValue();
			m.put("cxtj09", a0196c_combo);
			condList.add(m);
		}
		
		//现任乡镇（街道）镇长（主任）
		String a0133 = this.getPageElement("a0133").getValue();
		if(a0133!=null&&!"".equals(a0133)&&"1".equals(a0133)) {	
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "现任乡镇（街道）镇长（主任）");
			m2.put("cxtj07", "a0133");
			m2.put("cxtj04", a0133);
			m2.put("cxtj08", "checkbox");
			m2.put("cxtj09", "已选择");
			condList.add(m2);
		}
		
		//zdgwq重点岗位
		String zdgwq = this.getPageElement("zdgwq").getValue();
		if(zdgwq!=null&&!"".equals(zdgwq)) {
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "重点岗位");
			m2.put("cxtj07", "zdgwq");
			m2.put("cxtj04", zdgwq);
			m2.put("cxtj08", "comboTree");
			String zdgwq_combo = this.getPageElement("zdgwq_combotree").getValue();
			m2.put("cxtj09", zdgwq_combo);
			condList.add(m2);
		}
		
		//分管工作类型
		String a1706 = this.getPageElement("a1706").getValue();
		if(a1706!=null&&!"".equals(a1706)) {
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "分管工作类型");
			m2.put("cxtj07", "a1706");
			m2.put("cxtj04", a1706);
			m2.put("cxtj08", "comboTree");
			String a1706_combo = this.getPageElement("a1706_combotree").getValue();
			m2.put("cxtj09", a1706_combo);
			condList.add(m2);
		}
		
		//是否为现任
		String sfwxr = this.getPageElement("sfwxr").getValue();
		if(sfwxr!=null&&!"".equals(sfwxr)) {
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "分管是否为现任类型");
			m2.put("cxtj07", "sfwxr");
			m2.put("cxtj04", sfwxr);
			m2.put("cxtj08", "select2");
			m2.put("cxtj09", "01".equals(sfwxr)?"现任":"历任");
			condList.add(m2);
		}
		
		//任职经历新
		String newRZJL = this.getPageElement("newRZJL").getValue();
		if(newRZJL!=null&&!"".equals(newRZJL)) { 
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "任职经历新");
			m2.put("cxtj07", "newRZJL");
			m2.put("cxtj04", newRZJL);
			m2.put("cxtj08", "comboTree");
			String newRZJL_combo = this.getPageElement("newRZJL_combotree").getValue();
			m2.put("cxtj09", newRZJL_combo);
			condList.add(m2);	
		}
		
		// 熟悉领域
		String A0194_TAG = this.getPageElement("A0194_TAG").getValue();
		if (!"".equals(A0194_TAG)) {
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "熟悉领域");
			m2.put("cxtj07", "A0194_TAG");
			m2.put("cxtj04", A0194_TAG);
			m2.put("cxtj08", "comboTree");
			String A0194_TAG_combo = this.getPageElement("A0194_TAG_combotree").getValue();
			m2.put("cxtj09", A0194_TAG_combo);
			condList.add(m2);
		}
		
		// 干部工作名单
		String gbgzmd = this.getPageElement("gbgzmd").getValue();
		if (!"".equals(gbgzmd)) {
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "工作干部名单");
			m2.put("cxtj07", "gbgzmd");
			m2.put("cxtj04", gbgzmd);
			m2.put("cxtj08", "select2");
			String gbgzmd_combo = this.getPageElement("gbgzmd_combo").getValue();
			m2.put("cxtj09", gbgzmd_combo);
			condList.add(m2);
		}
		
		//是否为选调生
		String a99z103 = this.getPageElement("a99z103").getValue();
		if(a99z103!=null&&!"".equals(a99z103)&&"1".equals(a99z103)) {	
			Map<String,String> m2 = new HashMap<String, String>();
			m2.put("cxtj02", "是否为选调生");
			m2.put("cxtj07", "a99z103");
			m2.put("cxtj04", a99z103);
			m2.put("cxtj08", "checkbox");
			m2.put("cxtj09", "是");
			condList.add(m2);
		}
		
		//选调生时间
		String a99z104A = this.getPageElement("a99z104A").getValue();//出生年月
		String a99z104B = this.getPageElement("a99z104B").getValue();//出生年月
		if(a99z104A!=null&&!"".equals(a99z104A)&&a99z104B.length()>0){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "选调为选调生时间大于");
			m.put("cxtj07", "a99z104A");
			m.put("cxtj04", a99z104A);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a99z104A);
			condList.add(m);
		}
		if(a99z104B!=null&&!"".equals(a99z104B)&&a99z104B.length()>0){
			Map<String,String> m = new HashMap<String, String>();
			m.put("cxtj02", "选调为选调生时间小于");
			m.put("cxtj07", "a99z104B");
			m.put("cxtj04", a99z104B);
			m.put("cxtj08", "NewDateEditTag");
			m.put("cxtj09", a99z104B);
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
						desc.append(tiaoJianXuHao++ +"、"+("".equals(m.get("cxtj02"))?"":(m.get("cxtj02")+"："))+m.get("cxtj09")+"\n");
					}
				}
				if(fxyp00==null||"".equals(fxyp00)){//新增
					ps = conn.prepareStatement("insert into hz_fxyp_SJFA(fxyp00,fxyp03) values(?,?)");
					fxyp00 = UUID.randomUUID().toString();
					ps.setString(1, fxyp00);
					ps.setString(2, desc.toString());
					ps.executeUpdate();
				}else{//清除条件
					ps = conn.prepareStatement("update hz_fxyp_SJFA set fxyp03=? where fxyp00=?");
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
	
	public static Map<String,String> labelDescMap = new HashMap<String, String>(){
		{
			put("attr001", "市直单位");
			put("attr002", "区县市");
			put("attr003", "国企高校科研院所");
			put("attr004", "市委");
			put("attr005", "市政府");
			put("attr006", "市人大");
			put("attr008", "市政协");
			put("attr009", "产业集聚区领导");
			put("attr010", "市中院");
			put("attr011", "市中院副职");
			put("attr012", "市检察院");
			put("attr013", "市检察院副职");
			put("attr014", "区县（市）法院院长");
			put("attr015", "区县（市）检察长");
			put("attr016", "市纪委副书记");
			put("attr017", "区县（市）委书记");
			put("attr018", "区县（市）长");
			put("attr019", "市审计局长");
			put("attr020", "高校");
			put("attr021", "其他");
			put("attr022", "挂职");
			put("attr023", "是");
			put("attr024", "否");
			put("attr025", "市管正职");
			put("attr026", "其中一把手");
			put("attr027", "市管副职");
			put("attr028", "其中正局长级");
			put("attr029", "其它副局长级");
			put("attr030", "曾任一把手");
			put("attr031", "曾任除一把手外正局长级");
			put("attr032", "曾任副局长级");
			put("attr033", "党组（党委、党工委）成员");
			put("attr034", "除党内成员外的班子成员，如总师、党外副局长");
			put("attr035", "担任领导职务的非班子成员");
			put("attr036", "现单位（本地）成长干部");
			put("attr037", "现单位专业干部");
			put("attr038", "1个");
			put("attr039", "2个");
			put("attr040", "3个以上");
			put("attr041", "党政综合部门");
			put("attr042", "人大政协部门");
			put("attr043", "纪检监察部门");
			put("attr044", "组织人事部门");
			put("attr045", "意识形态部门");
			put("attr046", "统战外侨部门");
			put("attr047", "政法维稳部门");
			put("attr048", "经济财贸部门");
			put("attr049", "建设交通部门");
			put("attr050", "农资环保部门");
			put("attr051", "科教文卫部门");
			put("attr052", "社会事务部门");
			put("attr053", "审计部门");
			put("attr054", "群众团体");
			put("attr055", "科研院所");
			put("attr056", "高职院校");
			put("attr057", "医疗机构");
			put("attr058", "企业");
			put("attr059", "开发区(园区)");
			put("attr060", "区县（市）党政班子成员");
			put("attr061", "区县（市）委书记");
			put("attr062", "区县（市）长");
			put("attr063", "区县（市）党委部门正职");
			put("attr064", "区县（市）政府部门正职");
			put("attr065", "镇乡（街道）党委书记");
			put("attr066", "镇乡（街道）政府主要负责人");
			put("attr067", "镇乡（街道）其他领导");
			put("attr068", "对口支援");
			put("attr069", "大学生村官");
			put("attr070", "村干部");
			put("attr071", "有两年基层工作经历");
			put("attr072", "市委委员");
			put("attr073", "市委候补委员");
			put("attr074", "市纪委常委");
			put("attr075", "市纪委委员");
			put("attr076", "市人大常委(专职)");
			put("attr077", "市人大常委(兼职)");
			put("attr078", "市政协常委(专职)");
			put("attr079", "市政协常委(兼职)");
			put("attr080", "人大秘书长");
			put("attr081", "人大副秘书长");
			put("attr082", "人大办公厅");
			put("attr083", "人大委室主任");
			put("attr084", "人大委室副主任");
			put("attr085", "政协秘书长");
			put("attr086", "政协副秘书长");
			put("attr087", "政协办公厅");
			put("attr088", "政协委室主任");
			put("attr089", "政协委室副主任");
			put("attr090", "组长");
			put("attr091", "副组长");
			put("attr092", "副局长级巡察专员");
			put("attr093", "副师");
			put("attr094", "正团");
			put("attr095", "副团");
			put("attr096", "中央和省直机关挂职");
			put("attr097", "市直机关挂职");
			put("attr098", "重点工作和重点工程挂职");
			put("attr099", "乡镇(街道)挂职 ");
			put("attr100", "高校院所挂职");
			put("attr101", "企业挂职");
			put("attr102", "国外挂职");
			put("attr103", "其他挂职");
			put("attr104", "对口支援");
			put("attr105", "中央");
			put("attr106", "省");
			put("attr107", "市");
			put("attr108", "选调生");
			put("attr109", "国家级");
			put("attr110", "省级");
			put("attr111", "市直部门");
			put("attr112", "县市区");
			put("attr113", "国家级");
			put("attr114", "省级");
			put("attr115", "市直部门");
			put("attr116", "县市区");
			put("attr117", "国家级");
			put("attr118", "省级");
			put("attr119", "市直部门");
			put("attr120", "县市区");
			put("attr121", "双重管理单位干部");
			put("attr122", "现单位组织人事部门负责人");
		}
	};
	
	@PageEvent("DXcondGrid.dogridquery")
	@NoRequiredValidate         
	public int DXDogrid(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String type=this.getPageElement("query_type").getValue();
		String sql ="";
		if(type==null||"".equals(type)||"9".equals(type)) {
			sql = "select mxname dcondname,mxdsec dconddesc,mxtype,dxmxid from ZDGW_DXMX where substr(mxtype,1,1)='9' order by mxtype";
		}else {
			sql = "select mxname dcondname,mxdsec dconddesc,mxtype,dxmxid from ZDGW_DXMX where substr(mxtype,1,1)='"+type+"' order by mxtype";
		}
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("ZGcondGrid.dogridquery")
	@NoRequiredValidate         
	public int ZGDogrid(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String sessionid=this.request.getSession().getId();
		String sql = "select mxname zgcondname,mxdsec zgconddesc,mxcs1 zgcondcs1,decode(tjtype,'2',csdesc2,mxcs2) zgcondcs2,tjtype zgtjtype,zgmxid from ZDGW_ZGMX_QUERY where sessionid='"+sessionid+"' order by mxtype";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("QZcondGrid.dogridquery")
	@NoRequiredValidate         
	public int QZDogrid(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String sessionid=this.request.getSession().getId();
		String sql = "select mxname qzcondname,mxdsec qzconddesc,mxcs1 qzcondcs1,decode(tjtype,'2',csdesc2,mxcs2) qzcondcs2,tjtype qztjtype,grade qzgrade,qzmxid from ZDGW_QZMX_QUERY where sessionid='"+sessionid+"' order by mxtype";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 新增资格条件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("addZGMX")
	@Transaction
	public int addZGMX(String dxmxid) throws RadowException {
		String sessionid=this.request.getSession().getId();
		HBSession sess = HBUtil.getHBSession();
		try {
			boolean flag=dxmxid.contains("##");
			if(flag) {
				dxmxid=dxmxid.replace("##1", "");
			}
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String sql="insert into ZDGW_ZGMX_QUERY(zgmxid,mxname,mxdsec,mxsql,mxcs1,mxcs2,mxtype,wayid,mxsql_p,cstype,tjtype,mxcs_p,csdesc2,sessionid) "
					+ " select '"+uuid+"',mxname,mxdsec,mxsql,mxcs1,mxcs2,mxtype,'',mxsql_p,cstype,tjtype,mxcs_p,csdesc2,'"+sessionid+"' from ZDGW_DXMX where dxmxid='"+dxmxid+"'";
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			this.setNextEventName("ZGcondGrid.dogridquery");
			if(flag) {
				CommQuery cqbs=new CommQuery();
				sql="select mxname,mxcs1 from ZDGW_ZGMX_QUERY where zgmxid='"+uuid+"'";
				List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
				this.getExecuteSG().addExecuteCode("$h.openPageModeWin('updateMX','pages.mntpsj.PZZDGWMX','修改条件',500,315,{type1:'zg',id:'"+uuid+"',cs:'"+list.get(0).get("mxcs1")+"',query:'_query'},g_contextpath);");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 新增权重条件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("addQZMX")
	@Transaction
	public int addQZMX(String dxmxid) throws RadowException {
		String sessionid=this.request.getSession().getId();
		HBSession sess = HBUtil.getHBSession();
		try {
			boolean flag=dxmxid.contains("##");
			if(flag) {
				dxmxid=dxmxid.replace("##1", "");
			}
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String sql="insert into ZDGW_QZMX_QUERY(qzmxid,mxname,mxdsec,mxsql,mxcs1,mxcs2,mxtype,grade,wayid,mxsql_p,cstype,tjtype,mxcs_p,csdesc2,sessionid) "
					+ " select '"+uuid+"',mxname,mxdsec,mxsql,mxcs1,mxcs2,mxtype,100,'',mxsql_p,cstype,tjtype,mxcs_p,csdesc2,'"+sessionid+"' from ZDGW_DXMX where dxmxid='"+dxmxid+"'";
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			this.setNextEventName("QZcondGrid.dogridquery");
			if(flag) {
				CommQuery cqbs=new CommQuery();
				sql="select mxname,mxcs1 from ZDGW_QZMX_QUERY where qzmxid='"+uuid+"'";
				List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
				this.getExecuteSG().addExecuteCode("$h.openPageModeWin('updateMX','pages.mntpsj.PZZDGWMX','修改条件',500,315,{type1:'qz',id:'"+uuid+"',cs:'"+list.get(0).get("mxcs1")+"',query:'_query'},g_contextpath);");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteZGMX")
	@Transaction
	public int deleteZGMX(String zgmxid) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		try {
			String sql="delete from ZDGW_ZGMX_QUERY where zgmxid='"+zgmxid+"' ";
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			this.setNextEventName("ZGcondGrid.dogridquery");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteQZMX")
	@Transaction
	public int deleteQZMX(String qzmxid) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		try {
			String sql="delete from ZDGW_QZMX_QUERY where qzmxid='"+qzmxid+"' ";
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			this.setNextEventName("QZcondGrid.dogridquery");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("changeType")
	@Transaction
	public int changeType() throws RadowException {
		String dwtype=this.getPageElement("waytype").getValue();
		try {
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> TPQCode = cqbs.getListBySQL("select zdgwid id,zdgwname name "
					+ " from zdgw where dwtype='"+dwtype+"' order by sortid  ");
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(HashMap<String, Object> m : TPQCode){
				String id = m.get("id")==null?"":m.get("id").toString();
				String name = m.get("name")==null?"":m.get("name").toString();
				mapCode.put(id, name);
			}
			((Combo)this.getPageElement("zdgwid")).setValueListForSelect(mapCode);
			this.getPageElement("zdgwid").setValue("");
			this.getPageElement("zdgwid_combo").setValue("");
			this.createPageElement("zdgwid", ElementType.SELECT, false).setDisabled(false);
			this.getPageElement("wayid").setValue("");
			this.getPageElement("wayid_combo").setValue("");
			this.createPageElement("wayid", ElementType.SELECT, false).setDisabled(true);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("changeZdgw")
	@Transaction
	public int changeZdgw() throws RadowException {
		String zdgwid=this.getPageElement("zdgwid").getValue();
		try {
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> TPQCode = cqbs.getListBySQL("select wayid id,wayname name "
					+ " from zdgw_way where zdgwid='"+zdgwid+"' order by sortid  ");
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(HashMap<String, Object> m : TPQCode){
				String id = m.get("id")==null?"":m.get("id").toString();
				String name = m.get("name")==null?"":m.get("name").toString();
				mapCode.put(id, name);
			}
			((Combo)this.getPageElement("wayid")).setValueListForSelect(mapCode);
			this.getPageElement("wayid").setValue("");
			this.getPageElement("wayid_combo").setValue("");
			this.createPageElement("wayid", ElementType.SELECT, false).setDisabled(false);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("changeWay")
	@Transaction
	public int changeWay() throws RadowException {
		String sessionid=this.request.getSession().getId();
		String wayid=this.getPageElement("wayid").getValue();
		HBSession sess = HBUtil.getHBSession();
		Statement stmt;
		try {
			stmt = sess.connection().createStatement();
			String sql="delete from ZDGW_ZGMX_QUERY where sessionid='"+sessionid+"'";
			stmt.executeUpdate(sql);
			sql="delete from ZDGW_QZMX_QUERY where sessionid='"+sessionid+"'";
			stmt.executeUpdate(sql);
			sql="insert into ZDGW_ZGMX_QUERY (zgmxid,mxname,mxdsec,mxsql,mxcs1,mxcs2,mxtype,wayid,mxsql_p,cstype,tjtype,mxcs_p,csdesc2,sessionid)"
					+ "	select sys_guid(),mxname,mxdsec,mxsql,mxcs1,mxcs2,mxtype,wayid,mxsql_p,cstype,tjtype,mxcs_p,csdesc2,'"+sessionid+"' from ZDGW_ZGMX where wayid='"+wayid+"' ";
			stmt.executeUpdate(sql);
			sql="insert into ZDGW_QZMX_QUERY (qzmxid,mxname,mxdsec,mxsql,mxcs1,mxcs2,mxtype,wayid,grade,mxsql_p,cstype,tjtype,mxcs_p,csdesc2,sessionid)"
					+ "	select sys_guid(),mxname,mxdsec,mxsql,mxcs1,mxcs2,mxtype,wayid,grade,mxsql_p,cstype,tjtype,mxcs_p,csdesc2,'"+sessionid+"' from ZDGW_QZMX where wayid='"+wayid+"' ";
			stmt.executeUpdate(sql);
			stmt.close();
			this.setNextEventName("ZGcondGrid.dogridquery");
			this.setNextEventName("QZcondGrid.dogridquery");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
       
	@PageEvent("startQuery")
	@Transaction
	public int stratQuery() throws RadowException {
		String sessionid=this.request.getSession().getId();
		String userID = SysManagerUtils.getUserId();
		CommQuery commQuery =new CommQuery();
		String wayid=this.getPageElement("wayid").getValue();
		String sql_all="select a01.a0000  from ZHGBSYS.A01 a01 where a0163='1' and a0288 is not null and a0165 not like '%01%' and a0165 not like '%02%' ";
		try {
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
						a02_a0201b_sb.append(" or "+CommSQL.subString("a02.a0201b", 1, nodeid.length(), nodeid));
						cu_b0111_sb.append(" or "+CommSQL.subString("cu.b0111", 1, nodeid.length(), nodeid));
					}else if("true".equals(types[1])&&"false".equals(types[2])){
						a02_a0201b_sb.append(" or "+CommSQL.subString2("a02.a0201b", 1, nodeid.length(), nodeid));
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
			String sql_zg="select mxsql,mxtype from ZDGW_ZGMX_QUERY where sessionid='"+sessionid+"' ";
			List<HashMap<String, Object>> list_zg = commQuery.getListBySQL(sql_zg);
			if(list_zg!=null&&list_zg.size()>0) {
				String zg1="and (";
				String zg2="and";
				for(HashMap<String, Object> map:list_zg) {
					if("10".equals(map.get("mxtype"))||"910".equals(map.get("mxtype"))) {
						zg1=zg1+" ("+map.get("mxsql")+") or";
					}else {
						zg2=zg2+" "+map.get("mxsql")+" and";
					}
				}
				if("and (".equals(zg1)){
					zg1="";
				}else {
					zg1=zg1.substring(0,zg1.length()-2)+")";
				}
				zg2=zg2.substring(0,zg2.length()-3);
				sql_all=sql_all+zg1+zg2;
			}
			
			//this.request.getSession().setAttribute("queryTypeEX", "新改查询方式");
			
			
	        if(b01String!=null && !"".equals(b01String)){//tree!=null && !"".equals(tree.trim()
				sql_all=sql_all+" and a0000 in (select a02.a0000 from a02 where a0281='true' "+a02_a0201b_sb+" "
						+ " and a02.a0201b in (select cu.b0111 from competence_userdept cu, b01 b  where cu.b0111 = b.b0111 and cu.userid = '"+userID+"')"
						+ ")";
	        } 
			
	        
			String sql_gz="select '(case when '||mxsql||' then '||grade||' else 0 end)' as mxsql  from ZDGW_QZMX_QUERY where sessionid='"+sessionid+"' ";
			List<HashMap<String, Object>> list_gz = commQuery.getListBySQL(sql_gz);
			if(list_gz!=null&&list_gz.size()>0) {
				String qz="order by ";
				for(HashMap<String, Object> map:list_gz) {
					qz=qz+map.get("mxsql")+"+";
				}
				qz=qz.substring(0,qz.length()-1);
				sql_all=sql_all+qz+" desc";
				sql_all=sql_all+",(select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from (select a02.a0000,b0269,a0225"
						+ ",row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn "
						+ " from a02, b01 where a02.a0201b = b01.b0111  and a0281 = 'true'  and a0201b like '001.001%') t "
						+ "	 where rn = 1 and t.a0000 = a01.a0000) ";
				this.request.getSession().setAttribute("gbmcFlag", " order by tp.sort ");
			}else {
				this.request.getSession().setAttribute("gbmcFlag", "");
			}
			this.request.getSession().setAttribute("gbmcName", "");
			this.request.getSession().setAttribute("gbmcSql", sql_all);
			
			String fxyp00 =  this.getPageElement("fxyp00").getValue();     
	        this.getExecuteSG().addExecuteCode("collapseGroupWin('"+fxyp00+"');");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("queryA0101")
	@Transaction
	public int queryA0101() throws RadowException {
		String a0101=this.getPageElement("a0101").getValue()==null?"":this.getPageElement("a0101").getValue().toString();
		String sql_all="";
		if("".equals(a0101)) {
			sql_all="select a01.a0000  from ZHGBSYS.A01 a01 where a0163='1' and a0288 is not null ";
		}else {
			sql_all="select a01.a0000  from ZHGBSYS.A01 a01 where a0163='1' and a0288 is not null and a0101 like '%"+a0101+"%'";
		}
		try {
			this.request.getSession().setAttribute("gbmcFlag", "");
			this.request.getSession().setAttribute("gbmcName", "");
			this.request.getSession().setAttribute("gbmcSql", sql_all);
			
			String fxyp00 =  this.getPageElement("fxyp00").getValue();     
	        this.getExecuteSG().addExecuteCode("collapseGroupWin('"+fxyp00+"');");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}




