<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ page isELIgnored="false"%> 
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style type="text/css">
 
body { margin: 0px; font-size: 12px}
.text_title{ font-size: 30px; color: red; font-family: Arial, Helvetica, sans-serif; padding-top:5px}
span{ font-size: 16px; color: #cc0000; font-weight:bold; }
.div{width: 300;height: 300}

</style>
<script type="text/javascript">

function close(){  
	parent.Ext.WindowMgr.getActive().close();
}
function start(){
	var value = getRadioValue();
	var msg;
	if(value == 1){
		msg = "�����Ա��Ϣ�������������û���";
	}
	if(value == 2){
		msg = "����������ݣ����ݳ�ʼ����";
	}
	if(value == 3){
		msg = "���������Ա��Ϣ���������з�����";
	}
	if(msg){
		parent.$h.confirm("ϵͳ��ʾ��",'��ǰ�������ǣ�<span style="color: #cc0000; font-weight:bold;">'+msg+'</span><br/>�����ԭ�����ݽ��޷��ָ����Ƿ������',330,function(id) { 
			if("ok"==id){
				 parent.$h.confirm("ϵͳ��ʾ��",'���ٴ�ȷ�ϣ�',200,function(id) { 
					if("ok"==id){
						Ext.Msg.wait('���Ե�...','ϵͳ��ʾ');
						radow.doEvent('confirmonclick',value);
					}else{
						return false;
					}		
				});
			}else{
				return false;
			}		
		});
	}
	
}

function getRadioValue(){ 
    var radios = document.getElementsByName("choose"); 
    var value; 
    for(var i=0;i<radios .length;i++){ 
    	if(radios[i].checked){ 
        	value = radios[i].value; 
        	break; 
        } 
    }
    return value;
}
</script>

<div>
	<br/>
	<table  border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:0px">
      <tr>
      	<td  class="text_warning" colspan="2">
        	<input type="radio" name="choose" value="2" checked="checked"/><span>����������ݣ����ݳ�ʼ����</span>
        </td>
      </tr>
      <tr>
      	<td  class="text_warning" colspan="2">
        	<input type="radio" name="choose" value="3"/><span>�����������Ա���������з�����</span>
        </td>
      </tr>
      <tr>
        <td  class="text_warning" colspan="2">
        	<input type="radio" name="choose" value="1"/><span>�����Ա��Ϣ�������������û���</span>
        </td>
<%-- <pre style="font-size: 16px;line-height: 28px;">  <img src="${pageContext.request.contextPath}/images/warning.gif" width="21" height="21" />���ݿ��ʼ����ԭ�е����ݽ�
�޷��ָ����Ƿ�ȷ����
</pre> --%>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr align="center">
        <td align="center" ><odin:button text="��" property="confirm" handler="start" ></odin:button></td>
        <td align="center" ><odin:button text="��" property="cancel" handler="close"></odin:button></td>
      </tr>
    </table>
</div>