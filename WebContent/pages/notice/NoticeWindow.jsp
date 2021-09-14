<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<link href="<%=request.getContextPath()%>/pages/notice/themes/default/css/pagination.css" type="text/css" rel="stylesheet"></link>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/notice/third-party/jquery.pagination.js" charset="utf-8"></script>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.odin.framework.privilege.vo.UserVO"%>
<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@page import="com.insigma.odin.framework.db.DBUtil"%>
<%@page import="com.insigma.odin.framework.db.DBUtil.DBType"%>
<%@page import="org.hibernate.transform.Transformers"%>
<%@page import="com.insigma.siis.local.business.entity.NoticeListVo"%>
<%@page language="java" import="java.util.*" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<%
String ctxPath = request.getContextPath();
HBSession sess = HBUtil.getHBSession();

UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
//通知公告
String sql = "";
if(DBUtil.getDBType()==DBType.ORACLE){
	sql = "select a.id as id,a.title as content,b.see see,a.updateTime dateShow from Notice a left join NOTICERECIPIENT b on a.id = b.noticeId where b.recipientid = '"+user.getId()+"' order by b.see,a.updatetime";
}else{
	sql = "select a.id as id,a.title as content,b.see see,a.updateTime dateShow from Notice a left join NOTICERECIPIENT b on a.id = b.noticeId where b.recipientid = '"+user.getId()+"' order by b.see,a.updatetime";
}
List<NoticeListVo> noticeList = sess.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(NoticeListVo.class)).list();
List<Map<String,String>> list = new ArrayList<Map<String,String>>();
for(NoticeListVo nv : noticeList){
	Map<String,String> m = new HashMap<String,String>();
	m.put("id", nv.getId());
	m.put("content", nv.getContent());
	m.put("date", nv.getDateShow());
	list.add(m);
}

String v = JSONArray.fromObject(list).toString();

