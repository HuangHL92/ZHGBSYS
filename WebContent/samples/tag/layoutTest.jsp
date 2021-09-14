<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<odin:head/>
</head>
<body>

<div id="west">
    <p>Hi. I'm the west panel.</p>
  </div>
<div id="cn">
    <p>Hi. I'm the west panel.</p>
  </div>
  <div id="center2">
  <!-- iframe src="tagDemo4.jsp" width="100%" height="100%"></iframe-->
  </div>

<script type="text/javascript">                    
 Ext.onReady(function(){

        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
       var viewport = new Ext.Viewport({
            layout:'border',
            items:[
                {
                    region:'west',
                    id:'west-panel',
                    title:'West',
                    split:true,
                    width: 200,
                    minSize: 175,
                    maxSize: 400,
                    collapsible: true,
                    margins:'0 0 0 5',
                    layout:'accordion',
                    layoutConfig:{
                        animate:true
                    },
                    items: [{
                        contentEl: 'west',
                        title:'Navigation',
                        border:false,
                        iconCls:'nav'
                    },{
                        title:'Settings',
                        contentEl: 'cn',
                        border:false,
                        iconCls:'settings'
                    },{
                        title:'Settings',
                        contentEl: 'cn',
                        border:false,
                        iconCls:'settings'
                    }]
                },
                new Ext.Panel({
                    region:'center',
                    deferredRender:false,
                    title: '',
                    contentEl:'center2'
                })
             ]
        });
    });
	</script>
</body>
</html>