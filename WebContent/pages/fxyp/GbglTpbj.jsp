<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit.jsp" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cookie.js"></script>

<style type="text/css">
    body {
        margin: 0;
    }

    .body {
        background-color: #eaedf1;
    }
	.selected{
		background-color:#87CEEB;
	}
    .container {
        border-top: 0.08em #d7e5ff solid;
        width: 100%;
        height: 100%;
        overflow: auto;
    }

    .table,.tableHead {
        width: 100%;
        font-size: 18px;
        cursor: default;
    }

    .table tr,.tableHead tr {
        width: 100%;
        border-bottom: 0.08em #d7e5ff solid;
    }

	.table tr td,.tableHead tr td{
		padding: 10px;5px;
	}

    .title {
        width: 10%;
        height: 41px;
        vertical-align: middle;
        text-align: center;
        vertical-align: middle;
        color: #FFF;
        font-family: KaiTi;
        font-weight: bolder;
        background-color: rgb(61, 128, 255);
        font-family: 楷体_GB2312;
    }

    .content {
        height: 100%;
        text-align: center;
        vertical-align: middle;
        border-right: 0.08em #d7e5ff solid;
        color: #555555;
    }

    #headImage div {
        height: 176px;
        line-height: 176px;
    }

    #headImage img {
        width: 136px;
        height: 170px;
    }

    .again > img {
        position: absolute;
        z-index: 1428;
        width: 119px;
        height: 27px;
        left: calc(50% - 60px);
        top: 275%;
    }

    .span {
        vertical-align: middle;
    }

    .btns {
        position: relative;
        height: 45px;
    }

    #addSelect, #reselect, #setFields, #expSelect, #addPerson {
        /* height: 35px;
        position: absolute;
        margin: 5px 10px;
        cursor: pointer; */
    }


    .firstCol{
    	width: 80px!important;
    }
</style>
<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap-combined.min.css"> 

<div class="body">
    <div class="btns">
		<div style=" height:40px;float: right;margin-bottom: 5px;margin-right: 30px;">
			<button type='button' class="btn   btn-danger" onclick="if(subWinId){parent.odin.ext.getCmp(subWinId).close();}else{window.close();}" style='margin-top:5px;margin-left: 5px;'>关闭</button>
		</div>
		<div style=" height:40px;float: right;margin-bottom: 5px;margin-right: 30px;">
			<button type='button' id="expSelect"  onclick="expExcel()" class="btn  btn-primary" style='margin-top:5px;'>导出excel</button>
		</div>
		<div style=" height:40px;float: right;margin-bottom: 5px;margin-right: 30px;">
			<button type='button' id="reselect" class="btn  btn-primary" style='margin-top:5px;'>重新选择</button>
		</div>
		<div style=" height:40px;float: right;margin-bottom: 5px;margin-right: 30px;">
			<button type='button' id="setFields" class="btn  btn-primary" style='margin-top:5px;'>设置比较字段</button>
		</div>
		<div style=" height:40px;float: right;margin-bottom: 5px;margin-right: 30px; ">
			<button type='button' id="addPerson" onclick="ckPerson()" class="btn  btn-primary" style='margin-top:5px;'>确认人选</button>
		</div>
		
        <%-- <img id="addPerson" onclick="ckPerson()" src="<%=request.getContextPath()%>/images/tpbj/add_person.png">
        <img id="setFields" src="<%=request.getContextPath()%>/images/tpbj/set_fields.png">
        <img id="reselect"  src="<%=request.getContextPath()%>/images/tpbj/reselect.png"> --%>
        <img id="addSelect" style="display: none;" src="<%=request.getContextPath()%>/images/tpbj/add_select.png">
<%--         <img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png">
 --%>    </div>
    <div class="container">
    	<div><table class="tableHead"></table></div>
        <div style="overflow-y: scroll;" class="BDArea"><table class="table"></table></div>
    </div>
