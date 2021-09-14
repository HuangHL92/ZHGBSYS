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
  		width: 70%;
  	    overflow-x:auto;
        background-color: #eaedf1;
    }
	.selected{
		background-color:#87CEEB;
	}
    .container {
        border-top: 0.08em #d7e5ff solid;
        width: 80%;
        height: 100%;
        overflow: auto;
    }

    .table,.tableHead {
        width: 70%;
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
        height: 25px;
    }

	#addPerson{
		right: 710px;
	}
    #expSelect {
        right: 0;
    }
    #addSelect {
        right: 170;
    }

    #reselect {
        right: 340px;
    }

    .firstCol{
    	width: 80px!important;
    }
</style>
<table id="totaltable" >
	<tr>
		<td  width="450" >
				 <div class="btns" > 
			   	    <button  id="setFields" type="button">设置显示字段</button>
<%-- 			        <img id="setFields" src="<%=request.getContextPath()%>/images/tpbj/set_fields.png"> --%>
			        <button onclick="query()" type="button">查询</button>
<!-- 			        <button onclick="expExcel()" type="button">导出excel</button>  -->
			        <button onclick="selectPerson()" type="button">条件查询</button>
			        <button onclick="addDRMQ()" type="button">加入待任免区</button>
			    </div>
			<odin:editgrid2 property="ryGrid" hasRightMenu="false" pageSize="50"  width="450" height="670" isFirstLoadData="false" pageSize="200" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="checked"/>
						<odin:gridDataCol name="a0000" />		
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0107" />
						<odin:gridDataCol name="a0192a" isLast="true" />
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 locked="true" header="selectall" width="30" editor="checkbox" dataIndex="checked"  edited="true"/>
						<odin:gridEditColumn2 dataIndex="a0000"  header="人员主键" hidden="true" edited="false"  editor="text"/> 
						<odin:gridEditColumn2 dataIndex="a0101"   width="50"   header="姓名" editor="text"  edited="false" align="center"   />
						<odin:gridEditColumn2 dataIndex="a0107"  header="出生年月" width="50" edited="false" align="center"  editor="text"/> 
						<odin:gridEditColumn2 dataIndex="a0192a"  header="任现职务" width="130" edited="false"  editor="text" isLast="true" /> 
					</odin:gridColumnModel>
				</odin:editgrid2>
		</td>
		<td class="showspace" >
			<div class="body">
			    <div class="container">
			    	<div><table class="tableHead" id="selectTable1" ></table></div>
			        <div style="overflow-y: scroll;" class="BDArea"><table class="table" id="selectTable2"></table></div>
			    </div>
			</div>
		</td>
	<tr>
</table>
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<odin:hidden property="docpath" />
<odin:hidden property="dataArray" />
<odin:hidden property="mntp00"/>
<odin:hidden property="a0000s"/>
<odin:hidden property="addlist"/>
<odin:hidden property="exa0000"/>
<input type="hidden" id="ctx" value="<%=request.getContextPath()%>"/>

<script type="text/javascript"
        src="<%=request.getContextPath()%>/js/jquery-1.10.2.js"></script>
