package com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.business.entity.AddValue;
import com.insigma.siis.local.business.entity.CodeType;
import com.insigma.siis.local.business.entity.CodeValue;

public class ZWHZYQ_001_006_BS {

	public ZWHZYQ_001_006_BS() {
		session = HBUtil.getHBSession();
	}
	
    HBSession session = HBUtil.getHBSession();						//�������ݿ�Ự����
	
	public HBSession getSession() {
		return session;
	}

	public void setSession(HBSession session) {
		this.session = session;
	}

	/**
	 * ��ѯ���е���Ϣ���ͼ���
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AddType> getAddTypes() {
		String hql = "from AddType "
				+ "   "//��λ�Զ����ֶ�δ���ã��ȹ��˵�(ǰ̨����ʾ)where tableCode!='B01'
				+ " order by addTypeSequence";
		List<AddType> addTypeList = session.createQuery(hql).list();
		return addTypeList;
	}
	/**
	 * ��ѯ����Add_Type��������Ų�ת��Ϊint����
	 * @return
	 */
	public int getMaxSeq() {
		String sql = "select max(ADD_TYPE_SEQUENCE) max from ADD_TYPE";
		Connection conn = HBUtil.getHBSession().connection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int maxSeq = 1;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				maxSeq = rs.getInt("max");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return maxSeq;
	}
	/**
	 * ͨ��addTypeId����AddType
	 * @return
	 */
	public AddType getAddTypeById(String addTypeId) {
		AddType addType = (AddType)session.createQuery("from AddType where addTypeId=:addTypeId")
		.setParameter("addTypeId", addTypeId).uniqueResult();
		return addType;
	}
	/**
	 * ��ȡ������Ϣ���������
	 * @return
	 */
	public int getMaxAddValueSeq(String addTypeId) {
		Connection conn = HBUtil.getHBSession().connection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int maxSeq = 1;
		try {
			ps = conn.prepareStatement("select max(ADD_VALUE_SEQUENCE) ADD_VALUE_SEQUENCE from ADD_VALUE where ADD_TYPE_ID=?");
			ps.setString(1, addTypeId);
			rs = ps.executeQuery();
			if(rs.next()) {
				maxSeq = rs.getInt("ADD_VALUE_SEQUENCE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return maxSeq;
	}
	
	/**
	 * ͨ��Id������Ϣ��
	 * @return
	 */
	public AddValue getAddValueById(String addValueId) {
		String hql = "from AddValue where addValueId=:addValueId";
		AddValue addValue = (AddValue)session.createQuery(hql).setParameter("addValueId", addValueId).uniqueResult();
		return addValue;
	}
	/**
	 * ������Ϣ�����п�ʹ�õ�������Ϣ��  
	 * @return
	 */
	public List<AddType> getValidAddType() {
		String sql = "select * from add_type a where a.add_type_id in(select distinct(add_type_id) from add_value b where b.isused='1')";
		Connection conn = session.connection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AddType> list = new ArrayList<AddType>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				AddType addType = new AddType();
				addType.setAddTypeId(rs.getString("ADD_TYPE_ID"));
				addType.setAddTypeSequence(rs.getInt("ADD_TYPE_SEQUENCE"));
				addType.setAddTypeName(rs.getString("ADD_TYPE_NAME"));
				addType.setAddTypeDetail(rs.getString("ADD_TYPE_DETAIL"));
				list.add(addType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * ͨ��AddTypeId�������п�ʹ�õ�AddValue
	 * @return
	 */
	public List<AddValue> getValidAddValueById(String addTypeId) {
		List<AddValue> list = new ArrayList<AddValue>();
		String sql = "select * from ADD_VALUE where ISUSED='1' and MULTILINESHOW=?";
		Connection conn = session.connection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		AddValue addValue = new AddValue();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, addTypeId);
			rs = ps.executeQuery();
			while(rs.next()) {
				addValue.setAddValueId(rs.getString("ADD_VALUE_ID"));
				addValue.setAddValueSequence(rs.getInt("ADD_VALUE_SEQUENCE"));
				addValue.setAddValueName(rs.getString("ADD_VALUE_NAME"));
				addValue.setAddTypeId(rs.getString("ADD_TYPE_ID"));
				addValue.setCodeType(rs.getString("CODE_TYPE"));
				addValue.setAddValueDetail(rs.getString("ADD_VALUE_DETAIL"));
				addValue.setIsused(rs.getString("ISUSED"));
				addValue.setMultilineshow(rs.getString("MULTILINESHOW"));
				addValue.setPublishStatus(rs.getString("PUBLISH_STATUS"));
				addValue.setColType(rs.getString("COL_TYPE"));
				list.add(addValue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * ͨ��codeType��ѯ����codeValue�ĸ��ڵ�
	 * @param codeType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CodeValue> getFatherCodeValue(String codeType) {
		String hql = "from CodeValue where codeType=:codeType and subCodeValue='-1' order by codeValue";
		List<CodeValue> list = session.createQuery(hql).setParameter("codeType", codeType).list();
		return list;
	}
	/**
	 * �жϽڵ�������ӽڵ��Ƿ����Զ�����з���true��������false
	 * @return
	 */
	public boolean checkHasCustom(String codeType,String subCodeValue) {
		String sql = "select distinct t.iscustomize iscustomize from code_value t where t.code_type='"+codeType+"' and t.sub_code_value='"+subCodeValue+"' ";
		Connection conn = session.connection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String iscustomize = "0";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				iscustomize = rs.getString("iscustomize");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		boolean check = "0".equals(iscustomize) ? false : true;
		return check;
	}
	
	/**
	 * ��ȡcode_value�������
	 * @return
	 */
	public int getMaxCodeValueSeq() {
		Connection conn = session.connection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int maxSeq = 1;
		try {
			ps = conn.prepareStatement("select max(CODE_VALUE_SEQ) CODE_VALUE_SEQ from CODE_VALUE");
			rs = ps.executeQuery();
			if(rs.next()) {
				maxSeq = rs.getInt("CODE_VALUE_SEQ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return maxSeq;
	}
	/**
	 * ����������չ��Ϣ��code_value
	 * @param codeType
	 * @param subCodeValue
	 * @return
	 */
	public String generateCodeValueId(String codeType,String subCodeValue) {
		String check = hasChildren(codeType,subCodeValue);//�ж�Ҫ�½��ĸ��ڵ��Ƿ��������ӽڵ�
		String codeValueId = "";
		if("false".equals(check)) {
			codeValueId = subCodeValue+"01";
		} else if("true".equals(check)) {
			String sql = "select code_value "
					+ "from "
					+ "code_value "
					+ "where "
					+ "code_value_seq="
					+ "(select max(code_value_seq) "
					+ "from "
					+ "code_value "
					+ "where "
					+ "sub_code_value='"+subCodeValue+"' and code_type='"+codeType+"')";
			Connection conn = session.connection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String maxCodeValueId = "";
			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs.next()) {
					maxCodeValueId = rs.getString("code_value");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				try {
					rs.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			String maxCodeValueNum = maxCodeValueId.substring(subCodeValue.length());
			int maxnum = Integer.parseInt(maxCodeValueNum);
			if(maxnum < 9 ){//���code_value���ֵС��9��ô����ʾ��ʽӦ����01,02,03~09
				codeValueId = subCodeValue+"0"+(maxnum+1);
			} else {
				codeValueId = subCodeValue+(maxnum+1);
			}
		}
		return codeValueId;
	}
	/**
	 * �����ϼ��ڵ�Ϊ-1��code_valueֵ
	 * @param codeType
	 * @return
	 */
	public String generateRootCodeValue(String codeType) {
		String codeValueId = "";
		String sql = "select max(code_value) code_value from code_value where code_type='"+codeType+"' and sub_code_value='-1'";
		Connection conn = session.connection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String maxCodeValueId = "";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				maxCodeValueId = rs.getString("code_value");
			} 
			maxCodeValueId = maxCodeValueId==null ? "00":maxCodeValueId;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		int num = Integer.parseInt(maxCodeValueId);
		if(num < 9 ){//���code_value���ֵС��9��ô����ʾ��ʽӦ����01,02,03~09
			codeValueId = "0"+(num+1);
		} else {
			codeValueId = ""+(num+1);
		}
		return codeValueId;
	}
	
	/**
	 * �Ƿ���������
	 * @param codetype
	 * @param id
	 * @return
	 */
	private String hasChildren(String codetype,String id){
		String sql="select * from code_value b where sub_code_value='"+id+"' and code_type='"+codetype+"'";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(list!=null && list.size()>0){
			return "true";                             //��
		}else{
			return "false";                            //û��
		}
	}
	/**
	 * ��ѯCODEVALUE
	 * @param codeType
	 * @param codeValueId
	 * @return
	 */
	public CodeValue getCodeValueByCodeTypeAndId(String codeType, String codeValueId) {
		CodeValue codeValue = (CodeValue) session.createQuery("from CodeValue where codeType=:codeType and codeValue=:codeValue")
				                          .setParameter("codeType", codeType).setParameter("codeValue", codeValueId).uniqueResult();
		return codeValue;
	}
	
	@SuppressWarnings("unchecked")
	public List<CodeValue> getCustomizeCodeValueById(String codeType) {
		List<CodeValue>  list = session.createQuery("from CodeValue where codeType=:codeType and iscustomize='1'")
				               .setParameter("codeType", codeType).list();
		return list;
	}
	
	public CodeType getCodeTypeById(String codeTypeId) {
		CodeType codeType = (CodeType) session.createQuery("from CodeType where codeType=:codeTypeId")
				            .setParameter("codeTypeId", codeTypeId).uniqueResult();
		return codeType;
	}
	
	/**
	 * ��ѯinino
	 * @param codeType
	 * @return
	 */
	public int getIninoByCode(String codeType ) {
		Connection conn = session.connection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int maxSeq = 0;
		try {
			ps = conn.prepareStatement("select max(inino) inino from code_value  where code_type='"+codeType+"'");
			rs = ps.executeQuery();
			if(rs.next()) {
				maxSeq = rs.getInt("inino");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return maxSeq+1;
	}
}
