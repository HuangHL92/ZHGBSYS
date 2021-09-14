package com.insigma.siis.local.pagemodel.sysorg.org;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;

import net.sf.json.JSONObject;

import org.hibernate.Session;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtFunction;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.entity.SmtUserselfcolumn;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.MD5;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.business.sysorg.org.OrgNodeTree;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
//import com.insigma.siis.local.business.sysorg.SysOrgBs;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;
import com.insigma.siis.local.pagemodel.publicServantManage.chooseZDYtemPageModel;
import com.insigma.siis.local.pagemodel.search.PreSubmitPageModel;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;
import com.insigma.siis.local.pagemodel.exportexcel.FiledownServlet;

/**
 * @author lixy
 *
 */
public class JgTreePageModel extends PageModel {
	public static String b01String="";
	public static Map<Integer, String> map;
	/**
	 * ϵͳ������Ϣ
	 */
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String queryType2="0";//1�����������ѯ2�����ť��ѯ
	public static String tag="0";//0δִ��1ִ��
	


	@Override
	public int doInit() {
		
//		this.getExecuteSG().addExecuteCode("odin.ext.getCmp('peopleInfoGrid').view.refresh();");
		this.setNextEventName("peopleInfoGrid.dogridquery");
	    return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * �޸���Ա��Ϣ��˫���¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("peopleInfoGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{  //�򿪴��ڵ�ʵ��
		this.getExecuteSG().addExecuteCode("addTab('','"+this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	//�������ѯ�¼�
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException, AppException {
		/*if(!SysRuleBS.havaRule(id))
			throw new AppException("��û�и���֯Ȩ��");*/
		String userID = SysManagerUtils.getUserId();
		Map<String, String> map = PublicWindowPageModel.isHasRule(id, userID);
		if (!map.isEmpty()||map==null) {
			if ("2".equals(map.get("type"))){
				this.setMainMessage("��û�иû�����Ȩ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		String sql = CustomQueryPageModel.getcommSQL() + 
		" where  exists (select 1 from a02 where  a0201b='"+id+"' and (a02.a0201b !='-1' and a0255='1') and " +
				" a01.a0000=a02.a0000)  and a01.status='1' ";
		this.getPageElement("sql").setValue(sql);
        this.getPageElement("a0201b").setValue(id);
        this.request.getSession().setAttribute("queryType2", "1");
        this.getExecuteSG().addExecuteCode("lict()");
		this.setNextEventName("peopleInfoGrid.dogridquery");		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("selectAllBtn.onclick")
	public int selectAll() throws RadowException {
//		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("optionGroup.onchange")
	public void showmessage(){
		this.setMainMessage("sdfasdfa");
	}
	
	@PageEvent("duty.onchange")
	@NoRequiredValidate
	@AutoNoMask
	public int eab025change() throws RadowException{
		String str=this.getPageElement("duty").getValue();
		PageElement pe=this.createPageElement("duty1",ElementType.SELECT,false);
		String arg0 = "substr(aaa102,0,1)='"+str.substring(0, 1)+"'";
//		String arg0 = "aaa102='132'";
		pe.loadDataForSelectStore("ZB09","",arg0,true);
		this.getPageElement("duty1").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("peopleInfoGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException, SQLException{
		List<String> list = new ArrayList<String>();
		String sql = "select otherinfo from smt_user where userid = '"+SysManagerUtils.getUserId()+"' ";
		String id = (String) request.getSession().getAttribute("zdyid");
		if(id==null||"".equals(id)){
			id = (String) request.getSession().getAttribute("GXYtemID");
		}
		//System.out.println(sql);
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		while(re.next()){
			String jgbm = re.getString(1);
			list.add(jgbm);
		}
		for (String string : list) {
			//String sql1 = "select userid a0000, u.loginname loginname,b.b0101 b0101 from smt_user u, b01 b where b.b0111 = u.otherinfo and u.otherinfo like '"+string+"%'";
			String sql1 = "select su.userid a0000,su.loginname,su.b0101,decode(x.userid, null, 'false', 'true') personcheck "+
			  "from (select userid userid, u.loginname loginname, b.b0101 b0101,b.b0111 b0111 from smt_user u, b01 b where b.b0111 = u.otherinfo  and u.otherinfo like '"+string+"%') su "+
			  "left join powergx x on su.userid = x.userid and x.modelid = '"+id+"' order by su.b0111 ";
			this.pageQuery(sql1, "SQL", start, limit);
		}
		/*String GXYtemID = (String) request.getSession().getAttribute("GXYtemID");
		if(GXYtemID!=null&&!"".equals(GXYtemID)){
			String sql2 = "select ";
			StringBuffer ids = new StringBuffer(); 
			this.getExecuteSG().addExecuteCode("defChecked("+ids+")");
		}*/

		return EventRtnType.SPE_SUCCESS;
	}
	@PageEvent("query2")
	public int query2(String value) throws RadowException{
		String userID = SysManagerUtils.getUserId();
//		List alist = new ArrayList();
//		String b0111= SysUtil.getCacheCurrentUser().getUserGroups().get(0).getId();
		String b01String = value;
		//=========================================���ȷ��֮��Ѳ���b01String����chooseall��������չʾ����{
		request.getSession().setAttribute("true", "ȷ��");
		//=========================================}
		StringBuffer a02_a0201b_sb = new StringBuffer("");
        StringBuffer cu_b0111_sb = new StringBuffer("");
        if(b01String!=null && !"".equals(b01String)){
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" and (1=2 ");
				cu_b0111_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// ����jsonObject���ݣ���ӵ�Map����
			while (it.hasNext()) {
				String nodeid = it.next(); 
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");//[�������ƣ��Ƿ�����¼����Ƿ񱾼�ѡ��]
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
		StringBuffer sb = new StringBuffer("");
			String a0160 = this.getPageElement("a0160").getValue();//��Ա���
			String a0163 = this.getPageElement("a0163").getValue();//��Ա״̬
			String ageS = this.getPageElement("age").getValue();//��ʼ����
			String ageE = this.getPageElement("age1").getValue();//��������
			String female = this.getPageElement("female").getValue();//�Ա��Ƿ�Ů
			if(female.equals("1")){
				female = "2";
			}
			String minority = this.getPageElement("minority").getValue();//�Ƿ���������
			String nonparty = this.getPageElement("nonparty").getValue();//�Ƿ���й���Ա	
			String duty = this.getPageElement("duty").getValue();//��ʼְ����
			String duty1 = this.getPageElement("duty1").getValue();//����ְ����
			CommonQueryBS.systemOut("duty:::::"+duty);;
			CommonQueryBS.systemOut("duty1:::::"+duty1);
//			if(duty==""|| duty.length()<=0){
////				duty = "199";
//			}
//			if(duty1==""|| duty1.length()<=0){
////				duty1 = "020";
//			}
			String dutynow = this.getPageElement("dutynow").getValue();//ְ������ʼ����
			String dutynow1 = this.getPageElement("dutynow1").getValue();//ְ���ν�������
			String a0219 = this.getPageElement("a0219").getValue();//ְ�����
			String edu = this.getPageElement("edu").getValue();//��ʼѧ��
			String edu1 = this.getPageElement("edu1").getValue();//����ѧ��
			
//			String a0201b=this.getPageElement("a0201b").getValue();
//			if(edu==""|| edu.length()<=0){
//				edu = "81";
//			}
//			if(edu1==""|| edu1.length()<=0){
//				edu1 = "1";
//			}
			String allday = this.getPageElement("allday").getValue();//�Ƿ�ȫ����
			if(!"1".equals(allday)){
//				allday="2";
	            allday="";  //���������Ϊ��δ��ѡ�����  ѧ���Ƿ�ȫ�������� ��bug��ţ�708��  
            }
			
			if(!StringUtil.isEmpty(duty) && !StringUtil.isEmpty(duty1)){
				CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", duty);
				CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", duty1);
				if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
					try {
						throw new AppException("ְ���η�Χ������ͬһ������飡");
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//ְ���� ֵԽС ������˼Խ�߼�
				if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
					try {
						throw new AppException("ְ���η�Χ����ȷ�����飡");
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			if(!StringUtil.isEmpty(dutynow) && !StringUtil.isEmpty(dutynow1)){
				if(dutynow.compareTo(dutynow1)>0){
					try {
						throw new AppException("����ְ����ʱ�䷶Χ����ȷ�����飡");
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			if(!StringUtil.isEmpty(edu) && !StringUtil.isEmpty(edu1)){
				CodeValue eduCodeValue =RuleSqlListBS.getCodeValue("ZB64", edu);
				CodeValue edu1CodeValue =RuleSqlListBS.getCodeValue("ZB64", edu1);
				//ְ�� ֵԽС ������˼Խ�߼�
				if(eduCodeValue.getCodeValue().compareTo(edu1CodeValue.getCodeValue())<0){
					try {
						throw new AppException("ѧ����Χ����ȷ�����飡");
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
			//��ΧУ�� end
			StringBuffer a01sb = new StringBuffer("");
			if (!a0160.equals("")){
				a01sb.append(" and ");
				a01sb.append("a01.a0160 ='"+a0160+"'");
			}
			/*if (!a0163.equals("")){
				a01sb.append(" and ");
				a01sb.append("a01.a0163='"+a0163+"'");
			}*/
			if (!female.equals("0")){
				a01sb.append(" and ");
				a01sb.append("a01.a0104='"+female+"'");
			}
			if (!minority.equals("0")){
				a01sb.append(" and ");
				a01sb.append("a01.a0117!='01'");
			}
			if (nonparty.equals("1")){
				a01sb.append(" and ");
				a01sb.append("a01.a0141!='01'");			
			}
	        if(!"".equals(duty1)){
	        	a01sb.append(" and ");
	        	a01sb.append("a01.a0148>='"+duty1+"'");		
	        }
	        if(!"".equals(duty)){
	        	a01sb.append(" and ");
	        	a01sb.append("a01.a0148<='"+duty+"'");		
	        }      
	        if (!ageS.equals("")){
	        	a01sb.append(" and ");
	        	a01sb.append("get_birthday(a01.a0107,'"+DateUtil.getcurdate()+"')>"+ageS+"");
			}
			if (!ageE.equals("")){
				a01sb.append(" and ");
				a01sb.append("get_birthday(a01.a0107,'"+DateUtil.getcurdate()+"')<"+ageE+"");
			}
	        StringBuffer a02sb = new StringBuffer("");  
	        if(!"".equals(dutynow)){
	        	a02sb.append(" and ");
	        	a02sb.append("a02.a0288>='"+dutynow+"'");
	        }
	        if(!"".equals(dutynow1)){
	        	a02sb.append(" and ");
	        	a02sb.append(" a02.a0288<='"+dutynow1+"'");
	        }
	        if(!"".equals(a0219)){
	        	a02sb.append(" and ");
	        	a02sb.append(" a02.a0219='"+a0219+"' ");
	        }
	        
	        StringBuffer orther_sb = new StringBuffer("");
	        if(!"".equals(edu)&&"".equals(edu1)){
	        	orther_sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B <='"+edu+"'");
	            if(!"".equals(allday)){
	            	orther_sb.append(" and ");
	            	orther_sb.append("a0837 = '"+allday+"'");		
	            }
	            orther_sb.append(")");
	        }
	        if(!"".equals(edu1)&&"".equals(edu)){
	        	orther_sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B >='"+edu1+"'");	
	            if(!"".equals(allday)){
	            	orther_sb.append(" and ");
	            	orther_sb.append("a0837 = '"+allday+"'");		
	            }
	            orther_sb.append(")");
	        }
	        if(!"".equals(edu1)&&!"".equals(edu)){
	        	orther_sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B between '"+edu1+"' and '"+edu+"'");
	            if(!"".equals(allday)){
	            	orther_sb.append(" and ");
	            	orther_sb.append("a0837 = '"+allday+"'");		
	            }
	            orther_sb.append(")");
	        }
	        String finalsql = "";
	        
	        this.getPageElement("sql").setValue("select * from ("+finalsql+") a01 where 1=1 ");
	        //�������չʾ��������chooseall��
	        ////====================================={
	        this.request.getSession().setAttribute("truesql", "select * from ("+finalsql+") a01 where 1=1 ");
	        this.getPageElement("checkList").setValue("");
	        ////=====================================}

//			sb.append(CustomQueryPageModel.getcommSQL() +
//					" where    a01.status='1' ");
//			
//			if (!a0160.equals("")){
//				sb.append(" and ");
//				sb.append("a01.a0160 ='"+a0160+"'");
//			}
//			if (!a0163.equals("")){
//				sb.append(" and ");
//				sb.append("a01.a0163='"+a0163+"'");
//			}
//			if (!ageS.equals("")){
//				sb.append(" and ");
//				sb.append("a01.age>'"+ageS+"'");
//			}
//			if (!ageE.equals("")){
//				sb.append(" and ");
//				sb.append("a01.age<'"+ageE+"'");
//			}
//			if (!female.equals("0")){
//				sb.append(" and ");
//				sb.append("a01.a0104='"+female+"'");
//			}
//			if (!minority.equals("0")){
//				sb.append(" and ");
//				sb.append("a01.a0117<>'01'");
//			}
//			if (nonparty.equals("1")){
//				sb.append(" and ");
//				sb.append("a01.a0141<>'01'");			
//			}
//            if(!"".equals(duty1)){
//				sb.append(" and ");
//				sb.append("a01.a0148<='"+duty1+"'");		
//            }
//            if(!"".equals(duty)){
//				sb.append(" and ");
//				sb.append("a01.a0148>='"+duty+"'");		
//            }
//            
//            sb.append(" and exists (select 1 from a02 where  a02.a0201b !='-1' and a0255='1' and  a01.a0000=a02.a0000 ");
//            
//            if(!"".equals(dutynow)){
//				sb.append(" and ");
//				sb.append("a02.a0288>='"+dutynow+"'");		
//            }
//            if(!"".equals(dutynow1)){
//				sb.append(" and ");
//				sb.append("a02.a0288<='"+dutynow1+"'");		
//            }
//            if(!"".equals(a0219)){
//				sb.append(" and ");
//				sb.append("a02.a0219='"+a0219+"'");		
//            }
//            if(!"".equals(b01String)&&b01String!=null){
//				sb.append(" and ");
//				sb.append("a02.A0201B in ("+b01String+")");
//                this.getPageElement("a0201b").setValue(b01String.replace("'", "|").replace(",", "@"));	
//            }
//            sb.append(")");
//            
//            
//            if(!"".equals(edu)&&"".equals(edu1)){
//				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B <='"+edu+"'");
//	            if(!"".equals(allday)){
//					sb.append(" and ");
//					sb.append("a0837 = '"+allday+"'");		
//	            }
//	            sb.append(")");
//            }
//            if(!"".equals(edu1)&&"".equals(edu)){
//				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B >='"+edu1+"'");	
//	            if(!"".equals(allday)){
//					sb.append(" and ");
//					sb.append("a0837 = '"+allday+"'");		
//	            }
//	            sb.append(")");
//            }
//            if(!"".equals(edu1)&&!"".equals(edu)){
//				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B between '"+edu1+"' and '"+edu+"'");
//	            if(!"".equals(allday)){
//					sb.append(" and ");
//					sb.append("a0837 = '"+allday+"'");		
//	            }
//	            sb.append(")");
//            }
            this.request.getSession().setAttribute("queryType2", "2");
            
//    		this.getPageElement("sql").setValue(sb.toString());
    		//CommonQueryBS.systemOut(this.getPageElement("sql").getValue());
			this.setNextEventName("peopleInfoGrid.dogridquery");

			return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	public List<B01> selectListBySubId(String id){
		List<B01> list = new ArrayList<B01>();
		String sql="from B01 where B0111 like '"+id+"%'";
		list = HBUtil.getHBSession().createQuery(sql).list();
		return list;
	}
	
	@PageEvent("dogrant")
	public  int  gettree(String value) throws RadowException {
		String ret=null;
		String[] nodes = null;
		HashMap<String,String> nodemap = new HashMap<String,String>();
		if(value !=null) {
			nodes = value.split(",");
			B01 b01 =new B01();
			for(int i=0;i<nodes.length;i++) {
				if(nodes[i].split(":")[2].equals("1")){
					List<B01> b01s = selectListBySubId(nodes[i].split(":")[0]);
					for(int j=0;j<b01s.size();j++){
						b01=b01s.get(j);
						nodemap.put(b01.getB0111(),nodes[i].split(":")[1]);
					}
				}else if(nodes[i].split(":")[2].equals("2")){
					List<B01> b01s = selectListBySubId(nodes[i].split(":")[0]);
					for(int j=0;j<b01s.size();j++){
						b01=b01s.get(j);
						nodemap.put(b01.getB0111(),nodes[i].split(":")[1]);
					}
				}else{
					nodemap.put(nodes[i].split(":")[0], nodes[i].split(":")[1]);
				}
			}
		}
		StringBuffer addresourceIds = new StringBuffer();
		StringBuffer removeresourceIds = new StringBuffer();
		for(String node :nodemap.keySet()) {
			if(nodemap.get(node).equals("true")) {
				addresourceIds.append("'"+node+"',");
			}else if(nodemap.get(node).equals("false")) {
				removeresourceIds.append(node+",");
			}
		}
		ret=addresourceIds.toString();
		if(ret!=null&&!"".equals(ret)){
		   b01String=ret.substring(0,ret.lastIndexOf(","));
		   this.request.getSession().setAttribute("b01String", b01String);
		  // this.request.getSession().setAttribute("queryType", "1");
		}else{
			this.request.getSession().setAttribute("b01String", "");
		}
//		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	
	@PageEvent("getCheckList")//�÷����Ǹ�����ģ����һ��ģ�û�����꣬ɾ������ʱ��Ҫ���ǵ�ǰ��Ա�Ļ��������ǲ��ǣ��������powergx ҲҪ�ѵ�ǰ�˵Ļ���ҲҪɾ��
	public int getCheckList() throws RadowException{
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("Zhishow")//���ȷ����ť֮���ѡ�е��˷ŵ�powergx����
	public int Zhishow() throws RadowException, AppException{
		List<String> rlist = new ArrayList<String>();
		PageElement pe = this.getPageElement("peopleInfoGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		//��Ҫ����û��ѡѡ�˵��̵�
		for (HashMap<String, Object> hm : list) {
			String o = hm.get("personcheck").toString();
			//boolean b = (Boolean)o;
			if(!"".equals(hm.get("personcheck"))&&"true".equals(o)){
				String qqq = (String) hm.get("a0000");//�����userid
				rlist.add(qqq);
			}
		}
		int a = 0;
		PreparedStatement prepareStatement = null;
		Statement stm = null;
		//��Ҫ����û��ѡѡ�˵��̵�
		if(rlist !=null && !"".equals(rlist) && rlist.size()>0){
			//�ѵ�ǰ�������˼ӵ�powergx������ɾ����ʱ����������ʱ����
			try{
				String sql3 ="select LOGINNAME from smt_user where userid = '"+SysManagerUtils.getUserId()+"'";
				ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql3);
				re.next();
				String name0 = re.getString(1);//��¼��
				String id = (String) request.getSession().getAttribute("zdyid");
				String sql4 = "select tpname from listoutput where tpid = '"+id+"' group by tpname";
				ResultSet re1 = HBUtil.getHBSession().connection().createStatement().executeQuery(sql4);
				String name1 = "";
				if(re1.next()){
					name1 = re1.getString(1);//ģ������
				}
				String name = name1; // + "��"+name0+"��";
				String uuid = UUID.randomUUID().toString().replace("-", "");
				if(id==null||"".equals(id)){
					//ȡ������
					id = (String) request.getSession().getAttribute("GXYtemID");
					stm = HBUtil.getHBSession().connection().createStatement();
					for (int i = 0; i < rlist.size(); i++) {
						String qqq = (String) rlist.get(i);
						String sql = "delete from powergx where modelid = '"+id+"' and userid = '"+qqq+"' and owenr = '"+SysManagerUtils.getUserId()+"'  ";
						stm.addBatch(sql);
						a++;
						if(a % 10 == 0){
							stm.executeBatch();
							stm.clearBatch();
						}
					}
				}else{
					stm = HBUtil.getHBSession().connection().createStatement();
					for (int i = 0; i < rlist.size(); i++) {
						String qqq = (String) rlist.get(i);
						String repSql = "select 1 from powergx where userid='"+qqq+"' and modelid = '"+id+"' ";
						//List<Object> count = HBUtil.getHBSession().createSQLQuery(repSql).list();
						ResultSet rs = HBUtil.getHBSession().connection().createStatement().executeQuery(repSql);
						if(rs.next()){
								continue;
						}
						
						String sql  = "insert into powergx (uuid,modelid,userid,tpname,owenr) values ('"+uuid+"','"+id+"','"+qqq+"','"+name+"','"+SysManagerUtils.getUserId()+"')";
						
						
						stm.addBatch(sql);
						a++;
						if(a % 10 == 0){
							stm.executeBatch();
							stm.clearBatch();
						}
					}
				}
				if(stm != null && a % 10 != 0){
					stm.executeBatch();
					stm.clearBatch();
				}
				stm.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			request.getSession().setAttribute("GXYtemID","");
			request.getSession().setAttribute("zdyid","");
			//String sql2  = "insert into powergx (uuid,tpname,modelid,userid,owenr) values ('"+uuid+"','"+name+"','"+zdyid+"','"+SysManagerUtils.getUserId()+"','1')";
			//HBUtil.getHBSession().connection().createStatement().executeUpdate(sql2);
			closeCueWindow("transferSysOrgWin");
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('templateInfoGrid2').store.reload();");
		}else{
			this.setMainMessage("��û��ѡ����ĵ�λ");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public void closeCueWindow(String arg0) {
	this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('"+arg0+"').close();");}

	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String jsonStr =OrgNodeTree.getCodeTypeJS("4");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	/**
	 * �鿴/ɾ������
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("modifyAttach")
	public int editFile(String value) throws RadowException{
		this.setRadow_parent_data(value);
		this.openWindow("modifyFileWindow", "pages.search.ModifyAttach");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * Ԥ���ļ�
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */


	@PageEvent("tiaozhuan")
	public int tiaozhuan( ) throws RadowException{

		this.getExecuteSG().addExecuteCode("addTab('Ԥ��','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.search.PreSubmit',false,false)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
//	//�Զ���ģ����ֵ
	@PageEvent("choosedata")
	public int choosedata() throws RadowException{
		String s = "";
		try {
			ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select tpid,tpname from listoutput where tptype=1 and tpkind=1 group by tpid,tpname").executeQuery();
			while (res.next()) {
				s = s+"["+res.getString(1)+",'"+res.getString(2)+"'],";
			}
			this.request.getSession().setAttribute("s", s);
//			this.setRadow_parent_data(this.getPageElement("checkList").getValue());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//�Զ���ģ��չʾ
	@PageEvent("showtem")
	public int showtem(String value) throws RadowException{
		this.request.getSession().setAttribute("tpid", value);
		this.request.getSession().setAttribute("personids", this.getPageElement("checkList").getValue());
		this.getExecuteSG().addExecuteCode("addTab('���','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//��Ԥ������
	@PageEvent("showtemplate")
	public int showtemplate( ) throws RadowException{
		String tabname = this.getPageElement("tabname").getValue();
		this.getExecuteSG().addExecuteCode("addTab('"+tabname+"','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.search.PreSubmit',false,false)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	

	
	@PageEvent("chooseall")
	public int chooseall() throws RadowException{
		String sql = "";
		String id = "";
		String check = (String) request.getSession().getAttribute("true");
		if("ȷ��".equals(check)){
			sql = 	(String) request.getSession().getAttribute("truesql");
			List<Object[]> idall = HBUtil.getHBSession().createSQLQuery(sql).list();
			for(int a = 0;a<idall.size();a++){
				String ido = (String) idall.get(a)[0];
				id += "|"+ido+"|@";
			}
		}else{
			String groupid = this.getPageElement("a0201b").getValue();
			Object obj = this.getPageElement("existsCheckbox").getValue();
			if("1".equals(obj)){
//				sql = "select a01.a0000 from a01 a01,a02 a02 where a01.a0000 = a02.a0000 and a02.a0255 = '1' ";
				sql = "select a01.a0000 from a01 a01,a02 a02 where a01.a0000 = a02.a0000 and a02.a0201b like '"+groupid+"%' and a02.a0255='1' and a01.status='1' group by a01.a0000";
			}else{
				sql = "select a01.a0000 from a01 a01,a02 a02 where a01.a0000 = a02.a0000 and a02.a0201b = '"+groupid+"' and a02.a0255='1' and a01.status='1' group by a01.a0000";
			}
			List idall = HBUtil.getHBSession().createSQLQuery(sql).list();
			for(int a = 0;a<idall.size();a++){
				  String ido = (String) idall.get(a);
				id += "|"+ido+"|@";
			}
		}
		id = id.substring(0,id.length()-1);
		this.request.getSession().setAttribute("checkListAll",id);
		this.request.getSession().setAttribute("personidsall",id);
		this.request.getSession().setAttribute("yListall",id);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ģ�干���б���ѡ���¼�
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("peopleInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int peopleInfoGridOnRowClick() throws RadowException, AppException {
		
		int index = this.getPageElement("peopleInfoGrid").getCueRowIndex();
		String a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000", index).toString();
		
		Grid grid = (Grid)this.getPageElement("peopleInfoGrid");
		List<HashMap<String,Object>> gridList = grid.getValueList();
		List<HashMap<String,Object>> newList = new ArrayList<HashMap<String,Object>>();
		//1.��ѡ���У�ȡ�������еĹ�ѡ  
		for(int i=0;i<gridList.size();i++){
			HashMap<String,Object> map = gridList.get(i);
			
			if(a0000.equals(map.get("a0000"))){
				
				if(map.get("personcheck").toString().equals("true")){	//����Ѿ���ѡ���ٵ����ȥ����ѡ
					map.put("personcheck", false);
				}else{
					map.put("personcheck", true);
				}
				
			}
			newList.add(map);
		}
		grid.setValueList(newList);
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	
}