%>

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
   min-height:400px;
   margin-top:10px;
   margin-left:15px;
   border: 1px solid #DEDEDE;
}
.tzgg_title{
   width:100%;
   height:40px; 
   line-height:40px;
   font-size:16px;
   border-bottom:1px solid #DEDEDE;
   background:url(image/notice.png) 10px center no-repeat;
}
.dot_tzgg{
    width:49px;
    display:inline-block;
}
.tzgg_list{padding-top:10px;}
.tzgg_style{line-height:30px;height:30px;position:relative;width:100%;}
.tzgg_icon{float:left;background:url(image/point.png) no-repeat 95% center;width:3%;height:30px;}
.tzgg_content{width:85%;overflow:hidden;float:left;text-overflow:ellipsis;white-space:nowrap;cursor:default;}
.tzgg_content a{color:#000;text-decoration: none;}
.tzgg_content a:hover{color:#6a95e4; text-decoration: underline;}
.tzgg_date{width:10%;float:left;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;cursor:default;}
.box{width:297px;height:50px;margin-top:10px;margin-left:45%;}
/*以下是通知公告详情页面的样式*/
.tzgg_con_title{height:96px; line-height:48px; text-align:center; font-size:24px; font-weight:bold; }
.tzgg_con_sub{height:48px; line-height:24px;text-align:center; font-size:14px; font-weight:normal; color:#333;
border-bottom:1px solid #f3f3f3;}
.tzgg_info{ width:86%; margin:20px auto;
}
.tzgg_info p{font-size:14px; line-height:36px; text-indent:2em;}
.tzgg_other{width:86%; margin:20px auto; height:48px; font-size:12px;}
.tzgg_view{ width:70%; display: inline-block;}
.tzgg_option{ width:20%; display: inline-block;}
.tzgg_option a{ margin-left:5px; margin-right:5px; color:#0c7eb3; text-decoration: none}
</style>
<script type="text/javascript" src="basejs/pageUtil.js"></script>

<div>
	
</div>
<div class="tzgg_box">
	<div class="tzgg_title">
		<div class="dot_tzgg" style="float:left;height:40px;width:150px;padding-left:50px">通知公告</div>
		<div style="float: right;padding-right:10px;width:250px;height:40px;display:inline;position:relative;">
			<input id="qryNoticeText" label="查询" style="text-align:left;width:150px;height:25px;line-height:25px;font-size:18px;color:#000;margin-top:8px;padding-left:5px;" >
			<div onclick="queryNotice()" style="background:url(image/sea.png) 1px center no-repeat;float:left;height:24px;width:40px;_width:60px;font-size:15px;cursor:pointer;border:1px solid #1F80E9;margin-top:8px;line-height: 24px;padding-left:22px;font-family:"微软雅黑";">
				查询
			</div>
		</div>
	</div>
	<div style="background:#fff;height: 500px;">
		<div class="tzgg_list" id="tzggUL" style="height: 440px;"></div>
		<div class="M-box4" style="height:50px;margin-top:10px;padding-left:48%;"></div>
	</div>
</div>
<script type="text/javascript">
 var noticeArray = [];
 var showNoticeArray = [];
 var personTabsId=[];
 function addTab(atitle,aid,src,forced,autoRefresh){
	    var tab=parent.tabs.getItem(aid);
	    if (tab && !forced){ 
			parent.tabs.activate(tab);
			if(typeof autoRefresh!='undefined' && autoRefresh){
				document.getElementById('I'+aid).src = src;
			}
	    }else{
	  	 
	  	src = src+'&'+Ext.urlEncode({'id':aid});
	    	personTabsId.push(aid);
	      parent.tabs.add({
	      title: (atitle),
	      id: aid,
	      tabid:aid,
	      personid:aid,
	      html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
		    listeners:{//判断页面是否更改，
		    	
		    },
		    closable:true
	      }).show();  
			
	    }
	  }

 
    Ext.onReady(function() {
    	 var list = <%=v%>;
    	 showNoticeArray = noticeArray = list;
    	 convertNotice();
    });
    
	
	function GrantRender(value, params, rs, rowIndex, colIndex, ds){
		return "<a href=\"javascript:noticeInfo('"+rs.get('id')+"')\">查看</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" ;
	}
	
	//打开通知公告详情页面 
	function noticeInfo(id){
		addTab('通知公告详情',id,'<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.notice.NoticeInfo',false,false);
	}
	
	//是否查看 
	function isSee(see){
		var status = "已查看";
		if(see == 0){
			status = "未查看";
		}
		return status;
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
				var $p0 = $("<p class=tzgg_icon>");
				$p0.appendTo($li);
				var $p1 = $("<p class=tzgg_content>");
				$p1.appendTo($li);
				$("<a href='#' title='"+obj.content+"' onclick=showDetail('"+obj.id+"')></a>").html(obj.content).appendTo($p1);
				var $p2 = $("<p class=tzgg_date title='"+obj.date+"'>");
				$p2.html(obj.date).appendTo($li);
			}
		}
	}
	
	function showPage() {
		$('.M-box4').pagination({
		    totalData:showNoticeArray.length,
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
	
	function showDetail(noticeId) {
		addTab('公告详细信息',noticeId,'<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.notice.NoticeInfo',false,false);
		
	}
	
	function queryNotice() {
		var value = $("#qryNoticeText").val();
		showNoticeArray = new Array();
		if(value && value.trim() != ''){
			for(var i=0;i<noticeArray.length;i++) {
				var item = noticeArray[i];
				if(item.content.indexOf(value) > -1){
					showNoticeArray.push(item);
				}
			}
		} else {
			showNoticeArray = noticeArray;
		}
		convertNotice();
	}
	
	
	function ajaxNotices(){
		   var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.notice.NoticeWindow&eventNames=ajaxNotices';
	   		
		   	Ext.Ajax.request({
		   		timeout: 900000,
		   		url: path,
		   		async: true,
		           form:'data',
		           callback: function (options, success, response) {
		        	   if (success) {
		        		   var result = response.responseText;
		   					if(result){
		   						var json = eval('(' + result + ')');
		   						var data_str=json.data;
		   						var arr=data_str.split('@');
		   						
		   					}
		        	   }
		           }
		      });
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