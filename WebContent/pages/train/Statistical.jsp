<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.js"></script>
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
</script>

<odin:groupBox title="统计条件">
<table style="width: 100%;">
	<tr>
		<tags:PublicTextIconEdit property="tj1" codetype="ZB09" label="职务层次" readonly="true"/>
		<tags:PublicTextIconEdit3 property="tj2" label="机构单位" readonly="true" codetype="orgTreeJsonData"></tags:PublicTextIconEdit3>
		<odin:textEdit property="tj3" label="班次名称" ></odin:textEdit>
		<odin:textEdit property="tj4" width="200" label="姓名和身份证号" ></odin:textEdit>
	</tr>
</table>
</odin:groupBox>
<div class="chart">
	<table>
		<tr>
			<td style="width: 27%">
				<div id="myChart" style="height:450px;width:100%"></div>
			</td>
			<td style="width: 40%">
				<odin:editgrid2 property="grid1" hasRightMenu="false" topBarId="btnToolBar1" autoFill="true"  bbarId="pageToolBar"  url="/">
			<odin:gridJsonDataModel>
				<%-- <odin:gridDataCol name="pcheck" /> --%>
				<odin:gridDataCol name="trainid" />
				<odin:gridDataCol name="a1131" />
				<odin:gridDataCol name="g11020"/>
				<odin:gridDataCol name="a1101"/>
				<odin:gridDataCol name="a1114"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
				<%-- <odin:gridEditColumn2 locked="true" header="selectall" width="30" menuDisabled="true" 
							editor="checkbox" dataIndex="pcheck" edited="true"
							hideable="false" gridName="gridcq"  menuDisabled="true" />  --%>
				<odin:gridEditColumn2 dataIndex="trainid" width="10" editor="text" header="主键" hidden="true" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="a1131" width="110" header="班次名称" editor="text"  edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="g11020" width="190" header="培训年度" editor="text" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="a1101" width="140" header="类别" editor="select" codeType="ZB29" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="a1114" width="140" header="主办单位" editor="text" edited="false" align="center" isLast="true"/>
			</odin:gridColumnModel>
		  </odin:editgrid2>
			</td>
		</tr>
	</table>
</div>
</div>
<script type="text/javascript">
var option = {
    title : {
        text: '统计'
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:[]
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: false, readOnly: false},
            magicType : {show: true, type: ['line' ,'bar']}, //'stack', 'tiled'
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    grid : {
    	top: '49',
    	right: '20',
    	bottom: '30'
    },
    xAxis : [
        {
            type : 'category',
            boundaryGap : true,
            axisLabel: {  
           	    interval:0,
           	    rotate:30
           	},
            data : ['统计1','统计2','统计3','统计4','统计5','统计6']  //横坐标
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'人数',
            type:'bar',
            smooth:true,
            itemStyle: {normal: {areaStyle: {type: 'default'}}},
            data:[12, 34, 23, 45, 44, 24]
        }
     ]
};
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('myChart'));
myChart.setOption(option);
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var grid1 = Ext.getCmp('grid1');
	grid1.setWidth(viewSize.width-400);
	grid1.setHeight(viewSize.height-100);
	Ext.get('commForm').setWidth(viewSize.width);
});
</script>


