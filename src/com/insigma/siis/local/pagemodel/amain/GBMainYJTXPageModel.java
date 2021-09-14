package com.insigma.siis.local.pagemodel.amain;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class GBMainYJTXPageModel extends PageModel {
	

	GBMainBS bs = new GBMainBS();
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		String userid = SysUtil.getCacheCurrentUser().getId();
		@SuppressWarnings("unchecked")
		List<String> qxList= HBUtil.getHBSession().createSQLQuery("select actid from smt_act where roleid='c78831f179a17fce0179a28743d0000f' and userid='"+userid+"' ").list();
		if(qxList.size()==0) {
			this.getExecuteSG().addExecuteCode("$('.extrayj').hide();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	@Synchronous(true)
	public int initX()throws RadowException, AppException{
		try {
			CommQuery cqbs=new CommQuery();
			String sql="select count(1)  as dl, '1' as type" + 
					"  from (SELECT a01.a0000, a01.a0101, a01.a0104, a01.A0107, a01.A0192a" + 
					"          FROM (SELECT T.newA0107 Birthday," + 
					"                       T.Today," + 
					"                       MONTHS_BETWEEN(T.Today, T.newA0107) age," + 
					"                       T.a0104 sex," + 
					"                       T.a0000" + 
					"                  FROM (SELECT b.a0104," + 
					"                               b.A0000," + 
					"                               to_date((SUBSTR(RPAD(b.A0107, 8, '01'), 0, 6))," + 
					"                                       'yyyyMM') newA0107," + 
					"                               to_date(TO_CHAR(sysdate, 'yyyyMM'), 'yyyyMM') Today" + 
					"                          FROM ZHGBSYS.a01 b" + 
					"                         WHERE b.a0163 = '1'" + 
					"                           and (b.A0165 like '%10%' or b.A0165 like '%11%' or" + 
					"                               b.A0165 like '%18%' or b.A0165 like '%19%')" + 
					"                           AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa," + 
					"               ZHGBSYS.a01" + 
					"         WHERE 720 = Aa.age" + 
					"           AND aa.a0000 = a01.a0000) t" + 
					" union all" + 
					" select count(1)  as dl, '2' as type" + 
					"  from (SELECT a01.a0000, a01.a0101, a01.a0104, a01.A0107, a01.A0192a" + 
					"          FROM (SELECT T.newA0107 Birthday," + 
					"                       T.Today," + 
					"                       MONTHS_BETWEEN(T.Today, T.newA0107) age," + 
					"                       T.a0104 sex," + 
					"                       T.a0000" + 
					"                  FROM (SELECT b.a0104," + 
					"                               b.A0000," + 
					"                               to_date((SUBSTR(RPAD(b.A0107, 8, '01'), 0, 6))," + 
					"                                       'yyyyMM') newA0107," + 
					"                               to_date(TO_CHAR((SELECT TO_CHAR(ADD_MONTHS(SYSDATE," + 
					"                                                                         1)," + 
					"                                                              'yyyyMM')" + 
					"                                                 FROM DUAL))," + 
					"                                       'yyyyMM') Today" + 
					"                          FROM ZHGBSYS.a01 b" + 
					"                         WHERE b.a0163 = '1'" + 
					"                           and (b.A0165 like '%10%' or b.A0165 like '%11%' or" + 
					"                               b.A0165 like '%18%' or b.A0165 like '%19%')" + 
					"                           AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa," + 
					"               ZHGBSYS.a01" + 
					"         WHERE 720 = Aa.age" + 
					"           AND aa.a0000 = a01.a0000) t" + 
					" union all" + 
					" select count(1)  as dl, '3' as type" + 
					"  from (SELECT a01.a0000, a01.a0101, a01.a0104, a01.A0107, a01.A0192a" + 
					"          from ZHGBSYS.A01" + 
					"         where get_birthday(A01.A0107," + 
					"                            (select to_char(sysdate, 'yyyy') || '1231'" + 
					"                               from dual)) = 60" + 
					"           and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or" + 
					"                A01.A0165 like '%18%' or A01.A0165 like '%19%')" + 
					"           and a01.a0163 = '1' and exists (select a0201b from a02 where a02.a0000=a01.a0000 and a0279='1' and a0281='true')) t" + 
					" union all" + 
					" select count(1)  as dl, '4' as type" + 
					"  from (select a01.a0000, a01.a1701" + 
					"          from ZHGBSYS.A01 a01" + 
					"         where fkly is null" + 
					"           and 1 = 1" + 
					"           and a01.a0165 like '%03%'" + 
					"           and concat(a01.a0000, '') in" + 
					"               (select a02.a0000" + 
					"                  from ZHGBSYS.A02 a02" + 
					"                 where a02.a0281 = 'true'" + 
					"                   and (1 = 2 or substr(a02.a0201b, 1, 11) = '001.001.002'))" + 
					"        and a0160 like '01%'     and a0163 = '1'" + 
					"           and a0101  <> '谢国建'" + 
					"           and a01.a0107 < '19641231'" + 
					"           and exists (select 1" + 
					"          from a05" + 
					"         where a05.a0000 = a01.a0000" + 
					"           and a0501b in ('0122', '012201', '012202')" + 
					"           and substr(a0504, 0, 6) <= '201312')" + 
					"           and nvl(a01.a0192e, '@') not in ('11', '1A11')) t" + 
					" union all" + 
					" select  count(1)  as dl, '5' as type" + 
					"  from (select a01.a0000, a01.a1701" + 
					"          from ZHGBSYS.A01 a01" + 
					"         where fkly is null" + 
					"           and a0000 not in" + 
					"               (select a0000 from ZHGBSYS.attr_lrzw where ATTR2407 = '1')" + 
					"           and 1 = 1" + 
					"           and concat(a01.a0000, '') in" + 
					"               (select a02.a0000" + 
					"                  from ZHGBSYS.A02 a02" + 
					"                 where a02.a0281 = 'true'" + 
					"                   and a02.a0201b not in" + 
					"                       (select b0111" + 
					"                          from ZHGBSYS.b01" + 
					"                         where b0131 in ('36', '37', '1006', '1007', '3409'))" + 
					"                   and (1 = 2 or substr(a02.a0201b, 1, 11) = '001.001.002'))" + 
					"       and a0160 like '01%'      and a0163 = '1'" + 
					"           and a01.a0107 < '19641231'" + 
					"         and exists (select 1" + 
					"          from a05" + 
					"         where a05.a0000 = a01.a0000" + 
					"           and a0501b in ('013102', '013103', '013104')" + 
					"           and substr(a0504, 0, 6)<= '201112')" + 
					"           and nvl(a01.a0192e, '@') not in ('11', '1A11', '12', '1A12')) t" + 
					" union all" + 
					" select count(1)  as dl, '6' as type" + 
					"  from (select a01.a0000, a01.a1701" + 
					"          from ZHGBSYS.A01 a01" + 
					"         where fkly is null" + 
					"           and 1 = 1" + 
					"           and a01.a0165 like '%03%'" + 
					"           and concat(a01.a0000, '') in" + 
					"               (select a02.a0000" + 
					"                  from ZHGBSYS.A02 a02" + 
					"                 where a02.a0281 = 'true'" + 
					"                   and (1 = 2 or substr(a02.a0201b, 1, 11) = '001.001.002'))" + 
					"      and a0160 like '01%'       and a0163 = '1'" + 
					"           and a0101  <> '谢国建'" + 
					"           and a01.a0107 >= '19641231'" + 
					"           and exists (select 1" + 
					"          from a05" + 
					"         where a05.a0000 = a01.a0000" + 
					"           and a0501b in ('0122', '012201', '012202')" + 
					"           and substr(a0504, 0, 6) <= '201312')" + 
					"           and nvl(a01.a0192e, '@') not in ('11', '1A11')) t" + 
					" union all" + 
					" select  count(1)  as dl, '7' as type" + 
					"  from (select a01.a0000, a01.a1701" + 
					"          from ZHGBSYS.A01 a01" + 
					"         where fkly is null" + 
					"           and a0000 not in" + 
					"               (select a0000 from ZHGBSYS.attr_lrzw where ATTR2407 = '1')" + 
					"           and 1 = 1" + 
					"           and concat(a01.a0000, '') in" + 
					"               (select a02.a0000" + 
					"                  from ZHGBSYS.A02 a02" + 
					"                 where a02.a0281 = 'true'" + 
					"                   and a02.a0201b not in" + 
					"                       (select b0111" + 
					"                          from ZHGBSYS.b01" + 
					"                         where b0131 in ('36', '37', '1006', '1007', '3409'))" + 
					"                   and (1 = 2 or substr(a02.a0201b, 1, 11) = '001.001.002'))" + 
					"        and a0160 like '01%'     and a0163 = '1'" + 
					"           and a01.a0107 >= '19641231'" + 
					"         and exists (select 1" + 
					"          from a05" + 
					"         where a05.a0000 = a01.a0000" + 
					"           and a0501b in ('013102', '013103', '013104')" + 
					"           and substr(a0504, 0, 6)<= '201112')" + 
					"           and nvl(a01.a0192e, '@') not in ('11', '1A11', '12', '1A12')) t" + 
					" union all" + 
					" select  count(1)  as dl, '8' as type" + 
					"  from (select a01.a0000" + 
					"          from ZHGBSYS.A01 a01" + 
					"         where fkly is null" + 
					"    and a0160 like '01%'       and a0163 = '1' and exists (select 1 from ZJS_A0195_TAG t where t.a0000=a01.a0000 and a0195='1823')" + 
					"           and nvl(a01.a0192e, '@') not in ('11', '1A11', '12', '1A12')) t" + 
					" union all" + 
					" select  count(1)  as dl, '11' as type" + 
					"  from (select a01.a0000" + 
					"          from ZHGBSYS.A01 a01" + 
					"          left join ZHGBSYS.A02 a02" + 
					"            on a01.a0000 = a02.a0000" + 
					"         where a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and (a01.a0165 like '%10%' or a01.a0165 like '%11%' or" + 
					"               a01.a0165 like '%18%' or a01.a0165 like '%19%')" + 
					"           and a02.a0255 = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           and a02.a0201b like '001.001%'" + 
					"           and a02.a0279 = '1'" + 
					"           and TRUNC(((to_char(sysdate, 'yyyyMM')) -" + 
					"                     to_char(substr(a01.a0192f, 0, 6))) / 100," + 
					"                     1) <= 7.5" + 
					"           and TRUNC(((to_char(sysdate, 'yyyyMM')) -" + 
					"                     to_char(substr(a01.a0192f, 0, 6))) / 100," + 
					"                     1) >= 4.5)" + 
					" union all" + 
					" select count(1)  as dl, '12' as type" + 
					"  from (select a01.a0000" + 
					"          from ZHGBSYS.A01 a01" + 
					"          left join ZHGBSYS.A02 a02" + 
					"            on a01.a0000 = a02.a0000" + 
					"         where a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and (a01.a0165 like '%10%' or a01.a0165 like '%11%' or" + 
					"               a01.a0165 like '%18%' or a01.a0165 like '%19%')" + 
					"           and a02.a0255 = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           and a02.a0201b like '001.001%'" + 
					"           and a02.a0279 = '1'" + 
					"           and TRUNC(((to_char(sysdate, 'yyyyMM')) -" + 
					"                     to_char(substr(a01.a0192f, 0, 6))) / 100," + 
					"                     1) <= 9.5" + 
					"           and TRUNC(((to_char(sysdate, 'yyyyMM')) -" + 
					"                     to_char(substr(a01.a0192f, 0, 6))) / 100," + 
					"                     1) >= 7.5)" + 
					" union all" + 
					" select count(1)  as dl, '13' as type" + 
					"  from (select a01.a0000" + 
					"          from ZHGBSYS.A01 a01" + 
					"          left join ZHGBSYS.A02 a02" + 
					"            on a01.a0000 = a02.a0000" + 
					"         where a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and (a01.a0165 like '%10%' or a01.a0165 like '%11%' or" + 
					"               a01.a0165 like '%18%' or a01.a0165 like '%19%')" + 
					"           and a02.a0255 = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           and a02.a0201b like '001.001%'" + 
					"           and a02.a0279 = '1'" + 
					"           and TRUNC(((to_char(sysdate, 'yyyyMM')) -" + 
					"                     to_char(substr(a01.a0192f, 0, 6))) / 100," + 
					"                     1) < 10" + 
					"           and TRUNC(((to_char(sysdate, 'yyyyMM')) -" + 
					"                     to_char(substr(a01.a0192f, 0, 6))) / 100," + 
					"                     1) >= 9.5)" + 
					" union all" + 
					" select count(1)  as dl, '14' as type" + 
					"  from (select a01.a0000" + 
					"          from ZHGBSYS.A01 a01" + 
					"          left join ZHGBSYS.A02 a02" + 
					"            on a01.a0000 = a02.a0000" + 
					"         where a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and (a01.a0165 like '%10%' or a01.a0165 like '%11%' or" + 
					"               a01.a0165 like '%18%' or a01.a0165 like '%19%')" + 
					"           and a02.a0255 = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           and a02.a0201b like '001.001%'" + 
					"           and a02.a0279 = '1'" + 
					"           and TRUNC(((to_char(sysdate, 'yyyyMM')) -" + 
					"                     to_char(substr(a01.a0192f, 0, 6))) / 100," + 
					"                     1) >= 10)";
			List<HashMap<String, String>> list = cqbs.getListBySQL2(sql);
			String json = JSON.toJSONString(list);
			this.getExecuteSG().addExecuteCode("setCountData("+json+")");
			//区县市
			String gbdltx=" SELECT 'qxcount1' type,count(1)" + 
					"  FROM (SELECT T.newA0107 Birthday," + 
					"               T.Today," + 
					"               MONTHS_BETWEEN(T.Today, T.newA0107) age," + 
					"               T.a0104 sex," + 
					"               T.a0000" + 
					"          FROM (SELECT b.a0104," + 
					"                       b.A0000," + 
					"                       to_date((SUBSTR(RPAD(b.A0107, 8, '01'), 0, 6))," + 
					"                               'yyyyMM') newA0107," + 
					"                       to_date(TO_CHAR(sysdate, 'yyyyMM'), 'yyyyMM') Today" + 
					"                  FROM ZHGBSYS.a01 b" + 
					"                 WHERE b.a0163 = '1'" + 
					"                   and (b.A0165 like '%10%' or b.A0165 like '%11%' or" + 
					"                       b.A0165 like '%18%' or b.A0165 like '%19%')" + 
					"                   AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa," + 
					"       ZHGBSYS.a01" + 
					" WHERE 720 = Aa.age" + 
					"   AND aa.a0000 = a01.a0000" + 
					"   and (select a0201b" + 
					"          from a02" + 
					"         where a02.a0000 = aa.a0000" + 
					"           and a0281 = 'true'" + 
					"           and a0279 = '1') like '001.001.004%'" + 
					" union all" + 
					" SELECT 'qxcount2' type,count(1)" + 
					"          FROM (SELECT T.newA0107 Birthday," + 
					"                       T.Today," + 
					"                       MONTHS_BETWEEN(T.Today, T.newA0107) age," + 
					"                       T.a0104 sex," + 
					"                       T.a0000" + 
					"                  FROM (SELECT b.a0104," + 
					"                               b.A0000," + 
					"                               to_date((SUBSTR(RPAD(b.A0107, 8, '01'), 0, 6))," + 
					"                                       'yyyyMM') newA0107," + 
					"                               to_date(TO_CHAR((SELECT TO_CHAR(ADD_MONTHS(SYSDATE," + 
					"                                                                         1)," + 
					"                                                              'yyyyMM')" + 
					"                                                 FROM DUAL))," + 
					"                                       'yyyyMM') Today" + 
					"                          FROM ZHGBSYS.a01 b" + 
					"                         WHERE b.a0163 = '1'" + 
					"                           and (b.A0165 like '%10%' or b.A0165 like '%11%' or" + 
					"                               b.A0165 like '%18%' or b.A0165 like '%19%')" + 
					"                           AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa," + 
					"               ZHGBSYS.a01" + 
					"         WHERE 720 = Aa.age" + 
					"           AND aa.a0000 = a01.a0000" + 
					"              and (select a0201b" + 
					"          from a02" + 
					"         where a02.a0000 = aa.a0000" + 
					"           and a0281 = 'true'" + 
					"           and a0279 = '1') like '001.001.004%'" + 
					" union all" + 
					" SELECT  'qxcount3' type,count(1)" + 
					"          from ZHGBSYS.A01" + 
					"         where get_birthday(A01.A0107," + 
					"                            (select to_char(sysdate, 'yyyy') || '1231'" + 
					"                               from dual)) = 60" + 
					"           and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or" + 
					"                A01.A0165 like '%18%' or A01.A0165 like '%19%')" + 
					"           and a01.a0163 = '1'" + 
					"           and (select a0201b" + 
					"          from a02" + 
					"         where a02.a0000 = a01.a0000" + 
					"           and a0281 = 'true'" + 
					"           and a0279 = '1') like '001.001.004%'" + 
					//市直
					" union all" + 
					" SELECT 'szcount1' type,count(1)" + 
					"  FROM (SELECT T.newA0107 Birthday," + 
					"               T.Today," + 
					"               MONTHS_BETWEEN(T.Today, T.newA0107) age," + 
					"               T.a0104 sex," + 
					"               T.a0000" + 
					"          FROM (SELECT b.a0104," + 
					"                       b.A0000," + 
					"                       to_date((SUBSTR(RPAD(b.A0107, 8, '01'), 0, 6))," + 
					"                               'yyyyMM') newA0107," + 
					"                       to_date(TO_CHAR(sysdate, 'yyyyMM'), 'yyyyMM') Today" + 
					"                  FROM ZHGBSYS.a01 b" + 
					"                 WHERE b.a0163 = '1'" + 
					"                   and (b.A0165 like '%10%' or b.A0165 like '%11%' or" + 
					"                       b.A0165 like '%18%' or b.A0165 like '%19%')" + 
					"                   AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa," + 
					"       ZHGBSYS.a01" + 
					" WHERE 720 = Aa.age" + 
					"   AND aa.a0000 = a01.a0000" + 
					"   and (select a0201b" + 
					"          from a02" + 
					"         where a02.a0000 = aa.a0000" + 
					"           and a0281 = 'true'" + 
					"           and a0279 = '1') like '001.001.002%'" + 
					" union all" + 
					" SELECT 'szcount2' type,count(1)" + 
					"          FROM (SELECT T.newA0107 Birthday," + 
					"                       T.Today," + 
					"                       MONTHS_BETWEEN(T.Today, T.newA0107) age," + 
					"                       T.a0104 sex," + 
					"                       T.a0000" + 
					"                  FROM (SELECT b.a0104," + 
					"                               b.A0000," + 
					"                               to_date((SUBSTR(RPAD(b.A0107, 8, '01'), 0, 6))," + 
					"                                       'yyyyMM') newA0107," + 
					"                               to_date(TO_CHAR((SELECT TO_CHAR(ADD_MONTHS(SYSDATE," + 
					"                                                                         1)," + 
					"                                                              'yyyyMM')" + 
					"                                                 FROM DUAL))," + 
					"                                       'yyyyMM') Today" + 
					"                          FROM ZHGBSYS.a01 b" + 
					"                         WHERE b.a0163 = '1'" + 
					"                           and (b.A0165 like '%10%' or b.A0165 like '%11%' or" + 
					"                               b.A0165 like '%18%' or b.A0165 like '%19%')" + 
					"                           AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa," + 
					"               ZHGBSYS.a01" + 
					"         WHERE 720 = Aa.age" + 
					"           AND aa.a0000 = a01.a0000" + 
					"              and (select a0201b" + 
					"          from a02" + 
					"         where a02.a0000 = aa.a0000" + 
					"           and a0281 = 'true'" + 
					"           and a0279 = '1') like '001.001.002%'" + 
					" union all" + 
					" SELECT  'szcount3' type,count(1)" + 
					"          from ZHGBSYS.A01" + 
					"         where get_birthday(A01.A0107," + 
					"                            (select to_char(sysdate, 'yyyy') || '1231'" + 
					"                               from dual)) = 60" + 
					"           and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or" + 
					"                A01.A0165 like '%18%' or A01.A0165 like '%19%')" + 
					"           and a01.a0163 = '1'" + 
					"           and (select a0201b" + 
					"          from a02" + 
					"         where a02.a0000 = a01.a0000" + 
					"           and a0281 = 'true'" + 
					"           and a0279 = '1') like '001.001.002%'" + 
					//国企
					" union all" + 
					" SELECT 'gqgxcount1' type,count(1)" + 
					"  FROM (SELECT T.newA0107 Birthday," + 
					"               T.Today," + 
					"               MONTHS_BETWEEN(T.Today, T.newA0107) age," + 
					"               T.a0104 sex," + 
					"               T.a0000" + 
					"          FROM (SELECT b.a0104," + 
					"                       b.A0000," + 
					"                       to_date((SUBSTR(RPAD(b.A0107, 8, '01'), 0, 6))," + 
					"                               'yyyyMM') newA0107," + 
					"                       to_date(TO_CHAR(sysdate, 'yyyyMM'), 'yyyyMM') Today" + 
					"                  FROM ZHGBSYS.a01 b" + 
					"                 WHERE b.a0163 = '1'" + 
					"                   and (b.A0165 like '%10%' or b.A0165 like '%11%' or" + 
					"                       b.A0165 like '%18%' or b.A0165 like '%19%')" + 
					"                   AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa," + 
					"       ZHGBSYS.a01" + 
					" WHERE 720 = Aa.age" + 
					"   AND aa.a0000 = a01.a0000" + 
					"   and (select a0201b" + 
					"          from a02" + 
					"         where a02.a0000 = aa.a0000" + 
					"           and a0281 = 'true'" + 
					"           and a0279 = '1') like '001.001.003%'" + 
					" union all" + 
					" SELECT 'gqgxcount2' type,count(1)" + 
					"          FROM (SELECT T.newA0107 Birthday," + 
					"                       T.Today," + 
					"                       MONTHS_BETWEEN(T.Today, T.newA0107) age," + 
					"                       T.a0104 sex," + 
					"                       T.a0000" + 
					"                  FROM (SELECT b.a0104," + 
					"                               b.A0000," + 
					"                               to_date((SUBSTR(RPAD(b.A0107, 8, '01'), 0, 6))," + 
					"                                       'yyyyMM') newA0107," + 
					"                               to_date(TO_CHAR((SELECT TO_CHAR(ADD_MONTHS(SYSDATE," + 
					"                                                                         1)," + 
					"                                                              'yyyyMM')" + 
					"                                                 FROM DUAL))," + 
					"                                       'yyyyMM') Today" + 
					"                          FROM ZHGBSYS.a01 b" + 
					"                         WHERE b.a0163 = '1'" + 
					"                           and (b.A0165 like '%10%' or b.A0165 like '%11%' or" + 
					"                               b.A0165 like '%18%' or b.A0165 like '%19%')" + 
					"                           AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa," + 
					"               ZHGBSYS.a01" + 
					"         WHERE 720 = Aa.age" + 
					"           AND aa.a0000 = a01.a0000" + 
					"              and (select a0201b" + 
					"          from a02" + 
					"         where a02.a0000 = aa.a0000" + 
					"           and a0281 = 'true'" + 
					"           and a0279 = '1') like '001.001.003%'" + 
					" union all" + 
					" SELECT  'gqgxcount3' type,count(1)" + 
					"          from ZHGBSYS.A01" + 
					"         where get_birthday(A01.A0107," + 
					"                            (select to_char(sysdate, 'yyyy') || '1231'" + 
					"                               from dual)) = 60" + 
					"           and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or" + 
					"                A01.A0165 like '%18%' or A01.A0165 like '%19%')" + 
					"           and a01.a0163 = '1'" + 
					"           and (select a0201b" + 
					"          from a02" + 
					"         where a02.a0000 = a01.a0000" + 
					"           and a0281 = 'true'" + 
					"           and a0279 = '1') like '001.001.003%'" ;
			List<HashMap<String, String>> listGBDLTX = cqbs.getListBySQL2(gbdltx);
			String jsonGBDLTX = JSON.toJSONString(listGBDLTX);
			this.getExecuteSG().addExecuteCode("setGBDLTXData("+jsonGBDLTX+")");
			this.setNextEventName("init1");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("初始化失败");
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//初始化可退出领导岗位转任职级
	@PageEvent("init1")
	@NoRequiredValidate
	public  int  init1() throws RadowException{
		try {
			CommQuery cqbs=new CommQuery();
			String sql ="select'fz6ytc' type, count(1)" + 
					"  from a01,a02,b01" + 
					" where get_birthday(A01.A0107," + 
					"                   '20201231') >= 57" + 
					"   and (A01.A0165 like '%11%' or A01.A0165 like '%21%')" + 
					"   and A01.A0163 = '1'" + 
					"   and A01.status = '1'" + 
					"   and a0281='true' and a0279='1' and a02.a0000=a01.a0000 and a02.a0201b=b01.b0111" + 
					"   and A01.A0000 in (select distinct (A02.A0000)" + 
					"                       from ZHGBSYS.A02" + 
					"                      where A02.A0201E in ('1','3') and a0279='1')" + 
					" and substr(a0201b,1,11)<>'001.001.003'  and a0201b <>'001.001.002.02O.003'  and a0201b<>'001.001.002.00C' " + 
					" union all" + 
					" select 'zz12ytc' type,count(1)" + 
					"          from a01,a02,b01" + 
					"         where ((get_birthday(A01.A0107," + 
					"                            to_char(sysdate, 'yyyy') || '1231') >= 58 and b0131<>'1003' and b0131<>'1005')" + 
					"                  or" + 
					"                  (get_birthday(A01.A0107," + 
					"                            to_char(sysdate, 'yyyy') || '1231') >= 59 and (b0131='1003' or b0131='1005')))          " + 
					"           and A01.A0165 like '%10%'" + 
					"           and A01.A0163 = '1'" + 
					"           and A01.status = '1'" + 
					"           and a0281='true' and a0279='1' and a02.a0000=a01.a0000 and a02.a0201b=b01.b0111" + 
					"           and A01.A0000 in (select  A02.A0000" + 
					"                               from ZHGBSYS.A02" + 
					"                              where A02.A0201E in ('1','3') and a0279='1'" + 
					"                               )" + 
					"    and substr(a0201b,1,11)<>'001.001.003'     and a0201b <>'001.001.002.02O.003'  and a0201b<>'001.001.002.00C'  " + 
					" union all" + 
					" select 'fz12ytc' type, count(1)" + 
					"  from a01,a02,b01" + 
					" where ((get_birthday(A01.A0107," + 
					"                    to_char(sysdate, 'yyyy') || '1231') >= 57 and b0131<>'1003' and b0131<>'1005'  and get_birthday(A01.A0107,  '20201231') < 57)" + 
					"        or" + 
					"        (get_birthday(A01.A0107," + 
					"                    to_char(sysdate, 'yyyy') || '1231') >= 59 and (b0131='1003' or b0131='1005')))" + 
					"   and (A01.A0165 like '%11%' or A01.A0165 like '%21%')" + 
					"   and A01.A0163 = '1'" + 
					"   and A01.status = '1'" + 
					"   and a0281='true' and a0279='1' and a02.a0000=a01.a0000 and a02.a0201b=b01.b0111" + 
					"   and A01.A0000 in (select distinct (A02.A0000)" + 
					"                       from ZHGBSYS.A02" + 
					"                      where A02.A0201E in ('1','3') and a0279='1')" + 
					"  and substr(a0201b,1,11)<>'001.001.003' and a0201b <>'001.001.002.02O.003'  and a0201b<>'001.001.002.00C'  ";
			List<HashMap<String, String>> listKTC= cqbs.getListBySQL2(sql);
			String jsonKTC = JSON.toJSONString(listKTC);
			this.getExecuteSG().addExecuteCode("setGBDLTXData("+jsonKTC+")");
			
			
			
			//试用期
			String sysql="select 'bysyqjm' type ,count(1) from a01 where a0192a like '%试用期%' and a0165 like '%11%'" + 
					" and  MONTHS_BETWEEN(to_date(to_char(sysdate,'yyyymm'),'yyyymm'),to_date(substr(a0288,0,6),'yyyymm'))>=12" + 
					" union all" + 
					" select 'xysyqjm' type ,count(1) from a01 where a0192a like '%试用期%' and a0165 like '%11%'" + 
					" and  MONTHS_BETWEEN(to_date(to_char(sysdate,'yyyymm'),'yyyymm'),to_date(substr(a0288,0,6),'yyyymm'))>=11" + 
					" and  MONTHS_BETWEEN(to_date(to_char(sysdate,'yyyymm'),'yyyymm'),to_date(substr(a0288,0,6),'yyyymm'))<12" + 
					" union all" + 
					" select 'syqry' type ,count(1) from a01 where a0192a like '%试用期%' and a0165 like '%11%'";
			List<HashMap<String, String>> sy= cqbs.getListBySQL2(sysql);
			String jsonSY = JSON.toJSONString(sy);
			this.getExecuteSG().addExecuteCode("setGBDLTXData("+jsonSY+")");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("初始化失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//切换年份
	@PageEvent("yearchange")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int yearchange() throws RadowException, AppException {
		String datetime=this.getPageElement("datetime").getValue();
		if (datetime.length()!=8 && datetime.length()!=6) {
			this.setMainMessage("请输入6位或8位日期！");
			return EventRtnType.NORMAL_SUCCESS;
		}else if (datetime.length()!=8)	{
			datetime=datetime+"01";
		}
		this.getPageElement("searchdata").setValue(datetime);
		try {
			CommQuery cqbs=new CommQuery();
			String sql="select 'zzmytc' type,count(1)" + 
					"          from a01,a02,b01" + 
					"         where ((get_birthday(A01.A0107," + 
					"                            '"+datetime+"') >= 58 and b0131<>'1003' and b0131<>'1005')" + 
					"                  or" + 
					"                  (get_birthday(A01.A0107," + 
					"                            '"+datetime+"') >= 59 and (b0131='1003' or b0131='1005')))          " + 
					"           and A01.A0165 like '%10%'" + 
					"           and A01.A0163 = '1'" + 
					"           and A01.status = '1'" + 
					"           and a0281='true' and a0279='1' and a02.a0000=a01.a0000 and a02.a0201b=b01.b0111" + 
					"           and A01.A0000 in (select  A02.A0000" + 
					"                               from ZHGBSYS.A02" + 
					"                              where A02.A0201E in ('1','3') and a0279='1'" + 
					"                               )" + 
					"   and substr(a0201b,1,11)<>'001.001.003'      and a0201b <>'001.001.002.02O.003'  and a0201b<>'001.001.002.00C'  " + 
					" union all" + 
					" select 'fzmytc' type, count(1)" + 
					"  from a01,a02,b01" + 
					" where ((get_birthday(A01.A0107," + 
					"                    '"+datetime+"') >= 57 and b0131<>'1003' and b0131<>'1005')" + 
					"        or" + 
					"        (get_birthday(A01.A0107," + 
					"                    '"+datetime+"') >= 59 and (b0131='1003' or b0131='1005')))" + 
					"   and (A01.A0165 like '%11%' or A01.A0165 like '%21%')" + 
					"   and A01.A0163 = '1'" + 
					"   and A01.status = '1'" + 
					"   and a0281='true' and a0279='1' and a02.a0000=a01.a0000 and a02.a0201b=b01.b0111" + 
					"   and A01.A0000 in (select distinct (A02.A0000)" + 
					"                       from ZHGBSYS.A02" + 
					"                      where A02.A0201E in ('1','3') and a0279='1')" + 
					"  and substr(a0201b,1,11)<>'001.001.003'  and a0201b <>'001.001.002.02O.003'  and a0201b<>'001.001.002.00C'  ";
			List<HashMap<String, String>> listKTC= cqbs.getListBySQL2(sql);
			String jsonKTC = JSON.toJSONString(listKTC);
			this.getExecuteSG().addExecuteCode("setGBDLTXData("+jsonKTC+")");
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','年份切换失败！',null,'220')");
			return EventRtnType.FAILD;
		}

		return EventRtnType.NORMAL_SUCCESS;
	}
}
