package com.insigma.siis.local.pagemodel.fxyp;

import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.cadremgn.sysbuilder.CreateDefinePageModel;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONObject;
    
public class SKBSBMPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String SKBSid = this.getPageElement("SKBSid").getValue();
		String SKBSname="";
		try {
			if(SKBSid!=null&&!"".equals(SKBSid)){
				if("1".equals(SKBSid)) {
					SKBSname="��ֱ����ȱ��Ҫ�쵼����Ҫ�쵼�����ڵ��䡾��ɫ��";
				}else if("2".equals(SKBSid)) {
					SKBSname="����3����ȿ���ĩλ��������2λ����ֱ10λ������ɫ��";
				}else if("3".equals(SKBSid)) {
					SKBSname="���ӳ�Ա������ر���ְ��������ɫ��";
				}else if("4".equals(SKBSid)) {
					SKBSname="����Ӳ��Ž�����Ӱ����ְ����ɫ��";
				}else if("5".equals(SKBSid)) {
					SKBSname="����ɲ�����ƫ�ͣ�����15%������ɫ��";
				}else if("6".equals(SKBSid)) {
					SKBSname="�����ڰ��ӳ�Ա����1/3���ϻ�3�����ϡ���ɫ��";
				}else if("7".equals(SKBSid)) {
					SKBSname="������Ů�ɲ�������ɲ��䱸��������꡾��ɫ��";
				}else if("8".equals(SKBSid)) {
					SKBSname="������ȿ���ĩλ��������2λ����ֱ10λ������ɫ��";
				}else if("9".equals(SKBSid)) {
					SKBSname="ר������������ص��ע���ص����ĵȸ�����������ɫ��";
				}else if("10".equals(SKBSid)) {
					SKBSname="��������������ӳ�Ա�ܵ������񴦷֡���ɫ��";
				}else if("11".equals(SKBSid)) {
					SKBSname="���ӳ�Ա�����轻����ְ��������ɫ��";
				}
//				HBSession sess = HBUtil.getHBSession();
//				MeetingTheme mt = (MeetingTheme)sess.get(MeetingTheme.class, meetingid);
//				PMPropertyCopyUtil.copyObjValueToElement(mt, this);
			}
			this.getExecuteSG().addExecuteCode("document.getElementById(\"SKBSname\").innerHTML='��ʶ���ƣ�"+SKBSname+"'");
			this.getExecuteSG().addExecuteCode("updateTree();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ��");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ����
	 */         
	@PageEvent("save")
	@Transaction
	public int save(String json) throws RadowException {
		String SKBSid = this.getPageElement("SKBSid").getValue();
		try{
			JSONObject o=JSONObject.fromObject(json);
			Iterator<?> i=o.keys();
			CreateDefinePageModel cdpm=new CreateDefinePageModel();
			String key="";
			String value="";
			String sql="";
			String table_code="";
			HBSession hbsess = HBUtil.getHBSession();
			Statement  stmt = hbsess.connection().createStatement();
			sql="delete from SKBS where SKBSid='"+SKBSid+"'";
			stmt.executeQuery(sql);
			while(i.hasNext()){
				key=i.next().toString();
				value=o.getString(key);
			    table_code=key.substring(0,key.length()-value.length());
				if("-1".equals(table_code)){
					continue;
				}else{
					sql="insert into SKBS (SKBSid,b01id) values ('"+SKBSid+"','"+value+"')";
					stmt.executeQuery(sql);
				}
			}
			stmt.close();
			this.getExecuteSG().addExecuteCode("alert('����ɹ�');window.close();");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return 1;
	}
	
	@PageEvent("definedList")
	public int definedList() throws Exception {
		String SKBSid = this.getParameter("SKBSid");
		System.out.print(SKBSid);
		StringBuffer jsonStr = new StringBuffer();
		CommQuery cqbs=new CommQuery();
		String userid = SysManagerUtils.getUserId();
		try {
			String sql_root="select 1 from SKBS where SKBSid='"+SKBSid+"' ";
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql_root);
			
			String sql1="select b01id,b0101,(select count(*) from SKBS b where a.b01id=b.b01id and SKBSid='"+SKBSid+"' ) num from b01 a where b0111 like '001.001.001%' and length(b0111)=15 order by b0111";
			String sql2="select b01id,b0101,(select count(*) from SKBS b where a.b01id=b.b01id and SKBSid='"+SKBSid+"' ) num from b01 a where b0111 like '001.001.002%' and length(b0111)=15 order by b0111";
			String sql3="select b01id,b0101,(select count(*) from SKBS b where a.b01id=b.b01id and SKBSid='"+SKBSid+"' ) num from b01 a where b0111 like '001.001.003%' and length(b0111)=15 order by b0111";
			String sql4="select b01id,b0101,(select count(*) from SKBS b where a.b01id=b.b01id and SKBSid='"+SKBSid+"' ) num from b01 a where b0111 like '001.001.004%' and length(b0111)=15 order by b0111";
			List<HashMap<String, Object>> listuser1;
			List<HashMap<String, Object>> listuser2;
			List<HashMap<String, Object>> listuser3;
			List<HashMap<String, Object>> listuser4;
			listuser1 = cqbs.getListBySQL(sql1);
			listuser2 = cqbs.getListBySQL(sql2);
			listuser3 = cqbs.getListBySQL(sql3);
			listuser4 = cqbs.getListBySQL(sql4);
			
			
			//
			if (listuser1 != null && listuser1.size() > 0) {
				
				boolean flag=false;
				if(list!=null&&list.size()>0&&list.size()==listuser1.size()) {
					flag=true;
				}
				jsonStr.append("[");
				jsonStr.append("{\"text\" :\"��ί�����˴�������������Э\" ,\"id\" :\"6CC928C19F344D2B95AC264C9D545298\" ,\"cls\" :\"folder\" ,\"checked\" :"+flag+"");
				jsonStr.append(",\"children\" :[");
				
				for (int j = 0; j < listuser1.size(); j++) {
					String b01id =listuser1.get(j).get("b01id").toString();// �û���ID
					String b0101 = listuser1.get(j).get("b0101").toString();// �û�������
					String num = listuser1.get(j).get("num").toString();// �Ƿ��Ѷ�ʱ��1Ϊ�ǣ�0λ��
					boolean str=false;
					if("1".equals(num)){
						str= true;
					}
					
					jsonStr.append("{\"text\" :\""
							+ b0101
							+ "\" ,\"id\" :\""
							+ b01id
							+ "\" ,\"checked\" :"
							+ str
							+ " ,\"leaf\" :\"true\" ,\"cls\" :\"folder\"  }");
	
					if (j != listuser1.size() - 1) {
						jsonStr.append(",");
					}
	
				}
				jsonStr.append("]},");
			}
			if (listuser2 != null && listuser2.size() > 0) {
				
				boolean flag=false;
				if(list!=null&&list.size()>0&&list.size()==listuser2.size()) {
					flag=true;
				}
				jsonStr.append("{\"text\" :\"��ֱ��λ\" ,\"id\" :\"68EBAC3D5C24449AAE6CAEA289A331F4\" ,\"cls\" :\"folder\" ,\"checked\" :"+flag+"");
				jsonStr.append(",\"children\" :[");
				
				for (int j = 0; j < listuser2.size(); j++) {
					String b01id =listuser2.get(j).get("b01id").toString();// �û���ID
					String b0101 = listuser2.get(j).get("b0101").toString();// �û�������
					String num = listuser2.get(j).get("num").toString();// �Ƿ��Ѷ�ʱ��1Ϊ�ǣ�0λ��
					boolean str=false;
					if("1".equals(num)){
						str= true;
					}
					
					jsonStr.append("{\"text\" :\""
							+ b0101
							+ "\" ,\"id\" :\""
							+ b01id
							+ "\" ,\"checked\" :"
							+ str
							+ " ,\"leaf\" :\"true\" ,\"cls\" :\"folder\"  }");
	
					if (j != listuser2.size() - 1) {
						jsonStr.append(",");
					}
	
				}
				jsonStr.append("]},");
			}
			
			if (listuser3 != null && listuser3.size() > 0) {
				
				boolean flag=false;
				if(list!=null&&list.size()>0&&list.size()==listuser3.size()) {
					flag=true;
				}
				jsonStr.append("{\"text\" :\"�����У\" ,\"id\" :\"11D6D8E4C53E4AAEA68B95CA5417FE53\" ,\"cls\" :\"folder\" ,\"checked\" :"+flag+"");
				jsonStr.append(",\"children\" :[");
				
				for (int j = 0; j < listuser3.size(); j++) {
					String b01id =listuser3.get(j).get("b01id").toString();// �û���ID
					String b0101 = listuser3.get(j).get("b0101").toString();// �û�������
					String num = listuser3.get(j).get("num").toString();// �Ƿ��Ѷ�ʱ��1Ϊ�ǣ�0λ��
					boolean str=false;
					if("1".equals(num)){
						str= true;
					}
					
					jsonStr.append("{\"text\" :\""
							+ b0101
							+ "\" ,\"id\" :\""
							+ b01id
							+ "\" ,\"checked\" :"
							+ str
							+ " ,\"leaf\" :\"true\" ,\"cls\" :\"folder\"  }");
	
					if (j != listuser3.size() - 1) {
						jsonStr.append(",");
					}
	
				}
				jsonStr.append("]},");
			}
			
			if (listuser4 != null && listuser4.size() > 0) {
				
				boolean flag=false;
				if(list!=null&&list.size()>0&&list.size()==listuser4.size()) {
					flag=true;
				}
				jsonStr.append("{\"text\" :\"������\" ,\"id\" :\"7961321DB1D84E80BBAD5CC723B75A4D\" ,\"cls\" :\"folder\" ,\"checked\" :"+flag+"");
				jsonStr.append(",\"children\" :[");
				
				for (int j = 0; j < listuser4.size(); j++) {
					String b01id =listuser4.get(j).get("b01id").toString();// �û���ID
					String b0101 = listuser4.get(j).get("b0101").toString();// �û�������
					String num = listuser4.get(j).get("num").toString();// �Ƿ��Ѷ�ʱ��1Ϊ�ǣ�0λ��
					boolean str=false;
					if("1".equals(num)){
						str= true;
					}
					
					jsonStr.append("{\"text\" :\""
							+ b0101
							+ "\" ,\"id\" :\""
							+ b01id
							+ "\" ,\"checked\" :"
							+ str
							+ " ,\"leaf\" :\"true\" ,\"cls\" :\"folder\"  }");
	
					if (j != listuser4.size() - 1) {
						jsonStr.append(",");
					}
	
				}
				jsonStr.append("]}");
			}
			jsonStr.append("]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
}