<script>

    var ctx = document.getElementById("ctx").value
    var ids = []

    $(function () {
        Ext.onReady(function () {  
        	$('#mntp00').val(parent.Ext.getCmp(subWinId).initialConfig.mntp00);
            freshTable();
        })
    });

    // 渲染样式
    function drawStyle() {
        $(".table tr:nth-of-type(2n+1),.tableHead tr:nth-of-type(2n+1)").css("background-color", "#FFF")
        $(".table tr:nth-of-type(2n),.tableHead tr:nth-of-type(2n)").css("background-color", "#e8f1ff")
        //$(".table tr:nth-of-type(2n)").css("font-family:SimHei!important")
    }
    
    function selectPerson() {
    	var mntp00=document.getElementById('mntp00').value;
    	$h.openWin('RYCX','pages.gwdz.RYCX','人员查询',1500,731,null,'<%= request.getContextPath() %>',null,
    			{mntp00:mntp00},true);
        //$(".table tr:nth-of-type(2n)").css("font-family:SimHei!important")
    }
    
    
    function callback(param) {
        document.getElementById("exa0000").value += ','+param
        // 关闭选择人员窗口
        $h.getTopParent().Ext.getCmp('RYCX').close()
        var a0000s = param.split(",");
        var a0000listall=[];
        for(var i=0;i<a0000s.length;i++){
        	a0000listall.push(a0000s[i])
        }
        
        
        var grid = Ext.getCmp('ryGrid');
        var store = grid.store;
        for(var i =0;i<store.getCount();i++){
        	var record=store.getAt(i);
        	var data=record.get('checked');
        	if(data==true){
        		a0000listall.push(record.get('a0000'))
        	}
        }
        var a0000list = []; //一个新的临时数组
        for(var i = 0; i < a0000listall.length; i++){
            if(a0000list.indexOf(a0000listall[i]) == -1){
            	a0000list.push(a0000listall[i]);
            }
        }
        
        if(a0000list.length==0 || a0000list.length==null){
			$h.alert('系统提示：','请选择人员',null,150);
			return;
		}
        

        var tb = document.getElementById('selectTable1');
        var rowNum=tb.rows.length;
        for (i=0;i<rowNum;i++)
        {
            tb.deleteRow(i);
            rowNum=rowNum-1;
            i=i-1;
        }
        
        var td = document.getElementById('selectTable2');
        var rowNum1=td.rows.length;
        for (i=0;i<rowNum1;i++)
        {
            td.deleteRow(i);
            rowNum1=rowNum1-1;
            i=i-1;
        }
        /* ids=a0000list; */
        radow.doEvent('ryGrid.dogridquery');
        /* freshTable();  */
    }
    
    
    function query() {
 
        var grid = Ext.getCmp('ryGrid');
        var store = grid.store;
        var a0000list=[];
        for(var i =0;i<store.getCount();i++){
        	var record=store.getAt(i);
        	var data=record.get('checked');
        	if(data==true){
        		a0000list.push(record.get('a0000'))
        	}
        }
         
        if(a0000list.length==0 || a0000list.length==null){
			$h.alert('系统提示：','请选择人员',null,150);
			return;
		}
        

        var tb = document.getElementById('selectTable1');
        var rowNum=tb.rows.length;
        for (i=0;i<rowNum;i++)
        {
            tb.deleteRow(i);
            rowNum=rowNum-1;
            i=i-1;
        }
        
        var td = document.getElementById('selectTable2');
        var rowNum1=td.rows.length;
        for (i=0;i<rowNum1;i++)
        {
            td.deleteRow(i);
            rowNum1=rowNum1-1;
            i=i-1;
        }
        ids=a0000list;
        freshTable(); 
        
    }
    
    function addDRMQ(){
    	var addlist='';
    	$(".selected").each(function(){
    		var a=$('input',this).val();
    		if(a!=null && a!=''){
    			addlist+=a;
    			addlist+=',';
    		}    
    	});
    	if(addlist==null || addlist==''){	
    		$h.alert('系统提示：','请选择人员！',null,150);
    		return;	
    	}
    	addlist=addlist.substr(0, addlist.length - 1); 
    	radow.doEvent('addintolist',addlist);
    }
    
    
    
    
    
    function resizeContainer(){
    	var viewSize = Ext.getBody().getViewSize();
    	$('.body').width(viewSize.width-20);
    	$('.showspace').width(viewSize.width*0.69);
        $('.container').width(viewSize.width);
        $('.tableHead').width(viewSize.width*0.69);
        $('.container').height(viewSize.height-50);
        
        var tableHeadHT = $('.tableHead').height();
    	var tableHeadTDS = $('.tableHead tr:nth-child(1) td');
    	var cols = tableHeadTDS.length-1;
    	$('.tableHead tr:nth-child(1) td:gt(0)').width(90/cols+"%");
    	$('.table tr:nth-child(1) td:gt(0)').width(90/cols+"%");
    	
    	var containerHT = $('.container').height();
    	$('.BDArea').height(containerHT-tableHeadHT-35);
        
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
    	realParent.clearSelected();
        // 关闭窗口
        var win = $h.getTopParent().Ext.getCmp('tpbjWindow');
        win.close()
        
    }

    $("#setFields").click(function () {
        var url = contextPath + "/pages/fxyp/A01FieldsConfigTpbj.jsp?";
        $h.showWindowWithSrc("setTpbjFields", url, "设置比较字段", 400, 720);
    })

    function freshTable() {
        // 获取显示行
        Ext.Ajax.request({
            url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.gwdz.GWrybd&eventNames=getDisplayRow",
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
                    url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.gwdz.GWrybd&eventNames=getDisplayCol",
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