</div>
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<odin:hidden property="docpath" />
<odin:hidden property="dataArray" />
<input type="hidden" id="ctx" value="<%=request.getContextPath()%>"/>

<script type="text/javascript"
        src="<%=request.getContextPath()%>/js/jquery-1.10.2.js"></script>
<script>
	
    var ctx = document.getElementById("ctx").value
    var ids = []
    function reload() {
        document.location.reload();
    }
    $(function () {
        Ext.onReady(function () {
        	if(parent.Ext.getCmp(subWinId).initialConfig.addPerson){
        		$('#addPerson').hide();
        	}
        	
            // 后台传过来的数据
            ids = parent.Ext.getCmp(subWinId).initialConfig.data
            
            //模拟调配进来 隐藏没用的按钮
            if(parent.Ext.getCmp(subWinId).initialConfig.mntp){
            	$('#reselect').hide();
            	$('#addSelect').hide();
            }
            
            
            freshTable();
            
            var win = $h.getTopParent().Ext.getCmp('tpbjWindow');
            win.on('beforeclose',function(){
            	clearCookie("jggl.tpbj.ids")
            	realParent.clearSelected();
            })
            	
        })
    });

    // 渲染样式
    function drawStyle() {
        $(".table tr:nth-of-type(2n+1),.tableHead tr:nth-of-type(2n+1)").css("background-color", "#FFF")
        $(".table tr:nth-of-type(2n),.tableHead tr:nth-of-type(2n)").css("background-color", "#e8f1ff")
        //$(".table tr:nth-of-type(2n)").css("font-family:SimHei!important")
    }
    
    function resizeContainer(){
    	var viewSize = Ext.getBody().getViewSize();
        $('.container').width(viewSize.width);
        $('.tableHead').width(viewSize.width-17);
        $('.container').height(viewSize.height-50);
        
        var tableHeadHT = $('.tableHead').height();
    	var tableHeadTDS = $('.tableHead tr:nth-child(1) td');
    	var cols = tableHeadTDS.length-1;
    	$('.tableHead tr:nth-child(1) td:gt(0)').width(90/cols+"%");
    	$('.table tr:nth-child(1) td:gt(0)').width(90/cols+"%");
    	
    	var containerHT = $('.container').height();
    	$('.BDArea').height(containerHT-tableHeadHT-25);
        
    }
    
    
    function addSelectEvvent(){
    	resizeContainer();
    	
    	$('td:gt(0)','.table tr,.tableHead tr').bind('click',function(){
    		if($(this).hasClass("selected")){
    			$('td:eq('+$(this).index()+')','.table tr,.tableHead tr').removeClass("selected");
    		}else{
    			$('td:eq('+$(this).index()+')','.table tr,.tableHead tr').addClass("selected");
    		}
        });
    	
    	
    	
    	
    	
    	
    	
    }

    // 添加对比
    $("#addSelect").click(function () {
        setCookie("jggl.tpbj.ids", ids.join("#"));
        closeWin()
    })

    $("#reselect").click(function () {
        clearCookie("jggl.tpbj.ids")
        closeWin()
    })

    function closeWin() {
    	clearCookie("jggl.tpbj.ids")
    	realParent.clearSelected();
        // 关闭窗口
        var win = $h.getTopParent().Ext.getCmp('tpbjWindow');
        if(win){
        	win.close()
        }else{
        	realParent.Ext.getCmp('tpbjWindow').close()
        }
        
        
    }

    $("#setFields").click(function () {
        var url = contextPath + "/pages/fxyp/A01FieldsConfigTpbj.jsp?";
        $h.showWindowWithSrc("setTpbjFields", url, "设置比较字段", 400, 720);
    })

    function freshTable() {
        // 获取显示行
        Ext.Ajax.request({
            url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.fxyp.GbglTpbj&eventNames=getDisplayRow",
            method: 'POST',
            params: {},
            success: function (res) {
                var resData = Ext.decode(res.responseText)
                var fields = resData.data
                // 渲染显示行
                $(".tableHead").html('<tr id="headImage"><td class="select_td title"><span style="visibility: hidden;"></span></td></tr>')
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i]
                    // 人员id不显示
                    if (field[0] === "A0000"||"false"==field[5]) {
                        continue;
                    }
                    if(i<2){
                        $(".tableHead").append('<tr id="' + field[0] + '"><td class="select_td title">' + field[3] + '</td></tr>')
                    }else{
	                    $(".table").append('<tr id="' + field[0] + '"><td class="select_td title">' + field[3] + '</td></tr>')
                    }
                }
                // 获取显示列
                Ext.Ajax.request({
                    url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.fxyp.GbglTpbj&eventNames=getDisplayCol",
                    method: 'POST',
                    params: {
                        ids: Ext.encode(ids),
                        fields: Ext.encode(fields)
                    },
                    success: function (res) {
                        var resData = Ext.decode(res.responseText)
                        var persons = resData.data
                        // 循环人员
                        for (var i = 0; i < persons.length; i++) {
                            var fields = persons[i]
                            for (var j = 0; j < fields.length; j++) {
                                var field = fields[j]
                                if (field.key == "A0000") {
                                    var headImageSrc = ctx + "/servlet/DownloadUserHeadImage?a0000=" + encodeURI(encodeURI(field.desc))
                                    $("#headImage").append('<td style="position:relative;" class="select_td content"><input type="hidden" value="'+field.desc+'"/><img src="' + headImageSrc + '" />'+
                                    		'<div alt="移除" style="position:absolute;line-height:25px;right:0px;top:0px;cursor:pointer;width:25px;height:25px;background:url(<%=request.getContextPath()%>/picCut/image/del.png) no-repeat center;" onclick="deletePhoto(this);return false;"> <a href="javascript:void(0)" alt="移除" style="width:100%;height:100%;"></a> </div>'	
                                    		+'</td>')
                                } else {
                                    $("#" + field.key + "").append('<td class="select_td content"><span class="span">' + field.desc + '</span></td>')
                                }
                            }
                        }
                        drawStyle();
                        addSelectEvvent();
                    },
                    failure: function () {
                        Ext.Msg.alert("提示", "网络错误")
                    }
                })
            },
            failure: function () {
                Ext.Msg.alert("提示", "网络错误")
            }
        })
    }
    
    
