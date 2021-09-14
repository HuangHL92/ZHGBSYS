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
	
    HBSession session = HBUtil.getHBSession();						//创建数据库会话连接
	
	public HBSession getSession() {
		return session;
	}

	public void setSession(HBSession session) {
		this.session = session;
	}

	/**
	 * 查询所有的信息类型集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AddType> getAddTypes() {
		String hql = "from AddType "
				+ "   "//单位自定义字段未做好，先过滤掉(前台不显示)where tableCode!='B01'
				+ " order by addTypeSequence";
		List<AddType> addTypeList = session.createQuery(hql).list();
		return addTypeList;
	}
	/**
	 * 查询所有Add_Type中最大的序号并转换为int类型
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
	 * 通过addTypeId查找AddType
	 * @return
	 */
	public AddType getAddTypeById(String addTypeId) {
		AddType addType = (AddType)session.createQuery("from AddType where addTypeId=:addTypeId")
		.setParameter("addTypeId", addTypeId).uniqueResult();
		return addType;
	}
	/**
	 * 获取补充信息项最大的序号
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
	 * 通过Id查找信息项
	 * @return
	 */
	public AddValue getAddValueById(String addValueId) {
		String hql = "from AddValue where addValueId=:addValueId";
		AddValue addValue = (AddValue)session.createQuery(hql).setParameter("addValueId", addValueId).uniqueResult();
		return addValue;
	}
	/**
	 * 查找信息集下有可使用的所有信息项  
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
	 * 通过AddTypeId查找所有可使用的AddValue
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
	 * 通过codeType查询所有codeValue的父节点
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
	 * 判断节点下面的子节点是否含有自定义项含有返回true不含返回false
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
	 * 获取code_value最大的序号
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
	 * 生成新增扩展信息的code_value
	 * @param codeType
	 * @param subCodeValue
	 * @return
	 */
	public String generateCodeValueId(String codeType,String subCodeValue) {
		String check = hasChildren(codeType,subCodeValue);//判断要新建的父节点是否还有其他子节点
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
			if(maxnum < 9 ){//如果code_value最大值小于9那么其显示形式应该是01,02,03~09
				codeValueId = subCodeValue+"0"+(maxnum+1);
			} else {
				codeValueId = subCodeValue+(maxnum+1);
			}
		}
		return codeValueId;
	}
	/**
	 * 生成上级节点为-1的code_value值
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
		if(num < 9 ){//如果code_value最大值小于9那么其显示形式应该是01,02,03~09
			codeValueId = "0"+(num+1);
		} else {
			codeValueId = ""+(num+1);
		}
		return codeValueId;
	}
	
	/**
	 * 是否有子数据
	 * @param codetype
	 * @param id
	 * @return
	 */
	private String hasChildren(String codetype,String id){
		String sql="select * from code_value b where sub_code_value='"+id+"' and code_type='"+codetype+"'";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(list!=null && list.size()>0){
			return "true";                             //有
		}else{
			return "false";                            //没有
		}
	}
	/**
	 * 查询CODEVALUE
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
	 * 查询inino
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
