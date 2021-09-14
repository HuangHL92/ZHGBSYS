<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>
#tag_container {
	position: relative;
	width: 100%;
	height: 450px;
	border-width: 0;
	border-style: solid;
	border-color: #74A6CC;
	margin-top: 10px;
}

#tag_container .tag_div {
	position: relative;
	height: 100%;
	float: left;
	margin-left: 2%;
}

#tag_info_div {
	position: relative;
	width: 100%;
}

#tag_info_div #a0194z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}

#bottom_div {
	position: relative;
	width: 100%;
	height: 40px;
	margin-top: 5px;
}
table{
width: 100%
}
</style>

<odin:hidden property="a0194s"/>
<div id="tag_container">
	<div class="tag_div">
		<table>
		  <tr>
		    <td><input type="checkbox" name="attr41" id="attr41" class="marginLeft20"/><label for="attr41">网络安全与信息</label></td>
		    <td><input type="checkbox" name="attr42" id="attr42" class="marginLeft20"/><label for="attr42">行政综合</label></td>
		    <td><input type="checkbox" name="attr43" id="attr43" class="marginLeft20"/><label for="attr43">基层治理</label></td>
		  </tr>
		  <tr>
		  	<td><input type="checkbox" name="attr44" id="attr44" class="marginLeft20"/><label for="attr44">法律</label></td>
		  	<td><input type="checkbox" name="attr45" id="attr45" class="marginLeft20"/><label for="attr45">组织人事和党务</label></td>
		  	<td><input type="checkbox" name="attr46" id="attr46" class="marginLeft20"/><label for="attr46">经济金融（党政机关）</label></td>
		  </tr>
		  <tr>
		  	<td><input type="checkbox" name="attr31" id="attr31" class="marginLeft20"/><label for="attr31">企业经营管理</label></td>
		  	<td><input type="checkbox" name="attr47" id="attr47" class="marginLeft20"/><label for="attr47">企业金融投资</label></td>
		  	<td><input type="checkbox" name="attr48" id="attr48" class="marginLeft20"/><label for="attr48">财务审计</label></td>
		  </tr>
		  <tr>
		  	<td><input type="checkbox" name="attr49" id="attr49" class="marginLeft20"/><label for="attr49">巡视和纪检监察</label></td>
		  	<td><input type="checkbox" name="attr50" id="attr50" class="marginLeft20"/><label for="attr50">规划和工程基建</label></td>
		  	<td><input type="checkbox" name="attr05" id="attr05" class="marginLeft20"/><label for="attr05">统战</label></td>
		  </tr>
		  <tr>
		  	<td><input type="checkbox" name="attr06" id="attr06" class="marginLeft20"/><label for="attr06">政法</label></td>
		  	<td><input type="checkbox" name="attr07" id="attr07" class="marginLeft20"/><label for="attr07">群团</label></td>
		  	<td><input type="checkbox" name="attr19" id="attr19" class="marginLeft20"/><label for="attr19">科技</label></td>
		  </tr>
		  <tr>
		  	<td><input type="checkbox" name="attr51" id="attr51" class="marginLeft20"/><label for="attr51">生物医药卫生</label></td>
		  	<td><input type="checkbox" name="attr52" id="attr52" class="marginLeft20"/><label for="attr52">新闻媒体和宣传</label></td>
		  	<td><input type="checkbox" name="attr13" id="attr13" class="marginLeft20"/><label for="attr13">文化旅游</label></td>
		  </tr>
		  <tr>
		  	<td><input type="checkbox" name="attr21" id="attr21" class="marginLeft20"/><label for="attr21">教育</label></td>
		  	<td><input type="checkbox" name="attr99" id="attr99" class="marginLeft20"/><label for="attr99">其他</label></td>
		  </tr>
		</table>
		
		
		
		
		

	</div>
</div>
<div id="tag_info_div">
	<textarea rows="3" cols="113" id="a0194z" name="a0194z"></textarea>
</div>
<div id="bottom_div">
	<div align="center">
		<odin:button text="保&nbsp;&nbsp;存" property="save" />
	</div>		
</div> 
<script type="text/javascript">
Ext.onReady(function(){
	$('input:checkbox').bind('click',function(obj){
		fullContent(this,$(this).attr('id').replace('attr',''),$(this).parent().children('label').text());
		//alert($(this).parent().children('label').text())
	});
});


//输入框中显示选中标签
function fullContent(check,value,valuename){
	var a0194z = document.getElementById("a0194z").value;
	var a0194s = document.getElementById("a0194s").value;
	if($(check).is(':checked')) {
		if( a0194z == null || a0194z == '' ){
			a0194z = valuename;
		}else{
			a0194z = a0194z + "，" + valuename;
		}	
		if( a0194s == null || a0194s == '' ){
			a0194s = value;
		}else{
			a0194s = a0194s  + "，" + value;
		}			
	}else{
		a0194z = a0194z.replace('，'+valuename, '').replace(valuename+'，', '').replace(valuename, '');
		a0194s = a0194s.replace('，'+value, '').replace(value+'，', '').replace(value, '');
	}
	document.getElementById("a0194z").value = a0194z;
	document.getElementById("a0194s").value = a0194s;
}


</script>
