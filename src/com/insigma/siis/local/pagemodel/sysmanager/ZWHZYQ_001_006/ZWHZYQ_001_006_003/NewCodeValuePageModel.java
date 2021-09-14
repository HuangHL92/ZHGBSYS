package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_003;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.CodeDownload;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class NewCodeValuePageModel extends PageModel{
ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	
	@Override
	public int doInit() throws RadowException {
		return 0;
	}
    /**
     * �����������
     * @return
     */
	@PageEvent("getTreeJsonData")
	public int getJsonTree() throws AppException{
		String codetype = this.getParameter("nodeId");
		String node = (String)this.getParameter("node");
		String sql = "";
		String sql2 = "";
		String color = "#FFFFFF";
		String fontColor = "#000000";
		if(node.equals("-1")){
			sql="select t.code_value,t.sub_code_value,t.code_name,t.code_leaf,t.code_status,t.iscustomize from code_value t where t.sub_code_value ='-1' and t.code_type='"+codetype+"' order by t.code_value";
			sql2="select t.code_value,t.sub_code_value,t.code_name,t.code_leaf,t.code_status,t.iscustomize from code_value t where t.code_type='9999'";
		}else{
			//��|�ָ�code_value | code_leaf
			sql="select t.code_value,t.sub_code_value,t.code_name,t.code_leaf,t.code_status,t.iscustomize  from code_value t where t.sub_code_value='"+node+"' and t.code_type='"+codetype+"' order by t.code_value";
			sql2="select t.code_value,t.sub_code_value,t.code_name,t.code_leaf,t.code_status,t.iscustomize  from code_value t where t.code_type= '"+codetype+"' and t.code_value='"+node+"'";
		} 
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();//�õ���ǰ��֯��Ϣ
		List<Object[]> groups = HBUtil.getHBSession().createSQLQuery(sql2).list();//�õ���ǰ��֯��Ϣ
		//ֻ��ʾ���ڵ���֯���¼���֯ ������֯�� ����ʾȫ��
		List<Object[]> choose = new ArrayList();
		for(int i=0;i<groups.size();i++){
			for(int j=0;j<groups.size();j++){
				if(groups.get(j)[0].equals(groups.get(i)[1])){
					groups.remove(i);
					i--;
				}
			}
		}
		boolean equel = false;
		if(!groups.isEmpty()){
			for(int i = 0;i<list.size();i++){
				for(int j = 0;j<groups.size();j++){
					if(groups.get(j)[0].equals(list.get(i)[0])){
						choose.add(groups.get(j));
						equel = true;
					}
				}
			}
		}
		if(equel){
			list = choose;
		}
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (Object[] group : list) {
				if(i==0 && last==1) {
					//�Ƿ��ѡ
					//((group[3]==null||StringUtil.isEmpty(group[3].toString())||group[3].toString().trim().equals("1"))?true:false)
					jsonStr.append("[{\"text\" :\"" + group[2]
							+ "\" ,\"id\" :\"" + group[0]
							+ "\" ,\"leaf\" :" + hasChildren(codetype,group[0].toString())
							+ " ,\"cls\" :\"folder\"");
					jsonStr.append("}]");
				}else if (i == 0) {
					jsonStr.append("[{\"text\" :\"" + group[2]
							+ "\" ,\"id\" :\"" + group[0]
							+ "\" ,\"leaf\" :" + hasChildren(codetype,group[0].toString())
							+ " ,\"cls\" :\"folder\"");
					jsonStr.append("}");
				}else if (i == (last - 1)) {
					jsonStr.append(",{\"text\" :\"" + group[2]
							+ "\" ,\"id\" :\"" + group[0]
							+ "\" ,\"leaf\" :" + hasChildren(codetype,group[0].toString())
							+ " ,\"cls\" :\"folder\"");
					jsonStr.append("}]");
				} else {
					
					jsonStr.append(",{\"text\" :\"" + group[2]
							+ "\" ,\"id\" :\"" + group[0]
							+ "\" ,\"leaf\" :" + hasChildren(codetype,group[0].toString())
							+ " ,\"cls\" :\"folder\"");
					jsonStr.append("}");
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	/**
	 * ������չ����
	 * @param nodeId
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("append")
	public int append(String codeType) throws RadowException {
		String codeValueId = this.getPageElement("nodeId").getValue();
		if(codeType==null || "".equals(codeType) || "S000000".equals(codeType)) {
			this.setMainMessage("����ѡ��һ�����뼯��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("".equals(codeValueId) || codeValueId==null) {
			this.setRadow_parent_data("NEW_"+codeType+"_ROOT");          //NEW����:�½�  ROOT�����丸�ڵ��Ǹ��ڵ㼴û��ѡ����������½�
			this.openWindow("AddCodeValueCue", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_003.NewCodeValueCue");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.openWindow("AddCodeValueCue", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_003.NewCodeValueCue");
		this.setRadow_parent_data("NEW_"+codeType+"_"+codeValueId);
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * �޸���չ������
	 * @param codeType
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("modify")
	public int modify(String codeType) throws RadowException {
		String codeValueId = this.getPageElement("nodeId").getValue();
		if("".equals(codeValueId) || codeValueId==null) {
			this.setMainMessage("��ѡ��һ�������");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.openWindow("ModifyCodeValueCue", "pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_003.NewCodeValueCue");
		this.setRadow_parent_data("UPDATE_"+codeType+"_"+codeValueId);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ɾ���Զ��������
	 * @param nodeId
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("delete")
	public int delete(String codeType) throws RadowException {
		String codeValueId = this.getPageElement("nodeId").getValue();
		if("".equals(codeValueId) || codeValueId==null) {
			this.setMainMessage("��ѡ��һ�������");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		CodeValue codeValue = bs6.getCodeValueByCodeTypeAndId(codeType, codeValueId);
		if("1".equals(codeValue.getIscustomize())) {
			//ɾ���ýڵ㼰���ӽڵ�
			String sql = "delete from code_value where code_type='"+codeType+"' and code_value like '"+codeValueId+"%'";
			String hql = "from CodeValue where codeType='"+codeType+"' and codeValue like '"+codeValueId+"%'";
			//ɾ��codedownload
			List<CodeValue> list = bs6.getSession().createQuery(hql).list();
			for(CodeValue cv: list) {
				bs6.getSession().createSQLQuery("delete from code_download where code_value_seq="+cv.getCodeValueSeq()+"").executeUpdate();
			}
			bs6.getSession().createSQLQuery(sql).executeUpdate();
			//��¼��־
			try {
				new LogUtil().createLog("84", "CODEVALUE", String.valueOf(list.get(0).getCodeValueSeq()), list.get(0).getCodeName(), "ɾ��������", new Map2Temp().getLogInfo(list.get(0),new CodeValue()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.getExecuteSG().addExecuteCode("reloadTree()");
			this.getExecuteSG().addExecuteCode("clear()");
		} else {
			this.setMainMessage("�ô��������ɾ����������Ϊ��ʵϵͳ����������Ѿ��·���");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �Ƿ���������
	 * @param codetype
	 * @param id
	 * @return
	 */
	private String hasChildren(String codetype,String id){
		String sql="select * from code_value where sub_code_value='"+id+"' and code_type='"+codetype+"'";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(list!=null && list.size()>0){
			return "false";
		}else{
			return "true";
		}
	}
}
