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
		    	<odin:tabItem title="¼����漰Ӧ��Ȩ��" id="tab1"></odin:tabItem>
		    	<odin:tabItem title="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������Ȩ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" id="tab2"></odin:tabItem>
		    	<odin:tabItem title="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ѯͳ��Ȩ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" id="tab3"></odin:tabItem>
		    	<odin:tabItem title="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�˵�����Ȩ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" id="tab4" isLast="true"></odin:tabItem>
		    </odin:tabModel> 
		    <odin:tabCont itemIndex="tab1">
				<div style="width: 100%;height: 260px;margin-left: 20px;">
					<table>
						<tr>
							<td>
							 	<div style="width: 300px;height: 240px">
							 		<odin:groupBox property="g1" title="ϵͳ���������¼������б�">
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
								<odin:groupBox property="g2" title="�û���Ȩ¼�뷽����ȱʡ������">
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
							 	<odin:groupBox property="g3" title="ϵͳ���������Ӧ�ó���">
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
								<odin:groupBox property="g4" title="�û���Ȩ������Ӧ�ó���">
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
		    		<odin:groupBox title="�Զ��������Ȩ��" property="group1">
		    			<div id="tree-div1" style="height: 427px"></div>
		    		
		    		</odin:groupBox>
		    	</div>
		    	<div style="width: 210px;height: 480px;float: left;margin: 5px;">
		    		<odin:groupBox title="��λ�������Ȩ��" property="group2">
		    			<div id="tree-div2" style="height: 427px"></div>
		    		
		    		</odin:groupBox>
		    	</div>
		    	<div style="width: 210px;height: 480px;float: left;margin: 5px;">
		    		<odin:groupBox title="word���Ȩ��" property="group3">
		    		
		    			<div id="tree-div3" style="height: 427px"></div>
		    		</odin:groupBox>
		    	</div>
		    </odin:tabCont>
		    <odin:tabCont itemIndex="tab3">
		    	<div style="width: 315px;height: 480px;float: left;margin: 5px;margin-left: 24px;">
		    		<odin:groupBox title="��ѯ���Ȩ������" property="group1">
		    			<div id="tree-div4" style="height: 427px"></div>
		    		
		    		</odin:groupBox>
		    	</div>
		    	<div style="width: 315px;height: 480px;float: left;margin: 5px;">
		    		<odin:groupBox title="ͳ�����Ȩ������" property="group2">
		    			<div id="tree-div5" style="height: 427px"></div>
		    		
		    		</odin:groupBox>
		    	</div>
		    </odin:tabCont>
		    <odin:tabCont itemIndex="tab4">
		    		<div id="tree-div6" style="width: 630px;border: 2px solid #c3daf9;height: 450px;margin-top: 5px;margin-left: 30px;">
		    		
		    		</div>
		    		<div style="width: 630px;height: 30px;margin-top: 5px;margin-left: 30px;">
		    			<odin:checkbox property="checkbox1" label="&nbsp;ȫ�����û�ȫ��ȡ���˵�Ȩ��"></odin:checkbox>
		    		</div>
		    </odin:tabCont>
	 </odin:tab>
	
<odin:toolBar property="btnToolBar" applyTo="tooldiv">
	<odin:textForToolBar text="��ǰ�û���"></odin:textForToolBar>
	<odin:fill/>
	<odin:buttonForToolBar id="Save"  text="����"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="cancel"  text="ȡ��" isLast="true"/>
</odin:toolBar>

