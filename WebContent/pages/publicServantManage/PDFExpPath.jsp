<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<meta http-equiv="X-UA-Compatible" content="IE=8">
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/showDefault.js"></script>
<script type="text/javascript">
function browseFolder(path) {
    try {
        var Message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939"; //ѡ�����ʾ��Ϣ
        var Shell = new ActiveXObject("Shell.Application");
        var Folder = Shell.BrowseForFolder(0,Message, 64, 17); //��ʼĿ¼Ϊ���ҵĵ���
        //var Folder = Shell.BrowseForFolder(0, Message, 0); //��ʼĿ¼Ϊ������
        if (Folder != null) {
            Folder = Folder.items(); // ���� FolderItems ����
            Folder = Folder.item(); // ���� Folderitem ����
            Folder = Folder.Path; // ����·��
            if (Folder.charAt(Folder.length - 1) != "\\") {
                Folder = Folder + "\\";
            }
            document.getElementById('path').value = Folder;
            return Folder;
        }
    }
    catch (e) {
        alert(e.message);
    }
}
function exp(){
	var path=document.getElementById('path').value;
	var name=document.getElementById('name').value;
	if(path==""){
		alert("����ѡ��·��");
		return;
	} 
	if(name==""){
		alert("����д�ļ���");
		return;
	}
	window.parent.do_eportPDF(path,name);
	close();
}
  function close(){
	  radow.doEvent("close.onclick");
  }
</script>
<style>
 body{background:#DFE8F6}
 #tool{width:403}
</style>
<body>

<div>
	<table>
		<tr>
		    <td ><font style="font-size: 12">&nbsp&nbsp����·��:</font></td>
			<td height="50px"><input id="path" type="text" name="path" size="33" readonly="readonly"/></td>
			<td><odin:button text="ѡ���ļ���" handler="browseFolder"></odin:button></td>
		</tr>
		<tr>
			<td><font style="font-size: 12">&nbsp&nbsp�����ļ���:</font></td>
			<td height="40px"><input id="name" type="text" name="name" size="33"/></td>
		</tr>
	</table>
			
</div>
<div id="tool"></div>
<odin:toolBar property="" applyTo="tool">
  <odin:fill/>
  <odin:separator/>
  <odin:buttonForToolBar text="����" id="exp" handler="exp"></odin:buttonForToolBar>
  <odin:separator/>
  <odin:buttonForToolBar text="�ر�" id="close" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
</body>