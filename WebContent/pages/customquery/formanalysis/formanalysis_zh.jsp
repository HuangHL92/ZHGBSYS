<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<script src="<%=request.getContextPath()%>/pages/customquery/formanalysis/formanalysis.js"> </script>


<%--用户接受路径参数的值，并保存 --%>
<%@include file="/comOpenWinInit.jsp" %>

<%--统计表格 --%>
<div id="createtable" style="overflow-y:hidden;overflow-x:hidden;overflow: auto;margin-left:15px;display:block;height:100%;">
	<table style="width:100%;height:100%;" border="0">
		<col width="5%">
		<col width="20%">
		<col width="5%">
		<col width="20%">
		<col width="10%">
		<col width="10%">
		<col width="30%">
		<tr height="8px">
		</tr>
		<tr>
			<odin:select2 property="zwlb" label="职务层次" codeType="ZWCELB" multiSelectWithAll="true" ></odin:select2>
			<odin:select property="tjxm_col" label="统计项目" data="[1,'女少非'],[2,'学历'],[3,'学位'],[4,'年龄'],[5,'任现职务层次年限'],[6,'具有2年以上基层工作经历']" multiSelectWithAll="true" ></odin:select>
			<td>
				<odin:checkbox property="xianyin" label="隐藏全零行" value="1" onclick="xianyin_onclick(this)" ></odin:checkbox>
			</td>
			<td>
				<odin:checkbox property="yczb" label="隐藏占比" value="1" onclick="yczb_onclick(this)" ></odin:checkbox>
			</td>
			<td>
				<table>
					<tr>
						<td>
							<odin:button text="导出" property="imp_excel" handler="exportData"></odin:button>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table style="width:100%;height:100%;" border="0">
		<tr>
			<td colspan="4">
				<div style="width:100%;height:100%;">
					<object id="DCellWeb1" style="left: 0px; width: 964px; top: 0px; height: 440px" 
				    classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" 
				    codebase="<%=request.getContextPath()%>/softTools/CellWeb5.CAB#version=5,3,9,16">
				    <!-- #version=5.3.9.16 -->
				    <param name="_Version" value="65536" />
				    <param name="_ExtentX" value="10266" />
				    <param name="_ExtentY" value="7011" />
				    <param name="_StockProps" value="0" />
				    </object>
				</div>
			</td>
		</tr>
	</table>
</div>

<div>
	<odin:hidden property="zwlb_l"/><!-- 职务类别复选隐藏值 -->
	<odin:hidden property="xy_zwlb"/><!-- 显隐选择隐藏值 -->
	<odin:hidden property="jsonString_str"/><!-- 分组分析结果json字符串隐藏值 -->
	<odin:hidden property="tjxm_col_h"/> <!-- 统计项目复选隐藏值 -->
</div>
<script for="DCellWeb1" event="MouseDClick(col, row)" language="JavaScript">
	myMouseDClick(col, row);//定义双击事件
</script>
<script type="text/javascript" language="javascript">
	setAuth(document.getElementById("DCellWeb1"));//?
</script>
<script type="text/javascript">

