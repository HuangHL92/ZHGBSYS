<%@page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<link href="<%=request.getContextPath()%>/pages/notice/themes/default/css/pagination.css" type="text/css" rel="stylesheet"></link>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/notice/third-party/jquery.pagination.js" charset="utf-8"></script>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.odin.framework.db.DBUtil"%>
<%@page import="com.insigma.odin.framework.db.DBUtil.DBType"%>
<%@page import="org.hibernate.transform.Transformers"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page language="java" import="java.util.*" isELIgnored="false"%>
<style>
html,body{height:100%;width:100%;}
body{
	width:100%;
	min-width:850px;
	height:500px;
	background:#ebeef6 !important;
	font-size:14px;
	font-family: "微软雅黑"
}
.tzgg_box{
   width:98%;
   _width: expression(document.body.clientWidth <= 800 ? '800px' : '100%');
   overflow-x:hidden !important;
   min-height:440px;
   margin-top:10px;
   margin-left:15px;
   border: 1px solid #DEDEDE;
}
.tzgg_head{
   width:100%;
   height:40px;
   line-height:40px;
   font-size:16px;
   border-bottom:1px solid #DEDEDE;
   background:url(image/policy.png) 10px center no-repeat;
}

.dot_tzgg{
    width:49px;
    display:inline-block;
}
.tzgg_list{padding-top:10px;}
.tzgg_style{line-height:30px;height:30px;position:relative;width:100%;text-align:center;}
.tzgg_icon{float:left;background:url(image/point.png) no-repeat 95% center;width:3%;height:30px;}
.tzgg_title{float:left;width:87%;height:30px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;cursor:default;text-align:left;}
.tzgg_create_person{float:left;width:15%;height:30px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;cursor:default;}
.tzgg_date{width:15%;float:left;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;cursor:default;text-align:center;}
.tzgg_operate{width:8%;float:right;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;cursor:default;}
.tzgg_content{width:30%;float:left;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;cursor:default;}
</style>
<%
	String ctxPath = request.getContextPath();
	HBSession sess = HBUtil.getHBSession();
	String sql = "select * from POLICY order by UPDATETIME desc";
	List noticeList = sess.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	String v = JSONArray.fromObject(noticeList).toString();
%>
<div class="tzgg_box">
	<div class="tzgg_head">
		<div class="dot_tzgg" style="float:left;height:40px;width:150px;padding-left:50px">政策法规</div>
		<div style="float: right;padding-right:10px;width:230px;height:40px;display:inline;position:relative;">
			<input id="qryNoticeText" label="查询" style="text-align:left;width:150px;height:25px;line-height:25px;font-size:18px;color:#000;margin-top:8px;padding-left:5px;" >
			<div onclick="queryNotice()" style="background:url(image/sea.png) 1px center no-repeat;float:left;height:24px;width:40px;_width:60px;font-size:15px;cursor:pointer;border:1px solid #1F80E9;margin-top:8px;line-height: 24px;padding-left:22px;font-family:"微软雅黑";">
				查询
			</div>
		</div>
	</div>
	<div style="background:#fff;min-width:600px;height: 500px;">
		<!-- <div class="tzgg_style" style="background-color:#EFF0F2">
			<p class="tzgg_icon"></p>
			<p class="tzgg_title" style="border-left:1px solid #D0D0D0;">标题</p>
			<p class="tzgg_create_person" style="border-left:1px solid #D0D0D0;">创建人</p>
			<p class="tzgg_content" style="border-left:1px solid #D0D0D0;">文件名称</p>
			<p class="tzgg_date" style="border-left:1px solid #D0D0D0;">更新时间</p>
			<p class="tzgg_operate" style="border-left:1px solid #D0D0D0;">操作</p>
		</div> -->
		<div class="tzgg_list" id="tzggUL" style="height: 440px;" >
		</div>
		<div class="M-box4" style="height:50px;margin-top:10px;padding-left:48%;"></div>
	</div>
</div>

 <script type="text/javascript">
 	var noticeArray = [];
 	var showNoticeArray = [];
 	var personTabsId=[];
    Ext.onReady(function() {
    	//页面调整
    	var list = <%=v%>;
    	showNoticeArray = noticeArray = list;
    	convertNotice();
    });
	
	//下载政策法规文件 
	//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
	function downloadPolicyFile(id, fileurl){
		
		fileurl = fileurl.replace(/uuid/g, " ")
		
		window.location="PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl));
	}
	
	function convertNotice() {
		showContent(showNoticeArray.slice(0,14));
		showPage();
	}
	
	function showContent(listArray) {
		var $div = $('#tzggUL');
		$div.empty();
		if(listArray && listArray.length > 0) {
			var showArray = listArray
			for(var i=0;i<listArray.length;i++) {
				var obj = listArray[i];
				var $li = $("<div class=tzgg_style>");
				$li.appendTo($div);
				/* var $p0 = $("<p class=tzgg_icon>");
				$p0.html(i+1).appendTo($li); */
				
				var $p0 = $("<p class=tzgg_icon>");
				$p0.appendTo($li);
				
				var $p1 = $("<p class=tzgg_title title='"+obj.TITLE+"'>");
				$p1.html(obj.TITLE).appendTo($li);
				//var $p2 = $("<p class=tzgg_create_person title='"+obj.USERNAME+"'>");
				//$p2.html(obj.USERNAME).appendTo($li);
				//var $p3 = $("<p class=tzgg_content title='"+obj.FILENAME+"'>");
				//$p3.html(obj.FILENAME).appendTo($li);
				//var $p4 = $("<p class=tzgg_date title='"+obj.UPDATETIME+"'>");
				//$p4.html(obj.UPDATETIME).appendTo($li);
				var $p5 = $("<p class=tzgg_operate>");
				
				var url = obj.FILEURL.replace(/ /g, "uuid")
				
				$("<a href='#' style='cursor:pointer;' title='"+obj.FILENAME+"'onclick=downloadPolicyFile('"+obj.ID+"','"+url+"')></a>").html("下载").appendTo($p5);
				$p5.appendTo($li);
			}
		}
	}
	
	function showPage() {
		var total = showNoticeArray.length;
		$('.M-box4').pagination({
		    totalData:total,
		    showData:14,
		    coping:true,
		    callback:function(api){
		        var current = api.getCurrent();
		        var begin = (current-1)*14;
		        var end = current * 14;
		        showContent(showNoticeArray.slice(begin,end));
		    }
		});
	}
	
	function queryNotice() {
		var value = $("#qryNoticeText").val();
		showNoticeArray = new Array();
		if(value && value.trim() != ''){
			for(var i=0;i<noticeArray.length;i++) {
				var item = noticeArray[i];
				
				if(item.TITLE.indexOf(value) > -1){
					showNoticeArray.push(item);
				}
			}
		} else {
			showNoticeArray = noticeArray;
		}
		convertNotice();
	}
	
	
	
	//enter键禁用 
	$(document).ready(function () {
	    $(document).bind('keydown', function (event) {
	        if (event.keyCode == 13) {
	        	queryNotice();
	        	return false;   
	        }
	    });
	    
	});
</script>