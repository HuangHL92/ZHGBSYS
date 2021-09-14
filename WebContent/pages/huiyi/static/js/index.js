//读取文件并上传
function loadFile(e) {
    var files = e.files;
    if (files.length != 0) {
        var filesName = '';
        for (var i = 0; i < files.length; i++) {
            var suffix = files[i].name.substr(files[i].name.lastIndexOf(".") + 1);
            if (suffix == 'doc' || suffix == 'docx' || suffix == 'pdf') {
                filesName += files[i].name + "&nbsp;&nbsp;&nbsp;&nbsp;";
            } else {
                alert("上传文件类型必须为word或pdf");
                return false;
            }
        }
        //在前台显示文件名
        $(e).next().html(filesName);

        var formData = new FormData();
        var fileId = $(e).next().next().val();
        if (fileId != '') {
            formData.append('fileId', fileId);
        }
        for (var i = 0; i < files.length; i++) {
            formData.append('files', files[i]);
        }
        //使用ajax上传文件，上传完毕后将文件id放入隐藏域中
        $.ajax({
            url: projectName + '/huiyiUpload',
            type: 'post',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function (res) {
            $(e).next().next().val(res);
        });
    }
}

function confirmDevare(msg) {
    if (confirm(msg) == true) {
        return true;
    } else {
        return false;
    }
}

//增加阶段
function addSection(e) {
    issueCount++;
    var sectionDiv = $(e).parents('.sectionDiv');
    var innerHtml = '<div class="sectionDiv"> <div class="form-group" style="padding-top: 5px;height: 90px;"> <label class="col-sm-2 control-label">重点</label> <div class="col-sm-10"> <textarea class="form-control" rows="4" style="resize: none" name="hy0202"></textarea> </div> </div> <div class="form-group issueDiv"> <div class="col-sm-12" style="float: right;margin-top: 5px;"> <input type="button" class="btn btn-warning myBtn" style="float: right;" value="删除" onclick="deleteIssue(this)"> <input type="button" class="btn btn-primary myBtn" style="float: right;" value="添加议题" onclick="addIssue(this)"> </div> <div style="margin-top:45px"> <label class="col-sm-2 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>议题</label> <div class="col-sm-10"> <textarea class="form-control" rows="4" style="resize: none;" name="hy0301"></textarea> </div> <div class="form-group" style="padding-top: 120px;"> <label class="col-sm-2 control-label">列席</label> <div class="col-sm-9"> <textarea class="form-control" rows="4" style="resize: none" id="liexi' + issueCount + '" readonly></textarea> <input id="liexi' + issueCount + 'Input" type="hidden" name="hy0600"> </div> <div class="col-sm-1"> <input type="button" class="btn btn-primary" value="选择人员" onclick="openIframe(this)" style=" height: 33px;"> </div> </div> <div style="padding-top: 105px;"> <label class="col-sm-2 control-label">附件材料</label> <div class="col-sm-10"> <button class="btn btn-success fileinput-button" type="button">上传</button> <input multiple type="file" onchange="loadFile(this)" style="position:absolute;top:0;left:0;font-size:34px; opacity:0" accept=".doc,.docx,.pdf"> <span style="vertical-align: middle">未上传文件</span> <input type="hidden" id="fileInput' + issueCount + '" name="hy0700"> </div> </div> </div> </div> <div class="form-group"> <div class="col-sm-12" style="margin-top: 5px;text-align: center;"> <input type="button" class="btn btn-primary myBtn" onclick="addSection(this)" value="添加新的会议阶段"> <input type="button" class="btn btn-warning myBtn" onclick="deleteSection(this)" value="删除"> </div> </div> </div>';

    $(sectionDiv).after(innerHtml);
}

// 删除阶段
function devareSection(e) {
    var divs = $('.sectionDiv');
    if (divs.length < 2) {
        alert('最后一个阶段不能删除');
        return;
    }

    var msg = '确定删除此阶段吗？';
    var result = confirmDevare(msg);
    if (result) {
        var sectionDiv = $(e).parents('.sectionDiv');
        //删除阶段时删除子议题下的已上传文件
        var issueDivs = $(sectionDiv).find('.issueDiv');
        for (var i = 0; i < issueDivs.length; i++) {
            devareFiles(issueDivs[0]);
        }
        $(sectionDiv).remove();
    }
}

//计算议题高度，增加议题的时候动态填充
function getIssueHeight(e) {
    var issueDiv = $(e).parents('.issueDiv');
    var issueHeight = $(issueDiv).css('height');
    issueHeight = issueHeight.substring(0, issueHeight.length - 2);
    var issueTop = $(issueDiv).css('margin-top');
    issueTop = issueTop.substring(0, issueTop.length - 2);
    return parseInt(issueHeight) + parseInt(issueTop);
}

//本阶段原来高度，后面加上新增议题高度作为总高度
function getStyleHeight(e) {
    var sectionDiv = $(e).parents('.sectionDiv');
    var styleHeight = $(sectionDiv).css('height');
    styleHeight = styleHeight.substring(0, styleHeight.length - 2);
    return parseInt(styleHeight);
}

//议题计数器，对应为人员框的id值
var issueCount = 1;

//增加议题
function addIssue(e) {
    issueCount++;
    var result = getStyleHeight(e) + getIssueHeight(e);
    var sectionDiv = $(e).parents('.sectionDiv');
    var issueDiv = $(e).parents('.issueDiv');
    $(sectionDiv).css('height', result + 'px');

    var innerHtml = '<div class="form-group issueDiv"> <div class="col-sm-12" style="float: right;margin-top: 5px;"> <input type="button" class="btn btn-warning myBtn" style="float: right;" value="删除" onclick="deleteIssue(this)"> <input type="button" class="btn btn-primary myBtn" style="float: right;" value="添加议题" onclick="addIssue(this)"> </div> <div style="margin-top:45px"> <label class="col-sm-2 control-label"><span style="color: red;">*&nbsp;&nbsp;</span>议题</label> <div class="col-sm-10"> <textarea class="form-control" rows="4" style="resize: none;" name="hy0301"></textarea> </div> <div class="form-group" style="padding-top: 120px;"> <label class="col-sm-2 control-label">列席</label> <div class="col-sm-9"> <textarea class="form-control" rows="4" style="resize: none" id="liexi' + issueCount + '" readonly></textarea> <input id="liexi' + issueCount + 'Input" type="hidden" name="hy0600"> </div> <div class="col-sm-1"> <input type="button" class="btn btn-primary" value="选择人员" onclick="openIframe(this)" style=" height: 33px;"> </div> </div> <div style="padding-top: 105px;"> <label class="col-sm-2 control-label">附件材料</label> <div class="col-sm-10"> <button class="btn btn-success fileinput-button" type="button">上传</button> <input multiple type="file" onchange="loadFile(this)" style="position:absolute;top:0;left:0;font-size:34px; opacity:0" accept=".doc,.docx,.pdf"> <span style="vertical-align: middle">未上传文件</span> <input type="hidden" id="fileInput' + issueCount + '" name="hy0700"> </div> </div> </div> </div>';

    $(issueDiv).after(innerHtml);
}

//删除议题
function devareIssue(e) {
    var sectionDiv = $(e).parents('.sectionDiv');
    var divs = $(sectionDiv).find('.issueDiv');
    if (divs.length < 2) {
        alert('最后一个议题不能删除');
        return;
    }

    var result = getStyleHeight(e) - getIssueHeight(e);
    $(sectionDiv).css('height', result + 'px');

    var msg = '确定删除此议题吗？';
    result = confirmDevare(msg);
    if (result) {
        var issueDiv = $(e).parents('.issueDiv');
        //删除议题下的已上传文件
        devareFiles(issueDiv);
        $(issueDiv).remove();
    }
}

// 如果有上传文件，就给删除
function devareFiles(e) {
    var fileInput = $(e).find('input[name="hy0700"]');
    var fileId = $(fileInput).val();
    if (fileId != '') {
        $.post({
            url: projectName + '/radowAction.do?method=doEvent&pageModel=pages.huiyi.NewConference&eventNames=devareFiles',
            data: {fileId: fileId}
        })
    }
}

var pathName = window.document.location.pathname;
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

//选择人员弹出框，访问指定页面
function openIframe(e) {
    var children = $(e).parent().prev().children();
    var textAreaId = $(children[0]).attr('id');
    var inputId = $(children[1]).attr('id');
    $("#radow_parent_data").val(textAreaId + "," + inputId);

    layui.use('layer', function () {
        var layer = layui.layer;

        layer.open({
            type: 2,
            content: projectName + '/radowAction.do?method=doEvent&pageModel=pages.huiyi.GetPersons',
            area: ['70%', '70%'],
            offset: ['15%', '15%'],
            title: ['请选择人员']
        });
    });
}

//保存会议
function commitHuiyi() {
    //单个元素空值检查
    if ('' == $('#hy0101').val()) {
        alert('会议名称不能为空');
        return false;
    } else if ('' == $('#test1').val()) {
        alert('会议时间不能为空');
        return false;
    } else if ('' == $('#test2').val()) {
        alert('自动关闭日期不能为空');
        return false;
    }
    //重复元素空值检查
    var hy0301s = $('textarea[name="hy0301"]');
    for (var i = 0; i < hy0301s.length; i++) {
        if ('' == $(hy0301s[i]).val()) {
            alert('议题不能为空');
            return false;
        }
    }

    var sectionDivs = $('.sectionDiv');
    //传入后台，议题与阶段无法绑定，需要这个值来判断一个阶段包含几个议题
    var hy0202Intro = '';
    for (var i = 0; i < sectionDivs.length; i++) {
        var length = $(sectionDivs[i]).find('.issueDiv').length;
        hy0202Intro += length + ' ';
    }

    var form = document.getElementsByTagName("form");
    $.ajax({
        url: projectName + '/saveHuiyi',
        type: 'post',
        data: $(form).serialize() + '&hy0202Intro=' + hy0202Intro.trim(),
        success: function (res) {
            alert(res);
            if ('会议新建成功' == res) {
                window.location.reload();
            } else if ('会议更新成功' == res) {
                parent.location.reload();
                parent.layer.close(index);
            }
        },
        error: function (res) {
            alert('新建会议错误，请联系管理员');
        }
    });
}

function cancelHuiyi() {
    //取消新建会议时删除已上传文件
    var issueDivs = $('.issueDiv');
    for (var i = 0; i < issueDivs.length; i++) {
        devareFiles(issueDivs[0]);
    }
    window.location.reload();
}