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
	
	private List<CodeParameterItemDTO> list;//参数明细
	
	private List<CodeParameterItemDTO> delList;//删除明细
	
	
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
	 * 读取代码表中AA09中的全部内容在页面显示
	 * @return ParameterEditDTO
	 * @throws AppException
	 * @updated 2008-05-16
	 * @author 邓林
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
	 * 读取代码表中AA10中的全部内容在页面显示
	 * @return ParameterEditDTO
	 * @throws AppException
	 * @updated 2008-05-16
	 * @author 邓林
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
	 * 代码表新增数据
	 * @return ParameterEditDTO
	 * @throws AppException
	 * @updated 2008-05-20
	 * @author 邓林
	 */
	public void addParameter(CodeParameterDTO dto) throws AppException {
		
		HBSession session = HBUtil.getHBSession();
		
		String hql = "from Aa09 a where a.aaa100=:aaa100";	
		Query query = session.createQuery(hql);
		query.setString("aaa100", dto.getAaa100());
		Aa09 aa09 = (Aa09)query.uniqueResult();
		
		if(aa09 != null){
			throw new AppException("新增的代码已经存在！");
		}
		
		aa09 = new Aa09();
		aa09.setAaa100(dto.getAaa100());
		aa09.setAaa101(dto.getAaa101());
		aa09.setAaa104("1");
		session.save(aa09);
		session.flush();

	}
	
	/**
	 * 代码表删除数据
	 * @return ParameterEditDTO
	 * @throws AppException
	 * @updated 2008-05-20
	 * @author 邓林
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
	 * 保存AA10表
	 * @return ParameterEditDTO
	 * @throws AppException
	 * @updated 2008-05-20
	 * @author 邓林
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
