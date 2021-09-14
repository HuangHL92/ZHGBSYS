package com.insigma.siis.local.pagemodel.cbdHandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class UploadCBDPageModel extends PageModel {

	/**
	 * ϵͳ������Ϣ
	 */
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String queryType="0";//1�����������ѯ2�����ť��ѯ
	public static String tag="0";//0δִ��1ִ��
	public static String b01String="";
	public static String cbdType="1";//�ʱ������� 1Ϊ����2Ϊ�ϱ�
	public static String cbdStatus ="3";//0�����У�1����ɣ�2�˻�
	
	public UploadCBDPageModel(){
		try {
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname=user.getLoginname();
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);
			
			HBSession sess = HBUtil.getHBSession();
			Object[] area = null;
			if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")){
				area = SysOrgBS.queryInit();
				areaInfo.put("manager", "true");
			}else{
				area =  SysOrgBS.queryInit();
				areaInfo.put("manager", "false");
			}
			if(area!=null ) { 
				if(area[2].equals("1")){
					area[2]="picOrg";
				}else if(area[2].equals("2")){
					area[2]="picInnerOrg";
				}else{
					area[2]="picGroupOrg";
				}
				areaInfo.put("areaname", area[0]);
				areaInfo.put("areaid", area[1]);
				areaInfo.put("picType", area[2]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//�������ѯ�¼�
		@PageEvent("querybyid")
		@NoRequiredValidate
		public int query(String id) throws RadowException, AppException {
			if(!SysRuleBS.havaRule(id))
				throw new AppException("��û�и���֯Ȩ��");
			String sql="select distinct a01.a0000,a0101,a0104,a0107,a0117,a0141,a0192,a0193,a01.a0184 from A01 a01,A02 a02 where a01.a0000=a02.a0000 "+
				    " and a02.A0201B='"+id+"'";
			B01 b01 = CreateSysOrgBS.LoadB01(id);
			this.getPageElement("sql").setValue(sql);
	        this.getPageElement("a0201b").setValue(id);					
	        this.getPageElement("ereaname").setValue(b01.getB0101());					
			this.setNextEventName("peopleInfoGrid.dogridquery");		
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		@PageEvent("peopleInfoGrid.dogridquery")
		public int doMemberQuery(int start,int limit) throws RadowException{
			    String sql=this.getPageElement("sql").getValue();
		        //�û�ֻ�ܲ�ѯ�Լ���Ȩ�޵Ļ����µ���Ϣ
		        CurrentUser user = SysUtil.getCacheCurrentUser();
		        if(!areaInfo.get("manager").equals("true"))
		        sql=sql+" and a02.A0201B in (select b0111 from COMPETENCE_USERDEPT where userid='"+user.getId()+"')";
		        
				this.pageQuery(sql, "SQL", start, limit);
				return EventRtnType.SPE_SUCCESS;
			
		}

	@Override
	public int doInit() throws RadowException {
		this.request.getSession().setAttribute("cbdType", "1");
		this.request.getSession().setAttribute("cbdStatus", "3");
		CommonQueryBS.systemOut(cbdType);
		this.setNextEventName("CBDGrid.dogridquery");
		return 0;
	}
	
	/**
	 * ��ѯ�ʱ�����¼
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("CBDGrid.dogridquery")
	@NoRequiredValidate           ///??????
	public int dogridQuery(int start,int limit) throws RadowException{
		String sql = "";
		cbdType = (String)this.request.getSession().getAttribute("cbdType");
		cbdStatus = (String)this.request.getSession().getAttribute("cbdStatus");
		CommonQueryBS.systemOut(cbdType);
		//��ȡ��¼�û�����Ϣ
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		if(cbdType.equals("1")){
			if(cbdStatus.equals("0")||cbdStatus.equals("1")||cbdStatus.equals("2")){
				sql = "select ci.cbd_id," +
						"ci.cbd_name," +
						"ci.cbd_path," +
						"ci.cbd_date," +
						"ci.cdb_word_year_no as word," +
						"ci.cbd_leader,"+
						"ci.cbd_date1," +
						"ci.cbd_organ," +
						"ci.cbd_text," +
						"ci.cbd_personid," +
						"ci.cbd_personname," +
						"ci.cbd_userid," +
						"ci.cbd_username,"+
						"ci.status,"+
						"ci.objectno from cbd_info ci" +
						" where ci.cbd_userid = '"+user.getId()+"' and ci.cbd_path = '1' and ci.status = '"+cbdStatus+"' order by ci.cbd_date";
			}else{
				sql = "select ci.cbd_id," +
						"ci.cbd_name," +
						"ci.cbd_path," +
						"ci.cbd_date," +
						"ci.cdb_word_year_no as word," +
						"ci.cbd_leader,"+
						"ci.cbd_date1," +
						"ci.cbd_organ," +
						"ci.cbd_text," +
						"ci.cbd_personid," +
						"ci.cbd_personname," +
						"ci.cbd_userid," +
						"ci.cbd_username,"+
						"ci.status,"+
						"ci.objectno from cbd_info ci" +
						" where ci.cbd_userid = '"+user.getId()+"' and ci.cbd_path = '1' order by ci.cbd_date";
			}
			
		}else{
			if(cbdStatus.equals("0")||cbdStatus.equals("1")||cbdStatus.equals("2")){
				sql = "select ci.cbd_id," +
						"ci.cbd_name," +
						"ci.cbd_path," +
						"ci.cbd_date," +
						"ci.cdb_word_year_no as word," +
						"ci.cbd_leader,"+
						"ci.cbd_date1," +
						"ci.cbd_organ," +
						"ci.cbd_text," +
						"ci.cbd_personid," +
						"ci.cbd_personname," +
						"ci.cbd_userid," +
						"ci.cbd_username,"+
						"ci.status,"+
						"ci.objectno from cbd_info ci" +
						" where ci.cbd_userid = '"+user.getId()+"' and ci.cbd_path = '2' and ci.status = '"+cbdStatus+"' order by ci.cbd_date";
			}else{
				sql = "select ci.cbd_id," +
						"ci.cbd_name," +
						"ci.cbd_path," +
						"ci.cbd_date," +
						"ci.cdb_word_year_no as word," +
						"ci.cbd_leader,"+
						"ci.cbd_date1," +
						"ci.cbd_organ," +
						"ci.cbd_text," +
						"ci.cbd_personid," +
						"ci.cbd_personname," +
						"ci.cbd_userid," +
						"ci.cbd_username,"+
						"ci.status,"+
						"ci.objectno from cbd_info ci" +
						" where ci.cbd_userid = '"+user.getId()+"' and ci.cbd_path = '2' order by ci.cbd_date";
			}
			
		}
		this.pageQuery(sql,"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * �����ʱ�����ť
	 */
	@PageEvent("bjBtn.onclick")
	public int bjBtn(){
		this.request.getSession().setAttribute("cbdType", "1");
		this.setNextEventName("CBDGrid.dogridquery");
		return 0;
		
	}
	
	/**
	 * �ϱ��ʱ�����ť
	 */
	@PageEvent("sbBtn.onclick")
	public int sbBtn() throws RadowException{
		this.request.getSession().setAttribute("cbdType", "2");
		this.setNextEventName("CBDGrid.dogridquery");
		return 0;
		
	}
	
	/**
	 * ������
	 */
	@PageEvent("inProcess.onclick")
	public int inProcess() throws RadowException{
		this.request.getSession().setAttribute("cbdStatus", "0");
		this.setNextEventName("CBDGrid.dogridquery");
		return 0;
		
	}
	
	/**
	 * �����
	 */
	@PageEvent("allEnd.onclick")
	public int allEnd() throws RadowException{
		this.request.getSession().setAttribute("cbdStatus", "1");
		this.setNextEventName("CBDGrid.dogridquery");
		return 0;
		
	}
	
	/**
	 * �˻�
	 */
	@PageEvent("back.onclick")
	public int back() throws RadowException{
		this.request.getSession().setAttribute("cbdStatus", "2");
		this.setNextEventName("CBDGrid.dogridquery");
		return 0;
		
	}
	
	
	
	/**
	 * ¼��ʱ���ҳ��
	 * @return
	 */
	@PageEvent("addCBD.onclick")
	public int cbdAdd(){
		this.openWindow("editCBD", "pages.cbdHandler.AddCBDInfo");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �༭����
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("editFile")
	public int editFile(String value) throws RadowException{
		this.openWindow("editFileWindow", "pages.search.EditFile");
		this.setRadow_parent_data(value);//��ֵ���ݸ���ҳ��
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �޸ĳʱ�����Ϣ
	 * @param value
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("modifyCBD")
	public int modifyCBD(String value) throws RadowException{
		
		String[] values = value.split("@");
		if("0".equals(values[1])){
			
			this.setRadow_parent_data(values[0]+"@UU");
			this.openWindow("editCBD", "pages.cbdHandler.AddCBDInfo");
			this.getExecuteSG().addExecuteCode("onHide('editCBD',\"radow.doEvent('reload')\");");
		}else if("1".equals(values[1])){
			this.setRadow_parent_data(values[0]+"@U");
			this.openWindow("newUpCBD", "pages.cbdHandler.AddUPCBDInfo");
			this.getExecuteSG().addExecuteCode("onHide('editCBD',\"radow.doEvent('reload')\");");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * �鿴�ʱ�������
	 */
	@PageEvent("processSystem")
	public int processSystem(String value) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String cbd_id = value.split("@")[0];//��ȡǰ̨���ݵĳʱ���id
		String cbd_name = value.split("@")[1];//��ȡǰ̨���ݵĳʱ�������
		String cbd_path = value.split("@")[2];//��ȡǰ̨���ݵĳʱ�������
		String sql = "select cbd_personid from cbd_info where cbd_id = '"+cbd_id+"'";
		List personid = sess.createSQLQuery(sql).list();//��ѯ���ʱ�������Աid
		String personids = personid.get(0).toString();
		String value2 = cbd_name+"@"+personids+"@"+cbd_id+"@"+cbd_path;
		this.setRadow_parent_data(value2);//����Աid���ʱ������ƺͳʱ���id���ݵ��ʱ�������ҳ��
		this.openWindow("processSystemWindow", "pages.cbdHandler.processSystem");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ˢ��
	 */
	@PageEvent("reload")
	public int reload() throws RadowException {
	    this.setNextEventName("bjBtn.onclick");
	    this.request.getSession().setAttribute("cbdStatus", "3");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ˢ�°�ť
	 * @return
	 */
	@PageEvent("btnsx.onclick")
	public int Btnsx(){
		this.setNextEventName("bjBtn.onclick");
		this.request.getSession().setAttribute("cbdStatus", "3");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	

	@PageEvent("addUpCBD")
	public int addUpCBD(String value) throws RadowException{
		
		this.setRadow_parent_data(value+"@N");
		this.openWindow("newUpCBD", "pages.cbdHandler.AddUPCBDInfo");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ɾ��
	 */
	@PageEvent("deleteBtn")
	public int deleteBtn(String list) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String cbd_ids = list.replace("|", "'");
		String [] cbd_id = cbd_ids.split("@");
		for(int i=0;i<cbd_id.length;i++){
			try {
				HBUtil.executeUpdate("delete from cbd_info where cbd_id = "+cbd_id[i]+"");
				HBUtil.executeUpdate("delete from ATTACHMENT_INFO where OBJECTID = "+cbd_id[i]+"");
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("getCheckList")
	public int getCheckList() throws RadowException, AppException{
		String listString=null;
		int cnt=0;
		List<HashMap<String, Object>> gdlist=new ArrayList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("CBDGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			
			if(!"".equals(hm.get("checked"))&&(Boolean) hm.get("checked")){
				listString=listString+"@|"+hm.get("cbd_id")+"|";
				++cnt;
			}
		}
		if(!"".equals(listString)&&listString!=null)
		    listString=listString.substring(listString.indexOf("@")+1,listString.length());
 		this.getPageElement("checkList").setValue(listString);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 *  ��ɫ����ҳ���������޸�
	 * @return
	 * @throws RadowException
	 * @throws SQLException
	 */
	@PageEvent("CBDGrid.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int CBDGridAfterEdit() throws RadowException, SQLException {
		HBSession sess = HBUtil.getHBSession();
		//��ȡ��¼�û�����Ϣ
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();

		this.isShowMsg = false;
		//�Ƿ����ֶ�ֵ�ı��жϱ�־
		boolean isChange = false;
		//��ȡҳ������
		Grid CBDGrid = (Grid) this.getPageElement("CBDGrid");
		String cbd_id = (String)CBDGrid.getValue("cbd_id");
		String Status = (String)CBDGrid.getValue("status");
		String sql = "UPDATE cbd_info SET status = '"+Status+"' WHERE cbd_id = '"+cbd_id+"'";
		try {
			HBUtil.executeUpdate(sql);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String sql = "UPDATE cbd_info SET status = '"+Status+"' WHERE cbd_id = '"+cbd_id+"'";
		
		CBDGrid.reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �ʱ�����¼��˫���¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("CBDGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{  //�򿪴��ڵ�ʵ��
		String cbd_id = this.getPageElement("CBDGrid").getValue("cbd_id",this.getPageElement("CBDGrid").getCueRowIndex()).toString();
		String cbd_name = this.getPageElement("CBDGrid").getValue("cbd_name",this.getPageElement("CBDGrid").getCueRowIndex()).toString();
		String cbd_path = this.getPageElement("CBDGrid").getValue("cbd_path",this.getPageElement("CBDGrid").getCueRowIndex()).toString();
		this.getExecuteSG().addExecuteCode("processBtn1('"+cbd_id+"@"+cbd_name+"@"+cbd_path+"')");
		//this.setRadow_parent_data(cbd_id+"@"+cbd_name+"@"+cbd_path);
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * �����ʱ�������¼�
	 * @return
	 * @throws RadowException 
	 * @throws IOException 
	 */
	@PageEvent("btndown.onclick")
	@GridDataRange
	@Transaction
	public int expData() throws RadowException, IOException{
    	Map<String, Object> tmpData=new HashMap<String, Object>();
		List<String> personid = null;
		String id="";
		String cbd_id = "";
		HBSession sess = HBUtil.getHBSession();
		String sql = "";
		PageElement pe = this.getPageElement("CBDGrid");
		if(pe!=null){
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int j=0;j<list.size();j++){
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("checked");
				if(usercheck.equals(true)){
					cbd_id=this.getPageElement("CBDGrid").getValue("cbd_id",j).toString();
					personid = new ArrayList<String>();
					sql = "select CBD_PERSONID from cbd_info where CBD_ID= '"+cbd_id+"'";
					personid = sess.createSQLQuery(sql).list();//��ѯ���ʱ�������Աid
					id = id + personid.get(0).toString()+",";
				}
			}
			if("".equals(id)){
				this.setMainMessage("��ѡ��ʱ�����¼��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.getPageElement("a0000").setValue(id.substring(0,id.length()-1));
		}

		this.getExecuteSG().addExecuteCode("expCBD()");
		return EventRtnType.NORMAL_SUCCESS;
		
	}

}
