<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""  display-name="���������ѯ" description="���������ѯ���򵥣�" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="property" required="true" %>
<%@ attribute name="label" required="false" %>
<%@ attribute name="rows" required="false" %>
<%@ attribute name="colspan" required="false" %>
<%@ attribute name="cols" required="false" %>
<%@ attribute name="style" required="false" %>

<script type="text/javascript">
<% 
	String contextpath = request.getContextPath();
	if(rows==null || rows.equals("")){
		rows = "5";
	}
	if(colspan==null || colspan.equals("")){
		colspan = "3";
	}
	if(cols == null || cols.equals("")){
		cols = "30";
	}
%>

var queryOrgs = function (){
	var url = "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgTreeSingle";
	var option = "";
	var obj = new Object();
	var result = null;
	if(myBrowser()=='Chrome'){//���Chrome������ļ�������
		option = "height=500,width=300,top=50,left=300,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=0";
    	window.open(url,obj,option);
	}else{
		option = "help:no;status:no;location:no;dialogWidth:16.5;dialogHeight:28";
		result = window.showModalDialog(url,obj,option);
		Sure(result);
	}
};
function Sure(result){
	if(result!=null){
		var resulta = result.split(",");
		document.getElementById('<%=property%>').value=resulta[0];
		document.getElementById('SysOrgTreeIds').value=resulta[1];
		document.getElementById('b0101').focus();
	}
}
function myBrowser(){
    var userAgent = navigator.userAgent; //ȡ���������userAgent�ַ���
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
        return "Opera"
    }; //�ж��Ƿ�Opera�����
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    } //�ж��Ƿ�Firefox�����
    if (userAgent.indexOf("Chrome") > -1){
  		return "Chrome";
 	}
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    } //�ж��Ƿ�Safari�����
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
        return "IE";
    }; //�ж��Ƿ�IE�����
    return "IE";
}


</script>
<odin:textarea property="<%=property %>" label="<%=label%>" style="<%=style %>" rows="<%=rows%>" colspan="<%=colspan%>" cols="<%=cols%>"  onclick="queryOrgs()"/>
<odin:hidden property="SysOrgTreeIds"/>