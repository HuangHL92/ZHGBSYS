package com.insigma.siis.local.pagemodel.cadremgn.infmtionquery;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Qryview;
import com.utils.CommonQueryBS;

public class UserDefinedQueryPageModel extends PageModel {
	
	
	public static String jiaoyan = "0";//0�Ǵ� 1�ǵ��
	
	@Override
	public int doInit() throws RadowException {
//		System.setProperty("sun.net.client.defaultConnectTimeout", "120000");
//		System.setProperty("sun.net.client.defaultReadTimeout", "120000");
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException{
		String qid = this.getPageElement("subWinIdBussessId").getValue();
		if(qid!=null&&!"".equals(qid)){
			savetoqv(qid);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ���淽��
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("saveschemename")
	@Transaction
	public int saveschemename() throws RadowException{
		try{
			HBSession session = HBUtil.getHBSession();
			//��ͼ��
			String chinesename=this.getPageElement("queryName").getValue();
			//���ɵ�sql
//			String qrysql=this.getPageElement("qrysql").getValue();
//			if(qrysql!=null){
//				qrysql=qrysql.replaceAll("@@", "\'");
//			}
			if(chinesename==null||chinesename.length()==0){
				this.setMainMessage("�������ѯ����!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			String userid=PrivilegeManager.getInstance().getCueLoginUser().getId();
//			String num=HBUtil.getValueFromTab("count(*)", "qryview", "userid='"+userid+"' and viewname='"+viewname.trim()+"' ");
//			if(Integer.parseInt(num)>0){
//				this.setMainMessage("��ѯ�����Ѵ��ڣ�����������!");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			
			String qvid=this.getPageElement("qvid").getValue();//����
			
			
			if("402881ee6af96a5d016af96e62cb0005".equals(qvid)){
				CommonQueryBS cq=new CommonQueryBS();
				HashMap<String, Object>  QVIDmap= cq.getMapBySQL("select QVID from qryview where QVID='402881ee6af96a5d016af96e62cb0005'");
				if(QVIDmap!=null){
					Qryview qryview=(Qryview)session.get(Qryview.class, qvid);
					//��ͼ��
					qryview.setChinesename(chinesename);
					//���ɵ�sql
//					qryview.setQrysql(qrysql);
					session.update(qryview);
					session.flush();
//					//�����������tabҳ���ȫ��js����
//					this.getExecuteSG().addExecuteCode("clearParameter();");
				}else{
					Qryview qryview=new Qryview();
					//��ͼ��
					qryview.setChinesename(chinesename);
					//���ɵ�sql
//					qryview.setQrysql(qrysql);
					//����ʱ��
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
					Date date=new Date();
					String createtime=sdf.format(date);
					qryview.setCreatetime(createtime);
					//��ͼ����1 �Զ�����ͼ2 �����ͼ 3������ͼ 4�Զ��巽��
					qryview.setType("4");
					//�û�id
					qryview.setUserid(userid);
					session.save(qryview);
					qvid=qryview.getQvid();
					session.flush();
					//this.getPageElement("qvid").setValue(qryview.getQvid());
//					//�����������tabҳ���ȫ��js����
//					this.getExecuteSG().addExecuteCode("clearParameter();");
				}
			}else{
				if(qvid!=null&&qvid.length()>0){//����
					Qryview qryview=(Qryview)session.get(Qryview.class, qvid);
					//��ͼ��
					qryview.setChinesename(chinesename);
					//���ɵ�sql
//					qryview.setQrysql(qrysql);
					session.update(qryview);
					session.flush();
//					//�����������tabҳ���ȫ��js����
//					this.getExecuteSG().addExecuteCode("clearParameter();");
				}else{//����
					Qryview qryview=new Qryview();
					//��ͼ��
					qryview.setChinesename(chinesename);
					//���ɵ�sql
//					qryview.setQrysql(qrysql);
					//����ʱ��
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
					Date date=new Date();
					String createtime=sdf.format(date);
					qryview.setCreatetime(createtime);
					//��ͼ����1 �Զ�����ͼ2 �����ͼ 3������ͼ 4�Զ��巽��
					qryview.setType("4");
					//�û�id
					qryview.setUserid(userid);
					session.save(qryview);
					qvid=qryview.getQvid();
					session.flush();
					//this.getPageElement("qvid").setValue(qryview.getQvid());
//					//�����������tabҳ���ȫ��js����
//					this.getExecuteSG().addExecuteCode("clearParameter();");
				}
			}
			
			this.getExecuteSG().addExecuteCode("savescheall('"+qvid+"');");
			//this.setMainMessage("����ɹ�!");
			//ˢ����ͼ�б�
//			this.getExecuteSG().addExecuteCode("refreshList();");
			//��ת����������
//			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab2');");
			//������Ϣ���б�
//			this.getExecuteSG().addExecuteCode("setTab2();");
			//���ñ���Ԥ���ɱ༭
			//this.getExecuteSG().addExecuteCode("setDisalbe();");
			//������ͼ������iframeҳ��
//			this.getExecuteSG().addExecuteCode("setQvidToIframe('iframeCondition','"+qvid+"');");
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("viewListGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException, AppException{
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username =SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			instr="";
		}else{
			//instr="  and ( userid='"+userid+"'  or qvid in (select t.viewid from COMPETENCE_USERQRYVIEW t where t.userid = '"+userid+"' and t.type = '1' ) ) ";
		}
		String sql="select"
				+ "  qvid, "       //����
				+ " type, "        //��ͼ����1 �Զ�����ͼ2 �����ͼ 3������ͼ
				+ " viewname, "    //��ͼ����
				+ " chinesename, " //������
				+ " q.uses, "         //��;
				+ " funcdesc "    //��������
		+ " from qryview q "
		+ " where  1=1 "
		+ instr
		+ " and type='4' "
		+ " order by createtime asc ";
		this.pageQuery(sql, "SQL", start,limit);
		return EventRtnType.SPE_SUCCESS;
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
			//������
			this.getPageElement("queryName").setValue(qryview.getChinesename());
			//����
			this.getPageElement("qvid").setValue(qvid);
			this.getPageElement("qrysql").setValue(qryview.getQrysql());
			
			//�����������tabҳ���ȫ��js����
			this.getExecuteSG().addExecuteCode("document.getElementById('iframeCondition').contentWindow.clearConditionPar();");
			//������ͼ������iframeҳ��
			this.getExecuteSG().addExecuteCode("document.getElementById('iframeCondition').contentWindow.setQvId('"+qvid+"');");
			//������Ϣ���б�
			this.getExecuteSG().addExecuteCode("document.getElementById('iframeCondition').contentWindow.initList();");
			//�ж��Ƿ��Ѿ�����sql
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab1');");
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/*@PageEvent("initX")
	@NoRequiredValidate
	public void initX() throws RadowException{
		List<HashMap<String,Object>> arr=new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map=new HashMap<String,Object>();
    	map.put("table_name", "��Ա��Ϣ��");
    	arr.add(map);
    	this.getPageElement("tableListGrid").setValueList(arr);
    	
    	List<HashMap<String,Object>> arr2=new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map2=new HashMap<String,Object>();
    	map2.put("code_name", "����");
    	arr2.add(map2);
    	HashMap<String,Object> map3=new HashMap<String,Object>();
    	map3.put("code_name", "�Ա�");
    	arr2.add(map3);
    	this.getPageElement("codeListGrid").setValueList(arr2);
	}*/
	
	@PageEvent("doDel")
	public int doDel(String value) {
		HBUtil.getHBSession().createSQLQuery("delete from qryview where qvid='"+value+"'").executeUpdate();
		this.getExecuteSG().addExecuteCode("radow.doEvent('viewListGrid.dogridquery')");
		this.setMainMessage("ɾ���ɹ�");
		return EventRtnType.NORMAL_SUCCESS;
	}

}