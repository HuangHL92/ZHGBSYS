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
		msg = "清除人员信息（保留机构、用户）";
	}
	if(value == 2){
		msg = "清除所有数据（数据初始化）";
	}
	if(value == 3){
		msg = "清除机构人员信息（保留所有方案）";
	}
	if(msg){
		parent.$h.confirm("系统提示：",'当前操作的是：<span style="color: #cc0000; font-weight:bold;">'+msg+'</span><br/>清除后，原有数据将无法恢复，是否继续？',330,function(id) { 
			if("ok"==id){
				 parent.$h.confirm("系统提示：",'请再次确认？',200,function(id) { 
					if("ok"==id){
						Ext.Msg.wait('请稍等...','系统提示');
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
        	<input type="radio" name="choose" value="2" checked="checked"/><span>清除所有数据（数据初始化）</span>
        </td>
      </tr>
      <tr>
      	<td  class="text_warning" colspan="2">
        	<input type="radio" name="choose" value="3"/><span>清除机构和人员（保留所有方案）</span>
        </td>
      </tr>
      <tr>
        <td  class="text_warning" colspan="2">
        	<input type="radio" name="choose" value="1"/><span>清除人员信息（保留机构、用户）</span>
        </td>
<%-- <pre style="font-size: 16px;line-height: 28px;">  <img src="${pageContext.request.contextPath}/images/warning.gif" width="21" height="21" />数据库初始化后，原有的数据将
无法恢复，是否确定？
</pre> --%>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr align="center">
        <td align="center" ><odin:button text="是" property="confirm" handler="start" ></odin:button></td>
        <td align="center" ><odin:button text="否" property="cancel" handler="close"></odin:button></td>
      </tr>
    </table>
</div>