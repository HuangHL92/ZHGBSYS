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
    <div style="margin-top: 20px;">
        <input type="button" class="btn btn-primary" onclick="importDB()" value="导出">
        <input type="button" class="btn btn-warning" onclick="cancelDB()" value="取消">
    </div>
    <input type="hidden" id="editHuiyiHiddenId">
</div>
</body>
</html>
<script>
    var innerHtml = '<tr> <td width="8%"></td> <td width="20%">会议名称</td> <td width="15%">会议类型</td> <td width="20%">会议时间</td> <td width="17%">自动关闭日期</td><td width="20%">操作</td> </tr>';
    var pathName = window.document.location.pathname;
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    //动态添加table内元素
    $.post({
        url: projectName + '/radowAction.do?method=doEvent&pageModel=pages.huiyi.HuiyiList&eventNames=getHuiyiList',
        dataType: 'json',
        success: function (res) {
            for (var i = 0; i < res.length; i++) {
                innerHtml += '<tr> <td><input type="checkbox" style="width: 20px;" onclick="checkNum(this)"><input type="hidden" name="hy0100" value=' + res[i].hy0100 + '></td> <td>' + res[i].hy0101 + '</td> <td>部务会议题</td> <td>' + res[i].hy0102 + '</td> <td>' + res[i].hy0103 + '</td><td><input class="btn btn-primary" type="button" value="预览" style="width:60px;" onclick="previewHuiyi(\'' + res[i].hy0100 + '\')"><input class="btn btn-info" type="button" value="编辑" style="width:60px;margin-left: 20px;" onclick="editHuiyi(\'' + res[i].hy0100 + '\')"><input class="btn btn-warning" type="button" value="删除" style="width:60px;margin-left: 20px;" onclick="deleteHuiyi(\'' + res[i].hy0100 + '\')"></td> </tr>'
            }
            $('#myTable').html(innerHtml);
        }
    });

    function deleteHuiyi(hy0100) {
        if (confirm('确定删除会议吗？')) {
            $.post({
                url: projectName + '/radowAction.do?method=doEvent&pageModel=pages.huiyi.EditHuiyi&eventNames=deleteHuiyi',
                data: {hy0100: hy0100},
                //不管导出结果，首先关闭loading框
                success: function () {
                    alert('删除成功');
                    window.location.reload();
                },
                error: function () {
                    alert('删除失败，请联系管理员');
                }
            });
        }
    }

    function previewHuiyi(hy0100) {
        $("#editHuiyiHiddenId").val(hy0100);
        layui.use('layer', function () {
            var layer = layui.layer;

            layer.open({
                type: 2,
                content: projectName + '/radowAction.do?method=doEvent&pageModel=pages.huiyi.PreviewHuiyi',
                area: ['100%', '100%'],
                offset: [0, 0],
                title: ['预览会议'],
            });
        });
    }

    //编辑功能
    function editHuiyi(hy0100) {
        //将点击的会议id放入隐藏域中，供子页面使用
        $("#editHuiyiHiddenId").val(hy0100);
        layui.use('layer', function () {
            var layer = layui.layer;

            layer.open({
                type: 2,
                content: projectName + '/radowAction.do?method=doEvent&pageModel=pages.huiyi.EditHuiyi',
                area: ['100%', '100%'],
                offset: [0, 0],
                title: ['编辑会议'],
            });
        });
    }

    function checkNum(e) {
        var length = $('input[type="checkbox"]:checked').length;
        if (length > 4) {
            alert('导出不能超过4个会议');
            $(e).prop("checked", false);
        }
    }

    function importDB() {
        var length = $('input[type="checkbox"]:checked').length;
        if (length == 0) {
            alert('请选择导出会议')
            return false;
        }
        var result = '';
        $('input[type="checkbox"]:checked').each(
            function () {
                result += $(this).next().val() + " ";
                $(this).prop("checked", false);
            }
        );
        layui.use('layer', function () {
            var layer = layui.layer;
            //导出时间过长，给出一个loading框，不会自动关闭
            var layerIndex = layer.open({
                type: 1,
                title: false,
                closeBtn: 0,
                shade: 0.4,
                skin: 'no-shadow',
                content: '<div class="layui-layer-dialog layui-layer-msg "><div id="" class="layui-layer-content layui-layer-padding"><i class="layui-layer-ico layui-layer-ico16"></i>正在导出，请稍后</div><span class="layui-layer-setwin"></span></div>'
            });

            $.post({
                url: projectName + '/radowAction.do?method=doEvent&pageModel=pages.huiyi.HuiyiList&eventNames=importDB',
                data: {hy0100s: result},
                //不管导出结果，首先关闭loading框
                success: function (res) {
                    layer.close(layerIndex);
                    var result = confirm(res);
                    if (result) {
                        window.open(projectName + "/getHYZip");
                    }
                },
                error: function () {
                    layer.close(layerIndex);
                    alert('导出失败，请联系管理员')
                }
            });
        });
    }

    function cancelDB() {
        $('input[type="checkbox"]:checked').each(
            function () {
                $(this).prop("checked", false);
            }
        );
    }
</script>
