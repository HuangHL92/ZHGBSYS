<%@page import="java.util.UUID"%>
<%@page import="com.insigma.siis.local.business.entity.extra.ExtraTags"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.pagemodel.comm.CommQuery"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%-- <%String ctxPaths = request.getContextPath();%>
<link rel="stylesheet" type="text/css" href="<%=ctxPaths%>/pages/publicServantManage/personInfo/widthConfig.css"/>								  
<link rel="stylesheet" type="text/css" href="<%=ctxPaths%>/pages/publicServantManage/personInfo/heightConfig.css"/>								  
<link rel="stylesheet" type="text/css" href="<%=ctxPaths%>/pages/publicServantManage/personInfo/PersonInfo.css"/>  --%>

<%String ctxPath = request.getContextPath(); 
String sign = request.getParameter("sign");
String a0000 = request.getParameter("a0000");
//补充标签信息集
	CommQuery commQuery =new CommQuery();
	String sqlet = "select*from ZJS_EXTRA_TAGS where a0000='"+a0000+"'";
	List<HashMap<String, Object>> listet = commQuery.getListBySQL(sqlet);
	
	if(listet==null||listet.size()==0){
		
		HBUtil.executeUpdate("insert into ZJS_EXTRA_TAGS (a0000) values ('"+a0000+"')");
	}else{
		String a1401b=listet.get(0).get("a1401b")==null?" ":listet.get(0).get("a1401b").toString();
		String a1401c=listet.get(0).get("a1401c")==null?" ":listet.get(0).get("a1401c").toString();
		String a1401m=listet.get(0).get("a1401m")==null?" ":listet.get(0).get("a1401m").toString();
		String a1401n=listet.get(0).get("a1401n")==null?" ":listet.get(0).get("a1401n").toString();
	}
	String sqlet1= "select*from EXTRA_TAGS where a0000='"+a0000+"'";
	List<HashMap<String, Object>> listet1 = commQuery.getListBySQL(sqlet1);
	if(listet1==null||listet1.size()==0){
		
		HBUtil.executeUpdate("insert into EXTRA_TAGS (a0000) values ('"+a0000+"')");
	}else{
		String a1401a=listet1.get(0).get("a1401a")==null?" ":listet1.get(0).get("a1401a").toString();
		String a1401h=listet1.get(0).get("a1401h")==null?" ":listet1.get(0).get("a1401h").toString();
	}
	//int hb=HBUtil.executeUpdate("insert into PJTAGS (ID) values ('"+a0000+"')");
	int hb=HBUtil.executeUpdate("select * from PJTAGS where ID='"+a0000+"'");
	if(hb==0){
		HBUtil.executeUpdate("insert into PJTAGS (ID) values ('"+a0000+"')");
	}
	String sql= "select * from PJTAGS where ID='"+a0000+"'";
	List<HashMap<String, Object>> data = commQuery.getListBySQL(sql);
	if(data==null||data.size()==0){
		HBUtil.executeUpdate("insert into PJTAGS (ID) values ('"+a0000+"')");
	}else{
		String a1401b=data.get(0).get("a1401b")==null?" ":data.get(0).get("a1401b").toString();
		String a1401c=data.get(0).get("a1401c")==null?" ":data.get(0).get("a1401c").toString();
		String a1401d=data.get(0).get("a1401d")==null?" ":data.get(0).get("a1401d").toString();
		String a1401f=data.get(0).get("a1401f")==null?" ":data.get(0).get("a1401f").toString();
		String a1401p=data.get(0).get("a1401p")==null?" ":data.get(0).get("a1401p").toString();
		String a1401m=data.get(0).get("a1401m")==null?" ":data.get(0).get("a1401m").toString();
		String a1401n=data.get(0).get("a1401n")==null?" ":data.get(0).get("a1401n").toString();
	}
	String A99Z100 = UUID.randomUUID().toString().replace("-", "");
	int a99z=HBUtil.executeUpdate("select * from a99Z1 where a0000='"+a0000+"'");
	if(a99z==0){
		HBUtil.executeUpdate("insert into a99Z1 (a0000,A99Z100) values ('"+a0000+"','"+A99Z100+"')");
	}
	
	String sqlA99z1= "select * from a99Z1 where a0000='"+a0000+"'";
	List<HashMap<String, Object>> A99Z1Data = commQuery.getListBySQL(sqlA99z1);
	if(A99Z1Data==null||A99Z1Data.size()==0){
		HBUtil.executeUpdate("insert into a99Z1 (a0000,A99Z100) values ('"+a0000+"','"+A99Z100+"')");
	}else{
		String a99z1304=A99Z1Data.get(0).get("a99z1304")==null?" ":A99Z1Data.get(0).get("a99z1304").toString();
	
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
.div-c{ float:left;width:49%;border:1px solid #F00} 
.div-d{ float:right;width:49%;border:1px solid #000} 
body {
	background-color: rgb(214,227,243);
}
.div_left {
	float:left;
	position: relative;
	width: 49%;
}
.boxes{
font-size:16px; 
//font-weight:bold;
}
#tag_container01 {
	position: relative;
	width: 100%;
	height: 100px;
	border-width: 0;
	border-style: solid;
	border-color: #74A6CC;
	margin-top: 10px;
}

#tag_container01 .tag_div {
	position: relative;
	height: 100%;
	float: left;
	margin-left: 2%;
}

#tag_info_div01 {
	position: relative;
	width: 100%;
}
#tag_info_div01 #a0196z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 {
	position: relative;
	width: 100%;
}
#tag_info_div02 #a0196c {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}

