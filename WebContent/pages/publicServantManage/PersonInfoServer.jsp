<%@page import="com.insigma.siis.local.business.entity.A01"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.UUID"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%!	private String SV(String v) {
		return v == null ? "" : StringEscapeUtils.escapeHtml(v);
	}%>
<%!	private String schema() {
		return "@TO_GBDJD";
}%>
<%!private String SG(String gender) {
		if (null == gender) {
			return "";
		} else if ("1".equals(gender)) {
			return StringEscapeUtils.escapeHtml("男");
		} else if ("2".equals(gender)) {
			return StringEscapeUtils.escapeHtml("女");
		} else {
			return StringEscapeUtils.escapeHtml(gender);
		}
	}%>
<%!private String SD(String date) {
		if (null == date) {
			return "";
		} else if (6 == date.length()) {
			return StringEscapeUtils.escapeHtml(date.substring(0, 4) + "." + date.substring(4, 6));
		} else if (8 == date.length()) {
			return StringEscapeUtils
					.escapeHtml(date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6, 8));
		} else {
			return StringEscapeUtils.escapeHtml(date);
		}
	}%>
<%
	String ctxPath = request.getContextPath();
	String username = "";


	HBSession sess = HBUtil.getHBSession();
	
	String a0000 = request.getParameter("a0000");
	a0000 = URLDecoder.decode(a0000, "UTF8");
	String sql = "from A01 where a0000='" + a0000 + "'";
	List list = sess.createQuery(sql).list();

	A01 a01 = null;
	if (list == null || list.size() == 0) {
		a01 = new A01();
	} else {
		a01 = (A01) list.get(0);
	}

	String a0195Text = null;
	if (a01.getA0195() != null) {
		a0195Text = HBUtil.getValueFromTab("b0101", "b01", " b0111='" + a01.getA0195() + "'");

		if (a0195Text == null || a0195Text.equals("")) {
			a0195Text = a01.getA0195();
		}
	}

 	//判断是否有权限
	boolean xfflag=true;
	boolean cyflag=true;
	boolean zdsxflag=true;
	boolean ygsxflag=true;
	boolean jjflag=true;
	boolean cgflag=true;
	boolean xryrflag=true;
	boolean ddzwflag=true;
	boolean tqjwyj=true;

	//成员单位信息显示的是 省纪委,其他纪委意见,省审计厅
	List<Object[]> sjw = new ArrayList();
	if (a01 != null&&cyflag) {
		String sjwsql = "select to_char(nvl(jwfeedbacktime,tojwtime),'yyyyMMdd'),jwopinion from jw_checkopinion"+schema()+" where psnkey='"
				+ a01.getA0000() + "' and state='4' order by jwfeedbacktime";
		List<Object[]> sjws=sess.createSQLQuery(sjwsql).list();
		if(sjws.size()!=0){
			sjw = sjws;
		}	
	}
	
	
	List<Object[]>  jwqt=new ArrayList();
	if(a01!=null&&cyflag){
		String jwqtsql="select to_char(submittime,'yyyyMMdd') submittime,briefcase from jw_other"+schema()+" where psnkey='"
				+ a01.getA0000() + "'  order by submittime desc";
		List<Object[]> jwqts=sess.createSQLQuery(jwqtsql).list();
		if(jwqts.size()!=0){
			jwqt = jwqts;
		}	
	}
	
	//成员单位监督信息(导入部门：市纪委市监委)
	List<Object[]> cydw=new ArrayList();
	if(a01 != null&&cyflag){
		String cydwsql="select distinct  (select rolename from smt_role"+schema()+" where roleid=inputorgid) rolename,"+
						"to_char(inputtime,'yyyyMMdd') inputtime,briefcase,"+
						"(select code_name from code_value"+schema()+" where code_type='DJCF' and code_value=DJCF) djcfname,"+
						"(select code_name from code_value"+schema()+" where code_type='ZWCF' and code_value=ZWCF) zwcfname,"+
						"(select code_name from code_value"+schema()+" where code_type='XFZZCL' and code_value=cfqk_zzcl) zzclname,"+
						"(select code_name from code_value"+schema()+" where code_type='XFTXHXJM' and code_value=cfqk_txhxjm) txhxjmname,"+
						"(select code_name from code_value"+schema()+" where code_type = 'XFWJSCLQK' and code_value = cfqk_wfsclqk) wfsclqkname"+
						" from wadedryinfo"+schema()+" where inputorgid='131' and deleteflag = 0 and psnkey='"+a01.getA0000()+"' order by inputtime desc";
		System.out.println(cydwsql);
		List<Object[]> jwqts=sess.createSQLQuery(cydwsql).list();
		if(jwqts.size()!=0){
			cydw = jwqts;
		}	
	}
	
	//成员单位监督信息(导入部门：其他部门)
		List<Object[]> cydw2=new ArrayList();
		if(a01 != null&&cyflag){
			String cydwsql2="select distinct  (select rolename from smt_role"+schema()+" where roleid=inputorgid) rolename,"+
							"to_char(inputtime,'yyyyMMdd') inputtime,briefcase,"+
							"(select code_name from code_value"+schema()+" where code_type='DJCF' and code_value=DJCF) djcfname,"+
							"(select code_name from code_value"+schema()+" where code_type='ZWCF' and code_value=ZWCF) zwcfname,"+
							"(select code_name from code_value"+schema()+" where code_type='XFZZCL' and code_value=cfqk_zzcl) zzclname,"+
							"(select code_name from code_value"+schema()+" where code_type='XFTXHXJM' and code_value=cfqk_txhxjm) txhxjmname,"+
							"(select code_name from code_value"+schema()+" where code_type = 'XFWJSCLQK' and code_value = cfqk_wfsclqk) wfsclqkname"+
							" from wadedryinfo"+schema()+" where inputorgid not in ('131') and deleteflag = 0 and psnkey='"+a01.getA0000()+"' order by inputtime desc";
			System.out.println(cydwsql2);
			List<Object[]> jwqts2=sess.createSQLQuery(cydwsql2).list();
			if(jwqts2.size()!=0){
				cydw2 = jwqts2;
			}	
		}
	

	//处分情况(相关处理情况)
	List<Object[]> cfqk =new ArrayList();
	if (a01 != null&&xfflag) {
		String cfqksql = "select to_char(t.createtime, 'yyyyMMdd') time, a.reportcontent nr, t.punishtype type from xf_punish"+schema()+" t left join xf_main"+schema()+" a on t.p_oid = t.oid left join xf_breporter"+schema()+" b on t.breporertoid = b.oid where b.a00 = '"+a01.getA0000()+"' and a.status = '06'"
					+"  union select to_char(begintime,'yyyyMMdd') time,content nr,'诫勉' type from cy_punish"+schema()+" where psnkey='"+a01.getA0000()+"'"
					+"  union select to_char(a.createtime, 'yyyyMMdd') time, c.QUEINFO nr, '提醒' type from cy_tip"+schema()+" a left join questioninfo"+schema()+" c on c.REMIDID = a.oid where a.psnkey='"+a01.getA0000()+"'"
					+"  union select to_char(sendtime,'yyyyMMdd') time,checkcontent nr,'函询' type from cy_docquery"+schema()+" where psnkey='"+a01.getA0000()+"'  order by time";
		System.out.println(cfqksql);
		List<Object[]> cfqks=sess.createSQLQuery(cfqksql).list();
		if(cfqks.size()!=0){
			cfqk=cfqks;
		}
	}

	//信访举报(信访检查)
	String[] xfResults = {"存查", "属实", "部分属实", "不属实", "无法查清", "已转办"};
	List<List<Object[]>> xfjbs = new ArrayList<List<Object[]>>();
	if (a01 != null&&xfflag) {
		for(int i=0, len=xfResults.length; i<len; i++){
			//String xfjbsql = "select to_char(xf_createdate,'yyyyMMdd'),xf_reportcontent,result_count,xf_resulte from person_xf_info_table"+schema()+" where xf_resulte='"+xfResults[i]+"' and oid='"+a01.getA0000()+"' order by xf_createdate";
			String xfjbsql = "select d,jbnrzl,cljg from djdxf where cljg='"+xfResults[i]+"' and zhujian='"+a01.getA0000()+"' order by y desc";
			System.out.println(xfjbsql);
			List<Object[]> xfjb = sess.createSQLQuery(xfjbsql).list();
			if(xfjb.size()!=0){
				xfjbs.add(xfjb);
			}
		}
	}
	//个人重大事项(月度重大事项报告有关情况)
	List<Object[]> grzdsx = new ArrayList();
	if (a01 != null&&zdsxflag) {
		String grzdsxsql = "select acceptdate,code_value.code_name,content from ZDSX_PERSONALRPT"+schema()+"  left join code_value"+schema()+" on code_value = itemtype and   code_type='ZDSX002'   where psnkey='"
				+ a01.getA0000() + "' and acceptdate is not null order by acceptdate";
		System.out.print(grzdsxsql);
		List<Object[]> grzdsxs=sess.createSQLQuery(grzdsxsql).list();
		if(grzdsxs.size()!=0){
			grzdsx = grzdsxs;
		}
	}
	//个人有关事项(个人有关事项报告检查情况)
	List<Object[]> grygsx = new ArrayList();
	if (a01 != null&&ygsxflag) {
		/*String grygsxsql = "select acceptdate,reportyear,"+
				"(select code_name from "+schema()+"code_value where code_type='YGSX001' and code_value=UNITTYPE),"
		+"(select code_name from "+schema()+"code_value where code_type='YGSX002' and code_value=ITEMTYPE) from "+schema()+"YGSX_PERSONALRPT where psnkey='"
				+ a01.getA0000() + "' and acceptdate is not null order by acceptdate desc";
		List<Object[]> grygsxs=sess.createSQLQuery(grygsxsql).list();
		if(grygsxs.size()!=0){
			grygsx = grygsxs;
		}*/
	}

	//经济	
	List<Object[]> jjzr = null;
	if (a01 != null && jjflag) {
		String jjzrsql = " select  ndjjzrsj.sjsj  sjsj,nvl(cv1.code_name,ndjjzrsj.sjlb) sjlb,nvl(cv2.code_name ,ndjjzrsj.sjjl) sjjl,  ndjjzrsj.zywtzy  zywtzy from ndjjzrsj"+schema()+"  "
				+ " left join code_value"+schema()+" cv1 on cv1.code_value = ndjjzrsj.sjlb and   cv1.code_type = 'NDJJ001' "
				+ " left join code_value"+schema()+" cv2 on cv2.code_value = ndjjzrsj.sjlb and   cv2.code_type = 'NDJJ002' "
				+ "   where ndjjzrsj.SFZ = '" + a01.getA0184() + "' " + "   order by ndjjzrsj.creatordate ";
		jjzr = sess.createSQLQuery(jjzrsql).list();
	}

	//因公出国
	List<Object[]> ygcg = new ArrayList();
	if (a01 != null&&cgflag) {
		String ygcgsql = "select  country, /* substr(abroaddate,0,4)||'.'|| substr(abroaddate,5,2)||'.'||substr(abroaddate,7,2) */ abroaddate,residencetime,incident from abroad_duty"+schema()+" where 1=1  and a0000='"
				+ a01.getA0000() + "' order by abroaddate desc";
		List<Object[]> ygcgs=sess.createSQLQuery(ygcgsql).list();
		if(ygcgs.size()!=0){
			ygcg = ygcgs;
		}
	}

	//因私出国
	List<Object[]> yscg = new ArrayList();
	if (a01 != null&&cgflag) {
		String yscgsql = "select  country, /* substr(abroaddate,0,4)||'.'|| substr(abroaddate,5,2)||'.'||substr(abroaddate,7,2) */ abroaddate,residencetime,incident from abroad_intimate"+schema()+" where 1=1  and a0000='"
				+ a01.getA0000() + "' order by abroaddate desc";
		List<Object[]> yscgs=sess.createSQLQuery(yscgsql).list();
		if(yscgs.size()!=0){
			yscg = yscgs;
		}
	}
	
	//选人用人检查
	List<Object[]> xryr = new ArrayList();
	/*if (a01 != null&&xryrflag) {
		String xryrsql = "SELECT (select B0101 from b01 where b0111=ACCEPTDEPT) jcdept, to_char(ACCEPTDATE, 'yyyy.mm.dd') jcdate,"
			     +"  (select filename from "+schema()+"XRYRFILE where id=fileid) jcfilename, (select fileurl from "+schema()+"XRYRFILE where id=fileid) jcfileurl"
			     +"  FROM "+schema()+"XRYR_INSPECT WHERE DELETEFLAG=0 and psnkey='" + a01.getA0000() + "' "
		         +"  order by ACCEPTDATE asc";
		List<Object[]> xryrs=sess.createSQLQuery(xryrsql).list();
		if(xryrs.size()!=0){
			xryr = xryrs;
		}
	}*/
	//担当作为
	List<Object[]> ddzw = new ArrayList();
	if (a01 != null&&ddzwflag) {
		String ddzwsql = "SELECT  to_char(ACCEPTDATE, 'yyyymmdd') fxsj,"
			     +" MATTERS sy, MEASURE clcs"
			     +"  FROM DDZW_ACT"+schema()+" WHERE DELETEFLAG=0 and psnkey='" + a01.getA0000() + "' "
		         +"  order by ACCEPTDATE asc";
		List<Object[]> ddzws=sess.createSQLQuery(ddzwsql).list();
		if(ddzws.size()!=0){
			ddzw = ddzws;
		}
	}
	//年度经济责任审计
	List<Object[]> ndjjzr = new ArrayList();
	if (a01 != null&&jjflag) {
		String ndjjzrsql = "select nd,srzw,bdcdw,"
				+ "(select filename from JJZRSJFILE"+schema()+" where id=sjbg) sjbgname, (select code_name from code_value"+schema()+" where code_type='NDJJ002' and code_value=sjjl) SJJL"
				+ " from ndjjzrsj"+schema()+" where deleteflag=0 and SFZ='" + a01.getA0184() + "' "
				 +"  order by CREATORDATE asc";
		System.out.println(ndjjzrsql);
		List<Object[]> ndjjzrs=sess.createSQLQuery(ndjjzrsql).list();
		if(ndjjzrs.size()!=0){
			ndjjzr =ndjjzrs;
		}
	}
	//离任经济事项交接
		List<Object[]> lrjjsx = new ArrayList();
	if (a01 != null&&jjflag) {
		String lrjjsxsql = "select DWMC,LRLDZW,JRLDXM,"
				+ "(select filename from JJZRSJFILE"+schema()+" where id=jjfjcl) jjfjname, (select fileurl from JJZRSJFILE"+schema()+" where id=jjfjcl) jjfjurl "
				+ " from LRJJSXJJ"+schema()+" where deleteflag=0 and LRLDSFZ='" + a01.getA0184() + "' "
				 +"  order by CREATORDATE asc";
		List<Object[]> lrjjsxs=sess.createSQLQuery(lrjjsxsql).list();
		if(lrjjsxs.size()!=0){
			lrjjsx = lrjjsxs;
		}
	}
	
	//听取纪委反馈意见（意见审核）
		List<Object[]> yjsh = new ArrayList();
		if (a01 != null&&jjflag) {
			String yjshsql = "select (select rolename from smt_role"+schema()+" where roleid=creatordept) tjdept,jwopinion, replace(to_char(jwfeedbacktime,'yyyy/mm/dd'),'/','') jwfeedbacktime from jw_checkopinion"+schema()+" where psnkey='"+a01.getA0000()+"'"
					 +" and deleteflag=0  order by createtime desc";
			List<Object[]> yjsh1=sess.createSQLQuery(yjshsql).list();
			if(yjsh1.size()!=0){
				yjsh = yjsh1;
			}
			System.out.println(yjshsql);
		}
		//听取纪委反馈意见（其他）
		List<Object[]> qt = new ArrayList();
		if (a01 != null&&jjflag) {
			String qtsql = "select (select rolename from smt_role"+schema()+" where roleid=creatordept) tjdept,jwopinion,state from jw_checkopinion"+schema()+" where DELETEFLAG=0 and psnkey='"+a01.getA0000()+"'"
						+"  order by createtime desc";
			List<Object[]> qt1=sess.createSQLQuery(qtsql).list();
			if(qt1.size()!=0){
				qt = qt1;
			}
		}
%>
<script type="text/javascript">
</script>
