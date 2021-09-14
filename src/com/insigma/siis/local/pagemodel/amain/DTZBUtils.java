package com.insigma.siis.local.pagemodel.amain;

public class DTZBUtils {

	
	
	
	
	
	
	//区县市党政总sql 4，5,16
	public static String[] getSQLZB_QXS() {
		String sql_16 = "select    count(1) max,       sum(dzzz) dzzz,sum(case when age<42 then 1 else 0 end) age42,       b0111,sum(case when a0165 like '%02%' then 1 else 0 end) a0201e       ,sum(case when a0165 like '%02%' and age<42 then 1 else 0 end) a0201eage  from (select  a0101,                        substr(A0201B, 0, 15) b0111,                        (select max('1')                           from hz_a17 b                          where b.a0000 = a01.a0000                            and a1705 in ('0901', '0902', '0818')) dzzz,                        a01.a0000,substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age,qrzxl,a01.a0288        , min(a0201e) a0201e,a0165   from A01 a01,v_hz_a02 a02         where a01.a0000 = a02.a0000           and a02.A0201B like '001.001.004%'           and length(a02.A0201B) = 19           and (select b0131 from b01 where b01.b0111 = a02.A0201B) in               ('1001', '1004')           and a02.a0281 = 'true'           and a0255 = '1'           and a0201d = '1'           and a0163 = '1'           and decode(a0248, null, '0', '', '0', a0248) = '0'           and a0201e in ('1', '3', '31')           group by a0101,substr(A0201B, 0, 15),a01.a0000,a0107,qrzxl,a01.a0288,a0165           ) c group by b0111";
		
		String sql_ZB_QXS = "select (select sum(nvl(b0183, '0'))+ sum(nvl(b0185, '0')) + sum(nvl(b0246, '0')) from b01 where b01.b0121=t.b0111 and b0131 in('1001', '1004')) zs, b0101,t.*,(select count(1)from (select count(a0000),substr(a02.a0201b,0,15) b0111 from a02 where (select b0131 from b01 s where s.b0111 = a02.a0201b) in ('1001', '1004') and length(a02.a0201b) = 19  and a0255 = '1' and decode(a0248,null,'0','','0',a0248)='0' and a0201e in ('1', '3', '31') group by substr(a02.a0201b,0,15),a0000 having count(a0000) >= 2) w where w.b0111=b01.b0111) jcrz from b01 join ("+sql_16+") t on t.b0111=b01.b0111 order by b0269";
		//全部
		String sql = "select sum(jcrz) 交叉任职, sum(zs) 总职数, sum(max) 实配,sum(age42) 四十岁左右, sum(dzzz) 具有乡镇街道党政正职,sum(case when (dzzz/max)>=0.5 then 1 else 0 end) 乡镇街道党政正职达标单位,count(1) 单位数,sum(a0201e) 党政正职,sum(a0201eage) 四十岁左右党政正职 from("
				+sql_ZB_QXS+ ")";
		
		
		return new String[]{sql_ZB_QXS,sql};
	}
	//第8张表 结构性干部（女干部、党外干部）有关指标（指标17、18、19、20）
	public static String[] getSQLZB_QXS_NVDW() {
		//指标19 市直
		String sql_19 = "select (select b0101 from b01 where b01.b0111=c.b0111) b0101,count(1) 总数,sum(decode(a0104,'1',0,'2',1,0)) 女干部,b0111 from  (select  a0101,A0201B b0111, a0104    from A01 a01,v_hz_a02 a02     where a01.a0000 = a02.a0000       and a02.A0201B like '001.001.002%'       and (select b0131 from b01 where b01.b0111=a02.A0201B) in('3202','3203','3204','3205','32FF','3401','3402','3403','3404','3405','3406','3407','3408','3409','3410','3411','3412','3413','3414','3415','3416','3417','3418','3419','3420','3421','3422','3423','3424','3427','3428','3429','3430','3431','3432','3433','3436','3437','3438','3439','3440','3441','3442','3443','3444','3445','3446','3447','3450','3451','3452')       and a02.a0281 = 'true'       and a0255='1'       and a0201d='1'        and a0163 = '1'        and decode(a0248,null,'0','','0',a0248)='0'       and a0201e in ('1','3','31')) c  group by  b0111 ";
		//指标19 市直
		String sql_19_tj = "select  count(1) 班子总数, sum(case when 女干部>0 then 1 else 0 end) 女干部班子总数 from("
				+sql_19+")";
		//区县市党政 政协
		String sql_18 = "select (select b0101 from b01 where b01.b0111 = c.b0111) b0101,       count(1) max,       sum(case when a0104='2' and b0131 in ('1001','1004') then 1 else 0 end) 党政女干部人数,       sum(case when a0104='2' and b0131 in ('1001') then 1 else 0 end) 党委女干部人数,       sum(case when a0104='2' and b0131 in ('1004') then 1 else 0 end) 政府女干部人数,       sum(case when b0131 in ('1005') then 1 else 0 end) 政协干部,       sum(case when a0141!='01' and b0131 in ('1005') then 1 else 0 end) 政协党外干部,       b0111  from (select  a0101,substr(A0201B, 0, 15) b0111,       (select b0131 from b01 where b01.b0111=a02.A0201B) b0131,        a01.a0000        , min(a0201e) a0201e,a0104,a0141   from A01 a01,v_hz_a02 a02         where a01.a0000 = a02.a0000           and a02.A0201B like '001.001.004%' and length(a02.A0201B)=19           and (select b0131 from b01 where b01.b0111=a02.A0201B) in('1001','1004','1005')           and a02.a0281 = 'true'           and a0255 = '1'           and a0201d = '1'           and a0163 = '1'           and decode(a0248, null, '0', '', '0', a0248) = '0'           and a0201e in ('1', '3', '31')                      group by a0101,a02.A0201B,a01.a0000,a0104,a0141           ) c group by b0111";
		//区县市党政 政协统计
		String sql_qx = "select sum(党政女干部人数) 党政女干部总数,sum(党委女干部人数) 党委女干部总数,sum(政府女干部人数) 政府女干部总数,sum(政协干部) 政协干部总数,sum(政协党外干部) 政协党外干部总数 from("
				+ sql_18 + ")";
		return new String[]{sql_18, sql_qx,sql_19_tj};
	}
	//区县市乡镇统计总sql，   6，7，8，9
	public static String[] getSQLZB_QXSXZ() {
		//乡镇街道
		String sql_17 = "select (select b0101 from b01 where b01.b0111 = c.b0111) b0101,       count(1) max,       sum(case when a0132='1' or a0133='1' then 1 else 0 end) dzzz,       sum(case when age<35 then 1 else 0 end) age35,       sum(case when age<30 then 1 else 0 end) age30,       sum(case when (a0132='1' or a0133='1') and age<35 then 1 else 0 end) age35dzzz,       sum(case when (a0132='1') and age<35 then 1 else 0 end) age35dw,       sum(case when (a0133='1') and age<35 then 1 else 0 end) age35zf,       b0111  from (select  a0101,substr(A0201B, 0, 15) b0111,        a01.a0000,substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age,qrzxl,a01.a0288        , min(a0201e) a0201e,a0132,a0133   from A01 a01,v_hz_a02 a02         where a01.a0000 = a02.a0000           and a02.A0201B like '001.001.004%'           and (select b0124 from b01 where b01.b0111 = a02.A0201B) in ('81','82','83')           and a02.a0281 = 'true'           and a0255 = '1'           and a0201d = '1'           and a0163 = '1'           and decode(a0248, null, '0', '', '0', a0248) = '0'           and a0201e in ('1', '3', '31')           group by a0101,substr(A0201B, 0, 15),a01.a0000,a0107,qrzxl,a01.a0288,a0132,a0133           ) c group by b0111";
		//乡镇
		String sql_16 = "select (select b0101 from b01 where b01.b0111 = c.b0111) b0101,       count(1) max,       sum(case when a0132='1' or a0133='1' then 1 else 0 end) dzzz,       sum(case when age<35 then 1 else 0 end) age35,       sum(case when age<30 then 1 else 0 end) age30,       sum(case when (a0132='1' or a0133='1') and age<35 then 1 else 0 end) age35dzzz,       sum(case when (a0132='1') and age<35 then 1 else 0 end) age35dw,       sum(case when (a0133='1') and age<35 then 1 else 0 end) age35zf,       b0111  from (select  a0101,substr(A0201B, 0, 15) b0111,        a01.a0000,substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age,qrzxl,a01.a0288        , min(a0201e) a0201e,a0132,a0133   from A01 a01,v_hz_a02 a02         where a01.a0000 = a02.a0000           and a02.A0201B like '001.001.004%'           and (select b0124 from b01 where b01.b0111 = a02.A0201B) in ('81','82'     )           and a02.a0281 = 'true'           and a0255 = '1'           and a0201d = '1'           and a0163 = '1'           and decode(a0248, null, '0', '', '0', a0248) = '0'           and a0201e in ('1', '3', '31')           group by a0101,substr(A0201B, 0, 15),a01.a0000,a0107,qrzxl,a01.a0288,a0132,a0133           ) c group by b0111";
		//街道
		String sql_15 = "select (select b0101 from b01 where b01.b0111 = c.b0111) b0101,       count(1) max,       sum(case when a0132='1' or a0133='1' then 1 else 0 end) dzzz,       sum(case when age<35 then 1 else 0 end) age35,       sum(case when age<30 then 1 else 0 end) age30,       sum(case when (a0132='1' or a0133='1') and age<35 then 1 else 0 end) age35dzzz,       sum(case when (a0132='1') and age<35 then 1 else 0 end) age35dw,       sum(case when (a0133='1') and age<35 then 1 else 0 end) age35zf,       b0111  from (select  a0101,substr(A0201B, 0, 15) b0111,        a01.a0000,substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age,qrzxl,a01.a0288        , min(a0201e) a0201e,a0132,a0133   from A01 a01,v_hz_a02 a02         where a01.a0000 = a02.a0000           and a02.A0201B like '001.001.004%'           and (select b0124 from b01 where b01.b0111 = a02.A0201B) in (          '83')           and a02.a0281 = 'true'           and a0255 = '1'           and a0201d = '1'           and a0163 = '1'           and decode(a0248, null, '0', '', '0', a0248) = '0'           and a0201e in ('1', '3', '31')           group by a0101,substr(A0201B, 0, 15),a01.a0000,a0107,qrzxl,a01.a0288,a0132,a0133           ) c group by b0111";
		
		//乡镇全市
		String sql = "select sum(max) 实配,sum(age35) 三十五岁以下,sum(age30) 三十岁以下, sum(dzzz) 党政正职,sum(age35dzzz) 三十五岁党政正职,sum(age35dw) 三十五岁党政正职党委书记 from("
				+sql_16+ ")";
		
		String sql_17qb = "select sum(max) max,sum(dzzz) dzzz,sum(age35) age35,sum(age30) age30,sum(age35dzzz) age35dzzz,sum(age35dw) age35dw,sum(age35zf) age35zf from("
				+sql_17+ ")";
		String sql_16qb = "select sum(max) max,sum(dzzz) dzzz,sum(age35) age35,sum(age30) age30,sum(age35dzzz) age35dzzz,sum(age35dw) age35dw,sum(age35zf) age35zf from("
				+sql_16+ ")";
		String sql_15qb = "select sum(max) max,sum(dzzz) dzzz,sum(age35) age35,sum(age30) age30,sum(age35dzzz) age35dzzz,sum(age35dw) age35dw,sum(age35zf) age35zf from("
				+sql_15+ ")";
		
		return new String[]{sql_17,sql_16,sql_15,sql,sql_17qb,sql_16qb,sql_15qb};
	}
	
	
	//干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）
	public static String[] getv_hz_dtzb_9() {
		
		//分单位统计
		String sql_dw = "select b0101,市管新提任40,市管新提任40全日制,新提任40处级,新40处级全日制,新提任40处级 区县处级新提任40,新40处级全日制 区县新40处级全日制,市管干部,市管全日制,总职数,市直空缺,区县空缺,b0111  from v_hz_dtzb_9 t ";
				
		
		//全市
		String sql_qs = "  select sum(市管新提任40 ) 市管新提任40,sum(市管新提任40全日制) 市管新提任40全日制,   sum(case when b0111 like '001.001.003.%' or b0111 like '001.001.002.%' then 新提任40处级 else 0 end) 新提任40处级,   sum(case when b0111 like '001.001.003.%' or b0111 like '001.001.002.%' then 新40处级全日制 else 0 end) 新40处级全日制,   sum(case when b0111 like '001.001.004.%' then 新提任40处级 else 0 end) 区县处级新提任40,   sum(case when b0111 like '001.001.004.%' then 新40处级全日制 else 0 end) 区县新40处级全日制,   sum(市管干部) 市管干部,sum(市管全日制) 市管全日制,sum(总职数) 总职数,sum(市直空缺+区县空缺) 总空缺 ,sum(市直空缺) 市直空缺,sum(区县空缺) 区县空缺    from    v_hz_dtzb_9 t ";
		//市直全部
		String sql_szqb = sql_qs + " where  b0111 like '001.001.002.%'";
		//市直单位
		String sql_szdw = sql_dw + " where  b0111 like '001.001.002.%'";
		//国企全部
		String sql_gqqb = sql_qs + " where  b0111 like '001.001.003.%' and (select b0131 from b01 where b01.b0111=t.b0111)='511'";
		//国企单位
		String sql_gqdw = sql_dw + " where  b0111 like '001.001.003.%' and (select b0131 from b01 where b01.b0111=t.b0111)='511'";
		//高校全部
		String sql_gxqb = sql_qs + " where  b0111 like '001.001.003.%' and (select b0131 from b01 where b01.b0111=t.b0111)='43'";
		//高校单位
		String sql_gxdw = sql_dw + " where  b0111 like '001.001.003.%' and (select b0131 from b01 where b01.b0111=t.b0111)='43'";
		//区县全部
		String sql_qxqb = sql_qs + " where  b0111 like '001.001.004.%'";
		//区县单位
		String sql_qxdw = sql_dw + " where  b0111 like '001.001.004.%'";
		return new String[]{sql_qs,sql_szqb,sql_szdw,sql_gqqb,sql_gqdw,sql_gxqb,sql_gxdw,sql_qxqb,sql_qxdw};
	}
	
	
	//市直单位领导干部有关指标（指标2、3）
	public static String[] getv_hz_dtzb_2() {
			
		//分单位统计
		String sql_dw = "select b0101, 总职数,处级职数, 市直市管干部,市直市管干部45,市直市管干部40,市直处级干部,市直处级干部40,市直处级干部35,b0111 from v_hz_dtzb_2 t ";
		//全市
		String sql_qs = "  select sum(总职数) 总职数,sum(处级职数) 处级职数, sum(市直市管干部) 市直市管干部,sum(市直市管干部45) 市直市管干部45,sum(市直市管干部40) 市直市管干部40, sum(市直处级干部) 市直处级干部,sum(市直处级干部40) 市直处级干部40,sum(市直处级干部35) 市直处级干部35 from v_hz_dtzb_2 t ";
		//市直全部
		String sql_szqb = sql_qs + " where  b0111 like '001.001.002.%'";
		//市直单位
		String sql_szdw = sql_dw + " where  b0111 like '001.001.002.%'";
		//国企全部
		String sql_gqqb = sql_qs + " where  b0111 like '001.001.003.%' and (select b0131 from b01 where b01.b0111=t.b0111)='511'";
		//国企单位
		String sql_gqdw = sql_dw + " where  b0111 like '001.001.003.%' and (select b0131 from b01 where b01.b0111=t.b0111)='511'";
		//高校全部
		String sql_gxqb = sql_qs + " where  b0111 like '001.001.003.%' and (select b0131 from b01 where b01.b0111=t.b0111)='43'";
		//高校单位
		String sql_gxdw = sql_dw + " where  b0111 like '001.001.003.%' and (select b0131 from b01 where b01.b0111=t.b0111)='43'";
		return new String[]{sql_qs,sql_szqb,sql_szdw,sql_gqqb,sql_gqdw,sql_gxqb,sql_gxdw};
	}
	
	
	

}