function jcHeightWidth(){
	document.getElementById("DCellWeb1").style.height=document.body.offsetHeight-60;//底部滚动条宽度+查询条件高度 =60
	document.getElementById("DCellWeb1").style.width=document.body.offsetWidth;
}
Ext.onReady(function openfile(){
    try{
		document.getElementById("DCellWeb1").openfile(ctpath+"\\template\\gbjbqktj4.cll","");
    }catch(err){
    	//alert("提示:请下载安装华表插件!并使用IE浏览器(兼容模式)打开(可联系系统管理员远程协助)!");
    	//alert("请安装华表插件!");
    }
    //设置复选框上下居中
  	checkboxvl("xianyin");
  	checkboxvl("yczb");
  	document.getElementById("createtable").parentNode.parentNode.style.overflow='hidden';
  	window.onresize=jcHeightWidth;
});
//根据 odin:checkbox 的 property属性 设置复选框的上下居中样式
function checkboxvl(id){
	try{
		var node_66;
		var node_55;
		var node_5_height;
		var node_6_height;
		node_66=$("#"+id).parent();
		node_6_height=node_66[0].offsetHeight;
		//node_55=node_66.parent();
		node_55=node_66.children();
		node_5_height=node_55[0].offsetHeight;
		var mt=(node_6_height-node_5_height)/2;
		//node_55.css({"top": mt+"px"});
		//$("#"+id).parent().css({"top": mt+"px"});
		//$("#"+id).parent().parent().css({"top": mt+"px"});
		$("#"+id).parent().parent().parent().css({"top": mt+"px"});
	}catch(err){
		//alert(err);
	}
}
var col_xho_start=4;
var col_xho_end=38+4;
var col_xhj_start=39+4;
var col_xhj_end=49+4;
//表格json数据
var json_obj="";
//初始化方法 
var DCellWeb_tj="";
//json字符串 
//点击统计分析
function json_func(){
   //jsonstr,lengthstr
   var jsonstr=document.getElementById("jsonString_str").value;
   DCellWeb_tj=document.getElementById("DCellWeb1");//获取表格对象
   var obj ="";
   try{
	   obj = eval('(' + jsonstr + ')');
   }catch(err){
   }
   json_obj=obj;
  
   DCellWeb_tj.SetFixedCol(1, 2); 
   DCellWeb_tj.SetFixedRow(1, 3);
  
   for(var i=0;i<obj.length;i++){////插入数据 行数 循环
   	   var a0221=obj[i].a0221;//代码
   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
   			//continue;//类别代码为空
   			a0221='QT';
   	   }
   	   a0221=map[a0221];//行数
   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "||a0221===undefined){
   			//js 没有配置此类别代码
   		a0221=parseInt(array_row[array_row.length-1].split(",")[1])+1;//最后一行
   		var i_col=4;
   		var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
   		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].nv==0||obj[i].nv=='null')?temp:parseInt(obj[i].nv)+parseInt(temp));//女
   		i_col=i_col+2;
   		temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].sm==0||obj[i].sm=='null')?temp:parseInt(obj[i].sm)+parseInt(temp));//少数民族
	   	i_col=i_col+2;
	   	var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].zd==0||obj[i].zd=='null')?temp:parseInt(obj[i].zd)+parseInt(temp));//中共党员
	   	i_col=i_col+2;
	   	var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].fzd==0||obj[i].fzd=='null')?temp:parseInt(obj[i].fzd)+parseInt(temp));//非中共党员
	   	i_col=i_col+2;
	   	var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].yjs==0||obj[i].yjs=='null')?temp:parseInt(obj[i].yjs)+parseInt(temp));//研究生
	   	i_col=i_col+2;
	   	var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dxbk==0||obj[i].dxbk=='null')?temp:parseInt(obj[i].dxbk)+parseInt(temp));//大学本科
	   	i_col=i_col+2;
	   	var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dxzz==0||obj[i].dxzz=='null')?temp:parseInt(obj[i].dxzz)+parseInt(temp));//大学专科
	   	i_col=i_col+2;
	   	var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].zz==0||obj[i].zz=='null')?temp:parseInt(obj[i].zz)+parseInt(temp));//中专
		    i_col=i_col+2;
		    var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
	   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
	   			temp=0;
	   		}
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].gzjyx==0||obj[i].gzjyx=='null')?temp:parseInt(obj[i].gzjyx)+parseInt(temp));//高中及以下
		    i_col=i_col+2;
		    var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
	   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
	   			temp=0;
	   		}
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].bs==0||obj[i].bs=='null')?temp:parseInt(obj[i].bs)+parseInt(temp));//博士
		    i_col=i_col+2;
		    var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
	   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
	   			temp=0;
	   		}
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].ss==0||obj[i].ss=='null')?temp:parseInt(obj[i].ss)+parseInt(temp));//硕士
		    i_col=i_col+2;
		    var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
	   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
	   			temp=0;
	   		}
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].xs==0||obj[i].xs=='null')?temp:parseInt(obj[i].xs)+parseInt(temp));//学士
		    i_col=i_col+2;
		    var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
	   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
	   			temp=0;
	   		}
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].xydy35==0||obj[i].xydy35=='null')?temp:parseInt(obj[i].xydy35)+parseInt(temp));//35岁及以下
		    i_col=i_col+2;
		    var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
	   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
	   			temp=0;
	   		}
		DCellWeb_tj.SetCellString(
				i_col,a0221,0,(obj[i].dy36xy40==0||obj[i].dy36xy40=='null')?temp:parseInt(obj[i].dy36xy40)+parseInt(temp));//36岁至40岁
		i_col=i_col+2;
		var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
		DCellWeb_tj.SetCellString(
				i_col,a0221,0,(obj[i].dy41xy45==0||obj[i].dy41xy45=='null')?temp:parseInt(obj[i].dy41xy45)+parseInt(temp));//41岁至45岁
		i_col=i_col+2;
		var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
		DCellWeb_tj.SetCellString(
				i_col,a0221,0,(obj[i].dy46xy50==0||obj[i].dy46xy50=='null')?temp:parseInt(obj[i].dy46xy50)+parseInt(temp));//46岁至50岁
		i_col=i_col+2;
		var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
		DCellWeb_tj.SetCellString(
				i_col,a0221,0,(obj[i].dy51xy54==0||obj[i].dy51xy54=='null')?temp:parseInt(obj[i].dy51xy54)+parseInt(temp));//51岁至54岁
		i_col=i_col+2;
		var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
		DCellWeb_tj.SetCellString(
				i_col,a0221,0,(obj[i].dy55xy59==0||obj[i].dy55xy59=='null')?temp:parseInt(obj[i].dy55xy59)+parseInt(temp));//55岁至59岁
		i_col=i_col+2;
		var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
	    DCellWeb_tj.SetCellString(
	    		i_col,a0221,0,(obj[i].dy60==0||obj[i].dy60=='null')?temp:parseInt(obj[i].dy60)+parseInt(temp));//60岁及以上
	    i_col=i_col+2;
	    var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
   		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].pjnl==0||obj[i].pjnl=='null')?temp:returnFloat((parseInt(obj[i].pjnl)*(parseInt(obj[i].zd)+parseInt(obj[i].fzd)))+parseInt(temp)));//平均年龄
   		
   				i_col=i_col+1;
   		var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].zhccxy1==0||obj[i].zhccxy1=='null')?temp:parseInt(obj[i].zhccxy1)+parseInt(temp));//不满1年
	   	i_col=i_col+2;
	   	var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].zhccxy3==0||obj[i].zhccxy3=='null')?temp:parseInt(obj[i].zhccxy3)+parseInt(temp));//1年至不满3年
	   	i_col=i_col+2;
	   	var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
	    DCellWeb_tj.SetCellString(
	    		i_col,a0221,0,(obj[i].zhccxy5==0||obj[i].zhccxy5=='null')?temp:parseInt(obj[i].zhccxy5)+parseInt(temp));//3年至不满5年
	    i_col=i_col+2;
	    var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].zhccxy10==0||obj[i].zhccxy10=='null')?temp:parseInt(obj[i].zhccxy10)+parseInt(temp));//5年至不满10年
	   	i_col=i_col+2;
	   	var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].zhccxy11==0||obj[i].zhccxy11=='null')?temp:parseInt(obj[i].zhccxy11)+parseInt(temp));//10年及以上
	   	i_col=i_col+2;
	   	var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,(obj[i].a0197==0||obj[i].a0197=='null')?temp:parseInt(obj[i].a0197)+parseInt(temp));//是否具有两年以上基层工作经历
	   	//合计
	   	// 中共党员
	   	/* var zg=obj[i].zd==0?"":obj[i].zd;
	   	if(zg=='undefined' || zg=="" || zg==null||zg==" "){
	   		zg=0;
	   	} */
	   	var zg_temp=DCellWeb_tj.GetCellString(8,a0221,0);
	   	if(zg_temp=='undefined' || zg_temp=="" || zg_temp==null||zg_temp==" "){
	   		zg_temp=0;
	   	}
	   	//非中共党员
	   	/* var fzg=obj[i].fzd==0?"":obj[i].fzd;
		if(fzg=='undefined' || fzg=="" || fzg==null||fzg==" "){
			fzg=0;
	   	} */
		var fzg_temp=DCellWeb_tj.GetCellString(10,a0221,0);
		if(fzg_temp=='undefined' || fzg_temp=="" || fzg_temp==null||fzg_temp==" "){
			fzg_temp=0;
	   	}
		DCellWeb_tj.SetCellString(
	   			3,a0221,0, parseInt(zg_temp)+parseInt(fzg_temp));
   	   }
   	   else{
   	    var i_col=4;
   		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].nv==0||obj[i].nv=='null')?"":obj[i].nv);//女
   		i_col=i_col+2;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].sm==0||obj[i].sm=='null')?"":obj[i].sm);//少数民族
	   	i_col=i_col+2;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].zd==0||obj[i].zd=='null')?"":obj[i].zd);//中共党员
	   	i_col=i_col+2;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].fzd==0||obj[i].fzd=='null')?"":obj[i].fzd);//非中共党员
	   	i_col=i_col+2;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].yjs==0||obj[i].yjs=='null')?"":obj[i].yjs);//研究生
	   	i_col=i_col+2;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dxbk==0||obj[i].dxbk=='null')?"":obj[i].dxbk);//大学本科
	   	i_col=i_col+2;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dxzz==0||obj[i].dxzz=='null')?"":obj[i].dxzz);//大学专科
	   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].zz==0||obj[i].zz=='null')?"":obj[i].zz);//中专
		    i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].gzjyx==0||obj[i].gzjyx=='null')?"":obj[i].gzjyx);//高中及以下
		    i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].bs==0||obj[i].bs=='null')?"":obj[i].bs);//博士
		    i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].ss==0||obj[i].ss=='null')?"":obj[i].ss);//硕士
		    i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].xs==0||obj[i].xs=='null')?"":obj[i].xs);//学士
		    i_col=i_col+2;	
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].xydy35==0||obj[i].xydy35=='null')?"":obj[i].xydy35);//35岁及以下
		    i_col=i_col+2;
		DCellWeb_tj.SetCellString(
				i_col,a0221,0,(obj[i].dy36xy40==0||obj[i].dy36xy40=='null')?"":obj[i].dy36xy40);//36岁至40岁
		i_col=i_col+2;
		DCellWeb_tj.SetCellString(
				i_col,a0221,0,(obj[i].dy41xy45==0||obj[i].dy41xy45=='null')?"":obj[i].dy41xy45);//41岁至45岁
		i_col=i_col+2;
		DCellWeb_tj.SetCellString(
				i_col,a0221,0,(obj[i].dy46xy50==0||obj[i].dy46xy50=='null')?"":obj[i].dy46xy50);//46岁至50岁
		i_col=i_col+2;
		DCellWeb_tj.SetCellString(
				i_col,a0221,0,(obj[i].dy51xy54==0||obj[i].dy51xy54=='null')?"":obj[i].dy51xy54);//51岁至54岁
		i_col=i_col+2;
		DCellWeb_tj.SetCellString(
				i_col,a0221,0,(obj[i].dy55xy59==0||obj[i].dy55xy59=='null')?"":obj[i].dy55xy59);//55岁至59岁
		i_col=i_col+2;
	    DCellWeb_tj.SetCellString(
	    		i_col,a0221,0,(obj[i].dy60==0||obj[i].dy60=='null')?"":obj[i].dy60);//60岁及以上
	    i_col=i_col+2;
   		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].pjnl==0||obj[i].pjnl=='null')?"":returnFloat(obj[i].pjnl));//平均年龄
   		i_col=i_col+1;	
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].zhccxy1==0||obj[i].zhccxy1=='null')?"":obj[i].zhccxy1);//不满1年
	   	i_col=i_col+2;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].zhccxy3==0||obj[i].zhccxy3=='null')?"":obj[i].zhccxy3);//1年至不满3年
	   	i_col=i_col+2;
	    DCellWeb_tj.SetCellString(
	    		i_col,a0221,0,(obj[i].zhccxy5==0||obj[i].zhccxy5=='null')?"":obj[i].zhccxy5);//3年至不满5年
	    i_col=i_col+2;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].zhccxy10==0||obj[i].zhccxy10=='null')?"":obj[i].zhccxy10);//5年至不满10年
	   	i_col=i_col+2;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].zhccxy11==0||obj[i].zhccxy11=='null')?"":obj[i].zhccxy11);//10年及以上
	   	i_col=i_col+2;		
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,(obj[i].a0197==0||obj[i].a0197=='null')?"":obj[i].a0197);//是否具有两年以上基层工作经历
	   	//合计
	   	// 中共党员
	   	var zg=obj[i].zd==0?"":obj[i].zd;
	   	if(zg=='undefined' || zg=="" || zg==null||zg==" "||zg=="null"){
	   		zg=0;
	   	}
	   	//非中共党员
	   	var fzg=obj[i].fzd==0?"":obj[i].fzd;
		if(fzg=='undefined' || fzg=="" || fzg==null||fzg==" "||fzg=="null"){
			fzg=0;
	   	}
		DCellWeb_tj.SetCellString(
	   			3,a0221,0, parseInt(zg)+parseInt(fzg));
   	 }
	}/// for end
	//重新计算其他的平均值
	var age_temp=DCellWeb_tj.GetCellString(42,208,0);//总平均年龄
	if(age_temp=='undefined' || age_temp=="" || age_temp==null||age_temp==" "){
		age_temp=0;
   	}
	var num_per=DCellWeb_tj.GetCellString(3,208,0);//人数
	if(num_per=='undefined' || num_per=="" || num_per==null||num_per==" "){
		num_per=1;
   	}
	DCellWeb_tj.SetCellString(
			42,208,0, returnFloat(age_temp/num_per));
	checkOther();//最后一行 0转空
	//小计
	for(var i=0;i<array_row.length;i++){
		total_tj(parseInt(array_row[i].split(",")[0])-1,
				parseInt(array_row[i].split(",")[0]),
				parseInt(array_row[i].split(",")[1]));
	}

	//总计 
	total_zj();

	//占比
	zhanbi();
	
	//隐藏占比
	yincangzb();

	//隐藏 全行为0的行
	displayzeroinit();
	
	//设置页面不可编辑
	DCellWeb_tj.WorkbookReadonly=true;
	
	DCellWeb_tj.SetCellString(
			53,2,0,"具有2年以上基层工作经历");
	
}
function checkOther(){
	for(var i=4;i<=53;i++){
		var value=DCellWeb_tj.GetCellString(i,208,0);
		if(parseInt(value)==0){
			DCellWeb_tj.SetCellString(
					i,208,0,'');
		}
	}
}
</script>
<!-- 新开窗口 -->
<script type="text/javascript">
var ctpath="<%=request.getContextPath()%>";
// //表格双击事件 
function myMouseDClick(col1, row1) {
	//参数错误 
	if(col1==0||col1==""||col1==" "||col1==null||col1=="undefined"||col1<3){
		return;
	}
	if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<4){
		return;
	}
	//数值为0 或 空
	var value=DCellWeb_tj.GetCellString(col1,row1,0);
	if(value==0||value==""||value==" "||value==null||value=="undefined"){
		return;
	}
	//平均值
	if(col_xho_end==col1){
		return;
	}
	
	// 占比
	if(col1>=col_xho_start&&col1<=col_xho_end){
		if(col1%2==1){
			return;
		}
	}
	if(col1>=col_xhj_start&&col1<=col_xhj_end){
		if(col1%2==0){
			return;
		}
	}
	
	var title1="";
	var title2="";
	title1=DCellWeb_tj.GetCellString(2,row1,0);
	for(var i=0;i<array_row.length;i++){//小计
		if(row1==(parseInt(array_row[i].split(",")[0])-1)){
			title1=DCellWeb_tj.GetCellString(1,row1,0);
		}
	}
	if(row1==4||row1==208){//总计
		title1=DCellWeb_tj.GetCellString(1,row1,0);
	}
	
	if(col1==3){
		title2=DCellWeb_tj.GetCellString(col1,2,0);
	}{
		title2=DCellWeb_tj.GetCellString(col1,3,0);
	}
	var title=title1+"-"+title2;
	var cxtj=document.getElementById("subWinIdBussessId").value;
	
	cxtj=cxtj+"$"+$('#zwlb_combo').attr("value")+"$"+col1+"$"+row1+"$"+title;

	//系统时间
	var date_time = new Date();
	var time=date_time.getHours()+ date_time.getMinutes()+date_time.getSeconds();
	$h.openWin('formanalysiszh'+time,'pages.customquery.formanalysis.formanalysiszhlist',title+'',1000,550,cxtj,'<%=request.getContextPath()%>');
}
var title_zj="";
</script>
<!-- 初始化 -->
<script type="text/javascript">
//统计小计
function total_tj(row_xj,row_s,row_e){//row_xj 小计统计行 row_s开始行 row_e 结束行
	
	//第3列单独计算
	var temp_v_r=parseInt(col_total(3,row_s,row_e));
	DCellWeb_tj.SetCellString(
			3,row_xj,0,temp_v_r==0?"":temp_v_r );
	
	var temp_v="";
	for(var i=col_xho_start;i<=col_xho_end;i++){
			if(i%2==1){//奇数列
				continue;
			}
	    //偶数列 小计
			
			temp_v=col_total(i,row_s,row_e);
			DCellWeb_tj.SetCellString(
	   			i,row_xj,0,temp_v==0?"":temp_v );
		}
	for(var i=col_xhj_start;i<=col_xhj_end;i++){
			if(i%2==0){//偶数列
				continue;
			}
	    //偶数列 小计
			
			temp_v=col_total(i,row_s,row_e);
			DCellWeb_tj.SetCellString(
	   			i,row_xj,0,temp_v==0?"":temp_v );
		}
}
function col_total(col_xj,row_s,row_e){
    var col_num=0;
    var v=0;
	    for(var i=row_s;i<=row_e;i++){
			v=DCellWeb_tj.GetCellString2(col_xj, i, 0);
		if(v=='undefined' || v=="" || v==null||v==" "){
			v=0;
		}
			col_num=col_num+parseInt(v);
    }
		
	   if(col_xj==col_xho_end){//平均年龄 为平均值 
		  var num_38_3=0;
		  var temp_38=0;//当前行平均年龄
		  var temp_3=0;//当前cell值
		  var num_38_3_a=0;//年龄之和
		  var num_rs=0;//总人数
		  var num_rs_a=0;
		  for(var i=row_s;i<=row_e;i++){//行
  			 temp_38=DCellWeb_tj.GetCellString2(col_xj, i, 0);//当前行平均年龄
  			if(temp_38==""||temp_38==" "||temp_38=="undefined"||temp_38==null){
  				temp_38=0;
  			}
  			 for(var j=(col_xho_end-10-4);j<=(col_xho_end-2);j++){//列 年龄
  				if(j%2==1){
  					 continue;
  				}
  				temp_3=DCellWeb_tj.GetCellString2(j, i, 0);//人数
 	  			if(temp_3==""||temp_3==" "||temp_3=="undefined"||temp_3==null){
 	  				temp_3=0;
 	  			}
	  			num_rs=num_rs+parseInt(temp_3);//当前行总人数
  			}
  			
  			num_rs_a=parseInt(num_rs_a)+parseInt(num_rs);//所有行总人数
  			 
  			num_38_3=parseInt(temp_38)*parseInt(num_rs);//当前行总年龄
  	
  			num_38_3_a=parseInt(num_38_3_a)+parseInt(num_38_3);//所有行总年龄
  			
  			num_rs=0;
  			num_38_3=0;
		  }
		if(num_rs_a==""||num_rs_a==" "||num_rs_a=="undefined"||num_rs_a==null||num_rs_a==0){
			num_rs_a=1;
		}
		 col_num=returnFloat(Math.round(parseInt(num_38_3_a)/parseInt(num_rs_a)*100)/100);
	   }
	   
	   return col_num;
}
//总计
function total_zj(){
	   //第3列单独统计 
	   var temp_zj_=parseInt(total_zj_ittt(3));
		DCellWeb_tj.SetCellString(
			3,4,0,temp_zj_==0?"":temp_zj_ );
		
	   var _zj_total=0;
	   for(var i=col_xho_start;i<=col_xho_end;i++){
			if(i%2==1){//奇数列
				continue;
			}
			_zj_total=total_zj_ittt(i);
		    //偶数列 小计
			DCellWeb_tj.SetCellString(i,4,0,_zj_total==0?"":_zj_total );
		}
	   for(var i=col_xhj_start;i<=col_xhj_end;i++){
			if(i%2==0){//
				continue;
			}
			_zj_total=total_zj_ittt(i);
		    //偶数列 小计
			DCellWeb_tj.SetCellString(i,4,0,_zj_total==0?"":_zj_total );
		}
}
//叠加行值
function total_zj_ittt(i){
	   var zj_total=0;
	   var zj_temp_total=0;
	   for(var j=0;j<array_row.length;j++){
		   zj_temp_total=DCellWeb_tj.GetCellString2(i, parseInt(array_row[j].split(",")[0])-1, 0);
		   if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
				zj_temp_total=0;
		   }
		   zj_total=zj_total+parseInt(zj_temp_total);
	   }
	   //加上最后一行的值
	   var lastvalue=DCellWeb_tj.GetCellString2(i, parseInt(array_row[array_row.length-1].split(",")[1])+1, 0);
	   if(lastvalue=='undefined' || lastvalue=="" || lastvalue==null||lastvalue==" "){
		   lastvalue=0;
	   }
	   zj_total=zj_total+parseInt(lastvalue);
		if(i==col_xho_end){
			zj_total=pj_38_zj(i);
		}
		return zj_total;
}
//总计  平均年龄
function pj_38_zj(i){
	   var zj_total=0;
	   var zj_temp_total=0;//当前行平均年龄
	   var zj_temp_3=0;//当前行总人数
	 
	   var zrs=0;//总人数
	   for(var j=0;j<array_row.length;j++){//循环职务大类
		   for(var n=parseInt(array_row[j].split(",")[0]);n<=parseInt(array_row[j].split(",")[1]);n++){//循环职务等级
			   zj_temp_total=DCellWeb_tj.GetCellString2(i, n, 0);
			   if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
				   zj_temp_total=0;
			   }
			   
			   zj_temp_3=r_total_r(n);
			   if(zj_temp_3=='undefined' || zj_temp_3=="" || zj_temp_3==null||zj_temp_3==" "){
				   zj_temp_3=0;
			   }
			   zrs=zrs+zj_temp_3;
			   zj_total=zj_total+parseInt(zj_temp_total)*parseInt(zj_temp_3);
		   }
	    }
		
		//最后一行的平均年龄
		var lastage=DCellWeb_tj.GetCellString2(i, parseInt(array_row[array_row.length-1].split(",")[1])+1, 0);
		if(lastage=='undefined' || lastage=="" || lastage==null||lastage==" "){
			lastage=0;
		}
		//最后一行人数
		var lastnumber=DCellWeb_tj.GetCellString2(3, parseInt(array_row[array_row.length-1].split(",")[1])+1, 0);
		if(lastnumber=='undefined' || lastnumber=="" || lastnumber==null||lastnumber==" "){
			lastnumber=0;
		}
		
		zj_total=parseInt(zj_total)+(parseInt(lastage)*parseInt(lastnumber));
		zrs=parseInt(zrs)+parseInt(lastnumber);
		
		if(zrs=='undefined' || zrs=="" || zrs==null||zrs==" "||zrs==0){
			zrs=1;
		}
		return returnFloat(Math.round(parseInt(zj_total)/parseInt(zrs)*100)/100);
}
function returnFloat(value){
	/// var value=Math.round(parseFloat(value)*100)/100;
	 var xsd=value.toString().split(".");
	 if(xsd.length==1){
	 value=value.toString()+".00";
	 return value;
	 }
	 if(xsd.length>1){
	 if(xsd[1].length<2){
	 value=value.toString()+"0";
	 }
	 return value;
	 }
	}

