package com.insigma.siis.local.pagemodel.dataverify;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;

public class DataVerifyNewPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		HBSession sess =  HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs  = null;
		String param = this.getPageElement("subWinIdBussessId").getValue();
		String type = DBUtil.getDBType().toString();
		try {
			if(param==null || param.equals("")){
				return 0;
			}
			String param1[] = param.split("@");
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/" + param1[0] + "/";// �ϴ�·��
			String unzip = upload_file + "unzip/" + param1[0] + "/";// ��ѹ·��
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "/gwyinfo.xml");
			Map<String, String> map = headlist.get(0);
			//�жϰ汾��20171020
			String ftype = map.get("type");
			String dataverion = map.get("dataversion");
			/*if(("21".equals(ftype) || "22".equals(ftype)) && !"20171020".equals(dataverion)){ //21 hzb
				this.getExecuteSG().addExecuteCode("changeData();");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			
			String B0111 = map.get("B0111");// ���ڵ��ϼ�����id
			String org = map.get("B0101");// ���ڵ��ϼ�����id
			String B0114 = map.get("B0114");// ���ڵ��ϼ�����id
			String psncount = map.get("psncount");// ���ڵ��ϼ�����id
			String linkpsn = map.get("linkpsn");// ���ڵ��ϼ�����id
			String linktel = map.get("linktel");// ���ڵ��ϼ�����id
			map.put("b0131_combo", "");
			List<B01> b01s = null;
			b01s = HBUtil.getHBSession().createQuery(" from B01 t where t.b0101='" + org + "' and t.b0114='" + B0114 +"'").list();
			conn = sess.connection();
			stmt = conn.createStatement();
			String sql5 = "select count(b0111) from b01 where b0111 <> '-1'";
			rs = stmt.executeQuery(sql5);
			int size = 0;
			while(rs.next()){
				size = rs.getInt(1);
			}
			if(size == 0){
				this.getPageElement("xz").setValue("1");
				this.getPageElement("info").setValue("ϵͳΪ�տ⣬��ֱ�ӽ��е��룡");
				this.getPageElement("b0111new").setValue("001.001");
			} else if (b01s!=null && b01s.size()>0) {
				this.getPageElement("xz").setValue("1");
				String userID = SysManagerUtils.getUserId();
				Map<String, String> mapG = PublicWindowPageModel.isHasRule(b01s.get(0).getB0111(), userID);
				if (!mapG.isEmpty()) {
					if ("2".equals(mapG.get("type"))){
						this.getPageElement("info").setValue("��û�иû�����Ȩ��\nƥ�������" +org +"\n�������룺" + B0114);
						this.getPageElement("b0111new").setValue("");
						this.setMainMessage("��û�иû�����Ȩ��!");
					} else {
						this.getPageElement("info").setValue("�� �Զ�ƥ�������Ϣ\nƥ�������" +org +"\n�������룺" + B0114);
						this.getPageElement("b0111new").setValue(b01s.get(0).getB0111());
					}
				} else {
					this.getPageElement("b0111new").setValue("");
					this.setMainMessage("ϵͳ���������쳣����˶ԣ�");
				}
			} else {
				if (ftype!=null && ftype.equals("31")) {
					this.getPageElement("b0111new").setValue("");
					this.getPageElement("info").setValue("���ݰ�Ϊ����Ա�������ݰ�������а���Ա���빦�ܣ�");
				} else {
					this.getPageElement("xz").setValue("1");
					this.getPageElement("b0111new").setValue("");
					this.getPageElement("info").setValue("δƥ�䵽���������ֶ�ѡ����½���");
				}
			}
			sql5 = "select count(A0000) from a01";
			rs = stmt.executeQuery(sql5);
			int size2 = 0;
			while(rs.next()){
				size2 = rs.getInt(1);
			}
			this.getPageElement("b0101").setValue(org);
			this.getPageElement("b0114").setValue(B0114);
			this.getPageElement("linkpsn").setValue(linkpsn);
			this.getPageElement("linktel").setValue(linktel);
			this.getPageElement("uuid").setValue( param1[0]);
			this.getPageElement("filename").setValue( param1[1]);
			this.getPageElement("psncount").setValue(psncount);
			this.getPageElement("psnNum").setValue(psncount);
			this.getPageElement("count").setValue("" +size2);
			this.getPageElement("type11").setValue(type);
			this.getPageElement("ftype").setValue(ftype);
			this.getExecuteSG().addExecuteCode("document.getElementById('tree-div').disabled=true;");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("btnNew.onclick")
	@NoRequiredValidate
	public int btnNew() throws RadowException, AppException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("xz.onclick")
	@NoRequiredValidate
	public int xz() throws RadowException, AppException {
		String xz = this.getPageElement("xz").getValue();
		String org = this.getPageElement("b0101").getValue();// ���ڵ��ϼ�����id
		String B0114 = this.getPageElement("b0114").getValue();// ���ڵ��ϼ�����id
		HBSession sess =  HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs  = null;
		try {
			List<B01> b01s = null;
			b01s = HBUtil.getHBSession().createQuery(" from B01 t where t.b0101='" + org + "' and t.b0114='" + B0114 +"'").list();
			conn = sess.connection();
			stmt = conn.createStatement();
			String sql5 = "select count(b0111) from b01 where b0111 <> '-1'";
			rs = stmt.executeQuery(sql5);
			int size = 0;
			while(rs.next()){
				size = rs.getInt(1);
			}
			if(xz.equals("1")){
				if(size == 0){
					this.getPageElement("xz").setValue("1");
					this.getPageElement("info").setValue("ϵͳΪ�տ⣬��ֱ�ӽ��е��룡");
					this.getPageElement("b0111new").setValue("001.001");
				} else if (b01s!=null && b01s.size()>0) {
					this.getPageElement("xz").setValue("1");
					String userID = SysManagerUtils.getUserId();
					Map<String, String> map = PublicWindowPageModel.isHasRule(b01s.get(0).getB0111(), userID);
					if (!map.isEmpty()) {
						if ("2".equals(map.get("type"))){
							this.getPageElement("info").setValue("��û�иû�����Ȩ��\nƥ�������" +org +"\n�������룺" + B0114);
							this.getPageElement("b0111new").setValue("");
							this.setMainMessage("��û�иû�����Ȩ��!");
						} else {
							this.getPageElement("info").setValue("�� �Զ�ƥ�������Ϣ\nƥ�������" +org +"\n�������룺" + B0114);
							this.getPageElement("b0111new").setValue(b01s.get(0).getB0111());
						}
					} else {
						this.getPageElement("b0111new").setValue("");
						this.setMainMessage("ϵͳ���������쳣����˶ԣ�");
					}
					
				} else {
					this.getPageElement("xz").setValue("1");
					this.getPageElement("b0111new").setValue("");
					this.getPageElement("info").setValue("δƥ�䵽���������ֶ�ѡ����½���");
				}
				this.getExecuteSG().addExecuteCode("document.getElementById('tree-div').disabled=true;");
			} else {
				this.getPageElement("b0111new").setValue("");
				this.getPageElement("info").setValue("");
				this.getExecuteSG().addExecuteCode("document.getElementById('tree-div').disabled=false;");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int querybyid(String b0111) throws RadowException, AppException {
		String xz = this.getPageElement("xz").getValue();
		String org = this.getPageElement("b0101").getValue();// ���ڵ��ϼ�����id
		String B0114 = this.getPageElement("b0114").getValue();// ���ڵ��ϼ�����id
		HBSession sess =  HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs  = null;
		try {
			String userID = SysManagerUtils.getUserId();
			Map<String, String> map = PublicWindowPageModel.isHasRule(b0111, userID);
			
			List<B01> b01s = null;
			b01s = HBUtil.getHBSession().createQuery(" from B01 t where t.b0111='" + b0111 + "'").list();
			conn = sess.connection();
			stmt = conn.createStatement();
			if(xz.equals("1")){
				
			} else if(xz.equals("2")){
				if (!map.isEmpty()) {
					if ("2".equals(map.get("type"))){
						this.setMainMessage("��û�иû�����Ȩ��!");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if (b01s!=null && b01s.size()>0) {
					this.getPageElement("info").setValue("�ֶ�ƥ�������Ϣ"+(b01s.get(0).getB0101().equals(org) && 
							b01s.get(0).getB0114().equals(B0114)?"":"���������Ʊ��벻ƥ��")
							+"\nƥ�������" +b01s.get(0).getB0101() +"\n�������룺" + (b01s.get(0).getB0114()!=null ?b01s.get(0).getB0114() :""));
					this.getPageElement("b0111new").setValue(b01s.get(0).getB0111());
				} else {
					this.getPageElement("b0111new").setValue("");
					this.setMainMessage("ϵͳ���������쳣����˶ԣ�");
				}
			} else if(xz.equals("3")){
				if (b01s!=null && b01s.size()>0) {
					this.getPageElement("b0111new").setValue("");
					this.getPageElement("b0111p").setValue(b01s.get(0).getB0111());
				} else {
					this.setMainMessage("ϵͳ���������쳣����˶ԣ�");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("xjjg.onclick")
	@NoRequiredValidate
	public int createB01(String b0111) throws RadowException, AppException {
		String xz = this.getPageElement("xz").getValue();
		String org = this.getPageElement("b0101").getValue();// ���ڵ��ϼ�����id
		String B0114 = this.getPageElement("b0114").getValue();// ���ڵ��ϼ�����id
		String b0111p = this.getPageElement("b0111p").getValue();// ���ڵ��ϼ�����id
		try {
			if(b0111p==null || b0111p.equals("")){
				this.setMainMessage("�½�������Ҫѡ���Ӧ�ϼ�������");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.setRadow_parent_data("1," + b0111p + ",imp_create" );
			this.openWindow("addOrgImpWin", "pages.sysorg.org.CreateSysOrg");
			this.request.getSession().setAttribute("tag", "0");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
