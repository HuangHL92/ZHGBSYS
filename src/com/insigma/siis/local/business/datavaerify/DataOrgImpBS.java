package com.insigma.siis.local.business.datavaerify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.comm.BusinessBSSupport;

public class DataOrgImpBS extends BusinessBSSupport{

	public static List<List> getADataList(DataOIDTO dto) {
		List<List> list = new ArrayList<List>();
		StringBuilder b = new StringBuilder();
		b.append("select a1.a0000 from a01 a1,a02 a2 where a1.a0000=a2.a0000(+) and a0101 is not null ");
		if(dto.getLtry().equals("0")){
			b.append(" and a1.status<>'3'");
		}
		if(!dto.getGz_lb().equals("")){
			b.append(" and a1.a0160 in (" + dto.getGz_lb().substring(0, dto.getGz_lb().length()-1) + ")");
		}
		if(!dto.getGl_lb().equals("")){
			b.append(" and a1.a0165 in (" + dto.getGl_lb().substring(0, dto.getGl_lb().length()-1) + ")");
		}
		b.append(" and a2.a0201b in (select b.b0111 from b01 b start with b0111='" + dto.getSearchDeptid() +"' connect by prior b0111=b0121)");
		List<B01> list16 = HBUtil.getHBSession().createSQLQuery("select * from b01 start with b0111='" + dto.getSearchDeptid() +"' connect by prior b0111=b0121").addEntity(B01.class).list();
		List<A01> list1 = HBUtil.getHBSession().createSQLQuery("select * from a01 where a0000 in(" + b.toString() + ")").addEntity(A01.class).list();
		List<A02> list2 = HBUtil.getHBSession().createSQLQuery("select * from A02 where a0000 in(" + b.toString() + ")").addEntity(A02.class).list();
		List<A06> list3 = HBUtil.getHBSession().createSQLQuery("select * from A06 where a0000 in(" + b.toString() + ")").addEntity(A06.class).list();
		List<A08> list4 = HBUtil.getHBSession().createSQLQuery("select * from A08 where a0000 in(" + b.toString() + ")").addEntity(A08.class).list();
		List<A11> list5 = HBUtil.getHBSession().createSQLQuery("select * from A11 where a0000 in(" + b.toString() + ")").addEntity(A11.class).list();
		
		List<A14> list6 = HBUtil.getHBSession().createSQLQuery("select * from A14 where a0000 in(" + b.toString() + ")").addEntity(A14.class).list();
		List<A15> list7 = HBUtil.getHBSession().createSQLQuery("select * from A15 where a0000 in(" + b.toString() + ")").addEntity(A15.class).list();
		List<A29> list8 = HBUtil.getHBSession().createSQLQuery("select * from A29 where a0000 in(" + b.toString() + ")").addEntity(A29.class).list();
		List<A30> list9 = HBUtil.getHBSession().createSQLQuery("select * from A30 where a0000 in(" + b.toString() + ")").addEntity(A30.class).list();
		List<A31> list10 = HBUtil.getHBSession().createSQLQuery("select * from A31 where a0000 in(" + b.toString() + ")").addEntity(A31.class).list();
		
		List<A36> list11 = HBUtil.getHBSession().createSQLQuery("select * from A36 where a0000 in(" + b.toString() + ")").addEntity(A36.class).list();
		List<A37> list12 = HBUtil.getHBSession().createSQLQuery("select * from A37 where a0000 in(" + b.toString() + ")").addEntity(A37.class).list();
		List<A41> list13 = HBUtil.getHBSession().createSQLQuery("select * from A41 where a0000 in(" + b.toString() + ")").addEntity(A41.class).list();
		List<A53> list14 = HBUtil.getHBSession().createSQLQuery("select * from A53 where a0000 in(" + b.toString() + ")").addEntity(A53.class).list();
		List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000 in(" + b.toString() + ")").addEntity(A57.class).list();
		
		list.add(list1);
		list.add(list2);
		list.add(list3);
		list.add(list4);
		list.add(list5);
		list.add(list6);
		list.add(list7);
		list.add(list8);
		list.add(list9);
		list.add(list10);
		list.add(list11);
		list.add(list12);
		list.add(list13);
		list.add(list14);
		list.add(list15);
		list.add(list16);
		return list;
	}

	public static int getCountByOrg(DataOIDTO dto) {
		StringBuilder b = new StringBuilder();
		b.append("select distinct a1.a0000 a0000,rownum rn from a01 a1,a02 a2 where a1.a0000=a2.a0000(+) and a0101 is not null ");
		if(dto.getLtry().equals("0")){
			b.append(" and a1.status<>'3'");
		}
		if(!dto.getGz_lb().equals("")){
			b.append(" and a1.a0160 in (" + dto.getGz_lb().substring(0, dto.getGz_lb().length()-1) + ")");
		}
		if(!dto.getGl_lb().equals("")){
			b.append(" and a1.a0165 in (" + dto.getGl_lb().substring(0, dto.getGl_lb().length()-1) + ")");
		}
		b.append(" and a2.a0201b in (select b.b0111 from b01 b start with b0111='" + dto.getSearchDeptid() +"' connect by prior b0111=b0121)");
		Object obj = HBUtil.getHBSession().createSQLQuery("select count(a0000) from (" + b.toString() + ")").uniqueResult();
		if(obj!=null){
			int o = ((BigDecimal)obj).intValue();
			return o;
		}
		return 0;
	}

