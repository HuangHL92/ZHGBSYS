<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<table cellspacing="2" width="98%" align="center">
	<tr>
		<td><odin:textEdit property="adress" size="40%" label="ͼƬ��ַ:"></odin:textEdit>
		</td>
		<td><odin:button text="ͼƬ�ַ�" property="fenfa" handler=""></odin:button>
		</td>
	</tr>
	<tr>
		<td colspan="3"><label id="bz1" style="font-size: 12; color: red">ע��·���Է�б�ܡ�/����Ϊ�ָ������磺D:/TFTP����</label><br>
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
 //�������رհ�ť  
function refresh()  {  
	if(timeout){
	parent.odin.ext.getCmp('win10').hide();
	alert("�ַ����")
	 return;
	}else{
	myrefresh();  
 	setTimeout(refresh,10000); //time��ָ����,��ʱ�ݹ�����Լ�,100Ϊ�������ʱ��,��λ����  
	}	
}
</script>













