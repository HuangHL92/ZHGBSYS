<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/layui/layui.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/layui/css/layui.css"/>
<style>

    .x-grid3-cell-inner, .x-grid3-hd-inner {
        white-space: normal !important;
        word-break: break-all;
    }

    #frame {
        width: 1500px;
        position: relative;
        top: 10px;
        left: 15px;
    }
</style>


<table>
    <tr>
        <td colspan="4">
            <iframe id="frame" name="frame" width="600px" height="300px"
                    src="<%=request.getContextPath() %>/pages/sysmanager/importExcel/adfile.jsp"
                    style="border: 0"></iframe>
        </td>
    </tr>
</table>
<table>
    <tr>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: -131px;margin-left: 20px">
            <legend>�ɲ����ݵ���˵��</legend>
            <div class="layui-field-box">
                <span>1.������ļ���ʽ��ʱֻ�޶�excel��ʽ</span>
                <br>
                <br>
                <span>2.�������ʼ��Ϊȥ�������������Ҳ������ʽ���ݵ�������ʼ��</span>
                <br>
                <br>
                <span>3.�����������Ϊexcel���ݵ�������excel��ʾ�ڼ��о���ڼ��С�</span>
            </div>
        </fieldset>
    </tr>
</table>

<script type="text/javascript">


    Ext.onReady(function () {
        document.getElementById('framewe').style.width = document.body.clientWidth;
        //ҳ�����
        // Ext.getCmp('logGrid').setHeight((Ext.getBody().getViewSize().height - 140));
        // Ext.getCmp('memberGrid').setHeight(Ext.getCmp('logGrid').getHeight());
//	 Ext.getCmp('groupTreePanel').setWidth(document.body.clientWidth);
        //document.getElementById("groupTreePanel").style.width = document.body.clientWidth;
    });

    function objTop(obj) {
        var tt = obj.offsetTop;
        var ll = obj.offsetLeft;
        while (true) {
            if (obj.offsetParent) {
                obj = obj.offsetParent;
                tt += obj.offsetTop;
                ll += obj.offsetLeft;
            } else {
                return [tt, ll];
            }
        }
        return tt;
    }

    function time(value) {
        var length = value.length;

        if (length > 16) {
            value = value.substring(0, 19);
        }

        return value;
    }


    function flash() {
        radow.doEvent('memberGrid.dogridquery');

    }
</script>