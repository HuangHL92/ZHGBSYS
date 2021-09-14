<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="pages/huiyi/static/jquery/jquery-1.12.4.js" charset="UTF-8"></script>
    <link href="pages/huiyi/static/bootstrap/css/bootstrap.min.css" charset="UTF-8" rel="stylesheet">
    <script src="pages/huiyi/static/bootstrap/js/bootstrap.min.js" charset="UTF-8"></script>
    <script src="pages/huiyi/static/js/index.js" charset="UTF-8"></script>

    <script src="pages/huiyi/static/layui/layui.js" charset="UTF-8"></script>
    <link href="pages/huiyi/static/layui/css/layui.css" charset="UTF-8" media="all" rel="stylesheet">
</head>
<style>
    .sectionPreview {
        text-align: center;
        font-size: 40px;
        margin-top: 20px;
    }

    .chapter {
        margin-top: 20px;
    }

    .filePreview {
        vertical-align: middle;
        height: 50px;
        line-height: 50px;
    }
</style>
<body style="font-family: STFangsong;font-size: 30px;width: 80%;margin: auto;padding-bottom: 20px;">
<input type="hidden" id="editHuiyiId">

<div style="color: red;font-size: 40px;text-align: center;margin-top: 20px;" id="hy0101"></div>
<div style="color: red;font-size: 50px;text-align: center;margin-top: 15px;">����</div>
<div style="text-align: center;border-bottom: 10px solid red;margin-top: 15px;" id="hy0102"></div>


</body>
<script>
    let index = parent.layer.getFrameIndex(window.name);
    //�õ���ҳ��Ļ���id
    $('#editHuiyiId').val(parent.$('#editHuiyiHiddenId').val());

    $.post({
        url: projectName + '/radowAction.do?method=doEvent&pageModel=pages.huiyi.EditHuiyi&eventNames=getHuiyiInfo',
        data: {hy0100: $('#editHuiyiId').val()},
        dataType: 'json',
        success: function (res) {
            let hy01 = res.hy01;
            //��������
            $('#hy0101').html(hy01.hy0101);
            let hy0102 = hy01.hy0102;
            if (Number(hy0102.substr(11, 2)) < 12) {
                hy0102 = '(' + hy0102.substr(0, 4) + '��' + hy0102.substr(5, 2) + '��' + hy0102.substr(8, 2) + '�� ' + '����' + hy0102.substr(11) + ')';
            } else {
                hy0102 = '(' + hy0102.substr(0, 4) + '��' + hy0102.substr(5, 2) + '��' + hy0102.substr(8, 2) + '�� ' + '����' + hy0102.substr(11) + ')';
            }
            //����ʱ��
            $('#hy0102').html(hy0102);

            let hy02List = res.hy02List;
            let innerHtml = '';
            hy02List.forEach(function (hy02, indexHy02, arrayHy02) {
                //�ڶ��ٽ׶�
                let sectionName = '��' + NumberToChinese(indexHy02 + 1) + '�׶�';
                innerHtml += '<div class="sectionPreview">' + sectionName + '</div>';
                //�׶��ص�
                innerHtml += '<div class="chapter">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + hy02.hy0202 + '</div>';

                let hy03List = hy02.hy03List;
                hy03List.forEach(function (hy03, indexHy03, arrayHy03) {
                    //��������
                    innerHtml += '<div class="chapter">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + (indexHy03 + 1) + '.' + hy03.hy0301 + '</div>';

                    let hy06List = hy03.hy06List;
                    //������ϯ��Ա
                    innerHtml += '<div class="chapter">';
                    hy06List.forEach(function (hy06, indexHy06, arrayHy06) {
                        innerHtml += hy06.hy0501 + '&nbsp;&nbsp;&nbsp;&nbsp;';
                    });
                    innerHtml += '</div>';

                    //������Ϣ
                    let hy07List = hy03.hy07List;
                    hy07List.forEach(function (hy07) {
                        innerHtml += '<div class="filePreview">' + hy07.hy0701 + '</div>';
                    });
                });
            });
            innerHtml += '<div class="chapter">��ϯ��</div>';
            let hy04List = hy01.hy04List;
            //��ϯ��Ա
            innerHtml += '<div class="chapter" style="margin-bottom: 20px;">';
            hy04List.forEach(function (hy04) {
                innerHtml += hy04.hy0501 + '&nbsp;&nbsp;&nbsp;&nbsp;';
            });
            innerHtml += '</div>';
            $('body').append(innerHtml);
        }
    });

    function SectionToChinese(section) {
        let strIns = '', chnStr = '';
        let unitPos = 0;
        let zero = true;
        while (section > 0) {
            let v = section % 10;
            if (v === 0) {
                if (!zero) {
                    zero = true;
                    chnStr = chnNumChar[v] + chnStr;
                }
            } else {
                zero = false;
                strIns = chnNumChar[v];
                strIns += chnUnitChar[unitPos];
                chnStr = strIns + chnStr;
            }
            unitPos++;
            section = Math.floor(section / 10);
        }
        return chnStr;
    }

    function NumberToChinese(num) {
        let unitPos = 0;
        let strIns = '', chnStr = '';
        let needZero = false;

        if (num === 0) {
            return chnNumChar[0];
        }

        while (num > 0) {
            let section = num % 10000;
            if (needZero) {
                chnStr = chnNumChar[0] + chnStr;
            }
            strIns = SectionToChinese(section);
            strIns += (section !== 0) ? chnUnitSection[unitPos] : chnUnitSection[0];
            chnStr = strIns + chnStr;
            needZero = (section < 1000) && (section > 0);
            num = Math.floor(num / 10000);
            unitPos++;
        }

        return chnStr;
    }

    let chnNumChar = ["��", "һ", "��", "��", "��", "��", "��", "��", "��", "��"];

    let chnUnitSection = ["", "��", "��", "����", "����"];

    let chnUnitChar = ["", "ʮ", "��", "ǧ"];
</script>
</html>
