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
		<odin:tabItem title="����ṹ" id="tab1"></odin:tabItem>
		<odin:tabItem title="ѧ���ṹ" id="tab2" ></odin:tabItem>
		<odin:tabItem title="רҵ�ṹ" id="tab3" ></odin:tabItem>
		<odin:tabItem title="ר���ṹ" id="tab4" ></odin:tabItem>
		<odin:tabItem title="����ṹ" id="tab5" ></odin:tabItem>
		<odin:tabItem title="�Ա�ṹ" id="tab6" ></odin:tabItem>
		<odin:tabItem title="���ɽṹ" id="tab7" ></odin:tabItem>
		<odin:tabItem title="�����ṹ" id="tab8" ></odin:tabItem>
		<odin:tabItem title="����ṹ" id="tab9" ></odin:tabItem>
		<odin:tabItem title="�����ṹ" id="taba" ></odin:tabItem>
		<odin:tabItem title="��Դ�ṹ" id="tabb" ></odin:tabItem>
		<odin:tabItem title="�����ƽṹ" id="tabc" isLast="true"></odin:tabItem>
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
	//��ʼ��echartsʵ��
    var myChart = echarts.init(document.getElementById('dome'));

    //ʹ���ƶ����������������ʾͼ��
    myChart.setOption(option);
}

function grantTabChange(tabObj,item){//�л�
	var tab = item.getId();
     if(tab=='tab1'){//����
    	alert("����");
     } else if (tab=='tab2'){//ѧ��
     	alert("ѧ��");
    } else if (tab=='tab3'){//רҵ
     	alert("רҵ");
    }else if (tab=='tab4'){//ר��
     	alert("ר��");
    }else if (tab=='tab5'){//����
     	alert("����");
    }else if (tab=='tab6'){//�Ա�
     	alert("�Ա�");
    }else if (tab=='tab7'){//����
     	alert("����");
    }else if (tab=='tab8'){//����
     	alert("����");
    }else if (tab=='tab9'){//����
     	alert("����");
    }else if (tab=='taba'){//����
     	alert("����");
    }else if (tab=='tabb'){//��Դ
     	alert("��Դ");
    }else if (tab=='tabc'){//������
     	alert("������");
    }
}
</script>