<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit2.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="commform/basejs/json2.js"></script>
<script src="<%=request.getContextPath()%>/js/echarts.js"> </script>
<style>
/* 背景色 */
	/* body {
		background-color: #DFE8F6;
	} */
	#suppertext{
		width: 99%;
		border: none;
		overflow:hidden;
		resize:none;
		white-space:pre-wrap;
		white-space:-moz-pre-wrap;
	 	white-space:-o-pre-wrap;
	 	word-wrap:break-word;
	}
	#tableTest{
		margin-top: 20px;
	}
	#tableTest tr td{
		font-size: 16px;
		font-family: "黑体";
	}
	
	.headerText {
				font-size: 24px;
				line-height: 26px;
				font-weight: bold;
				text-align: center;				
	}

	
	.structText {
		font-size: 20px;
		/* line-height: 20px; */
		font-weight: bold;
		font-family: 楷体_GB2312;
	}
	
	.nested {
		border: 1px solid #000000;
		border-collapse:collapse;				
	}
	
	.nested td {
		border: 1px solid #000000;	
		empty-cells: show;				
	}

	.fzxq{
		width: 9%;
		text-align: center;
		font-family: 宋体;
		font-size: 16px
	}
			
	/* .hover{background: #38AAE1;} */
	
</style>

<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill/>
	<odin:buttonForToolBar text="上一个" id="up" icon="images/icon/table.gif"/>
	<odin:separator/>
	<odin:buttonForToolBar text="下一个" id="next" icon="images/icon/table.gif" isLast="true"/>
</odin:toolBar>

<odin:hidden property="ids" />
<odin:hidden property="gridB0111" />
<odin:hidden property="cueRowIndex" />
<odin:hidden property="type" />

<div id="btnToolBarDiv" ></div>
<div style="float:left;width:280px;height:290px" >
		<odin:editgrid property="peopleInfoGrid" title="机构信息列表" bbarId="pageToolBar" pageSize="500"
					autoFill="false" width="100" height="290" >
					<odin:gridJsonDataModel>
						<%-- <odin:gridDataCol name="personcheck" /> --%>
						<odin:gridDataCol name="b0111" />
						<odin:gridDataCol name="b0101" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="b0111" width="100" header="机构主键"
							align="center" editor="text" edited="false" hidden="true"/>
						<odin:gridEditColumn2 dataIndex="b0101" width="250" header="机构名称" isLast="true"
							align="center" editor="text" edited="false"/>
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
					        data:[]
					    }
					</odin:gridJsonData>
				</odin:editgrid>
