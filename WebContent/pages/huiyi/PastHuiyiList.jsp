<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="pages/huiyi/static/layui/css/layui.css" charset="UTF-8" media="all" rel="stylesheet">
    <script src="pages/huiyi/static/layui/layui.js" charset="UTF-8"></script>

    <script src="pages/huiyi/static/jquery/jquery-1.12.4.js" charset="UTF-8"></script>
    <link href="pages/huiyi/static/bootstrap/css/bootstrap.min.css" charset="UTF-8" rel="stylesheet">
    <script src="pages/huiyi/static/bootstrap/js/bootstrap.min.js" charset="UTF-8"></script>
    <link href="pages/huiyi/static/css/huiyi.css" charset="UTF-8" rel="stylesheet">
</head>
<body>
<div class="huiyiDiv">
    <table id="myTable" border="1" cellspacing="0" class="huiyiTable">

    </table>
    <div id="page1"></div>
</div>

<script>
    var pathName = window.document.location.pathname;
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

    layui.use('laypage', function () {
        $.post({
            url: projectName + '/radowAction.do?method=doEvent&pageModel=pages.huiyi.PastHuiyiList&eventNames=getCount',
            success: function (res) {
                var laypage = layui.laypage;

                //执行一个laypage实例
                laypage.render({
                    elem: 'page1' //注意，这里的 test1 是 ID，不用加 # 号
                    , count: res //数据总数，从服务端得到
                    , groups: 5
                    , jump: function (obj, first) {
                        //obj包含了当前分页的所有参数，比如：
                        var innerHtml = '<tr> <td>会议名称</td> <td>会议类型</td> <td>会议时间</td> <td>自动关闭日期</td> </tr>';
                        $.post({
                            url: projectName + '/radowAction.do?method=doEvent&pageModel=pages.huiyi.PastHuiyiList&eventNames=getPastHuiyiList',
                            data: {curr: obj.curr, limit: obj.limit},
                            dataType: 'json',
                            success: function (res) {
                                for (var i = 0; i < res.length; i++) {
                                    innerHtml += '<tr> <td>' + res[i].hy0101 + '</td> <td>部务会议题</td> <td>' + res[i].hy0102 + '</td> <td>' + res[i].hy0103 + '</td> </tr>'
                                }
                                $('#myTable').html(innerHtml);
                            }
                        });
                    }
                });
            }
        });

    });
</script>
</body>
</html>
