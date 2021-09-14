<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<table cellspacing="2" width="98%" align="center">
	<tr>
		<td><odin:textEdit property="adress" size="40%" label="图片地址:"></odin:textEdit>
		</td>
		<td><odin:button text="图片分发" property="fenfa" handler=""></odin:button>
		</td>
	</tr>
	<tr>
		<td colspan="3"><label id="bz1" style="font-size: 12; color: red">注：路径以反斜杠“/”作为分隔符（如：D:/TFTP）。</label><br>
		</td>
	</tr>

</table>
<odin:hidden property="uuid" />
<odin:hidden property="user" />

<script type="text/javascript">
function myrefresh() 
{
     radow.doEvent('btnsx');
     
} 
var timeout = false;
function close1(){
	timeout =  true;
}
 //启动及关闭按钮  
function refresh()  {  
	if(timeout){
	parent.odin.ext.getCmp('win10').hide();
	alert("分发完成")
	 return;
	}else{
	myrefresh();  
 	setTimeout(refresh,10000); //time是指本身,延时递归调用自己,100为间隔调用时间,单位毫秒  
	}	
}
</script>













