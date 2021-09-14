<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<style>

.info
{
font: normal 15px arial, tahoma, helvetica, sans-serif;size:22px;
}

</style>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<odin:commformhead/>
<odin:commformMDParam></odin:commformMDParam>
</head>
	<body>
  		<table class="info"> 
  			<tr width="100px">
  				<td >
  					<label id="bz1" style="font-size: 12;color: red">注：该操作说明比较简单，之后会有比较详细的操作手册。</label><br>
				</td>
  			</tr>
  			<tr>
  				<td class="info">
  					<div style="width: 1000px">&nbsp打印空模板：打印没有任何数据的模板包括标准模板，自定义模板，共享模板。</div>
		  			<div style="width: 1020px">&nbsp新建模板： &nbsp&nbsp新建模板可以建三类模板，1 表格，2 标准名册，3 花名册。画板默认是纵向展示如果要横向可以在“页面设置”按钮进行设置。</div>
		  			<div style="width: 1000px">&nbsp&nbsp&nbsp表格： &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp光标确定展示信息的位置单击左边的备选信息，建好之后删除多余的行，列然后点击保存（表格是每人一页展示），</div>
		  			<div style="width: 1000px">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp在“人员信息列表”-->“表册输出”-->“自定义模板”看到，双击展示数据。</div>
		  			<div style="width: 1000px">&nbsp&nbsp&nbsp标准名册：&nbsp光标确定展示信息的位置单击左边的备选信息（备选项一定要放在一行上，不要上下错行），建好之后删除多余的行，列然后</div>
		  			<div style="width: 1000px">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp点击保存（标准名册是多人一页展示）在“人员信息列表”-->“表册输出”-->“自定义模板”看到，双击展示数据。</div>
		  			<div style="width: 1000px">&nbsp&nbsp&nbsp花名册： &nbsp&nbsp&nbsp花名册比较特殊，需要按照模板进行自定义，有些是不能改变的，1，“目录”的位置一定要在前4行,</div>
		  			<div style="width: 1000px">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp（“目录”sheet不要写别的信息可能会被覆盖），“目录”两个字写到别的位置可能展示错误。</div>
		  			<div style="width: 1000px">&nbsp建同类表： 	 &nbsp&nbsp建同类表如同复制模板，只不过你在复制的过程中可以修改模板的内容。</div>
		  			<div style="width: 1000px">&nbsp编辑模板： 	 &nbsp&nbsp对已有的自定义模板如有改动可以通过这个按钮进行继续编辑。</div>
		  			<div style="width: 1000px">&nbsp重命名： &nbsp&nbsp&nbsp&nbsp&nbsp对已有的自定义模板可以重新进行命名。</div>
		  			<div style="width: 1000px">&nbsp删除模板：   &nbsp&nbsp删除无用的模板。</div>
		  			<div style="width: 1000px">&nbsp导出模板：   &nbsp&nbsp导出模板可以导出2个格式分别是“cll”,“excel”,暂不支持word。</div>
		  			<div style="width: 1000px">&nbsp导入模板：   &nbsp&nbsp导入模板分为2个格式6个类型，导入时注意把模板类型和按钮的类型进行匹配，否则即使能导进去展示数据时也可能发生错。</div>
		  			<div style="width: 1000px">&nbsp共享：       &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp共享分为 1 设为共享，2 取消共享 ,共享只能共享给你的同级机构人员和下级机构人员(指定到人)。</div>
		  			<div style="width: 1000px">&nbsp设为共享：   &nbsp&nbsp点击“设为共享”会看到你的本机机构人员和所有的下级机构人员，选择你要想共享的人员，点击确定被分享的人在共享栏就能</div>
		  			<div style="width: 1000px">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp看到共享的模板。</div>
		  			<div style="width: 1000px">&nbsp取消共享：   &nbsp&nbsp点击“点击共享”会看到该模板已共享的所有人员，如果不想对某人取消共性请把前面的对号单击去掉，点击确定把所以选中的</div>
		  			<div style="width: 1000px">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp人员进行取消共享。</div>
  				</td>
  			</tr>
  			
  		</table>  
	</body>
</html>