#tag_info_div02 #sza0193z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #sza0194z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a99 {
	width: 98%;
	margin-left: 20px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}

#tag_info_div02 #a98 {
	width: 98%;
	margin-left: 20px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}

#bottom_div01 {
	position: relative;
	width: 100%;
	height: 40px;
	margin-top: 5px;
}
#tag_info_div02 #a1401b {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a1401g {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a1401h {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a1401c {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a1401d {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a1401f {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a1401p {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a1401k {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a1401l {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a1401m {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a1401n {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a99z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a1401g {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a99z1304 {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}

div.pgHead {
	margin: 2px 0 5px 0;
	font-size: 20px;
	font-weight: bold;
	line-height: 24px;
	text-align: left;
	text-indent: 6px;
	letter-spacing: 1px;
	background-color: #C0DCF1;
}

table{
width: 100%
}

</style>
<div id="page2">
	<div class="pgHead" >总体评价</div>
	<div class="div_left">
		<div >&nbsp;&nbsp;主要优点:</div>
		<div id="tag_info_div02">
			<textarea rows="8" cols="113" id="a1401k" name="a1401k" class="boxes"><%=data.get(0).get("a1401k")==null?" ":data.get(0).get("a1401k").toString()%></textarea>
		</div>
	</div>
	<div class="div_left">
		<div >&nbsp;&nbsp;&nbsp;主要不足:</div>
		<div id="tag_info_div02">
			<textarea rows="8" cols="113" id="a99z" name="a99z" class="boxes"><%=A99Z1Data.get(0).get("a99z1304")==null?" ":A99Z1Data.get(0).get("a99z1304").toString()%></textarea>
		</div>
	</div>
	<div class="div_left">
		<div >&nbsp;&nbsp;一言素描:</div>
		<div id="tag_info_div02">
			<textarea rows="8" cols="113" id="a1401f" name="a1401f" class="boxes"><%=data.get(0).get("a1401f")==null?" ":data.get(0).get("a1401f").toString()%></textarea>
		</div>
	</div>
	<div class="div_left">
		<div >&nbsp;&nbsp;政治素质:</div>
		<div id="tag_info_div02">
			<textarea rows="8" cols="113" id="a1401p" name="a1401p" class="boxes"><%=data.get(0).get("a1401p")==null?" ":data.get(0).get("a1401p").toString()%></textarea>
		</div>
	</div>
	
	
	
	
	<div style="width: 100%;clear: both;"></div>
	<div class="pgHead">重要经历熟悉领域</div>
	<div class="div_left">
		<div>&nbsp;&nbsp;重要经历:</div>
		<div id="tag_info_div02">
			<textarea id="sza0193z" name="sza0193z" rows="3" cols="113"  readonly="readonly" ondblclick="rzzyjlClick();" ><%=listet.get(0).get("a1401b")==null?" ":listet.get(0).get("a1401b").toString()%></textarea>
		</div>
	</div>
	<div class="div_left">
		<div>&nbsp;&nbsp;熟悉领域:</div>
		<div id="tag_info_div02">
			<textarea id="sza0194z" name="sza0194z" rows="3" cols="113" readonly="readonly" ondblclick="sxlyClick();" ><%=listet.get(0).get("a1401c")==null?" ":listet.get(0).get("a1401c").toString()%></textarea>
		</div>
	</div>
	
	<div style="width: 100%;clear: both;"></div>
	<div class="pgHead">&nbsp;&nbsp;使用方式建议:</div>
	<odin:hidden property="a0196"/>
	<div class="div_left">
		<div ></div>
		<!-- <div class="div_myleft" style="padding-left: 11px;">
			 <table>
			  <tr>
			  	 <td><input type="checkbox"  name="attr210" id="myattr210" class="marginLeft20"/><label  class="attr19" for="attr16">本地本单位提拔使用</label></td>
			  </tr>
			  <tr>
			  	 <td><input type="checkbox"  name="attr212" id="myattr212" class="marginLeft20"/><label  class="attr19" for="attr17">交流到区县(市),高校,国企使用 </label></td>
			  </tr>
			   <tr>
			  	 <td><input type="checkbox"  name="attr213" id="myattr213" class="marginLeft20"/><label  class="attr19" for="attr18">交流到市党委工作部门,群众团体,公检法.经济综合<br/>城建交通,宣传文化,行政执法,农林水等部门使用</label></td>
			  </tr>
			   <tr>
			  	 <td><input type="checkbox"  name="attr214" id="myattr214" class="marginLeft20"/><label class="attr19" for="attr19">现岗位压担锻炼,轮岗交流,党校培训,下派锻炼等</label></td>
			  </tr>
			   </table>
			  </div> -->
		<div id="tag_info_div02">
