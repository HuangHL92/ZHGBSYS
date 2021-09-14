<%@page import="com.insigma.siis.local.business.slabel.TagDazsbfj"%>
<%@page import="com.insigma.siis.local.business.slabel.TagNdkhdjbfj"%>
<%@page import="com.insigma.siis.local.business.slabel.TagKcclfj2"%>
<%@page import="com.insigma.siis.local.util.StringUtil"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.AddZHGBRmbPageModel"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.insigma.siis.local.business.helperUtil.IdCardManageUtil"%>
<%@page import="com.insigma.siis.local.business.helperUtil.DateUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.UUID"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.siis.local.business.entity.LogMain"%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<%@page import="com.insigma.siis.local.business.entity.A01"%>
<%@page import="com.insigma.siis.local.business.entity.A15"%>
<%@page import="com.insigma.siis.local.business.entity.A33"%>
<%@page import="com.insigma.siis.local.business.entity.A99Z1"%>
<%@page import="com.insigma.siis.local.business.entity.extra.ExtraTags"%>
<%@page import="com.insigma.siis.local.business.entity.DBWY"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%!private String SV(String v){
	return v==null?"":StringEscapeUtils.escapeHtml(v);
}
private String SVNEW(Object v){
	return v==null?"":(("0".equals(v.toString())) ||  ("0.0".equals(v.toString()) || "0.00".equals(v.toString())) ?"":v.toString());
}
%>
<%
	String ctxPath = request.getContextPath(); 
	String username = "";
	try{
		username = SysManagerUtils.getUserName(); 	//操作用户（此处为用户登录名）。
	}catch(Exception e){
		out.println("<script>alert('登录超时！');parent.window.location='"+ctxPath+"/LogonDialog.jsp'</script>");
		return;
	}
	
	
	String RrmbCodeType = (String)session.getAttribute("RrmbCodeType");
	//RrmbCodeType = CodeType2js.getRrmbCodeType();
	if(RrmbCodeType==null){
		RrmbCodeType = CodeType2js.getRrmbCodeType();
		session.setAttribute("RrmbCodeType",RrmbCodeType);
	}
	
	HBSession sess = HBUtil.getHBSession();
	String setTitle = "";
	
	String a0000 = request.getParameter("a0000");
	String checkedgroupid = request.getParameter("checkedgroupid");
	a0000 = URLDecoder.decode(a0000,"UTF8");
	String signTemp = request.getParameter("signTemp");
	
	//在浏览机构树的情况下，判断查询的该人员的在任职务所在单位，是否有编辑权限，有编辑权限，既可编辑；若没有编辑权限，做所有信息项的控制
	String userid = SysManagerUtils.getUserId();
	//String lookOrWrite = request.getParameter("lookOrWrite");
	String sign = "";
	if(!"40288103556cc97701556d629135000f".equals(userid)){
		//在任 并 输出 可以修改人员信息；主职务 已免的单位也能修改人员信息
		String lookSql="";
		//如果是增加信息，则不做这个权限判断
		if(!"add".equals(signTemp)){
		 lookSql = "select c.userdeptid from competence_userdept c where c.b0111 in (select a02.a0201b from a02 where "+
				"a02.a0000 = '"+a0000+"' and a02.a0281 = 'true') and "+
				"c.userid = '"+userid+"'";
		List b0111s = sess.createSQLQuery(lookSql).list();
		if(b0111s!=null&&b0111s.size()>0){
			sign = "write";
		}else{
			sign = "look";
		}
		}
		//当sign = "look"时，再判断改人员是否属于无职务人员，如果是则可以编辑
		if("look".equals(sign)){
			lookSql = "select a01.a0000 from A01 a01 where 1 = 1  and not exists (select 1 from a02 "+
					"where a02.a0000 = a01.a0000 and a0281 = 'true' and a0201b in (select b0111 from b01 where b0111 != '-1')) and (a01.status != '4' or a01.status is null) "+
					" and a01.a0000 = '"+a0000+"'";
			Object obj = sess.createSQLQuery(lookSql).uniqueResult();
			if(obj!=null&&!"".equals(obj)){
				sign = "write";
			}
		}
		
		/* //若sign还是"look"，再判断改人员是否属于无主职务或主职务已免人员，如果是则可以编辑
		if("look".equals(sign)){
			lookSql = "select a01.a0000 from A01 a01 where 1 = 1  and fkly is null and (not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0279 = '1') or A0000  in  (select A02.A0000  from  A02  where  A02.A0279 = '1' and A02.A0255 = '0' ))"+
					" and (a01.status != '4' or a01.status is null) "+
					" and a01.a0000 = '"+a0000+"'";
			Object obj = sess.createSQLQuery(lookSql).uniqueResult();
			if(obj!=null&&!"".equals(obj)){
				sign = "write";
			}
		} */
		if("look".equals(sign)){
			out.println("<script>alert('您没有访问权限！');parent.window.location='"+ctxPath+"/LogonDialog.jsp'</script>");
			return;
		}
	}

	//查询COMPETENCE_USERSMTBUSINESS表，看当前用户是否有查看  家庭成员  的权限
	List businessids = sess.createSQLQuery("select c.businessid from COMPETENCE_USERSMTBUSINESS c where c.userid = '"+userid+"'").list();
	boolean familySign = false;
	if("40288103556cc97701556d629135000f".equals(userid)){
		familySign = true;
	}else if(businessids!=null&&businessids.size()>0){
		for(Object obj : businessids){
			if("01".equals(obj)&&"write".equals(sign)){
				
				familySign = true;
			}
		}
	}
	
	
	String sql = "from A01 where a0000='"+a0000+"'";
	List list = sess.createQuery(sql).list();
		
	String fromModules = request.getParameter("FromModules");
	
	
	A01 a01 = null;
	A15 a15 = null;
	if(list==null||list.size()==0){
		a01 = new A01();
		a01.setA0000(a0000);
		a01.setA0163("1");//默认现职人员
		a01.setA14z101("无");//奖惩描述
		a01.setStatus("4");
		a01.setA0197("0");//基层工作经历时间两年以上
		a01.setTbr(SysManagerUtils.getUserId());
		a01.setTbsj(DateUtil.getTimestamp().getTime());
		a01.setA0155(DateUtil.getTimestamp().toString());
		a01.setA0128("健康");
		sess.save(a01);
		sess.flush();
	}else{
		a01 = (A01) list.get(0);
		List A15Z101 = sess.createSQLQuery("select t.ischange from competence_usertablecol t where col_code = 'A15Z101' and t.userid in ( "+
                " select a.roleid from smt_act a where "+
				" a.userid = '" + userid + "' )  order by t.ischange desc").list();
		if (A15Z101.size() > 0 && A15Z101.get(0).equals("0")) {
			String A0000 = a01.getA0000();
			String sql1 = "from A15 where a0000='" + A0000 + "' order by a1521 asc";
			List<A15> list2 = HBUtil.getHBSession().createQuery(sql1).list();
			if (list2 != null && list2.size() > 0) {
				int years = "".equals(3) ? list2.size() : Integer.valueOf(3);
				if (years > list2.size()) {
					years = list2.size();
				}
				StringBuffer desc = new StringBuffer();
				for (int i = list2.size() - years; i < list2.size(); i++) {
					a15 = list2.get(i);
					//考核年度
					String a1521 = a15.getA1521();
					//考核结果
					String a1517 = a15.getA1517();
					String a1517Name = HBUtil.getCodeName("ZB18", a1517);
					if (a1517Name.equals("不定等次")) {
						desc.append(a1521 + "年年度考核" + a1517Name + "；");
					} else {
						desc.append(a1521 + "年年度考核“" + a1517Name + "”等次；");
					}
				}
				if(desc.length()>0){
					desc.replace(desc.length()-1, desc.length(), "。");
				}
				a01.setA15z101(desc.toString());
			}
		}
		
		
		
		
		
		
		
		
		setTitle = AddZHGBRmbPageModel.setTitle(a01);
	}
	
	
	
	
	
	
	String a0195Text = null;
	if(a01.getA0195()!=null){
		a0195Text = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");
		
		if(a0195Text == null || a0195Text.equals("")){
			a0195Text = a01.getA0195();
		}
	}
	
	String a0163Text = null;
	if(a01.getA0163()!=null){
		a0163Text = HBUtil.getValueFromTab("code_name", "code_value", " code_type='ZB126' and code_value='"+a01.getA0163()+"'");
		if(a0163Text==null||"".equals(a0163Text)){
			a0163Text = a01.getA0163();
		}
	}
	
	//家庭成员
	String sqla36 = "from A36 where a0000='"+a0000+"' order by sortid,case " + 
			"        when A3604A='丈夫' or A3604A='妻子' then 1 " + 
			"        when A3604A='儿子' or A3604A='女儿'or A3604A='长女'or A3604A='长子' or A3604A='次女' or A3604A='次子' or A3604A='三女' or A3604A='三子' or A3604A='四女' or A3604A='四子' or A3604A='五女' or A3604A='五子' or A3604A='其他女儿' or A3604A='其他子' then 2 " + 
			"        when A3604A='父亲'  then 3 " + 
			"        when A3604A='母亲'  then 4 " + 
			"        when A3604A='继父'  then 5 " + 
			"        when A3604A='继母'  then 6 " + 
			"      end   , " + 
 			" case "+
            "    when "+
            "      A3604A='儿子' or A3604A='女儿' or A3604A='长女'  or "+
            "      A3604A='长子' or A3604A='次女' or A3604A='次子'  or "+
            "      A3604A='三女' or A3604A='三子' or A3604A='四女'  or "+
            "      A3604A='四子' or A3604A='五女' or A3604A='五子'  or "+
            "      A3604A='其他女儿' or A3604A='其他子' "+
	        "    then   "+
	        "      to_number(GETAGE(A3607)) "+
            "    end"+
            "        desc";
	List lista36 = sess.createQuery(sqla36).list();
	int lista36Length = lista36.size();
	
	//补充信息集 
	String sqlaA99Z1 = "from A99Z1 where a0000='"+a0000+"'";
	List listA99Z1 = sess.createQuery(sqlaA99Z1).list();
	
	A99Z1 a99Z1 = null;
	if(listA99Z1==null||listA99Z1.size()==0){
		//a0000 = UUID.randomUUID().toString();
		a99Z1 = new A99Z1();
	}else{
		a99Z1 = (A99Z1) listA99Z1.get(0);
	}
	
	
	String dbwysql = "from DBWY where a0000='"+a01.getA0000()+"'";
	List dbwylist = HBUtil.getHBSession().createQuery(dbwysql).list();
	DBWY dbwy=null;
	if(dbwylist==null||dbwylist.size()==0){
		dbwy = new DBWY();
	}else{
		dbwy = (DBWY) dbwylist.get(0);
	}
	request.setAttribute("dbwy", dbwy);
	//补充标签信息集
	String sqlet = "from ExtraTags where a0000='"+a0000+"'";
	List listet = sess.createQuery(sqlet).list();
	
	ExtraTags extraTags = null;
	if(listet==null||listet.size()==0){
		extraTags = new ExtraTags();
	}else{
		extraTags = (ExtraTags) listet.get(0);
	}
	
	request.setAttribute("a01", a01);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//补充标签信息集
		String szsqlet = "from com.insigma.siis.local.business.slabel.ExtraTags where a0000='"+a0000+"'";
		List szlistet = sess.createQuery(szsqlet).list();
				
		com.insigma.siis.local.business.slabel.ExtraTags szextraTags = null;
		if(szlistet==null||szlistet.size()==0){
			szextraTags = new com.insigma.siis.local.business.slabel.ExtraTags();
		}else{
			szextraTags = (com.insigma.siis.local.business.slabel.ExtraTags) szlistet.get(0);
			if( szextraTags.getTagzybj() != null && !"".equals(szextraTags.getTagzybj())){
				szextraTags.setComboxArea_tagzybj(HBUtil.getCodeName("TAGZYBJ", szextraTags.getTagzybj()));
			}
			if( szextraTags.getTagzc() != null && !"".equals(szextraTags.getTagzc())){
				szextraTags.setComboxArea_tagzc(HBUtil.getCodeName("TAGZC", szextraTags.getTagzc()));
			}
		}
				
		// 考察材料  附件
		String sqlTagKcclfj2 = "from com.insigma.siis.local.business.slabel.TagKcclfj2 where a0000='" + a0000 + "' order by updatedate desc";
		List tagKcclfj2List = sess.createQuery(sqlTagKcclfj2).list();

		TagKcclfj2 tagKcclfj20 = null;
		TagKcclfj2 tagKcclfj21 = null;
		if (null == tagKcclfj2List || 0 == tagKcclfj2List.size()) {
			tagKcclfj20 = new TagKcclfj2();
			tagKcclfj21 = new TagKcclfj2();
		} else if(1 == tagKcclfj2List.size()){
			tagKcclfj20 = (TagKcclfj2) tagKcclfj2List.get(0);
			tagKcclfj21 = new TagKcclfj2();
		} else if(2 <= tagKcclfj2List.size()){
			tagKcclfj20 = (TagKcclfj2) tagKcclfj2List.get(0);
			tagKcclfj21 = (TagKcclfj2) tagKcclfj2List.get(1);
		} else {
			tagKcclfj20 = new TagKcclfj2();
			tagKcclfj21 = new TagKcclfj2();
		}
				
		// 年度考核登记表  附件
		String sqlTagNdkhdjbfj = "from com.insigma.siis.local.business.slabel.TagNdkhdjbfj where a0000='" + a0000 + "' order by updatedate desc";
		List tagNdkhdjbfjList = sess.createQuery(sqlTagNdkhdjbfj).list();

		TagNdkhdjbfj tagNdkhdjbfj0 = null;
		TagNdkhdjbfj tagNdkhdjbfj1 = null;
		if (null == tagNdkhdjbfjList || 0 == tagNdkhdjbfjList.size()) {
			tagNdkhdjbfj0 = new TagNdkhdjbfj();
			tagNdkhdjbfj1 = new TagNdkhdjbfj();
		} else if(1 == tagNdkhdjbfjList.size()){
			tagNdkhdjbfj0 = (TagNdkhdjbfj) tagNdkhdjbfjList.get(0);
			tagNdkhdjbfj1 = new TagNdkhdjbfj();
		} else if(2 <= tagNdkhdjbfjList.size()){
			tagNdkhdjbfj0 = (TagNdkhdjbfj) tagNdkhdjbfjList.get(0);
			tagNdkhdjbfj1 = (TagNdkhdjbfj) tagNdkhdjbfjList.get(1);
		} else {
			tagNdkhdjbfj0 = new TagNdkhdjbfj();
			tagNdkhdjbfj1 = new TagNdkhdjbfj();
		}
				
		// 档案专审表  附件
		String sqlTagDazsbfj = "from com.insigma.siis.local.business.slabel.TagDazsbfj where a0000='" + a0000 + "' order by updatedate desc";
		List tagDazsbfjList = sess.createQuery(sqlTagDazsbfj).list();

		TagDazsbfj tagDazsbfj0 = null;
		TagDazsbfj tagDazsbfj1 = null;
		if (null == tagDazsbfjList || 0 == tagDazsbfjList.size()) {
			tagDazsbfj0 = new TagDazsbfj();
			tagDazsbfj1 = new TagDazsbfj();
		} else if(1 == tagDazsbfjList.size()){
			tagDazsbfj0 = (TagDazsbfj) tagDazsbfjList.get(0);
			tagDazsbfj1 = new TagDazsbfj();
		} else if(2 <= tagDazsbfjList.size()){
			tagDazsbfj0 = (TagDazsbfj) tagDazsbfjList.get(0);
			tagDazsbfj1 = (TagDazsbfj) tagDazsbfjList.get(1);
		} else {
			tagDazsbfj0 = new TagDazsbfj();
			tagDazsbfj1 = new TagDazsbfj();
		}	
	
	
	
	
	
	
	
