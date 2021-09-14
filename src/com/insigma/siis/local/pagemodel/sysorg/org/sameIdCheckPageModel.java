package com.insigma.siis.local.pagemodel.sysorg.org;

import java.beans.IntrospectionException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class sameIdCheckPageModel extends PageModel {
	
	public static void main(String[] args) {
		CommonQueryBS.systemOut(20/69+"");
	}
	public static String jiaoyan = "0";//0�Ǵ� 1�ǵ��
	
	@Override
	public int doInit() {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		return 0;
	}
	
	@PageEvent("query.onclick")
	@NoRequiredValidate
	public int query()throws RadowException, AppException{
		this.setNextEventName("repeatInfogrid.dogridquery");
		return 0;
	}

	@PageEvent("repeatInfogrid.rowdbclick")
	@GridDataRange
	public int persongrid1OnRowDbClick() throws RadowException, AppException{  //�򿪴��ڵ�ʵ��
		String a0000=this.getPageElement("repeatInfogrid").getValue("a0000",this.getPageElement("repeatInfogrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','��Ա��Ϣ�޸�',1009,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'repeatInfogrid',maximizable:false,resizable:false,draggable:false},true);");*/
			this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("����Ա����ϵͳ�У�");
		}
	}
	
	@PageEvent("repeatInfogrid.dogridquery")
	@NoRequiredValidate
	public int repeatInfogridQuery(int start,int limit)throws RadowException, AppException{
		String name = this.getPageElement("a0101").getValue();
		String id = this.getPageElement("a0184").getValue();
		String sql2="";
		String where1 = "";
	    String condition = " a0184 ";
	    String where= "  where a.a0195 = v.b0111 and a.A0184 = b.a0184 ";
	    condition = " a0184 ";
	    String	orderby = " a.a0184 ";
		if(name != null && !"".equals(name.trim())){
			condition += ",a0101 ";
    		orderby +=",a.a0101";
    		where += " and a.a0101 = b.a0101 ";
    		where1 += " and a0101 = '"+name+"' ";
		}
		String sql_com = "select a.a0000, a.a0101, a.a0104, a.a0184, a.a0117, v.b0101 a0195, a.a0192a from A01 a,b01 v,";
		if(id != null && !"".equals(id.trim())){
			sql2 = sql_com+"(select "+ condition +" from (select "+ condition +" from a01 group by "+ condition +" having count(1)>= 2 "+where1+" and a0184='"+id+"') t ) b" + where +" order by " + orderby;
		}else{
			sql2 = sql_com+"(select "+ condition +" from (select "+ condition +" from a01 group by "+ condition +" having count(1)>= 2 "+where1+" ) t ) b" + where +" order by " + orderby;
		}
		this.request.getSession().setAttribute("peopleQueryRepeat", sql2);
		this.pageQuery(sql2, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	private void dialog_set(String fnDelte, String strHint,String a0000){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // �������Ϣ�����ȷ��ʱ�������´��¼�
		ne.setNextEventName(fnDelte);
		ne.setNextEventParameter(a0000);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// �������Ϣ�����ȡ��ʱ�������´��¼�
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage(strHint); // ������ʾ��Ϣ
	}
	
	@PageEvent("deleteEvent")
	@NoRequiredValidate
	public int deleteEvent(String a0000)throws RadowException, AppException{
		//�ж��Ƿ����޸�Ȩ�ޡ�
		if(!verifyAuth(a0000)){
			this.setMainMessage("��û�в�������Ա��Ȩ�ޣ�");
			return EventRtnType.FAILD;
		}
		dialog_set("deleteconfirm1","��ȷ����������ѡ��Ĳ�����",a0000.toString().replace("'", "^"));
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("deleteconfirm1")
	public int deleteconfirm1(String a0000)throws AppException, RadowException{
		dialog_set("deleteconfirm","���ٴ�ȷ�����Ĳ�����",a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//ɾ���¼�
	@PageEvent("deleteconfirm")
	@Transaction
	public int deleteconfirm(String peopleId)throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException{
		//String radio = this.getPageElement("radio").getValue();
		//��ȡ����ѡ����Ա���
		String sa0000 = "";
		String a0000s = peopleId.replace("^", "'");
		HBSession sess = HBUtil.getHBSession();
		String[] values = a0000s.split("@");
		if (values.length > 1) {
			StringBuffer sb = new StringBuffer();
			String sql = values[0];
			if(!sql.contains("*")){
				sql="select * from ("+sql+")";
			}
			String newsql = sql.replace("*", "a0000");
			List allSelect = sess.createSQLQuery(newsql).list();
			if (allSelect.size() > 0) {
				for (int i = 0; i < allSelect.size(); i++) {
					//�ж��Ƿ���ɾ��Ȩ�ޡ�c.type������Ȩ������(0�������1��ά��)
					String a0000 = allSelect.get(i).toString();
					A01 a01 = (A01)sess.get(A01.class, a0000);
					String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
							" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
							" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
					String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
					List elist = sess.createSQLQuery(editableSQL).list();
					List elist2 = sess.createSQLQuery(editableSQL2).list();
		/*			�жϸ���Ա�Ĺ���������Ȩ��------------------------------------------------------------------------------------------------------*/
					String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
					if(type == null || !type.contains("'")){
						type ="'zz'";//�滻��������
					}
					List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
					if(elist3.size()>0){//�޹������ά��Ȩ��,����Ա��Ϣ���ɱ༭
						continue;
					}
					if(elist2==null||elist2.size()==0){//ά��Ȩ��
						if(elist!=null&&elist.size()>0){//�����Ȩ��
							continue;
						}else{
							//���������������ְ��Ա��������ְ��Ա������ְ��Աֻ�ܲ鿴�����ܱ༭��������ְ��Ա�ɲ鿴���ɱ༭
							if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//����ְ��Ա
								continue;
							}else {
								sb.append("'").append(a0000).append("',");
							}
							
						}
					}else {
						sb.append("'").append(a0000).append("',");
					}
				}
				if (sb.length() == 0) {
					throw new AppException("��ѡ��Ա���ɲ�����");
				}
				sa0000 = sb.substring(0, sb.length() - 1);
			} else {
				throw new AppException("����ѡ��Ҫɾ������Ա��");
			}
		}else{
			String[] s =  a0000s.split(",");
			for(String str : s){
				str = "'"+str+"'";
				A01 a01 = (A01)sess.get(A01.class, str.replace("'", ""));
				String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
						" b.a0201b=c.b0111 and a.a0000="+str+" and c.userid='"+SysManagerUtils.getUserId()+"' " +
						" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
				String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
				" b.a0201b=c.b0111 and a.a0000="+str+" and c.userid='"+SysManagerUtils.getUserId()+"' " +
				" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
				List elist = sess.createSQLQuery(editableSQL).list();
				List elist2 = sess.createSQLQuery(editableSQL2).list();
	/*			�жϸ���Ա�Ĺ���������Ȩ��------------------------------------------------------------------------------------------------------*/
				String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
				if(type == null || !type.contains("'")){
					type ="'zz'";//�滻��������
				}
				List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000="+str+" and a01.a0165 in ("+type+")").list();
				if(elist3.size()>0){//�޹������ά��Ȩ��,����Ա��Ϣ���ɱ༭
					
				}
				if(elist2==null||elist2.size()==0){//ά��Ȩ��
					if(elist!=null&&elist.size()>0){//�����Ȩ��
						
					}else{
						//���������������ְ��Ա��������ְ��Ա������ְ��Աֻ�ܲ鿴�����ܱ༭��������ְ��Ա�ɲ鿴���ɱ༭
						if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//����ְ��Ա
							
						}else {
							sa0000 = sa0000 + str + ","; 
						}
						
					}
				}else {
					sa0000 = sa0000 + str + ","; 
				}
			}
			if(sa0000.length() > 0){
				sa0000 = sa0000.substring(0, sa0000.length() - 1);
			}
		}
		//�жϣ������ֵ��ѡ��
		if(!"".equals(sa0000) && sa0000 != null){

				//a01.setStatus("0");//�޸���ȫɾ��
				//sess.saveOrUpdate(a01);
				deletePerson(sa0000);
				this.setMainMessage("ɾ���ɹ���");
				 this.getExecuteSG().addExecuteCode("reloadGird()");
				//sess.flush();
				//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('delete');");
				
			
		}else{
			throw new AppException("��ѡ��Ա���ɲ�����");
		}
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	private void deletePerson(String a0000) throws AppException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		List<A01> a01_list = (List<A01>) HBUtil.getHBSession().createQuery("from A01 where a0000 in("+a0000+")").list();
		
		List<A57> a57_list = (List<A57>) HBUtil.getHBSession().createQuery("from A57 where a0000 in("+a0000+")").list();
		for(A57 a57 : a57_list){
			if(a57!=null&&a57.getPhotopath()!=null&&!"".equals(a57.getPhotopath())){
				String photourl = a57.getPhotopath();
				File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
				if(fileF.isFile()){
					fileF.delete();
				}
				
			}
		}
		Statement stmt=null;
		Connection conn = HBUtil.getHBSession().connection();
		try{
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			//String a0000 = a01.getA0000();
			stmt.executeUpdate("delete from a02 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a05 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a06 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a08 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a14 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a15 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a36 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a11 where a1100 in(select a1100 from a41 where a0000 in ("+a0000+"))");
			stmt.executeUpdate("delete from a41 where a0000 in ("+a0000+")");
			
			stmt.executeUpdate("delete from a29 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a53 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a37 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a31 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a30 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a57 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a60 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a61 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a62 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a63 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a64 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a01 where a0000 in("+a0000+")");
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			throw new AppException("���ݿ⴦���쳣��",e);
		}finally{
			if (stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new AppException("���ݿ⴦���쳣��", e);
				}
			}
				
		}
		
		for(int i=0;i<a01_list.size();i++){
			
			new LogUtil("33", "A01", a01_list.get(i).getA0000(), a01_list.get(i).getA0101(), "ɾ����¼", new ArrayList()).start();
		}
		
	}

	
	/**
	 * ��Ա����Ȩ�޿���
	 * ����false ��Ȩ��  ����true ��Ȩ��
	 * @param id
	 * @return
	 */
	public static boolean verifyAuth(String id){
		try {
			HBSession sess = HBUtil.getHBSession();
			/*String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000='"+id+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";*/
			String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
			" b.a0201b=c.b0111 and a.a0000='"+id+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
			" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
			//List elist = sess.createSQLQuery(editableSQL).list();
			List elist2 = sess.createSQLQuery(editableSQL2).list();
			if(elist2==null||elist2.size()==0){	
				return false;
			}
		} catch (Exception e) {
			
		}
		return true;
	
	}
}	