<%-- 			<textarea id="a1401l" name="a1401l" rows="8" cols="113" ><%=data.get(0).get("a1401l")==null?"":data.get(0).get("a1401l").toString()%></textarea>
 --%>			<textarea id="a1401l" name="a1401l" rows="3" cols="113"  readonly="readonly" ondblclick="a1401lClick();" ><%=data.get(0).get("a1401l")==null?"":data.get(0).get("a1401l").toString()%></textarea>
			
		</div>
	</div>
	<%-- <div class="div_left">
		<div>&nbsp;&nbsp;使用方法建议:</div>
		<div id="tag_info_div02">
			<textarea id="a1401l" name="a1401l" rows="3" cols="113"  readonly="readonly" ondblclick="a1401lClick();" ><%=listet.get(0).get("a1401l")==null?" ":listet.get(0).get("a1401l").toString()%></textarea>
		</div>
	</div> --%>
	<div style="width: 100%;clear: both;"></div>
	<div class="pgHead">口碑评价</div>
		<div class="div_left">
	    <div>&nbsp;&nbsp;上级领导评价：</div>
		<div id="tag_info_div02">
		<textarea rows="8" cols="113" id="a1401m" name="a1401m" class="boxes"><%=data.get(0).get("a1401m")==null?" ":data.get(0).get("a1401m").toString()%></textarea>
		</div>
	</div>
	<div class="div_left">
		<div>&nbsp;&nbsp;条线评价：</div>
		<div id="tag_info_div02">
			<textarea rows="8" cols="113" id="a1401n" name="a1401n"class="boxes"><%=data.get(0).get("a1401n")==null?" ":data.get(0).get("a1401n").toString()%></textarea>
		</div>
	</div>
	<div class="div_left">
	    <div>&nbsp;&nbsp;领导评价：</div>
		<div id="tag_info_div02">
		<textarea rows="8" cols="113" id="a1401b" name="a1401b" class="boxes"><%=data.get(0).get("a1401b")==null?" ":data.get(0).get("a1401b").toString()%></textarea>
		</div>
	</div>
	<div class="div_left">
		<div>&nbsp;&nbsp;班子成员评价：</div>
		<div id="tag_info_div02">
			<textarea rows="8" cols="113" id="a1401c" name="a1401c"class="boxes"><%=data.get(0).get("a1401c")==null?" ":data.get(0).get("a1401c").toString()%></textarea>
		</div>
	</div>
	<div style="width: 100%;clear: both;"></div>
	<div class="tag_info_div02">&nbsp;&nbsp;其他干部评价：</div>
	<div id="tag_info_div02">
		<textarea rows="8" cols="113" id="a1401d" name="a1401d" class="boxes"><%=data.get(0).get("a1401d")==null?" ":data.get(0).get("a1401d").toString()%></textarea>
	</div>
	
	<div style="width: 100%;clear: both;"></div>
	<div class="pgHead">工作实绩与说说心里话 </div>
	<div class="div_left">
		<div class="tag_info_div02" id="">&nbsp;&nbsp;实绩回溯：</div>
		<div id="tag_info_div02">
			<textarea rows="10" cols="113" id="a1401g" name="a1401g" class="boxes"><%=data.get(0).get("a1401g")==null?" ":data.get(0).get("a1401g").toString()%></textarea>
		</div>
	</div>
	<div class="div_left">
		<div class="tag_info_div02">&nbsp;&nbsp;说说心里话：</div>
		<div id="tag_info_div02">
			<textarea  rows="10" cols="113" id="a1401h" name="a1401h" class="boxes"><%=data.get(0).get("a1401h")==null?" ":data.get(0).get("a1401h").toString()%></textarea>
		</div>
	</div>
