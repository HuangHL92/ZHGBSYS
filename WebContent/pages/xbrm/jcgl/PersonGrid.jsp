<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<%@include file="/comOpenWinInit2.jsp" %>
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
</script>
<odin:hidden property="checkregid"/>
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" title="��Ա��Ϣ" 
	autoFill="true"  bbarId="pageToolBar" grouping="true" groupCol="sortid1" 
	url="/" groupTextTpl="�����ˣ�{[values.rs[0].data.crp001]}({[values.rs.length]}��)">
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
		<odin:gridEditColumn2 dataIndex="sortid1" header="���" menuDisabled="true" edited="false" editor="text" hidden="true"/> 
		<odin:gridEditColumn2 dataIndex="crp002" header="��ν" width="70" sortable="false" menuDisabled="true" edited="false" editor="select" selectData="['��ż','��ż'],['��Ů','��Ů']" align="center" />
		<odin:gridEditColumn2 dataIndex="crp001" header="����" width="70" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
		<odin:gridEditColumn2 dataIndex="crp003" header="��������" width="70" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
		<odin:gridEditColumn2 dataIndex="crp006" header="���֤" width="170" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
		<odin:gridEditColumn2 dataIndex="crp005" header="������ò" width="70" sortable="false" menuDisabled="true" edited="false" editor="select" codeType="GB4762" align="center" />
		<odin:gridEditColumn2 dataIndex="crp004" header="ְ��" width="180" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
		
		<odin:gridEditColumn2 dataIndex="crp007" header="" menuDisabled="true" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn2 dataIndex="crp008" header="" menuDisabled="true" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn2 dataIndex="a0000" header="��Աid" menuDisabled="true" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn2 dataIndex="crp000" header="id" menuDisabled="true" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn2 dataIndex="a3600" header="��ͥ��Աid"  menuDisabled="true" edited="false" editor="text" hidden="true" isLast="true" />
		
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
    isAllSome = isAllSome == undefined ? true : isAllSome; // Ĭ��Ϊtrue
    // 1.�Ƿ�������
    //var gridView = document.getElementById(grid.getView().getId() + '-body');
    var gridView = document.getElementById('ext-gen16');
    console.log(gridView);
    if (gridView == null) {
        return;
    }
    // 2.��ȡGrid������tr
    var trArray = [];
    //if (grid.layout.type == 'table') { // ����table����ʽ����ȡ��tr��ʽ����
        trArray = gridView.childNodes;
    //} else {
    //    trArray = gridView.getElementsByTagName('tr');
    //}
        console.log(trArray);
    // 3.���кϲ�����
    if (isAllSome) { // 3.1 ȫ���кϲ���ֻ������tr��ָ����td����ͬ�Ż���кϲ�
        var lastTr = trArray[0]; // ָ���һ��
        // 1)����grid��tr���ӵڶ��������п�ʼ
        for (var i = 1, trLength = trArray.length; i < trLength; i++) {
            var thisTr = trArray[i];
            var isPass = true; // �Ƿ���֤ͨ��
            // 2)������Ҫ�ϲ�����
            for (var j = 0, colArrayLength = colIndexArray.length; j < colArrayLength; j++) {
                var colIndex = colIndexArray[j];
                // 3)�Ƚ�2��td�����Ƿ�ƥ�䣬����ƥ�䣬�Ͱ�lastָ��ǰ��
                if (lastTr.childNodes[colIndex].innerText != thisTr.childNodes[colIndex].innerText) {
                    lastTr = thisTr;
                    isPass = false;
                    break;
                }
            }

            // 4)��colIndexArray��֤ͨ�����Ͱѵ�ǰ�кϲ���'�ϲ���'
            if (isPass) {
                for (var j = 0, colArrayLength = colIndexArray.length; j < colArrayLength; j++) {
                    var colIndex = colIndexArray[j];
                    // 5)���úϲ��е�td rowspan����
                    if (lastTr.childNodes[colIndex].hasAttribute('rowspan')) {
                        var rowspan = lastTr.childNodes[colIndex].getAttribute('rowspan') - 0;
                        rowspan++;
                        lastTr.childNodes[colIndex].setAttribute('rowspan', rowspan);
                    } else {
                        lastTr.childNodes[colIndex].setAttribute('rowspan', '2');
                    }
                    // lastTr.childNodes[colIndex].style['text-align'] = 'center';; // ˮƽ����
                    lastTr.childNodes[colIndex].style['vertical-align'] = 'middle';; // �������
                    thisTr.childNodes[colIndex].style.display = 'none';

                }
            }
        }
    } else { // 3.2 ����кϲ���ÿ������ǰ���кϲ���ǰ���¿ɷֱ�ϲ�
        // 1)�����е��������
        for (var i = 0, colArrayLength = colIndexArray.length; i < colArrayLength; i++) {
            var colIndex = colIndexArray[i];
            var lastTr = trArray[0]; // �ϲ�tr��Ĭ��Ϊ��һ������
            // 2)����grid��tr���ӵڶ��������п�ʼ
            for (var j = 1, trLength = trArray.length; j < trLength; j++) {
                var thisTr = trArray[j];
                // 3)2��tr��td����һ��
                if (lastTr.childNodes[colIndex].innerText == thisTr.childNodes[colIndex].innerText) {
                    // 4)��ǰ���tdδ�ϲ��������td�������кϲ�����
                    if (i > 0 && thisTr.childNodes[colIndexArray[i - 1]].style.display != 'none') {
                        lastTr = thisTr;
                        continue;
                    } else {
                        // 5)���������ϲ�td
                        if (lastTr.childNodes[colIndex].hasAttribute('rowspan')) {
                            var rowspan = lastTr.childNodes[colIndex].getAttribute('rowspan') - 0;
                            rowspan++;
                            lastTr.childNodes[colIndex].setAttribute('rowspan', rowspan);
                        } else {
                            lastTr.childNodes[colIndex].setAttribute('rowspan', '2');
                        }
                       // lastTr.childNodes[colIndex].style['text-align'] = 'center';; // ˮƽ����
                        lastTr.childNodes[colIndex].style['vertical-align'] = 'middle';; // �������
                        thisTr.childNodes[colIndex].style.display = 'none'; // ��ǰ������
                    }
                } else {
                    // 5)2��tr��td���ݲ�һ��
                    lastTr = thisTr;
                }
            }
        }
    }
}

var span = function(grid,row,col,type,num){
	switch(type){
		case'row':     //�ϲ���
			tds = Ext.get(grid.view.getNode(row)).query('td');
			Ext.get(tds[col]).set({rowspan:num});
			Ext.get(tds[col]).setStyle({'vertical-align':'middle'});
			for(var i = row + 1; i < row + num; i ++){
				Ext.get(Ext.get(grid.view.getNode(i)).query('td')[col]).destroy();
			}break;
		case 'col':   //�ϲ���
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


