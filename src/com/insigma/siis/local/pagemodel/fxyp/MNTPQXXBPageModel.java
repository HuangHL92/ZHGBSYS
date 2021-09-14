package com.insigma.siis.local.pagemodel.fxyp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
    
public class MNTPQXXBPageModel extends PageModel{
	@Override
	public int doInit() throws RadowException {
		String sfys=request.getSession().getAttribute("sfys")==null?"0":request.getSession().getAttribute("sfys").toString();
		this.getPageElement("sfys").setValue(sfys);
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("setB0111")
	@NoRequiredValidate
	public int setB0111() throws RadowException {
		
		String b0111s = this.getPageElement("b0101").getValue();
		if(!StringUtils.isEmpty(b0111s)){
			this.request.getSession().setAttribute("mntp_tpqxx_org", b0111s);
		}else{
			this.request.getSession().removeAttribute("mntp_tpqxx_org");
		}
		this.getExecuteSG().addExecuteCode("updatebtn(document.getElementById('queryType').value)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("updateYs")
	@NoRequiredValidate
	public int updateYs() throws RadowException {
		String sfys = this.getPageElement("sfys").getValue();
		this.request.getSession().setAttribute("sfys", sfys);
		this.getExecuteSG().addExecuteCode("updatebtn(document.getElementById('queryType').value)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String mntp_tpqxx_org = request.getSession().getAttribute("mntp_tpqxx_org")==null?"":request.getSession().getAttribute("mntp_tpqxx_org").toString();
		this.request.getSession().removeAttribute("mntp_tpqxx_org");
		String type=this.getPageElement("queryType").getValue();
		String condition="";
		String name="";
		if("1".equals(type)){//区县市
			condition="select nvl(b0104,b0101) b0104,b0111,b01id from b01 where substr(b0111,1,11)='001.001.004' and length(b0111)=19 and b0131 in('1001','1003','1004','1005') order by b0269";
			//condition="select b0104,b0111,b0183,b0185,decode(b0183,null,decode(b0185,null,'',b0185||'副'),b0183||'正'||decode(b0185,null,'',b0185||'副')) b0188,b0236 from b01 where b0111='001.001.002.01U' order by b0269";
			name="区县市调配前机构人员信息表";
		}else if("2".equals(type)){//区县市平台
			condition="select nvl(b0104,b0101) b0104,b0111,b01id from b01 where b0121 like '001.001.004.%' and length(b0111)=19 and b0131='3480' order by b0269";	
			name="区县市平台调配前机构人员信息表";
		}else if("3".equals(type)){//市直
			condition="select nvl(b0104,b0101) b0104,b0111,b01id from b01 where (b0121 = '001.001.002'"
					+"  and b01id not in ('6C759252379B4E01BFA614D2B06D31FA','B9E46D6110134E77B7273E01EACF21A3','6B48873119494B34A80F0E314549813D')  )"
					+"    or b01id in (  'F0E0EA716C0442328DE41B549BC73C9C','0B3931450F264D36895440B7EAB46B81','24A2EF2597174AE8BF1E65D7EE34DD24',"
					+"   '70BF51BDAD28458DA98B7092B049AECF','BC923D3D6F034B8584C4DDF60071C895','96D6674BC36C428B84166728D0A21455',"
					+"   '72C8D307601E4D15B4DED1B82B41E8DE','A6F4AE51884D4231B7AF3C8623A56884') order by b0269";
			name="市直单位调配前机构人员信息表";
		}else if("4".equals(type)){//国企高校
			condition="select nvl(b0104,b0101) b0104,b0111,b01id  from b01 where b0111 like '001.001.003.%'"
					 +"  and b01id not in ( '36E7A2F90629493AA4FCAB4345AF6F77','6C5E5C831D8443D48EBF42E89CDE7055','DBAD9B3F2C2E4BAE935FC8F4C245555D','3601E75C9C6F4EF4AD7274C9D6CA09B5','B9E46D6110134E77B7273E01EACF21A3','6C759252379B4E01BFA614D2B06D31FA','D563DF546904420B8D042038B927549A','7D174AA620804C608774111467F3F111')"
					 +"  and b0111 not like '001.001.002.02N%'  and b0114 not like '%X09' order by b0269";	
			name="国企高校调配前机构人员信息表";
		}
		List<HashMap<String, Object>> list_b01=this.queryB01(condition);
		if(list_b01!=null&&list_b01.size()>0){
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(HashMap<String, Object> m : list_b01){
				String b0104 = m.get("b0104")==null?"":m.get("b0104").toString();
				String b0111 = m.get("b0111")==null?"":m.get("b0111").toString();
				String b01id = m.get("b01id")==null?"":m.get("b01id").toString();
				mapCode.put(b0111, b0104);
			}
			((Combo)this.getPageElement("b0101")).setValueListForSelect(mapCode);
			
			
			if(!StringUtils.isEmpty(mntp_tpqxx_org)){
				this.getPageElement("b0101").setValue(mntp_tpqxx_org);
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("queryB01")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryB01(String condition) throws RadowException {
		List<HashMap<String, Object>> list=null;
		try{
			CommQuery commQuery =new CommQuery();
			list = commQuery.getListBySQL(condition);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@PageEvent("querySPZS")
	@NoRequiredValidate
	public String querySPZS(String b0111) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String str="";
		String sql6 = "select count(*) ZZ from (SELECT A01.A0000 FROM a02, a01 "
				+ "WHERE a01.A0000 = a02.a0000 AND a01.a0163='1' and a01.status='1' and a02.a0281 = 'true' AND a02.a0255 = '1' "
				+ "and a02.a0201e='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b='"+b0111+"') tt ";
		Object o6 = sess.createSQLQuery(sql6).uniqueResult();
		int zz = Integer.valueOf(o6.toString());
		String sql7 = "select count(*) FZ from (SELECT A01.A0000 FROM a02, a01 "
				+ "WHERE a01.A0000 = a02.a0000 AND a01.a0163='1' and a01.status='1' and a02.a0281 = 'true' AND a02.a0255 = '1' "
				+ "and a02.a0201e in ('3','31') and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b='"+b0111+"') tt ";
		Object o7 = sess.createSQLQuery(sql7).uniqueResult();
		int fz = Integer.valueOf(o7.toString());
		if(zz>0) {
			str=str+zz+"正";
		}
		if(fz>0) {
			str=str+fz+"副";
		}
		return str;
	}
	
	@PageEvent("queryBZRY")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryBZRY(String b0111) throws RadowException {
		List<HashMap<String, Object>> list=null;
		String sql= "select a0000,a0101,nvl(substr(a0107,1,4)*12+substr(a0107,5,2),0) a0107,a0215a,a0248,a0277,gwmc,"
				+ " case when a0165 like '%10%' then '10' when a0165 like '%11%' then '11' else '12' end  a0165,"
				+ "nvl(to_char(sysdate,'yyyy')*12+to_char(sysdate,'MM')-substr(a0192f,1,4)*12-substr(a0192f,5,2),0) a0192f,"
				+ " case when substr(a0201b,1,15) in ('001.001.002.01O','001.001.002.01Q','001.001.002.02O') then '1' else '2' end  a0201b"
				+ " from  (select a02.a0200,a01.a0000,a01.a0101,a0107,a02.a0215a,a02.a0248,"
				+ "			a02.a0277,a0165,a0192f,a0201b,(select gwmc from gwpxall b where b0111='"+b0111+"' and a02.a0200=b.id) gwmc from a02, a01 "
				+ "		 WHERE a01.A0000 = a02.a0000  AND a01.a0163 = '1'  and a01.status = '1'"
				+ "			 and a02.a0281 = 'true'  and a02.a0201b = '"+b0111+"'"
				+ " 		 and a02.a0201e in ('1','3','31') " 
				+ "	  union all "
				+ "		   select qpid,'','','',gwname,'','','','','"+b0111+"',(select gwmc from gwpxall b where b0111='"+b0111+"' and BGWQP.qpid=b.id) gwmc from BGWQP where b01id=(select b01id from b01 where b0111='"+b0111+"')  ) t"
				+ "  order by nvl((select sortid from gwpxall b where b0111='"+b0111+"' and t.a0200=b.id),99999)";
		try{
			CommQuery commQuery =new CommQuery();
			list = commQuery.getListBySQL(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
//	@PageEvent("queryQpxx")
//	@NoRequiredValidate
//	public List<HashMap<String, Object>> queryQpxx(String b0111) throws RadowException {
//		List<HashMap<String, Object>> list=null;
//		String sql= "select * from BGWQP where b01id in (select b01id from b01 where b0111='"+b0111+"') order by sortid";
//		try{
//			CommQuery commQuery =new CommQuery();
//			list = commQuery.getListBySQL(sql);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return list;
//	}
	
//	@PageEvent("querySumqp")
//	@NoRequiredValidate
//	public String querySumqp(String b0111) throws RadowException {
//		List<HashMap<String, Object>> list=null;
//		String sql= "select nvl(sum(gwnum),'0') cnt from BGWQP where b01id in (select b01id from b01 where b0111='"+b0111+"')";
//		try{
//			CommQuery commQuery =new CommQuery();
//			list = commQuery.getListBySQL(sql);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		String number=list.get(0).get("cnt").toString();
//		
//		return number;
//	}
	
	public String isnull(Object obj) {
		String str="";
		if(obj==null||"".equals(obj)) {
			
		}else {
			str=obj.toString();
		}
		return str;
	}
	
	//获取当前日期的month
	public int getMonth() {
		int number=0;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		String date=df.format(new Date());
		String year=date.substring(0,4);
		String month=date.substring(4);
		number=Integer.valueOf(year)*12+Integer.valueOf(month)+6;
		return number;
	}
	
	/**
	 * 修改人员信息的双击事件
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("openRmb")
	@GridDataRange
	public int openRmb(String a0000) throws RadowException, AppException{  //打开窗口的实例

		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			String rmbs=this.getPageElement("rmbs").getValue();

			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+a0000+"');}}, 500);");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	//重点岗位查询
	@PageEvent("setGWSQL")
	@GridDataRange
	@NoRequiredValidate
	public int queryByPerson(String fxyp00) throws RadowException {
		Object[] query = (Object[]) HBUtil.getHBSession()
				.createSQLQuery("select b.sql,b.wayname from gwpxall a, zdgw_way b where a.id='" + fxyp00
						+ "' and  a.gwmc = b.wayid ")
				.uniqueResult();
		String sql = "";
		String name = "";
		String flag = " order by sort ";
		if (query != null) {
			Object sqlo = query[0];
			if(sqlo==null){
				this.setMainMessage("未配置查询条件");
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				sql = sqlo.toString();
			}
			name = query[1].toString();
		}
		this.request.getSession().setAttribute("gbmcName", name);
		this.request.getSession().setAttribute("gbmcSql", sql);
		this.request.getSession().setAttribute("gbmcFlag", flag);
		this.getExecuteSG().addExecuteCode("openYouGuanRenXuann('"+fxyp00+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