</div>



<%-- 
<div>&nbsp;&nbsp;&nbsp;&nbsp;领导评价</div>
<div id="tag_info_div02">
	<textarea rows="5" cols="113" id="a1401b" name="a1401b" ><%=data.get(0).get("a1401b")==null?"":data.get(0).get("a1401b").toString()%></textarea>
</div>
<br/>
<div>&nbsp;&nbsp;&nbsp;&nbsp;班子成员评价</div>
<div id="tag_info_div02">
	<textarea rows="5" cols="113" id="a1401b" name="a1401c"><%=data.get(0).get("a1401c")==null?"":data.get(0).get("a1401c").toString()%></textarea>
</div>
<br/>
<div>&nbsp;&nbsp;&nbsp;&nbsp;其他干部评价</div>
<div id="tag_info_div02">
	<textarea rows="5" cols="113" id="a1401b" name="a1401d" ><%=data.get(0).get("a1401d")==null?"":data.get(0).get("a1401d").toString()%></textarea>
</div>
<br/>
<div>&nbsp;&nbsp;&nbsp;&nbsp;一言素描</div>
<div id="tag_info_div02">
	<textarea rows="5" cols="113" id="a1401b" name="a1401f" ><%=data.get(0).get("a1401f")==null?"":data.get(0).get("a1401f").toString()%></textarea>
