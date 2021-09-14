<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<meta http-equiv="X-UA-Compatible" content="IE=8">
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/showDefault.js"></script>
<script type="text/javascript">
function browseFolder(path) {
    try {
        var Message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939"; //选择框提示信息
        var Shell = new ActiveXObject("Shell.Application");
        var Folder = Shell.BrowseForFolder(0,Message, 64, 17); //起始目录为：我的电脑
        //var Folder = Shell.BrowseForFolder(0, Message, 0); //起始目录为：桌面
        if (Folder != null) {
            Folder = Folder.items(); // 返回 FolderItems 对象
            Folder = Folder.item(); // 返回 Folderitem 对象
            Folder = Folder.Path; // 返回路径
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
		alert("请先选择路径");
		return;
	} 
	if(name==""){
		alert("请填写文件名");
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
		    <td ><font style="font-size: 12">&nbsp&nbsp保存路径:</font></td>
			<td height="50px"><input id="path" type="text" name="path" size="33" readonly="readonly"/></td>
			<td><odin:button text="选择文件夹" handler="browseFolder"></odin:button></td>
		</tr>
		<tr>
			<td><font style="font-size: 12">&nbsp&nbsp保存文件名:</font></td>
			<td height="40px"><input id="name" type="text" name="name" size="33"/></td>
		</tr>
	</table>
			
</div>
<div id="tool"></div>
<odin:toolBar property="" applyTo="tool">
  <odin:fill/>
  <odin:separator/>
  <odin:buttonForToolBar text="导出" id="exp" handler="exp"></odin:buttonForToolBar>
  <odin:separator/>
  <odin:buttonForToolBar text="关闭" id="close" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
</body>