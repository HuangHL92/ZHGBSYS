<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="basejs/jquery-ui/jquery-1.10.2.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.core.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.widget.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.progressbar.js"></script>
<style>
 #jltab__tab1 {
 	margin-left: 75px;
 }
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>	
<div id="tooldiv"></div>
	<odin:tab id="jltab">
		    <odin:tabModel>
		    	<odin:tabItem title="录入界面及应用权限" id="tab1"></odin:tabItem>
		    	<odin:tabItem title="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;表格名册权限&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" id="tab2"></odin:tabItem>
		    	<odin:tabItem title="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询统计权限&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" id="tab3"></odin:tabItem>
		    	<odin:tabItem title="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;菜单功能权限&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" id="tab4" isLast="true"></odin:tabItem>
		    </odin:tabModel> 
		    <odin:tabCont itemIndex="tab1">
				<div style="width: 100%;height: 260px;margin-left: 20px;">
					<table>
						<tr>
							<td>
							 	<div style="width: 300px;height: 240px">
							 		<odin:groupBox property="g1" title="系统定义的数据录入界面列表">
							 		<odin:editgrid property="contentList" url="/" height="210">
										<odin:gridJsonDataModel id="id" root="data">
											<odin:gridDataCol name="rowsname" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridColumn header="selectall" gridName="appgrid" dataIndex="change" editor="checkbox" edited="true" width="8"/>
											<odin:gridEditColumn header="" align="left" edited="false" width="55" dataIndex="rowsname" editor="text" isLast="true"/>
										</odin:gridColumnModel>
									</odin:editgrid>
									</odin:groupBox>
							 	</div>
							</td>
							<td align="center">
								<div id='rigthBtn' style="width: 50px"></div>
								<div id='rigthAllBtn'></div>
								<div id='liftBtn'></div>
								<div id='liftAllBtn'></div>
							<td>
								<div style="width: 300px;height: 240px">
								<odin:groupBox property="g2" title="用户授权录入方案（缺省方案）">
							 		<odin:editgrid property="contentList2" url="/" height="210">
										<odin:gridJsonDataModel id="id" root="data">
											<odin:gridDataCol name="rowsname" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridColumn header="selectall" gridName="appgrid" dataIndex="change" editor="checkbox" edited="true" width="8"/>
											<odin:gridEditColumn header="" align="left" edited="false" width="55" dataIndex="rowsname" editor="text" isLast="true"/>
										</odin:gridColumnModel>
									</odin:editgrid>
								</odin:groupBox>
							 	</div>
							</td>
						</tr>
					</table>
				</div>
				
				<div style="width: 100%;height: 260px;margin-left: 20px;">
					<table>
						<tr>
							<td>
							 	<div style="width: 300px;height: 240px">
							 	<odin:groupBox property="g3" title="系统定义的链接应用程序">
							 		<odin:editgrid property="contentList3" url="/" height="210">
										<odin:gridJsonDataModel id="id" root="data">
											<odin:gridDataCol name="rowsname" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridColumn header="selectall" gridName="appgrid" dataIndex="change" editor="checkbox" edited="true" width="8"/>
											<odin:gridEditColumn header="" align="left" edited="false" width="55" dataIndex="rowsname" editor="text" isLast="true"/>
										</odin:gridColumnModel>
									</odin:editgrid>
								</odin:groupBox>
							 	</div>
							</td>
							<td align="center">
								<div id='rigthBtn2' style="width: 50px"></div>
								
								<div id='rigthAllBtn2'></div>
								
								<div id='liftBtn2'></div>
								
								<div id='liftAllBtn2'></div>
							<td>
								<div style="width: 300px;height: 240px">
								<odin:groupBox property="g4" title="用户授权的链接应用程序">
							 		<odin:editgrid property="contentList4" url="/" height="210">
										<odin:gridJsonDataModel id="id" root="data">
											<odin:gridDataCol name="rowsname" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridColumn header="selectall" gridName="appgrid" dataIndex="change" editor="checkbox" edited="true" width="8"/>
											<odin:gridEditColumn header="" align="left" edited="false" width="55" dataIndex="rowsname" editor="text" isLast="true"/>
										</odin:gridColumnModel>
									</odin:editgrid>
								</odin:groupBox>
							 	</div>
							</td>
						</tr>
					</table>
				</div>
			</odin:tabCont>
		    <odin:tabCont itemIndex="tab2">
		    	<div style="width: 210px;height: 480px;float: left;margin: 5px;margin-left: 24px;">
		    		<odin:groupBox title="自定义表格输出权限" property="group1">
		    			<div id="tree-div1" style="height: 427px"></div>
		    		
		    		</odin:groupBox>
		    	</div>
		    	<div style="width: 210px;height: 480px;float: left;margin: 5px;">
		    		<odin:groupBox title="单位名册输出权限" property="group2">
		    			<div id="tree-div2" style="height: 427px"></div>
		    		
		    		</odin:groupBox>
		    	</div>
		    	<div style="width: 210px;height: 480px;float: left;margin: 5px;">
		    		<odin:groupBox title="word表格权限" property="group3">
		    		
		    			<div id="tree-div3" style="height: 427px"></div>
		    		</odin:groupBox>
		    	</div>
		    </odin:tabCont>
		    <odin:tabCont itemIndex="tab3">
		    	<div style="width: 315px;height: 480px;float: left;margin: 5px;margin-left: 24px;">
		    		<odin:groupBox title="查询输出权限设置" property="group1">
		    			<div id="tree-div4" style="height: 427px"></div>
		    		
		    		</odin:groupBox>
		    	</div>
		    	<div style="width: 315px;height: 480px;float: left;margin: 5px;">
		    		<odin:groupBox title="统计输出权限设置" property="group2">
		    			<div id="tree-div5" style="height: 427px"></div>
		    		
		    		</odin:groupBox>
		    	</div>
		    </odin:tabCont>
		    <odin:tabCont itemIndex="tab4">
		    		<div id="tree-div6" style="width: 630px;border: 2px solid #c3daf9;height: 450px;margin-top: 5px;margin-left: 30px;">
		    		
		    		</div>
		    		<div style="width: 630px;height: 30px;margin-top: 5px;margin-left: 30px;">
		    			<odin:checkbox property="checkbox1" label="&nbsp;全部设置或全部取消菜单权限"></odin:checkbox>
		    		</div>
		    </odin:tabCont>
	 </odin:tab>
	
