package com.insigma.siis.local.business.common.bs;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.siis.local.business.entity.Aa10;
import com.insigma.siis.local.business.entity.Aa09;


public class CodeParameterBS extends BSSupport{
	
	private List<CodeParameterItemDTO> list;//������ϸ
	
	private List<CodeParameterItemDTO> delList;//ɾ����ϸ
	
	
	public List<CodeParameterItemDTO> getList() {
		return list;
	}

	public void setList(List<CodeParameterItemDTO> list) {
		this.list = list;
	}

	public List<CodeParameterItemDTO> getDelList() {
		return delList;
	}

	public void setDelList(List<CodeParameterItemDTO> delList) {
		this.delList = delList;
	}

	/**
	 * ��ȡ�������AA09�е�ȫ��������ҳ����ʾ
	 * @return ParameterEditDTO
	 * @throws AppException
	 * @updated 2008-05-16
	 * @author ����
	 */
	@SuppressWarnings("unchecked")
	public List<CodeParameterDTO> loadBasicMessage() throws AppException {

		HBSession session = HBUtil.getHBSession();
		String hql = "from Aa09 a order by a.aaa100";	
		Query query = session.createQuery(hql);	
		List<Aa09> list = query.list();
		
		List<CodeParameterDTO> items = new ArrayList<CodeParameterDTO>();
		
		for(Aa09 a : list){
			CodeParameterDTO dto = new CodeParameterDTO();
			BeanUtil.propertyCopy(a, dto);
			items.add(dto);
		}
		
		return items; 
	}
	
	/**
	 * ��ȡ�������AA10�е�ȫ��������ҳ����ʾ
	 * @return ParameterEditDTO
	 * @throws AppException
	 * @updated 2008-05-16
	 * @author ����
	 */
	@SuppressWarnings("unchecked")
	public List<CodeParameterItemDTO> loadParameterMessage(String aaa100) throws AppException {
		
		HBSession session = HBUtil.getHBSession();
		String hql = "from Aa10 a where a.aaa100=:aaa100 order by a.aaa102";	
		Query query = session.createQuery(hql);	
		query.setString("aaa100", aaa100);
		query.list();
		List<Aa10> list = query.list();
		
		List<CodeParameterItemDTO> items = new ArrayList<CodeParameterItemDTO>();
		
		for(Aa10 a : list){
			CodeParameterItemDTO dto = new CodeParameterItemDTO();
			BeanUtil.propertyCopy(a, dto);
			dto.setFlag(new Long(0));
			items.add(dto);
		}
		
		return items; 
		
	}
	
	/**
	 * �������������
	 * @return ParameterEditDTO
	 * @throws AppException
	 * @updated 2008-05-20
	 * @author ����
	 */
	public void addParameter(CodeParameterDTO dto) throws AppException {
		
		HBSession session = HBUtil.getHBSession();
		
		String hql = "from Aa09 a where a.aaa100=:aaa100";	
		Query query = session.createQuery(hql);
		query.setString("aaa100", dto.getAaa100());
		Aa09 aa09 = (Aa09)query.uniqueResult();
		
		if(aa09 != null){
			throw new AppException("�����Ĵ����Ѿ����ڣ�");
		}
		
		aa09 = new Aa09();
		aa09.setAaa100(dto.getAaa100());
		aa09.setAaa101(dto.getAaa101());
		aa09.setAaa104("1");
		session.save(aa09);
		session.flush();

	}
	
	/**
	 * �����ɾ������
	 * @return ParameterEditDTO
	 * @throws AppException
	 * @updated 2008-05-20
	 * @author ����
	 */
	@SuppressWarnings("unchecked")
	public void deleteParameter(String aaa100) throws AppException {
		
		HBSession session = HBUtil.getHBSession();
		
		String hql = "from Aa10 a where a.aaa100=:aaa100";	
		Query query = session.createQuery(hql);
		query.setString("aaa100", aaa100);
		List<Aa10> list = query.list();
		
		for(Aa10 a : list){
			session.delete(a);
		}
		
		hql = "from Aa09 a where a.aaa100=:aaa100";	
		query = session.createQuery(hql);
		query.setString("aaa100", aaa100);
		Aa09 aa09 = (Aa09)query.uniqueResult();
		
		session.delete(aa09);
		session.flush();
	
	}
	
	/**
	 * ����AA10��
	 * @return ParameterEditDTO
	 * @throws AppException
	 * @updated 2008-05-20
	 * @author ����
	 */
	public Object save() throws AppException {
		
		HBSession session = HBUtil.getHBSession();
		
		for(CodeParameterItemDTO dto : delList){
			if(dto.getFlag().longValue() != 1){
				Aa10 aa10 = new Aa10();
				BeanUtil.propertyCopy(dto, aa10);
				session.delete(aa10);
				session.flush();
			}
		}
		
		for(CodeParameterItemDTO dto : list){
			if(dto.getFlag().longValue() == 1){
				Aa10 aa10 = new Aa10();
				BeanUtil.propertyCopy(dto, aa10);
				aa10.setAaz093(Long.parseLong(HBUtil.getSequence("SQ_AAZ093")));
				session.save(aa10);
				session.flush();
			}else if(dto.getFlag().longValue() == 2){
				Aa10 aa10 = new Aa10();
				BeanUtil.propertyCopy(dto, aa10);
				session.update(aa10);
				session.flush();
			}
		}
		
		//session.flush();
		
		return null;
	}
	
}
