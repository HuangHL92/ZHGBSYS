package com.insigma.siis.local.pagemodel.publicServantManage;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class PersonSortPageModel extends PageModel{
	
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("gridA01.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	//刷新列表
	@PageEvent("gridA01.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String a0201b = this.getPageElement("a0201b").getValue();
		String a0000 = this.getPageElement("a0000").getValue();//人员id
		String a0101 = this.getPageElement("a0101").getValue();//姓名
		String a0225 = this.getPageElement("a0225").getValue();//集体内排序
		String b0111 = this.request.getParameter("initParams");
		if(b0111!=null&&!"".equals(b0111)) {
			a0201b = b0111;
		}

		if(a0225==null||"".equals(a0225)){
			a0225 = "0";
		}
		String authoritySQL = "select t.b0111 b from COMPETENCE_USERDEPT t where t.userid='"+SysManagerUtils.getUserId()+"'";
		
		String sql="from B01 where B0111='"+a0201b+"' ";
		List alist = HBUtil.getHBSession().createQuery(sql).list();
		if(alist!=null && alist.size()>0){
			/*String sql2="select t2.a0225 a0225,t.a0000 a0000,  t.a0101 a0101,lpad(t2.a0225,5,'0') as a0225N,t.a0192a as a0192a from a01 t,(select distinct a0000,a0225  from a02 where a0201b='"+a0201b+"') t2 where t.a0000=t2.a0000 and status='1' and exists (select a0000 from a02 b where t.a0000=b.a0000 and a0255='1' and a0201b='"+a0201b+"' and exists  ("+authoritySQL+" and t.b0111=b.a0201b)) " +
					" union " +
					" select '"+a0225+"' a0225,t.a0000 a0000,'"+a0101+"' a0101,lpad('"+a0225+"',5,'0') as a0225N,t.a0192a as a0192a from a01 t where a0000='"+a0000+"' and not exists " +
							"(select 1 from  a01 t,(select distinct a0000,a0225  from a02 where a0201b='"+a0201b+"') t2 where t.a0000='"+a0000+"' and t.a0000=t2.a0000 and status='1' and exists (select a0000 from a02 b where t.a0000=b.a0000 and a0255='1' and a0201b='"+a0201b+"' and  a0201b in("+authoritySQL+")))" +
					"order by a0225N";*/
			String sql2="select t2.a0225 a0225, t.a0000 a0000,  t.a0101 a0101,lpad(t2.a0225,5,'0') as a0225N,t.a0192a as a0192a from a01 t,(select distinct a0000, a0225, row_number() over(partition by a0000 order by to_number(a0225) asc) row_number from a02 where a0201b='"+a0201b+"') t2 where t2.row_number = '1' and t.a0000=t2.a0000 and status='1' and exists (select a0000 from a02 b where t.a0000=b.a0000 and a0255='1' and a0201b='"+a0201b+"' and exists  ("+authoritySQL+" and t.b0111=b.a0201b))" +
					" union " +
					" select '"+a0225+"' a0225,t.a0000 a0000,'"+a0101+"' a0101,lpad('"+a0225+"',5,'0') as a0225N,t.a0192a as a0192a from a01 t where a0000='"+a0000+"' and not exists " +
							"(select 1 from  a01 t,(select distinct a0000,a0225  from a02 where a0201b='"+a0201b+"') t2 where t.a0000='"+a0000+"' and t.a0000=t2.a0000 and status='1' and exists (select a0000 from a02 b where t.a0000=b.a0000 and a0255='1' and a0201b='"+a0201b+"' and  a0201b in("+authoritySQL+")))" +
					"order by a0225N";
			this.pageQuery(sql2, "SQL", start, limit); 
			return EventRtnType.SPE_SUCCESS;
		}else{
			return EventRtnType.SPE_SUCCESS;
		}
	}
	
	
	@PageEvent("saveBtn.onclick")
	@Transaction
	@Synchronous(true)
	public int savePersonsort()throws RadowException, AppException{
		List<HashMap<String,String>> list = this.getPageElement("gridA01").getStringValueList();
		String a0201b = this.getPageElement("a0201b").getValue();	
		String cura0000 = this.getPageElement("a0000").getValue();	
		//HBSession sess = null;
		try {
			//sess = HBUtil.getHBSession();
			//int i = list.size();
			int i = 1;
			for(HashMap<String,String> m : list){
				String a0000 = m.get("a0000");
				HBUtil.executeUpdate("update a02 set a0225="+i+" where a0000='"+a0000+"' and a0201b='"+a0201b+"'");
				if(a0000.equals(cura0000)){
					this.getPageElement("a0225").setValue(i+"");
					//this.getExecuteSG().addExecuteCode("parent.window.document.getElementById('iframe_workUnits').contentWindow.document.getElementById('a0225').value='"+i+"'");
					
					this.getExecuteSG().addExecuteCode("parent.document.getElementById('a0225').value='"+i+"'");
				}
				i++;
				
			}
			//对a01表的两个排序字段做维护TORGID（最高机构），TORDER（最高机构所在机体内排序）
			//HBUtil.executeUpdate("update a01 set a01.torgid= get_torgid(a0000) where a0000 = '"+a0000+"'");
			HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') where a01.a0000 in (select a0000 from a02 where a0201b = '"+a0201b+"')");  
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		
		this.getExecuteSG().addExecuteCode("radow.doEvent('gridA01.dogridquery');");
		
		this.setMainMessage("排序成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("closeBtn.onclick")
	public int close() throws AppException, RadowException {
		
		//this.closeCueWindow("A01SortGrid");
		this.getExecuteSG().addExecuteCode("window.close();");
		
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String closeCueWindowEX(){
		return "window.close();";
	}
	
	
	@PageEvent("sortOrderCode")
	@Transaction
	@Synchronous(true)
	public int sortOrderCode()throws RadowException, AppException{
		List<HashMap<String,String>> list = this.getPageElement("gridA01").getStringValueList();
		String a0201b = this.getPageElement("a0201b").getValue();		//当前排序机构
		String cura0000 = this.getPageElement("a0000").getValue();		//当前任免表人员id
		
		try {
			
			for(HashMap<String,String> m : list){
				String a0000 = m.get("a0000");		//当前循环的a0000
				String a0225 = m.get("a0225");		//当前循环的a0255集体内排序
				
				
			    // 邮箱验证规则
			    String regEx =  "/^[1-9]+\\d*$";
			    // 编译正则表达式
			    Pattern pattern = Pattern.compile(regEx);
			    Matcher matcher = pattern.matcher(a0225);
			    // 字符串是否与正则表达式相匹配
			    boolean rs = matcher.matches();
				//判断当前的a0255集体内排序是否合法
				/*if(!rs){
					//this.getExecuteSG().addExecuteCode("radow.doEvent('gridA01.dogridquery');");
					this.setMainMessage("序码存在的不是正整数！");
					return EventRtnType.FAILD;
				}*/
				
				HBUtil.executeUpdate("update a02 set a0225="+a0225+" where a0000='"+a0000+"' and a0201b='"+a0201b+"'");
				if(a0000.equals(cura0000)){
					this.getPageElement("a0225").setValue(a0225+"");
					this.getExecuteSG().addExecuteCode("parent.document.getElementById('a0225').value='"+ a0225 +"'");
				}
				
				//对a01表的两个排序字段做维护TORGID（最高机构），TORDER（最高机构所在机体内排序）
				HBUtil.executeUpdate("update a01 set a01.torgid= get_torgid(a0000) where a0000 = '"+a0000+"'");
				HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') where a01.a0000 = '"+a0000+"'");  
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		
		this.getExecuteSG().addExecuteCode("radow.doEvent('gridA01.dogridquery');");
		this.setMainMessage("排序成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("sortPost")
	@Transaction
	@Synchronous(true)
	public int sortPost()throws RadowException, AppException{
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		
		//组装一个HashMap集合
		String a0201b = this.getPageElement("a0201b").getValue();
		String a0000String = this.getPageElement("a0000").getValue();//人员id
		String a0101 = this.getPageElement("a0101").getValue();//姓名
		String a0225 = this.getPageElement("a0225").getValue();//集体内排序
		if(a0225==null||"".equals(a0225)){
			a0225 = "0";
		}
		String authoritySQL = "select t.b0111 b from COMPETENCE_USERDEPT t where t.userid='"+SysManagerUtils.getUserId()+"'";
		
		String sql="from B01 where B0111='"+a0201b+"' ";
		List alist = HBUtil.getHBSession().createQuery(sql).list();
		if(alist!=null && alist.size()>0){
			String sql2="select t2.a0225 a0225,t.a0000 a0000,  t.a0101 a0101,lpad(t.a0221, 7, '0') AS a0221,lpad(t.a0192e, 5, '0') AS a0192e from a01 t,(select distinct a0000,a0225  from a02 where a0201b='"+a0201b+"') t2 where t.a0000=t2.a0000 and status='1' and exists (select a0000 from a02 b where t.a0000=b.a0000 and a0255='1' and a0201b='"+a0201b+"' and exists  ("+authoritySQL+" and t.b0111=b.a0201b)) " +
					" union " +
					" select '"+a0225+"' a0225,t.a0000 a0000,'"+a0101+"' a0101,lpad(t.a0221, 7, '0') AS a0221,lpad(t.a0192e, 5, '0') AS a0192e from a01 t where a0000='"+a0000String+"' and not exists " +
							"(select 1 from  a01 t,(select distinct a0000,a0225  from a02 where a0201b='"+a0201b+"') t2 where t.a0000='"+a0000String+"' and t.a0000=t2.a0000 and status='1' and exists (select a0000 from a02 b where t.a0000=b.a0000 and a0255='1' and a0201b='"+a0201b+"' and  a0201b in("+authoritySQL+")))" +
					"order by a0221,a0192e";
			
			//
			List<Object[]>  allList = HBUtil.getHBSession().createSQLQuery(sql2).list();
			
			for(int i=0;i<allList.size();i++){
				Object[] info=allList.get(i);
				Object a0000 = info[1];
				
				HashMap<String,String> map = new HashMap<String, String>();
				map.put("a0000", a0000.toString());
				
				list.add(map);
				
			}
			
		}
		
		
		
		//String a0201b = this.getPageElement("a0201b").getValue();	
		String cura0000 = this.getPageElement("a0000").getValue();	
		
		try {
			
			int i = 1;
			for(HashMap<String,String> m : list){
				String a0000 = m.get("a0000");
				HBUtil.executeUpdate("update a02 set a0225="+i+" where a0000='"+a0000+"' and a0201b='"+a0201b+"'");
				if(a0000.equals(cura0000)){
					this.getPageElement("a0225").setValue(i+"");
					
					this.getExecuteSG().addExecuteCode("parent.document.getElementById('a0225').value='"+i+"'");
				}
				i++;
				
				//对a01表的两个排序字段做维护TORGID（最高机构），TORDER（最高机构所在机体内排序）
				HBUtil.executeUpdate("update a01 set a01.torgid= get_torgid(a0000) where a0000 = '"+a0000+"'");
				HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') where a01.a0000 = '"+a0000+"'");  
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		
		this.getExecuteSG().addExecuteCode("radow.doEvent('gridA01.dogridquery');");
		
		this.setMainMessage("排序成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
