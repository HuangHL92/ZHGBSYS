<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="com.lbs.commons.GlobalNames"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>������Ա����Ϣ</title>
</head>
<script>
    //ǰҳ����������ֵ
    var formerobj=window.dialogArguments;
    var HiddenListGroup=formerobj.groupids;
    var arry_HiddenListGroup=HiddenListGroup.split(",");
    var groupid=arry_HiddenListGroup[0];
    document.write("<frameset cols='50,50'  frameborder='no' border='0' framespacing='0'>");
    document.write("<frame src='<%=request.getContextPath()%>/switchAction.do?prefix=/sysmanager&page=/userlist/UserList.jsp' name='left' marginwidth='0' marginheight='0' scrolling='yes'>")
    if(null==groupid||""==groupid){
       document.write("<frame src='userListAction.do?method=findByKey' name='right' marginwidth='0' marginheight='0'>");
    }else{
       document.write("<frame src='userListAction.do?method=findByKey&key="+groupid+"' name='right' marginwidth='0' marginheight='0'>");
    }
    document.write("</frameset>");
    document.write("<noframes></noframes>");
</script>
</html>