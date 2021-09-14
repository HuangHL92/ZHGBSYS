<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<%@include file="/comOpenWinInit2.jsp" %>
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
</script>
<odin:hidden property="checkregid"/>
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" title="人员信息" 
	autoFill="true"  bbarId="pageToolBar" grouping="true" groupCol="sortid1" 
	url="/" groupTextTpl="报告人：{[values.rs[0].data.crp001]}({[values.rs.length]}人)">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="a0000" />
		<odin:gridDataCol name="crp000" />
		<odin:gridDataCol name="a3600" />
		<odin:gridDataCol name="crp001" />
		<odin:gridDataCol name="crp002" />
		<odin:gridDataCol name="crp003" />
		<odin:gridDataCol name="crp004" />
		<odin:gridDataCol name="crp005" />
		<odin:gridDataCol name="crp006" />
		<odin:gridDataCol name="crp007" />
		<odin:gridDataCol name="crp008" />
		<odin:gridDataCol name="sortid1" />
		
		<odin:gridDataCol name="checkregid" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn2 dataIndex="sortid1" header="序号" menuDisabled="true" edited="false" editor="text" hidden="true"/> 
		<odin:gridEditColumn2 dataIndex="crp002" header="称谓" width="70" sortable="false" menuDisabled="true" edited="false" editor="select" selectData="['配偶','配偶'],['子女','子女']" align="center" />
		<odin:gridEditColumn2 dataIndex="crp001" header="姓名" width="70" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
		<odin:gridEditColumn2 dataIndex="crp003" header="出生日期" width="70" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
		<odin:gridEditColumn2 dataIndex="crp006" header="身份证" width="170" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
		<odin:gridEditColumn2 dataIndex="crp005" header="政治面貌" width="70" sortable="false" menuDisabled="true" edited="false" editor="select" codeType="GB4762" align="center" />
		<odin:gridEditColumn2 dataIndex="crp004" header="职务" width="180" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
		
		<odin:gridEditColumn2 dataIndex="crp007" header="" menuDisabled="true" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn2 dataIndex="crp008" header="" menuDisabled="true" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn2 dataIndex="a0000" header="人员id" menuDisabled="true" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn2 dataIndex="crp000" header="id" menuDisabled="true" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn2 dataIndex="a3600" header="家庭成员id"  menuDisabled="true" edited="false" editor="text" hidden="true" isLast="true" />
		
	</odin:gridColumnModel>
	<odin:gridJsonData>
		{
	        data:[]
	    }
	</odin:gridJsonData>
</odin:editgrid2>

<script type="text/javascript">
function selectRow(a,store){
	var grid = Ext.getCmp('memberGrid');
	var colIndexArray = [1];
	//span(grid,1,1,'row',4);
}

