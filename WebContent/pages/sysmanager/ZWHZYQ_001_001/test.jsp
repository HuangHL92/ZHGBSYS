<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%> 
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<html>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/odin.css"/>

 <script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/Wizard.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/Ext.layout.SlideLayout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/Ext.ux.Tutorial.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">



<script type="text/javascript">
var panel;
Ext.onReady(function(){ 
	var baseInfo = new Ext.FormPanel({
        labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;',
        frame:true,labelWidth:80,monitorValid:true,id:'c1',
        items:[
            {layout:'column',border:false,labelSeparator:':',
            defaults:{layout: 'form',border:false,columnWidth:.5},
            items:[
				{	border: false,
   				 	bodyStyle: "background:none;padding-bottom:30px;",
    				html: "����д�û��Ļ�����Ϣ��",
    				columnWidth:1
				},
                {columnWidth:1,width:100,items: [{xtype:'textfield',fieldLabel: '�û���¼��',id: 'loginname',anchor:'20%'}]},
                {columnWidth:1,width:100,items: [{xtype:'textfield',fieldLabel: '����',id: 'password',anchor:'20%'}]},
                {columnWidth:1,width:100,items: [{xtype:'textfield',fieldLabel: 'ȷ������',id: 'confirm',anchor:'20%'}]},
                {columnWidth:.3,items: [{xtype:'textfield',fieldLabel: '����',id: 'xming',anchor:'50%'}]},

                {columnWidth:.7,items: [{xtype:'textfield',fieldLabel: '������λ',id: 'unit',anchor:'70%'}]},
                {columnWidth:.3,items: [{xtype:'textfield',fieldLabel: '�ֻ�',id: 'mobilephone',anchor:'90%'}]},
                {columnWidth:.3,items: [{xtype:'textfield',fieldLabel: '�칫�绰',id: 'phone',anchor:'90%'}]},
                {columnWidth:.3,items: [{xtype:'textfield',fieldLabel: '�ʼ�',id: 'email',anchor:'90%'}]},
                {columnWidth:.3,items: [{xtype:'combo',fieldLabel: '�û�״̬',id: 'status',anchor:'50%'}]},
                {columnWidth:.3,items: [{xtype:'combo',fieldLabel: '�Ƿ�Ϊ����Ա',id: 'isleader',anchor:'50%'}]},
               {columnWidth:1.05,items: [{xtype:'htmleditor',height:150,enableAlignments:false,enableLists:false,fieldLabel: '��ע',name: 'xming',anchor:'90%'}]}
                ]//items
                }
        ]//items
    })//FormPanel
    
	 panel = new Ext.Panel({   
//	    renderTo: Ext.getBody(),   
	    frame: true,   
	    layout: "card",   
	    title: "�½��û�",   
	    height: 500,   
	    activeItem: 0,   
	    defaults: {   
	        bodyStyle: "padding:3px; background-color: #F6F6F6"  
	    },   
	    items: [  baseInfo ,   
	              {id:'c2',title: '��ɫ��Ȩ',deferredRender : true,html: ' <iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+'<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysmanager.role.roletree'+'"> </iframe>'       
        },   
	        {id: "c3", title:"������Ȩ", html: ' <iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+'<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.MechanismComWindow'+'"> </iframe>'       
	    },
        {id: "c4", title:"��Ϣ��Ȩ�������", html: ' <iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+'<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.InfoComWindow'+'"> </iframe>'       
	    }  
	    ],   
	    buttons: [   
	        {   
	            text: "��һҳ",   
	            handler: changePage   
	        },   
	        {   
	            text: "��һҳ",   
	            handler: changePage   
	        }   
	    ]   
	});   
	  
	function changePage(btn){   
	    var index = Number(panel.layout.activeItem.id.substring(1));   
	    if(btn.text == "��һҳ"){   
	        index -= 1;   
	        if(index<1) index = 1;   
	    }else{   
	        index += 1;   
	        if(index>4) index = 4;   
	    }   
	    panel.layout.setActiveItem("c"+index);   
	}
	});   
new Ext.Viewport({   
    layout: 'fit',  
//    renderTo: Ext.getBody(),
    items: panel   
}); 
</script>

<odin:response onSuccess="doSuccess"/>
</body>
</html>