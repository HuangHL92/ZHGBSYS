<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Test Touch</title>
<odin:head></odin:head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/basejs/touch/resources/css/sencha-touch.css" type="text/css" title="senchatouch" id="senchatouch" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/basejs/touch/resources/css/sink.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/touch/sencha-touch.js"></script>


</head>
<body>
<table width="80%" align="center">
<tr>
	<odin:dateEdit property="d" label="日期"/>
	<odin:numberEdit property="n1" label="数字"></odin:numberEdit>
	<odin:textEdit property="t1" label="文本"></odin:textEdit>
</tr>
<tr>
	<td colspan="6"><div id="p" style="width: 100%;height: 100%;"></div></td>
</tr>
</table>
<script type="text/javascript">
Touch.setup({
    tabletStartupScreen: 'tablet_startup.png',
    phoneStartupScreen: 'phone_startup.png',
    icon: 'icon.png',
    glossOnIcon: false,
    onReady: function () {

        var buttonsSpecTop = [
            { ui: 'back', text: 'Back' },
            { xtype: 'spacer' },
            { ui: 'forward', text: 'Forward' }
        ]

        var buttonsSpecBottom = [
            { ui: 'normal', text: 'Normal' },
            { ui: 'round', text: 'Round' },
            { ui: 'action', text: 'Action' },
            { ui: 'confirm', text: 'Confirm' }
        ]

        var tapHandler = function (btn, evt) {
            alert("Button '" + btn.text + "' tapped.");
        }

        var dockedItems = [{
            xtype: 'toolbar',
            title: 'Buttons',
            ui: 'dark',
            dock: 'top',
            items: buttonsSpecTop,
            defaults: { handler: tapHandler }
        }, {
            xtype: 'toolbar',
            ui: 'dark',
            dock: 'bottom',
            items: buttonsSpecBottom,
            defaults: { handler: tapHandler }
        }]

        new Touch.Panel({
            id: 'buttonsPanel',
            fullscreen: false,
            height:400,
            width:1000,
            dockedItems: dockedItems,
            renderTo:Touch.get("p")
        });
    }
});
</script>

</body>
</html>