</div> --%>
<br/>
<odin:hidden property="a0000" title="人员主键"/>
<odin:hidden property="a0196s"/>
<div style="width: 100%;clear: both;"></div>
<div class="pgHead">其他</div>
<div id="tag_container01">
	<div class="tag_div">
		<table>
		  <tr>
		    <td><input type="checkbox" name="attr101" id="attr101" class="marginLeft20"/><label for="attr41">党务类 </label></td>
		    <td><input type="checkbox" name="attr102" id="attr102" class="marginLeft20"/><label for="attr42">综合管理类 </label></td>
		    <td><input type="checkbox" name="attr103" id="attr103" class="marginLeft20"/><label for="attr43">制造业和工业经济类 </label></td>
		  </tr>
		  <tr>
		  	<td><input type="checkbox" name="attr104" id="attr104" class="marginLeft20"/><label for="attr44">大数据和信息技术类</label></td>
		  	<td><input type="checkbox" name="attr105" id="attr105" class="marginLeft20"/><label for="attr45">城建城管类 </label></td>
		  	<td><input type="checkbox" name="attr106" id="attr106" class="marginLeft20"/><label for="attr46">教育卫生类 </label></td>
		  </tr>
		  <tr>
		  	<td><input type="checkbox" name="attr107" id="attr107" class="marginLeft20"/><label for="attr31">服务商贸类 </label></td>
		  	<td><input type="checkbox" name="attr108" id="attr108" class="marginLeft20"/><label for="attr47">农业农村类 </label></td>
		  	<td><input type="checkbox" name="attr109" id="attr109" class="marginLeft20"/><label for="attr48">文化发展和旅游类 </label></td>
		  </tr>
		  <tr>
		  	<td><input type="checkbox" name="attr110" id="attr110" class="marginLeft20"/><label for="attr49">公检法政法类</label></td>
		  	<td><input type="checkbox" name="attr111" id="attr111" class="marginLeft20"/><label for="attr50">企业经营管理类</label></td>
		  	<td><input type="checkbox" name="attr112" id="attr112" class="marginLeft20"/><label for="attr05">金融财务类</label></td>
		  </tr>
		</table>
	</div>
</div>
<div id="tag_info_div01">
	<textarea rows="3" cols="113" id="a0196z" name="a0196z"></textarea>
</div>
<!-- <div>&nbsp;&nbsp;专业类型备注</div>
<div id="tag_info_div02">
	<textarea rows="5" cols="113" id="a0196c" name="a0196c" class="boxes"></textarea>
</div> -->
<br/>

<br/>
<table>
<tr>
<td>
<div>&nbsp;&nbsp;担当作为好干部</div>
</td>
<td>
<div id="tag_info_div02">
	<odin:select2 property="a98" data="['1','是'],['0','否']"  value='<%=listet1.get(0).get("a1401a")==null?" ":listet1.get(0).get("a1401a").toString()%>'></odin:select2>
</div>
</td>
<%-- <td>
<div>&nbsp;&nbsp;近期可提拔干部</div>
<div id="tag_info_div02">
	<odin:select2 property="a99" data="['近期可提任市管正职','近期可提任市管正职'],['近期可提任市管副职','近期可提任市管副职']"  value='<%=listet1.get(0).get("a1401h")==null?" ":listet1.get(0).get("a1401h").toString()%>'></odin:select2>
</div>
</td> --%>
</tr>
</table>
<br/>
<%-- 
<tr style="height:50px;">
												<td>
													<table   align="left" bgcolor="white" width="90" height="100%" cellspacing="0px;">
													  <tr style="height:50px;">
														<odin:select2 property="a99z1301" codeType="PY02" width="140"  label="培养方向" multiSelect="true"   colspan="1"  styleClass="a99z191"/>
													  </tr>
													</table>
												</td>
												<td>
													<table   align="left" bgcolor="white" width="90" height="100%" cellspacing="0px;">
													  <tr style="height:50px;">
														<odin:select property="a99z1302" codeType="PY01" width="120" label="培养方式" multiSelect="true" colspan="1"  styleClass="a99z191"></odin:select>
													  </tr>
													</table>
													
												</td>

												</tr> --%>
<% 
	}
%>
<%-- <div id="bottom_div01">
	<div align="center">
		<odin:button text="保&nbsp;&nbsp;存" property="save" />
	</div>		
</div>  --%>
<table align="center" width="96%">	
			<td align="center">
				<img src="<%=request.getContextPath()%>/images/bc.png" onclick="radow.doEvent('save')">
			</td>
</table>
<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("EXTRA_TAGS",sign)%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("EXTRA_TAGS")%>;

Ext.onReady(function(){
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(fieldsDisabled); 
	//对信息集明细的权限控制，是否可以查看
	//var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
	
});

