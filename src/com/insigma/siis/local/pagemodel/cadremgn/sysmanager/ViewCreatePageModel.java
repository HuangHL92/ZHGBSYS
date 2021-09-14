package com.insigma.siis.local.pagemodel.cadremgn.sysmanager;


import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Qryview;

public class ViewCreatePageModel extends PageModel {
	
	
	@Override
	public int doInit() throws RadowException {
		//System.setProperty("sun.net.client.defaultConnectTimeout", "120000");
		//System.setProperty("sun.net.client.defaultReadTimeout", "120000");
		//this.setNextEventName("initX");
		this.setNextEventName("viewListGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("viewListGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException, AppException{
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username =SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		/* ע��Ȩ�޲��� - �ൺȨ��
		 * if("system".equals(username)){
			instr="";
		}else{
			instr="  and ( userid='"+userid+"'  or qvid in (select t.viewid from COMPETENCE_USERQRYVIEW t where t.userid = '"+userid+"' and t.type = '1' ) ) ";
		}*/
		String sql="select"
				+ "  qvid, "       //����
				+ " type, "        //��ͼ����1 �Զ�����ͼ2 �����ͼ 3������ͼ
				+ " viewname, "    //��ͼ����
				+ " chinesename, " //������
				+ " f.uses, "         //��;
				+ " funcdesc"    //��������
		+ " from qryview  f"
		+ " where  1=1 "
		+ instr
		+ " and type='1' ";
		this.pageQuery(sql, "SQL", start,limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * ��ѯ��ͼ�����б�
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("initX")
	@NoRequiredValidate
	public void initX() throws RadowException, AppException{
//		CommQuery cq=new CommQuery();
//		List<HashMap<String, Object>> list=cq.getListBySQL("select"
//						+ "  qvid, "       //����
//						+ " type, "        //��ͼ����1 �Զ�����ͼ2 �����ͼ 3������ͼ
//						+ " viewname, "    //��ͼ����
//						+ " chinesename, " //������
//						+ " uses, "         //��;
//						+ " funcdesc "    //��������
//				+ " from qryview ");
//    	this.getPageElement("viewListGrid").setValueList(list);
//    	
//    	List<HashMap<String,Object>> arr2=new ArrayList<HashMap<String,Object>>();
//		HashMap<String,Object> map2=new HashMap<String,Object>();
//    	map2.put("code_name", "����");
//    	arr2.add(map2);
//    	HashMap<String,Object> map3=new HashMap<String,Object>();
//    	map3.put("code_name", "�Ա�");
//    	arr2.add(map3);
//    	this.getPageElement("codeListGrid").setValueList(arr2);
	}
	
	/**
	 * ��ͼ���沢����
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("saveandset")
	public int saveandset() throws RadowException{
		try{
			HBSession session = HBUtil.getHBSession();
			//��ͼ��
			String viewname=this.getPageElement("viewname").getValue();
			
			if(viewname==null||viewname.length()==0){
				this.setMainMessage("��������ͼ��!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(!viewname.substring(0, 1).matches("^[a-zA-Z]$")){
				this.setMainMessage("��ͼ����Ҫ����ĸ��ͷ!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//������
			String chinesename=this.getPageElement("chinesename").getValue();
//			if(chinesename==null||chinesename.trim().length()==0||chinesename.equals("null")){
//				this.setMainMessage("��������ͼ������!");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			String userid=PrivilegeManager.getInstance().getCueLoginUser().getId();
			
			//��;
			String use=this.getPageElement("use").getValue();
			//��������
			String funcdesc=this.getPageElement("funcdesc").getValue();
			String qvid=this.getPageElement("qvid").getValue();//����
			if(qvid!=null&&qvid.length()>0){//����
				
				Qryview qryview=(Qryview)session.get(Qryview.class, qvid);
				//��ͼ��
				qryview.setViewname(viewname);
				//������
				qryview.setChinesename(chinesename);
				//��;
				qryview.setUses(use);
				//��������
				qryview.setFuncdesc(funcdesc);
				session.update(qryview);
				session.flush();
				//�����������tabҳ���ȫ��js����
				this.getExecuteSG().addExecuteCode("clearParameter();");
			}else{//����
				String num=HBUtil.getValueFromTab("count(*)", "qryview", "userid='"+userid+"' and viewname='"+viewname.trim()+"' ");
				if(Integer.parseInt(num)>0){
					this.setMainMessage("��ͼ���Ѵ��ڣ�����������!");
					return EventRtnType.NORMAL_SUCCESS;
				}
				Qryview qryview=new Qryview();
				//��ͼ��
				qryview.setViewname(viewname);
				//������
				qryview.setChinesename(chinesename);
				//��;
				qryview.setUses(use);
				//��������
				qryview.setFuncdesc(funcdesc);
				//����ʱ��
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
				Date date=new Date();
				String createtime=sdf.format(date);
				qryview.setCreatetime(createtime);
				//��ͼ����1 �Զ�����ͼ2 �����ͼ 3������ͼ
				qryview.setType("1");
				//�û�id
				qryview.setUserid(userid);
				session.save(qryview);
				qvid=qryview.getQvid();
				
				//������ͼ����Ϊ��ǰ�����û�����Ȩ��
				/* ע��Ȩ�޲��� - �ൺȨ��
				 * session.createSQLQuery("insert into COMPETENCE_USERQRYVIEW values("
						+ "'"+UUID.randomUUID().toString().replaceAll("-", "")+"',"
						+ "'"+userid+"','"+qvid+"','1')").executeUpdate();*/
				
				session.flush();
				this.getPageElement("qvid").setValue(qryview.getQvid());
				//�����������tabҳ���ȫ��js����
				this.getExecuteSG().addExecuteCode("clearParameter();");
			}
			//ˢ����ͼ�б�
			this.getExecuteSG().addExecuteCode("refreshList();");
			//��ת����������
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab2');");
			//������ͼ������iframeҳ��
			this.getExecuteSG().addExecuteCode("setQvidToIframe('iframeCondition','"+qvid+"');");
			//������Ϣ���б�
			this.getExecuteSG().addExecuteCode("setTab2();");
			//����Ԥ������
//			this.getExecuteSG().addExecuteCode("var divUpdivUp=document.frames['iframePreview'].document.getElementById('divUp');if(divUpdivUp==null){}else{ .style.display='block'};"
//					+ "document.frames['iframePreview'].document.getElementById('divDown').style.display='none';");
			this.getExecuteSG().addExecuteCode("var divUpdivUp2=document.frames['iframePreview'].document.getElementById('divUp');if(divUpdivUp2==null){}else{divUpdivUp2.style.display='block'};"
					+ "var divDowndivDown2=document.frames['iframePreview'].document.getElementById('divDown');if(divDowndivDown2==null){}else{divDowndivDown2.style.display='none'};");
//			//���ñ���Ԥ���ɱ༭
			//this.getExecuteSG().addExecuteCode("setDisalbe();");
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ˫���ѽ���ͼ�м�¼
	 * copy��ͼ��Ϣ��ҳ��
	 * @return
	 */
	@PageEvent("savetoqv")
	public int savetoqv(String qvid) throws RadowException{
		try{
			HBSession session = HBUtil.getHBSession();
			Qryview qryview = (Qryview)session.get(Qryview.class, qvid);
			//��ͼ��
			this.getPageElement("viewname").setValue(qryview.getViewname());
			//������
			this.getPageElement("chinesename").setValue(qryview.getChinesename());
			//��;
			this.getPageElement("use").setValue(qryview.getUses());
			//��������
			this.getPageElement("funcdesc").setValue(qryview.getFuncdesc());
			//����
			this.getPageElement("qvid").setValue(qryview.getQvid());
			
			//�����������tabҳ���ȫ��js����
			this.getExecuteSG().addExecuteCode("clearParameter();");
			//������ͼ������iframeҳ��
			this.getExecuteSG().addExecuteCode("setQvidToIframe('iframeCondition','"+qvid+"');");
			//������Ϣ���б�
			this.getExecuteSG().addExecuteCode("setTab2();");
			//�ж��Ƿ��Ѿ�����sql
			String id=HBUtil.getValueFromTab("qvid", "qryview", " qrysql is not null and qvid='"+qvid+"' ");
			//����Ԥ������
			if(id!=null&&!"".equals(id)&&!"null".equals(id)){//�Ѿ�����sql
				//�����б�����
				this.getExecuteSG().addExecuteCode("loadGridPre('"+qvid+"');");
			}else{//Ϊ����sql
				this.getExecuteSG().addExecuteCode("var divUpdivUp2=document.frames['iframePreview'].document.getElementById('divUp');if(divUpdivUp2==null){}else{divUpdivUp2.style.display='block'};"
						+ "var divDowndivDown2=document.frames['iframePreview'].document.getElementById('divDown');if(divDowndivDown2==null){}else{divDowndivDown2.style.display='none'};");
//				this.getExecuteSG().addExecuteCode("window.setTimeout(\"aaasavetoqv()\",300);function aaasavetoqv(){document.frames['iframePreview'].document.getElementById('divUp').style.display='block';"
//						+ "document.frames['iframePreview'].document.getElementById('divDown').style.display='none';}");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ����,�����ͼ��Ϣ
	 * @return
	 */
	@PageEvent("clearqv")
	public int clearqv(){
		this.clearDivData("area3");
		this.getExecuteSG().addExecuteCode("clearParameter();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	/**
	 * ɾ����ʾ
	 * @param
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("deleteCheck")
	public int deleteCheck(String v) throws RadowException, AppException {
		HBSession session = HBUtil.getHBSession();
		String qvid=this.getPageElement("qvid").getValue();//����
		if(qvid==null||qvid.length()==0){
			this.setMainMessage("��˫���ѽ���ͼ�б�ѡ��Ҫɾ������ͼ!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "deleteqv");
		//this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("ȷ��ɾ����ͼ��"+v+"����");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	/**
	 * ɾ����ͼ
	 * @return
	 */
	@PageEvent("deleteqv")
	@Transaction
	public int deleteqv() throws RadowException{
		try{
			HBSession session = HBUtil.getHBSession();
			String qvid=this.getPageElement("qvid").getValue();//����
			if(qvid==null||qvid.length()==0){
				this.setMainMessage("��˫���ѽ���ͼ�б�ѡ��Ҫɾ������ͼ!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//��Ϣ��
			Qryview qryview = (Qryview)session.get(Qryview.class, qvid);
			session.delete(qryview);
			String viewnametb=qryview.getViewnametb();//��ͼ��
			//ɾ����Ϣ��
			Connection connection = session.connection();
			Statement stmt=connection.createStatement();
			stmt.execute("delete from qryviewfld where qvid='"+qvid+"'");
			//ɾ������
			stmt.execute("delete from qryuse where qvid='"+qvid+"'");
			if(viewnametb!=null&&!viewnametb.trim().equals("")&&!viewnametb.equals("null")){
				//ɾ����ͼ
				String sql="drop view "+viewnametb;
				stmt.execute(sql);
			}
			stmt.execute(" delete from COMPETENCE_USERQRYVIEW  where viewid  ='"+qvid+"' ");//ɾ��Ȩ��
			session.flush();
			//ˢ����ͼ�б�
			this.getExecuteSG().addExecuteCode("refreshList();");
			//�����ͼ��Ϣ
			clearqv();
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}