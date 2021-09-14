package com.insigma.siis.local.business.publicServantManage;

public class LongSQL {
	//会议名册sql
	public static String gbmc2SQL(String ids) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append("(case  when length(a0101)=2 then (Substr(a0101,0,1))||'  '||(Substr(a0101,2,2))else a0101 end) as a0101,");
		sql.append("a.a0192a,a0104,a.a0117,a.a0111a,a.qrzxl,a.qrzxlxx,a.zzxl,a.zzxlxx,a.a0140,");
		sql.append("Format_Date_Point(substr(NVL(a.a0192c, a.A0288),0,6)) AS a0192c,");
		sql.append("Format_Date_Point(substr(a.a0134,0,6)) as a0134,");
		sql.append("Format_Date_Point(substr(a.a0107,0,6)) as a0107,");
		sql.append("Format_Date_Point(substr((select max(a2.a0243) from a02 a2 where a2.a0000 = a.a0000 and a2.a0279 = '1'),0,6)) as a0192f,");
		sql.append("( case when ZGXW is  not null and instr(ZGXW,'学士')=0 then ZGXW||chr(10) end  )   ||''|| (case when instr(A0196,'(')>0 then substr(A0196,0,instr(A0196,'(')-1) when instr(A0196,'（')>0 then substr(A0196,0,instr(A0196,'（')-1) else A0196 end) ");
		sql.append("  as  comments ");
		sql.append(" from a01 a where a.a0000 in (");
		sql.append(ids);
		sql.append(")");
		sql.append("order by a.torgid , a.torder");
		return sql.toString();
	}
	//干部名册sql
	/**
	 * 根据人员信息导出人员所有职务信息
	 * @param ids
	 * @return
	 */
	public static String gbmc1SQL(String ids) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append("a2.a0201b,b.b0101,");
		sql.append("a2.a0283g as a0192a,");
		sql.append("(case  when length(a0101)=2 then (Substr(a0101,0,1))||'  '||(Substr(a0101,2,2))else a0101 end) as a0101,");
		sql.append("a0104,a.a0117,a.a0111a,a.qrzxl,a.qrzxlxx,a.zzxl,a.zzxlxx,a.a0140,");
		sql.append("Format_Date_Point(substr(NVL(a.a0192c, a.A0288),0,6)) AS a0192c,");
		sql.append("Format_Date_Point(substr(a.a0134,0,6)) as a0134,");
		sql.append("Format_Date_Point(substr(a.a0107,0,6)) as a0107,");
		sql.append("Format_Date_Point(substr(a2.a0243,0,6)) as a0192f,");
		sql.append("( case when ZGXW is  not null and instr(ZGXW,'学士')=0 then ZGXW||chr(10) end  )   ||''|| (case when instr(A0196,'(')>0 then substr(A0196,0,instr(A0196,'(')-1) when instr(A0196,'（')>0 then substr(A0196,0,instr(A0196,'（')-1) else A0196 end) ");
		sql.append("  as  comments ");
		sql.append(" from a01 a inner join a02 a2 ");
		sql.append(" on a.a0000=a2.a0000  and a2.a0255 = '1'and a2.a0281 = 'true' ");
		sql.append(" left join b01 b on a2.a0201b = b.b0111 ");
		sql.append(" where a.a0000 in ( ");
		sql.append(ids);
		sql.append(" )");
		sql.append(" order by a2.a0201b,b.sortid,to_number(a2.a0225)");
		return sql.toString();
	}
	
	/**
	 * 根据人员信息导出人员所有职务信息 杭州
	 * @param ids
	 * @return
	 */
	public static String gbmc1SQL_HZ(String ids,String checkedgroupid) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		//sql.append("a2.a0201b,b.b0101,");
		sql.append("a.a0192a,");
		sql.append("(case  when length(a0101)=2 then (Substr(a0101,0,1))||'  '||(Substr(a0101,2,2))else a0101 end) as a0101,");
		sql.append("a0104,a.a0117,a.a0111a,a.qrzxl||a.qrzxw qrzxl,a.qrzxlxx,a.zzxl||a.zzxw zzxl,a.zzxlxx,a.a0140,a.a0196,");
		sql.append("Format_Date_Point(substr(a.a0192c,0,6)) AS a0192c,");
		sql.append("Format_Date_Point(substr(a.A0288,0,6)) AS a0288,");
		sql.append("Format_Date_Point(substr(a.a0134,0,6)) as a0134,");
		sql.append("Format_Date_Point(substr(a.a0107,0,6)) as a0107,");
		sql.append("Format_Date_Point(substr(a.a0192f,0,6)) as a0192f,");
		sql.append("( case when ZGXW is  not null and instr(ZGXW,'学士')=0 then ZGXW||chr(10) end  )   ||''|| (case when instr(A0196,'(')>0 then substr(A0196,0,instr(A0196,'(')-1) when instr(A0196,'（')>0 then substr(A0196,0,instr(A0196,'（')-1) else A0196 end) ");
		sql.append("  as  comments ");
		sql.append(" from a01 a ");
		sql.append(" where a.a0000 in ( ");
		sql.append(ids);
		sql.append(" )");
		//sql.append(" order by length(a2.a0201b),b.sortid,to_number(a2.a0225)");
		sql.append(" order by ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' and a0201b like '"+checkedgroupid+"%')  t where rn=1 and t.a0000=a.a0000))");

		return sql.toString();
	}
	
	//干部名册sql
	/**
	 * 只导出指定机构的人员职务信息
	 * @param ids
	 * @param b0111
	 * @return
	 */
	public static String gbmc1SQL(String ids,String b0111) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append("a2.a0201b,b.b0101,");
		sql.append("a2.a0283g as a0192a,");
		sql.append("(case  when length(a0101)=2 then (Substr(a0101,0,1))||'  '||(Substr(a0101,2,2))else a0101 end) as a0101,");
		sql.append("a0104,a.a0117,a.a0111a,a.qrzxl,a.qrzxlxx,a.zzxl,a.zzxlxx,a.a0140,");
		sql.append("Format_Date_Point(substr(NVL(a.a0192c, a.A0288),0,6)) AS a0192c,");
		sql.append("Format_Date_Point(substr(a.a0134,0,6)) as a0134,");
		sql.append("Format_Date_Point(substr(a.a0107,0,6)) as a0107,");
		sql.append("Format_Date_Point(substr(a2.a0243,0,6)) as a0192f,");
		sql.append("( case when ZGXW is  not null and instr(ZGXW,'学士')=0 then ZGXW||chr(10) end  )   ||''|| (case when instr(A0196,'(')>0 then substr(A0196,0,instr(A0196,'(')-1) when instr(A0196,'（')>0 then substr(A0196,0,instr(A0196,'（')-1) else A0196 end) ");
		sql.append("  as  comments ");
		sql.append(" from a01 a inner join a02 a2 ");
		sql.append(" on a.a0000=a2.a0000  and a2.a0255 = '1'and a2.a0281 = 'true' and ( ");
		sql.append(b0111);
		sql.append(") left join b01 b on a2.a0201b = b.b0111 ");
		sql.append(" where a.a0000 in ( ");
		sql.append(ids);
		sql.append(" )  ");
		//sql.append(" order by a2.a0201b,b.sortid,to_number(a2.a0225)");
		sql.append(" order by length(a2.a0201b),b.sortid,to_number(a2.a0225)");
		return sql.toString();
	}
}