//输入框中显示选中标签
function fullContent(check,value,valuename){
	var a0196z = document.getElementById("a0196z").value;
	var a0196s = document.getElementById("a0196s").value;
	if($(check).is(':checked')) {
		
		if( a0196z == null || a0196z == '' ){
			a0196z = valuename;
		}else{
			a0196z = a0196z + "" + valuename;
		}	
		if( a0196s == null || a0196s == '' ){
			a0196s = value;
		}else{
			a0196s = a0196s  + "，" + value;
		}			
	}else{
		a0196z = a0196z.replace('，'+valuename, '').replace(valuename+'，', '').replace(valuename, '');
		a0196s = a0196s.replace('，'+value, '').replace(value+'，', '').replace(value, ''); 
	/* 	a0196z = a0196z.replace(valuename+'；', '').replace(valuename, '');
		a0196s = a0196s.replace(value+'；', '').replace(value, ''); */
	}
	document.getElementById("a0196z").value = a0196z;
	document.getElementById("a0196s").value = a0196s;
	//alert(a0196s);
	
}


	//输入框中显示选中标签
	function fullContents(check,value,valuename){
		var a0196z = document.getElementById("a1401l").value;
		var a0196s = document.getElementById("a0196").value;
		if($(check).is(':checked')) {
			if( a0196z == null || a0196z == '' ){
				a0196z = valuename;
			}else{
				a0196z = a0196z + "，" + valuename;
			}	
			if( a0196s == null || a0196s == '' ){
				a0196s = value;
			}else{
				a0196s = a0196s  + "，" + value;
			}			
		}else{
			a0196z = a0196z.replace('，'+valuename, '').replace(valuename+'，', '').replace(valuename, '');
			a0196s = a0196s.replace('，'+value, '').replace(value+'，', '').replace(value, ''); 
		 	/* a0196z = a0196z.replace(valuename+'；', '').replace(valuename, '');
			a0196s = a0196s.replace(value+'；', '').replace(value, '');  */
		}
		document.getElementById("a1401l").value = a0196z;
		document.getElementById("a0196").value = a0196s; 
	}



 function checked(){
	var a0196s=document.getElementById("a1401l").value;
	var as=a0196s.split("，");
	$.each(as,function(i,s){
		$('#'+s).prop('checked')='checked';
	});
} 




/* function save(){
	radow.doEvent('save');
} */
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	$("[id^='attr']").bind('click',function(obj){
		fullContent(this,$(this).attr('id').replace('attr',''),$(this).parent().children('label').text());
		//alert($(this).parent().children('label').text())
		//checked();
	});
});
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	$("[id^='myattr']").bind('click',function(obj){
		fullContents(this,$(this).attr('id').replace('attr',''),$(this).parent().children('label').text());
		checked();
	});
});
function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
}
function formcheck(){
	return odin.checkValue(document.forms.commForm);
}

function rzzyjlClick(){
    var Id = document.getElementById("a0000").value;
    $h.openPageModeWin('szA0195ZTags','pages.zj.slabel.A0195TagsAddPage','历任(含现任)重要职务重要经历标志',810,580,Id,"<%=request.getContextPath()%>");
}

<%-- function sxlyClick(){
    var Id = document.getElementById("a0000").value;
    $h.openPageModeWin('szA0196ZTags','pages.zj.slabel.A0196TagsAddPage','熟悉领域',810,550,Id,"<%=request.getContextPath()%>");
} --%>
function sxlyClick(){
    var Id = document.getElementById("a0000").value;
    $h.openPageModeWin('szA0197ZTags','pages.zj.slabel.A0197TagsAddPage','熟悉领域',810,300,Id,"<%=request.getContextPath()%>");
}

function a1401lClick(){
    var Id = document.getElementById("a0000").value;
    $h.openPageModeWin('szA1401LTags','pages.zj.slabel.A1401LTagsAddPage','使用方式建议',810,550,Id,"<%=request.getContextPath()%>");
}

</script>
