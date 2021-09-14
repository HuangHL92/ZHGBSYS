package com.insigma.siis.local.pagemodel.meeting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.entity.Publish;
import com.insigma.siis.local.business.entity.PublishAtt;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;
    
public class PersonCopyPageModel extends PageModel{
	@Override
	public int doInit() throws RadowException {
		try {
			String userid = SysManagerUtils.getUserId();
			String sql = "select meetingid,meetingname from "
			+ "(select meetingid,meetingname,meetingtype,time,userid from meetingtheme a where userid='"+userid+"'"
			+ " union "
			+ "  select meetingid,meetingname,meetingtype,time,userid from meetingtheme a where "
			+ "		meetingid in (select b.meetingid from publish b,publishuser c where b.publishid=c.publishid and c.userid='"+userid+"' and c.ischange='1')"
			+ "	) t order by time desc";
			
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				String tp0116 = listCode.get(i).get("meetingid").toString();
				mapCode.put(tp0116, listCode.get(i).get("meetingname").toString());
			}
			((Combo)this.getPageElement("meetingname")).setValueListForSelect(mapCode);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ��");
		}
		return 0;
	}
	
	@PageEvent("gridcq.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		try {
			String titleid=this.getPageElement("titlename").getValue();
			String sql = "select a.a0000,a.a0101,a.a0104,a0107,a.a0192a,a.sh000,a.yy_flag from "
					+ "(select a.a0000,a.a0101,a.a0104,substr(a.a0107,1,4)||'.'||substr(a.a0107,5,2) a0107,a.a0192a,a.sh000,a.sh001,'2' yy_flag from hz_sh_a01 a where titleid='"+titleid+"' "
					+ "			union select a.a0000,a.a0101,a.a0104,substr(a.a0107,1,4)||'.'||substr(a.a0107,5,2) a0107,a.a0192a,a.sh000,b.sh001,'1' yy_flag from hz_sh_a01 a,personcite b where a.sh000=b.sh000 and b.titleid_new='"+titleid+"' "
					+ "		) a order by sh001,a0101";

//			String sql = "select a.a0000,a.a0101,a.a0104,substr(a.a0107,1,4)||'.'||substr(a.a0107,5,2) a0107,a.a0192a,a.sh000 from hz_sh_a01 a where  a.titleid='"+titleid+"'";

			this.pageQuery(sql, "SQL", start, 1000);

        	return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.SPE_SUCCESS;
		}
	}
	
	
	
	//���
	@PageEvent("rigthBtn.onclick")
	public int rigthBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("gridcq");
		List<HashMap<String, Object>> list = pe.getValueList();//��ѯ��Ա�б�
		List<HashMap<String, Object>> listSelect=this.getPageElement("selectName").getValueList();//ѡ����Ա�б�
		for (HashMap<String, Object> hm : list) {
			if(hm.get("personcheck")!=null&&!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				addlist.add(hm);
			}
		}
		
		
		
		//ȥ����ѯ�б��б���ӵ���Ա
		list.removeAll(addlist);
		String gridcqdata = JSONArray.fromObject(list).toString();
		
		List<HashMap<String, Object>> addListFinal=new LinkedList<HashMap<String,Object>>();
		one:for(HashMap<String, Object> hm:addlist){
			for(HashMap<String, Object> sel:listSelect){
				if(hm.get("a0000").equals(sel.get("a0000"))){
					continue one;
				}
			}
			addListFinal.add(hm);
		}
		
		
 		//��������ӵ��Ǽ���Ա�б�
		listSelect.addAll(addListFinal);
		String data= JSONArray.fromObject(listSelect).toString();
 		this.getPageElement("selectName").setValue(data);
 		pe.setValue(gridcqdata);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ȫ�����
	@PageEvent("rigthAllBtn.onclick")
	public int rigthAllBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("gridcq");
		List<HashMap<String, Object>> list = pe.getValueList();//��ѯ��Ա�б�
		List<HashMap<String, Object>> listSelect=this.getPageElement("selectName").getValueList();//ѡ����Ա�б�
		for (HashMap<String, Object> hm : list) {
			addlist.add(hm);
		}
		//ȥ����ѯ�б��б���ӵ���Ա
		list.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(list).toString());
		List<HashMap<String, Object>> addListFinal=new LinkedList<HashMap<String,Object>>();
		one:for(HashMap<String, Object> hm:addlist){
			for(HashMap<String, Object> sel:listSelect){
				if(hm.get("a0000").equals(sel.get("a0000"))){
					continue one;
				}
			}
			addListFinal.add(hm);
		}
 		//��������ӵ��Ǽ���Ա�б�
		listSelect.addAll(addListFinal);
		String data= JSONArray.fromObject(listSelect).toString();
 		this.getPageElement("selectName").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//�Ƴ�
	@PageEvent("liftBtn.onclick")
	public int liftBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//ѡ����Ա�б�
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		List<HashMap<String, Object>> list=this.getPageElement("gridcq").getValueList();//��ѯ��Ա�б�
		for (HashMap<String, Object> hm : listSelect) {
			if(hm.get("personcheck2")!=null&&!"".equals(hm.get("personcheck2"))&&(Boolean) hm.get("personcheck2")){
				addlist.add(hm);
			}
		}
		//ȥ����ѯ�б�ȥ��������
		listSelect.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(listSelect).toString());
 		//��������ӵ��Ǽ���Ա�б�
		list.addAll(addlist);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement("gridcq").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ȫ���Ƴ�
	@PageEvent("liftAllBtn.onclick")
	public int liftAllBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//ѡ����Ա�б�
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		List<HashMap<String, Object>> list=this.getPageElement("gridcq").getValueList();//��ѯ��Ա�б�
		for (HashMap<String, Object> hm : listSelect) {
			addlist.add(hm);
		}
		//ȥ����ѯ�б�ȥ��������
		listSelect.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(listSelect).toString());
 		//��������ӵ��Ǽ���Ա�б�
		list.addAll(addlist);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement("gridcq").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private static String getPath() {
        String upload_file = AppConfig.HZB_PATH + "/";
        try {
            File file = new File(upload_file);
            //����ļ��в������򴴽�
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //��ѹ·��
        return upload_file;
    }
	
	public final static String disk = getPath();
	
	/**
	 * ����
	 */         
	@PageEvent("btnSave.onclick")
	@Transaction
	public int save() throws RadowException {
		String publishid=this.getPageElement("publishid").getValue();
		String titleid=this.getPageElement("titleid").getValue();
		String agendaname=this.getPageElement("agendaname").getValue();
		String titlename=this.getPageElement("titlename").getValue();
		
		List<String> addlist=new LinkedList<String>();
		PageElement pe = this.getPageElement(change("selectName"));//ѡ����Ա�б�
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		
		if(agendaname==null||"".equals(agendaname)) {
			this.setMainMessage("�����Ƶ����ⲻ��Ϊ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(publishid.equals(agendaname)) {
			this.setMainMessage("�����Ƶ������븴�Ƶ������ⲻ����ͬ");
			return EventRtnType.NORMAL_SUCCESS;
		} 
		
		if(titlename==null||"".equals(titlename)) {
			this.setMainMessage("�����Ƶı��ⲻ��Ϊ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(listSelect.size()<1){
			this.setMainMessage("��ѡ����Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		HBSession sess = HBUtil.getHBSession();
		try {
			Statement stmt = sess.connection().createStatement();
			String sql="";
			MeetingByNamePageModel mb=new MeetingByNamePageModel();
			int i=Integer.valueOf(mb.getMax_sort(titleid,sess))+1;
			for(HashMap<String, Object> map_p1 : listSelect) {
				String uuid_p1=UUID.randomUUID().toString().replaceAll("-", "");
				sql="insert into hz_sh_a01 (sh000,a0000,publishid,sh001,titleid,tp0111,tp0112,tp0113,tp0114,tp0115,tp0116,tp0117,tp0118,tp0119,tp0121,tp0122,tp0123,tp0124,tp0125,a0101,a0104,a0107,a0141,zgxl,zgxw,a0192a) "
						+ " select '"+uuid_p1+"',a0000,'"+publishid+"','"+i+"','"+titleid+"',tp0111,tp0112,tp0113,tp0114,'"+isnull(map_p1.get("tp0115")).replace(agendaname, publishid)+"',tp0116,tp0117,tp0118,tp0119,tp0121,tp0122,tp0123,tp0124,tp0125,a0101,a0104,a0107,a0141,zgxl,zgxw,a0192a from hz_sh_a01 where sh000='"+map_p1.get("sh000")+"'";
				stmt.executeUpdate(sql);
				i++;
				if(map_p1.get("tp0118")!=null&&!"".equals(map_p1.get("tp0118"))) {
					String oldPath=disk +map_p1.get("tp0115").toString()+map_p1.get("tp0118").toString();
					String newPath=disk +isnull(map_p1.get("tp0115")).replace(agendaname, publishid)+map_p1.get("tp0118").toString();
					File f = new File(disk +isnull(map_p1.get("tp0115")).replace(agendaname, publishid));
					if(!f.isDirectory()){
						f.mkdirs();
					}
					int bytesum = 0; 
		            int byteread = 0; 
		            File oldfile = new File(oldPath); 
		            if (oldfile.exists()) { //�ļ�����ʱ 
		               InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ� 
		               FileOutputStream fs = new FileOutputStream(newPath); 
		               byte[] buffer = new byte[1024*2]; 
		               int length; 
		               while ( (byteread = inStream.read(buffer)) != -1) { 
		                   bytesum += byteread; //�ֽ��� �ļ���С 
		                   System.out.println(bytesum); 
		                   fs.write(buffer, 0, byteread); 
		               } 
		               inStream.close(); 
		            } 
			     } 
			}
			stmt.close();
			
			String xtitlename = this.getPageElement("xtitlename").getValue();
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			new LogUtil(user).createLogNew(user.getId(),"������Ա","hz_sh_a01",user.getId(),xtitlename, new ArrayList());
			
			this.getExecuteSG().addExecuteCode("saveCallBack();");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private String change(String name) throws RadowException {
		if ("1".equals(this.getPageElement("selectType").getValue())) {
			name=name+"2";
		}
		return name;
	}
	//�������б�
	@PageEvent("clearRst")
	public int clearRst() throws RadowException, AppException{
		this.getPageElement("gridcq").setValue("[]");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ������������ѡ
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("updatepublish")
	@Transaction
	public int updatepublish() throws RadowException {
		String meetingname = this.getPageElement("meetingname").getValue();
		String userid = SysManagerUtils.getUserId();
		CommQuery cqbs=new CommQuery();
		String sql="select *  from publish where meetingid='"+meetingname+"' and (publishid in (select publishid from publishuser where ischange='1' and userid='"+userid+"') or userid='"+userid+"') order by to_number(sort) ";
		List<HashMap<String, Object>> listCode;
		try {
			listCode = cqbs.getListBySQL(sql);
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				String tp0116 = listCode.get(i).get("publishid").toString();
				mapCode.put(tp0116, listCode.get(i).get("agendaname").toString());
			}
			((Combo)this.getPageElement("agendaname")).setValueListForSelect(mapCode);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ���±�������ѡ
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("updatetitle")
	@Transaction
	public int updatetitle() throws RadowException {
		String agendaname = this.getPageElement("agendaname").getValue();
		CommQuery cqbs=new CommQuery();
		String sql="select * from hz_sh_title where publishid='"+agendaname+"' order by title02,to_number(sortid) ";
		List<HashMap<String, Object>> listCode;
		try {
			listCode = cqbs.getListBySQL(sql);
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				String tp0116 = listCode.get(i).get("titleid").toString();
				mapCode.put(tp0116, listCode.get(i).get("title01").toString());
			}
			((Combo)this.getPageElement("titlename")).setValueListForSelect(mapCode);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public String isnull (Object obj) {
		String str="";
		if(obj==null||"".equals(obj)) {
			
		}else {
			str=obj.toString();
		}
		return str;	
	}
	
}