%>


<script type="text/javascript">
var ctxPath = '<%=ctxPath%>';
<%=RrmbCodeType%>

var sign_js='<%=sign%>';

/* var realParent = window.dialogArguments.window;
var parent = window.dialogArguments.window;
var gridName = window.dialogArguments.wincfg.gridName; */
var realParent = window.opener;
var parent = window.opener;
if(!realParent){
	realParent = window.dialogArguments.window;
	parent = realParent;
}


//var gridName = parent.Ext.getCmp("personInfoOP").initialConfig.gridName;


function tabSwitch(tabname, content_prefix, active) {
	
	if(active==1){
		$('.hzdbInfo').scrollTop(
				0
			);
		
	}else{
		$('.hzdbInfo').scrollTop(
				//$("#tabs" + active+"tab").offset().top - $('.hzdbInfo').offset().top + $('.hzdbInfo').scrollTop()
				$("#" +content_prefix+ active).offset().top - $('.hzdbInfo').offset().top + $('.hzdbInfo').scrollTop()
			);
	}
	
	for (var i = 1; i <= 6; i++) {
		$("#tabs" + i).removeClass('active');
	}
	
	$("#tabs" + active).addClass('active');
	return;
	for (var i = 1; i <= 6; i++) {
		$("#tabs" + i).removeClass('active');
		$("#page" + i).css('display', 'none');
	}
	$("#tabs" + active).addClass('active');
	if (active == 3 || active == 4 || active == 5|| active == 6) {
		$("#table_div").css('display', 'none');
	} else {
		$("#table_div").css('display', 'block');
	}

	$("#page" + active).css('display', 'block');
	document.getElementById("tabIndex").value = active;
}


function lookdata(sign) {
	if (sign == 'look') {
		$('p').removeAttr('ondblclick');
		$('p').removeAttr('onclick');
		$('p').removeAttr('onfocus');

		$('textarea').removeAttr('ondblclick');
		$('textarea').removeAttr('onclick');
        $('textarea').removeAttr('onfocus');

        $('#create').removeAttr('onclick');
        $('#formatting').removeAttr('onclick');
        // window.frames["ueditor_0"].document.getElementById("bodyContent").disabled="disabled";
    }

}
</script>
<!-- 关键词变量 -->
<%
String sid = request.getSession().getId();
String a1701_keyWords = (String)sess.createSQLQuery("select k.value from KEYWORDS k where k.sid = '"+sid+"' and k.a0000 = '"+a0000+"' and k.name = 'a1701_key'").uniqueResult();
System.out.println(a1701_keyWords);
if(StringUtil.isEmpty(a1701_keyWords))
	a1701_keyWords="";
%>