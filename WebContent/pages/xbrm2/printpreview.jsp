<meta http-equiv="X-UA-Compatible" content="IE=8"/>
<title>
    ��ӡ����
</title>
<head>
    <%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
    <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
    <%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
    <%@page isELIgnored="false" %>
    <%@include file="/comOpenWinInit.jsp" %>

    <object ID='WebBrowser' WIDTH=0 HEIGHT=0 CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></object>


    <META http-equiv=Content-Type content="text/html; charset=gbk">
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>

    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/CoordTable/js/jquery-ui-1.8.9.custom.min.js"
            type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/CoordTable/js/jquery.contextmenu.r2.js"
            type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/CoordTable/js/coordTable.js"
            type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/tableEditer.js" type="text/javascript"></script>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mainPage/css/font-awesome.css">
    <!--[if lte IE 7]>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mainPage/css/font-awesome-ie7.min.css">
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mainPage/css/bootstrap.min.css">

	<script src="<%=request.getContextPath()%>/pages/xbrm2/js/printpreview.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm2/gbtp.css">

    <style>


        <!--
        .A3 {
            page-break-before: auto;
            page-break-after: always;
        }

        -->

        * {
            margin: 0px !important;
            padding: 0px !important;
        }

        body {
            margin: 1px;
            overflow-y: scroll;
            overflow-x: hidden;
            font-family: '����', Simsun;
            word-break: break-all;
            margin-bottom: 250px;
            overflow-y: hidden;
            <%--background: url(<%=request.getContextPath()%>/images/yilanbiao/dongyi.jpg) no-repeat;--%>
            background-position: -60px -24px;
        }

        #jqContextMenu {
            border-style: groove;
            background-color: gary;
            border-width: 1px;
        }

        .txt_editer {
            border-style: none;
        }

        .kcclClass {
            background-color: rgb(102, 204, 255) !important;
        }

        .drag_color {
            background-color: rgb(232, 232, 232) !important;
        }

        .drag_pre_color {
            background-color: rgb(233, 250, 238) !important;
        }

        .default_color {
            background-color: #FFFFFF !important;
        }
    </style>

    <style>
        @media print {
            ���� .noprint {
                display: none;
            }
        }
    </style> 
</head>
<body style="overflow-x:auto;overflow-y:auto" onload="onloadPage()">
<div class="noprint" style="width:100%;height:50px;margin-top:10px;font-size:12px;text-align:right;">
    <BR>
    <OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="wb" name="wb" width="0">
    </OBJECT>

    <button class="btn btn-primary" style="margin:5px;padding:5px" name=button_setup style="width:100px;height:30px"
            value="" onclick="javascript:printsetup();">&nbsp;��ӡҳ������&nbsp;
    </button>
    <button class="btn btn-primary" style="margin:5px;padding:5px" name=button_show style="width:100px;height:30px"
            value="" onclick="javascript:printpreview();">&nbsp;��ӡԤ��&nbsp;
    </button>
    <button class="btn btn-primary" style="margin:5px;padding:5px" value="��ӡ" type="button"
            style="width:60px;height:30px" value="" onclick="javascript:printdata(); ">&nbsp;��ӡ&nbsp;
    </button>
    <button class="btn btn-primary" style="margin:5px;padding:5px" name=button_fh style="width:60px;height:30px"
            value="" onclick="javascript:window.close();"> &nbsp;�ر� &nbsp;
    </button> &nbsp; 
</div>
<BR>
<p class="JiMi" style=""><font>JiMi </font></p>
<div id="tilte1fa" style="    text-align: center;    height: 115px; width:1440px	">
    <span class="BiaoTouP" style="text-align:center;font-family: ����С���μ���;height: 70px;">��&nbsp;��&nbsp;��&nbsp;Ϣ&nbsp;һ&nbsp;��&nbsp;��  </span>
    <div class="TiaoPeiShiJian" id="TiaoPeiShiJianPrint" style="text-align:center;" class="BiaoTouDate">(<span
            class="TiaoPeiShiJianYSPAN  TNR"><%= (new java.util.Date()).getYear() + 1900 %></span>��<span
            class="TiaoPeiShiJianMSPAN TNR"><%= (new java.util.Date()).getMonth() + 1%></span>��<span
            class="TiaoPeiShiJianDSPAN TNR"><%= (new java.util.Date()).getDate() %></span>��)
    </div>
</div>
<div id="coordTable_div0">
</div>
<div  id="typecore" ></div> 
</body>
</html>