//占比 计算
function zhanbi(){
	   var temp_hj=0;
	   var temp_col_num=0;
	   var biliz=0;
	   var j_v=parseInt(array_row[array_row.length-1].split(",")[1]);//总行数
	   for(var j=4;j<=j_v+1;j++){//循环行
			 	//合计
				temp_hj=DCellWeb_tj.GetCellString2(3, j, 0);
				if(temp_hj=='undefined' || temp_hj=="" || temp_hj==null||temp_hj==" "||temp_hj==0){
					temp_hj=1;
				}
			   for(var i=col_xho_start;i<=col_xho_end;i++){//循环列
				   if(i%2==0){
		 				continue;
		 			}
					temp_col_num=DCellWeb_tj.GetCellString2(i-1, j, 0);
					if(temp_col_num=='undefined' || temp_col_num=="" || temp_col_num==null||temp_col_num==" "){
						temp_col_num=0;
					}
					biliz=Math.round(temp_col_num/temp_hj*10000)/100;
					DCellWeb_tj.SetCellString(i,j,0,biliz==0?"":returnFloat(biliz)+"%" );
			   }
			   for(var i=col_xhj_start;i<=col_xhj_end;i++){//循环列
					if(i%2==1){
						continue;
					}
					
					temp_col_num=DCellWeb_tj.GetCellString2(i-1, j, 0);
					if(temp_col_num=='undefined' || temp_col_num=="" || temp_col_num==null||temp_col_num==" "){
						temp_col_num=0;
					}
					biliz=Math.round(temp_col_num/temp_hj*10000)/100;
					DCellWeb_tj.SetCellString(i,j,0,biliz==0?"":returnFloat(biliz)+"%" );
			   }
	   }
	  
}
//隐藏占比
function yincangzb(){
	   for(var i=col_xho_start;i<=col_xho_end;i++){//循环列
		   if(i%2==0){//偶数跳过奇数隐藏
				continue;
			}
		   DCellWeb_tj.SetColHidden(i, i);
		}
	   for(var i=col_xhj_start;i<=col_xhj_end;i++){//循环列
		   if(i%2==1){//奇数跳过偶数隐藏
				continue;
			}
		   DCellWeb_tj.SetColHidden(i, i);
		}
}
//隐藏 全行为0的行
function displayzeroinit(){
	  var v=0;
	  var j_v=parseInt(array_row[array_row.length-1].split(",")[1])+1;//总行数
		 for(var i=(parseInt(array_row[0].split(",")[0])-2);i<=j_v;i++){//行
			 v=DCellWeb_tj.GetCellString(3, i, 0);//判断合计是否为零  ，为零 则为行全零
			 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
				 DCellWeb_tj.SetRowHidden(i, i);
			 }else{
				 DCellWeb_tj.SetRowUnhidden(i, i);
			 }
		 }
}
</script>
<!-- 条件 -->
<script type="text/javascript">
//根据选择职位，显示 统计 
var zwlb_data ="";
var xy_zwlb="1";
function zwlb_xy(){
	  xy_zwlb=document.getElementById("xy_zwlb").value;
	  zwlb_data=document.getElementById("zwlb_l").value;
	  clear4();
	  if(zwlb_data=='undefined' || zwlb_data=="" || zwlb_data==null || zwlb_data==" "){
		  if(xy_zwlb=='0'){
			  xsall();//显示所有行
		  }else{
			  displayzeroinit();//隐藏全0行
		  }
		  total_zj_xy();//总计
		  zhanbi_xy_zwlb();//重新统计第4行占比
		  DCellWeb_tj.SetRowUnhidden(4, 4);//显示
		  return;
	  }
	  
	  if(zwlb_data=='all'||zwlb_data=='ALL'){
		  if(xy_zwlb=='0'){
		  	xsall();//显示所有行
		  }else{
			  displayzeroinit();//隐藏全0行
		  }
		  total_zj();//重新统计
		  zhanbi_xy_zwlb();//重新统计第4行占比
		  DCellWeb_tj.SetRowUnhidden(4, 4);//显示
		  return;
	  }
	  var strs= new Array(); //定义一数组 存储职务类别代码 
	  var strs_m= new Array(); //定义一数组  存储行数 
	  strs=zwlb_data.split(","); //字符分割
	  DCellWeb_tj.SetRowhidden(parseInt(array_row[0].split(",")[0])-2, parseInt(array_row[array_row.length-1].split(",")[1])+1);//隐藏所有行
	  for (var i=0;i<strs.length ;i++ ) { 
		  strs_m=map_zwlb[strs[i]].split(",");
		  DCellWeb_tj.SetRowUnhidden(strs_m[0], strs_m[1]);//显示
		  if(strs_m[1]!=(parseInt(array_row[array_row.length-1].split(",")[1])+1)){//不是最后一行有小计
			  //小计
			  DCellWeb_tj.SetRowUnhidden(parseInt(strs_m[0])-1, parseInt(strs_m[0])-1);//显示
		  }
		  //重新统计
		  DCellWeb_tj.SetRowUnhidden(4, 4);//显示
		  total_zj_xy();//总计
		  zhanbi_xy_zwlb();
		  //zhanbi();//重新统计占比
		  if(xy_zwlb=='1'){//隐藏行
			  displayzero_zwlb(strs_m[0], strs_m[1]);//隐藏 0行
		  }
	  } 
}
//清空第3行的数据
function clear4(){
	  for(var i=3;i<=tjxm_arr.length-1;i++){
		  DCellWeb_tj.SetCellString(i,4,0,'');
	  }
}
//显示所有行
function xsall(){
	DCellWeb_tj.SetRowUnhidden(parseInt(array_row[0].split(",")[0])-2, parseInt(array_row[array_row.length-1].split(",")[1])+1);
}
//总计
function total_zj_xy(){
	   //第3列单独统计 
	   var temp_zj_=parseInt(total_zj_ittt_xy(3));
		DCellWeb_tj.SetCellString(
			3,4,0,temp_zj_==0?"":temp_zj_ );
		
	   var _zj_total=0;
	   for(var i=col_xho_start;i<=col_xho_end;i++){
			if(i%2==1){//奇数列
				continue;
			}
			_zj_total=total_zj_ittt_xy(i);
			
		    //偶数列 小计
			DCellWeb_tj.SetCellString(i,4,0,_zj_total==0?"":_zj_total );
		}
	   for(var i=col_xhj_start;i<=col_xhj_end;i++){
			if(i%2==0){//
				continue;
			}
			_zj_total=total_zj_ittt_xy(i);
		    //偶数列 小计
			DCellWeb_tj.SetCellString(i,4,0,_zj_total==0?"":_zj_total );
		}
}
function total_zj_ittt_xy(i){
	   var zj_total=0;
	   var zj_temp_total=0;
	   for(var j=0;j<array_row.length;j++){
		   if(DCellWeb_tj.IsRowHidden(parseInt(array_row[j].split(",")[0])-1,0)==false){//行显示
				zj_temp_total=DCellWeb_tj.GetCellString2(i, parseInt(array_row[j].split(",")[0])-1, 0);
				if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
						zj_temp_total=0;
				}
				zj_total=zj_total+parseInt(zj_temp_total);
		 	}
	   }
	   if(DCellWeb_tj.IsRowHidden(parseInt(array_row[array_row.length-1].split(",")[1])+1,0)==false){//行显示
		   //加上最后一行的值
		   var lastvalue=DCellWeb_tj.GetCellString2(i, parseInt(array_row[array_row.length-1].split(",")[1])+1, 0);
		   if(lastvalue=='undefined' || lastvalue=="" || lastvalue==null||lastvalue==" "){
			   lastvalue=0;
		   }
		   zj_total=zj_total+parseInt(lastvalue);
	   }
	 	if(i==col_xho_end){
	 		zj_total=total_zj_ittt_38(i);
	 	}
	 	
		return zj_total;
}
//平均年龄
function total_zj_ittt_38(i){
	  var zj_total=0;//总年龄
	   var zj_temp_total=0;//本行平均年龄
	   var zj_temp_3=0;//本行总人数
	   var zrs=0;//总人数
	   for(var j=0;j<array_row.length;j++){
		   if(DCellWeb_tj.IsRowHidden(parseInt(array_row[j].split(",")[0])-1,0)==false){//行显示
			   for(var n=parseInt(array_row[j].split(",")[0]);n<=parseInt(array_row[j].split(",")[1]);n++){//循环职务等级
				   zj_temp_total=DCellWeb_tj.GetCellString2(i, n, 0);//本行平均年龄
				   if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
					   zj_temp_total=0;
				   }
				   zj_temp_3=r_total_r(n);//本行总人数
				   if(zj_temp_3=='undefined' || zj_temp_3=="" || zj_temp_3==null||zj_temp_3==" "){
					   zj_temp_3=0;
				   }
				   zj_total=zj_total+parseInt(zj_temp_total)*parseInt(zj_temp_3);//本行总人数 叠加
				   zrs=zrs+zj_temp_3;//本行总人数 叠加
			   }
		 	}
	   }
	   if(DCellWeb_tj.IsRowHidden(parseInt(array_row[array_row.length-1].split(",")[1])+1,0)==false){//行显示
		 //最后一行的平均年龄
			var lastage=DCellWeb_tj.GetCellString2(i, parseInt(array_row[array_row.length-1].split(",")[1])+1, 0);
			if(lastage=='undefined' || lastage=="" || lastage==null||lastage==" "){
				lastage=0;
			}
			//最后一行人数
			var lastnumber=DCellWeb_tj.GetCellString2(3, parseInt(array_row[array_row.length-1].split(",")[1])+1, 0);
			if(lastnumber=='undefined' || lastnumber=="" || lastnumber==null||lastnumber==" "){
				lastnumber=0;
			}
			zj_total=parseInt(zj_total)+parseInt(lastage)*parseInt(lastnumber);
			zrs=parseInt(zrs)+parseInt(lastnumber);
	   }
	   
	 	if(zrs=='undefined' || zrs=="" || zrs==null||zrs==" "||zrs==0){
	 		zrs=1;
		}
	 	return returnFloat(Math.round(parseInt(zj_total)/parseInt(zrs)*100)/100);
}
//小计 平均年龄对应人数
function r_total_r(row){
	   var zj_temp_3=0;
	   var zj_temp_cell=0;
	   for(var j=(col_xho_end-10-4);j<=(col_xho_end-2);j++){//列
			if(j%2==1){
				 continue;
			}
			zj_temp_cell=DCellWeb_tj.GetCellString2(j, row, 0);
			if(zj_temp_cell=='undefined' || zj_temp_cell=="" || zj_temp_cell==null||zj_temp_cell==" "){
				zj_temp_cell=0;
			}
			zj_temp_3=zj_temp_3+parseInt(zj_temp_cell);
	 }
	   return zj_temp_3;
}
//紧重新统计第4行的占比
function zhanbi_xy_zwlb(){
	   var temp_hj=0;
	   var temp_col_num=0;
	   var biliz=0;
	   for(var j=4;j<=4;j++){//循环行
		   
		   	//合计
			temp_hj=DCellWeb_tj.GetCellString2(3, j, 0);
			if(temp_hj=='undefined' || temp_hj=="" || temp_hj==null||temp_hj==" "||temp_hj==0){
				temp_hj=1;
			}
		   for(var i=col_xho_start;i<=col_xho_end;i++){//循环列
			   if(i%2==0){
					continue;
				}
			   temp_col_num=DCellWeb_tj.GetCellString2(i-1, j, 0);
			   if(temp_col_num=='undefined' || temp_col_num=="" || temp_col_num==null||temp_col_num==" "){
					temp_col_num=0;
				}
				biliz=Math.round(temp_col_num/temp_hj*10000)/100;
				DCellWeb_tj.SetCellString(i,j,0,biliz==0?"":returnFloat(biliz)+"%" );
		   }
		  
		   for(var i=col_xhj_start;i<=col_xhj_end;i++){//循环列
			   if(i%2==1){
					continue;
				}
			   temp_col_num=DCellWeb_tj.GetCellString2(i-1, j, 0);
			   if(temp_col_num=='undefined' || temp_col_num=="" || temp_col_num==null||temp_col_num==" "){
					temp_col_num=0;
				}
				biliz=Math.round(temp_col_num/temp_hj*10000)/100;
				DCellWeb_tj.SetCellString(i,j,0,biliz==0?"":returnFloat(biliz)+"%" );
		   }
	   }
}
//隐藏 0行
function displayzero_zwlb(start,end){
	  var v=0;
		 for(var i=parseInt(start);i<=parseInt(end);i++){//行
			 v=DCellWeb_tj.GetCellString(3, i, 0);//判断合计是否为零  ，为零 则为行全零
			 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
				 DCellWeb_tj.SetRowHidden(i, i);
			 }
		 }
		 v=DCellWeb_tj.GetCellString(3, parseInt(start)-1, 0);//判断合计是否为零  ，为零 则为行全零
		 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
			 DCellWeb_tj.SetRowHidden(parseInt(start)-1,parseInt(start)-1);
		 }
}
//隐藏占比
function yincangzb(){
	   for(var i=col_xho_start;i<=col_xho_end;i++){//循环列
		   if(i%2==0){//偶数跳过奇数隐藏
				continue;
			}
		   DCellWeb_tj.SetColHidden(i, i);
		}
	   for(var i=col_xhj_start;i<=col_xhj_end;i++){//循环列
		   if(i%2==1){//奇数跳过偶数隐藏
				continue;
			}
		   DCellWeb_tj.SetColHidden(i, i);
		}
}
//显示占比
function xszhanbi(){
	   DCellWeb_tj.SetColUnhidden(3, tjxm_arr.length-1);
}
//隐藏全零行
function displayzero(xy){
	  zwlb_xy();
}
function xs_zwlb_zero(){
	  zwlb_xy();
}
var map_tjxm={
		'1':'4,11',//女少非
		'2':'12,21',//学历
		'3':'22,27',//学位
		'4':'28,42',//年龄
		'5':'43,52',//任现职务层次年限
		'6':'53,53'//具有2年以上基层工作经历
	  };
