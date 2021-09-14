<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="人员信息查询" description="人员信息查询（简单）" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="inputWidth" required="false" %>
<%@ attribute name="dispWidth" required="false" %>
<%@ attribute name="selectSuccess" required="false" %>
<%@ attribute name="selectFailure" required="false" %>

<script type="text/javascript" src="pages/medicalmgmt/reimburseexpense/getHospitalStub.js"></script>
<script>
<!--
	function openPeopleInfoCommonQuery(){
		var rs=window.showModalDialog(contextPath+"/pages/common/openPeopleInfoCommonQueryAction.do",null,"help:no;status:no;dialogWidth:50;dialogHeight:28");
		if(rs){
			document.all.eac001.value=rs.eac001;
			document.all.aac001.value=rs.aac001;
			document.all.aac003.value=rs.aac003;
			document.all.aae135.value=rs.aae135;
		}
	}
	
//-->
</script>

<odin:textIconEdit property="eac001" readonly="false" iconClick="openPeopleInfoCommonQuery" maxlength="10" label="<%=(label==null?\"社保编码\":label)%>" width="<%=(inputWidth==null?\"145\":inputWidth)%>"/>
<odin:hidden property="aac001"/>


