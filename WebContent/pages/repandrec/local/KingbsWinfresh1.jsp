<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>

<%
	String id=request.getParameter("id");
%>
<html>
<head>

<link rel="stylesheet" type="text/css" href="/hzb/basejs/ext/resources/css/ext-all.css"/>
<link rel="stylesheet" type="text/css" href="/hzb/css/odin.css"/>
<link rel="stylesheet" type="text/css" id="extThemeCss"/>
<script type="text/javascript" src="/hzb/basejs/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="/hzb/basejs/ext/ext-all.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="/hzb/basejs/ext/ux/GroupSummary.js"></script>
<link rel="stylesheet" type="text/css" href="/hzb/basejs/ext/ux/css/Ext.ux.form.LovCombo.css"/>
<script type="text/javascript" src="/hzb/basejs/ext/ux/Ext.ux.form.LovCombo.js"></script>
<script type="text/javascript" src="/hzb/basejs/ext/ux/BufferView.js"></script>
<script type="text/javascript" src="/hzb/basejs/odin.js"></script>
<script type="text/javascript" src="/hzb/basejs/odin.ext.increase.js"></script>
<script type="text/javascript" src="/hzb/basejs/ext/ext-lang-zh_CN-GBK.js"></script>
<script>Ext.BLANK_IMAGE_URL = '/hzb/basejs/ext/resources/images/default/s.gif';var contextPath="/hzb";var sysDefaultPageSize=50;</script>
<%@include file="/comOpenWinInit.jsp" %>
<body>
<div>
<div id="grid"></div>
</div>
</body>
<script type="text/javascript">
	var arrs = new Array();
	Ext.onReady(function () {
        //数据源
        var uuid = '<%=id %>';
        var jsonstore = new Ext.data.JsonStore({
       	 url: '<%=request.getContextPath()%>/ImpRefeshServlet?method=query&uuid='+uuid,  
       	 root:'datas', 
       	 fields:['name','status','info']
        });
        jsonstore.load();

        //复选框列
        var sm = new Ext.grid.CheckboxSelectionModel({
       	   singleSelect : true 
        });
        //定义列
        var column = new Ext.grid.ColumnModel({
            columns: [
					new Ext.grid.RowNumberer(),
                 { header: '阶段', dataIndex: 'name',width:20},
                 { header: '状态', dataIndex: 'status',renderer:renderDescn,width:10},
                 { header: '详情', dataIndex: 'info',width:40} 
                 
            ]
        });

        //分页控件
        var pager = new Ext.PagingToolbar({
            pageSize: 20,
            store: jsonstore,
            listeners: {
                "beforechange": function (bbar, params) {
                    var grid = bbar.ownerCt;
                    var store = grid.getStore();
                    var start = params.start;
                    var limit = params.limit;
                    return false;
                }
            }
        });
        
        //列表
        var grid = new Ext.grid.GridPanel({
            sm: sm,
            id: 'Fgrid',
          	 height: 200,
            store: jsonstore,
           // bbar: pager,
            renderTo: 'grid',
            viewConfig : {
				forceFit : true,
				autoFill : true
			},
            bodyStyle:"height:100%;width:100%",
            colModel: column
        });
        Ext.TaskMgr.start({ 
       		run:function(){
               //如果加载完成 则发送ajax请求
               	if(uuid !='' && typeof uuid !='undefined'){
               	 	Ext.Ajax.request({
                        url: '<%=request.getContextPath()%>/ImpRefeshServlet?method=dorefresh&uuid='+ uuid,             
                        method: 'post',
                        success: function (response, options) {
                        	
                        	var str = response.responseText.substring(1,response.responseText.length-2).split(",");
                        	var obj = eval('('+response.responseText+')') ;
                        	if(grid.store.getCount()==0){
                        		grid.store.reload();
                        	} else {
                        		grid.store.getAt(0).set("status", obj.data.type1.status); 	
          						grid.store.getAt(0).set("info", obj.data.type1.info); 	
          						grid.store.getAt(1).set("status", obj.data.type2.status); 	
          						grid.store.getAt(1).set("info", obj.data.type2.info); 	
          						grid.store.getAt(2).set("status", obj.data.type3.status); 	
          						grid.store.getAt(2).set("info", obj.data.type3.info); 	
          						if(obj.data.type3.status =='2'){
          							alert('导入完成');
          							//关闭详情窗口
///          							window.close();
          							parent.Ext.getCmp(subWinId).close();
          						} else if(obj.data.type3.status =='4'){
          							alert('导入失败');
          							parent.Ext.getCmp(subWinId).close();
          						}
          						
                        	}
                        	
                        },
                        failure: function (response, options) {
                            //Ext.MessageBox.alert('错误', '请求超时或网络故障,错误编号：' + response.status);
                        }
               	 	});
              	}
       		},
       		interval:15000 
		});
	         function renderDescn(value, params, record, rowIndex, colIndex, ds){
	        	 if(value=='0'){
	        			return "";
	        		} else if(value=='1'){
	        			return "<img src='<%=request.getContextPath()%>/basejs/ext/resources/images/default/grid/wait.gif'>";
	        		} else if(value=='2'){
	        			if(rowIndex==2){
	        				Ext.TaskMgr.stopAll();    //终止定时调用^-^  
	        				realParent.odin.ext.getCmp('MGrid').store.reload();
	        			}
	        			return "<img src='<%=request.getContextPath()%>/images/right1.gif'>";
	        		} else if(value=='4'){
	        			Ext.TaskMgr.stopAll();    //终止定时调用^-^  
	        			return "<img src='<%=request.getContextPath()%>/images/wrong.gif'>";
	        		}
	         }
	    	//页面调整
	    	//document.getElementById('toolDiv').style.width = document.body.clientWidth +'px';
	    	document.getElementById('grid').style.width = document.body.clientWidth +'px';
	    	//document.getElementById('grid').style.height = document.body.clientHeight-GetObjWHLT(document.getElementById('grid')).top +'px';
			//grid.setHeight(document.body.clientHeight-GetObjWHLT(document.getElementById('grid')).top-2);
    });   
         
</script>

</html>