function mergeGrid(grid, colIndexArray, isAllSome) {
    isAllSome = isAllSome == undefined ? true : isAllSome; // 默认为true
    // 1.是否含有数据
    //var gridView = document.getElementById(grid.getView().getId() + '-body');
    var gridView = document.getElementById('ext-gen16');
    console.log(gridView);
    if (gridView == null) {
        return;
    }
    // 2.获取Grid的所有tr
    var trArray = [];
    //if (grid.layout.type == 'table') { // 若是table部署方式，获取的tr方式如下
        trArray = gridView.childNodes;
    //} else {
    //    trArray = gridView.getElementsByTagName('tr');
    //}
        console.log(trArray);
    // 3.进行合并操作
    if (isAllSome) { // 3.1 全部列合并：只有相邻tr所指定的td都相同才会进行合并
        var lastTr = trArray[0]; // 指向第一行
        // 1)遍历grid的tr，从第二个数据行开始
        for (var i = 1, trLength = trArray.length; i < trLength; i++) {
            var thisTr = trArray[i];
            var isPass = true; // 是否验证通过
            // 2)遍历需要合并的列
            for (var j = 0, colArrayLength = colIndexArray.length; j < colArrayLength; j++) {
                var colIndex = colIndexArray[j];
                // 3)比较2个td的列是否匹配，若不匹配，就把last指向当前列
                if (lastTr.childNodes[colIndex].innerText != thisTr.childNodes[colIndex].innerText) {
                    lastTr = thisTr;
                    isPass = false;
                    break;
                }
            }

            // 4)若colIndexArray验证通过，就把当前行合并到'合并行'
            if (isPass) {
                for (var j = 0, colArrayLength = colIndexArray.length; j < colArrayLength; j++) {
                    var colIndex = colIndexArray[j];
                    // 5)设置合并行的td rowspan属性
                    if (lastTr.childNodes[colIndex].hasAttribute('rowspan')) {
                        var rowspan = lastTr.childNodes[colIndex].getAttribute('rowspan') - 0;
                        rowspan++;
                        lastTr.childNodes[colIndex].setAttribute('rowspan', rowspan);
                    } else {
                        lastTr.childNodes[colIndex].setAttribute('rowspan', '2');
                    }
                    // lastTr.childNodes[colIndex].style['text-align'] = 'center';; // 水平居中
                    lastTr.childNodes[colIndex].style['vertical-align'] = 'middle';; // 纵向居中
                    thisTr.childNodes[colIndex].style.display = 'none';

                }
            }
        }
    } else { // 3.2 逐个列合并：每个列在前面列合并的前提下可分别合并
        // 1)遍历列的序号数组
        for (var i = 0, colArrayLength = colIndexArray.length; i < colArrayLength; i++) {
            var colIndex = colIndexArray[i];
            var lastTr = trArray[0]; // 合并tr，默认为第一行数据
            // 2)遍历grid的tr，从第二个数据行开始
            for (var j = 1, trLength = trArray.length; j < trLength; j++) {
                var thisTr = trArray[j];
                // 3)2个tr的td内容一样
                if (lastTr.childNodes[colIndex].innerText == thisTr.childNodes[colIndex].innerText) {
                    // 4)若前面的td未合并，后面的td都不进行合并操作
                    if (i > 0 && thisTr.childNodes[colIndexArray[i - 1]].style.display != 'none') {
                        lastTr = thisTr;
                        continue;
                    } else {
                        // 5)符合条件合并td
                        if (lastTr.childNodes[colIndex].hasAttribute('rowspan')) {
                            var rowspan = lastTr.childNodes[colIndex].getAttribute('rowspan') - 0;
                            rowspan++;
                            lastTr.childNodes[colIndex].setAttribute('rowspan', rowspan);
                        } else {
                            lastTr.childNodes[colIndex].setAttribute('rowspan', '2');
                        }
                       // lastTr.childNodes[colIndex].style['text-align'] = 'center';; // 水平居中
                        lastTr.childNodes[colIndex].style['vertical-align'] = 'middle';; // 纵向居中
                        thisTr.childNodes[colIndex].style.display = 'none'; // 当前行隐藏
                    }
                } else {
                    // 5)2个tr的td内容不一样
                    lastTr = thisTr;
                }
            }
        }
    }
}

var span = function(grid,row,col,type,num){
	switch(type){
		case'row':     //合并行
			tds = Ext.get(grid.view.getNode(row)).query('td');
			Ext.get(tds[col]).set({rowspan:num});
			Ext.get(tds[col]).setStyle({'vertical-align':'middle'});
			for(var i = row + 1; i < row + num; i ++){
				Ext.get(Ext.get(grid.view.getNode(i)).query('td')[col]).destroy();
			}break;
		case 'col':   //合并列
			tds = Ext.get(grid.view.getNode(row)).query('td');
			Ext.get(tds[col]).set({colspan:num});
		break;
	}
	 
}


Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height);
	document.getElementById('checkregid').value = parentParam.checkregid;
});
function infoSearch(){
	radow.doEvent('memberGrid.dogridquery');
}
</script>