	public static B01 getOrg(DataOIDTO dto) {
		B01 b01 = (B01) HBUtil.getHBSession().get(B01.class, dto.getSearchDeptid());
		return b01;
	}

	public static List<List> getADataList(DataOIDTO dto, int i, int packcount) {
		List<List> list = new ArrayList<List>();
		StringBuilder b = new StringBuilder();
		b.append("select distinct a1.a0000 a0000,rownum rn from a01 a1,a02 a2 where a1.a0000=a2.a0000(+) and a0101 is not null ");
		if(dto.getLtry().equals("0")){
			b.append(" and a1.status<>'3'");
		}
		if(!dto.getGz_lb().equals("")){
			b.append(" and a1.a0160 in (" + dto.getGz_lb().substring(0, dto.getGz_lb().length()-1) + ")");
		}
		if(!dto.getGl_lb().equals("")){
			b.append(" and a1.a0165 in (" + dto.getGl_lb().substring(0, dto.getGl_lb().length()-1) + ")");
		}
		b.append(" and a2.a0201b in (select b.b0111 from b01 b start with b0111='" + dto.getSearchDeptid() +"' connect by prior b0111=b0121)");
		StringBuilder a01sql = new StringBuilder();
		a01sql.append(" select a0000 from(");
		a01sql.append(b);
		a01sql.append(") where rn >=" + ((i-1)*packcount + 1));
		a01sql.append(" and rn <=" + (i*packcount));
		List<B01> list16 = null;
		if(i==1){
			list16 = HBUtil.getHBSession().createSQLQuery("select * from b01 start with b0111='" + dto.getSearchDeptid() +"' connect by prior b0111=b0121").addEntity(B01.class).list();
		}
		List<A01> list1 = HBUtil.getHBSession().createSQLQuery("select * from a01 where a0000 in(" + a01sql.toString() + ")").addEntity(A01.class).list();
		List<A02> list2 = HBUtil.getHBSession().createSQLQuery("select * from A02 where a0000 in(" + a01sql.toString() + ")").addEntity(A02.class).list();
		List<A06> list3 = HBUtil.getHBSession().createSQLQuery("select * from A06 where a0000 in(" + a01sql.toString() + ")").addEntity(A06.class).list();
		List<A08> list4 = HBUtil.getHBSession().createSQLQuery("select * from A08 where a0000 in(" + a01sql.toString() + ")").addEntity(A08.class).list();
		List<A11> list5 = HBUtil.getHBSession().createSQLQuery("select * from A11 where a0000 in(" + a01sql.toString() + ")").addEntity(A11.class).list();
		
		List<A14> list6 = HBUtil.getHBSession().createSQLQuery("select * from A14 where a0000 in(" + a01sql.toString() + ")").addEntity(A14.class).list();
		List<A15> list7 = HBUtil.getHBSession().createSQLQuery("select * from A15 where a0000 in(" + a01sql.toString() + ")").addEntity(A15.class).list();
		List<A29> list8 = HBUtil.getHBSession().createSQLQuery("select * from A29 where a0000 in(" + a01sql.toString() + ")").addEntity(A29.class).list();
		List<A30> list9 = HBUtil.getHBSession().createSQLQuery("select * from A30 where a0000 in(" + a01sql.toString() + ")").addEntity(A30.class).list();
		List<A31> list10 = HBUtil.getHBSession().createSQLQuery("select * from A31 where a0000 in(" + a01sql.toString() + ")").addEntity(A31.class).list();
		
		List<A36> list11 = HBUtil.getHBSession().createSQLQuery("select * from A36 where a0000 in(" + a01sql.toString() + ")").addEntity(A36.class).list();
		List<A37> list12 = HBUtil.getHBSession().createSQLQuery("select * from A37 where a0000 in(" + a01sql.toString() + ")").addEntity(A37.class).list();
		List<A41> list13 = HBUtil.getHBSession().createSQLQuery("select * from A41 where a0000 in(" + a01sql.toString() + ")").addEntity(A41.class).list();
		List<A53> list14 = HBUtil.getHBSession().createSQLQuery("select * from A53 where a0000 in(" + a01sql.toString() + ")").addEntity(A53.class).list();
		List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000 in(" + a01sql.toString() + ")").addEntity(A57.class).list();
		list.add(list1);
		list.add(list2);
		list.add(list3);
		list.add(list4);
		list.add(list5);
		list.add(list6);
		list.add(list7);
		list.add(list8);
		list.add(list9);
		list.add(list10);
		list.add(list11);
		list.add(list12);
		list.add(list13);
		list.add(list14);
		list.add(list15);
		list.add(list16);
		return list;
	}
	

}
