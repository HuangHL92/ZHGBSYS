<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="疾病信息查询" description="疾病信息查询（简单）" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="inputWidth" required="false" %>
<%@ attribute name="dispWidth" required="false" %>
<%@ attribute name="selectSuccess" required="false" %>
<%@ attribute name="selectFailure" required="false" %>

<script type="text/javascript">
<!--
	function openDiseaseInfoCommonQuery(){
		var rs=window.showModalDialog(contextPath+"/pages/common/openDiseaseInfoCommonQueryAction.do",null,"help:no;status:no;location:no;dialogWidth:50;dialogHeight:30");
		if(rs){
			//alert(rs.aka120);
			//document.all.aaz001.value=rs.aaz001;
			document.all.aka120.value=rs.aka120;
			//document.all.aae044.value=rs.aae044;
			aka120Change();
		}
	}
	
	function aka120Change(){
		document.all.jbbm.value="";
		var aka120=document.all.aka120.value;
		if(aka120){
			odin.Ajax.request(contextPath+"/com/insigma/siis/local/module/common/search/GetDiseaseInfoByCodeAction.do?method=getDiseaseInfoByCode",
							{'aka120':aka120},getDiseaseInfoSuccess,getDiseaseInfoFailure);
		}
	}
	
	function getDiseaseInfoSuccess(response){
		document.all.aka120.value=response.data.aka121;
		document.all.jbbm.value=response.data.aka120;
		<%if(selectSuccess!=null&&!selectSuccess.equals("")){%>
		<%=selectSuccess%>(response);
		<%}%>
	}
	
	function getDiseaseInfoFailure(response){
		odin.showErrorMessage(response);
		document.all.aka120.focus();
		document.all.aka120.select();
		<%if(selectFailure!=null&&!selectFailure.equals("")){%>
		<%=selectFailure%>();
		<%}%>
	}
//-->
</script>

<odin:textIconEdit property="aka120" required="true" onchange="aka120Change()" iconClick="openDiseaseInfoCommonQuery" maxlength="8" label="<%=(label==null?\"疾病名称\":label)%>" width="<%=(inputWidth==null?\"117\":inputWidth)%>"/>
<odin:hidden property="jbbm"/>

