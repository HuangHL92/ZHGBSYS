<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="单位信息查询" description="单位信息查询（简单）" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="inputWidth" required="false" %>
<%@ attribute name="dispWidth" required="false" %>
<%@ attribute name="selectSuccess" required="false" %>
<%@ attribute name="selectFailure" required="false" %>
<%@ attribute name="required" required="false" %>

<script type="text/javascript">
<!--
	function openCorpInfoCommonQuery(){
		var rs=window.showModalDialog(contextPath+"/pages/common/openCorpInfoCommonQueryAction.do",null,"help:no;status:no;dialogWidth:50;dialogHeight:35");
		if(rs){
			//alert(rs.aae044);
			//document.all.aaz001.value=rs.aaz001;
			document.all.eab001.value=rs.eab001;
			//document.all.aae044.value=rs.aae044;
			eab001Change();
		}
	}
	
	function eab001Change(){
		document.all.aaz001.value="";
		document.all.aae044.value="";
		var eab001=document.all.eab001.value;
		if(eab001){
			odin.Ajax.request(contextPath+"/com/insigma/siis/local/module/common/search/GetCorpInfoByCodeAction.do?method=getCorpInfoByCode",
							{'eab001':eab001},getCorpInfoSuccess,getCorpInfoFailure);
		}
	}
	
	function getCorpInfoSuccess(response){
		document.all.aaz001.value=response.data.aaz001;
		document.all.aae044.value=response.data.aae044;
		<%if(selectSuccess!=null&&!selectSuccess.equals("")){%>
		<%=selectSuccess%>(response);
		<%}%>
	}
	
	function getCorpInfoFailure(response){
	    //odin.showErrorMessage(response);
		document.all.eab001.focus();
		document.all.eab001.select();
		<%if(selectFailure!=null&&!selectFailure.equals("")){%>
		<%=selectFailure%>();
		<%}%>
	}
//-->
</script>

<odin:textIconEdit property="eab001" onchange="eab001Change()" iconClick="openCorpInfoCommonQuery" maxlength="30" label='<%=(label==null?"单位编码":label)%>' width='<%=(inputWidth==null?"160":inputWidth)%>' required='<%=(required==null?"false":"true")%>'/>
<odin:textEdit property="aae044" readonly="true" width='<%=(dispWidth==null?"239":dispWidth) %>'/>
<odin:hidden property="aaz001"/>