</div>
<div id="div1" style="width:1065px;margin-left: 40px;overflow: auto;">
		<div id="MainBody""> <!--主体内容-->
		   <odin:tab id="tab" width="100%" ><!-- tabchange="grantTabChange" -->
		   <odin:tabModel>
		    <odin:tabItem title="&nbsp结构模型&nbsp" id="tab1"></odin:tabItem>
		    <odin:tabItem title="&nbsp班子成员花名册&nbsp" id="tab2" isLast="true"></odin:tabItem>
		   </odin:tabModel>
		   
		   
		   <odin:tabCont itemIndex="tab1">
		   <div id="jgmx" style="width:100%;padding-left: 30px;padding-right: 30px;overflow: auto;">
		   <table width="100%" border="0" >
				<tr height="60">
					<td class="headerText"><span class="headerText" id="unit" name="unit"></span>领导班子结构模型分析表</td>
				</tr>
			</table>
			<div>
			<table border="0" cellpadding="0" cellspacing="0" style="float: left;">
				<tr height="32">
					<td class="structText" style="width: 110px">整体状况：</td>
					<td  style="width: 240px"><span id="ztzk"></span></td>
				</tr>
				<tr height="32">
					<td class="structText" style="width: 110px">显色情况：</span></td>
					<td  style="width: 20px" id="backColor" ></td>
				</tr>
			</table>
			
			<table class="nested" style="text-align: center;float: right;" >
		    <tr style="width: 100%;height: 20px;">
		       <td style="width: 150px" rowspan="3"> <font class="font-tab">附：评分标准</font></td>
		       <td style="width: 150px;background-color: orangeRed"></td>
		       <td style="width: 150px"> 
					<font class="font-tab">&nbsp;&nbsp;&nbsp;小于</font>
					<input id="redf" type="text" value="" style="width: 60px"/>
		       </td>
		    </tr>
		    <tr style="width: 100%;height: 20px;">
		       <td style="width: 150px;background-color: yellow"></td>
		       <td> 
					<!-- <input id="" type="text" value="10" style="width: 60px"/> -->
					<font class="font-tab"></font><input id="redf" type="text" value="60" style="width: 60px;display: none"/>
		       </td>
		    </tr>
		    <tr style="width: 100%;height: 20px;">
		       <td style="width: 150px;background-color: green"></td>
		       <td> 
					<font class="font-tab">大于等于</font><input id="greenf" type="text" value="" style="width: 60px"/>
		       </td>
		    </tr>
		   </table>	
		    </div>
		    
			<table id="zsTable" style="width:100%;height:60px;margin-top: 30px" cellpadding="0" cellspacing="0" class="nested" > 
					<tr height="50">
						<td colspan="4" style="text-align: center;font-family: 宋体;font-weight: bolder;;font-size: 20px;">一、分值详情</td>
					</tr>
					<tr height="50">
						<td class="fzxq">领导班子<br/>人数</td>
						<td class="fzxq">性别</td>
						<td class="fzxq">党派</td>
						<td style="width: 30%" rowspan="8">
							<div id="div2" style="width:100%;height:100%;text-align:center;">
    						</div>
						</td>
					</tr>
					<tr id="yp1" height="50">
						<td  style="text-align: center;"><font id="bzf"></font></td>
						<td style="text-align: center;"><font id="xbf"></font></td>
						<td style="text-align: center;"><font id="dpf"></font></td>
					</tr>	
					<tr height="50">
						<td class="fzxq">民族</td>
						<td class="fzxq">年龄</td>
						<td class="fzxq">学历</td>
					</tr>
					<tr id="yp1" height="50">
						<td style="text-align: center;"><font id="mzf"></font></td>
						<td style="text-align: center;"><font id="nlf"></font></td>
						<td style="text-align: center;"><font id="xlf"></font></td>
					</tr>	
					<tr height="50">
						<td class="fzxq">专业</td>
						<td class="fzxq">地域</td>
						<td class="fzxq">熟悉领域</td>
					</tr>	
					<tr id="yp1" height="50">
						<td style="text-align: center;"><font id="zyf"></font></td>
						<td style="text-align: center;"><font id="dyf"></font></td>
						<td style="text-align: center;"><font id="knowf"></font></td>
					</tr>
					
					<tr>
						<td class="fzxq" height="50">经历</td>
						<td class="fzxq">总得分</td>
						<td></td>
					</tr>
					<tr>
						<td style="text-align: center;" height="50"><font id="jlf"></font></td>
						<td style="text-align: center;"><font id="total"></font></td>
						<td></td>
					</tr>
						
			</table>
			
			<table id="tabNature" class="nested" style="margin-top: 30px;text-align: center;width:100%;height:50px;">
				<tr  height="50">
					<td align="center" colspan="5" style="text-align: center;font-family: 宋体;font-weight: bolder;;font-size: 20px;">二、自然结构</td>
				</tr>
				<tr  height="50">
					<td style="width: 20%">项目</td>
					<td style="width: 20%">要素</td>
					<td style="width: 20%">目标</td>
					<td style="width: 20%">现状</td>
					<td style="width: 20%">一票否决</td>
				</tr>
			</table>

		  </div>  
		   </odin:tabCont>
		   
		   
		   
		   <odin:tabCont itemIndex="tab2">
		   
		      <div id="jgmx1" style="width:100%;overflow: scroll;">
		   <table width="100%" border="0" >
				<tr height="50">
					<td class="headerText"><span class="headerText" id="unit1" name="unit1"></span>领导班子成员花名册</td>
				</tr>
			</table>
		   <odin:editgrid property="TrainingInfoGrid" height="document.body.clientHeight-113" title="" autoFill="true" bbarId="pageToolBar" sm="row" remoteSort="true" hasRightMenu="true" rightMenuId="updateM" isFirstLoadData="false" pageSize="20"  url="/"  >
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a0101" />
					<odin:gridDataCol name="a0192a" />
 			  		<odin:gridDataCol name="a0107"  /> 
 			  		<odin:gridDataCol name="a0111a"  />
 			  		<odin:gridDataCol name="a0221"  /> 
 			  		<odin:gridDataCol name="a0288"  /> 
 			  		<odin:gridDataCol name="a0192e"  /> 
			  		<odin:gridDataCol name="a0192f" isLast="true"/> 
				</odin:gridJsonDataModel>
					<odin:gridColumnModel>
				    <odin:gridRowNumColumn></odin:gridRowNumColumn>
				  	<odin:gridEditColumn2 header="姓名" align="center" dataIndex="a0101" edited="false" editor="text" width="100"/>
				  	<odin:gridEditColumn2 header="现任职务" align="center" dataIndex="a0192a" edited="false" editor="text" width="280"/>
				  	<odin:gridEditColumn2 header="出生年月" align="center" dataIndex="a0107" editor="text"  edited="false"  width="100" /> 
 				  	<odin:gridEditColumn2 header="籍贯" align="center" dataIndex="a0111a" editor="text" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="现职务层次" align="center" dataIndex="a0221" codeType="ZB09" editor="select" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="任现职务层次时间" align="center" dataIndex="a0288" editor="text" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="现职级" align="center" dataIndex="a0192e" editor="select" edited="false" width="100"/> 
				  	<odin:gridEditColumn2 width="100" header="任现职级时间" dataIndex="a0192c" editor="text" edited="false"  isLast="true"/>
					</odin:gridColumnModel>
			</odin:editgrid>
		   
		   </div>
		   </odin:tabCont>
		   
		   </odin:tab>
		   </div>
