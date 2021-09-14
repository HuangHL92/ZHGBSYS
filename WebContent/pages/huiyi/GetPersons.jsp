<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>��ȡ��Ա</title>

    <script src="pages/huiyi/static/jquery/jquery-1.12.4.js" charset="UTF-8"></script>
    <link href="pages/huiyi/static/bootstrap/css/bootstrap.min.css" charset="UTF-8" rel="stylesheet">
</head>
<body>
<div style="height: 7%;">
    <div class="splitDiv" style="width: 50%;float: left;">�����б�</div>
    <div class="splitDiv" style="width: 50%;float: left;">��Ա�б�</div>
</div>
<div style="height: 73%;">
    <%--����ѡ������--%>
    <div id="hy0502Div" style="width: 50%;float: left;height: 100%;overflow: auto;">

    </div>
    <%--��Աѡ������--%>
    <div id="hy0501Div" style="width: 50%;float: left;height: 100%;overflow: auto;">

    </div>
</div>
<%--ѡ����Աչʾ����--%>
<div style="height: 10%;" id="showDiv">

</div>
<%--���ص���Աid����--%>
<div style="display: none" id="hy0500Div">

</div>
<%--�����¼�������ĸ�ҳ���id--%>
<input type="hidden" id="inputDivId">

<div style="height: 10%;text-align: center;">
    <button class="btn btn-primary" onclick="parentValue()" style="width: 150px;">ȷ��</button>
    <button class="btn btn-warning" onclick="closeFrame()" style="width: 150px;">ȡ��</button>
</div>
</body>
<style>
    .splitDiv {
        height: 30px;
        line-height: 30px;
        padding-left: 20px;
    }
</style>
<script>
    var pathName = window.document.location.pathname;
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    //�������������Ϣ
    $.post({
        url: projectName + "/radowAction.do?method=doEvent&pageModel=pages.huiyi.GetPersons&eventNames=getHY0502",
        dataType: 'json',
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                var div = $('<div class="splitDiv" onclick="getHY05(\'' + data[i] + '\')">' + data[i] + '</div>');
                $('#hy0502Div').append(div);
            }
        }
    });
    //�������¼��Ա������id������ɾ�����ұ�֤��¼˳��
    var names = [];
    var ids = [];

    //������Ҳ�ѯ������Ա��Ϣ
    function getHY05(hy0502) {
        $.post({
            url: projectName + "/radowAction.do?method=doEvent&pageModel=pages.huiyi.GetPersons&eventNames=getHY05",
            data: {hy0502: hy0502},
            dataType: 'json',
            success: function (data) {
                $('#hy0501Div').empty();
                //��̬������Ա
                for (var i = 0; i < data.length; i++) {
                    var div = $('<div class="addPersonClass splitDiv" data-flag="1" data-hy0500=' + data[i].hy0500 + ' data-hy0501=' + data[i].hy0501 + '>' + data[i].hy0501 + '</div>');
                    $('#hy0501Div').append(div);
                }
                //����Աdiv���һ�������¼�
                $(".addPersonClass").bind("click", function () {
                    var flag = $(this).data("flag");
                    var hy0501 = $(this).data("hy0501");    //����
                    var hy0500 = $(this).data("hy0500");    //id
                    //flagΪ1�������������鵱�У���Ҫ��ӽ�ȥ
                    if (flag == '1') {
                        names.push(hy0501);
                        ids.push(hy0500);
                        $('#showDiv').html('');
                        $('#hy0500Div').html('');
                        $(this).css('background-color', 'lightgrey');
                        for (var i = 0; i < names.length; i++) {
                            $('#showDiv').append(names[i] + '&nbsp;&nbsp;&nbsp;&nbsp;');
                            $('#hy0500Div').append(ids[i] + ' ');
                        }
                        $(this).data("flag", "-1");
                    } else {
                        //ɾ����Ա
                        names.remove(hy0501);
                        ids.remove(hy0500);
                        $('#showDiv').html('');
                        $('#hy0500Div').html('');
                        $(this).css('background-color', 'white');
                        for (var i = 0; i < names.length; i++) {
                            $('#showDiv').append(names[i] + '&nbsp;&nbsp;&nbsp;&nbsp;');
                            $('#hy0500Div').append(ids[i] + ' ');
                        }
                        $(this).data("flag", "1");
                    }
                });
            }
        })
    }

    //���ݸ�ҳ��id����ҳ�����Ա��������Աid���뵽��ҳ����
    function parentValue() {
        debugger;
        var show = $('#showDiv').html();
        var hy0500 = $('#hy0500Div').html();
        var children = $('#inputDivId').val().split(',');
        parent.$('#' + children[0]).html(show.trim());
        parent.$('#' + children[1]).val(hy0500.trim());
        parent.layer.close(index);
    }

    var index = parent.layer.getFrameIndex(window.name);

    function closeFrame() {
        parent.layer.close(index);
    }
</script>
</html>
