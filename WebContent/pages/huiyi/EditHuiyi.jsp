<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="pages/huiyi/static/jquery/jquery-1.12.4.js" charset="UTF-8"></script>
    <link href="pages/huiyi/static/bootstrap/css/bootstrap.min.css" charset="UTF-8" rel="stylesheet">
    <script src="pages/huiyi/static/bootstrap/js/bootstrap.min.js" charset="UTF-8"></script>

    <link href="pages/huiyi/static/css/index.css" rel="stylesheet" charset="UTF-8">
    <script src="pages/huiyi/static/js/index.js" charset="UTF-8"></script>

    <script src="pages/huiyi/static/layui/layui.js" charset="UTF-8"></script>
    <link href="pages/huiyi/static/layui/css/layui.css" charset="UTF-8" media="all" rel="stylesheet">
</head>
<body>
<input type="hidden" id="editHuiyiId" name="oldHy0100">

<div style="background-color: rgb(238,238,238);padding: 5px;font-size: 18px;" id="test3">
    <div style="height: 225px;background-color: white">
        <div class="form-group" style="padding-top: 5px;">
            <label class="col-sm-1 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>��������</label>
            <div class="col-sm-11">
                <input type="hidden" id="hy0100" name="hy0100">
                <input type="text" class="form-control" id="hy0101" name="hy0101">
            </div>
        </div>
        <div class="form-group" style="padding-top: 25px;">
            <label class="col-sm-1 control-label">����ص�</label>
            <div class="col-sm-11">
                <input type="text" class="form-control" id="hy0107" name="hy0107">
            </div>
        </div>
        <div class="form-group" style="padding-top: 25px;">
            <label class="col-sm-1 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>����ʱ��</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="test1" name="hy0102">
            </div>
            <label class="col-sm-2 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>�Զ��ر�����</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="test2" name="hy0103">
            </div>
        </div>
        <div class="form-group" style="padding-top: 25px;">
            <label class="col-sm-1 control-label">��ϯ</label>
            <div class="col-sm-10">
                <textarea id="chuxi" class="form-control" rows="4" style="resize: none" readonly></textarea>
                <input type="hidden" id="chuxiInput" name="hy0500">
            </div>
            <div class="col-sm-1">
                <input type="button" class="btn btn-primary" value="ѡ����Ա" onclick="openIframe(this)" style="
    height: 33px;">
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var index = parent.layer.getFrameIndex(window.name);
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        // ����ʱ��
        laydate.render({
            elem: '#test1'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm'
        });
        // �Զ��ر�����
        laydate.render({
            elem: '#test2'
            , type: 'date'
            , format: 'yyyy-MM-dd'
        });
    });
    //�õ���ҳ��Ļ���id
    $('#editHuiyiId').val(parent.$('#editHuiyiHiddenId').val());
    $.post({
        url: projectName + '/radowAction.do?method=doEvent&pageModel=pages.huiyi.EditHuiyi&eventNames=getHuiyiInfo',
        data: {hy0100: $('#editHuiyiId').val()},
        dataType: 'json',
        success: function (res) {
            var hy01 = res.hy01;
            $('#hy0100').val(hy01.hy0100);
            $('#hy0101').val(hy01.hy0101);
            $('#hy0107').val(hy01.hy0107);
            $('#test1').val(hy01.hy0102);
            $('#test2').val(hy01.hy0103);
            var hy04List = hy01.hy04List;
            var chuxi = '';
            var chuxiInput = '';
            hy04List.forEach(function (hy04) {
                chuxi += hy04.hy0501 + ' ';
                chuxiInput += hy04.hy0500 + ' ';
            });
            $('#chuxi').html(chuxi);
            $('#chuxiInput').val(chuxiInput);

            var hy02List = res.hy02List;
            var innerHtml = '';
            hy02List.forEach(function (hy02) {
                var hy03List = hy02.hy03List;
                //����û�а취��̬��ȡdiv�ĸ߶ȣ�ֻ��д�����޸�css��ʱ����Ҫͬ���޸�
                var height = ((hy03List.length - 1) * (287 + 5)) + 435;

                innerHtml += '<div class="sectionDiv" style="height: ' + height + 'px"> <div class="form-group" style="padding-top: 5px;height: 90px;"> <label class="col-sm-1 control-label">�ص�</label> <div class="col-sm-11"> <textarea class="form-control" rows="4" style="resize: none" name="hy0202">' + hy02.hy0202 + '</textarea> </div> </div>';
                hy03List.forEach(function (hy03) {
                    innerHtml += '<div class="form-group issueDiv"> <div class="col-sm-12" style="float: right;margin-top: 5px;"> <input type="button" class="btn btn-warning myBtn" style="float: right;" value="ɾ��" onclick="deleteIssue(this)"> <input type="button" class="btn btn-primary myBtn" style="float: right;" value="�������" onclick="addIssue(this)"> </div> <div style="margin-top:45px"> <label class="col-sm-1 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>����</label> <div class="col-sm-11"> <textarea class="form-control" rows="4" style="resize: none;" name="hy0301">' + hy03.hy0301 + '</textarea> </div>';
                    var hy06List = hy03.hy06List;
                    var liexi = '';
                    var liexiInput = '';
                    hy06List.forEach(function (hy06) {
                        liexi += hy06.hy0501 + ' ';
                        liexiInput += hy06.hy0500 + ' ';
                    });
                    issueCount++;
                    innerHtml += '<div class="form-group" style="padding-top: 100px;"> <label class="col-sm-1 control-label">��ϯ</label> <div class="col-sm-10"> <textarea class="form-control" rows="4" style="resize: none" id="liexi' + issueCount + '" readonly>' + liexi + '</textarea> <input id="liexi' + issueCount + 'Input" type="hidden" name="hy0600" value="' + liexiInput + '"> </div> <div class="col-sm-1"> <input type="button" class="btn btn-primary" value="ѡ����Ա" onclick="openIframe(this)" style=" height: 33px;"> </div> </div>';

                    var hy07List = hy03.hy07List;
                    var fileName = '';
                    var fileInput = '';
                    hy07List.forEach(function (hy07) {
                        fileName += hy07.hy0701 + ' ';
                        fileInput += hy07.hy0700 + ' ';
                    });
                    innerHtml += '<div style="padding-top: 85px;"> <label class="col-sm-1 control-label">��������</label> <div class="col-sm-11"> <button class="btn btn-success fileinput-button" type="button">�ϴ�</button> <input multiple type="file" onchange="loadFile(this)" style="position:absolute;top:0;left:0;font-size:34px; opacity:0" accept=".doc,.docx,.pdf"> <span style="vertical-align: middle">' + fileName + '</span> <input type="hidden" id="fileInput' + issueCount + '" name="hy0700" value="' + fileInput + '"> </div> </div></div> </div>';
                });
                innerHtml += ' <div class="form-group"> <div class="col-sm-12" style="margin-top: 5px;text-align: center;"> <input type="button" class="btn btn-primary myBtn" onclick="addSection(this)" value="����µĻ���׶�"> <input type="button" class="btn btn-warning myBtn" onclick="deleteSection(this)" value="ɾ��"> </div> </div> </div>';
            });

            innerHtml += '<div style="height: 80px;background-color: white;margin-top: 5px;text-align: center;vertical-align: middle;line-height: 80px;"> <input type="button" class="btn btn-primary myBtn" value="ȷ��" onclick="commitHuiyi()"> <input type="button" class="btn btn-warning myBtn" value="ȡ��" onclick="cancelUpdateHuiyi()"> </div>';

            $('#test3').append(innerHtml);
        }
    });

    function cancelUpdateHuiyi() {
        parent.layer.close(index);
    }
</script>
</html>
