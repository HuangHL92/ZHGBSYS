<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>获取人员</title>

    <script src="pages/huiyi/static/jquery/jquery-1.12.4.js" charset="UTF-8"></script>
    <link href="pages/huiyi/static/bootstrap/css/bootstrap.min.css" charset="UTF-8" rel="stylesheet">
</head>
<body>
<div style="height: 7%;">
    <div class="splitDiv" style="width: 50%;float: left;">处室列表</div>
    <div class="splitDiv" style="width: 50%;float: left;">人员列表</div>
</div>
<div style="height: 73%;">
    <%--科室选择区域--%>
    <div id="hy0502Div" style="width: 50%;float: left;height: 100%;overflow: auto;">

    </div>
    <%--人员选择区域--%>
    <div id="hy0501Div" style="width: 50%;float: left;height: 100%;overflow: auto;">

    </div>
</div>
<%--选择人员展示区域--%>
<div style="height: 10%;" id="showDiv">

</div>
<%--隐藏的人员id区域--%>
<div style="display: none" id="hy0500Div">

</div>
<%--这里记录传进来的父页面的id--%>
<input type="hidden" id="inputDivId">

<div style="height: 10%;text-align: center;">
    <button class="btn btn-primary" onclick="parentValue()" style="width: 150px;">确定</button>
    <button class="btn btn-warning" onclick="closeFrame()" style="width: 150px;">取消</button>
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
    //进入请求科室信息
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
    //用数组记录人员姓名和id，方便删除并且保证记录顺序
    var names = [];
    var ids = [];

    //点击科室查询科室人员信息
    function getHY05(hy0502) {
        $.post({
            url: projectName + "/radowAction.do?method=doEvent&pageModel=pages.huiyi.GetPersons&eventNames=getHY05",
            data: {hy0502: hy0502},
            dataType: 'json',
            success: function (data) {
                $('#hy0501Div').empty();
                //动态增加人员
                for (var i = 0; i < data.length; i++) {
                    var div = $('<div class="addPersonClass splitDiv" data-flag="1" data-hy0500=' + data[i].hy0500 + ' data-hy0501=' + data[i].hy0501 + '>' + data[i].hy0501 + '</div>');
                    $('#hy0501Div').append(div);
                }
                //给人员div添加一个单击事件
                $(".addPersonClass").bind("click", function () {
                    var flag = $(this).data("flag");
                    var hy0501 = $(this).data("hy0501");    //姓名
                    var hy0500 = $(this).data("hy0500");    //id
                    //flag为1代表不存在于数组当中，需要添加进去
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
                        //删除人员
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

    //根据父页面id将本页面的人员姓名和人员id放入到父页面中
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