<script type="text/javascript">

 

 
 	function verify(vpid,b0111,ruleids,bsType,a0163){
		var param={'vpid':vpid,'b0111':b0111,'ruleids':ruleids,'bsType':bsType,'a0163':a0163,'method':'verify'};
		//console.log(param);
		ShowCellCover('start','ϵͳ��ʾ','����У��...');
		$.ajax({
	        type: "POST",
	        url:"<%=request.getContextPath()%>/CustomExcelServlet",
	        data: param ,//$('#excelForm').serialize(),// ���formid
	        error: function(request) {
	            //$("#orginfo").val(data);
	            //ShowCellCover('failure','ϵͳ��ʾ','����ʧ��');
	        },
	        success: function(data) {
	        
	        	  radow.doEvent("showgrid",vpid);
	          	
	        }
		}); 
	 }
	 
	 
	 function ShowCellCover(elementId, titles, msgs){	
	 	Ext.MessageBox.buttonText.ok = "�ر�";
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
	            //True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������    
	            collapsible: true,    
	            /* title: '����',//�����ı�  */   
	            /* width: 100, */    
	            border : false,//���    
	            autoScroll: true,//�Զ�������    
	            animate : true,//����Ч��    
	            rootVisible: true,//���ڵ��Ƿ�ɼ�    
	            split: true,    
	            /* loader : new Ext.tree.TreeLoader(),   */  
	            root : new Ext.tree.AsyncTreeNode({    
	                text:'������', 
	                checked: false,
	                children:[{    
	                    text : 'δ���鱨��', 
	                    checked: false,
	                    leaf : true   
	                }]    
	            }),  
	            listeners: {        
	                afterrender: function(node) {        
	                    tree.expandAll();//չ����     
	                }        
	            }     
	        });    
		 tree.render();    

	 
	 	var tree2 = new Ext.tree.TreePanel({    
         region: 'center',  
         el: 'tree-div2',
         //True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������    
         collapsible: true,    
         /* title: '����',//�����ı�  */   
         /* width: 100, */    
         border : false,//���    
         autoScroll: true,//�Զ�������    
         animate : true,//����Ч��    
         rootVisible: true,//���ڵ��Ƿ�ɼ�    
         split: true,    
         /* loader : new Ext.tree.TreeLoader(),   */  
         root : new Ext.tree.AsyncTreeNode({    
             text:'��ʽ������', 
             checked: false,
             children:[{    
                 text : 'δ���鱨��', 
                 checked: false,
                 leaf : true   
             }]    
         }),  
         listeners: {        
             afterrender: function(node) {        
                 tree2.expandAll();//չ����     
             }        
         }     
     });    
	 	tree2.render();   
	 	
	 	var tree3 = new Ext.tree.TreePanel({    
	         region: 'center',  
	         el: 'tree-div3',
	         //True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������    
	         collapsible: true,    
	         /* title: '����',//�����ı�  */   
	         /* width: 100, */    
	         border : false,//���    
	         autoScroll: true,//�Զ�������    
	         animate : true,//����Ч��    
	         rootVisible: true,//���ڵ��Ƿ�ɼ�    
	         split: true,    
	         /* loader : new Ext.tree.TreeLoader(),   */  
	         root : new Ext.tree.AsyncTreeNode({    
	             text:'word����', 
	             checked: false,
	             children:[{    
	                 text : 'δ���鱨��', 
	                 checked: false,
	                 leaf : true   
	             }] 
	         }),  
	         listeners: {        
	             afterrender: function(node) {        
	                 tree3.expandAll();//չ����     
	             }        
	         }     
	     });    
		 tree3.render();
		 
		 
		 var tree4 = new Ext.tree.TreePanel({    
	         region: 'center',  
	         el: 'tree-div4',
	         //True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������    
	         collapsible: true,    
	         /* title: '����',//�����ı�  */   
	         /* width: 100, */    
	         border : false,//���    
	         autoScroll: true,//�Զ�������    
	         animate : true,//����Ч��    
	         rootVisible: false,//���ڵ��Ƿ�ɼ�    
	         split: true,    
	         /* loader : new Ext.tree.TreeLoader(),   */  
	         root : new Ext.tree.AsyncTreeNode({    
	             text:'', 
	             checked: false,
	             children:[{    
	                 text : '��ͼ�б�', 
	                 checked: false,
	                 children:[{    
		                 text : '�걨��ͼ', 
		                 checked: false,
		                 leaf : true   
		             },{    
		                 text : '����ͳ����ͼ', 
		                 checked: false,
		                 leaf : true   
		             }]   
	             },{    
	                 text : '�����ѯ�б�', 
	                 checked: false,
	                 leaf : true   
	             }] 
	         }),  
	         listeners: {        
	             afterrender: function(node) {        
	                 tree4.expandAll();//չ����     
	             }        
	         }     
	     });    
		 tree4.render();
		 
		 var tree5 = new Ext.tree.TreePanel({    
	         region: 'center',  
	         el: 'tree-div5',
	         //True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������    
	         collapsible: true,    
	         /* title: '����',//�����ı�  */   
	         /* width: 100, */    
	         border : false,//���    
	         autoScroll: true,//�Զ�������    
	         animate : true,//����Ч��    
	         rootVisible: false,//���ڵ��Ƿ�ɼ�    
	         split: true,    
	         /* loader : new Ext.tree.TreeLoader(),   */  
	         root : new Ext.tree.AsyncTreeNode({    
	             text:'', 
	             checked: false,
	             children:[{    
	                 text : '����ͳ��', 
	                 checked: false,
	                 children:[{    
		                 text : '�Ա�ֲ���', 
		                 checked: false,
		                 leaf : true   
		             },{    
		                 text : '����ֲ���', 
		                 checked: false,
		                 leaf : true   
		             },{    
		                 text : '����ֲ���', 
		                 checked: false,
		                 leaf : true   
		             }]   
	             },{    
	                 text : 'ͨ��ͳ��', 
	                 checked: false,
	                 leaf : true   
	             },{    
	                 text : '�ּ��ɲ�ͳ��', 
	                 checked: false,
	                 leaf : true   
	             }] 
	         }),  
	         listeners: {        
	             afterrender: function(node) {        
	                 tree5.expandAll();//չ����     
	             }        
	         }     
	     });    
		 tree5.render();
		 
		 var tree6 = new Ext.tree.TreePanel({    
	         region: 'center',  
	         el: 'tree-div6',
	         //True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������    
	         collapsible: true,    
	         /* title: '����',//�����ı�  */   
	         /* width: 100, */    
	         border : false,//���    
	         autoScroll: true,//�Զ�������    
	         animate : true,//����Ч��    
	         rootVisible: false,//���ڵ��Ƿ�ɼ�    
	         split: true,    
	         /* loader : new Ext.tree.TreeLoader(),   */  
	         root : new Ext.tree.AsyncTreeNode({    
	             text:'', 
	             checked: false,
	             children:[{    
	                 text : '��Ϣά��', 
	                 checked: false,
	                 children:[{    
		                 text : '��������', 
		                 checked: false,
		                 leaf : true   
		             },{    
		                 text : '��������', 
		                 checked: false,
		                 leaf : true   
		             },{    
		                 text : '�ⲿ���ݵ���', 
		                 checked: false,
		                 leaf : true   
		             }]   
	             },{    
	                 text : '����У��', 
	                 checked: false,
	                 leaf : true   
	             },{    
	                 text : '�������', 
	                 checked: false,
	                 leaf : true   
	             }] 
	         }),  
	         listeners: {        
	             afterrender: function(node) {        
	                 tree6.expandAll();//չ����     
	             }        
	         }     
	     });    
		 tree6.render();
	});
	

	</script>