<odin:toolBar property="btnToolBar" applyTo="tooldiv">
	<odin:textForToolBar text="当前用户："></odin:textForToolBar>
	<odin:fill/>
	<odin:buttonForToolBar id="Save"  text="保存"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="cancel"  text="取消" isLast="true"/>
</odin:toolBar>

<script type="text/javascript">

 

 
 	function verify(vpid,b0111,ruleids,bsType,a0163){
		var param={'vpid':vpid,'b0111':b0111,'ruleids':ruleids,'bsType':bsType,'a0163':a0163,'method':'verify'};
		//console.log(param);
		ShowCellCover('start','系统提示','正在校验...');
		$.ajax({
	        type: "POST",
	        url:"<%=request.getContextPath()%>/CustomExcelServlet",
	        data: param ,//$('#excelForm').serialize(),// 你的formid
	        error: function(request) {
	            //$("#orginfo").val(data);
	            //ShowCellCover('failure','系统提示','汇总失败');
	        },
	        success: function(data) {
	        
	        	  radow.doEvent("showgrid",vpid);
	          	
	        }
		}); 
	 }
	 
	 
	 function ShowCellCover(elementId, titles, msgs){	
	 	Ext.MessageBox.buttonText.ok = "关闭";
	 	if(elementId.indexOf("start") != -1){
	 	
	 		Ext.MessageBox.show({
	 			title:titles,
	 			msg:msgs,
	 			width:300,
	 	        height:300,
	 			closable:false,
	 		//	buttons: Ext.MessageBox.OK,		
	 			modal:true,
	 			progress:true,
	 			wait:true,
	 			animEl: 'elId',
	 			increment:5, 
	 			waitConfig: {interval:150}
	 			//,icon:Ext.MessageBox.INFO        
	 		});
	 	}else if(elementId.indexOf("success") != -1){
	 			Ext.MessageBox.show({
	 				title:titles,
	 				msg:msgs,
	 				width:300,
	 		        height:300,
	 		        modal:true,
	 				closable:true,
	 				//icon:Ext.MessageBox.INFO,  
	 				buttons: Ext.MessageBox.OK
	 			});
	 			/*
	 			setTimeout(function(){
	 					Ext.MessageBox.hide();
	 			, 2000);
	 			*/
	 					
	 	}else if(elementId.indexOf("failure") != -1){
	 			Ext.MessageBox.show({
	 				title:titles,
	 				msg:msgs,
	 				width:300,
	 				modal:true,
	 		        height:300,
	 				closable:true,
	 				//icon:Ext.MessageBox.INFO,
	 				buttons: Ext.MessageBox.OK		
	 			});
	 			/*
	 			setTimeout(function(){
	 					Ext.MessageBox.hide();
	 			}, 2000);
	 			*/
	 	}else {
	 			Ext.MessageBox.show({
	 				title:titles,
	 				msg:msgs,
	 				width:300,
	 				modal:true,
	 		        height:300,
	 				closable:true,
	 				//icon:Ext.MessageBox.INFO,
	 				buttons: Ext.MessageBox.OK		
	 			});
	 		}

	 	
	 }
 
	 Ext.onReady(function() {
			new Ext.Button({
				icon : 'images/icon/rightOne.png',
				id:'btn1',
			    cls :'inline pl',
			    renderTo:"rigthBtn"/* ,
			    handler:function(){
			    	rigthBtnFun();
			    } */
			});
			new Ext.Button({
				icon : 'images/icon/rightAll.png',
				id:'btn2',
			    cls :'inline pl',
			    renderTo:"rigthAllBtn"/* ,
			    handler:function(){
			    	rigthAllBtnFun();
			    } */
			});
			new Ext.Button({
				icon : 'images/icon/leftOne.png',
				id:'btn3',
			    cls :'inline pl',
			    renderTo:"liftBtn"/* ,
			    handler:function(){
			    	liftBtnFun();
			    } */
			});
			new Ext.Button({
				icon : 'images/icon/leftAll.png',
				id:'btn4',
			    cls :'inline pl',
			    renderTo:"liftAllBtn"/* ,
			    handler:function(){
			    	liftAllBtnFun();
			    } */
			});	
			
			
			
			new Ext.Button({
				icon : 'images/icon/rightOne.png',
				id:'btn1',
			    cls :'inline pl',
			    renderTo:"rigthBtn2"/* ,
			    handler:function(){
			    	rigthBtnFun();
			    } */
			});
			new Ext.Button({
				icon : 'images/icon/rightAll.png',
				id:'btn2',
			    cls :'inline pl',
			    renderTo:"rigthAllBtn2"/* ,
			    handler:function(){
			    	rigthAllBtnFun();
			    } */
			});
			new Ext.Button({
				icon : 'images/icon/leftOne.png',
				id:'btn3',
			    cls :'inline pl',
			    renderTo:"liftBtn2"/* ,
			    handler:function(){
			    	liftBtnFun();
			    } */
			});
			new Ext.Button({
				icon : 'images/icon/leftAll.png',
				id:'btn4',
			    cls :'inline pl',
			    renderTo:"liftAllBtn2"/* ,
			    handler:function(){
			    	liftAllBtnFun();
			    } */
			});	
	 });
	 
	 
	 Ext.onReady(function() {

		 var tree = new Ext.tree.TreePanel({    
	            region: 'center',  
	            el: 'tree-div1',
	            //True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条    
	            collapsible: true,    
	            /* title: '标题',//标题文本  */   
	            /* width: 100, */    
	            border : false,//表框    
	            autoScroll: true,//自动滚动条    
	            animate : true,//动画效果    
	            rootVisible: true,//根节点是否可见    
	            split: true,    
	            /* loader : new Ext.tree.TreeLoader(),   */  
	            root : new Ext.tree.AsyncTreeNode({    
	                text:'表格输出', 
	                checked: false,
	                children:[{    
	                    text : '未分组报表', 
	                    checked: false,
	                    leaf : true   
	                }]    
	            }),  
	            listeners: {        
	                afterrender: function(node) {        
	                    tree.expandAll();//展开树     
	                }        
	            }     
	        });    
		 tree.render();    

	 
	 	var tree2 = new Ext.tree.TreePanel({    
         region: 'center',  
         el: 'tree-div2',
         //True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条    
         collapsible: true,    
         /* title: '标题',//标题文本  */   
         /* width: 100, */    
         border : false,//表框    
         autoScroll: true,//自动滚动条    
         animate : true,//动画效果    
         rootVisible: true,//根节点是否可见    
         split: true,    
         /* loader : new Ext.tree.TreeLoader(),   */  
         root : new Ext.tree.AsyncTreeNode({    
             text:'格式化报表', 
             checked: false,
             children:[{    
                 text : '未分组报表', 
                 checked: false,
                 leaf : true   
             }]    
         }),  
         listeners: {        
             afterrender: function(node) {        
                 tree2.expandAll();//展开树     
             }        
         }     
     });    
	 	tree2.render();   
	 	
	 	var tree3 = new Ext.tree.TreePanel({    
	         region: 'center',  
	         el: 'tree-div3',
	         //True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条    
	         collapsible: true,    
	         /* title: '标题',//标题文本  */   
	         /* width: 100, */    
	         border : false,//表框    
	         autoScroll: true,//自动滚动条    
	         animate : true,//动画效果    
	         rootVisible: true,//根节点是否可见    
	         split: true,    
	         /* loader : new Ext.tree.TreeLoader(),   */  
	         root : new Ext.tree.AsyncTreeNode({    
	             text:'word报表', 
	             checked: false,
	             children:[{    
	                 text : '未分组报表', 
	                 checked: false,
	                 leaf : true   
	             }] 
	         }),  
	         listeners: {        
	             afterrender: function(node) {        
	                 tree3.expandAll();//展开树     
	             }        
	         }     
	     });    
		 tree3.render();
		 
		 
		 var tree4 = new Ext.tree.TreePanel({    
	         region: 'center',  
	         el: 'tree-div4',
	         //True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条    
	         collapsible: true,    
	         /* title: '标题',//标题文本  */   
	         /* width: 100, */    
	         border : false,//表框    
	         autoScroll: true,//自动滚动条    
	         animate : true,//动画效果    
	         rootVisible: false,//根节点是否可见    
	         split: true,    
	         /* loader : new Ext.tree.TreeLoader(),   */  
	         root : new Ext.tree.AsyncTreeNode({    
	             text:'', 
	             checked: false,
	             children:[{    
	                 text : '视图列表', 
	                 checked: false,
	                 children:[{    
		                 text : '年报视图', 
		                 checked: false,
		                 leaf : true   
		             },{    
		                 text : '常用统计视图', 
		                 checked: false,
		                 leaf : true   
		             }]   
	             },{    
	                 text : '随机查询列表', 
	                 checked: false,
	                 leaf : true   
	             }] 
	         }),  
	         listeners: {        
	             afterrender: function(node) {        
	                 tree4.expandAll();//展开树     
	             }        
	         }     
	     });    
		 tree4.render();
		 
		 var tree5 = new Ext.tree.TreePanel({    
	         region: 'center',  
	         el: 'tree-div5',
	         //True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条    
	         collapsible: true,    
	         /* title: '标题',//标题文本  */   
	         /* width: 100, */    
	         border : false,//表框    
	         autoScroll: true,//自动滚动条    
	         animate : true,//动画效果    
	         rootVisible: false,//根节点是否可见    
	         split: true,    
	         /* loader : new Ext.tree.TreeLoader(),   */  
	         root : new Ext.tree.AsyncTreeNode({    
	             text:'', 
	             checked: false,
	             children:[{    
	                 text : '常用统计', 
	                 checked: false,
	                 children:[{    
		                 text : '性别分布表', 
		                 checked: false,
		                 leaf : true   
		             },{    
		                 text : '民族分布表', 
		                 checked: false,
		                 leaf : true   
		             },{    
		                 text : '年龄分布表', 
		                 checked: false,
		                 leaf : true   
		             }]   
	             },{    
	                 text : '通用统计', 
	                 checked: false,
	                 leaf : true   
	             },{    
	                 text : '局级干部统计', 
	                 checked: false,
	                 leaf : true   
	             }] 
	         }),  
	         listeners: {        
	             afterrender: function(node) {        
	                 tree5.expandAll();//展开树     
	             }        
	         }     
	     });    
		 tree5.render();
		 
		 var tree6 = new Ext.tree.TreePanel({    
	         region: 'center',  
	         el: 'tree-div6',
	         //True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条    
	         collapsible: true,    
	         /* title: '标题',//标题文本  */   
	         /* width: 100, */    
	         border : false,//表框    
	         autoScroll: true,//自动滚动条    
	         animate : true,//动画效果    
	         rootVisible: false,//根节点是否可见    
	         split: true,    
	         /* loader : new Ext.tree.TreeLoader(),   */  
	         root : new Ext.tree.AsyncTreeNode({    
	             text:'', 
	             checked: false,
	             children:[{    
	                 text : '信息维护', 
	                 checked: false,
	                 children:[{    
		                 text : '考察资料', 
		                 checked: false,
		                 leaf : true   
		             },{    
		                 text : '出国审批', 
		                 checked: false,
		                 leaf : true   
		             },{    
		                 text : '外部数据导入', 
		                 checked: false,
		                 leaf : true   
		             }]   
	             },{    
	                 text : '数据校核', 
	                 checked: false,
	                 leaf : true   
	             },{    
	                 text : '表格名册', 
	                 checked: false,
	                 leaf : true   
	             }] 
	         }),  
	         listeners: {        
	             afterrender: function(node) {        
	                 tree6.expandAll();//展开树     
	             }        
	         }     
	     });    
		 tree6.render();
	});
	

	</script>