//统计项目，条件
function tjxm_xy(){
	var tjxm_col_h=document.getElementById("tjxm_col_h").value;
	
	if(tjxm_col_h=='all'||tjxm_col_h=='ALL'||tjxm_col_h==""
			||tjxm_col_h==null||tjxm_col_h=='undefined'){//列全部显示
		DCellWeb_tj.SetColUnhidden(4, 53);
		if(document.getElementById("yczb").checked==true){//占比隐藏
			yincangzb();
		}
		return;
	}
	var strs= new Array(); //定义一数组 存储职务类别代码 
	var strs_m= new Array(); //定义一数组  存储行数 
	strs=tjxm_col_h.split(","); //字符分割
	if(strs.length>0){
		DCellWeb_tj.SetColhidden(4, 53);
	}
	for(var i=0;i<strs.length;i++){
		strs_m=map_tjxm[strs[i]].split(",");
		DCellWeb_tj.SetColUnhidden(parseInt(strs_m[0]),parseInt(strs_m[1]));
		if(document.getElementById("yczb").checked==true){//占比隐藏
			if(parseInt(strs_m[1])<=col_xho_end){
				for(var j=parseInt(strs_m[0]);j<=parseInt(strs_m[1]);j++){//循环列
					   if(j%2==0){//偶数跳过奇数隐藏
							//continue;
						}else{
						   DCellWeb_tj.SetColHidden(j, j);
						}
				}
			}else if(col_xho_end<parseInt(strs_m[1])<=col_xhj_end){
				for(var j=parseInt(strs_m[0]);j<=parseInt(strs_m[1]);j++){//循环列
					   if(j%2==1){//奇数跳过偶数隐藏
							continue;
						}else{
						   DCellWeb_tj.SetColHidden(j, j);
						}
				}
			}
		}
	}
	

}
</script>
<script type="text/javascript">
function exportData(){
	DCellWeb_tj.ExportExcelDlg();
}
</script>
<script type="text/javascript">
function xianyin_onclick(obj){
	if(document.getElementById("xianyin").checked==true){//隐藏全零行
		document.getElementById("xy_zwlb").value=1;
		xs_zwlb_zero();
	}else if(document.getElementById("xianyin").checked==false){//显示全零行
		document.getElementById("xy_zwlb").value=0;
		displayzero(0);
	}
}
function yczb_onclick(){//显示隐藏
	tjxm_xy();
}
</script>