<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style> 
input, button{font-family:"Arial", "Tahoma", "΢���ź�", "�ź�";border:0;
vertical-align:middle;margin:0px;line-height:18px;font-size:18px} 
.btn{width:140px;height:36px;line-height:18px;font-size:18px;
background:url("image/1.jpg") no-repeat left top;color:#FFF;padding-bottom:4px} 
</style> 
<!-- �������Ĺ������� -->
<script type="text/javascript">
    
	function tytj(param){
		var ss = $h.openWin('GeneralStatistics','pages.statisticalanalysis.simpleanalysis.GeneralStatistics','�Զ���ͨ��ͳ��',700,500,param,'<%=request.getContextPath()%>');
	    ss.on('beforeclose',function(){parent.reload();});
	}
	function zdytj(param){
		var ss = $h.openWin('TwoDStatistics','pages.statisticalanalysis.simpleanalysis.TwoDStatistics','�Զ����άͳ��',1000,650,param,'<%=request.getContextPath()%>');
		ss.on('beforeclose',function(){parent.reload();});
	}
	function zdyew(){
		radow.doEvent('ew');
	}
	function zdyty(){
		radow.doEvent('ty');
	}
</script>
<table>
   <tr style="text-align:center; height:90px">
  	  <td >
      	<div style="width:45px" />
      <td>
      <td >
      	 <img src="image/11.png" id="tytp">
      </td>
      <td style="margin:0px">
		 <input type="button" class="btn" value="ͨ��ͳ��" id="ty" onmouseover="this.style.backgroundPosition='left -36px'"
		  onmouseout="this.style.backgroundPosition='left top'" onclick="zdyty()"/>
	  </td>
  </tr> 
  <tr style="text-align:center; height:90px">
      <td >
      	<div style="width:45px" />
      <td>
  	  <td >
  	  	 <img src="image/22.png" id="ewtp">
  	  </td>	
  	  <td style="margin:0px">	
		<input type="button" class="btn" value="��άͳ��" id="ew" onmouseover="this.style.backgroundPosition='left -36px'"
		 onmouseout="this.style.backgroundPosition='left top'" onclick="zdyew()"/>
	  </td>	 
  </tr>	  
</table>