</div>

<script type="text/javascript">

var myChart;
Ext.onReady(function(){
	var width=document.body.clientWidth;
	var height=document.body.clientHeight;
	Ext.getCmp("peopleInfoGrid").setHeight(height-25);
	document.getElementById('div1').style.height=(height-30)+"px";
	
	myChart = echarts.init(document.getElementById('div2'));
	option = {
		    title: {
		        text: '班子结构',
		        left: 'center'
		    },
		    radar: [
		        {
		            indicator: [
		                { text: '人数', max:10  },
		                { text: '性别', max:10  },
		                { text: '党派', max:10  },
		                { text: '年龄', max:10  },
		                { text: '民族', max:10  },
		                { text: '学历', max:10  },
		                { text: '专业', max:10  },
		                { text: '地域', max:10  },
		                { text: '熟悉领域', max:10  },
		                { text: '经历', max:10  }
		            ],
		            center: ['50%', '50%'],
		            radius: 130
		        }
		    ],
		    series: [
		        {
		            name: '得分',
		            type: 'radar',
		            data: [
		                {
		                    value: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
		                    name: '得分'
		                }
		            ]
		        }
		    ]
		}; 
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
});

function appendScore(obj){
	var val = eval('(' + obj + ')');
	if("null"==val||!val){
		return;
	}
	var statustype = val.statustype;
	if("0"==statustype){
		$("#ztzk").html("<font style='color: red'>红（一票否决）</font>");
		$("#backColor").css("background-color","red");
	}else if("1"==statustype){
		$("#ztzk").html("<font style='color: orangeRed'>红（非一票否决，总分未合格）</font>");
		$("#backColor").css("background-color","orangeRed");
	}else if("2"==statustype){
		$("#ztzk").html("<font style='color: yellow'>黄</font>");
		$("#backColor").css("background-color","yellow");
	}else if("3"==statustype){
		$("#ztzk").html("<font style='color: green'>绿</font>");
		$("#backColor").css("background-color","green");
	}
	
	var bzf = val.bzf;
	var xbf = val.xbf;
	var dpf = val.dpf;
	var mzf = val.mzf;
	var nlf = val.nlf;
	var xlf = val.xlf;
	var zyf = val.zyf;
	var dyf = val.dyf;
	var knowf = val.knowf;
	var jlf = val.jlf;
	var score = val.score;
	var redf = val.redf;
	var greenf = val.greenf;
	var m = val.max;//最大分值
	
	$("#bzf").text(bzf);
	$("#xbf").text(xbf);
	$("#dpf").text(dpf);
	$("#mzf").text(mzf);
	$("#nlf").text(nlf);
	$("#xlf").text(xlf);
	$("#zyf").text(zyf);
	$("#dyf").text(dyf);
	$("#knowf").text(knowf);
	$("#jlf").text(jlf);
	$("#total").text(score);
	$("#redf").val(redf);
	$("#greenf").val(greenf);
	
	option = {
		    title: {
		        text: '班子结构',
		        left: 'center'
		    },
		    radar: [
		        {
		            indicator: [
		                { text: '人数', max:m  },
		                { text: '性别', max:m  },
		                { text: '党派', max:m  },
		                { text: '年龄', max:m  },
		                { text: '民族', max:m  },
		                { text: '学历', max:m  },
		                { text: '专业', max:m  },
		                { text: '地域', max:m  },
		                { text: '熟悉领域', max:m  },
		                { text: '经历', max:m  }
		            ],
		            center: ['50%', '50%'],
		            radius: 130
		        }
		    ],
		    series: [
		        {
		            name: '得分',
		            type: 'radar',
		            data: [
		                {
		                    value: [bzf, xbf, dpf, nlf, mzf, xlf, zyf, dyf, knowf, jlf],//'人数','性别','党派'，'年龄'，'民族'，'学历'，'专业'，'地域'，'熟悉领域'，'经历'
		                    //value: [10, 10, 10, 10, 10, 10, 10, 10, 10, 10],
		                    name: '得分'
		                }
		            ]
		        }
		    ]
		}; 
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

/* t.category, t.project, t.quantity, t.one_ticket_veto,t.count, t.id */
function appendTable(index,obj){
	$("tr").remove(".newTr") ;
	
	var val = eval('(' + obj + ')');
	//console.log(val.tr0);
	if(val){
		for(var i=0;i<index;i++){
			var tri = "val.tr"+i;
			var v = eval('(' + tri + ')');
			var aaa = "a"+i;
			//console.log(v);
			var htmlList = '<tr class=\"newTr\" id=\"'+v.id+'\">';
			//htmlList += '<td><input style=\"display: none\" type=\"text\" width=\"80\" value=\"'+v.a1700+'\"></td>';
			//htmlList += '<td><input style=\"display: none\" type=\"text\" width=\"80\" value=\"'+v.a0000+'\"></td>';
			htmlList += '<td height="50">'+v.category+'</td>';
			htmlList += '<td height="50">'+v.project+'</td>';
			htmlList += '<td height="50">'+v.quantity+'</td>';
			htmlList += '<td height="50"><a id="'+aaa+'" href="javaScript:void(0)">'+v.count+'</a></td>';
			htmlList += '<td height="50">'+v.one_ticket_veto+'</td>';
			htmlList += '</tr>';
			//在行最后添加数据
		    $("#tabNature tr:last").after(htmlList);
			
		    jueryArea(aaa,v.id);
		}
		
	}
}

function jueryArea(a,obj){
	var v = eval('(' + a + ')');
	$(v).click(function() {
		$h.openPageModeWin('backCheck','pages.templateconf.BackCheck','人员反查详情',900,520,obj,'<%=request.getContextPath()%>',window);
	});
}

</script>