function expExcel(){
	var dataArray = [];
	var tr = $(".table tr");
	$.each(tr,function (i, item){
		var tdArray = [];
	 	var td = $("td",$(this));
	 	$.each(td,function (ti, titem){
	 		if(i==0&&ti>0){
	 			tdArray.push($("img",this).attr("src"));
	 		}else{
	 			tdArray.push($(this).text());
	 		}
		});
	 	dataArray.push(tdArray)
	});
	if(dataArray.length>0){
		$("#dataArray").val(Ext.encode(dataArray));
		radow.doEvent('expExcel');
	}
}
    
function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}    

function deletePhoto(obj){
	
	var td = $(obj).parent();
	var index = td.index()+1;
	$("td:nth-child("+index+")",'.table tr,.tableHead tr').remove();
	var a0000 = $('input',td).val();
	var index = (ids.indexOf(a0000));
	if (index > - 1) {
		ids.splice(index, 1);
	}
	resizeContainer();
	setCookie("jggl.tpbj.ids", ids.join("#"));
}


function ckPerson(){
	var a0000s = "";
	$(".selected input:hidden").each(function(i, item){
		a0000s += $(this).val()+",";
	});
	if (a0000s == '') {
		odin.alert("请选择人员！");
		return;
	}
	a0000s = a0000s.substring(0, a0000s.length - 1);
	realParent.savePFromTpbj(a0000s);
	closeWin();
}
</script>