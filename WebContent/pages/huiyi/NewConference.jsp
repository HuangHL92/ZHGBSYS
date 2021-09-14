<%@ page contentType="text/html;charset=GBK" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>新建会议</title>
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
            <label class="col-sm-2 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>会议名称</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="hy0101" name="hy0101">
            </div>
        </div>
        <div class="form-group" style="padding-top: 25px;">
            <label class="col-sm-2 control-label">会议地点</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="hy0107" name="hy0107">
            </div>
        </div>
        <div class="form-group" style="padding-top: 25px;">
            <label class="col-sm-2 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>会议时间</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="test1" name="hy0102">
            </div>
            <label class="col-sm-2 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>自动关闭日期</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="test2" name="hy0103">
            </div>
        </div>
        <div class="form-group" style="padding-top: 25px;">
            <label class="col-sm-2 control-label">出席</label>
            <div class="col-sm-9">
                <textarea id="chuxi" class="form-control" rows="4" style="resize: none" readonly></textarea>
                <input type="hidden" id="chuxiInput" name="hy0500">
            </div>
            <div class="col-sm-1">
                <input type="button" class="btn btn-primary" value="选择人员" onclick="openIframe(this)" style="
    height: 33px;">
            </div>
        </div>
    </div>

    <div class="sectionDiv">
        <div class="form-group" style="padding-top: 5px;height: 90px;">
            <label class="col-sm-2 control-label">重点</label>
            <div class="col-sm-10">
                <textarea class="form-control" rows="4" style="resize: none" name="hy0202"></textarea>
            </div>
        </div>
        <div class="form-group issueDiv">
            <div class="col-sm-12" style="float: right;margin-top: 5px;">
                <input type="button" class="btn btn-warning myBtn" style="float: right;" value="删除"
                       onclick="deleteIssue(this)">
                <input type="button" class="btn btn-primary myBtn" style="float: right;" value="添加议题"
                       onclick="addIssue(this)">
            </div>
            <div style="margin-top:45px">
                <label class="col-sm-2 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>议题</label>
                <div class="col-sm-10">
                    <textarea class="form-control" rows="4" style="resize: none;" name="hy0301"></textarea>
                </div>
                <div class="form-group" style="padding-top: 120px;">
                    <label class="col-sm-2 control-label">列席</label>
                    <div class="col-sm-9">
                        <textarea class="form-control" rows="4" style="resize: none" id="liexi1" readonly></textarea>
                        <input id="liexi1Input" type="hidden" name="hy0600">
                    </div>
                    <div class="col-sm-1">
                        <input type="button" class="btn btn-primary" value="选择人员" onclick="openIframe(this)" style="
    height: 33px;">
                    </div>
                </div>
                <div style="padding-top: 105px;">
                    <label class="col-sm-2 control-label">附件材料</label>
                    <div class="col-sm-10">
                        <button class="btn btn-success fileinput-button" type="button">上传</button>
                        <input multiple type="file" onchange="loadFile(this)"
                               style="position:absolute;top:0;left:0;font-size:34px; opacity:0"
                               accept=".doc,.docx,.pdf">
                        <span style="vertical-align: middle">未上传文件</span>
                        <input type="hidden" id="fileInput1" name="hy0700">
                    </div>
                </div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-12" style="margin-top: 5px;text-align: center;">
                <input type="button" class="btn btn-primary myBtn" onclick="addSection(this)" value="添加新的会议阶段">
                <input type="button" class="btn btn-warning myBtn" onclick="deleteSection(this)" value="删除">
            </div>
        </div>
    </div>

    <div style="height: 80px;background-color: white;margin-top: 5px;text-align: center;vertical-align: middle;line-height: 80px;">
        <input type="button" class="btn btn-primary myBtn" value="确定" onclick="commitHuiyi()">
        <input type="button" class="btn btn-warning myBtn" value="取消" onclick="cancelHuiyi()">
    </div>
</div>
</body>
<script>
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        // 开会时间
        laydate.render({
            elem: '#test1'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm'
        });
        // 自动关闭日期
        laydate.render({
            elem: '#test2'
            , type: 'date'
            , format: 'yyyy-MM-dd'
        });
    });
</script>
</html>
