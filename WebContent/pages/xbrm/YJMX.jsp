<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp"%>

<head>
	<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
</head>
	
<body>
	<odin:hidden property="js0100" /> <!-- 拟任免人选基本情况ID -->
	<odin:hidden property="type" /> <!-- 预警类别 -->
	
	<div id="panel_content" align="center">
		<div></div>
	</div>
	<odin:panel contentEl="panel_content" property="mypanel" ></odin:panel>

	<odin:editgrid2 property="gridYjmx" height="480" bbarId="pageToolBar"
		isFirstLoadData="false" clicksToEdit="false" autoFill="false" pageSize="20">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="gcheck" />
			<odin:gridDataCol name="js0100" />
			<odin:gridDataCol name="chinesename" />
			<odin:gridDataCol name="yjtype" isLast="true" />
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn />
			<odin:gridEditColumn2 locked="true" header="selectall" width="60"
				editor="checkbox" dataIndex="gcheck" edited="true" hideable="false" gridName="gridcq" hidden="true" />
			<odin:gridEditColumn2 dataIndex="js0100" header="ID" width="600"
				edited="false" editor="text" align="center" hidden="true" />
			<odin:gridEditColumn2 dataIndex="chinesename" header="明细"
				width="660" edited="false" editor="text" align="center" />
			<odin:gridEditColumn2 dataIndex="yjtype" header="类别" width="100"
				edited="false" editor="text" align="center" isLast="trye" renderer="showType" />
		</odin:gridColumnModel>
		<odin:gridJsonData>
			{
		        data:[]
		    }
		</odin:gridJsonData>
	</odin:editgrid2>
</body>	

<script>
	var basePath = "<%=request.getContextPath()%>";
	
	$(function(){
		$("#js0100").val(parent.Ext.getCmp("yjmx").initialConfig.js0100);
		$("#type").val(parent.Ext.getCmp("yjmx").initialConfig.type);
	});
	
	Ext.onReady(function() {
		//页面调整
		Ext.getCmp('gridYjmx').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_gridYjmx'))[0]);
		Ext.getCmp('gridYjmx').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_gridYjmx'))[1]-2); 
		document.getElementById("panel_content").style.width = Ext.getCmp('gridYjmx').getWidth() + "px";
	});
	
	//预警类别显示处理
	function showType(value, params, record, rowIndex, colIndex, ds){
		var src = basePath;
		var alt = '';
		if("1"==value){
			src += "/icos/emergency-off.png";
			alt = "红灯预警";
		} else if("2"==value){
			src += "/icos/emergency-y.png";
			alt = "黄灯预警";
		} else if("3"==value){
			src += "/icos/emergency-g.png";
			alt = "绿灯预警";
		}  else if("4"==value){
			src += "/icos/fine.png";
			alt = "优秀";
		} 
		return "<img alt='"+alt+"' src='"+src+"' width='20' height='20' >";
	}
	
	function objTop(obj){
	    var tt = obj.offsetTop;
	    var ll = obj.offsetLeft;
	    while(true){
	    	if(obj.offsetParent){
	    		obj = obj.offsetParent;
	    		tt+=obj.offsetTop;
	    		ll+=obj.offsetLeft;
	    	}else{
	    		return [tt,ll];
	    	}
		}
	    return tt;  
	}
</script>

