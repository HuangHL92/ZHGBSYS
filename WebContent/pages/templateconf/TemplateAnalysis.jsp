<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-lang-zh_CN-GBK.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/jquery-ui/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@page import="com.insigma.siis.local.pagemodel.templateconf.TemplateConfPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.ZjzzyPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>

<odin:tab id="tab" tabchange="grantTabChange" applyTo="tab_top">
	<odin:tabModel >
		<odin:tabItem title="年龄结构" id="tab1"></odin:tabItem>
		<odin:tabItem title="学历结构" id="tab2" ></odin:tabItem>
		<odin:tabItem title="专业结构" id="tab3" ></odin:tabItem>
		<odin:tabItem title="专长结构" id="tab4" ></odin:tabItem>
		<odin:tabItem title="地域结构" id="tab5" ></odin:tabItem>
		<odin:tabItem title="性别结构" id="tab6" ></odin:tabItem>
		<odin:tabItem title="党派结构" id="tab7" ></odin:tabItem>
		<odin:tabItem title="能力结构" id="tab8" ></odin:tabItem>
		<odin:tabItem title="经验结构" id="tab9" ></odin:tabItem>
		<odin:tabItem title="经历结构" id="taba" ></odin:tabItem>
		<odin:tabItem title="来源结构" id="tabb" ></odin:tabItem>
		<odin:tabItem title="任期制结构" id="tabc" isLast="true"></odin:tabItem>
	</odin:tabModel>
	<odin:tabCont itemIndex="tab1">
		
	</odin:tabCont>
	<odin:tabCont itemIndex="tab2">
		
	</odin:tabCont>
	<odin:tabCont itemIndex="tab3">
		
	</odin:tabCont>
	<odin:tabCont itemIndex="tab4">
	
	</odin:tabCont>
	<odin:tabCont itemIndex="tab5">
	
	</odin:tabCont>
	<odin:tabCont itemIndex="tab6">
	
	</odin:tabCont>
	<odin:tabCont itemIndex="tab7">
	
	</odin:tabCont>
	<odin:tabCont itemIndex="tab8">
	
	</odin:tabCont>
	<odin:tabCont itemIndex="tab9">
	
	</odin:tabCont>
	<odin:tabCont itemIndex="taba">
	
	</odin:tabCont>
	<odin:tabCont itemIndex="tabb">
	
	</odin:tabCont>
	<odin:tabCont itemIndex="tabc">
	
	</odin:tabCont>
</odin:tab>

<div id="tab_top"></div>
<div id="tab_under"></div>
<div id="demo" style="width:600px; height: 400px;"></div>
<script>
function demo(){
	var option = {
	    xAxis: {
	        type: 'category',
	        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: [{
	        data: [820, 932, 901, 934, 1290, 1330, 1320],
	        type: 'line'
	    }]
	};
	//初始化echarts实例
    var myChart = echarts.init(document.getElementById('dome'));

    //使用制定的配置项和数据显示图表
    myChart.setOption(option);
}

function grantTabChange(tabObj,item){//切换
	var tab = item.getId();
     if(tab=='tab1'){//年龄
    	alert("年龄");
     } else if (tab=='tab2'){//学历
     	alert("学历");
    } else if (tab=='tab3'){//专业
     	alert("专业");
    }else if (tab=='tab4'){//专长
     	alert("专长");
    }else if (tab=='tab5'){//地域
     	alert("地域");
    }else if (tab=='tab6'){//性别
     	alert("性别");
    }else if (tab=='tab7'){//党派
     	alert("党派");
    }else if (tab=='tab8'){//能力
     	alert("能力");
    }else if (tab=='tab9'){//经验
     	alert("经验");
    }else if (tab=='taba'){//经历
     	alert("经历");
    }else if (tab=='tabb'){//来源
     	alert("来源");
    }else if (tab=='tabc'){//任期制
     	alert("任期制");
    }
}
</script>