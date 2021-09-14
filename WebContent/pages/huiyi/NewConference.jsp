<%@ page contentType="text/html;charset=GBK" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>�½�����</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>

    <script src="pages/huiyi/static/jquery/jquery-1.12.4.js" charset="UTF-8"></script>
    <link href="pages/huiyi/static/bootstrap/css/bootstrap.min.css" charset="UTF-8" rel="stylesheet">
    <script src="pages/huiyi/static/bootstrap/js/bootstrap.min.js" charset="UTF-8"></script>

    <link href="pages/huiyi/static/css/index.css" rel="stylesheet" charset="UTF-8">
    <script src="pages/huiyi/static/js/index.js" charset="UTF-8"></script>

    <script src="pages/huiyi/static/layui/layui.js" charset="UTF-8"></script>
    <link href="pages/huiyi/static/layui/css/layui.css" charset="UTF-8" media="all" rel="stylesheet">
</head>
<body>
<div style="background-color: rgb(238,238,238);padding: 5px;font-size: 16px;">
    <div style="height: 250px;background-color: white">
        <div class="form-group" style="padding-top: 5px;">
            <label class="col-sm-2 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>��������</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="hy0101" name="hy0101">
            </div>
        </div>
        <div class="form-group" style="padding-top: 25px;">
            <label class="col-sm-2 control-label">����ص�</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="hy0107" name="hy0107">
            </div>
        </div>
        <div class="form-group" style="padding-top: 25px;">
            <label class="col-sm-2 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>����ʱ��</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="test1" name="hy0102">
            </div>
            <label class="col-sm-2 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>�Զ��ر�����</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="test2" name="hy0103">
            </div>
        </div>
        <div class="form-group" style="padding-top: 25px;">
            <label class="col-sm-2 control-label">��ϯ</label>
            <div class="col-sm-9">
                <textarea id="chuxi" class="form-control" rows="4" style="resize: none" readonly></textarea>
                <input type="hidden" id="chuxiInput" name="hy0500">
            </div>
            <div class="col-sm-1">
                <input type="button" class="btn btn-primary" value="ѡ����Ա" onclick="openIframe(this)" style="
    height: 33px;">
            </div>
        </div>
    </div>

    <div class="sectionDiv">
        <div class="form-group" style="padding-top: 5px;height: 90px;">
            <label class="col-sm-2 control-label">�ص�</label>
            <div class="col-sm-10">
                <textarea class="form-control" rows="4" style="resize: none" name="hy0202"></textarea>
            </div>
        </div>
        <div class="form-group issueDiv">
            <div class="col-sm-12" style="float: right;margin-top: 5px;">
                <input type="button" class="btn btn-warning myBtn" style="float: right;" value="ɾ��"
                       onclick="deleteIssue(this)">
                <input type="button" class="btn btn-primary myBtn" style="float: right;" value="�������"
                       onclick="addIssue(this)">
            </div>
            <div style="margin-top:45px">
                <label class="col-sm-2 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>����</label>
                <div class="col-sm-10">
                    <textarea class="form-control" rows="4" style="resize: none;" name="hy0301"></textarea>
                </div>
                <div class="form-group" style="padding-top: 120px;">
                    <label class="col-sm-2 control-label">��ϯ</label>
                    <div class="col-sm-9">
                        <textarea class="form-control" rows="4" style="resize: none" id="liexi1" readonly></textarea>
                        <input id="liexi1Input" type="hidden" name="hy0600">
                    </div>
                    <div class="col-sm-1">
                        <input type="button" class="btn btn-primary" value="ѡ����Ա" onclick="openIframe(this)" style="
    height: 33px;">
                    </div>
                </div>
                <div style="padding-top: 105px;">
                    <label class="col-sm-2 control-label">��������</label>
                    <div class="col-sm-10">
                        <button class="btn btn-success fileinput-button" type="button">�ϴ�</button>
                        <input multiple type="file" onchange="loadFile(this)"
                               style="position:absolute;top:0;left:0;font-size:34px; opacity:0"
                               accept=".doc,.docx,.pdf">
                        <span style="vertical-align: middle">δ�ϴ��ļ�</span>
                        <input type="hidden" id="fileInput1" name="hy0700">
                    </div>
                </div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-12" style="margin-top: 5px;text-align: center;">
                <input type="button" class="btn btn-primary myBtn" onclick="addSection(this)" value="����µĻ���׶�">
                <input type="button" class="btn btn-warning myBtn" onclick="deleteSection(this)" value="ɾ��">
            </div>
        </div>
    </div>

    <div style="height: 80px;background-color: white;margin-top: 5px;text-align: center;vertical-align: middle;line-height: 80px;">
        <input type="button" class="btn btn-primary myBtn" value="ȷ��" onclick="commitHuiyi()">
        <input type="button" class="btn btn-warning myBtn" value="ȡ��" onclick="cancelHuiyi()">
    </div>
</div>
</body>
<script>
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
</script>
</html>
